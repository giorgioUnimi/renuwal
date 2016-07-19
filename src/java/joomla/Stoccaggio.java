/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joomla;

import operativo.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
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

/**
 *Il bean stoccaggio deve contenere una lista di recordstoccaggio per poter mostrare stoccaggio con un campo che è un
 * menu a tendina.Recordstoccaggio contiene una lista di tutti gli stoccaggi possibili.
 * @author giorgio
 */
@ManagedBean(name="stoccaggioJ")
@ViewScoped
public class Stoccaggio implements Serializable {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private static final long serialVersionUID = 1L;

    private List<RecordStoccaggio> tabella= new ArrayList<RecordStoccaggio>();
    
    
    private HtmlDataTable dataTable;
    private RecordStoccaggio dataItem = new RecordStoccaggio();
    /**
     * riferimento al contenitore del cuaa aziendale definito 
     * nella pagina dettaglioazienda.xhmtl
     */
    DettaglioCuaa dettCuaa = null;
    
    
    
    //rappresenta il bottone aggiungi nella pagina stoccaggio.xhtml
   //serve per aver eun conetesto in cui inserire il messaggio di errore 
    //eventuale
    private UIComponent bottoneaggiungi1;
    
    
    public Stoccaggio() {        
       
        tabella.clear();
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
       ELContext elContext = FacesContext.getCurrentInstance().getELContext();
          dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
    
         inizializzaTabella();
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
            entityManager = connessione.apri("renuwal1");
         }
        
         Query q1 = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", this.dettCuaa.getCuaa());
        List<db.AziendeI> aziende = (List<db.AziendeI>)q1.getResultList();
        
        /**
         * se la lista non è vuota
         */
        if(aziende.isEmpty())
        {
             this.dettCuaa.setCuaa("");
             return -1l;
        }else
        {
         /**
          * prendo la prima ed unica azienda
          */   
         db.AziendeI azienda =(db.AziendeI) aziende.get(0);
         this.dettCuaa.setCuaa(azienda.getCuaa());
         /**
         * mi serve per cancellare la cache dell'entity manager
         */ 
          entityManager.getEntityManagerFactory().getCache().evictAll();
        //System.out.println("++++++++++++   " + this.getClass().getCanonicalName() + " dettCuaa.getScenarioString() " +  dettCuaa.getScenarioString());
        //Query q = entityManager.createNamedQuery("AziendeI.findByCuaa") .setParameter("cuaa", dettCuaa.getCuaa());
         Query q2 = entityManager.createQuery("Select s from ScenarioI s where s.cuaa = ?1 and s.id = ?2");
         q2.setParameter(1, azienda);
         q2.setParameter(2,Integer.parseInt(this.dettCuaa.getScenarioString()));
         List<db.ScenarioI> scenari = (List<db.ScenarioI>)q2.getResultList();
         
         if(!scenari.isEmpty())
         {
             db.ScenarioI scenario = scenari.get(0);
            this.dettCuaa.setIdscenario(scenario.getIdscenario());
            //System.out.println(" scenari non empty cuaa " + scenario.getCuaa().getCuaa() + "  id " +scenario.getId());
         }else
         {
             this.dettCuaa.setIdscenario(-1L);
            // System.out.println(" scenari empty " + this.dettCuaa.getIdscenario());
         }
        }
         
        Connessione.getInstance().chiudi();
         
        
       // System.out.println(".............. idscenario " + this.dettCuaa.getIdscenario());
        
