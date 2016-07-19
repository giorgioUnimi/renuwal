/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="gestioneAziende")
@ViewScoped
public class GestioneAziende implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
  

    private List<db.AziendeI> tabella= new ArrayList<db.AziendeI>();
    
    private String username;
    private String password;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    
    
    
    public GestioneAziende()
    {
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
      db.Utenti ut = null;
      if(q1.getResultList().size()!= 0) {
            ut = (db.Utenti)q1.getResultList().get(0);
        }else
      {
          return;
      }
      
      
      /**
       * seleziono anche l'utente admin sotto cui ci sono tutte le aziende
       */
      /*q1 = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.password='19visto45iia' AND u.username='admin'");
      
      db.Utenti ut1 = null;
      
      ut1 = (db.Utenti)q1.getSingleResult();*/
      
      /**
       * se l'utente rilevato è un super user mostragli tutte le aziende altrimenti solo 
       * le sue
       */
      Query q = null;
     /* if(ut.get(0).getSuperuser())
      {
          q = entityManager.createQuery("SELECT a from AziendeI a WHERE a.idUtente IS NOT NULL");
      }else
      {
         q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.idUtente.password=?1 AND a.idUtente.username=?2");
         q.setParameter(1, utenti.getPassword());
         q.setParameter(2, utenti.getUsername());
      }  */
      
      
      q = entityManager.createQuery("SELECT a from AziendeI a where a.cuaa != 'predefinito' and  a.idUtente=?1");
      q.setParameter(1, ut);
     // q.setParameter(2, ut1);
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
     
    /**
     * metedo associato all'evento click suil bottone elimina
     */
    public void test()
    {
        //Map<String,String> params =  FacesContext.getExternalContext().getRequestParameterMap();
        Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String cuaavero = params.get("cuaavero");
        String cuaafinto = params.get("cuaafinto");
        
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " cuaa vero " + cuaavero + " cuaafinto " + cuaafinto);
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
          
          
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("DELETE FROM AziendeI az WHERE az.cuaa='"+cuaavero+"' ");
        //List<db1.Aziende> results = q.getResultList();
        q.executeUpdate();
        //return ""; // Navigation case.
        
        entityManager.getTransaction().commit();
    }
    
}
