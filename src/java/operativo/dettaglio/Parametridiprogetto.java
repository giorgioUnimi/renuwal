/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo.dettaglio;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
public class Parametridiprogetto {
    
    
     /**
    * Lista dei parametri di progetto
    */
   List<db.ParametridiprogettoS> parametridiprogetto = null;
  
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    /**
     * stringa che identifica una specifica connessione con il database
     */
    private String connessione ="";
    /**
     * identificativo numerico del trattamento
     */
    private int idtrattamento = 0;
    /**
     * identificativo numerico dello scenario
     */
    private long idscenario = 0;
    /**
     * recupera tutti i parametri di progetto per uno
     * specifico trattamento. Le informazioni dello scenario le prende dal session bean DettaglioCuaa
     * @param idtrattamento il valore numerico del trattamento
     * @param idscenario il valore numerico di uno specifico scenario di una azienda
     * */
    public Parametridiprogetto(String connessione,int idtrattamento,long idscenario)
    {
        this.connessione = connessione ;
        this.idscenario = idscenario;
        this.idtrattamento = idtrattamento;
        parametridiprogetto = new LinkedList<db.ParametridiprogettoS>();
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory(this.connessione);
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
        
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
        
        
       /**
         * mi serve per cancellare la cache dell'entity manager
         */
          entityManager.getEntityManagerFactory().getCache().evictAll();
      /**
         * recupero il session bean dettaGlioCuaa
         */
        // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        // DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         
         Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", idscenario);
         db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
         
         Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",idtrattamento);
         db.TrattamentoS tratta = (db.TrattamentoS)q2.getSingleResult();
         
         
        Query q = entityManager.createQuery("SELECT t FROM ParametridiprogettoS t WHERE  t.idTrattamento=?2 AND t.idScenario =?1");
        q.setParameter(1,sce );
        q.setParameter(2, tratta);
        
        parametridiprogetto = (List<db.ParametridiprogettoS>)q.getResultList();
        
        
        
        Connessione.getInstance().chiudi();
        
        
    }
    
   /**
     * @return la lista dei parametri di progetto dello specifico trattamento
     */
  public List<db.ParametridiprogettoS> getlistaparametri()
  {
      
        
        
      return  parametridiprogetto ;      
                     
  }
  
  
  /**
   * aggiorna la lista dei parametri di progetto 
   */
  public void aggiornaLista()
  {
      
        parametridiprogetto = new LinkedList<db.ParametridiprogettoS>();
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory(this.connessione);
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
        
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal2");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
        
      
      /**
         * recupero il session bean dettaGlioCuaa
         */
        // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        // DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         
         Query q1 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", idscenario);
         db.ScenarioI sce =(db.ScenarioI) q1.getSingleResult();
         
         Query q2 = entityManager.createNamedQuery("TrattamentoS.findById").setParameter("id",idtrattamento);
         db.TrattamentoS tratta = (db.TrattamentoS)q2.getSingleResult();
         
         
        Query q = entityManager.createQuery("SELECT t FROM ParametridiprogettoS t WHERE  t.idTrattamento=?2 AND t.idScenario =?1");
        q.setParameter(1,sce );
        q.setParameter(2, tratta);
        
        parametridiprogetto = (List<db.ParametridiprogettoS>)q.getResultList();
        
        
        Connessione.getInstance().chiudi();
  }
  
  
  
