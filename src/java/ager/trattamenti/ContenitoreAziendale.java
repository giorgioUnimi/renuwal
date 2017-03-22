/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.TipiReflui;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *Si occupa di recuperare i dati aziedanli di un 'azienda ovvero allevamenti con le loro caratterstiche di staluazione
 * e delle acque di stoccaggio. Verra usato dai trattamenti LetameT e LiquameT cosi venga fatta una sola query
 * per entrambe i trattamenti.
 * @author giorgio
 */
public class ContenitoreAziendale {
    /**
     * istanza statica della classe usata per implementare 
     * il pattern singleton
     */
    private static ContenitoreAziendale istanza;
    /**
     * la variabile cuaa interna sara lo strumento per capire
     * se i dati aziendali sono quelli correnti oppure no e quindi è necessario rifare la query
     */
    private String cuaa;
    
    private int id = 0;
    
    /**
     * le quatro varibili seguenti mi servono per interroegare il database aziendale
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    /**
     * la seguente hashtable mi serve per fare il mapping tra le specie definite nel db e quelle deifnite nel contenitore reflui
     */
    private Hashtable mappingspecie = new Hashtable();
    
    
    
    /**
     * contenitore dei risultati della trasformazioe aziendale 
     * il contentitore verra passato al LetameT e LiquameT
     */
  private ContenitoreReflui contenitore = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
    
  
   /**
   * sono stati inseriti secondo il documento word 
   * "descrizione e e struttura azienda fino a stoccaggio-2"
   */
  private double azotoalcampoliquame = 0;
  private double azotoalcampoletame = 0;
  private double azotoalcampototale = 0;
  
  /**
   * variabili di reiferimento per lo stoccaggio dell'intera azienda
   */
  private double acquameteo = 0;
  private double acquatot = 0;
  
  
  
  /**
   * costruttore privato della classe usato per implementare il singleton
   * @param cuaa 
   */
  private ContenitoreAziendale(String cuaa,int id)
  {
     this.cuaa = cuaa;
     inizializzaSpecie();
  }

  public static ContenitoreAziendale getInstance(String cuaa,int id)
  {
   // if (istanza == null)
    {
      istanza = new ContenitoreAziendale(cuaa,id);
    }

    return istanza;
  }

    /**
     * @return the cuaa
     */
    public String getCuaa() {
        return cuaa;
    }

    /**
     * @param cuaa the cuaa to set
     */
    public void setCuaa(String cuaa) {
        this.cuaa = cuaa;
    }
    
    /**
     * 
     * @return il record della stabulazione in funzione della tipologia di allevamento
     * definito da des_allevamento,des_specie,des_cat_allev,des_stabulazione
     */
    /*private db.Stabulaziones getStabulazione(String allevamento,String specie,String categoria,String stabulazione)
    {
        Query q = entityManager.createQuery("SELECT l FROM Stabulaziones l WHERE l.desSpecie=?1 AND l.desCatAllev=?2 AND l.desAllevamento=?3 AND l.desStabulazione=?4");
        q.setParameter(1, specie);
        q.setParameter(2, categoria);
        q.setParameter(3, allevamento);
        q.setParameter(4, stabulazione);
       List<db.Stabulaziones> stabula =  q.getResultList();
       
       if(stabula.size() != 0)
            return stabula.get(0);
       else
           return null;

    }*/
    
    
    /**
     * apro la connessione il db postgresql
     * @param connessione "renuwal2"
     */
    /*public void apri(String connessione)
    {
        
        if (entityManager == null)  {
            entityManagerFactory = Persistence.createEntityManagerFactory(connessione);
            entityManager = entityManagerFactory.createEntityManager();
            jpa = (JpaEntityManager) entityManager.getDelegate();
            serverSession = jpa.getServerSession();
        }
    }*/
    
    /**
     * chiuso la connesione con il db postgresql
     */
    /*public void chiudi()
    {
        if(entityManager.isOpen())
        {
            entityManager.close();
            entityManagerFactory.close();
        }
    }*/
    
    
    
