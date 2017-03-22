/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.UnitOfWork;
import org.eclipse.persistence.sessions.server.ServerSession;
/**
 *
 * @author giorgio
 */
public class OperazioneDB {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    JpaEntityManager jpa;
    ServerSession serverSession;
    
    
    
   /* private void connetti()
    {
       
        
        
        entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        
       /* if(jpa.isOpen())
            return true;
        else
            return false;*/
   /* }
    
    
    
    private void disconnetti()
    {
         entityManager.close();
         entityManagerFactory.close();
    }*/
    
    
    private long getlastscenario()
    {
                
         Query q = entityManager.createQuery("SELECT s.idscenario FROM ScenarioI s ORDER BY s.idscenario DESC");
                
         /**
          * recupero l'ultimo idscenario tra la lista degli scenari
          */
         long ultimo = 0L;
         if(!q.getResultList().isEmpty())
         { 
           ultimo = (long)q.getResultList().get(0);
          System.out.println("from operazioneDB creascenario ultimo idscenario " + ultimo);
         }
         
         
         return ultimo;
    }
    
    /**
     * crea un nuovo scenario per una data azienda individuata dal suo cuaa 
     * @param cuaa
     * @param descrizione
     * @return 0 nel caso non crei lo scenario un valore diverso nel caso lo crei
     */
    public long creaScenario(String idazienda,String descrizione,String anno) 
    {
        long ultimo = 0;
      
         System.out.println(" da operazionedb id " +  idazienda+ "  anno " + anno);
         
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
             entityManagerFactory = connessione.getEntityManagerFactory();
         }
        
        entityManagerFactory.getCache().evictAll();
        /**
         * dall anno recupero l id dell anno db.Anni
         */
        anno = anno.trim();
        Query q1 = entityManager.createQuery("Select s from Anni s where s.descrizione = ?1").setParameter(1,anno);
        db.Anni idanno = (db.Anni)q1.getResultList().get(0);
        Integer id_anno = idanno.getId();
        /**
         * recupero l'azienda dal cuaa e  duplico i suoi allevamenti,acque di stoccaggio e stoccaggi creando lo scenario 
         * a cui assegno la descrizione che mi viene fornita
         */
        idazienda = idazienda.trim();
        Query q = entityManager.createNamedQuery("AziendeI.findById").setParameter("id",Integer.parseInt(idazienda));
         
         
         List<db.AziendeI> results = q.getResultList();
         db.AziendeI azienda = results.get(0);
         
         /**
          * ciclo sui record correlati di AziendeAnni per cercare il record 
          * che ha l'anno che cerco
          */
         Iterator<db.AziendeAnni> iterAziendeAnni = azienda.getAziendeAnniCollection().iterator();
         db.AziendeAnni aziendaAnniC = null;
         
         while(iterAziendeAnni.hasNext())
         {
             db.AziendeAnni temp = iterAziendeAnni.next();
             if(temp.getIdAnno().getDescrizione().equals(anno))
             {
                 aziendaAnniC = temp;
                 break;
             }
         }
         
       //Collection<db.ScenarioI> scenariazienda = null;
         EntityTransaction tx = entityManager.getTransaction();
        
        /**
         * se non trovo il record in aziendeanni lo aggiungo
         * di conseguenza questo nuovo record non ha nessun scenario
         */ 
         if(aziendaAnniC == null) {
            System.out.println(" nessun azienda trovata con id " +  idazienda+ "  anno " + anno);
            tx.begin();
            aziendaAnniC = new db.AziendeAnni();
            aziendaAnniC.setIdAnno(idanno);
            aziendaAnniC.setIdAzienda(azienda);
            entityManager.persist(aziendaAnniC);
            tx.commit();
            
        }
         
        
        tx = entityManager.getTransaction();
        tx.begin(); 
         
        db.ScenarioI nuovoscenario = new db.ScenarioI();
        nuovoscenario.setIdAziendeanni(aziendaAnniC);
        nuovoscenario.setDescrizione(descrizione);
    
      
        entityManager.persist(nuovoscenario);
               
        tx.commit();
        /**
          * recupero l'idscenario dell'ultimo appena inserito scenario 
          */
        ultimo = nuovoscenario.getIdscenario();

              
        entityManager.clear();
         
