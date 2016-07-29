/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import operativo.*;
import db.Appezzamento;
import db.Colturaprecedente;
import db.Storicocolturaappezzamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *bean legato alle operazioni nella pagina Appezzamenti.xhtml
 * @author giorgio
 */
@ManagedBean(name="gestoreAppezzamenti")
@ViewScoped


public class GestoreAppezzamenti extends ListaAppezzamenti implements Serializable{
    
    private static final long serialVersionUID = -3832235132261771583L;
    
    //private List<RecordAppezzamento> listaAppezzamenti ;//= new LinkedList<RecordAppezzamento>();
    private int currentAppIndex;
    private long currentAppIndex1;
    
   
    //private List<db.TipoTerreno> tipiTerreni;
    //private List<db.TipoIrrigazione> tipiIrrigazione;
   // private String tipoTerreno = "1";
   // private String tipoIrrigazione = "1";
   // private String nome = "";
   // private double superficie = 0d;
  //  private boolean svn = false;
    private Appezzamento appezzamento;
    //private DettaglioCuaa dettaglioCuaa;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private RecordAppezzamento appezzamentoEdit ;
    private RecordAppezzamento appezzamentoDelete;
    private RecordAppezzamento appezzamentoNew = new RecordAppezzamento(null);
    //scenario di riferimento per gliappezzamenti
    private db.ScenarioI sceT;
    private static final int DECIMALS = 1;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    
    
    private int page = 1;
 
    private int clientRows;
    
    private boolean checkboxReadOnly = true;
    
    /**
     * Creates a new instance of GestoreAppezzamenti
     */
    public GestoreAppezzamenti() {
        
       super();
       
       //this.listaAppezzamenti = super.getListaAppezzamenti();
      /*  Utente ut = new Utente();
        ut.setUsername("giorgio");
        ut.setPassword("giorgio");
        ut.isCorretto();*/
        
        //popolaAppezzamenti();
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +"  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "  costruttore ");
    
       
    }
    
    /**
     * modifica del record della tabella appezzamenti del db renuwal1 e modifica di una riga della tabella della pagina a
     * appezzamenti.xhtml
     */
    public void store() {              
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.appezzamentoEdit.getId() + " nome " + this.appezzamentoEdit.getNome() + " superficie " + this.appezzamentoEdit.getSuperficie() + " svn " +this.appezzamentoEdit.isSvn() + " tipo terreno id " + this.appezzamentoEdit.getTipoTerreno()+ " descrizione terreno"+this.appezzamentoEdit.getTipoTerreno().getDescrizione() + " tipo irrigazione " + this.appezzamentoEdit.getTipoIrrigazione() + " tipo irrigazione descrizione " + this.appezzamentoEdit.getTipoIrrigazione().getDescrizione());
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
         
         db.Appezzamento appT = entityManager.find(db.Appezzamento.class,this.appezzamentoEdit.getId());
         
         Query q = entityManager.createNamedQuery("TipoIrrigazione.findByDescrizione").setParameter("descrizione", this.appezzamentoEdit.getTipoIrrigazione().getDescrizione());
         db.TipoIrrigazione tipoIrrigazione =(db.TipoIrrigazione)q.getSingleResult();
         Query q1 = entityManager.createNamedQuery("TipoTerreno.findByDescrizione").setParameter("descrizione", this.appezzamentoEdit.getTipoTerreno().getDescrizione());
         db.TipoTerreno tipoTerreno =(db.TipoTerreno)q1.getSingleResult();
         //db.TipoIrrigazione irriT = entityManager.find(db.TipoIrrigazione.class,this.appezzamentoEdit.);
         if(appT != null)
         {
             appT.setNome(this.appezzamentoEdit.getNome());
             appT.setSuperficie(this.appezzamentoEdit.getSuperficie());
             appT.setSvz(this.appezzamentoEdit.isSvn());
             appT.setTipoirrigazione(tipoIrrigazione);
             appT.setTipoterreno(tipoTerreno);
         }
         
         entityManager.getTransaction().begin();
         entityManager.merge(appT);
         entityManager.getTransaction().commit();
         
