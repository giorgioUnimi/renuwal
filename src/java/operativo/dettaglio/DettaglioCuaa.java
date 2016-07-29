/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo.dettaglio;

//import ager.trattamenti.Trattamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.Utente;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;






@ManagedBean(name = "dettaglioCuaa")
@SessionScoped
/**
 *
 * @author giorgio
 */
public class DettaglioCuaa implements Serializable{

    
    private static final long serialVersionUID = 1L;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    //id dell azienda ovvero la chiave della tabella aziende
    private Integer id;
    private String cuaa;
    private String cuaa_finto;
    private String utente;
    private String ragionesociale;
    private String ragionesociale_finto;
    private String comune;
    private int codicespecie;
    private String codicecategoria;
    private int codicestabulazione;
    private int scenario;
    private String scenarioString;
    private String descrizionescenario;
    private Long idscenario;
    private db.AlternativeS alternativa;
    private int percorso;
    private String anno  ;
    //id di aziende_anni
    //private String idanno;
    //anni per l'inserimento
    private List<db.Anni> anni;
       
    private static String numeroRandom = "";
    /**
     * il valore numerico dell alternativa che va in esecuzione
     */
    private int alternativaN = 0;
        
    public DettaglioCuaa(){
         
     }
     
     /**
     * @return the id
     */
    public Integer getId() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.id);
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.id);
       this.id = id;
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
     * @return the cuaa_finto
     */
    public String getCuaa_finto() {
        return cuaa_finto;
    }

    /**
     * @param cuaa_finto the cuaa_finto to set
     */
    public void setCuaa_finto(String cuaa_finto) {
        this.cuaa_finto = cuaa_finto;
    }

    /**
     * @return the utente
     */
    public String getUtente() {
        return utente;
    }

    /**
     * @param utente the utente to set
     */
    public void setUtente(String utente) {
        this.utente = utente;
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
     * @return the ragionesociale_finto
     */
    public String getRagionesociale_finto() {
        return ragionesociale_finto;
    }

    /**
     * @param ragionesociale_finto the ragionesociale_finto to set
     */
    public void setRagionesociale_finto(String ragionesociale_finto) {
        this.ragionesociale_finto = ragionesociale_finto;
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
     * @return the codicespecie
     */
    public int getCodicespecie() {
        return codicespecie;
    }

    /**
     * @param codicespecie the codicespecie to set
     */
    public void setCodicespecie(int codicespecie) {
        this.codicespecie = codicespecie;
    }

    /**
     * @return the codicecategoria
     */
    public String getCodicecategoria() {
        return codicecategoria;
    }

    /**
     * @param codicecategoria the codicecategoria to set
     */
    public void setCodicecategoria(String codicecategoria) {
        this.codicecategoria = codicecategoria;
    }

    /**
     * @return the codicestabulazione
     */
    public int getCodicestabulazione() {
        return codicestabulazione;
    }

    /**
     * @param codicestabulazione the codicestabulazione to set
     */
    public void setCodicestabulazione(int codicestabulazione) {
        this.codicestabulazione = codicestabulazione;
    }

    /**
     * @return the scenario
     */
    public int getScenario() {
        return scenario;
    }

    /**
     * @param scenario the scenario to set
     */
    public void setScenario(int scenario) {
        this.scenario = scenario;
    }  

    /**
     * @return the descrizionescenario
     */
    public String getDescrizionescenario() {
        return descrizionescenario;
    }

    /**
     * @param descrizionescenario the descrizionescenario to set
     */
    public void setDescrizionescenario(String descrizionescenario) {
        this.descrizionescenario = descrizionescenario;
    }

    /**
     * @return the idscenario
     */
    public Long getIdscenario() {
        return idscenario;
    }

    /**
     * @param idscenario the idscenario to set
     */
    public void setIdscenario(Long idscenario) {
        this.idscenario = idscenario;
    }

    /**
     * @return the alternativa
     */
    public db.AlternativeS getAlternativa() {
        return alternativa;
    }

    /**
     * @param alternativa the alternativa to set
     */
    public void setAlternativa(db.AlternativeS alternativa) {
        this.alternativa = alternativa;
    }

    /**
     * @return the percorso
     */
    public int getPercorso() {
        return percorso;
    }

    /**
     * @param percorso the percorso to set
     */
    public void setPercorso(int percorso) {
        this.percorso = percorso;
    }   

    /**
     * @return the numeroRandom
     */
    public static String getNumeroRandom() {
        return numeroRandom;
    }

    /**
     * @param numeroRandom the numeroRandom to set
     */
    public static void setNumeroRandom(String numeroRandom) {
        DettaglioCuaa.numeroRandom = numeroRandom;
    }

    /**
     * @return the alternativaN
     */
    public int getAlternativaN() {
        return alternativaN;
    }

    /**
     * @param alternativaN the alternativaN to set
     */
    public void setAlternativaN(int alternativaN) {
        this.alternativaN = alternativaN;
    }

    /**
     * @return the scenarioString
     */
    public String getScenarioString() {
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+ Thread.currentThread().getStackTrace()[1].getMethodName() + " scenario string " + this.scenarioString);
        return scenarioString;
    }

    /**
     * @param scenarioString the scenarioString to set
     */
    public void setScenarioString(String scenarioString) {
        this.scenarioString = scenarioString;
    }

    /**
     * @return the anno
     */
    public String getAnno() {
        return anno;
    }

    /**
     * @param anno the anno to set
     */
    public void setAnno(String anno) {
        this.anno = anno;
    }

    /**
     * interroga il db renuwal1 e recupera la lista degli
     * anni 
     * @return the anni
     */
    public List<db.Anni> getAnni() {
        
         if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("renuwal1");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
         entityManager.clear();
        
        /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
        entityManager.getEntityManagerFactory().getCache().evictAll();
       
        
        /**
         * verifico che il cuaa non sia già presente nel db 
         */
        Query  q = entityManager.createNamedQuery("Anni.findAll");
        this.anni = (List<db.Anni>)q.getResultList();
        
        
        return anni;
    }

    /**
     * @param anni the anni to set
     */
    public void setAnni(List<db.Anni> anni) {
        this.anni = anni;
    }

   
    
    /*public  String getRandomNumber1(int digCount) {
        Random rr = new Random();


        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++) {
            sb.append((char) ('0' + rr.nextInt(10)));
        }
        
        
        System.out.println(Thread.currentThread().getStackTrace()[0].getClassName() + " random numerber " + sb.toString());
        
        return sb.toString();
    }*/
    
   
    

    
    
    
}
