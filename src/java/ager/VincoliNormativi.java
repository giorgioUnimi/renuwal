/*
 * Si occupa di verificare i tre confronti per verificare la conformita normativa
 * dell'azienda dopo aver applicato l'alternativa di trattamento oppure nessuna alternativa
 * nel caso in cui abbia scelto il trattamento 0
 * confronto 1 vincolo normativo
 * confronto 2 vincolo mas
 * confronto 3 asportazione fosforo
 */
package ager;

import java.util.Iterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.RecordAppezzamento;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
public class VincoliNormativi {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    ContenitoreReflui contenitoreReflui;
    Long idscenario;
    
    public VincoliNormativi(ContenitoreReflui contenitoreReflui){
        
         this.contenitoreReflui = contenitoreReflui;       
         
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettTemp = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
         this.idscenario = dettTemp.getIdscenario();
         
    }
    
    /**
     * verifica il vincolo normativo nitrati
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloNormativo()
    {
        double ettari_zvn = 0;
        double ettari_nzvn = 0;
        double totale_n_zootecnico = 0 ;
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }        
        
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", this.idscenario);
       
       if(q.getResultList().isEmpty())
           return false;
       
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);
       Iterator<db.Appezzamento> iterAppezzamenti=sceT.getAppezzamentoCollection().iterator();
       //ciclo sugli appezzamenti per calcolare la somma degli ettari in zvn e quelli in nzvn
        while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           if(apptemp.getSvz())
           {
               ettari_zvn += apptemp.getSuperficie();
           }else
           {
              ettari_nzvn += apptemp.getSuperficie(); 
           }
           
       }
        
        //devo calcolare la somma di N per tutte le specie animali e tipi di refluo tranne che per le biomasse
        //recupero le tipologie e ciclo su di esse nel contenitoreReflui
        Refluo re = new Refluo("");
        for(String  s:this.contenitoreReflui.getTipologie())
        {
              if(!s.contains("Biomassa")) {
                totale_n_zootecnico +=contenitoreReflui.getContenitore().get(s).getAzotototale();
            }
                 
                
        }
        
        //se totale_n_zootecnico < uguale a (ettari_zvn * 170 + ettari_nzvn * 340 )
        
        double temp = totale_n_zootecnico - (ettari_zvn * 170 + ettari_nzvn *340);
        
        Connessione.getInstance().chiudi();
        if(temp < 0) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * verifica il vincolo dei Mas
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloMas()
    {
        
        return true;
    }
    /**
     * verifica il vincolo fosforo
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloFosforo(){
        
        return true;
    }
    
    
}
