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
@ManagedBean(name = "trasportoGUI")
@ViewScoped

public class TrasportoGUI implements Serializable{
    
    
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
   
    
    private Double consumo_operazioni_l;
    private Double consumo_l;
    private Double tempo_operazioni_l;
    private Double ore_lavoro_l;
    private Double velocita_media_l;
    private Double capacita_carico_l;
    private Double b_l;
    private Double m1_l;
    private Double m2_l;
    private Double ef_co2_trasp_road_l;
    private Double ef_nh3_trasp_l;
    private Double ef_n2o_tra_road_l;
    private Double ef_n2o_cold_l;
    private Double ef_ch4_tra_road_l;
    private Double ef_ch4_cold_l;
    private Double ef_no_tra_l;
    private Double ef_n2o_tra_offroad_l;
    private Double ef_ch4_tra_offroad_l;
   
    private Double consumo_operazioni_q;
    private Double consumo_q;
    private Double tempo_operazioni_q;
    private Double ore_qavoro_q;
    private Double velocita_media_q;
    private Double capacita_carico_q;
    private Double b_q;
    private Double m1_q;
    private Double m2_q;
    private Double ef_co2_trasp_road_q;
    private Double ef_nh3_trasp_q;
    private Double ef_n2o_tra_road_q;
    private Double ef_n2o_cold_q;
    private Double ef_ch4_tra_road_q;
    private Double ef_ch4_cold_q;
    private Double ef_no_tra_q;
    private Double ef_n2o_tra_offroad_q;
    private Double ef_ch4_tra_offroad_q;
    
