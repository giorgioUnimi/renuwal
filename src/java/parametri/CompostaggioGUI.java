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
@ManagedBean(name = "compostaggioGUI")
@ViewScoped

public class CompostaggioGUI implements Serializable{
    
    
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
   
    
    private Double ss_cof;
    private Double ss_mis;
    private Double d_pal;
    private Double ss_comp;
    private Double d_comp;
    private Double giorni_ciclo;
    private Double sempre_attivo;
    private Double sv_comp;
    private Double p_cof;
    private Double k_cof;
    private Double investimento;
    private Double anni;
    private Double costo_ges;
    private Double manutenzione;
    private Double interesse;
    private Double ric_comp;
    private Double e_ss;
    private Double e_n;
    private Double frac_amm;
    private Double em_ch4;
     private Double em_nh3;
      private Double em_co2;
       private Double em_n2o;
        private Double em_n2;
    
    
   public CompostaggioGUI()
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
         classeparametri = new Parametridiprogetto("renuwal2",20,dettCuaa.getIdscenario());
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

             case 18:

                 this.costo_ges = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 176:

                 this.ss_cof = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 177:

                 this.ss_mis = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 173:

                 this.d_pal = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 178:

                 this.ss_comp = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 179:

                 this.d_comp = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 180:

                 this.giorni_ciclo = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 181:
                 if (temp.getValore().equals("no")) {
                     this.sempre_attivo =  new Double(0);
                 } else {
                     this.sempre_attivo = new Double(1);
                 }
                 //this.sempre_attivo = new Double(Double.parseDouble(temp.getValore()));
                 break;
             /*case 148:

                 this.potenza_termica_necessaria = new Double(Double.parseDouble(temp.getValore()));
                 break;*/

             case 182:

                 this.sv_comp = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 183:

                 this.p_cof = new Double(Double.parseDouble(temp.getValore()));
                 break;
             /**
              * l'efficienza viene calcoalata nel modulo in funzione di altri parametri
              */    
             /*case 150:

                 this.efficienza_strippaggio = new Double(Double.parseDouble(temp.getValore()));
                 break;*/
             case 184:

                 this.k_cof = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 143:

                 this.manutenzione = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 61:

                 this.interesse = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 185:

                 this.ric_comp = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 186:

                 this.e_ss = new Double(Double.parseDouble(temp.getValore()));
                 break;

             case 187:

                 this.e_n = new Double(Double.parseDouble(temp.getValore()));
                 break;   
                
             
             case 188:

                 this.frac_amm = new Double(Double.parseDouble(temp.getValore()));
                 break;        
            case 152:

                 this.em_ch4 = new Double(Double.parseDouble(temp.getValore()));
                 break;     
            
            case 153:

                 this.em_nh3 = new Double(Double.parseDouble(temp.getValore()));
                 break;     
            
           case 154:

                 this.em_co2 = new Double(Double.parseDouble(temp.getValore()));
                 break;     
           case 155:

                 this.em_n2= new Double(Double.parseDouble(temp.getValore()));
                 break;    
               
               
           case 156:

                 this.em_n2o = new Double(Double.parseDouble(temp.getValore()));
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
            
             case "18":
                temp = classeparametri.getParametrodiprogetto(0, 18,null,null, null);
                temp.setValore(String.valueOf(this.costo_ges.doubleValue()));
                break;         
             case "176":
                temp = classeparametri.getParametrodiprogetto(0, 176,null,null, null);
                temp.setValore(String.valueOf(this.ss_cof.doubleValue()));
                break;     
             case "177":
                temp = classeparametri.getParametrodiprogetto(0, 177,null,null, null);
                temp.setValore(String.valueOf(this.ss_mis.doubleValue()));
                break;     
             case "173":
                temp = classeparametri.getParametrodiprogetto(0, 173,null,null, null);
                temp.setValore(String.valueOf(this.d_pal.doubleValue()));
                break;     
              