        return  this.dettCuaa.getIdscenario();
    }
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private List inizializzaTabella()
    {
            
        
        tabella.clear();
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
        /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
        entityManager.getEntityManagerFactory().getCache().evictAll();
        
        Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());  
      
        List<db.ScenarioI> resultList = (List<db.ScenarioI>)q.getResultList();
        db.ScenarioI result = null;
        
        if(resultList.isEmpty())
            return null;
        result = resultList.get(0);
        
        
        List<db.StoccaggioI> stoccaggi = (List<db.StoccaggioI>)result.getStoccaggioICollection();  
        
        ListIterator<db.StoccaggioI> iterStoccaggi = stoccaggi.listIterator(); 
      
        RecordStoccaggio recordStocca ;
       
        while(iterStoccaggi.hasNext())
         {
             db.StoccaggioI a = (db.StoccaggioI)iterStoccaggi.next();             

                  recordStocca = new RecordStoccaggio();  
                  
                  recordStocca.setCuaa(a.getIdscenario().getIdAziendeanni().getIdAzienda().getCuaa());
                  recordStocca.setId(a.getId());
                  recordStocca.setIdstoccaggio(a.getIdstoccaggio().getIdstoccaggio());                  
                  
                  if(a.getIdstoccaggio().getDescrizione()!= null) {
                 recordStocca.setDescrizione(a.getIdstoccaggio().getDescrizione());
             }
                  
                  if(a.getSuperficiescoperta() != null) {
                 recordStocca.setSuperficiescoperta(a.getSuperficiescoperta().toString());
             }
                  
                  if(a.getSuperficietotale() != null) {
                 recordStocca.setSuperficietotale(a.getSuperficietotale().toString());
             } 
                  
                  if(a.getCapacita() != null) {
                 recordStocca.setCapacita(a.getCapacita().toString());
             }
                  
                  
             tabella.add(recordStocca);
         }
        
     
          tabella.add(new RecordStoccaggio());
         //entityManager.close();
           
        
        Connessione.getInstance().chiudi();
        
         return tabella;
        

    }
       
     public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }

    /**
     * @return the tabella
     */
    public List<RecordStoccaggio> getTabella() {
        
       // this.inizializzaTabella();
        
        System.out.println("----------gettabella in stoccaggio -----------------");
        //tabella.clear();
        //inizializzaTabella();
       // tabella.add(new RecordStoccaggio());
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<RecordStoccaggio> tabella) {
        System.out.println("-----------settabella in stoccaggio --------------------");
        
        this.tabella = tabella;
    }
    
   
    /**
     * è associato al comando aggiorna presente nella pagina stoccaggio.xhtml
     * prende i dati dai campi testo della pagina stoccaggio ed aggiorna il record mostrato e recuperato dalla
     * tabella acque_stoccaggi 
     */
    public void aggiorna()
    {
        /**
         * recupero il contenuto del record nella pagina web
         */
      //  db.Inputstoccaggis acque = (db.Inputstoccaggis)this.getDataTable().getRowData();
        
        /**
         * recupero il record a cui fa riferimento la struttura acque di stoccaggio 
         * secondo ilcuaa dellì'azienda
         */
      
        
       //  Query q = entityManager.createNamedQuery("Inputstoccaggis.findByCuaa").setParameter("cuaa", dettCuaa.getCuaa());

     //  db.Inputstoccaggis  results =(db.Inputstoccaggis ) q.getResultList().get(0);
        
       /**
        * aggiorna attulamente non fa nulla perchè non so ancora quali campi mostrare nella
        * pagina web e quindi quali sono i cnmapi che l'utente deve vedere e modificare
        */
        
        
    }
    
    /**
     * aggiorna un record della tabella a seconda del contenuto 
     * di una determinata riga modificata nella tabella della pagina quando l'utente
     * clicca sul link "Aggionra Riga"
     */
    public void aggionraRiga()
    {
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
         
        FacesMessage message = null;
        FacesContext context = null; 
        
        /*message = new FacesMessage("");
        context = FacesContext.getCurrentInstance();
        context.addMessage(bottoneaggiungi1.getClientId(context), message);*/
        
        
                  
         dataItem = ((RecordStoccaggio) getDataTable().getRowData());
           if(dataItem == null)
         {
              System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
               message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return ;
         }
           
         
         /**
          * verifico che i campi capacita superficie scoperta e superficie totale 
          * della pagina stoccaggio non siano vuoti o contengano
          * valori non numerici o contengano virgole o punti o inizi con lo 0
          */
         Pattern pattern = Pattern.compile("[^0-9.]+|^[0]");
         Matcher matcher = pattern.matcher(dataItem.getCapacita());
         Matcher matcher1 = pattern.matcher(dataItem.getSuperficiescoperta());
         Matcher matcher2 = pattern.matcher(dataItem.getSuperficietotale());
         
         if(dataItem.getSuperficiescoperta()== null || dataItem.getSuperficiescoperta().trim().length() == 0 ||dataItem.getSuperficietotale() == null || dataItem.getSuperficietotale().trim().length() == 0 ||dataItem.getCapacita() == null || dataItem.getCapacita().trim().length() == 0 || matcher.find()|| matcher1.find()|| matcher2.find())
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè dataitem della riga è nullo");
             message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return ;
         }  
           
           
         if(dataItem.getDescrizione() == null )
         {
             
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             message = new FacesMessage(" Aggiornamento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return ;
         }
         
         Query q1 = entityManager.createNamedQuery("TipostoccaggioS.findByDescrizione").setParameter("descrizione", dataItem.getDescrizione());
         //db.TipostoccaggioS tipostoccaggio = (db.TipostoccaggioS)q1.getSingleResult();
         
         List<db.TipostoccaggioS> tipostoccaggioL = (List<db.TipostoccaggioS>)q1.getResultList();
         
         if(tipostoccaggioL != null && !tipostoccaggioL.isEmpty() )
         {    
         
         db.TipostoccaggioS tipostoccaggio = tipostoccaggioL.get(0);    
             
          Query q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
          q.setParameter(1 , dettCuaa.getIdscenario());
        
          db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
          List<db.StoccaggioI> listaStoccaggi = (List<db.StoccaggioI>)sc.getStoccaggioICollection();
           
          
          ListIterator<db.StoccaggioI> iterStocca = listaStoccaggi.listIterator();
          db.StoccaggioI sto  = null;
          while(iterStocca.hasNext())
          {
               sto = iterStocca.next();
               
               if(sto.getId() == dataItem.getId())
               {
                   break;
               }
          }
          
          if(sto != null)
          {
               EntityTransaction tx = entityManager.getTransaction();
               tx.begin();
       
        sto.setIdstoccaggio(tipostoccaggio);
        sto.setSuperficiescoperta(Double.parseDouble(dataItem.getSuperficiescoperta()));
        sto.setSuperficietotale(Double.parseDouble(dataItem.getSuperficietotale()));
        sto.setCapacita(Double.parseDouble(dataItem.getCapacita()));
       
        entityManager.persist(sto);
        tx.commit();
          }
         }
    }
    /**
     * @return the dataTable
     */
    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    /**
     * @param dataTable the dataTable to set
     */
    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }
    
     public String addRecord() {
        
         int ultimo = getDataTable().getRowCount();
         DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
         String cuaa = dettaglioCuaa.getCuaa();
         /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal1");
         entityManager = entityManagerFactory.createEntityManager();
         jpa = (JpaEntityManager) entityManager.getDelegate();
         serverSession = jpa.getServerSession();
         serverSession.dontLogMessages();*/

         
         
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
         
         
         getDataTable().setRowIndex(ultimo - 1);
         
         
          FacesMessage message = null;
          FacesContext context = null; 
         
          /*message = new FacesMessage("");
          context = FacesContext.getCurrentInstance();
          context.addMessage(bottoneaggiungi1.getClientId(context), message);*/
          
          
         dataItem = ((RecordStoccaggio) getDataTable().getRowData());
         
          if(dataItem == null)
         {
              System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             
             message = new FacesMessage(" Inserimento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return null;
            
         }
          
          
         
          
         /**
          * verifico che i campi capacita superficie scoperta e superficie totale 
          * della pagina stoccaggio non siano vuoti o contengano
          * valori non numerici o contengano virgole o punti o inizi con lo 0
          */
         Pattern pattern = Pattern.compile("[^0-9.]+|^[0]");
         Matcher matcher = pattern.matcher(dataItem.getCapacita());
         Matcher matcher1 = pattern.matcher(dataItem.getSuperficiescoperta());
         Matcher matcher2 = pattern.matcher(dataItem.getSuperficietotale());
         
         if(dataItem.getSuperficiescoperta()== null || dataItem.getSuperficiescoperta().trim().length() == 0 ||dataItem.getSuperficietotale() == null || dataItem.getSuperficietotale().trim().length() == 0 ||dataItem.getCapacita() == null || dataItem.getCapacita().trim().length() == 0 || matcher.find()|| matcher1.find()|| matcher2.find())
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè dataitem della riga è nullo");
             message = new FacesMessage(" Inserimento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return null;
         } 
          
          
          
         if(dataItem.getDescrizione() == null || Double.parseDouble(dataItem.getSuperficiescoperta()) < 1 || Double.parseDouble(dataItem.getSuperficietotale()) < 1 || Double.parseDouble(dataItem.getCapacita()) < 1  )
         {
             
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
               message = new FacesMessage(" Inserimento non corretto ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi1.getClientId(context), message);
             return null ;
         }
         
         
         
         
         Query q1 = entityManager.createNamedQuery("TipostoccaggioS.findByDescrizione").setParameter("descrizione", dataItem.getDescrizione());
         //db.TipostoccaggioS tipostoccaggio = (db.TipostoccaggioS)q1.getSingleResult();
         
         List<db.TipostoccaggioS> tipostoccaggioL = (List<db.TipostoccaggioS>)q1.getResultList();
         
          if(tipostoccaggioL != null && !tipostoccaggioL.isEmpty() )
         {    
         
         db.TipostoccaggioS tipostoccaggio = tipostoccaggioL.get(0);    
         
         //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" scenario " + dettCuaa.getIdscenario());
          Query q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
          q.setParameter(1 , dettCuaa.getIdscenario());
        
          db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
          List<db.StoccaggioI> listaStoccaggi = (List<db.StoccaggioI>)sc.getStoccaggioICollection();
           
      
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        db.StoccaggioI stoccaggio = new db.StoccaggioI();
        stoccaggio.setIdscenario(sc);
        stoccaggio.setIdstoccaggio(tipostoccaggio);
        stoccaggio.setSuperficiescoperta(Double.parseDouble(dataItem.getSuperficiescoperta()));
        stoccaggio.setSuperficietotale(Double.parseDouble(dataItem.getSuperficietotale()));
        stoccaggio.setCapacita(Double.parseDouble(dataItem.getCapacita()));
        listaStoccaggi.add(stoccaggio);
        entityManager.persist(stoccaggio);
        tx.commit();
        
        this.tabella.add(new RecordStoccaggio());
           /**
          * cerco l'azienda che ha il cuaa specificato e da quell'azienda
          * recupero gli allevamenti
          */
         //Query q = entityManager.createNamedQuery("Aziendei.findByCuaa").setParameter("cuaa", dettCuaa.getCuaa());
         /**
          * recupero il risultato della query che è una lista e prendo il primo
          * risultato perchè il cuaa è chiave di aziende
          */
       /*  List<db.Aziendei> results = q.getResultList();
         db.Aziendei aziendaT = results.get(0);*/
         
       
         
         /**
          * recupero il numero id dell'ultimo allevamento presente
          */
        
        // Query q1 = entityManager.createNamedQuery("Inputstoccaggis.findAllOrderedID");
         // int ultimoA = 1;
        // List<db.Inputstoccaggis> numeroStoccaggi = q1.getResultList();
        // if(numeroStoccaggi.size() != 0)
       //    ultimoA = numeroStoccaggi.get(0).getId() + 1;
       
         
       
         /**
          * data l'azienda prendo la collezione di allevamenti ad essa associati
          * tramite la relazione uno a molti cuaa azienda cuaa allevamento
          */
        // List<db.Inputstoccaggis> listStoccaggi = (List<db.Inputstoccaggis>) results.get(0).getInputstoccaggisCollection();


         /**
          * devo recupera l'id dalla tabella menustoccaggi usando la descrizione
          * inserita dall'utenete nel menu a tendina descrizione della pagina
          * stoccaggi. Il record recuperato mi serve per l'inserimento del nuovo record 
          * stoccaggio che vado ad inserire nella tabella inputstoccagis del db. 
          */
         
         //System.out.println("son in stoccaggio addrecord descrizione " + getDataItem().getDescrizione());
         
       //  Query q2 = entityManager.createNamedQuery("Menustoccaggis.findByDescrizione").setParameter("descrizione", getDataItem().getDescrizione());
      /*   List<db.Menustoccaggis> results2 = q2.getResultList();
         db.Menustoccaggis menusto = null;
      
             menusto = results2.get(0);*/
       
       /*  EntityTransaction tx = entityManager.getTransaction();
         tx.begin();
         
         //  System.out.println("stoccaggio add record aziendaT " + aziendaT.getCuaa());
         //     System.out.println("stoccaggio add record idstoccaggio " + menusto.getIdstoccaggio());
              System.out.println("stoccaggio add record id " + ultimoA);
         
         /**
          * creo il record allevamento e lo aggiungo alla lista degli
          * allevamenti della azienda selezionata e pesisto l'azienda
          * selezionata
          */
         //imposto tramite il costruttore l'id
       /*  db.Inputstoccaggis stoccaggio = new db.Inputstoccaggis();
         stoccaggio.setId(ultimoA);
         stoccaggio.setCuaa(aziendaT);
         stoccaggio.setDescrizionestoccaggio(getDataItem().getDescrizione());
         stoccaggio.setCapacita(getDataItem().getCapacita());
         stoccaggio.setSuperficiescoperta(getDataItem().getSuperficiescoperta());
         stoccaggio.setSuperficietotale(getDataItem().getSuperficietotale());
         stoccaggio.setIdstoccaggio(menusto);

         dataItem.setCuaa(aziendaT.getCuaa());
         dataItem.setId(ultimoA);
         dataItem.setIdstoccaggio(menusto.getIdstoccaggio());
         /**
          * aggiungo il record di allevamento impostato secondo le
          * caratteristicvhe inserite dall'utente
          */
        /* listStoccaggi.add(stoccaggio);
        
         aziendaT.setInputstoccaggisCollection(listStoccaggi);

         entityManager.persist(aziendaT);
        


         tabella.add(new RecordStoccaggio());
       
          tx.commit();

        


         entityManager.close();

       
         
*/
        
         Query q2 = entityManager.createQuery("Select s from StoccaggioI s ORDER BY s.id DESC").setMaxResults(1);
         List<db.StoccaggioI> listaUltimo = q2.getResultList();
         /*ListIterator<db.StoccaggioI> iterUltimo = listaUltimo.listIterator();
         long idultimoinserito = 0;
         while(iterUltimo.hasNext())
         {
             db.StoccaggioI stoT = iterUltimo.next();
             if(idultimoinserito < stoT.getId())
                 idultimoinserito = stoT.getId();
         }*/
         
         System.out.println("idultimoinserito " + listaUltimo.get(0).getId());
         
         dataItem.setId(listaUltimo.get(0).getId());
         //dataItem.setId(sc.getIdscenario());
       
        // entityManager.close();
         
         }
         Connessione.getInstance().chiudi();
         
         return null;
    }
     
     
     
     public String deleteSelected() {
        
        boolean trovato = false;
        String cuaa="";
        long id = 0;
        
       /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal1");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
       
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
        
        Iterator<RecordStoccaggio> entries =  this.tabella.iterator();
        
         
      
       
        while (entries.hasNext()) {
                RecordStoccaggio person = entries.next();
               System.out.println(" id " +person.getId() + " cuaa " + person.getCuaa());
                if (person.isSelected()) {
                        trovato = true;
                        cuaa = person.getCuaa();
                        id = person.getId();
                         System.out.println("selected true    from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato + " " + person.getCuaa() + " " + person.getId());
                        
                        entries.remove();
                        
                       
                        
                }
        
        
        
        
    
        if(trovato)
        {
            
          
            
            entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("DELETE FROM StoccaggioI l WHERE l.id  ="+id);
        q.executeUpdate();
        
        
        entityManager.getTransaction().commit();
        
        trovato =false;
        
        }
        
     }
        //entityManager.close();
        
        Connessione.getInstance().chiudi();
      
        return null;
    }

    /**
     * @return the dataItem
     */
    public RecordStoccaggio getDataItem() {
        return dataItem;
    }

    /**
     * @param dataItem the dataItem to set
     */
    public void setDataItem(RecordStoccaggio dataItem) {
        this.dataItem = dataItem;
    }

    /**
     * @return the bottoneaggiungi1
     */
    public UIComponent getBottoneaggiungi1() {
        return bottoneaggiungi1;
    }

    /**
     * @param bottoneaggiungi1 the bottoneaggiungi1 to set
     */
    public void setBottoneaggiungi1(UIComponent bottoneaggiungi1) {
        this.bottoneaggiungi1 = bottoneaggiungi1;
    }
}
