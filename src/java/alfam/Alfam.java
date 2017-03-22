/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alfam;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 * Ha lo scopo di replicare il calcolo delle emissioni di ammoniaca
 * dalla distribuzione in campo degli effluenti seguendo il modello ALFAM
 * Per maggiori chiarimenti il riferimento è il documento word Funzione ALFAM.docx in 
 * renuwal disco di rete . I campi superficie , manurerate e caratteristiche chimiche devono essere passati alla 
 * classe. E' un singleton perchè verra usato molte volte solo per calcolo di Nmax e Km.Per poter usare 
 * correttamente questa classe bisogna chiederne un istanza , impostargli superficie e manureRate ,id_epoca,id_tipo_refluo,id_tecnica
 * chiamare il metodo parametriAlfam verificare se questo ha tornato una stringa vuota.
 * Manurerate contiene il parametro inserito dall'utente nel campo m3/ha
 * @author giorgio
 */
public class Alfam {
    /**
     * inizio variabili per il calcolo di get_Lost_Tan_Tot_kg()
     */
    //private double manuretan = 0;
    private double manurerate = 0;
    private double lost_tan_rel_perc = 0;
    /**
     * fine variabili per il calcolo di get_Lost_Tan_Tot_kg()
     */
    /**
     * inizio variabili per il calcolo di Nmax
     */
    //umidità del suolo: secco = 0 ; umido = 1
    //la prendi in funzione del mese dalla tabella epoca
    private int l_sm1 = 0;
    //per le 4 epoche previste nel piano di distribuzione e quindi: 
    //primavera - estate - autunno - fine inverno liprendi dalla tabella epoca in funzione del mese
    private double airtemp = 0;
    //costante 2 m/s
    private int windspeed = 2;
    //refluo: bovino = 0 ; suino = 1
    private int l_mt1 = 0;
    //sostanza secca del refluo (%). Questo dato si ricava da Renuwal - sezione “confronto” rapportando DM/Volume
    private double manureDM  = 0;
    //azoto ammoniacale (kg/m3). Questo dato si ricava da Renuwal - sezione “confronto” rapportando TAN/Volume
    private double manureTan = 0;
    //1 (se “tecnica” è piatto deviatore, altrimenti =0)
    private int l_ma0 = 0;
    //1 (se “tecnica” è rasoterra per strisce, altrimenti =0)
    private int l_ma1 = 0;
    //1 (se “tecnica” è iniezione superficiale a bande, altrimenti =0)
    private int l_ma2 = 0;
    //1 (se “tecnica” è solchi chiusi, altrimenti =0)
    private int l_ma3 = 0;
    //1 (se “tecnica” è solchi aperti, altrimenti =0)
    private int l_ma4 = 0;
     /**
     * fine variabili per il calcolo di Nmax
     */
    //arriva dal confronto
    private db.RisultatoConfronto caratteristicheChmiche = null;
    //superficie come somma appezzamenti per una distribuzione
    private double superficie = 0;
    //mi serve per definire l_ma0,....,l_ma4 in funzione del tipo di tecnica
    private int id_tecnica = 0;
    //è l’intervallo di ore in cui si verifica l’emissione di ammoniaca in seguito alla distribuzione in campo del refluo
    private int tempo_h = 150;
    //bovino,suino,avicolo,altro,biomasse mi serve per definire l_mt1
    private int id_refluo = 0;
    //letame tal quale, liquame tal quale, digestato tal quale, separato liquido da digestato
    private int id_forma_refluo = 0;
    //mi serve per definre airTemp in funzoine del tipo di epoca
    private int id_epoca = 0;
    //
    private double coe_tipo = 1.2;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    /*private static Alfam instance = null;
    protected Alfam(){
    }
    public static Alfam getInstance() {
      if(instance == null) {
         instance = new Alfam();
      }
      return instance;
   }*/
    
    public Alfam(){}
    
