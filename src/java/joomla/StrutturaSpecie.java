/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joomla;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *strutturaspecie contiene la struttura dati a cascata popolata una sola volta specie-> categoria->allevamento->stabulazione
 * usata nei menu a tendina della pagina allevamenti di un determinato scenario di una determinata azienda
 * @author giorgio
 */
public class StrutturaSpecie {
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    
   //List<MioTipo> specie = new LinkedList<MioTipo>();
    private static StrutturaSpecie istanza;
    
    private StrutturaSpecie()
    {
         tuttelespecie();
    }
    
    public static StrutturaSpecie getIstanza() {
        if (istanza == null) {
            {
                istanza = new StrutturaSpecie();
                System.out.println("creo una nuova istanza di strutturaspecie");
            }
        }

        return istanza;
    }
    
    private MioTipo creaTipo(String tipo)
    {
        MioTipo miotipo = new MioTipo();
        miotipo.setNome(tipo);
        
        return miotipo;
    }
     
    private  MioTipo radice = null; 
    
    /**
     * popola la struttura dati a cascata specie -> categoria -> allevammento -> categoria
     * 
     */
    private void tuttelespecie()
    {
        
            setRadice(new MioTipo());
            getRadice().setNome("radice");
        
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal1");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
          
           Query q = entityManager.createNamedQuery("SpecieS.findAll");
           List<db.SpecieS> sp =(List<db.SpecieS>)q.getResultList();
           
           ListIterator<db.SpecieS> iteraSpecie = sp.listIterator();
          
           
           MioTipo specie1 = null;
           MioTipo categoria1 = null;
           MioTipo allevamento1 = null;
           MioTipo stabulazione1 = null;
           /**
            * ciclo sulle specie
            */
           while(iteraSpecie.hasNext())
           {
               db.SpecieS specieA = iteraSpecie.next();
               
               specie1 = creaTipo(specieA.getDesSpecie());
               getRadice().getLista().add(specie1);
               
               
              // System.out.println("specie -- : " + radice.getLista().get(radice.getLista().size()-1).getNome());
               /**
                * recupero le categorie di quella specie
                */
               Iterator<db.CategoriaSpecie> catspecie =  specieA.getCategoriaSpecieCollection().iterator();
               
             while(catspecie.hasNext())
             {
                 db.CategoriaSpecie catspecieTemp = catspecie.next();
                 db.CategoriaS categoria = catspecieTemp.getCodiceCategoria();
                 categoria1 = creaTipo(categoria.getDesCatAllev());
                 specie1.getLista().add(categoria1);
                 
                 /**
                  * recupero gli allevamenti per una determinata categoria
                  */
                 
                 Iterator<db.AllevamentoCategoria> catallevamento = categoria.getAllevamentoCategoriaCollection().iterator();
                 
                 while(catallevamento.hasNext())
                 {
                     db.AllevamentoCategoria allevamentocategoria = catallevamento.next();
                     db.TipoallevamentoS tipoallevamento = allevamentocategoria.getTipoAllevamentoBId();
                     allevamento1 = creaTipo(tipoallevamento.getDesAllevamento());
                     categoria1.getLista().add(allevamento1);
                     
                     /**
                      * itero sulle stabulazioni dato un allevamento
                      */
                    // Iterator<db.StabulazioneAllevamento> stabulazione = tipoallevamento.getStabulazioneAllevamentoCollection().iterator();
                     
                     
                     q = entityManager.createQuery("SELECT DISTINCT l FROM StabulazioneAllevamentoCategoria l WHERE l.categoriabSId=?1 AND l.tipoAllevamentoBId=?2");
                     q.setParameter(1, categoria);
                     q.setParameter(2, tipoallevamento);
                     

                     List<db.StabulazioneAllevamentoCategoria> results = q.getResultList();
                     Iterator<db.StabulazioneAllevamentoCategoria> iterresults = results.iterator();
                     
                     while(iterresults.hasNext())
                     {
                         stabulazione1 = creaTipo(iterresults.next().getStabulazionebSId().getDesStabulazione());
                         allevamento1.getLista().add(stabulazione1); 
                     }
                     
                     
                     
                    /* while(stabulazione.hasNext())
                     {
                         db.StabulazioneAllevamento stabulazioneallevamento = stabulazione.next();
                         db.TipostabulazioneS tipostabulazione = stabulazioneallevamento.getStabulazionebSId();
                         stabulazione1 = creaTipo(tipostabulazione.getDesStabulazione());
                         allevamento1.getLista().add(stabulazione1);
                     }*/
                 }
                 
                 //System.out.println("categoria -- : " + specie1.getLista().get(specie1.getLista().size()-1).getNome());
             }
             
             
               //System.out.println(specieA.getDesSpecie());
           }
           
          Connessione.getInstance().chiudi();
    }
    
    
    
//   private void tuttelespecie2()
//    {
//        
//            setRadice(new MioTipo());
//            getRadice().setNome("radice");
//        
//          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
//                            {
//                               Connessione connessione = Connessione.getInstance();
//                               entityManager = connessione.apri("renuwal1");
//                               entityManagerFactory = connessione.getEntityManagerFactory();
//                            }
//          
//           Query q = entityManager.createNamedQuery("SpecieS.findAll");
//           List<db.SpecieS> sp =(List<db.SpecieS>)q.getResultList();
//           
//           ListIterator<db.SpecieS> iteraSpecie = sp.listIterator();
//          
//           
//           MioTipo specie1 = null;
//           MioTipo categoria1 = null;
//           MioTipo allevamento1 = null;
//           MioTipo stabulazione1 = null;
//           /**
//            * ciclo sulle specie
//            */
//           while(iteraSpecie.hasNext())
//           {
//               db.SpecieS specieA = iteraSpecie.next();
//               
//               specie1 = creaTipo(specieA.getDesSpecie());
//               getRadice().getLista().add(specie1);
//               
//               
//              // System.out.println("specie -- : " + radice.getLista().get(radice.getLista().size()-1).getNome());
//               /**
//                * recupero le categorie di quella specie
//                */
//               Iterator<db.CategoriaSpecie> catspecie =  specieA.getCategoriaSpecieCollection().iterator();
//               
//             while(catspecie.hasNext())
//             {
//                 db.CategoriaSpecie catspecieTemp = catspecie.next();
//                 db.CategoriaS categoria = catspecieTemp.getCodiceCategoria();
//                 categoria1 = creaTipo(categoria.getDesCatAllev());
//                 specie1.getLista().add(categoria1);
//                 
//                 /**
//                  * recupero gli allevamenti per una determinata categoria
//                  */
//                 Iterator<db.AllevamentoCategoria> catallevamento = categoria.getAllevamentoCategoriaCollection().iterator();
//                 
//                 while(catallevamento.hasNext())
//                 {
//                     db.AllevamentoCategoria allevamentocategoria = catallevamento.next();
//                     db.TipoallevamentoS tipoallevamento = allevamentocategoria.getTipoAllevamentoBId();
//                     allevamento1 = creaTipo(tipoallevamento.getDesAllevamento());
//                     categoria1.getLista().add(allevamento1);
//                     
//                     /**
//                      * itero sulle stabulazioni dato un allevamento
//                      */
//                     Iterator<db.StabulazioneAllevamento> stabulazione = tipoallevamento.getStabulazioneAllevamentoCollection().iterator();
//                     
//                    /* q = entityManager.createQuery("SELECT DISTINCT l.desStabulazione FROM Stabulaziones l WHERE l.desSpecie=?1 AND l.desCatAllev=?2 AND l.desAllevamento=?3");
//                     q.setParameter(1, specieA);
//                     q.setParameter(2, categoria);
//                     q.setParameter(3, tipoallevamento.getDesAllevamento());
//
//                     List<String> results = q.getResultList();
//                     Iterator<String> iterresults = results.iterator();
//                     
//                     while(iterresults.hasNext())
//                     {
//                         stabulazione1 = creaTipo(iterresults.next());
//                         allevamento1.getLista().add(stabulazione1); 
//                     }*/
//                     
//                     
//                     
//                    /* while(stabulazione.hasNext())
//                     {
//                         db.StabulazioneAllevamento stabulazioneallevamento = stabulazione.next();
//                         db.TipostabulazioneS tipostabulazione = stabulazioneallevamento.getStabulazionebSId();
//                         stabulazione1 = creaTipo(tipostabulazione.getDesStabulazione());
//                         allevamento1.getLista().add(stabulazione1);
//                     }*/
//                 }
//                 
//                 //System.out.println("categoria -- : " + specie1.getLista().get(specie1.getLista().size()-1).getNome());
//             }
//             
//             
//               //System.out.println(specieA.getDesSpecie());
//           }
//           
//          Connessione.getInstance().chiudi();
//    }
    
    
   /**
    * utility interna per mostrare il contenuto della struttura dati
    * @param lista 
    */ 
    public void mostraContenuto(List<MioTipo> lista)
    {
        ListIterator<MioTipo> iterSpecie = lista.listIterator();
        
        while(iterSpecie.hasNext())
        {
            MioTipo miotipo = iterSpecie.next();
            
            if(miotipo.getLista().isEmpty()) 
            {
                    System.out.println(miotipo.getNome());
            }
            else
            {
                    System.out.println(miotipo.getNome());
                    mostraContenuto(miotipo.getLista());
            }
        }
    }
    
