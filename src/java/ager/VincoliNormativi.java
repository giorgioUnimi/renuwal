/*
 * Si occupa di verificare i tre confronti per verificare la conformita normativa
 * dell'azienda dopo aver applicato l'alternativa di trattamento oppure nessuna alternativa
 * nel caso in cui abbia scelto il trattamento 0
 * confronto 1 vincolo normativo
 * confronto 2 vincolo mas
 * confronto 3 asportazione fosforo
 */
package ager;

import java.util.HashMap;
import java.util.Iterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.RecordAppezzamento;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
public class VincoliNormativi {
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private ContenitoreReflui contenitoreReflui;
    private Long idscenario;
    private int alternativa;
    
    public VincoliNormativi(ContenitoreReflui contenitoreReflui, int alternativa){
        
         this.contenitoreReflui = contenitoreReflui;
         this.alternativa = alternativa;
         
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioCuaa dettTemp = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
        
         this.idscenario = dettTemp.getIdscenario();
         
    }
    
    /**
     * verifica il vincolo normativo nitrati
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloNormativo()
    {
        double ettari_zvn = 0;
        double ettari_nzvn = 0;
        double totale_n_zootecnico = 0 ;
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }        
        
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", this.idscenario);
       
       if(q.getResultList().isEmpty())
           return false;
       
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);
       Iterator<db.Appezzamento> iterAppezzamenti=sceT.getAppezzamentoCollection().iterator();
       //ciclo sugli appezzamenti per calcolare la somma degli ettari in zvn e quelli in nzvn
        while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           if(apptemp.getSvz())
           {
               ettari_zvn += apptemp.getSuperficie();
           }else
           {
              ettari_nzvn += apptemp.getSuperficie(); 
           }
           
       }
        
        //devo calcolare la somma di N per tutte le specie animali e tipi di refluo tranne che per le biomasse
        //recupero le tipologie e ciclo su di esse nel contenitoreReflui
        Refluo re = new Refluo("");
        for(String  s:this.contenitoreReflui.getTipologie())
        {
              if(!s.contains("Biomassa")) {
                totale_n_zootecnico +=contenitoreReflui.getContenitore().get(s).getAzotototale();
            }
                 
                
        }        
        //se totale_n_zootecnico < uguale a (ettari_zvn * 170 + ettari_nzvn * 340 )
        
        double tempA =  ettari_zvn * 170 + ettari_nzvn *340;
        
        Connessione.getInstance().chiudi();         
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " azoto animale " +totale_n_zootecnico +  " < campo " +tempA);
        
        if(totale_n_zootecnico <= tempA ) {
            return false;
        }
        else {
            return true;
        }
    }
    
    /**
     * verifica il vincolo dei Mas
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloMas()
    {
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }        
        /**
         * recupero lo scenario per avere la lista degli appezzamenti con le loro superfici
         * e per avere per ogni appezzamento la lista delle rotazioni e delle conseguenti asportazioni
         * di azoto
         */
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", this.idscenario);
       
