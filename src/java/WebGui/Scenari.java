/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebGui;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;

/**
 *
 * @author giorgio
 */
public class Scenari {
     private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private db.AziendeI azienda;
    
    private LinkedList<db.ScenarioI> listaScenari;
    private String cuaa ;
    
    
    DettaglioCuaa dettaglioCuaa;
    
    public Scenari()
    {
        dettaglioCuaa = new DettaglioCuaa();
        
        
        
    }

    /**
     * @return the azienda
     */
    public db.AziendeI getAzienda() {
        return azienda;
    }

    /**
     * @param azienda the azienda to set
     */
    public void setAzienda(db.AziendeI azienda) {
        this.azienda = azienda;
        
        System.out.println(this.getClass().getCanonicalName() + " setazienda " +dettaglioCuaa.getUtente() );
        
        if(dettaglioCuaa.getUtente().equals("azienda1"))
                this.setCuaa(azienda.getCuaa() +"-" +azienda.getRagioneSociale());
        else
                this.setCuaa(azienda.getCuaaFinto()+"-" +azienda.getRagioneSocialeFinto());
        /* if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("provagiorgio13");
         }*/
        
        /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
       //entityManager.getEntityManagerFactory().getCache().evictAll();
        
      /* Query q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",azienda.getCuaa());
                 
        
       //q.setParameter(2, this.getScenario());
       List<db.ScenarioI> sce1 = (List<db.ScenarioI>)q.getResultList();*/
       
      
       db.ScenarioI sce = null;
        listaScenari = new LinkedList<db.ScenarioI>();  
           /**
            * popolo la lista degli scenari sulla base del cuaa
            * 
            */
           Iterator<db.AziendeAnni> iterAziendaAnni = azienda.getAziendeAnniCollection().iterator();//sce1.listIterator();
           Iterator<db.ScenarioI>  iterScenari = null;
           while (iterAziendaAnni.hasNext()) {
               
                iterScenari = iterAziendaAnni.next().getScenarioICollection().iterator();
                
                while(iterScenari.hasNext())
                     getListaScenari().add(sce);
                          
               
           }
        
        
        
        
        
    }

    /**
     * @return the listaScenari
     */
    public LinkedList<db.ScenarioI> getListaScenari() {
        return listaScenari;
    }

    /**
     * @param listaScenari the listaScenari to set
     */
    public void setListaScenari(LinkedList<db.ScenarioI> listaScenari) {
        this.listaScenari = listaScenari;
    }

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
    
    
    
}
