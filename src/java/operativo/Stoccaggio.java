/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
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
@ManagedBean(name="stoccaggio")
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
    public Stoccaggio() {
        
        
       
        tabella.clear();
         
       // System.out.println("sono nel costruttore di stoccaggio");
      
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
        dettCuaa = new DettaglioCuaa();
        
       // System.out.println("dettaglio cuaa " + dettCuaa.getCuaa() + " nome " + dettCuaa.getRagionesociale());
        
        inizializzaTabella();
        
          // initialise with one entry
       tabella.add(new RecordStoccaggio());
    }
    
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private List inizializzaTabella()
    {
            
        
        tabella.clear();
        
       
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();*/
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
         }
        /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
        entityManager.getEntityManagerFactory().getCache().evictAll();
        
        Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
       
       // DettaglioCuaa detto = new DettaglioCuaa();
      
        db.ScenarioI result = (db.ScenarioI)q.getSingleResult();
        List<db.StoccaggioI> stoccaggi = (List<db.StoccaggioI>)result.getStoccaggioICollection();
      
      //List<db.Inputstoccaggis> results=(List<db.Inputstoccaggis>)result.get(0).getInputstoccaggisCollection();
        
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
                 recordStocca.setSuperficiescoperta(a.getSuperficiescoperta());
             }
                  
                  if(a.getSuperficietotale() != null) {
                 recordStocca.setSuperficietotale(a.getSuperficietotale());
             } 
                  
                  if(a.getCapacita() != null) {
                 recordStocca.setCapacita(a.getCapacita());
             }
                  
                  
             tabella.add(recordStocca);
         }
        
      
         
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
         /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
         entityManager = entityManagerFactory.createEntityManager();
         jpa = (JpaEntityManager) entityManager.getDelegate();
         serverSession = jpa.getServerSession();
         serverSession.dontLogMessages();*/

         
         
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
         }
         
         
         getDataTable().setRowIndex(ultimo - 1);

         dataItem = ((RecordStoccaggio) getDataTable().getRowData());
         
         
         Query q1 = entityManager.createNamedQuery("TipostoccaggioS.findByDescrizione").setParameter("descrizione", dataItem.getDescrizione());
         db.TipostoccaggioS tipostoccaggio = (db.TipostoccaggioS)q1.getSingleResult();
         
          Query q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
          q.setParameter(1 , dettaglioCuaa.getIdscenario());
        
          db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
          List<db.StoccaggioI> listaStoccaggi = (List<db.StoccaggioI>)sc.getStoccaggioICollection();
           
      
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        db.StoccaggioI stoccaggio = new db.StoccaggioI();
        stoccaggio.setIdscenario(sc);
        stoccaggio.setIdstoccaggio(tipostoccaggio);
        stoccaggio.setSuperficiescoperta(dataItem.getSuperficiescoperta());
        stoccaggio.setSuperficietotale(dataItem.getSuperficietotale());
        stoccaggio.setCapacita(dataItem.getCapacita());
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
        
    
         dataItem.setId(sc.getIdscenario());
       
        // entityManager.close();
         
         
         Connessione.getInstance().chiudi();
         
         return null;
    }
     
     
     
     public String deleteSelected() {
        
        boolean trovato = false;
        String cuaa="";
        long id = 0;
        
       /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
       
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
         }
        
        Iterator<RecordStoccaggio> entries =  this.tabella.iterator();
        
         
      
       
        while (entries.hasNext()) {
                RecordStoccaggio person = entries.next();
               
                if (person.isSelected()) {
                        trovato = true;
                        cuaa = person.getCuaa();
                        id = person.getId();
                         System.out.println("selected true    from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato + " " + person.getCuaa() + " " + person.getId());
                        
                        entries.remove();
                        
                       
                        
                }
        }
        
        
        
    
        if(trovato)
        {
            
          
            
            entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("DELETE FROM StoccaggioI l WHERE l.id  ="+id);
        q.executeUpdate();
        
        
        entityManager.getTransaction().commit();
        
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
}
