/*
 * Trattamento della seprazione a vite . E' un trattamento e quindi ha al suo interno un contenitore reflui.
 * In questo caso ha degli ooutput gestionali.Modifica le caratteristiche chimiche delle varie tipologie di reflui
 * contenuti nel contenitore reflui in base alle funzioni di trasformazione del trattamento specifico della separazione.
 */
package ager.trattamenti;

import ager.Refluo;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.Parametridiprogetto;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;



/**
 * Questa classe rappresenta il blocco di trattamento della "Separazione a vite elicoidale".
 * @author giorgio
 */
public class SeparazioneAVite extends Trattamento {
    
  private static SeparazioneAVite istanza;
  
  /*****************************************************************
   * OUTPUT GESTIONALI
   *****************************************************************/
  /**
   * numero di ore complessive
   */
  private double orecomplessive = 0;
  /**
   * numero dei separatori
   */
  private double numeroseparatori = 0;
  /**
   * costo investimento
   */
  //private double costoinvestimento = 0;sostituito da Trattamento.investimento
  /**
   * costo gestione
   */
  //private double costogestione = 0; sostituito da Trattamento.esercizio
  
  /**
   * costo gestione
   */
  private double costototale = 0;
  /*****************************************************************
   * OUTPUT GESTIONALI
   *****************************************************************/
  
  /****************************************************************
   * PARAMETRI DI PROGETTO
   * Sono i valori di riferimento per il calcolo degli output
   * gestionali. Tali calcoli sono basati sulla scelta del tipo macchina
   * e sul totale liquame.
   ****************************************************************/
  /**
   * Sarà il futuro indice degli arry di double sottostanti.
   * Lo zero sta per tipo macchina piccola e l'1 per tipo macchina
   * grande. La scelta del tipo macchina dipende dal costo totale.
   */
    int tipomacchina = 0;
    double[] investimentopar = new double[2];//{45000, 60000};
    double[] annipar = new double[2];//{8, 8};
    double[] costogestionepar = new double[2];//{0.2, 0.25};
    double[] costoenergiapar = new double[2];//{0.15, 0.15};
    double[] potenzaseppar = new double[2];//{4, 5.5};
    double[] oreutlizzopar = new double[2];//{5000, 6000};
    double[] portatapar = new double[2];//{15, 24};
    double[] fattorech4par = new double[2];//{0, 0};
    double[] fattorenh3par = new double[2];//{5, 5};
    double[] fattoreco2par = new double[2];//{0.1, 0.1};
    double[] fattoren2par = new double[2];//{0, 0};
    double[] fattoren20par = new double[2];//{0, 0};
    
   /****************************************************************
   * PARAMETRO DI PROGETTO
   *****************************************************************/
  /**
    * Lista dei parametri di progetto
    */
   List<db.ParametridiprogettoS> parametridiprogetto = null;
  
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
 
