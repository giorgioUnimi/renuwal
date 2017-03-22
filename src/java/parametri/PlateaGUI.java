/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parametri;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import ager.trattamenti.Platea;
import ager.trattamenti.SeparazioneAVite;
import ager.trattamenti.Vasca;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
@ManagedBean(name = "plateaGUI")
@ViewScoped
public class PlateaGUI implements Serializable{
    
    
     private static final long serialVersionUID = 1L;
   
   /**
    * popolano i campi testo dei valori di input al trattamento
    * della separazione
    */
   private List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
   private List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
   
   /**
    * popolano i campi testo di output del trattamento della seprazione
    */
   private List<Refluo> listaCaratteristicheLiqOut = new LinkedList<Refluo>();
   private List<Refluo> listaCaratteristicheLetOut = new LinkedList<Refluo>();
    
   ContenitoreReflui contenitoreIniziale = null;
   
    /**
     * emissioni di un trattamento comune a tutti i trattamenti
     */
  private  double ch4 = 0;
  private  double nh3 = 0;
  private  double co2 = 0;
  private  double n2 = 0;
  private  double n2o = 0;
  
  /**
   * dati di output
   */
   private double stoccaggirichiesti  = 0;
   private double stoccaggipresenti = 0;
   private double stoccaggidarealizzare = 0;
  /**
   * gestionali
   */
   private double costocomplessivo = 0;
   private double costomanutenzione = 0;
   private double energiavenduta = 0;
   private double venditacompost = 0;
   private double solidoessiccato = 0;
   private double energiaprodotta = 0;
   private double energiaconsumata = 0;
   
    /**
    *INIZIO - di seguito la lista dei parametri di progetto del trattamento vasca
    */
   
   
   /**
    * coefficiente di emissione fnh3,fn20,...selezionato in vasca.xhtml mediante selectonemenu
    */
   private String coefficienteemissione ="1";
   /**
    * tipo di stoccaggio selezionato in platea.xhtml mediante selectonemenu
    */
   private String stoccaggio="21";
   /**
    * tipo di stoccaggio selezionato in platea.xhtml mediante selectonemenu
    */
   private String stoccaggio1="21";
   /**
    * tipo di coefficente economico del trattamento vasca ovvero una variabile 
    * che rappresenta i seguenti parametri di progetto di una vasca che sono a,b,Vmin,Vmax,Coefficiente di manutenzione annua
    */
   private String coefficienteeconomico = "9";
   /**
    * valore del coefficiente relativo allas elzione da selectonemenu coefficiente di emissione 
    * e tipo stoccaggio nella pagina vasca.xhtml
    */
   private Double valorecoefficiente= new Double(0d);
   /**
    * valore del coefficiente relativo alla selezione da selectonemenu coefficiente di emissione 
    * e tipo stoccaggio nella pagina platea.xhtml
    */
   private Double valorecoefficiente1= new Double(0d);
   
    private String giornianno = "";
    private String giorniprevisti = "";
    /*private String giornistoccaggiobovino = "";
    private String giornistoccaggiosuino = "";
    private String giornistoccaggioavicolo = "";
    private String giornistoccaggioaltri = "";
    private String giornistoccaggiobiomasse = "";*/
    private String giornistoccaggiopalabili ="";
    private String coefficientemetanobovino = "";
    private String coefficientemetanosuino = "";
    private String coefficientemetanoavicolo = "";
    private String coefficientemetanoaltro = "";
    private String coefficientemetanobiomasse = "";
     private String coefficientemetanobase = "";
    /**
    *FINE - di seguito la lista dei parametri di progetto del trattamento platea
    */
   
   /**
     * variabili tuili per la connesione al db
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    Parametridiprogetto classeparametri = null;
   
   public PlateaGUI()
   {
        DettaglioCuaa detto = new DettaglioCuaa();
        
       // ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(detto.getCuaa());
        
       
        
      //   contenitoreIniziale = contenitore.getContenitore();
        
           
        contenitoreIniziale = Platea.getInstance().getContenitoreRefluiIn();
      /*  System.out.println("----------STAMPA CONTENUTO PLATEA GUI ---------------");
        
        contenitoreIniziale.stampaContenuto();
         System.out.println("----------STAMPA CONTENUTO PLATEA GUI ---------------");*/
        
