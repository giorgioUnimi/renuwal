/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ajax;

import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="aggiornaDettaglio")
@ViewScoped
public class AggiornaDettaglio implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int operazione;
    private static double valore  = -1;
    private String tipologia;
    private int tipocaratteristica;
    
    HashMap<Integer,String> mapCaratterstiche = new HashMap<>();
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    private Long idscenario;
    
    public AggiornaDettaglio(){
            
        mapCaratterstiche.clear();
        mapCaratterstiche.put(1, "Volume");
         mapCaratterstiche.put(2, "tkn");
          mapCaratterstiche.put(3, "tan");
           mapCaratterstiche.put(4, "dm");
            mapCaratterstiche.put(5, "vs");
             mapCaratterstiche.put(6, "k");
              mapCaratterstiche.put(7, "p");
            
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " costruttore " );

    }
    
    
    
    public void aggiorna()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario "+this.idscenario+" operazione " + this.operazione + " valore " + AggiornaDettaglio.valore + " tipo " + this.tipologia + " caratterstica " + this.mapCaratterstiche.get(new Integer(this.tipocaratteristica)));
        
        //if(AggiornaDettaglio.valore != -1) {
            this.modificaRipristina(AggiornaDettaglio.valore, this.tipologia, this.tipocaratteristica, this.operazione, this.idscenario.intValue());
       // }
   
        //AggiornaDettaglio.valore  = -1;
        
    }
    
    public void aggiornaS()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " idscenario "+this.idscenario + " valore " + AggiornaDettaglio.valore );
    }
    
    
    /**
     * @return the operazione
     */
    public int getOperazione() {
        return operazione;
    }

    /**
     * @param operazione the operazione to set
     */
    public void setOperazione(int operazione) {
        
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " set operazione " );

        
        this.operazione = operazione;
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
     * @return the tipocaratteristica
     */
    public int getTipocaratteristica() {
        return tipocaratteristica;
    }

    /**
     * @param tipocaratteristica the tipocaratteristica to set
     */
    public void setTipocaratteristica(int tipocaratteristica) {
        this.tipocaratteristica = tipocaratteristica;
    }
    
    
    private void modificaRipristina(Double valore,String tipologia,int posizione,int azione,int scenario)   
   {
       if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal2");
             entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
        
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", scenario);
       db.ScenarioI sce = (db.ScenarioI)q.getResultList().get(0);
       db.CaratteristicheChmiche caratteristichechimiche = sce.getCaratteristicheChmiche();
       
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() + " valore passato  " +valore + " azione " + azione + " posizione " + posizione + " tipologia " +tipologia);
      
       /**
        * se azione è 0 modifico la caratteristica utente usando valore
        * se azione è 1 rirpistino il valore della caratteristica usando il 
        * valore della caratteristica di sistema
        */
       switch (tipologia) {
           case "Liquame Bovino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3LBovU(valore);
                           caratteristichechimiche.setM3LBovM(true);
                           
                           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " modifica valore volume ");
                                                    
                       } else {
                           caratteristichechimiche.setM3LBovU(caratteristichechimiche.getM3LBovS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBovS());
                           caratteristichechimiche.setM3LBovM(false);
                           
                           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ripristina valore volume ");

                           
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknLBovU(valore);
                           caratteristichechimiche.setTknLBovM(true);
                       } else {
                           caratteristichechimiche.setTknLBovU(caratteristichechimiche.getTknLBovS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBovS());
                           caratteristichechimiche.setTknLBovM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanLBovU(valore);
                           caratteristichechimiche.setTanLBovM(true);
                       } else {
                           caratteristichechimiche.setTanLBovU(caratteristichechimiche.getTanLBovS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBovS());
                           caratteristichechimiche.setTanLBovM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmLBovU(valore);
                           caratteristichechimiche.setDmLBovM(true);
                       } else {
                           caratteristichechimiche.setDmLBovU(caratteristichechimiche.getDmLBovS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBovS())
                            caratteristichechimiche.setDmLBovM(false);
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsLBovU(valore);
                           caratteristichechimiche.setVsLBovM(true);
                       } else {
                           caratteristichechimiche.setVsLBovU(caratteristichechimiche.getVsLBovS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBovS());
                            caratteristichechimiche.setVsLBovM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKLBovU(valore);
                           caratteristichechimiche.setKLBovM(true);
                       } else {
                           caratteristichechimiche.setKLBovU(caratteristichechimiche.getKLBovS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBovS());
                            caratteristichechimiche.setKLBovM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPLBovU(valore);
                           caratteristichechimiche.setPLBovM(true);
                       } else {
                           caratteristichechimiche.setPLBovU(caratteristichechimiche.getPLBovS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBovS());
                           caratteristichechimiche.setPLBovM(false);
                       }
                       break;
               }
               break;
           case "Liquame Suino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3LSuiU(valore);
                           caratteristichechimiche.setM3LSuiM(true);
                       } else {
                           caratteristichechimiche.setM3LSuiU(caratteristichechimiche.getM3LSuiS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LSuiS());
                            caratteristichechimiche.setM3LSuiM(false);
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknLSuiU(valore);
                            caratteristichechimiche.setTknLSuiM(true);
                       } else {
                           caratteristichechimiche.setTknLSuiU(caratteristichechimiche.getTknLSuiS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLSuiS());
                            caratteristichechimiche.setTknLSuiM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanLSuiU(valore);
                           caratteristichechimiche.setTanLSuiM(true);
                       } else {
                           caratteristichechimiche.setTanLSuiU(caratteristichechimiche.getTanLSuiS());
                            //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLSuiS());
                           caratteristichechimiche.setTanLSuiM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmLSuiU(valore);
                           caratteristichechimiche.setDmLSuiM(true);
                       } else {
                           caratteristichechimiche.setDmLSuiU(caratteristichechimiche.getDmLSuiS());
                            caratteristichechimiche.setDmLSuiM(false);
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLSuiS());
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsLSuiU(valore);
                            caratteristichechimiche.setVsLSuiM(true);
                       } else {
                           caratteristichechimiche.setVsLSuiU(caratteristichechimiche.getVsLSuiS());
                           // getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLSuiS());
                             caratteristichechimiche.setVsLSuiM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKLSuiU(valore);
                           caratteristichechimiche.setKLSuiM(true);
                       } else {
                           caratteristichechimiche.setKLSuiU(caratteristichechimiche.getKLSuiS());
                          // getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLSuiS());
                           caratteristichechimiche.setKLSuiM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPLSuiU(valore);
                           caratteristichechimiche.setPLSuiM(true);
                       } else {
                           caratteristichechimiche.setPLSuiU(caratteristichechimiche.getPLSuiS());
                          // getDataItemLiq().setFosforototale(caratteristichechimiche.getPLSuiS());
                           caratteristichechimiche.setPLSuiM(false);
                       }
                       break;
               }
               break;
           case "Liquame Avicolo":
               // setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3LAviU(valore);
                            caratteristichechimiche.setM3LAviM(true);
                       } else {
                           caratteristichechimiche.setM3LAviU(caratteristichechimiche.getM3LAviS());
                              caratteristichechimiche.setM3LAviM(false);
                          // getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAviS());
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknLAviU(valore);
                            caratteristichechimiche.setTknLAviM(true);
                       } else {
                           caratteristichechimiche.setTknLAviU(caratteristichechimiche.getTknLAviS());
                           caratteristichechimiche.setTknLAviM(false);
                           // getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAviS());
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanLAviU(valore);
                           caratteristichechimiche.setTanLAviM(true);
                       } else {
                           caratteristichechimiche.setTanLAviU(caratteristichechimiche.getTanLAviS());
                           caratteristichechimiche.setTanLAviM(false);
                          // getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAviS());
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmLAviU(valore);
                           caratteristichechimiche.setDmLAviM(true);
                       } else {
                           caratteristichechimiche.setDmLAviU(caratteristichechimiche.getDmLAviS());
                            caratteristichechimiche.setDmLAviM(false);
                           // getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAviS());
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsLAviU(valore);
                            caratteristichechimiche.setVsLAviM(true);
                       } else {
                           caratteristichechimiche.setVsLAviU(caratteristichechimiche.getVsLAviS());
                             caratteristichechimiche.setVsLAviM(false);
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAviS());
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKLAviU(valore);
                            caratteristichechimiche.setKLAviM(true);
                       } else {
                           caratteristichechimiche.setKLAviU(caratteristichechimiche.getKLAviS());
                            caratteristichechimiche.setKLAviM(false);
                            //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAviS());
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPLAviU(valore);
                            caratteristichechimiche.setPLAviM(true);
                       } else {
                           caratteristichechimiche.setPLAviU(caratteristichechimiche.getPLAviS());
                           caratteristichechimiche.setPLAviM(false);
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAviS());
                       }
                       break;
               }
               break;
           case "Liquame Altro":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3LAltU(valore);
                           caratteristichechimiche.setM3LAltM(true);
                       } else {
                           caratteristichechimiche.setM3LAltU(caratteristichechimiche.getM3LAltS());
                            caratteristichechimiche.setM3LAltM(false);
                            //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAltS());
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknLAltU(valore);
                            caratteristichechimiche.setTknLAltM(true);
                       } else {
                           caratteristichechimiche.setTknLAltU(caratteristichechimiche.getTknLAltS());
                             caratteristichechimiche.setTknLAltM(false);
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAltS());
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanLAltU(valore);
                            caratteristichechimiche.setTanLAltM(true);
                       } else {
                           caratteristichechimiche.setTanLAltU(caratteristichechimiche.getTanLAltS());
                            caratteristichechimiche.setTanLAltM(false);
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAltS());
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmLAltU(valore);
                            caratteristichechimiche.setDmLAltM(true);
                       } else {
                           caratteristichechimiche.setDmLAltU(caratteristichechimiche.getDmLAltS());
                            caratteristichechimiche.setDmLAltM(false);
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAltS());
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsLAltU(valore);
                           caratteristichechimiche.setVsLAltM(true);
                       } else {
                           caratteristichechimiche.setVsLAltU(caratteristichechimiche.getVsLAltS());
                           caratteristichechimiche.setVsLAltM(false);
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAltS());
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKLAltU(valore);
                           caratteristichechimiche.setKLAltM(true);
                       } else {
                           caratteristichechimiche.setKLAltU(caratteristichechimiche.getKLAltS());
                            caratteristichechimiche.setKLAltM(false);
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAltS());
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPLAltU(valore);
                           caratteristichechimiche.setPLAltM(true);
                       } else {
                           caratteristichechimiche.setPLAltU(caratteristichechimiche.getPLAltS());
                            caratteristichechimiche.setPLAltM(false);
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAltS());
                       }
                       break;
               }
               break;
           case "Liquame Biomassa":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3LBioU(valore);
                            caratteristichechimiche.setM3LBioM(true);
                       } else {
                           caratteristichechimiche.setM3LBioU(caratteristichechimiche.getM3LBioS());
                            caratteristichechimiche.setM3LBioM(false);
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBioS());
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknLBioU(valore);
                           caratteristichechimiche.setTknLBioM(true);
                       } else {
                           caratteristichechimiche.setTknLBioU(caratteristichechimiche.getTknLBioS());
                           caratteristichechimiche.setTknLBioM(false);
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBioS());
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanLBioU(valore);
                            caratteristichechimiche.setTanLBioM(true);
                       } else {
                           caratteristichechimiche.setTanLBioU(caratteristichechimiche.getTanLBioS());
                            caratteristichechimiche.setTanLBioM(false);
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBioS());
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmLBioU(valore);
                            caratteristichechimiche.setDmLBioM(true);
                       } else {
                           caratteristichechimiche.setDmLBioU(caratteristichechimiche.getDmLBioS());
                            caratteristichechimiche.setDmLBioM(false);
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBioS());
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsLBioU(valore);
                            caratteristichechimiche.setVsLBioM(true);
                       } else {
                           caratteristichechimiche.setVsLBioU(caratteristichechimiche.getVsLBioS());
                            caratteristichechimiche.setVsLBioM(false);
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBioS());
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKLBioU(valore);
                            caratteristichechimiche.setKLBioM(true);
                       } else {
                           caratteristichechimiche.setKLBioU(caratteristichechimiche.getKLBioS());
                            caratteristichechimiche.setKLBioM(false);
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBioS());
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPLBioU(valore);
                            caratteristichechimiche.setPLBioM(true);
                       } else {
                           caratteristichechimiche.setPLBioU(caratteristichechimiche.getPLBioS());
                             caratteristichechimiche.setPLBioM(false);
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBioS());
                       }
                       break;
               }
               break;
           case "Letame Bovino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           
                           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " letame bovino modifica " + valore);

                           
                           caratteristichechimiche.setM3PBovU(valore);
                            caratteristichechimiche.setM3PBovM(true);
                       } else {
                           
                           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " letame bovino ripristina " + caratteristichechimiche.getM3PBovS());
                           
                           caratteristichechimiche.setM3PBovU(caratteristichechimiche.getM3PBovS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PBovS());
                             caratteristichechimiche.setM3PBovM(false);
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknPBovU(valore);
                            caratteristichechimiche.setTknPBovM(true);
                       } else {
                           caratteristichechimiche.setTknPBovU(caratteristichechimiche.getTknPBovS());
                           //dataItemLet.setAzotototale(caratteristichechimiche.getTknPBovS());
                            caratteristichechimiche.setTknPBovM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanPBovU(valore);
                           caratteristichechimiche.setTanPBovM(true);
                       } else {
                           caratteristichechimiche.setTanPBovU(caratteristichechimiche.getTanPBovS());
                           //dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBovS());
                            caratteristichechimiche.setTanPBovM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmPBovU(valore);
                            caratteristichechimiche.setDmPBovM(true);
                       } else {
                           caratteristichechimiche.setDmPBovU(caratteristichechimiche.getDmPBovS());
                           //dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBovS());
                            caratteristichechimiche.setDmPBovM(false);
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsPBovU(valore);
                           caratteristichechimiche.setVsPBovM(true);
                       } else {
                           caratteristichechimiche.setVsPBovU(caratteristichechimiche.getVsPBovS());
                           //dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBovS());
                             caratteristichechimiche.setVsPBovM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKPBovU(valore);
                           caratteristichechimiche.setKPBovM(true);
                       } else {
                           caratteristichechimiche.setKPBovU(caratteristichechimiche.getKPBovS());
                           //dataItemLet.setPotassiototale(caratteristichechimiche.getKPBovS());
                           caratteristichechimiche.setKPBovM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPPBovU(valore);
                            caratteristichechimiche.setPPBovM(true);
                       } else {
                           caratteristichechimiche.setPPBovU(caratteristichechimiche.getPPBovS());
                           //dataItemLet.setFosforototale(caratteristichechimiche.getPPBovS());
                            caratteristichechimiche.setPPBovM(false);
                       }
                       break;
               }
               break;
           case "Letame Suino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3PSuiU(valore);
                           caratteristichechimiche.setM3PSuiM(true);
                       } else {
                           caratteristichechimiche.setM3PSuiU(caratteristichechimiche.getM3PSuiS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PSuiS());
                           caratteristichechimiche.setM3PSuiM(false);
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknPSuiU(valore);
                           caratteristichechimiche.setTknPSuiM(true);
                       } else {
                           caratteristichechimiche.setTknPSuiU(caratteristichechimiche.getTknPSuiS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPSuiS());
                           caratteristichechimiche.setTknPSuiM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanPSuiU(valore);
                           caratteristichechimiche.setTanPSuiM(true);
                       } else {
                           caratteristichechimiche.setTanPSuiU(caratteristichechimiche.getTanPSuiS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPSuiS());
                           caratteristichechimiche.setTanPSuiM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmPSuiU(valore);
                           caratteristichechimiche.setDmPSuiM(true);
                       } else {
                           caratteristichechimiche.setDmPSuiU(caratteristichechimiche.getDmPSuiS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPSuiS());
                            caratteristichechimiche.setDmPSuiM(false);
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsPSuiU(valore);
                           caratteristichechimiche.setVsPSuiM(true);
                       } else {
                           caratteristichechimiche.setVsPSuiU(caratteristichechimiche.getVsPSuiS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPSuiS());
                            caratteristichechimiche.setVsPSuiM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKPSuiU(valore);
                           caratteristichechimiche.setKPSuiM(true);
                       } else {
                           caratteristichechimiche.setKPSuiU(caratteristichechimiche.getKPSuiS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPSuiS());
                            caratteristichechimiche.setKPSuiM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPPSuiU(valore);
                            caratteristichechimiche.setPPSuiM(true);
                       } else {
                           caratteristichechimiche.setPPSuiU(caratteristichechimiche.getPPSuiS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPSuiS());
                             caratteristichechimiche.setPPSuiM(false);
                       }
                       break;
               }
               break;
           case "Letame Avicolo":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3PAviU(valore);
                           caratteristichechimiche.setM3PAviM(true);
                       } else {
                           caratteristichechimiche.setM3PAviU(caratteristichechimiche.getM3PAviS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAviS());
                           caratteristichechimiche.setM3PAviM(false);
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknPAviU(valore);
                           caratteristichechimiche.setTknPAviM(true);
                       } else {
                           caratteristichechimiche.setTknPAviU(caratteristichechimiche.getTknPAviS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAviS());
                            caratteristichechimiche.setTknPAviM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanPAviU(valore);
                           caratteristichechimiche.setTanPAviM(true);
                       } else {
                           caratteristichechimiche.setTanPAviU(caratteristichechimiche.getTanPAviS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAviS());
                            caratteristichechimiche.setTanPAviM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmPAviU(valore);
                            caratteristichechimiche.setDmPAviM(true);
                       } else {
                           caratteristichechimiche.setDmPAviU(caratteristichechimiche.getDmPAviS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAviS());
                            caratteristichechimiche.setDmPAviM(false);
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsPAviU(valore);
                           caratteristichechimiche.setVsPAviM(true);
                       } else {
                           caratteristichechimiche.setVsPAviU(caratteristichechimiche.getVsPAviS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAviS());
                            caratteristichechimiche.setVsPAviM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKPAviU(valore);
                           caratteristichechimiche.setKPAviM(true);
                       } else {
                           caratteristichechimiche.setKPAviU(caratteristichechimiche.getKPAviS());
                         //  dataItemLet.setPotassiototale(caratteristichechimiche.getKPAviS());
                            caratteristichechimiche.setKPAviM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPPAviU(valore);
                            caratteristichechimiche.setPPAviM(true);
                       } else {
                           caratteristichechimiche.setPPAviU(caratteristichechimiche.getPPAviS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAviS());
                            caratteristichechimiche.setPPAviM(false);
                       }
                       break;
               }
               break;
           case "Letame Altro":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3PAltU(valore);
                           caratteristichechimiche.setM3PAltM(true);
                       } else {
                           caratteristichechimiche.setM3PAltU(caratteristichechimiche.getM3PAltS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAltS());
                            caratteristichechimiche.setM3PAltM(false);
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknPAltU(valore);
                            caratteristichechimiche.setTknPAltM(true);
                       } else {
                           caratteristichechimiche.setTknPAltU(caratteristichechimiche.getTknPAltS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAltS());
                           caratteristichechimiche.setTknPAltM(false);
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanPAltU(valore);
                            caratteristichechimiche.setTanPAltM(true);
                       } else {
                           caratteristichechimiche.setTanPAltU(caratteristichechimiche.getTanPAltS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAltS());
                            caratteristichechimiche.setTanPAltM(false);
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmPAltU(valore);
                           caratteristichechimiche.setDmPAltM(true);
                       } else {
                           caratteristichechimiche.setDmPAltU(caratteristichechimiche.getDmPAltS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAltS());
                           caratteristichechimiche.setDmPAltM(false);
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsPAltU(valore);
                            caratteristichechimiche.setVsPAltM(true);
                       } else {
                           caratteristichechimiche.setVsPAltU(caratteristichechimiche.getVsPAltS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAltS());
                            caratteristichechimiche.setVsPAltM(false);
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKPAltU(valore);
                           caratteristichechimiche.setKPAltM(true);
                       } else {
                           caratteristichechimiche.setKPAltU(caratteristichechimiche.getKPAltS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPAltS());
                           caratteristichechimiche.setKPAltM(false);
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPPAltU(valore);
                           caratteristichechimiche.setPPAltM(true);
                       } else {
                           caratteristichechimiche.setPPAltU(caratteristichechimiche.getPPAltS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAltS());
                           caratteristichechimiche.setPPAltM(false);
                       }
                       break;
               }
               break;
           case "Letame Biomassa":
               // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (posizione) {
                   case 1:
                       if (azione == 0) {
                           caratteristichechimiche.setM3PBioU(valore);
                           caratteristichechimiche.setM3PBioM(true);
                       } else {
                          
                           caratteristichechimiche.setM3PBioU(caratteristichechimiche.getM3PBioS());
                         caratteristichechimiche.setM3PBioM(false);
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PBioS());
                       }
                       break;
                   case 2:
                        if (azione == 0) {
                           caratteristichechimiche.setTknPBioU(valore);
                           caratteristichechimiche.setTknPBioM(true);
                       } else {
                           caratteristichechimiche.setTknPBioU(caratteristichechimiche.getTknPBioS());
                           caratteristichechimiche.setTknPBioM(false);
                          
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPBioS());
                       }
                       break;
                   case 3:
                       if (azione == 0) {
                           caratteristichechimiche.setTanPBioU(valore);
                           caratteristichechimiche.setTanPBioM(true);
                       } else {
                           caratteristichechimiche.setTanPBioU(caratteristichechimiche.getTanPBioS());
                            caratteristichechimiche.setTanPBioM(false);
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBioS());
                       }
                       break;
                   case 4:
                       if (azione == 0) {
                           caratteristichechimiche.setDmPBioU(valore);
                           caratteristichechimiche.setDmPBioM(true);
                       } else {
                           caratteristichechimiche.setDmPBioU(caratteristichechimiche.getDmPBioS());
                           caratteristichechimiche.setDmPBioM(false);
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBioS());
                       }
                       break;
                   case 5:
                       if (azione == 0) {
                           caratteristichechimiche.setVsPBioU(valore);
                           caratteristichechimiche.setVsPBioM(true);
                       } else {
                           caratteristichechimiche.setVsPBioU(caratteristichechimiche.getVsPBioS());
                            caratteristichechimiche.setVsPBioM(false);
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBioS());
                       }
                       break;
                   case 6:
                       if (azione == 0) {
                           caratteristichechimiche.setKPBioU(valore);
                           caratteristichechimiche.setKPBioM(true);
                       } else {
                           caratteristichechimiche.setKPBioU(caratteristichechimiche.getKPBioS());
                           caratteristichechimiche.setKPBioM(false);
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPBioS());
                       }
                       break;
                   case 7:
                       if (azione == 0) {
                           caratteristichechimiche.setPPBioU(valore);
                            caratteristichechimiche.setPPBioM(true);
                       } else {
                           caratteristichechimiche.setPPBioU(caratteristichechimiche.getPPBioS());
                            caratteristichechimiche.setPPBioM(false);
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPBioS());
                       }
                       break;
               }
               break;
       }
       
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();
      entityManager.persist(caratteristichechimiche);
      entityManager.flush();
      tx.commit();
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
    
    
}
