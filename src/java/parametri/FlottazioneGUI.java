/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parametri;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import ager.trattamenti.SeparazioneAVite;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import operativo.RefluiAzienda;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.Parametridiprogetto;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;
//import operativo.UserAger;

/**
 *Classe che si occupa di mostrare il dettaglio del trattamento della separazione 
 * senza la presenza delle percentuali che potrebbero complicare la verifica della correttezza della
 * classe specifica del trattamento
 * @author giorgio
 */
@ManagedBean(name = "flottazioneGUI")
@ViewScoped
public class FlottazioneGUI implements Serializable{
    
   private static final long serialVersionUID = 1L;
   
   /**
    * popolano i campi testo dei valori di input al trattamento
    * della separazione
    */
   private List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
   private List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
   
   /**
    * popolano i campi testo di output del trattamento della seprazione
    */
   private List<Refluo> listaCaratteristicheLiqOut = new LinkedList<Refluo>();
   private List<Refluo> listaCaratteristicheLetOut = new LinkedList<Refluo>();
    
   ContenitoreReflui contenitoreIniziale = null;
   
   
   //output gestionali
  /**
   * numero di ore complessive
   */
  //private  double orecomplessive = 0;
  /**
   * numero dei separatori
   */
 // private  double numeroseparatori = 0;
  /**
   * energia elettrica consumata
   */
 /* private  double  costoinvestimento =0;
  
  private  double costogestione = 0;
  
  private  double costototale = 0;*/
  
  /**
   * gestionali
   */
   /*private double costocomplessivo = 0;
   private double costomanutenzione = 0;
   private double energiavenduta = 0;
   private double venditacompost = 0;
   private double solidoessiccato = 0;
   private double energiaprodotta = 0;
   private double energiaconsumata = 0;*/
  
  
  
  /**
     * emissioni di un trattamento comune a tutti i trattamenti
     */
  /*private  double ch4 = 0;
  private  double nh3 = 0;
  private  double co2 = 0;
  private  double n2 = 0;
  private  double n2o = 0;*/
  
  /**
   * valore del componente selectonemenu per la selezione del parametro della macchina
   * (investimento,ann,costo gestione,costo energia,...) presente nella pagina 
   * separazione.xhtml
   */
  private String coefficientemaccchina = "96";
   /**
   * valore del componente selectonemenu per la selezione del parametro della macchina
   * (piccolo,grande) presente nella pagina 
   * separazione.xhtml
   */
  //private String coefficientetipomacchina = "senza_additivi";
  /**
   * valore del campo testo del primo sistema di selezione dei parametri 
   * della separazione a vite elicoidale
   */
  private String valorecoefficiente = "";
  /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale
   */
  private String coefficienterefluo ="1";
   /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale
   */
  private String coefficentecaratteristica = "1";
  /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale che corrisponde a c1
   */
  private String valorecoefficiente1 = "";
  /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale che corrisponde a c2
   */
  private String valorecoefficiente2 = "";
  /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale che corrisponde a m1
   */
  private String valorecoefficiente3 = "";
  /**
   * valore del campo testo del secondo sistema di selezione dei parametri 
   * della separazione a vite elicoidale che corrisponde a m2
   */
  private String valorecoefficiente4 = "";
  
  private double investimento = 1000;
  private double anni = 8;
  private double ore_funzionamento_anno = 7884;
  private double costo_sale_metallico = 10;
  private double costo_polielettrolita = 40;
  private double consumo_sale_metallico = 0.07;
  private double consumo_polielettrolita = 0.008;
  private double costo_energia = 0.15;
  private double costo_gestione_unitario = 0.5;
  private double carico_idraulico_superficiale = 2;
  private double tempo_ritenzione_idraulica = 1;
  private double potenza_unitaria = 0.48;
  private double fattore_ch4 = 0;
  private double fattore_nh3 = 5;
  private double fattore_co2 = 0.1;
  private double fattore_n2 = 0 ;
  private double fattore_n20 = 0;
  private double tasso_interesse = 0.05;
  
  
  /**
   * contenitore dei parametri diprogetto 
   */
  Parametridiprogetto classeparametri = null;
   
