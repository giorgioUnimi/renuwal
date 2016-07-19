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
public class Vasca extends Trattamento{
    
  private static Vasca istanza;
  
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
    * coefficiente di durata per i liquami
    */
   private double coeff_durata_l = 0;
 
   /**
    * superficie totale scoperta
    * utile solo per i liquidi per calcolare l'apporto aggiunto 
    * di acqua nei metri cubi di liquame 
    */
   private double scopertoTotale = 0;
   /**
    * piovosita media
    */
   private double pioggiaMedia = 0;
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
  // private double costocomplessivo = 0;sostituito da Trattamento.investimento
 //  private double costomanutenzione = 0;sostituito da Trattamento.esercizio
   /**
    * 
    * OUTPUT GESTIONALI
    */
  private Vasca(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
   // super(totlet,totliq);
      //parametridiprogetto = new LinkedList<db.ParametridiprogettoS>();
  }

  public static Vasca getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Vasca(/*totlet,totliq*/);
    }

    return istanza;
  }
  
  
  /**
   * recupera tutti i parametri di progetto per uno specifico scenario e per uno
   * specifico trattamento
   */
 /* private List<db.ParametridiprogettoS> getlistaparametri()
  {
      entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio13");
      entityManager = entityManagerFactory.createEntityManager();
      jpa = (JpaEntityManager) entityManager.getDelegate();
      
      /**
         * recupero il session bean dettaGlioCuaa
         */
    /*     ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         
         Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
         db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
         
         Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",6);
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
       * recupero l'id dello scenario dal session bean DettaglioCuaa
       */  
      ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
      /**
       * recupero la lista dei parametri diprogetto
       */
     classeparametri = new Parametridiprogetto("provagiorgio13",6,dettCuaa.getIdscenario());
      this.parametridiprogetto = classeparametri.getlistaparametri();
      
      
      // this.setContenitoreRefluiOut(this.getContenitoreRefluiIn());
         
      //     this.costocomplessivo = 0;
        
     //   this.costomanutenzione = 0;
        this.stoccaggidarealizzare = 0;
        this.stoccaggipresenti = 0;
        this.stoccaggirichiesti = 0;
        this.capacita = 0;
        this.scopertoTotale = 0;
        this.investimento = 0;
        this.esercizio = 0;
        this.costototale = 0;
          /**
          * recupero il volume totale di letame dal contenitore reflui
          */
         Refluo totale = this.getContenitoreRefluiIn().totale("Liquame");
         
         
          /**
          * imposta la capacita totale per i palabili dalle superfici di stoccaggio,
          * i coefficienti medi di fnh3,fn20,fch4,fsv,fnmin, la durata in giorni ed i coefficienti
          * di durata
          */
        this.datiDibase(totale.getMetricubi());
        
        /**
         * mi restituisce l'acqua totale dovuta alle superficie scoperte 
         * ed alla piovosita media
         */
        double acq = acquaInAggiunta();
        
        /**
         * suddivido la quantita d'acqua calcolata sulla refluo 
         */
        aggiungiAcqua(acq,totale);
        
        
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
        Double tempCosto;
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
        menu = stocco.getIdstoccaggio();
        capacita1 = stocco.getCapacita();
        
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
            
          tempCosto = new Double(this.investimento);
            
          a = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"a", menu.getIdstoccaggio().toString(),null,null).getValore());
          b = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"b",menu.getIdstoccaggio().toString(),null,null).getValore());
          coefficientedimanutenzione = Double.parseDouble(classeparametri.getParametrodiprogetto(1,"coefficiente_di_manutenzione_annua",menu.getIdstoccaggio().toString(),null,null).getValore());
            this.investimento =  this.investimento + a * Math.pow(capacita1, b) * stocco.getCapacita() ;
            
            this.esercizio = this.esercizio + (this.investimento - tempCosto) * coefficientedimanutenzione;
            
            
           System.out.println("son in vasca impostagestionali descrizione  "+menu.getDescrizione()+" forma   "+menu.getForma()+"  A " + a + " capacita1 " + capacita1 + " B " + b + " costocomplessivo  " + this.investimento + " manutenzione " + this.esercizio);

        }
    }
    
    /**
     * imposta la capacità totale della  capacià delle strutture dedicate alla platea 
     * e quindi ai palabili ed imposta dato che ha gia a disposizione i dati dalla query
     * i coefficienti medi fnh3,fn2o,fch4,fsv,fnmin.Imposto la durata e i coefficienti di durata
     * come richiesto dal documento modulo stoccaggi 
     * @return 
     */
    //private double getCapacitaTotale()
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
        //Query q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", dettCuaa.getCuaa());
        
      Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", dettCuaa.getIdscenario());
      db.ScenarioI result = (db.ScenarioI)q.getResultList().get(0);
        
        //imposto la piovosita media della zona in funzione
        //del cuaa aziendale con cui ho trovato l'azienda
        //e dall'azienda recupero da acque_stoccaggi la piovosita media
        this.pioggiaMedia = result.getAcquastoccaggioI().getPioggia();
        
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
            
            if(itero.getIdstoccaggio().getForma().getTipo().equals("liquidi"))
            {
               capacita +=  itero.getCapacita();
              
               /**
                * imposta i costi
                */
                impostaGestionali(itero);
            }
            
             this.scopertoTotale += itero.getSuperficiescoperta();
             
            
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
            
            if(itero.getIdstoccaggio().getForma().getId().equals("liquidi"))
            {
              
                this.fnh3medio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fnh3", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fn2omedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fn2o", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fch4medio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fch4", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fsvmedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fsv", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
                this.fnminmedio += (itero.getCapacita() * Double.parseDouble((classeparametri.getParametrodiprogetto(1,"fnmin", itero.getIdstoccaggio().toString(),null,null).getValore()))) / capacita;
               
            }
        }
        
        
       // System.out.println("capacita totale dei palabili ------------   " + capacita);
        
        
      this.durata = (Double.parseDouble((classeparametri.getParametrodiprogetto(0,"giorni_anno", null,null,null).getValore())) * volume) / capacita;
      
      this.coeff_durata_l = this.durata / Double.parseDouble((classeparametri.getParametrodiprogetto(0,"giorni_previsti",null, null,null).getValore()));
      /**
       * quest'ultimo coefficiente deve essere compreso tra 0 e 1 
       * se è maggiore di 1 lo si considera comunque 1
       */
      if(this.coeff_durata_l > 1) {
            this.coeff_durata_l = 1;
        }
      
      entityManager.close();
      
      entityManagerFactory.close();
      
      
    }
    
    /**
     * calcola la quantita d'acqua dovuta alle precipitazioni
     * raccolte dalle superfici scoperte in funzione della piovosita media della zona
     * ed alla somma e delle superifici scoperte. La piovosita media è recuperata nel metodo datiDiBase.
     * La superficie scoperta totale è recuperata/calcolata nel metodo datiDiBase
     * @return 
     */
   private double acquaInAggiunta()
   {
       
       
       return (this.pioggiaMedia * this.scopertoTotale ) / 1000;
   }
   
   
   /**
    * per ogni tipologia di refluo liquido aggiungo in proporzione la quantita
    * d'acqua calcolata nel metodo acquaInAggiunta
    * @param acqua toale acqua calcolata in funzione delle strutture scoperte 
    */
   private void aggiungiAcqua(double acqua,Refluo totale)
   {
       
       
      // System.out.println("sono in agggiungiAcqua di Vasca totale m3 " + totale.getMetricubi() + "  acqua " + acqua + " pioggia " + this.pioggiaMedia + " scoperto  " + this.scopertoTotale);
       
      for(String t : this.getContenitoreRefluiIn().getTipologie())
      {
          /**
           * ciclo sulle diverse tipologie di relfuo prendeneod in considerazione
           * solo quelle che contengono liquame
           */
          if(t.contains("Liquame"))
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
     * TANmin = (TAN in + (TKN in – TAN in)* f Nmin * coeff_durata / 100 Le emissioni di ammoniaca vengono
     * conteggiate sulla base dell’azoto ammoniacale e della durata: NH3 (kg N)=
     * TANmin * f NH3 * coeff_durata / 100 Le emissioni di protossido di azoto
     * sono funzione del TKN in entrata e della durata: N2O (kg N) = TKN in * f
     * N2O * coeff_durata / 100 I valori di azoto totale e minerale in uscita
     * saranno: TKNout = TKNin – NH3 – N2O TANout = TAN min – e NH3 Il fosforo e
     * il potassio si assumono costanti, non subendo trasformazioni importanti.
     * TPout=TPin TKout=TKin La sostanza secca e i solidi volatili vengono
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
       
        for(String t: this.getContenitoreRefluiIn().getTipologie())
        {
            if(t.contains("Liquame"))
                    {
                        Refluo rein = this.getContenitoreRefluiIn().getTipologia(t);
                        Refluo reout = this.getContenitoreRefluiOut().getTipologia(t);
                        
                        /**
                         * controllo che la tipologia di letame abbia una massa ovvero i suoi metri cubi non siano 
                         * uguali a zero 
                         */
                        if(rein.getMetricubi() > 0)
                        {
                        
                        double tanmin =rein.getAzotoammoniacale() +( rein.getAzotototale()-rein.getAzotoammoniacale()) * this.fnminmedio * this.coeff_durata_l / 100;
                        
                        this.nh3 += (tanmin * this.fnh3medio * this.coeff_durata_l ) / 100 ;
                        
                        this.n2o += (rein.getAzotototale() * this.fn2omedio * this.coeff_durata_l) / 100 ;
                        
                        
                        //System.out.println("da caratteristicheoutput in vasca nh3 " + this.nh3 + " n20 " + this.n2o + " fch4medio " + this.fch4medio + " fn2omedio  " + this.fn2omedio + " fnh3medio " + this.fnh3medio + " fnmimedio  " + this.fnminmedio + " fsvmedio  " + this.fsvmedio + " tanmin " + tanmin + " coefff durata " + this.coeff_durata_l);
                        
                        reout.setAzotototale( rein.getAzotototale() - this.nh3 - this.n2o);
                        
                        reout.setAzotoammoniacale(tanmin - this.nh3);
                        
                        reout.setPotassiototale(rein.getPotassiototale());
                        
                        reout.setFosforototale(rein.getFosforototale());
                        
                        reout.setSostanzasecca(rein.getSostanzasecca() - (rein.getSolidivolatili() * this.fsvmedio * this.coeff_durata_l) / 100);
                        
                        reout.setSolidivolatili(rein.getSolidivolatili() - (rein.getSolidivolatili()*this.fsvmedio * this.coeff_durata_l) / 100 );
                        
                        
                         basech4 = (rein.getSolidivolatili() * c0 * this.fch4medio * this.coeff_durata_l ) / 100;
                         
                         
                        // System.out.println("vasca carattersticheoutput basech4 " + basech4 + " fch4medio " + this.fch4medio + " coeff_durata " + this.coeff_durata_l);
                        
                        switch(t)
                        {
                            case "Liquame Bovino":
                                //c1 = 0.24;
                                 this.ch4 += basech4 * c1;
                                break;
                            case "Liquame Suino":
                                //c2 = 0.45;
                                 this.ch4 += basech4 * c2;
                                break;
                            case "Liquame Avicolo":
                                //c3 = 0.32;
                                 this.ch4 += basech4 * c3;
                                break;
                            case "Liquame Altro":
                                //c4 = 0.19;
                                 this.ch4 += basech4 * c4;
                                break;
                            case "Liquame Biomassa":
                                //c5 = 0.45;
                                 this.ch4 += basech4 * c5;
                                break;
                        }
                        
                       
                        
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
        int giorni = 0;
        int giornibovino = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_liquame_bovino",null, null,null).getValore());
        int giornisuino = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_liquame_suino",null, null,null).getValore());
        int giorniavicolo = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_liquame_avicolo",null, null,null).getValore());
        int giornialtro = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_liquame_altro",null, null,null).getValore());
        int giornibiomassa = Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_stoccaggio_liquame_biomasse",null, null,null).getValore());
        int giornianno =Integer.parseInt(classeparametri.getParametrodiprogetto(0,"giorni_anno",null, null,null).getValore());
        for(String t:this.getContenitoreRefluiOut().getTipologie())
        {
            if(t.contains("Liquame"))
            {
                Refluo re = this.getContenitoreRefluiOut().getTipologia(t);
                  switch(t)
                        {
                            case "Liquame Bovino":
                                giorni = giornibovino;
                                break;
                            case "Liquame Suino":
                                giorni = giornisuino;
                                break;
                            case "Liquame Avicolo":
                                giorni = giorniavicolo;
                                break;
                            case "Liquame Altro":
                                giorni = giornialtro;
                                break;
                            case "Liquame Biomassa":
                                giorni = giornibiomassa;
                                break;
                        }
                  if(re.getMetricubi() > 0) {
                    this.stoccaggirichiesti += (re.getMetricubi() * giorni ) / giornianno;
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
   /* public void setCostocomplessivo(double costocomplessivo) {
        this.costocomplessivo = costocomplessivo;
    }*/

    /**
     * @return the costomanutenzione
     */
   /* public double getCostomanutenzione() {
        return costomanutenzione;
    }*/

    /**
     * @param costomanutenzione the costomanutenzione to set
     */
    /*public void setCostomanutenzione(double costomanutenzione) {
        this.costomanutenzione = costomanutenzione;
    }*/
   
  
}