       if(q.getResultList().isEmpty())
           return false;
       
       
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);
       Iterator<db.Appezzamento> iterAppezzamenti=sceT.getAppezzamentoCollection().iterator();
       double asp_azoto_appezzamenti = 0;
       //ciclo sugli appezzamenti per calcolare la somma delle asportazioni di azoto
       //e quindi calcolo l'azoto da gestione colturale
       while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           Iterator<db.Storicocolturaappezzamento> iterRotazioni = apptemp.getStoricocolturaappezzamentoCollection().iterator();
           
           double aspAzoto = 0;
           
           while(iterRotazioni.hasNext())
           {
               db.Storicocolturaappezzamento tempRotazione = iterRotazioni.next();
               aspAzoto += tempRotazione.getAsportazioneazoto();
           }
           
           asp_azoto_appezzamenti += aspAzoto * apptemp.getSuperficie();
           
       }
       //per il confronto devo calcolare la sommatoria di azoto per specie comprensivo delle biomasse
       //moltiplicato per l'efficienza della singola specie di refluo prendondo il refluo
       //dal contenitore di refluo preso dal costruttore
       Refluo re = new Refluo("");
       HashMap<String,Integer> mappingTipiMateria = new HashMap<String,Integer>();
       mappingTipiMateria.put("bovino",1);
        mappingTipiMateria.put("suino",2);
         mappingTipiMateria.put("avicolo",3);
          mappingTipiMateria.put("biomassa",9);
          double azoto_colturale = 0;
          double azoto_animale  = 0;
         Query q1 = null; 
         /**
          * recupero l'alternativa dal db per capire se ha digestato o meno 
          * la presenza di digestato fa cambiare la query
          * ciclo sulle tipologia di refluo sommando l'azoto totale
          */
          Query q2 = entityManager.createNamedQuery("AlternativeS.findById").setParameter("id", this.alternativa); 
          db.AlternativeS alternativaMas = (db.AlternativeS)q2.getSingleResult();
         // boolean dadigestato = alternativa..getDigestato() == 0 ? false : true;
        /**
         * ciclo sulle tipologie di refluo e splitto la tipologia perchè l'efficienza dell'azoto 
         * cambia se è liquame o letame. Se l'alternativa ha digestato devo scelgiere quei coefficienti che 
         * hanno il campo digestato a true
         */  
        for (String s : this.contenitoreReflui.getTipologie()) {
            
           /* if(s.contains("Altro")) {
                continue;
            }*/
            
            String[] tipo_splittato = s.split(" ");
            tipo_splittato[0] = tipo_splittato[0].toLowerCase();
            if(tipo_splittato[0].trim().equals("Liquame")) {
                tipo_splittato[0] = "liquido";
            }
            else {
                tipo_splittato[0] = "palabile";
            }
            
            
            tipo_splittato[1] = tipo_splittato[1].toLowerCase();
            
            
             System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " spli 1 " + tipo_splittato[1] );
            
            
            azoto_colturale = contenitoreReflui.getContenitore().get(s).getAzotototale();
            Query q4 = entityManager.createQuery("SELECT a FROM Formarefluo a WHERE a.tipo1 =?1 ");
            q4.setParameter(1, tipo_splittato[0].trim());
            db.Formarefluo formaRefluo = (db.Formarefluo)q4.getSingleResult();
            Query q5 = entityManager.createQuery("SELECT a FROM ProvenienzaRefluo a WHERE a.nome =?1 ");
            q5.setParameter(1,tipo_splittato[1].trim().toLowerCase());
            db.ProvenienzaRefluo provenienzaRefluo =( db.ProvenienzaRefluo)q5.getSingleResult();
            
            Query q3 = entityManager.createQuery("SELECT a FROM EfficienzeNpVincoliNormativi a WHERE a.idAlternativa = :alternativa and a.idFormaRefluo = :idforma and a.idProvenienzaRefluo = :idprovenienza");
            q3.setParameter("alternativa", alternativaMas);
            q3.setParameter("idforma", formaRefluo);
            q3.setParameter("idprovenienza", provenienzaRefluo);
            
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " spli 0 " + tipo_splittato[0] + " spli 1 " + tipo_splittato[1] + " alternativa " + alternativaMas.getNome() + " idforma " + formaRefluo.getTipo1() + " provenienza " + provenienzaRefluo.getNome());
           db.EfficienzeNpVincoliNormativi efficienzaNormativa = (db.EfficienzeNpVincoliNormativi)q3.getSingleResult();
           
            //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " spli 0 " + tipo_splittato[0] + " spli 1 " + tipo_splittato[1]);
            
            //mi restituisce il valore numerico del codice materia
            //della tipologia di refluo che sto prendendo dal contenitorereflui
            /*int tipomateria = mappingTipiMateria.get( tipo_splittato[1].trim());
             Query q0 = entityManager.createNamedQuery("TipomateriaS.findById").setParameter("id", tipomateria);
            db.TipomateriaS tipoM = (db.TipomateriaS)q0.getSingleResult();
            q1 = entityManager.createQuery("SELECT a FROM Efficienze a WHERE a.tipomateriaId = :tipo AND a.daDigestato = :digestato", db.Efficienze.class);
            q1.setParameter("tipo", tipoM);
            q1.setParameter("digestato", dadigestato);
            db.Efficienze efficienza = (db.Efficienze) q1.getSingleResult();*/
            
           azoto_animale += azoto_colturale * efficienzaNormativa.getCoefficienteN();
           
           
          /*  if (s.contains("liquame")) {
                 azoto_animale += azoto_colturale * efficienza.getEfficienzaAzotoLiquame();
            } else {//letame
                azoto_animale += azoto_colturale * efficienza.getEfficienzaAzotoLetame();
            }*/

            
        }
        
        Connessione.getInstance().chiudi();
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " azoto animale " +azoto_animale +  " < campo " +asp_azoto_appezzamenti);
        
        
        if(azoto_animale < asp_azoto_appezzamenti) {
            return false;
        }
        else {
            return true;
        }
    }
    /**
     * verifica il vincolo fosforo
     * @return true se è ripsttato false altrimenti
     */
    public boolean vincoloFosforo(){
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }        
        /**
         * recupero lo scenario per avere la lista degli appezzamenti con le loro superfici
         * e per avere per ogni appezzamento la lista delle rotazioni e delle conseguenti asportazioni
         * di fosforo
         */
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", this.idscenario);
       
       if(q.getResultList().isEmpty())
           return false;
       
       
       db.ScenarioI sceT = (db.ScenarioI)q.getResultList().get(0);
       Iterator<db.Appezzamento> iterAppezzamenti=sceT.getAppezzamentoCollection().iterator();
       double asp_fosforo_appezzamenti = 0;
       //ciclo sugli appezzamenti per calcolare la somma delle asportazioni di fosforo
       //e quindi calcolo l'fosforo da gestione colturale
       while(iterAppezzamenti.hasNext())
       {
           db.Appezzamento apptemp = iterAppezzamenti.next();
           Iterator<db.Storicocolturaappezzamento> iterRotazioni = apptemp.getStoricocolturaappezzamentoCollection().iterator();
           
           double aspFosforo = 0;
           
           while(iterRotazioni.hasNext())
           {
               db.Storicocolturaappezzamento tempRotazione = iterRotazioni.next();
               aspFosforo += tempRotazione.getAsportazionefosforo();
           }
           
           asp_fosforo_appezzamenti += aspFosforo * apptemp.getSuperficie();
           
       }
       //per il confronto devo calcolare la sommatoria di fosforo per specie comprensivo delle biomasse
       //moltiplicato per l'efficienza della singola specie di refluo prendondo il refluo
       //dal contenitore di refluo preso dal costruttore
       Refluo re = new Refluo("");
       HashMap<String,Integer> mappingTipiMateria = new HashMap<String,Integer>();
       mappingTipiMateria.put("bovino",1);
        mappingTipiMateria.put("suino",2);
         mappingTipiMateria.put("avicolo",3);
          mappingTipiMateria.put("biomassa",9);
          double fosforo_colturale = 0;
          double fosforo_animale  = 0;
         Query q1 = null; 
         /**
          * recupero l'alternativa dal db per capire se ha digestato o meno 
          * la presenza di digestato fa cambiare la query
          * ciclo sulle tipologia di refluo sommando l'fosforo totale
          */
          Query q2 = entityManager.createNamedQuery("AlternativeS.findById").setParameter("id", this.alternativa); 
          db.AlternativeS alternativaMas = (db.AlternativeS)q2.getSingleResult();
          //boolean dadigestato = alternativa.getDigestato() == 0 ? false : true;
        /**
         * ciclo sulle tipologie di refluo e splitto la tipologia perchè l'efficienza dell'fosforo 
         * cambia se è liquame o letame. Se l'alternativa ha digestato devo scelgiere quei coefficienti che 
         * hanno il campo digestato a true
         */  
        for (String s : this.contenitoreReflui.getTipologie()) {
              if(s.contains("Altro")) {
                continue;
            }
            
           // fosforo_colturale = contenitoreReflui.getContenitore().get(s).getFosforototale();
            
              String[] tipo_splittato = s.split(" ");
            tipo_splittato[0] = tipo_splittato[0].toLowerCase();
            if(tipo_splittato[0].trim().equals("Liquame")) {
                tipo_splittato[0] = "liquido";
            }
            else {
                tipo_splittato[0] = "palabile";
            }
            
            
            tipo_splittato[1] = tipo_splittato[1].toLowerCase();
            
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName()+ " spli 0 " + tipo_splittato[0] + " spli 1 " + tipo_splittato[1] );
          
            fosforo_colturale = contenitoreReflui.getContenitore().get(s).getFosforototale();
            Query q4 = entityManager.createQuery("SELECT a FROM Formarefluo a WHERE a.tipo1 =?1");
            q4.setParameter(1, tipo_splittato[0].trim());
            db.Formarefluo formaRefluo = (db.Formarefluo)q4.getSingleResult();
            Query q5 = entityManager.createQuery("SELECT a FROM ProvenienzaRefluo a WHERE a.nome =?1");
            q5.setParameter(1, tipo_splittato[1].trim().toLowerCase());
            db.ProvenienzaRefluo provenienzaRefluo =( db.ProvenienzaRefluo)q5.getSingleResult();
            
            Query q3 = entityManager.createQuery("SELECT a FROM EfficienzeNpVincoliNormativi a WHERE a.idAlternativa = :alternativa and a.idFormaRefluo = :idforma and a.idProvenienzaRefluo = :idprovenienza");
            q3.setParameter("alternativa", alternativaMas);
            q3.setParameter("idforma", formaRefluo);
            q3.setParameter("idprovenienza", provenienzaRefluo);
            
            
           db.EfficienzeNpVincoliNormativi efficienzaNormativa = (db.EfficienzeNpVincoliNormativi)q3.getSingleResult();
           
          /*  String[] tipo_splittato = s.split(" ");
            tipo_splittato[0] = tipo_splittato[0].toLowerCase();
            tipo_splittato[1] = tipo_splittato[1].toLowerCase();*/
            
            //mi restituisce il valore numerico del codice materia
            //della tipologia di refluo che sto prendendo dal contenitorereflui
          /*  int tipomateria = mappingTipiMateria.get( tipo_splittato[1].trim());
            Query q0 = entityManager.createNamedQuery("TipomateriaS.findById").setParameter("id", tipomateria);
            db.TipomateriaS tipoM = (db.TipomateriaS)q0.getSingleResult();
            q1 = entityManager.createQuery("SELECT a FROM Efficienze a WHERE a.tipomateriaId = :tipo AND a.daDigestato = :digestato", db.Efficienze.class);
            q1.setParameter("tipo", tipoM);
            q1.setParameter("digestato", dadigestato);
            db.Efficienze efficienza = (db.Efficienze) q1.getSingleResult();*/
            
            //if (s.contains("liquame")) {
              //   fosforo_animale += fosforo_colturale * efficienza.getEfficienzaFosforo();
           /* } else {//letame
                fosforo_animale += fosforo_colturale * efficienza.getEfficienzaAzotoLetame();
            }*/
            fosforo_animale += fosforo_colturale * efficienzaNormativa.getCoefficienteP();
            
        }
        
        Connessione.getInstance().chiudi();
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " fosforo animale " +fosforo_animale +  " < campo " +asp_fosforo_appezzamenti);

        
        if(fosforo_animale < asp_fosforo_appezzamenti) {
            return false;
        }
        else {
            return true;
        }
    }
    
    
}
