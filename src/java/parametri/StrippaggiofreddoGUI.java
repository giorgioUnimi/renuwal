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
@ManagedBean(name = "strippaggiofreddoGUI")
@ViewScoped

public class StrippaggiofreddoGUI implements Serializable{
    
    
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
   
    
    private Double investimento;
    private Double anni;
    private Double costo_energia;
    private Double costo_calce;
    private Double costo_acido_solforico;
    private Double manutenzione;
    private Double valore_solfato_ammonio;
    private Double usodicalce;
    private Double usodiacidosolforico;
    private Double energia_elettrica;
    private Double efficienza_rimozione_volume;
    //private Double costo_energia_termica;
    private Double tasso_interesse;
    private Double efficienza_strippaggio;
    private Double ricavo_solfato_ammonio;
    private Double emissioni_ch4;
    private Double emissioni_nh3;
    private Double emissioni_co2;
    private Double emissioni_n2;
    private Double emissioni_n2o;
    
    
   public StrippaggiofreddoGUI()
   {
       
      inizializzaParametri();
            
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
         classeparametri = new Parametridiprogetto("renuwal2",16,dettCuaa.getIdscenario());
         List<db.ParametridiprogettoS> pa= classeparametri.getlistaparametri();
      //List<db.ParametridiprogettoS> pa= (List<db.ParametridiprogettoS>)q.getResultList();
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
             case 16:

                 this.investimento = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 17:

                 this.anni = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 19:

                 this.costo_energia = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 141:

                 this.costo_calce = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 142:

                 this.costo_acido_solforico = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 143:

                 this.manutenzione = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 144:

                 this.valore_solfato_ammonio = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 145:

                 this.usodicalce = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 146:

                 this.usodiacidosolforico = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 147:

                 this.energia_elettrica = new Double(Double.parseDouble(temp.getValore()));
                 break;
             /*case 148:

                 this.potenza_termica_necessaria = new Double(Double.parseDouble(temp.getValore()));
                 break;*/

             case 157:

                 this.efficienza_rimozione_volume = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 61:

                 this.tasso_interesse = new Double(Double.parseDouble(temp.getValore()));
                 break;
             /**
              * l'efficienza viene calcoalata nel modulo in funzione di altri parametri
              */    
             /*case 150:

                 this.efficienza_strippaggio = new Double(Double.parseDouble(temp.getValore()));
                 break;*/
             case 151:

                 this.ricavo_solfato_ammonio = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 153:

                 this.emissioni_nh3 = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 156:

                 this.emissioni_n2o = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 152:

                 this.emissioni_ch4 = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 154:

                 this.emissioni_co2 = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 155:

                 this.emissioni_n2 = new Double(Double.parseDouble(temp.getValore()));
                 break;   
                
                
                
           
                
         }
     }
      
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
       
      /* int tt = Integer.parseInt(action);
       
       if(tt < 6 && tt > 0)
       {
           System.out.println("codice action " + tt + " tipobiomassa " + this.tipobiomassa);
           return ;
       
       }*/
       
       
        switch (action) {
           
              
             case "16":
                temp = classeparametri.getParametrodiprogetto(0, 16,null,null, null);
                temp.setValore(String.valueOf(this.investimento.doubleValue()));
                break;        
              
             case "17":
                temp = classeparametri.getParametrodiprogetto(0, 17,null,null, null);
                temp.setValore(String.valueOf(this.anni.doubleValue()));
                break;        
            
             case "19":
                temp = classeparametri.getParametrodiprogetto(0, 19,null,null, null);
                temp.setValore(String.valueOf(this.costo_energia.doubleValue()));
                break;         
             case "141":
                temp = classeparametri.getParametrodiprogetto(0, 141,null,null, null);
                temp.setValore(String.valueOf(this.costo_calce.doubleValue()));
                break;     
             case "143":
                temp = classeparametri.getParametrodiprogetto(0, 143,null,null, null);
                temp.setValore(String.valueOf(this.manutenzione.doubleValue()));
                break;     
             case "144":
                temp = classeparametri.getParametrodiprogetto(0, 144,null,null, null);
                temp.setValore(String.valueOf(this.valore_solfato_ammonio.doubleValue()));
                break;     
              
             case "145":
                temp = classeparametri.getParametrodiprogetto(0, 145,null,null, null);
                temp.setValore(String.valueOf(this.usodicalce.doubleValue()));
                break;             
             case "146":
                temp = classeparametri.getParametrodiprogetto(0, 146,null,null, null);
                temp.setValore(String.valueOf(this.usodiacidosolforico.doubleValue()));
                break; 
             case "147":
                temp = classeparametri.getParametrodiprogetto(0,147, null, null, null);
                temp.setValore(String.valueOf(this.energia_elettrica.doubleValue()));
                break;     
             /*case "148":
                temp = classeparametri.getParametrodiprogetto(0, 148, null, null, null);
                temp.setValore(String.valueOf(this.potenza_termica_necessaria));
                break;     
               */
             case "157":
                temp = classeparametri.getParametrodiprogetto(0, 157,null,null, null);
                temp.setValore(String.valueOf(this.efficienza_rimozione_volume.doubleValue()));
                break;        
             case "61":
                temp = classeparametri.getParametrodiprogetto(0, 61, null, null, null);
                temp.setValore(String.valueOf(this.tasso_interesse.doubleValue()));
                break;     
             /*case "150":
                temp = classeparametri.getParametrodiprogetto(0, 150, null, null, null);
                temp.setValore(String.valueOf(this.efficienza_strippaggio));
                break;  */   
              
             case "151":
                temp = classeparametri.getParametrodiprogetto(0, 151,null,null, null);
                temp.setValore(String.valueOf(this.ricavo_solfato_ammonio.doubleValue()));
                break;        
              
             case "152":
                temp = classeparametri.getParametrodiprogetto(0, 152,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_ch4.doubleValue()));
                break;       
                 
              case "153":
                temp = classeparametri.getParametrodiprogetto(0, 153,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_nh3.doubleValue()));
                break;           
                  
              case "154":
                temp = classeparametri.getParametrodiprogetto(0, 154,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_co2.doubleValue()));
                break;       
              
              case "155":
                temp = classeparametri.getParametrodiprogetto(0, 155,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_n2.doubleValue()));
                break;
              
            case "156":
                temp = classeparametri.getParametrodiprogetto(0, 156,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_n2o.doubleValue()));
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

    /**
     * @return the emissioni_nh3
     */
    public Double getEmissioni_nh3() {
        return emissioni_nh3;
    }

    /**
     * @param emissioni_nh3 the emissioni_nh3 to set
     */
    public void setEmissioni_nh3(Double emissioni_nh3) {
        if(emissioni_nh3 != null) {
            this.emissioni_nh3 = emissioni_nh3;
        }
    }

    /**
     * @return the emissioni_n2o
     */
    public Double getEmissioni_n2o() {
        return emissioni_n2o;
    }

    /**
     * @param emissioni_n2o the emissioni_n2o to set
     */
    public void setEmissioni_n2o(Double emissioni_n2o) {
        if(emissioni_n2o != null) {
            this.emissioni_n2o = emissioni_n2o;
        }
    }

    /**
     * @return the emissioni_ch4
     */
    public Double getEmissioni_ch4() {
        return emissioni_ch4;
    }

    /**
     * @param emissioni_ch4 the emissioni_ch4 to set
     */
    public void setEmissioni_ch4(Double emissioni_ch4) {
        if(emissioni_ch4 != null) {
            this.emissioni_ch4 = emissioni_ch4;
        }
    }

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
    public Double getCosto_energia() {
        return costo_energia;
    }

    /**
     * @param costo_energia the costo_energia to set
     */
    public void setCosto_energia(Double costo_energia) {
        if(costo_energia != null) {
            this.costo_energia = costo_energia;
        }
    }

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
    public Double getTasso_interesse() {
        return tasso_interesse;
    }

    /**
     * @param tasso_interesse the tasso_interesse to set
     */
    public void setTasso_interesse(Double tasso_interesse) {
        if(tasso_interesse != null) {
            this.tasso_interesse = tasso_interesse;
        }
    }

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
    public Double getEmissioni_n2() {
        return emissioni_n2;
    }

    /**
     * @param emissioni_n2 the emissioni_n2 to set
     */
    public void setEmissioni_n2(Double emissioni_n2) {
        if(emissioni_n2 != null) {
            this.emissioni_n2 = emissioni_n2;
        }
    }

    /**
     * @return the manutenzione
     */
    public Double getManutenzione() {
        return manutenzione;
    }

    /**
     * @param manutenzione the manutenzione to set
     */
    public void setManutenzione(Double manutenzione) {
        if(manutenzione != null) {
            this.manutenzione = manutenzione;
        }
    }

    /**
     * @return the investimento
     */
    public Double getInvestimento() {
        return investimento;
    }

    /**
     * @param investimento the investimento to set
     */
    public void setInvestimento(Double investimento) {
        if(investimento != null) {
            this.investimento = investimento;
        }
    }

    /**
     * @return the anni
     */
    public Double getAnni() {
        return anni;
    }

    /**
     * @param anni the anni to set
     */
    public void setAnni(Double anni) {
        if(anni != null) {
            this.anni = anni;
        }
    }

    /**
     * @return the costo_calce
     */
    public Double getCosto_calce() {
        return costo_calce;
    }

    /**
     * @param costo_calce the costo_calce to set
     */
    public void setCosto_calce(Double costo_calce) {
        if(costo_calce != null) {
            this.costo_calce = costo_calce;
        }
    }

    /**
     * @return the costo_acido_solforico
     */
    public Double getCosto_acido_solforico() {
        return costo_acido_solforico;
    }

    /**
     * @param costo_acido_solforico the costo_acido_solforico to set
     */
    public void setCosto_acido_solforico(Double costo_acido_solforico) {
        if(costo_acido_solforico != null) {
            this.costo_acido_solforico = costo_acido_solforico;
        }
    }

    /**
     * @return the valore_solfato_ammonio
     */
    public Double getValore_solfato_ammonio() {
        return valore_solfato_ammonio;
    }

    /**
     * @param valore_solfato_ammonio the valore_solfato_ammonio to set
     */
    public void setValore_solfato_ammonio(Double valore_solfato_ammonio) {
        if(valore_solfato_ammonio != null) {
            this.valore_solfato_ammonio = valore_solfato_ammonio;
        }
    }

    /**
     * @return the usodicalce
     */
    public Double getUsodicalce() {
        return usodicalce;
    }

    /**
     * @param usodicalce the usodicalce to set
     */
    public void setUsodicalce(Double usodicalce) {
        if(usodicalce != null) {
            this.usodicalce = usodicalce;
        }
    }

    /**
     * @return the usodiacidosolforico
     */
    public Double getUsodiacidosolforico() {
        return usodiacidosolforico;
    }

    /**
     * @param usodiacidosolforico the usodiacidosolforico to set
     */
    public void setUsodiacidosolforico(Double usodiacidosolforico) {
        if(usodiacidosolforico != null) {
            this.usodiacidosolforico = usodiacidosolforico;
        }
    }

    /**
     * @return the energia_elettrica
     */
    public Double getEnergia_elettrica() {
        return energia_elettrica;
    }

    /**
     * @param energia_elettrica the energia_elettrica to set
     */
    public void setEnergia_elettrica(Double energia_elettrica) {
        if(energia_elettrica != null) {
            this.energia_elettrica = energia_elettrica;
        }
    }

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
    public Double getRicavo_solfato_ammonio() {
        return ricavo_solfato_ammonio;
    }

    /**
     * @param ricavo_solfato_ammonio the ricavo_solfato_ammonio to set
     */
    public void setRicavo_solfato_ammonio(Double ricavo_solfato_ammonio) {
        if(ricavo_solfato_ammonio != null) {
            this.ricavo_solfato_ammonio = ricavo_solfato_ammonio;
        }
    }

    /**
     * @return the efficienza_strippaggio
     */
    public Double getEfficienza_strippaggio() {
        return efficienza_strippaggio;
    }

    /**
     * @param efficienza_strippaggio the efficienza_strippaggio to set
     */
    public void setEfficienza_strippaggio(Double efficienza_strippaggio) {
        if(efficienza_strippaggio != null) {
            this.efficienza_strippaggio = efficienza_strippaggio;
        }
    }

    /**
     * @return the efficienza_rimozione_volume
     */
    public Double getEfficienza_rimozione_volume() {
        return efficienza_rimozione_volume;
    }

    /**
     * @param efficienza_rimozione_volume the efficienza_rimozione_volume to set
     */
    public void setEfficienza_rimozione_volume(Double efficienza_rimozione_volume) {
        if(efficienza_rimozione_volume != null) {
            this.efficienza_rimozione_volume = efficienza_rimozione_volume;
        }
    }

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
