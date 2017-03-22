/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parametri;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import ager.trattamenti.Vasca;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.Utente;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.Parametridiprogetto;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
/**
 *Classe che si occupa di mostrare il dettaglio del trattamento della separazione 
 * senza la presenza delle percentuali che potrebbero complicare la verifica della correttezza della
 * classe specifica del trattamento
 * @author giorgio
 */
@ManagedBean(name = "rimozioneazotoGUI")
@ViewScoped

public class RimozioneazotoGUI implements Serializable{
    
    
      private static final long serialVersionUID = 1L;
   
   
  /**
     * variabili tuili per la connesione al db
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    Parametridiprogetto classeparametri = null;
   /**
    * 
    */
   // private String tipobiomassa ="1";
    /**
     * parametri biomasse
     */
   // private double rapportobiomassaliquame = 0.1, rendimentocogeneratore = 0.4 ,  biogasemesso = 0.2 , biogasutilizzato =0.9, energiaautoconsumo =0.1, emissioninh3 = 0.02;
   // private int orefunzionamento = 7884 ;
   // private double produzionebiogas,metanonelbiogas,densitagas,prezzobiomassa,densitabiomassa;
    /**
     * caratteristiche biomassa
     */
   // private double st,sv,azoto,azotoammoniacale,potassio,fosforo;
   // private String tipobiomassa1 = "5" ;
    /**
     * parametri economici
     */
    //private double c_inv = 40027,e_inv = 0.6667,costogestione = 0.05,prezzoenergia = 0.24,durata_investimento = 15,tasso_interesse=0.05;
    
    
    //parametri di progetto
   
    
    /*private double investimento;
    private double anni;
    private double costo_energia;
    private double costo_calce;
    private double costo_acido_solforico;
    private double manutenzione;
    private double valore_solfato_ammonio;
    private double usodicalce;
    private double usodiacidosolforico;
    private double energia_elettrica;
    private double efficienza_rimozione_volume;
    //private double costo_energia_termica;
    private double tasso_interesse;
    private double efficienza_strippaggio;
    private double ricavo_solfato_ammonio;
    private double emissioni_ch4;
    private double emissioni_nh3;
    private double emissioni_co2;
    private double emissioni_n2;
    private double emissioni_n2o;*/
    
    private Double emissioni_co2;
    private Double preferisco_cedere_il_palabile;
    private Double preferisco_cedere_il_liquame;
    private Double utilizzo_il_palabile_su_terreni_terzi;
    private Double utilizzo_il_liquame_su_terreni_terzi;
    private Double costo_cessione_liquame;
    private Double costo_cessione_palabile;
    private Double costo_utilizzo_terreni_terzi_liquame;
    private Double costo_utilizzo_terreni_terzi_palabile;
    private Double costo_trasporto;
    private Double emissioni_nh3_liq;
    private Double emissioni_nh3_pal;
    private Double emissioni_no;
    private Double energia_consumata;
    private Double eff_media;
    private Double costo_n_min;
    private Double titolo_fertilizzante;
    private Double distanza_media_trasporto_min;
    private Double coeff_em_no;
    private Double coeff_em_n2o;
    private Double coeff_em_nh3;
    private Double coeff_em_co2;
    private Double energia_consumata_minerale;
    private Double costo_trasporto_minerale;
    private Double costo_distribuzione;
    private Double consumo_concimazione_min;
    private Double distanza_preferisco_cedere_il_palabile;
    private Double distanza_preferisco_cedere_il_liquame;
    private Double distanza_utilizzo_il_palabile_su_terreni_terzi;
    private Double distanza_utilizzo_il_liquame_su_terreni_terzi;
    private Double preferenza_rimozione;
    private int sceltaoperazione=118;
    private int distanza =10;
    
    //contiene le distanze delle preferenze dei parametri di progetto del modulo
    //rimozione azoto
    private Hashtable<Integer,Integer> distanzerimo = new Hashtable<Integer,Integer>();
    
   public RimozioneazotoGUI()
   {
       
      inizializzaParametri();
            
   }
   
   /**
    * salva le distanze delle preferenze cosi da usarle quando viene fatto il refresh nella pagina
    * ovvero quando l'utente cambia preferenza deve vedere la distanza corrispondente
    */
   private void salvadistanze()
   {
       //classeparametri.getParametrodiprogetto(0, 166, null, null, null).getIdNomeparametro().getId();
       //classeparametri.getParametrodiprogetto(0, 166, null, null, null).getValore()
       
       distanzerimo.put(new Integer(classeparametri.getParametrodiprogetto(0, 166, null, null, null).getIdNomeparametro().getId()),new Integer(Integer.parseInt(classeparametri.getParametrodiprogetto(0, 166, null, null, null).getValore())));
       distanzerimo.put(new Integer(classeparametri.getParametrodiprogetto(0, 167, null, null, null).getIdNomeparametro().getId()),new Integer(Integer.parseInt(classeparametri.getParametrodiprogetto(0, 167, null, null, null).getValore())));
       distanzerimo.put(new Integer(classeparametri.getParametrodiprogetto(0, 168, null, null, null).getIdNomeparametro().getId()),new Integer(Integer.parseInt(classeparametri.getParametrodiprogetto(0, 168, null, null, null).getValore())));
       distanzerimo.put(new Integer(classeparametri.getParametrodiprogetto(0, 169, null, null, null).getIdNomeparametro().getId()),new Integer(Integer.parseInt(classeparametri.getParametrodiprogetto(0, 169, null, null, null).getValore())));
       
 
 
   }
   
