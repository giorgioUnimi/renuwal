/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="pianoDistribuzione")
@ViewScoped
public class PianoDistribuzione {
    
    private List<RecordDistribuzione> appezzamenti = new LinkedList();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private DettaglioCuaa dettaglioCuaa;
    /**
     * Creates a new instance of PianoDistribuzione
     */
    public PianoDistribuzione() {
    }
    
    
    private void popolaPiano()
    {
        synchronized(this){
        /**
         * verifico se è stato impostato un valore per sceanriostring in dettaglioCuaa
         */
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            setDettaglioCuaa((DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa"));

        if (getDettaglioCuaa().getScenarioString() != null) {
            
      /**
       * verifico la connnesione con il database
       */
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
         
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", getDettaglioCuaa().getScenario());
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);         
       Iterator<db.Appezzamento> iterAppezzamento = sceT.getAppezzamentiCollection().iterator();
       
       
       while(iterAppezzamento.hasNext())
       {
           db.Appezzamento appT = iterAppezzamento.next();
           Iterator<db.StoricoColturaAppezzamento> storicoTemp = appT.getStoricoColturaAppezzamento().iterator();
           while(storicoTemp.hasNext())
           {
               db.StoricoColturaAppezzamento storicoT = storicoTemp.next();
               RecordDistribuzione recordDistribuzione = new RecordDistribuzione();
               recordDistribuzione.setNomeAppezzamento(appT.getNome());
               recordDistribuzione.setSuperficie(appT.getSuperficie());
               recordDistribuzione.setColtura(storicoT.getIdColtura().getDescrizione());
               recordDistribuzione.setResa_attesa(storicoT.getResa_attesa());
               recordDistribuzione.setAsportazioniN(storicoT.getAsportazioneAzoto());
               recordDistribuzione.setRotazione(storicoT.getRotazione().getId());
               this.appezzamenti.add(recordDistribuzione);
           }
       }
       
       Connessione.getInstance().chiudi();
       
        
      }
      
    }
    }
    
    /**
     * @return the appezzamenti
     */
    public List<RecordDistribuzione> getAppezzamenti() {
        
        if(appezzamenti.isEmpty()) {
                popolaPiano();
                
            }
        
        
        return appezzamenti;
    }

    /**
     * @param appezzamenti the appezzamenti to set
     */
    public void setAppezzamenti(List<RecordDistribuzione> appezzamenti) {
        this.appezzamenti = appezzamenti;
    }

    /**
     * @return the dettaglioCuaa
     */
    public DettaglioCuaa getDettaglioCuaa() {
        return dettaglioCuaa;
    }

    /**
     * @param dettaglioCuaa the dettaglioCuaa to set
     */
    public void setDettaglioCuaa(DettaglioCuaa dettaglioCuaa) {
        this.dettaglioCuaa = dettaglioCuaa;
    }
}
