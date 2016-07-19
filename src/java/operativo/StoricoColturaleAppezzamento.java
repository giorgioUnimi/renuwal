/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import bean_entity.StoricoColturaAppezzamentoE;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="storicoColturaleAppezzamenti")
//@ApplicationScoped
@ViewScoped
public class StoricoColturaleAppezzamento extends ListaAppezzamenti implements Serializable{
    
    private static final long serialVersionUID = -3832235132261771583L;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private double asportazioneAzoto;
    private double asportazioneFosforo;
    private double asportazionePotassio;
    
    private double test = 0;
    
    private static boolean evento_resa = true;
    
    private static boolean renderAsp = true;
    
    private boolean checkboxReadOnly = true;
 
     
     //private List<RecordAppezzamento> listaAppezzamentiG;
    /**
     * Creates a new instance of StoricoColturaleAppezzamento
     */
    public StoricoColturaleAppezzamento() {
        
        super();
        //super.getListaAppezzamenti().clear();
        //        popolaAppezzamenti();
        //this.getListaAppezzamenti().clear();
     //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName()+"  lista colture "+ this.getListaColture().size() + " primo " + this.getListaColture().get(0).getDescrizione());
        renderAsp = true;
    }
    
  /*   @Override
   public List<RecordAppezzamento> getListaAppezzamenti() {
        synchronized(this){
                super.getListaAppezzamenti().clear();
                popolaAppezzamenti();
                
            
        }
        
        //System.out.println("ListaAppezzamenti getListaAppezamenti  :dimensione lista " + .listaAppezzamenti.size() +" dimensione lista colture " + this.listaColture.size());
        
        return super.getListaAppezzamenti();
    }*/

    /**
     * @param listaAppezzamenti the listaAppezzamenti to set
     */
    /*@Override
    public void setListaAppezzamenti(List<RecordAppezzamento> listaAppezzamenti) {
        super.setListaAppezzamenti(listaAppezzamenti);
    }*/
    
    /**
     * mi informa se evento resa nella pagina piano_colturale 
     * è avvenuto tramite campo testo resa o tramite menu a tendina delle colture
     * @return the evento_resa
     */
    public static boolean isEvento_resa() {
        return evento_resa;
    }

    /**
     * @param aEvento_resa the evento_resa to set
     */
    public static void setEvento_resa(boolean aEvento_resa) {
        evento_resa = aEvento_resa;
    }
    