    /**
     * recupero i dati che mi servono per popolare i contenitori di refluo 
     * di LetameT e LiquameT. Recupero dal db aziendale la lista degli allevamenti dell'azienda con cuaa
     * passato come paramentro e di ogni allevamento i dati di stabulazione.
     */
    public void getData(int id)
    {
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();*/
        /**
         * se l'entityManager è chiuso aprilo uso questo sistema per
         * mantenere una sola connessione aperta e non avere il falso errore
         * di eclipselink class cast exception
         */
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal2");
              entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
        
         
        entityManagerFactory.getCache().evictAll();
         
         
        this.contenitore = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
        
        //DettaglioCuaa detto = new DettaglioCuaa();
        
          ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        DettaglioCuaa detto = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
        boolean utenteospite = false;
       /* if(!detto.getUtente().equals("azienda1"))
        {
            utenteospite = true;
        }*/
        /**
         * recupero l'azienda che ha un deteminato cuaa
         */
         Query q = null;
       /* if(!utenteospite)
        {
          q = entityManager.createNamedQuery("AziendeI.findByCuaa") .setParameter("cuaa", cuaa);
        }
        else
        {
           q = entityManager.createNamedQuery("AziendeI.findByCuaaFinto") .setParameter("cuaaFinto", cuaa); 
        }*/
      
        
       // System.out.println(" utenteospite " + utenteospite + " cuaa " + cuaa);
        
        q = entityManager.createNamedQuery("ScenarioI.findByIdscenario") .setParameter("idscenario", detto.getIdscenario());
        if(q.getResultList().isEmpty())
            return;
        db.ScenarioI scena = (db.ScenarioI)q.getResultList().get(0);
        
        
       /* List<db.ScenarioI> scenari =(List<db.ScenarioI>) results.get(0).getAziendeAnniCollection().iterator().next().getScenarioICollection();
        ListIterator<db.ScenarioI> iterscenari = scenari.listIterator();
        
        boolean trovato = false;
        db.ScenarioI scena = null;
        while(iterscenari.hasNext())
        {
            scena = iterscenari.next();
            
            
            if(scena.getIdscenario() == id)
            {
                trovato = true;
                break;
            }
        }
        
        /**
         * se non lo trovo esco
         */
       /* if(!trovato) {
      //        System.out.println(this.getClass().getCanonicalName() + "non ho  trovato id " + id + " scena.getId() " +scena.getId() );
            return;
        }*/
        
        
        /**
         * recupero la lista degli allevamenti di una determanata azienda
         */
        //List<db.Allevamentoi> allevamentiCo =(List) results.get(0).getScenarioICollection()..getAllevamentoiCollection();
        List<db.AllevamentoI> allevamentiCo =(List<db.AllevamentoI>) scena.getAllevamentoICollection();
        
        /**
         * salvo l'idscenario in dettagliocuaa cosi da usarlo anche nelle altre classi senza doer fare ulteriori query
         */
        //DettaglioCuaa detto = new DettaglioCuaa();
        detto.setIdscenario(scena.getIdscenario());
        
        for(db.AllevamentoI allevamento:allevamentiCo)
        {
            System.out.println("numero capi" + allevamento.getNumCapiSpecieStab() + "specie " + allevamento.getSpeciecategoriaallevamentostabulazionebId().getSpeciebSCodSpecie().getDesSpecie());
        }
        /**
         * recupero l'acqua di stoccaggio dell'azienda con cuaa passato come parametro
         */
        //db..AcqueStoccaggii acqueSto = results.get(0).getAcqueStoccaggii();
        db.AcquastoccaggioI acqueSto = scena.getAcquastoccaggioI();
        
        //System.out.println("Acque impianti  " + acqueSto.getAcquaImpianti());
        
        /**
         * uso un iteratore per muovermi tra gli allevamenti
         */
        //ListIterator<db.Allevamentoi> iterAllevamenti = allevamentiCo.listIterator(); 
        ListIterator<db.AllevamentoI> iterAllevamenti = allevamentiCo.listIterator();
        /**
         * per ogni allevamento recupero i suoi dati in stabulazione e popolo il contenitore reflui
         */
        while(iterAllevamenti.hasNext())
        {
           // db.Allevamentoi alleva = iterAllevamenti.next();
           // db.Stabulaziones stabula = getStabulazione(alleva.getDesAllevamento(),alleva.getDesSpecie(),alleva.getDesCatAllev(),alleva.getDesStabulazione());
            
            db.AllevamentoI alleva = iterAllevamenti.next();
           db.SpeciecategoriaallevamentostabulazionebS stabula= alleva.getSpeciecategoriaallevamentostabulazionebId();
           
            if(stabula != null)
            {
               // popolaContenitore(alleva,stabula);
                popolaContenitoreA(alleva,stabula);
                
            }
            
        }       
       
            
            
          
        /**
         * modifica i totali in funzione dello stocccaggio dell'acqua
         */
        if(acqueSto != null) {
            //this.calcolaStoccaggiA(acqueSto,stabula,alleva);
            this.calcolaStoccaggiA(acqueSto);
        }
        
       
        
       /* entityManager.close();
        entityManagerFactory.close();*/
        
        
         //Connessione connessione = Connessione.getInstance();
         //connessione.chiudi();
        
    }
    
