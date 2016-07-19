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
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
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
import org.richfaces.component.UIDataTable;
import org.richfaces.component.AbstractExtendedDataTable;

/**
 *Il bean stoccaggio deve contenere una lista di recordstoccaggio per poter mostrare stoccaggio con un campo che è un
 * menu a tendina.Recordstoccaggio contiene una lista di tutti gli stoccaggi possibili.
 * @author giorgio
 */
@ManagedBean(name="acquastoccaggioJ")
@ViewScoped
public class Acquastoccaggio implements Serializable {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private static final long serialVersionUID = 1L;

   // private List<db.AcquastoccaggioI> tabella= new ArrayList<db.AcquastoccaggioI>();
    
    
    //private HtmlDataTable dataTable;
  //  private UIDataTable dataTable1;
  //  private db.AcquastoccaggioI dataItem = new db.AcquastoccaggioI();
    
   // private double acqua;
   // private UIInput acqua1;
   // private Collection<Object> selection;
   // private String selectionMode = "single";
    
   
    private double acquaImpianti;
    private double superfici_scoperte;
    private double pioggia;
    private double capacita_liquidi;
    private double capacita_solidi;
    private double superfici_scoperte_solidi;
    private double superfici_scoperte_liquidi;
      
    
    /**
     * mi informa sul fatto che non è stato trovato un record nella tabella
     * acque_stoccaggi per l'azienda che ha un determinato cuaa.Se true non ci sono record
     * se false un record è stato trovato. Nel metodo aggiorna se norecord è true devo fare un inserimento
     * se false devo fare un update
     */
   // private boolean norecord = false;
    /**
     * riferimento al contenitore del cuaa aziendale definito 
     * nella pagina dettaglioazienda.xhmtl
     */
    DettaglioCuaa dettCuaa = null;
    
    
    //rappresenta il bottone aggiungi nella pagina acquastoccaggio.xhtml
   //serve per aver eun conetesto in cui inserire il messaggio di errore 
    //eventuale
   // private UIComponent bottoneaggiungi2;
    
    
    public Acquastoccaggio() {
        
        
       
      //  tabella.clear();
         
        System.out.println("sono nel costruttore di acquastoccaggio");
      
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
          dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
    
      
        
       // System.out.println("dettaglio cuaa " + dettCuaa.getCuaa() + " nome " + dettCuaa.getRagionesociale());
        
       // inizializzaTabella();
          
          inizializzaStato();
        
          // initialise with one entry
       //tabella.add(new RecordStoccaggio());
    }
    
    
    private void  inizializzaStato()
    {
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
        List<db.ScenarioI> sceList = (List<db.ScenarioI>)q.getResultList();
        db.ScenarioI sce = null;
        
        if(sceList.isEmpty())
           return ;
        else
           sce = sceList.get(0);
        
        if(sce.getAcquastoccaggioI() != null)
        {
            db.AcquastoccaggioI temp = sce.getAcquastoccaggioI();
            this.setAcquaImpianti(temp.getAcquaImpianti());
            this.setSuperfici_scoperte(temp.getSuperficiScoperte());
            this.setPioggia(temp.getPioggia());
            this.setCapacita_liquidi(temp.getCapLiquidi1rac());
            this.setCapacita_solidi(temp.getCapSolidi1rac());
            this.setSuperfici_scoperte_liquidi(temp.getSupLiquidi1rac());
            this.setSuperfici_scoperte_solidi(temp.getSupSolidi1rac());
        }
    }
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
   /*private List inizializzaTabella()
    {
            
     
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
        
        /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
   /*     entityManager.getEntityManagerFactory().getCache().evictAll();
        
        Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
        List<db.ScenarioI> sceList = (List<db.ScenarioI>)q.getResultList();
        db.ScenarioI sce = null;
        
        if(sceList.isEmpty())
           return null;
        else
           sce = sceList.get(0);
        
        if(sce.getAcquastoccaggioI() != null)
        {
            tabella.add(sce.getAcquastoccaggioI());
        }
       
        if(tabella.isEmpty()) {
            tabella.add(new db.AcquastoccaggioI() );
        }
       
         
       Connessione.getInstance().chiudi();
               
         
       //  return tabella;
       return null;
    }*/
    
    
    
       
   /*  public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }

    /**
     * @return the tabella
     */
  /*  public List<db.AcquastoccaggioI> getTabella() {
        
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
  /*  public void setTabella(List<db.AcquastoccaggioI> tabella) {
        //System.out.println("-----------settabella in stoccaggio --------------------");
        
        this.tabella = tabella;
    }*/
    
