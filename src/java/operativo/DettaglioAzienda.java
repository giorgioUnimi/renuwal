/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
 import org.eclipse.persistence.jpa.JpaEntityManager;
 import org.eclipse.persistence.sessions.UnitOfWork;
 import org.eclipse.persistence.sessions.server.ServerSession;
import operativo.dettaglio.DettaglioCuaa;
import org.richfaces.component.AbstractExtendedDataTable;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="dettaglioAzienda")
@RequestScoped
public class DettaglioAzienda implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    
    /**
     * contiene le caratteristiche chimiche delle singole 4 tipologie di refluo
     * ovvero liquame bovino , liquamne suino , letame bovino , letame suinno
     */
    private List<Refluo> listaCaratteristiche = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    //private HtmlDataTable dataTableLet;
    //private ager.Refluo dataItemLet;
    //private HtmlDataTable dataTableLiq;
    //private ager.Refluo dataItemLiq;
    //private javax.faces.component.UIComponent valoreLiq;
    private String selectionMode = "single";
    private Collection<Object> selection;
    private List<Refluo> selezioneCaratteristiche = new LinkedList<Refluo>();
    private String desc;
        
       /* private double sauzv = 0;
        private double sauzvn = 0;
        private double mas_lordo = 0;
        private double eff_media = 0;
        private double maxnsau = 0;
        private double maxncolture = 0;
        private double distanzacentro = 0;
        */
       // private List<db.DatiRimozioneazoto> datiRimozione ;
        //private double distanzacentro = 0;
    public DettaglioCuaa detto ;
        
    private double valore;
    private String tipologia;
    private int posizione;
    private int azione;
        
    public DettaglioAzienda()
    {
        
         //nascondiMostraRisultati("tabellaAzienda",false);
          //nascondiMostraRisultati("tabellaAziendaLetPan",false);
          //nascondiMostraRisultati("tabellaAziendaLiqPan",false);
        
        
        //DettaglioCuaa detto = new DettaglioCuaa();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detto = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(detto.getCuaa(),detto.getScenario());
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() +"  cuaa "+ detto.getCuaa() +  " detto.getScenario() " + detto.getScenario() + " detto.getIdscenario() "  + detto.getIdscenario() + " alternativa " + detto.getAlternativaN()+" detto anno " + detto.getAnno());
        
        
        if(detto.getScenario() == 0)
            return;
   
            contenitore.getData(detto.getScenario());        
       
       ContenitoreReflui contenitoreIniziale = contenitore.getContenitore();
        
       contenitoreIniziale.stampaContenuto();
        
        for(String s:contenitoreIniziale.getTipologie())
        {
            if(s.contains("Liquame")) {
                listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
                System.out.println("aggiungo liquame");
            }
            
            if(s.contains("Letame")) {
                listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
                 System.out.println("aggiungo letame");
            }
        }
                
        listaCaratteristiche.add(contenitoreIniziale.totale("Letame"));
        listaCaratteristiche.add(contenitoreIniziale.totale("Liquame"));
        listaCaratteristiche.add(contenitoreIniziale.totale("Totale"));       
       
        //salvo la configurazione calcolata nel db
       salvaCaratteristiche(detto.getIdscenario(),contenitoreIniziale);
        
    
        
        for(Refluo re: this.listaCaratteristicheLet)
        {
                       System.out.println("*-*-*-*-*- " + this.getClass().getCanonicalName() +  " tipo " + re.getTipologia() +" azo "+ re.getAzotoammoniacale() +" metri "+ re.getMetricubi());

        }
        
    
        
        //nascondi la tabella dei dettagli delle caratteristiche chimiche
        nascondiDettaglio();
    }
    
    
    private void stampaSelezione()
    {
        Iterator<Refluo> iterRefluo =this.selezioneCaratteristiche.listIterator();
        
        while(iterRefluo.hasNext())
        {
            ager.Refluo temp = iterRefluo.next();
            
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " "+ Thread.currentThread().getStackTrace()[1].getMethodName() + " metri " +temp.getMetricubi() + " az amm " +temp.getAzotoammoniacale() +" az tot " + temp.getAzotototale());
        }
    }
    
    public void actionMethod()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() +" desc  " +  this.desc);
    }
    
    public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
        selezioneCaratteristiche.clear();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selezioneCaratteristiche.add((Refluo) dataTable.getRowData());
            }
        }
        dataTable.setRowKey(originalKey);
    }
    
    
    
    
    /**
     * salva nel db.caratteristiche la configurazione calcolata delle caratteristiche chimiche
     * se non è ancora presente altrimenti non fa nulla
     * @param idscenario
     * @param reflui 
     */
    private void salvaCaratteristiche(Long idscenario,ContenitoreReflui reflui)
    {
         Connessione connessione = null;
        
                
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal1");
         }
         
         
         System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario " + idscenario);
         
         Query q = entityManager.createNamedQuery("CaratteristicheChmiche.findByIdScenario").setParameter("idScenario",idscenario);
         List<db.CaratteristicheChmiche> listaCaratteristiche1 = q.getResultList();
         db.CaratteristicheChmiche caratteristica = listaCaratteristiche1.get(0);
        
        /* if(!listaCaratteristiche1.isEmpty()) {
             connessione.chiudi();
             return ;
        }*/
          System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario 1 " + idscenario);
         //caratteristica = new db.CaratteristicheChmiche(new Long(idscenario));
         
         for(String tipologia:reflui.getTipologie())
         {
            Refluo reT = reflui.getTipologia(tipologia); 
            
              System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario " + idscenario + " tipologia che aggiungo " + tipologia);
            switch(tipologia)
            {
                case "Liquame Bovino":
                    caratteristica.setM3LBovS(reT.getMetricubi());
                    caratteristica.setM3LBovU(reT.getMetricubi());
                    caratteristica.setTanLBovS(reT.getAzotoammoniacale());
                    caratteristica.setTanLBovU(reT.getAzotoammoniacale());
                    caratteristica.setTknLBovS(reT.getAzotototale());
                    caratteristica.setTknLBovU(reT.getAzotototale());
                    caratteristica.setDmLBovS(reT.getSostanzasecca());
                    caratteristica.setDmLBovU(reT.getSostanzasecca());
                    caratteristica.setVsLBovS(reT.getSolidivolatili());
                    caratteristica.setVsLBovU(reT.getSolidivolatili());
                    caratteristica.setPLBovS(reT.getFosforototale());
                    caratteristica.setPLBovU(reT.getFosforototale());
                    caratteristica.setKLBovS(reT.getPotassiototale());
                    caratteristica.setKLBovU(reT.getPotassiototale());
                    break;
                case "Liquame Suino":
                    caratteristica.setM3LSuiS(reT.getMetricubi());
                    caratteristica.setM3LSuiU(reT.getMetricubi());
                    caratteristica.setTanLSuiS(reT.getAzotoammoniacale());
                    caratteristica.setTanLSuiU(reT.getAzotoammoniacale());
                    caratteristica.setTknLSuiS(reT.getAzotototale());
                    caratteristica.setTknLSuiU(reT.getAzotototale());
                    caratteristica.setDmLSuiS(reT.getSostanzasecca());
                    caratteristica.setDmLSuiU(reT.getSostanzasecca());
                    caratteristica.setVsLSuiS(reT.getSolidivolatili());
                    caratteristica.setVsLSuiU(reT.getSolidivolatili());
                    caratteristica.setPLSuiS(reT.getFosforototale());
                    caratteristica.setPLSuiU(reT.getFosforototale());
                    caratteristica.setKLSuiS(reT.getPotassiototale());
                    caratteristica.setKLSuiU(reT.getPotassiototale());
                    break;
                case "Liquame Avicolo":
                    caratteristica.setM3LAviS(reT.getMetricubi());
                    caratteristica.setM3LAviU(reT.getMetricubi());
                    caratteristica.setTanLAviS(reT.getAzotoammoniacale());
                    caratteristica.setTanLAviU(reT.getAzotoammoniacale());
                    caratteristica.setTknLAviS(reT.getAzotototale());
                    caratteristica.setTknLAviU(reT.getAzotototale());
                    caratteristica.setDmLAviS(reT.getSostanzasecca());
                    caratteristica.setDmLAviU(reT.getSostanzasecca());
                    caratteristica.setVsLAviS(reT.getSolidivolatili());
                    caratteristica.setVsLAviU(reT.getSolidivolatili());
                    caratteristica.setPLAviS(reT.getFosforototale());
                    caratteristica.setPLAviU(reT.getFosforototale());
                    caratteristica.setKLAviS(reT.getPotassiototale());
                    caratteristica.setKLAviU(reT.getPotassiototale());
                    break;
                case "Liquame Altro":
                    caratteristica.setM3LAltS(reT.getMetricubi());
                    caratteristica.setM3LAltU(reT.getMetricubi());
                    caratteristica.setTanLAltS(reT.getAzotoammoniacale());
                    caratteristica.setTanLAltU(reT.getAzotoammoniacale());
                    caratteristica.setTknLAltS(reT.getAzotototale());
                    caratteristica.setTknLAltU(reT.getAzotototale());
                    caratteristica.setDmLAltS(reT.getSostanzasecca());
                    caratteristica.setDmLAltU(reT.getSostanzasecca());
                    caratteristica.setVsLAltS(reT.getSolidivolatili());
                    caratteristica.setVsLAltU(reT.getSolidivolatili());
                    caratteristica.setPLAltS(reT.getFosforototale());
                    caratteristica.setPLAltU(reT.getFosforototale());
                    caratteristica.setKLAltS(reT.getPotassiototale());
                    caratteristica.setKLAltU(reT.getPotassiototale());
                    break;
                case "Liquame Biomassa":
                    caratteristica.setM3LBioS(reT.getMetricubi());
                    caratteristica.setM3LBioU(reT.getMetricubi());
                    caratteristica.setTanLBioS(reT.getAzotoammoniacale());
                    caratteristica.setTanLBioU(reT.getAzotoammoniacale());
                    caratteristica.setTknLBioS(reT.getAzotototale());
                    caratteristica.setTknLBioU(reT.getAzotototale());
                    caratteristica.setDmLBioS(reT.getSostanzasecca());
                    caratteristica.setDmLBioU(reT.getSostanzasecca());
                    caratteristica.setVsLBioS(reT.getSolidivolatili());
                    caratteristica.setVsLBioU(reT.getSolidivolatili());
                    caratteristica.setPLBioS(reT.getFosforototale());
                    caratteristica.setPLBioU(reT.getFosforototale());
                    caratteristica.setKLBioS(reT.getPotassiototale());
                    caratteristica.setKLBioU(reT.getPotassiototale());
                    break;
                case "Letame Bovino":
                    caratteristica.setM3PBovS(reT.getMetricubi());
                    caratteristica.setM3PBovU(reT.getMetricubi());
                    caratteristica.setTanPBovS(reT.getAzotoammoniacale());
                    caratteristica.setTanPBovU(reT.getAzotoammoniacale());
                    caratteristica.setTknPBovS(reT.getAzotototale());
                    caratteristica.setTknPBovU(reT.getAzotototale());
                    caratteristica.setDmPBovS(reT.getSostanzasecca());
                    caratteristica.setDmPBovU(reT.getSostanzasecca());
                    caratteristica.setVsPBovS(reT.getSolidivolatili());
                    caratteristica.setVsPBovU(reT.getSolidivolatili());
                    caratteristica.setPPBovS(reT.getFosforototale());
                    caratteristica.setPPBovU(reT.getFosforototale());
                    caratteristica.setKPBovS(reT.getPotassiototale());
                    caratteristica.setKPBovU(reT.getPotassiototale());
                    break;
                case "Letame Suino":
                    caratteristica.setM3PSuiS(reT.getMetricubi());
                    caratteristica.setM3PSuiU(reT.getMetricubi());
                    caratteristica.setTanPSuiS(reT.getAzotoammoniacale());
                    caratteristica.setTanPSuiU(reT.getAzotoammoniacale());
                    caratteristica.setTknPSuiS(reT.getAzotototale());
                    caratteristica.setTknPSuiU(reT.getAzotototale());
                    caratteristica.setDmPSuiS(reT.getSostanzasecca());
                    caratteristica.setDmPSuiU(reT.getSostanzasecca());
                    caratteristica.setVsPSuiS(reT.getSolidivolatili());
                    caratteristica.setVsPSuiU(reT.getSolidivolatili());
                    caratteristica.setPPSuiS(reT.getFosforototale());
                    caratteristica.setPPSuiU(reT.getFosforototale());
                    caratteristica.setKPSuiS(reT.getPotassiototale());
                    caratteristica.setKPSuiU(reT.getPotassiototale());
                    break;
                case "Letame Avicolo":
                    caratteristica.setM3PAviS(reT.getMetricubi());
                    caratteristica.setM3PAviU(reT.getMetricubi());
                    caratteristica.setTanPAviS(reT.getAzotoammoniacale());
                    caratteristica.setTanPAviU(reT.getAzotoammoniacale());
                    caratteristica.setTknPAviS(reT.getAzotototale());
                    caratteristica.setTknPAviU(reT.getAzotototale());
                    caratteristica.setDmPAviS(reT.getSostanzasecca());
                    caratteristica.setDmPAviU(reT.getSostanzasecca());
                    caratteristica.setVsPAviS(reT.getSolidivolatili());
                    caratteristica.setVsPAviU(reT.getSolidivolatili());
                    caratteristica.setPPAviS(reT.getFosforototale());
                    caratteristica.setPPAviU(reT.getFosforototale());
                    caratteristica.setKPAviS(reT.getPotassiototale());
                    caratteristica.setKPAviU(reT.getPotassiototale());
                    break;
                case "Letame Altro":
                    caratteristica.setM3PAltS(reT.getMetricubi());
                    caratteristica.setM3PAltU(reT.getMetricubi());
                    caratteristica.setTanPAltS(reT.getAzotoammoniacale());
                    caratteristica.setTanPAltU(reT.getAzotoammoniacale());
                    caratteristica.setTknPAltS(reT.getAzotototale());
                    caratteristica.setTknPAltU(reT.getAzotototale());
                    caratteristica.setDmPAltS(reT.getSostanzasecca());
                    caratteristica.setDmPAltU(reT.getSostanzasecca());
                    caratteristica.setVsPAltS(reT.getSolidivolatili());
                    caratteristica.setVsPAltU(reT.getSolidivolatili());
                    caratteristica.setPPAltS(reT.getFosforototale());
                    caratteristica.setPPAltU(reT.getFosforototale());
                    caratteristica.setKPAltS(reT.getPotassiototale());
                    caratteristica.setKPAltU(reT.getPotassiototale());
                    break;
                case "Letame Biomassa":
                    caratteristica.setM3PBioS(reT.getMetricubi());
                    caratteristica.setM3PBioU(reT.getMetricubi());
                    caratteristica.setTanPBioS(reT.getAzotoammoniacale());
                    caratteristica.setTanPBioU(reT.getAzotoammoniacale());
                    caratteristica.setTknPBioS(reT.getAzotototale());
                    caratteristica.setTknPBioU(reT.getAzotototale());
                    caratteristica.setDmPBioS(reT.getSostanzasecca());
                    caratteristica.setDmPBioU(reT.getSostanzasecca());
                    caratteristica.setVsPBioS(reT.getSolidivolatili());
                    caratteristica.setVsPBioU(reT.getSolidivolatili());
                    caratteristica.setPPBioS(reT.getFosforototale());
                    caratteristica.setPPBioU(reT.getFosforototale());
                    caratteristica.setKPBioS(reT.getPotassiototale());
                    caratteristica.setKPBioU(reT.getPotassiototale());
                    break;    
            }
           
         }
         
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
            caratteristica.setIdScenario(new Long(idscenario));
            entityManager.persist(caratteristica);
        tx.commit();

         
         connessione.chiudi();
    }
