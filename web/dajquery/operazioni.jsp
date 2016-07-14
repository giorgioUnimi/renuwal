<%@page import="operativo.dettaglio.Connessione"%>
<%@page import="javax.persistence.EntityTransaction"%>
<%@page import="javax.persistence.Query"%>
<%@page import="javax.persistence.Persistence"%>

<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="org.eclipse.persistence.jpa.JpaEntityManager"%>
<%@page import="org.eclipse.persistence.sessions.UnitOfWork"%>
<%@page import="org.eclipse.persistence.sessions.server.ServerSession"%>



<%

            EntityManagerFactory entityManagerFactory = null;
            EntityManager entityManager = null;
            JpaEntityManager jpa;
            ServerSession serverSession;

        String idazienda ="";
        String descrizione="";
        String anno="";
        /*
 * l id dell'operazione indica di quale operaizione sto trattando secondo la lista che segue
 * 1 - creazione scenario ritonrna il numero dello scenario creato duplicando secondo l'id dello scenario creato
 * gli allevamenti ,stoccaggi ed acqua_stoccaggi dello scenario 0 che fa da riferimento
 * */

        String idoperazione = request.getParameter("idoperazione");
        idoperazione = idoperazione.trim();
        System.out.println("idoperazione  " + idoperazione);
        long ritorno =0;
        operativo.OperazioneDB opera = null;
         
         
             /*
              * operazioni.jsp viene chiamata mediante ajax da operazioni.js. Operazioni.js passa operazioni.jsp le
              * richieste di una specifica operaizone pasando un valore intero ed una serie di parametri. Il parametro per capire
              * quale operazione fare è idoperazione  
              * idoperazione 1 creasceanrio
              * idoperazione 2 eliminascenario 
              * idoprerazione 3 ottieni idscenario a partire da cuaa e id         
              * */
        switch (Integer.parseInt(idoperazione)) {
                case 1:
                    idazienda = request.getParameter("idazienda");
                    System.out.println("cuaa  " + idazienda);

                    descrizione = request.getParameter("descrizione");
                    System.out.println("descrizione  " + descrizione);
                    
                    anno = request.getParameter("anno");
                    System.out.println("anno  " + anno);

                    opera = new operativo.OperazioneDB();
                    ritorno = 0;

                    ritorno = opera.creaScenario(idazienda, descrizione, anno);

                    System.out.println("valore di ritorno da crea scenario " +ritorno);


                    break;

                case 2:
                    //idazienda = request.getParameter("idazienda");
                    String idscenario = request.getParameter("idscenario");
                    idscenario = idscenario.trim();
                    opera = new operativo.OperazioneDB();
                    ritorno = 0;

                    ritorno = opera.eliminaScenario(idscenario);

                    System.out.println("da operazioni.jsp valore di ritorno " + ritorno);



                    out.println(ritorno);

                    break;

                case 3:
                   /* cuaa = request.getParameter("cuaa");
                    String id1 = request.getParameter("id");
                    
                    opera = new operativo.OperazioneDB();
                    long ritorno1 = opera.getidscenario(cuaa, Integer.parseInt(id1));
                    
                    out.println(ritorno1);*/
                    break;
                    
                    
                   /**
                   *dato il cuaa finto ritorna 
                   *il vero cuaa dell'azienda viene usata 
                   *dal lato jquery delle pagine
                   */
  
              case 4:
                  
                   /*idazienda = request.getParameter("idazienda");
                   if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione1 = Connessione.getInstance();
                               entityManager = connessione1.apri("renuwal1");
                               entityManagerFactory = connessione1.getEntityManagerFactory();
                            }
                      
                   Query q1 = entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto", idazienda);
                   db.AziendeI az =(db.AziendeI)q1.getSingleResult();       

                   
                    out.println(az.getCuaa().trim());*/
                    break;                                          

            }
        
       

       /* EntityManagerFactory entityManagerFactory;
        EntityManager entityManager;
        JpaEntityManager jpa;
        ServerSession serverSession;
        entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio9");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        
      //  Query q = entityManager.createQuery("SELECT  l.pesoVivoKg FROM scenario_i");
       
        
         EntityTransaction tx = entityManager.getTransaction();
         tx.begin(); 
         Double numeroRandom = Math.random() * 100; 
         String numeroR = String.valueOf(numeroRandom.intValue());
         db.AziendeI azienda = new db.AziendeI("ab1"+numeroR);
         azienda.setDesComunePresentazione("misintomilano");
         azienda.setRagioneSociale("azienda azienda azienda");
        
        
         entityManager.persist(azienda);
         tx.commit();
         entityManager.close();
         
         out.println("sono in operazioni idoperazione : " +idoperazione +" cuaa " + cuaa +" descrizone " + descrizione );*/
                 

%>