    public void aggiorna()
    {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
          dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
      
          
          
        db.AcquastoccaggioI acquaSto = entityManager.find(db.AcquastoccaggioI.class,dettCuaa.getIdscenario().intValue());
        acquaSto.setAcquaImpianti(this.acquaImpianti);
        acquaSto.setSuperficiScoperte(this.superfici_scoperte);
        acquaSto.setPioggia(this.pioggia);
        acquaSto.setCapLiquidi1rac(this.capacita_solidi);
        acquaSto.setCapSolidi1rac(this.capacita_solidi);
        acquaSto.setSupLiquidi1rac(this.superfici_scoperte_liquidi);
        acquaSto.setSupSolidi1rac(this.superfici_scoperte_solidi);
        
        entityManager.getTransaction().begin();  
        
        entityManager.persist(acquaSto);
        
        entityManager.getTransaction().commit();
        
        Connessione.getInstance().chiudi();
        
    }
    /**
     * è associato al comando aggiorna presente nella pagina stoccaggio.xhtml
     * prende i dati dai campi testo della pagina stoccaggio ed aggiorna il record mostrato e recuperato dalla
     * tabella acque_stoccaggi 
     */
    /*public void aggiorna1()
    {
        /**
         * recupero il contenuto del record nella pagina web
         */
      /*  db.AcquastoccaggioI acque = (db.AcquastoccaggioI)this.getDataTable1().getRowData();
        
        
        
        System.out.println(this.getClass().getCanonicalName() + " sono in aggiorna ");
        
        FacesMessage message = null;
        FacesContext context = null; 
        
      
         
         if(acque.getAcquaImpianti()== null  ||acque.getCapLiquidi1rac() == null || acque.getCapSolidi1rac() == null ||acque.getPioggia() == null || acque.getSupLiquidi1rac() == null ||acque.getSupSolidi1rac() == null ||acque.getSuperficiScoperte() == null )
         {
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè acque della riga è nullo");
             message = new FacesMessage("L'aggiornamento non è andato a buon fine. ");
             context = FacesContext.getCurrentInstance();
             context.addMessage(bottoneaggiungi2.getClientId(context), message);
             return ;
         }  
        /**
         * recupero il record a cui fa riferimento la struttura acque di stoccaggio 
         * secondo ilcuaa dellì'azienda
         */
      
       /*  if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
        entityManager.getTransaction().begin();
        
         Query q = null;
        
         
        /**
         * se c'è gia un record devo ffare l'update altrimenti devo fare un nuovo inserimento
         */
      /* if(!this.norecord)
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
        
        } 
        entityManager.getTransaction().commit();
        
      
        
        
        Connessione.getInstance().chiudi();
        
    }*/
    
    
   /* public void selectClient()
    {
         // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " acqua  " + this.acquaImpianti + " capacita " + this.capLiquidi1rac);

    }*/
    
    
    /**
     * @return the dataTable
     */
    /*public HtmlDataTable getDataTable() {
        return dataTable;
    }*/

    /**
     * @param dataTable the dataTable to set
     */
    /*public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }*/
    
   
     
     
     

    /**
     * @return the dataItem
     */
   /* public db.AcquastoccaggioI getDataItem() {
        return dataItem;
    }*/

    /**
     * @param dataItem the dataItem to set
     */
   /* public void setDataItem(db.AcquastoccaggioI dataItem) {
        this.dataItem = dataItem;
    }*/

    /**
     * @return the bottoneaggiungi2
     */
  /*  public UIComponent getBottoneaggiungi2() {
        return bottoneaggiungi2;
    }*/

    /**
     * @param bottoneaggiungi2 the bottoneaggiungi2 to set
     */
   /* public void setBottoneaggiungi2(UIComponent bottoneaggiungi2) {
        this.bottoneaggiungi2 = bottoneaggiungi2;
    }*/