//    private void impostaDatiRimozioneAzoto(String cuaa,int scenario)
//    {
//        Connessione connessione = null;
//        
//        
//        setDatiRimozione(new LinkedList<db.DatiRimozioneazoto>());
//        
//         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
//         {
//             connessione = Connessione.getInstance();
//             entityManager = connessione.apri("renuwal1");
//         }
//        
//        /**
//         * mi serve per cancellare la cache prodotto dalla precedente query 
//         */
//        entityManager.getEntityManagerFactory().getCache().evictAll();
//        
//        Query q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",cuaa);
//                 
//        db.AziendeI azienda =(db.AziendeI) q.getResultList().get(0);
//        
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " cuaa " + cuaa + " scenario " + scenario);
//        q = entityManager.createQuery("Select s from ScenarioI s where s.cuaa =?1 and s.id =?2");
//        q.setParameter(1, azienda);
//        q.setParameter(2, scenario);
//        db.ScenarioI sce = (db.ScenarioI)q.getSingleResult();
//        
//        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " cuaa " + cuaa + " scenario " + scenario + " sce " + sce.getIdscenario());
//
//        
//        
//        db.DatiRimozioneazoto rimozioneAzoto = sce.getDatiRimozioneazoto();
//        db.DatiRimozioneazoto azotorimosso = new db.DatiRimozioneazoto();
//        
//       azotorimosso.setSauzv(rimozioneAzoto.getSauzv());
//            
//        azotorimosso.setSauzvn( rimozioneAzoto.getSauzvn());
//        azotorimosso.setMasLordo(rimozioneAzoto.getMasLordo());
//        azotorimosso.setEffMedia(rimozioneAzoto.getEffMedia());
//        azotorimosso.setMaxnsau(rimozioneAzoto.getMaxnsau());
//        azotorimosso.setMaxncolture(rimozioneAzoto.getMaxncolture());
//        
//        datiRimozione.add(azotorimosso);
//        
//        db.DistanzaCentroParticelle distanzaCentro1 = sce.getDistanzaCentroParticelle();
//        distanzacentro = distanzaCentro1.getDistanza();
//        
//         
//        connessione.chiudi();
//           
//    }

    /**
     * @return the listaCaratteristiche
     */
    public List<Refluo> getListaCaratteristiche() {
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName());
        
        return listaCaratteristiche;
    }

    /**
     * @param listaCaratteristiche the listaCaratteristiche to set
     */
    public void setListaCaratteristiche(List<Refluo> listaCaratteristiche) {
        this.listaCaratteristiche = listaCaratteristiche;
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName());

    }

    /**
     * @return the listaCaratteristicheLiq
     */
    public List<Refluo> getListaCaratteristicheLiq() {
        return listaCaratteristicheLiq;
    }

    /**
     * @param listaCaratteristicheLiq the listaCaratteristicheLiq to set
     */
    public void setListaCaratteristicheLiq(List<Refluo> listaCaratteristicheLiq) {
        this.listaCaratteristicheLiq = listaCaratteristicheLiq;
    }

    /**
     * @return the listaCaratteristicheLet
     */
    public List<Refluo> getListaCaratteristicheLet() {
        return listaCaratteristicheLet;
    }

    /**
     * @param listaCaratteristicheLet the listaCaratteristicheLet to set
     */
    public void setListaCaratteristicheLet(List<Refluo> listaCaratteristicheLet) {
        this.listaCaratteristicheLet = listaCaratteristicheLet;
    }
    
    
  /**
      * mostra o nasconde un componente della pagina in funzione 
      * del suo id e della variabile boolena mostra . Se mostra true viene 
      * mostratao se false viene nascosto
      * @param id html id del tag della pagina
      * @param mostra se true mostra i componente se false lo nasconde
      * @return true se i componente è stato trovato false altrimenti
      */
    private boolean nascondiMostraRisultati(String id,boolean mostra)
    {
        UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
        //UIComponent comp = view.findComponent(id);
        
        
        //FacesContext context = FacesContext.getCurrentInstance();
    //UIViewRoot root = context.getViewRoot();

    
    UIComponent comp = findComponent(root, id);
   // return c.getClientId(context);

        
        
        if(comp != null)
        {
            System.out.println("id componente "+ id+ "  client id   " + comp.getClientId());
            comp.setRendered(mostra);
            return true;        
        }
        else
            return false;
        
       
        
    }
     
    
    
    /**
   * Finds component with the given id
   */
  private UIComponent findComponent(UIComponent c, String id) {
    if (id.equals(c.getId())) {
      return c;
    }
    Iterator<UIComponent> kids = c.getFacetsAndChildren();
    while (kids.hasNext()) {
      UIComponent found = findComponent(kids.next(), id);
      if (found != null) {
        return found;
      }
    }
    return null;
  }
    
  
  public void mostraDettaglio()
  {
      nascondiMostraRisultati("tabellaAziendaLetPan",true);
       nascondiMostraRisultati("tabellaAziendaLiqPan",true);
  }
  
  
  public void nascondiDettaglio()
  {
      nascondiMostraRisultati("tabellaAziendaLetPan",false);
      nascondiMostraRisultati("tabellaAziendaLiqPan",false);
  }

    /**
     * @return the datiRimozione
     */
