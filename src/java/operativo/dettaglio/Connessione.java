/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo.dettaglio;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *Si occupa di gestire la connessione con il database 
 * @author giorgio
 */
public class Connessione {
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    /**
     * nome della connesione da usare in glassfish per collegarsi al databse postgresql
     * nome della persistence unit definita nel file persistence.xml
     */
    private String nomeconnessione ="";
    
    
    
    private static Connessione connessione;
    private Connessione(){}
    
    public static Connessione getInstance()
    {
        if(connessione == null)
        {
            connessione = new Connessione();
        }
        
        return connessione;
    }
    
    
    
    /**
     * @return the nomeconnessione
     */
    public String getNomeconnessione() {
        return nomeconnessione;
    }

    /**
     * @param nomeconnessione the nomeconnessione to set
     */
    public void setNomeconnessione(String nomeconnessione) {
        this.nomeconnessione = nomeconnessione;
    }
    
    /**
     * apro la connessione il db postgresql
     * @param connessione "renuwal1"
     */
    public synchronized EntityManager apri(String connessione)
    {
        
        
        
       // if (getEntityManagerFactory() == null || !(entityManagerFactory.isOpen()) ) {
            entityManagerFactory = Persistence.createEntityManagerFactory(connessione);
            entityManager = getEntityManagerFactory().createEntityManager();
            jpa = (JpaEntityManager) entityManager.getDelegate();
            serverSession = jpa.getServerSession();
                                
        //}
        
        return entityManager;
    }
    
    /**
     * chiuso la connesione con il db postgresql
     */
    public synchronized void chiudi()
    {
        if(getEntityManagerFactory().isOpen())
        {
            entityManager.close();
            getEntityManagerFactory().close();
        }
    }
    
    
    /**
     * chiuso la connesione con il db postgresql(test per risolvere il prblema della sincronizzazione)
     */
    public synchronized void chiudi(boolean stato)
    {
        if(getEntityManagerFactory().isOpen())
        {
            entityManager.close();
            getEntityManagerFactory().close();
        }
    }
    
    /**
     * metedo in sostituzione dei due metodi di accesso al database apri e chiudi che danno un problema di 
     * sincronizzazione
     * @param connessione
     * @param apri true se vglio aprire il databse false per chiuderlo
     * @return 
     */
    /*public synchronized EntityManager aprichiudi(String connessione,boolean apri)
    {
        
        if(apri)
        {
         if (getEntityManagerFactory() == null || !(entityManagerFactory.isOpen()) ) {
            entityManagerFactory = Persistence.createEntityManagerFactory(connessione);
            entityManager = getEntityManagerFactory().createEntityManager();
            jpa = (JpaEntityManager) entityManager.getDelegate();
            serverSession = jpa.getServerSession();
                                
        }
        }else
        {
         
        if(getEntityManagerFactory().isOpen())
        {
            entityManager.close();
            getEntityManagerFactory().close();
        }
        } 
         
         
        return entityManager;
    }*/
    
    
    /**
     * @return the entityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    
    
    /**
     * data un alternativa ritorna una lista dei trattamenti che sono contenuti nell'alternativa
     * @param alternativa
     * @return 
     */         
//    public List<db.TrattamentoS> recuperaTrattamenti(db.AlternativeS alternativa)         
//    {
//        /**
//         * testo se chi usa la classe ha prima aperto la connessione 
//         * con il db
//         */
//        if(this.entityManager == null)
//            return null;
//        
//        List<db.TrattamentoS> listaRitorno = new LinkedList<db.TrattamentoS>();
//        
//        /*if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
//                            {
//                               Connessione connessione = Connessione.getInstance();
//                               entityManager = connessione.apri("renuwal1");
//                               entityManagerFactory = connessione.getEntityManagerFactory();
//                            }*/
//       
//         Query q = null;
//      
//            q = entityManager.createQuery("SELECT a FROM AlternativaTrattamento a where a.alternativa =?1 ");
//            q.setParameter(1,alternativa);
//     
//       
//       List<db.AlternativaTrattamento> results = q.getResultList();
//        
//        ListIterator<db.AlternativaTrattamento> iterTrattamenti = results.listIterator();
//        
//        while(iterTrattamenti.hasNext())
//        {
//          listaRitorno.add(iterTrattamenti.next().getTrattamento());
//           
//        }
//        
//       
//        
//        return listaRitorno;
//        
//    }
    
    /**
     * ritorna una lista di variabiliModello legate ad uno specifico trattamento
     * o generali di tutte le alternative se il trattamento che gli viene pasato è null
     */
   /* public List<db.VariabiliModello> recuperaVariabiliModello(db.TrattamentoS trattamento)
    {
         /**
         * testo se chi usa la classe ha prima aperto la connessione 
         * con il db
         */
    /*    if(this.entityManager == null)
            return null;
        Query q = null;
        
        if(trattamento != null)
        {
                q = this.entityManager.createQuery("Select a From VariabiliModello a where a.trattamentoId =?1");
                q.setParameter(1,trattamento);
        }else
        {
                q = this.entityManager.createQuery("Select a From VariabiliModello a where a.trattamentoId IS NULL");
        }
        
        List<db.VariabiliModello> variabili = q.getResultList();
        
        
        return variabili;
        
        
    }*/
    
    
}
