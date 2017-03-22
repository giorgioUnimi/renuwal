/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;


import ager.RisultatoConfronto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.server.ServerSession;



@ManagedBean(name="listaAziende")
@ViewScoped
public class ListaAziende implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<db.AziendeI> tabella= new ArrayList<db.AziendeI>();
    private db.AziendeI aziendaEdit ;
    private List<db.AziendeAnni> anniazienda = new ArrayList<db.AziendeAnni>();
    private List<db.ScenarioI> listaScenari = new ArrayList<db.ScenarioI>();
    
    private String username;  
    private String password;
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private int currentAppIndex;
    //select delle aziende
    private List<SelectItem> selectaziende = new ArrayList<SelectItem>();
    //select degli anni di una determinata azienda
    private List<SelectItem> selectanni = new ArrayList<SelectItem>();
    //select degli scenari di una dseterminata azienda di un determinato anno
    private List<SelectItem> selectscenari = new ArrayList<SelectItem>();
    
    private String currentItem = "";
    private String currentTypeAz = "      ";
    private String currentTypeAn = "      ";
    private String currentTypeSce = "      ";
    
    //binding con outputpanel second di appezzamenti
    private UIComponent secondoAppezzamenti;
    
    //private boolean statoAppezzamenti = false;
    //private UIComponent pannelloFourth;
    private int page = 1;
    
    
    public ListaAziende() {
        
        tabella.clear();
        selectaziende.clear();
        SelectItem te = new SelectItem("0","        ");
        selectaziende.add(te);
        inizializzaTabella();
    }
    
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private void inizializzaTabella()
    {
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
         }
          /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      Utente utenti = (Utente) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "utente");
         
      
      /**
       * verifico se l'utente è o meno un superuser ovvero se un utente normale devo mostrargli 
       * solo le aziende ad esso collegate se invece è un super user devi mostrargli tutte le aziende
       */
      Query q1 = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.password=?1 AND u.username=?2");
      q1.setParameter(1,utenti.getPassword());
      q1.setParameter(2, utenti.getUsername());
      db.Utenti ut = null;
      if(q1.getResultList().size()!= 0) {
            ut = (db.Utenti)q1.getResultList().get(0);
        }else
      {
          return;
      }
      
      
      /**
       * seleziono anche l'utente admin sotto cui ci sono tutte le aziende
       */
    /* q1 = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.password='19visto45iia' AND u.username='admin'");
      
      db.Utenti ut1 = null;
      
      ut1 = (db.Utenti)q1.getSingleResult();*/
      
      /**
       * se l'utente rilevato è un super user mostragli tutte le aziende altrimenti solo 
       * le sue
       */
      Query q = null;
      q = entityManager.createQuery("SELECT a from AziendeI a where a.cuaa != 'predefinito' and  a.idUtente=?1");
      q.setParameter(1, ut);
      //q.setParameter(2, ut1);
      List<db.AziendeI> results = q.getResultList();

        for(int i=0; i < results.size();i++)
         {            
             db.AziendeI a = (db.AziendeI)results.get(i);                
             tabella.add(a);
             
             System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " aggiungo azienda " + a.getCuaa() );
             //aggiungo i valori alla selec aziende
             this.selectaziende.add(new SelectItem(a.getId(),a.getCuaa()));
             
         }
         
      
         
      Connessione.getInstance().chiudi();

    }
    
    
    
     public void resetValues() {
      
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() );
        // reset input fields to prevent stuck values after a validation failure
        // not necessary in JSF 2.2+ (@resetValues on a4j:commandButton)
        /*if (!JsfVersion.getCurrent().isCompliantWith(JsfVersion.JSF_2_2)) {
            FacesContext fc = FacesContext.getCurrentInstance();
            UIComponent comp = fc.getViewRoot().findComponent("form:editGrid");
 
            ((EditableValueHolder) comp.findComponent("form:price")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:mage")).resetValue();
            ((EditableValueHolder) comp.findComponent("form:vin")).resetValue();
        }*/
    }
    /**
     * metodo associato all'evento salva del popup della pagina aziende
     * nel caso del modifica azienda
     */
     public void store() {              
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " id " + this.aziendaEdit.getId() + " cuaa " + this.aziendaEdit.getCuaa() + " comune " + this.aziendaEdit.getDesComune() + " provincia " +this.aziendaEdit.getDesprovincia() + " deroga " + this.aziendaEdit.getDeroga());
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
         
         if (this.aziendaEdit.getCuaa() != null || !this.aziendaEdit.getCuaa().isEmpty() || this.aziendaEdit.getRagioneSociale() != null || !this.aziendaEdit.getRagioneSociale().isEmpty()) {
             db.AziendeI editAzienda = entityManager.find(db.AziendeI.class, this.aziendaEdit.getId());
             entityManager.getTransaction().begin();
             editAzienda.setCodComune(this.aziendaEdit.getCodComune());
             editAzienda.setDesComune(this.aziendaEdit.getDesComune());
             editAzienda.setCodiceprovincia(this.aziendaEdit.getCodiceprovincia());
             editAzienda.setDesprovincia(this.aziendaEdit.getDesprovincia());
             editAzienda.setRagioneSociale(this.aziendaEdit.getRagioneSociale());
             editAzienda.setDeroga(this.aziendaEdit.getDeroga());
             editAzienda.setCuaa(this.aziendaEdit.getCuaa());
             entityManager.getTransaction().commit();
         }
                 
         this.tabella.clear();
         
         this.inizializzaTabella();
         
         
         Connessione.getInstance().chiudi();
     }
    
    
    
    
    
    /**
     * select azienda quando selezioni un azienda dalla select nella pagina
     * Aziende scatta questo evento
     * @param event 
     */
    public void aziendeChanged(ValueChangeEvent event) {
        //svuoto la select degli anni
        this.selectanni.clear();
        SelectItem te = new SelectItem("","");
        selectanni.add(te);
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println(" sorgente evento " + event.getComponent().getId() + " valore " + event.getNewValue() + " azienda " + this.getCurrentTypeAz());
        
        if (null != event.getNewValue()) {
                       
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
       /**
        * l'id della azienda deriva dal valore del item della select  selezionato che è contenuto
        * nell'evento event che arriva a questo metodo
        * id di dettaglioCuaa è l'id dell' azienda e data un azienda recupero tutti i suoi anni
        */ 
        Query q1 = entityManager.createNamedQuery("AziendeI.findById",db.AziendeI.class).setParameter("id",Integer.parseInt(event.getNewValue().toString()) );
        db.AziendeI azTemp = null;
        if(q1.getResultList().isEmpty()) {
                return;
            }
        
        azTemp = (db.AziendeI)q1.getResultList().get(0);
        /**
         * recupero gli anni e popolo la select degli anni
         */
        Iterator<db.AziendeAnni> iterAziendeAnni = azTemp.getAziendeAnniCollection().iterator();
        while(iterAziendeAnni.hasNext())
        {
            db.AziendeAnni t = iterAziendeAnni.next();
            this.selectanni.add(new SelectItem(t.getId(),t.getIdAnno().getDescrizione()));
            
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " aggiungo anno " + t.getIdAnno().getDescrizione() + " all azienda " + t.getIdAzienda().getCuaa());
        }
        
            
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"   " +event.getNewValue());
            
            Connessione.getInstance().chiudi();
       
            
             ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         dettaglioCuaa.setId(Integer.parseInt(event.getNewValue().toString()));
         dettaglioCuaa.setCuaa(azTemp.getCuaa());
         
            
            
            }
        
        
         //this.secondoAppezzamenti.getAttributes().put("display", true);
        
        
    }
    
    /**
     * metodo che avviene quando l'utente seleziona un anno
     * @param event 
     */
     public void anniChanged(ValueChangeEvent event) {
        //svuoto la select degli anni
        this.selectscenari.clear();
        SelectItem te = new SelectItem("  ","  ");
        this.selectscenari.add(te);
        
        if (null != event.getNewValue()) {
                     
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        
        entityManager.getEntityManagerFactory().getCache().evictAll();
        /**
         * avendo l'id dell'anno dell'azienda attraverso il valore dell'evento che mi arriva
         * recupero prima l'entita aziendeanni e poi gli scenari legati a quel anno
         */
        Query q1 = entityManager.createNamedQuery("AziendeAnni.findById").setParameter("id",Integer.parseInt(event.getNewValue().toString()) );
        db.AziendeAnni azanniTemp = null;
        if(q1.getResultList().isEmpty()) {
                return;
            }
        
        azanniTemp = (db.AziendeAnni)q1.getResultList().get(0);
        
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         dettaglioCuaa.setAnno(azanniTemp.getIdAnno().getDescrizione());
        
        /**
         * recupero gli anni e popolo la select degli anni
         */
        Iterator<db.ScenarioI> iterScenari = azanniTemp.getScenarioICollection().iterator();
        while(iterScenari.hasNext())
        {
            db.ScenarioI t = iterScenari.next();
            this.selectscenari.add(new SelectItem(t.getIdscenario(),t.getDescrizione()));
        }
        
        
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"   " +event.getNewValue() + " numero scenari " + azanniTemp.getScenarioICollection().size());
        Connessione.getInstance().chiudi();
        }
     }
    /**
     * metodo che scatta quando l'utente seleziona un anno
     * @param event 
     */
     public void scenariChanged(ValueChangeEvent event) {   
         
        if (null != event.getNewValue()) {
            
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }    
        /**
         * recupero dettagliocuaa che è il contenitore delle informazioni che uso tra le varie classi
         * ed imposto il valore di scenariostring in funzione del valore scelto dall'utente
         * attravero il contenuto della variabile evento
         */
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         dettaglioCuaa.setScenario(Integer.parseInt(event.getNewValue().toString()));
         dettaglioCuaa.setIdscenario(Long.parseLong(event.getNewValue().toString()));
         db.ScenarioI sceT = entityManager.find(db.ScenarioI.class,Long.parseLong(event.getNewValue().toString()));
         
         dettaglioCuaa.setScenarioString(sceT.getDescrizione());
         //dettaglioCuaa.setIdscenario(sceT.getIdscenario());
         //GestoreAppezzamenti_2 gestoreAppezzamento = (GestoreAppezzamenti_2) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "gestoreAppezzamenti_2");
        // gestoreAppezzamento.popolaAppezzamenti();
         
         //dettaglioCuaa.setScenarioString(event.getNewValue().toString());
         //aggiorno lo stato del pannello che mostra gli appezzamenti 
         //cosi da essere visibile
        
         //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formAppezzamenti:pannelloFourth");
         //this.statoAppezzamenti = true;
         //this.pannelloFourth.setRendered(true);    
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"   " +event.getNewValue());
        
        Connessione.getInstance().chiudi();   
        
        /**
         * ho cambiato lo scenario e per aggiornare la lista degli appezzamneti prima la svuoto
         */
        ListaAppezzamenti listaAppezzamenti = ListaAppezzamenti.getInstance();
        listaAppezzamenti.getListaAppezzamenti().clear();
        listaAppezzamenti.popolaAppezzamenti();
        
       
        
        //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " listaappezzamenti size " + listaAppezzamenti.getListaAppezzamenti().size());
         //StoricoColturaleAppezzamento.setRenderAsp(false);   
        }
      }
      
    
     public UnitOfWork acquireUnitOfWork() {
          return serverSession.acquireClientSession().acquireUnitOfWork();
     }

    /**
     * @return the tabella
     */
    public List<db.AziendeI> getTabella() {
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<db.AziendeI> tabella) {
        this.tabella = tabella;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the selectaziende
     */
    public List<SelectItem> getSelectaziende() {
        return selectaziende;
    }

    /**
     * @param selectaziende the selectaziende to set
     */
    public void setSelectaziende(List<SelectItem> selectaziende) {
        this.selectaziende = selectaziende;
    }

    /**
     * @return the selectanni
     */
    public List<SelectItem> getSelectanni() {
        return selectanni;
    }

    /**
     * @param selectanni the selectanni to set
     */
    public void setSelectanni(List<SelectItem> selectanni) {
        this.selectanni = selectanni;
    }

    /**
     * @return the selectscenari
     */
    public List<SelectItem> getSelectscenari() {
        return selectscenari;
    }

    /**
     * @param selectscenari the selectscenari to set
     */
    public void setSelectscenari(List<SelectItem> selectscenari) {
        this.selectscenari = selectscenari;
    }

    /**
     * @return the currentItem
     */
    public String getCurrentItem() {
        return currentItem;
    }

    /**
     * @param currentItem the currentItem to set
     */
    public void setCurrentItem(String currentItem) {
        this.currentItem = currentItem;
    }

   
    
    /**
     * metodo invocato dal bottone cancella della pagina aziende
     * ha lo scopo di eliminare l'azienda che ha idazienda uguale al
     * parametro passato id
     */
    public void cancella(){
        Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String idazienda = params.get("id");
        
        ListIterator<db.AziendeI> iterAz = this.tabella.listIterator();
        db.AziendeI azT = null;
        while(iterAz.hasNext())
        {
         azT = iterAz.next();   
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " azGetId " + azT.getId() + " idazienda " + idazienda);
           
            if(azT.getId() == Integer.parseInt(idazienda))
            {
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " trovato ");
                break;
            }
        }
        
        if(azT != null) {
            this.tabella.remove(azT);
        }
        
        
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"  idazienda " + idazienda);
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        /**
         * mi collego al database ed elimino l'azienda che ha id come parametro passato
         */
        db.AziendeI az = entityManager.find(db.AziendeI.class, Integer.parseInt(idazienda));
        entityManager.getTransaction().begin();
        entityManager.remove(az);
        entityManager.getTransaction().commit();
        
        Connessione.getInstance().chiudi();
    }
    /**
     * metodo invocato dal bottone modifica della pagina aziende
     * ha lo scopo di modifica l'azienda che ha idazienda uguale al
     * parametro passato id
     */
    public void modifica(){
        Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String idazienda = params.get("id");
        
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"  idazienda " + idazienda);

    }
    
    
    
     /**
     * metodo invocato dal bottone modifica della pagina aziende
     * ha lo scopo di modifica l'azienda che ha idazienda uguale al
     * parametro passato id
     */
    public void clona(){
        Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	String idazienda = params.get("id");
        
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"  idazienda " + idazienda);

    }
    
    
    public void creaScenario()
    {
       //Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	//String idazienda = params.get("id");
       ELContext elContext = FacesContext.getCurrentInstance().getELContext();
       DettaglioCuaa dettaglioCuaa = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"  idazienda " + this.getCurrentTypeAz() + " idanno  " + dettaglioCuaa.getAnno() + " descrizione  " + dettaglioCuaa.getDescrizionescenario());
       
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
       
       
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " 1 " );
       /**
        * recupero l'azienda usando l'id contenuto in currenttype ddella form associata crea scenario
        */
       Query q1 = entityManager.createQuery("Select a from AziendeI a where a.id=?1").setParameter(1, Integer.parseInt(this.getCurrentTypeAz()));
       db.AziendeI azT = null;
       if(!q1.getResultList().isEmpty())
       {
           azT = (db.AziendeI)q1.getResultList().get(0);
       }
       
       /**
        * recupero l'anno usaqndo l'id usando dettaglioCuaa
        */
       q1 = entityManager.createQuery("Select a from Anni a where a.id=?1").setParameter(1, Integer.parseInt(dettaglioCuaa.getAnno()));
       db.Anni anT = null;
       if(!q1.getResultList().isEmpty())
       {
           anT = (db.Anni)q1.getResultList().get(0);
       }
       
       EntityTransaction tx = entityManager.getTransaction();
       /**
        * verifico se è gia presente un record azienda anno prima di aggiungerene uno
        */
       Query q = entityManager.createQuery("Select a from AziendeAnni a where a.idAnno = :idanno and a.idAzienda= :idazienda");
       q.setParameter("idanno", anT);
       q.setParameter("idazienda", azT);
       db.AziendeAnni azanT = null;
       if(q.getResultList() == null || q.getResultList().isEmpty())
        {
            /**
             * mi devo creare un istanza di aziendeAnni prima di creare lo
             * scenario perchè scenario ha bisogno di aziendeanni
             */
            azanT = new db.AziendeAnni();
            azanT.setIdAnno(anT);
            azanT.setIdAzienda(azT);
            tx.begin();
            entityManager.persist(azanT);
            tx.commit();
        }else
       {
           azanT = (db.AziendeAnni)q.getSingleResult();
       }
       
              System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " 2 " );

       
        
       db.ScenarioI sceN = new db.ScenarioI();
       sceN.setDescrizione(dettaglioCuaa.getDescrizionescenario());
       sceN.setIdAziendeanni(azanT);
       
      
       /**
        * uso aziendeanni appena creato per cr4eare un nuovo scneario
        */
       tx.begin();
        entityManager.persist(sceN);
       
       
       tx.commit();
       
       
       
       
     
              
       Connessione.getInstance().chiudi();
       
       
    }
    
    /**
     * elimina loscenario di una aprticolare azuienda anno in funzioen delle 
     * informazioni contenuti nella form elimina scenario della pagina aziende.xhtml
     * il valore dello scenario da eliminare è in listaAziende.currentype
     */
    public void eliminaScenario()
    {
       //Map<String,String> params =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	//String idazienda = params.get("id");
       ELContext elContext = FacesContext.getCurrentInstance().getELContext();
       DettaglioCuaa dettaglioCuaa = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() +"  scenario " + this.getCurrentTypeSce() );
       
       
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
       entityManager.getTransaction().begin();
       //Query q1 = entityManager.createQuery("Delete from ScenarioI s where s.idscenario=?1").setParameter(1, Long.parseLong(this.currentType));
       //q1.executeUpdate();
       /**
        * cancello uno scenario e verifico se non ci sono altri scenari per quella azienda anno
        * nella tabella scenari. Se non ci altri scenari per quella coppia anno scenario
        * rimuovo il idazienda anche da aziendaanni
        */
       db.ScenarioI sceT = entityManager.find(db.ScenarioI.class, Long.parseLong(this.getCurrentTypeSce()));
       entityManager.remove(sceT);
       db.AziendeAnni aziendaAnno = sceT.getIdAziendeanni();
       
       if(aziendaAnno.getScenarioICollection().isEmpty())
       {
           entityManager.remove(sceT);
       }
       
       entityManager.getTransaction().commit();
       
      
               
       Connessione.getInstance().chiudi();
       
       
       
    }

    /**
     * @return the secondoAppezzamenti
     */
    public UIComponent getSecondoAppezzamenti() {
        return secondoAppezzamenti;
    }

    /**
     * @param secondoAppezzamenti the secondoAppezzamenti to set
     */
    public void setSecondoAppezzamenti(UIComponent secondoAppezzamenti) {
        this.secondoAppezzamenti = secondoAppezzamenti;
    }

    /**
     * @return the page
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return the aziendaEdit
     */
    public db.AziendeI getAziendaEdit() {
        return aziendaEdit;
    }

    /**
     * @param aziendaEdit the aziendaEdit to set
     */
    public void setAziendaEdit(db.AziendeI aziendaEdit) {
        this.aziendaEdit = aziendaEdit;
    }

    /**
     * @return the currentAppIndex
     */
    public int getCurrentAppIndex() {
        return currentAppIndex;
    }

    /**
     * @param currentAppIndex the currentAppIndex to set
     */
    public void setCurrentAppIndex(int currentAppIndex) {
        this.currentAppIndex = currentAppIndex;
    }

    /**
     * @return the currentTypeAz
     */
    public String getCurrentTypeAz() {
        return currentTypeAz;
    }

    /**
     * @param currentTypeAz the currentTypeAz to set
     */
    public void setCurrentTypeAz(String currentTypeAz) {
        this.currentTypeAz = currentTypeAz;
    }

    /**
     * @return the currentTypeAn
     */
    public String getCurrentTypeAn() {
        return currentTypeAn;
    }

    /**
     * @param currentTypeAn the currentTypeAn to set
     */
    public void setCurrentTypeAn(String currentTypeAn) {
        this.currentTypeAn = currentTypeAn;
    }

    /**
     * @return the currentTypeSce
     */
    public String getCurrentTypeSce() {
        return currentTypeSce;
    }

    /**
     * @param currentTypeSce the currentTypeSce to set
     */
    public void setCurrentTypeSce(String currentTypeSce) {
        this.currentTypeSce = currentTypeSce;
    }

   
    
}