    /**
     * la tecnica incide su tempo_h
     * caratteristicheChimiche determinano manureTan e manureDM
     * epoca si porta con se L_SM1 e AirTemp
     * quantitaDistribuita copre manureRate gia rapportato alle quantita presenti nel db dopo il 
     * confronto ovvero è gia proporzionato alle quantita di tutte le specie corrisponde ad una
     * porzione della casella del piano di distribuzione “m3/ha”
     * id_refluo bovino,suino,avicolo,biomasse,altro
     * id_tipo_refluo liquame tal quale, letame tal quale, digestato tal quale,separato solido da digestato ....
     * superficie : superficie dell'appezzamento
     */
    public String parametriAlfam(int id_tecnica,db.RisultatoConfronto caratteristicheChimiche,db.Epoca epoca,double quantitaDistribuita,int id_refluo,int tipo_refluo){
        
        String ritorno = "";
        if(id_tecnica < 1 || id_tecnica > 10) {
            return "Valore della tecnica errato";
        }
        else
        {
            this.id_tecnica = id_tecnica;
            this.tempo_h = 150;
            
            //imposto i parametri L_MA0 - L_MA4
            set_L_MA04(id_tecnica);
            
            //con piatto deviatore e interramento entro 24h
            if(this.id_tecnica == 2)
            {
                this.tempo_h = 24; 
            }
            //con piatto deviatore e interramento entro 4h
            if(this.id_tecnica == 3)
            {
                this.tempo_h = 4; 
            }
        }
        
        //calcolo manureTAn e manureDm usando caratteristicheChimiche che arriva da db.risultatoConfronto
        if(caratteristicheChimiche == null)
        {
            return "Risultato Confronto null";
        }else
        {
             this.caratteristicheChmiche = caratteristicheChimiche;
            
           //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" idrefluo "+id_refluo+" tiporefluo "+ tipo_refluo);

           // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+"   tan l bov " + this.caratteristicheChmiche.getTanLBov()+" m l bov "+  this.caratteristicheChmiche.getM3LBov()+" idrefluo "+id_refluo+" tiporefluo "+ tipo_refluo);
            
           
            double[] temp = this.calcolaManureTanDM(id_refluo,tipo_refluo);
            this.manureTan = temp[0];
            this.manureDM = 0.06;//temp[1]; imposto in un secondo momento da flavio e alberto
        }
        
        //uso l'epoca per recuperare L_SM1 e AirTemp
        if(epoca == null)
        {
           return "Epoca null";
        }else
        {
          this.airtemp = epoca.getAirtemp();  
          this.l_sm1 = epoca.getLSm1();
        }
        
        //imposta la quantita distribuita        
        this.manurerate = quantitaDistribuita;
        
        this.id_refluo = id_refluo;
        this.id_forma_refluo = tipo_refluo;
         
        setL_mt1_coe_tipo(this.id_refluo,this.id_forma_refluo);
        return ritorno;
    }
    
    /**
     * imposta i parametri l_mt1 e coe_tipo infunzione del refluo(bovino ,suino,avicolo)
     * e della forma
     * @param id_refluo1 bovino suino avicolo altro ..
     * @param id_forma_refluo1  liquame tal quale, letame tal quale , digestato tal quale ,....
     */
    private boolean setL_mt1_coe_tipo(int id_refluo1,int id_forma_refluo1){
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }     
         
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",id_forma_refluo1);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
         
          Query q2 = entityManager.createNamedQuery("ProvenienzaRefluo.findById").setParameter("id",id_refluo1);
          db.ProvenienzaRefluo provenienzaRefluo = (db.ProvenienzaRefluo)q2.getSingleResult();
         
          Query q = entityManager.createQuery("Select a FROM MappingRefluoTipoAlfam a WHERE a.idProvenienzaRefluo=?1 and a.idTipoFormaRefluo=?2 ");
          q.setParameter(1,provenienzaRefluo);
          q.setParameter(2, tipoformarefluo);
          
          List<db.MappingRefluoTipoAlfam> listaMapping =q.getResultList();
          db.MappingRefluoTipoAlfam mapping = null;
          
          if(listaMapping.isEmpty()) {
            return false;
            }
          else
          {
            mapping = listaMapping.get(0);
          }
          
          this.l_mt1 = mapping.getLMt1();
          this.coe_tipo = mapping.getCoeTipo();
                    
          Connessione.getInstance().chiudi();
          