   private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
   
   
   public FlottazioneGUI()
   {
       
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
          
     
       classeparametri = new Parametridiprogetto("renuwal2",12,dettCuaa.getIdscenario());
       
    System.out.println("cuaa " +dettCuaa.getCuaa() +"    scenario " + dettCuaa.getIdscenario());
        
        
     /*     contenitoreIniziale = SeparazioneAVite.getInstance().getContenitoreRefluiIn();
        
            

        
        for(String s:contenitoreIniziale.getTipologie())
        {
            if(s.contains("Liquame"))
                        listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
            
            if(s.contains("Letame"))
                listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
        }
        
        
         listaCaratteristicheLet.add(contenitoreIniziale.totale("Letame"));
        listaCaratteristicheLiq.add(contenitoreIniziale.totale("Liquame"));
       
      RefluiAzienda dettRefluiAzienda = (RefluiAzienda) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "refluiAzienda");
          
 */
        
   }
   
   
   
   
   
   /**
    * aggiorna i valori dei parametri gestionali di output 
    * in funzione dei calcoli delle trasformazioni della separazione
    */
  /* private void aggiornaOutputGestionali()
   {
       SeparazioneAVite separazione = SeparazioneAVite.getInstance();
        
       
         if(separazione.getContenitoreRefluiIn().totale("Liquame").getMetricubi() != 0)
         {
       this.setOrecomplessive(separazione.getOrecomplessive());
       this.setNumeroseparatori(separazione.getNumeroseparatori());
       this.setCostocomplessivo(separazione.getInvestimento());
       this.setCostomanutenzione(separazione.getEsercizio());
       this.setEnergiavenduta(separazione.getEnergiavenduta());
       this.setVenditacompost(separazione.getCompostvenduto());
       this.setSolidoessiccato(separazione.getSolidoessiccato());
       this.setEnergiaprodotta(separazione.getEnergiaprodotta());
       this.setEnergiaconsumata(separazione.getEnergiaconsumata());
         }
       //System.out.println("outputgestionali ore " + this.getOrecomplessive() + "  numero " + this.getNumeroseparatori() + " investimento " + this.getCostoinvestimento() + " gestione " + this.getCostogestione() + " totale " + this.getCostototale());
   }*/
   
   
    /**
    * aggiorna i valori dei parametri delle emissioni di output 
    * in funzione dei calcoli delle trasformazioni della separazione
    */
   /*private void aggiornaEmissioni()
   {
       SeparazioneAVite separazione = SeparazioneAVite.getInstance();
         if(separazione.getContenitoreRefluiIn().totale("Liquame").getMetricubi() != 0)
         {
       this.setCh4(separazione.getCh4());
       this.setNh3(separazione.getNh3());
       this.setCo2(separazione.getCo2());
       this.setN2(separazione.getN2());
       this.setN2o(separazione.getN2o());
         }
   }*/
   
//   /**
//    * metodo che viene lanciato quando accade l'evento click 
//    * sul bottone calcola
//    */
//    public void calcola()
//    {
//       
//        SeparazioneAVite separazioneavite = SeparazioneAVite.getInstance();
//      
//        
//        ContenitoreReflui contenitorereflui = separazioneavite.getContenitoreRefluiOut();
//       
//        Hashtable<String,Refluo> tabellaHash = contenitorereflui.getContenitore();
//        
//        /**
//         * svuoto le due liste di output 
//         */
//        this.listaCaratteristicheLetOut.clear();
//        this.listaCaratteristicheLiqOut.clear();
//        
//        /**
//         * variabile di appoggio per la classe Refluo
//         */
//        Refluo re = null;
//        
//        /**
//         * ciclo sulle chiavi della tabella hash ed aggiungo alla listacaratteristicheoutliq
//         * le diverse tipologie di liquame ed aggiungo alla listacaratteriscticheoutlet le diverse
//         * tipolgie di letame
//         */
//        for(String s:contenitorereflui.getTipologie())
//        {
//            if(s.contains("Letame"))
//            {
//               re = tabellaHash.get(s);
//               
//               this.listaCaratteristicheLetOut.add(re);
//               
//            }
//            
//            if(s.contains("Liquame"))
//            {
//               re = tabellaHash.get(s);
//               
//               this.listaCaratteristicheLiqOut.add(re);
//            }
//        }
//        
//      
//        
//    }

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
    
    
    /*private void simulaCaratteristicheChimiche(ContenitoreReflui contenitore)
    {
        
        Refluo re = contenitore.getTipologia("Letame Suino");
        
        re.setAzotoammoniacale(new Double("0"));
        re.setAzotototale(new Double("0"));
        re.setFosforototale(new Double("0"));
        re.setMetricubi(new Double("0"));
        //re.setMetricubibiomassa(new Double("0"));
        re.setPotassiototale(new Double("0"));
        re.setSolidivolatili(new Double("0"));
        re.setSostanzasecca(new Double("0"));
        
       
        re = contenitore.getTipologia("Letame Bovino");
        re.setAzotoammoniacale(new Double("0"));
        re.setAzotototale(new Double("0"));
       
        re.setFosforototale(new Double("0"));
        re.setMetricubi(new Double("0"));
        //re.setMetricubibiomassa(new Double("0"));
        re.setPotassiototale(new Double("0"));
        re.setSolidivolatili(new Double("0"));
        re.setSostanzasecca(new Double("0"));
        
       
         re = contenitore.getTipologia("Liquame Suino");
        
        re.setAzotoammoniacale(new Double("70340"));
        re.setAzotototale(new Double("125606"));
        re.setFosforototale(new Double("98280"));
        re.setMetricubi(new Double("73427"));
        //re.setMetricubibiomassa(new Double("0"));
        re.setPotassiototale(new Double("112455"));
        re.setSolidivolatili(new Double("2931390"));
        re.setSostanzasecca(new Double("3794175"));
        
         re = contenitore.getTipologia("Liquame Bovino");
        
        
        re.setAzotoammoniacale(new Double("5215"));
        re.setAzotototale(new Double("9315"));
        re.setFosforototale(new Double("2982"));
        re.setMetricubi(new Double("5734"));
        //re.setMetricubibiomassa(new Double("0"));
        re.setPotassiototale(new Double("4798"));
        re.setSolidivolatili(new Double("210715"));
        re.setSostanzasecca(new Double("252858"));
        
       
        
        
    }*/

