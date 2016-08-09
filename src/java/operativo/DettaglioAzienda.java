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
    
    
    /*private List<Refluo> listaCaratteristicheXml = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLiqXml = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLetXml = new LinkedList<Refluo>();*/
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
      
      /**
       * cosa deve fare : la tabella caratteristiche_chimiche ha i campi utente con _u, i campi calcolati
       * dal software con _s e i booleani con _m . In funzione degli allevamenti, stoccaggi, acque calcola le caratteristiche
       * chimiche dell'azienda e prima di mostrarli a video nella pagina dettaglioazienda.xhtml deve vedere se è 
       * presente uno scenario in carattertistiche_chmiche del db e se c'è mostrare i campi mosdificati dall'utente 
       * 
       * Se l'utente fa una modifica quella modifica finisce nei valori _u 
       * se fa un ripristino il valore viene preso tra quelli con _s .
       * Deve sempre mostrare i valori lato utente sulla pagina web . Al primo accesso al dettaglioAziendale
       * calcola le caratteristiche chimiche dell'azienda in funzione degli allevamenti,stoccaggi ed acque e salva questa
       * caratteristica nella tabella caratteristche_chimiche sia lato utente che lato sistema. 
       * Agli accessi successivi ricalcola le caratteristiche_chimiche dell'azienda e le confronta con quelle nel db lato sistema
       * per verificare se l'utente ha fatto qualche modifica nella composizione degli allevamenti,stoccaggi o acque.
       * 
       * 
       */
        
      /**
       * recupero lo scenario
       */
        //DettaglioCuaa detto = new DettaglioCuaa();
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detto = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(detto.getCuaa(),detto.getScenario());
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() +"  cuaa "+ detto.getCuaa() +  " detto.getScenario() " + detto.getScenario() + " detto.getIdscenario() "  + detto.getIdscenario() + " alternativa " + detto.getAlternativaN()+" detto anno " + detto.getAnno());
                
        if(detto.getIdscenario() == null || detto.getIdscenario() == 0 )
            return;
        
         /**
             * con lo scenario calcolo le caratteristiche chimiche dell'azienda
             */
            contenitore.getData(detto.getIdscenario().intValue());

            ContenitoreReflui contenitoreIniziale = contenitore.getContenitore();
            
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal1");
             entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
        
        
        /**
         * controllo se in caratteristiche_chimiche è gia stata salvata una configurazione
         * con questo scenario se non lo è procedo con il calcolo delle caratteristiche e con 
         * queste popolo listaCaratteristicheLiq e listaCaratteristicheLet
         */
         Query q = entityManager.createNamedQuery("CaratteristicheChmiche.findByIdScenario").setParameter("idScenario",(long)detto.getScenario());
         //Query q = entityManager.createNamedQuery("CaratteristicheChmiche.findByIdScenario").setParameter("idScenario",(long)399);

         List<db.CaratteristicheChmiche> listaCaratteristiche1 = q.getResultList();
         
        if (listaCaratteristiche1.isEmpty()) {
           
            //contenitoreIniziale.stampaContenuto();
            /**
             * popolo le liste listaCaratteristicheLiq e listaCaratteristicheLet
             * usando contenitoreIniziale
             */
            for (String s : contenitoreIniziale.getTipologie()) {
                if (s.contains("Liquame")) {
                    listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
                    //  System.out.println("aggiungo liquame");
                }

                if (s.contains("Letame")) {
                    listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
                    //  System.out.println("aggiungo letame");
                }
            }

            listaCaratteristiche.add(contenitoreIniziale.totale("Letame"));
            listaCaratteristiche.add(contenitoreIniziale.totale("Liquame"));
            listaCaratteristiche.add(contenitoreIniziale.totale("Totale"));

            //salvo la configurazione calcolata nel db sia sul lato sistema che nel lato utente
            //ovvero mette gli stessi valori calcolati sia per l'utente che per il sistema la prima volta
            salvaCaratteristiche(detto.getIdscenario(), contenitoreIniziale);
            /* for(Refluo re: this.listaCaratteristicheLet)
             {
             System.out.println("*-*-*-*-*- " + this.getClass().getCanonicalName() +  " tipo " + re.getTipologia() +" azo "+ re.getAzotoammoniacale() +" metri "+ re.getMetricubi());

             }*/

        }//if listaCaratteristiche1.isEmpty
        else {
            //contiene tutte le caratteristiche chimiche di questo scenario
            db.CaratteristicheChmiche caratteristica = listaCaratteristiche1.get(0);
            //se sono qui è perchè un record nel db tabella caratteristiche_chimiche è gia presente
            //e quindi prendo il contenuto del lato utente e lo mostro ed uso quello del sistema per il 
            //ripristino per se l'utente ha modificato l'assetto zootecnico dell'azienda anche le caratteristiche
            //chimiche calcolate dal programma sono cambuiate e sono diverse da quelle salvate nel db per cui verifico se queste due
            //componenti sono diverse se lo sono salvo le nuove carattersitiche nel lato software delle carattersitche_chimiche(_s)
            //e metto nel lato utente(_u) quelle che hanno il corrispettivo flag a true in caratteristiche_chimiche
            if(confronto(caratteristica,contenitoreIniziale)) {
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " confronto true ");
                //popola le liste liquame e letame che sono usate dalla pagina dettagliAziendale
                //se le caratterstiche chimiche nel db sono uguali a quelle calcolate
                popolaLiquameLetame(caratteristica);
            }
            else{
              System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " confronto false ");

                //altrimenti salvo il contenuto del contenitoreIniziale che contiene le caratteristiche
                //calcolate nel db lato sistema e nel lato utente se questo non è stato modificato
                caratteristica = aggiornaCaratteristiche(caratteristica,contenitoreIniziale,detto.getIdscenario());
                popolaLiquameLetame(caratteristica);
            }
                
        }
        
        //nascondi la tabella dei dettagli delle caratteristiche chimiche
        //nascondiDettaglio();
    }
    
        /**
         * imposta le caratteristiche chimiche nel db lato sistema prendendole da contenitoreIniziale e aggiorna anche il lato
         * utente se non è stato modificato
         * @param caratteristica
         * @param contenitoreIniziale 
         */
        private db.CaratteristicheChmiche aggiornaCaratteristiche(db.CaratteristicheChmiche caratteristica,ContenitoreReflui contenitoreIniziale,long scenario){
                /**
                 * imposto le componenti software _s delle carattersitiche chimiche
                 */
                /**
                 * -----------------------liquame
                 */
                
                //recupero il liquame bovino
                Refluo refluo = contenitoreIniziale.getTipologia("Liquame Bovino");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3LBovS(refluo.getMetricubi());
                caratteristica.setTknLBovS(refluo.getAzotototale());
                caratteristica.setTanLBovS(refluo.getAzotoammoniacale());
                caratteristica.setPLBovS(refluo.getFosforototale());
                caratteristica.setKLBovS(refluo.getPotassiototale());
                caratteristica.setVsLBovS(refluo.getSolidivolatili());
                caratteristica.setDmLBovS(refluo.getSostanzasecca());
                
                
                //recupero il liquame suino
                refluo = contenitoreIniziale.getTipologia("Liquame Suino");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3LSuiS(refluo.getMetricubi());
                caratteristica.setTknLSuiS(refluo.getAzotototale());
                caratteristica.setTanLSuiS(refluo.getAzotoammoniacale());
                caratteristica.setPLSuiS(refluo.getFosforototale());
                caratteristica.setKLSuiS(refluo.getPotassiototale());
                caratteristica.setVsLSuiS(refluo.getSolidivolatili());
                caratteristica.setDmLSuiS(refluo.getSostanzasecca());
                
                
                //recupero il liquame avicolo
                refluo = contenitoreIniziale.getTipologia("Liquame Avicolo");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3LAviS(refluo.getMetricubi());
                caratteristica.setTknLAviS(refluo.getAzotototale());
                caratteristica.setTanLAviS(refluo.getAzotoammoniacale());
                caratteristica.setPLAviS(refluo.getFosforototale());
                caratteristica.setKLAviS(refluo.getPotassiototale());
                caratteristica.setVsLAviS(refluo.getSolidivolatili());
                caratteristica.setDmLAviS(refluo.getSostanzasecca());
                
                 //recupero il liquame altro
                refluo = contenitoreIniziale.getTipologia("Liquame Altro");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3LAltS(refluo.getMetricubi());
                caratteristica.setTknLAltS(refluo.getAzotototale());
                caratteristica.setTanLAltS(refluo.getAzotoammoniacale());
                caratteristica.setPLAltS(refluo.getFosforototale());
                caratteristica.setKLAltS(refluo.getPotassiototale());
                caratteristica.setVsLAltS(refluo.getSolidivolatili());
                caratteristica.setDmLAltS(refluo.getSostanzasecca());
                
                //recupero il liquame biomassa
                refluo = contenitoreIniziale.getTipologia("Liquame Biomassa");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3LBioS(refluo.getMetricubi());
                caratteristica.setTknLBioS(refluo.getAzotototale());
                caratteristica.setTanLBioS(refluo.getAzotoammoniacale());
                caratteristica.setPLBioS(refluo.getFosforototale());
                caratteristica.setKLBioS(refluo.getPotassiototale());
                caratteristica.setVsLBioS(refluo.getSolidivolatili());
                caratteristica.setDmLBioS(refluo.getSostanzasecca());
                
                 
                /**
                 * -----------------------letame
                 */
                //recupero il letame bovino
                refluo = contenitoreIniziale.getTipologia("Letame Bovino");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3PBovS(refluo.getMetricubi());
                caratteristica.setTknPBovS(refluo.getAzotototale());
                caratteristica.setTanPBovS(refluo.getAzotoammoniacale());
                caratteristica.setPPBovS(refluo.getFosforototale());
                caratteristica.setKPBovS(refluo.getPotassiototale());
                caratteristica.setVsPBovS(refluo.getSolidivolatili());
                caratteristica.setDmPBovS(refluo.getSostanzasecca());
                
                
                //recupero il letame suino
                refluo = contenitoreIniziale.getTipologia("Letame Suino");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3PSuiS(refluo.getMetricubi());
                caratteristica.setTknPSuiS(refluo.getAzotototale());
                caratteristica.setTanPSuiS(refluo.getAzotoammoniacale());
                caratteristica.setPPSuiS(refluo.getFosforototale());
                caratteristica.setKPSuiS(refluo.getPotassiototale());
                caratteristica.setVsPSuiS(refluo.getSolidivolatili());
                caratteristica.setDmPSuiS(refluo.getSostanzasecca());
                
                
                //recupero il letame avicolo
                refluo = contenitoreIniziale.getTipologia("Letame Avicolo");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3PAviS(refluo.getMetricubi());
                caratteristica.setTknPAviS(refluo.getAzotototale());
                caratteristica.setTanPAviS(refluo.getAzotoammoniacale());
                caratteristica.setPPAviS(refluo.getFosforototale());
                caratteristica.setKPAviS(refluo.getPotassiototale());
                caratteristica.setVsPAviS(refluo.getSolidivolatili());
                caratteristica.setDmPAviS(refluo.getSostanzasecca());
                
                 //recupero il letame altro
                refluo = contenitoreIniziale.getTipologia("Letame Altro");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3PAltS(refluo.getMetricubi());
                caratteristica.setTknPAltS(refluo.getAzotototale());
                caratteristica.setTanPAltS(refluo.getAzotoammoniacale());
                caratteristica.setPPAltS(refluo.getFosforototale());
                caratteristica.setKPAltS(refluo.getPotassiototale());
                caratteristica.setVsPAltS(refluo.getSolidivolatili());
                caratteristica.setDmPAltS(refluo.getSostanzasecca());
                
                //recupero il letame biomassa
                refluo = contenitoreIniziale.getTipologia("Letame Biomassa");
                //lo imposto nelle caratteristiche chimiche
                caratteristica.setM3PBioS(refluo.getMetricubi());
                caratteristica.setTknPBioS(refluo.getAzotototale());
                caratteristica.setTanPBioS(refluo.getAzotoammoniacale());
                caratteristica.setPPBioS(refluo.getFosforototale());
                caratteristica.setKPBioS(refluo.getPotassiototale());
                caratteristica.setVsPBioS(refluo.getSolidivolatili());
                caratteristica.setDmPBioS(refluo.getSostanzasecca());
                
                 /**
                 * imposto le componenti utenti _u delle carattersitiche chimiche
                 */
                /**
                 * -----------------------liquame
                 */
                //recupero il liquame bovino
                refluo = contenitoreIniziale.getTipologia("Liquame Bovino");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3LBovM()) {
                caratteristica.setM3LBovU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanLBovM()) {
                caratteristica.setTknLBovU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknLBovM()) {
                caratteristica.setTanLBovU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPLBovM()) {
                caratteristica.setPLBovU(refluo.getFosforototale());
            }
            if (!caratteristica.getKLBovM()) {
                caratteristica.setKLBovU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsLBovM()) {
                caratteristica.setVsLBovU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmLBovM()) {
                caratteristica.setDmLBovU(refluo.getSostanzasecca());
            }
               
            
            //recupero il liquame suino
            refluo = contenitoreIniziale.getTipologia("Liquame Suino");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3LSuiM()) {
                caratteristica.setM3LSuiU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanLSuiM()) {
                caratteristica.setTknLSuiU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknLSuiM()) {
                caratteristica.setTanLSuiU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPLSuiM()) {
                caratteristica.setPLSuiU(refluo.getFosforototale());
            }
            if (!caratteristica.getKLSuiM()) {
                caratteristica.setKLSuiU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsLSuiM()) {
                caratteristica.setVsLSuiU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmLSuiM()) {
                caratteristica.setDmLSuiU(refluo.getSostanzasecca());
            }
            
           //recupero il liquame avicolo
                refluo = contenitoreIniziale.getTipologia("Liquame Avicolo");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3LAviM()) {
                caratteristica.setM3LAviU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanLAviM()) {
                caratteristica.setTknLAviU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknLAviM()) {
                caratteristica.setTanLAviU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPLAviM()) {
                caratteristica.setPLAviU(refluo.getFosforototale());
            }
            if (!caratteristica.getKLAviM()) {
                caratteristica.setKLAviU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsLAviM()) {
                caratteristica.setVsLAviU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmLAviM()) {
                caratteristica.setDmLAviU(refluo.getSostanzasecca());
            }
            
             //recupero il liquame altro
                refluo = contenitoreIniziale.getTipologia("Liquame Altro");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3LAltM()) {
                caratteristica.setM3LAltU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanLAltM()) {
                caratteristica.setTknLAltU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknLAltM()) {
                caratteristica.setTanLAltU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPLAltM()) {
                caratteristica.setPLAltU(refluo.getFosforototale());
            }
            if (!caratteristica.getKLAltM()) {
                caratteristica.setKLAltU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsLAltM()) {
                caratteristica.setVsLAltU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmLAltM()) {
                caratteristica.setDmLAltU(refluo.getSostanzasecca());
            }
            
             //recupero il liquame biomassa
                refluo = contenitoreIniziale.getTipologia("Liquame Biomassa");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3LBioM()) {
                caratteristica.setM3LBioU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanLBioM()) {
                caratteristica.setTknLBioU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknLBioM()) {
                caratteristica.setTanLBioU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPLBioM()) {
                caratteristica.setPLBioU(refluo.getFosforototale());
            }
            if (!caratteristica.getKLBioM()) {
                caratteristica.setKLBioU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsLBioM()) {
                caratteristica.setVsLBioU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmLBioM()) {
                caratteristica.setDmLBioU(refluo.getSostanzasecca());
            }
            
            
            /**
             * modifico la componente letame lato utente
             */
                //recupero il letame bovino
                refluo = contenitoreIniziale.getTipologia("Letame Bovino");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3PBovM()) {
                caratteristica.setM3PBovU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanPBovM()) {
                caratteristica.setTknPBovU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknPBovM()) {
                caratteristica.setTanPBovU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPPBovM()) {
                caratteristica.setPPBovU(refluo.getFosforototale());
            }
            if (!caratteristica.getKPBovM()) {
                caratteristica.setKPBovU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsPBovM()) {
                caratteristica.setVsPBovU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmPBovM()) {
                caratteristica.setDmPBovU(refluo.getSostanzasecca());
            }
               
            
            //recupero il letame suino
            refluo = contenitoreIniziale.getTipologia("Letame Suino");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3PSuiM()) {
                caratteristica.setM3PSuiU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanPSuiM()) {
                caratteristica.setTknPSuiU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknPSuiM()) {
                caratteristica.setTanPSuiU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPPSuiM()) {
                caratteristica.setPPSuiU(refluo.getFosforototale());
            }
            if (!caratteristica.getKPSuiM()) {
                caratteristica.setKPSuiU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsPSuiM()) {
                caratteristica.setVsPSuiU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmPSuiM()) {
                caratteristica.setDmPSuiU(refluo.getSostanzasecca());
            }
            
           //recupero il letame avicolo
                refluo = contenitoreIniziale.getTipologia("Letame Avicolo");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3PAviM()) {
                caratteristica.setM3PAviU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanPAviM()) {
                caratteristica.setTknPAviU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknPAviM()) {
                caratteristica.setTanPAviU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPPAviM()) {
                caratteristica.setPPAviU(refluo.getFosforototale());
            }
            if (!caratteristica.getKPAviM()) {
                caratteristica.setKPAviU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsPAviM()) {
                caratteristica.setVsPAviU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmPAviM()) {
                caratteristica.setDmPAviU(refluo.getSostanzasecca());
            }
            
             //recupero il letame altro
                refluo = contenitoreIniziale.getTipologia("Letame Altro");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3PAltM()) {
                caratteristica.setM3PAltU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanPAltM()) {
                caratteristica.setTknPAltU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknPAltM()) {
                caratteristica.setTanPAltU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPPAltM()) {
                caratteristica.setPPAltU(refluo.getFosforototale());
            }
            if (!caratteristica.getKPAltM()) {
                caratteristica.setKPAltU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsPAltM()) {
                caratteristica.setVsPAltU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmPAltM()) {
                caratteristica.setDmPAltU(refluo.getSostanzasecca());
            }
            
             //recupero il letame biomassa
                refluo = contenitoreIniziale.getTipologia("Letame Biomassa");
                //lo imposto nelle caratteristiche chimiche
                //se non è stato modificato dall'utente lo cambio altrimenti no
            if (!caratteristica.getM3PBioM()) {
                caratteristica.setM3PBioU(refluo.getMetricubi());
            }
            if (!caratteristica.getTanPBioM()) {
                caratteristica.setTknPBioU(refluo.getAzotototale());
            }
            if (!caratteristica.getTknPBioM()) {
                caratteristica.setTanPBioU(refluo.getAzotoammoniacale());
            }
            if (!caratteristica.getPPBioM()) {
                caratteristica.setPPBioU(refluo.getFosforototale());
            }
            if (!caratteristica.getKPBioM()) {
                caratteristica.setKPBioU(refluo.getPotassiototale());
            }
            if (!caratteristica.getVsPBioM()) {
                caratteristica.setVsPBioU(refluo.getSolidivolatili());
            }
            if (!caratteristica.getDmPBioM()) {
                caratteristica.setDmPBioU(refluo.getSostanzasecca());
            }
            
                 Connessione connessione = null;
        
         
            if (entityManagerFactory == null || !(entityManagerFactory.isOpen())) {
                connessione = Connessione.getInstance();
                entityManager = connessione.apri("renuwal1");
            }

            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            caratteristica.setIdScenario(scenario);
            entityManager.persist(caratteristica);
            tx.commit();


            connessione.chiudi();
            
            
            return caratteristica;
         
         
        }
    
    /**
     * confronta il contenuto delle caratteristiche chimiche dell'azienda presente nel db tabella caratteristiche_chimiche 
     * con quelle calcolate da dettaglioazienda se sono diverse ritorna false e salva le caratteristiche calcolate nel db
     * lato software e lato utente controllando se il lato utente è stato modificato dall'utente
     * @return 
     */
    private boolean confronto(db.CaratteristicheChmiche caratteristica,ContenitoreReflui contenitoreIniziale){
        boolean ritorno = true;
        
        if(contenitoreIniziale.getTipologia("Liquame Bovino").getMetricubi() != caratteristica.getM3LBovS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Liquame Suino").getMetricubi() != caratteristica.getM3LSuiS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Liquame Avicolo").getMetricubi() != caratteristica.getM3LAviS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Liquame Altro").getMetricubi() != caratteristica.getM3LAltS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Liquame Biomassa").getMetricubi() != caratteristica.getM3LBioS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Letame Bovino").getMetricubi() != caratteristica.getM3PBovS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Letame Suino").getMetricubi() != caratteristica.getM3PSuiS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Letame Avicolo").getMetricubi() != caratteristica.getM3PAviS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Letame Altro").getMetricubi() != caratteristica.getM3PAltS())
        {
            ritorno = false;
            return ritorno;
        }
        
        if(contenitoreIniziale.getTipologia("Letame Biomassa").getMetricubi() != caratteristica.getM3PBioS())
        {
            ritorno = false;
            return ritorno;
        }
        
        
        
        return ritorno;
        
    }
    
    
    /**
     * prende le caratteristiche_chimiche dal db lato utente e le copia in 
     * listaCaratteristicheLet e listaCaratteristicheLiq perchè queste due liste 
     * sono usate per mostrare i valori nella pagina web dettaglioAziendale. Se però
     * sono state apportate delle modifche agli allevamenti,stoccaggi ed acque allora le caratteristiche
     * chimiche dell'azienda sono cambiate e quindi devono essere salvate nel db lato sistema (per il ripristino)
     * e messe nei campi utente che non sono stati modificati dall'utente stesso
     * @param caratteristica 
     */
    private void popolaLiquameLetame(db.CaratteristicheChmiche caratteristica)
    {
        Refluo lbov = new Refluo("Liquame Bovino");
        Refluo lsui = new Refluo("Liquame Suino");
        Refluo lavi = new Refluo("Liquame Avicolo");
        Refluo lalt = new Refluo("Liquame Altro");
        Refluo lbio = new Refluo("Liquame Biomassa");
    
        Refluo pbov = new Refluo("Letame Bovino");
        Refluo psui = new Refluo("Letame Suino");
        Refluo pavi = new Refluo("Letame Avicolo");
        Refluo palt = new Refluo("Letame Altro");
        Refluo pbio = new Refluo("Letame Biomassa");
         /**
         * --------------------Letame/Palabile
         */
     
        
        pbov.setMetricubi(caratteristica.getM3PBovU());
        pbov.setAzotototale(caratteristica.getTknPBovU());
        pbov.setAzotoammoniacale(caratteristica.getTanPBovU());
        pbov.setFosforototale(caratteristica.getPPBovU());
        pbov.setPotassiototale(caratteristica.getKPBovU());
        pbov.setSolidivolatili(caratteristica.getVsPBovU());
        pbov.setSostanzasecca(caratteristica.getDmPBovU());
        
        psui.setMetricubi(caratteristica.getM3PSuiU());
        psui.setAzotototale(caratteristica.getTknPSuiU());
        psui.setAzotoammoniacale(caratteristica.getTanPSuiU());
        psui.setFosforototale(caratteristica.getPPSuiU());
        psui.setPotassiototale(caratteristica.getKPSuiU());
        psui.setSolidivolatili(caratteristica.getVsPSuiU());
        psui.setSostanzasecca(caratteristica.getDmPSuiU());
        
        pavi.setMetricubi(caratteristica.getM3PAviU());
        pavi.setAzotototale(caratteristica.getTknPAviU());
        pavi.setAzotoammoniacale(caratteristica.getTanPAviU());
        pavi.setFosforototale(caratteristica.getPPAviU());
        pavi.setPotassiototale(caratteristica.getKPAviU());
        pavi.setSolidivolatili(caratteristica.getVsPAviU());
        pavi.setSostanzasecca(caratteristica.getDmPAviU());
        
        palt.setMetricubi(caratteristica.getM3PAltU());
        palt.setAzotototale(caratteristica.getTknPAltU());
        palt.setAzotoammoniacale(caratteristica.getTanPAltU());
        palt.setFosforototale(caratteristica.getPPAltU());
        palt.setPotassiototale(caratteristica.getKPAltU());
        palt.setSolidivolatili(caratteristica.getVsPAltU());
        palt.setSostanzasecca(caratteristica.getDmPAltU());
        
        pbio.setMetricubi(caratteristica.getM3PBioU());
        pbio.setAzotototale(caratteristica.getTknPBioU());
        pbio.setAzotoammoniacale(caratteristica.getTanPBioU());
        pbio.setFosforototale(caratteristica.getPPBioU());
        pbio.setPotassiototale(caratteristica.getKPBioU());
        pbio.setSolidivolatili(caratteristica.getVsPBioU());
        pbio.setSostanzasecca(caratteristica.getDmPBioU());
       
       /**
        * -------------------- Liquame
        */
        
        
        lbov.setMetricubi(caratteristica.getM3LBovU());
        lbov.setAzotototale(caratteristica.getTknLBovU());
        lbov.setAzotoammoniacale(caratteristica.getTanLBovU());
        lbov.setFosforototale(caratteristica.getPLBovU());
        lbov.setPotassiototale(caratteristica.getKLBovU());
        lbov.setSolidivolatili(caratteristica.getVsLBovU());
        lbov.setSostanzasecca(caratteristica.getDmLBovU());
        
        lsui.setMetricubi(caratteristica.getM3LSuiU());
        lsui.setAzotototale(caratteristica.getTknLSuiU());
        lsui.setAzotoammoniacale(caratteristica.getTanLSuiU());
        lsui.setFosforototale(caratteristica.getPLSuiU());
        lsui.setPotassiototale(caratteristica.getKLSuiU());
        lsui.setSolidivolatili(caratteristica.getVsLSuiU());
        lsui.setSostanzasecca(caratteristica.getDmLSuiU());
        
        lavi.setMetricubi(caratteristica.getM3LAviU());
        lavi.setAzotototale(caratteristica.getTknLAviU());
        lavi.setAzotoammoniacale(caratteristica.getTanLAviU());
        lavi.setFosforototale(caratteristica.getPLAviU());
        lavi.setPotassiototale(caratteristica.getKLAviU());
        lavi.setSolidivolatili(caratteristica.getVsLAviU());
        lavi.setSostanzasecca(caratteristica.getDmLAviU());
        
        lalt.setMetricubi(caratteristica.getM3LAltU());
        lalt.setAzotototale(caratteristica.getTknLAltU());
        lalt.setAzotoammoniacale(caratteristica.getTanLAltU());
        lalt.setFosforototale(caratteristica.getPLAltU());
        lalt.setPotassiototale(caratteristica.getKLAltU());
        lalt.setSolidivolatili(caratteristica.getVsLAltU());
        lalt.setSostanzasecca(caratteristica.getDmLAltU());
        
        lbio.setMetricubi(caratteristica.getM3LBioU());
        lbio.setAzotototale(caratteristica.getTknLBioU());
        lbio.setAzotoammoniacale(caratteristica.getTanLBioU());
        lbio.setFosforototale(caratteristica.getPLBioU());
        lbio.setPotassiototale(caratteristica.getKLBioU());
        lbio.setSolidivolatili(caratteristica.getVsLBioU());
        lbio.setSostanzasecca(caratteristica.getDmLBioU());
        
        this.listaCaratteristicheLet.clear();
        this.listaCaratteristicheLiq.clear();
        
        this.listaCaratteristicheLiq.add(lbov);
        this.listaCaratteristicheLiq.add(lsui);
        this.listaCaratteristicheLiq.add(lavi);
        this.listaCaratteristicheLiq.add(lalt);
        this.listaCaratteristicheLiq.add(lbio);
        
        this.listaCaratteristicheLet.add(pbov);
        this.listaCaratteristicheLet.add(psui);
        this.listaCaratteristicheLet.add(pavi);
        this.listaCaratteristicheLet.add(palt);
        this.listaCaratteristicheLet.add(pbio);
      
        ContenitoreReflui cont = new ContenitoreReflui();
        cont.setTipologia("Liquame Bovino", lbov);
        cont.setTipologia("Liquame Suino", lsui);
        cont.setTipologia("Liquame Avicolo", lavi);
        cont.setTipologia("Liquame Altro", lalt);
        cont.setTipologia("Liquame Biomassa", lbio);

        cont.setTipologia("Letame Bovino", pbov);
        cont.setTipologia("Letame Suino", psui);
        cont.setTipologia("Letame Avicolo", pavi);
        cont.setTipologia("Letame Altro", palt);
        cont.setTipologia("Letame Biomassa", pbio);

          
        
         listaCaratteristiche.add(cont.totale("Letame"));
            listaCaratteristiche.add(cont.totale("Liquame"));
            listaCaratteristiche.add(cont.totale("Totale"));
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
         if(listaCaratteristiche1.isEmpty())
             return;
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
                           caratteristichechimiche.setM3PBovU(getValore());
                       } else {
                           caratteristichechimiche.setM3PBovU(caratteristichechimiche.getM3PBovS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3PBovS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBovU(caratteristichechimiche.getTknPBovS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknPBovS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBovU(caratteristichechimiche.getTanPBovS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanPBovS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBovU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBovU(caratteristichechimiche.getDmPBovS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmPBovS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBovU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBovU(caratteristichechimiche.getVsPBovS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsPBovS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBovU(getValore());
                       } else {
                           caratteristichechimiche.setKPBovU(caratteristichechimiche.getKPBovS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKPBovS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBovU(getValore());
                       } else {
                           caratteristichechimiche.setPPBovU(caratteristichechimiche.getPPBovS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPPBovS());
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
                           caratteristichechimiche.setM3PBioU(getValore());
                       } else {
                           caratteristichechimiche.setM3PBioU(caratteristichechimiche.getM3PBioS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3PBioS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBioU(caratteristichechimiche.getTknPBioS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknPBioS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBioU(caratteristichechimiche.getTanPBioS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanPBioS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBioU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBioU(caratteristichechimiche.getDmPBioS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmPBioS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBioU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBioU(caratteristichechimiche.getVsPBioS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsPBioS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBioU(getValore());
                       } else {
                           caratteristichechimiche.setKPBioU(caratteristichechimiche.getKPBioS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKPBioS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBioU(getValore());
                       } else {
                           caratteristichechimiche.setPPBioU(caratteristichechimiche.getPPBioS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPPBioS());
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