    /**
     * ritorna una lista di stringhe contenente le specie animali
     */
    public List<String> getSpecie()
    {
        List<String> ret = new LinkedList<String>();
        ret.add("----");
         MioTipo temp = null;
         
        if(radice != null)
        {
            
            ListIterator<MioTipo> itera = radice.getLista().listIterator();
            
            while(itera.hasNext())
            {
                temp = itera.next();
                ret.add(temp.getNome());
                //System.out.println("specie in getSpecie di StrutturaSpecie " + temp.getNome());
            }
         }
        
        
        return ret;
    }      
    
    /**
     * ritorna la lista delle categorie data una detetminata specie 
     * esaminando il contenuto presente nella lista a partire dalla radice MioTipo radice
     */
    public List<String> getCategorie(String specie)
    {
         List<String> ret = new LinkedList<String>();
          ret.add("----");
        MioTipo temp = null;
        MioTipo cate = null;
        ListIterator<MioTipo> iteraCategoria = null;
        
         
        if(radice != null)
        {
            
            ListIterator<MioTipo> itera = radice.getLista().listIterator();
            
            while(itera.hasNext())
            {
                temp = itera.next();
                //ret.add(temp.getNome());
                
                if(temp.getNome().equals(specie))
                {
                    iteraCategoria = temp.getLista().listIterator();
                    
                    cate = null;
                    
                    while(iteraCategoria.hasNext())
                    {
                        cate = iteraCategoria.next();
                        ret.add(cate.getNome());
                       // System.out.println("specie "+temp.getNome()+" categoria " + cate.getNome());
                    }
                }
                //System.out.println(temp.getNome());
            }
         }
        
        
        return ret;
    }
   
    
    /**
     * ritorna la lista di stringhe degli allevamenti dati specie e categoria
     * a partire dalla radice MioTipo radice popolato nel metodo creaStruttura()
     * @param specie
     * @param categoria
     * @return 
     */
    public List<String> getAllevamenti(String specie,String categoria)
    {
        List<String> ret = new LinkedList<String>();
         ret.add("----");
        MioTipo temp = null;
        MioTipo cate = null;
        ListIterator<MioTipo> iteraCategoria = null;
        ListIterator<MioTipo> iterAlleva = null;
        MioTipo alle = null;
        
         
        if(radice != null)
        {
            
            ListIterator<MioTipo> itera = radice.getLista().listIterator();
            
            while(itera.hasNext())
            {
                temp = itera.next();
                //ret.add(temp.getNome());
                /**
                 * cerco la specie ed ottengo la sottolista delle categoria
                 */
                if(temp.getNome().equals(specie))
                {
                    iteraCategoria = temp.getLista().listIterator();
                    
                    cate = null;
                    
                    while(iteraCategoria.hasNext())
                    {
                        cate = iteraCategoria.next();
                        
                        if(cate.getNome().equals(categoria))
                        {
                             iterAlleva = cate.getLista().listIterator();
                             
                             alle = null;
                             
                             while(iterAlleva.hasNext())
                             {
                                 alle = iterAlleva.next();
                                 
                                 ret.add(alle.getNome());
                                 
                                 //System.out.println("specie " + specie + " categoria " + categoria + " allevamento " + alle.getNome());
                             }
                        }
                        //ret.add(cate.getNome());
                       // System.out.println("specie "+temp.getNome()+" categoria " + cate.getNome());
                    }
                }
                //System.out.println(temp.getNome());
            }
         }
        
        
        return ret;
    }
            
    
    /**
     * ritorna la lista delle stabulazioni dati la specie,categoria ed allevamento
     * @param specie
     * @param categoria
     * @param allevamento
     * @return 
     */
    public List<String> getStabulazioni(String specie,String categoria,String allevamento)
    {
        List<String> ret = new LinkedList<String>();
         ret.add("----");
        MioTipo temp = null;
        MioTipo cate = null;
        ListIterator<MioTipo> iteraCategoria = null;
        ListIterator<MioTipo> iterAlleva = null;
        MioTipo alle = null;
        ListIterator<MioTipo> iterStabula = null;
        MioTipo stabu = null;
        
        if(radice != null)
        {
            
            ListIterator<MioTipo> itera = radice.getLista().listIterator();
            
            while(itera.hasNext())
            {
                temp = itera.next();
                //ret.add(temp.getNome());
                /**
                 * cerco la specie ed ottengo la sottolista delle categoria
                 */
                if(temp.getNome().equals(specie))
                {
                    iteraCategoria = temp.getLista().listIterator();
                    
                    cate = null;
                    /**
                     * cerco la categoria
                     */
                    while(iteraCategoria.hasNext())
                    {
                        cate = iteraCategoria.next();
                        
                        if(cate.getNome().equals(categoria))
                        {
                             iterAlleva = cate.getLista().listIterator();
                             
                             alle = null;
                             /*
                              * cerco l'allevamento
                              */
                             while(iterAlleva.hasNext())
                             {
                                 alle = iterAlleva.next();
                                 
                                 if(alle.getNome().equals(allevamento))
                                 {
                                      iterStabula = alle.getLista().listIterator();
                                      
                                      stabu = null;
                                      
                                      while(iterStabula.hasNext())
                                      {
                                          stabu = iterStabula.next();
                                          
                                          ret.add(stabu.getNome());
                                          
                                         // System.out.println("specie " + specie + " categoria " + categoria + " allevamento " + alle.getNome()+ " stabulazione " + stabu.getNome());
                                      }
                                 }
                                 //ret.add(alle.getNome());
                                 
                                 //System.out.println("specie " + specie + " categoria " + categoria + " allevamento " + alle.getNome());
                             }
                        }
                        //ret.add(cate.getNome());
                       // System.out.println("specie "+temp.getNome()+" categoria " + cate.getNome());
                    }
                }
                //System.out.println(temp.getNome());
            }
         }
        
        
        return ret;
    }
    
