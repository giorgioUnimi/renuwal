/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.validation.constraints.Size;
import operativo.Utente;
import operativo.dettaglio.Connessione;
//import operativo.dettaglio.UrlDistanza;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name = "nuovaazienda")
@ViewScoped
public class NuovaAzienda implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
     @Size(min=5,max=30,message="Il cuaa deve avere una lunghezza minima di 5 caratteri e massima di 30.")
     private String cuaa;
      @Size(min=5,max=30,message="La ragione sociale deve avere una lunghezza minima di 5 caratteri e massima di 30.")
     private String ragionesociale;
       @Size(min=4,max=20,message="Il comune deve avere una lunghezza minima di 4 caratteri e massima di 20.")
     private String comune;
       @Size(min=1,max=10,message="Il codice comune deve avere una lunghezza minima di 1 caratteri e massima di 10.")
     private String codice_comune;
      @Size(min=2,max=20,message="La provincia deve avere una lunghezza minima di 4 caratteri e massima di 20.")
     private String provincia;
      @Size(min=1,max=10,message="Il codice provincia deve avere una lunghezza minima di 1 caratteri e massima di 10.")
     private String codice_provincia;
     private String coordinata_x ;
     private String coordinata_y;
     private String sauzvn;
     private String sazv;
     
     private String statoOperazione;
     private String cuaamessage;
      /**
     * variabili tuili per la connesione al db
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private boolean deroga = false;
     
     public NuovaAzienda(){}
     
     
     /**
      * metodo associato al click su bottone salva della pagina nuova azienda
      */
     public String salva()
     {
         
         
          
         
         
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " cuaa: " + this.cuaa + " rag_sociale: " + this.ragionesociale + " comune : " + this.comune + " codice_comune : " + this.codice_comune +" provincia : "+ this.provincia+" codice_provincia " + this.codice_provincia + " coordinata_x " + this.coordinata_x + " coordinata_y " + this.coordinata_y + " sauzvn  " + this.sauzvn + " sauzv  " + this.sazv);
       
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
        Query  q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.cuaa = ?1 ");
        q.setParameter(1, this.cuaa);
        if(q.getResultList().size() !=0 ) 
        {
            this.statoOperazione ="cuaa : "+ this.cuaa  +" già presente.";
            return "";
        }
           
         //creo la nuova azienda 
        //modificato il 24/11/2015 : ho messo 1111 nel costruttore solo per tacitare l'errore
         db.AziendeI newazienda = new db.AziendeI();
         //newazienda.setId(2);
         newazienda.setCuaaFinto(cuaa);
         newazienda.setCuaa(cuaa);
         newazienda.setCodComune(Integer.parseInt(this.codice_comune));
         newazienda.setDesComune(this.comune);
         newazienda.setCodiceprovincia(this.codice_provincia);
         newazienda.setDesprovincia(this.provincia);
         newazienda.setCentrox(0.0);
         newazienda.setCentroy(0.0);
         
         newazienda.setDeroga(this.deroga);
         //newazienda.setCodComunePresentazione(Integer.parseInt(this.codice_comune));
         //newazienda.setCodProvinciaPresentazione(Integer.parseInt(this.codice_provincia));
         //newazienda.setDesComunePresentazione(this.comune);
         newazienda.setRagioneSociale(ragionesociale);
         newazienda.setRagioneSocialeFinto(ragionesociale);
         
        //recupero i dati dell'utente che si è loggato per ottenrere dal db l'id dell'utente
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         Utente utenteLog = (Utente) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "utente");
         
         System.out.println("utente username " + utenteLog.getUsername() + " password  " + utenteLog.getPassword());
         
          
        
        
       
        
        
        q = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.username=?1 AND u.password=?2");
        q.setParameter(1, utenteLog.getUsername());
        q.setParameter(2, utenteLog.getPassword());
         
         db.Utenti utente = (db.Utenti)q.getSingleResult();
         newazienda.setIdUtente(utente);
           
         
         /*db.Datilocalizzazione nuovalocalizzazione = new db.Datilocalizzazione(cuaa);
         nuovalocalizzazione.setAziendeI(newazienda);
         nuovalocalizzazione.setCentrox(Double.parseDouble(this.coordinata_x));
         nuovalocalizzazione.setCentroy(Double.parseDouble(this.coordinata_y));
         
         db.ScenarioI sce = new db.ScenarioI();
         sce.setCuaa(newazienda);
         sce.setId(0);
         sce.setDescrizione("iniziale");*/
         
         
         
        EntityTransaction tx = entityManager.getTransaction();
        
        tx.begin();
        entityManager.persist(newazienda);
        tx.commit();
         /*entityManager.persist(newazienda);
         entityManager.persist(sce);
         entityManager.persist(nuovalocalizzazione);*/
         /* entityManager.persist(datirimozione);
          
         entityManager.persist(particellescenario);
          entityManager.persist(particellescenario1);
           entityManager.persist(distanzacentro);*/
        //tx.commit();
        
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " idscenario dopo la commit " + sce.getIdscenario());

       /* q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.cuaa = ?1 ");
        q.setParameter(1, this.cuaa);
        
        db.AziendeI az2 = null;
        
        if(!q.getResultList().isEmpty()) {
             az2 = (db.AziendeI)q.getResultList().get(0);
         }
            
        q = entityManager.createQuery("SELECT s FROM ScenarioI s WHERE s.cuaa = ?1 and s.id = 0");
        q.setParameter(1, az2);
        db.ScenarioI sce1 =(db.ScenarioI)q.getResultList().get(0);
        db.DatiRimozioneazoto datirimozione = new db.DatiRimozioneazoto(sce1.getIdscenario());
        datirimozione.setScenarioI(sce1);
        datirimozione.setIdscenario(sce1.getIdscenario());
        datirimozione.setEffMedia(0.65);
        datirimozione.setMasLordo(250.0);
        double sauzvdouble = Double.parseDouble(this.sazv);
        double sauzvndouble = Double.parseDouble(this.sauzvn);
        datirimozione.setSauzv(sauzvdouble);
        datirimozione.setSauzvn(sauzvndouble);
        double maxzoo = (250 *(sauzvdouble + sauzvndouble) / 0.65);
        double maxn = (170 * sauzvdouble + 340 * sauzvndouble);
        datirimozione.setMaxncolture(maxzoo);
        datirimozione.setMaxnsau(maxn);
         
         db.Particellescenario particellescenario = new db.Particellescenario(sce1.getIdscenario());
         particellescenario.setFlagvulnerabilita("S");
         particellescenario.setSuperficieutilizzata(10000*Double.parseDouble(this.sazv));
         particellescenario.setIdscenario(sce1);
         
         db.Particellescenario particellescenario1 = new db.Particellescenario(sce1.getIdscenario());
         particellescenario1.setFlagvulnerabilita("N");
         particellescenario1.setSuperficieutilizzata(10000 * Double.parseDouble(this.sauzvn));
         particellescenario1.setIdscenario(sce1);
         //devo aggiungere un recordi di acquastoccagigo perchè il trigger dello scenario 
         //non lo fa in automatico
        db.AcquastoccaggioI acquastocca = new db.AcquastoccaggioI(sce1.getIdscenario().intValue());
         acquastocca.setScenarioI(sce1);
         acquastocca.setIdscenario(sce1.getIdscenario().intValue());
         acquastocca.setCapLiquidi1rac(0.0);
         acquastocca.setCapLiquidi1rac(0.0);
         acquastocca.setCapSolidi1rac(0.0);
         acquastocca.setPioggia(840.0);
          acquastocca.setSupLiquidi1rac(0.0);
           acquastocca.setSupSolidi1rac(0.0);
            acquastocca.setSuperficiScoperte(0.0);
            acquastocca.setAcquaImpianti(0.0);
        
        
           tx = entityManager.getTransaction();
           tx.begin();
         
           entityManager.persist(datirimozione);
           entityManager.persist(particellescenario);
           entityManager.persist(particellescenario1);
           entityManager.persist(acquastocca);
                      
           tx.commit();
        
        // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " idscenario dopo la commit " + sce.getIdscenario());
         
        
         
         */
         
         /**
          * dopo la creazione dell'azienda popolo anche le distanze tra se e le altre aziende che serve 
          * per la pagina localizzazione
          */
        /* UrlDistanza urldistanza = new UrlDistanza();
         urldistanza.setAggiorna(true);
         urldistanza.setCuaa(cuaa);
         urldistanza.setNuovazienda(this);
         urldistanza.run();*/
         
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(NuovaAzienda.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         if(this.statoOperazione == null ) {
             this.statoOperazione = "Creazione nuova azienda andata a buon fine : clicca sul bottone 'Fine' per tornare alla Home";
             
             return "";
         }
         
         //devo aggiungere un recordi di acquastoccagigo perchè il trigger dello scenario 
         //non lo fa in automatico
         /*db.AcquastoccaggioI acquastocca = new db.AcquastoccaggioI(sce1.getIdscenario().intValue());
         acquastocca.setScenarioI(sce1);
         acquastocca.setIdscenario(sce1.getIdscenario().intValue());
         acquastocca.setCapLiquidi1rac(0.0);
         acquastocca.setCapLiquidi1rac(0.0);
         acquastocca.setCapSolidi1rac(0.0);
         acquastocca.setPioggia(840.0);
          acquastocca.setSupLiquidi1rac(0.0);
           acquastocca.setSupSolidi1rac(0.0);
            acquastocca.setSuperficiScoperte(0.0);
          tx = entityManager.getTransaction();
           tx.begin();
           entityManager.persist(acquastocca);
          tx.commit();*/
         
         
        return "";
         
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
     * @return the codice_comune
     */
    public String getCodice_comune() {
        return codice_comune;
    }

    /**
     * @param codice_comune the codice_comune to set
     */
    public void setCodice_comune(String codice_comune) {
        this.codice_comune = codice_comune;
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

    /**
     * @return the codice_provincia
     */
    public String getCodice_provincia() {
        return codice_provincia;
    }

    /**
     * @param codice_provincia the codice_provincia to set
     */
    public void setCodice_provincia(String codice_provincia) {
        this.codice_provincia = codice_provincia;
    }

    /**
     * @return the coordinata_x
     */
    public String getCoordinata_x() {
        return coordinata_x;
    }

    /**
     * @param coordinata_x the coordinata_x to set
     */
    public void setCoordinata_x(String coordinata_x) {
        this.coordinata_x = coordinata_x;
    }

    /**
     * @return the coordinata_y
     */
    public String getCoordinata_y() {
        return coordinata_y;
    }

    /**
     * @param coordinata_y the coordinata_y to set
     */
    public void setCoordinata_y(String coordinata_y) {
        this.coordinata_y = coordinata_y;
    }

    /**
     * @return the sauzvn
     */
    public String getSauzvn() {
        return sauzvn;
    }

    /**
     * @param sauzvn the sauzvn to set
     */
    public void setSauzvn(String sauzvn) {
        this.sauzvn = sauzvn;
    }

    /**
     * @return the sazv
     */
    public String getSazv() {
        return sazv;
    }

    /**
     * @param sazv the sazv to set
     */
    public void setSazv(String sazv) {
        this.sazv = sazv;
    }

    /**
     * @return the statoOperazione
     */
    public String getStatoOperazione() {
        return statoOperazione;
    }

    /**
     * @param statoOperazione the statoOperazione to set
     */
    public void setStatoOperazione(String statoOperazione) {
        this.statoOperazione = statoOperazione;
    }

    /**
     * @return the cuaamessage
     */
    public String getCuaamessage() {
       if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
        
       
        /**
         * verifico la presenza del nuovo utente : il suo username è gia presente
         * se si non procedere con l'inserimento altrimenti si 
         */
        Query q = entityManager.createQuery("SELECT az FROM AziendeI az WHERE az.cuaa=?1 ");
        q.setParameter(1, this.cuaa);
        
        if(q.getResultList().size()!= 0)
            cuaamessage= "cuaa già presente";
        else
            cuaamessage = "";
        System.out.println("get cuaamessage " +cuaamessage + " cuaa " + this.cuaa );
       // username_messaggio = "b";
        
        
        
        
        
        
        return cuaamessage;
    }

    /**
     * @param cuaamessage the cuaamessage to set
     */
    public void setCuaamessage(String cuaamessage) {
        this.cuaamessage = cuaamessage;
    }

    /**
     * @return the deroga
     */
    public boolean isDeroga() {
        return deroga;
    }

    /**
     * @param deroga the deroga to set
     */
    public void setDeroga(boolean deroga) {
        this.deroga = deroga;
    }
     
     
    
}