          return true;
    }
    
    /**
     * imposta i  parametri I_MA0-L_MA4 in funzione del valore di id_tecnica
     * come da word FunzioneAlfam
     * @param id_tecnica 
     */
    private void set_L_MA04(int id_tecnica){
        switch(id_tecnica)
        {
            case 6://iniezione in pressione
                this.l_ma0 = this.l_ma1 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;
            case 1://piatto deviatore 
                 this.l_ma0 = 1;
                 this.l_ma1 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;
            case 2://piatto deviatore e interramento entro 24 ore
                 this.l_ma0 = 1;
                 this.l_ma1 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;
            case 3://piatto deviatore e interramento entro 4 ore
                 this.l_ma0 = 1;
                 this.l_ma1 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;   
             case 4://rasoterra per strisce
                 this.l_ma1 = 1;
                 this.l_ma0 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;   
            case 5://fertirrigazione con adacquamento
                 this.l_ma1 = 1;
                 this.l_ma0 = this.l_ma2=this.l_ma3=this.l_ma4 = 0;
                break;     
           case 7://iniezione superficiale a bande
                 this.l_ma2 = 1;
                 this.l_ma0 = this.l_ma1=this.l_ma3=this.l_ma4 = 0;
                break;          
            case 8://solchi aperti
                 this.l_ma4 = 1;
                 this.l_ma0 = this.l_ma1=this.l_ma3=this.l_ma2 = 0;
                break;     
           case 9://solchi chiusi
                 this.l_ma3 = 1;
                 this.l_ma0 = this.l_ma1=this.l_ma4=this.l_ma2 = 0;
                break;          
        }
    }
    
    /**
     * ManureTan = azoto ammoniacale (kg/m3). Questo dato si ricava da Renuwal - sezione “confronto” rapportando TAN/Volume
     * (come somma su letame e liquame su tutte le specie animali ?)
     * ManureDM = sostanza secca del refluo (%). Questo dato si ricava da Renuwal - sezione “confronto” rapportando DM/Volume
     * @return array di double con manureTan nella prima posizione e manureDM nella seconda
     */
    private double[] calcolaManureTanDM(int id_refluo,int tipo_refluo)
    {
        double[] tandm = new double[2];
       
          //liquami
        if(tipo_refluo == 1 ||tipo_refluo == 3 ||tipo_refluo == 4 ||tipo_refluo == 5 )
        {
            switch(id_refluo)
            {
                case 1://bovino
                    tandm[0] = this.caratteristicheChmiche.getTanLBov() / this.caratteristicheChmiche.getM3LBov();
                    tandm[1] = this.caratteristicheChmiche.getDmLBov() / this.caratteristicheChmiche.getM3LBov();
                    break;
                 case 2://suino
                    tandm[0] = this.caratteristicheChmiche.getTanLSui() / this.caratteristicheChmiche.getM3LSui();
                    tandm[1] = this.caratteristicheChmiche.getDmLSui() / this.caratteristicheChmiche.getM3LSui();
                    break;   
                 case 3://avicolo
                    tandm[0] = this.caratteristicheChmiche.getTanLAvi() / this.caratteristicheChmiche.getM3LAvi();
                    tandm[1] = this.caratteristicheChmiche.getDmLAvi() / this.caratteristicheChmiche.getM3LAvi();
                    break;
                 case 4://altro
                     tandm[0] = this.caratteristicheChmiche.getTanLAlt() / this.caratteristicheChmiche.getM3LAlt();
                    tandm[1] = this.caratteristicheChmiche.getDmLAlt() / this.caratteristicheChmiche.getM3LAlt();
                    break;
                 case 9://biomasse
                    tandm[0] = this.caratteristicheChmiche.getTanLBio() / this.caratteristicheChmiche.getM3LBio();
                    tandm[1] = this.caratteristicheChmiche.getDmLBio() / this.caratteristicheChmiche.getM3LBio();
                    break;    
            }
        }
        //letami
        if(tipo_refluo == 2 ||tipo_refluo == 6 ||tipo_refluo == 7 )
        {
         switch(id_refluo)
            {
               case 1://bovino
                    tandm[0] = this.caratteristicheChmiche.getTanPBov() / this.caratteristicheChmiche.getM3PBov();
                    tandm[1] = this.caratteristicheChmiche.getDmPBov() / this.caratteristicheChmiche.getM3PBov();
                    break;
                 case 2://suino
                    tandm[0] = this.caratteristicheChmiche.getTanPSui() / this.caratteristicheChmiche.getM3PSui();
                    tandm[1] = this.caratteristicheChmiche.getDmPSui() / this.caratteristicheChmiche.getM3PSui();
                    break;   
                 case 3://avicolo
                    tandm[0] = this.caratteristicheChmiche.getTanPAvi() / this.caratteristicheChmiche.getM3PAvi();
                    tandm[1] = this.caratteristicheChmiche.getDmPAvi() / this.caratteristicheChmiche.getM3PAvi();
                    break;
                 case 4://altro
                     tandm[0] = this.caratteristicheChmiche.getTanPAlt() / this.caratteristicheChmiche.getM3PAlt();
                    tandm[1] = this.caratteristicheChmiche.getDmPAlt() / this.caratteristicheChmiche.getM3PAlt();
                    break;
                 case 9://biomasse
                    tandm[0] = this.caratteristicheChmiche.getTanPBio() / this.caratteristicheChmiche.getM3PBio();
                    tandm[1] = this.caratteristicheChmiche.getDmPBio() / this.caratteristicheChmiche.getM3PBio();
                    break;    
            }
            
        }  
      
        
        return tandm;
    }
  
    
    /**
     * Perdita totale di azoto sotto forma di Ammoniaca: lost_TAN_tot_kg
     * Calcolo della perdita di TAN, sotto forma di emissione in aria, espressa in kgN/ha
     * @return 
     */
    public double get_Lost_Tan_Tot_kg(){
        return this.manureTan * this.manurerate * this. get_Lost_Tan_rel_perc() / 100;
    }
    
    /**
     * Perdita totale di azoto sotto forma di Ammoniaca: lost_TAN_%
     * Calcolo della perdita di TAN, sotto forma di emissione in aria, espressa in percentuale
     * @return 
     */
    public double get_Lost_Tan_rel_perc(){
        return 100 * get_Nmax() *this.tempo_h / (this.tempo_h + this.get_Km());
    }
    /**
     * TAN massimo che si può perdere per volatilizzazione rispetto al TAN distribuito al campo 
     * @return 
     */
    public double get_Nmax(){
        double valore = -6.5757 + 0.0971 * this.l_sm1 + 0.0221 * this.airtemp + 0.0409 * this.windspeed + (-0.156) * this.l_mt1 +
                +0.1024 * this.manureDM + (-0.1888) * this.manureTan + 3.5691 * this.l_ma0 + 3.0198 * this.l_ma1 + 3.1592 * this.l_ma2 +
                +2.2702 * this.l_ma3 + 2.9582 * this.l_ma4 + (-0.00433)*this.manurerate + 2.4291 + (-0.5485) * this.coe_tipo;
        
        return Math.exp(valore);
    }
    
    /**
     * Km = corrisponde al numero di ore in cui si raggiunge Nmax.
     * @return 
     */
    public double get_Km()
    {
        double valore = 0.037 + 0.0974 * this.l_sm1 + (-0.0409) * this.airtemp + (-0.0517) * this.windspeed + 1.3567 * this.l_mt1 +
                +0.1614 * this.manureDM + 0.1011 * this.manureTan + 0.0175 * this.manurerate + 0.7024 ;
        
        return Math.exp(valore);
        
    }

    /**
     * @return the manurerate
     */
   /* public double getManurerate() {
        return manurerate;
    }
*/
    /**
     * @param manurerate the manurerate to set
     */
  /*  public void setManurerate(double manurerate) {
        this.manurerate = manurerate;
    }*/

    /**
     * @return the caratteristicheChmiche
     */
   /* public db.RisultatoConfronto getCaratteristicheChmiche() {
        return caratteristicheChmiche;
    }*/

    /**
     * @param caratteristicheChmiche the caratteristicheChmiche to set
     */
  /*  public void setCaratteristicheChmiche(db.RisultatoConfronto caratteristicheChmiche) {
        this.caratteristicheChmiche = caratteristicheChmiche;
    }*/

    /**
     * @return the superficie
     */
 /*   public double getSuperficie() {
        return superficie;
    }*/

    /**
     * @param superficie the superficie to set
     */
  /*  public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }*/

    /**
     * @return the l_sm1
     */
   /* public int getL_sm1() {
        return l_sm1;
    }*/

    /**
     * @param l_sm1 the l_sm1 to set
     */
  /*  public void setL_sm1(int l_sm1) {
        this.l_sm1 = l_sm1;
    }*/

    /**
     * @return the airtemp
     */
   /* public double getAirtemp() {
        return airtemp;
    }*/

    /**
     * @param airtemp the airtemp to set
     */
   /* public void setAirtemp(double airtemp) {
        this.airtemp = airtemp;
    }*/

    /**
     * @return the l_mt1
     */
   /* public int getL_mt1() {
        return l_mt1;
    }*/

    /**
     * @param l_mt1 the l_mt1 to set
     */
  /*  public void setL_mt1(int l_mt1) {
        this.l_mt1 = l_mt1;
    }*/

    /**
     * @return the id_tecnica
     */
   /* public int getId_tecnica() {
        return id_tecnica;
    }*/

    /**
     * @param id_tecnica the id_tecnica to set
     */
   /* public void setId_tecnica(int id_tecnica) {
        this.id_tecnica = id_tecnica;
    }*/

    /**
     * @return the id_tipo_refluo
     */
   /* public int getId_tipo_refluo() {
        return id_tipo_refluo;
    }*/

    /**
     * @param id_tipo_refluo the id_tipo_refluo to set
     */
   /* public void setId_tipo_refluo(int id_tipo_refluo) {
        this.id_tipo_refluo = id_tipo_refluo;
    }*/

    /**
     * @return the id_epoca
     */
   /* public int getId_epoca() {
        return id_epoca;
    }*/

    /**
     * @param id_epoca the id_epoca to set
     */
  /*  public void setId_epoca(int id_epoca) {
        this.id_epoca = id_epoca;
    }*/
    
}