        for(String s:contenitoreIniziale.getTipologie())
        {
            if(s.contains("Liquame")) {
                listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
            }
            
            if(s.contains("Letame")) {
                listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
            }
        }
        
        
         listaCaratteristicheLet.add(contenitoreIniziale.totale("Letame"));
        listaCaratteristicheLiq.add(contenitoreIniziale.totale("Liquame"));
     
       inizializzaParametri();
        calcola();
   }
   
   
    /**
    * inizializza i parametri di progetto del trattamento vasca
    */
   private void inizializzaParametri()
   {
      /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
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
      /*   Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",7 );
         db.TrattamentoS tra = (db.TrattamentoS)q2.getSingleResult();
       /**
        * cerco tutti i parametri di progetto di un particolare trattamento e scenario
        */
    /*  Query q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p WHERE p.idScenario =?1 AND p.idTrattamento=?2 ");
      q.setParameter(1, sce);
      q.setParameter(2,tra);
      List<db.ParametridiprogettoS> pa= (List<db.ParametridiprogettoS>)q.getResultList();*/
       classeparametri = new Parametridiprogetto("renuwal2",7,dettCuaa.getIdscenario());
         List<db.ParametridiprogettoS> pa = classeparametri.getlistaparametri();
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
             case 6 :
                 this.giornianno = temp.getValore();
                 break;
             case 7:
                 this.giorniprevisti = temp.getValore();
                 break;
             /*case 8:
                 this.giornistoccaggiobovino = temp.getValore();
                 break;
             case 14:
                 this.giornistoccaggiosuino = temp.getValore();
                 break;
             case 15:
                 this.giornistoccaggioavicolo = temp.getValore();
                 break;
             case  42:
                 this.giornistoccaggioaltri = temp.getValore();
                 break;
             case 53:
                 this.giornistoccaggiobiomasse = temp.getValore();
                 break;*/
             case 54:
                 this.giornistoccaggiopalabili = temp.getValore();
                 break;
             case 55:
                 this.coefficientemetanobase = temp.getValore();
                 break;
             case 56:
                 this.coefficientemetanobovino = temp.getValore();
                 break;
             case 57:
                 this.coefficientemetanosuino = temp.getValore();
                 break;
             case 58 :
                 this.coefficientemetanoavicolo = temp.getValore();
                 break;
             case 59:
                 this.coefficientemetanoaltro = temp.getValore();
                 break;
             case 60:  
                 this.coefficientemetanobiomasse = temp.getValore();
                 break;
         }
     }
      
   }
   
   
   
    /**
    * aggiorna i valori dei parametri delle emissioni di output 
    * in funzione dei calcoli delle trasformazioni della platea
    */
   private void aggiornaEmissioni()
   {
       
       
       Platea platea = Platea.getInstance();
       
         if(platea.getContenitoreRefluiIn().totale("Letame").getMetricubi() != 0)
        {
       
       this.setCh4(platea.getCh4());
       this.setNh3(platea.getNh3());
       this.setCo2(platea.getCo2());
       this.setN2(platea.getN2());
       this.setN2o(platea.getN2o());
        }
   }
   
   
   private void aggiornaDatiOutput()
   {
        Platea platea = Platea.getInstance();
        
        if(platea.getContenitoreRefluiIn().totale("Letame").getMetricubi() != 0)
        {
        this.setStoccaggidarealizzare(platea.getStoccaggidarealizzare());
        this.setStoccaggipresenti(platea.getStoccaggipresenti());
        this.setStoccaggirichiesti(platea.getStoccaggirichiesti());
        this.setEnergiaconsumata(platea.getEnergiaconsumata());
                this.setEnergiaprodotta(platea.getEnergiaprodotta());
                this.setEnergiavenduta(platea.getEnergiavenduta());
                this.setVenditacompost(platea.getCompostvenduto());
                this.setSolidoessiccato(platea.getSolidoessiccato());
        }
   }
   
   
   private void aggiornaGestionali()
   {
       Platea platea = Platea.getInstance();
       
       //  if(platea.getContenitoreRefluiIn().totale("Letame").getMetricubi() != 0)
        {
       
      // this.setCostocomplessivo(platea.getInvestimento());
      // this.setCostomanutenzione(platea.getEsercizio());
       
       this.setCostocomplessivo(platea.getInvestimento());
            this.setCostomanutenzione(platea.getEsercizio());
            this.setEnergiaconsumata(platea.getEnergiaconsumata());
                this.setEnergiaprodotta(platea.getEnergiaprodotta());
                this.setEnergiavenduta(platea.getEnergiavenduta());
                this.setVenditacompost(platea.getCompostvenduto());
                this.setSolidoessiccato(platea.getSolidoessiccato());
       
       
        }
   }
   
   /* private boolean nascondiMostraRisultati(String id,boolean mostra)
    {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent comp = view.findComponent(id);
        if(comp != null)
        {
            System.out.println("id componente "+ id+ "  client id   " + comp.getClientId());
            comp.setRendered(mostra);
            
            return true;        
        }
        else
            return false;
        
       
        
    }*/
    
    
    
    
    public void calcola()
    {
         /**
         * richiamo una istanza del trattammento della separazione a vite
         */
        
        
        Platea platea = Platea.getInstance();
        
        
       // platea.azzeraContenitori();
        /**
         * imposto il suo contenitore dei reflui a quello iniziale del session bena in questione
         * che è SeparazioneGUI
         */
       platea.setContenitoreRefluiIn(contenitoreIniziale);
        
        /**
         * 
         */
       // platea.calcolaEmissioni();
       // platea.calcolaGestionali();
       // platea.calcolaRefluo();
        
        
        /**
         * dopo la separazione recupero il contenuto del Contenitorereflui del trattamento
         * della seprazione e popolo con esso il contenuto delle liste caratterstiche liquame e letame
         * out
         */
        
        ContenitoreReflui contenitorereflui = platea.getContenitoreRefluiOut();
        
        /**
         * Ogni trattamento contiene una tabella hash contenuta nella classe ContenitoreReflui
         */
        Hashtable<String,Refluo> tabellaHash = contenitorereflui.getContenitore();
        
        /**
         * svuoto le due liste di output 
         */
        this.listaCaratteristicheLetOut.clear();
        this.listaCaratteristicheLiqOut.clear();
        
        /**
         * variabile di appoggio per la classe Refluo
         */
        Refluo re = null;
        
        /**
         * ciclo sulle chiavi della tabella hash ed aggiungo alla listacaratteristicheoutliq
         * le diverse tipologie di liquame ed aggiungo alla listacaratteriscticheoutlet le diverse
         * tipolgie di letame
         */
        for(String s:contenitorereflui.getTipologie())
        {
            if(s.contains("Letame"))
            {
               re = tabellaHash.get(s);
               
               this.listaCaratteristicheLetOut.add(re);
               
            }
            
            if(s.contains("Liquame"))
            {
               re = tabellaHash.get(s);
               
               this.listaCaratteristicheLiqOut.add(re);
            }
        }
        
        
        
          this.aggiornaEmissioni();
          
          
          this.aggiornaGestionali();
          
          this.aggiornaDatiOutput();
    }

    /**
     * @return the listaCaratteristicheLiq
     */
    public List<Refluo> getListaCaratteristicheLiq() {
        return listaCaratteristicheLiq;
    }

    /**
     * @param listaCaratteristicheLiq the listaCaratteristicheLiq to set
     */
    public void setListaCaratteristicheLiq(List<Refluo> listaCaratteristicheLiq) {
        this.listaCaratteristicheLiq = listaCaratteristicheLiq;
    }

    /**
     * @return the listaCaratteristicheLet
     */
    public List<Refluo> getListaCaratteristicheLet() {
        return listaCaratteristicheLet;
    }

    /**
     * @param listaCaratteristicheLet the listaCaratteristicheLet to set
     */
    public void setListaCaratteristicheLet(List<Refluo> listaCaratteristicheLet) {
        this.listaCaratteristicheLet = listaCaratteristicheLet;
    }

    /**
     * @return the listaCaratteristicheLiqOut
     */
    public List<Refluo> getListaCaratteristicheLiqOut() {
        return listaCaratteristicheLiqOut;
    }

    /**
     * @param listaCaratteristicheLiqOut the listaCaratteristicheLiqOut to set
     */
    public void setListaCaratteristicheLiqOut(List<Refluo> listaCaratteristicheLiqOut) {
        this.listaCaratteristicheLiqOut = listaCaratteristicheLiqOut;
    }

    /**
     * @return the listaCaratteristicheLetOut
     */
    public List<Refluo> getListaCaratteristicheLetOut() {
        return listaCaratteristicheLetOut;
    }

    /**
     * @param listaCaratteristicheLetOut the listaCaratteristicheLetOut to set
     */
    public void setListaCaratteristicheLetOut(List<Refluo> listaCaratteristicheLetOut) {
        this.listaCaratteristicheLetOut = listaCaratteristicheLetOut;
    }

    /**
     * @return the ch4
     */
    public double getCh4() {
        return ch4;
    }

    /**
     * @param ch4 the ch4 to set
     */
    public void setCh4(double ch4) {
        this.ch4 = ch4;
    }

    /**
     * @return the nh3
     */
    public double getNh3() {
        return nh3;
    }

    /**
     * @param nh3 the nh3 to set
     */
    public void setNh3(double nh3) {
        this.nh3 = nh3;
    }

    /**
     * @return the co2
     */
    public double getCo2() {
        return co2;
    }

    /**
     * @param co2 the co2 to set
     */
    public void setCo2(double co2) {
        this.co2 = co2;
    }

    /**
     * @return the n2
     */
    public double getN2() {
        return n2;
    }

    /**
     * @param n2 the n2 to set
     */
    public void setN2(double n2) {
        this.n2 = n2;
    }

    /**
     * @return the n2o
     */
    public double getN2o() {
        return n2o;
    }

    /**
     * @param n2o the n2o to set
     */
    public void setN2o(double n2o) {
        this.n2o = n2o;
    }

    /**
     * @return the stoccaggirichiesti
     */
    public double getStoccaggirichiesti() {
        return stoccaggirichiesti;
    }

    /**
     * @param stoccaggirichiesti the stoccaggirichiesti to set
     */
    public void setStoccaggirichiesti(double stoccaggirichiesti) {
        this.stoccaggirichiesti = stoccaggirichiesti;
    }

    /**
     * @return the stoccaggipresenti
     */
    public double getStoccaggipresenti() {
        return stoccaggipresenti;
    }

    /**
     * @param stoccaggipresenti the stoccaggipresenti to set
     */
    public void setStoccaggipresenti(double stoccaggipresenti) {
        this.stoccaggipresenti = stoccaggipresenti;
    }

    /**
     * @return the stoccaggidarealizzare
     */
    public double getStoccaggidarealizzare() {
        return stoccaggidarealizzare;
    }

    /**
     * @param stoccaggidarealizzare the stoccaggidarealizzare to set
     */
    public void setStoccaggidarealizzare(double stoccaggidarealizzare) {
        this.stoccaggidarealizzare = stoccaggidarealizzare;
    }

    /**
     * @return the costocomplessivo
     */
    public double getCostocomplessivo() {
        return costocomplessivo;
    }

    /**
     * @param costocomplessivo the costocomplessivo to set
     */
    public void setCostocomplessivo(double costocomplessivo) {
        this.costocomplessivo = costocomplessivo;
    }

    /**
     * @return the costomanutenzione
     */
    public double getCostomanutenzione() {
        return costomanutenzione;
    }

    /**
     * @param costomanutenzione the costomanutenzione to set
     */
    public void setCostomanutenzione(double costomanutenzione) {
        this.costomanutenzione = costomanutenzione;
    }

    /**
     * @return the energiavenduta
     */
    public double getEnergiavenduta() {
        return energiavenduta;
    }

    /**
     * @param energiavenduta the energiavenduta to set
     */
    public void setEnergiavenduta(double energiavenduta) {
        this.energiavenduta = energiavenduta;
    }

    /**
     * @return the venditacompost
     */
    public double getVenditacompost() {
        return venditacompost;
    }

    /**
     * @param venditacompost the venditacompost to set
     */
    public void setVenditacompost(double venditacompost) {
        this.venditacompost = venditacompost;
    }

    /**
     * @return the solidoessiccato
     */
    public double getSolidoessiccato() {
        return solidoessiccato;
    }

    /**
     * @param solidoessiccato the solidoessiccato to set
     */
    public void setSolidoessiccato(double solidoessiccato) {
        this.solidoessiccato = solidoessiccato;
    }

    /**
     * @return the energiaprodotta
     */
    public double getEnergiaprodotta() {
        return energiaprodotta;
    }

    /**
     * @param energiaprodotta the energiaprodotta to set
     */
    public void setEnergiaprodotta(double energiaprodotta) {
        this.energiaprodotta = energiaprodotta;
    }

    /**
     * @return the energiaconsumata
     */
    public double getEnergiaconsumata() {
        return energiaconsumata;
    }

    /**
     * @param energiaconsumata the energiaconsumata to set
     */
    public void setEnergiaconsumata(double energiaconsumata) {
        this.energiaconsumata = energiaconsumata;
    }

    /**
     * @return the coefficienteemissione
     */
    public String getCoefficienteemissione() {
        return coefficienteemissione;
    }

    /**
     * @param coefficienteemissione the coefficienteemissione to set
     */
    public void setCoefficienteemissione(String coefficienteemissione) {
        this.coefficienteemissione = coefficienteemissione;
    }

    /**
     * @return the stoccaggio
     */
    public String getStoccaggio() {
        return stoccaggio;
    }

    /**
     * @param stoccaggio the stoccaggio to set
     */
    public void setStoccaggio(String stoccaggio) {
        this.stoccaggio = stoccaggio;
    }

    /**
     * @return the stoccaggio1
     */
    public String getStoccaggio1() {
        return stoccaggio1;
    }

    /**
     * @param stoccaggio1 the stoccaggio1 to set
     */
    public void setStoccaggio1(String stoccaggio1) {
        this.stoccaggio1 = stoccaggio1;
    }

    /**
     * @return the coefficienteeconomico
     */
    public String getCoefficienteeconomico() {
        return coefficienteeconomico;
    }

    /**
     * @param coefficienteeconomico the coefficienteeconomico to set
     */
    public void setCoefficienteeconomico(String coefficienteeconomico) {
        this.coefficienteeconomico = coefficienteeconomico;
    }

    /**
     * @return the valorecoefficiente
     */
    public Double getValorecoefficiente() {
        
        
         System.out.println(this.getClass().getCanonicalName() + "  getValorecoefficiente() coefficienteemissione " + this.coefficienteemissione +"   stoccaggio " + this.stoccaggio);
           if(this.coefficienteemissione.length() != 0 && this.stoccaggio.length() != 0)
        {
            /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
     /* ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
            
           /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
             entityManager = entityManagerFactory.createEntityManager();
                 jpa = (JpaEntityManager) entityManager.getDelegate();
                 /**
                  * recupero lo scenario ed il trattamento
                  */
              /*   Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario",dettCuaa.getIdscenario());
                 db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
                 
                 q1 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",7);
                 db.TrattamentoS tratta = (db.TrattamentoS)q1.getSingleResult();
                 
                 q1 = entityManager.createNamedQuery("NomeparametrdiprogettoS.findById").setParameter("id",Integer.parseInt(this.coefficienteemissione));
                 db.NomeparametrdiprogettoS nome = (db.NomeparametrdiprogettoS)q1.getSingleResult();
                 
                 Query q = entityManager.createQuery("Select p FROM ParametridiprogettoS p WHERE p.idTrattamento=?1 AND p.idScenario=?2 AND p.contenutoattributo=?3 AND p.idNomeparametro=?4");
                 q.setParameter(1,tratta);
                 q.setParameter(2, sce);
                 q.setParameter(3, this.stoccaggio);
                 q.setParameter(4, nome);
                 
                 db.ParametridiprogettoS param =(db.ParametridiprogettoS)q.getSingleResult();
                 
                 valorecoefficiente = Double.parseDouble(param.getValore());*/
      
                /* Parametridiprogetto classeparametri = new Parametridiprogetto("renuwal2",7,dettCuaa.getIdscenario());*/
                 db.ParametridiprogettoS param = classeparametri.getParametrodiprogetto(1,Integer.parseInt(this.coefficienteemissione),this.stoccaggio,null,null);
                 valorecoefficiente = new Double(Double.parseDouble(param.getValore()));
                 
                 System.out.println(this.getClass().getCanonicalName() + " valorecoefficiente " + this.valorecoefficiente.doubleValue());
        }
        
        
        return valorecoefficiente;
    }

    /**
     * @param valorecoefficiente the valorecoefficiente to set
     */
    public void setValorecoefficiente(Double valorecoefficiente) {
        if(valorecoefficiente != null)
            this.valorecoefficiente = new Double(valorecoefficiente.doubleValue());
    }

    /**
     * @return the valorecoefficiente1
     */
    public Double getValorecoefficiente1() {
        
         System.out.println(this.getClass().getCanonicalName() + "  getValorecoefficiente() coefficienteemissione " + this.coefficienteeconomico +"   stoccaggio " + this.stoccaggio1);
           if(this.coefficienteeconomico.length()!= 0 && this.stoccaggio1.length() != 0)
        {
            /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
     /* ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
            
           /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
             entityManager = entityManagerFactory.createEntityManager();
                 jpa = (JpaEntityManager) entityManager.getDelegate();
                 /**
                  * recupero lo scenario ed il trattamento
                  */
               /*  Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario",dettCuaa.getIdscenario());
                 db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
                 
                 q1 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",7);
                 db.TrattamentoS tratta = (db.TrattamentoS)q1.getSingleResult();
                 
                 q1 = entityManager.createNamedQuery("NomeparametrdiprogettoS.findById").setParameter("id",Integer.parseInt(this.coefficienteeconomico));
                 db.NomeparametrdiprogettoS nome = (db.NomeparametrdiprogettoS)q1.getSingleResult();
                 
                 Query q = entityManager.createQuery("Select p FROM ParametridiprogettoS p WHERE p.idTrattamento=?1 AND p.idScenario=?2 AND p.contenutoattributo=?3 AND p.idNomeparametro=?4");
                 q.setParameter(1,tratta);
                 q.setParameter(2, sce);
                 q.setParameter(3, this.stoccaggio1);
                 q.setParameter(4, nome);
                 
                 db.ParametridiprogettoS param =(db.ParametridiprogettoS)q.getSingleResult();*/
                 
               /*  Parametridiprogetto classeparametri = new Parametridiprogetto("renuwal2",7,dettCuaa.getIdscenario());*/
                 db.ParametridiprogettoS param = classeparametri.getParametrodiprogetto(1,Integer.parseInt(this.coefficienteeconomico),this.stoccaggio1,null,null);
                 
                 valorecoefficiente1 = new Double(Double.parseDouble(param.getValore()));
                 
                 
                 System.out.println(this.getClass().getCanonicalName() + " valorecoefficiente " + this.valorecoefficiente1.doubleValue());
        }
        
        
        
        return valorecoefficiente1;
    }

    /**
     * @param valorecoefficiente1 the valorecoefficiente1 to set
     */
    public void setValorecoefficiente1(Double valorecoefficiente1) {
        if(valorecoefficiente1 != null) {
            this.valorecoefficiente1 = new Double(valorecoefficiente1.doubleValue());
        }
    }

    /**
     * @return the giornianno
     */
    public String getGiornianno() {
        return giornianno;
    }

    /**
     * @param giornianno the giornianno to set
     */
    public void setGiornianno(String giornianno) {
        if(giornianno !=null && giornianno.length()!=0) {
            this.giornianno = giornianno;
        }
    }

    /**
     * @return the giorniprevisti
     */
    public String getGiorniprevisti() {
        return giorniprevisti;
    }

    /**
     * @param giorniprevisti the giorniprevisti to set
     */
    public void setGiorniprevisti(String giorniprevisti) {
        if(giorniprevisti != null && giorniprevisti.length()!=0) {
            this.giorniprevisti = giorniprevisti;
        }
    }

   

    /**
     * @return the coefficientemetanobovino
     */
    public String getCoefficientemetanobovino() {
        return coefficientemetanobovino;
    }

    /**
     * @param coefficientemetanobovino the coefficientemetanobovino to set
     */
    public void setCoefficientemetanobovino(String coefficientemetanobovino) {
        if(coefficientemetanobovino != null && coefficientemetanobovino.length()!=0) {
            this.coefficientemetanobovino = coefficientemetanobovino;
        }
    }

    /**
     * @return the coefficientemetanosuino
     */
    public String getCoefficientemetanosuino() {
        return coefficientemetanosuino;
    }

    /**
     * @param coefficientemetanosuino the coefficientemetanosuino to set
     */
    public void setCoefficientemetanosuino(String coefficientemetanosuino) {
        if(coefficientemetanosuino !=null && coefficientemetanosuino.length()!=0) {
            this.coefficientemetanosuino = coefficientemetanosuino;
        }
    }

    /**
     * @return the coefficientemetanoavicolo
     */
    public String getCoefficientemetanoavicolo() {
        return coefficientemetanoavicolo;
    }

    /**
     * @param coefficientemetanoavicolo the coefficientemetanoavicolo to set
     */
    public void setCoefficientemetanoavicolo(String coefficientemetanoavicolo) {
       if(coefficientemetanoavicolo !=null && coefficientemetanoavicolo.length()!=0) {
            this.coefficientemetanoavicolo = coefficientemetanoavicolo;
        }
    }

    /**
     * @return the coefficientemetanoaltro
     */
    public String getCoefficientemetanoaltro() {
        return coefficientemetanoaltro;
    }

    /**
     * @param coefficientemetanoaltro the coefficientemetanoaltro to set
     */
    public void setCoefficientemetanoaltro(String coefficientemetanoaltro) {
       if(coefficientemetanoaltro !=null && coefficientemetanoaltro.length()!=0) {
            this.coefficientemetanoaltro = coefficientemetanoaltro;
        }
    }

    /**
     * @return the coefficientemetanobiomasse
     */
    public String getCoefficientemetanobiomasse() {
        return coefficientemetanobiomasse;
    }

    /**
     * @param coefficientemetanobiomasse the coefficientemetanobiomasse to set
     */
    public void setCoefficientemetanobiomasse(String coefficientemetanobiomasse) {
        if(coefficientemetanobiomasse != null && coefficientemetanobiomasse.length()!=0) {
            this.coefficientemetanobiomasse = coefficientemetanobiomasse;
        }
    }

    /**
     * @return the coefficientemetanobase
     */
    public String getCoefficientemetanobase() {
        return coefficientemetanobase;
    }

    /**
     * @param coefficientemetanobase the coefficientemetanobase to set
     */
    public void setCoefficientemetanobase(String coefficientemetanobase) {
        if(coefficientemetanobase != null && coefficientemetanobase.length() != 0) {
            this.coefficientemetanobase = coefficientemetanobase;
        }
    }

    /**
     * @return the giornistoccaggiopalabili
     */
    public String getGiornistoccaggiopalabili() {
        return giornistoccaggiopalabili;
    }

    /**
     * @param giornistoccaggiopalabili the giornistoccaggiopalabili to set
     */
    public void setGiornistoccaggiopalabili(String giornistoccaggiopalabili) {
       if(giornistoccaggiopalabili != null && giornistoccaggiopalabili.length() != 0) {
            this.giornistoccaggiopalabili = giornistoccaggiopalabili;
        }
    }
    
    
    
    /**
     * metodo che si occupa di fare l'aggiornamento di un determinato pèarametro di progetto 
     * deve essere in grado di capire chi lo sta chimando e per questo uso il passaggio di parametro dalla
     * pagina vasca.xhtml al preente metodo passando un valore stringa che in reaslta è numerico e corrisponde 
     * al bottone che viene cliccato
     */
    public void aggiorna()
    {
       Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	  String action = params.get("action");
          
          System.out.println(this.getClass().getCanonicalName() + " azione " + action);
          
          
          
       /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
          
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
        
        /**
         * recupero il session bean dettaGlioCuaa
         */
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        /**
         * recupero un particolare scenario in funzione del valore presente in dettagliocuaa
         */
         Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
         db.ScenarioI sce = (db.ScenarioI)q1.getSingleResult();
         /**
          * recupero il trattamento relativo alla platea
          */
         Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",7 );
         db.TrattamentoS tra = (db.TrattamentoS)q2.getSingleResult();
         /**
          * seleziono il valore che devo passare in funzione del codice che mi è stato pasato di bottoni
          */
         String temp = "";
         int idnomeparametro = 0 ;
         String valoreattributo="";
         switch(Integer.parseInt(action))
         {
             /**
              * il caso 1 e 2 sono i casi delle due combo box in cui passo un valore numerico per distinguere su quale riga di combo
              * box sto lavorando . Posso usare direttamente i valori delle varaibili ed assegnarle a idnomeparametro e valoreattributo 
              * per il sistema di assegnazione dei valori adati agli item delle selectonemenu
              */
             case 1:
                //System.out.println(this.getClass().getCanonicalName() + " caso 1 coefficiente emissione "+  this.coefficienteemissione);
                //System.out.println( this.getClass().getCanonicalName() + " caso 1 stoccaggio "+ this.stoccaggio);
                 temp = String.valueOf(this.valorecoefficiente.doubleValue());
                 idnomeparametro = Integer.parseInt(this.coefficienteemissione);
                 valoreattributo = this.stoccaggio;
                 break;
             case 2:
                  //System.out.println(this.getClass().getCanonicalName() + " caso 1 coefficiente economico "+ this.coefficienteeconomico);
                 //System.out.println(this.getClass().getCanonicalName() + " caso 2 stoccaggio1 "+ this.stoccaggio1);
                
                 temp = String.valueOf(this.valorecoefficiente1.doubleValue());
                 idnomeparametro = Integer.parseInt(this.coefficienteeconomico);
                 valoreattributo = this.stoccaggio1;
                 break;
             
             case 6 :
                 temp = this.giornianno ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 7:
                temp =  this.giorniprevisti ;
                idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
            /* case 8:
                temp = this.giornistoccaggiobovino ;
                idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 14:
                 temp = this.giornistoccaggiosuino ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 15:
                 temp = this.giornistoccaggioavicolo ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case  42:
                 temp = this.giornistoccaggioaltri ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 53:
                temp = this.giornistoccaggiobiomasse ;
                idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;*/
             case 54:
                 temp = this.giornistoccaggiopalabili;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo = "";
                 break;
             case 55:
                 temp = this.coefficientemetanobase ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 56:
                 temp = this.coefficientemetanobovino ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 57:
                 temp = this.coefficientemetanosuino ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 58 :
                 temp = this.coefficientemetanoavicolo ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 59:
                 temp = this.coefficientemetanoaltro ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
             case 60:  
                 temp = this.coefficientemetanobiomasse ;
                 idnomeparametro = Integer.parseInt(action);
                 valoreattributo="";
                 break;
         }
          entityManager.getTransaction().begin();
         Query q4 = entityManager.createNamedQuery("NomeparametrdiprogettoS.findById").setParameter("id",idnomeparametro);
         db.NomeparametrdiprogettoS para = (db.NomeparametrdiprogettoS)q4.getSingleResult();
         /**
          * devo porre una distinzione tra il caso in cui valore attributo è vuoto ovvero mi trovo nel caso di aggiornamento di 
          * un parametro che dipende da altre entita ed il caso in cui il parametro dipenda da altre entita per cui valore attributo contiene
          * il valore dell'attributo specificato da idattributo ed identita in realzione con nomeentitaatrtibuto in parametridiprogetto
          */
         Query q = null;
         if(valoreattributo.length() == 0)
         {
             q = entityManager.createQuery("UPDATE ParametridiprogettoS p SET p.valore =?3 WHERE p.idScenario =?1 AND p.idTrattamento=?2 AND p.idNomeparametro=?4");
             q.setParameter(1, sce);
             q.setParameter(2,tra);
             q.setParameter(3,temp);
             q.setParameter(4, para);
             System.out.println(this.getClass().getCanonicalName() + " numero record aggiornati " +q.executeUpdate());
         }else
         {
             q = entityManager.createQuery("UPDATE ParametridiprogettoS p SET p.valore =?3 WHERE p.idScenario =?1 AND p.idTrattamento=?2 AND p.idNomeparametro=?4 AND p.contenutoattributo=?5");
             q.setParameter(1, sce);
             q.setParameter(2,tra);
             q.setParameter(3,temp);
             q.setParameter(4, para);
             q.setParameter(5, valoreattributo);
               System.out.println(this.getClass().getCanonicalName() + " numero record aggiornati " +q.executeUpdate());
         }
         System.out.println(this.getClass().getCanonicalName() + " idnomeparametro " + idnomeparametro + " valore attributo " + valoreattributo + " valore " + temp);
       /**
        * cerco tutti i parametri di progetto di un particolare trattamento e scenario
        */
     /* Query q = entityManager.createQuery("UPDATE ParametridiprogettoS p SET p.valore =?3 WHERE p.idScenario =?1 AND p.idTrattamento=?2 ");
      q.setParameter(1, sce);
      q.setParameter(2,tra);
      q.executeUpdate();*/
     
         
          entityManager.getTransaction().commit();
           //entityManager.close();
          
          
          Connessione.getInstance().chiudi();
         
     }
    
   
}