        entityManager.close();
       
        
        System.out.println("valore idscenario creato " + ultimo);
        
        
        return ultimo=64;
    }
    
    
    
     /**
     * crea un nuovo scenario per una data azienda individuata dal suo cuaa 
     * @param cuaa
     * @param descrizione
     * @return 0 nel caso non crei lo scenario un valore diverso nel caso lo crei
     */
//    public long creaScenario1(String cuaa,String descrizione) 
//    {
//        long ultimo = 0;
//           //String date1 = "...", date2 = "...";  
//    //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");  
//   // Date d1 = new Date(); 
//   
//       
//    
//    //long diffInMilliseconds = Math.abs(d1.getTime());   
//       
//         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
//         {
//             Connessione connessione = Connessione.getInstance();
//             entityManager = connessione.apri("renuwal2");
//             entityManagerFactory = connessione.getEntityManagerFactory();
//         }
//        
//        entityManagerFactory.getCache().evictAll();
//        
//        /**
//         * recupero l'azienda dal cuaa e  duplico i suoi allevamenti,acque di stoccaggio e stoccaggi creando lo scenario 
//         * a cui assegno la descrizione che mi viene fornita
//         */
//        Query q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",cuaa);
//         
//         
//         List<db.AziendeI> results = q.getResultList();
//         db.AziendeI azienda = results.get(0);
//         /**
//          * recupero la collezione degli scenari di un azienda e prendo l'ultimo id ovvero 
//          * quello piu grande della collezione degli scenari
//          */
//         List<db.ScenarioI> scenari =(List<db.ScenarioI>) azienda.getScenarioICollection();
//         int ultimoid =0;
//         for(db.ScenarioI sce:scenari)
//         {
//             if(ultimoid < sce.getId())
//                 ultimoid = sce.getId();
//             System.out.println("Ultimo id : " + ultimoid + " getId " + sce.getId());
//         }
//         
//         /**
//          * a questo punto ho idscenario(id che unisce cuaa ed id proprio dello scenario) e l'ultimo id tra gli scenari di un azienda
//          * per cui posso duplicare la lista degli allevamenti , delgi stoccaggi e delle acque di stoccaggio. I dati degli allevamenti ,stoccaggi e
//          * acque di stoccaggio li prendo dallo scenario zero dell'azienda.
//          */
//         db.ScenarioI scenario = scenari.get(0);
//         db.AcquastoccaggioI acquastoccaggio = (db.AcquastoccaggioI)scenario.getAcquastoccaggioI();
//         List<db.AllevamentoI> allevamenti = (List<db.AllevamentoI>)scenario.getAllevamentoICollection();
//         List<db.StoccaggioI> stoccaggi = (List<db.StoccaggioI>)scenario.getStoccaggioICollection();
//         //List<db.ParametridiprogettoS> parametridiprogetto = (List<db.ParametridiprogettoS>)scenario.getParametridiprogettoSCollection();
//         
//       /*  System.out.println("from operazioneDB creascenario idsceanrio "+acquastoccaggio.getIdscenario()+" acqua   " + acquastoccaggio.getAcquaImpianti() + "  capacita " + acquastoccaggio.getCapLiquidi1rac());
//         for(db.AllevamentoI all :allevamenti)
//         {
//             System.out.println("scenario " + all.getIdscenario() +" num capi  " +all.getNumCapiSpecieStab() );
//         }*/
//         EntityTransaction tx = entityManager.getTransaction();
//         tx.begin(); 
//         
//        db.ScenarioI nuovoscenario = new db.ScenarioI();
//        nuovoscenario.setCuaa(azienda);
//        nuovoscenario.setId(ultimoid+1);
//        nuovoscenario.setDescrizione(descrizione);
//       
//      
//         entityManager.persist(nuovoscenario);
//         
//        
//         //entityManager.persist(nuovoscenario);
//         tx.commit();
//         
//      // ultimo =  nuovoscenario.getIdscenario();
//         /**
//          * recupero l'idscenario dell'ultimo appena inserito scenario 
//          */
//         ultimo = getlastscenario();
////         System.out.println(this.getClass().getCanonicalName() + " idscenario " + ultimo);
////         q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", ultimo);
////         
////         db.ScenarioI scena0 = (db.ScenarioI)q.getResultList().get(0);
////         tx = entityManager.getTransaction();
////         tx.begin(); 
////         scena0.setAcquastoccaggioI(acquastoccaggio);
////         scena0.setAllevamentoICollection(allevamenti);
////         scena0.setStoccaggioICollection(stoccaggi);
////         
////         entityManager.persist(scena0);
//         /*
//          * per duplicare i parametri di progetto durante la creazione di un nuovo scenario
//          * mi appoggio all'azienda fantasma che ho inerito come campione che ha cuaa "predefinito"
//          * id ed idscenario nella tabella scenario_i uguale a 0
//          */
//        Query q1 = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa","predefinito");
//         db.AziendeI az = (db.AziendeI)q1.getSingleResult();
//         List<db.ScenarioI> sce =(List<db.ScenarioI>)az.getScenarioICollection();
//         db.ScenarioI sce1 = sce.get(0);
//         List<db.ParametridiprogettoS> para =(List<db.ParametridiprogettoS>) sce1.getParametridiprogettoSCollection();
//         
//         
//        
//      //int it = 0;
//         /**
//          * duplico i parametri di progetto dellìazienda predefninita
//          * nello scenario della ditta
//          */
//        List<db.ParametridiprogettoS> listaParametri = new LinkedList<db.ParametridiprogettoS>();
//        db.ParametridiprogettoS para2 ;
//        tx.begin();
//        
//         for(db.ParametridiprogettoS para1:para)
//         {
//              //tx.begin();
//             para2  = new db.ParametridiprogettoS();
//              para2.setIdScenario(nuovoscenario);
//              
//              if(para1.getContenutoattributo() != null) {
//                 para2.setContenutoattributo(para1.getContenutoattributo());
//             }
//              if(para1.getContenutoattributo1() != null) {
//                 para2.setContenutoattributo1(para1.getContenutoattributo1());
//             }
//              if(para1.getDiscriminante() != null) {
//                 para2.setDiscriminante(para1.getDiscriminante());
//             }
//              if(para1.getEntitacoinvolte() != null) {
//                 para2.setEntitacoinvolte(para1.getEntitacoinvolte());
//             }
//              if(para1.getIdAttributo() != null) {
//                 para2.setIdAttributo(para1.getIdAttributo());
//             }
//              if(para1.getIdAttributo1() != null) {
//                 para2.setIdAttributo1(para1.getIdAttributo1());
//             }
//              if(para1.getIdEntita() != null) {
//                 para2.setIdEntita(para1.getIdEntita());
//             }
//              if(para1.getIdEntita1() != null) {
//                 para2.setIdEntita1(para1.getIdEntita1());
//             }
//              if(para1.getIdNomeparametro() != null) {
//                 para2.setIdNomeparametro(para1.getIdNomeparametro());
//             }
//              if(para1.getIdTrattamento() != null) {
//                 para2.setIdTrattamento(para1.getIdTrattamento());
//             }
//              if(para1.getTipo() != null) {
//                 para2.setTipo( para1.getTipo());
//             }
//              if(para1.getValore() != null) {
//                 para2.setValore(para1.getValore());
//             }
//            
//              //parametridiprogetto.add(para2);
//             listaParametri.add(para2);
//             
//             
//             
//             //entityManager.persist(para2);
//             // tx.commit();
//              
//              
//           
//             
//         }
//          entityManager.persist(listaParametri);
//              tx.commit();
//         /*Date d2 = new Date();
//         diffInMilliseconds = Math.abs(d1.getTime() - d2.getTime());  
//        
//         
//         System.out.println("d1 "+d1.getTime()+"  d2 "+d2.getTime()+"  tempo ------ : " + diffInMilliseconds);*/
//         
//         //d1 = new Date();
//         
//         for(db.AllevamentoI alleva:allevamenti)
//         {
//             tx.begin();
//             db.AllevamentoI allevaA = new db.AllevamentoI();
//             allevaA.setIdscenario(nuovoscenario);
//             allevaA.setNumCapiSpecieStab(alleva.getNumCapiSpecieStab());
//             allevaA.setSpeciecategoriaallevamentostabulazionebId(alleva.getSpeciecategoriaallevamentostabulazionebId());
//             entityManager.persist(allevaA);
//             tx.commit();
//         }
//         
//        for(db.StoccaggioI stocca:stoccaggi)
//        {
//            tx.begin();
//            db.StoccaggioI stoA = new db.StoccaggioI();
//            stoA.setIdscenario(nuovoscenario);
//            stoA.setCapacita(stocca.getCapacita());
//            stoA.setSuperficiescoperta(stocca.getSuperficiescoperta());
//            stoA.setSuperficietotale(stocca.getSuperficietotale());
//            stoA.setIdstoccaggio(stocca.getIdstoccaggio());
//             entityManager.persist(stoA);
//            tx.commit();
//        }
//        
//        tx.begin();
//        db.AcquastoccaggioI acquastoccaA = new db.AcquastoccaggioI();
//        //acquastoccaA.setScenarioI(nuovoscenario);
//        acquastoccaA.setIdscenario(nuovoscenario.getIdscenario().intValue());
//        acquastoccaA.setAcquaImpianti(acquastoccaggio.getAcquaImpianti());
//        acquastoccaA.setCapLiquidi1rac(acquastoccaggio.getCapLiquidi1rac());
//        acquastoccaA.setCapSolidi1rac(acquastoccaggio.getCapSolidi1rac());
//        acquastoccaA.setPioggia(acquastoccaggio.getPioggia());
//        acquastoccaA.setSupLiquidi1rac(acquastoccaggio.getSupLiquidi1rac());
//        acquastoccaA.setSupSolidi1rac(acquastoccaggio.getSupSolidi1rac());
//        acquastoccaA.setSuperficiScoperte(acquastoccaggio.getSuperficiScoperte());
//        entityManager.persist(acquastoccaA);
//        tx.commit();
//        
//         /*d2 = new Date();
//         diffInMilliseconds = Math.abs(d1.getTime() - d2.getTime());  
//        
//         
//         System.out.println("d1 "+d1.getTime()+"  d2 "+d2.getTime()+"  tempo ------ : " + diffInMilliseconds);*/
//       // disconnetti();
//        Connessione.getInstance().chiudi();
//        
//         
//         return ultimoid+1;
//    }
    
    
    
    
    /**
     * dato il cuaa aziendale e l'id dello scenario dell'azienda 
     * elimina tale scenario e di conseguenza gli allevamebnti,stoccaggi ed acquastoccaggio ad esso legato
     * @param cuaa
     * @param id
     * @return 
     */
    public int eliminaScenario(String idscenario)
    {
        
       /**
         * se id è 0 non devi cancellarlo
         */
       /* if(Long.parseLong(id) == 0) {
            System.out.println("Stai tentando di cancellare l'id 0 " + id);
            return -1;
        }*/
       // connetti();
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
             entityManagerFactory = connessione.getEntityManagerFactory();
         }
        
        Long idscenarioL = Long.parseLong(idscenario);
        
         /**
         * mi serve per cancellare la cache dell'entity manager
         */
          entityManager.getEntityManagerFactory().getCache().evictAll();
        
      // System.out.println(this.getClass().getCanonicalName() +   " eliminascenario prima della query cuaa " +cuaa + " id " + id );
        
       /**
        * recupero l'azienda con un determinato cuaa e di questa recupero 
        * lo scenario ovvero l'idscenario mediante cuaa ed id cehmi vengono passati tramite
        * parametri del metodo
        */
        EntityTransaction tx = entityManager.getTransaction();
       tx.begin();    
       Query q = entityManager.createQuery("Delete  from ScenarioI s where s.idscenario = ?1 ").setParameter(1 , idscenarioL);
       q.executeUpdate();
       tx.commit();
  
            
            Connessione.getInstance().chiudi();
            
          return Integer.parseInt(idscenario);
    }
    
    
    /**
     * ritorna idscenario ovvero la chiave che ingloba cuaa e id a partire da cuaa e id 
     * cercandolo nella tabella scenario_i
     * @param cuaa cuaa dell'azienda
     * @param id id dello scenario
     * @return 
     */
    public long getidscenario(String cuaa,int id)
    {
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
             entityManager = connessione.apri("renuwal2");
             entityManagerFactory = connessione.getEntityManagerFactory();
         }
        
        
        
        
         Query q = entityManager.createQuery("SELECT idscenario FROM ScenarioI WHERE cuaa=?1 AND id=?2");
         q.setParameter(1,cuaa);
         q.setParameter(2,id);
         
         long idscenario =(long)q.getSingleResult();
         
         
         Connessione.getInstance().chiudi();
         
         
         return idscenario;
    }
    
}