    /**
     * crea la struttura completa che contiene per ogni specie le categorie , per ogni categoria gli allevamenti e 
     * per ogni allevamento le stabulazioni
     */
   /* public void creaStruttura()
    {
        
         System.out.println("creo una nuova struttura di strutturaspecie");
        tuttelespecie();
        
       /* for(int i = 0; i < 10; i++)
            System.out.println("----------------------------mostra contenuto--------------------------------");
        
        
        mostraContenuto(this.getRadice().getLista());
        
        
         for(int i = 0; i < 10; i++)
            System.out.println("----------------------------mostra specie--------------------------------");
         
         this.getSpecie();
         
          for(int i = 0; i < 10; i++)
            System.out.println("----------------------------mostra categoria--------------------------------");
          
          this.getCategorie("AVICOLI");
          
          
          for(int i = 0; i < 10; i++)
            System.out.println("----------------------------mostra allevamenti--------------------------------");
          
          this.getAllevamenti("AVICOLI","TACCHINI - FEMMINA");
          
          
          for(int i = 0; i < 10; i++)
            System.out.println("----------------------------mostra stabulazione--------------------------------");
          
          this.getStabulazioni("AVICOLI","TACCHINI - FEMMINA","TACCHINI");
          */
   // }

    /**
     * @return the radice
     */
    public MioTipo getRadice() {
        return radice;
    }

    /**
     * @param radice the radice to set
     */
    public void setRadice(MioTipo radice) {
        this.radice = radice;
    }
    
}
