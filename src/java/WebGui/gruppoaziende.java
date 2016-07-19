/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebGui;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.Utente;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name = "gruppoaziende")
@ViewScoped
public class gruppoaziende implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    //contenitore di tutte le azinde e per ogni azienda 
    //la lista degli scenari
    private LinkedList<Scenari> tabellaAziende = null;
    
    public gruppoaziende()
    {
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("provagiorgio13");
         }
       
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      Utente utenti = (Utente) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "utente");
      
      
      
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
      q1 = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.password='19visto45iia' AND u.username='admin'");
      
      db.Utenti ut1 = null;
      
      ut1 = (db.Utenti)q1.getSingleResult();
      
      
         
       //creo una nuova istanza della lista delle aziende  
       tabellaAziende = new LinkedList<Scenari>();
       //svuoto la cache di precedeenti query  
       entityManager.getEntityManagerFactory().getCache().evictAll();
        
       
       Query q = entityManager.createQuery("SELECT a from AziendeI a where a.cuaa != 'predefinito' and ( a.idUtente =?2 or a.idUtente=?1)");
        q.setParameter(1, ut);
      q.setParameter(2, ut1);
       List<db.AziendeI> aziende = ( List<db.AziendeI>)q.getResultList();
       ListIterator<db.AziendeI> iterAziende  = aziende.listIterator();
       
       Scenari dett ;
       
       while(iterAziende.hasNext())
       {
           db.AziendeI az = iterAziende.next();
           
           dett = new Scenari();
           
          // if (!az.getCuaa().equals("predefinito")) {
               
               //System.out.println("set cuaa gruppoaziende " + az.getCuaa());
               
               dett.setAzienda(az);

               tabellaAziende.add(dett);
          // }
       }
         
         
    }

    /**
     * @return the tabellaAziende
     */
    public LinkedList<Scenari> getTabellaAziende() {
        return tabellaAziende;
    }

    /**
     * @param tabellaAziende the tabellaAziende to set
     */
    public void setTabellaAziende(LinkedList<Scenari> tabellaAziende) {
        this.tabellaAziende = tabellaAziende;
    }

   
    
    
    
    
}
