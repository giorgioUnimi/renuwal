/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joomla;


import operativo.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
//import javax.faces.component.html.HtmlDataTable;
//import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.mail.FetchProfile.Item;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.server.ServerSession;
import org.richfaces.component.AbstractExtendedDataTable;
import org.richfaces.component.UIExtendedDataTable;
import org.richfaces.component.UIDataTable;



@ManagedBean(name="listaAllevamentiJ")
@ViewScoped
public class ListaAllevamenti implements Serializable {

    private static final long serialVersionUID = 1L;   

    private List<RecordAllevamento1> tabella= new ArrayList<RecordAllevamento1>();    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private String selectionMode = "single";
    //private HtmlDataTable dataTable;
    private UIExtendedDataTable dataTable1;
    //private HtmlDataTable dataTable2;
    private RecordAllevamento1 dataItem = new RecordAllevamento1();
    
    /**
     * riferimento al contenitore del cuaa aziendale definito 
     * nella pagina dettaglioazienda.xhmtl
     */
    private DettaglioCuaa dettCuaa = null;
    
    
    //rappresenta il bottone aggiungi nella pagina allevamento.xhtml
    private UIComponent bottoneaggiungi;
    
    private Collection<Object> selection;
    
    private long idallevamento;
    private RecordAllevamento1 allevamentoEdit = new RecordAllevamento1();
    
    /* private RecordAllevamento1 allevamentoEdit;*/
    
    
    public ListaAllevamenti() {
        
        tabella.clear();
         
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
     
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
          ELContext elContext = FacesContext.getCurrentInstance().getELContext();
          dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
        
        // inizializzaTabella(); 
         popolaTabella();
         
      Connessione.getInstance().chiudi();
    }
    
  
    /**
     * fa un test per verificare e recuperare il valore di idscenario
     * se è -1 ritorna false altrimenti true
     * @return 
     */
    public boolean testIdscenario()
    {
        return recuperaIdscenario()==-1?false:true;
    }
    /**
     * via web mi arriva dal lato agronomico il cuaa e lo scenario e con 
     * questi recupero il codice univoco che per me è idscenario il quale rappresenta l'unione di scenario e cuaa
     * e lo imposto nella classe che uso per le informazioni comuni che dettagliocuaa
     */
    private long recuperaIdscenario()
    {
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        
         Query q1 = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", this.getDettCuaa().getCuaa());
        List<db.AziendeI> aziende = (List<db.AziendeI>)q1.getResultList();
        
        /**
         * se la lista non è vuota
         */
        if(aziende.isEmpty())
        {
             this.getDettCuaa().setCuaa("");
             return -1l;
        }else
        {
         /**
          * prendo la prima ed unica azienda
          */   
         db.AziendeI azienda =(db.AziendeI) aziende.get(0);
            this.getDettCuaa().setCuaa(azienda.getCuaa());
         /**
         * mi serve per cancellare la cache dell'entity manager
         */ 
          entityManager.getEntityManagerFactory().getCache().evictAll();
        //System.out.println("++++++++++++   " + this.getClass().getCanonicalName() + " dettCuaa.getScenarioString() " +  dettCuaa.getScenarioString());
        //Query q = entityManager.createNamedQuery("AziendeI.findByCuaa") .setParameter("cuaa", dettCuaa.getCuaa());
         Query q2 = entityManager.createQuery("Select s from ScenarioI s where  s.id = ?1");
         q2.setParameter(2,this.getDettCuaa().getIdscenario());
         List<db.ScenarioI> scenari = (List<db.ScenarioI>)q2.getResultList();
         
         if(!scenari.isEmpty())
         {
             db.ScenarioI scenario = scenari.get(0);
                this.getDettCuaa().setIdscenario(scenario.getIdscenario());
            //System.out.println(" scenari non empty cuaa " + scenario.getCuaa().getCuaa() + "  id " +scenario.getId());
         }else
         {
                this.getDettCuaa().setIdscenario(-1L);
            // System.out.println(" scenari empty " + this.dettCuaa.getIdscenario());
         }
        }
         
        Connessione.getInstance().chiudi();
         
        
       // System.out.println(".............. idscenario " + this.dettCuaa.getIdscenario());
        
        return  this.getDettCuaa().getIdscenario();
    }
    