             case "178":
                temp = classeparametri.getParametrodiprogetto(0, 178,null,null, null);
                temp.setValore(String.valueOf(this.ss_comp.doubleValue()));
                break;             
             case "179":
                temp = classeparametri.getParametrodiprogetto(0, 179,null,null, null);
                temp.setValore(String.valueOf(this.d_comp.doubleValue()));
                break; 
             case "180":
                temp = classeparametri.getParametrodiprogetto(0,180, null, null, null);
                temp.setValore(String.valueOf(this.giorni_ciclo.doubleValue()));
                break;     
             case "181":
                temp = classeparametri.getParametrodiprogetto(0, 181, null, null, null);
                temp.setValore(String.valueOf(this.sempre_attivo.doubleValue()));
                break;     
               
             case "182":
                temp = classeparametri.getParametrodiprogetto(0, 182,null,null, null);
                temp.setValore(String.valueOf(this.sv_comp.doubleValue()));
                break;        
             case "183":
                temp = classeparametri.getParametrodiprogetto(0, 183, null, null, null);
                temp.setValore(String.valueOf(this.p_cof.doubleValue()));
                break;     
             case "184":
                temp = classeparametri.getParametrodiprogetto(0, 184, null, null, null);
                temp.setValore(String.valueOf(this.k_cof.doubleValue()));
                break;  
              
             case "143":
                temp = classeparametri.getParametrodiprogetto(0, 143,null,null, null);
                temp.setValore(String.valueOf(this.manutenzione.doubleValue()));
                break;        
              
             case "61":
                temp = classeparametri.getParametrodiprogetto(0, 61,null,null, null);
                temp.setValore(String.valueOf(this.interesse.doubleValue()));
                break;       
                 
              case "185":
                temp = classeparametri.getParametrodiprogetto(0, 185,null,null, null);
                temp.setValore(String.valueOf(this.ric_comp.doubleValue()));
                break;           
                  
              case "186":
                temp = classeparametri.getParametrodiprogetto(0, 186,null,null, null);
                temp.setValore(String.valueOf(this.e_ss.doubleValue()));
                break;       
              
              case "187":
                temp = classeparametri.getParametrodiprogetto(0, 187,null,null, null);
                temp.setValore(String.valueOf(this.e_n.doubleValue()));
                break;
              
            case "188":
                temp = classeparametri.getParametrodiprogetto(0, 188,null,null, null);
                temp.setValore(String.valueOf(this.frac_amm.doubleValue()));
                break;  
                
                
            case "152":
                temp = classeparametri.getParametrodiprogetto(0, 152,null,null, null);
                temp.setValore(String.valueOf(this.em_ch4.doubleValue()));
                break;      
            