   public TrasportoGUI()
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
         classeparametri = new Parametridiprogetto("renuwal2",21,dettCuaa.getIdscenario());
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
             case 189:
                 if(temp.getDiscriminante().equals("liquido"))
                    this.setConsumo_operazioni_l(new Double(Double.parseDouble(temp.getValore())));
                 else
                     this.setConsumo_operazioni_q(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 190:
                  if(temp.getDiscriminante().equals("liquido"))
                    this.setConsumo_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                     this.setConsumo_q(new Double(Double.parseDouble(temp.getValore())));    
                 break;

             case 191:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setTempo_operazioni_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                      this.setTempo_operazioni_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 192:
                    if(temp.getDiscriminante().equals("liquido"))
                        this.setOre_lavoro_l(new Double(Double.parseDouble(temp.getValore())));
                    else
                        this.setOre_qavoro_q(new Double(Double.parseDouble(temp.getValore())));
                        
                 break;

             case 193:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setVelocita_media_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                    this.setVelocita_media_q(new Double(Double.parseDouble(temp.getValore())));   
                 break;
             case 194:
                  if(temp.getDiscriminante().equals("liquido"))
                       this.setCapacita_carico_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                      this.setCapacita_carico_q(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 195:
                      if(temp.getDiscriminante().equals("liquido"))
                 this.setB_l(new Double(Double.parseDouble(temp.getValore())));
                      else
                          this.setB_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 30:
                   if(temp.getDiscriminante().equals("liquido"))
                 this.setM1_l(new Double(Double.parseDouble(temp.getValore())));
                   else
                        this.setM1_q(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 31:
                   if(temp.getDiscriminante().equals("liquido"))
                 this.setM2_l(new Double(Double.parseDouble(temp.getValore())));
                   else
                        this.setM2_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 196:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_co2_trasp_road_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                       this.setEf_co2_trasp_road_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 197:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_nh3_trasp_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                        this.setEf_nh3_trasp_q(new Double(Double.parseDouble(temp.getValore())));
                 break;

             case 198:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_n2o_tra_road_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                       this.setEf_n2o_tra_road_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 199:
                 if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_n2o_cold_l(new Double(Double.parseDouble(temp.getValore())));
                 else
                     this.setEf_n2o_cold_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             /**
              * l'efficienza viene calcoalata nel modulo in funzione di altri parametri
              */    
             case 200:
                   if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_ch4_tra_road_l(new Double(Double.parseDouble(temp.getValore())));
                   else
                       this.setEf_ch4_tra_road_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 201:
                    if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_ch4_cold_l(new Double(Double.parseDouble(temp.getValore())));
                    else
                     this.setEf_ch4_cold_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             
             case 202:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_no_tra_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                      this.setEf_no_tra_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
             case 203:
                  if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_n2o_tra_offroad_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                   this.setEf_n2o_tra_offroad_q(new Double(Double.parseDouble(temp.getValore())));
                 break;
               
            case 204:
                if(temp.getDiscriminante().equals("liquido"))
                 this.setEf_ch4_tra_offroad_l(new Double(Double.parseDouble(temp.getValore())));
                  else
                   this.setEf_ch4_tra_offroad_q(new Double(Double.parseDouble(temp.getValore())));
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
           
              
             case "189l":
                temp = classeparametri.getParametrodiprogetto(2, 189,null,null, "liquido");
                temp.setValore(String.valueOf(this.consumo_operazioni_l.doubleValue()));
                break;        
              
             case "190l":
                temp = classeparametri.getParametrodiprogetto(2, 190,null,null, "liquido");
                temp.setValore(String.valueOf(this.consumo_l.doubleValue()));
                break;        
            
             case "191l":
                temp = classeparametri.getParametrodiprogetto(2, 191,null,null,"liquido");
                temp.setValore(String.valueOf(this.tempo_operazioni_l.doubleValue()));
                break;         
             case "192l":
                temp = classeparametri.getParametrodiprogetto(2, 192,null,null, "liquido");
                temp.setValore(String.valueOf(this.ore_lavoro_l.doubleValue()));
                break;     
             case "193l":
                temp = classeparametri.getParametrodiprogetto(2, 193,null,null, "liquido");
                temp.setValore(String.valueOf(this.velocita_media_l.doubleValue()));
                break;     
             case "194l":
                temp = classeparametri.getParametrodiprogetto(2, 194,null,null, "liquido");
                temp.setValore(String.valueOf(this.capacita_carico_l.doubleValue()));
                break;     
              
             case "195l":
                temp = classeparametri.getParametrodiprogetto(2, 195,null,null, "liquido");
                temp.setValore(String.valueOf(this.b_l.doubleValue()));
                break;             
             case "30l":
                temp = classeparametri.getParametrodiprogetto(2, 30,null,null, "liquido");
                temp.setValore(String.valueOf(this.m1_l.doubleValue()));
                break; 
                 
             case "31l":
                temp = classeparametri.getParametrodiprogetto(2, 31,null,null, "liquido");
                temp.setValore(String.valueOf(this.m2_l.doubleValue()));
                break;      
                 
             case "196l":
                temp = classeparametri.getParametrodiprogetto(2,196, null, null, "liquido");
                temp.setValore(String.valueOf(this.ef_co2_trasp_road_l.doubleValue()));
                break;   
                 
             case "197l":
                temp = classeparametri.getParametrodiprogetto(2, 197,null,null, "liquido");
                temp.setValore(String.valueOf(this.ef_nh3_trasp_l.doubleValue()));
                break;      
             case "198l":
                temp = classeparametri.getParametrodiprogetto(2, 198, null, null, "liquido");
                temp.setValore(String.valueOf(this.ef_n2o_tra_road_l.doubleValue()));
                break;     
               
             case "199l":
                temp = classeparametri.getParametrodiprogetto(2, 199,null,null, "liquido");
                temp.setValore(String.valueOf(this.ef_n2o_cold_l.doubleValue()));
                break;        
             case "200l":
                temp = classeparametri.getParametrodiprogetto(2,200, null, null, "liquido");
                temp.setValore(String.valueOf(this.ef_ch4_tra_road_l.doubleValue()));
                break;     
             case "201l":
                temp = classeparametri.getParametrodiprogetto(2,201, null, null, "liquido");
                temp.setValore(String.valueOf(this.ef_ch4_cold_l.doubleValue()));
                break;  
              
             case "202l":
                temp = classeparametri.getParametrodiprogetto(2,202,null,null, "liquido");
                temp.setValore(String.valueOf(this.ef_no_tra_l.doubleValue()));
                break;        
              
             case "203l":
                temp = classeparametri.getParametrodiprogetto(2,203,null,null, "liquido");
                temp.setValore(String.valueOf(this.ef_n2o_tra_offroad_l.doubleValue()));
                break;       
           
                 
           case "204l":
                temp = classeparametri.getParametrodiprogetto(2,204,null,null, "liquido");
                temp.setValore(String.valueOf(this.ef_ch4_tra_offroad_l.doubleValue()));
                break;      
            
             case "189q":
                temp = classeparametri.getParametrodiprogetto(2, 189,null,null, "palabile");
                temp.setValore(String.valueOf(this.consumo_operazioni_q.doubleValue()));
                break;        
              
             case "190q":
                temp = classeparametri.getParametrodiprogetto(2, 190,null,null, "palabile");
                temp.setValore(String.valueOf(this.consumo_q.doubleValue()));
                break;        
            
             case "191q":
                temp = classeparametri.getParametrodiprogetto(2, 191,null,null,"palabile");
                temp.setValore(String.valueOf(this.tempo_operazioni_q.doubleValue()));
                break;         
             case "192q":
                temp = classeparametri.getParametrodiprogetto(2, 192,null,null, "palabile");
                temp.setValore(String.valueOf(this.ore_qavoro_q.doubleValue()));
                break;     
             case "193q":
                temp = classeparametri.getParametrodiprogetto(2, 193,null,null, "palabile");
                temp.setValore(String.valueOf(this.velocita_media_q.doubleValue()));
                break;     
             case "194q":
                temp = classeparametri.getParametrodiprogetto(2, 194,null,null, "palabile");
                temp.setValore(String.valueOf(this.capacita_carico_q.doubleValue()));
                break;     
              
             case "195q":
                temp = classeparametri.getParametrodiprogetto(2, 195,null,null, "palabile");
                temp.setValore(String.valueOf(this.b_q.doubleValue()));
                break;             
             case "30q":
                temp = classeparametri.getParametrodiprogetto(2, 30,null,null, "palabile");
                temp.setValore(String.valueOf(this.m1_q.doubleValue()));
                break; 
                 
             case "31q":
                temp = classeparametri.getParametrodiprogetto(2, 31,null,null, "palabile");
                temp.setValore(String.valueOf(this.m2_q.doubleValue()));
                break;      
                 
             case "196q":
                temp = classeparametri.getParametrodiprogetto(2,196, null, null, "palabile");
                temp.setValore(String.valueOf(this.ef_co2_trasp_road_q.doubleValue()));
                break;   
                 
             case "197q":
                temp = classeparametri.getParametrodiprogetto(2, 197,null,null, "palabile");
                temp.setValore(String.valueOf(this.ef_nh3_trasp_q.doubleValue()));
                break;      
             case "198q":
                temp = classeparametri.getParametrodiprogetto(2, 198, null, null, "palabile");
                temp.setValore(String.valueOf(this.ef_n2o_tra_road_q.doubleValue()));
                break;     
               
             case "199q":
                temp = classeparametri.getParametrodiprogetto(2, 199,null,null, "palabile");
                temp.setValore(String.valueOf(this.ef_n2o_cold_q.doubleValue()));
                break;        
             case "200q":
                temp = classeparametri.getParametrodiprogetto(2,200, null, null, "palabile");
                temp.setValore(String.valueOf(this.ef_ch4_tra_road_q.doubleValue()));
                break;     
             case "201q":
                temp = classeparametri.getParametrodiprogetto(2,201, null, null, "palabile");
                temp.setValore(String.valueOf(this.ef_ch4_cold_q.doubleValue()));
                break;  
              
             case "202q":
                temp = classeparametri.getParametrodiprogetto(2,202,null,null, "palabile");
                temp.setValore(String.valueOf(this.ef_no_tra_q.doubleValue()));
                break;        
              
             case "203q":
                temp = classeparametri.getParametrodiprogetto(2,203,null,null, "palabile");
                temp.setValore(String.valueOf(this.ef_n2o_tra_offroad_q.doubleValue()));
                break;       
           
                 
           case "204q":
                temp = classeparametri.getParametrodiprogetto(2,204,null,null, "palabile");
                temp.setValore(String.valueOf(this.ef_ch4_tra_offroad_q.doubleValue()));
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
     * @return the consumo_operazioni_l
     */
    public Double getConsumo_operazioni_l() {
        return consumo_operazioni_l;
    }

    /**
     * @param consumo_operazioni_l the consumo_operazioni_l to set
     */
    public void setConsumo_operazioni_l(Double consumo_operazioni_l) {
        if(consumo_operazioni_l != null) {
            this.consumo_operazioni_l = consumo_operazioni_l;
        }
    }

    /**
     * @return the consumo_l
     */
    public Double getConsumo_l() {
        return consumo_l;
    }

    /**
     * @param consumo_l the consumo_l to set
     */
    public void setConsumo_l(Double consumo_l) {
        if(consumo_l != null) {
            this.consumo_l = consumo_l;
        }
    }

    /**
     * @return the tempo_operazioni_l
     */
    public Double getTempo_operazioni_l() {
        return tempo_operazioni_l;
    }

    /**
     * @param tempo_operazioni_l the tempo_operazioni_l to set
     */
    public void setTempo_operazioni_l(Double tempo_operazioni_l) {
        if(tempo_operazioni_l != null) {
            this.tempo_operazioni_l = tempo_operazioni_l;
        }
    }

    /**
     * @return the ore_lavoro_l
     */
    public Double getOre_lavoro_l() {
        return ore_lavoro_l;
    }

    /**
     * @param ore_lavoro_l the ore_lavoro_l to set
     */
    public void setOre_lavoro_l(Double ore_lavoro_l) {
        if(ore_lavoro_l != null) {
            this.ore_lavoro_l = ore_lavoro_l;
        }
    }

    /**
     * @return the velocita_media_l
     */
    public Double getVelocita_media_l() {
        return velocita_media_l;
    }

    /**
     * @param velocita_media_l the velocita_media_l to set
     */
    public void setVelocita_media_l(Double velocita_media_l) {
        if(velocita_media_l != null) {
            this.velocita_media_l = velocita_media_l;
        }
    }

    /**
     * @return the capacita_carico_l
     */
    public Double getCapacita_carico_l() {
        return capacita_carico_l;
    }

    /**
     * @param capacita_carico_l the capacita_carico_l to set
     */
    public void setCapacita_carico_l(Double capacita_carico_l) {
        if(capacita_carico_l != null) {
            this.capacita_carico_l = capacita_carico_l;
        }
    }

    /**
     * @return the b_l
     */
    public Double getB_l() {
        return b_l;
    }

    /**
     * @param b_l the b_l to set
     */
    public void setB_l(Double b_l) {
        if(b_l != null) {
            this.b_l = b_l;
        }
    }

    /**
     * @return the m1_l
     */
    public Double getM1_l() {
        return m1_l;
    }

    /**
     * @param m1_l the m1_l to set
     */
    public void setM1_l(Double m1_l) {
        if(m1_l != null) {
            this.m1_l = m1_l;
        }
    }

    /**
     * @return the m2_l
     */
    public Double getM2_l() {
        return m2_l;
    }

    /**
     * @param m2_l the m2_l to set
     */
    public void setM2_l(Double m2_l) {
        if(m2_l != null) {
            this.m2_l = m2_l;
        }
    }

    /**
     * @return the ef_co2_trasp_road_l
     */
    public Double getEf_co2_trasp_road_l() {
        return ef_co2_trasp_road_l;
    }

    /**
     * @param ef_co2_trasp_road_l the ef_co2_trasp_road_l to set
     */
    public void setEf_co2_trasp_road_l(Double ef_co2_trasp_road_l) {
        if(ef_co2_trasp_road_l != null) {
            this.ef_co2_trasp_road_l = ef_co2_trasp_road_l;
        }
    }

    /**
     * @return the ef_nh3_trasp_l
     */
    public Double getEf_nh3_trasp_l() {
        return ef_nh3_trasp_l;
    }

    /**
     * @param ef_nh3_trasp_l the ef_nh3_trasp_l to set
     */
    public void setEf_nh3_trasp_l(Double ef_nh3_trasp_l) {
        if(ef_nh3_trasp_l != null) {
            this.ef_nh3_trasp_l = ef_nh3_trasp_l;
        }
    }

    /**
     * @return the ef_n2o_tra_road_l
     */
    public Double getEf_n2o_tra_road_l() {
        return ef_n2o_tra_road_l;
    }

    /**
     * @param ef_n2o_tra_road_l the ef_n2o_tra_road_l to set
     */
    public void setEf_n2o_tra_road_l(Double ef_n2o_tra_road_l) {
        if(ef_n2o_tra_road_l != null) {
            this.ef_n2o_tra_road_l = ef_n2o_tra_road_l;
        }
    }

    /**
     * @return the ef_n2o_cold_l
     */
    public Double getEf_n2o_cold_l() {
        return ef_n2o_cold_l;
    }

    /**
     * @param ef_n2o_cold_l the ef_n2o_cold_l to set
     */
    public void setEf_n2o_cold_l(Double ef_n2o_cold_l) {
        if(ef_n2o_tra_road_l != null) {
            this.ef_n2o_cold_l = ef_n2o_cold_l;
        }
    }

 

    /**
     * @return the consumo_operazioni_q
     */
    public Double getConsumo_operazioni_q() {
        return consumo_operazioni_q;
    }

    /**
     * @param consumo_operazioni_q the consumo_operazioni_q to set
     */
    public void setConsumo_operazioni_q(Double consumo_operazioni_q) {
        if(consumo_operazioni_q != null) {
            this.consumo_operazioni_q = consumo_operazioni_q;
        }
    }

    /**
     * @return the consumo_q
     */
    public Double getConsumo_q() {
        return consumo_q;
    }

    /**
     * @param consumo_q the consumo_q to set
     */
    public void setConsumo_q(Double consumo_q) {
        if(consumo_q != null) {
            this.consumo_q = consumo_q;
        }
    }

    /**
     * @return the tempo_operazioni_q
     */
    public Double getTempo_operazioni_q() {
        return tempo_operazioni_q;
    }

    /**
     * @param tempo_operazioni_q the tempo_operazioni_q to set
     */
    public void setTempo_operazioni_q(Double tempo_operazioni_q) {
        if(tempo_operazioni_q != null) {
            this.tempo_operazioni_q = tempo_operazioni_q;
        }
    }

    /**
     * @return the ore_qavoro_q
     */
    public Double getOre_qavoro_q() {
        return ore_qavoro_q;
    }

    /**
     * @param ore_qavoro_q the ore_qavoro_q to set
     */
    public void setOre_qavoro_q(Double ore_qavoro_q) {
        if(ore_qavoro_q != null) {
            this.ore_qavoro_q = ore_qavoro_q;
        }
    }

    /**
     * @return the velocita_media_q
     */
    public Double getVelocita_media_q() {
        return velocita_media_q;
    }

    /**
     * @param velocita_media_q the velocita_media_q to set
     */
    public void setVelocita_media_q(Double velocita_media_q) {
        if(velocita_media_q != null) {
            this.velocita_media_q = velocita_media_q;
        }
    }

    /**
     * @return the capacita_carico_q
     */
    public Double getCapacita_carico_q() {
        return capacita_carico_q;
    }

    /**
     * @param capacita_carico_q the capacita_carico_q to set
     */
    public void setCapacita_carico_q(Double capacita_carico_q) {
        if(capacita_carico_q != null) {
            this.capacita_carico_q = capacita_carico_q;
        }
    }

    /**
     * @return the b_q
     */
    public Double getB_q() {
        return b_q;
    }

    /**
     * @param b_q the b_q to set
     */
    public void setB_q(Double b_q) {
        if(b_q != null) {
            this.b_q = b_q;
        }
    }

    /**
     * @return the m1_q
     */
    public Double getM1_q() {
        return m1_q;
    }

    /**
     * @param m1_q the m1_q to set
     */
    public void setM1_q(Double m1_q) {
        if(m1_q != null) {
            this.m1_q = m1_q;
        }
    }

    /**
     * @return the m2_q
     */
    public Double getM2_q() {
        return m2_q;
    }

    /**
     * @param m2_q the m2_q to set
     */
    public void setM2_q(Double m2_q) {
        if(m2_q != null) {
            this.m2_q = m2_q;
        }
    }

    /**
     * @return the ef_co2_trasp_road_q
     */
    public Double getEf_co2_trasp_road_q() {
        return ef_co2_trasp_road_q;
    }

    /**
     * @param ef_co2_trasp_road_q the ef_co2_trasp_road_q to set
     */
    public void setEf_co2_trasp_road_q(Double ef_co2_trasp_road_q) {
        if(ef_co2_trasp_road_q != null) {
            this.ef_co2_trasp_road_q = ef_co2_trasp_road_q;
        }
    }

    /**
     * @return the ef_nh3_trasp_q
     */
    public Double getEf_nh3_trasp_q() {
        return ef_nh3_trasp_q;
    }

    /**
     * @param ef_nh3_trasp_q the ef_nh3_trasp_q to set
     */
    public void setEf_nh3_trasp_q(Double ef_nh3_trasp_q) {
        if(ef_nh3_trasp_q != null) {
            this.ef_nh3_trasp_q = ef_nh3_trasp_q;
        }
    }

    /**
     * @return the ef_n2o_tra_road_q
     */
    public Double getEf_n2o_tra_road_q() {
        return ef_n2o_tra_road_q;
    }

    /**
     * @param ef_n2o_tra_road_q the ef_n2o_tra_road_q to set
     */
    public void setEf_n2o_tra_road_q(Double ef_n2o_tra_road_q) {
        if(ef_n2o_tra_road_q != null) {
            this.ef_n2o_tra_road_q = ef_n2o_tra_road_q;
        }
    }

    /**
     * @return the ef_n2o_cold_q
     */
    public Double getEf_n2o_cold_q() {
        return ef_n2o_cold_q;
    }

    /**
     * @param ef_n2o_cold_q the ef_n2o_cold_q to set
     */
    public void setEf_n2o_cold_q(Double ef_n2o_cold_q) {
        if(ef_n2o_cold_q != null) {
            this.ef_n2o_cold_q = ef_n2o_cold_q;
        }
    }

    /**
     * @return the ef_ch4_tra_road_q
     */
    public Double getEf_ch4_tra_road_q() {
        return ef_ch4_tra_road_q;
    }

    /**
     * @param ef_ch4_tra_road_q the ef_ch4_tra_road_q to set
     */
    public void setEf_ch4_tra_road_q(Double ef_ch4_tra_road_q) {
        if(ef_ch4_tra_road_q != null) {
            this.ef_ch4_tra_road_q = ef_ch4_tra_road_q;
        }
    }

    /**
     * @return the ef_ch4_cold_q
     */
    public Double getEf_ch4_cold_q() {
        return ef_ch4_cold_q;
    }

    /**
     * @param ef_ch4_cold_q the ef_ch4_cold_q to set
     */
    public void setEf_ch4_cold_q(Double ef_ch4_cold_q) {
        if(ef_ch4_cold_q != null) {
            this.ef_ch4_cold_q = ef_ch4_cold_q;
        }
    }

    /**
     * @return the ef_no_tra_q
     */
    public Double getEf_no_tra_q() {
        return ef_no_tra_q;
    }

    /**
     * @param ef_no_tra_q the ef_no_tra_q to set
     */
    public void setEf_no_tra_q(Double ef_no_tra_q) {
        if(ef_no_tra_q != null) {
            this.ef_no_tra_q = ef_no_tra_q;
        }
    }

    /**
     * @return the ef_n2o_tra_offroad_q
     */
    public Double getEf_n2o_tra_offroad_q() {
        return ef_n2o_tra_offroad_q;
    }

    /**
     * @param ef_n2o_tra_offroad_q the ef_n2o_tra_offroad_q to set
     */
    public void setEf_n2o_tra_offroad_q(Double ef_n2o_tra_offroad_q) {
        if(ef_n2o_tra_offroad_q != null) {
            this.ef_n2o_tra_offroad_q = ef_n2o_tra_offroad_q;
        }
    }

    /**
     * @return the ef_ch4_tra_offroad_q
     */
    public Double getEf_ch4_tra_offroad_q() {
        return ef_ch4_tra_offroad_q;
    }

    /**
     * @param ef_ch4_tra_offroad_q the ef_ch4_tra_offroad_q to set
     */
    public void setEf_ch4_tra_offroad_q(Double ef_ch4_tra_offroad_q) {
        if(ef_ch4_tra_offroad_q != null) {
            this.ef_ch4_tra_offroad_q = ef_ch4_tra_offroad_q;
        }
    }

    /**
     * @return the ef_ch4_tra_road_l
     */
    public Double getEf_ch4_tra_road_l() {
        return ef_ch4_tra_road_l;
    }

    /**
     * @param ef_ch4_tra_road_l the ef_ch4_tra_road_l to set
     */
    public void setEf_ch4_tra_road_l(Double ef_ch4_tra_road_l) {
        if(ef_ch4_tra_road_l != null) {
            this.ef_ch4_tra_road_l = ef_ch4_tra_road_l;
        }
    }

    /**
     * @return the ef_ch4_cold_l
     */
    public Double getEf_ch4_cold_l() {
        return ef_ch4_cold_l;
    }

    /**
     * @param ef_ch4_cold_l the ef_ch4_cold_l to set
     */
    public void setEf_ch4_cold_l(Double ef_ch4_cold_l) {
        if(ef_ch4_cold_l != null) {
            this.ef_ch4_cold_l = ef_ch4_cold_l;
        }
    }

    /**
     * @return the ef_no_tra_l
     */
    public Double getEf_no_tra_l() {
        return ef_no_tra_l;
    }

    /**
     * @param ef_no_tra_l the ef_no_tra_l to set
     */
    public void setEf_no_tra_l(Double ef_no_tra_l) {
        if(ef_no_tra_l != null) {
            this.ef_no_tra_l = ef_no_tra_l;
        }
    }

    /**
     * @return the ef_n2o_tra_offroad_l
     */
    public Double getEf_n2o_tra_offroad_l() {
        return ef_n2o_tra_offroad_l;
    }

    /**
     * @param ef_n2o_tra_offroad_l the ef_n2o_tra_offroad_l to set
     */
    public void setEf_n2o_tra_offroad_l(Double ef_n2o_tra_offroad_l) {
        if(ef_n2o_tra_offroad_l != null) {
            this.ef_n2o_tra_offroad_l = ef_n2o_tra_offroad_l;
        }
    }

    /**
     * @return the ef_ch4_tra_offroad_l
     */
    public Double getEf_ch4_tra_offroad_l() {
        return ef_ch4_tra_offroad_l;
    }

    /**
     * @param ef_ch4_tra_offroad_l the ef_ch4_tra_offroad_l to set
     */
    public void setEf_ch4_tra_offroad_l(Double ef_ch4_tra_offroad_l) {
        if(ef_ch4_tra_offroad_l != null) {
            this.ef_ch4_tra_offroad_l = ef_ch4_tra_offroad_l;
        }
    }

  
          
        
    
}