    DettaglioCuaa dettoCuaa ;
    Parametridiprogetto classeparametri = null;
    
    
  private SeparazioneAVite(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
      //dettoCuaa = new DettaglioCuaa();
       //parametridiprogetto = new LinkedList<db.ParametridiprogettoS>();
       
       
       /**
         * recupero il session bean dettaGlioCuaa
         */
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         dettoCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         /**
          * recupero i parametri di progetto della seprazione a vite elicoidale e li uso in tutta la classe
          */
         classeparametri = new Parametridiprogetto("provagiorgio13",3,dettoCuaa.getIdscenario());
         
         inizializzaParametri();
         
  }
  
  
   public static SeparazioneAVite getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new SeparazioneAVite(/*totlet,totliq*/);
    }

    return istanza;
  }
  
  
   /**
    * imposta in un solo colpo le variabili della separazione a vite elicoidale 
    * in base al contenuto della listaparametri che sono i parametri di progetto
    * recuperati dal database
    */
   private void inizializzaParametri()
   {
       //this.annipar[0] =  getParametrodiprogetto("anni", null).
       
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        dettoCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
        Parametridiprogetto classeparametri = new Parametridiprogetto("provagiorgio13",3,dettoCuaa.getIdscenario());
        /**
         * salvo il parametro investimento piccolo e grande
         */
        this.investimentopar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "investimento",null, null,"piccolo").getValore());
        this.investimentopar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "investimento",null, null,"grande").getValore());
         /**
         * salvo il parametro anni piccolo e grande
         */
        this.annipar[0] =  Double.parseDouble(classeparametri.getParametrodiprogetto(2, "anni",null, null,"piccolo").getValore());
        this.annipar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "anni",null, null,"grande").getValore());
         /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.costogestionepar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "costo_gestione",null, null,"piccolo").getValore());
        this.costogestionepar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "costo_gestione",null, null,"grande").getValore());
         /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.costoenergiapar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "costo_energia",null, null,"piccolo").getValore());
        this.costoenergiapar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "costo_energia",null, null,"grande").getValore());
        /**
         * salvo il parametro costo gestione piccolo e grande
         */
        
        this.potenzaseppar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "potenza_separatore",null, null,"piccolo").getValore());
        this.potenzaseppar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "potenza_separatore",null, null,"grande").getValore());
        
         /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.oreutlizzopar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "ore_utilizzo_separatore_anno",null, null,"piccolo").getValore());
        this.oreutlizzopar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "ore_utilizzo_separatore_anno",null, null,"grande").getValore());
        
         /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.portatapar[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "portata",null, null,"piccolo").getValore());
        this.portatapar[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "portata",null, null,"grande").getValore());
        /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.fattorech4par[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_ch4",null, null,"piccolo").getValore());
        this.fattorech4par[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_ch4",null, null,"grande").getValore());
         /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.fattorenh3par[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_nh3",null, null,"piccolo").getValore());
        this.fattorenh3par[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_nh3",null, null,"grande").getValore());
        /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.fattoreco2par[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_co2",null, null,"piccolo").getValore());
        this.fattoreco2par[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_co2",null, null,"grande").getValore());
        /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.fattoren2par[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_n2",null, null,"piccolo").getValore());
        this.fattoren2par[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_n2",null, null,"grande").getValore());
        /**
         * salvo il parametro costo gestione piccolo e grande
         */
        this.fattoren20par[0] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_n2o",null, null,"piccolo").getValore());
        this.fattoren20par[1] = Double.parseDouble(classeparametri.getParametrodiprogetto(2, "fattore_emissione_n2o",null, null,"grande").getValore());
 
        
   }
   
   
  
  
  
  
 

    /**
     * @return the orecomplessive
     */
    public double getOrecomplessive() {
        return orecomplessive;
    }

    /**
     * @param orecomplessive the orecomplessive to set
     */
    public void setOrecomplessive(double orecomplessive) {
        this.orecomplessive = orecomplessive;
    }

    /**
     * @return the numeroseparatori
     */
    public double getNumeroseparatori() {
        return numeroseparatori;
    }

    /**
     * @param numeroseparatori the numeroseparatori to set
     */
    public void setNumeroseparatori(double numeroseparatori) {
        this.numeroseparatori = numeroseparatori;
    }
    
    /**
     * Ritorna il costo totale inteso come costo di investimento  + costo di gestione
     * in funzione del tipo di macchina che si passa al metodo.
     * @param tipomacchina 0 tipomacchina piccola 1 tipo macchina grande
     * @return tipo double costo totale
     */
    private double sceltaTipoMacchina(int tipomacchina)
    {
        
        if(tipomacchina < 0 || tipomacchina > 1)
            return 0;
        
        double totaleliquame = this.getContenitoreRefluiIn().totale("Liquame").getMetricubi();
        
        this.orecomplessive = totaleliquame / this.portatapar[tipomacchina];
        this.numeroseparatori = Math.ceil(this.orecomplessive / this.oreutlizzopar[tipomacchina]);
       /* this.setCostoinvestimento(this.numeroseparatori * this.investimentopar[tipomacchina] / this.annipar[tipomacchina]);
        this.setCostogestione((this.costogestionepar[tipomacchina] + this.costoenergiapar[tipomacchina] * this.potenzaseppar[tipomacchina])*this.orecomplessive);
        this.setCostototale(this.getCostogestione() + this.getCostoinvestimento());
        */
        
        this.investimento =this.numeroseparatori * this.investimentopar[tipomacchina] / this.annipar[tipomacchina];
        this.esercizio =(this.costogestionepar[tipomacchina] + this.costoenergiapar[tipomacchina] * this.potenzaseppar[tipomacchina])*this.orecomplessive;
        this.setCostototale(this.investimento+ this.esercizio);
        
        
        
        this.energiaconsumata = this.potenzaseppar[tipomacchina] * this.orecomplessive;
        
        return this.getCostototale();
    }
    
    
    /**
     * calcola gli output gestionali ovvero imposta  
     * le ore complessive , il numer separatori, costo investimento ,
     * costo gestione, costo totale in funzione del costo totale minore.
     * Di conseguenza imposta la variable tipomacchina adeguato .
     * Questo metodo deve essere lanciato prima del calcolaRefluo perchè nel calcola
     * refluo devi gia sapere quale tipo di macchina hai scelto .
     */
    public void calcolaGestionali()
    {
        double scelta1 = sceltaTipoMacchina(0);
        double scelta2 = sceltaTipoMacchina(1);
        
        if(scelta1 > scelta2)
            this.tipomacchina = 1;
        else
            this.tipomacchina = 0;
        
        
        sceltaTipoMacchina(this.tipomacchina);
        
    }
     
    
    /**
     * Prende in ingresso il nome di un entita del databse, il  nome del campo(attributo) ed il valore del campo
     * ed esegue una query per recuperare il valore del campo 
     * @return 
     */
  /*  private String decodificaParametroProgetto(EntityManager entityManager , String entita,String attributo,int valoreattributo)
    {
        
        String query = "SELECT s.nome FROM "+entita+" s WHERE s."+attributo+" = "+ valoreattributo;
         System.out.println(query);
         Query q = entityManager.createQuery(query);
         
         String result = (String)q.getSingleResult();
         
         if(result == null)
         {
             result = "null";
             System.out.println(query);
         }
        
        return result;
    }*/
    
    
    
    /**
     * interroga la tabella parametridiprogetto per ottenere le efficienze per la aseparazione a vite
     * @param scenario
     * @param trattamento
     * @param entitacoinvolte
     * @param tipoliquame 1 bovino 2 suino 3 avicolo 4 altro
     * @param caratteristica 1 volume 2 azotototale 3 azotoammoniacale 4 sostanzasecca 5 solidivolatili 6 fosforototale 7 potassio
     * @return 
     */
   /* private double[] recuperaEfficienze(db.ScenarioI scenario,db.TrattamentoS trattamento,int entitacoinvolte,int tipoliquame,int caratteristica)
    {
        
        Query  q = entityManager.createQuery("SELECT t FROM ParametridiprogettoS t WHERE ( t.idScenario=?1 AND t.idTrattamento =?2 AND t.entitacoinvolte=?3 AND t.contenutoattributo=?4 AND t.contenutoattributo1=?5 )AND(t.idNomeparametro.nome=?6 OR t.idNomeparametro.nome=?7 OR t.idNomeparametro.nome=?8 OR t.idNomeparametro.nome=?9)");
        q.setParameter(1, scenario);
        q.setParameter(2, trattamento);
        /**
         * numero delle entita coinvolte
         */
       /* q.setParameter(3, entitacoinvolte);
        /**
         * è il contenuto di contenutoattributo di parametridiprogetto che nel caso della separazione a vite 
         * rappresenta il tipomateria ovvero bovini,suini,avioli,altri
         */
      /*  q.setParameter(4, String.valueOf(tipoliquame));
        /**
         * è il contenuto di contenutoattributo1 di parametridiprogetto che nel caso della separazione a vite 
         * rappresenta le caratteristichechimiche ovvero volume
         */
     /*   q.setParameter(5, String.valueOf(caratteristica));
        q.setParameter(6, "c1");
        q.setParameter(7, "c2");
        q.setParameter(8, "m1");
        q.setParameter(9, "m2");
        
        List<db.ParametridiprogettoS> parametri = (List<db.ParametridiprogettoS>)q.getResultList();
        
        double[] ritorno = new double[4];
        String tmp = "";
        for(db.ParametridiprogettoS para:parametri)
        {
            //tmp = para.getValore().replace(",", ".");
            
            //System.out.println("nome parametro " + para.getIdNomeparametro().getNome() + " valore " + para.getValore());
             
                if(para.getIdNomeparametro().getNome().equals("c1"))
                {
                             
                             ritorno[0] = Double.valueOf(tmp);
                             //System.out.println("c1 in 0 " + ritorno[0]  + para.getIdNomeparametro().getNome());
                }
                
                
                if(para.getIdNomeparametro().getNome().equals("c2"))
                {
                             ritorno[1] = Double.valueOf(tmp);
                              //System.out.println("c2 in 1 " + ritorno[1] +para.getIdNomeparametro().getNome() );
                }
                
                
                if(para.getIdNomeparametro().getNome().equals("m1"))
                {
                             ritorno[2] = Double.valueOf(tmp);
                             //System.out.println("m1 in 2 " + ritorno[2] +para.getIdNomeparametro().getNome() );
                }
                
                
                if(para.getIdNomeparametro().getNome().equals("m2"))
                {
                             ritorno[3] = Double.valueOf(tmp);
                             //System.out.println("m2 in 3 " + ritorno[3] +para.getIdNomeparametro().getNome() );
                }
                    
        }
        return ritorno;
    }*/
    
    
    /**
     * Modifica il contenuto di contenitore reflui di tutte le tipolgie
     * di refluo in funzione delle specifiche delle efficienze e delle funzioni
     * di trasformazione della separazione.
     */
     
    public void calcolaRefluo()
    {
        
        /**
         * recupero il totale di liqume e letame su tutte le tipolgie di refluo
         */
        //Refluo totale = this.getContenitoreReflui().totale("Totale");
                
        /**
         * mi collego al db provagiorgio4 per ottenere tutte le efficienze
         */
        entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio13");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
       
     // Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario",dettoCuaa.getIdscenario());
     // System.out.println(this.getClass().getCanonicalName() + " dettoCuaa.getIdscenario " + dettoCuaa.getIdscenario() + " scenario " + dettoCuaa.getScenario() + " cuaa " + dettoCuaa.getCuaa());
     // db.ScenarioI scenario = (db.ScenarioI)q.getSingleResult();
      
      /**
       * id 3 perchè la separazione a vite elicoidale nella tabella dei trattamenti a id 3
       */
     // q = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",3);
     // db.TrattamentoS trattamento = (db.TrattamentoS)q.getSingleResult();
     
      /**
       * recupero le caratteristiche chimiche e le tipologie di animali in tipomateria
       */
      Query q3 = entityManager.createNamedQuery("CaratteristichechimicheS.findAll");
      List<db.CaratteristicheChmiche> caratteri = (List<db.CaratteristicheChmiche>)q3.getResultList();
      Query q4 = entityManager.createNamedQuery("TipomateriaS.findByAnimale").setParameter("animale", true);
      /*List<db.TipomateriaS> tipo = (List<db.TipomateriaS>)q4.getResultList();
      
      for(db.TipomateriaS ti:tipo)
      {
          for(db.CaratteristicheChmiche cara:caratteri)
          {
              
               
              // double[] effi = this.classeparametri.getEfficienze(ti.getId(),cara.geti);
               
              // calcolaEfficienza(ti.getNome(),cara.getNome(),effi);
          }
      }
      
      
     
        
        /**
         * l'1 nella riga seguente ovvero della query q dovra essere il medesimo in q.setparameter(4,"1")
         * l'1 nella riga 282 ovvero della query q1 dovra essere il medesimo in q.setparameter(5,"1")
         */
       /* Query q1 = entityManager.createNamedQuery("CaratteristichechimicheS.findById").setParameter("id",1);
        db.CaratteristichechimicheS cara = (db.CaratteristichechimicheS)q1.getSingleResult();
        Query q2 = entityManager.createNamedQuery("TipomateriaS.findById").setParameter("id",1);
        db.TipomateriaS tipomateria = (db.TipomateriaS)q2.getSingleResult();
        
       
        System.out.println("   c1 "+ risultato[0] + " c2 " + risultato[1] + " m1 " + risultato[2] + " m2 " + risultato[3] + " caratteristica " +cara.getNome() + " tipo " + tipomateria.getNome());
        
        */
        entityManager.close();
        /**
         * per ogni efficienza trovata modifico la caratterstica corrispettiva del refluo 
         */
      /*  for(db.Efficienza effi:results)
        {
           // System.out.println(" Tipologia animale " + effi.getTiporefluo().getTipologiaanimale() +"  caratteristica " + effi.getCaratteristichechimiche().getNome() );
            calcolaEfficienza(effi.getTiporefluo().getTipologiaanimale(),effi.getCaratteristichechimiche().getNome(),effi);
        }*/
       
        
        /**
         * effettuo lo scambio tra i contenitori cosi che uso quello iniziale per avere 
         * i dati per calcolare i risulati che metto nel contenitore di out e poi aggirno 
         * quello iniziale con i dati di quello out.
         * 
         */
       // this.setContenitoreRefluiIn(this.getContenitoreRefluiOut());
        
        //System.out.println("esempio svn");
        
    }
    
    
    /**
     * Modifica i valori del contenitore reflui in funzione delle efficienze rilevate nel db provagiorgio4 
     * tabella efficienze
     * @param tipoliquame
     * @param caratteristica
     * @param volumeTotale 
     * @param efficienza
     */
    private void calcolaEfficienza(String tipoliquame,String caratteristica,double[] efficienza)
    {
         String  tipoletame ="";
        /**
         * metto la prima lettera di tipoliquame maiuscola
         */
        char[] stringArray = tipoliquame.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        tipoliquame = new String(stringArray);
        /**
         * gli aggiungo la stringa "Letame" perchè devo calcolare le efficienze e modificare prima
         * i letami e poi sulla base dei valori dei letami modifico i liquami
         */
       tipoletame = "Letame " + tipoliquame;
       tipoliquame = "Liquame " + tipoliquame;
        
        
        /**
         * sostanza secca X = è il parametro per la rilevazione dell'efficienza ovvero riporto 
         * la sostanza secca sull'asse delle x dividendo la sostanza secca di un particolare liquame
         * per i metri cubi di quel particolare liquame
         */
        double sostanzaSeccaX = 0;
        
       
        /**
         * temp per l'efficienza
         */
        double efficienzaRilevata = 0;
       
                /**
                 * recupero il refluo corrispondente ad una particolare tipolgia di letame ed una
                 * tipologia di liquame in ingresso ed in uscita   
                 */
                Refluo reliquameIn = this.getContenitoreRefluiIn().getContenitore().get(tipoliquame);
                Refluo reletameIn = this.getContenitoreRefluiIn().getContenitore().get(tipoletame);
                
                Refluo reliquameOut = this.getContenitoreRefluiOut().getContenitore().get(tipoliquame);
                Refluo reletameOut = this.getContenitoreRefluiOut().getContenitore().get(tipoletame);
           
                /**
                 * calcolo il parametro che riporta sull'assse delle x
                 */
                sostanzaSeccaX = reliquameIn.getSostanzasecca() / reliquameIn.getMetricubi();
             
               /**
                 * recupero il valore dell'efficienza della caratteristica del particolare tipologia di relfuo animale
                 */
               efficienzaRilevata= getEfficienza(sostanzaSeccaX,efficienza[0],efficienza[1],efficienza[2],efficienza[3]);
              /**
                * sulla base dell'efficienza rilavata/calcolata modifico le caratteristiche del refluo 
                */
               modificaCaratteristica(reliquameIn,reletameIn,reliquameOut,reletameOut,caratteristica,efficienzaRilevata);
        
        
    }
    
    
    /**
     * Ritorna l'efficienza in percentuale in funziona della posizione
     * di valore sulla retta indicata/indivuduata dai quattro parametri c1,c2,m1,m2.
     * L'equazione è costruita sulla base della relazione con la sostanza secca presente nel refluo.
     * @param valore
     * @param c1
     * @param c2
     * @param m1
     * @param m2
     * @return 
     */
    private double getEfficienza(double valore,double c1,double c2,double m1,double m2)
    {
        double ret = 0;
        
        if(valore < m1)
        {
            ret = c1 * m1 + c2;
            return ret;
        }
        
        
        if(valore >= m1 && valore <= m2)
        {
            ret = c1 * valore +c2;
            return ret;
        }
        
        
        if(valore > m2)
        {
            ret = c1 * m2 + c2;
            return ret;
        }
        
        return ret;
    }
    
    
    /**
     * modifica la caratteristica chimica indicata dal parametro caratteristica del particolare refluo
     * @param liquame liquame che sara modificato
     * @param letame letame che sara modificato
     * @param caratteristica caratterstica chimica del refluo da modificare
     * @param efficienza  
     */
    private void modificaCaratteristica(Refluo liquameIn,Refluo letameIn,Refluo liquameOut,Refluo letameOut,String caratteristica,double efficienza)
    {
        double tmp1 = 0;
        double tmp2 = 0;
        
        switch(caratteristica)
        {
            case "volume":
                tmp1 = (liquameIn.getMetricubi() * efficienza) / 100;
               // System.out.println(" caratteristica liquame metri " +  liquameIn.getMetricubi()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());
                    letameOut.setMetricubi(tmp1);
                tmp2 = liquameIn.getMetricubi();
                    liquameOut.setMetricubi(tmp2 - tmp1);
                break;
            case "azotototale":
                tmp1 = (liquameIn.getAzotototale() - (liquameIn.getAzotoammoniacale() * this.fattorenh3par[this.tipomacchina] / 100) ) * efficienza / 100;
              //System.out.println(" caratteristica azotototale  azoto " +  liquameIn.getAzotototale()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setAzotototale(tmp1);
                tmp2 = liquameIn.getAzotototale();
                   liquameOut.setAzotototale((tmp2 - (liquameIn.getAzotoammoniacale() * this.fattorenh3par[this.tipomacchina]/100))*(1 -efficienza/100));
               
                break;
            case "azotoammoniacale":
                tmp1 = (liquameIn.getAzotoammoniacale() - (liquameIn.getAzotoammoniacale())*this.fattorenh3par[tipomacchina]/100)*efficienza/100;
           //System.out.println(" caratteristica azotoammoniacale azotoamm " +  liquameIn.getAzotoammoniacale()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setAzotoammoniacale(tmp1);
                tmp2 = liquameIn.getAzotoammoniacale();
                    liquameOut.setAzotoammoniacale((liquameIn.getAzotoammoniacale() - (liquameIn.getAzotoammoniacale()*this.fattorenh3par[tipomacchina]/100))*(1-efficienza/100));
                break;
            case "sostanzasecca":
                tmp1 = liquameIn.getSostanzasecca() * efficienza / 100;
                
          //System.out.println(" caratteristica sostanzasecca sostanzasecca " +  liquameIn.getSostanzasecca() + " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setSostanzasecca(tmp1);
                tmp2 = liquameIn.getSostanzasecca();    
                    liquameOut.setSostanzasecca(tmp2 - tmp1);
                    
             //       System.out.println("sostanza secca letame " + tmp1 + " sostanza secca liquame " + tmp2);
                    
                break;
            case "solidivolatili":
                tmp1 = liquameIn.getSolidivolatili()* efficienza / 100;
                
           //System.out.println(" caratteristica solidi volatili sol vol " +  liquameIn.getSolidivolatili()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setSolidivolatili(tmp1 );
                tmp2 =  liquameIn.getSolidivolatili();
                    liquameOut.setSolidivolatili(tmp2 - tmp1);
                break;
            case "fosforototale":
                tmp1 = liquameIn.getFosforototale() * efficienza / 100;
                
               // System.out.println(" caratteristica fosforototale  fosforototale " +  liquameIn.getFosforototale()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setFosforototale(tmp1);
                tmp2 = liquameIn.getFosforototale();
                    liquameOut.setFosforototale(tmp2 - tmp1);
                    
                    
             //          System.out.println("fosforo totale letame " + tmp1 + " fosforo totale liquame " + tmp2);
                    
                break;
            case "potassio":
                tmp1 = liquameIn.getPotassiototale() * efficienza /100;
                
               //System.out.println(" caratteristica potassio potassio " +  liquameIn.getPotassiototale()+ " efficienza " +efficienza + " letameout " + letameOut.getTipologia() + " liquameout " +liquameOut.getTipologia() + " liquamein "+liquameIn.getTipologia());

                    letameOut.setPotassiototale(tmp1);
                tmp2 = liquameIn.getPotassiototale();
                    liquameOut.setPotassiototale(tmp2 - tmp1);
                break;

                    
                    
                               
        }
    }
    
    /**
     * Calcola le emissioni prodotte dal trattamento della seprazione.
     * Le emissioni sono :
     * CH4  :metano
     * NH3  :azoto ammoniacale
     * C02  :anidride carbonica
     * N2   :azoto
     * N2O  :protossido di azoto
     * Il calcolo delle emissioni è fatto sul totale liquame.
     */
    public void calcolaEmissioni()
    {
        /**
         * ritorna una classe refluo che contiene tutti i totali 
         */
        Refluo re = this.getContenitoreRefluiIn().totale("Liquame");
        
        
        this.ch4 = re.getSolidivolatili() * this.fattorech4par[this.tipomacchina] / 100;
        this.nh3 = re.getAzotoammoniacale() * this.fattorenh3par[this.tipomacchina] / 100;
        this.co2 = re.getSolidivolatili() * this.fattoreco2par[this.tipomacchina] /100;
        this.n2 = re.getAzotototale() * this.fattoren2par[this.tipomacchina] / 100;
        this.n2o = re.getAzotototale() * this.fattoren20par[this.tipomacchina] / 100;
        
        
        
       // System.out.println("----separazione a vite elicoidale  calcolaEmissioni " + re.getAzotoammoniacale() + " fattore nh3 " + this.fattorenh3par[this.tipomacchina] + " solidi volatili " + re.getSolidivolatili() + " fattore co2 " + this.fattoreco2par[this.tipomacchina] + " ch4 " + this.ch4 + " co2 " + this.co2 + " nh3 " + this.nh3);
    }

    /**
     * @return the costoinvestimento
     */
   /* public double getCostoinvestimento() {
        return costoinvestimento;
    }

    /**
     * @param costoinvestimento the costoinvestimento to set
     */
    /*public void setCostoinvestimento(double costoinvestimento) {
        this.costoinvestimento = costoinvestimento;
    }

    /**
     * @return the costogestione
     */
   /* public double getCostogestione() {
        return costogestione;
    }

    /**
     * @param costogestione the costogestione to set
     */
   /* public void setCostogestione(double costogestione) {
        this.costogestione = costogestione;
    }

    /**
     * @return the costototale
     */
    public double getCostototale() {
        return costototale;
    }

    /**
     * @param costototale the costototale to set
     */
    public void setCostototale(double costototale) {
        this.costototale = costototale;
    }
    
}