    /**
     * @return the listaCaratteristicheLiqOut
     */
    public List<Refluo> getListaCaratteristicheLiqOut() {
        return listaCaratteristicheLiqOut;
    }

    /**
     * @param listaCaratteristicheLiqOut the listaCaratteristicheLiqOut to set
     */
    public void setListaCaratteristicheLiqOut(List<Refluo> listaCaratteristicheLiqOut) {
        this.listaCaratteristicheLiqOut = listaCaratteristicheLiqOut;
    }

    /**
     * @return the listaCaratteristicheLetOut
     */
    public List<Refluo> getListaCaratteristicheLetOut() {
        return listaCaratteristicheLetOut;
    }

    /**
     * @param listaCaratteristicheLetOut the listaCaratteristicheLetOut to set
     */
    public void setListaCaratteristicheLetOut(List<Refluo> listaCaratteristicheLetOut) {
        this.listaCaratteristicheLetOut = listaCaratteristicheLetOut;
    }

    

//    /**
//     * @return the ch4
//     */
//    public double getCh4() {
//        return ch4;
//    }
//
//    /**
//     * @param ch4 the ch4 to set
//     */
//    public void setCh4(double ch4) {
//        this.ch4 = ch4;
//    }
//
//    /**
//     * @return the nh3
//     */
//    public double getNh3() {
//        return nh3;
//    }
//
//    /**
//     * @param nh3 the nh3 to set
//     */
//    public void setNh3(double nh3) {
//        this.nh3 = nh3;
//    }
//
//    /**
//     * @return the co2
//     */
//    public double getCo2() {
//        return co2;
//    }
//
//    /**
//     * @param co2 the co2 to set
//     */
//    public void setCo2(double co2) {
//        this.co2 = co2;
//    }
//
//    /**
//     * @return the n2
//     */
//    public double getN2() {
//        return n2;
//    }
//
//    /**
//     * @param n2 the n2 to set
//     */
//    public void setN2(double n2) {
//        this.n2 = n2;
//    }
//
//    /**
//     * @return the n2o
//     */
//    public double getN2o() {
//        return n2o;
//    }
//
//    /**
//     * @param n2o the n2o to set
//     */
//    public void setN2o(double n2o) {
//        this.n2o = n2o;
//    }
    
    
  /* private boolean nascondiMostraRisultati(String id,boolean mostra)
    {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent comp = view.findComponent(id);
        if(comp != null)
        {
            System.out.println("id componente "+ id+ "  client id   " + comp.getClientId());
            comp.setRendered(mostra);
            
            return true;        
        }
        else
            return false;
        
       
        
    }*/

//    /**
//     * @return the orecomplessive
//     */
//    public double getOrecomplessive() {
//        return orecomplessive;
//    }
//
//    /**
//     * @param orecomplessive the orecomplessive to set
//     */
//    public void setOrecomplessive(double orecomplessive) {
//        this.orecomplessive = orecomplessive;
//    }
//
//    /**
//     * @return the numeroseparatori
//     */
//    public double getNumeroseparatori() {
//        return numeroseparatori;
//    }
//
//    /**
//     * @param numeroseparatori the numeroseparatori to set
//     */
//    public void setNumeroseparatori(double numeroseparatori) {
//        this.numeroseparatori = numeroseparatori;
//    }
//
//    /**
//     * @return the costocomplessivo
//     */
//    public double getCostocomplessivo() {
//        return costocomplessivo;
//    }
//
//    /**
//     * @param costocomplessivo the costocomplessivo to set
//     */
//    public void setCostocomplessivo(double costocomplessivo) {
//        this.costocomplessivo = costocomplessivo;
//    }
//
//    /**
//     * @return the costomanutenzione
//     */
//    public double getCostomanutenzione() {
//        return costomanutenzione;
//    }
//
//    /**
//     * @param costomanutenzione the costomanutenzione to set
//     */
//    public void setCostomanutenzione(double costomanutenzione) {
//        this.costomanutenzione = costomanutenzione;
//    }
//
//    /**
//     * @return the energiavenduta
//     */
//    public double getEnergiavenduta() {
//        return energiavenduta;
//    }
//
//    /**
//     * @param energiavenduta the energiavenduta to set
//     */
//    public void setEnergiavenduta(double energiavenduta) {
//        this.energiavenduta = energiavenduta;
//    }
//
//    /**
//     * @return the venditacompost
//     */
//    public double getVenditacompost() {
//        return venditacompost;
//    }
//
//    /**
//     * @param venditacompost the venditacompost to set
//     */
//    public void setVenditacompost(double venditacompost) {
//        this.venditacompost = venditacompost;
//    }
//
//    /**
//     * @return the solidoessiccato
//     */
//    public double getSolidoessiccato() {
//        return solidoessiccato;
//    }
//
//    /**
//     * @param solidoessiccato the solidoessiccato to set
//     */
//    public void setSolidoessiccato(double solidoessiccato) {
//        this.solidoessiccato = solidoessiccato;
//    }
//
//    /**
//     * @return the energiaprodotta
//     */
//    public double getEnergiaprodotta() {
//        return energiaprodotta;
//    }
//
//    /**
//     * @param energiaprodotta the energiaprodotta to set
//     */
//    public void setEnergiaprodotta(double energiaprodotta) {
//        this.energiaprodotta = energiaprodotta;
//    }
//
//    /**
//     * @return the energiaconsumata
//     */
//    public double getEnergiaconsumata() {
//        return energiaconsumata;
//    }
//
//    /**
//     * @param energiaconsumata the energiaconsumata to set
//     */
//    public void setEnergiaconsumata(double energiaconsumata) {
//        this.energiaconsumata = energiaconsumata;
//    }