           case "153":
                temp = classeparametri.getParametrodiprogetto(0, 153,null,null, null);
                temp.setValore(String.valueOf(this.em_nh3.doubleValue()));
                break;         
          case "154":
                temp = classeparametri.getParametrodiprogetto(0, 154,null,null, null);
                temp.setValore(String.valueOf(this.em_co2.doubleValue()));
                break;          
         case "155":
                temp = classeparametri.getParametrodiprogetto(0, 155,null,null, null);
                temp.setValore(String.valueOf(this.em_n2.doubleValue()));
                break;         
         case "156":
                temp = classeparametri.getParametrodiprogetto(0, 156,null,null, null);
                temp.setValore(String.valueOf(this.em_n2o.doubleValue()));
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
//
//    /**
//     * @return the emissioni_co2
//     */
//    public double getEmissioni_co2() {
//        return emissioni_co2;
//    }
//
//    /**
//     * @param emissioni_co2 the emissioni_co2 to set
//     */
//    public void setEmissioni_co2(double emissioni_co2) {
//        this.emissioni_co2 = emissioni_co2;
//    }

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

//    /**
//     * @return the costo_energia
//     */
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
//     */
//    public Double getCosto_calce() {
//        return costo_calce;
//    }
//
//    /**
//     * @param costo_calce the costo_calce to set
//     */
//    public void setCosto_calce(Double costo_calce) {
//        this.costo_calce = costo_calce;
//    }
//
//    /**
//     * @return the costo_acido_solforico
//     */
//    public Double getCosto_acido_solforico() {
//        return costo_acido_solforico;
//    }
//
//    /**
//     * @param costo_acido_solforico the costo_acido_solforico to set
//     */
//    public void setCosto_acido_solforico(Double costo_acido_solforico) {
//        this.costo_acido_solforico = costo_acido_solforico;
//    }
//
//    /**
//     * @return the valore_solfato_ammonio
//     */
//    public Double getValore_solfato_ammonio() {
//        return valore_solfato_ammonio;
//    }
//
//    /**
//     * @param valore_solfato_ammonio the valore_solfato_ammonio to set
//     */
//    public void setValore_solfato_ammonio(Double valore_solfato_ammonio) {
//        this.valore_solfato_ammonio = valore_solfato_ammonio;
//    }
//
//    /**
//     * @return the usodicalce
//     */
//    public Double getUsodicalce() {
//        return usodicalce;
//    }
//
//    /**
//     * @param usodicalce the usodicalce to set
//     */
//    public void setUsodicalce(Double usodicalce) {
//        this.usodicalce = usodicalce;
//    }
//
//    /**
//     * @return the usodiacidosolforico
//     */
//    public Double getUsodiacidosolforico() {
//        return usodiacidosolforico;
//    }
//
//    /**
//     * @param usodiacidosolforico the usodiacidosolforico to set
//     */
//    public void setUsodiacidosolforico(Double usodiacidosolforico) {
//        this.usodiacidosolforico = usodiacidosolforico;
//    }
//
//    /**
//     * @return the energia_elettrica
//     */
//    public Double getEnergia_elettrica() {
//        return energia_elettrica;
//    }
//
//    /**
//     * @param energia_elettrica the energia_elettrica to set
//     */
//    public void setEnergia_elettrica(Double energia_elettrica) {
//        this.energia_elettrica = energia_elettrica;
//    }

    /**
     * @return the potenza_termica_necessaria
     */
  /*  public Double getPotenza_termica_necessaria() {
        return potenza_termica_necessaria;
    }*/

    /**
     * @param potenza_termica_necessaria the potenza_termica_necessaria to set
     */
    /*public void setPotenza_termica_necessaria(Double potenza_termica_necessaria) {
        this.potenza_termica_necessaria = potenza_termica_necessaria;
    }*/

    /**
     * @return the costo_energia_termica
     */
    /*public Double getCosto_energia_termica() {
        return costo_energia_termica;
    }*/

    /**
     * @param costo_energia_termica the costo_energia_termica to set
     */
    /*public void setCosto_energia_termica(Double costo_energia_termica) {
        this.costo_energia_termica = costo_energia_termica;
    }*/

    /**
     * @return the efficienza_strippaggio
     */
   /* public Double getEfficienza_strippaggio() {
        return efficienza_strippaggio;
    }

    /**
     * @param efficienza_strippaggio the efficienza_strippaggio to set
     */
   /* public void setEfficienza_strippaggio(Double efficienza_strippaggio) {
        this.efficienza_strippaggio = efficienza_strippaggio;
    }*/

   