   /**
    * inizializza i parametri di progetto del trattamento vasca
    */
   private void inizializzaParametri()
   {
       /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        
        /**
         * recupero il session bean dettaGlioCuaa
         */
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        /**
         * recupero un particolare scenario in funzione del valore presente in dettagliocuaa
         */
       /*  Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
         db.ScenarioI sce = (db.ScenarioI)q1.getSingleResult();
         /**
          * recupero il trattamento relativo alla vasca
          */
      /*  Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",6 );
         db.TrattamentoS tra = (db.TrattamentoS)q2.getSingleResult();
       /**
        * cerco tutti i parametri di progetto di un particolare trattamento e scenario
        */
     /* Query q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p WHERE p.idScenario =?1 AND p.idTrattamento=?2 ");
      q.setParameter(1, sce);
      q.setParameter(2,tra);*/
         classeparametri = new Parametridiprogetto("renuwal2",14,dettCuaa.getIdscenario());
         List<db.ParametridiprogettoS> pa= classeparametri.getlistaparametri();
      //List<db.ParametridiprogettoS> pa= (List<db.ParametridiprogettoS>)q.getResultList();
         /**
          * recupero le informazioni sulla preferenza della cessione del surplus rappresentato dai due
          * menu a tendina in fondo alla pagina parametririmozioenazoto
          */
         db.ParametridiprogettoS tempPreferenza = classeparametri.getParametrodiprogetto(0, 170, null, null, null);
         String preferenza =  tempPreferenza.getValore();
         
         System.out.println(this.getClass().getCanonicalName() + " inizializza parametri preferenza " + preferenza);
         
         
         salvadistanze();
         
         switch(preferenza)
         {
              case "1":
                  //this.sceltaoperazione = 1;
                  this.setSceltaoperazione(118);
                  tempPreferenza = classeparametri.getParametrodiprogetto(0, 166, null, null, null);
                  this.distanza = Integer.parseInt(tempPreferenza.getValore());
                 break;
              case "2":
                  //this.sceltaoperazione = 2;
                  this.setSceltaoperazione(119);
                     System.out.println(this.getClass().getCanonicalName() + " inizializza parametri preferenza " + preferenza);
                  tempPreferenza = classeparametri.getParametrodiprogetto(0, 167, null, null, null);
                    this.distanza = Integer.parseInt(tempPreferenza.getValore());
                 break;
              case "3":
                  //this.sceltaoperazione = 3;
                  this.setSceltaoperazione(120);
                  tempPreferenza = classeparametri.getParametrodiprogetto(0, 168, null, null, null);
                    this.distanza = Integer.parseInt(tempPreferenza.getValore());
                 break;
              case "4":
                  //this.sceltaoperazione = 4;
                  this.setSceltaoperazione(121);
                  tempPreferenza = classeparametri.getParametrodiprogetto(0, 169, null, null, null);
                    this.distanza = Integer.parseInt(tempPreferenza.getValore());
                 break;     
                  
                 
         }
         
      ListIterator<db.ParametridiprogettoS> iterPa = pa.listIterator();
      
      
      
      
      /**
       * i parametri relativi ai coefficienti di di emissione(fnh3,fnh2,..) e quelli economici (a,b,vmin,vmax,..)sono gestiti dalle combo box
       * ad inizio pagina in vasca.xhtml.
       * Di seguito innvece aggiorno i valori dei parametri della vasca restanti quale gioni anno,giorni previsti , le emissioni di metano,..
       * Per una corrisposndenza tra gli id dei nomidiprametrodiprogetto(che corrispondono ai case dello switch seguente)
       * e le variabili corrispondenti in vascaGUI guarda la tabella nomeparametrdiprogetto
       */
     while(iterPa.hasNext())
     {
         db.ParametridiprogettoS temp = iterPa.next();
         
         switch(temp.getIdNomeparametro().getId())
         {
             case 94:

                 this.setEmissioni_co2(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 118:

                 this.setPreferisco_cedere_il_palabile(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 119:

                 this.setPreferisco_cedere_il_liquame(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 120:

                 this.setUtilizzo_il_palabile_su_terreni_terzi(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 121:

                 this.setUtilizzo_il_liquame_su_terreni_terzi(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 122:

                 this.setCosto_cessione_liquame(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 123:

                 this.setCosto_cessione_palabile(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 124:

                 this.setCosto_utilizzo_terreni_terzi_liquame(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 125:

                 this.setCosto_utilizzo_terreni_terzi_palabile(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 126:

                 this.setCosto_trasporto(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 127:

                 this.setEmissioni_nh3_liq(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 128:

                 this.setEmissioni_nh3_pal(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 129:

                 this.setEmissioni_no(new Double(Double.parseDouble(temp.getValore())));
                 break;
           case 130:

                 this.setEnergia_consumata(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 131:

                 this.setEff_media(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 132:

                 this.setCosto_n_min(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 133:

                 this.setTitolo_fertilizzante(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 134:

                 this.setDistanza_media_trasporto_min(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 135:

                 this.setCoeff_em_no(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 136:

                 this.setCoeff_em_n2o(new Double(Double.parseDouble(temp.getValore())));
                 break;   
                
              case 137:

                 this.setCoeff_em_nh3(new Double(Double.parseDouble(temp.getValore())));
                 break;       
               case 138:

                 this.setCoeff_em_co2(new Double(Double.parseDouble(temp.getValore())));
                 break;      
             case 139:

                 this.setEnergia_consumata_minerale(new Double(Double.parseDouble(temp.getValore())));
                 break;   
                 
                 
              case 140:

                 this.setCosto_trasporto_minerale(new Double(Double.parseDouble(temp.getValore())));
                 break;        
                  
              case 158:

                 this.setCosto_distribuzione(new Double(Double.parseDouble(temp.getValore())));
                 break;         
                  
             case 159:

                 this.setConsumo_concimazione_min(new Double(Double.parseDouble(temp.getValore())));
                 break;          
              case 166:

                 this.setDistanza_preferisco_cedere_il_palabile(new Double(Double.parseDouble(temp.getValore())));
                 break;       
               case 167:

                 this.setDistanza_preferisco_cedere_il_liquame(new Double(Double.parseDouble(temp.getValore())));
                 break;        
                   
                   
              case 168:

                 this.setDistanza_utilizzo_il_palabile_su_terreni_terzi(new Double(Double.parseDouble(temp.getValore())));
                 break;          
               case 169:

                 this.setDistanza_utilizzo_il_liquame_su_terreni_terzi(new Double(Double.parseDouble(temp.getValore())));
                 break;        
                  
               case 170:

                 this.setPreferenza_rimozione(new Double(Double.parseDouble(temp.getValore())));
               
                 break;        
         }
     }
      this.refreshPage();
   }
   
   /**
    * nel caso dei coefficienti biogas in cui il tipo di input puo cambiare 
    * e rappresenta il tipo di input mentre i nomi dei parametri di input rimangono uguali
    * ovvero produzione biogas,metano nel biogas,.. mi serve un numero che rappresenti il parametro che 
    * nel db è già òlegato al tipo di input ovvero c'è gia produzionebiogas_bovino
    * @param primo indicatore del tipo di input :1-bovino,2-suino,3-avicolo,4-altri,5,6,7,8
    * @param secondo indicatore del nome del parametro :1-produzione biogas,2-metano nel biogas,3-densita gas,4-prezzo biomassa,5-densita biomassa
    * @return 
    */
  /* private int mappaCodiceParametro(int primo,int secondo)
   {
       int ret = 0;
       
       if(primo)
       
       
       return ret;
   }*/
   
   public void aggiorna()
   {
       Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
       String action = params.get("action");
          
       db.ParametridiprogettoS temp = null;
         db.ParametridiprogettoS  temp2 = null;
         boolean anchetemp2 = false;
       
      /* int tt = Integer.parseInt(action);
       
       if(tt < 6 && tt > 0)
       {
           System.out.println("codice action " + tt + " tipobiomassa " + this.tipobiomassa);
           return ;
       
       }*/
       
       
        switch (action) {
           
            /**
             * il caso zero è quello della selezione della preferenza utente gestito con due menu a tendina
             * in cui la preferenza coincide con i parametri 118,119,120,121 del modulo rimozione azoto in funzione del parametro
             * scelto  viene impostata la distanza di uno dei parametri 166,167,168,169 che contiene la distanza di quella preferenza.
             * ricordati che sceltaoperazione contiene il numero del parametro di progetto ed in funzione di quel numero impopsto il parametro distanza
             * usando il secondo switch dentro il caso 0 cioè 118 coicide con 166 , 119 con 167 , 120 con 168 .....
             * Dato che negli altri campi andavo a salvare solo temp qui metto anche un boolean per inforamre dopo lo switch 
             * quando devo salvare il contenuto della variabile nel db 
             */
            case "0":
              
                System.out.println("distanza " + this.distanza + " preferenza " + this.sceltaoperazione);
                //recupero la variabile della preferenza
                db.ParametridiprogettoS  temp1 = classeparametri.getParametrodiprogetto(0, this.sceltaoperazione,null,null, null);
                //recuper preferenza_rimozione
                temp = classeparametri.getParametrodiprogetto(0, 170,null,null, null);
                //imposto i lvalore della preferenza
                 temp.setValore(String.valueOf(temp1.getValore()));
                 anchetemp2 =true;
                 switch(this.sceltaoperazione)
                 {
                     case 118:
                         System.out.println("118");
                         temp2 = classeparametri.getParametrodiprogetto(0, 166,null,null, null);
                         temp2.setValore(String.valueOf( this.distanza));
                         break;
                      case 119:
                           System.out.println("119");
                            temp2 = classeparametri.getParametrodiprogetto(0, 167,null,null, null);
                            temp2.setValore(String.valueOf( this.distanza));
                         break;
                      case 120:
                           System.out.println("120");
                            temp2 = classeparametri.getParametrodiprogetto(0, 168,null,null, null);
                            temp2.setValore(String.valueOf( this.distanza));
                         break;
                      case 121:
                           System.out.println("121");
                            temp2 = classeparametri.getParametrodiprogetto(0, 169,null,null, null);
                            temp2.setValore(String.valueOf( this.distanza));
                         break;     
                 }
                 
                break;
             case "94":
                temp = classeparametri.getParametrodiprogetto(0, 94,null,null, null);
                temp.setValore(String.valueOf(this.getEmissioni_co2().doubleValue()));
                break;        
              
             case "118":
                temp = classeparametri.getParametrodiprogetto(0, 118,null,null, null);
                temp.setValore(String.valueOf(this.getPreferisco_cedere_il_palabile().doubleValue()));
                break;        
            
             case "119":
                temp = classeparametri.getParametrodiprogetto(0, 119,null,null, null);
                temp.setValore(String.valueOf(this.getPreferisco_cedere_il_liquame().doubleValue()));
                break;         
             case "120":
                temp = classeparametri.getParametrodiprogetto(0, 120,null,null, null);
                temp.setValore(String.valueOf(this.getUtilizzo_il_palabile_su_terreni_terzi().doubleValue()));
                break;     
             case "121":
                temp = classeparametri.getParametrodiprogetto(0, 121,null,null, null);
                temp.setValore(String.valueOf(this.getUtilizzo_il_liquame_su_terreni_terzi().doubleValue()));
                break;     
             case "122":
                temp = classeparametri.getParametrodiprogetto(0, 122,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_cessione_liquame().doubleValue()));
                break;     
              
             case "123":
                temp = classeparametri.getParametrodiprogetto(0, 123,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_cessione_palabile().doubleValue()));
                break;             
             case "124":
                temp = classeparametri.getParametrodiprogetto(0, 124,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_utilizzo_terreni_terzi_liquame().doubleValue()));
                break; 
             case "125":
                temp = classeparametri.getParametrodiprogetto(0,125, null, null, null);
                temp.setValore(String.valueOf(this.getCosto_utilizzo_terreni_terzi_palabile().doubleValue()));
                break;     
             case "126":
                temp = classeparametri.getParametrodiprogetto(0, 126, null, null, null);
                temp.setValore(String.valueOf(this.getCosto_trasporto().doubleValue()));
                break;     
               
             case "127":
                temp = classeparametri.getParametrodiprogetto(0, 127,null,null, null);
                temp.setValore(String.valueOf(this.getEmissioni_nh3_liq().doubleValue()));
                break;        
             case "128":
                temp = classeparametri.getParametrodiprogetto(0, 128, null, null, null);
                temp.setValore(String.valueOf(this.getEmissioni_nh3_pal().doubleValue()));
                break;     
             case "129":
                temp = classeparametri.getParametrodiprogetto(0, 129, null, null, null);
                temp.setValore(String.valueOf(this.getEmissioni_no().doubleValue()));
                break;     
              
             case "130":
                temp = classeparametri.getParametrodiprogetto(0, 130,null,null, null);
                temp.setValore(String.valueOf(this.getEnergia_consumata().doubleValue()));
                break;        
              
             case "131":
                temp = classeparametri.getParametrodiprogetto(0, 131,null,null, null);
                temp.setValore(String.valueOf(this.getEff_media().doubleValue()));
                break;       
                 
              case "132":
                temp = classeparametri.getParametrodiprogetto(0, 132,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_n_min().doubleValue()));
                break;           
                  
              case "133":
                temp = classeparametri.getParametrodiprogetto(0, 133,null,null, null);
                temp.setValore(String.valueOf(this.getTitolo_fertilizzante().doubleValue()));
                break;       
              
              case "134":
                temp = classeparametri.getParametrodiprogetto(0, 134,null,null, null);
                temp.setValore(String.valueOf(this.getDistanza_media_trasporto_min().doubleValue()));
                break;
              
            case "135":
                temp = classeparametri.getParametrodiprogetto(0, 135,null,null, null);
                temp.setValore(String.valueOf(this.getCoeff_em_no().doubleValue()));
                break;          
                
                
                   
            case "136":
                temp = classeparametri.getParametrodiprogetto(0, 136,null,null, null);
                temp.setValore(String.valueOf(this.getCoeff_em_n2o().doubleValue()));
                break;          
                
                
                   
            case "137":
                temp = classeparametri.getParametrodiprogetto(0, 137,null,null, null);
                temp.setValore(String.valueOf(this.getCoeff_em_nh3().doubleValue()));
                break;          
                
                
                
                   
            case "138":
                temp = classeparametri.getParametrodiprogetto(0, 138,null,null, null);
                temp.setValore(String.valueOf(this.getCoeff_em_co2().doubleValue()));
                break;          
                
                
                
                   
            case "139":
                temp = classeparametri.getParametrodiprogetto(0, 139,null,null, null);
                temp.setValore(String.valueOf(this.getEnergia_consumata_minerale().doubleValue()));
                break;          
                
                
                
                   
            case "140":
                temp = classeparametri.getParametrodiprogetto(0, 140,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_trasporto_minerale().doubleValue()));
                break;          
                
                
                
                   
            case "158":
                temp = classeparametri.getParametrodiprogetto(0, 158,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_distribuzione().doubleValue()));
                break;          
                
                
                   
            case "159":
                temp = classeparametri.getParametrodiprogetto(0, 159,null,null, null);
                temp.setValore(String.valueOf(this.getConsumo_concimazione_min().doubleValue()));
                break;          
                
                
                   
            case "166":
                temp = classeparametri.getParametrodiprogetto(0, 166,null,null, null);
                temp.setValore(String.valueOf(this.getDistanza_preferisco_cedere_il_palabile().doubleValue()));
                break;          
                
                
                   
            case "167":
                temp = classeparametri.getParametrodiprogetto(0, 167,null,null, null);
                temp.setValore(String.valueOf(this.getDistanza_preferisco_cedere_il_liquame().doubleValue()));
                break;          
                
                
                
                   
            case "168":
                temp = classeparametri.getParametrodiprogetto(0, 168,null,null, null);
                temp.setValore(String.valueOf(this.getDistanza_utilizzo_il_palabile_su_terreni_terzi().doubleValue()));
                break;  
                
              
            case "169":
                temp = classeparametri.getParametrodiprogetto(0, 169,null,null, null);
                temp.setValore(String.valueOf(this.getDistanza_utilizzo_il_liquame_su_terreni_terzi().doubleValue()));
                break;          
                
             
            case "170":
                temp = classeparametri.getParametrodiprogetto(0, 170,null,null, null);
                temp.setValore(String.valueOf(this.getPreferenza_rimozione().doubleValue()));
                break;                
                
                
                
                
                
                 
    }
    
      if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
    
    
         entityManager.getTransaction().begin();
         entityManager.merge(temp);
         
         //relativo al caso 0 nello switch
         if(anchetemp2)
                 entityManager.merge(temp2);
         entityManager.flush();
         entityManager.getTransaction().commit();
         //System.out.println("action " + action + " this.coefficientemaccchina " + this.coefficientemaccchina + " this.coefficientetipomacchina " + this.coefficientetipomacchina + " valore " + temp.getValore() + " idnomeparametro  " + temp.getIdNomeparametro() + " trattamento  " + temp.getIdTrattamento());
    
    
         Connessione.getInstance().chiudi();
    
    System.out.println("-------fine aggiorna ");
       
   }
   
   
   


    /**
     * effettua un refresh della pagina
     */
      private void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

//    /**
//     * @return the tipobiomassa
//     */
//    public String getTipobiomassa() {
//        return tipobiomassa;
//    }
//
//    /**
//     * @param tipobiomassa the tipobiomassa to set
//     */
//    public void setTipobiomassa(String tipobiomassa) {
//        this.tipobiomassa = tipobiomassa;
//    }
//
//    /**
//     * @return the rapportobiomassaliquame
//     */
//    public double getRapportobiomassaliquame() {
//        return rapportobiomassaliquame;
//    }
//
//    /**
//     * @param rapportobiomassaliquame the rapportobiomassaliquame to set
//     */
//    public void setRapportobiomassaliquame(double rapportobiomassaliquame) {
//        this.rapportobiomassaliquame = rapportobiomassaliquame;
//    }
//
//    /**
//     * @return the rendimentocogeneratore
//     */
//    public double getRendimentocogeneratore() {
//        return rendimentocogeneratore;
//    }
//
//    /**
//     * @param rendimentocogeneratore the rendimentocogeneratore to set
//     */
//    public void setRendimentocogeneratore(double rendimentocogeneratore) {
//        this.rendimentocogeneratore = rendimentocogeneratore;
//    }
//
//    /**
//     * @return the biogasemesso
//     */
//    public double getBiogasemesso() {
//        return biogasemesso;
//    }
//
//    /**
//     * @param biogasemesso the biogasemesso to set
//     */
//    public void setBiogasemesso(double biogasemesso) {
//        this.biogasemesso = biogasemesso;
//    }
//
//    /**
//     * @return the biogasutilizzato
//     */
//    public double getBiogasutilizzato() {
//        return biogasutilizzato;
//    }
//
//    /**
//     * @param biogasutilizzato the biogasutilizzato to set
//     */
//    public void setBiogasutilizzato(double biogasutilizzato) {
//        this.biogasutilizzato = biogasutilizzato;
//    }
//
//    /**
//     * @return the energiaautoconsumo
//     */
//    public double getEnergiaautoconsumo() {
//        return energiaautoconsumo;
//    }
//
//    /**
//     * @param energiaautoconsumo the energiaautoconsumo to set
//     */
//    public void setEnergiaautoconsumo(double energiaautoconsumo) {
//        this.energiaautoconsumo = energiaautoconsumo;
//    }
//
//    /**
//     * @return the emissioninh3
//     */
//    public double getEmissioninh3() {
//        return emissioninh3;
//    }
//
//    /**
//     * @param emissioninh3 the emissioninh3 to set
//     */
//    public void setEmissioninh3(double emissioninh3) {
//        this.emissioninh3 = emissioninh3;
//    }
//
//    /**
//     * @return the orefunzionamento
//     */
//    public int getOrefunzionamento() {
//        return orefunzionamento;
//    }
//
//    /**
//     * @param orefunzionamento the orefunzionamento to set
//     */
//    public void setOrefunzionamento(int orefunzionamento) {
//        this.orefunzionamento = orefunzionamento;
//    }
//
//    /**
//     * @return the produzionebiogas
//     */
//    public double getProduzionebiogas() {
//        
//       // System.out.println("get produzione biogas ");
//       db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 74,this.tipobiomassa,null, null);
//                this.produzionebiogas = Double.parseDouble(temp.getValore());
//        return produzionebiogas;
//    }
//
//    /**
//     * @param produzionebiogas the produzionebiogas to set
//     */
//    public void setProduzionebiogas(double produzionebiogas) {
//        //System.out.println("set produzione biogas ");
//        this.produzionebiogas = produzionebiogas;
//    }
//
//    /**
//     * @return the metanonelbiogas
//     */
//    public double getMetanonelbiogas() {
//        
//        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 69,this.tipobiomassa,null, null);
//        this.metanonelbiogas = Double.parseDouble(temp.getValore());
//        return metanonelbiogas;
//    }
//
//    /**
//     * @param metanonelbiogas the metanonelbiogas to set
//     */
//    public void setMetanonelbiogas(double metanonelbiogas) {
//        this.metanonelbiogas = metanonelbiogas;
//    }
//
//    /**
//     * @return the densitagas
//     */
//    public double getDensitagas() {
//        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 73,this.tipobiomassa,null, null);
//        this.densitagas = Double.parseDouble(temp.getValore());
//        return densitagas;
//    }
//
//    /**
//     * @param densitagas the densitagas to set
//     */
//    public void setDensitagas(double densitagas) {
//        this.densitagas = densitagas;
//    }
//
//    /**
//     * @return the prezzobiomassa
//     */
//    public double getPrezzobiomassa() {
//        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 75,this.tipobiomassa,null, null);
//        this.prezzobiomassa = Double.parseDouble(temp.getValore());
//        return prezzobiomassa;
//    }
//
//    /**
//     * @param prezzobiomassa the prezzobiomassa to set
//     */
//    public void setPrezzobiomassa(double prezzobiomassa) {
//        this.prezzobiomassa = prezzobiomassa;
//    }
//
//    /**
//     * @return the densitabiomassa
//     */
//    public double getDensitabiomassa() {
//        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 99,this.tipobiomassa,null, null);
//        this.densitabiomassa = Double.parseDouble(temp.getValore());
//        return densitabiomassa;
//    }
//
//    /**
//     * @param densitabiomassa the densitabiomassa to set
//     */
//    public void setDensitabiomassa(double densitabiomassa) {
//        this.densitabiomassa = densitabiomassa;
//    }
//
//    /**
//     * @return the st
//     */
//    public double getSt() {
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 43,this.tipobiomassa1,null, null);
//                this.st = Double.parseDouble(temp.getValore());
//        return st;
//    }
//
//    /**
//     * @param st the st to set
//     */
//    public void setSt(double st) {
//        this.st = st;
//    }
//
//    /**
//     * @return the sv
//     */
//    public double getSv() {
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 44,this.tipobiomassa1,null, null);
//         this.sv = Double.parseDouble(temp.getValore());
//         
//        return sv;
//    }
//
//    /**
//     * @param sv the sv to set
//     */
//    public void setSv(double sv) {
//        this.sv = sv;
//    }
//
//    /**
//     * @return the azoto
//     */
//    public double getAzoto() {
//        
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 45,this.tipobiomassa1,null, null);
//        // System.out.println(this.getClass().getCanonicalName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " azoto " + temp.getValore() + " tipo biomassa " + this.tipobiomassa1);
//                this.azoto = Double.parseDouble(temp.getValore());
//        
//        return azoto;
//    }
//
//    /**
//     * @param azoto the azoto to set
//     */
//    public void setAzoto(double azoto) {
//        this.azoto = azoto;
//    }
//
//    /**
//     * @return the azotoammoniacale
//     */
//    public double getAzotoammoniacale() {
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 46,this.tipobiomassa1,null, null);
//                this.azotoammoniacale = Double.parseDouble(temp.getValore());
//        return azotoammoniacale;
//    }
//
//    /**
//     * @param azotoammoniacale the azotoammoniacale to set
//     */
//    public void setAzotoammoniacale(double azotoammoniacale) {
//        this.azotoammoniacale = azotoammoniacale;
//    }
//
//   
//
//    /**
//     * @return the tipobiomassa1
//     */
//    public String getTipobiomassa1() {
//        return tipobiomassa1;
//    }
//
//    /**
//     * @param tipobiomassa1 the tipobiomassa1 to set
//     */
//    public void setTipobiomassa1(String tipobiomassa1) {
//        this.tipobiomassa1 = tipobiomassa1;
//    }
//
//    /**
//     * @return the potassio
//     */
//    public double getPotassio() {
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 47,this.tipobiomassa1,null, null);
//                this.potassio = Double.parseDouble(temp.getValore());
//        return potassio;
//    }
//
//    /**
//     * @param potassio the potassio to set
//     */
//    public void setPotassio(double potassio) {
//        this.potassio = potassio;
//    }
//
//    /**
//     * @return the fosforo
//     */
//    public double getFosforo() {
//         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 48,this.tipobiomassa1,null, null);
//                this.fosforo = Double.parseDouble(temp.getValore());
//        return fosforo;
//    }
//
//    /**
//     * @param fosforo the fosforo to set
//     */
//    public void setFosforo(double fosforo) {
//        this.fosforo = fosforo;
//    }
//
//    /**
//     * @return the c_inv
//     */
//    public double getC_inv() {
//        return c_inv;
//    }
//
//    /**
//     * @param c_inv the c_inv to set
//     */
//    public void setC_inv(double c_inv) {
//        this.c_inv = c_inv;
//    }
//
//    /**
//     * @return the e_inv
//     */
//    public double getE_inv() {
//        return e_inv;
//    }
//
//    /**
//     * @param e_inv the e_inv to set
//     */
//    public void setE_inv(double e_inv) {
//        this.e_inv = e_inv;
//    }
//
//    /**
//     * @return the costogestione
//     */
//    public double getCostogestione() {
//        return costogestione;
//    }
//
//    /**
//     * @param costogestione the costogestione to set
//     */
//    public void setCostogestione(double costogestione) {
//        this.costogestione = costogestione;
//    }
//
//    /**
//     * @return the prezzoenergia
//     */
//    public double getPrezzoenergia() {
//        return prezzoenergia;
//    }
//
//    /**
//     * @param prezzoenergia the prezzoenergia to set
//     */
//    public void setPrezzoenergia(double prezzoenergia) {
//        this.prezzoenergia = prezzoenergia;
//    }
//
//    /**
//     * @return the durata_investimento
//     */
//    public double getDurata_investimento() {
//        return durata_investimento;
//    }
//
//    /**
//     * @param durata_investimento the durata_investimento to set
//     */
//    public void setDurata_investimento(double durata_investimento) {
//        this.durata_investimento = durata_investimento;
//    }
//
//    /**
//     * @return the tasso_interesse
//     */
//    public double getTasso_interesse() {
//        return tasso_interesse;
//    }
//
//    /**
//     * @param tasso_interesse the tasso_interesse to set
//     */
//    public void setTasso_interesse(double tasso_interesse) {
//        this.tasso_interesse = tasso_interesse;
//    }
//    

//    /**
//     * @return the eff_sep_st
//     */
//    public double getEff_sep_st() {
//        return eff_sep_st;
//    }
//
//    /**
//     * @param eff_sep_st the eff_sep_st to set
//     */
//    public void setEff_sep_st(double eff_sep_st) {
//        this.eff_sep_st = eff_sep_st;
//    }
//
//    /**
//     * @return the eff_sep_vol
//     */
//    public double getEff_sep_vol() {
//        return eff_sep_vol;
//    }
//
//    /**
//     * @param eff_sep_vol the eff_sep_vol to set
//     */
//    public void setEff_sep_vol(double eff_sep_vol) {
//        this.eff_sep_vol = eff_sep_vol;
//    }
//
//    /**
//     * @return the eff_sep_tkn
//     */
//    public double getEff_sep_tkn() {
//        return eff_sep_tkn;
//    }
//
//    /**
//     * @param eff_sep_tkn the eff_sep_tkn to set
//     */
//    public void setEff_sep_tkn(double eff_sep_tkn) {
//        this.eff_sep_tkn = eff_sep_tkn;
//    }
//
//    /**
//     * @return the eff_sep_tan
//     */
//    public double getEff_sep_tan() {
//        return eff_sep_tan;
//    }
//
//    /**
//     * @param eff_sep_tan the eff_sep_tan to set
//     */
//    public void setEff_sep_tan(double eff_sep_tan) {
//        this.eff_sep_tan = eff_sep_tan;
//    }
//
//    /**
//     * @return the eff_sep_sv
//     */
//    public double getEff_sep_sv() {
//        return eff_sep_sv;
//    }
//
//    /**
//     * @param eff_sep_sv the eff_sep_sv to set
//     */
//    public void setEff_sep_sv(double eff_sep_sv) {
//        this.eff_sep_sv = eff_sep_sv;
//    }
//
//    /**
//     * @return the eff_sep_p
//     */
//    public double getEff_sep_p() {
//        return eff_sep_p;
//    }
//
//    /**
//     * @param eff_sep_p the eff_sep_p to set
//     */
//    public void setEff_sep_p(double eff_sep_p) {
//        this.eff_sep_p = eff_sep_p;
//    }
//
//    /**
//     * @return the eff_sep_k
//     */
//    public double getEff_sep_k() {
//        return eff_sep_k;
//    }
//
//    /**
//     * @param eff_sep_k the eff_sep_k to set
//     */
//    public void setEff_sep_k(double eff_sep_k) {
//        this.eff_sep_k = eff_sep_k;
//    }
//
//    /**
//     * @return the tasso_nitrificazione
//     */
//    public double getTasso_nitrificazione() {
//        return tasso_nitrificazione;
//    }
//
//    /**
//     * @param tasso_nitrificazione the tasso_nitrificazione to set
//     */
//    public void setTasso_nitrificazione(double tasso_nitrificazione) {
//        this.tasso_nitrificazione = tasso_nitrificazione;
//    }
//
//    /**
//     * @return the tasso_denitrificazione
//     */
//    public double getTasso_denitrificazione() {
//        return tasso_denitrificazione;
//    }
//
//    /**
//     * @param tasso_denitrificazione the tasso_denitrificazione to set
//     */
//    public void setTasso_denitrificazione(double tasso_denitrificazione) {
//        this.tasso_denitrificazione = tasso_denitrificazione;
//    }
//
//    /**
//     * @return the n_max_uscita
//     */
//    public double getN_max_uscita() {
//        return n_max_uscita;
//    }
//
//    /**
//     * @param n_max_uscita the n_max_uscita to set
//     */
//    public void setN_max_uscita(double n_max_uscita) {
//        this.n_max_uscita = n_max_uscita;
//    }
//
//    /**
//     * @return the rendimento_min
//     */
//    public double getRendimento_min() {
//        return rendimento_min;
//    }
//
//    /**
//     * @param rendimento_min the rendimento_min to set
//     */
//    public void setRendimento_min(double rendimento_min) {
//        this.rendimento_min = rendimento_min;
//    }
//
//    /**
//     * @return the rendimento_max
//     */
//    public double getRendimento_max() {
//        return rendimento_max;
//    }
//
//    /**
//     * @param rendimento_max the rendimento_max to set
//     */
//    public void setRendimento_max(double rendimento_max) {
//        this.rendimento_max = rendimento_max;
//    }
//
//    /**
//     * @return the tempo_attivo_totale
//     */
//    public double getTempo_attivo_totale() {
//        return tempo_attivo_totale;
//    }
//
//    /**
//     * @param tempo_attivo_totale the tempo_attivo_totale to set
//     */
//    public void setTempo_attivo_totale(double tempo_attivo_totale) {
//        this.tempo_attivo_totale = tempo_attivo_totale;
//    }

//    /**
//     * @return the emissioni_nh3
//     */
//    public double getEmissioni_nh3() {
//        return emissioni_nh3;
//    }
//
//    /**
//     * @param emissioni_nh3 the emissioni_nh3 to set
//     */
//    public void setEmissioni_nh3(double emissioni_nh3) {
//        this.emissioni_nh3 = emissioni_nh3;
//    }
//
//    /**
//     * @return the emissioni_n2o
//     */
//    public double getEmissioni_n2o() {
//        return emissioni_n2o;
//    }
//
//    /**
//     * @param emissioni_n2o the emissioni_n2o to set
//     */
//    public void setEmissioni_n2o(double emissioni_n2o) {
//        this.emissioni_n2o = emissioni_n2o;
//    }
//
//    /**
//     * @return the emissioni_ch4
//     */
//    public double getEmissioni_ch4() {
//        return emissioni_ch4;
//    }
//
//    /**
//     * @param emissioni_ch4 the emissioni_ch4 to set
//     */
//    public void setEmissioni_ch4(double emissioni_ch4) {
//        this.emissioni_ch4 = emissioni_ch4;
//    }

    /**
     * @return the emissioni_co2
     */
    public Double getEmissioni_co2() {
        return emissioni_co2;
    }

    /**
     * @param emissioni_co2 the emissioni_co2 to set
     */
    public void setEmissioni_co2(Double emissioni_co2) {
        if(emissioni_co2 != null) {
            this.emissioni_co2 = emissioni_co2;
        }
    }

    /**
     * @return the preferisco_cedere_il_palabile
     */
    public Double getPreferisco_cedere_il_palabile() {
        return preferisco_cedere_il_palabile;
    }

    /**
     * @param preferisco_cedere_il_palabile the preferisco_cedere_il_palabile to set
     */
    public void setPreferisco_cedere_il_palabile(Double preferisco_cedere_il_palabile) {
        if(preferisco_cedere_il_palabile != null) {
            this.preferisco_cedere_il_palabile = preferisco_cedere_il_palabile;
        }
    }

    /**
     * @return the preferisco_cedere_il_liquame
     */
    public Double getPreferisco_cedere_il_liquame() {
        return preferisco_cedere_il_liquame;
    }

    /**
     * @param preferisco_cedere_il_liquame the preferisco_cedere_il_liquame to set
     */
    public void setPreferisco_cedere_il_liquame(Double preferisco_cedere_il_liquame) {
        if(preferisco_cedere_il_liquame != null) {
            this.preferisco_cedere_il_liquame = preferisco_cedere_il_liquame;
        }
    }

    /**
     * @return the utilizzo_il_palabile_su_terreni_terzi
     */
    public Double getUtilizzo_il_palabile_su_terreni_terzi() {
        return utilizzo_il_palabile_su_terreni_terzi;
    }

    /**
     * @param utilizzo_il_palabile_su_terreni_terzi the utilizzo_il_palabile_su_terreni_terzi to set
     */
    public void setUtilizzo_il_palabile_su_terreni_terzi(Double utilizzo_il_palabile_su_terreni_terzi) {
        if(utilizzo_il_palabile_su_terreni_terzi != null) {
            this.utilizzo_il_palabile_su_terreni_terzi = utilizzo_il_palabile_su_terreni_terzi;
        }
    }

    /**
     * @return the utilizzo_il_liquame_su_terreni_terzi
     */
    public Double getUtilizzo_il_liquame_su_terreni_terzi() {
        return utilizzo_il_liquame_su_terreni_terzi;
    }

    /**
     * @param utilizzo_il_liquame_su_terreni_terzi the utilizzo_il_liquame_su_terreni_terzi to set
     */
    public void setUtilizzo_il_liquame_su_terreni_terzi(Double utilizzo_il_liquame_su_terreni_terzi) {
        if(utilizzo_il_liquame_su_terreni_terzi != null) {
            this.utilizzo_il_liquame_su_terreni_terzi = utilizzo_il_liquame_su_terreni_terzi;
        }
    }

    /**
     * @return the costo_cessione_liquame
     */
    public Double getCosto_cessione_liquame() {
        return costo_cessione_liquame;
    }

    /**
     * @param costo_cessione_liquame the costo_cessione_liquame to set
     */
    public void setCosto_cessione_liquame(Double costo_cessione_liquame) {
        if(costo_cessione_liquame != null) {
            this.costo_cessione_liquame = costo_cessione_liquame;
        }
    }

    /**
     * @return the costo_cessione_palabile
     */
    public Double getCosto_cessione_palabile() {
        return costo_cessione_palabile;
    }

    /**
     * @param costo_cessione_palabile the costo_cessione_palabile to set
     */
    public void setCosto_cessione_palabile(Double costo_cessione_palabile) {
        if(costo_cessione_palabile !=null) {
            this.costo_cessione_palabile = costo_cessione_palabile;
        }
    }

    /**
     * @return the costo_utilizzo_terreni_terzi_liquame
     */
    public Double getCosto_utilizzo_terreni_terzi_liquame() {
        return costo_utilizzo_terreni_terzi_liquame;
    }

    /**
     * @param costo_utilizzo_terreni_terzi_liquame the costo_utilizzo_terreni_terzi_liquame to set
     */
    public void setCosto_utilizzo_terreni_terzi_liquame(Double costo_utilizzo_terreni_terzi_liquame) {
        if(costo_utilizzo_terreni_terzi_liquame != null) {
            this.costo_utilizzo_terreni_terzi_liquame = costo_utilizzo_terreni_terzi_liquame;
        }
    }

    /**
     * @return the costo_utilizzo_terreni_terzi_palabile
     */
    public Double getCosto_utilizzo_terreni_terzi_palabile() {
        return costo_utilizzo_terreni_terzi_palabile;
    }

    /**
     * @param costo_utilizzo_terreni_terzi_palabile the costo_utilizzo_terreni_terzi_palabile to set
     */
    public void setCosto_utilizzo_terreni_terzi_palabile(Double costo_utilizzo_terreni_terzi_palabile) {
        if(costo_utilizzo_terreni_terzi_palabile != null) {
            this.costo_utilizzo_terreni_terzi_palabile = costo_utilizzo_terreni_terzi_palabile;
        }
    }

    /**
     * @return the costo_trasporto
     */
    public Double getCosto_trasporto() {
        return costo_trasporto;
    }

    /**
     * @param costo_trasporto the costo_trasporto to set
     */
    public void setCosto_trasporto(Double costo_trasporto) {
        if(costo_trasporto != null) {
            this.costo_trasporto = costo_trasporto;
        }
    }

    /**
     * @return the emissioni_nh3_liq
     */
    public Double getEmissioni_nh3_liq() {
        return emissioni_nh3_liq;
    }

    /**
     * @param emissioni_nh3_liq the emissioni_nh3_liq to set
     */
    public void setEmissioni_nh3_liq(Double emissioni_nh3_liq) {
        if(emissioni_nh3_liq != null) {
            this.emissioni_nh3_liq = emissioni_nh3_liq;
        }
    }

    /**
     * @return the emissioni_nh3_pal
     */
    public Double getEmissioni_nh3_pal() {
        return emissioni_nh3_pal;
    }

    /**
     * @param emissioni_nh3_pal the emissioni_nh3_pal to set
     */
    public void setEmissioni_nh3_pal(Double emissioni_nh3_pal) {
        if(emissioni_nh3_pal != null) {
            this.emissioni_nh3_pal = emissioni_nh3_pal;
        }
    }

    /**
     * @return the emissioni_no
     */
    public Double getEmissioni_no() {
        return emissioni_no;
    }

    /**
     * @param emissioni_no the emissioni_no to set
     */
    public void setEmissioni_no(Double emissioni_no) {
        if(emissioni_no != null) {
            this.emissioni_no = emissioni_no;
        }
    }

    /**
     * @return the energia_consumata
     */
    public Double getEnergia_consumata() {
        return energia_consumata;
    }

    /**
     * @param energia_consumata the energia_consumata to set
     */
    public void setEnergia_consumata(Double energia_consumata) {
        if(energia_consumata != null) {
            this.energia_consumata = energia_consumata;
        }
    }

    /**
     * @return the eff_media
     */
    public Double getEff_media() {
        return eff_media;
    }

    /**
     * @param eff_media the eff_media to set
     */
    public void setEff_media(Double eff_media) {
        if(eff_media != null) {
            this.eff_media = eff_media;
        }
    }

    /**
     * @return the costo_n_min
     */
    public Double getCosto_n_min() {
        return costo_n_min;
    }

    /**
     * @param costo_n_min the costo_n_min to set
     */
    public void setCosto_n_min(Double costo_n_min) {
        if(costo_n_min != null) {
            this.costo_n_min = costo_n_min;
        }
    }

    /**
     * @return the titolo_fertilizzante
     */
    public Double getTitolo_fertilizzante() {
        return titolo_fertilizzante;
    }

    /**
     * @param titolo_fertilizzante the titolo_fertilizzante to set
     */
    public void setTitolo_fertilizzante(Double titolo_fertilizzante) {
        if(titolo_fertilizzante != null) {
            this.titolo_fertilizzante = titolo_fertilizzante;
        }
    }

    /**
     * @return the distanza_media_trasporto_min
     */
    public Double getDistanza_media_trasporto_min() {
        return distanza_media_trasporto_min;
    }

    /**
     * @param distanza_media_trasporto_min the distanza_media_trasporto_min to set
     */
    public void setDistanza_media_trasporto_min(Double distanza_media_trasporto_min) {
        if(distanza_media_trasporto_min !=null) {
            this.distanza_media_trasporto_min = distanza_media_trasporto_min;
        }
    }

    /**
     * @return the coeff_em_no
     */
    public Double getCoeff_em_no() {
        return coeff_em_no;
    }

    /**
     * @param coeff_em_no the coeff_em_no to set
     */
    public void setCoeff_em_no(Double coeff_em_no) {
        if(coeff_em_no != null) {
            this.coeff_em_no = coeff_em_no;
        }
    }

    /**
     * @return the coeff_em_n2o
     */
    public Double getCoeff_em_n2o() {
        return coeff_em_n2o;
    }

    /**
     * @param coeff_em_n2o the coeff_em_n2o to set
     */
    public void setCoeff_em_n2o(Double coeff_em_n2o) {
        if(coeff_em_n2o != null) {
            this.coeff_em_n2o = coeff_em_n2o;
        }
    }

    /**
     * @return the coeff_em_nh3
     */
    public Double getCoeff_em_nh3() {
        return coeff_em_nh3;
    }

    /**
     * @param coeff_em_nh3 the coeff_em_nh3 to set
     */
    public void setCoeff_em_nh3(Double coeff_em_nh3) {
        if(coeff_em_nh3 != null) {
            this.coeff_em_nh3 = coeff_em_nh3;
        }
    }

    /**
     * @return the coeff_em_co2
     */
    public Double getCoeff_em_co2() {
        return coeff_em_co2;
    }

    /**
     * @param coeff_em_co2 the coeff_em_co2 to set
     */
    public void setCoeff_em_co2(Double coeff_em_co2) {
        if(coeff_em_co2 != null) {
            this.coeff_em_co2 = coeff_em_co2;
        }
    }

    /**
     * @return the energia_consumata_minerale
     */
    public Double getEnergia_consumata_minerale() {
        return energia_consumata_minerale;
    }

    /**
     * @param energia_consumata_minerale the energia_consumata_minerale to set
     */
    public void setEnergia_consumata_minerale(Double energia_consumata_minerale) {
        if(energia_consumata_minerale != null) {
            this.energia_consumata_minerale = energia_consumata_minerale;
        }
    }

    /**
     * @return the costo_trasporto_minerale
     */
    public Double getCosto_trasporto_minerale() {
        return costo_trasporto_minerale;
    }

    /**
     * @param costo_trasporto_minerale the costo_trasporto_minerale to set
     */
    public void setCosto_trasporto_minerale(Double costo_trasporto_minerale) {
        if(costo_trasporto_minerale != null) {
            this.costo_trasporto_minerale = costo_trasporto_minerale;
        }
    }

    /**
     * @return the costo_distribuzione
     */
    public Double getCosto_distribuzione() {
        return costo_distribuzione;
    }

    /**
     * @param costo_distribuzione the costo_distribuzione to set
     */
    public void setCosto_distribuzione(Double costo_distribuzione) {
        if(costo_distribuzione != null) {
            this.costo_distribuzione = costo_distribuzione;
        }
    }

    /**
     * @return the consumo_concimazione_min
     */
    public Double getConsumo_concimazione_min() {
        return consumo_concimazione_min;
    }

    /**
     * @param consumo_concimazione_min the consumo_concimazione_min to set
     */
    public void setConsumo_concimazione_min(Double consumo_concimazione_min) {
        if(consumo_concimazione_min != null) {
            this.consumo_concimazione_min = consumo_concimazione_min;
        }
    }

    /**
     * @return the distanza_preferisco_cedere_il_palabile
     */
    public Double getDistanza_preferisco_cedere_il_palabile() {
        return distanza_preferisco_cedere_il_palabile;
    }

    /**
     * @param distanza_preferisco_cedere_il_palabile the distanza_preferisco_cedere_il_palabile to set
     */
    public void setDistanza_preferisco_cedere_il_palabile(Double distanza_preferisco_cedere_il_palabile) {
        if(distanza_preferisco_cedere_il_palabile != null) {
            this.distanza_preferisco_cedere_il_palabile = distanza_preferisco_cedere_il_palabile;
        }
    }

    /**
     * @return the distanza_preferisco_cedere_il_liquame
     */
    public Double getDistanza_preferisco_cedere_il_liquame() {
        return distanza_preferisco_cedere_il_liquame;
    }

    /**
     * @param distanza_preferisco_cedere_il_liquame the distanza_preferisco_cedere_il_liquame to set
     */
    public void setDistanza_preferisco_cedere_il_liquame(Double distanza_preferisco_cedere_il_liquame) {
        if(distanza_preferisco_cedere_il_liquame != null) {
            this.distanza_preferisco_cedere_il_liquame = distanza_preferisco_cedere_il_liquame;
        }
    }

    /**
     * @return the distanza_utilizzo_il_palabile_su_terreni_terzi
     */
    public Double getDistanza_utilizzo_il_palabile_su_terreni_terzi() {
        return distanza_utilizzo_il_palabile_su_terreni_terzi;
    }

    /**
     * @param distanza_utilizzo_il_palabile_su_terreni_terzi the distanza_utilizzo_il_palabile_su_terreni_terzi to set
     */
    public void setDistanza_utilizzo_il_palabile_su_terreni_terzi(Double distanza_utilizzo_il_palabile_su_terreni_terzi) {
        if(distanza_utilizzo_il_palabile_su_terreni_terzi != null) {
            this.distanza_utilizzo_il_palabile_su_terreni_terzi = distanza_utilizzo_il_palabile_su_terreni_terzi;
        }
    }

    /**
     * @return the distanza_utilizzo_il_liquame_su_terreni_terzi
     */
    public Double getDistanza_utilizzo_il_liquame_su_terreni_terzi() {
        return distanza_utilizzo_il_liquame_su_terreni_terzi;
    }

    /**
     * @param distanza_utilizzo_il_liquame_su_terreni_terzi the distanza_utilizzo_il_liquame_su_terreni_terzi to set
     */
    public void setDistanza_utilizzo_il_liquame_su_terreni_terzi(Double distanza_utilizzo_il_liquame_su_terreni_terzi) {
        if(distanza_utilizzo_il_liquame_su_terreni_terzi != null) {
            this.distanza_utilizzo_il_liquame_su_terreni_terzi = distanza_utilizzo_il_liquame_su_terreni_terzi;
        }
    }

    /**
     * @return the preferenza_rimozione
     */
    public Double getPreferenza_rimozione() {
        return preferenza_rimozione;
    }

    /**
     * @param preferenza_rimozione the preferenza_rimozione to set
     */
    public void setPreferenza_rimozione(Double preferenza_rimozione) {
        if(preferenza_rimozione != null) {
            this.preferenza_rimozione = preferenza_rimozione;
        }
    }

  

    /**
     * @return the sceltaoperazione
     */
    public int getSceltaoperazione() {
        return sceltaoperazione;
    }

    /**
     * @param sceltaoperazione the sceltaoperazione to set
     */
    public void setSceltaoperazione(int sceltaoperazione) {
        this.sceltaoperazione = sceltaoperazione;
    }

    /**
     * @return the distanza
     */
    public int getDistanza() {
        //distanza++;
        System.out.println("get distanza " + distanza + " preferenza " + this.preferenza_rimozione + " scelta " + this.sceltaoperazione);
        //setDistanza(distanza);
        switch (this.sceltaoperazione)
        {
            case 118:
                
               setDistanza(distanzerimo.get(new Integer(166)));
                break;
             case 119:
                 setDistanza(distanzerimo.get(new Integer(167)));
                break;
              case 120:
                  setDistanza(distanzerimo.get(new Integer(168)));
                break;
              case 121:
                  setDistanza(distanzerimo.get(new Integer(169)));
                break;     
                  
                  
                
        }
        return distanza;
    }

    /**
     * @param distanza the distanza to set
     */
    public void setDistanza(int distanza) {
        
       this.distanza =  distanza;
         System.out.println("set distanza " + distanza + " preferenza " + this.preferenza_rimozione + " scelta " + this.sceltaoperazione);
         switch (this.sceltaoperazione)
        {
            case 118:
                distanzerimo.put(new Integer(166),distanza);
               
                break;
             case 119:
                 distanzerimo.put(new Integer(167),distanza);
                break;
              case 120:
                  distanzerimo.put(new Integer(168),distanza);
                break;
              case 121:
                  distanzerimo.put(new Integer(169),distanza);
                break;     
                  
                  
                
        }
        
        
    }

    /**
     * @return the fattore_trasferimento_o2
     */
//    public double getFattore_trasferimento_o2() {
//        return fattore_trasferimento_o2;
//    }
//
//    /**
//     * @param fattore_trasferimento_o2 the fattore_trasferimento_o2 to set
//     */
//    public void setFattore_trasferimento_o2(double fattore_trasferimento_o2) {
//        this.fattore_trasferimento_o2 = fattore_trasferimento_o2;
//    }

    /**
     * @return the costo_energia
     */
//    public double getCosto_energia() {
//        return costo_energia;
//    }
//
//    /**
//     * @param costo_energia the costo_energia to set
//     */
//    public void setCosto_energia(double costo_energia) {
//        this.costo_energia = costo_energia;
//    }

    /**
     * @return the costo_investimento
//     */
//    public double getCosto_investimento() {
//        return costo_investimento;
//    }
//
//    /**
//     * @param costo_investimento the costo_investimento to set
//     */
//    public void setCosto_investimento(double costo_investimento) {
//        this.costo_investimento = costo_investimento;
//    }

//    /**
//     * @return the costo_gestione_unitaria
//     */
//    public double getCosto_gestione_unitaria() {
//        return costo_gestione_unitaria;
//    }
//
//    /**
//     * @param costo_gestione_unitaria the costo_gestione_unitaria to set
//     */
//    public void setCosto_gestione_unitaria(double costo_gestione_unitaria) {
//        this.costo_gestione_unitaria = costo_gestione_unitaria;
//    }

    /**
     * @return the tasso_interesse
     */
//    public double getTasso_interesse() {
//        return tasso_interesse;
//    }
//
//    /**
//     * @param tasso_interesse the tasso_interesse to set
//     */
//    public void setTasso_interesse(double tasso_interesse) {
//        this.tasso_interesse = tasso_interesse;
//    }

    /**
     * @return the durata_investimento
     */
//    public double getDurata_investimento() {
//        return durata_investimento;
//    }
//
//    /**
//     * @param durata_investimento the durata_investimento to set
//     */
//    public void setDurata_investimento(double durata_investimento) {
//        this.durata_investimento = durata_investimento;
//    }
//
//    /**
//     * @return the hrt
//     */
//    public double getHrt() {
//        return hrt;
//    }
//
//    /**
//     * @param hrt the hrt to set
//     */
//    public void setHrt(double hrt) {
//        this.hrt = hrt;
//    }

    /**
     * @return the emissioni_n2
     */
//    public double getEmissioni_n2() {
//        return emissioni_n2;
//    }
//
//    /**
//     * @param emissioni_n2 the emissioni_n2 to set
//     */
//    public void setEmissioni_n2(double emissioni_n2) {
//        this.emissioni_n2 = emissioni_n2;
//    }
//
//    /**
//     * @return the manutenzione
//     */
//    public double getManutenzione() {
//        return manutenzione;
//    }
//
//    /**
//     * @param manutenzione the manutenzione to set
//     */
//    public void setManutenzione(double manutenzione) {
//        this.manutenzione = manutenzione;
//    }
//
//    /**
//     * @return the investimento
//     */
//    public double getInvestimento() {
//        return investimento;
//    }
//
//    /**
//     * @param investimento the investimento to set
//     */
//    public void setInvestimento(double investimento) {
//        this.investimento = investimento;
//    }
//
//    /**
//     * @return the anni
//     */
//    public double getAnni() {
//        return anni;
//    }
//
//    /**
//     * @param anni the anni to set
//     */
//    public void setAnni(double anni) {
//        this.anni = anni;
//    }
//
//    /**
//     * @return the costo_calce
//     */
//    public double getCosto_calce() {
//        return costo_calce;
//    }
//
//    /**
//     * @param costo_calce the costo_calce to set
//     */
//    public void setCosto_calce(double costo_calce) {
//        this.costo_calce = costo_calce;
//    }
//
//    /**
//     * @return the costo_acido_solforico
//     */
//    public double getCosto_acido_solforico() {
//        return costo_acido_solforico;
//    }
//
//    /**
//     * @param costo_acido_solforico the costo_acido_solforico to set
//     */
//    public void setCosto_acido_solforico(double costo_acido_solforico) {
//        this.costo_acido_solforico = costo_acido_solforico;
//    }
//
//    /**
//     * @return the valore_solfato_ammonio
//     */
//    public double getValore_solfato_ammonio() {
//        return valore_solfato_ammonio;
//    }
//
//    /**
//     * @param valore_solfato_ammonio the valore_solfato_ammonio to set
//     */
//    public void setValore_solfato_ammonio(double valore_solfato_ammonio) {
//        this.valore_solfato_ammonio = valore_solfato_ammonio;
//    }
//
//    /**
//     * @return the usodicalce
//     */
//    public double getUsodicalce() {
//        return usodicalce;
//    }
//
//    /**
//     * @param usodicalce the usodicalce to set
//     */
//    public void setUsodicalce(double usodicalce) {
//        this.usodicalce = usodicalce;
//    }
//
//    /**
//     * @return the usodiacidosolforico
//     */
//    public double getUsodiacidosolforico() {
//        return usodiacidosolforico;
//    }
//
//    /**
//     * @param usodiacidosolforico the usodiacidosolforico to set
//     */
//    public void setUsodiacidosolforico(double usodiacidosolforico) {
//        this.usodiacidosolforico = usodiacidosolforico;
//    }
//
//    /**
//     * @return the energia_elettrica
//     */
//    public double getEnergia_elettrica() {
//        return energia_elettrica;
//    }
//
//    /**
//     * @param energia_elettrica the energia_elettrica to set
//     */
//    public void setEnergia_elettrica(double energia_elettrica) {
//        this.energia_elettrica = energia_elettrica;
//    }

    /**
     * @return the potenza_termica_necessaria
     */
  /*  public double getPotenza_termica_necessaria() {
        return potenza_termica_necessaria;
    }*/

    /**
     * @param potenza_termica_necessaria the potenza_termica_necessaria to set
     */
    /*public void setPotenza_termica_necessaria(double potenza_termica_necessaria) {
        this.potenza_termica_necessaria = potenza_termica_necessaria;
    }*/

    /**
     * @return the costo_energia_termica
     */
    /*public double getCosto_energia_termica() {
        return costo_energia_termica;
    }*/

    /**
     * @param costo_energia_termica the costo_energia_termica to set
     */
    /*public void setCosto_energia_termica(double costo_energia_termica) {
        this.costo_energia_termica = costo_energia_termica;
    }*/

    /**
     * @return the efficienza_strippaggio
     */
   /* public double getEfficienza_strippaggio() {
        return efficienza_strippaggio;
    }

    /**
     * @param efficienza_strippaggio the efficienza_strippaggio to set
     */
   /* public void setEfficienza_strippaggio(double efficienza_strippaggio) {
        this.efficienza_strippaggio = efficienza_strippaggio;
    }*/

   

    /**
     * @return the ricavo_solfato_ammonio
     */
//    public double getRicavo_solfato_ammonio() {
//        return ricavo_solfato_ammonio;
//    }
//
//    /**
//     * @param ricavo_solfato_ammonio the ricavo_solfato_ammonio to set
//     */
//    public void setRicavo_solfato_ammonio(double ricavo_solfato_ammonio) {
//        this.ricavo_solfato_ammonio = ricavo_solfato_ammonio;
//    }
//
//    /**
//     * @return the efficienza_strippaggio
//     */
//    public double getEfficienza_strippaggio() {
//        return efficienza_strippaggio;
//    }
//
//    /**
//     * @param efficienza_strippaggio the efficienza_strippaggio to set
//     */
//    public void setEfficienza_strippaggio(double efficienza_strippaggio) {
//        this.efficienza_strippaggio = efficienza_strippaggio;
//    }
//
//    /**
//     * @return the efficienza_rimozione_volume
//     */
//    public double getEfficienza_rimozione_volume() {
//        return efficienza_rimozione_volume;
//    }
//
//    /**
//     * @param efficienza_rimozione_volume the efficienza_rimozione_volume to set
//     */
//    public void setEfficienza_rimozione_volume(double efficienza_rimozione_volume) {
//        this.efficienza_rimozione_volume = efficienza_rimozione_volume;
//    }

    /**
     * @return the rimozione_sv
     */
//    public double getRimozione_sv() {
//        return rimozione_sv;
//    }
//
//    /**
//     * @param rimozione_sv the rimozione_sv to set
//     */
//    public void setRimozione_sv(double rimozione_sv) {
//        this.rimozione_sv = rimozione_sv;
//    }
//
//    /**
//     * @return the tempo_areazione_sultotale
//     */
//    public double getTempo_areazione_sultotale() {
//        return tempo_areazione_sultotale;
//    }
//
//    /**
//     * @param tempo_areazione_sultotale the tempo_areazione_sultotale to set
//     */
//    public void setTempo_areazione_sultotale(double tempo_areazione_sultotale) {
//        this.tempo_areazione_sultotale = tempo_areazione_sultotale;
//    }
//
//    /**
//     * @return the fabbisognoo2
//     */
//    public double getFabbisognoo2() {
//        return fabbisognoo2;
//    }
//
//    /**
//     * @param fabbisognoo2 the fabbisognoo2 to set
//     */
//    public void setFabbisognoo2(double fabbisognoo2) {
//        this.fabbisognoo2 = fabbisognoo2;
//    }
          
        
    
}
