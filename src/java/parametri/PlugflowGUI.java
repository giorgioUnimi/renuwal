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
@ManagedBean(name = "plugflowGUI")
@ViewScoped

public class PlugflowGUI implements Serializable{
    
    
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
    private String tipobiomassa ="1";
    /**
     * parametri biomasse
     */
    //private double rapportobiomassaliquame = 0.1, rendimentocogeneratore = 0.4 ,  biogasemesso = 0.2 , biogasutilizzato =0.9, energiaautoconsumo =0.1, emissioninh3 = 0.02;
    //private int orefunzionamento = 7884 ;
   // private double produzionebiogas,metanonelbiogas,densitagas,prezzobiomassa,densitabiomassa;
    /**
     * caratteristiche biomassa
     */
    //private double st,sv,azoto,azotoammoniacale,potassio,fosforo;
   // private String tipobiomassa1 = "5" ;
    /**
     * parametri economici
     */
    //private double c_inv = 40027,e_inv = 0.6667,costogestione = 0.05,prezzoenergia = 0.24,durata_investimento = 15,tasso_interesse=0.05;
    
    
    
    /**
     * parametri biomasse
     */
    private Double rapportobiomassaliquame = 0d,rendimentoimpianto=0.7d, rendimentocogeneratore = 0.4 ,  biogasemesso = 0.2 , biogasutilizzato =0.9, energiaautoconsumo =0.1, emissioninh3 = 0.02;
    private Double orefunzionamento = 7884d ;
    private Double produzionebiogas,metanonelbiogas,densitagas,prezzobiomassa,densitabiomassa;
    /**
     * caratteristiche biomassa
     */
    private Double st,sv,azoto,azotoammoniacale,potassio,fosforo;
    private String tipobiomassa1 = "5" ;
    /**
     * parametri economici
     */
    private Double c_inv = 40027d,e_inv = 0.6667,costogestione = 0.05,prezzoenergia = 0.24,durata_investimento = 15d,tasso_interesse=0.05;
    
    
   public PlugflowGUI()
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
         classeparametri = new Parametridiprogetto("renuwal2",17,dettCuaa.getIdscenario());
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
             case 33:
                 this.rapportobiomassaliquame = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 34:
                 this.rendimentocogeneratore = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 35:
                 this.orefunzionamento = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 98:
                 this.biogasemesso = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 36:
                 this.biogasutilizzato = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 37:
                 this.energiaautoconsumo = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 38:
                 this.emissioninh3 = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 74: 
                 if(temp.getContenutoattributo().equals("1")) {
             this.produzionebiogas = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 69:
                if(temp.getContenutoattributo().equals("1")) {
             this.metanonelbiogas = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 73:
                if(temp.getContenutoattributo().equals("1")) {
             this.densitagas = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 75:
               if(temp.getContenutoattributo().equals("1")) {
             this.prezzobiomassa = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 99:
                if(temp.getContenutoattributo().equals("1")) {
             this.densitabiomassa = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 43:
                  if(temp.getContenutoattributo().equals("5")) {
             this.st =new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 44:
                  if(temp.getContenutoattributo().equals("5")) {
             this.sv =new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 45:
                  if(temp.getContenutoattributo().equals("5")) {
             this.azoto = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 46:
                  if(temp.getContenutoattributo().equals("5")) {
             this.azotoammoniacale = new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 47:    
              if(temp.getContenutoattributo().equals("5")) {
             this.potassio =new Double(Double.parseDouble(temp.getValore()));
         }
                break;    
            case 48:
                  if(temp.getContenutoattributo().equals("5")) {
             this.fosforo =new Double(Double.parseDouble(temp.getValore()));
         }
                break;
            case 49:
                 this.c_inv= new Double(Double.parseDouble(temp.getValore()));
                 break;    
             case 50:
                 this.e_inv = new Double(Double.parseDouble(temp.getValore()));
                 break;
             case 18:
                 this.costogestione = new Double(Double.parseDouble(temp.getValore()));
                 break;     
             case 19:
                 this.prezzoenergia = new Double(Double.parseDouble(temp.getValore()));
                 break;     
             case 52:
                 this.durata_investimento = new Double(Double.parseDouble(temp.getValore()));
                 break;     
              case 61:
                 this.tasso_interesse = new Double(Double.parseDouble(temp.getValore()));
                 break; 
             case 160:
                 this.rendimentoimpianto = new Double(Double.parseDouble(temp.getValore()));
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
       
       int tt = Integer.parseInt(action);
       
       if(tt < 6 && tt > 0)
       {
           System.out.println("codice action " + tt + " tipobiomassa " + this.tipobiomassa);
           return ;
       
       }
       
       
        switch (action) {
            case "33":
                temp = classeparametri.getParametrodiprogetto(0,33, null, null, null);
                temp.setValore(String.valueOf(this.rapportobiomassaliquame.doubleValue()));
                break;
            case "34":
                temp = classeparametri.getParametrodiprogetto(0,34, null, null, null);
                //System.out.println("");
                temp.setValore(String.valueOf(this.rendimentocogeneratore.doubleValue()));
                break;
            case "35":
                temp = classeparametri.getParametrodiprogetto(0, 35, null, null, null);
                temp.setValore(String.valueOf(this.orefunzionamento.doubleValue()));
                break;
            case "36":
                temp = classeparametri.getParametrodiprogetto(0, 36, null, null, null);
                temp.setValore(String.valueOf(this.biogasutilizzato.doubleValue()));
                break;
            case "37":
                temp = classeparametri.getParametrodiprogetto(0, 37, null, null, null);
                temp.setValore(String.valueOf(this.energiaautoconsumo.doubleValue()));
                break;
            case "38":
                temp = classeparametri.getParametrodiprogetto(0, 38, null, null, null);
                temp.setValore(String.valueOf(this.emissioninh3.doubleValue()));
                break;
            case "98":
                temp = classeparametri.getParametrodiprogetto(0, 98, null,null, null);
                temp.setValore(String.valueOf(this.biogasemesso.doubleValue()));
                break;
            case "160":
                temp = classeparametri.getParametrodiprogetto(0, 160, null,null, null);
                temp.setValore(String.valueOf(this.rendimentoimpianto.doubleValue()));
                break;    
            case "74": 
                temp = classeparametri.getParametrodiprogetto(1, 74,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.produzionebiogas.doubleValue()));
                break;
            case "69":
                temp = classeparametri.getParametrodiprogetto(1, 69,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.metanonelbiogas.doubleValue()));
                break;
            case "73":
                temp = classeparametri.getParametrodiprogetto(1, 73,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.densitagas.doubleValue()));
                break;
            case "75":
                temp = classeparametri.getParametrodiprogetto(1, 75,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.prezzobiomassa.doubleValue()));
                break;
            case "99":
                temp = classeparametri.getParametrodiprogetto(1, 99,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.densitabiomassa.doubleValue()));
                break;
            case "43":
                temp = classeparametri.getParametrodiprogetto(1, 43,this.tipobiomassa1,null, null);
                temp.setValore(String.valueOf(this.st.doubleValue()));
                break;
             case "44":
                temp = classeparametri.getParametrodiprogetto(1, 44,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.sv.doubleValue()));
                break;         
             case "45":
                temp = classeparametri.getParametrodiprogetto(1, 45,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.azoto.doubleValue()));
                break;     
             case "46":
                temp = classeparametri.getParametrodiprogetto(1, 46,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.azotoammoniacale.doubleValue()));
                break;     
             case "47":
                temp = classeparametri.getParametrodiprogetto(1, 47,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.potassio.doubleValue()));
                break;     
             case "48":
                temp = classeparametri.getParametrodiprogetto(1, 48,this.tipobiomassa,null, null);
                temp.setValore(String.valueOf(this.fosforo.doubleValue()));
                break; 
             case "49":
                temp = classeparametri.getParametrodiprogetto(0, 49, null, null, null);
                temp.setValore(String.valueOf(this.c_inv.doubleValue()));
                break;     
             case "50":
                temp = classeparametri.getParametrodiprogetto(0, 50, null, null, null);
                temp.setValore(String.valueOf(this.e_inv.doubleValue()));
                break;     
             case "18":
                temp = classeparametri.getParametrodiprogetto(0, 18, null, null, null);
                temp.setValore(String.valueOf(this.costogestione.doubleValue()));
                break;     
             case "19":
                temp = classeparametri.getParametrodiprogetto(0, 19, null, null, null);
                temp.setValore(String.valueOf(this.prezzoenergia.doubleValue()));
                break;     
             case "52":
                temp = classeparametri.getParametrodiprogetto(0, 52, null, null, null);
                temp.setValore(String.valueOf(this.durata_investimento.doubleValue()));
                break;     
            case "61":
                temp = classeparametri.getParametrodiprogetto(0, 61, null, null, null);
                temp.setValore(String.valueOf(this.tasso_interesse.doubleValue()));
                break;      
                 
                 
    }
    
