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
@ManagedBean(name="acquastoccaggio")
@ViewScoped
public class Acquastoccaggio implements Serializable {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private static final long serialVersionUID = 1L;

    private List<db.AcquastoccaggioI> tabella= new ArrayList<db.AcquastoccaggioI>();
    
    
    private HtmlDataTable dataTable;
    private db.AcquastoccaggioI dataItem = new db.AcquastoccaggioI();
    
    /**
     * mi informa sul fatto che non è stato trovato un record nella tabella
     * acque_stoccaggi per l'azienda che ha un determinato cuaa.Se true non ci sono record
     * se false un record è stato trovato. Nel metodo aggiorna se norecord è true devo fare un inserimento
     * se false devo fare un update
     */
    private boolean norecord = false;
    /**
     * riferimento al contenitore del cuaa aziendale definito 
     * nella pagina dettaglioazienda.xhmtl
     */
    DettaglioCuaa dettCuaa = null;
    public Acquastoccaggio() {
        
        
       
      //  tabella.clear();
         
        System.out.println("sono nel costruttore di acquastoccaggio");
      
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
        dettCuaa = new DettaglioCuaa();
        
       // System.out.println("dettaglio cuaa " + dettCuaa.getCuaa() + " nome " + dettCuaa.getRagionesociale());
        
        inizializzaTabella();
        
          // initialise with one entry
       //tabella.add(new RecordStoccaggio());
    }
    
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private List inizializzaTabella()
    {
            
        
      //  tabella.clear();
        
       
        
       /* entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
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
        db.ScenarioI sce = (db.ScenarioI)q.getSingleResult();
       
        
        if(sce.getAcquastoccaggioI() != null)
        {
            tabella.add(sce.getAcquastoccaggioI());
        }
       // Query q = entityManager.createNamedQuery("AcqueStoccaggii.findAll");

        //Query q = entityManager.createQuery("SELECT l FROM Allevamentoi l ");
      //  List<db.Aziendei> result = q.getResultList();
        
      
    //  db.AcqueStoccaggii results=result.get(0).getAcqueStoccaggii();
        
    
      /*  if(results != null) {
            tabella.add(results);
            this.norecord = false;
        }else
        {
            tabella.add(new db.AcqueStoccaggii(dettCuaa.getCuaa()));
            this.norecord = true;
        }*/
         
        
       
         
       Connessione.getInstance().chiudi();
               
         
       //  return tabella;
       return null;
    }
    
    
    
       
     public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }

    /**
     * @return the tabella
     */
    public List<db.AcquastoccaggioI> getTabella() {
        
       // this.inizializzaTabella();
        
       // System.out.println("----------gettabella in stoccaggio -----------------");
        //tabella.clear();
        //inizializzaTabella();
       // tabella.add(new RecordStoccaggio());
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<db.AcquastoccaggioI> tabella) {
        //System.out.println("-----------settabella in stoccaggio --------------------");
        
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
        db.AcquastoccaggioI acque = (db.AcquastoccaggioI)this.getDataTable().getRowData();
        
        /**
         * recupero il record a cui fa riferimento la struttura acque di stoccaggio 
         * secondo ilcuaa dellì'azienda
         */
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();*/
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
         }
        entityManager.getTransaction().begin();
        
         Query q = null;
        
         
        /**
         * se c'è gia un record devo ffare l'update altrimenti devo fare un nuovo inserimento
         */
       if(!this.norecord)
        { 
            q = entityManager.createQuery("Update AcquastoccaggioI l SET  l.superficiScoperte ="+acque.getSuperficiScoperte()+"  , l.acquaImpianti = " +acque.getAcquaImpianti() + " ,  l.pioggia = " + acque.getPioggia() + " , l.capLiquidi1rac ="+ acque.getCapLiquidi1rac() + " ,l.capSolidi1rac ="+acque.getCapSolidi1rac() + " , l.supLiquidi1rac ="+acque.getSupLiquidi1rac() + " , l.supSolidi1rac ="+acque.getSupSolidi1rac() + " WHERE l.idscenario = " + dettCuaa.getIdscenario());
      
            
            q.executeUpdate();
        }else
        {
          
            db.AcquastoccaggioI nuovo = new db.AcquastoccaggioI(dettCuaa.getScenario());
            nuovo.setAcquaImpianti(acque.getAcquaImpianti());
            nuovo.setCapLiquidi1rac(acque.getSupLiquidi1rac());
            nuovo.setCapSolidi1rac(acque.getCapSolidi1rac());
            nuovo.setPioggia(acque.getPioggia());
            nuovo.setSupLiquidi1rac(acque.getSupLiquidi1rac());
            nuovo.setSupSolidi1rac(acque.getSupSolidi1rac());
            nuovo.setSuperficiScoperte(acque.getSuperficiScoperte());
            
            entityManager.persist(nuovo);
        //List<db1.Aziende> results = q.getResultList();
        } 
        entityManager.getTransaction().commit();
        
       /**
        * aggiorna attulamente non fa nulla perchè non so ancora quali campi mostrare nella
        * pagina web e quindi quali sono i cnmapi che l'utente deve vedere e modificare
        */
       // entityManager.close();
        
        
        Connessione.getInstance().chiudi();
        
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
    
   
     
     
     

    /**
     * @return the dataItem
     */
    public db.AcquastoccaggioI getDataItem() {
        return dataItem;
    }

    /**
     * @param dataItem the dataItem to set
     */
    public void setDataItem(db.AcquastoccaggioI dataItem) {
        this.dataItem = dataItem;
    }
}