    /**
     * @return the coefficientemaccchina
     */
    public String getCoefficientemaccchina() {
        return coefficientemaccchina;
    }

    /**
     * @param coefficientemaccchina the coefficientemaccchina to set
     */
    public void setCoefficientemaccchina(String coefficientemaccchina) {
        this.coefficientemaccchina = coefficientemaccchina;
    }

    /**
     * @return the coefficientetipomacchina
     */
   /* public String getCoefficientetipomacchina() {

        System.out.println(this.getClass().getCanonicalName() + Thread.currentThread().getStackTrace()[1].getMethodName() + " get  coefficientetipomacchina  " +coefficientetipomacchina);

        return coefficientetipomacchina;
    }*/

    /**
     * @param coefficientetipomacchina the coefficientetipomacchina to set
     */
   /* public void setCoefficientetipomacchina(String coefficientetipomacchina) {
        
        System.out.println(this.getClass().getCanonicalName() + Thread.currentThread().getStackTrace()[0].getMethodName() + " set  coefficientetipomacchina  " +coefficientetipomacchina);
        this.coefficientetipomacchina = coefficientetipomacchina;
    }*/

    /**
     * @return the valorecoefficiente
     */
    public String getValorecoefficiente() {
        
         //  System.out.println(this.getClass().getCanonicalName() +" " + Thread.currentThread().getStackTrace()[0].getMethodName()+ "  getValorecoefficiente() coefficienteemissione " + this.coefficientemaccchina +"   stoccaggio " + this.coefficientetipomacchina);
           if(this.coefficientemaccchina.length() != 0 )
        {
            /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
     /* ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
            
         
      
                 Parametridiprogetto classeparametri = new Parametridiprogetto("renuwal2",3,dettCuaa.getIdscenario());*/
                 
                System.out.println(" prima " + this.getClass().getCanonicalName() +"  sono nellif   metodo " +Thread.currentThread().getStackTrace()[1].getMethodName()+" valorecoefficiente " + this.valorecoefficiente + " this.coefficientemaccchina " +this.coefficientemaccchina  );

            
                 db.ParametridiprogettoS param = classeparametri.getParametrodiprogetto(0,Integer.parseInt(this.coefficientemaccchina),null,null,null);
                 valorecoefficiente = param.getValore();
                 
                 //System.out.println(this.getClass().getCanonicalName() + " valorecoefficiente " + this.valorecoefficiente);
                 //valorecoefficiente = Double.parseDouble(param.getValore());
                 
                 
                 System.out.println("dopo " + this.getClass().getCanonicalName() +"  sono nellif   metodo " +Thread.currentThread().getStackTrace()[1].getMethodName()+" valorecoefficiente " + this.valorecoefficiente + " this.coefficientemaccchina " +this.coefficientemaccchina  );
        }
        
        
        return valorecoefficiente;
    }