    public String doSave(ActionEvent ev) {
        Item selectedItem = null;
        UIDataTable objHtmlDataTable = retrieveDataTable((UIComponent) ev.getSource());

        if (objHtmlDataTable != null) {
            selectedItem = (Item) objHtmlDataTable.getRowData();
            
            
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " " + selectedItem.toString());
        }
        return null;
    }

    private static UIDataTable retrieveDataTable(UIComponent component) {
        if (component instanceof UIDataTable) {
            return (UIDataTable) component;
        }
        if (component.getParent() == null) {
            return null;
        }
        return retrieveDataTable(component.getParent());
    }
    
   /* public void aggiorna(long id){
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() );
        Object originalKey = dataTable1.getRowKey();
       /* RecordAllevamento1 colonna = (RecordAllevamento1)dataTable1.getRowData();
        
      System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " specie " + colonna.getDesSpecie() + " categoria " + colonna.getDesCatAllev());
*/
        
       /* for (Object selectionKey : selection) {
            dataTable1.setRowKey(selectionKey);
            
            if (dataTable1.isRowAvailable()) {
                RecordAllevamento1 record=((RecordAllevamento1) dataTable1.getRowData());
               
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" " +record.getDesAllevamento()+" " + record.getDesCatAllev() + " " + record.getId());
            }
        }
        dataTable1.setRowKey(originalKey);
    }*/
    
    
    /* public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() );
        Object originalKey = dataTable1.getRowKey();
      
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            
            if (dataTable.isRowAvailable()) {
                RecordAllevamento1 record=((RecordAllevamento1) dataTable.getRowData());
               
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" " +record.getDesAllevamento()+" " + record.getDesCatAllev() + " " + record.getId());
            }
        }
        dataTable.setRowKey(originalKey);
    }*/
    
    public void popolaTabella() {
        
        if(tabella.isEmpty()){
                synchronized (this) {

            this.tabella.clear();
            tabella = new ArrayList();

            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            setDettCuaa((DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa"));


            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " scenario " + getDettCuaa().getScenarioString());

            if (getDettCuaa().getScenario() != 0) {

                /**
                 * verifico la connnesione con il database
                 */
                if (entityManagerFactory == null || (!entityManagerFactory.isOpen())) {
                    Connessione connessione = Connessione.getInstance();
                    entityManager = connessione.apri("renuwal2");
                }

                /**
                 * mi serve per cancellare la cache dell'entity manager
                 */
                entityManager.getEntityManagerFactory().getCache().evictAll();
                Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", getDettCuaa().getIdscenario());
                List<db.ScenarioI> scene = (List<db.ScenarioI>) q.getResultList();
                db.ScenarioI scena = null;
                if (scene.isEmpty()) {
                    scena = null;
                } else {
                    scena = scene.get(0);
                }

                if (scena != null) {

                    List<db.AllevamentoI> allevamentiCo = (List) scena.getAllevamentoICollection();

                    ListIterator<db.AllevamentoI> iterAllevamenti = allevamentiCo.listIterator();

                    RecordAllevamento1 recordAlleva;
                    List<RecordAllevamento1> listaRecord = new LinkedList<RecordAllevamento1>();
                    while (iterAllevamenti.hasNext()) {
                        // System.out.println("comune " + a.getComune() + " denominazione " + a.getDenominazione() + " cuaa " + a.getAziendePK().getCuaa());
                        db.AllevamentoI a = (db.AllevamentoI) iterAllevamenti.next();
                        recordAlleva = new RecordAllevamento1();

                        recordAlleva.setCodCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getId());

                        recordAlleva.setCodSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getCodSpecie());

                        recordAlleva.setCodStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getId());

                        recordAlleva.setCuaa(getDettCuaa().getCuaa());

                        recordAlleva.setDesAllevamento(a.getSpeciecategoriaallevamentostabulazionebId().getTipoAllevamentoBId().getDesAllevamento());

                        recordAlleva.setDesCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getDesCatAllev());

                        recordAlleva.setDesComunePresentazione(getDettCuaa().getComune());

                        recordAlleva.setDesSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());

                        recordAlleva.setDesStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getDesStabulazione());

                        recordAlleva.setId(a.getId().intValue());


                        if (a.getNumCapiSpecieStab() != null) {
                            recordAlleva.setNumCapiSpecieStab(a.getNumCapiSpecieStab().toString());
                        }

                        recordAlleva.setPesoVivo(a.getSpeciecategoriaallevamentostabulazionebId().getPesoVivoKg());

                        recordAlleva.setRagioneSociale(getDettCuaa().getRagionesociale());

                        listaRecord.add(recordAlleva);



                    }

                    /**
                     * aggiungo tutti irecord in un colpo solo
                     */
                    this.tabella.addAll(listaRecord);
                    
                   // this.tabella.add(new RecordAllevamento1());

                }
                Connessione.getInstance().chiudi();
                
            }//if scenario != 0

        }//close synchro
        }
    }
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private List inizializzaTabella()
    {
        
         this.tabella.clear();
         
        tabella= new ArrayList<RecordAllevamento1>();
        
   
         
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
     
        
       /**
         * mi serve per cancellare la cache dell'entity manager
         */ 
          entityManager.getEntityManagerFactory().getCache().evictAll();
      //  System.out.println("++++++++++++   " + this.getClass().getCanonicalName() + " dettCuaa.getScenarioString() " +  dettCuaa.getScenarioString());
        //Query q = entityManager.createNamedQuery("AziendeI.findByCuaa") .setParameter("cuaa", dettCuaa.getCuaa());
         Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario") .setParameter("idscenario", getDettCuaa().getIdscenario());
        //Query q = entityManager.createQuery("SELECT l FROM Allevamentoi l ");
        //List<db.AziendeI> results = q.getResultList();
        List<db.ScenarioI> scene = (List<db.ScenarioI>)q.getResultList();
        db.ScenarioI scena = null;
        if(scene.isEmpty())
        {
            scena = null;
        }else   
        {
            scena = scene.get(0);
        }
        /**
         * per il momento prendo lo scenario 0 
         */
        //List<db.ScenarioI> scenari =(List) results.get(0).getScenarioICollection();
        
        
        
        
        //System.out.println("cuaa in inizilizza tabella " + dettCuaa.getCuaa() + " scenario " +scena.getDescrizione());
        
        if(scena != null)
        {
           // List<db.AllevamentoI> allevamentiCo =(List) results.get(0).
            List<db.AllevamentoI> allevamentiCo = (List) scena.getAllevamentoICollection();
        
        ListIterator<db.AllevamentoI> iterAllevamenti = allevamentiCo.listIterator(); 
        //ListIterator<db.Aziendei> iterAllevamenti = results.listIterator();
        
        // for(int i=0; i < results.size();i++)
        RecordAllevamento1 recordAlleva;
        List<RecordAllevamento1> listaRecord = new LinkedList<RecordAllevamento1>();
        while(iterAllevamenti.hasNext())
         {
            // System.out.println("comune " + a.getComune() + " denominazione " + a.getDenominazione() + " cuaa " + a.getAziendePK().getCuaa());
             db.AllevamentoI a = (db.AllevamentoI)iterAllevamenti.next();
             recordAlleva = new RecordAllevamento1();
             
            // a.getSpeciecategoriaallevamentostabulazionebId();
                     
                     
             /*if(a.getCodCatAllev()!=null) {
                 recordAlleva.setCodCatAllev(a.getCodCatAllev());
             }*/
             
             recordAlleva.setCodCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getId());
             
             
             /*if(a.getCodSpecie()!=null) {
                 recordAlleva.setCodSpecie(a.getCodSpecie());
             }*/
             
             recordAlleva.setCodSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getCodSpecie());
             
             /*if(a.getCodStabulazione()!=null) {
                 recordAlleva.setCodStabulazione(a.getCodStabulazione());
             }*/
             
             recordAlleva.setCodStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getId());
             
             /*if(dettCuaa.getCuaa()!=null) {
                 recordAlleva.setCuaa(dettCuaa.getCuaa());
             }*/
             
             recordAlleva.setCuaa(getDettCuaa().getCuaa());

             /*if(a.getDesAllevamento()!=null) {
                 recordAlleva.setDesAllevamento(a.getDesAllevamento());
             }*/
             
             
             recordAlleva.setDesAllevamento(a.getSpeciecategoriaallevamentostabulazionebId().getTipoAllevamentoBId().getDesAllevamento());
             
             
             /*if(a.getDesCatAllev()!=null) {
                 recordAlleva.setDesCatAllev(a.getDesCatAllev());
             }*/
             
             
             recordAlleva.setDesCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getDesCatAllev());
             
             /*if(a.getDesComunePresentazione()!=null) {
                 recordAlleva.setDesComunePresentazione(a.getDesComunePresentazione());
             }*/
             
             
             recordAlleva.setDesComunePresentazione(getDettCuaa().getComune());
             
             
            /* if(a.getDesSpecie()!=null) {
                 recordAlleva.setDesSpecie(a.getDesSpecie());
             }*/
             
             
             recordAlleva.setDesSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());
             
             
             //System.out.println(this.getClass().getCanonicalName() + " inizializzatabella " + a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());
             
             /*if(a.getDesStabulazione()!=null) {
                 recordAlleva.setDesStabulazione(a.getDesStabulazione());
             }*/
             
             recordAlleva.setDesStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getDesStabulazione());
             
             
             /*if(a.getId()!=null) {
                 recordAlleva.setId(a.getId());
             }*/
             
             
             recordAlleva.setId(a.getId().intValue());
             
             
             if(a.getNumCapiSpecieStab()!=null) {
                 recordAlleva.setNumCapiSpecieStab(a.getNumCapiSpecieStab().toString());
             }
             /*if(a.getPesoVivo()!=null) {
                 recordAlleva.setPesoVivo(a.getPesoVivo());
             }*/
             
             
             recordAlleva.setPesoVivo(a.getSpeciecategoriaallevamentostabulazionebId().getPesoVivoKg());
             
             /*if(a.getRagioneSociale()!=null) {
                 recordAlleva.setRagioneSociale(a.getRagioneSociale());
             }*/
             
             
             recordAlleva.setRagioneSociale(getDettCuaa().getRagionesociale());
             
             listaRecord.add(recordAlleva);
             
            // getTabella().add(recordAlleva);
             
         }
        
           //tabella.add(new RecordAllevamento1());
        
        /**
         * aggiungo tutti irecord in un colpo solo
         */
        this.tabella.addAll(listaRecord);
        
        //this.tabella.add(new RecordAllevamento1());
        
        }
         //entityManager.close();
         
         
       /*    ListIterator<RecordAllevamento1> iterll = getTabella().listIterator();
         while(iterll.hasNext())
         {
             RecordAllevamento1 redd = iterll.next();
             
             System.out.println("stampo tabella dopo ogni aggiunta cuaa in allevamento " + redd.getCuaa() + " descrizione  " + redd.getDesAllevamento() + " id " + redd.getId() );
         }*/
            Connessione.getInstance().chiudi();
            
           return this.tabella;       
         //entityManagerFactory.close();
         
        // serverSession.disconnect();
           
           
          

    }
       
     /*public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }*/

   /*public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
        selectionItems.clear();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selectionItems.add((InventoryItem) dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }*/
 

    /**
     * @param tabella the tabella to set
     */
   /* public void setTabella(List<RecordAllevamento1> tabella) {
        this.tabella = tabella;
    }*/
    
    public String addRecord() {
       
        /**
         * faccio puntare il dataItem all'ultima riga della tabella 
         * cosi da prendere i dati inseriti dall'utente
         */
        //int ultimo = getDataTable().getRowCount();
        //getDataTable().setRowIndex(ultimo-1);
        
         int ultimo = getDataTable1().getRowCount();
        getDataTable1().setRows(ultimo-1);
         
        System.out.println(this.getClass().getCanonicalName() + " add record");
        
        /**
         * verifico se il numero capi ed il peso vivo oppure uno degli altri campi 
         * è null ritorno null e non faccio un inserimento
         */
         //dataItem = (RecordAllevamento1) getDataTable().getRowData();
         dataItem = (RecordAllevamento1) getDataTable1().getRowData();
         
          FacesMessage message = null;
          FacesContext context = null;
         
          System.out.println(this.getClass().getCanonicalName() + " dataItem.getnumcpi " + dataItem.getNumCapiSpecieStab());
          
          
         if(dataItem == null)
         {
              System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè dataitem della riga è nullo");
                message = new FacesMessage(" Inserimento non corretto ");
                context = FacesContext.getCurrentInstance();
                context.addMessage(bottoneaggiungi.getClientId(context), message);
             return null;
         }
         
         /**
          * verifico che il campo numero capi della pagina allevamento non sia vuoto o contenga
          * valori non numerici o contenga virgole o punti o inizi con lo 0
          */
         Pattern pattern = Pattern.compile("[^0-9]+|^[0]");
         Matcher matcher = pattern.matcher(dataItem.getNumCapiSpecieStab());
         
         if(dataItem.getNumCapiSpecieStab() == null || dataItem.getNumCapiSpecieStab().trim().length() == 0 || matcher.find())
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè dataitem della riga è nullo");
             message = new FacesMessage(" Inserimento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi.getClientId(context), message);
             return null;
         }
         
        /* if (matcher.find() || dataItem.getNumCapiSpecieStab().isEmpty()) {
            System.out.println("irregolarita trovata");
         } else {
            System.out.println("input corretto");
         }*/
         
         
         
         /**
          * se il trasformare una string ain numero lancia un eccezzione significa che 
          * la stringa era una stringa o la stringa è vuota 
          */
         /*try{
             Integer.parseInt(dataItem.getNumCapiSpecieStab());
         }catch(NumberFormatException ex)
         {
              message = new FacesMessage(" Inserimento non corretto ");
              context = FacesContext.getCurrentInstance();
              context.addMessage(bottoneaggiungi.getClientId(context), message);
              return null;
         }*/
         
        /* if(dataItem.getNumCapiSpecieStab().isEmpty())
         {
              message = new FacesMessage(" Inserimento non corretto ");
              context = FacesContext.getCurrentInstance();
              context.addMessage(bottoneaggiungi.getClientId(context), message);
              return null;
         }*/
         
         if(dataItem.getDesSpecie() == null || dataItem.getDesSpecie().isEmpty() || dataItem.getDesCatAllev() == null || dataItem.getDesCatAllev().isEmpty() ||  dataItem.getDesAllevamento() == null || dataItem.getDesAllevamento().isEmpty() || dataItem.getDesStabulazione() == null || dataItem.getDesStabulazione().isEmpty() || Integer.parseInt(dataItem.getNumCapiSpecieStab()) < 1 )
         {
             message = new FacesMessage(" Inserimento non corretto ");
          
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi.getClientId(context), message);
             
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             return null;
         }
          /**
         * se l'entityManager è chiuso aprilo uso questo sistema per
         * mantenere una sola connessione aperta e non avere il falso errore
         * di eclipselink class cast exception
         */
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
         
        //DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        DettaglioCuaa dettaglioCuaa = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
       
        //Long idscenario = dettaglioCuaa.getIdscenario();
      
        /*jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
                serverSession.dontLogMessages();*/

        
       
       
        dataItem = (RecordAllevamento1) getDataTable1().getRowData();
       
        /**
         * recupero il numero id dell'ultimo allevamento presente
         */
        Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",dataItem.getDesSpecie());
                        db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                       // Integer cospecie = sp.getCodSpecie();
                        
                        q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", dataItem.getDesCatAllev());
                        db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                        //Integer cocat = cat.getId();
                        
                        q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", dataItem.getDesAllevamento());
                        db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                        //Integer coalleva = tipoalleva.getId();
                        
                        q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione",dataItem.getDesStabulazione());
                        db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getSingleResult();
                        
                         q = entityManager.createQuery("SELECT DISTINCT l  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                        q.setParameter(1, sp);
                        q.setParameter(2, cat);
                        q.setParameter(3, tipoalleva);
                        q.setParameter(4, tipostabu);
                        
                        db.SpeciecategoriaallevamentostabulazionebS scas = (db.SpeciecategoriaallevamentostabulazionebS)q.getSingleResult();
        /**
         * cerco l'azienda che ha il cuaa specificato e da quell'azienda recupero gli allevamenti
         */
        q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
        q.setParameter(1 , dettaglioCuaa.getIdscenario());
        
        db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
        List<db.AllevamentoI> listaAllevamenti = (List<db.AllevamentoI>)sc.getAllevamentoICollection();
      
        /**
         * 
         * da cancellare
         */
        System.out.println(this.getClass().getCanonicalName() + " ----------------------------------addrecord  idscenario " + sc.getIdscenario());
        
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        db.AllevamentoI alleva = new db.AllevamentoI();
        alleva.setIdscenario(sc);
        alleva.setSpeciecategoriaallevamentostabulazionebId(scas);
        alleva.setNumCapiSpecieStab(Integer.parseInt(dataItem.getNumCapiSpecieStab()));
        listaAllevamenti.add(alleva);
        entityManager.persist(alleva);
        tx.commit();
        /**
         * creo il record allevamento e lo aggiungo alla lista degli allevamenti della azienda selezionata
         * e pesisto l'azienda selezionata
         */
        //imposto tramite il costruttore l'id
      /*  db.AllevamentoI alleva = new db.AllevamentoI();
        alleva..setId((Long)ultimoA);
     
        alleva.setDesComunePresentazione(dettaglioCuaa.getComune());
        alleva.setRagioneSociale(dettaglioCuaa.getRagionesociale());
        alleva.setDesAllevamento(dataItem.getDesAllevamento());
      
        alleva.setDesSpecie(dataItem.getDesSpecie());
       
        alleva.setDesCatAllev(dataItem.getDesCatAllev());
        
        alleva.setDesStabulazione(dataItem.getDesStabulazione());
        alleva.setNumCapiSpecieStab(dataItem.getNumCapiSpecieStab());
        alleva.setPesoVivo(dataItem.getPesoVivo());
        
        /**
         * aggiungo il record di allevamento impostato secondo le caratteristicvhe inserite dall'utente
         */
       /* listAllevamento.add(alleva);
     
                aziendaT.setAllevamentoiCollection(listAllevamento);

        entityManager.persist(aziendaT);
        tx.commit();
        
   
        
        dataItem.setCuaa(aziendaT.getCuaa());
        dataItem.setId(ultimoA);
       
        getTabella().add(new RecordAllevamento1());
     
        
       
          entityManager.close();
                  
       
   */
       // getTabella().add(new RecordAllevamento1());
        
        
         //entityManager.close();
        
       Connessione.getInstance().chiudi();
       
       
       
       this.inizializzaTabella();
       
        return null;
    }

    /**
     * @return the dataTable
     */
   /* public HtmlDataTable getDataTable() {
        return dataTable;
    }*/

    /**
     * @param dataTable the dataTable to set
     */
   /* public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }*/
    
    
    
    
    public String deleteSelected() {
        
        boolean trovato = false;
        String cuaa="";
        int id = 0;
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
                serverSession.dontLogMessages();*/
 /**
         * se l'entityManager è chiuso aprilo uso questo sistema per
         * mantenere una sola connessione aperta e non avere il falso errore
         * di eclipselink class cast exception
         */
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        
        DettaglioCuaa dettoCuaa = new DettaglioCuaa();
        
        
        Iterator<RecordAllevamento1> entries = getTabella().iterator();
        while (entries.hasNext()) {
                RecordAllevamento1 person = entries.next();

                if (person.isSelected()) {
                        trovato = true;
                        cuaa = person.getCuaa();
                       
                        id = person.getId();
                        entries.remove();
                        
                       System.out.println("selected true    from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato + " " + person.getCuaa() + " " + person.getId());

                }
       
        
        
       // this.dataTable = null;
      /*    dataItem = (RecordAllevamento1) dataTable.getRowData();
          cuaa = dataItem.getCuaa();
          id = dataItem.getId();*/
       // System.out.println("from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato);
        
        if(trovato)
        {
            entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("DELETE FROM AllevamentoI l WHERE l.id='"+id+"' ");
        //List<db1.Aziende> results = q.getResultList();
        q.executeUpdate();
        //return ""; // Navigation case.
        
        entityManager.getTransaction().commit();
        
        trovato = false;
        }
         }
        
        //entityManager.close();
        
        Connessione.getInstance().chiudi();
        
       // entityManagerFactory.close();
        return null;
    }

    /**
     * @return the tabella
     */
    public List<RecordAllevamento1> getTabella() {
       // System.out.println(this.getClass().getCanonicalName() + " get tabella ");
         synchronized (this) {
           popolaTabella();
         }
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<RecordAllevamento1> tabella) {
     System.out.println(this.getClass().getCanonicalName() + " set tabella ");
        this.tabella = tabella;
    }
    
    /**
     * aggiorna il peso vivo  modificato nella pagina allevamento quando 
     * l'utente modifica tale campo e preme invio
     * @param e 
     */
   /* public void aggiornaPesoVivo(ValueChangeEvent e)
    {
        
         int index = this.getDataTable().getRowIndex(); 
         System.out.println("aggionanumero peso vivo " + e.getComponent().getClass().toString());
         RecordAllevamento1 alleva= (RecordAllevamento1) this.getDataTable().getRowData(); 
        
        System.out.println("++++++++++++++++++++++++++++cambio di valore di peso viso numero capi " + alleva.getNumCapiSpecieStab() +" peso vivo  " + alleva.getPesoVivo() + " nuovo valore " + e.getNewValue().toString() +" specie " + alleva.getDesSpecie() + " categoria " + alleva.getDesCatAllev() + " allevamento "+ alleva.getDesAllevamento() +" stabulazione " + alleva.getDesStabulazione() + " idscenario " + this.dettCuaa.getIdscenario() + " indice riga " + index);
    }*/
    
    /**
     * viene chiamato da allevamento.xhtml nel caso di cambio di valore in uno dei campi della tabella
     * L'idcampo serve per identificare la sorgente / campo a vui viene modifcato il valore
     * @param e 
     */
     public void aggiornaCampo(ValueChangeEvent e)
    {
        
         int index = this.getDataTable1().getRowIndex(); 
          String questionId =    (String) ((UIInput) e.getSource()).getAttributes().get("idcampo");
        // System.out.println("aggionanumero capi valore dell atributo passato " + questionId);
         RecordAllevamento1 alleva= (RecordAllevamento1) this.getDataTable1().getRowData(); 
        
       // System.out.println("++++++++++++++++++++++++++++cambio di valore di peso viso numero capi " + alleva.getNumCapiSpecieStab() +" peso vivo  " + alleva.getPesoVivo() + " nuovo valore " + e.getNewValue().toString() +" specie " + alleva.getDesSpecie() + " categoria " + alleva.getDesCatAllev() + " allevamento "+ alleva.getDesAllevamento() +" stabulazione " + alleva.getDesStabulazione() + " idscenario " + this.dettCuaa.getIdscenario() + " indice riga " + index);
    }
    
    public void eliminaRiga(long id) 
    {
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
          
          System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "id passato  " + id);
          
          entityManager.getTransaction().begin();
          
          db.AllevamentoI alleva = entityManager.find(db.AllevamentoI.class,id);
          entityManager.remove(alleva);
          
          entityManager.getTransaction().commit();
          
          
          Connessione.getInstance().chiudi();
        
    }
     
    
     
    public void aggiornaRiga(long id)
    {
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "id passato  " + id);
        Object originalKey = dataTable1.getRowKey();
        String[] riga = (String[])originalKey;
        
        System.out.println(riga[0] + "  " + riga[1]);
        
            for (Object selectionKey : selection) {

                dataTable1.setRowKey(selectionKey);

                if (dataTable1.isRowAvailable()) {

                    String searchedItem = (String) dataTable1.getRowData();
                    
                    System.out.println("trovato " + searchedItem);
                    
                    //dataBase.remove(searchedItem);

                }

            }
            
            
            
        /* int index = this.getDataTable1().getRowIndex(); 
        
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " indice " + index);
         RecordAllevamento1 alleva= (RecordAllevamento1) this.getDataTable1().getRowData(); 
         
         
         System.out.println("+++aggiorna riga id " + alleva.getId() +"  numero capi " +  alleva.getNumCapiSpecieStab() +" peso vivo  " + alleva.getPesoVivo() + " specie " + alleva.getDesSpecie() + " categoria " + alleva.getDesCatAllev() + " allevamento "+ alleva.getDesAllevamento() +" stabulazione " + alleva.getDesStabulazione() + " idscenario " + this.getDettCuaa().getIdscenario() + " indice riga " + index);
         
          FacesMessage message = null;
          FacesContext context = null;
         
          if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
          
        dataItem = (RecordAllevamento1) getDataTable1().getRowData();
       
       if(dataItem == null)
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi.getClientId(context), message);
             return ;
             
         }
       
        //
         // verifico che il campo numero capi della pagina allevamento non sia vuoto o contenga
          // valori non numerici o contenga virgole o punti o inizi con lo 0
          //
       /*  Pattern pattern = Pattern.compile("[^0-9]+|^[0]");
         Matcher matcher = pattern.matcher(dataItem.getNumCapiSpecieStab());
         
         if(dataItem.getNumCapiSpecieStab() == null || dataItem.getNumCapiSpecieStab().trim().length() == 0 || matcher.find())
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè dataitem della riga è nullo");
             message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi.getClientId(context), message);
             return ;
         }
         
         
         if(dataItem.getDesSpecie() == null || dataItem.getDesSpecie().isEmpty() || dataItem.getDesCatAllev() == null || dataItem.getDesCatAllev().isEmpty() ||  dataItem.getDesAllevamento() == null || dataItem.getDesAllevamento().isEmpty() || dataItem.getDesStabulazione() == null || dataItem.getDesStabulazione().isEmpty() || Integer.parseInt(dataItem.getNumCapiSpecieStab()) < 1 || dataItem.getPesoVivo() == 0)
         {
             
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi.getClientId(context), message);
             return ;
             
         }
        
         
        
         
         
        /**
         * recupero il numero id dell'ultimo allevamento presente
         */
      /*  Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",dataItem.getDesSpecie());
                        db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                        
                        q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", dataItem.getDesCatAllev());
                        db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                        
                        q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", dataItem.getDesAllevamento());
                        db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                        
                        q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione",dataItem.getDesStabulazione());
                        db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getSingleResult();
                        
                         q = entityManager.createQuery("SELECT DISTINCT l  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                        q.setParameter(1, sp);
                        q.setParameter(2, cat);
                        q.setParameter(3, tipoalleva);
                        q.setParameter(4, tipostabu);
                        
                        db.SpeciecategoriaallevamentostabulazionebS scas = (db.SpeciecategoriaallevamentostabulazionebS)q.getSingleResult();
        /**
         * cerco l'azienda che ha il cuaa specificato e da quell'azienda recupero gli allevamenti
         */
       /* q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
        q.setParameter(1 , getDettCuaa().getIdscenario());
        
        System.out.println("scenario in listallevamenti " + getDettCuaa().getIdscenario());
        
        
        db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
        List<db.AllevamentoI> listaAllevamenti = (List<db.AllevamentoI>)sc.getAllevamentoICollection();
        
        ListIterator<db.AllevamentoI> iterAllevamenti = listaAllevamenti.listIterator();
        
        db.AllevamentoI alle = null;
        
        while(iterAllevamenti.hasNext())
        {
            alle = iterAllevamenti.next();
            
            /**
             * confronto gli id degli allevamenti con l'id del record nel datable selezionato
             */
         /*   if(alle.getId() == alleva.getId())
            {
              break;
            }
        }
        
       
        
        /**
         * 
         * da cancellare
         */
      /*  System.out.println(this.getClass().getCanonicalName() + " ----------------------------------aggiorna record  idscenario " + sc.getIdscenario());
        
        if(alle != null)
        {
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
         alle.setNumCapiSpecieStab(Integer.parseInt(alleva.getNumCapiSpecieStab()));
                alle.setSpeciecategoriaallevamentostabulazionebId(scas);
        entityManager.persist(alle);
        tx.commit();
        }
        
        
        //entityManager.close();
        
        Connessione.getInstance().chiudi();*/
    }

    /**
     * @return the bottoneaggiungi
     */
    public UIComponent getBottoneaggiungi() {
        return bottoneaggiungi;
    }

    /**
     * @param bottoneaggiungi the bottoneaggiungi to set
     */
    public void setBottoneaggiungi(UIComponent bottoneaggiungi) {
        this.bottoneaggiungi = bottoneaggiungi;
    }

    /**
     * @return the dataTable1
     */
    public UIExtendedDataTable getDataTable1() {
        return dataTable1;
    }

    /**
     * @param dataTable1 the dataTable1 to set
     */
    public void setDataTable1(UIExtendedDataTable dataTable1) {
        this.dataTable1 = dataTable1;
    }

    /**
     * @return the selection
     */
    public Collection<Object> getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(Collection<Object> selection) {
        this.selection = selection;
    }

    /**
     * @return the selectionMode
     */
    public String getSelectionMode() {
        return selectionMode;
    }

    /**
     * @param selectionMode the selectionMode to set
     */
    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    /**
     * @return the dettCuaa
     */
    public DettaglioCuaa getDettCuaa() {
        return dettCuaa;
    }

    /**
     * @param dettCuaa the dettCuaa to set
     */
    public void setDettCuaa(DettaglioCuaa dettCuaa) {
        this.dettCuaa = dettCuaa;
    }

    /**
     * @return the idallevamento
     */
    public long getIdallevamento() {
        return idallevamento;
    }

    /**
     * @param idallevamento the idallevamento to set
     */
    public void setIdallevamento(long idallevamento) {
        this.idallevamento = idallevamento;
    }

    /**
     * @return the allevamentoEdit
     */
    public RecordAllevamento1 getAllevamentoEdit() {
        return allevamentoEdit;
    }

    /**
     * @param allevamentoEdit the allevamentoEdit to set
     */
    public void setAllevamentoEdit(RecordAllevamento1 allevamentoEdit) {
        this.allevamentoEdit = allevamentoEdit;
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
    
    public void store(){
    System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.allevamentoEdit.getId() + " categoria " + this.allevamentoEdit.getDesCatAllev() + " specie " + this.allevamentoEdit.getDesSpecie());
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
         
        // db.Appezzamento appT = entityManager.find(db.Appezzamento.class,this.appezzamentoEdit.getId());
         db.AllevamentoI  alle = entityManager.find(db.AllevamentoI.class,(long)this.allevamentoEdit.getId());
         Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",this.allevamentoEdit.getDesSpecie());
                        db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                       // Integer cospecie = sp.getCodSpecie();
                        
                        q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", this.allevamentoEdit.getDesCatAllev());
                        db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                        //Integer cocat = cat.getId();
                        
                        q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", this.allevamentoEdit.getDesAllevamento());
                        db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                        //Integer coalleva = tipoalleva.getId();
                        
                        q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione",this.allevamentoEdit.getDesStabulazione());
                        db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getSingleResult();
                        
           q = entityManager.createQuery("SELECT DISTINCT l  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                        q.setParameter(1, sp);
                        q.setParameter(2, cat);
                        q.setParameter(3, tipoalleva);
                        q.setParameter(4, tipostabu);
                        
                        db.SpeciecategoriaallevamentostabulazionebS scas = (db.SpeciecategoriaallevamentostabulazionebS)q.getSingleResult();
                
                       
                        
                        alle.setNumCapiSpecieStab(Integer.parseInt(this.allevamentoEdit.getNumCapiSpecieStab()));
                        alle.setSpeciecategoriaallevamentostabulazionebId(scas);
                        
                        entityManager.getTransaction().begin();
                         entityManager.persist(alle);
                        entityManager.getTransaction().commit();
         Connessione.getInstance().chiudi();
    }
}
