/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean_entity;

import db.Appezzamento;
import db.Coltura;
import db.GruppoColturale;
import db.Rotazione;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import operativo.StoricoColturaleAppezzamento;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
public class StoricoColturaAppezzamentoE {
    
    private Long id;   
    private long idColtura  = 1;   
    private Appezzamento idAppezzamento;   
    private Rotazione rotazione;   
    private GruppoColturale idGruppoColturale;
    private double asportazioneAzoto;
    private double asportazioneFosforo;
    private double asportazionePotassio;
    //riferimento per resa_attesa e mas il MAIS GRANELLA
    private double resa_attesa = 0;
    private double mas = 0;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private DettaglioCuaa dettaglioCuaa;
    
    
    
    public StoricoColturaAppezzamentoE(db.Appezzamento appezzamento)
    {
        this.idAppezzamento = appezzamento;
    }

    
   
    
    
    /**
     * @return the idColtura
     */
    public long getIdColtura() {
        return idColtura;
    }

    /**
     * @param idColtura the idColtura to set
     */
    public void setIdColtura(long idColtura) {
        this.idColtura = idColtura;
        
        //StoricoColturaleAppezzamento.setEvento_resa(false);
       /*  if(!StoricoColturaleAppezzamento.isEvento_resa()  && this.idColtura != 1)
        {
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         } 
    
         
        db.Coltura colturaScelta=entityManager.find(db.Coltura.class, this.idColtura);
    
        this.resa_attesa =  colturaScelta.getResa().getQuantita();
      
        
        Connessione.getInstance().chiudi();
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" evento resa " + StoricoColturaleAppezzamento.isEvento_resa() + " resa attesa " + this.resa_attesa + " id coltura  " + this.idColtura);

        }*/
        
       
        
    System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" evento resa " + StoricoColturaleAppezzamento.isEvento_resa() + " id coltura " + this.idColtura);

    }

    /**
     * @return the idAppezzamento
     */
    public Appezzamento getIdAppezzamento() {
        return idAppezzamento;
    }

    /**
     * @param idAppezzamento the idAppezzamento to set
     */
    public void setIdAppezzamento(Appezzamento idAppezzamento) {
        this.idAppezzamento = idAppezzamento;
    }

    /**
     * @return the rotazione
     */
    public Rotazione getRotazione() {
        return rotazione;
    }

    /**
     * @param rotazione the rotazione to set
     */
    public void setRotazione(Rotazione rotazione) {
        this.rotazione = rotazione;
    }

    /**
     * @return the idGruppoColturale
     */
    public GruppoColturale getIdGruppoColturale() {
        return idGruppoColturale;
    }

    /**
     * @param idGruppoColturale the idGruppoColturale to set
     */
    public void setIdGruppoColturale(GruppoColturale idGruppoColturale) {
        this.idGruppoColturale = idGruppoColturale;
    }
    
    /**
     * da cancellare
     * @param event 
     */
    /*public void listener(AjaxBehaviorEvent event) {
        System.out.println("listener");
        //result = "called by " + event.getComponent().getClass().getName();
        
        getAsportazioneAzoto();
    }*/
    
    
    /**
     * @return the asportazioneAzoto
     */
    public double getAsportazioneAzoto() {
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         }
        /**
         * recupero nell'appezzamento la coltura precedente per capire se devo togliere 
         */
        db.Appezzamento app = entityManager.find(db.Appezzamento.class,this.idAppezzamento.getId());
        double azoto_precedente = 0;
        if (this.rotazione.getId() != null && this.rotazione.getId() == 1) {
            switch (app.getColturaPrecedente().getId()) {
                case 2:
                    azoto_precedente = -40;
                    break;
                case 3:
                    azoto_precedente = -60;
                    break;
            }
        }
        
         /**
          * recupero da db il fattore correttivo e la resa della coltura
          * per calcolare il nuovo mas se l'utente ha mpodificato il valore 
          * della resa nella pagina piano_colturale
          */
         db.Coltura colt = entityManager.find(db.Coltura.class,this.idColtura);
         db.Asportazione asp = colt.getAsportazione();
         double fattoreCorrettivo = asp.getFattoreCorrettivo();
         double resadb = colt.getResa().getQuantita();
         double aspP = asp.getAspP2O5();
         double aspK = asp.getK2O();
         this.mas =colt.getAsportazione().getMasN();
          
         
         this.asportazioneAzoto = mas +(this.resa_attesa - resadb ) *fattoreCorrettivo + azoto_precedente;
         this.asportazioneFosforo = this.resa_attesa * aspP;
         this.asportazionePotassio = this.resa_attesa * aspK; 
         
        
         Connessione.getInstance().chiudi();
        
        // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" asportazioneAzoto " +this.asportazioneAzoto +" mas " +this.mas +" resa_attesa "+ this.resa_attesa +" resadb " + resadb +" evento resa " + StoricoColturaleAppezzamento.isEvento_resa()+" coltura precedente " +app.getColturaPrecedente().getId() );
        
        return asportazioneAzoto;
    }

    /**
     * @param asportazioneAzoto the asportazioneAzoto to set
     */
    public void setAsportazioneAzoto(double asportazioneAzoto) {
    //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" asportazioneAzoto " +this.asportazioneAzoto +" mas " +this.mas +" resa_attesa "+ this.resa_attesa );

        this.asportazioneAzoto = asportazioneAzoto;
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
    
   /* public void processInput(AjaxBehaviorEvent event)
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " " + event.getComponent().getAttributes().get("name"));
    }*/
    /**
     * devi recuperare se l'appezzamento è in svn , la coltura precedente se c'è ed usare queste per calcolare 
     * le asportazioni azoto, potassio e fosfro della coltura dell'appezzamento. Poi dopo aver calcolato queste apostazioni devi 
     * aggiornare le asportazioni totali dell'appezzamaneto in funzione delle tre colture ed aggiornare le asportazioni
     * totali come somma di tutti gli appezzamenti
     * dato che getresa_attesa è chiamatao quando si seleziona una coltura e quando si cambia la resa
     * devo capire in quale situazione mi trovo perchè le viene cambiata la coltura è giusto che venga cambiata la resa
     * ma se l'utente cambia la resa non devi cambiare la coltura per cui nei due eventi keyup e valuechange in
     * piano coltuale passo un parametro a StoriColturaleAppezzamanto.processInput che indica in quale evento mi trovo 
     * e imposto la variabile statica evento_resa della medesima classe
     * @return the resa_attesa
     */
    public double getResa_attesa() {
        
        if(!StoricoColturaleAppezzamento.isEvento_resa())
        {
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         } 
    
         
        db.Coltura colturaScelta=entityManager.find(db.Coltura.class, this.idColtura);
    
        this.resa_attesa =  colturaScelta.getResa().getQuantita();
        
       
        
        Connessione.getInstance().chiudi();
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" evento resa " + StoricoColturaleAppezzamento.isEvento_resa() + " resa attesa " + this.resa_attesa + " id coltura  " + this.idColtura +" coltura scelta "+ colturaScelta.getDescrizione()+ " in if");

        }
        
      
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" evento resa " + StoricoColturaleAppezzamento.isEvento_resa() + " resa attesa " + this.resa_attesa + " id coltura  " + this.idColtura + " coltura scelta " + ""  + " fuori if");

      
        
        return resa_attesa;
    }

    /**
     * @param resa_attesa the resa_attesa to set
     */
    public void setResa_attesa(double resa_attesa) {
        this.resa_attesa = resa_attesa;
        
     //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() );

    }

    /**
     * @return the mas
     */
    public double getMas() {
     //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " mas  " + this.mas +" resa  " + this.resa_attesa + " idcoltura " + this.idColtura);
        
         
                
         return mas ;
    }

    /**
     * @param mas the mas to set
     */
    public void setMas(double mas) {
        this.mas = mas;
    //    System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " mas  " + this.mas +" resa  " + this.resa_attesa);

    }
    
            
      
    
}
