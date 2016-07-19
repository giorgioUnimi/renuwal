/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="recordAzienda")
@ViewScoped
public class RecordAzienda {
    
      
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
  
    private String cuaa;
    private String ragionesociale;
    private String comune;
    private String provincia;
   // private List<String> scenari = new LinkedList<String>();

    /**
     * Data un azienda recuper agli scenari di un azienda.
     * Ogni scenario consiste di allevamenti,stoccaggi e acque di stoccaggio
     * @return the scenari
     */
   /* public List<String> getScenari() {
        
        
        entityManagerFactory = Persistence.createEntityManagerFactory("testG8");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        
        Query q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",this.cuaa);
        db.AziendeI azienda = (db.AziendeI)q.getResultList().get(0);
        for(db.ScenarioI scenario:azienda.getScenarioICollection())
        {
           // scenario.
        }
        
         entityManager.close();
         
         
         entityManagerFactory.close();
        return scenari;
    }

    /**
     * @param scenari the scenari to set
     */
   /* public void setScenari(List<String> scenari) {
        this.scenari = scenari;
    }*/

    /**
     * @return the cuaa
     */
    public String getCuaa() {
        return cuaa;
    }

    /**
     * @param cuaa the cuaa to set
     */
    public void setCuaa(String cuaa) {
        this.cuaa = cuaa;
    }

    /**
     * @return the ragionesociale
     */
    public String getRagionesociale() {
        return ragionesociale;
    }

    /**
     * @param ragionesociale the ragionesociale to set
     */
    public void setRagionesociale(String ragionesociale) {
        this.ragionesociale = ragionesociale;
    }

    /**
     * @return the comune
     */
    public String getComune() {
        return comune;
    }

    /**
     * @param comune the comune to set
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
    
    
}
