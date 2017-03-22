/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputHidden;
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


@ManagedBean(name="listaAllevamenti")
@ViewScoped
public class ListaAllevamenti implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<RecordAllevamento> tabella= new ArrayList<RecordAllevamento>();
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private HtmlDataTable dataTable;
    private RecordAllevamento dataItem = new RecordAllevamento();
    
    /**
     * riferimento al contenitore del cuaa aziendale definito 
     * nella pagina dettaglioazienda.xhmtl
     */
    DettaglioCuaa dettCuaa = null;
    
    public ListaAllevamenti() {
        
        tabella.clear();
         
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
       // apri("renuwal2");
        // initialise with one entry
      // tabella.add(new Azienda());
        /**
         * uso le varibili statiche definite nella classe dettagliocuaa che sono cuaa e ragione sociale che rappresentano
         * la chiave di navigazione nel database
         * 
         */
        dettCuaa = new DettaglioCuaa();
        
       // System.out.println("dettaglio cuaa " + dettCuaa.getCuaa() + " nome " + dettCuaa.getRagionesociale());
        
        inizializzaTabella();
        tabella.add(new RecordAllevamento());
        
        
      Connessione.getInstance().chiudi();
    }
    
    
    /**
     * apro la connessione il db postgresql
     * @param connessione "renuwal2"
     */
    /*public boolean apri(String connessione)
    {
        
        
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()) ) {
            entityManagerFactory = Persistence.createEntityManagerFactory(connessione);
            entityManager = entityManagerFactory.createEntityManager();
            jpa = (JpaEntityManager) entityManager.getDelegate();
            serverSession = jpa.getServerSession();
            
            
            System.out.println("apri la connessione sono dentro if ");
        }
        
         if(entityManager.isOpen())
             return true;
         else
             return false;
    }*/
    
    /**
     * chiuso la connesione con il db postgresql
     */
 /*   public void chiudi()
    {
        if(entityManager.isOpen())
        {
            entityManager.close();
            entityManagerFactory.close();
        }
    }*/
    
    
    
    
    /**
     * popola la tabella con il contenuto della tabella del db AziendeI
     */
    private List inizializzaTabella()
    {
        
         getTabella().clear();
         
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
            
      /*  entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();*/
        
        /**
         * mi serve per cancellare la cache dell'entity manager
         */
          entityManager.getEntityManagerFactory().getCache().evictAll();
        
        //Query q = entityManager.createNamedQuery("AziendeI.findByCuaa") .setParameter("cuaa", dettCuaa.getCuaa());
         Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario") .setParameter("idscenario", dettCuaa.getIdscenario());
        //Query q = entityManager.createQuery("SELECT l FROM Allevamentoi l ");
        //List<db.AziendeI> results = q.getResultList();
        db.ScenarioI scena = (db.ScenarioI)q.getSingleResult();
        /**
         * per il momento prendo lo scenario 0 
         */
        //List<db.ScenarioI> scenari =(List) results.get(0).getScenarioICollection();
        
        
        
        
        System.out.println("cuaa in inizilizza tabella " + dettCuaa.getCuaa());
        
        if(scena != null)
        {
           // List<db.AllevamentoI> allevamentiCo =(List) results.get(0).
            List<db.AllevamentoI> allevamentiCo = (List) scena.getAllevamentoICollection();
        
        ListIterator<db.AllevamentoI> iterAllevamenti = allevamentiCo.listIterator(); 
        //ListIterator<db.Aziendei> iterAllevamenti = results.listIterator();
        
        // for(int i=0; i < results.size();i++)
        RecordAllevamento recordAlleva;
        
        while(iterAllevamenti.hasNext())
         {
            // System.out.println("comune " + a.getComune() + " denominazione " + a.getDenominazione() + " cuaa " + a.getAziendePK().getCuaa());
             db.AllevamentoI a = (db.AllevamentoI)iterAllevamenti.next();
             recordAlleva = new RecordAllevamento();
             
             a.getSpeciecategoriaallevamentostabulazionebId();
                     
                     
             /*if(a.getCodCatAllev()!=null) {
                 recordAlleva.setCodCatAllev(a.getCodCatAllev());
             }*/
             
             recordAlleva.setCodCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getId());
             
             
             /*if(a.getCodSpecie()!=null) {
                 recordAlleva.setCodSpecie(a.getCodSpecie());
             }*/
             
             recordAlleva.setCodSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getCodSpecie());
             
             /*if(a.getCodStabulazione()!=null) {
                 recordAlleva.setCodStabulazione(a.getCodStabulazione());
             }*/
             
             recordAlleva.setCodStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getId());
             
             /*if(dettCuaa.getCuaa()!=null) {
                 recordAlleva.setCuaa(dettCuaa.getCuaa());
             }*/
             
             recordAlleva.setCuaa(dettCuaa.getCuaa());

             /*if(a.getDesAllevamento()!=null) {
                 recordAlleva.setDesAllevamento(a.getDesAllevamento());
             }*/
             
             
             recordAlleva.setDesAllevamento(a.getSpeciecategoriaallevamentostabulazionebId().getTipoAllevamentoBId().getDesAllevamento());
             
             
             /*if(a.getDesCatAllev()!=null) {
                 recordAlleva.setDesCatAllev(a.getDesCatAllev());
             }*/
             
             
             recordAlleva.setDesCatAllev(a.getSpeciecategoriaallevamentostabulazionebId().getCategoriabSId().getDesCatAllev());
             
             /*if(a.getDesComunePresentazione()!=null) {
                 recordAlleva.setDesComunePresentazione(a.getDesComunePresentazione());
             }*/
             
             
             recordAlleva.setDesComunePresentazione(dettCuaa.getComune());
             
             
            /* if(a.getDesSpecie()!=null) {
                 recordAlleva.setDesSpecie(a.getDesSpecie());
             }*/
             
             
             recordAlleva.setDesSpecie(a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());
             
             
             System.out.println(this.getClass().getCanonicalName() + " inizializzatabella " + a.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());
             
             /*if(a.getDesStabulazione()!=null) {
                 recordAlleva.setDesStabulazione(a.getDesStabulazione());
             }*/
             
             recordAlleva.setDesStabulazione(a.getSpeciecategoriaallevamentostabulazionebId().getStabulazionebSId().getDesStabulazione());
             
             
             /*if(a.getId()!=null) {
                 recordAlleva.setId(a.getId());
             }*/
             
             
             recordAlleva.setId(a.getId().intValue());
             
             
             if(a.getNumCapiSpecieStab()!=null) {
                 recordAlleva.setNumCapiSpecieStab(a.getNumCapiSpecieStab());
             }
             /*if(a.getPesoVivo()!=null) {
                 recordAlleva.setPesoVivo(a.getPesoVivo());
             }*/
             
             
             recordAlleva.setPesoVivo(a.getSpeciecategoriaallevamentostabulazionebId().getPesoVivoKg().intValue());
             
             /*if(a.getRagioneSociale()!=null) {
                 recordAlleva.setRagioneSociale(a.getRagioneSociale());
             }*/
             
             
             recordAlleva.setRagioneSociale(dettCuaa.getRagionesociale());
             
             
             
                getTabella().add(recordAlleva);
         }
        
           //tabella.add(new RecordAllevamento());
        }
         //entityManager.close();
         
         
       /*    ListIterator<RecordAllevamento> iterll = getTabella().listIterator();
         while(iterll.hasNext())
         {
             RecordAllevamento redd = iterll.next();
             
             System.out.println("stampo tabella dopo ogni aggiunta cuaa in allevamento " + redd.getCuaa() + " descrizione  " + redd.getDesAllevamento() + " id " + redd.getId() );
         }*/
            Connessione.getInstance().chiudi();
           return getTabella();       
         //entityManagerFactory.close();
         
        // serverSession.disconnect();
           
           
          

    }
       
     /*public UnitOfWork acquireUnitOfWork() {
		    return serverSession.acquireClientSession().acquireUnitOfWork();
		  }*/

   

    /**
     * @param tabella the tabella to set
     */
   /* public void setTabella(List<RecordAllevamento> tabella) {
        this.tabella = tabella;
    }*/
    
    public String addRecord() {
        
        /**
         * faccio puntare il dataItem all'ultima riga della tabella 
         * cosi da prendere i dati inseriti dall'utente
         */
        int ultimo = getDataTable().getRowCount();
        getDataTable().setRowIndex(ultimo-1);
         
          /**
         * se l'entityManager è chiuso aprilo uso questo sistema per
         * mantenere una sola connessione aperta e non avere il falso errore
         * di eclipselink class cast exception
         */
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
         
        DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
        //String cuaa = dettaglioCuaa.getCuaa();
        Long idscenario = dettaglioCuaa.getIdscenario();
        entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
                serverSession.dontLogMessages();

        
       
       
        dataItem = (RecordAllevamento) getDataTable().getRowData();
       
        /**
         * recupero il numero id dell'ultimo allevamento presente
         */
        Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",dataItem.getDesSpecie());
                        db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                       // Integer cospecie = sp.getCodSpecie();
                        
                        q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", dataItem.getDesCatAllev());
                        db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                        //Integer cocat = cat.getId();
                        
                        q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", dataItem.getDesAllevamento());
                        db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                        //Integer coalleva = tipoalleva.getId();
                        
                        q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione",dataItem.getDesStabulazione());
                        db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getSingleResult();
                        
                         q = entityManager.createQuery("SELECT DISTINCT l  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                        q.setParameter(1, sp);
                        q.setParameter(2, cat);
                        q.setParameter(3, tipoalleva);
                        q.setParameter(4, tipostabu);
                        
                        db.SpeciecategoriaallevamentostabulazionebS scas = (db.SpeciecategoriaallevamentostabulazionebS)q.getSingleResult();
        /**
         * cerco l'azienda che ha il cuaa specificato e da quell'azienda recupero gli allevamenti
         */
        q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
        q.setParameter(1 , dettaglioCuaa.getIdscenario());
        
        db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
        List<db.AllevamentoI> listaAllevamenti = (List<db.AllevamentoI>)sc.getAllevamentoICollection();
      
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        db.AllevamentoI alleva = new db.AllevamentoI();
        alleva.setIdscenario(sc);
        alleva.setSpeciecategoriaallevamentostabulazionebId(scas);
        alleva.setNumCapiSpecieStab(dataItem.getNumCapiSpecieStab());
        listaAllevamenti.add(alleva);
        entityManager.persist(alleva);
        tx.commit();
        /**
         * creo il record allevamento e lo aggiungo alla lista degli allevamenti della azienda selezionata
         * e pesisto l'azienda selezionata
         */
        //imposto tramite il costruttore l'id
      /*  db.AllevamentoI alleva = new db.AllevamentoI();
        alleva..setId((Long)ultimoA);
     
        alleva.setDesComunePresentazione(dettaglioCuaa.getComune());
        alleva.setRagioneSociale(dettaglioCuaa.getRagionesociale());
        alleva.setDesAllevamento(dataItem.getDesAllevamento());
      
        alleva.setDesSpecie(dataItem.getDesSpecie());
       
        alleva.setDesCatAllev(dataItem.getDesCatAllev());
        
        alleva.setDesStabulazione(dataItem.getDesStabulazione());
        alleva.setNumCapiSpecieStab(dataItem.getNumCapiSpecieStab());
        alleva.setPesoVivo(dataItem.getPesoVivo());
        
        /**
         * aggiungo il record di allevamento impostato secondo le caratteristicvhe inserite dall'utente
         */
       /* listAllevamento.add(alleva);
     
                aziendaT.setAllevamentoiCollection(listAllevamento);

        entityManager.persist(aziendaT);
        tx.commit();
        
   
        
        dataItem.setCuaa(aziendaT.getCuaa());
        dataItem.setId(ultimoA);
       
        getTabella().add(new RecordAllevamento());
     
        
       
          entityManager.close();
                  
       
   */
        getTabella().add(new RecordAllevamento());
         //entityManager.close();
        
       Connessione.getInstance().chiudi();
        return null;
    }

    /**
     * @return the dataTable
     */
    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    /**
     * @param dataTable the dataTable to set
     */
    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }
    
    
    
    
    public String deleteSelected() {
        
        boolean trovato = false;
        String cuaa="";
        int id = 0;
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
                serverSession.dontLogMessages();*/
 /**
         * se l'entityManager è chiuso aprilo uso questo sistema per
         * mantenere una sola connessione aperta e non avere il falso errore
         * di eclipselink class cast exception
         */
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        
        DettaglioCuaa dettoCuaa = new DettaglioCuaa();
        
        
        Iterator<RecordAllevamento> entries = getTabella().iterator();
        while (entries.hasNext()) {
                RecordAllevamento person = entries.next();

                if (person.isSelected()) {
                        trovato = true;
                        cuaa = person.getCuaa();
                       
                        id = person.getId();
                        entries.remove();
                        
                                   System.out.println("selected true    from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato + " " + person.getCuaa() + " " + person.getId());

                }
        }
        
        
       // this.dataTable = null;
      /*    dataItem = (RecordAllevamento1) dataTable.getRowData();
          cuaa = dataItem.getCuaa();
          id = dataItem.getId();*/
       // System.out.println("from delete selected id " + id + " cuaa " + cuaa + " trovato " + trovato);
        
        if(trovato)
        {
            entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("DELETE FROM AllevamentoI l WHERE l.id='"+id+"' ");
        //List<db1.Aziende> results = q.getResultList();
        q.executeUpdate();
        //return ""; // Navigation case.
        
        entityManager.getTransaction().commit();
        
        }
        
        
        //entityManager.close();
        
        Connessione.getInstance().chiudi();
        
       // entityManagerFactory.close();
        return null;
    }

    /**
     * @return the tabella
     */
    public List<RecordAllevamento> getTabella() {
        return tabella;
    }

    /**
     * @param tabella the tabella to set
     */
    public void setTabella(List<RecordAllevamento> tabella) {
     
        this.tabella = tabella;
    }
    
}
