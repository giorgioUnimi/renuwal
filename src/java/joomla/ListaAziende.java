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
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.server.ServerSession;


@ManagedBean(name="listaAziendeJ")
@ViewScoped
public class ListaAziende implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<db.AziendeI> tabella= new ArrayList<db.AziendeI>();
    
    private String username;
    private String password;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;

    public ListaAziende() {
        
        tabella.clear();
         
        // initialise with one entry
      // tabella.add(new Azienda());
        
        inizializzaTabella();
    }
    
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private void inizializzaTabella()
    {
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal1");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();*/
        
        
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
        
        
         //Query q = entityManager.createNamedQuery("AziendeI.findAll");
         //Query q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.idUtente.password=?1 AND a.idUtente.username=?1");
      
         /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      Utente utenti = (Utente) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "utente");
         
      
      /**
       * verifico se l'utente è o meno un superuser ovvero se un utente normale devo mostrargli 
       * solo le aziende ad esso collegate se invece è un super user devi mostrargli tutte le aziende
       */
      Query q1 = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.password=?1 AND u.username=?2");
      q1.setParameter(1,utenti.getPassword());
      q1.setParameter(2, utenti.getUsername());
      List<db.Utenti> ut = (List<db.Utenti>)q1.getResultList();
      /**
       * se l'utente rilevato è un super user mostragli tutte le aziende altrimenti solo 
       * le sue
       */
      Query q = null;
      if(ut.get(0).getSuperuser())
      {
     // System.out.println("inizializza tabella username " + utenti.getUsername() + " password  " +utenti.getPassword());
          q = entityManager.createQuery("SELECT a from AziendeI a WHERE a.idUtente IS NOT NULL");
      }else
      {
         q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.idUtente.password=?1 AND a.idUtente.username=?2");
         q.setParameter(1, utenti.getPassword());
         q.setParameter(2, utenti.getUsername());
      }  
      
      List<db.AziendeI> results = q.getResultList();


         for(int i=0; i < results.size();i++)
         {
            
             db.AziendeI a = (db.AziendeI)results.get(i);
                
           
             
             tabella.add(a);
         }
         
        /* entityManager.close();
         
         
         entityManagerFactory.close();*/
         
         
         Connessione.getInstance().chiudi();

    }
       
     public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }

    /**
     * @return the tabella
     */
    public List<db.AziendeI> getTabella() {
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<db.AziendeI> tabella) {
        this.tabella = tabella;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    
}





///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package operativo;
//
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
//import javax.faces.component.html.HtmlDataTable;
//import javax.faces.component.html.HtmlInputHidden;
//import javax.faces.model.SelectItem;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import javax.persistence.Query;
//import org.eclipse.persistence.jpa.JpaEntityManager;
//import org.eclipse.persistence.sessions.UnitOfWork;
//import org.eclipse.persistence.sessions.server.ServerSession;
//
//
//@ManagedBean(name="listaAziende")
//@ViewScoped
//public class ListaAziende implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    private List<RecordAzienda> tabella= new ArrayList<RecordAzienda>();
//    
//    
//    private EntityManagerFactory entityManagerFactory;
//    private EntityManager entityManager;
//    private JpaEntityManager jpa;
//    private ServerSession serverSession;
//
//    public ListaAziende() {
//        
//        tabella.clear();
//         
//        // initialise with one entry
//      // tabella.add(new Azienda());
//        
//        inizializzaTabella();
//    }
//    
//    
//    /**
//     * popola la tabella con il contenuto della tabella del db AziendeI
//     */
//    private void inizializzaTabella()
//    {
//        entityManagerFactory = Persistence.createEntityManagerFactory("testG8");
//        entityManager = entityManagerFactory.createEntityManager();
//        jpa = (JpaEntityManager) entityManager.getDelegate();
//        serverSession = jpa.getServerSession();
//
//         Query q = entityManager.createNamedQuery("Aziende_i.findAll");
//        //Query q = entityManager.createQuery("SELECT l FROM AziendeI l");
//        List<db.AziendeI> results = q.getResultList();
//
//
//         for(int i=0; i < results.size();i++)
//         {
//            // System.out.println("comune " + a.getComune() + " denominazione " + a.getDenominazione() + " cuaa " + a.getAziendePK().getCuaa());
//             db.AziendeI a = (db.AziendeI)results.get(i);
//                
//            
//             
//             tabella.add(a);
//         }
//         
//         entityManager.close();
//         
//         
//         entityManagerFactory.close();
//
//    }
//       
//     public UnitOfWork acquireUnitOfWork() {
//		    return serverSession.acquireClientSession().acquireUnitOfWork();
//		  }
//
//    /**
//     * @return the tabella
//     */
//    public List<db.AziendeI> getTabella() {
//        return tabella;
//    }
//
//    /**
//     * @param tabella the tabella to set
//     */
//    public void setTabella(List<db.AziendeI> tabella) {
//        this.tabella = tabella;
//    }
//    
//    
//    
//    
//}
