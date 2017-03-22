/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.el.ELContext;
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
public class ListaAppezzamenti {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private DettaglioCuaa dettaglioCuaa;
    
    private List<db.TipoTerreno> tipiTerreni;
    private List<db.TipoIrrigazione> tipiIrrigazione;
    private List<db.Coltura> listaColture;
    //private String tipoTerreno = "1";
    private int upa = 1;
    private String tipoIrrigazione = "1";
    private String nome = "";
    private double superficie = 0d;
    private boolean svn = false;
    private db.ScenarioI sceT;
    private static List<RecordAppezzamento> listaAppezzamenti = new LinkedList<RecordAppezzamento>();
    
    private static ListaAppezzamenti instance = null;
    protected ListaAppezzamenti(){
    
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +"  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "  costruttore ");
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         } 
         
          Query q = entityManager.createNamedQuery("TipoTerreno.findAll");
         tipiTerreni=(List<db.TipoTerreno>)q.getResultList();
         
         q = entityManager.createNamedQuery("TipoIrrigazione.findAll");
         tipiIrrigazione = (List<db.TipoIrrigazione>)q.getResultList();
         
         
         q = entityManager.createNamedQuery("Coltura.findAll");
         listaColture = (List<db.Coltura>)q.getResultList();
         
        // this.listaAppezzamenti.clear(); 
    
    
    }
    
    public static ListaAppezzamenti getInstance(){
        if(instance == null) {
         instance = new ListaAppezzamenti();
      }
      return instance;
    }
    
    /*public ListaAppezzamenti()
    {
      //  
         
         
    }*/
    
    
    
     public synchronized void popolaAppezzamenti() {
         
        this.listaAppezzamenti.clear(); 
         
        //if(this.listaAppezzamenti.size() != 0)
       //     return ;
        /**
         * verifico se è stato impostato un valore per sceanriostring in dettaglioCuaa
         */
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            setDettaglioCuaa((DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa"));
            
            
         //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+" scenariostr " +getDettaglioCuaa().getScenarioString() + " id scenario " +getDettaglioCuaa().getIdscenario());
            
        if (getDettaglioCuaa().getScenario() != 0) {
           
            
      /**
       * verifico la connnesione con il database
       */
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
      
       
       entityManager.getEntityManagerFactory().getCache().evictAll();
       //devo creare un nuovo appezzamento e quindi devo cercare prima lo scenario
       //il tipo di terreno , il tipo di irrigazione
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", getDettaglioCuaa().getIdscenario());
       if(q.getResultList().isEmpty()) {
                return;
            }
       setSceT((db.ScenarioI)q.getResultList().get(0));
       Iterator<db.Appezzamento> iterAppezzamenti=getSceT().getAppezzamentoCollection().iterator();
          
       //         getListaAppezzamenti().clear();
       
       while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           RecordAppezzamento reT = new RecordAppezzamento(apptemp);
           reT.popolaRotazioni(apptemp.getId());
           reT.setNome(apptemp.getNome());
           reT.setSuperficie(apptemp.getSuperficie());
           reT.setSvn(apptemp.getSvz());
           reT.setTipoIrrigazione(apptemp.getTipoirrigazione());
          // reT.setTipoTerreno(apptemp.getTipoterreno());
           reT.setUpa(apptemp.getUpa());
           reT.setId((long)apptemp.getId());
           reT.setColtura_precedente(apptemp.getColturaprecedenteId().getId());
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" idappezzamento " + reT.getId() + " aggiunto" + " nome: " + reT.getNome());
           
           this.listaAppezzamenti.add(reT);
       }
            
            
            //RecordAppezzamento iniziale = new RecordAppezzamento();
            //listaAppezzamenti.add(iniziale);

            /*RecordAppezzamento temp = new RecordAppezzamento();
                this.setTipiIrrigazione(temp.getTipiIrrigazione());
                this.setTipiTerreni(temp.getTipiTerreni());*/
            
            //this.pannelloFourth.setRendered(true);
       
       
       Connessione.getInstance().chiudi();
            
       
        
      //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +"  " + Thread.currentThread().getStackTrace()[1].getMethodName() + "  aggiorno lista appezzamenti");
    }
    }

    /**
     * @return the tipiTerreni
     */
    public List<db.TipoTerreno> getTipiTerreni() {
        return tipiTerreni;
    }

    /**
     * @param tipiTerreni the tipiTerreni to set
     */
    public void setTipiTerreni(List<db.TipoTerreno> tipiTerreni) {
        this.tipiTerreni = tipiTerreni;
    }

    /**
     * @return the tipiIrrigazione
     */
    public List<db.TipoIrrigazione> getTipiIrrigazione() {
        return tipiIrrigazione;
    }

    /**
     * @param tipiIrrigazione the tipiIrrigazione to set
     */
    public void setTipiIrrigazione(List<db.TipoIrrigazione> tipiIrrigazione) {
        this.tipiIrrigazione = tipiIrrigazione;
    }

    /**
     * @return the tipoTerreno
     */
    /*public String getTipoTerreno() {
        return tipoTerreno;
    }*/

    /**
     * @param tipoTerreno the tipoTerreno to set
     */
   /* public void setTipoTerreno(String tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }*/

    /**
     * @return the tipoIrrigazione
     */
    public String getTipoIrrigazione() {
        return tipoIrrigazione;
    }

    /**
     * @param tipoIrrigazione the tipoIrrigazione to set
     */
    public void setTipoIrrigazione(String tipoIrrigazione) {
        this.tipoIrrigazione = tipoIrrigazione;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the superficie
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * @param superficie the superficie to set
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * @return the svn
     */
    public boolean isSvn() {
        return svn;
    }

    /**
     * @param svn the svn to set
     */
    public void setSvn(boolean svn) {
        this.svn = svn;
    }

    /**
     * @return the sceT
     */
    public db.ScenarioI getSceT() {
        return sceT;
    }

    /**
     * @param sceT the sceT to set
     */
    public void setSceT(db.ScenarioI sceT) {
        this.sceT = sceT;
    }

    /**
     * @return the listaAppezzamenti
     */
    public List<RecordAppezzamento> getListaAppezzamenti() {
       
       /*   ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettTemp = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
        Long idscenario = dettTemp.getIdscenario();    */
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " numero appezzamaneti " + this.listaAppezzamenti.size() );   
        // if(listaAppezzamenti.isEmpty()  || !StoricoColturaleAppezzamento.isRenderAsp()) {
       /**
        * and idscenario è
        */
        //listaAppezzamenti.clear();
        
        
        if(listaAppezzamenti.isEmpty()  ) {
                popolaAppezzamenti();
                
            }
       
        
      //  System.out.println("ListaAppezzamenti getListaAppezamenti  :dimensione lista " + this.listaAppezzamenti.size() +" dimensione lista colture " + this.listaColture.size());
        
        return listaAppezzamenti;
    }

    /**
     * @param listaAppezzamenti the listaAppezzamenti to set
     */
    public void setListaAppezzamenti(List<RecordAppezzamento> listaAppezzamenti) {
        this.listaAppezzamenti = listaAppezzamenti;
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

    /**
     * @return the listaColture
     */
    public List<db.Coltura> getListaColture() {
        return listaColture;
    }

    /**
     * @param listaColture the listaColture to set
     */
    public void setListaColture(List<db.Coltura> listaColture) {
        this.listaColture = listaColture;
    }

    /**
     * @return the upa
     */
    public int getUpa() {
        return upa;
    }

    /**
     * @param upa the upa to set
     */
    public void setUpa(int upa) {
        this.upa = upa;
    }
    
    
}