    /**
     * in funzione dei valori recuperati dalla query fatta sul db aziendale che ha 
     * rewcuperato l'azienda e di questa la lista degli allevamenti 
     * e di ogni allevamento i dati di stabulazine vado a rimepieri 
     * il contenitore dei reflui in funzione del documento di trasformazione 
     * @param alleva
     * @param stabula 
     */
   /* private void popolaContenitore(db.Allevamentoi alleva,db.Stabulaziones stabula)
    {
        String chiave =(String) mappingspecie.get(alleva.getDesSpecie());
        
    
        //peso vivo della categoria in tonnellate
        double peso = (alleva.getPesoVivo() * alleva.getNumCapiSpecieStab()) / 1000;
        
        Refluo liquido = this.contenitore.getTipologia("Liquame "+chiave);
        
        
        if(liquido == null)
        {
            System.out.println("non ho trovato alleva.specie " + alleva.getDesSpecie() + " chiave " + chiave );
            
             
        }else
        {
        
        
        if(stabula.getLiquame() != null) {
                liquido.addMetricubi(stabula.getLiquame() * peso);
                
            }
        else {
                liquido.addMetricubi(0);
            }
        
        
        if(stabula.getLiquameNEx() != null) {
                liquido.addAzotoTotale(stabula.getLiquameNEx() * peso);
            }
        else {
                liquido.addAzotoTotale(0);
            }

        if(stabula.getLiquameTanEx() != null) {
                liquido.addAzotoAmmoniacale(stabula.getLiquameTanEx()* peso);
            }
        else {
                liquido.addAzotoAmmoniacale(0);
            }
        
        if(stabula.getLiquameSt() != null) {
                liquido.addSostanzaSecca(stabula.getLiquameSt() * peso * 1000);
            }
        else {
                liquido.addSostanzaSecca(0);
            }
        
        if(stabula.getLiquameSt() != null) {
                liquido.addSolidiVolatili(stabula.getLiquameSv()* peso * 1000);
            }    
        else {
                liquido.addSolidiVolatili(0);
            }    

        if(stabula.getLiquameP205() != null) {
                liquido.addFosforoTotale(stabula.getLiquameP205()* peso );
            }
        else {
                liquido.addFosforoTotale(0);
            }

        if(stabula.getLiquameK20() != null) {
                liquido.addPotassioTotale(stabula.getLiquameK20()* peso );
            }
        else {
                liquido.addPotassioTotale(0);
            }
        /**
         * azotototalealcampo è una variabile di contenitorereflui
         */
       /* if(stabula.getLiquameN() != null)
            this.addAzotoalcampoliquame(stabula.getLiquameN()*peso);
        
              Refluo test= this.contenitore.setTipologia("Liquame "+chiave, liquido);
            
                
        }
        
        
     

        
        
        Refluo solido = this.contenitore.getTipologia("Letame "+chiave);
        
         if(solido == null)
        {
            System.out.println("(solido ) non ho trovato alleva.specie " + alleva.getDesSpecie() + " chiave " + chiave );
            
               
        }else
         {
        
         
         if(stabula.getLetame() != null) {
                 solido.addMetricubi(stabula.getLetame()* peso);
             }
         else {
                 solido.addMetricubi(0);
             }
         
         
         if(stabula.getLetameNEx() != null) {
                 solido.addAzotoTotale(stabula.getLetameNEx() * peso);
             }
         else {
                 solido.addAzotoTotale(0);
             }
         
         if(stabula.getLetameTanEx() != null) {
                 solido.addAzotoAmmoniacale(stabula.getLetameTanEx() * peso);
             }
         else {
                 solido.addAzotoAmmoniacale(0);
             }
         
         if(stabula.getLetameSt() != null) {
                 solido.addSostanzaSecca(stabula.getLetameSt() * peso *1000);
             }
         else {
                 solido.addSostanzaSecca(0);
             }

         if(stabula.getLetameSv() != null) {
                 solido.addSolidiVolatili(stabula.getLetameSv() * peso * 1000);
             }
         else {
                 solido.addSolidiVolatili(0);
             }

         if(stabula.getLetameP205() != null) {
                 solido.addFosforoTotale(stabula.getLetameP205() * peso );
             }
         else {
                 solido.addFosforoTotale(0);
             }
         
         if(stabula.getLetameK20() != null) {
                 solido.addPotassioTotale(stabula.getLetameK20() * peso );
             }
         else {
                 solido.addPotassioTotale(0);
             }

         
         /**
         * azotototalealcampo è una variabile di contenitorereflui
         */
       /* if(stabula.getLetameN() != null) {
                 this.addAzotoalcampoletame(stabula.getLetameN() * peso);
             }
         
         
           this.contenitore.setTipologia("Letame "+chiave, solido);
           
         }

    }*/
    
    
    