  /**
   * cerca il parametro nella lista dei parametri di progetto che verifica nome e contenuto attributo
   * oppure nome e discriminante. int tipo mi informa se la ricerca deve essere fatta usando il solo nome oppure il nome e contenutoaatributo oppue
   * nome e discriminante.tipo == 0 solo il nome tipo ==1 nome e contenutoattibuto tipo == 2 nome e discriminante
   * @param nome del parametro di progetto presente nella tabella nomeparametrdiprogetto
   * @param contenutoattributo il valore numerico che conttrastingue il tipo di entitaattributo a cui è collegato il nome
   * del parametro
   * @return una istanza di parametridiprogetto
   */
  public db.ParametridiprogettoS getParametrodiprogetto(int tipo,String nome, String contenutoattributo,String contenutoattributo1,String discriminante)
  {
      if(tipo > 3 || tipo < 0)
         return null;
      
      
      ListIterator<db.ParametridiprogettoS> iterparametri = parametridiprogetto.listIterator();
      db.ParametridiprogettoS temp = null;
      
     switch(tipo)
     {
         /**
          * caso ricerca in base al solo nome
          */
         case 0 :
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) ) {
                  break;
              }
          }
          
          break;
         /**
          * caso ricerca in base al nome ed al contenutoattributo
          */    
         case 1 :
    
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) && temp.getContenutoattributo().equals(contenutoattributo)) {
                  break;
              }
          }
             break;
             
         /**
          * caso ricerca in funzione del nome e del discriminante
          */    
         case 2: 
              while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) && temp.getDiscriminante().equals(discriminante)) {
                  break;
              }
          }
             break;
        /**
          * caso ricerca in funzione del nome e del conteutoatributo e contenutoattributo1
          */    
         case 3: 
              //System.out.println("\n\n\n++++++++sono nel caso 3 +++++++++++++++\n\n\n");
              while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getNome().equals(nome) &&  temp.getEntitacoinvolte()==2 && temp.getContenutoattributo().equals(contenutoattributo) && temp.getContenutoattributo1().equals(contenutoattributo1)) {
                  break;
              }
          }
             break;
             
      }
      
      return temp;
  }

  
  
  /**
   * cerca il parametro nella lista dei parametri di progetto che verifica idnome e contenuto attributo
   * oppure idnome e discriminante. int tipo mi informa se la ricerca deve essere fatta usando il solo idnome oppure il idnome e contenutoaatributo oppue
   * idnome e discriminante.tipo == 0 solo il nome tipo ==1 nome e contenutoattibuto tipo == 2 nome e discriminante
   * @param idnome il valor enumerico del parametro di progetto presente nella tabella nomeparametrdiprogetto
   * @param contenutoattributo il valore numerico che conttrastingue il tipo di entitaattributo a cui è collegato il nome
   * del parametro
   * @return una istanza di parametridiprogetto
   */
  public db.ParametridiprogettoS getParametrodiprogetto(int tipo,int  idnome, String contenutoattributo,String contenutoattributo1,String discriminante)
  {
      if(tipo > 3 || tipo < 0)
         return null;
      if(idnome < 0)
          return null;
      
      
    // System.out.println(this.getClass().getCanonicalName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " idnome111  " + idnome + " contenutoattributo" +contenutoattributo+" contenutoattributo1 " + contenutoattributo1 + " discriminante " + discriminante );

      
      ListIterator<db.ParametridiprogettoS> iterparametri = parametridiprogetto.listIterator();
      db.ParametridiprogettoS temp = null;
      
     switch(tipo)
     {
         /**
          * caso ricerca in base al solo nome
          */
         case 0 :
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if (temp.getIdNomeparametro().getId() == idnome ) {
                  break;
              }
          }
          
          break;
         /**
          * caso ricerca in base al nome ed al contenutoattributo
          */    
         case 1 :
    
          while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              if ((temp.getIdNomeparametro().getId() == idnome) && temp.getContenutoattributo().equals(contenutoattributo)) {
                  break;
              }
          }
             break;
             
         /**
          * caso ricerca in funzione del nome e del discriminante
          */    
         case 2: 
             
              
              while (iterparametri.hasNext()) {
                 temp = iterparametri.next();
                 //System.out.println("confronto " + idnome + " discriminante " + discriminante +" con  temp.getIdNomeparametro().getId() " + temp.getIdNomeparametro().getId() + " discriminante " + temp.getDiscriminante());
                 if ((temp.getIdNomeparametro().getId() == idnome) && temp.getDiscriminante().equals(discriminante)) {
                     //System.out.println("trovato " + temp.getIdNomeparametro().getId() + " discriminante " + temp.getDiscriminante());

                     break;
                 }
                 
                 //System.out.println("continuo a cercare ");
             }
             break;
         /**
          * caso ricerca in funzione del idnome e del conteutoatributo e contenutoattributo1
          */    
         case 3: 
            // System.out.println("\n\n\n++++++++sono nel caso 3 +++++++++++++++\n\n\n");
             // System.out.println("confronto " + idnome + " contenutoattributo "+ contenutoattributo+ " contenutoattributo1 "+contenutoattributo1 );

              while (iterparametri.hasNext()) {
              temp = iterparametri.next();
              System.out.println("confronto " + idnome + " temp.getContenutoattributo() "+temp.getContenutoattributo() + " temp.getContenutoattributo() "+temp.getContenutoattributo1() + " contenutoattributo "+ contenutoattributo + " contenutoattributo1 "+ contenutoattributo1);
              if (temp.getIdNomeparametro().getId()==idnome &&  temp.getEntitacoinvolte()==2 && temp.getContenutoattributo().equals(contenutoattributo) && temp.getContenutoattributo1().equals(contenutoattributo1)) {
                  break;
              }
          }
             break;
             
      }
      
      return temp;
  }
  
  /**
   * 
   * @param tipoliquame valore intero che rappresenta:bovino,suino,avicolo,altri
   * @param caratteristica valore numerico che rappresenta:liquame,azoto,azoto ammoniacale,sostanza secca,solidi volatili,fosforo totale,potassio
   * @return una riga della tabella efficienza come da modulo separazione cioè il c1,c2,m1,m2 di una specifica caratteristica chimica del refluo e 
   * di un particolare refluo 
   */
  public double[] getEfficienze(int tipoliquame,int caratteristica)
  {
       double[] ritorno = new double[4];
      
      // System.out.println("tipoliquame " + tipoliquame + " caratteristica " + caratteristica);
       
       
       ListIterator<db.ParametridiprogettoS> iterparametri = parametridiprogetto.listIterator();
       db.ParametridiprogettoS temp = null;
       
       while(iterparametri.hasNext())
       {
           temp = iterparametri.next();
           
           
           if(temp.getEntitacoinvolte() == 2 && temp.getContenutoattributo().equals(String.valueOf(tipoliquame)) && temp.getContenutoattributo1().equals(String.valueOf(caratteristica)))
           {
           if(temp.getIdNomeparametro().getNome().equals("c1"))
           {
               if(temp.getValore() !=  null) {
                   ritorno[0] = Double.parseDouble(temp.getValore());
               }
               else {
                   ritorno[0] = 0d;
               }
           }
           
            if(temp.getIdNomeparametro().getNome().equals("c2"))
           {
               if(temp.getValore() !=  null) {
                    ritorno[1] = Double.parseDouble(temp.getValore());
               }
               else {
                   ritorno[1] = 0d;
               }
           }
            
            
            if(temp.getIdNomeparametro().getNome().equals("m1"))
           {
                if(temp.getValore() !=  null) {
               ritorno[2] = Double.parseDouble(temp.getValore());
                }else
                {
                    ritorno[2] = 0d; 
                }
           }
            
            
           if(temp.getIdNomeparametro().getNome().equals("m2"))
           {
                if(temp.getValore() !=  null) {
               ritorno[3] = Double.parseDouble(temp.getValore());
                }
                else
                {
                   ritorno[3] = 0d;    
                }
           }  
           
           }
           
       }
       
       
       return ritorno;
      
  }
  
  
    /**
     * @return the connessione
     */
    public String getConnessione() {
        return connessione;
    }

    /**
     * @param connessione the connessione to set
     */
    public void setConnessione(String connessione) {
        this.connessione = connessione;
    }
    
    
}
