/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.Iterator;
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
    
    private RecordDistribuzione findAppezzamento(String coltura,int idrotazione)
    {
        RecordDistribuzione item = null;
        
        ListIterator<RecordDistribuzione> iterrecordDistribuzione = this.appezzamenti.listIterator();
        RecordDistribuzione tempRecord;
        
        while(iterrecordDistribuzione.hasNext())
        {
           tempRecord = iterrecordDistribuzione.next();
           
          // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " cerco " + coltura + " rotazione " + idrotazione +" attuale coltura " +tempRecord.getColtura() + " attuale rotazione " +tempRecord.getRotazione());
           
           if(tempRecord.getColtura().equals(coltura) && tempRecord.getRotazione() == idrotazione)
           {
               item = tempRecord;
              // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " cerco " + coltura + " rotazione " + idrotazione +" attuale coltura " +tempRecord.getColtura() + " attuale rotazione " +tempRecord.getRotazione() + " ----------trovato------------------");

               break;
           }
        }
        
      
        
        return item;
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
            entityManager = connessione.apri("renuwal2");
         }
         
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", getDettaglioCuaa().getScenario());
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);         
       Iterator<db.Appezzamento> iterAppezzamento = sceT.getAppezzamentoCollection().iterator();
       
       boolean nuovo=false;
       while(iterAppezzamento.hasNext())
       {
           db.Appezzamento appT = iterAppezzamento.next();
           Iterator<db.Storicocolturaappezzamento> storicoTemp = appT.getStoricocolturaappezzamentoCollection().iterator();
           while(storicoTemp.hasNext())
           {
               db.Storicocolturaappezzamento storicoT = storicoTemp.next();
               //storicoT contiene le rotazioni di uno specifico appezzamento
               //se se una rotazione non è impostata contiene la coltura 1
               if(storicoT.getIdcolturaId().getId() == 1) {
                   continue;
               }
               //cerco se quella coltura con quella rotazione sono gia presenti nella lista
               //cosi da sommare la superficie di appezzamenti che hanno la stessa coltura e rotazione
               RecordDistribuzione recordDistribuzione = this.findAppezzamento(storicoT.getIdcolturaId().getDescrizione(), storicoT.getRotazioneId().getId());
               
               
               //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " tan l bov  " +sceT.getRisultatoConfronto().getTanLBov() + " scenario " + sceT.getRisultatoConfronto().getIdScenario());

               
               if(recordDistribuzione == null)
               {
                   
                   recordDistribuzione = new  RecordDistribuzione(sceT.getRisultatoConfronto(),storicoT.getIdcolturaId());
                   nuovo = true;
               }else
               {
                   nuovo = false;
               }
               
               
               
               
               recordDistribuzione.setNomeAppezzamento(appT.getNome());
               recordDistribuzione.setSuperficie(recordDistribuzione.getSuperficie() + appT.getSuperficie());
               recordDistribuzione.setUpa(appT.getUpa());
               recordDistribuzione.setColtura(storicoT.getIdcolturaId().getDescrizione());
               recordDistribuzione.setResa_attesa(storicoT.getResaAttesa());
               recordDistribuzione.setAsportazioniNKgsuHA(storicoT.getAsportazioneazoto());
               recordDistribuzione.setAsportazioniNKg(storicoT.getAsportazioneazoto() * recordDistribuzione.getSuperficie());
               
               recordDistribuzione.setAsportazioniPKgsuHA(storicoT.getAsportazionefosforo());
               recordDistribuzione.setAsportazioniPKg(storicoT.getAsportazionefosforo() * recordDistribuzione.getSuperficie());
               
               recordDistribuzione.setAsportazioniKKgsuHA(storicoT.getAsportazionepotassio());
               recordDistribuzione.setAsportazioniKKg(storicoT.getAsportazionepotassio() * recordDistribuzione.getSuperficie());
               
               recordDistribuzione.setRotazione(storicoT.getRotazioneId().getId());
               if(nuovo) {
                   this.appezzamenti.add(recordDistribuzione);
               }
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