         Connessione.getInstance().chiudi();
         
         
         super.getListaAppezzamenti().clear();
         this.popolaAppezzamenti();
    
    }
    
    /**
     * chiamato dalla pagina appezzamenti attraverso il bottone x remove
     * deve cancellare un recordappezzamento dalla lista degli appezzamenti visibili nella tabella
     * della pagina appezzamenti.xhtml e prima deve cancellare il record appezzamento nel db
     */
    public void remove() {   
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()  + "   size listaappezzamenti " + super.getListaAppezzamenti().size() + " --remove--" + " idappezzamento  " + this.currentAppIndex1 +"idscenario  " + this.getDettaglioCuaa().getScenarioString() );

        Iterator<RecordAppezzamento> iterAppezzamento = super.getListaAppezzamenti().iterator();
        RecordAppezzamento re = null;
        while(iterAppezzamento.hasNext())
        {
            re = iterAppezzamento.next();
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()  + "this.currentAppIndex1 " + this.currentAppIndex1 + " id appezzamento" + re.getId() + "nel ciclo while  ");
            if(re.getId() == this.currentAppIndex1 )
            {
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()  + "trovato nel ciclo while ");
            }
        }
        //se ho trovato l'appezzamento in listaappezzzamenti lo tolgo dalla lista
        //ed elimino il relativo record dalla tabella del database appezzamenti
        if(re != null) {
            super.getListaAppezzamenti().remove(re);
            deleteAppezzamento(this.currentAppIndex1);
        }
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.currentAppIndex + " appezzamento id " + this.currentAppIndex1 + " nuova dimnensione " + super.getListaAppezzamenti().size() + " --remove--");
    }
    
    /**
     * elimina un appezzamento dalla tabella del db appezzamenti
     * @param idappezzamento
     * @param idscenario 
     */
    private void deleteAppezzamento(long idappezzamento)
    {
        
        System.out.println("gestore appezzamenti deleteappezzamanto idappezzamento " + idappezzamento);
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
        db.ScenarioI sceT = entityManager.find(db.ScenarioI.class,this.getDettaglioCuaa().getIdscenario());
        Iterator<db.Appezzamento> iterApp = sceT.getAppezzamentoCollection().iterator();
        db.Appezzamento app = null;
        while(iterApp.hasNext())
        {
            app = iterApp.next();
            if(app.getId() == this.currentAppIndex1)
            {
                break;
            }
        }
        
        if(app != null)
        {
           sceT.getAppezzamentoCollection().remove(app); 
        }
        
        entityManager.getTransaction().begin();
        entityManager.merge(sceT);
        entityManager.getTransaction().commit();
        
        Connessione.getInstance().chiudi();
        
        
    }
    
   
  /*@Override
   public List<RecordAppezzamento> getListaAppezzamenti() {
        synchronized(this){
                super.getListaAppezzamenti().clear();
                popolaAppezzamenti();
                
            
        }
        
        //System.out.println("ListaAppezzamenti getListaAppezamenti  :dimensione lista " + .listaAppezzamenti.size() +" dimensione lista colture " + this.listaColture.size());
        
        return super.getListaAppezzamenti();
    }*/

    /**
     * @param listaAppezzamenti the listaAppezzamenti to set
     */
   /* @Override
    public void setListaAppezzamenti(List<RecordAppezzamento> listaAppezzamenti) {
        super.setListaAppezzamenti(listaAppezzamenti);
    }*/

   /**
    * creo il record in appezzamenti nel db e poi creo il record nella tabella della pagina appezzamenti.xhtml
    */ 
   public void add() {              
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " nome  " + super.getNome() + " superficie " + super.getSuperficie() + " svn " + super.isSvn() + " tipoterreno " + super.getTipoTerreno() + " tipoirrigazione " + super.getTipoIrrigazione());
        //aggino il record nel database
        long id = addRecord();
        
        /*RecordAppezzamento re = new RecordAppezzamento(null);
        re.setNome(this.getNome());
        re.setSuperficie(this.getSuperficie());
        re.setSvn(this.isSvn());
        re.setTipoTerreno(this.getTipiTerreni().get(Integer.parseInt(this.getTipoTerreno())-1));
        re.setTipoIrrigazione(this.getTipiIrrigazione().get(Integer.parseInt(this.getTipoIrrigazione())-1));
        re.setId(id);
        this.getListaAppezzamenti().add(re);*/
       /* db.Appezzamento nuovo = new db.Appezzamento();
        nuovo.setNome(super.getNome());
        nuovo.setSuperficie(super.getSuperficie());
        nuovo.setSvz(super.isSvn());
        //nuovo.setTipoirrigazione();*/
        
        super.getListaAppezzamenti().clear();
        this.popolaAppezzamenti(); 
        
   
   }
 
    public void switchAjaxLoading(ValueChangeEvent event) {
        this.setClientRows((Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0);
    }
 
  /*  public void popolaAppezzamenti() {
        synchronized(this){
        /**
         * verifico se è stato impostato un valore per sceanriostring in dettaglioCuaa
         */
       /* ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");

        if (dettaglioCuaa.getScenarioString() != null) {
            
             /**
       * verifico la connnesione con il database
       */
      /* if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
      
       //devo creare un nuovo appezzamento e quindi devo cercare prima lo scenario
       //il tipo di terreno , il tipo di irrigazione
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", Long.parseLong(dettaglioCuaa.getScenarioString()));
       sceT =(db.ScenarioI)q.getResultList().get(0);
       Iterator<db.Appezzamento> iterAppezzamenti=sceT.getAppezzamentiCollection().iterator();
          
       listaAppezzamenti.clear();
       
       while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           RecordAppezzamento reT = new RecordAppezzamento();
           reT.setNome(apptemp.getNome());
           reT.setSuperficie(apptemp.getSuperficie());
           reT.setSvn(apptemp.isSvz());
           reT.setTipoIrrigazione(apptemp.getTipoIrrigazione());
           reT.setTipoTerreno(apptemp.getTipoTerreno());
           reT.setId(apptemp.getId());
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" idappezzamento " + reT.getId() + " aggiunto");
           
           listaAppezzamenti.add(reT);
       }
            
            
            //RecordAppezzamento iniziale = new RecordAppezzamento();
            //listaAppezzamenti.add(iniziale);

            RecordAppezzamento temp = new RecordAppezzamento();
            this.tipiIrrigazione = temp.getTipiIrrigazione();
            this.tipiTerreni = temp.getTipiTerreni();
            
            //this.pannelloFourth.setRendered(true);
            
        }else
        {
            /*if(this.pannelloFourth != null)
                this.pannelloFourth.setRendered(true);*/
     /*   }
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +"  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "  aggiorno lista appezzamenti");
    }
    }*/
    
    
    
    
    /**
     * @return the listaAppezzamenti
     */
    /*public List<RecordAppezzamento> getListaAppezzamenti() {
       //this.popolaAppezzamenti();
        synchronized(this){
        if(super.getListaAppezzamenti().isEmpty()) {
                popolaAppezzamenti();
                
            }
        }
        
        System.out.println("gestoreappezzamenti_2 getListaAppezamenti  :dimensione lista " + this.listaAppezzamenti.size() );
        
        return super.getListaAppezzamenti();
    }

    /**
     * @param listaAppezzamenti the listaAppezzamenti to set
     */
   /* public void setListaAppezzamenti(List<RecordAppezzamento> listaAppezzamenti) {
        this.listaAppezzamenti = listaAppezzamenti;
    }
    
    
    /**
     * legato al popup nuovo appezzamento della pagina appezzamenti
     * serve per creare un nuovo appezzamento
     */
    public long addRecord()
    {
      //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " id appezzamento " + this.currentAppIndex + " nome " + this.getNome() + " superficie " +this.getSuperficie() + " svn " + this.isSvn() + " terreno " + this.getTipoTerreno() + " irrigazione " + this.getTipoIrrigazione() + " scenario "  + this.getDettaglioCuaa().getScenarioString());
      
      if(this.getNome() == null)
          return -1;
      /**
       * verifico la connnesione con il database
       */
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
      
       //devo creare un nuovo appezzamento e quindi devo cercare prima lo scenario
       //il tipo di terreno , il tipo di irrigazione
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", this.getDettaglioCuaa().getIdscenario());
       db.ScenarioI scenT =null;
       db.TipoTerreno tipoTer = null;
       db.TipoIrrigazione tipoIrr = null;
       
       if(!q.getResultList().isEmpty())
       {
           scenT = (db.ScenarioI)q.getResultList().get(0);
           
       }else
       {
          System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " idsceanrio null");
          return -1;
       }
     
       q = entityManager.createNamedQuery("TipoTerreno.findById").setParameter("id", Long.parseLong(super.getTipoTerreno()));
       if(!q.getResultList().isEmpty())
       {
           tipoTer = (db.TipoTerreno)q.getResultList().get(0);
       }else
       {
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " tipo terreno null");
           return -1;
       }
       
       q = entityManager.createNamedQuery("TipoIrrigazione.findById").setParameter("id", Long.parseLong(super.getTipoIrrigazione()));
       if(!q.getResultList().isEmpty())
       {
           tipoIrr = (db.TipoIrrigazione)q.getResultList().get(0);
       }else
       {
          System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " tipo irrigazione null");
          return -1;
       }
       
       Colturaprecedente colturaPrecedente = entityManager.find(db.Colturaprecedente.class,1);
       
       
        entityManager.getTransaction().begin();
        db.Appezzamento nuovoappezzamento = new db.Appezzamento();
        nuovoappezzamento.setNome(this.getNome());
        nuovoappezzamento.setSuperficie(this.getSuperficie());
        nuovoappezzamento.setScenarioIdscenario(scenT);
        nuovoappezzamento.setTipoirrigazione(tipoIrr);
        nuovoappezzamento.setTipoterreno(tipoTer);
        nuovoappezzamento.setSvz(this.isSvn());
        nuovoappezzamento.setColturaprecedenteId(colturaPrecedente);
        
       //List<StoricoColturaAppezzamento> storicoColtura = nuovoappezzamento.getStoricoColturaAppezzamento();
     
         
        //entityManager.persist(nuovoappezzamento);
        scenT.getAppezzamentoCollection().add(nuovoappezzamento);
        entityManager.persist(nuovoappezzamento);
        entityManager.getTransaction().commit();
        entityManager.close();
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
        
        entityManager.getTransaction().begin();
        
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" id appezzamento " + nuovoappezzamento.getId() );

        db.Appezzamento app1 = entityManager.find(db.Appezzamento.class,nuovoappezzamento.getId());
        db.Coltura coltura = entityManager.find(db.Coltura.class, 1);
        db.Gruppocolturale gruppoColturale = entityManager.find(db.Gruppocolturale.class, 6L);
        db.Rotazione rot1 = entityManager.find(db.Rotazione.class, 1);
        db.Rotazione rot2 = entityManager.find(db.Rotazione.class, 2);
        db.Rotazione rot3 = entityManager.find(db.Rotazione.class, 3);
        
        db.Storicocolturaappezzamento sto1 = new db.Storicocolturaappezzamento();
        sto1.setIdappezzamentoId(app1);
        sto1.setAsportazioneazoto(0d);
        sto1.setAsportazionefosforo(0d);
        sto1.setAsportazionepotassio(0d);
        sto1.setMas(0d);      
        sto1.setResaAttesa(0d);
        sto1.setIdcolturaId(coltura);
        sto1.setIdgruppocolturaleId(gruppoColturale);
        sto1.setRotazioneId(rot1);
        
         db.Storicocolturaappezzamento sto2 = new db.Storicocolturaappezzamento();
        sto2.setIdappezzamentoId(app1);
        sto2.setAsportazioneazoto(0d);
        sto2.setAsportazionefosforo(0d);
        sto2.setAsportazionepotassio(0d);
        sto2.setMas(0d);      
        sto2.setResaAttesa(0d);
        sto2.setIdcolturaId(coltura);
        sto2.setIdgruppocolturaleId(gruppoColturale);
        sto2.setRotazioneId(rot2);
        
         db.Storicocolturaappezzamento sto3 = new db.Storicocolturaappezzamento();
        sto3.setIdappezzamentoId(app1);
        sto3.setAsportazioneazoto(0d);
        sto3.setAsportazionefosforo(0d);
        sto3.setAsportazionepotassio(0d);
        sto3.setMas(0d);      
        sto3.setResaAttesa(0d);
        sto3.setIdcolturaId(coltura);
        sto3.setIdgruppocolturaleId(gruppoColturale);
        sto3.setRotazioneId(rot3);
        
        app1.getStoricocolturaappezzamentoCollection().add(sto1);
          app1.getStoricocolturaappezzamentoCollection().add(sto2);
            app1.getStoricocolturaappezzamentoCollection().add(sto3);
       
        entityManager.persist(app1);
        entityManager.persist(sto1);
        entityManager.persist(sto2);
        entityManager.persist(sto3);
        entityManager.getTransaction().commit();
        entityManager.close();
            
       return nuovoappezzamento.getId();
      
    }
    
    /**
     * viene invocato dal comando delete con l'immagine della croce nella pagina
     * appezzamenti
     */
   /* public void deleteSelected()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " id appezzamento " + this.currentAppIndex1 );//+ " idappe " + this.appezzamentoDelete.getId());
    
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
      
       Iterator<db.Appezzamento> iterApp = sceT.getAppezzamentiCollection().iterator(); 
       db.Appezzamento dd = null;
       while(iterApp.hasNext())
       {
           dd = iterApp.next();
           if(dd.getId() == this.currentAppIndex1)
           break;    
       }
       
       if(dd != null)
           sceT.getAppezzamentiCollection().remove(dd);
       
        
       //cerco tra gli appezzamenti quello che ha id uguale al parametro passato attraverso currentAppIndex1
       //e lo rimuovo
       //Appezzamento at = entityManager.find(Appezzamento.class,this.currentAppIndex1);
       entityManager.getTransaction().begin();
       entityManager.merge(sceT);
       entityManager.getTransaction().commit();
       
    
    
    }*/
    
    
   /* public void modifySelected()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " id appezzamento " + this.currentAppIndex1 + " nome " + this.appezzamentoEdit.getNome() );//+ " idappe " + this.appezzamentoDelete.getId());
        for(Object o:selection)
        {
            
        }
    }*/
    
    
    /**
     * viene chiamatao dal bottone modifica del pop up di aggiornamento di 
     * di un record appezzamento
     */
    /*public void modify(String nome)
    {
      System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" nuovo nome " + this.appezzamentoEdit.getNome() + " nuova superficie " + this.appezzamentoEdit.getSuperficie() + " nuovo nome " + nome);
      
    }*/
    
   /* public void modify2(AjaxBehaviorEvent event)
    {
        /*int index = this.getDataTable().getRowIndex(); 
        System.out.println(event.getNewValue().toString());
         List listaappezzamento= (List) this.getDataTable().getValue(); 
      System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " indice " + index);
 
         RecordAppezzamento app = (RecordAppezzamento)listaappezzamento.get(index);
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " nome " + app.getNome());
*/
        
    /*     UIExtendedDataTable dataTable= (UIExtendedDataTable) event.getComponent();
         Object originalKey= dataTable.getRowKey();
         RecordAppezzamento riga =(RecordAppezzamento) dataTable.getRowData();
         
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " nome " + riga.getNome());

         */
         
    /*_tableSelectedEntries.clear();
    for (Object selectionKey: _tableSelection) {
        dataTable.setRowKey(selectionKey);
        if (dataTable.isRowAvailable()) {
            _tableSelectedEntries.add((Entry) dataTable.getRowData());
        }
    }
    dataTable.setRowKey(originalKey);*/
        
        
  /*  }*/
    
  /*  public void modify1(int index)
    {
        //int index = this.getDataTable().getRowIndex(); 
        //  String questionId =    (String) ((UIInput) e.getSource()).getAttributes().get("idcampo");
        // System.out.println("aggionanumero capi valore dell atributo passato " + questionId);
        //Integer currIndex = this.getDataTable().getRowIndex();  
      List listaappezzamento= (List) this.getDataTable().getValue(); 
      System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " indice " + index);
 
         RecordAppezzamento app = (RecordAppezzamento)listaappezzamento.get(index);
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " nome " + app.getNome());

         
    }*/
    
    /**
     * @return the currentAppIndex
     */
    public int getCurrentAppIndex() {
        return currentAppIndex;
    }

    /**
     * @param currentAppIndex the currentAppIndex to set
     */
    public void setCurrentAppIndex(int currentAppIndex) {
        this.currentAppIndex = currentAppIndex;
    }

    /**
     * @return the tipiTerreni
     */
   /* public List<db.TipoTerreno> getTipiTerreni() {
        return tipiTerreni;
    }

    /**
     * @param tipiTerreni the tipiTerreni to set
     */
   /* public void setTipiTerreni(List<db.TipoTerreno> tipiTerreni) {
        this.tipiTerreni = tipiTerreni;
    }

    /**
     * @return the tipiIrrigazione
     */
   /* public List<db.TipoIrrigazione> getTipiIrrigazione() {
        return tipiIrrigazione;
    }

    /**
     * @param tipiIrrigazione the tipiIrrigazione to set
     */
   /* public void setTipiIrrigazione(List<db.TipoIrrigazione> tipiIrrigazione) {
        this.tipiIrrigazione = tipiIrrigazione;
    }*/

    

   

    /**
     * @return the nome
     */
   /* public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
   /* public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the superficie
     */
   /* public double getSuperficie() {
        return superficie;
    }

    /**
     * @param superficie the superficie to set
     */
   /* public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * @return the svn
     */
   /* public boolean isSvn() {
        return svn;
    }

    /**
     * @param svn the svn to set
     */
  /*  public void setSvn(boolean svn) {
        this.svn = svn;
    }

    /**
     * @return the appezzamento
     */
    public Appezzamento getAppezzamento() {
        return appezzamento;
    }

    /**
     * @param appezzamento the appezzamento to set
     */
    public void setAppezzamento(Appezzamento appezzamento) {
        this.appezzamento = appezzamento;
    }

    /**
     * @return the tipoTerreno
     */
   /* public String getTipoTerreno() {
        return tipoTerreno;
    }

    /**
     * @param tipoTerreno the tipoTerreno to set
     */
  /*  public void setTipoTerreno(String tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    /**
     * @return the tipoIrrigazione
     */
  /*  public String getTipoIrrigazione() {
        return tipoIrrigazione;
    }

    /**
     * @param tipoIrrigazione the tipoIrrigazione to set
     */
   /* public void setTipoIrrigazione(String tipoIrrigazione) {
        this.tipoIrrigazione = tipoIrrigazione;
    }

    /**
     * @return the appezzamentoEdit
     */
    public RecordAppezzamento getAppezzamentoEdit() {
        return appezzamentoEdit;
    }

    /**
     * @param appezzamentoEdit the appezzamentoEdit to set
     */
    public void setAppezzamentoEdit(RecordAppezzamento appezzamentoEdit) {
        this.appezzamentoEdit = appezzamentoEdit;
    }

  public void resetValues() {
      
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() );
        // reset input fields to prevent stuck values after a validation failure
        // not necessary in JSF 2.2+ (@resetValues on a4j:commandButton)
        /*if (!JsfVersion.getCurrent().isCompliantWith(JsfVersion.JSF_2_2)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            UIComponent comp = fc.getViewRoot().findComponent("form:editGrid");
 
            ((EditableValueHolder) comp.findComponent("form:price")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:mage")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:vin")).resetValue();
        }*/
    }

    /**
     * @return the appezzamentoDelete
     */
    public RecordAppezzamento getAppezzamentoDelete() {
        return appezzamentoDelete;
    }

    /**
     * @param appezzamentoDelete the appezzamentoDelete to set
     */
    public void setAppezzamentoDelete(RecordAppezzamento appezzamentoDelete) {
        this.appezzamentoDelete = appezzamentoDelete;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the clientRows
     */
    public int getClientRows() {
        return clientRows;
    }

    /**
     * @param clientRows the clientRows to set
     */
    public void setClientRows(int clientRows) {
        this.clientRows = clientRows;
    }

    /**
     * @return the appezzamentoNew
     */
    public RecordAppezzamento getAppezzamentoNew() {
        return appezzamentoNew;
    }

    /**
     * @param appezzamentoNew the appezzamentoNew to set
     */
    public void setAppezzamentoNew(RecordAppezzamento appezzamentoNew) {
        this.appezzamentoNew = appezzamentoNew;
    }

    /**
     * @return the currentAppIndex1
     */
    public long getCurrentAppIndex1() {
        return currentAppIndex1;
    }

    /**
     * @param currentAppIndex1 the currentAppIndex1 to set
     */
    public void setCurrentAppIndex1(long currentAppIndex1) {
        this.currentAppIndex1 = currentAppIndex1;
    }

    /**
     * @return the checkboxReadOnly
     */
    public boolean isCheckboxReadOnly() {
        return checkboxReadOnly;
    }

    /**
     * @param checkboxReadOnly the checkboxReadOnly to set
     */
    public void setCheckboxReadOnly(boolean checkboxReadOnly) {
        this.checkboxReadOnly = checkboxReadOnly;
    }

    /**
     * @return the currentAppIndex1
     */
  /*  public long getCurrentAppIndex1() {
        return currentAppIndex1;
    }
*/
    /**
     * @param currentAppIndex1 the currentAppIndex1 to set
     */
   /* public void setCurrentAppIndex1(long currentAppIndex1) {
        this.currentAppIndex1 = currentAppIndex1;
    }*/

    /**
     * @return the dataTable
     */
   /* public org.richfaces.component.UIDataTable getDataTable() {
        return dataTable;
    }*/

    /**
     * @param dataTable the dataTable to set
     */
   /* public void setDataTable(org.richfaces.component.UIDataTable dataTable) {
        this.dataTable = dataTable;
    }*/

    /**
     * @return the selection
     */
   /* public Collection<Object> getSelection() {
        return selection;
    }*/

    /**
     * @param selection the selection to set
     */
   /* public void setSelection(Collection<Object> selection) {
        this.selection = selection;
    }*/

    /**
     * @return the selectionMode
     */
   /* public String getSelectionMode() {
        return selectionMode;
    }*/

    /**
     * @param selectionMode the selectionMode to set
     */
   /* public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }*/

   
    
   
}
