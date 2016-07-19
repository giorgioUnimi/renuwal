/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
/**
 *
 * @author giorgio
 */
@ManagedBean(name="recordStoccaggio")
@ViewScoped
public class RecordStoccaggio {
    
    private double superficiescoperta;
    private double superficietotale;
    private double capacita;
    private String cuaa;
    private Integer idstoccaggio;
    private Long id;
    private String descrizione;
    private ArrayList descrizioneLista = new ArrayList();
     private transient boolean selected = false;
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    /**
     * @return the superficiescoperta
     */
    public double getSuperficiescoperta() {
        return superficiescoperta;
    }

    /**
     * @param superficiescoperta the superficiescoperta to set
     */
    public void setSuperficiescoperta(double superficiescoperta) {
        this.superficiescoperta = superficiescoperta;
    }

    /**
     * @return the superficetotale
     */
    public double getSuperficietotale() {
        return superficietotale;
    }

    /**
     * @param superficetotale the superficetotale to set
     */
    public void setSuperficietotale(double superficietotale) {
        this.superficietotale = superficietotale;
    }

    /**
     * @return the capacita
     */
    public double getCapacita() {
        return capacita;
    }

    /**
     * @param capacita the capacita to set
     */
    public void setCapacita(double capacita) {
        this.capacita = capacita;
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

    /**
     * @return the idstoccaggio
     */
    public Integer getIdstoccaggio() {
        return idstoccaggio;
    }

    /**
     * @param idstoccaggio the idstoccaggio to set
     */
    public void setIdstoccaggio(Integer idstoccaggio) {
        this.idstoccaggio = idstoccaggio;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the descrizioneLista
     */
    public ArrayList getDescrizioneLista() {
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal1");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        serverSession.dontLogMessages();*/
        
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal1");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }


       

        Query q = entityManager.createQuery("SELECT l.descrizione FROM TipostoccaggioS l");
        List<String> results = q.getResultList();


        // for(int i = 0; i < results.size();i++)
        //     System.out.println(results.get(i));

        //List dropList = new ArrayList();
        this.descrizioneLista = new ArrayList();
        //SelectItem selectItemForDropList = new SelectItem();
        try {
            /*dropList.add(new SelectItem(String.valueOf("Alabama"), "Alabama"));
            dropList.add(new SelectItem(String.valueOf("Alaska"), "Alaska"));
            dropList.add(new SelectItem(String.valueOf("Arizona"), "Arizona"));*/


            for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i);
                this.descrizioneLista.add(new SelectItem(String.valueOf(temp), temp));
            }


        } catch (Exception e) {
//do something
        }
       
        
        
        
        //entityManager.close();
      //  entityManagerFactory.close();
        
        
        Connessione.getInstance().chiudi();
        
      
        
        
        
        return descrizioneLista;
    }

    /**
     * @param descrizioneLista the descrizioneLista to set
     */
    public void setDescrizioneLista(ArrayList descrizioneLista) {
        this.descrizioneLista = descrizioneLista;
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
}