    /**
     * @return the ricavo_solfato_ammonio
//     */
//    public Double getRicavo_solfato_ammonio() {
//        return ricavo_solfato_ammonio;
//    }
//
//    /**
//     * @param ricavo_solfato_ammonio the ricavo_solfato_ammonio to set
//     */
//    public void setRicavo_solfato_ammonio(Double ricavo_solfato_ammonio) {
//        this.ricavo_solfato_ammonio = ricavo_solfato_ammonio;
//    }
//
//    /**
//     * @return the efficienza_strippaggio
//     */
//    public Double getEfficienza_strippaggio() {
//        return efficienza_strippaggio;
//    }
//
//    /**
//     * @param efficienza_strippaggio the efficienza_strippaggio to set
//     */
//    public void setEfficienza_strippaggio(Double efficienza_strippaggio) {
//        this.efficienza_strippaggio = efficienza_strippaggio;
//    }
//
//    /**
//     * @return the efficienza_rimozione_volume
//     */
//    public Double getEfficienza_rimozione_volume() {
//        return efficienza_rimozione_volume;
//    }
//
//    /**
//     * @param efficienza_rimozione_volume the efficienza_rimozione_volume to set
//     */
//    public void setEfficienza_rimozione_volume(Double efficienza_rimozione_volume) {
//        this.efficienza_rimozione_volume = efficienza_rimozione_volume;
//    }

    /**
     * @return the ss_cof
     */
    public Double getSs_cof() {
        return ss_cof;
    }

    /**
     * @param ss_cof the ss_cof to set
     */
    public void setSs_cof(Double ss_cof) {
        if(ss_cof != null) {
            this.ss_cof = ss_cof;
        }
    }

    /**
     * @return the ss_mis
     */
    public Double getSs_mis() {
        return ss_mis;
    }

    /**
     * @param ss_mis the ss_mis to set
     */
    public void setSs_mis(Double ss_mis) {
        if(ss_mis != null) {
            this.ss_mis = ss_mis;
        }
    }

    /**
     * @return the d_pal
     */
    public Double getD_pal() {
        return d_pal;
    }

    /**
     * @param d_pal the d_pal to set
     */
    public void setD_pal(Double d_pal) {
        if(d_pal != null) {
            this.d_pal = d_pal;
        }
    }

    /**
     * @return the ss_comp
     */
    public Double getSs_comp() {
        return ss_comp;
    }

    /**
     * @param ss_comp the ss_comp to set
     */
    public void setSs_comp(Double ss_comp) {
        if(ss_comp != null) {
            this.ss_comp = ss_comp;
        }
    }

    /**
     * @return the d_comp
     */
    public Double getD_comp() {
        return d_comp;
    }

    /**
     * @param d_comp the d_comp to set
     */
    public void setD_comp(Double d_comp) {
        if(d_comp != null) {
            this.d_comp = d_comp;
        }
    }

    /**
     * @return the giorni_ciclo
     */
    public Double getGiorni_ciclo() {
        return giorni_ciclo;
    }

    /**
     * @param giorni_ciclo the giorni_ciclo to set
     */
    public void setGiorni_ciclo(Double giorni_ciclo) {
        if(giorni_ciclo != null) {
            this.giorni_ciclo = giorni_ciclo;
        }
    }

    /**
     * @return the sempre_attivo
     */
    public Double getSempre_attivo() {
        return sempre_attivo;
    }

    /**
     * @param sempre_attivo the sempre_attivo to set
     */
    public void setSempre_attivo(Double sempre_attivo) {
        if(sempre_attivo != null) {
            this.sempre_attivo = sempre_attivo;
        }
    }

    /**
     * @return the sv_comp
     */
    public Double getSv_comp() {
        return sv_comp;
    }

    /**
     * @param sv_comp the sv_comp to set
     */
    public void setSv_comp(Double sv_comp) {
        if(sv_comp != null) {
            this.sv_comp = sv_comp;
        }
    }

    /**
     * @return the p_cof
     */
    public Double getP_cof() {
        return p_cof;
    }

    /**
     * @param p_cof the p_cof to set
     */
    public void setP_cof(Double p_cof) {
        if(p_cof != null) {
            this.p_cof = p_cof;
        }
    }

    /**
     * @return the k_cof
     */
    public Double getK_cof() {
        return k_cof;
    }

    /**
     * @param k_cof the k_cof to set
     */
    public void setK_cof(Double k_cof) {
        if(k_cof != null) {
            this.k_cof = k_cof;
        }
    }