      if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
    
    if(temp != null)
    {
         entityManager.getTransaction().begin();
         entityManager.merge(temp);
    
         entityManager.flush();
         entityManager.getTransaction().commit();
         //System.out.println("action " + action + " this.coefficientemaccchina " + this.coefficientemaccchina + " this.coefficientetipomacchina " + this.coefficientetipomacchina + " valore " + temp.getValore() + " idnomeparametro  " + temp.getIdNomeparametro() + " trattamento  " + temp.getIdTrattamento());
    }
    
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

    /**
     * @return the tipobiomassa
     */
    public String getTipobiomassa() {
        return tipobiomassa;
    }

    /**
     * @param tipobiomassa the tipobiomassa to set
     */
    public void setTipobiomassa(String tipobiomassa) {
        if(tipobiomassa != null && tipobiomassa.length() != 0) {
            this.tipobiomassa = tipobiomassa;
        }
    }

    /**
     * @return the rapportobiomassaliquame
     */
    public Double getRapportobiomassaliquame() {
        return rapportobiomassaliquame;
    }

    /**
     * @param rapportobiomassaliquame the rapportobiomassaliquame to set
     */
    public void setRapportobiomassaliquame(Double rapportobiomassaliquame) {
        if(rapportobiomassaliquame != null ) {
            this.rapportobiomassaliquame = rapportobiomassaliquame;
        }
    }