    /**
     * in funzione dei valori recuperati dalla query fatta sul db aziendale che ha 
     * rewcuperato l'azienda e di questa la lista degli allevamenti 
     * e di ogni allevamento i dati di stabulazine vado a rimepieri 
     * il contenitore dei reflui in funzione del documento di trasformazione 
     * @param alleva
     * @param stabula 
     */
    private void popolaContenitoreA(db.AllevamentoI alleva,db.SpeciecategoriaallevamentostabulazionebS stabula)
    {
        String chiave =(String) mappingspecie.get(stabula.getSpeciebSCodSpecie().getDesSpecie());
        
    
        //peso vivo della categoria in tonnellate
        double peso = (stabula.getPesoVivoKg() * alleva.getNumCapiSpecieStab()) / 1000;
        
        Refluo liquido = this.contenitore.getTipologia("Liquame "+chiave);
        
       // System.out.println("allevamento peso " + peso  +" liquame " + stabula.getLiquame() +"  peso vivo " +stabula.getPesoVivoKg() +" nmero capi " + alleva.getNumCapiSpecieStab());
        
        
        if(liquido == null)
        {
            System.out.println("non ho trovato alleva.specie " + stabula.getSpeciebSCodSpecie().getDesSpecie() + " chiave " + chiave );
            
             
        }else
        {
        
        
        if(stabula.getLiquame() != null) {
                liquido.addMetricubi(stabula.getLiquame() * peso);
                
            }
        else {
                liquido.addMetricubi(0);
            }
        
        
        if(stabula.getLiquameNEx() != null) {
                liquido.addAzotoTotale(stabula.getLiquameNEx() * peso);
            }
        else {
                liquido.addAzotoTotale(0);
            }

        if(stabula.getLiquameTanEx() != null) {
                liquido.addAzotoAmmoniacale(stabula.getLiquameTanEx()* peso);
            }
        else {
                liquido.addAzotoAmmoniacale(0);
            }
        
        if(stabula.getLiquameSt() != null) {
                liquido.addSostanzaSecca(stabula.getLiquameSt() * peso * 1000);
            }
        else {
                liquido.addSostanzaSecca(0);
            }
        
        if(stabula.getLiquameSt() != null) {
                liquido.addSolidiVolatili(stabula.getLiquameSv()* peso * 1000);
            }    
        else {
                liquido.addSolidiVolatili(0);
            }    

        if(stabula.getLiquameP205() != null) {
                liquido.addFosforoTotale(stabula.getLiquameP205()* peso );
            }
        else {
                liquido.addFosforoTotale(0);
            }

        if(stabula.getLiquameK20() != null) {
                liquido.addPotassioTotale(stabula.getLiquameK20()* peso );
            }
        else {
                liquido.addPotassioTotale(0);
            }
        /**
         * azotototalealcampo è una variabile di contenitorereflui
         */
       if(stabula.getLiquameN() != null)
            this.addAzotoalcampoliquame(stabula.getLiquameN()*peso);
        
              Refluo test= this.contenitore.setTipologia("Liquame "+chiave, liquido);
            
                
        }
        
        
     

        
        
        Refluo solido = this.contenitore.getTipologia("Letame "+chiave);
        
         if(solido == null)
        {
            System.out.println("(solido ) non ho trovato alleva.specie " + stabula.getSpeciebSCodSpecie().getDesSpecie() + " chiave " + chiave );
            
               
        }else
         {
        
         
         if(stabula.getLetame() != null) {
                 solido.addMetricubi(stabula.getLetame()* peso);
             }
         else {
                 solido.addMetricubi(0);
             }
         
         
         if(stabula.getLetameNEx() != null) {
                 solido.addAzotoTotale(stabula.getLetameNEx() * peso);
             }
         else {
                 solido.addAzotoTotale(0);
             }
         
         if(stabula.getLetameTanEx() != null) {
                 solido.addAzotoAmmoniacale(stabula.getLetameTanEx() * peso);
             }
         else {
                 solido.addAzotoAmmoniacale(0);
             }
         
         if(stabula.getLetameSt() != null) {
                 solido.addSostanzaSecca(stabula.getLetameSt() * peso *1000);
             }
         else {
                 solido.addSostanzaSecca(0);
             }

         if(stabula.getLetameSv() != null) {
                 solido.addSolidiVolatili(stabula.getLetameSv() * peso * 1000);
             }
         else {
                 solido.addSolidiVolatili(0);
             }

         if(stabula.getLetameP205() != null) {
                 solido.addFosforoTotale(stabula.getLetameP205() * peso );
             }
         else {
                 solido.addFosforoTotale(0);
             }
         
         if(stabula.getLetameK20() != null) {
                 solido.addPotassioTotale(stabula.getLetameK20() * peso );
             }
         else {
                 solido.addPotassioTotale(0);
             }

         
         /**
         * azotototalealcampo è una variabile di contenitorereflui
         */
        if(stabula.getLetameN() != null) {
                 this.addAzotoalcampoletame(stabula.getLetameN() * peso);
             }
         
         
           this.contenitore.setTipologia("Letame "+chiave, solido);
           
         }

    }
    