//    public List<db.DatiRimozioneazoto> getDatiRimozione() {
//        return datiRimozione;
//    }
//
//    /**
//     * @param datiRimozione the datiRimozione to set
//     */
//    public void setDatiRimozione(List<db.DatiRimozioneazoto> datiRimozione) {
//        this.datiRimozione = datiRimozione;
//    }

    
   /**
    * usato da bottoni modifica e ripristina della pagina dettaglioazienda
    * @param valore del campo inputtext inserito dall utente
    * @param posizione 1 metri cubi 2 azoto totale 3 azoto ammoniacale 4 sostanza secca 5 solidi volatili 6 fosforo 7 potassio 
    * @param tipologia  "Liquame Bovino","Liquame Suino","Liquame Avicolo","Liquame Altro","Liquame Biomassa","Letame Bovino","Letame Suino","Letame Avicolo","Letame Altro","Letame Biomassa"
    * @param azione 0 nodifica 1 ripristina
    */
   //public void modificaRipristina(Double valore,String tipologia,int posizione,int azione)
   public void modificaRipristina()         
   {
       if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal1");
             entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
        
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", detto.getIdscenario());
       db.ScenarioI sce = (db.ScenarioI)q.getResultList().get(0);
       db.CaratteristicheChmiche caratteristichechimiche = sce.getCaratteristicheChmiche();
       
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() + " valore passato  " +getValore());
       
       /**
        * se azione è 0 modifico la caratteristica utente usando valore
        * se azione è 1 rirpistino il valore della caratteristica usando il 
        * valore della caratteristica di sistema
        */
       switch (getTipologia()) {
           case "Liquame Bovino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LBovU(getValore());
                       } else {
                           caratteristichechimiche.setM3LBovU(caratteristichechimiche.getM3LBovS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBovS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLBovU(getValore());
                       } else {
                           caratteristichechimiche.setTknLBovU(caratteristichechimiche.getTknLBovS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBovS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLBovU(getValore());
                       } else {
                           caratteristichechimiche.setTanLBovU(caratteristichechimiche.getTanLBovS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBovS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLBovU(getValore());
                       } else {
                           caratteristichechimiche.setDmLBovU(caratteristichechimiche.getDmLBovS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBovS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLBovU(getValore());
                       } else {
                           caratteristichechimiche.setVsLBovU(caratteristichechimiche.getVsLBovS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBovS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLBovU(getValore());
                       } else {
                           caratteristichechimiche.setKLBovU(caratteristichechimiche.getKLBovS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBovS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLBovU(getValore());
                       } else {
                           caratteristichechimiche.setPLBovU(caratteristichechimiche.getPLBovS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBovS());
                       }
                       break;
               }
               break;
           case "Liquame Suino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LSuiU(getValore());
                       } else {
                           caratteristichechimiche.setM3LSuiU(caratteristichechimiche.getM3LSuiS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LSuiS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTknLSuiU(caratteristichechimiche.getTknLSuiS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLSuiS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTanLSuiU(caratteristichechimiche.getTanLSuiS());
                            //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLSuiS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setDmLSuiU(caratteristichechimiche.getDmLSuiS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLSuiS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setVsLSuiU(caratteristichechimiche.getVsLSuiS());
                           // getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLSuiS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setKLSuiU(caratteristichechimiche.getKLSuiS());
                          // getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLSuiS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setPLSuiU(caratteristichechimiche.getPLSuiS());
                          // getDataItemLiq().setFosforototale(caratteristichechimiche.getPLSuiS());
                       }
                       break;
               }
               break;
           case "Liquame Avicolo":
               // setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LAviU(getValore());
                       } else {
                           caratteristichechimiche.setM3LAviU(caratteristichechimiche.getM3LAviS());
                          // getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAviS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLAviU(getValore());
                       } else {
                           caratteristichechimiche.setTknLAviU(caratteristichechimiche.getTknLAviS());
                           // getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAviS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLAviU(getValore());
                       } else {
                           caratteristichechimiche.setTanLAviU(caratteristichechimiche.getTanLAviS());
                          // getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAviS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLAviU(getValore());
                       } else {
                           caratteristichechimiche.setDmLAviU(caratteristichechimiche.getDmLAviS());
                           // getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAviS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLAviU(getValore());
                       } else {
                           caratteristichechimiche.setVsLAviU(caratteristichechimiche.getVsLAviS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAviS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLAviU(getValore());
                       } else {
                           caratteristichechimiche.setKLAviU(caratteristichechimiche.getKLAviS());
                            //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAviS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLAviU(getValore());
                       } else {
                           caratteristichechimiche.setPLAviU(caratteristichechimiche.getPLAviS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAviS());
                       }
                       break;
               }
               break;
           case "Liquame Altro":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LAltU(getValore());
                       } else {
                           caratteristichechimiche.setM3LAltU(caratteristichechimiche.getM3LAltS());
                            //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAltS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLAltU(getValore());
                       } else {
                           caratteristichechimiche.setTknLAltU(caratteristichechimiche.getTknLAltS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAltS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLAltU(getValore());
                       } else {
                           caratteristichechimiche.setTanLAltU(caratteristichechimiche.getTanLAltS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAltS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLAltU(getValore());
                       } else {
                           caratteristichechimiche.setDmLAltU(caratteristichechimiche.getDmLAltS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAltS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLAltU(getValore());
                       } else {
                           caratteristichechimiche.setVsLAltU(caratteristichechimiche.getVsLAltS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAltS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLAltU(getValore());
                       } else {
                           caratteristichechimiche.setKLAltU(caratteristichechimiche.getKLAltS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAltS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLAltU(getValore());
                       } else {
                           caratteristichechimiche.setPLAltU(caratteristichechimiche.getPLAltS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAltS());
                       }
                       break;
               }
               break;
           case "Liquame Biomassa":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LBioU(getValore());
                       } else {
                           caratteristichechimiche.setM3LBioU(caratteristichechimiche.getM3LBioS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBioS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLBioU(getValore());
                       } else {
                           caratteristichechimiche.setTknLBioU(caratteristichechimiche.getTknLBioS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBioS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLBioU(getValore());
                       } else {
                           caratteristichechimiche.setTanLBioU(caratteristichechimiche.getTanLBioS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBioS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLBioU(getValore());
                       } else {
                           caratteristichechimiche.setDmLBioU(caratteristichechimiche.getDmLBioS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBioS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLBioU(getValore());
                       } else {
                           caratteristichechimiche.setVsLBioU(caratteristichechimiche.getVsLBioS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBioS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLBioU(getValore());
                       } else {
                           caratteristichechimiche.setKLBioU(caratteristichechimiche.getKLBioS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBioS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLBioU(getValore());
                       } else {
                           caratteristichechimiche.setPLBioU(caratteristichechimiche.getPLBioS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBioS());
                       }
                       break;
               }
               break;
           case "Letame Bovino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PBovU(getValore());
                       } else {
                           caratteristichechimiche.setM3PBovU(caratteristichechimiche.getM3PBovS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PBovS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBovU(caratteristichechimiche.getTknPBovS());
                           //dataItemLet.setAzotototale(caratteristichechimiche.getTknPBovS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBovU(caratteristichechimiche.getTanPBovS());
                           //dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBovS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBovU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBovU(caratteristichechimiche.getDmPBovS());
                           //dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBovS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBovU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBovU(caratteristichechimiche.getVsPBovS());
                           //dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBovS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBovU(getValore());
                       } else {
                           caratteristichechimiche.setKPBovU(caratteristichechimiche.getKPBovS());
                           //dataItemLet.setPotassiototale(caratteristichechimiche.getKPBovS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBovU(getValore());
                       } else {
                           caratteristichechimiche.setPPBovU(caratteristichechimiche.getPPBovS());
                           //dataItemLet.setFosforototale(caratteristichechimiche.getPPBovS());
                       }
                       break;
               }
               break;
           case "Letame Suino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PSuiU(getValore());
                       } else {
                           caratteristichechimiche.setM3PSuiU(caratteristichechimiche.getM3PSuiS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PSuiS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTknPSuiU(caratteristichechimiche.getTknPSuiS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPSuiS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTanPSuiU(caratteristichechimiche.getTanPSuiS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPSuiS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setDmPSuiU(caratteristichechimiche.getDmPSuiS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPSuiS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setVsPSuiU(caratteristichechimiche.getVsPSuiS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPSuiS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setKPSuiU(caratteristichechimiche.getKPSuiS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPSuiS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setPPSuiU(caratteristichechimiche.getPPSuiS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPSuiS());
                       }
                       break;
               }
               break;
           case "Letame Avicolo":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PAviU(getValore());
                       } else {
                           caratteristichechimiche.setM3PAviU(caratteristichechimiche.getM3PAviS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAviS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPAviU(getValore());
                       } else {
                           caratteristichechimiche.setTknPAviU(caratteristichechimiche.getTknPAviS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAviS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPAviU(getValore());
                       } else {
                           caratteristichechimiche.setTanPAviU(caratteristichechimiche.getTanPAviS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAviS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPAviU(getValore());
                       } else {
                           caratteristichechimiche.setDmPAviU(caratteristichechimiche.getDmPAviS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAviS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPAviU(getValore());
                       } else {
                           caratteristichechimiche.setVsPAviU(caratteristichechimiche.getVsPAviS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAviS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPAviU(getValore());
                       } else {
                           caratteristichechimiche.setKPAviU(caratteristichechimiche.getKPAviS());
                         //  dataItemLet.setPotassiototale(caratteristichechimiche.getKPAviS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPAviU(getValore());
                       } else {
                           caratteristichechimiche.setPPAviU(caratteristichechimiche.getPPAviS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAviS());
                       }
                       break;
               }
               break;
           case "Letame Altro":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PAltU(getValore());
                       } else {
                           caratteristichechimiche.setM3PAltU(caratteristichechimiche.getM3PAltS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAltS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPAltU(getValore());
                       } else {
                           caratteristichechimiche.setTknPAltU(caratteristichechimiche.getTknPAltS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAltS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPAltU(getValore());
                       } else {
                           caratteristichechimiche.setTanPAltU(caratteristichechimiche.getTanPAltS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAltS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPAltU(getValore());
                       } else {
                           caratteristichechimiche.setDmPAltU(caratteristichechimiche.getDmPAltS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAltS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPAltU(getValore());
                       } else {
                           caratteristichechimiche.setVsPAltU(caratteristichechimiche.getVsPAltS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAltS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPAltU(getValore());
                       } else {
                           caratteristichechimiche.setKPAltU(caratteristichechimiche.getKPAltS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPAltS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPAltU(getValore());
                       } else {
                           caratteristichechimiche.setPPAltU(caratteristichechimiche.getPPAltS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAltS());
                       }
                       break;
               }
               break;
           case "Letame Biomassa":
               // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PBioU(getValore());
                       } else {
                          
                           caratteristichechimiche.setM3PBioU(caratteristichechimiche.getM3PBioS());
                         
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PBioS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBioU(caratteristichechimiche.getTknPBioS());
                          
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPBioS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBioU(caratteristichechimiche.getTanPBioS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBioS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBioU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBioU(caratteristichechimiche.getDmPBioS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBioS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBioU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBioU(caratteristichechimiche.getVsPBioS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBioS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBioU(getValore());
                       } else {
                           caratteristichechimiche.setKPBioU(caratteristichechimiche.getKPBioS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPBioS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBioU(getValore());
                       } else {
                           caratteristichechimiche.setPPBioU(caratteristichechimiche.getPPBioS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPBioS());
                       }
                       break;
               }
               break;
       }
       
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();
      entityManager.persist(caratteristichechimiche);
      tx.commit();
       
      System.out.println(this.getClass().getCanonicalName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+ " valore " + getValore() +  " idscenario " +detto.getIdscenario() + "posizione " + getPosizione() + " azione " + getAzione() + " tipologia " + getTipologia());
  
//      if(azione == 1)
//  {
//       int index = this.getDataTable().getRowIndex(); 
//      
//      dataItem =(ager.Refluo) getDataTable().getRowData();
//     
//      dataItem.setAzotototale(0);
//     
//     
//  }
   
   }

    /**
     * @return the selectionMode
     */
    public String getSelectionMode() {
        return selectionMode;
    }

    /**
     * @param selectionMode the selectionMode to set
     */
    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    /**
     * @return the selection
     */
    public Collection<Object> getSelection() {
        return selection;
    }

    /**
     * @param selection the selection to set
     */
    public void setSelection(Collection<Object> selection) {
        this.selection = selection;
    }

    /**
     * @return the selezioneCaratteristiche
     */
    public List<Refluo> getSelezioneCaratteristiche() {
        return selezioneCaratteristiche;
    }

    /**
     * @param selezioneCaratteristiche the selezioneCaratteristiche to set
     */
    public void setSelezioneCaratteristiche(List<Refluo> selezioneCaratteristiche) {
        this.selezioneCaratteristiche = selezioneCaratteristiche;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

   

    /**
     * @return the valore
     */
    public double getValore() {
        return valore;
    }

    /**
     * @param valore the valore to set
     */
    public void setValore(double valore) {
        this.valore = valore;
    }

    /**
     * @return the tipologia
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * @param tipologia the tipologia to set
     */
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * @return the posizione
     */
    public int getPosizione() {
        return posizione;
    }

    /**
     * @param posizione the posizione to set
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    /**
     * @return the azione
     */
    public int getAzione() {
        return azione;
    }

    /**
     * @param azione the azione to set
     */
    public void setAzione(int azione) {
        this.azione = azione;
    }

    

    /**
     * @return the dataTableLiq
     */
    /*public HtmlDataTable getDataTableLiq() {
        return dataTableLiq;
    }*/

    /**
     * @param dataTableLiq the dataTableLiq to set
     */
    /*public void setDataTableLiq(HtmlDataTable dataTableLiq) {
        this.dataTableLiq = dataTableLiq;
    }*/

    /**
     * @return the dataItemLiq
     */
   /* public ager.Refluo getDataItemLiq() {
        return dataItemLiq;
    }*/

    /**
     * @param dataItemLiq the dataItemLiq to set
     */
    /*public void setDataItemLiq(ager.Refluo dataItemLiq) {
        this.dataItemLiq = dataItemLiq;
    }*/

    /**
     * @return the dataTableLet
     */
    /*public HtmlDataTable getDataTableLet() {
        return dataTableLet;
    }*/

    /**
     * @param dataTableLet the dataTableLet to set
     */
    /*public void setDataTableLet(HtmlDataTable dataTableLet) {
        this.dataTableLet = dataTableLet;
    }*/

    /**
     * @return the valoreLiq
     */
   /* public javax.faces.component.UIComponent getValoreLiq() {
        return valoreLiq;
    }*/

    /**
     * @param valoreLiq the valoreLiq to set
     */
    /*public void setValoreLiq(javax.faces.component.UIComponent valoreLiq) {
        this.valoreLiq = valoreLiq;
    }*/

   
   

    
}