    /**
     * @return the rendimentocogeneratore
     */
    public Double getRendimentocogeneratore() {
        return rendimentocogeneratore;
    }

    /**
     * @param rendimentocogeneratore the rendimentocogeneratore to set
     */
    public void setRendimentocogeneratore(Double rendimentocogeneratore) {
        if(rendimentocogeneratore != null) {
            this.rendimentocogeneratore = rendimentocogeneratore;
        }
    }

    /**
     * @return the biogasemesso
     */
    public Double getBiogasemesso() {
        return biogasemesso;
    }

    /**
     * @param biogasemesso the biogasemesso to set
     */
    public void setBiogasemesso(Double biogasemesso) {
        if(biogasemesso != null) {
            this.biogasemesso = biogasemesso;
        }
    }

    /**
     * @return the biogasutilizzato
     */
    public Double getBiogasutilizzato() {
        return biogasutilizzato;
    }

    /**
     * @param biogasutilizzato the biogasutilizzato to set
     */
    public void setBiogasutilizzato(Double biogasutilizzato) {
        if(biogasutilizzato != null) {
            this.biogasutilizzato = biogasutilizzato;
        }
    }

    /**
     * @return the energiaautoconsumo
     */
    public Double getEnergiaautoconsumo() {
        return energiaautoconsumo;
    }

    /**
     * @param energiaautoconsumo the energiaautoconsumo to set
     */
    public void setEnergiaautoconsumo(Double energiaautoconsumo) {
        if(energiaautoconsumo != null) {
            this.energiaautoconsumo = energiaautoconsumo;
        }
    }

