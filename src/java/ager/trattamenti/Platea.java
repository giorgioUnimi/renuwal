/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;

import ager.Refluo;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.Parametridiprogetto;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;


/**
 *
 * @author giorgio
 */
public class Platea extends Trattamento{
    
    //parametro per rendere questa classe un sigleton
    private static Platea istanza;
    
    /**
     * variabili tuili per la connesione al db
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    
    /**
     * capacità totale delle strutture per palabili
     */
   private double capacita = 0;
    /**
     * parametri utii per i calcoli
     */
   private double fnh3medio = 0;
   private  double fn2omedio = 0;
   private double fch4medio = 0;
   private double fsvmedio = 0;
   private  double fnminmedio = 0;
   
   /**
    * durata in giorni
    */
   private double durata = 0;
   /**
    * coefficiente di durata per i palabili
    */
   private double coeff_durata_p = 0;
  /**
    * 
    * 
    * DADTI DI OUTPUT
    * 
    */
   private double stoccaggirichiesti  = 0;
   private double stoccaggipresenti = 0;
   private double stoccaggidarealizzare = 0;
   /**
    * 
    * DATI DI OUTPUT
    * 
    */
   /**
    * Lista dei parametri di progetto
    */
   List<db.ParametridiprogettoS> parametridiprogetto = null;
    Parametridiprogetto classeparametri = null;
/**
 * 
 * OUTPUT GESTIONALI
 */
   //private double costocomplessivo = 0;sostituito da Trattamento.investimento
   //private double costomanutenzione = 0;sostituito da Trattamento.esercizio
   /**
    * 
    * OUTPUT GESTIONALI
    */
  private Platea(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
     // parametridiprogetto = new LinkedList<db.ParametridiprogettoS>();
     
  }

