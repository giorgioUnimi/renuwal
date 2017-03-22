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
@ManagedBean(name = "fitodepurazioneGUI")
@ViewScoped

public class FitodepurazioneGUI implements Serializable{
    
    
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
    private Double costo_energia_elettrica;
    private Double costo_manutenzione;
    private Double energia_elettrica;
    private Double tempo_ritenzione;
    private Double altezza_bacino;
    private Double interesse;
    private Double em_ch4;
    private Double em_nh3;
    private Double em_co2;
    private Double em_n2o;
    private Double em_n2;
    
    
   public FitodepurazioneGUI()
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
         classeparametri = new Parametridiprogetto("renuwal2",19,dettCuaa.getIdscenario());
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

                 this.setInvestimento(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 17:

                 this.setAnni(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 19:

                 this.setCosto_energia_elettrica(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 18:

                 this.setCosto_manutenzione(new Double(Double.parseDouble(temp.getValore())));
                 break;

          
             case 147:

                 this.setEnergia_elettrica(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 105:

                 this.setTempo_ritenzione(new Double(Double.parseDouble(temp.getValore())));
                 break;
                 
             case 175:

                 this.setAltezza_bacino(new Double(Double.parseDouble(temp.getValore())));
                 break;      
                 
                 
             case 61:

                 this.setInteresse(new Double(Double.parseDouble(temp.getValore())));
                 break;
           
               
            case 152:

                 this.setEm_ch4(new Double(Double.parseDouble(temp.getValore())));
                 break;     
            
            case 153:

                 this.setEm_nh3(new Double(Double.parseDouble(temp.getValore())));
                 break;     
            
           case 154:

                 this.setEm_co2(new Double(Double.parseDouble(temp.getValore())));
                 break;     
           case 155:

                 this.setEm_n2(new Double(Double.parseDouble(temp.getValore())));
                 break;    
               
               
           case 156:

                 this.setEm_n2o(new Double(Double.parseDouble(temp.getValore())));
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
                temp.setValore(String.valueOf(this.getInvestimento().doubleValue()));
                break;        
              
             case "17":
                temp = classeparametri.getParametrodiprogetto(0, 17,null,null, null);
                temp.setValore(String.valueOf(this.getAnni().doubleValue()));
                break;        
            
             case "19":
                temp = classeparametri.getParametrodiprogetto(0, 19,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_energia_elettrica().doubleValue()));
                break;         
             case "18":
                temp = classeparametri.getParametrodiprogetto(0, 18,null,null, null);
                temp.setValore(String.valueOf(this.getCosto_manutenzione().doubleValue()));
                break;     
            
             case "147":
                temp = classeparametri.getParametrodiprogetto(0, 147,null,null, null);
                temp.setValore(String.valueOf(this.getEnergia_elettrica().doubleValue()));
                break;        
            
             case "105":
                temp = classeparametri.getParametrodiprogetto(0, 105,null,null, null);
                temp.setValore(String.valueOf(this.getTempo_ritenzione().doubleValue()));
                break;            
            
             case "175":
                temp = classeparametri.getParametrodiprogetto(0, 175,null,null, null);
                temp.setValore(String.valueOf(this.getAltezza_bacino().doubleValue()));
                break;            
                 
             case "61":
                temp = classeparametri.getParametrodiprogetto(0, 61,null,null, null);
                temp.setValore(String.valueOf(this.getInteresse().doubleValue()));
                break;       
           
                    
            case "152":
                temp = classeparametri.getParametrodiprogetto(0, 152,null,null, null);
                temp.setValore(String.valueOf(this.getEm_ch4().doubleValue()));
                break;      
            
           case "153":
                temp = classeparametri.getParametrodiprogetto(0, 153,null,null, null);
                temp.setValore(String.valueOf(this.getEm_nh3().doubleValue()));
                break;         
          case "154":
                temp = classeparametri.getParametrodiprogetto(0, 154,null,null, null);
                temp.setValore(String.valueOf(this.getEm_co2().doubleValue()));
                break;          
         case "155":
                temp = classeparametri.getParametrodiprogetto(0, 155,null,null, null);
                temp.setValore(String.valueOf(this.getEm_n2().doubleValue()));
                break;         
         case "156":
                temp = classeparametri.getParametrodiprogetto(0, 156,null,null, null);
                temp.setValore(String.valueOf(this.getEm_n2o().doubleValue()));
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
     * @return the costo_energia_elettrica
     */
    public Double getCosto_energia_elettrica() {
        return costo_energia_elettrica;
    }

    /**
     * @param costo_energia_elettrica the costo_energia_elettrica to set
     */
    public void setCosto_energia_elettrica(Double costo_energia_elettrica) {
        if(costo_energia_elettrica !=null) {
            this.costo_energia_elettrica = costo_energia_elettrica;
        }
    }

    /**
     * @return the costo_manutenzione
     */
    public Double getCosto_manutenzione() {
        return costo_manutenzione;
    }

    /**
     * @param costo_manutenzione the costo_manutenzione to set
     */
    public void setCosto_manutenzione(Double costo_manutenzione) {
        if(costo_manutenzione != null) {
            this.costo_manutenzione = costo_manutenzione;
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
     * @return the tempo_ritenzione
     */
    public Double getTempo_ritenzione() {
        return tempo_ritenzione;
    }

    /**
     * @param tempo_ritenzione the tempo_ritenzione to set
     */
    public void setTempo_ritenzione(Double tempo_ritenzione) {
        if(tempo_ritenzione != null) {
            this.tempo_ritenzione = tempo_ritenzione;
        }
    }

    /**
     * @return the altezza_bacino
     */
    public Double getAltezza_bacino() {
        return altezza_bacino;
    }

    /**
     * @param altezza_bacino the altezza_bacino to set
     */
    public void setAltezza_bacino(Double altezza_bacino) {
        if(altezza_bacino != null) {
            this.altezza_bacino = altezza_bacino;
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
        if(interesse != null ) {
            this.interesse = interesse;
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
        if(em_ch4 != null) {
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
        if(em_nh3 != null) {
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
        if(em_co2 != null) {
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
        if(em_n2o != null) {
            this.em_n2o = em_n2o;
        }
    }

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
        if(em_n2 != null) {
            this.em_n2 = em_n2;
        }
    }

  

          
        
    
}
