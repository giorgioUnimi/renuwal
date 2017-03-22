/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

import ager.trattamenti.ContenitoreAziendale;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
/**
 *
 * @author giorgio
 */
@ManagedBean(name="risultatoConfronto")
@RequestScoped
 
//@Singleton
public class RisultatoConfronto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private ContenitoreReflui contenitoreReflui = null ;//new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
    
    private List<Refluo> listaCaratteristiche = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
            
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private DettaglioCuaa detto;
    
    private db.AlternativeS alternativa;
    
    private boolean testScenario = false;
    
    private String tipoLiquame = "";
    private String tipoLetame = "";
    /**
     *
     */
   
    public RisultatoConfronto()
    {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detto = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() +"  cuaa "+ detto.getCuaa() +  " detto.getScenario() " + detto.getScenario() + " detto.getIdscenario() "  + detto.getIdscenario() + " alternativa " + detto.getAlternativaN()+" detto anno " + detto.getAnno());
                
        if(detto.getIdscenario() == null || detto.getIdscenario() == 0 )
            return;
        
       if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal2");
             entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
         
       /**
         * mi serve per cancellare la cache prodotto dalla precedente query 
         */
        entityManager.getEntityManagerFactory().getCache().evictAll();
       
        Query q = entityManager.createNamedQuery("RisultatoConfronto.findByIdScenario").setParameter("idScenario",(long)detto.getScenario());
        
        Refluo refluo = null;
        
        if(!q.getResultList().isEmpty())
        {
           popolaContenitore((db.RisultatoConfronto)q.getResultList().get(0));
           
            for (String s : contenitoreReflui.getTipologie()) {
                if (s.contains("Liquame") || s.contains("frazione")) {
                    listaCaratteristicheLiq.add(contenitoreReflui.getTipologia(s));
                    //  System.out.println("aggiungo liquame");
                }

                if (s.contains("Letame")) {
                    listaCaratteristicheLet.add(contenitoreReflui.getTipologia(s));
                    //  System.out.println("aggiungo letame");
                }
            }

            listaCaratteristiche.add(contenitoreReflui.totale("Letame"));
            listaCaratteristiche.add(contenitoreReflui.totale("Liquame"));
            listaCaratteristiche.add(contenitoreReflui.totale("Totale"));
           
        }
        
        
         Connessione.getInstance().chiudi();
        
    }
    
    /**
     * carica le etrichette corrette nel contenitore reflui
     * @param alternativa 
     */
    private void associaEtichette(db.AlternativeS alt)
    {
                if(alt.getId() == 0 || alt.getId() == 1 || alt.getId() == 4 || alt.getId() == 5 || alt.getId() == 12 || alt.getId() == 13 || alt.getId() == 15)
                {
                    this.contenitoreReflui = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
                }
                
                
                if(alt.getId() == 2 || alt.getId() == 8 || alt.getId() == 11 || alt.getId() == 14)
                {
                   this.contenitoreReflui = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDigestato());
 
                }
                
                
               if(alt.getId() == 3 || alt.getId() == 6 || alt.getId() == 7 || alt.getId() == 9 || alt.getId() == 10 || alt.getId() == 16 )
               {
                  this.contenitoreReflui = new ContenitoreReflui(TipiReflui.getInstance().getTipologieChiarificato());
  
               }
                
    }
    
    /**
     * popolo il contenitoreReflui privato di questa classe con il contenuto della query che interroga
     * il db nella tabella RisultatoConfronto che contiene l'output del solutore per uno 
     * specifico scenario
     * @param risultatoConfronto 
     */
    private void popolaContenitore(db.RisultatoConfronto risultatoConfronto)
    {
        
        this.setAlternativa(risultatoConfronto.getIdAlternativa());
        db.AlternativeS alt = risultatoConfronto.getIdAlternativa();   
        
        //imposto le stringhe che descrivono il tipo di refluo nella pagina piano_distribuzione.xhtml
        this.setTipoLetame(alt.getIdTipoFormaRefluoPalabile().getDescrizione());
        this.setTipoLiquame(alt.getIdTipoFormaRefluoLiquido().getDescrizione());
        
        
        this.associaEtichette(alternativa);
        Refluo refluo = null;
        if(alt.getId() == 0 || alt.getId() == 1 || alt.getId() == 4 || alt.getId() == 5 || alt.getId() == 12 || alt.getId() == 13 || alt.getId() == 15)
        {    
         //refluo da allevamento   
        
        /*********************************************************
         * 
         *                      LIQUAME
         * 
         * *************************************************************/
        
        refluo = new Refluo("Liquame Bovino");
        refluo.setMetricubi(risultatoConfronto.getM3LBov() + risultatoConfronto.getM3LAlt() + risultatoConfronto.getM3LBio());
        refluo.setAzotototale(risultatoConfronto.getTknLBov() + risultatoConfronto.getTknLAlt() + risultatoConfronto.getTknLBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLBov() + risultatoConfronto.getTanLAlt() + risultatoConfronto.getTanLBio());
        refluo.setFosforototale(risultatoConfronto.getKLBov() + risultatoConfronto.getKLAlt() + risultatoConfronto.getKLBio());
        refluo.setPotassiototale(risultatoConfronto.getPLBov() + risultatoConfronto.getPLAlt() + risultatoConfronto.getPLBio() );
        refluo.setSolidivolatili(risultatoConfronto.getVsLBov() + risultatoConfronto.getVsLAlt() + risultatoConfronto.getVsLBio() );
        refluo.setSostanzasecca(risultatoConfronto.getDmLBov() + risultatoConfronto.getDmLAlt() + risultatoConfronto.getDmLBio());
        
        this.contenitoreReflui.setTipologia("Liquame Bovino", refluo);
        
        refluo = new Refluo("Liquame Suino");
        refluo.setMetricubi(risultatoConfronto.getM3LSui());
        refluo.setAzotototale(risultatoConfronto.getTknLSui());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLSui());
        refluo.setFosforototale(risultatoConfronto.getKLSui());
        refluo.setPotassiototale(risultatoConfronto.getPLSui());
        refluo.setSolidivolatili(risultatoConfronto.getVsLSui());
        refluo.setSostanzasecca(risultatoConfronto.getDmLSui());
        
        this.contenitoreReflui.setTipologia("Liquame Suino", refluo);
        
        refluo = new Refluo("Liquame Avicolo");
        refluo.setMetricubi(risultatoConfronto.getM3LAvi());
        refluo.setAzotototale(risultatoConfronto.getTknLAvi());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLAvi());
        refluo.setFosforototale(risultatoConfronto.getKLAvi());
        refluo.setPotassiototale(risultatoConfronto.getPLAvi());
        refluo.setSolidivolatili(risultatoConfronto.getVsLAvi());
        refluo.setSostanzasecca(risultatoConfronto.getDmLAvi());
        
        this.contenitoreReflui.setTipologia("Liquame Avicolo", refluo);
        
        
        /*refluo = new Refluo("Liquame Altro");
        refluo.setMetricubi(risultatoConfronto.getM3LAlt());
        refluo.setAzotototale(risultatoConfronto.getTknLAlt());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLAlt());
        refluo.setFosforototale(risultatoConfronto.getKLAlt());
        refluo.setPotassiototale(risultatoConfronto.getPLAlt());
        refluo.setSolidivolatili(risultatoConfronto.getVsLAlt());
        refluo.setSostanzasecca(risultatoConfronto.getDmLAlt());
        
        this.contenitoreReflui.setTipologia("Liquame Altro", refluo);
        
        
        refluo = new Refluo("Liquame Biomassa");
        refluo.setMetricubi(risultatoConfronto.getM3LBio());
        refluo.setAzotototale(risultatoConfronto.getTknLBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLBio());
        refluo.setFosforototale(risultatoConfronto.getKLBio());
        refluo.setPotassiototale(risultatoConfronto.getPLBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsLBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmLBio());
        
        this.contenitoreReflui.setTipologia("Liquame Biomassa", refluo);*/
        
        /****************************************************************
         * 
         *                      PALABILE
         * 
         * ****************************************************************/
        
        refluo = new Refluo("Letame Bovino");
        refluo.setMetricubi(risultatoConfronto.getM3PBov() + risultatoConfronto.getM3PAlt() + risultatoConfronto.getM3PBio());
        refluo.setAzotototale(risultatoConfronto.getTknPBov() + risultatoConfronto.getTknPAlt() + risultatoConfronto.getTknPBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPBov() + risultatoConfronto.getTanPAlt() + risultatoConfronto.getTanPBio());
        refluo.setFosforototale(risultatoConfronto.getKPBov() + risultatoConfronto.getKPAlt() + risultatoConfronto.getKPBio());
        refluo.setPotassiototale(risultatoConfronto.getPPBov() + risultatoConfronto.getPPAlt() + risultatoConfronto.getPPBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsPBov()+ risultatoConfronto.getVsPAlt() + risultatoConfronto.getVsPBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmPBov() + risultatoConfronto.getDmPAlt() + risultatoConfronto.getDmPBio());
        
        this.contenitoreReflui.setTipologia("Letame Bovino", refluo);
        
        refluo = new Refluo("Letame Suino");
        refluo.setMetricubi(risultatoConfronto.getM3PSui());
        refluo.setAzotototale(risultatoConfronto.getTknPSui());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPSui());
        refluo.setFosforototale(risultatoConfronto.getKPSui());
        refluo.setPotassiototale(risultatoConfronto.getPPSui());
        refluo.setSolidivolatili(risultatoConfronto.getVsPSui());
        refluo.setSostanzasecca(risultatoConfronto.getDmPSui());
        
        this.contenitoreReflui.setTipologia("Letame Suino", refluo);
        
        refluo = new Refluo("Letame Avicolo");
        refluo.setMetricubi(risultatoConfronto.getM3PAvi());
        refluo.setAzotototale(risultatoConfronto.getTknPAvi());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPAvi());
        refluo.setFosforototale(risultatoConfronto.getKPAvi());
        refluo.setPotassiototale(risultatoConfronto.getPPAvi());
        refluo.setSolidivolatili(risultatoConfronto.getVsPAvi());
        refluo.setSostanzasecca(risultatoConfronto.getDmPAvi());
        
        this.contenitoreReflui.setTipologia("Letame Avicolo", refluo);
        
        
        /*refluo = new Refluo("Letame Altro");
        refluo.setMetricubi(risultatoConfronto.getM3PAlt());
        refluo.setAzotototale(risultatoConfronto.getTknPAlt());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPAlt());
        refluo.setFosforototale(risultatoConfronto.getKPAlt());
        refluo.setPotassiototale(risultatoConfronto.getPPAlt());
        refluo.setSolidivolatili(risultatoConfronto.getVsPAlt());
        refluo.setSostanzasecca(risultatoConfronto.getDmPAlt());
        
        this.contenitoreReflui.setTipologia("Letame Altro", refluo);
        
        
        refluo = new Refluo("Letame Biomassa");
        refluo.setMetricubi(risultatoConfronto.getM3PBio());
        refluo.setAzotototale(risultatoConfronto.getTknPBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPBio());
        refluo.setFosforototale(risultatoConfronto.getKPBio());
        refluo.setPotassiototale(risultatoConfronto.getPPBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsPBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmPBio());
        
        this.contenitoreReflui.setTipologia("Letame Biomassa", refluo);*/
        }
        
        
       if(alt.getId() == 2 || alt.getId() == 8 || alt.getId() == 11 || alt.getId() == 14)
       {
        //refluo da digestato non chiarificato  
        //tipi disponibili
        //"Liquame Digestato da Bovino" ,"Liquame Digestato da Suino" ,"Liquame Digestato da Avicolo" ,"Liquame Digestato da Biomasse" ,"Letame Digestato da Bovino" ,"Letame Digestato da Suino" ,"Letame Digestato da Avicolo" ,"Letame Digestato da Biomasse"   
      
        /*********************************************************
         * 
         *                      LIQUAME
         * 
         * *************************************************************/
        
        refluo = new Refluo("Liquame Digestato da Bovino");
        refluo.setMetricubi(risultatoConfronto.getM3LBov() + risultatoConfronto.getM3LAlt() );
        refluo.setAzotototale(risultatoConfronto.getTknLBov() + risultatoConfronto.getTknLAlt() );
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLBov() + risultatoConfronto.getTanLAlt() );
        refluo.setFosforototale(risultatoConfronto.getKLBov() + risultatoConfronto.getKLAlt() );
        refluo.setPotassiototale(risultatoConfronto.getPLBov() + risultatoConfronto.getPLAlt()  );
        refluo.setSolidivolatili(risultatoConfronto.getVsLBov() + risultatoConfronto.getVsLAlt()  );
        refluo.setSostanzasecca(risultatoConfronto.getDmLBov() + risultatoConfronto.getDmLAlt() );
        
        this.contenitoreReflui.setTipologia("Liquame Digestato da Bovino", refluo);
        
        refluo = new Refluo("Liquame Digestato da Suino");
        refluo.setMetricubi(risultatoConfronto.getM3LSui());
        refluo.setAzotototale(risultatoConfronto.getTknLSui());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLSui());
        refluo.setFosforototale(risultatoConfronto.getKLSui());
        refluo.setPotassiototale(risultatoConfronto.getPLSui());
        refluo.setSolidivolatili(risultatoConfronto.getVsLSui());
        refluo.setSostanzasecca(risultatoConfronto.getDmLSui());
        
        this.contenitoreReflui.setTipologia("Liquame Digestato da Suino", refluo);
        
        refluo = new Refluo("Liquame Digestato da Avicolo");
        refluo.setMetricubi(risultatoConfronto.getM3LAvi());
        refluo.setAzotototale(risultatoConfronto.getTknLAvi());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLAvi());
        refluo.setFosforototale(risultatoConfronto.getKLAvi());
        refluo.setPotassiototale(risultatoConfronto.getPLAvi());
        refluo.setSolidivolatili(risultatoConfronto.getVsLAvi());
        refluo.setSostanzasecca(risultatoConfronto.getDmLAvi());
        
        this.contenitoreReflui.setTipologia("Liquame Digestato da Avicolo", refluo);
             
        
        refluo = new Refluo("Liquame Digestato da Biomasse");
        refluo.setMetricubi(risultatoConfronto.getM3LBio());
        refluo.setAzotototale(risultatoConfronto.getTknLBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLBio());
        refluo.setFosforototale(risultatoConfronto.getKLBio());
        refluo.setPotassiototale(risultatoConfronto.getPLBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsLBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmLBio());
        
        this.contenitoreReflui.setTipologia("Liquame Digestato da Biomasse", refluo);
        
        /****************************************************************
         * 
         *                      PALABILE
         * 
         * ****************************************************************/
        
        refluo = new Refluo("Letame Digestato da Bovino");
        refluo.setMetricubi(risultatoConfronto.getM3PBov() + risultatoConfronto.getM3PAlt() );
        refluo.setAzotototale(risultatoConfronto.getTknPBov() + risultatoConfronto.getTknPAlt() );
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPBov() + risultatoConfronto.getTanPAlt() );
        refluo.setFosforototale(risultatoConfronto.getKPBov() + risultatoConfronto.getKPAlt() );
        refluo.setPotassiototale(risultatoConfronto.getPPBov() + risultatoConfronto.getPPAlt() );
        refluo.setSolidivolatili(risultatoConfronto.getVsPBov()+ risultatoConfronto.getVsPAlt() );
        refluo.setSostanzasecca(risultatoConfronto.getDmPBov() + risultatoConfronto.getDmPAlt() );
        
        this.contenitoreReflui.setTipologia("Letame Digestato da Bovino", refluo);
        
        refluo = new Refluo("Letame Digestato da Suino");
        refluo.setMetricubi(risultatoConfronto.getM3PSui());
        refluo.setAzotototale(risultatoConfronto.getTknPSui());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPSui());
        refluo.setFosforototale(risultatoConfronto.getKPSui());
        refluo.setPotassiototale(risultatoConfronto.getPPSui());
        refluo.setSolidivolatili(risultatoConfronto.getVsPSui());
        refluo.setSostanzasecca(risultatoConfronto.getDmPSui());
        
        this.contenitoreReflui.setTipologia("Letame Digestato da Suino", refluo);
        
        refluo = new Refluo("Letame Digestato da Avicolo");
        refluo.setMetricubi(risultatoConfronto.getM3PAvi());
        refluo.setAzotototale(risultatoConfronto.getTknPAvi());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPAvi());
        refluo.setFosforototale(risultatoConfronto.getKPAvi());
        refluo.setPotassiototale(risultatoConfronto.getPPAvi());
        refluo.setSolidivolatili(risultatoConfronto.getVsPAvi());
        refluo.setSostanzasecca(risultatoConfronto.getDmPAvi());
        
        this.contenitoreReflui.setTipologia("Letame Digestato da Avicolo", refluo);
        
        
        refluo = new Refluo("Letame Digestato da Biomasse");
        refluo.setMetricubi(risultatoConfronto.getM3PBio());
        refluo.setAzotototale(risultatoConfronto.getTknPBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPBio());
        refluo.setFosforototale(risultatoConfronto.getKPBio());
        refluo.setPotassiototale(risultatoConfronto.getPPBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsPBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmPBio());
        
        this.contenitoreReflui.setTipologia("Letame Digestato da Biomasse", refluo);
       
       
       }
        
        if(alt.getId() == 3 || alt.getId() == 6 || alt.getId() == 7 || alt.getId() == 9 || alt.getId() == 10 || alt.getId() == 16 )
        { 
         //refluo da digestato  chiarificato 
         //tipi disponibili
         //"Digestato frazione chiarificata","Letame"   
            
         /********************************************************
          * 
          *         DIGESTATO FRAZIONE CHIARIFICATA
          * 
          * ******************************************************/
        refluo = new Refluo("Digestato frazione chiarificata");
        refluo.setMetricubi(risultatoConfronto.getM3LBov() +risultatoConfronto.getM3LSui() +risultatoConfronto.getM3LAvi() + risultatoConfronto.getM3LAlt() + risultatoConfronto.getM3LBio());
        refluo.setAzotototale(risultatoConfronto.getTknLBov() +risultatoConfronto.getTknLSui() +risultatoConfronto.getTknLAvi() + risultatoConfronto.getTknLAlt() + risultatoConfronto.getTknLBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanLBov() +risultatoConfronto.getTanLSui() +risultatoConfronto.getTanLAvi() + risultatoConfronto.getTanLAlt() + risultatoConfronto.getTanLBio());
        refluo.setFosforototale(risultatoConfronto.getKLBov() +risultatoConfronto.getKLSui() +risultatoConfronto.getKLAvi() + risultatoConfronto.getKLAlt() + risultatoConfronto.getKLBio());
        refluo.setPotassiototale(risultatoConfronto.getPLBov() +risultatoConfronto.getPLSui() +risultatoConfronto.getPLAvi() + risultatoConfronto.getPLAlt() + risultatoConfronto.getPLBio() );
        refluo.setSolidivolatili(risultatoConfronto.getVsLBov() +risultatoConfronto.getVsLSui() +risultatoConfronto.getVsLAvi() + risultatoConfronto.getVsLAlt() + risultatoConfronto.getVsLBio() );
        refluo.setSostanzasecca(risultatoConfronto.getDmLBov() +risultatoConfronto.getDmLSui() +risultatoConfronto.getDmLAvi() + risultatoConfronto.getDmLAlt() + risultatoConfronto.getDmLBio());
        
        this.contenitoreReflui.setTipologia("Digestato frazione chiarificata", refluo);   
            
            
          /********************************************************
          * 
          *         LETAME
          * 
          * ******************************************************/   
         refluo = new Refluo("Letame");
        refluo.setMetricubi(risultatoConfronto.getM3PBov() +risultatoConfronto.getM3PSui() +risultatoConfronto.getM3PAvi() + risultatoConfronto.getM3PAlt() + risultatoConfronto.getM3PBio());
        refluo.setAzotototale(risultatoConfronto.getTknPBov() +risultatoConfronto.getTknPSui() +risultatoConfronto.getTknPAvi() + risultatoConfronto.getTknPAlt() + risultatoConfronto.getTknPBio());
        refluo.setAzotoammoniacale(risultatoConfronto.getTanPBov() +risultatoConfronto.getTanPSui() +risultatoConfronto.getTanPAvi() + risultatoConfronto.getTanPAlt() + risultatoConfronto.getTanPBio());
        refluo.setFosforototale(risultatoConfronto.getKPBov() +risultatoConfronto.getKPSui() +risultatoConfronto.getKPAvi() + risultatoConfronto.getKPAlt() + risultatoConfronto.getKPBio());
        refluo.setPotassiototale(risultatoConfronto.getPPBov() +risultatoConfronto.getPPSui() +risultatoConfronto.getPPAvi() + risultatoConfronto.getPPAlt() + risultatoConfronto.getPPBio());
        refluo.setSolidivolatili(risultatoConfronto.getVsPBov()+risultatoConfronto.getVsPSui()+risultatoConfronto.getVsPAvi()+ risultatoConfronto.getVsPAlt() + risultatoConfronto.getVsPBio());
        refluo.setSostanzasecca(risultatoConfronto.getDmPBov() +risultatoConfronto.getDmPSui() +risultatoConfronto.getDmPAvi() + risultatoConfronto.getDmPAlt() + risultatoConfronto.getDmPBio());
        
        this.contenitoreReflui.setTipologia("Letame", refluo);     
        }
        
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
     * @return the listaCaratteristiche
     */
    public List<Refluo> getListaCaratteristiche() {
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ------ ");
        return listaCaratteristiche;
    }

    /**
     * @param listaCaratteristiche the listaCaratteristiche to set
     */
    public void setListaCaratteristiche(List<Refluo> listaCaratteristiche) {
        this.listaCaratteristiche = listaCaratteristiche;
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
     *  usato nella pagina piano_concimazione 
     * per verificare se devo mostrare o meno l'avviso del confronto ovvero
     * se arrivi in questa pagina e non ha ancora inserito lo scenario ti mostro l'avviso di selezionare
     * lo scenario altrimenti se lo scenario l'hai gia selezionato e non c'è legato un confronto allora
     * ti mostro l'avviso che ti allerta a fare i lconfronto prima
     * @return the testScenario
     */
    public boolean isTestScenario() {     
   
        if(detto.getScenario() == 0) {
            return testScenario = false;
        }
        else {
            return testScenario = true;
        } 
        
    }

    /**
     * @param testScenario the testScenario to set
     */
    public void setTestScenario(boolean testScenario) {
        this.testScenario = testScenario;
    }

    /**
     * @return the tipoLiquame
     */
    public String getTipoLiquame() {
        return tipoLiquame;
    }

    /**
     * @param tipoLiquame the tipoLiquame to set
     */
    public void setTipoLiquame(String tipoLiquame) {
        this.tipoLiquame = tipoLiquame;
    }

    /**
     * @return the tipoLetame
     */
    public String getTipoLetame() {
        return tipoLetame;
    }

    /**
     * @param tipoLetame the tipoLetame to set
     */
    public void setTipoLetame(String tipoLetame) {
        this.tipoLetame = tipoLetame;
    }

    
    
}