  public static Platea getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Platea(/*totlet,totliq*/);
     
    }

    return istanza;
  }
  
  
  
  /**
   * recupera tutti i parametri di progetto per uno specifico scenario e per uno
   * specifico trattamento
   */
  /*private List<db.ParametridiprogettoS> getlistaparametri()
  {
      entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio13");
      entityManager = entityManagerFactory.createEntityManager();
      jpa = (JpaEntityManager) entityManager.getDelegate();
      
      /**
         * recupero il session bean dettaGlioCuaa
         */
      /*   ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         
         Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
         db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
         
         Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",7);
         db.TrattamentoS tratta = (db.TrattamentoS)q2.getSingleResult();
         
         
        Query q = entityManager.createQuery("SELECT t FROM ParametridiprogettoS t WHERE  t.idTrattamento=?2 AND t.idScenario =?1");
        q.setParameter(1,sce );
        q.setParameter(2, tratta);
        
        
      return  parametridiprogetto = (List<db.ParametridiprogettoS>)q.getResultList();
        
               
  }*/
  
  
  
  /**
   * cerca il parametro nella lista dei parametri di progetto che verifica nome e contenuto attributo
   * @param nome del parametro di progetto presente nella tabella nomeparametrdiprogetto
   * @param contenutoattributo il valore numerico che conttrastingue il tipo di entitaattributo a cui è collegato il nome
   * del parametro
   * @return una istanza di parametridiprogetto
   */
 /* private db.ParametridiprogettoS getParametrodiprogetto(String nome, String contenutoattributo)
  {
      
      System.out.println(this.getClass().getCanonicalName() + " nome " + nome + " contenutoatributo " + contenutoattributo);
      
      
      
      ListIterator<db.ParametridiprogettoS> iterparametri = parametridiprogetto.listIterator();
      db.ParametridiprogettoS temp = null;
      
      if (contenutoattributo == null) {
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) ) {
                  break;
              }
          }
      } else {
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) && temp.getContenutoattributo().equals(contenutoattributo)) {
                  break;
              }
          }
      }
      
      return temp;
  }*/
  
  
  public void calcolaRefluo() {
       /**
        * dato che il trattamento non fa calcoli per il momento copio l'inout nell'outpput
        */
         //this.setContenitoreRefluiOut(this.getContenitoreRefluiIn());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
        classeparametri = new Parametridiprogetto("provagiorgio13",7,dettCuaa.getIdscenario());
      
      /**
       * recupero la lista dei parametri diprogetto
       */
      this.parametridiprogetto = classeparametri.getlistaparametri();
      
      
        //this.costocomplessivo = 0;
        
        //this.costomanutenzione = 0;
        this.stoccaggidarealizzare = 0;
        this.stoccaggipresenti = 0;
        this.stoccaggirichiesti = 0;
         this.capacita = 0;
          this.investimento = 0;
        this.esercizio = 0;
        this.costototale = 0;
       
         
         /**
          * recupero il volume totale di letame dal contenitore reflui
          */
         Refluo totale = this.getContenitoreRefluiIn().totale("Letame");
         
         
          /**
          * imposta la capacita totale per i palabili dalle superfici di stoccaggio,
          * i coefficienti medi di fnh3,fn20,fch4,fsv,fnmin, la durata in giorni ed i coefficienti
          * di durata
          */
        this.datiDibase(totale.getMetricubi());
         
        /**
         * suddivido la quantita d'acqua calcolata sulla refluo .
         * DAto che uso gli stessi metodi tra vasca e platea mantengo ilmetodo aggiungiAcqua
         * usandolo pero con il parametro acqua uguale a zero cosi da scrivere nel contenitore di output
         * i volumi presenti nel contenitore di input
         */
        aggiungiAcqua(0,totale);
        
          /**
         * calcola caratteristiche di output
         */
        
        caratteristicheOutput();
        
        
        
         /**
         * imposto i valori per i dati di output che sono
         * gli stoccaggirichiesti,darealizzare e presenti
         * con i due metodi sotto stanti
         */
        
        this.stoccaggioRichiesto();
        
        this.impostaStoccaggi();
        
        
        /*  System.out.println("----------STAMPA CONTENUTO PLATEA IN CALCOLA REFLUO ---------------");
        this.getContenitoreRefluiOut().stampaContenuto();
           System.out.println("----------STAMPA CONTENUTO PLATEA IN CALCOLA REFLUO ---------------");
        */
    }

   
    public void calcolaEmissioni() {
       
    }

   
    public void calcolaGestionali() {
        
    }
    
    
   /**
     * calcola il costo di investimento e di manutenzione
     */
    private void impostaGestionali(db.StoccaggioI stocco )
    {
        Double tempCosto ;
        double capacita1 = 0;
        db.TipostoccaggioS menu = null;
        db.ParametridiprogettoS vmax = null;
        double vmax1 = 0;
        db.ParametridiprogettoS vmin = null;
        double vmin1 = 0;
        double a = 0;
        double b = 0;
        double coefficientedimanutenzione = 0;
        /**
         * recupero il menu stoccaggi relativo al tipo di stoccaggio inserito 
         * nella tabella inputstoccaggis
         */
       //  menu = stocco.getIdstoccaggio();
        /**
         * recupero la capacità dello stoccaggio 
         */
         capacita1 = stocco.getCapacita();
        
        /**
         * verifico le dimensioni della capacita dello stoccaggio
         * 
         */
        if(capacita1 > 0)
        {
           /* if(capacita1 > menu.getVmax()) {
                capacita1 = menu.getVmax();
            }
            
            if(capacita1 < menu.getVmin()) {
                capacita1 = menu.getVmin();
            }*/
            
             vmax = classeparametri.getParametrodiprogetto(1,"vmax", menu.getIdstoccaggio().toString(),null,null);
            vmax1 = Double.parseDouble(vmax.getValore());
            
            vmin = classeparametri.getParametrodiprogetto(1,"vmin", menu.getIdstoccaggio().toString(),null,null);
            vmin1 = Double.parseDouble(vmin.getValore());
            
            if(capacita1 > vmax1)
            {
                capacita1 = vmax1;
            }
            
            if(capacita1 < vmin1)
            {
                capacita1 = vmin1;
            }
            
          /**
           * uso tempCosto per calcolare il gradino del costo con il passaggio
           * precedente
           */  
          tempCosto = new Double(this.investimento);
            
           a = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"a", menu.getIdstoccaggio().toString(),null,null).getValore());
          b = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"b",menu.getIdstoccaggio().toString(),null,null).getValore());
          coefficientedimanutenzione = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"coefficiente_di_manutenzione_annua",menu.getIdstoccaggio().toString(),null,null).getValore());
           
          
          
            this.investimento = this.investimento + a *  Math.pow(capacita1, b) * stocco.getCapacita() ;
            
            this.esercizio = this.esercizio + (this.investimento - tempCosto) * coefficientedimanutenzione;
            
            
            System.out.println("sono in platea in impostagestionali  capacita " + capacita1 + " A " + a + " B " + b + " costocomplessivo  " + this.investimento + " manutenzione " + this.esercizio);
            
            
        }
    }
    
    
    /**
     * imposta la capacità totale della  capacià delle strutture dedicate alla platea 
     * e quindi ai palabili ed imposta dato che ha gia a disposizione i dati dalla query
     * i coefficienti medi fnh3,fn2o,fch4,fsv,fnmin.Imposto la durata e i coefficienti di durata
     * come richiesto dal documento modulo stoccaggi 
     * @return 
     */
   
    private void datiDibase(double volume)
    {
        
       capacita = 0;
        
        DettaglioCuaa dettCuaa = new DettaglioCuaa();
        
         
        entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio13");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        
        /**
         * recupero gli stoccaggi di una particolare azienda infunzione del suo cuaa
         */
       /* Query q = entityManager.createNamedQuery("Aziendei.findByCuaa").setParameter("cuaa", dettCuaa.getCuaa());
      
              
       
        db.Aziendei result = (db.Aziendei)q.getResultList().get(0);*/
        
        Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
        db.ScenarioI result =(db.ScenarioI) q.getSingleResult();
        
        List<db.StoccaggioI> stoccaggi = (List<db.StoccaggioI>) result.getStoccaggioICollection();
        
        ListIterator<db.StoccaggioI> iterStoccaggi = stoccaggi.listIterator();
        
        /**
         * di tutti gli stoccaggi di un azienda 
         * prendo quelli che hanno forma solida cioè queli 
         * riferitia alla platea
         */
        while(iterStoccaggi.hasNext())
        {
            db.StoccaggioI itero = iterStoccaggi.next();
            
          /*  if(itero.getIdstoccaggio().getForma().getTipo().equals("palabili"))
            {
               capacita +=  itero.getCapacita();
                impostaGestionali(itero); 
               
            }*/
            
            
        }
        
        
        /**
         * calcolo i coefficienti medi delle strutture per i palabili
         * prima li inizializzo a zero per sommarli a queli precedenti
         */
        this.fch4medio = 0;
        this.fn2omedio = 0;
        this.fnh3medio = 0;
        this.fnminmedio = 0;
        this.fsvmedio = 0;
        iterStoccaggi = stoccaggi.listIterator();
        
        /**
         * recupero gli stoccaggi palabili e cacolo i coefficienti medi 
         * come previsto da formulo del documento modulo stoccaggi 
         */
         while(iterStoccaggi.hasNext())
        {
            db.StoccaggioI itero = iterStoccaggi.next();
            
         /*   if(itero.getIdstoccaggio().getForma().equals("palabili"))
            {
              
                this.fnh3medio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fnh3", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fn2omedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fn2o", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fch4medio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fch4", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fsvmedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fsv", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fnminmedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fnmin", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
               
               
            }*/
        }
        
        
        
        
      this.durata = (Double.parseDouble((classeparametri.getParametrodiprogetto(0,"giorni_anno",null, null,null).getValore())) * volume) / capacita;
      
      this.coeff_durata_p = this.durata / Double.parseDouble((classeparametri.getParametrodiprogetto(0,"giorni_previsti",null, null,null).getValore()));
      /**
       * quest'ultimo coefficiente deve essere compreso tra 0 e 1 
       * se è maggiore di 1 lo si considera comunque 1
       */
      if(this.coeff_durata_p > 1) {
            this.coeff_durata_p = 1;
        }
      
      
       entityManager.close();
      
      entityManagerFactory.close();
    }
    
    /**
    * per ogni tipologia di refluo liquido aggiungo in proporzione la quantita
    * d'acqua calcolata nel metodo acquaInAggiunta.
    * Nel caso della platea il volume in uscita del palabile rimane costante 
    * per cui questo metodo viene chiamato con acqua uguale a zero
    * @param acqua toale acqua calcolata in funzione delle strutture scoperte 
    */
   private void aggiungiAcqua(double acqua,Refluo totale)
   {
      for(String t : this.getContenitoreRefluiIn().getTipologie())
      {
          /**
           * ciclo sulle diverse tipologie di relfuo prendeneod in considerazione
           * solo quelle che contengono liquame
           */
          if(t.contains("Letame"))
          {
              Refluo  re = this.getContenitoreRefluiIn().getTipologia(t);
              
              /**
               * metto nel contenitore refluo out la somma del refluo  piu la quantita di acqua
               * raccolta dalle superfici scoperte suddivisa in modo proporzionale alla quantita di refluo della tipolgia
               */
              if(re.getMetricubi() != 0)
              {
                  Refluo reout = this.getContenitoreRefluiOut().getTipologia(t);
                  
                  reout.setMetricubi(re.getMetricubi() + re.getMetricubi() * (acqua / totale.getMetricubi()));
                  
              }
          }
      }
   }
    /**
     * calcola le caratteristiche di output del refluo in input solo però per il
     * letame visto che si tratta della platea le formule applicate fanno
     * riferimento al documento modulo stoccaggio
     * TANmin = (TAN in + (TKN in – TAN in)* f Nmin * coeff_durata / 100 
     * Le emissioni di ammoniaca vengono
     * conteggiate sulla base dell’azoto ammoniacale e della durata:
     * NH3 (kg N)=  TANmin * f NH3 * coeff_durata / 100 
     * Le emissioni di protossido di azoto
     * sono funzione del TKN in entrata e della durata: 
     * N2O (kg N) = TKN in * fN2O * coeff_durata / 100 
     * I valori di azoto totale e minerale in uscita
     * saranno: 
     * TKNout = TKNin – NH3 – N2O 
     * TANout = TAN min – e NH3 
     * Il fosforo e il potassio si assumono costanti, non subendo trasformazioni importanti.
     * TPout=TPin
     * TKout=TKin
     * La sostanza secca e i solidi volatili vengono
     * degradati in funzione della durata: DMout=DMin - SVin * fSV *
     * coeff_durata / 100 SVout=SVin- SVin * fSV * coeff_durata / 100
     *
     */
    private void caratteristicheOutput()
    {
        this.ch4 = 0;
        this.n2o = 0;
        this.nh3 = 0; 
     
       double basech4 = 0;
       /**
        * di seguito i coefficienti metano di appoggio da 0 a 4 sono riferiti
        * a base metano,bovini,suini,avicoli,altri
        */
       double c0 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_base_metano",null, null,null).getValore()); 
       double c1 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_metano_bovini",null, null,null).getValore());
       double c2 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_metano_suini",null, null,null).getValore());
       double c3 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_metano_avicoli",null, null,null).getValore());
       double c4 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_metano_altri",null, null,null).getValore());
       double c5 = Double.parseDouble(classeparametri.getParametrodiprogetto(0,"coefficiente_metano_biomasse",null, null,null).getValore());
      // double c = 0;
        
        for(String t: this.getContenitoreRefluiIn().getTipologie())
        {
            if(t.contains("Letame"))
                    {
                        Refluo rein = this.getContenitoreRefluiIn().getTipologia(t);
                        
                        Refluo reout = this.getContenitoreRefluiOut().getTipologia(t);
                        /**
                         * controllo che la tipologia di letame abbia una massa ovvero i suoi metri cubi non siano 
                         * uguali a zero 
                         */
                        if(rein.getMetricubi() > 0)
                        {
                            
                        double tanmin =rein.getAzotoammoniacale() +( rein.getAzotototale()-rein.getAzotoammoniacale()) * this.fnminmedio * this.coeff_durata_p / 100;
                        
                        this.nh3 += (tanmin * this.fnh3medio * this.coeff_durata_p ) / 100 ;
                        
                        this.n2o += (rein.getAzotototale() * this.fn2omedio * this.coeff_durata_p) / 100 ;
                        
                        reout.setAzotototale( rein.getAzotototale() - this.nh3 - this.n2o);
                        
                        reout.setAzotoammoniacale(tanmin - this.nh3);
                        
                        reout.setPotassiototale(rein.getPotassiototale());
                        
                        reout.setFosforototale(rein.getFosforototale());
                        
                        reout.setSostanzasecca(rein.getSostanzasecca() - (rein.getSolidivolatili() * this.fsvmedio * this.coeff_durata_p) / 100);
                        
                        reout.setSolidivolatili(rein.getSolidivolatili() - (rein.getSolidivolatili()*this.fsvmedio * this.coeff_durata_p) / 100 );
                        
                        basech4 = (rein.getSolidivolatili() * c0 * this.fch4medio * this.coeff_durata_p ) / 100;
                        
                        switch(t)
                        {
                            case "Letame Bovino":
                                
                                 this.ch4 += basech4 * c1;
                                break;
                            case "Letame Suino":
                               
                                 this.ch4 += basech4 * c2;
                                break;
                            case "Letame Avicolo":
                                 this.ch4 += basech4 * c3;
                                
                                break;
                            case "Letame Altro":
                                 this.ch4 += basech4 * c4;
                                
                                break;
                            case "Letame Biomassa":
                                 this.ch4 += basech4 * c5;
                                
                                break;
                        }
                        
                       // this.ch4 += basech4 * c;
                        }//close if metri cubi > 0
                        
                    }
        }
    }

    
    /**
     * sulla base della capacita richesta dal refluo in output 
     * e dalla capacità delle strutture defininte negli stoccaggi
     * vengono calcolati i dati di output per gli stoccaggi ovvero 
     * stoccaggirichiesti,stoccaggipreenti,stoccaggidarealizzare
     */
    private void impostaStoccaggi()
    {
        this.stoccaggipresenti = this.capacita;
        
        if(this.stoccaggirichiesti > this.stoccaggipresenti) {
            this.stoccaggidarealizzare = this.stoccaggirichiesti - this.stoccaggipresenti;
        }
    }
    
    /**
     * calcola la capacità totale degli stoccaggi per tutti i liquami 
     * intesa come somma delle capacità delle tipologie diliquami in output in funzione dei giorni
     * richiesti.
     * @return 
     */
    private void stoccaggioRichiesto()
    {
        //double richiestatotale = 0;
        /**
         * per i palabili i giorni sono sempre 90
         */
        int giorni = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_palabili",null, null,null).getValore());;
        int giornianno = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_anno",null, null,null).getValore());
        
        for(String t:this.getContenitoreRefluiOut().getTipologie())
        {
            if(t.contains("Letame"))
            {
                Refluo re = this.getContenitoreRefluiOut().getTipologia(t);
                 
                  if(re.getMetricubi() > 0) {  
               this.stoccaggirichiesti += (re.getMetricubi() * giorni ) / 365;
                  }
            }
        }
        
        
    }
    
    
    /**
     * @return the stoccaggirichiesti
     */
    public double getStoccaggirichiesti() {
        return stoccaggirichiesti;
    }

    /**
     * @param stoccaggirichiesti the stoccaggirichiesti to set
     */
    public void setStoccaggirichiesti(double stoccaggirichiesti) {
        this.stoccaggirichiesti = stoccaggirichiesti;
    }

    /**
     * @return the stoccaggipresenti
     */
    public double getStoccaggipresenti() {
        return stoccaggipresenti;
    }

    /**
     * @param stoccaggipresenti the stoccaggipresenti to set
     */
    public void setStoccaggipresenti(double stoccaggipresenti) {
        this.stoccaggipresenti = stoccaggipresenti;
    }

    /**
     * @return the stoccaggidarealizzare
     */
    public double getStoccaggidarealizzare() {
        return stoccaggidarealizzare;
    }

    /**
     * @param stoccaggidarealizzare the stoccaggidarealizzare to set
     */
    public void setStoccaggidarealizzare(double stoccaggidarealizzare) {
        this.stoccaggidarealizzare = stoccaggidarealizzare;
    }

    /**
     * @return the costocomplessivo
     */
   /* public double getCostocomplessivo() {
        return costocomplessivo;
    }*/

    /**
     * @param costocomplessivo the costocomplessivo to set
     */
    /*public void setCostocomplessivo(double costocomplessivo) {
        this.costocomplessivo = costocomplessivo;
    }*/

    /**
     * @return the costomanutenzione
     */
    /*public double getCostomanutenzione() {
        return costomanutenzione;
    }*/

    /**
     * @param costomanutenzione the costomanutenzione to set
     */
    /*public void setCostomanutenzione(double costomanutenzione) {
        this.costomanutenzione = costomanutenzione;
    }*/

    
  
}