    /**
     * @param valorecoefficiente the valorecoefficiente to set
     */
    public void setValorecoefficiente(String valorecoefficiente) {
        if(valorecoefficiente != null && valorecoefficiente.length() != 0) {
            this.valorecoefficiente = valorecoefficiente;
        }
    }

    /**
     * @return the coefficienterefluo
     */
    public String getCoefficienterefluo() {
        return coefficienterefluo;
    }

    /**
     * @param coefficienterefluo the coefficienterefluo to set
     */
    public void setCoefficienterefluo(String coefficienterefluo) {
        this.coefficienterefluo = coefficienterefluo;
    }

    /**
     * @return the coefficentecaratteristica
     */
    public String getCoefficentecaratteristica() {
        return coefficentecaratteristica;
    }

    /**
     * @param coefficentecaratteristica the coefficentecaratteristica to set
     */
    public void setCoefficentecaratteristica(String coefficentecaratteristica) {
        this.coefficentecaratteristica = coefficentecaratteristica;
    }

    /**
     * @return the valorecoefficiente1
     */
    public String getValorecoefficiente1() {
        
            System.out.println(this.getClass().getCanonicalName() + "  getValorecoefficiente() coefficienteemissione " + this.coefficientemaccchina );
           if(this.coefficientemaccchina.length() != 0 )
        {
            /**
          * le seguenti righe mi servono per recuperare il session bean utente impostato nella pagina index
          * in cui dopo la verifica dell'account ho impostato lo username e la password
          */
      /*ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
            
         
      
                 Parametridiprogetto classeparametri = new Parametridiprogetto("renuwal2",3,dettCuaa.getIdscenario());*/
                 double[] params = classeparametri.getEfficienze(Integer.parseInt(this.coefficienterefluo),Integer.parseInt(this.coefficentecaratteristica));
                 valorecoefficiente1 = String.valueOf(params[0]);
                   valorecoefficiente2 = String.valueOf(params[1]);
                     valorecoefficiente3 = String.valueOf(params[2]);
                       valorecoefficiente4 = String.valueOf(params[3]);
                 
                // System.out.println(this.getClass().getCanonicalName() + " valorecoefficiente " + this.valorecoefficiente);
                 //valorecoefficiente = Double.parseDouble(param.getValore());
                 
                 
                 System.out.println(this.getClass().getCanonicalName() + " valorecoefficiente " + this.valorecoefficiente);
        }
        
        
        
        return valorecoefficiente1;
    }