    /**
     * evento richiamato da piano_colturale.xhtml ed ha lo scopo di impostare la variabbile statica
     * di questa classe evento_resa in funzione del parametro che gli arriva da ajax valuechange o keyup del campo
     * testo resa o del menu a tendina listacolture
     * @param event 
     */  
    public void processInput(AjaxBehaviorEvent event) {
        boolean evento_resaA = event.getComponent().getAttributes().get("evento_resa").toString().equals("true");
        
        StoricoColturaleAppezzamento.setEvento_resa(true);
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ "   " + event.getComponent().getAttributes().get("evento_resa") + " evento_resa " +  StoricoColturaleAppezzamento.isEvento_resa());

        
    }
    
    
    public void processInputA(AjaxBehaviorEvent event) {
        //boolean evento_resaA = event.getComponent().getAttributes().get("evento_resa").toString().equals("true");
        
        StoricoColturaleAppezzamento.setEvento_resa(false);
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ "   " + event.getComponent().getAttributes().get("evento_resa") + " evento_resa " +  StoricoColturaleAppezzamento.isEvento_resa());

        
    }
    
    
   /**
    * è una azione che scatta quando si clicca su salva in piano colturale 
    * @param storico 
    */ 
   public void salva(List<StoricoColturaAppezzamentoE> storico)
   {      
       if(storico != null)
       {
     /**
       * verifico la connnesione con il database
       */
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }           
            
        /**
         * da storico che è una lista recuperp l'id dell'appezzamento e svuoto la lista degli storici per quell appezzamento
         */    
        Iterator<StoricoColturaAppezzamentoE> iter = storico.iterator();
        db.Appezzamento appT = null;
        db.StoricoColturaAppezzamento stoT = null;
        StoricoColturaAppezzamentoE sto = iter.next();
        //prendo il primo record dello storico per avere l'id dell'appezzamento
        appT = entityManager.find(db.Appezzamento.class, sto.getIdAppezzamento().getId());
      //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dimensione lista storico " + appT.getStoricoColturaAppezzamento().size());
       /* appT.getStoricoColturaAppezzamento().clear();
         entityManager.getTransaction().begin();
         entityManager.merge(appT);
         entityManager.getTransaction().commit();*/
          entityManager.getTransaction().begin();
         Query query = entityManager.createQuery("DELETE FROM StoricoColturaAppezzamento s where s.idAppezzamento = :dacancellare");
         int numerocancellati = query.setParameter("dacancellare", appT).executeUpdate();
          entityManager.getTransaction().commit();
        //riinizializzo l'iteratore della lista        
        iter = storico.iterator();        
        int rotaz = 1;
        while (iter.hasNext()) {
            sto = iter.next();
         //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dimensione storico " + storico.size() + " " + storico.get(1).getIdAppezzamento().getId() + " idcoltura " + sto.getIdColtura() + " mas " + sto.getMas() + " resa attesa" + sto.getResa_attesa());

           
            //if (sto.getIdColtura() != 1) {
                
                stoT = new db.StoricoColturaAppezzamento();
                stoT.setAsportazioneAzoto(sto.getAsportazioneAzoto());
                stoT.setAsportazioneFosforo(sto.getAsportazioneFosforo());
                stoT.setAsportazionePotassio(sto.getAsportazionePotassio());
                stoT.setIdAppezzamento(sto.getIdAppezzamento());
                db.Coltura colT = entityManager.find(db.Coltura.class, sto.getIdColtura());
                db.Rotazione rota = entityManager.find(db.Rotazione.class,rotaz);
                stoT.setIdColtura(colT);
                stoT.setMas(sto.getMas());
                stoT.setRotazione(rota);
                rotaz++;
                stoT.setResa_attesa(sto.getResa_attesa());
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dimensione storico " + storico.size() + " add item alla lista id appezzamento " + storico.get(1).getIdAppezzamento().getId() + " idcoltura " + sto.getIdColtura() + " mas " + sto.getMas() + " resa attesa" + sto.getResa_attesa());

                //appT.getStoricoColturaAppezzamento().add(stoT);
                entityManager.getTransaction().begin();
                entityManager.persist(stoT);
                entityManager.getTransaction().commit();
               
           // }

        } 
        
       
        /*entityManager.getTransaction().begin();
        entityManager.persist(appT);
        entityManager.getTransaction().commit();*/
       
       
       
        Connessione.getInstance().chiudi();
        }
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + "id appezzamento " + storico.get(0).getIdAppezzamento().getId() + " coltura1 " + storico.get(0).getIdColtura());
      //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dimensione storico " + storico.size() + " " + storico.get(1).getIdAppezzamento().getId()+" " +storico.get(1).getIdColtura()+" " +storico.get(1).getMas() );

    
    }

    /**
     * @return the asportazioneAzoto
     */
    public synchronized double getAsportazioneAzoto() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StoricoColturaleAppezzamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.asportazioneAzoto = 0;
        this.asportazioneFosforo = 0;
        this.asportazionePotassio = 0;
       
        Iterator<RecordAppezzamento> iterAppezza = this.getListaAppezzamenti().iterator();
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " numero appezzamaneti  " + this.getListaAppezzamenti().size());

        
        while(iterAppezza.hasNext())
        {
            RecordAppezzamento recordAppezza = iterAppezza.next();
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " appezza  " +recordAppezza.getNome());

            this.asportazioneAzoto += recordAppezza.getAsp_azoto() * recordAppezza.getSuperficie();
            this.asportazioneFosforo +=recordAppezza.getAsp_fosforo() * recordAppezza.getSuperficie() ;
            this.asportazionePotassio +=recordAppezza.getAsp_potassio() * recordAppezza.getSuperficie();
        }
   
        
     //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " asp totale n " +this.asportazioneAzoto+" asp totale p " +this.asportazioneFosforo + " asp totale k " +this.asportazionePotassio);
   
        return asportazioneAzoto;
    }

    /**
     * @param asportazioneAzoto the asportazioneAzoto to set
     */
    public void setAsportazioneAzoto(double asportazioneAzoto) {
        this.asportazioneAzoto = asportazioneAzoto;
        
               // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " asp totale n " +this.asportazioneAzoto+" asp totale p " +this.asportazioneFosforo + " asp totale k " +this.asportazionePotassio);

    }

    /**
     * @return the asportazioneFosforo
     */
    public double getAsportazioneFosforo() {
        return asportazioneFosforo;
    }

    /**
     * @param asportazioneFosforo the asportazioneFosforo to set
     */
    public void setAsportazioneFosforo(double asportazioneFosforo) {
        this.asportazioneFosforo = asportazioneFosforo;
    }

    /**
     * @return the asportazionePotassio
     */
    public double getAsportazionePotassio() {
        return asportazionePotassio;
    }

    /**
     * @param asportazionePotassio the asportazionePotassio to set
     */
    public void setAsportazionePotassio(double asportazionePotassio) {
        this.asportazionePotassio = asportazionePotassio;
    }

    /**
     * @return the test
     */
    public double getTest() {
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() );
        return test++;
    }

    /**
     * @param test the test to set
     */
    public void setTest(double test) {
             //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() );

        this.test = test+1;
    }

    /**
     * @return the renderAsp
     */
    public static boolean isRenderAsp() {
        return StoricoColturaleAppezzamento.renderAsp;
    }

    /**
     * @param renderAsp the renderAsp to set
     */
    public static void setRenderAsp(boolean renderAsp) {
        StoricoColturaleAppezzamento.renderAsp = renderAsp;
    }

    /**
     * @return the checkboxReadOnly
     */
    public boolean isCheckboxReadOnly() {
        return checkboxReadOnly;
    }

    /**
     * @param checkboxReadOnly the checkboxReadOnly to set
     */
    public void setCheckboxReadOnly(boolean checkboxReadOnly) {
        this.checkboxReadOnly = checkboxReadOnly;
    }

  
    /**
     * @return the listaAppezzamenti
     */
    /*public List<RecordAppezzamento> getListaAppezzamentiG() {
        
        
        
        return listaAppezzamentiG  =super.getListaAppezzamenti();
    }*/

    /**
     * @param listaAppezzamenti the listaAppezzamenti to set
     */
  /* public void setListaAppezzamentiG(List<RecordAppezzamento> listaAppezzamenti) {
        this.listaAppezzamentiG = listaAppezzamenti;
    }*/
}