    /**
     * rimpei la hashtable mappignspecie con le corrspondenza tra le specie 
     * nel database aziendale e quelle(le chiavi della hashtable: solo la seconda parte delle chiave) del contenitore reflui 
     */
    private void inizializzaSpecie()
    {
        mappingspecie.clear();
        
        mappingspecie.put("SUINI","Suino");
        mappingspecie.put("BOVINI DA LATTE","Bovino");
        mappingspecie.put("BOVINI DA CARNE","Bovino");
        mappingspecie.put("OVINI","Altro");
        mappingspecie.put("AVICOLI","Avicolo");
        mappingspecie.put("BUFALINI DA CARNE","Bovino");
        mappingspecie.put("BUFALINI DA LATTE","Bovino");
        mappingspecie.put("EQUINI","Altro");
        mappingspecie.put("CAPRINI","Altro");
        mappingspecie.put("CUNICOLI","Altro");
        


        
    }
    
    
    public ContenitoreReflui getContenitore()
    {
        return this.contenitore;
    }
    
    
    /**
     * valuta il documento descrizioen struttura azienda fino a stoccaggio -2 c
     * calcolando l'influenza delle acque di stoccaggio date dalle superfici scoperte
     */
   /* private void calcolaStoccaggi(db.AcqueStoccaggii stabula)
    {
        /**
         * calcolo acqua meteo e acqua tot come da documento word "descrizione struttura azienda fino a stoccaggio"
         */
       /* if(stabula.getSuperficiScoperte() != null && stabula.getSupLiquidi1rac() != null && stabula.getPioggia() != null && stabula.getPioggia() != 0)
                this.acquameteo = (stabula.getSuperficiScoperte() + stabula.getSupLiquidi1rac())*stabula.getPioggia() /1000;
        
        if(stabula.getAcquaImpianti() != null)
            this.acquatot = this.acquameteo + stabula.getAcquaImpianti();
        
        /**
         * recupero il toatle dei liquami presenti nel contenitore reflui di questa classe 
         * precedetnemente caricato con il getData
         */
       /* Double metri = new Double(this.getContenitore().totale("Liquame").getMetricubi());
        double liquameTotale = 0;
        if(metri != null) {
            liquameTotale = this.getContenitore().totale("Liquame").getMetricubi();
        }
        
        Refluo refluo ;
        double temp = 0;
        
        /**
         * per ogni specie contenuta in contenitore ricalcolo i metri cubi l'azoto ammoniacale e totale
         * in funzione delle formule previste sul documento "descrizione e struttura azienda fino allo stoccaggio -2 "
         */
       /* for(String s:this.contenitore.getTipologie())
        {
            if(s.contains("Liquame"))
            {
                /**
                 * valuto i nmuovi metri cubi in funzione degli stoccaggi
                 */
              /*  refluo = this.contenitore.getTipologia(s);
                temp = refluo.getMetricubi();
                if(temp > 0)
                {
                    
                //    System.out.println("contenitore aziendale calcolostoccaggi metri cubi " + refluo.getMetricubi() + " temp " + temp + " acuqatot " + this.acquatot + " liquame tot " + liquameTotale );
                temp = temp + this.acquatot * this.acquatot / liquameTotale;
                refluo.setMetricubi(temp);
                temp = refluo.getAzotototale();
                refluo.setAzotototale(temp * 0.87);
                temp = refluo.getAzotoammoniacale();
                refluo.setAzotoammoniacale(temp * 0.87);
                }
                
            }
        }
    }*/
    
