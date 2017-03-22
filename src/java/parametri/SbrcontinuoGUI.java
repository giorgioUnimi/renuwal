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
@ManagedBean(name = "sbrcontinuoGUI")
@ViewScoped

public class SbrcontinuoGUI implements Serializable{
    
    
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
    private Double eff_sep_st;
    private Double eff_sep_vol;
    private Double eff_sep_tkn;
    private Double eff_sep_tan;
    private Double eff_sep_sv;
    private Double eff_sep_p;
    private Double eff_sep_k;
    
    private Double tasso_nitrificazione;
    private Double tasso_denitrificazione;
    private Double n_max_uscita;
    private Double rendimento_min;
    private Double rendimento_max;
    private Double tempo_attivo_totale;
    private Double emissioni_nh3;
    private Double emissioni_n2o;
    private Double emissioni_ch4;
    private Double emissioni_co2;
    private Double fattore_trasferimento_o2;
    private Double costo_energia;
    private Double costo_investimento;
    private Double costo_gestione_unitaria;
    private Double tasso_interesse;
    private Double durata_investimento;
    
    
    
   public SbrcontinuoGUI()
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
         classeparametri = new Parametridiprogetto("renuwal2",13,dettCuaa.getIdscenario());
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
             case 82:
                 this.eff_sep_st = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 79:
                 this.eff_sep_vol = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 80:
                 this.eff_sep_tkn = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 81:
                 this.eff_sep_tan = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 83:
                 this.eff_sep_sv = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 84:
                 this.eff_sep_p = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 85:
                 this.eff_sep_k = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 86: 
                 this.tasso_nitrificazione = new Double(Double.parseDouble(temp.getValore()));
                break;
            case 87:
                 this.tasso_denitrificazione = new Double(Double.parseDouble(temp.getValore()));
                break;
            case 88:
               
                this.n_max_uscita = new Double(Double.parseDouble(temp.getValore()));
                break;
            case 89:
             
                this.rendimento_min = new Double(Double.parseDouble(temp.getValore()));
                break;
            case 90:
                
                this.rendimento_max =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 91:
                 
                this.tempo_attivo_totale =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 38:
                
                    this.emissioni_nh3 =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 92:
                  
                    this.emissioni_n2o =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 93:
                 
                    this.emissioni_ch4 =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 94:    
             
                this.emissioni_co2 =new Double(Double.parseDouble(temp.getValore()));
                break;    
            case 95:
                 
