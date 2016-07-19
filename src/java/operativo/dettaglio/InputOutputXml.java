/*
 * Questa classe si occupa di recuperare i risultati dal file xml che proviene
 * dal runtime che si occupa di trovare la soluzione ottima.
 * 
 */
package operativo.dettaglio;

import ager.Refluo;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.eclipse.persistence.*;


 
/**
 *esempi di query xml
 * "//modulo[@nome='digestione_anaerobica']/caratteristichechimiche/bovino"
 * "//modulo[@nome='digestione_anaerobica']/emissioni/bovino"
 * "//modulo[@nome='vasca']/emissioni"
 * @author giorgio
 */
public class InputOutputXml {
    
    /**
     * xml document in lettura
     */
    Document doc = null;
    DocumentBuilder builder = null;
    /**
     * nome del file che verrà parsato
     */
    String nomefilelettura="";
    /**
     * nome del file che verrascritto
     */
    //String nomefilescritto="";
    /**
     * xml document in scrittura
     */
    Document docW=null;
    /**
     * 
     * @param nomefile nome del file xml che contiene i risultati
     * imposta  il file di lettura
     */
    
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    
    
     private EntityManagerFactory entityManagerFactoryS;
    private EntityManager entityManagerS;
   
   // private String ritorno;
    
    public boolean impostaFile(String nomefile)
    {
        
        if(!verificaFile(nomefile))
            return false;
        
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        try {
            builder = domFactory.newDocumentBuilder();
            //Document doc = builder.parse("books.xml");
            doc = builder.parse(nomefile);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
        //if(doc.getDoctype())
        
        return true;
    }
    
    /**
     * apre uno stream xml con la url verra usato per il calcolo delle distanze
     * @param url
     * @return 
     */
    public boolean impostaStream(URL url)
    {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        try {
            builder = domFactory.newDocumentBuilder();
            //Document doc = builder.parse("books.xml");
            doc = builder.parse(url.openStream());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        
         return true;
    }
    
    
    /**
     * verifica se il nome file esiste prima di parsarlo nel costruttore
     * @param nomefile file da verififcare se esiste
     * @return true se il  file esiste false altrimenti
     */
    private boolean verificaFile(String nomefile)
    {
      File f = new File(nomefile);
          
      String currentDir = System.getProperty("user.dir");
      
      System.out.println("cerco il file "+nomefile + " in " + currentDir);
    
     if(f.exists())
     {
         return true;
     }else
     {
         return false;
     }
    }
    
    
    /**
     * mi serve per sapere quante alternative sono presenti nel ranking
     * prende un lemento tag dell'xml e mi restituisce il numero di occorenze nel file
     * @return 
     */
    public int contaNodi(String elemento)
    {
        XPathExpression expr = null;
        Object result = null;
       // Hashtable<String, Double> ritorno = null;

        if (doc == null) {
            return 0;
        }

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        NodeList listaNodi = doc.getElementsByTagName(elemento);
       
        return listaNodi.getLength();
    }
    
    
    private Hashtable<String,Double> popola(NodeList nodes,Hashtable<String,Double> ritorno)
    {
        
        for (int i = 0; i < nodes.getLength(); i++) {


            Node temp = nodes.item(i);

            if (temp.getNodeType() == Node.ELEMENT_NODE) {
                ritorno.put(temp.getNodeName(), Double.parseDouble(temp.getFirstChild().getNodeValue()));
                //System.out.println("nome " + temp.getNodeName() + " valore " + temp.getFirstChild().getNodeValue());
            }
        }
        
        
        return ritorno;
    }
    
   /**
    * Cerca nel dom del xml il percorso definito nella query 
    * Per funzionare correttamente la query deve puntare alle foglie dell'xml cosi
    * da restituire un solo livello di risultati
    * @param query di ricerca nel xml
    * @return hastable<nome variabile,valore>
    */ 
    public  Hashtable<String,Double> cerca(String query)
    {
        XPathExpression expr = null;
        Object result = null;
        Hashtable<String, Double> ritorno = null;

        if (doc == null) {
              System.out.println("sono qui");
            return null;
        }

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            
            //expr = xpath.compile("//modulo[@nome='digestione_anaerobica']/caratteristichechimiche/bovino");
            expr = xpath.compile(query);

            result = expr.evaluate(doc, XPathConstants.NODE);
            
            NodeList listaNodi  = (NodeList)result;
            if(listaNodi.getLength() == 0)
                result = expr.evaluate(doc, XPathConstants.NODESET);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
           // System.out.println("sono qui1");
            
            return null;
        }
        
        /**
         * recupero la lista dei nodi prodotta dalla query
         */
        NodeList nodes = (NodeList) result;
        
        /**
         * se ilnumero dei nodi è maggiore di zero popolo 
         * la hashtable con i risultati altrimenti ritorno un null
         */
        if(nodes != null && nodes.getLength() != 0)
        {
            ritorno = new Hashtable<String,Double>();
            popola(nodes,ritorno);
        }
        
        

        return ritorno;
    }
    
    
     /**
    * Cerca nel dom del xml il percorso definito nella query 
    * Per funzionare correttamente la query deve puntare alle foglie dell'xml cosi
    * da restituire un solo livello di risultati
    * @param query di ricerca nel xml
    * @return hastable<nome variabile,valore>
    */ 
    public  NodeList cerca1(String query,boolean singolo)
    {
        XPathExpression expr = null;
        Object result = null;
        Hashtable<String, Double> ritorno = null;

        if (doc == null) {
              System.out.println("doc null in cerca1 do inputoutputxml");
            return null;
        }

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
           // System.out.println(" query xml " + query + "  0 :::::::::::::::");
            //expr = xpath.compile("//modulo[@nome='digestione_anaerobica']/caratteristichechimiche/bovino");
            expr = xpath.compile(query);
           // System.out.println(" query xml " + query + "  1 :::::::::::::::");
            if(singolo){
                result = expr.evaluate(doc, XPathConstants.NODE);
            }
            else
            {
                result = expr.evaluate(doc, XPathConstants.NODESET);
            }
           // System.out.println(" query xml " + query + "  2 :::::::::::::::");
            /*NodeList listaNodi  = (NodeList)result;
            if(listaNodi.getLength() == 0)
            {
                 System.out.println(" query xml " + query + "  3 :::::::::::::::");
                result = expr.evaluate(doc, XPathConstants.NODESET);
                
                 System.out.println(" query xml " + query + "  4 :::::::::::::::");
            }*/
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            System.out.println("errore nel recupero dato del xml");
            
            return null;
        }
        
        /**
         * recupero la lista dei nodi prodotta dalla query
         */
        NodeList nodes = (NodeList) result;
        
        /**
         * se ilnumero dei nodi è maggiore di zero popolo 
         * la hashtable con i risultati altrimenti ritorno un null
         */
       /* if(nodes != null && nodes.getLength() != 0)
        {
            ritorno = new Hashtable<String,Double>();
            popola(nodes,ritorno);
        }*/
        
        

        return nodes;
    }
    
    
    /**
     * cerca nel xml di output un singolo nodo e di questo ritorna il valore 
     * @param query  esempio //modulo[@nome='digestione_anaerobica']/caratteristichechimiche/bovino
     * @return 
     */
     public  String cercaSingolo(String query)
    {
        String ritorno = "";
        
        XPathExpression expr = null;
        Object result = null;
       // Hashtable<String, Double> ritorno = null;

        if (doc == null) {
            return null;
        }

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            
            //expr = xpath.compile("//modulo[@nome='digestione_anaerobica']/caratteristichechimiche/bovino");
            expr = xpath.compile(query);

            result = expr.evaluate(doc, XPathConstants.NODE);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            return null;
        }
        
        /**
         * recupero la lista dei nodi prodotta dalla query
         */
        NodeList nodes = (NodeList) result;
        
        
        
        /**
         * se ilnumero dei nodi è maggiore di zero popolo 
         * la hashtable con i risultati altrimenti ritorno un null
         */
        if(nodes != null && nodes.getLength() != 0)
        {
            ritorno = nodes.item(0).getNodeValue();
            
        }
        
        