    /**
     * valuta il documento descrizioen struttura azienda fino a stoccaggio -2 c
     * calcolando l'influenza delle acque di stoccaggio date dalle superfici scoperte
     */
    private void calcolaStoccaggiA(db.AcquastoccaggioI stabula)
    {
        /**
         * calcolo acqua meteo e acqua tot come da documento word "descrizione struttura azienda fino a stoccaggio"
         */
        if(stabula.getSuperficiScoperte() != null && stabula.getSupLiquidi1rac() != null && stabula.getPioggia() != null && stabula.getPioggia() != 0) {
            this.acquameteo = (stabula.getSuperficiScoperte() + stabula.getSupLiquidi1rac())*stabula.getPioggia() /1000;
        }
        
        if(stabula.getAcquaImpianti() != null) {
            this.acquatot = this.acquameteo + stabula.getAcquaImpianti();
        }
        
        /**
         * recupero il toatle dei liquami presenti nel contenitore reflui di questa classe 
         * precedetnemente caricato con il getData
         */
        Double metri = new Double(this.getContenitore().totale("Liquame").getMetricubi());
        double liquameTotale = 0;
        if(metri != null) {
            liquameTotale = this.getContenitore().totale("Liquame").getMetricubi();
        }
        
        Refluo refluo ;
        double temp = 0;
        
        /**
         * per ogni specie contenuta in contenitore ricalcolo i metri cubi l'azoto ammoniacale e totale
         * in funzione delle formule previste sul documento "descrizione e struttura azienda fino allo stoccaggio -2 "
         */
        for(String s:this.contenitore.getTipologie())
        {
            if(s.contains("Liquame"))
            {
                /**
                 * valuto i nmuovi metri cubi in funzione degli stoccaggi
                 */
                refluo = this.contenitore.getTipologia(s);
                temp = refluo.getMetricubi();
                if(temp > 0)
                {
                    
                //  System.out.println("contenitore aziendale calcolostoccaggi metri cubi " + refluo.getMetricubi() + " temp " + temp + " acuqatot " + this.acquatot + " liquame tot " + liquameTotale );
                temp = temp + (this.acquatot * temp / liquameTotale);
                 //temp = temp + (this.acquatot * sta1.getLiquame() * capi.getNumCapiSpecieStab()/ liquameTotale);
                
                               //   System.out.println("contenitore aziendale calcolostoccaggi metri cubi " + refluo.getMetricubi() + " temp " + temp + " acuqatot " + this.acquatot + " liquame tot " + liquameTotale );

                refluo.setMetricubi(temp);
                temp = refluo.getAzotototale();
                refluo.setAzotototale(temp * 0.87);
                temp = refluo.getAzotoammoniacale();
                refluo.setAzotoammoniacale(temp * 0.87);
                }
                
            }
        }
    }
    