    /**
     * @param valorecoefficiente1 the valorecoefficiente1 to set
     */
    public void setValorecoefficiente1(String valorecoefficiente1) {
        if(valorecoefficiente1 != null && valorecoefficiente1.length() != 0) {
            this.valorecoefficiente1 = valorecoefficiente1;
        }
    }

    /**
     * @return the costoinvestimento
     */
   /* public double getCostoinvestimento() {
        return costoinvestimento;
    }

    /**
     * @param costoinvestimento the costoinvestimento to set
     */
    /*public void setCostoinvestimento(double costoinvestimento) {
        this.costoinvestimento = costoinvestimento;
    }

    /**
     * @return the costogestione
     */
   /* public double getCostogestione() {
        return costogestione;
    }

    /**
     * @param costogestione the costogestione to set
     */
   /* public void setCostogestione(double costogestione) {
        this.costogestione = costogestione;
    }

    /**
     * @return the costototale
     */
   /* public double getCostototale() {
        return costototale;
    }

    /**
     * @param costototale the costototale to set
     */
  /*  public void setCostototale(double costototale) {
        this.costototale = costototale;
    }*/

    
public void aggiorna()
{
    
     Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	  String action = params.get("action");
          
          System.out.println(this.getClass().getCanonicalName() + " azione " + action);
    
          db.ParametridiprogettoS temp = null;
          
   /*if(action.equals("1"))
    {
        temp = classeparametri.getParametrodiprogetto(2,Integer.parseInt(this.coefficientemaccchina), null, null,this.coefficientetipomacchina);
    }*/   
    /*else
        temp = classeparametri.getParametrodiprogetto(3, this.coefficientemaccchina, this.coefficienterefluo,this.coefficentecaratteristica,null);*/
   
    System.out.println("action value " + action + " this.coefficienterefluo " + this.coefficienterefluo + " this.coefficentecaratteristica " + this.coefficentecaratteristica);      
          
          
    switch (action) {
        case "1":
            temp = classeparametri.getParametrodiprogetto(0, Integer.parseInt(this.coefficientemaccchina), null, null,null);
            temp.setValore(this.valorecoefficiente);
            break;
        case "28":
             temp = classeparametri.getParametrodiprogetto(3,28,this.coefficienterefluo,this.coefficentecaratteristica, null);
            //System.out.println("");
             temp.setValore(this.valorecoefficiente1);
            break;
        case "29":
             temp = classeparametri.getParametrodiprogetto(3,29,this.coefficienterefluo,this.coefficentecaratteristica, null);
            temp.setValore(this.valorecoefficiente2);
            break;
        case "30":
             temp = classeparametri.getParametrodiprogetto(3,30,this.coefficienterefluo,this.coefficentecaratteristica, null);
            temp.setValore(this.valorecoefficiente3);
            break;
        case "31":
             temp = classeparametri.getParametrodiprogetto(3,31,this.coefficienterefluo,this.coefficentecaratteristica, null);
            temp.setValore(this.valorecoefficiente4);
            break;
    }
    
    
    
    
    
    //temp.setValore(this.valorecoefficiente);
    /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
    
      if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
    
    
         entityManager.getTransaction().begin();
    entityManager.merge(temp);
    //entityManager.flush();
     entityManager.getTransaction().commit();
    System.out.println("action " + action + " this.coefficientemaccchina " + this.coefficientemaccchina +  " valore " + temp.getValore());
    
    
    Connessione.getInstance().chiudi();
    
}