    /**
     * @return the dataTable1
     */
   /* public UIDataTable getDataTable1() {
        return dataTable1;
    }*/

    /**
     * @param dataTable1 the dataTable1 to set
     */
   /* public void setDataTable1(UIDataTable dataTable1) {
        this.dataTable1 = dataTable1;
    }*/

    /**
     * @return the acqua
     */
  /* public double getAcqua() {
        return acqua;
    }*/

    /**
     * @param acqua the acqua to set
     */
  /*  public void setAcqua(double acqua) {
        this.acqua = acqua;
    }*/

    /**
     * @return the acqua1
     */
   /* public UIInput getAcqua1() {
        return acqua1;
    }*/

    /**
     * @param acqua1 the acqua1 to set
     */
   /* public void setAcqua1(UIInput acqua1) {
        this.acqua1 = acqua1;
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
  /*  public void setSelection(Collection<Object> selection) {
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
    
   /* public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() );
        Object originalKey = dataTable.getRowKey();
      
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            
            if (dataTable.isRowAvailable()) {
                db.AcquastoccaggioI record=((db.AcquastoccaggioI) dataTable.getRowData());
               
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" " +record.getAcquaImpianti()+" " + record.getCapLiquidi1rac() + " " + record.getIdscenario());
            }
        }
        dataTable.setRowKey(originalKey);
    }*/

    /**
     * @return the capLiquidi1rac
     */
  /*  public double getCapLiquidi1rac() {
        return capLiquidi1rac;
    }*/

    /**
     * @param capLiquidi1rac the capLiquidi1rac to set
     */
   /* public void setCapLiquidi1rac(double capLiquidi1rac) {
        this.capLiquidi1rac = capLiquidi1rac;
    }
*/
    /**
     * @return the acquaImpianti
     */
    public double getAcquaImpianti() {
        return acquaImpianti;
    }

    /**
     * @param acquaImpianti the acquaImpianti to set
     */
    public void setAcquaImpianti(double acquaImpianti) {
        this.acquaImpianti = acquaImpianti;
    }

    /**
     * @return the superfici_scoperte
     */
    public double getSuperfici_scoperte() {
        return superfici_scoperte;
    }

    /**
     * @param superfici_scoperte the superfici_scoperte to set
     */
    public void setSuperfici_scoperte(double superfici_scoperte) {
        this.superfici_scoperte = superfici_scoperte;
    }

    /**
     * @return the pioggia
     */
    public double getPioggia() {
        return pioggia;
    }

    /**
     * @param pioggia the pioggia to set
     */
    public void setPioggia(double pioggia) {
        this.pioggia = pioggia;
    }

    /**
     * @return the capacita_liquidi
     */
    public double getCapacita_liquidi() {
        return capacita_liquidi;
    }

    /**
     * @param capacita_liquidi the capacita_liquidi to set
     */
    public void setCapacita_liquidi(double capacita_liquidi) {
        this.capacita_liquidi = capacita_liquidi;
    }

    /**
     * @return the capacita_solidi
     */
    public double getCapacita_solidi() {
        return capacita_solidi;
    }

    /**
     * @param capacita_solidi the capacita_solidi to set
     */
    public void setCapacita_solidi(double capacita_solidi) {
        this.capacita_solidi = capacita_solidi;
    }

    /**
     * @return the superfici_scoperte_solidi
     */
    public double getSuperfici_scoperte_solidi() {
        return superfici_scoperte_solidi;
    }

    /**
     * @param superfici_scoperte_solidi the superfici_scoperte_solidi to set
     */
    public void setSuperfici_scoperte_solidi(double superfici_scoperte_solidi) {
        this.superfici_scoperte_solidi = superfici_scoperte_solidi;
    }

    /**
     * @return the superfici_scoperte_liquidi
     */
    public double getSuperfici_scoperte_liquidi() {
        return superfici_scoperte_liquidi;
    }

    /**
     * @param superfici_scoperte_liquidi the superfici_scoperte_liquidi to set
     */
    public void setSuperfici_scoperte_liquidi(double superfici_scoperte_liquidi) {
        this.superfici_scoperte_liquidi = superfici_scoperte_liquidi;
    }
}