    /**
     * @return the azotoalcampoliquame
     */
    public double getAzotoalcampoliquame() {
        return azotoalcampoliquame;
    }

    /**
     * @param azotoalcampoliquame the azotoalcampoliquame to set
     */
    public void setAzotoalcampoliquame(double azotoalcampoliquame) {
        this.azotoalcampoliquame = azotoalcampoliquame;
    }

    /**
     * @return the azotoalcampoletame
     */
    public double getAzotoalcampoletame() {
        return azotoalcampoletame;
    }

    /**
     * @param azotoalcampoletame the azotoalcampoletame to set
     */
    public void setAzotoalcampoletame(double azotoalcampoletame) {
        this.azotoalcampoletame = azotoalcampoletame;
    }

    /**
     * @return the azotoalcampototale
     */
    public double getAzotoalcampototale() {
        return azotoalcampototale = this.azotoalcampoletame + this.azotoalcampoliquame;
    }

    /**
     * @param azotoalcampototale the azotoalcampototale to set
     */
    public void setAzotoalcampototale(double azotoalcampototale) {
        this.azotoalcampototale = azotoalcampototale;
    }
    
   
    
    public void addAzotoalcampoliquame(double azotoalcampoliquame)
    {
        this.azotoalcampoliquame +=azotoalcampoliquame;
    }
    
    public void addAzotoalcampoletame(double azotoalcampoletame)
    {
        this.azotoalcampoletame +=azotoalcampoletame;
    }

    /**
     * @return the acquameteo
     */
    public double getAcquameteo() {
        return acquameteo;
    }

    /**
     * @param acquameteo the acquameteo to set
     */
    public void setAcquameteo(double acquameteo) {
        this.acquameteo = acquameteo;
    }

    /**
     * @return the acquatot
     */
    public double getAcquatot() {
        return acquatot;
    }

    /**
     * @param acquatot the acquatot to set
     */
    public void setAcquatot(double acquatot) {
        this.acquatot = acquatot;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