        return ritorno;
    }
    
    public void stampa(NodeList nodi)
    {
       
        for(int i = 0; i < nodi.getLength();i++)
        {
            Node te = nodi.item(i);
            if(te.getNodeType() == Node.ELEMENT_NODE)
                System.out.println("contenuto nodo " + te.getNodeName());
        }
    }
    
    public void stampa(Hashtable<String,Double> contenitore)
    {
        
        
        
         if(contenitore!= null)
         {
            String[] keys = (String[]) contenitore.keySet().toArray(new String[0]);  
            Arrays.sort(keys);  
            for(String key : keys)
            {
                System.out.println(key + " " +contenitore.get(key));
            }
         }
      
    }
    
   /**
    * genera il file xml in funzione del contenuto salvato nel docW ovvero nel document XML
    * @throws TransformerConfigurationException
    * @throws TransformerException 
    */ 
    public void scriviFile(String nomefilescritto) throws TransformerConfigurationException, TransformerException
    {
        
        System.out.println("..................." + nomefilescritto);
        //prima cancello il file se esite
        try{
        File file = new File(nomefilescritto);
        if(file.exists())
            if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(docW);
		StreamResult result = new StreamResult(new File(nomefilescritto));
                
                System.out.println(this.getClass().getCanonicalName() + " parte nome file " + nomefilescritto);
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(source, result);
                
                
     
 
		System.out.println("File saved!");
    }
    
    /**
     * mi serve per mappare il nome delle tipologie di refluo di marco con quelle impostate nel database
     */
    private String tipologiaRefluo(String quale)
    {
        String ritorno ="";
        
        switch(quale)
        {
            case "Liquame Bovino":
                ritorno = "bovini";
                break;
                
             case "Liquame Suino":
                ritorno = "suini";
                break;    
             case "Liquame Avicolo":
                ritorno = "avicoli";
                break;     
                 
             case "Liquame Altro":
                ritorno = "altri";
                break; 
            
            case "Letame Bovino":
                ritorno = "bovini";
                break;
                
             case "Letame Suino":
                ritorno = "suini";
                break;    
             case "Letame Avicolo":
                ritorno = "avicoli";
                break;     
                 
             case "Letame Altro":
                ritorno = "altri";
                break;          
                 
        }
        
        
        return ritorno;
    }
    /**
     * sulla base delle caratteristiche chimiche degli allevamenti popola il file xml con le caratteristiche
     * chimiche dei suddetti suddividendoli per tipologia di refluo e di animale
     * @param elementoPadre
     * @param listaCaratteristicheLiq
     * @param listaCaratteristicheLet
     * @return 
     */
    public Element aggiungiAllevamenti(Element elementoPadre,List<Refluo> listaCaratteristicheLiq,List<Refluo> listaCaratteristicheLet)
    {
        for(int i = 0; i< 4;i++)
        {
            Refluo liquame = listaCaratteristicheLiq.get(i);
            Refluo letame = listaCaratteristicheLet.get(i);
            
            
            Element elemento = docW.createElement("allevamento");
	    elementoPadre.appendChild(elemento);
 
		// set attribute to staff element
		Attr attr = docW.createAttribute("tipo");
		attr.setValue( tipologiaRefluo(liquame.getTipologia()));
                
           elemento.setAttributeNode(attr);  
           
           
           /**
            * aggiungo i liquami di una specie
            */
           Element liquame1 = docW.createElement("liquame");
	   elemento.appendChild(liquame1);
            
            Element m3 = docW.createElement("m3");
            m3.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getMetricubi()))));
	    liquame1.appendChild(m3);
            
            
             Element at = docW.createElement("at");
            at.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getAzotototale()))));
	    liquame1.appendChild(at);
            
            
             Element am = docW.createElement("am");
            am.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getAzotoammoniacale()))));
	    liquame1.appendChild(am);
            
             Element ss = docW.createElement("ss");
            ss.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getSostanzasecca()))));
	    liquame1.appendChild(ss);
            
             Element sv = docW.createElement("sv");
            sv.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getSolidivolatili()))));
	    liquame1.appendChild(sv);
            
             Element ft = docW.createElement("ft");
            ft.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getFosforototale()))));
	    liquame1.appendChild(ft);
            
            
             Element pt = docW.createElement("pt");
            pt.appendChild(docW.createTextNode(String.valueOf(Math.round(liquame.getPotassiototale()))));
	    liquame1.appendChild(pt);
            
            
            /**
             * aggiungo i letami di una specie
             */
            
            Element letame1 = docW.createElement("letame");
	   elemento.appendChild(letame1);
            
            Element m31 = docW.createElement("m3");
            m31.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getMetricubi()))));
	    letame1.appendChild(m31);
            
            
             Element at1 = docW.createElement("at");
            at1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getAzotototale()))));
	    letame1.appendChild(at1);
            
            
             Element am1 = docW.createElement("am");
            am1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getAzotoammoniacale()))));
	    letame1.appendChild(am1);
            
             Element ss1 = docW.createElement("ss");
            ss1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getSostanzasecca()))));
	    letame1.appendChild(ss1);
            
             Element sv1 = docW.createElement("sv");
            sv1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getSolidivolatili()))));
	   letame1.appendChild(sv1);
            
             Element ft1 = docW.createElement("ft");
            ft1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getFosforototale()))));
	    letame1.appendChild(ft1);
            
            
             Element pt1 = docW.createElement("pt");
            pt1.appendChild(docW.createTextNode(String.valueOf(Math.round(letame.getPotassiototale()))));
	    letame1.appendChild(pt1);
            
            
            
        }
        
        return elementoPadre;
    }
    
    
    /**
     * crea il nodo root del file xml e ritorna l'elemt principale del xml
     * allo stesso tempo si salva il nome del file in cui scrivere il conutenuto xml
     * @param nomefile
     * @return 
     */
    public Element generaXml()
   {
       
       Element rootElement = null;
       
       try {
                
                //this.nomefilescritto = nomefile;
           
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		docW = docBuilder.newDocument();
		rootElement = docW.createElement("input");
		docW.appendChild(rootElement);
                
               /**
                * aggiungo il cuaa dell'azienda,il nome dell'utente e la data 
                * è un modo per passare questi valori al solutore c e ritrovarli nel 
                * documento xml di output cosi da associare il risultato con l'azienda o le aziende
                */
               /* Element tmp = docW.createElement("cuaa");
                DettaglioCuaa dett = new DettaglioCuaa();
                tmp.appendChild(docW.createTextNode(dett.getCuaa()));
                rootElement.appendChild(tmp);*/
                
                /**
                 * aggiungo un campo che informa l'utente finale della data in cui
                 * è stato creato il file 
                 */
               /* DettaglioCuaa dett = new DettaglioCuaa();
                Element tmp = docW.createElement("data");
                java.util.Date date= new java.util.Date();
                tmp.appendChild(docW.createTextNode(new Timestamp(date.getTime()).toString()));
                rootElement.appendChild(tmp);*/
                
                /**
                 * aggiungo un campo nome utente nell'xml input cosi da ricondurre l'operazione con 
                 * l'utente e  l'azienda
                 */
                /*tmp = docW.createElement("utente");
                tmp.appendChild(docW.createTextNode(dett.getUtente()));
                rootElement.appendChild(tmp);*/
                
                /*
		// staff elements
		Element staff = doc.createElement("Staff");
		rootElement.appendChild(staff);
 
		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);
 
		// shorten way
		// staff.setAttribute("id", "1");
 
		// firstname elements
		Element firstname = doc.createElement("firstname");
		firstname.appendChild(doc.createTextNode("yong"));
		staff.appendChild(firstname);
 
		// lastname elements
		Element lastname = doc.createElement("lastname");
		lastname.appendChild(doc.createTextNode("mook kim"));
		staff.appendChild(lastname);
 
		// nickname elements
		Element nickname = doc.createElement("nickname");
		nickname.appendChild(doc.createTextNode("mkyong"));
		staff.appendChild(nickname);
 
		// salary elements
		Element salary = doc.createElement("salary");
		salary.appendChild(doc.createTextNode("100000"));
		staff.appendChild(salary);
 
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("file.xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
 
		transformer.transform(source, result);
 
		System.out.println("File saved!");
 */
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  }
	 return rootElement;
   }
    
    
    
    public void inserisciData(Element elementoPadre)
    {
         /**
                 * aggiungo un campo che informa l'utente finale della data in cui
                 * è stato creato il file 
                 */
              
                Element tmp = docW.createElement("data");
                java.util.Date date= new java.util.Date();
                tmp.appendChild(docW.createTextNode(new Timestamp(date.getTime()).toString()));
                elementoPadre.appendChild(tmp);
    }
    
    public void inserisciUtente(Element elementoPadre,String nomeutente)
    {
                //DettaglioCuaa dett = new DettaglioCuaa();
                Element tmp = docW.createElement("utente");
                //tmp.appendChild(docW.createTextNode(dett.getUtente()));
                tmp.appendChild(docW.createTextNode(nomeutente));
                elementoPadre.appendChild(tmp);
    }
    /**
     * inserisce il valore della scelta se consortile o meno
     * nel file xml
     * @param elementoPadre
     * @param valoreConsortile 
     */
    public void consortile(Element elementoPadre,int valoreConsortile)
    {
        Element elemento = docW.createElement("consortile");
        elemento.appendChild(docW.createTextNode(String.valueOf(valoreConsortile)));
	elementoPadre.appendChild(elemento);
         
        
         
    }
    
    
    /**
     * pende in input una lista di aziende e aggiunge nel file xml il tag simulazione . In simulazione
     * inserisce il tag azienda come di seguito
     * <datiagronomici>
     *      <azienda>
     *          <cuaa>    </cuaa>
     *          <organico>
     *                  <valore> </valore>
     *                  <superficie></superficie>
     *                  <nh3_0></nh3_0>
     *                  <leach_0></leach_0>
     *                  <drainage_0></drainage_0>
     *                   <nh3_1></nh3_1>
     *                  <leach_1></leach_1>
     *                  <drainage_1></drainage_1>
     *                      .
     *                      .
     *                      .
     *                   <nh3_10></nh3_10>
     *                  <leach_10></leach_10>
     *                  <drainage_10></drainage_10>
     *              
     *          </organico>
     *           <organico>
     *                  <valore> </valore>
     *                  <superficie></superficie>
     *                  <nh3_0></nh3_0>
     *                  <leach_0></leach_0>
     *                  <drainage_0></drainage_0>
     *                   <nh3_1></nh3_1>
     *                  <leach_1></leach_1>
     *                  <drainage_1></drainage_1>
     *                      .
     *                      .
     *                      .
     *                   <nh3_10></nh3_10>
     *                  <leach_10></leach_10>
     *                  <drainage_10></drainage_10>
     *              
     *          </organico>
     *           <organico>
     *                  <valore> </valore>
     *                  <superficie></superficie>
     *                  <nh3_0></nh3_0>
     *                  <leach_0></leach_0>
     *                  <drainage_0></drainage_0>
     *                   <nh3_1></nh3_1>
     *                  <leach_1></leach_1>
     *                  <drainage_1></drainage_1>
     *                      .
     *                      .
     *                      .
     *                   <nh3_10></nh3_10>
     *                  <leach_10></leach_10>
     *                  <drainage_10></drainage_10>
     *              
     *          </organico>
     *      </azienda>
     * </datiagronomici>
     * @param aziende 
     */