    /**
     * @return the emissioninh3
     */
    public Double getEmissioninh3() {
        return emissioninh3;
    }

    /**
     * @param emissioninh3 the emissioninh3 to set
     */
    public void setEmissioninh3(Double emissioninh3) {
        if(emissioninh3 != null) {
            this.emissioninh3 = emissioninh3;
        }
    }

    /**
     * @return the orefunzionamento
     */
    public Double getOrefunzionamento() {
        return orefunzionamento;
    }

    /**
     * @param orefunzionamento the orefunzionamento to set
     */
    public void setOrefunzionamento(Double orefunzionamento) {
        if(orefunzionamento != null) {
            this.orefunzionamento = orefunzionamento;
        }
    }

    /**
     * @return the produzionebiogas
     */
    public Double getProduzionebiogas() {
        
       // System.out.println("get produzione biogas ");
       db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 74,this.tipobiomassa,null, null);
                this.produzionebiogas = new Double(Double.parseDouble(temp.getValore()));
        return produzionebiogas;
    }

    /**
     * @param produzionebiogas the produzionebiogas to set
     */
    public void setProduzionebiogas(Double produzionebiogas) {
        //System.out.println("set produzione biogas ");
        if(produzionebiogas != null) {
            this.produzionebiogas = produzionebiogas;
        }
    }

    /**
     * @return the metanonelbiogas
     */
    public Double getMetanonelbiogas() {
        
        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 69,this.tipobiomassa,null, null);
        this.metanonelbiogas = new Double(Double.parseDouble(temp.getValore()));
        return metanonelbiogas;
    }

    /**
     * @param metanonelbiogas the metanonelbiogas to set
     */
    public void setMetanonelbiogas(Double metanonelbiogas) {
        if(metanonelbiogas != null) {
            this.metanonelbiogas = metanonelbiogas;
        }
    }

    /**
     * @return the densitagas
     */
    public Double getDensitagas() {
        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 73,this.tipobiomassa,null, null);
        this.densitagas = new Double(Double.parseDouble(temp.getValore()));
        return densitagas;
    }

    /**
     * @param densitagas the densitagas to set
     */
    public void setDensitagas(Double densitagas) {
        if(densitagas != null) {
            this.densitagas = densitagas;
        }
    }

    /**
     * @return the prezzobiomassa
     */
    public Double getPrezzobiomassa() {
        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 75,this.tipobiomassa,null, null);
        this.prezzobiomassa = new Double(Double.parseDouble(temp.getValore()));
        return prezzobiomassa;
    }

    /**
     * @param prezzobiomassa the prezzobiomassa to set
     */
    public void setPrezzobiomassa(Double prezzobiomassa) {
        if(prezzobiomassa != null) {
            this.prezzobiomassa = prezzobiomassa;
        }
    }

    /**
     * @return the densitabiomassa
     */
    public Double getDensitabiomassa() {
        db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 99,this.tipobiomassa,null, null);
        this.densitabiomassa = new Double(Double.parseDouble(temp.getValore()));
        return densitabiomassa;
    }

    /**
     * @param densitabiomassa the densitabiomassa to set
     */
    public void setDensitabiomassa(Double densitabiomassa) {
        this.densitabiomassa = densitabiomassa;
    }

    /**
     * @return the st
     */
    public Double getSt() {
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 43,this.tipobiomassa1,null, null);
                this.st = new Double(Double.parseDouble(temp.getValore()));
        return st;
    }

    /**
     * @param st the st to set
     */
    public void setSt(Double st) {
        if(st != null) {
            this.st = st;
        }
    }

    /**
     * @return the sv
     */
    public Double getSv() {
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 44,this.tipobiomassa1,null, null);
         this.sv = new Double(Double.parseDouble(temp.getValore()));
         
        return sv;
    }

    /**
     * @param sv the sv to set
     */
    public void setSv(Double sv) {
        if(sv != null) {
            this.sv = sv;
        }
    }

    /**
     * @return the azoto
     */
    public Double getAzoto() {
        
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 45,this.tipobiomassa1,null, null);
        // System.out.println(this.getClass().getCanonicalName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " azoto " + temp.getValore() + " tipo biomassa " + this.tipobiomassa1);
                this.azoto = new Double(Double.parseDouble(temp.getValore()));
        
        return azoto;
    }

    /**
     * @param azoto the azoto to set
     */
    public void setAzoto(Double azoto) {
        if(azoto != null) {
            this.azoto = azoto;
        }
    }

    /**
     * @return the azotoammoniacale
     */
    public Double getAzotoammoniacale() {
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 46,this.tipobiomassa1,null, null);
                this.azotoammoniacale =  new Double(Double.parseDouble(temp.getValore()));
        return azotoammoniacale;
    }

    /**
     * @param azotoammoniacale the azotoammoniacale to set
     */
    public void setAzotoammoniacale(Double azotoammoniacale) {
        if(azotoammoniacale != null) {
            this.azotoammoniacale = azotoammoniacale;
        }
    }

   

    /**
     * @return the tipobiomassa1
     */
    public String getTipobiomassa1() {
        return tipobiomassa1;
    }

    /**
     * @param tipobiomassa1 the tipobiomassa1 to set
     */
    public void setTipobiomassa1(String tipobiomassa1) {
        if(tipobiomassa1 != null && tipobiomassa1.length() != 0) {
            this.tipobiomassa1 = tipobiomassa1;
        }
    }

    /**
     * @return the potassio
     */
    public Double getPotassio() {
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 47,this.tipobiomassa1,null, null);
                this.potassio = new Double(Double.parseDouble(temp.getValore()));
        return potassio;
    }

    /**
     * @param potassio the potassio to set
     */
    public void setPotassio(Double potassio) {
        if(potassio != null) {
            this.potassio = potassio;
        }
    }

    /**
     * @return the fosforo
     */
    public Double getFosforo() {
         db.ParametridiprogettoS  temp = classeparametri.getParametrodiprogetto(1, 48,this.tipobiomassa1,null, null);
                this.fosforo = new Double(Double.parseDouble(temp.getValore()));
        return fosforo;
    }

    /**
     * @param fosforo the fosforo to set
     */
    public void setFosforo(Double fosforo) {
        if(fosforo != null) {
            this.fosforo = fosforo;
        }
    }

    /**
     * @return the c_inv
     */
    public Double getC_inv() {
        return c_inv;
    }

    /**
     * @param c_inv the c_inv to set
     */
    public void setC_inv(Double c_inv) {
        if(c_inv != null) {
            this.c_inv = c_inv;
        }
    }

    /**
     * @return the e_inv
     */
    public Double getE_inv() {
        return e_inv;
    }

    /**
     * @param e_inv the e_inv to set
     */
    public void setE_inv(Double e_inv) {
        if(e_inv != null) {
            this.e_inv = e_inv;
        }
    }

    /**
     * @return the costogestione
     */
    public Double getCostogestione() {
        return costogestione;
    }

    /**
     * @param costogestione the costogestione to set
     */
    public void setCostogestione(Double costogestione) {
        if(costogestione != null) {
            this.costogestione = costogestione;
        }
    }

    /**
     * @return the prezzoenergia
     */
    public Double getPrezzoenergia() {
        return prezzoenergia;
    }

    /**
     * @param prezzoenergia the prezzoenergia to set
     */
    public void setPrezzoenergia(Double prezzoenergia) {
        if(prezzoenergia != null) {
            this.prezzoenergia = prezzoenergia;
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
        if(tasso_interesse !=null) {
            this.tasso_interesse = tasso_interesse;
        }
    }

    /**
     * @return the rendimentoimpianto
     */
    public Double getRendimentoimpianto() {
        return rendimentoimpianto;
    }

    /**
     * @param rendimentoimpianto the rendimentoimpianto to set
     */
    public void setRendimentoimpianto(Double rendimentoimpianto) {
        this.rendimentoimpianto = rendimentoimpianto;
    }
    
          
        
    
}