    /**
     * @return the valorecoefficiente2
     */
    public String getValorecoefficiente2() {
        return valorecoefficiente2;
    }

    /**
     * @param valorecoefficiente2 the valorecoefficiente2 to set
     */
    public void setValorecoefficiente2(String valorecoefficiente2) {
        if(valorecoefficiente2 != null && valorecoefficiente2.length() != 0) {
            this.valorecoefficiente2 = valorecoefficiente2;
        }
    }

    /**
     * @return the valorecoefficiente3
     */
    public String getValorecoefficiente3() {
        return valorecoefficiente3;
    }

    /**
     * @param valorecoefficiente3 the valorecoefficiente3 to set
     */
    public void setValorecoefficiente3(String valorecoefficiente3) {
        if(valorecoefficiente3 != null && valorecoefficiente3.length() != 0) {
            this.valorecoefficiente3 = valorecoefficiente3;
        }
    }

    /**
     * @return the valorecoefficiente4
     */
    public String getValorecoefficiente4() {
        return valorecoefficiente4;
    }

    /**
     * @param valorecoefficiente4 the valorecoefficiente4 to set
     */
    public void setValorecoefficiente4(String valorecoefficiente4) {
        if(valorecoefficiente4 != null && valorecoefficiente4.length() != 0) {
            this.valorecoefficiente4 = valorecoefficiente4;
        }
    }

    /**
     * @return the investimento
     */
    public double getInvestimento() {
        return investimento;
    }

    /**
     * @param investimento the investimento to set
     */
    public void setInvestimento(double investimento) {
        this.investimento = investimento;
    }

    /**
     * @return the anni
     */
    public double getAnni() {
        return anni;
    }

    /**
     * @param anni the anni to set
     */
    public void setAnni(double anni) {
        this.anni = anni;
    }

    /**
     * @return the ore_funzionamento_anno
     */
    public double getOre_funzionamento_anno() {
        return ore_funzionamento_anno;
    }

    /**
     * @param ore_funzionamento_anno the ore_funzionamento_anno to set
     */
    public void setOre_funzionamento_anno(double ore_funzionamento_anno) {
        this.ore_funzionamento_anno = ore_funzionamento_anno;
    }

    /**
     * @return the costo_sale_metallico
     */
    public double getCosto_sale_metallico() {
        return costo_sale_metallico;
    }

    /**
     * @param costo_sale_metallico the costo_sale_metallico to set
     */
    public void setCosto_sale_metallico(double costo_sale_metallico) {
        this.costo_sale_metallico = costo_sale_metallico;
    }

    /**
     * @return the costo_polielettrolita
     */
    public double getCosto_polielettrolita() {
        return costo_polielettrolita;
    }

    /**
     * @param costo_polielettrolita the costo_polielettrolita to set
     */
    public void setCosto_polielettrolita(double costo_polielettrolita) {
        this.costo_polielettrolita = costo_polielettrolita;
    }

    /**
     * @return the consumo_sale_metallico
     */
    public double getConsumo_sale_metallico() {
        return consumo_sale_metallico;
    }

    /**
     * @param consumo_sale_metallico the consumo_sale_metallico to set
     */
    public void setConsumo_sale_metallico(double consumo_sale_metallico) {
        this.consumo_sale_metallico = consumo_sale_metallico;
    }