//    public void aggiungiDatiAgronomici(Element elementoPadre,db.AziendeI azienda) {
//         Query simulaQuery;
//         List<db.Simulazione> simulazioni;
//         ListIterator<db.Simulazione> simulazioniIter;
//         Element tagAzienda,tagTemp , tagOrganico , tagTemp1  ; 
//        
//        
//        //aggiungo l'elemento
//         //Element elemento = docW.createElement("datiagronomici");
//         //elementoPadre.appendChild(elemento);
//        //connesisone alla vista simulazione.simulazione di seespig 
//        entityManagerFactoryS = Persistence.createEntityManagerFactory("simulazione");
//        entityManagerS = entityManagerFactoryS.createEntityManager();
//        //  jpa = (JpaEntityManager) entityManager.getDelegate();   
//
//       // ListIterator<db.AziendeI> iterAziende = aziende.listIterator();
//        /**
//         * ciclo sulle aziende della lista e per ogni azienda recupero i dati
//         * dalla tabella delle simulazioni
//         */
//       // while (iterAziende.hasNext()) {
//            //db.AziendeI azTemp = iterAziende.next();
//
//            simulaQuery = entityManagerS.createQuery("SELECT s FROM Simulazione s WHERE s.azienda = ?1 and s.scenario = ?2");
//            //simulaQuery.setParameter(1, azTemp.getCuaa());
//            simulaQuery.setParameter(1, azienda.getCuaa());
//            simulaQuery.setParameter(2, 0);
//            
//            
//           
//            if(simulaQuery.getResultList().isEmpty())
//            {
//                simulaQuery = entityManagerS.createQuery("SELECT s FROM Simulazione s WHERE s.azienda = ?1 and s.scenario = ?2");
//            //simulaQuery.setParameter(1, azTemp.getCuaa());
//                simulaQuery.setParameter(1, "00846210177");
//                simulaQuery.setParameter(2, 0);
//            }
//
//
//            simulazioni = simulaQuery.getResultList();
//            simulazioniIter = simulazioni.listIterator();
//            
//            tagAzienda =docW.createElement("azienda_agro");
//            elementoPadre.appendChild(tagAzienda);
//            //tagTemp = docW.createElement("cuaa_agro");
//            //tagTemp.appendChild(docW.createTextNode(String.valueOf(azienda.getCuaa())));
//            //tagAzienda.appendChild(tagTemp);
//            
//            
//           
//            
//             
//            /**
//             * per ogni azienda ho n righe che hanno valore di organico diverso 
//             * che corrispondono ai trattamenti; ogni organico in valore
//             * corrisponde ad un preciso trattamento
//             */
//            while(simulazioniIter.hasNext())
//            {
//                db.Simulazione simuTemp = simulazioniIter.next();
//                
//                
//                 tagOrganico = docW.createElement("organico");
//                 tagAzienda.appendChild(tagOrganico);
//             
//                
//                tagTemp1 =docW.createElement("valore_organico");
//                tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getOrganico())));
//                tagOrganico.appendChild( tagTemp1);
//                
//                
//                tagTemp1 = docW.createElement("superficie");
//                tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getSuperficie())));
//                tagOrganico.appendChild(tagTemp1);
//                
//                 tagTemp1 = docW.createElement("superficiezvn");
//                 tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getSuperficiezvn())));
//                tagOrganico.appendChild(tagTemp1);
//                
//               tagTemp1 = docW.createElement("superficiezo");
//               tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getSuperficiezo())));
//                tagOrganico.appendChild(tagTemp1);
//                
//                tagTemp1 = docW.createElement("nh3");
//                tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh3())));
//                tagOrganico.appendChild(tagTemp1);
//                
//                tagTemp1 = docW.createElement("leach");
//                tagTemp1.appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach())));
//                tagOrganico.appendChild(tagTemp1);
//                
//                tagTemp1 = docW.createElement("drainage");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                
//                tagTemp1 = docW.createElement("nh3_0");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh30())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_0");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach0())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_0");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage0())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                
//                tagTemp1 = docW.createElement("nh3_1");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh31())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_1");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach1())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_1");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage1())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_2");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh32())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_2");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach2())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_2");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage2())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_3");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh33())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_3");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach3())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_3");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage3())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                
//                tagTemp1 = docW.createElement("nh3_4");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh34())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_4");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach4())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_4");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage4())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_5");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh35())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_5");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach5())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_5");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage6())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                
//                tagTemp1 = docW.createElement("nh3_6");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh36())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_6");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach6())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_6");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage6())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_7");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh37())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_7");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach7())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_7");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage7())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_8");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh38())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_8");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach8())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_8");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage8())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_9");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh39())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_9");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach9())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_9");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage9())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                tagTemp1 = docW.createElement("nh3_10");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getNh310())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("leach_10");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getLeach10())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                tagTemp1 = docW.createElement("drainage_10");
//                tagTemp1 .appendChild(docW.createTextNode(String.valueOf(simuTemp.getDrainage10())));
//                tagOrganico.appendChild(tagTemp1 );
//                
//                
//                
//                
//                
//            }
//            
//       // }
//        
//        entityManagerS.close();
//        entityManagerFactoryS.close();
//     
//
//
//
//    }
    
    
//    public Element aggiungiDistanze(Element elementoPadre,LinkedList<String> aziende)
//    {
//        Element distanze = docW.createElement("distanze");
//        
//          /**
//         * interrogo il db per conoscere i nomi degli stoccaggi in vasca
//         */
//          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
//                            {
//                               Connessione connessione = Connessione.getInstance();
//                               entityManager = connessione.apri("provagiorgio13");
//                               entityManagerFactory = connessione.getEntityManagerFactory();
//                            }
//          /**
//           * per ogni azienda all'interno dell'array aziende prendo le sue distanze 
//           * verso le altre aziende ma non inserisco tutte le destinazioni 
//           * ma solo quelleo che sono nell'array aziende per chè mi interressa avere le distanze tra le aziende 
//           * scelte dall'utente nella pagina web
//           */
//          ListIterator<String> iterAziende = aziende.listIterator();
//         /* while(iterAziende.hasNext())
//          {
//              
//              System.out.println("aziende nella lista " + iterAziende.next());
//          }*/
//          
//         
//          
//         // for (int i = 0; i < aziende.length; i++) {
//          /**
//           * uso dettagliocuaa per vedere se l'utente loggato è guest o no
//           * e di conseguenza modificare la query che faccio su aziende ovvero se usare il cuaa
//           * o il cuaa finto
//           */
//          DettaglioCuaa detto = new DettaglioCuaa();
//          boolean utenteospite = false;
//          LinkedList<String> cuaaStrings = new LinkedList<String>();
//          if(!detto.getUtente().equals("azienda1"))
//          {
//              utenteospite = true;
//              Query q;
//              
//              while(iterAziende.hasNext())    
//              {
//                   String tt = iterAziende.next();  
//                   q = entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto",tt);
//                   db.AziendeI azienda =(db.AziendeI) q.getSingleResult();
//                   cuaaStrings.add(azienda.getCuaa());
//              }
//              
//              aziende = cuaaStrings;
//          }
//          
//          
//          
//           iterAziende = aziende.listIterator();
//          
//          while(iterAziende.hasNext())    
//          {
//            Element distanza = docW.createElement("distanza");
//            distanze.appendChild(distanza);
//            String tt = iterAziende.next();  
//            Query q;
//            /*if(utenteospite)
//            {
//             q = entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto",tt);
//            }
//            else
//            {*/
//             q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",tt);
//            //}
//            
//            List<db.AziendeI> listaAziende = q.getResultList();
//            
//            if(!listaAziende.isEmpty())
//            {
//                //contiene l'aziende iniziale che da cui mostrare le distazne verso le altre
//                 db.AziendeI az = listaAziende.get(0);
//           
//
//
//                List<db.Distanze> distanze1 = (List<db.Distanze>) az.getDatilocalizzazione().getDistanzeCollection();
//
//                ListIterator<db.Distanze> iterDistanze1 = distanze1.listIterator();
//                
//                
//                Element cuaasorg = docW.createElement("sorgente");
//                if(utenteospite)
//                {
//                        cuaasorg.appendChild(docW.createTextNode(az.getCuaaFinto()));
//                }
//                else
//                {
//                          cuaasorg.appendChild(docW.createTextNode(az.getCuaa()));
//                }
//                
//                distanza.appendChild(cuaasorg);
//                        
//                        
//               // System.out.println(" calcolo distanze aziednda "+ az.getCuaaFinto()+" numero aziende  " + distanze1.size());        
//                        
//                /**
//                 * controllo che la destinazione sia nell'array iniziale delle destinazioni
//                 * cosi da aggiungere solo le desintazioni che mi servono
//                 */
//                while (iterDistanze1.hasNext()) {
//                    db.Distanze dis = iterDistanze1.next();
//                    double di1 = dis.getDistanza();
//                    String destinazione = dis.getDestinazione();
//                    
//                    //System.out.println("classe " + this.getClass().getCanonicalName() +" metodo " + Thread.currentThread().getStackTrace()[1].getMethodName()  + " nel while  sorgente " +az.getCuaa() + " destinazione " + destinazione);
//                    
//                    
//                    
//                    if(aziende.contains(destinazione))
//                    {
//                        //aggiungo l'elemento
//                        //System.out.println("sono nel if ");
//                        Element cuaadest = docW.createElement("destinazione");
//                        Element cuaadestnome = docW.createElement("nome");
//                        
//                        if(utenteospite)
//                        {
//                            q = entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa",destinazione);
//                            db.AziendeI azTT = (db.AziendeI)q.getSingleResult();
//                            cuaadestnome.appendChild(docW.createTextNode(azTT.getCuaaFinto()));
//                        }else
//                        {
//                            cuaadestnome.appendChild(docW.createTextNode(destinazione));
//                        }
//                        Element cuaadestdistanza = docW.createElement("dist");
//                        cuaadestdistanza.appendChild(docW.createTextNode(String.valueOf(di1)));
//                        cuaadest.appendChild(cuaadestnome);
//                        cuaadest.appendChild(cuaadestdistanza);
//                        distanza.appendChild(cuaadest);
//                       
//                    }
//                }
//                
//                 //distanza.appendChild(cuaasorg);
//            }
//
//        }
//        
//        
//       //Connessione.getInstance().chiudi();
//        
//        
//        elementoPadre.appendChild(distanze);
//        return distanze;
//    }
    
   /**
    * 
    * @param elementoPadre
    * @param cuaa identificativo dell'azienda
    * @param scenario 
    * @param userguest se true dice che l'utente non è azienda1 per cui la query va fatta sul cuaa finto
    * @return 
    */
    public Element aggiungiAzienda(Element elementoPadre, String cuaa, String scenario,Boolean userguest) {
        // DettaglioCuaa dett = new DettaglioCuaa();
        Element azienda = docW.createElement("azienda");
        Element tmp = docW.createElement("cuaa");
        //tmp.appendChild(docW.createTextNode(dett.getCuaa()));
        tmp.appendChild(docW.createTextNode(cuaa));
        azienda.appendChild(tmp);

        Element tmp1 = docW.createElement("scenario");
        //tmp1.appendChild(docW.createTextNode(dett.getIdscenario().toString()));
        tmp1.appendChild(docW.createTextNode(scenario));
        azienda.appendChild(tmp1);
           System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " riga 1274 cuaa " + cuaa + " scenario " + scenario);    

        if (entityManagerFactory == null || !(entityManagerFactory.isOpen())) {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
            entityManagerFactory = connessione.getEntityManagerFactory();
        }
        
        Query azQ = null;
        
        if(!userguest) {
            azQ =  entityManager.createNamedQuery("AziendeI.findByCuaa").setParameter("cuaa", cuaa.trim());
        }    
        else {
            azQ =  entityManager.createNamedQuery("AziendeI.findByCuaaFinto").setParameter("cuaaFinto", cuaa.trim());
        }     
                 db.AziendeI az =(db.AziendeI)azQ.getSingleResult();
        
         //inserisco le coordinate gps del centro aziendale        
         double coordinataX = 0d;//az.getDatilocalizzazione().getCentrox();
         double coordinataY = 0d;//az.getDatilocalizzazione().getCentroy();         
        Element tmp2x = docW.createElement("coordinataX");
        //tmp1.appendChild(docW.createTextNode(dett.getIdscenario().toString()));
        tmp2x.appendChild(docW.createTextNode(String.valueOf(coordinataX)));
        azienda.appendChild(tmp2x);
        
        Element tmp2y = docW.createElement("coordinataY");
        //tmp1.appendChild(docW.createTextNode(dett.getIdscenario().toString()));
        tmp2y.appendChild(docW.createTextNode(String.valueOf(coordinataY)));
        azienda.appendChild(tmp2y);
        
        
        
        TypedQuery<db.ScenarioI> q = entityManager.createQuery("SELECT a FROM ScenarioI a WHERE a.idscenario=?1 ", db.ScenarioI.class);
        
        q.setParameter(1, Integer.parseInt(scenario));
        long idscenario = q.getSingleResult().getIdscenario();
        
        //db.ScenarioI idscenariodb = q.getSingleResult();

        //TypedQuery<db.DistanzaCentroParticelle> q1 = entityManager.createQuery("SELECT a FROM DistanzaCentroParticelle a WHERE a.idscenario=?1 ", db.DistanzaCentroParticelle.class);
        //q1.setParameter(1, idscenario);
        TypedQuery<db.DatiRimozioneazoto> q2 = entityManager.createQuery("SELECT ab FROM DatiRimozioneazoto  ab WHERE ab.idscenario = ?1 ",db.DatiRimozioneazoto.class);
        q2.setParameter(1, idscenario);
        db.DatiRimozioneazoto datirimozione = q2.getSingleResult();
       
        double distanza = q2.getSingleResult().getDistanzaCentroParticelle();
        
        Element tmp2 = docW.createElement("distanza_centro_particelle");
        //tmp1.appendChild(docW.createTextNode(dett.getIdscenario().toString()));
        tmp2.appendChild(docW.createTextNode(String.valueOf(distanza)));
        azienda.appendChild(tmp2);
        
        
        
        
        
        
        Element tmp3 = docW.createElement("n_max_zoo");
      
        tmp3.appendChild(docW.createTextNode(String.valueOf(datirimozione.getMaxnsau())));
        // tmp3.appendChild(docW.createTextNode(String.valueOf("0")));
        azienda.appendChild(tmp3);
        
        
        Element tmp4 = docW.createElement("n_max_tot");
       
        tmp4.appendChild(docW.createTextNode(String.valueOf(datirimozione.getMaxncolture())));
        //tmp4.appendChild(docW.createTextNode(String.valueOf("0")));
        azienda.appendChild(tmp4);
        
        
        
        elementoPadre.appendChild(azienda);






        return azienda;
    }
    
    
     /**
     * aggiunge gli stoccaggi nel file xml di input in base ad un cuaa ed uno scenario
     * @param elementoPadre 
     */
    public void aggiungiAcquaStoccaggio(Element elementoPadre,int idscenario)
    {
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
      //System.out.println(this.getClass().getSimpleName() + " scenario " + idscenario);
      
     
        /**
      * recupero i parametri delle vasche
      */
     //Query  q = entityManager.createQuery("SELECT p FROM AcquastoccaggioI p where p.idscenario = "+ dettaglioCuaa.getIdscenario()+" ;" );
     Query  q = entityManager.createQuery("SELECT p FROM AcquastoccaggioI p where p.idscenario = "+ idscenario  );
     List<db.AcquastoccaggioI>   listaAcque = (List<db.AcquastoccaggioI>)q.getResultList();
      
      //System.out.println(this.getClass().getSimpleName() + " scenario " + idscenario + " listaacque " +listaAcque.size() + " qualche dato " + listaAcque.get(0).getCapSolidi1rac());
     
     db.AcquastoccaggioI acqua = null;
     if(listaAcque.size() != 0)
     {
         acqua = listaAcque.get(0);
     }else
     {
        acqua = new db.AcquastoccaggioI(); 
        acqua.setAcquaImpianti(0d);
        acqua.setCapLiquidi1rac(0d);
        acqua.setCapSolidi1rac(0d);
        acqua.setPioggia(0d);
        acqua.setSupLiquidi1rac(0d);
        acqua.setSupSolidi1rac(0d);
        acqua.setSuperficiScoperte(0d);
     }
     
     
     Element elementost = docW.createElement("acquastoccaggi");
     elementoPadre.appendChild(elementost);
    
     Element tempNodo = null;
     tempNodo = docW.createElement("acqua_impianti");
     tempNodo.appendChild(docW.createTextNode(acqua.getAcquaImpianti().toString()));
     elementost.appendChild(tempNodo);
     
     tempNodo = docW.createElement("superfici_scoperte");
     tempNodo.appendChild(docW.createTextNode(acqua.getSuperficiScoperte().toString()));
     elementost.appendChild(tempNodo);
     
     
     tempNodo = docW.createElement("pioggia");
     tempNodo.appendChild(docW.createTextNode(acqua.getPioggia().toString()));
     elementost.appendChild(tempNodo);
     
     
     tempNodo = docW.createElement("capacita_liquidi");
     tempNodo.appendChild(docW.createTextNode(acqua.getCapLiquidi1rac().toString()));
     elementost.appendChild(tempNodo);
     
     tempNodo = docW.createElement("capacita_solidi");
     tempNodo.appendChild(docW.createTextNode(acqua.getCapSolidi1rac().toString()));
     elementost.appendChild(tempNodo);
     
     tempNodo = docW.createElement("superficie_scoperta_liquidi");
     tempNodo.appendChild(docW.createTextNode(acqua.getSupLiquidi1rac().toString()));
     elementost.appendChild(tempNodo);
     
     tempNodo = docW.createElement("superficie_scoperta_solidi");
     tempNodo.appendChild(docW.createTextNode(acqua.getSupSolidi1rac().toString()));
     elementost.appendChild(tempNodo);
     
     
     
     //Connessione.getInstance().chiudi();
    }
    
    
    
    
    
    /**
     * aggiunge gli stoccaggi nel file xml di input in base ad un cuaa ed uno scenario
     * @param elementoPadre 
     */
    public void aggiungiStoccaggi(Element elementoPadre,int idscenario)
    {
        
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
      
      /**
       * recupero l'informazione sullo idscenario che mi informa sul cuaa e lo scenario di un determinato cuaa
       */ 
       
      //   ELContext elContext = FacesContext.getCurrentInstance().getELContext();
     //   DettaglioCuaa dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
       
       /**
        * inserisco nei parametri diprogetto la piovosità ed il costo dell'energia.La piovosità non è un parametro di progetto ma un dato preso da acqua_stoccaggio e legato al cuaa 
        * ed allo scenario.Il costo dell'energia è invece un parametro di progetto e quindi presente nella tabella dei parametri di progetto.
        */ 
       
        
        /**
      * recupero i parametri delle vasche
      */
    // Query  q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 6" );
       Query  q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ idscenario +" and p.idTrattamento.id = 6" );
     
     List<db.ParametridiprogettoS>   parametri = (List<db.ParametridiprogettoS>)q.getResultList();
     
      Element elementost = docW.createElement("stoccaggi");
      elementoPadre.appendChild(elementost);
     
      
      //System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario " + idscenario);
      
     //this.inserisciVasche(elementost, parametri,dettaglioCuaa.getIdscenario());
     this.inserisciVasche(elementost, parametri,Long.valueOf(String.valueOf(idscenario)));
     
     /**
      * recupero i paramerti delle platee e le inserisco
      */
     //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 7" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ idscenario +" and p.idTrattamento.id = 7" );

      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciPlatee(elementost, parametri,Long.valueOf(String.valueOf(idscenario)));
     
     
     //Connessione.getInstance().chiudi();
    }
    /**
     * inserisce il blocco a partire dalla root delle minimizzazioni che informano il solutore su quale strategia usare 
     * nella minimizzazione
     * @param elementoPadre
     * @param perccosto
     * @param percemissioni
     * @param percenergia 
     */
    public void minimizzazioni(Element elementoPadre,double perccosto,double percemissioni,double percenergia)
    {
       Element elemento = docW.createElement("minimizzazioni");
	 elementoPadre.appendChild(elemento);
         
         
          Element perccostoE = docW.createElement("perc_costo");
          perccostoE.appendChild(docW.createTextNode(String.valueOf(perccosto)) );
	  elemento.appendChild(perccostoE);
          
          /*Element percemissioniE = docW.createElement("perc_emissioni");
          percemissioniE.appendChild(docW.createTextNode(String.valueOf(percemissioni)) );
	  elemento.appendChild(percemissioniE);*/
          
          Element percemissioniAcide = docW.createElement("perc_emissioni_acid");
          percemissioniAcide.appendChild(docW.createTextNode(String.valueOf(percemissioni/2)) );
	  elemento.appendChild(percemissioniAcide);
          
          Element percemissioniSerra = docW.createElement("perc_emissioni_serra");
          percemissioniSerra.appendChild(docW.createTextNode(String.valueOf(percemissioni/2)) );
	  elemento.appendChild(percemissioniSerra);
          
          
          Element percenergiaE = docW.createElement("perc_energia");
          percenergiaE.appendChild(docW.createTextNode(String.valueOf(percenergia)) );
	  elemento.appendChild(percenergiaE); 
    }
    
    
    /**
     * inserisce il blocco a partire dalla root delle minimizzazioni che informano il solutore su quale strategia usare 
     * nella minimizzazione
     * @param elementoPadre
     * @param perccosto
     * @param percemissioni
     * @param percenergia 
     */
    public void minimizzazioni1(Element elementoPadre,double perccosto,double percemissionia,double percemissionig,double percenergia)
    {
       Element elemento = docW.createElement("minimizzazioni");
	 elementoPadre.appendChild(elemento);
         
         
          Element perccostoE = docW.createElement("perc_costo");
          perccostoE.appendChild(docW.createTextNode(String.valueOf(perccosto)) );
	  elemento.appendChild(perccostoE);
          
          /*Element percemissioniE = docW.createElement("perc_emissioni");
          percemissioniE.appendChild(docW.createTextNode(String.valueOf(percemissioni)) );
	  elemento.appendChild(percemissioniE);*/
          
          Element percemissioniAcide = docW.createElement("perc_emissioni_acid");
          percemissioniAcide.appendChild(docW.createTextNode(String.valueOf(percemissionia)) );
	  elemento.appendChild(percemissioniAcide);
          
          Element percemissioniSerra = docW.createElement("perc_emissioni_serra");
          percemissioniSerra.appendChild(docW.createTextNode(String.valueOf(percemissionig)) );
	  elemento.appendChild(percemissioniSerra);
          
          
          Element percenergiaE = docW.createElement("perc_energia");
          percenergiaE.appendChild(docW.createTextNode(String.valueOf(percenergia)) );
	  elemento.appendChild(percenergiaE); 
    }
    
    
    /**
     * inserisce il blocco a partire dalla root delle minimizzazioni che informano il solutore su quale strategia usare 
     * nella minimizzazione
     * @param elementoPadre
     * @param perccosto
     * @param percemissioni
     * @param percenergia 
     */
    public void minimizzazioni4(Element elementoPadre,double perccosto,double percemissionia,double percemissionis,double percenergia)
    {
       Element elemento = docW.createElement("minimizzazioni");
	 elementoPadre.appendChild(elemento);
         
         
          Element perccostoE = docW.createElement("perc_costo");
          perccostoE.appendChild(docW.createTextNode(String.valueOf(perccosto)) );
	  elemento.appendChild(perccostoE);
          
          /*Element percemissioniE = docW.createElement("perc_emissioni");
          percemissioniE.appendChild(docW.createTextNode(String.valueOf(percemissioni)) );
	  elemento.appendChild(percemissioniE);*/
          
          Element percemissioniAcide = docW.createElement("perc_emissioni_acid");
          percemissioniAcide.appendChild(docW.createTextNode(String.valueOf(percemissionia)) );
	  elemento.appendChild(percemissioniAcide);
          
          Element percemissioniSerra = docW.createElement("perc_emissioni_serra");
          percemissioniSerra.appendChild(docW.createTextNode(String.valueOf(percemissionis)) );
	  elemento.appendChild(percemissioniSerra);
          
          
          Element percenergiaE = docW.createElement("perc_energia");
          percenergiaE.appendChild(docW.createTextNode(String.valueOf(percenergia)) );
	  elemento.appendChild(percenergiaE); 
    }
    
    
    
    
    
   /**
    * inserisce la sezione della selezione dell'operazione da efettuare da parte del solutore
    * se rank = 1 calcola tutte le alternative e ne restiuisce il ranking
    * se migliore = 1 le calcola tutte ma restituisce solo laprima del ranking 
    * se alternativa = numero che rappresenta un alternativa e quindi diverso da zero calcola solo 
    * l'alternativa voluta
    * @param elementoPadre
    * @param rank
    * @param migliore
    * @param alternativa 
    */ 
    public void selezioni(Element elementoPadre,int rank,int migliore,int alternativa)
    {
         Element elemento = docW.createElement("selezioni");
	 elementoPadre.appendChild(elemento);
         
         
          Element rankE = docW.createElement("rank");
          rankE.appendChild(docW.createTextNode(String.valueOf(rank)) );
	  elemento.appendChild(rankE);
          
          Element miglioreE = docW.createElement("migliore");
          miglioreE.appendChild(docW.createTextNode(String.valueOf(migliore)) );
	  elemento.appendChild(miglioreE);
          
          Element alternativaE = docW.createElement("alternativa");
          alternativaE.appendChild(docW.createTextNode(String.valueOf(alternativa)) );
	  elemento.appendChild(alternativaE);
          
    }
    
    
   
            
    /**
     * inserisce i parametri di progetto nel file xml in funzione dell'alternativa scelta
     * dall'utente
     * @param elementoPadre
     * @param alternativa 
     */
    public void parametriDiProgetto(Element elementoPadre,int scenario)
    {
        
        /**
         * creo il nodo parametri di progetto
         */
         Element elemento = docW.createElement("parametri_di_progetto");
         elementoPadre.appendChild(elemento);
        
        
            //System.out.println("--------parametriDiProgetto ------" + scenario + " ------------------");
      
        /**
         * aggiungo un elemento di prova che poi andra TOLTO 
         */
        Element piovosita = docW.createElement("piovosita");
        piovosita.appendChild(docW.createTextNode("840"));
        elemento.appendChild(piovosita);
         
         
         
         
         
       if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal1");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
      
      /**
       * recupero l'informazione sullo idscenario che mi informa sul cuaa e lo scenario di un determinato cuaa
       */ 
       
       //  ELContext elContext = FacesContext.getCurrentInstance().getELContext();
      //  DettaglioCuaa dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
       
       /**
        * inserisco nei parametri diprogetto la piovosità ed il costo dell'energia.La piovosità non è un parametro di progetto ma un dato preso da acqua_stoccaggio e legato al cuaa 
        * ed allo scenario.Il costo dell'energia è invece un parametro di progetto e quindi presente nella tabella dei parametri di progetto.
        */ 
        //query per ottenere il costo energia
       //Query q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ DettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 3  and p.idNomeparametro.id = 19" );
       Query q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario+" and p.idTrattamento.id = 3  and p.idNomeparametro.id = 19" );

       List<db.ParametridiprogettoS> para1 = (List<db.ParametridiprogettoS>)q.getResultList();
       
       
       //System.out.println("  numero risultati trovati in parametri di progetto " + para1.size() + " scenario " + scenario );
       
       Element costoenergia = docW.createElement(para1.get(0).getIdNomeparametro().getNome());
       costoenergia.appendChild(docW.createTextNode(para1.get(0).getValore()) );
       elemento.appendChild(costoenergia);
       
        //query per inserire la piovosita
        //q = entityManager.createQuery("SELECT p FROM AcquastoccaggioI p where  p.idscenario = "+dettaglioCuaa.getIdscenario()+" ; " );
        /*q = entityManager.createQuery("SELECT p FROM AcquastoccaggioI p where  p.idscenario = "+ scenario +" ; " );
       //q = entityManager.createQuery("SELECT p FROM AcquastoccaggioI p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 3" );
       db.AcquastoccaggioI piovosita =(db.AcquastoccaggioI) q.getSingleResult();
       piovosita.getPioggia();
       
       Element pioggia = docW.createElement("piovosita");
       pioggia.appendChild(docW.createTextNode(piovosita.getPioggia().toString()));
       elemento.appendChild(pioggia);*/
       
      
      /**
       * prendo tutti  i parametri di progetto della seprazione a vite elicoidale e inserisco quelli che mi servono 
       * nel file xml 
       */                  
     // q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 3" );
        q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario+" and p.idTrattamento.id = 3" );

        
     List<db.ParametridiprogettoS> parametri = (List<db.ParametridiprogettoS>)q.getResultList();
     
     /**
      * creo il nodo della separazione a vite elicoidale e lo attacco a quello dei parametri di progetto
      */
     
      Element elementosv = docW.createElement("separatore_a_vite_elicoidale");
      elemento.appendChild(elementosv);
     
     this.inserisciSeparazioneAVite(elementosv, parametri);
       
     /**
      * recupero i parametri delle vasche
      */
      /*q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 6" );
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
     
      Element elementost = docW.createElement("stoccaggi");
      elemento.appendChild(elementost);
     
     this.inserisciVasche(elementost, parametri,dettaglioCuaa.getIdscenario());*/
     
     
     /**
      * recupero i paramerti delle platee e le inserisco
      */
    /* q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 7" );
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciPlatee(elementost, parametri,dettaglioCuaa.getIdscenario());*/
     
      
      /**
       * creo l'elemento digestione
       */
       Element elementoDigestione = docW.createElement("adcstr");
      elemento.appendChild(elementoDigestione);
      /**
       * recupero iparametri diprogetto della digestione anaerobica e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 9" );
        q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario +" and p.idTrattamento.id = 9" );

      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciDigestione(elementoDigestione, parametri);
    
     
     
      /**
       * creo l'elemento separazione centrifuga
       */
      Element elementoCentrifuga = docW.createElement("centrifuga");
      elemento.appendChild(elementoCentrifuga);
      /**
       * recupero iparametri diprogetto della separazione centrifuga e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 11" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 11" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciSeprazioneCentrifuga(elementoCentrifuga, parametri);
    
      
     
      /**
       * creo l'elemento sbr
       */
      Element elementoSbr = docW.createElement("sbr");
      elemento.appendChild(elementoSbr);
      /**
       * recupero iparametri diprogetto dell'sbr-ndn e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 5" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inseriscisbr(elementoSbr, parametri);
      this.inserisciParametri(elementoSbr, parametri);
    
      /**
       * creo l'elemento sbr
       */
      Element elementoSbrcontinuo = docW.createElement("sbr_continuo");
      elemento.appendChild(elementoSbrcontinuo);
      /**
       * recupero iparametri diprogetto dell'sbr-continuo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 13" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inseriscisbrcontinuo(elementoSbrcontinuo, parametri);
      this.inserisciParametri(elementoSbrcontinuo, parametri);
      
      /**
       * creo l'elemento flottazione
       */
      Element elementoFlottatore = docW.createElement("flottatore");
      elemento.appendChild(elementoFlottatore);
      /**
       * recupero iparametri diprogetto della flottazione e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 12" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciFlottazione(elementoFlottatore, parametri);
    
      
      
      /**
       * creo l'elemento rimozione azoto
       */
      Element elementoRimozioneAzoto = docW.createElement("rimozioneazoto");
      elemento.appendChild(elementoRimozioneAzoto);
      /**
       * recupero iparametri diprogetto della flottazione e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 14" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciRimozioneAzoto(elementoRimozioneAzoto, parametri);
      this.inserisciParametri(elementoRimozioneAzoto, parametri);
      
      /**
       * creo l'elemento rimozione azoto
       */
      Element elementoStrippaggioCaldo = docW.createElement("strippaggiocaldo");
      elemento.appendChild(elementoStrippaggioCaldo);
      /**
       * recupero iparametri diprogetto della flottazione e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 15" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
     // this.inserisciStrippaggioCaldo(elementoStrippaggioCaldo, parametri);
       this.inserisciParametri(elementoStrippaggioCaldo, parametri);
      
      
      /**
       * creo l'elemento strippaggio a freddo
       */
      Element elementoStrippaggioFreddo = docW.createElement("strippaggiofreddo");
      elemento.appendChild( elementoStrippaggioFreddo);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 16" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciStrippaggioFreddo(elementoStrippaggioFreddo, parametri);
       this.inserisciParametri(elementoStrippaggioFreddo, parametri);
      
      
      
      /**
       * creo l'elemento adcstr - plugflow
       */
      Element elementoAdrplugflow = docW.createElement("adcstr_plugflow");
      elemento.appendChild( elementoAdrplugflow);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 17" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
      
     
      
      
      /**
       * creo l'elemento adcstr - plugflow
       */
      Element elementoStabilizzazione = docW.createElement("stabilizzazione_aerobica");
      elemento.appendChild(elementoStabilizzazione);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 10" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri(elementoStabilizzazione, parametri);
      
      
       
        /**
       * creo l'elemento evaporazione
       */
      Element elementoEvaporazione = docW.createElement("evaporazione");
      elemento.appendChild(elementoEvaporazione);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 18 or ( p.idTrattamento.id = 9 and p.idNomeparametro.id = 35 and p.idScenario.idscenario = "+ scenario + " )" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri(elementoEvaporazione, parametri);
       
       
       
        /**
       * creo l'elemento evaporazione
       */
      Element elementoFitodepurazione = docW.createElement("fitodepurazione");
      elemento.appendChild(elementoFitodepurazione);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 19" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri(elementoFitodepurazione, parametri);
       
       
       
       
        /**
       * creo l'elemento evaporazione
       */
      Element elementoCompostaggio = docW.createElement("compostaggio");
      elemento.appendChild(elementoCompostaggio);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 20" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri(elementoCompostaggio, parametri);
       
       
        /**
       * creo l'elemento trasporto
       */
      Element elementoTrasporto = docW.createElement("trasporto");
      elemento.appendChild(elementoTrasporto);
      
     /**
       * creo l'elemento liquame per trasporto
       */
      Element elementoTrasportoLiquame = docW.createElement("liquame");
      elementoTrasporto.appendChild(elementoTrasportoLiquame);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 21 and p.discriminante = 'liquido'" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri( elementoTrasportoLiquame, parametri);
       
       
        /**
       * creo l'elemento liquame per trasporto
       */
      Element elementoTrasportoPalabile = docW.createElement("letame");
      elementoTrasporto.appendChild(elementoTrasportoPalabile);
      /**
       * recupero iparametri diprogetto del modulo srtippaggio a fredo e li inserisco
       */
      //q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ dettaglioCuaa.getIdscenario()+" and p.idTrattamento.id = 5" );
      q = entityManager.createQuery("SELECT p FROM ParametridiprogettoS p where p.idScenario.idscenario = "+ scenario + " and p.idTrattamento.id = 21 and p.discriminante = 'palabile'" );
 
      parametri = (List<db.ParametridiprogettoS>)q.getResultList();
      //this.inserisciAdrplugflow(elementoAdrplugflow, parametri);
       this.inserisciParametri( elementoTrasportoPalabile, parametri);
       
      
     // Connessione.getInstance().chiudi(); 
       
    }
    
     private void inserisciAdrplugflow(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
       
        
        /**
         * inserisco i codici dei parametri presenti nella tabella parametri di progetto per la digestione anerobica
         * cosi da verificare facilmetne se un prametro c'è o no
         */
        Set idparametri = new HashSet();
       // idparametri.add(39);
       // idparametri.add(40);
      //  idparametri.add(41);
      //  idparametri.add(62);
      //  idparametri.add(66);
      //  idparametri.add(70);
      //  idparametri.add(63);
      //  idparametri.add(67);
      //  idparametri.add(71);
      //  idparametri.add(64);
      //  idparametri.add(68);
     //   idparametri.add(72);
        idparametri.add(65);
        idparametri.add(69);
        idparametri.add(73);
        idparametri.add(74);
        idparametri.add(75);
        idparametri.add(99);
        
        
        
         Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
        
         ListIterator<db.ParametridiprogettoS> iterP = parametri.listIterator();
         
         Element temp = null;
         
         /**
          * i parametri della digestione anaerobica che vanno sotto il nodo parametri 
          * vanno dal 32 al 38
          */
         
         while(iterP.hasNext())
         {
            db.ParametridiprogettoS p = iterP.next();
            if(p.getIdNomeparametro().getId() >= 32 && p.getIdNomeparametro().getId() <= 38 || p.getIdNomeparametro().getId()== 98 || p.getIdNomeparametro().getId()== 160)
            {
                temp = docW.createElement(p.getIdNomeparametro().getNome());
                temp.appendChild(docW.createTextNode(p.getValore()));
                elemento.appendChild(temp);
            }
         }
         
         /**
          * creo il nodo valori_biogas
          */
         
         elemento = docW.createElement("valori_biogas");
         elementoPadre.appendChild(elemento);
         boolean trovato = false;
         /**
          * inizializzo all'iniziola lista dei parametri
          */
         iterP = parametri.listIterator();
         
          while (iterP.hasNext()) {
            db.ParametridiprogettoS p = iterP.next();
            /**
             * se il parametro è conteuto nella lista dei parametri in alto allora la inserisco
             */
            if (idparametri.contains(p.getIdNomeparametro().getId())) {
                if (p.getIdNomeparametro().getId() == 69 || p.getIdNomeparametro().getId() == 73 || p.getIdNomeparametro().getId() == 74 || p.getIdNomeparametro().getId() == 75 || p.getIdNomeparametro().getId() == 99) {
                    /**
                     * se il prametro è il 69 o il 73 significa che è una biomassa allora devo conrtollare anche il 
                     * contenutoattributo che corrisponda ad 8 ovvero alla biomassa per non inserire altri parametri
                     */
                    //if (p.getContenutoattributo().equals("8")) 
                    /**
                     * trovato nello switch
                     */
                    trovato = false;
                    String t1 = p.getIdNomeparametro().getNome();
                    switch (p.getContenutoattributo()) {
                        case "1":
                            t1 = t1 +"_bov";
                            trovato = true;
                            break;
                        case "2":
                             t1 = t1 +"_sui";
                              trovato = true;
                            break;
                        case "3":
                             t1 = t1 +"_avi";
                              trovato = true;
                            break;
                        case "4":
                             t1 = t1 +"_alt";
                              trovato = true;
                            break;
                        /*case "5":
                             t1 = t1 +"_insilati";
                            break;
                        case "6":
                             t1 = t1 +"_glicerina";
                            break;
                        case "7":
                             t1 = t1 +"_scarti";
                            break;
                        case "8":
                             t1 = t1 +"_colture";
                            break;*/

                    }
                    if(trovato)
                    {
                        temp = docW.createElement(t1);
                        temp.appendChild(docW.createTextNode(p.getValore()));
                        elemento.appendChild(temp);
                    }
                    //} } else {
                     //   temp = docW.createElement(p.getIdNomeparametro().getNome());
                     //   temp.appendChild(docW.createTextNode(p.getValore()));
                      //  elemento.appendChild(temp);
                    }//close if
                
            }//close if
        }//close while
         
          
          
          /**
          * creo il nodo eco_ad
          */
         
         elemento = docW.createElement("eco_ad");
         elementoPadre.appendChild(elemento);
         iterP = parametri.listIterator();
         
         /**
          * per verificare la presenza di un parametro di progetto uso
          * un hashset come insieme contenitore dei valori numerici che rappresentano
          * i parametri di progetto che devo inserire nel nodo eco_ad
          */
         idparametri = new HashSet();
        idparametri.add(49);
         idparametri.add(50);
          idparametri.add(18);
           idparametri.add(19);
            idparametri.add(52);
             idparametri.add(61);
        
        /**
         * ciclo sulla lista deiparametri di progetto verficando la presenza nelhashset
         */
         while(iterP.hasNext())
         {
             db.ParametridiprogettoS pa = iterP.next();
             
             if(idparametri.contains(pa.getIdNomeparametro().getId()))
                     {
                         temp = docW.createElement(pa.getIdNomeparametro().getNome());
                        temp.appendChild(docW.createTextNode(pa.getValore()));
                        elemento.appendChild(temp);
                     }
         }
        
          
          
          /**
          * creo il nodo tipi_biomasse
          */
         
         elemento = docW.createElement("tipi_biomassa");
         elementoPadre.appendChild(elemento);
          
          Element te = null;
        Element biomInsilati = docW.createElement("biomassa");
        te = docW.createElement("id");
        te.appendChild(docW.createTextNode("1"));
        biomInsilati.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("insilati"));
        biomInsilati.appendChild(te);
        elemento.appendChild(biomInsilati);
        
        
        Element biomGlicerina = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("2"));
        biomGlicerina.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("glicerina"));
        biomGlicerina.appendChild(te);
        elemento.appendChild(biomGlicerina);
        
        Element biomScarti = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("3"));
        biomScarti.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("scarti organici agricoli"));
        biomScarti.appendChild(te);
        elemento.appendChild(biomScarti);
        Element biomColture = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("4"));
        biomColture.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("colture energetiche"));
        biomColture.appendChild(te);
        elemento.appendChild(biomColture);
          /**
          * inizializzo all'iniziola lista dei parametri
          */
         iterP = parametri.listIterator();
         /**
          * svuoto l'insieme dei valori numerici di confronto con i parametri e lo rimpio con quelli che mi servono 
          * per le biomasse
          */
        idparametri = new HashSet();
        idparametri.add(43);
        idparametri.add(44);
        idparametri.add(45);
        idparametri.add(46);
        idparametri.add(47);
        idparametri.add(48);
        idparametri.add(74);
        idparametri.add(75);
        idparametri.add(69);
        idparametri.add(73);
        idparametri.add(99);
        
        
         
         while(iterP.hasNext())
         {
             db.ParametridiprogettoS pa = iterP.next();
             /**
              * tra i diveri parametri per le biomasse a me servono solo quelli riferiti all'input xml 
              * che sono i parametri 74,43,44,45,46,47,48,75
              */
             if(idparametri.contains(pa.getIdNomeparametro().getId()))
              {
              te = docW.createElement(pa.getIdNomeparametro().getNome());
              te.appendChild(docW.createTextNode(pa.getValore()));
             
              if(pa.getContenutoattributo() == null)
                  continue;
              
             switch(Integer.parseInt(pa.getContenutoattributo()))
             {
                 
                 
                 case 5:
                     
                      biomInsilati.appendChild(te);
                     break;
                 case 6:
                     biomGlicerina.appendChild(te);
                     break;
                 case 7:
                      biomScarti.appendChild(te);
                     break;
                 case 8:
                      biomColture.appendChild(te);
                     break;
             }
             
              }
         }
         
         
    
    }
    
    
    private void inserisciParametri(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
    }
    
   /* private void inserisciRimozioneAzoto(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
    }
    
     private void inserisciStrippaggioCaldo(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
    }
     
     private void inserisciStrippaggioFreddo(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
    }
    
    private void inseriscisbr(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
         Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
         
    }*/
    
    
    
    
    /*private void inseriscisbrcontinuo(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
         Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
         
         
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        Element tampone = null;
        
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
          
            tampone = docW.createElement(pa.getIdNomeparametro().getNome());
            tampone.appendChild(docW.createTextNode(pa.getValore()));
            elemento.appendChild(tampone);
        }
         
    }*/
    
    
    private void inserisciSeparazioneAVite(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        /**
         * inserisco i codici dei parametri presenti nella tabella parametri di progetto per il sepratore piccolo
         * cosi da verificare facilmetne se un prametro c'è o no
         */
        Set idparametri = new HashSet();
        idparametri.add(16);
        idparametri.add(17);
        idparametri.add(61);
        idparametri.add(18);
        idparametri.add(19);
        idparametri.add(20);
        idparametri.add(21);
        idparametri.add(22);
        idparametri.add(23);
        idparametri.add(24);
        idparametri.add(25);
        idparametri.add(26);
        idparametri.add(27);
        Element elemento = docW.createElement("piccolo");
         elementoPadre.appendChild(elemento);

        
        Element separatorepiccolo = null;
        
        ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();

        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
            /**
             * se il parametro è presente nella hashset dei parametri allora lo inserisco nel file xml
             */
            if(pa.getDiscriminante() != null)
            {
            if (idparametri.contains(pa.getIdNomeparametro().getId()) && pa.getDiscriminante().equals("piccolo")) {
               // System.out.println();
                separatorepiccolo = docW.createElement(pa.getIdNomeparametro().getNome());
                separatorepiccolo.appendChild(docW.createTextNode(pa.getValore()) );
                elemento.appendChild(separatorepiccolo);
               // elementoPadre.appendChild(elemento);
            }
            }
        }
        
        
        
      Element  elemento1 = docW.createElement("grande");
      

        
        Element separatoregrande = null;
        
        para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();

        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
            /**
             * se il parametro è presente nella hashset dei parametri allora lo inserisco nel file xml
             */
            if(pa.getDiscriminante() != null)
            {
            if (idparametri.contains(pa.getIdNomeparametro().getId()) && pa.getDiscriminante().equals("grande")) {
                
                separatoregrande = docW.createElement(pa.getIdNomeparametro().getNome());
                separatoregrande.appendChild(docW.createTextNode(pa.getValore()) );
                elemento1.appendChild(separatoregrande);
            }
            }
        }
        
          elementoPadre.appendChild(elemento1);
          
          
          
         /**
          * aggiungo i parametri c1,c2,m1,m2
          */ 
         Element valori = docW.createElement("valori");
         elementoPadre.appendChild(valori);
         
          /**
          * aggiungo i parametri per i bovini
          */
         Element valoriBovini = docW.createElement("bovini");
         valori.appendChild(valoriBovini);
         
         //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         String atr = "";
         //contenitore dell'attributo1
         String atr1 = "";
         //elemento xml tampone per i bovini
         Element coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("1"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
        
         
          /**
          * aggiungo i parametri per i suini
          */
         Element valoriSuini = docW.createElement("suini");
         valori.appendChild(valoriSuini);
         
         
          //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("2"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
          /**
          * aggiungo i parametri per i avicoli
          */
         Element valoriAvicoli = docW.createElement("avicoli");
         valori.appendChild(valoriAvicoli);
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("3"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
         
          /**
          * aggiungo i parametri per gli altri tipi
          */
         Element valoriAltri = docW.createElement("altri");
         valori.appendChild(valoriAltri);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("4"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
          
          
          
          
           /**
          * aggiungo i parametri per le biomasse
          */
         Element valoriBiomasse = docW.createElement("biomasse");
         valori.appendChild(valoriBiomasse);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("9"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
          
          
          
          
         
    }
    
    
    
     private void inserisciPlatee(Element elementoPadre,List<db.ParametridiprogettoS> parametri,Long idscenario)
    {
      
         
        
        
        /**
         * interrogo il db per conoscere i nomi degli stoccaggi in vasca
         */
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
          /**
           * recupero la lista degli stoccaggi che contengono forma liquida ovvero vasche
           */
      Query q = null;
     
          q = entityManager.createQuery("Select p from TipostoccaggioS p where p.forma.id = 2 order by p.idstoccaggio");
    
      
      List<db.TipostoccaggioS> stocchi =(List<db.TipostoccaggioS>)q.getResultList();
        
      ListIterator<db.TipostoccaggioS> stocchiIter  = stocchi.listIterator();
      
      /**
       * devo scoprire anche le dimesioni delle vasche dell'azienda
       */
      Query q2 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", idscenario);
        db.ScenarioI scenario =(db.ScenarioI)q2.getSingleResult();
        
        Iterator<db.StoccaggioI> stoccaggi = scenario.getStoccaggioICollection().iterator();
        
        /**
         * creo una lista degli elementi che rappresentano tutti vasca ma con il parametro nome
         * diverso questa lista di elementi la usero anche dopo per l'inserinebto dei singoli parametri delle vasche
         * ovvero la uso come lista di puntatori agli elementi dei nodi xml
         */
        List<Element> listaElementi = new LinkedList<Element>();
        Element ele = null;
        int pos = 0;
        /**
         * flag che mi dice se ho trovato lo stoccaggio o menno
         */
        boolean trovato = false;
        while(stocchiIter.hasNext())
        {
            trovato = false;
            db.TipostoccaggioS stoc = stocchiIter.next();
            listaElementi.add(docW.createElement("platea"));
            ele = docW.createElement("nome");
            //sostituisce gli spazi con un underscore
            String temp = stoc.getDescrizione().replaceAll("\\s+","_");
            ele.appendChild(docW.createTextNode(temp));
            listaElementi.get(pos).appendChild(ele);
            elementoPadre.appendChild(listaElementi.get(pos));
            
            /**
             * cerco lo stoccagio nella lista di quelli di un dterminata azienda e se lo trovo inserisco
             * nel nodo xml i valori numerici corrispondenti altrimenti li metto a zero
             *  
             */
            stoccaggi = scenario.getStoccaggioICollection().iterator();
            while(stoccaggi.hasNext())
            {
                db.StoccaggioI sto = stoccaggi.next();
                
                if(sto.getIdstoccaggio().getDescrizione().equals(stoc.getDescrizione()))
                {
                    trovato = true;
                    ele = docW.createElement("superficie_scoperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getSuperficiescoperta())));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("superficie_coperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getSuperficietotale() - sto.getSuperficiescoperta())));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("capacita");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getCapacita())));
                    listaElementi.get(pos).appendChild(ele);
                }
            }
            
            /**
             * se nel ciclo while precedente non l'ho trovato 
             * metto icampi corrispondenti a zero
             */
            if(!trovato)
            {
                 ele = docW.createElement("superficie_scoperta");
                    ele.appendChild(docW.createTextNode("0"));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("superficie_coperta");
                    ele.appendChild(docW.createTextNode("0"));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("capacita");
                    ele.appendChild(docW.createTextNode("0"));
                    listaElementi.get(pos).appendChild(ele);
            }
            
            pos++;
        }
        
      
      
        ListIterator<db.ParametridiprogettoS> para = parametri.listIterator();
        
        
        Element el = null;
        /**
         * passo in rassegna la lista dei parametri di progetto delle vasche ed in funzione del contenuto del contenutoattributo
         * che mi indica il tipo di vasca inserisco i suoi parametri nella lista delle vasche create precedentemente. Tale lista deriva da
         * una query che ritorna le vasche ordinate per idstoccaggio ecco perchè le posso inserire nella listaelementi
         */
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
            
            if(pa.getContenutoattributo() == null )
                continue;
            
            
            if ( pa.getContenutoattributo().equals("21")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(0).appendChild(el);
                                           
            }


            if (pa.getContenutoattributo().equals("22")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(1).appendChild(el);
            }



            if (pa.getContenutoattributo().equals("23")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(2).appendChild(el);
            }


            if (pa.getContenutoattributo().equals("24")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(3).appendChild(el);
            }


            
        }
        
        // Connessione.getInstance().chiudi(); 
        
    }
    
    
    
    private void inserisciVasche(Element elementoPadre,List<db.ParametridiprogettoS> parametri,Long idscenario)
    {
      
        
        
        
        /**
         * interrogo il db per conoscere i nomi degli stoccaggi in vasca
         */
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("provagiorgio13");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
          /**
           * recupero la lista degli stoccaggi che contengono forma liquida ovvero vasche
           */
      Query q = null;
     
          q = entityManager.createQuery("Select p from TipostoccaggioS p where p.forma.id = 1 order by p.idstoccaggio");
    
      
      List<db.TipostoccaggioS> stocchi =(List<db.TipostoccaggioS>)q.getResultList();
        
      ListIterator<db.TipostoccaggioS> stocchiIter  = stocchi.listIterator();
      
      /**
       * devo scoprire anche le dimesioni delle vasche dell'azienda
       */
      Query q2 = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", idscenario);
        db.ScenarioI scenario =(db.ScenarioI)q2.getSingleResult();
        
        Iterator<db.StoccaggioI> stoccaggi = scenario.getStoccaggioICollection().iterator();
        
       // System.out.println("aggiungi stocccaggi nell xml numero stoccaggi : " + scenario.getStoccaggioICollection().size() + " sceanrio " + scenario.getIdscenario()+" id " + scenario.getId()+ "idsceanrio " + idscenario);
        
      /*  while(stoccaggi.hasNext())
        {
            db.StoccaggioI stotemp = stoccaggi.next();
            System.out.println("aggiungi stocccaggi nell xml " + stotemp.getIdstoccaggio().getDescrizione() + stotemp.getSuperficiescoperta() + stotemp.getSuperficietotale() + stotemp.getCapacita());
        }*/
        
       // stoccaggi = scenario.getStoccaggioICollection().iterator();
        /**
         * creo una lista degli elementi che rappresentano tutti vasca ma con il parametro nome
         * diverso questa lista di elementi la usero anche dopo per l'inserinebto dei singoli parametri delle vasche
         * ovvero la uso come lista di puntatori agli elementi dei nodi xml
         */
        List<Element> listaElementi = new LinkedList<Element>();
        Element ele = null;
        int pos = 0;
        /**
         * flag che mi dice se ho trovato lo stoccaggio o menno
         */
        boolean trovato = false;
        /**
         * per ogni stocaggio definito negli stoccaggi possbili
         */
        while(stocchiIter.hasNext())
        {
           // trovato = false;
            db.TipostoccaggioS stoc = stocchiIter.next();
            listaElementi.add(docW.createElement("vasca"));
            ele = docW.createElement("nome");
            //sostituisce gli spazi con un underscore
            String temp = stoc.getDescrizione().replaceAll("\\s+","_");
            ele.appendChild(docW.createTextNode(temp));
            listaElementi.get(pos).appendChild(ele);
            elementoPadre.appendChild(listaElementi.get(pos));
            
            /**
             * cerco lo stoccagio nella lista di quelli di un dterminata azienda e se lo trovo inserisco
             * nel nodo xml i valori numerici corrispondenti altrimenti li metto a zero
             *  
             */
            stoccaggi = scenario.getStoccaggioICollection().iterator();
            double superficiescoperta = 0;
            double superficiecoperta = 0;
            double capacita = 0;
            while(stoccaggi.hasNext())
            {
                db.StoccaggioI sto = stoccaggi.next();
                
                if(sto.getIdstoccaggio().getDescrizione().equals(stoc.getDescrizione()))
                {
                   // trovato = true;
                   /* ele = docW.createElement("superficie_scoperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getSuperficiescoperta())));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("superficie_coperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getSuperficietotale() - sto.getSuperficiescoperta())));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("capacita");
                    ele.appendChild(docW.createTextNode(String.valueOf(sto.getCapacita())));
                    listaElementi.get(pos).appendChild(ele);*/
                    superficiescoperta = superficiescoperta + sto.getSuperficiescoperta();
                    superficiecoperta = superficiecoperta + (sto.getSuperficietotale() - sto.getSuperficiescoperta());
                    capacita = capacita + sto.getCapacita();
                    
                }
            }
            
            /**
             * se nel ciclo while precedente non l'ho trovato 
             * metto icampi corrispondenti a zero
             */
           // if(!trovato)
         //   {
                 ele = docW.createElement("superficie_scoperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(superficiescoperta)));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("superficie_coperta");
                    ele.appendChild(docW.createTextNode(String.valueOf(superficiecoperta)));
                    listaElementi.get(pos).appendChild(ele);
                    
                    ele = docW.createElement("capacita");
                    ele.appendChild(docW.createTextNode(String.valueOf(capacita)));
                    listaElementi.get(pos).appendChild(ele);
         //   }
            
            pos++;
        }
        
      
      
        ListIterator<db.ParametridiprogettoS> para = parametri.listIterator();
        
        
        Element el = null;
        /**
         * passo in rassegna la lista dei parametri di progetto delle vasche ed in funzione del contenuto del contenutoattributo
         * che mi indica il tipo di vasca inserisco i suoi parametri nella lista delle vasche create precedentemente. Tale lista deriva da
         * una query che ritorna le vasche ordinate per idstoccaggio ecco perchè le posso inserire nella listaelementi
         */
        while (para.hasNext()) {
            db.ParametridiprogettoS pa = para.next();
            
            if(pa.getContenutoattributo() == null )
                continue;
            
            
            if ( pa.getContenutoattributo().equals("1")) {
                
               // System.out.println(" stoccagi caso 1  *********** nome parametro " + pa.getIdNomeparametro().getNome() + " valore " + pa.getValore() );
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(0).appendChild(el);
                                           
            }


            if (pa.getContenutoattributo().equals("2")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(1).appendChild(el);
            }



            if (pa.getContenutoattributo().equals("3")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(2).appendChild(el);
            }


            if (pa.getContenutoattributo().equals("4")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(3).appendChild(el);
            }


            if (pa.getContenutoattributo().equals("5")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(4).appendChild(el);
            }


            if (pa.getContenutoattributo().equals("6")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(5).appendChild(el);
            }


            if (pa.getContenutoattributo().equals("7")) {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                listaElementi.get(6).appendChild(el);
            }
        }
        
        // Connessione.getInstance().chiudi(); 
        
    }
    
    
   
    
    private void inserisciSeprazioneCentrifuga(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
         Element elementosenza = docW.createElement("senza_additivi");
         elementoPadre.appendChild(elementosenza);  
         
         Element elementocon = docW.createElement("con_additivi");
         elementoPadre.appendChild(elementocon);  
         
         ListIterator<db.ParametridiprogettoS> para = parametri.listIterator();
         
         Element el = null;
         
         while(para.hasNext())
         {
             db.ParametridiprogettoS pa = para.next();
             
             if(pa.getDiscriminante() == null)
                 continue;
             
             if(pa.getDiscriminante().equals("senza_additivi"))
             {
                el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                elementosenza.appendChild(el);
             }
             
             
             if(pa.getDiscriminante().equals("con_additivi"))
             {
                 el = docW.createElement(pa.getIdNomeparametro().getNome());
                el.appendChild(docW.createTextNode(pa.getValore()));
                elementocon.appendChild(el); 
             }
         }
         
         
         
         /**
          * aggiungo i parametri c1,c2,m1,m2
          */ 
         Element valori = docW.createElement("valori");
         elementoPadre.appendChild(valori);
         
          /**
          * aggiungo i parametri per i bovini
          */
         Element valoriBovini = docW.createElement("bovini");
         valori.appendChild(valoriBovini);
         
         //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         String atr = "";
         //contenitore dell'attributo1
         String atr1 = "";
         //elemento xml tampone per i bovini
         Element coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("1"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
        
         
          /**
          * aggiungo i parametri per i suini
          */
         Element valoriSuini = docW.createElement("suini");
         valori.appendChild(valoriSuini);
         
         
          //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("2"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
          /**
          * aggiungo i parametri per i avicoli
          */
         Element valoriAvicoli = docW.createElement("avicoli");
         valori.appendChild(valoriAvicoli);
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("3"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
         
          /**
          * aggiungo i parametri per i bovini
          */
         Element valoriAltri = docW.createElement("altri");
         valori.appendChild(valoriAltri);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("4"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
         
          /**
          * aggiungo i parametri per le biomasse
          */
         Element valoriBiomasse = docW.createElement("biomasse");
         valori.appendChild(valoriBiomasse);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("4"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
          
          
          
          
         // Connessione.getInstance().chiudi(); 
         
    }
    
    
    private void inserisciFlottazione(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        /*Set idparametri = new HashSet();
        idparametri.add(96);
        idparametri.add(17);
        idparametri.add(21);
        idparametri.add(100);
        idparametri.add(101);
        idparametri.add(102);
        idparametri.add(103);
        idparametri.add(19);
        idparametri.add(104);
        idparametri.add(105);
        idparametri.add(106);
        idparametri.add(23);
        idparametri.add(24);
        idparametri.add(25);
        idparametri.add(26);
        idparametri.add(27);*/



        Element elemento = docW.createElement("parametri");
        elementoPadre.appendChild(elemento);


        ListIterator<db.ParametridiprogettoS> iterP = parametri.listIterator();

        Element temp = null;

        /**
         * i parametri della digestione anaerobica che vanno sotto il nodo
         * parametri sono tuttti quelli della flottazione tranne quelli che hanno codice 28,29,30,31 che sono le efficienze che vanno dopo
         */
        while (iterP.hasNext()) {
            db.ParametridiprogettoS p = iterP.next();
            if (p.getIdNomeparametro().getId() != 28 && p.getIdNomeparametro().getId() != 29 && p.getIdNomeparametro().getId() != 30 && p.getIdNomeparametro().getId() != 31) {
                temp = docW.createElement(p.getIdNomeparametro().getNome());
                temp.appendChild(docW.createTextNode(p.getValore()));
                elemento.appendChild(temp);
            }
        }
        
        
        
         ListIterator<db.ParametridiprogettoS> para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
        
        
        
        
        
        
        
        /**
          * aggiungo i parametri c1,c2,m1,m2
          */ 
         Element valori = docW.createElement("valori");
         elementoPadre.appendChild(valori);
         
          /**
          * aggiungo i parametri per i bovini
          */
         Element valoriBovini = docW.createElement("bovini");
         valori.appendChild(valoriBovini);
         
         //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         String atr = "";
         //contenitore dell'attributo1
         String atr1 = "";
         //elemento xml tampone per i bovini
         Element coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("1"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBovini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
        
         
          /**
          * aggiungo i parametri per i suini
          */
         Element valoriSuini = docW.createElement("suini");
         valori.appendChild(valoriSuini);
         
         
          //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("2"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriSuini.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
          /**
          * aggiungo i parametri per i avicoli
          */
         Element valoriAvicoli = docW.createElement("avicoli");
         valori.appendChild(valoriAvicoli);
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("3"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAvicoli.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
         
         
          /**
          * aggiungo i parametri per gli altri tipi
          */
         Element valoriAltri = docW.createElement("altri");
         valori.appendChild(valoriAltri);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("4"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriAltri.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
          
          
          
          
           /**
          * aggiungo i parametri per le biomasse
          */
         Element valoriBiomasse = docW.createElement("biomasse");
         valori.appendChild(valoriBiomasse);
         
         
         
           //riinizializzo il puntatore para all'inizio della lista dei parametri
          para = (ListIterator<db.ParametridiprogettoS>) parametri.listIterator();
         //contenitore dell'attributo 
         atr = "";
         //contenitore dell'attributo1
         atr1 = "";
         //elemento xml tampone per i bovini
         coefficienteB = null;
          while(para.hasNext())
          {
              db.ParametridiprogettoS pa = para.next();
              atr = pa.getContenutoattributo();
              //attributo uguale a 1 equivale  nei parametri di progetto a bovino
              //mediante id_attributo , id_entita , contenutoattributo
              if(atr != null && atr.equals("9"))
              {
                  atr1 = pa.getContenutoattributo1();
                  //contenutoatributo1 in questo caso contiene le caratteristiche chimiche e quando c'è 1 
                  //rappresenta volume
                  switch (atr1) {
                      case "1":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_m3");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;
                          
                      case "2":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_at");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                      
                      case "3":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_am");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                      case "4":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ss");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                          
                       case "5":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_sv");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "6":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_ft");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                          valoriBiomasse.appendChild(coefficienteB);
                          break;     
                           
                       case "7":
                          coefficienteB = docW.createElement(pa.getIdNomeparametro().getNome() + "_pt");
                          coefficienteB.appendChild(docW.createTextNode(pa.getValore()));
                         valoriBiomasse.appendChild(coefficienteB);
                          break;          
                          
                  }
              }
              
          }
        
        
                  
    }
    
    
    
    private void inserisciDigestione(Element elementoPadre,List<db.ParametridiprogettoS> parametri)
    {
        
        /**
         * inserisco i codici dei parametri presenti nella tabella parametri di progetto per la digestione anerobica
         * cosi da verificare facilmetne se un prametro c'è o no
         */
        Set idparametri = new HashSet();
       // idparametri.add(39);
       // idparametri.add(40);
      //  idparametri.add(41);
      //  idparametri.add(62);
      //  idparametri.add(66);
      //  idparametri.add(70);
      //  idparametri.add(63);
      //  idparametri.add(67);
      //  idparametri.add(71);
      //  idparametri.add(64);
      //  idparametri.add(68);
     //   idparametri.add(72);
        idparametri.add(65);
        idparametri.add(69);
        idparametri.add(73);
        idparametri.add(74);
        idparametri.add(75);
        idparametri.add(99);
        
        
        
         Element elemento = docW.createElement("parametri");
         elementoPadre.appendChild(elemento);
         
        
         ListIterator<db.ParametridiprogettoS> iterP = parametri.listIterator();
         
         Element temp = null;
         
         /**
          * i parametri della digestione anaerobica che vanno sotto il nodo parametri 
          * vanno dal 32 al 38
          */
         
         while(iterP.hasNext())
         {
            db.ParametridiprogettoS p = iterP.next();
            if(p.getIdNomeparametro().getId() >= 32 && p.getIdNomeparametro().getId() <= 38 || p.getIdNomeparametro().getId()== 98)
            {
                temp = docW.createElement(p.getIdNomeparametro().getNome());
                temp.appendChild(docW.createTextNode(p.getValore()));
                
                
                //System.out.println("nome parametro " + p.getIdNomeparametro().getNome() + " valore " + p.getValore() );
                
                elemento.appendChild(temp);
            }
         }
         
         /**
          * creo il nodo valori_biogas
          */
         
         elemento = docW.createElement("valori_biogas");
         elementoPadre.appendChild(elemento);
         boolean trovato = false;
         /**
          * inizializzo all'iniziola lista dei parametri
          */
         iterP = parametri.listIterator();
         
          while (iterP.hasNext()) {
            db.ParametridiprogettoS p = iterP.next();
            /**
             * se il parametro è conteuto nella lista dei parametri in alto allora la inserisco
             */
            if (idparametri.contains(p.getIdNomeparametro().getId())) {
                if (p.getIdNomeparametro().getId() == 69 || p.getIdNomeparametro().getId() == 73 || p.getIdNomeparametro().getId() == 74 || p.getIdNomeparametro().getId() == 75 || p.getIdNomeparametro().getId() == 99) {
                    /**
                     * se il prametro è il 69 o il 73 significa che è una biomassa allora devo conrtollare anche il 
                     * contenutoattributo che corrisponda ad 8 ovvero alla biomassa per non inserire altri parametri
                     */
                    //if (p.getContenutoattributo().equals("8")) 
                    /**
                     * trovato nello switch
                     */
                    trovato = false;
                    String t1 = p.getIdNomeparametro().getNome();
                    switch (p.getContenutoattributo()) {
                        case "1":
                            t1 = t1 +"_bov";
                            trovato = true;
                            break;
                        case "2":
                             t1 = t1 +"_sui";
                              trovato = true;
                            break;
                        case "3":
                             t1 = t1 +"_avi";
                              trovato = true;
                            break;
                        case "4":
                             t1 = t1 +"_alt";
                              trovato = true;
                            break;
                        /*case "5":
                             t1 = t1 +"_insilati";
                            break;
                        case "6":
                             t1 = t1 +"_glicerina";
                            break;
                        case "7":
                             t1 = t1 +"_scarti";
                            break;
                        case "8":
                             t1 = t1 +"_colture";
                            break;*/

                    }
                    if(trovato)
                    {
                        temp = docW.createElement(t1);
                        temp.appendChild(docW.createTextNode(p.getValore()));
                        elemento.appendChild(temp);
                    }
                    //} } else {
                     //   temp = docW.createElement(p.getIdNomeparametro().getNome());
                     //   temp.appendChild(docW.createTextNode(p.getValore()));
                      //  elemento.appendChild(temp);
                    }//close if
                
            }//close if
        }//close while
         
          
          
          /**
          * creo il nodo eco_ad
          */
         
         elemento = docW.createElement("eco_ad");
         elementoPadre.appendChild(elemento);
         iterP = parametri.listIterator();
         
         /**
          * per verificare la presenza di un parametro di progetto uso
          * un hashset come insieme contenitore dei valori numerici che rappresentano
          * i parametri di progetto che devo inserire nel nodo eco_ad
          */
         idparametri = new HashSet();
        idparametri.add(49);
         idparametri.add(50);
          idparametri.add(18);
           idparametri.add(19);
            idparametri.add(52);
             idparametri.add(61);
        
        /**
         * ciclo sulla lista deiparametri di progetto verficando la presenza nelhashset
         */
         while(iterP.hasNext())
         {
             db.ParametridiprogettoS pa = iterP.next();
             
             if(idparametri.contains(pa.getIdNomeparametro().getId()))
                     {
                         temp = docW.createElement(pa.getIdNomeparametro().getNome());
                        temp.appendChild(docW.createTextNode(pa.getValore()));
                        elemento.appendChild(temp);
                     }
         }
        
          
          
          /**
          * creo il nodo tipi_biomasse
          */
         
         elemento = docW.createElement("tipi_biomassa");
         elementoPadre.appendChild(elemento);
          
          Element te = null;
        Element biomInsilati = docW.createElement("biomassa");
        te = docW.createElement("id");
        te.appendChild(docW.createTextNode("1"));
        biomInsilati.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("insilati"));
        biomInsilati.appendChild(te);
        elemento.appendChild(biomInsilati);
        
        
        Element biomGlicerina = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("2"));
        biomGlicerina.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("glicerina"));
        biomGlicerina.appendChild(te);
        elemento.appendChild(biomGlicerina);
        
        Element biomScarti = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("3"));
        biomScarti.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("scarti organici agricoli"));
        biomScarti.appendChild(te);
        elemento.appendChild(biomScarti);
        Element biomColture = docW.createElement("biomassa");
         te = docW.createElement("id");
        te.appendChild(docW.createTextNode("4"));
        biomColture.appendChild(te);
        te = docW.createElement("nome");
        te.appendChild(docW.createTextNode("colture energetiche"));
        biomColture.appendChild(te);
        elemento.appendChild(biomColture);
          /**
          * inizializzo all'iniziola lista dei parametri
          */
         iterP = parametri.listIterator();
         /**
          * svuoto l'insieme dei valori numerici di confronto con i parametri e lo rimpio con quelli che mi servono 
          * per le biomasse
          */
        idparametri = new HashSet();
        idparametri.add(43);
        idparametri.add(44);
        idparametri.add(45);
        idparametri.add(46);
        idparametri.add(47);
        idparametri.add(48);
        idparametri.add(74);
        idparametri.add(75);
        idparametri.add(69);
        idparametri.add(73);
        idparametri.add(99);
        
        
         
         while(iterP.hasNext())
         {
             db.ParametridiprogettoS pa = iterP.next();
             /**
              * tra i diveri parametri per le biomasse a me servono solo quelli riferiti all'input xml 
              * che sono i parametri 74,43,44,45,46,47,48,75
              */
             if(idparametri.contains(pa.getIdNomeparametro().getId()))
              {
              te = docW.createElement(pa.getIdNomeparametro().getNome());
              te.appendChild(docW.createTextNode(pa.getValore()));
             
              if(pa.getContenutoattributo() == null)
                  continue;
              
             switch(Integer.parseInt(pa.getContenutoattributo()))
             {
                 
                 
                 case 5:
                     
                      biomInsilati.appendChild(te);
                     break;
                 case 6:
                     biomGlicerina.appendChild(te);
                     break;
                 case 7:
                      biomScarti.appendChild(te);
                     break;
                 case 8:
                      biomColture.appendChild(te);
                     break;
             }
             
              }
         }
         
         
    }        
    
}
