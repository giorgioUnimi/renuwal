<%@page import="javax.jms.ServerSession"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="java.util.ListIterator"%>
<%@page import="java.io.File"%>
<%@page import="operativo.LetturaRisultati"%>
<%@page import="javax.persistence.NamedQuery"%>
<%@page import="operativo.dettaglio.Connessione"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="javax.persistence.Query"%>
<%@page import="ager.ContenitoreReflui"%>
<%@page import="ager.Refluo"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.List"%>
<%@page import="ager.trattamenti.ContenitoreAziendale"%>
<%@page import="operativo.Modello"%>
<%@page import="javax.xml.transform.TransformerException"%>
<%@page import="operativo.RefluiAzienda"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="javax.xml.transform.TransformerConfigurationException"%>
<%@page import="operativo.dettaglio.DettaglioCuaa"%>
<%@page import="java.util.Random"%>
<%@page import="org.w3c.dom.Element"%>
<%@page import="operativo.dettaglio.InputOutputXml"%>
<%@page import="org.eclipse.persistence.jpa.JpaEntityManager"%>


<%!
  private  String getRandomNumber(int digCount) {
    Random rr = new Random();   
    
    StringBuilder sb = new StringBuilder(digCount);
    for(int i=0; i < digCount; i++)
        sb.append((char)('0' + rr.nextInt(10)));
    return sb.toString();
}   


  /*
 * stampa sul web il contenitore iniziale e totale di tutte le aziende di un gruppo
 * */
  private void tabellaIniziale(ContenitoreReflui contenitoreReflui,javax.servlet.jsp.JspWriter dinamicOut)
                   {
      
                           try{
                           dinamicOut.println("<table class=\"localizzazioneTable2\">");
                           dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(kg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>PO(kg)</th><th>KO(kg)</th></tr></thead>");
                          
                           }catch( Exception e)
                               {
                                e.printStackTrace();
                               
                           }
                                                   
                           
                           String[] tipi = contenitoreReflui.getTipologie();



                           for (String tipologia : tipi) {
                               
                               Refluo ref = contenitoreReflui.getTipologia(tipologia);
                                try{
                               dinamicOut.println("<tr><td>"+tipologia+"</td><td>"+ref.getMetricubi()+"</td><td>"+ref.getAzotototale()+"</td><td>"+ref.getAzotoammoniacale()+"</td><td>"+ref.getSostanzasecca()+"</td><td>"+ref.getSolidivolatili()+"</td><td>"+ref.getFosforototale()+"</td><td>"+ref.getPotassiototale()+"</td></tr>");
                               }catch( Exception e)
                               {
                                e.printStackTrace();
                               
                           }
                               
                           }
                           
                            try{
                               dinamicOut.println("</table>");
                               }catch( Exception e)
                               {
                                e.printStackTrace();
                               
                           }
                                   
                                   
                       }

        
%>

<%

    


    //recupero il numero random 
      String numerorandom1 = request.getParameter("numerorandom");
      
      System.out.println("dajquery/gruppoaziende.jsp " + numerorandom1 );
      
      
       EntityManagerFactory entityManagerFactory = null;
      EntityManager entityManager = null;
      JpaEntityManager jpa = null;
     
       /**
        *viene eseguito da localizzazione2.jsp e viene impiegfato per passare tramite file xml 
        *le informazioni al solutore e recuperare la risposta da mostrare nella pagina localizzazione2.jsp
        */
        
        DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
                
        System.out.println("utente loggato : " + dettaglioCuaa.getUtente());
        /**
         * creo una classe che mi servirà per generare il file xml che 
         * farà da input al file xml
         */
        InputOutputXml inputoutputxml = new InputOutputXml();
        /**
         * recupero un riferimento al nodo root del documento xml che mi servirà
         * anche in altri metodi per ulteriori inserimenti
         */
        Element elemento = inputoutputxml.generaXml();
        
        inputoutputxml.inserisciData(elemento);
        inputoutputxml.inserisciUtente(elemento,dettaglioCuaa.getUtente());
         //inputoutputxml.inserisciUtente(elemento,"azienda1");
        
         
       String operazione = request.getParameter("operazionescelta");
       if(operazione.equals("rankmulti"))    
            inputoutputxml.selezioni(elemento, 1, 0, 0);
        
         if(operazione.equals("miglioremulti"))    
            inputoutputxml.selezioni(elemento, 0, 1, 0);
         if(operazione.equals("singolamulti"))
                         {
                            String alternativa = request.getParameter("alternativa");
                            inputoutputxml.selezioni(elemento, 0, 0, Integer.parseInt(alternativa));
                       }
         
         
        inputoutputxml.consortile(elemento, 2);
         
         
         
      /**
        * recupero i parametri delle minimizzazioni chemi arrivano   
        * da localizzazione2.jsp che sono le percentuali dei costi,energia ed emissioni
        * che andro ad aggiungere al file xml sotto il tag minimizzazioni
        */
        String costo = request.getParameter("costo");
        String energia = request.getParameter("energia");
        String emissionia = request.getParameter("emissionia");
        String emissionig = request.getParameter("emissionig");
        
        
        //out.println("costo " +costo +" energia " + energia + " emissioni " + emissioni);
        /**
        *le due righe seguenti sono da verificare 
        *sopratutto le minimizzazioni che sono parametriche devono arrivare
        *dalla pagina web
        */
         System.out.println("sono riga 85 costo " + costo +" emissionia  " +emissionia +" emissionig  " +emissionig +" energia " +energia); 
        inputoutputxml.minimizzazioni1(elemento,Double.parseDouble(costo),Double.parseDouble(emissionia),Double.parseDouble(emissionig),Double.parseDouble(energia));
         System.out.println("sono riga 87"); 
        
         



           
      

          
          /**
     * contiene le caratteristiche chimiche delle singole 4 tipologie di refluo
     * ovvero liquame bovino , liquamne suino , letame bovino , letame suinno
     */
     List<Refluo> listaCaratteristiche = new LinkedList<Refluo>();
     List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
     List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
     ContenitoreReflui contenitoreIniziale = null;
     /**
     *usato come somma dei contenitori di reflui
     */
      ContenitoreReflui contenitoreTotale = new ContenitoreReflui();
       
         /**
         *recupera la lista delle aziende e cicla su di essa per
         *popolare la seconda parte del file di input al solutore
        */
         String[] aziende = request.getParameter("aziende").split("-");
         
         
         
         /**
         *mi connetto al db se non sonon ancora connesso
         */
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen())) {
                 Connessione connessione = Connessione.getInstance();
                 entityManager = connessione.apri("provagiorgio13");
                 entityManagerFactory = connessione.getEntityManagerFactory();
             }
             //lo scenario mi arriva nella forma "scenario : 0" per cui nella posizione modulo 3 devo togliere la scritta scenario :
             // e lasciar e il numero
         /*
              * recupero la prima azienda per passare i parametri i progetto 
              * dellì'azienda all'xml di input al solutore
              * */
             String azienda1 = aziende[3];
             int scen1 = Integer.parseInt(azienda1.replace("scenario: ", ""));
             System.out.println("scen1 " + scen1);
             
             
             //il seguente booleano mi dice se l'utente è azienda1 o guest : se true è guest
             boolean utenteospite = false;
             Query azQ1 ;
             
             if(dettaglioCuaa.getUtente().equals("azienda1"))
                { 
                 azQ1 = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", aziende[1]);
                 System.out.println("query con cuaa azienda1 " + aziende[1]); 
                }
             else
              {
                 System.out.println("query con cuaa finto  azienda1 "  + aziende[1]);
                azQ1 = entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto", aziende[1]);
                utenteospite = true ;
              }  
             
             db.AziendeI az1 = (db.AziendeI) azQ1.getSingleResult();

             System.out.println("scen1 " + scen1 + " azienda " + az1.getCuaa());

             TypedQuery<db.ScenarioI> q1 = entityManager.createQuery("SELECT a FROM ScenarioI a WHERE a.id=?1 AND a.cuaa=?2", db.ScenarioI.class);
             q1.setParameter(2, az1);
             q1.setParameter(1, scen1);
             long idscenario1 = q1.getSingleResult().getIdscenario();
         
                
                 
                 
                                       /**
          * inserisco i parametri di progetto
          */
          inputoutputxml.parametriDiProgetto(elemento,(int)idscenario1);
        
         
         
         /*for(int i = 0; i < aziende.length;i++)
             System.out.println(" sono nel nuovo gruppoaziende.jsp " + aziende[i]);
 * /
         
         /*
          *se il numero di elementi in  
          */     

      
         /**
           * impsto il consortile o meno
           */
          //inputoutputxml.consortile(elemento, 1);
      
         
         
         
         
           /**
           *contenitore in formato stringa dei cuaa selezionati dall'utente
           *
           */
         LinkedList<String> cuaaString = new LinkedList<String>();
        /**
           *mi costruisco una lista di aziende vuote che riempio nel ciclo successivo 
           *e che userò nel meotod aggiungidatiAgronomici della classe InputoutputXml per ciclare sulle diverse 
           *aziende selezionate dall'utente ed inserire nel xml da passare al solutore i parametri 
           *agronomici
           */
         //List<db.AziendeI> listaAziende  = new LinkedList<db.AziendeI>();
         /**
         *per ogni azienda aggiungo gli allevamenti,gli sotccaggi e l'acqua di stoccaggio
         *mi muovo di tre in tre perchè nell'array aziende c'è il cuaa la ragione sociale e lo scenario divisi da un 
         *trattino che quando vado a splittare diventano tre per ogni azienda
         *
         */
         for (int i = 1; i < aziende.length; i = i + 3) {
                 System.out.println("idoperazione  " + aziende[i]);
                 //lo scenario mi arriva nella forma "scenario : 0" per cui nella posizione modulo 3 devo togliere la scritta scenario :
                 // e lasciar e il numero
                 int scen = Integer.parseInt(aziende[i+2].replace("scenario: ", ""));
               //System.out.println("-----------scenario " + scen);
                 //aggiungo nella lista tampone i cuaa selezionati che usero dopo il ciclo for per
                 //aggiungere le distanze
                 cuaaString.add(aziende[i]);
                 Element azienda = null;
                 
                 if(utenteospite)
                     azienda = inputoutputxml.aggiungiAzienda(elemento, aziende[i], String.valueOf(scen),true);
                 else
                     azienda = inputoutputxml.aggiungiAzienda(elemento, aziende[i], String.valueOf(scen),false);
                 
                 
                 ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(aziende[i], scen);
                 contenitore.getData(aziende[i],scen);
                 contenitoreIniziale = contenitore.getContenitore();
                 
                 //aggiungo il contenuto delle varie tipolgie di refluo di ogni azianda
                 //a questo contenitore cosi da avere le caratteristiche chimiche inizieli
                 //prima di applicare ogni alternativa
                 contenitoreTotale.aggiungiContenitore(contenitoreIniziale);
                
                 listaCaratteristicheLiq = new LinkedList<Refluo>();
                 listaCaratteristicheLet = new LinkedList<Refluo>();
                 
                 
                 for (String s : contenitoreIniziale.getTipologie()) {
                     if (s.contains("Liquame")) {
                         listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
                     }

                     if (s.contains("Letame")) {
                         listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
                     }
                 }
                 
                
                 
                 
                 
                 inputoutputxml.aggiungiAllevamenti(azienda, listaCaratteristicheLiq, listaCaratteristicheLet);
                 
                 
                 if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
                 
                 
                 
                 //Query azQ =  entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", aziende[i]);    
                 
                Query azQ;
                 
                //if(dettaglioCuaa.getUtente().equals("azienda1"))
                if(!utenteospite)
                {
                    //System.out.println("query con cuaa");
                    azQ = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", aziende[i]);
                    
                }
                else
                {
                     //System.out.println("query con cuaa finto");
                    azQ = entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto", aziende[i]);
                   
                }
                 
                db.AziendeI az =(db.AziendeI)azQ.getSingleResult();
                 
                 /**
                 *aggiungo l'azienda nella lista per il lato aggiunta dati lato agronomico
                 */
                 //listaAziende.add(az);
                 
                 TypedQuery<db.ScenarioI> q = entityManager.createQuery("SELECT a FROM ScenarioI a WHERE a.id=?1 AND a.cuaa=?2",db.ScenarioI.class);                 
                 q.setParameter(2, az);
                 q.setParameter(1, scen);
                 long idscenario = q.getSingleResult().getIdscenario();
                 
                 
                 inputoutputxml.aggiungiStoccaggi(azienda,(int)idscenario);
                 
                 inputoutputxml.aggiungiAcquaStoccaggio(azienda,(int)idscenario);
                 
                  //inputoutputxml.aggiungiDatiAgronomici(azienda, az);
                 
             }
         
        
         
        
         /**
              * aggiungo le distanze nel file xml
              *
              */
             //inputoutputxml.aggiungiDistanze(elemento, cuaaString);


             /**
              * numero random che mi serve per distinguere differenti file di
              * input al solutore da parte di differenti utenti connessi alla
              * pagina web
              */
            //String numerorandom1 = this.getRandomNumber(5);
         
      
         
          DettaglioCuaa.setNumeroRandom(numerorandom1);
          
          
          //aggiungo i valori della simulazione lato agronomcio al file xml da passare al solutore
      //inputoutputxml.aggiungiDatiAgronomici(elemento, listaAziende);
          
          
       
        try {       
            inputoutputxml.scriviFile("input_"+numerorandom1+".xml");
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        Modello modello = null;
        
       
         String currentDir = System.getProperty("user.dir");
         System.out.println("Current dir using System:         " + currentDir);
         System.out.println("numero random del file xml           " + numerorandom1);
         
        /**
        *test recupero dati da simulazione ovvero recupero dati
        *dal lato agronomico
        **/
   
        /*    entityManagerFactory = Persistence.createEntityManagerFactory("simulazione");
            entityManager = entityManagerFactory.createEntityManager();
            jpa = (JpaEntityManager) entityManager.getDelegate();   
         
      Query simulaQuery =  entityManager.createQuery("SELECT s FROM Simulazione s WHERE s.azienda = ?1 and s.scenario = ?2");
      simulaQuery.setParameter(1,"00846210177");  
      simulaQuery.setParameter(2,2);
      List<db.Simulazione> simulazioni = simulaQuery.getResultList();
      System.out.println(" simulazione da udine dimensione " + simulazioni.size());
      ListIterator<db.Simulazione> simulazioniIter = simulazioni.listIterator();
      
     
      
      while(simulazioniIter.hasNext())
          {
            db.Simulazione simu = simulazioniIter.next();
            
            System.out.println(" simulazione da udine cuaa " + simu.getAzienda() + " scenario " + simu.getScenario() + " organico  " + simu.getOrganico() + " nh3_0 " + simu.getNh30()+ " drainage_0 "+ simu.getDrainage0() + " nh3_9 " + simu.getNh39() + " drainage_9 " + simu.getDrainage9());
          }*/
     
      
      
         
         if(System.getProperty("os.name").contains("Windows"))
         {
             modello = new Modello("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespig\\","seespig1.exe",numerorandom1,false);
         }else
         {
             modello = new Modello("/home/giorgiogalassi/Documenti/solutorec/","./solutore1.out",numerorandom1,true); 
         }
         
                
       
          modello.run();
          
          //tabellaIniziale(contenitoreTotale,out);
     
        
        
      InputOutputXml leggiXml = new InputOutputXml();
      LetturaRisultati test1 = new LetturaRisultati(leggiXml,numerorandom1,out,modello,operazione);
      test1.setContenitoreflui(contenitoreTotale);
      test1.run();
        
     
        
        
        
        
       
     
   
     
%>