                this.fattore_trasferimento_o2 =new Double(Double.parseDouble(temp.getValore()));
                break;
            case 19:
                 this.costo_energia= new Double(Double.parseDouble(temp.getValore()));
                 break;    
             case 96:
                 this.costo_investimento = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 117:
                 this.costo_gestione_unitaria = new Double(Double.parseDouble(temp.getValore()));
                 break;     
             case 76:
                 this.tasso_interesse = new Double(Double.parseDouble(temp.getValore()));
                 break;     
             case 52:
                 this.durata_investimento = new Double(Double.parseDouble(temp.getValore()));
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
            case "82":
                temp = classeparametri.getParametrodiprogetto(0,82, null, null, null);
                temp.setValore(String.valueOf(this.eff_sep_st.doubleValue()));
                break;
            case "79":
                temp = classeparametri.getParametrodiprogetto(0,79, null, null, null);
                //System.out.println("");
                temp.setValore(String.valueOf(this.eff_sep_vol.doubleValue()));
                break;
            case "80":
                temp = classeparametri.getParametrodiprogetto(0, 80, null, null, null);
                temp.setValore(String.valueOf(this.eff_sep_tkn.doubleValue()));
                break;
            case "81":
                temp = classeparametri.getParametrodiprogetto(0, 81, null, null, null);
                temp.setValore(String.valueOf(this.eff_sep_tan.doubleValue()));
                break;
            case "83":
                temp = classeparametri.getParametrodiprogetto(0, 83, null, null, null);
                temp.setValore(String.valueOf(this.eff_sep_sv.doubleValue()));
                break;
            case "84":
                temp = classeparametri.getParametrodiprogetto(0, 84, null, null, null);
                temp.setValore(String.valueOf(this.eff_sep_p.doubleValue()));
                break;
            case "85":
                temp = classeparametri.getParametrodiprogetto(0, 85, null,null, null);
                temp.setValore(String.valueOf(this.eff_sep_k.doubleValue()));
                break;
            case "86": 
                temp = classeparametri.getParametrodiprogetto(0, 86,null,null, null);
                temp.setValore(String.valueOf(this.tasso_nitrificazione.doubleValue()));
                break;
            case "87":
               temp = classeparametri.getParametrodiprogetto(0, 87,null,null, null);
                temp.setValore(String.valueOf(this.tasso_denitrificazione.doubleValue()));
                break;
            case "88":
                temp = classeparametri.getParametrodiprogetto(0, 88,null,null, null);
                temp.setValore(String.valueOf(this.n_max_uscita.doubleValue()));
                break;
            case "89":
                temp = classeparametri.getParametrodiprogetto(0, 89,null,null, null);
                temp.setValore(String.valueOf(this.rendimento_min.doubleValue()));
                break;
            case "90":
              temp = classeparametri.getParametrodiprogetto(0, 90,null,null, null);
                temp.setValore(String.valueOf(this.rendimento_max.doubleValue()));
                break;
            case "91":
                temp = classeparametri.getParametrodiprogetto(0, 91,null,null, null);
                temp.setValore(String.valueOf(this.tempo_attivo_totale.doubleValue()));
                break;
             case "38":
                temp = classeparametri.getParametrodiprogetto(0, 38,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_nh3.doubleValue()));
                break;         
             case "92":
                temp = classeparametri.getParametrodiprogetto(0, 92,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_n2o.doubleValue()));
                break;     
             case "93":
                temp = classeparametri.getParametrodiprogetto(0, 93,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_ch4.doubleValue()));
                break;     
             case "94":
                temp = classeparametri.getParametrodiprogetto(0, 94,null,null, null);
                temp.setValore(String.valueOf(this.emissioni_co2.doubleValue()));
                break;     
             case "95":
                temp = classeparametri.getParametrodiprogetto(0, 95,null,null, null);
                temp.setValore(String.valueOf(this.fattore_trasferimento_o2.doubleValue()));
                break; 
             case "19":
                temp = classeparametri.getParametrodiprogetto(0,19, null, null, null);
                temp.setValore(String.valueOf(this.costo_energia.doubleValue()));
                break;     
             case "96":
                temp = classeparametri.getParametrodiprogetto(0, 96, null, null, null);
                temp.setValore(String.valueOf(this.costo_investimento.doubleValue()));
                break;     
             case "117":
                temp = classeparametri.getParametrodiprogetto(0, 117, null, null, null);
                temp.setValore(String.valueOf(this.costo_gestione_unitaria.doubleValue()));
                break;     
             case "76":
                temp = classeparametri.getParametrodiprogetto(0, 76, null, null, null);
                temp.setValore(String.valueOf(this.tasso_interesse.doubleValue()));
                break;     
             case "52":
                temp = classeparametri.getParametrodiprogetto(0, 52, null, null, null);
                temp.setValore(String.valueOf(this.durata_investimento.doubleValue()));
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

    /**
     * @return the eff_sep_st
     */
    public Double getEff_sep_st() {
        return eff_sep_st;
    }

    /**
     * @param eff_sep_st the eff_sep_st to set
     */
    public void setEff_sep_st(Double eff_sep_st) {
        if(eff_sep_st != null) {
            this.eff_sep_st = eff_sep_st;
        }
    }

    /**
     * @return the eff_sep_vol
     */
    public Double getEff_sep_vol() {
        return eff_sep_vol;
    }

    /**
     * @param eff_sep_vol the eff_sep_vol to set
     */
    public void setEff_sep_vol(Double eff_sep_vol) {
        if(eff_sep_vol != null) {
            this.eff_sep_vol = eff_sep_vol;
        }
    }

    /**
     * @return the eff_sep_tkn
     */
    public Double getEff_sep_tkn() {
        return eff_sep_tkn;
    }

    /**
     * @param eff_sep_tkn the eff_sep_tkn to set
     */
    public void setEff_sep_tkn(Double eff_sep_tkn) {
        if(eff_sep_tkn != null) {
            this.eff_sep_tkn = eff_sep_tkn;
        }
    }

    /**
     * @return the eff_sep_tan
     */
    public Double getEff_sep_tan() {
        return eff_sep_tan;
    }

    /**
     * @param eff_sep_tan the eff_sep_tan to set
     */
    public void setEff_sep_tan(Double eff_sep_tan) {
        if(eff_sep_tan != null) {
            this.eff_sep_tan = eff_sep_tan;
        }
    }

    /**
     * @return the eff_sep_sv
     */
    public Double getEff_sep_sv() {
        return eff_sep_sv;
    }

    /**
     * @param eff_sep_sv the eff_sep_sv to set
     */
    public void setEff_sep_sv(Double eff_sep_sv) {
        if(eff_sep_sv != null) {
            this.eff_sep_sv = eff_sep_sv;
        }
    }

    /**
     * @return the eff_sep_p
     */
    public Double getEff_sep_p() {
        return eff_sep_p;
    }

    /**
     * @param eff_sep_p the eff_sep_p to set
     */
    public void setEff_sep_p(Double eff_sep_p) {
        if(eff_sep_p != null) {
            this.eff_sep_p = eff_sep_p;
        }
    }

    /**
     * @return the eff_sep_k
     */
    public Double getEff_sep_k() {
        return eff_sep_k;
    }

    /**
     * @param eff_sep_k the eff_sep_k to set
     */
    public void setEff_sep_k(Double eff_sep_k) {
        if(eff_sep_k != null) {
            this.eff_sep_k = eff_sep_k;
        }
    }

    /**
     * @return the tasso_nitrificazione
     */
    public Double getTasso_nitrificazione() {
        return tasso_nitrificazione;
    }

    /**
     * @param tasso_nitrificazione the tasso_nitrificazione to set
     */
    public void setTasso_nitrificazione(Double tasso_nitrificazione) {
        if(tasso_nitrificazione != null) {
            this.tasso_nitrificazione = tasso_nitrificazione;
        }
    }

    /**
     * @return the tasso_denitrificazione
     */
    public Double getTasso_denitrificazione() {
        return tasso_denitrificazione;
    }

    /**
     * @param tasso_denitrificazione the tasso_denitrificazione to set
     */
    public void setTasso_denitrificazione(Double tasso_denitrificazione) {
        if(tasso_denitrificazione != null) {
            this.tasso_denitrificazione = tasso_denitrificazione;
        }
    }

    /**
     * @return the n_max_uscita
     */
    public Double getN_max_uscita() {
        return n_max_uscita;
    }

    /**
     * @param n_max_uscita the n_max_uscita to set
     */
    public void setN_max_uscita(Double n_max_uscita) {
        if(n_max_uscita != null) {
            this.n_max_uscita = n_max_uscita;
        }
    }

    /**
     * @return the rendimento_min
     */
    public Double getRendimento_min() {
        return rendimento_min;
    }

    /**
     * @param rendimento_min the rendimento_min to set
     */
    public void setRendimento_min(Double rendimento_min) {
        if(rendimento_min != null) {
            this.rendimento_min = rendimento_min;
        }
    }

    /**
     * @return the rendimento_max
     */
    public Double getRendimento_max() {
        return rendimento_max;
    }

    /**
     * @param rendimento_max the rendimento_max to set
     */
    public void setRendimento_max(Double rendimento_max) {
        if(rendimento_max != null) {
            this.rendimento_max = rendimento_max;
        }
    }

    /**
     * @return the tempo_attivo_totale
     */
    public Double getTempo_attivo_totale() {
        return tempo_attivo_totale;
    }

    /**
     * @param tempo_attivo_totale the tempo_attivo_totale to set
     */
    public void setTempo_attivo_totale(Double tempo_attivo_totale) {
        if(tempo_attivo_totale != null) {
            this.tempo_attivo_totale = tempo_attivo_totale;
        }
    }

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
    public Double getFattore_trasferimento_o2() {
        return fattore_trasferimento_o2;
    }

    /**
     * @param fattore_trasferimento_o2 the fattore_trasferimento_o2 to set
     */
    public void setFattore_trasferimento_o2(Double fattore_trasferimento_o2) {
        if(fattore_trasferimento_o2 != null) {
            this.fattore_trasferimento_o2 = fattore_trasferimento_o2;
        }
    }

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
     */
    public Double getCosto_investimento() {
        return costo_investimento;
    }

    /**
     * @param costo_investimento the costo_investimento to set
     */
    public void setCosto_investimento(Double costo_investimento) {
        if(costo_investimento != null) {
            this.costo_investimento = costo_investimento;
        }
    }

    /**
     * @return the costo_gestione_unitaria
     */
    public Double getCosto_gestione_unitaria() {
        return costo_gestione_unitaria;
    }

    /**
     * @param costo_gestione_unitaria the costo_gestione_unitaria to set
     */
    public void setCosto_gestione_unitaria(Double costo_gestione_unitaria) {
        if(costo_gestione_unitaria != null) {
            this.costo_gestione_unitaria = costo_gestione_unitaria;
        }
    }

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
    public Double getDurata_investimento() {
        return durata_investimento;
    }

    /**
     * @param durata_investimento the durata_investimento to set
     */
    public void setDurata_investimento(Double durata_investimento) {
        if(durata_investimento != null) {
            this.durata_investimento = durata_investimento;
        }
    }
          
        
    
}