    /**
     * @return the costo_ges
     */
    public Double getCosto_ges() {
        return costo_ges;
    }

    /**
     * @param costo_ges the costo_ges to set
     */
    public void setCosto_ges(Double costo_ges) {
        if(costo_ges != null) {
            this.costo_ges = costo_ges;
        }
    }

    /**
     * @return the interesse
     */
    public Double getInteresse() {
        return interesse;
    }

    /**
     * @param interesse the interesse to set
     */
    public void setInteresse(Double interesse) {
        if(interesse != null) {
            this.interesse = interesse;
        }
    }

    /**
     * @return the ric_comp
     */
    public Double getRic_comp() {
        return ric_comp;
    }

    /**
     * @param ric_comp the ric_comp to set
     */
    public void setRic_comp(Double ric_comp) {
        if(ric_comp != null) {
            this.ric_comp = ric_comp;
        }
    }

    /**
     * @return the e_ss
     */
    public Double getE_ss() {
        return e_ss;
    }

    /**
     * @param e_ss the e_ss to set
     */
    public void setE_ss(Double e_ss) {
        if(e_ss !=null) {
            this.e_ss = e_ss;
        }
    }

    /**
     * @return the e_n
     */
    public Double getE_n() {
        return e_n;
    }

    /**
     * @param e_n the e_n to set
     */
    public void setE_n(Double e_n) {
        if(e_n != null) {
            this.e_n = e_n;
        }
    }

    /**
     * @return the frac_amm
     */
    public Double getFrac_amm() {
        return frac_amm;
    }

    /**
     * @param frac_amm the frac_amm to set
     */
    public void setFrac_amm(Double frac_amm) {
        if(frac_amm !=null) {
            this.frac_amm = frac_amm;
        }
    }

    /**
     * @return the em_ch4
     */
    public Double getEm_ch4() {
        return em_ch4;
    }

    /**
     * @param em_ch4 the em_ch4 to set
     */
    public void setEm_ch4(Double em_ch4) {
        if(em_ch4 !=null) {
            this.em_ch4 = em_ch4;
        }
    }

    /**
     * @return the em_nh3
     */
    public Double getEm_nh3() {
        return em_nh3;
    }

    /**
     * @param em_nh3 the em_nh3 to set
     */
    public void setEm_nh3(Double em_nh3) {
        if(em_nh3 !=null) {
            this.em_nh3 = em_nh3;
        }
    }

    /**
     * @return the em_co2
     */
    public Double getEm_co2() {
        return em_co2;
    }

    /**
     * @param em_co2 the em_co2 to set
     */
    public void setEm_co2(Double em_co2) {
        if(em_co2 !=null) {
            this.em_co2 = em_co2;
        }
    }

    /**
     * @return the em_n2o
     */
    public Double getEm_n2o() {
        return em_n2o;
    }

    /**
     * @param em_n2o the em_n2o to set
     */
    public void setEm_n2o(Double em_n2o) {
        if(em_n2o !=null) {
            this.em_n2o = em_n2o;
        }
    }

//    /**
//     * @return the no
//     */
//    public Double getNo() {
//        return no;
//    }
//
//    /**
//     * @param no the no to set
//     */
//    public void setNo(Double no) {
//        this.no = no;
//    }
//
//    /**
//     * @return the em_no
//     */
//    public Double getEm_no() {
//        return em_no;
//    }
//
//    /**
//     * @param em_no the em_no to set
//     */
//    public void setEm_no(Double em_no) {
//        this.em_no = em_no;
//    }

    /**
     * @return the em_n2
     */
    public Double getEm_n2() {
        return em_n2;
    }

    /**
     * @param em_n2 the em_n2 to set
     */
    public void setEm_n2(Double em_n2) {
        if(em_n2 !=null) {
            this.em_n2 = em_n2;
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