    /**
     * @return the consumo_polielettrolita
     */
    public double getConsumo_polielettrolita() {
        return consumo_polielettrolita;
    }

    /**
     * @param consumo_polielettrolita the consumo_polielettrolita to set
     */
    public void setConsumo_polielettrolita(double consumo_polielettrolita) {
        this.consumo_polielettrolita = consumo_polielettrolita;
    }

    /**
     * @return the costo_energia
     */
    public double getCosto_energia() {
        return costo_energia;
    }

    /**
     * @param costo_energia the costo_energia to set
     */
    public void setCosto_energia(double costo_energia) {
        this.costo_energia = costo_energia;
    }

    /**
     * @return the costo_gestione_unitario
     */
    public double getCosto_gestione_unitario() {
        return costo_gestione_unitario;
    }

    /**
     * @param costo_gestione_unitario the costo_gestione_unitario to set
     */
    public void setCosto_gestione_unitario(double costo_gestione_unitario) {
        this.costo_gestione_unitario = costo_gestione_unitario;
    }

    /**
     * @return the carico_idraulico_superficiale
     */
    public double getCarico_idraulico_superficiale() {
        return carico_idraulico_superficiale;
    }

    /**
     * @param carico_idraulico_superficiale the carico_idraulico_superficiale to set
     */
    public void setCarico_idraulico_superficiale(double carico_idraulico_superficiale) {
        this.carico_idraulico_superficiale = carico_idraulico_superficiale;
    }

    /**
     * @return the tempo_ritenzione_idraulica
     */
    public double getTempo_ritenzione_idraulica() {
        return tempo_ritenzione_idraulica;
    }

    /**
     * @param tempo_ritenzione_idraulica the tempo_ritenzione_idraulica to set
     */
    public void setTempo_ritenzione_idraulica(double tempo_ritenzione_idraulica) {
        this.tempo_ritenzione_idraulica = tempo_ritenzione_idraulica;
    }

    /**
     * @return the potenza_unitaria
     */
    public double getPotenza_unitaria() {
        return potenza_unitaria;
    }

    /**
     * @param potenza_unitaria the potenza_unitaria to set
     */
    public void setPotenza_unitaria(double potenza_unitaria) {
        this.potenza_unitaria = potenza_unitaria;
    }

    /**
     * @return the fattore_ch4
     */
    public double getFattore_ch4() {
        return fattore_ch4;
    }

    /**
     * @param fattore_ch4 the fattore_ch4 to set
     */
    public void setFattore_ch4(double fattore_ch4) {
        this.fattore_ch4 = fattore_ch4;
    }

    /**
     * @return the fattore_nh3
     */
    public double getFattore_nh3() {
        return fattore_nh3;
    }

    /**
     * @param fattore_nh3 the fattore_nh3 to set
     */
    public void setFattore_nh3(double fattore_nh3) {
        this.fattore_nh3 = fattore_nh3;
    }

    /**
     * @return the fattore_co2
     */
    public double getFattore_co2() {
        return fattore_co2;
    }

    /**
     * @param fattore_co2 the fattore_co2 to set
     */
    public void setFattore_co2(double fattore_co2) {
        this.fattore_co2 = fattore_co2;
    }

    /**
     * @return the fattore_n2
     */
    public double getFattore_n2() {
        return fattore_n2;
    }

    /**
     * @param fattore_n2 the fattore_n2 to set
     */
    public void setFattore_n2(double fattore_n2) {
        this.fattore_n2 = fattore_n2;
    }

    /**
     * @return the fattore_n20
     */
    public double getFattore_n20() {
        return fattore_n20;
    }

    /**
     * @param fattore_n20 the fattore_n20 to set
     */
    public void setFattore_n20(double fattore_n20) {
        this.fattore_n20 = fattore_n20;
    }

    /**
     * @return the tasso_interesse
     */
    public double getTasso_interesse() {
        return tasso_interesse;
    }

    /**
     * @param tasso_interesse the tasso_interesse to set
     */
    public void setTasso_interesse(double tasso_interesse) {
        this.tasso_interesse = tasso_interesse;
    }
    
    
    
}
