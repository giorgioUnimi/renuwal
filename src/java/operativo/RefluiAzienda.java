/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import ager.ContenitoreReflui;

import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import ager.trattamenti.Da;
import ager.trattamenti.LetameT;
import ager.trattamenti.LiquameT;
import ager.trattamenti.NMinerale;
import ager.trattamenti.Ndn;
import ager.trattamenti.Platea;
import ager.trattamenti.ReteIdrica;
import ager.trattamenti.Sbr;
import ager.trattamenti.SeparazioneAVite;
import ager.trattamenti.Stabilizzazione;
import ager.trattamenti.Vasca;
import db.AlternativaTrattamento;

import java.io.BufferedWriter;

//import ager.trattamenti.Vendita;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
//import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
//import java.util.Map;
//import javax.enterprise.context.SessionScoped;
//import javax.enterprise.inject.spi.Bean;
//import javax.enterprise.inject.spi.Bean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

//import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.ExternalContext;
//import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
//import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.InputOutputXml;

import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 * @author giorgio
 */
@ManagedBean(name = "refluiAzienda")
@ViewScoped


public class RefluiAzienda implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    
    /**
     * contiene le caratteristiche chimiche delle singole 4 tipologie di refluo
     * ovvero liquame bovino , liquamne suino , letame bovino , letame suinno
     */
    private List<Refluo> listaCaratteristiche = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
    private List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
    /**
     * contiene le caratteristiche chimiche del totale del letame e del liquame
     */
   
    
    private List<db.AlternativeS> listaAlternative = new LinkedList<db.AlternativeS>();
    private List<Integer> listaAlternativeID = new LinkedList<Integer>();
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private String descrizioneAlternativa="";
   
    private String immagine="../images/alt0.gif";
    //private List<db.FlussoS> listaFlussi = new LinkedList<db.FlussoS>();
    db.AlternativeS alternative2 = null;
    
    static int indiceDescrizione = 0;
     
    /**
     * riferitmento per l'alternativa scelta nella pagina alternative.xhtml
     */
    private int alternativa = 0;
    
    /**
     * lista delle pagine dei trattamenti. MI costruisco un array perchè mi serve discriminare 
     * la provenienza della navigazione tra la pagina alternative che richiama se stessa per effettuare 
     * i calcoli oppure le pagine della normale naviagazione oppure ancora
     * le pagine dei trattamenti.
     */
    private String[] paginetrattamenti = {"alternative.xhtml","separazione.xhtml","platea.xhtml","vasca.xhtml","parametrivasca.xhtml","parametriplatea.xhtml","parametriseparazione.xhtml"};
    
    ContenitoreReflui contenitoreIniziale = null;
    
          
             
             DettaglioCuaa dettaglioCuaa;
             
             /**
              * variabile che mi informa sulla scelta fatta dall'utente ovvero se vuole il ranking , 
              * il calcolo dell'alternativa migliore o il calcolo della singola alternativa
              */
             private String operazioneScelta ="rank";
             /**
              * peso da dare alla compoenente costo , emissioni , energia nell'operazione scelta dall'utente
              * peso che incide nel ranking e nel calcolo dell'alternativa milgiore
              */        
             //private double costo = 0.2;
             //private double emissionia = 0.25 ;
             //private double emissionig = 0.25 ;
             
             //private double energia = 0.3;
             
     //private List<List<String>> dynamicList; // Simulate fake DB.
   // private String[] dynamicHeaders; // Optional.
     private HtmlPanelGroup dynamicDataTableGroup; // Placeholder.
     //private HtmlPanelGroup dynamicDataTableGroup1;
    
     private HtmlPanelGrid dynamicGrid; 
     //private HtmlPanelGrid dynamicGrid1; 
     
     /**
      * riferimento al messagio del campo testo costo 
      * che contiene indicazioni nel caso in cui i pesi non siano corretti
      * Questo campo è in binding con il campo testo costo presente in alternative.xhtml
      * ed usato nel metodo presente in questo bean validaPesi
      */
     private UIComponent messaggioCosto;
     /**
      * mantiene lo stato sui pesi costo,emissioni,energia e blocca l'esecuzione del calcola nel caos 
      * in cui la somma non sia 1
      */
     private boolean pesicorretti = true;
     
     /**
      * rappresenta il numero random che viene attaccato al file xml di output
      * per identificare una determinata esecuzione
      */
    // private  String numeroRandom = "nessun numero";
     
     
     
     private boolean operazionesingola = false;
     
     
      Modello modello = null;
     
      
      
      /**
       * contenitore del ranking delle alternative
       */
    private  LinkedList<String> listaRanking = new LinkedList<String>();
    
    
    private String numeroRandom = "0";
      
     public RefluiAzienda()
    {
       
        
         dynamicDataTableGroup = new HtmlPanelGroup();
        
          
         ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         dettaglioCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
         this.getListaAlternativeID();
         
         
if(dettaglioCuaa.getAlternativa() != null)         
{
         this.alternativa = dettaglioCuaa.getAlternativa().getId();
         this.alternative2 = dettaglioCuaa.getAlternativa();
         this.setDescrizioneAlternativa(alternative2.getDescrizione());
  
         this.setImmagine("../images/alt"+this.alternativa+".gif");
}else
{
         this.alternative2 = this.listaAlternative.get(0);
         this.alternativa = this.alternative2.getId();
         this.alternative2 = this.listaAlternative.get(0);
         this.setDescrizioneAlternativa(alternative2.getDescrizione());
  
         this.setImmagine("../images/alt"+this.alternativa+".gif");
}
                 
         ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(dettaglioCuaa.getCuaa(),dettaglioCuaa.getScenario());
               
         contenitoreIniziale = contenitore.getContenitore();
        
             
        for(String s:contenitoreIniziale.getTipologie())
        {
            if(s.contains("Liquame")) {
                listaCaratteristicheLiq.add(contenitoreIniziale.getTipologia(s));
            }
            
            if(s.contains("Letame")) {
                listaCaratteristicheLet.add(contenitoreIniziale.getTipologia(s));
            }
        }
        
        
         listaCaratteristiche.add(contenitoreIniziale.totale("Letame"));
        listaCaratteristiche.add(contenitoreIniziale.totale("Liquame"));
        listaCaratteristiche.add(contenitoreIniziale.totale("Totale"));
          
          
          
       
        /**
         * li aggiungo alla lista caratteristiche
         */
        
        /**
         * recupero la provenienza della chiamata web e se proviene 
         * da una sorgente diversa rispetto a se stessa inizializzo i trattmenti
         */
          /*Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
   
          String gg = ((HttpServletRequest) request).getRequestURL().toString();
    
          String[] ss = gg.split("['/']");
          
          String dadove = ss[ss.length-1];
          
          
          System.out.println("-------PROVENIENZA pagina " + dadove);
          
          /*if(dadove.equals("modulo.xhtml"))
          {
              this.nascondiMostraRisultati("dettagliranking",false);
              this.nascondiMostraRisultati("pesiOperazione",false);
              this.nascondiMostraRisultati("labelpesi",false);
          }*/
          /**
           * cerca tra le pagine ddei moduli inseriti nell'array
           * paginetrattamewnti se non trovi la pagina significa che arrivi da un altro percorso
           * per cui devi riinizializzare i trattamenti
           */
         /* boolean trovata = false;
          for(String pagina: this.paginetrattamenti)
          {
              if(dadove.equals(pagina))
              {
                  trovata = true;
                  
                  
                  
                  
                  break;
              }
            
          }
          
          
          
          
          this.refreshPage();*/
          
          
          
         // nascondiDettaglio();
          
        
    }
 
     
     
     /**
      * mostra o nasconde un componente della pagina in funzione 
      * del suo id e della variabile boolena mostra . Se mostra true viene 
      * mostratao se false viene nascosto
      * @param id html id del tag della pagina
      * @param mostra se true mostra i componente se false lo nasconde
      * @return true se i componente è stato trovato false altrimenti
      */
    public boolean nascondiMostraRisultati(String id,boolean mostra)
    {
        UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
        
     

    
    UIComponent comp = findComponent(root, id);
   

        
        
        if(comp != null)
        {
            System.out.println("id componente "+ id+ "  client id   " + comp.getClientId() + " mostra " + mostra);
            comp.setRendered(mostra);
            return true;        
        }
        else
        {
           System.out.println("id componente "+ id+ " non trovato " );  
        }
            return false;
        
       
        
    }
     
    
    
    /**
   * Finds component with the given id
   */
  private UIComponent findComponent(UIComponent c, String id) {
    if (id.equals(c.getId())) {
      return c;
    }
    Iterator<UIComponent> kids = c.getFacetsAndChildren();
    while (kids.hasNext()) {
      UIComponent found = findComponent(kids.next(), id);
      if (found != null) {
        return found;
      }
    }
    return null;
  }
    
  
  public void mostraDettaglio()
  {
      nascondiMostraRisultati("tabellaAziendaLetPan",true);
       nascondiMostraRisultati("tabellaAziendaLiqPan",true);
  }
  
  
  public void nascondiDettaglio()
  {
      nascondiMostraRisultati("tabellaAziendaLetPan",false);
      nascondiMostraRisultati("tabellaAziendaLiqPan",false);
  }
     
 

    

    /**
     * @return the listaCaratteristiche
     */
    public List<Refluo> getListaCaratteristiche() {
        return listaCaratteristiche;
    }

    /**
     * @param listaCaratteristiche the listaCaratteristiche to set
     */
    public void setListaCaratteristiche(List<Refluo> listaCaratteristiche) {
        this.listaCaratteristiche = listaCaratteristiche;
    }

    /**
     * Recupero dal db postgres provagiorgio4 la lista delle alternative.
     * Questo meto viene lanciato in automatico da pagina jsf index.xhtml per fare il rendering del menu a tendina
     * delle alternative
     * @return the listaAlternative
     */
    public List<Integer> getListaAlternativeID() {
        
        listaAlternativeID.clear();
        
        listaAlternative.clear();
        
       /* entityManagerFactory = Persistence.createEntityManagerFactory("provagiorgio13");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
      
       
        
        
       
        
        System.out.println("classloader " + this.getClass().getClassLoader());

        TypedQuery<db.AlternativeS> q=entityManager.createQuery("SELECT a FROM AlternativeS a ORDER BY a.id ASC",db.AlternativeS.class);
        List<db.AlternativeS> results = q.getResultList();
        
        
        System.out.println("indice descrizione " + indiceDescrizione  + " dimensione risultati " + results.size() + " alla riga 475 di refluiazienda");
        
       /* int indice = 0;
        
        ListIterator<db.AlternativeS> altiter = results.listIterator();
        while(altiter.hasNext())
        {
            db.AlternativeS alter = altiter.next();
            if(alter.getId() == indiceDescrizione)
            {
                indice = altiter.
            }
            
        }*/
        
        /**
         * imposto la prima descrizione come default
         */
        //this.setDescrizioneAlternativa(results.get(indiceDescrizione).getDescrizione());
         this.setDescrizioneAlternativa(results.get(0).getDescrizione());
       
       
        
        /**
         * imposto i flussi della prima alternativa come flussi di default svuotando 
         * la llsta dei flussi e riempiendola con la lista dei flussi che 
         * corrispondono alla prima alternativa
         */
        /*this.listaFlussi.clear();
        
        Iterator<db.FlussoS> fl1 = results.get(indiceDescrizione).getFlussoSCollection().iterator();
        while (fl1.hasNext()) {
            db.FlussoS fl = fl1.next();
            listaFlussi.add(fl);

        }*/
        
        
        /**
         * aggiungo tutto le alternative alla lista che contiene le alternative
         */
       int posizione=0;
        for(db.AlternativeS alt:results)
        {
            //System.out.println(alt.getDescrizione() + " " + alt.getId());
            listaAlternativeID.add(posizione,alt.getId());
            listaAlternative.add(posizione,alt);
            
            posizione++;
        }
        
        
        /**
         * rilascio la risorsa che gestisce l'entita
         */
        // entityManager.close();
        
        
          Connessione.getInstance().chiudi();
        
        return listaAlternativeID;
    }

    /**
     * @param listaAlternative the listaAlternative to set
     */
    public void setListaAlternativeID(List<Integer> listaAlternativeID) {
        this.listaAlternativeID = listaAlternativeID;
    }

    /**
     * @return the descrizioneAlternativa
     */
    public String getDescrizioneAlternativa() {
        
       //System.out.println("vai get");
        // this.dynamicGrid.getChildren().clear();
       //  if(this.dynamicGrid != null)
       //     this.dynamicGrid.getChildren().clear();
          System.out.println("vai get  " + descrizioneAlternativa);
        return descrizioneAlternativa;
     // return alternative2.getDescrizione(); 
    }

    /**
     * @param descrizioneAlternativa the descrizioneAlternativa to set
     */
    public void setDescrizioneAlternativa(String descrizioneAlternativa) {
        
         System.out.println("vai set  " + descrizioneAlternativa);
        
        this.descrizioneAlternativa = descrizioneAlternativa;
        
         //this.descrizioneAlternativa = descrizioneAlternativa;
       // if(this.dynamicGrid != null)
       //     this.dynamicGrid.getChildren().clear();
    }
    
   
    
    /**
     * E' l'evento che viene lanciato quando si seleziona un elemento 
     * nel menu a tendina . Sulla base della scelta fatta dall'utente 
     * cambio la descrizione del alternativa scelta e l'immagine 
     * di riferimento. Recupero l'alternativa in base alla selezione fatta dall'utente
     * , di quella alternativa recupero tutti i flussi ed eseguo le singole operazioni dei
     * flussi.
     * @param value 
     */
    
    public void valueChanged(ValueChangeEvent value) {
        
        
       System.out.println("alternativa scelta :" + this.alternativa + "nuovo valore  " +(String) value.getNewValue().toString()); 
        
      //this.listaFlussi.clear();  
     
    /**
       * rappresenta il numero del menu a tendina
       */  
    String newValue = (String) value.getNewValue().toString();
    
    /**
     * imposto il nuovo indice della descrizione
     */
    indiceDescrizione = Integer.parseInt(newValue) ;
    
    
    System.out.println("indice descrizione prima  " + Integer.parseInt(newValue) );
    
    /**
     * aggiorno il numero dell'alternativa
     */
    this.alternativa = Integer.parseInt(newValue);
    
    ListIterator<db.AlternativeS> iterAlternative = listaAlternative.listIterator();
    indiceDescrizione  = 0;
    while(iterAlternative.hasNext())
    {
        db.AlternativeS al = iterAlternative.next();
        
        if(al.getId() == Integer.parseInt(newValue))
        {
            alternative2 = al;
           
            break;
        }   
        
         indiceDescrizione = indiceDescrizione + 1;
        
    }
    
     System.out.println("indice descrizione dopo " + indiceDescrizione + " descrizione  " + alternative2.getDescrizione() + " alternativa numero " + this.alternativa);
    
    //alternative2 = listaAlternative.get(Integer.parseInt(newValue));
    
   /* Iterator<db.FlussoS> fl1= alternative2.getFlussoSCollection().iterator();
    while(fl1.hasNext())
    {
        db.FlussoS fl= fl1.next();
        listaFlussi.add(fl);

    }*/
  
    
    
    
    this.setDescrizioneAlternativa(alternative2.getDescrizione());
  
    this.setImmagine("../images/alt"+newValue+".gif");
   
     dettaglioCuaa.setAlternativa(alternative2);
      
    
    /**
     * quando cambi la descrizione significa che hai scelto un altra alternativa per cui 
     * nascondo i risultati della precedeten elebaorazine con l'alternativa precedente per non creare 
     * un fraintendimento 
     */
    
    System.out.println("-----------descrizione cambiata ----------------------------");
    
    //svuoto la tabella dei risultati
   // this.dynamicGrid.getChildren().clear();
    
    //this.refreshPage();
    //this.nascondiMostraRisultati("pesiOperazione", false);
    //this.nascondiMostraRisultati("labelpesi", false);
   
    
    }

    /**
     * @return the immagine
     */
    public String getImmagine() {
        return immagine;
    }

    /**
     * @param immagine the immagine to set
     */
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    /**
     * @return the listaFlussi
     */
   /* public List<db.FlussoS> getListaFlussi() {
        return listaFlussi;
    }*/

    /**
     * @param listaFlussi the listaFlussi to set
     */
    /*public void setListaFlussi(List<db.FlussoS> listaFlussi) {
        this.listaFlussi = listaFlussi;
    }*/

    
    
    /**
         * inizializza tutti i trattamenti con i contenitori di reflui di input e di output vuoti 
         * tranne che per i blocchi LiquameT e LetameT che essendo quelli iniziali di ogni alternativa
         * il loro contenitore di input deve eseere inizializzato a quello che deriva dalle caratteristiche aziendali
         * che attualmente viene simulato.
         */
//        private void inizializzaTrattamenti(ContenitoreReflui contenitoreIniziale)
//        {
//            /**
//             * svuoto i contenitori di reflui di input ed output 
//             */
//            LiquameT.getInstance().azzeraContenitori();
//            LiquameT.getInstance().azzeraGestionali();
//
//            ContenitoreReflui contenitoreReflui = new ContenitoreReflui();
//            contenitoreReflui.setContenitore(contenitoreIniziale.getContenitore());
//            /**
//             * imposto il contenitore di reflui di input quello iniziale creato dal RefluiAzienda
//             */
//            LiquameT.getInstance().setContenitoreRefluiIn(contenitoreIniziale);
//         
//            /**
//             * svuoto i contenitori di reflui di input ed output 
//             */
//            LetameT.getInstance().azzeraContenitori();
//            LetameT.getInstance().azzeraGestionali();
//                      contenitoreReflui = new ContenitoreReflui();
//                      contenitoreReflui.setContenitore(contenitoreIniziale.getContenitore());
//            
//            /**
//             * imposto il contenitore di reflui di input quello iniziale creato dal RefluiAzienda
//             */          
//            LetameT.getInstance().setContenitoreRefluiIn(contenitoreIniziale);
//           
//
//
//            SeparazioneAVite.getInstance().azzeraContenitori();
//            SeparazioneAVite.getInstance().azzeraGestionali();
//            
//            
//            Ndn.getInstance().azzeraContenitori();
//           Ndn.getInstance().azzeraGestionali();
//           
//           Sbr.getInstance().azzeraContenitori();
//          Sbr.getInstance().azzeraGestionali();
//          Vasca.getInstance().azzeraContenitori();
//          Vasca.getInstance().azzeraGestionali();
//          Platea.getInstance().azzeraContenitori();
//          Platea.getInstance().azzeraGestionali();
//           
//        
//            
//           NMinerale.getInstance().azzeraContenitori();
//           NMinerale.getInstance().azzeraGestionali();
//          Da.getInstance().azzeraContenitori();
//           Da.getInstance().azzeraGestionali();
//          Stabilizzazione.getInstance().azzeraContenitori();
//         Stabilizzazione.getInstance().azzeraGestionali();
//          ReteIdrica.getInstance().azzeraContenitori();
//         ReteIdrica.getInstance().azzeraGestionali();
//          
//          
//       
//         
//           
//        }
//    
//        
        
             
            
            
            // private  ArrayList versovascaArray = null;
            // private  ArrayList versoplateaArray = null;
    
   /* private Hashtable<String,Double> recuperaVariabili()
    {
         Hashtable<String,Double> dati = new Hashtable<String,Double>();
         
           /**
         * recupero dal db i trattamenti
         */
     /*   Connessione connetti = Connessione.getInstance();
        connetti.apri("provagiorgio13");
        List<db.TrattamentoS> trattamenti = connetti.recuperaTrattamenti(alternative2);
        
        /**
         * mostro tutte le variabili dei trattamenti della specifica alternativa scelta
         * dall'utente
         */
        //System.out.println("interroga modello  " + alternative2.getNome() + " id " +alternative2.getId());
        
     /* *  for(db.TrattamentoS tra:trattamenti)
        {
           // System.out.println("---" + tra.getNome() + "---");
            for(db.VariabiliModello vari:tra.getVariabiliModelloCollection())
            {
                //System.out.println("----"+ vari.getDescrizione()+"----"+"++++"+vari.getNome()+"+++++");
               // dati.put(tra.getNome()+"-"+vari.getDescrizione()+"");
            }
        }
        
        System.out.println("Variabili comuni a tutte le alternative ");
        
        List<db.VariabiliModello> variabili = connetti.recuperaVariabiliModello(null);
        for(db.VariabiliModello vari:variabili)
            {
                System.out.println("----"+ vari.getDescrizione()+"----"+"++++"+vari.getNome()+"+++++");
            }
        
        
        connetti.chiudi();
         
        
        return null;
    }*/
    
    
    
   private  String getRandomNumber(int digCount) {
    Random rr = new Random();   
    
    StringBuilder sb = new StringBuilder(digCount);
    for(int i=0; i < digCount; i++)
        sb.append((char)('0' + rr.nextInt(10)));
    return sb.toString();
}     
        
    
   
   
   
   private String interrogaModello1()
    {
     
         
        
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
        inputoutputxml.inserisciUtente(elemento,"azienda1");
      
        /**
         * inserisco la sezione delle selezioni che serve per dire al solutore se deve calcolare al singola alternativa
         * caclolarle tutte facendo un ranking e trovare quella migliore. La scelta presa dall'utente è definita nella variabile 
         * operazioneScelta
         */
        if(this.operazioneScelta.equals("rank"))
        {
            inputoutputxml.selezioni(elemento,1,0,0);
           
        }
        if(this.operazioneScelta.equals("migliore"))
            inputoutputxml.selezioni(elemento,0,1,0);
        if(this.operazioneScelta.equals("singola"))
        {
            //System.out.println("alternativa scelta " + this.alternative2.getId());
            inputoutputxml.selezioni(elemento,0,0,this.alternative2.getId());
            
            //this.nascondiMostraRisultati("pesiOperazione", false);
        }
         //inputoutputxml.selezioni(elemento,1,0,0);
        
        /**
         * inserisco il blocco delle minimizzazioni che informa il solutore su quale strategia usare per la minimizzazione
         * firma del metodo = minimizzazioni(nodo padre xml - costo - emissioni -energia)
         */
         //inputoutputxml.minimizzazioni(elemento,0.3,0.5,0.2);
        
      //  System.out.println("costo  " +getCosto() +" emissioni " + getEmisisoni() +  " energia " +  getEnergia());
        
        
         inputoutputxml.minimizzazioni1(elemento, 0.3d, 0.3d,0.2d,0.2d);
         /**
          * inserisco i parametri di progetto
          */
          inputoutputxml.parametriDiProgetto(elemento,dettaglioCuaa.getIdscenario().intValue());
        
          /**
           * impsto il consortile o meno
           */
          inputoutputxml.consortile(elemento, 0);
          
          DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
          
          boolean utenteospite = false;
          if(!dettaglioCuaa.getUtente().equals("azienda1"))
          {
              utenteospite = true;
          }
          
         Element azienda =  inputoutputxml.aggiungiAzienda(elemento,dettaglioCuaa.getCuaa(),String.valueOf(dettaglioCuaa.getScenario()),utenteospite);
          
            /**
         * inserisco le caratteristiche chimiche a partire dal nodo root
         */
        inputoutputxml.aggiungiAllevamenti(azienda, listaCaratteristicheLiq, listaCaratteristicheLet);
        
        inputoutputxml.aggiungiStoccaggi(azienda,dettaglioCuaa.getIdscenario().intValue());
        
        inputoutputxml.aggiungiAcquaStoccaggio(azienda,dettaglioCuaa.getIdscenario().intValue());
         /**
          * numero random che mi serve per distinguere differenti file di input al solutore
          * da parte di differenti utenti connessi alla pagina web
          */ 
         //int numerorandom = (int)Math.round((Math.random()*1000));
         String numerorandom1 = this.getRandomNumber(5) ;
         
        // this.setNumeroRandom(numerorandom1);
         
         
        // System.out.println("numero random in raflui azienda " + this.getNumeroRandom());
         
         
          dettaglioCuaa.setNumeroRandom(numerorandom1);
         this.numeroRandom = String.valueOf(numerorandom1);
        try {       
            inputoutputxml.scriviFile("input_"+numerorandom1+".xml");
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
         modello = null;
        
       
         
         System.out.println("numero random del file xml " + numerorandom1);
         
         if(System.getProperty("os.name").contains("Windows"))
         {
             //modello = new Modello("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespigUM_8g\\","solutore.exe",String.valueOf(numerorandom),false);
             modello = new Modello("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespig\\","seespig1.exe",String.valueOf(numerorandom1),false);
         }else
         {
             //modello = new Modello("/mnt/disco2/runmodello/","./solutore.out",String.valueOf(numerorandom),true); 
             modello = new Modello("/home/giorgiogalassi/Documenti/solutorec/","./solutore1_renuwal.out",String.valueOf(numerorandom1),true); 
         }
         
        //modello.risolvi();        
        modello.run();
        
        
        //this.refreshPage();
        
        return numerorandom1;
       
    }     
        
        
  /*  public void interrogaModello()
    {
        
        
       
        
     
                 
//       Modello   modello = null;
//       DatiModello datiModello = new DatiModello();
//       /**
//          * per comodità nelle prove rilevo il sistema operativo perchè il percorso del modello ed il file dati sul computer 
//          * su cui sviluppo è diverso dal server finale percui il percorso del file è diverso.Il server è linux mentre quello su cui sviluppo
//          * è windows
//          */  
//       if(System.getProperty("os.name").contains("Windows"))
//       {
//            datiModello.eliminaPrecedente("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespigUM_8e\\data_prototipo.dat");
//            datiModello.apridati("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespigUM_8e\\data_prototipo.dat");
//
//       }else{
//            datiModello.eliminaPrecedente("/mnt/disco2/modelli/data_prototipo.dat");
//            datiModello.apridati("/mnt/disco2/modelli/data_prototipo.dat");
//       }
//       
//       datiModello.aggiungiCostanti();
//       datiModello.aggiungiAllevamento(listaCaratteristicheLiq, listaCaratteristicheLet);
//       
//       System.out.println(this.getClass().getCanonicalName() + " alternative2.getId()  " + alternative2.getId());
//        System.out.println(this.getClass().getCanonicalName() + "contenitoreIniziale.totale(\"Totale\").getMetricubi()  " + contenitoreIniziale.totale("Totale").getMetricubi());
//       datiModello.aggiungiAlternativa(alternative2.getId().toString(),contenitoreIniziale.totale("Totale").getMetricubi());
//       datiModello.chiudidati();
//            
//       if(System.getProperty("os.name").contains("Windows"))
//       {
//         modello = new Modello("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespigUM_8e\\SingolaAziendaOK.mod","C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespigUM_8e\\data_prototipo.dat");
//
//       }else
//       {
//         modello = new Modello("/mnt/disco2/modelli/SingolaAziendaOK.mod","/mnt/disco2/modelli/data_prototipo.dat"); 
//       }
//      
//      
//       /**
//        * risolvo il modello opl ed a questo punto posso interrogare ilmodello opl
//        * per ottenere le variabili ed i valori delle variabili
//        */
//        modello.risolvi();
//
//       // System.out.println("----------------Dati Vasca--------------------------");
//       
//        
//        System.out.println("alternativa " + alternative2.getDescrizione());
//        
//       /**
//        * gli passo l'alternativa 
//        */
//        RisultatiModello1.setAlternativa(alternative2);
//        RisultatiModello1.setModello(modello);
//        
//         risultatiModello = RisultatiModello1.getInstance();
//        
//         vascaArraykeys = risultatiModello.getdatiFinaliIndici(0);
//         vascaArray = risultatiModello.getdatiFinaliValori(0);
//         
//         plateaArraykeys = risultatiModello.getdatiFinaliIndici(1);
//         plateaArray = risultatiModello.getdatiFinaliValori(1);
//         
//         emissioniArraykeys = risultatiModello.getdatiFinaliIndici(2);
//         emissioniArray = risultatiModello.getdatiFinaliValori(2);
//         
//         
//         this.gestionaliArraykeys = risultatiModello.getdatiFinaliIndici(3);
//         this.gestionaliArray = risultatiModello.getdatiFinaliValori(3);
//      
//       
//        modello.chiudi();
  //  }
    
    String labelTabella="";
    
    /**
     * uso il panelgrid
     * @param leggi 
     */
    /* private void recuperaSingola1(InputOutputXml leggi,boolean cache,String padre,String alternativa) {

         if(cache)
        dynamicGrid.getChildren().clear();

        Hashtable<String, Double> temp = null;

        Application app = FacesContext.getCurrentInstance().getApplication();
        
        
        HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Alternativa " + alternativa);
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
         this.aggiungiNuovaLinea(0);
        

        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Caratterisitiche chimiche");
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
       
        

        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/bovino/liquame");
        
       // System.out.println(" query xpath    ------ : " + padre+"/totali/caratteristichechimiche/bovino/liquame dimesione chiavi " + temp.keySet().size());
        //leggi.stampa(temp);

        this.creaHeaderGrid(temp, "Tipologia");
        this.aggiungiValoriGrid(temp, "liquame bovino");
        //leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/bovino/letame");
        this.aggiungiValoriGrid(temp, "letame bovino");
        // leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/suino/liquame");
        this.aggiungiValoriGrid(temp, "liquame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/suino/letame");
        this.aggiungiValoriGrid(temp, "letame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/avicolo/liquame");
        this.aggiungiValoriGrid(temp, "liquame avicolo");
        // leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/avicolo/letame");
        this.aggiungiValoriGrid(temp, "letame avicolo");
        //leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/altro/liquame");
        this.aggiungiValoriGrid(temp, "liquame altro");
        // leggi.stampa(temp);
        temp = leggi.cerca(padre+"/totali/caratteristichechimiche/altro/letame");
        this.aggiungiValoriGrid(temp, "letame altro");


         this.aggiungiNuovaLinea(0);
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Emissioni");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1);
          temp =leggi.cerca(padre+"/totali/emissioni");
         this.creaHeaderGrid(temp, "");
         this.aggiungiNuovaLinea(6);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(6);
          
           this.aggiungiNuovaLinea(0);
         
         
           
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Energia");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca(padre+"/totali/energia");
         this.creaHeaderGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         
          this.aggiungiNuovaLinea(0);
          
          
          
          text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Costi");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca(padre+"/totali/costi");
         this.creaHeaderGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         
          this.aggiungiNuovaLinea(0);
                      // leggi.stampa(temp);
                     //temp =leggi.cerca("//totali/costi");*/
                      // leggi.stampa(temp);*/
         
    //}
    
     
     
      /*private void recuperaSingola2(InputOutputXml leggi,boolean cache,String padre,String alternativa) {

         if(cache)
                dynamicGrid.getChildren().clear();

       NodeList temp = null;

        Application app = FacesContext.getCurrentInstance().getApplication();
        
        
        HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Alternativa " + alternativa);
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
         this.aggiungiNuovaLinea(0);
        

        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Caratterisitiche chimiche");
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
       
        

        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/bovino/liquame",true);
        
       // System.out.println(" query xpath    ------ : " + padre+"/totali/caratteristichechimiche/bovino/liquame dimesione chiavi " + temp.keySet().size());
        //leggi.stampa(temp);

        this.creaHeaderGrid(temp, "Tipologia");
        this.aggiungiValoriGrid(temp, "liquame bovino");
        //leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/bovino/letame",true);
        this.aggiungiValoriGrid(temp, "letame bovino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/suino/liquame",true);
        this.aggiungiValoriGrid(temp, "liquame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/suino/letame",true);
        this.aggiungiValoriGrid(temp, "letame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/avicolo/liquame",true);
        this.aggiungiValoriGrid(temp, "liquame avicolo");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/avicolo/letame",true);
        this.aggiungiValoriGrid(temp, "letame avicolo");
        //leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/altro/liquame",true);
        this.aggiungiValoriGrid(temp, "liquame altro");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/altro/letame",true);
        this.aggiungiValoriGrid(temp, "letame altro");


         this.aggiungiNuovaLinea(0);
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Emissioni");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1);
          temp =leggi.cerca1(padre+"/totali/emissioni",true);
         this.creaHeaderGrid(temp, "");
         this.aggiungiNuovaLinea(6);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(6);
          
           this.aggiungiNuovaLinea(0);
         
         
           
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Energia");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca1(padre+"/totali/energia",true);
         this.creaHeaderGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         
          this.aggiungiNuovaLinea(0);
          
          
          
          text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Costi");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca1(padre+"/totali/costi",true);
         this.creaHeaderGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         
          this.aggiungiNuovaLinea(0);
                      // leggi.stampa(temp);
                     //temp =leggi.cerca("//totali/costi");*/
                      // leggi.stampa(temp);*/
         
   // }
     
     
    /**
     * fatta con la htmltable e però riesco a farla funzioanre solo su una tabella cioè deve usare una lista 
     * che deve essere predefinita problema che non ho se uso il panelgrid
     * @param leggi 
     */
   /* private void recuperaSingola(InputOutputXml leggi)
    {
         
             Hashtable<String,Double> temp = null;
             dynamicDataTableGroup.getChildren().clear();
            
             List<List<String>>  dynamicList;
             String[]  dynamicHeader ;
              temp =leggi.cerca("//totali/caratteristichechimiche/bovino/liquame");
              leggi.stampa(temp);
              dynamicHeader = creaHeader(temp,"Tipologia");
              dynamicList = newListaDinamica(temp,"liquame bovino");
              //leggi.stampa(temp);
               temp = leggi.cerca("//totali/caratteristichechimiche/bovino/letame");
                 dynamicList = appendListaDinamica(temp,"letame bovino",dynamicList);
                // leggi.stampa(temp);
                temp =leggi.cerca("//totali/caratteristichechimiche/suino/liquame");
                 dynamicList = appendListaDinamica(temp,"liquame suino",dynamicList);
                 // leggi.stampa(temp);
                 temp =leggi.cerca("//totali/caratteristichechimiche/suino/letame");
                   dynamicList = appendListaDinamica(temp,"letame suino",dynamicList);
                  // leggi.stampa(temp);
                 temp = leggi.cerca("//totali/caratteristichechimiche/avicolo/liquame"); 
                   dynamicList = appendListaDinamica(temp,"liquame avicolo",dynamicList);
                  // leggi.stampa(temp);
                  temp =leggi.cerca("//totali/caratteristichechimiche/avicolo/letame");
                    dynamicList = appendListaDinamica(temp,"letame avicolo",dynamicList);
                    //leggi.stampa(temp);
                   temp =leggi.cerca("//totali/caratteristichechimiche/altro/liquame");
                     dynamicList = appendListaDinamica(temp,"liqaume altro",dynamicList);
                    // leggi.stampa(temp);
                    temp =leggi.cerca("//totali/caratteristichechimiche/altro/letame");
                      dynamicList = appendListaDinamica(temp,"letame altro",dynamicList);
                     // leggi.stampa(temp);
                    labelTabella ="Caratteristiche chimiche";
                      labelTabella(labelTabella);
                     //  getDynamicDataTableGroup();
                      populateDynamicDataTable(dynamicList,dynamicHeader);
                      
                   
                       Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      text.setValue("some text");
      dynamicGrid.getChildren().add(text);
                   /*    temp =leggi.cerca("//totali/emissioni");
                        dynamicHeader = creaHeader(temp,"Emissioni");
                      dynamicList = newListaDinamica(temp,"emissioni");
                        labelTabella ="Emissioni";
                      labelTabella(labelTabella);
                        populateDynamicDataTable(dynamicList,dynamicHeader);*/
                     //  dynamicList = new ArrayList<List<String>>();
                 /*    temp =leggi.cerca("//totali/emissioni");
                     System.out.println("stampa emissioni");
                      leggi.stampa(temp);
                     dynamicHeaders = null;
                       dynamicList = new ArrayList<List<String>>();
                       creaHeader(temp,"");
                       loadListaDinamica(temp,"");
                        labelTabella ="Emissioni";
                      labelTabella(labelTabella);
                       populateDynamicDataTable();
                   
                     temp =leggi.cerca("//totali/energia");
                      // leggi.stampa(temp);
                     temp =leggi.cerca("//totali/costi");*/
                      // leggi.stampa(temp);*/
                  
        
                       
   /* }*/
    
    private void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }
    
    
   /*  private void refreshPortionPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context..getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }*/
    
    
    
    /**
     * dato che il ranking prende in considerazione tutte le alternative e produce in 
     * outpput (xml) la lista ordinata per posizione delle alternative vado prima a
     * leggere il numero di alternative che si trovano in output ovvero nel file xml e poi 
     * ciclo su ogno alternativa. In xpath l'incremanetale dop il nome di un nodo ne identifica la posizione
     * del nodo all'interno del xml ovvero //alternativa[4] significa prendi la 4 alternativa nella lista
     * delle laternative
     * @param leggi 
     */
    /*public void recuperaRank(InputOutputXml leggi)
    {
     
          dynamicGrid.getChildren().clear();
       
            int numeronodi = leggi.contaNodi("alternativa");
        
           
            for(int i = 1; i <= numeronodi;i++)
           {
               String hh = leggi.cercaSingolo("//alternativa["+i+"]/scelta");
               recuperaSingola2(leggi,false,"//alternativa["+i+"]",hh);
           }
           
           
        
    }*/
    
   /* public void recuperMigliore(InputOutputXml leggi)
    {
        String hh = leggi.cercaSingolo("//alternativa/scelta");
        recuperaSingola2(leggi,true,"//alternativa",hh);
    }   */     
            
    
    
   /* public void ottieniValori(InputOutputXml leggi)
    {
         
        
        switch(this.operazioneScelta)
        {
            case "rank":
                recuperaRank(leggi);
                    break;
            case "migliore":
                recuperMigliore(leggi);
                    break;
            case "singola":
                    
                   recuperaSingola2(leggi,true,"//alternativa","");
                 
                
                    break;
        }
       
    }*/
   
   
    /*private void aggiornaPesi()
    {
         HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
         FacesContext fc = FacesContext.getCurrentInstance();
       /**
        * recupero i campi costo,emissioni ed energia e li imposto come valori delle variabili
        * locali del bean
        */
       /*String txtCosto = request.getParameter("formRoot:costoid");
       
       String txtEmissionia = request.getParameter("formRoot:emissioneida");
       String txtEmissionig = request.getParameter("formRoot:emissioneidg");
       String txtEnergia = request.getParameter("formRoot:energiaid");
       if(txtCosto != null || txtEmissionia != null || txtEnergia != null)
       {
       txtCosto = txtCosto.replace(',', '.');
       txtEmissionia = txtEmissionia.replace(',', '.');
       txtEnergia = txtEnergia.replace(',', '.');
       
       this.setCosto(Double.parseDouble(txtCosto));
       this.setEmissionia(Double.parseDouble(txtEmissionia));
        this.setEmissionig(Double.parseDouble(txtEmissionig));
       this.setEnergia(Double.parseDouble(txtEnergia));
       
       double totale = this.costo + this.emissionia + this.energia + this.emissionig;
       
       if(totale > 1 || totale < 0)
       {
           this.pesicorretti = false;
       }
       
       
       if(this.costo > 1 || this.costo < 0)
       {
           this.pesicorretti = false;
       }
        if(this.emissionia > 1 || this.emissionia < 0)
       {
           this.pesicorretti = false;
       }
        
       if(this.emissionig > 1 || this.emissionig < 0)
       {
           this.pesicorretti = false;
       } 
        
         if(this.energia > 1 || this.energia < 0)
       {
           this.pesicorretti = false;
       }
       
      // System.out.println("peso costo " + this.getCosto() + " peso emissioni " + this.getEmissionia() + " peso energia " + this.getEnergia() + " operazione scelta " + this.operazioneScelta);
       }
     
        
    }*/
    
    
    /**
     * Metodo che viene lanciato come evento click sul bttone calcola della pagina index.
     */
    public void calcola()
    {
       
        
      this.pesicorretti = true;  
        
      //this.aggiornaPesi();
      
      /**
       * se hai verficato che i pesi non sono corretti non procedere e cancella il contenuto 
       * precedente della tabella
       */
      if(!this.pesicorretti)
      {
          if(dynamicGrid != null)
           dynamicGrid.getChildren().clear();
           //dynamicGrid1.getChildren().clear();
          
          System.out.println("pesi non corretti ");
           return;
      }
      
      
      //System.out.println("pesi corretti coasto " + this.costo + " eneriga " + this.energia + " emissioni " + this.emissionia);
       
     //   System.out.println("costo " + txtCosto  + " emissioni " + txtEmissioni + " energia " + txtEnergia);
     //   System.out.println("---------      prima di interroga modello ");
     //   System.out.println("peso costo " + this.getCosto() + " peso emissioni " + this.getEmissioni() + " peso energia " + this.getEnergia() + " operazione scelta " + this.operazioneScelta);
        
      String numero = interrogaModello1();
      
      
      InputOutputXml leggiXml = new InputOutputXml();
      
     // int i = 0;
      /**
       * ciclo fino ache il file xml è pronto
       */
      /*while(!leggiXml.impostaFile("output_"+numero+".xml"))
      {
          System.out.println("documento non pronto - operazione scelta : " + this.operazioneScelta);
          i++;
          
          
              return;
      }*/
      
      /**
       * da cancellare in attesa delle modifiche sul solutore
       * imposto il valore numerico su una vecchia simulazione
       */
     // numero = "73233";
      //DettaglioCuaa.setNumeroRandom(numero);
      LetturaRisultati test1 = new LetturaRisultati(leggiXml,numero,dynamicGrid,this.operazioneScelta,modello);
      
      test1.run();
      
      
      
     System.out.println(this.getClass().getCanonicalName() + " alternativa attuale   " + this.alternativa);
      
      
      //this.ottieniValori(leggiXml);
      
     // Hashtable<String,Double> temp = null;
      
     // temp = leggiXml.cerca("//modulo[@name='stoccaggi']/caratteristichechimiche/bovino/liquame");
      
     // leggiXml.stampa(temp);
       
         //this.refreshPage();
      
      
      // nascondiMostraRisultati("leftside11",false);
      // nascondiMostraRisultati("leftside11",true);
      
      
       /* nascondiMostraRisultati("tabellaAziendaLetPan",false);
        nascondiMostraRisultati("tabellaAziendaLiqPan",false);
      
        nascondiMostraRisultati("tabellaAziendaLetPan",true);
        nascondiMostraRisultati("tabellaAziendaLiqPan",true);*/
      
     // nascondiDettaglio();
    //  mostraDettaglio();
    }

    /**
     * @return the listaCaratteristicheLiq
     */
    public List<Refluo> getListaCaratteristicheLiq() {
        return listaCaratteristicheLiq;
    }

    /**
     * @param listaCaratteristicheLiq the listaCaratteristicheLiq to set
     */
    public void setListaCaratteristicheLiq(List<Refluo> listaCaratteristicheLiq) {
        this.listaCaratteristicheLiq = listaCaratteristicheLiq;
    }

    /**
     * @return the listaCaratteristicheLet
     */
    public List<Refluo> getListaCaratteristicheLet() {
        return listaCaratteristicheLet;
    }

    /**
     * @param listaCaratteristicheLet the listaCaratteristicheLet to set
     */
    public void setListaCaratteristicheLet(List<Refluo> listaCaratteristicheLet) {
        this.listaCaratteristicheLet = listaCaratteristicheLet;
    }

    /**
     * @return the alternativa
     */
    public int getAlternativa() {
        return alternativa;
    }

    /**
     * @param alternativa the alternativa to set
     */
    public void setAlternativa(int alternativa) {
        this.alternativa = alternativa;
    }

    

    /**
     * @return the paginetrattamenti
     */
    public String[] getPaginetrattamenti() {
        return paginetrattamenti;
    }

    /**
     * @param paginetrattamenti the paginetrattamenti to set
     */
    public void setPaginetrattamenti(String[] paginetrattamenti) {
        this.paginetrattamenti = paginetrattamenti;
    }

   

   
    /**
     * restituisce true se il parametro pagina è contenuto
     * nell'arra di stringhe paginetrattamenti ovvero viene usato 
     * durante la navigazione delle pagine dopo il calcola alternative
     * per capire se una determinata pagina appartiene alle pagine che possono
     * essere visitate dopo la scelta dell'alternativa
     */
    public boolean paginedeiTrattamenti(String pagina)
    {
        if(Arrays.asList(paginetrattamenti).contains(pagina))
        {
            return true;
        }else
            return false;
    }

    /**
     * @return the operazioneScelta
     */
    public String getOperazioneScelta() {
        return operazioneScelta;
    }

    /**
     * @param operazioneScelta the operazioneScelta to set
     */
    public void setOperazioneScelta(String operazioneScelta) {
        this.operazioneScelta = operazioneScelta;
    }
    
    /**
     * metdodo chiamta dal menu a tandina della scelta delle operazioni
     * @param e 
     */
    /*public void valueChangeMethod(ValueChangeEvent e){
		//...
        
        System.out.println("------------------------  operazione scelta " + e.getNewValue().toString());
        
        this.setOperazioneScelta(e.getNewValue().toString());
        
        this.dynamicGrid.getChildren().clear();
        
        
	}*/

    /**
     * @return the costo
     */
    /*public double getCosto() {
        return costo;
    }*/

    /**
     * @param costo the costo to set
     */
    /*public void setCosto(double costo) {
        this.costo = costo;
    }*/

    

    /**
     * @return the energia
     */
    /*public double getEnergia() {
        return energia;
    }*/

    /**
     * @param energia the energia to set
     */
   /* public void setEnergia(double energia) {
        this.energia = energia;
    }*/

    
    
   
 
    // Actions -----------------------------------------------------------------------------------
 
   /* private void loadDynamicList() {
 
        // Set headers (optional).
        dynamicHeaders = new String[] {"Region Id", "Region Name "," esempio colonna "};
 
        // Set rows
        dynamicList = new ArrayList<List<String>>();
        dynamicList.add(Arrays.asList(new String[] { "1", "Europe" ," giorgio"}));
        dynamicList.add(Arrays.asList(new String[] { "2", "Americas"," davide" }));
        dynamicList.add(Arrays.asList(new String[] { "3", "Asia","alberto" }));
        dynamicList.add(Arrays.asList(new String[] { "4", "Middle East and Africa","simone"}));
 
    }*/
    
    /**
     * 
     * @param elementiinseriti numero di elementi gia presenti sulla riga
     */
    /*private void aggiungiNuovaLinea(int elementiinseriti)
    {
        
        int colonne = dynamicGrid.getColumns();
        
         Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
       text.setValue("  ");
      for(int i = elementiinseriti; i < colonne; i++)
      {
          dynamicGrid.getChildren().add(text);
          text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      }
       
    }*/
    
     /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @return 
     */
    /*private void aggiungiValoriGrid(Hashtable<String,Double> contenuto,String label)
    {
     
      Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      
      text.setValue(label);
      dynamicGrid.getChildren().add(text);
        Enumeration chiavi = contenuto.keys();
        
        while(chiavi.hasMoreElements())
        {
            String c =(String) chiavi.nextElement();
            int temp =(int) contenuto.get(c).intValue();
            text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
            text.setValue(String.valueOf(temp));
      dynamicGrid.getChildren().add(text);
      
        }
        
        
        
    }*/
    
     /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @return 
     */
   /* private void aggiungiValoriGrid(NodeList contenuto,String label)
    {
     
      Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      
      text.setValue(label);
      dynamicGrid.getChildren().add(text);
       // Enumeration chiavi = contenuto.keys();
        
       for(int i = 0; i < contenuto.getLength();i++)
        {
              Node temp = contenuto.item(i);
             if (temp.getNodeType() == Node.ELEMENT_NODE) {
                 String tt = temp.getFirstChild().getNodeValue();
                 
                 if(tt.contains("-1") || tt.contains("#") || tt.contains("nan"))
                 {
                     tt = "0";
                 }    
                 
                    int c = (int)  Double.parseDouble(tt);
                    text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
                    text.setValue(String.valueOf(c));
                    dynamicGrid.getChildren().add(text);
                 
             }
        }
        
        
        
    }*/
    
    
     /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @return 
     */
    /*private void creaHeaderGrid(NodeList contenuto,String label)
    {
     
      Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      text.setValue(label);
      dynamicGrid.getChildren().add(text);
      
     
      
        
        
      for(int i = 0; i < contenuto.getLength();i++)
        {
              Node temp = contenuto.item(i);
             if (temp.getNodeType() == Node.ELEMENT_NODE) {
                    String c = temp.getNodeName();
                    text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
                    text.setValue(c);
                    dynamicGrid.getChildren().add(text);
             }
        }
        
        
        
    }*/
    /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @return 
     */
    /*private void creaHeaderGrid(Hashtable<String,Double> contenuto,String label)
    {
     
      Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      text.setValue(label);
      dynamicGrid.getChildren().add(text);
      
     
      
        Enumeration chiavi = contenuto.keys();
        
        if(chiavi == null)
             System.out.println(" chiavi null  ");
            
        
        
        while(chiavi.hasMoreElements())
        {
            String c =(String) chiavi.nextElement();
           text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      text.setValue(c);
      dynamicGrid.getChildren().add(text);
        }
        
        
        
    }*/
    
    
    /**
     * usato per l'header del datatable
     * @param contenuto
     * @param label
     * @return 
     */
   /* private String[] creaHeader(Hashtable<String,Double> contenuto,String label)
    {
      String[] dynamicHeaders = new String[contenuto.size() + 1];
        dynamicHeaders[0] = label;
        Enumeration chiavi = contenuto.keys();
        int dove = 1;
        while(chiavi.hasMoreElements())
        {
            String c =(String) chiavi.nextElement();
            dynamicHeaders[dove] = c;
            dove++;
        }
        
        
        return dynamicHeaders;
    }*/
    
 /*   private List<List<String>> appendListaDinamica(Hashtable<String,Double> contenuto,String tipologia, List<List<String>> dynamicList)
    {
        
       // List<List<String>> dynamicList = new ArrayList<List<String>>();;
        // dynamicHeaders = label;
        //dynamicHeaders = contenuto.keySet().toArray(new String[0]);
       Enumeration chiavi = contenuto.keys();
        List<String> listaValori = new LinkedList<String>();
        listaValori.add(tipologia);
        chiavi = contenuto.keys();
        
        while(chiavi.hasMoreElements())
        {
            String c = (String)chiavi.nextElement();
            //System.out.println("sono qui " + c);
            listaValori.add(String.valueOf(Math.round(contenuto.get(c))));
        }
        
        
         dynamicList.add(listaValori);
         
         return dynamicList;
    }*/
    
    
    
    
    
   /* private List<List<String>> newListaDinamica(Hashtable<String,Double> contenuto,String tipologia)
    {
        
         dynamicList = new ArrayList<List<String>>();;
        // dynamicHeaders = label;
        //dynamicHeaders = contenuto.keySet().toArray(new String[0]);
       Enumeration chiavi = contenuto.keys();
        List<String> listaValori = new LinkedList<String>();
        listaValori.add(tipologia);
        chiavi = contenuto.keys();
        
        while(chiavi.hasMoreElements())
        {
            String c = (String)chiavi.nextElement();
            //System.out.println("sono qui " + c);
            listaValori.add(String.valueOf(Math.round(contenuto.get(c))));
        }
        
        
         dynamicList.add(listaValori);
         
         
         return dynamicList;
    }*/
    
  /*  private void labelTabella(String label)
    {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = fCtx.getELContext();
        ExpressionFactory ef = fCtx.getApplication().getExpressionFactory();
         HtmlOutputText output = new HtmlOutputText();
         ValueExpression ve = ef.createValueExpression(elCtx,"#{refluiAzienda.dynamicList}",List.class);
          ve = ef.createValueExpression(elCtx, label, String.class);
          output.setValueExpression("value", ve);
          
          dynamicDataTableGroup.getChildren().add(0, output);
        
    }*/
    
  /*  private void populateDynamicDataTable( List<List<String>> dynamicList,String[] dynamicHeaders) {
 
        // Context and Expression Factory
        FacesContext fCtx = FacesContext.getCurrentInstance();
        ELContext elCtx = fCtx.getELContext();
        ExpressionFactory ef = fCtx.getApplication().getExpressionFactory();
 
        // Create <h:dataTable value="#{datatableManagedBean.dynamicList}" var="dynamicRow">.
        HtmlDataTable dynamicDataTable = new HtmlDataTable();
        
        ValueExpression ve = ef.createValueExpression(elCtx,"#{refluiAzienda.dynamicList}",List.class);
        dynamicDataTable.setValueExpression("value", ve);
        dynamicDataTable.setVar("dynamicRow");
        
    
        
        
        if( dynamicList == null)
        {
            System.out.println("dimensione dle dynamic list null");
            return;
        }else
        {
            System.out.println("dimensione dle dynamic list " + dynamicList.size());
        }
        
       
        
        
        // Iterate over columns
        for (int i = 0; i < dynamicList.get(0).size(); i++) {
 
            // Create <h:column>.
            HtmlColumn column = new HtmlColumn();
            dynamicDataTable.getChildren().add(column);
 
            // Create <h:outputText value="dynamicHeaders[i]"> for <f:facet name="header"> of column.
            HtmlOutputText header = new HtmlOutputText();
            header.setValue(dynamicHeaders[i]);
            column.setHeader(header);
         
 
            // Create <h:outputText value="#{dynamicRow[" + i + "]}"> for the body of column.
            HtmlOutputText output = new HtmlOutputText();
            ve = ef.createValueExpression(elCtx, "#{dynamicRow[" + i + "]}", String.class);
            output.setValueExpression("value", ve);
            column.getChildren().add(output);
 
        }
        
       
        
        // Add the datatable to <h:panelGroup binding="#{datatableManagedBean.dynamicDataTableGroup}">.
      //  dynamicDataTableGroup = new HtmlPanelGroup();
        
        
        
        
        
        dynamicDataTableGroup.getChildren().add(dynamicDataTable);
        
     
        
        
        
      
    }*/
 
    // Getters -----------------------------------------------------------------------------------
 
    public HtmlPanelGroup getDynamicDataTableGroup() {
        // This will be called once in the first RESTORE VIEW phase.
       // if (dynamicDataTableGroup == null) {
           // loadDynamicList(); // Preload dynamic list.
          //  populateDynamicDataTable(); // Populate editable datatable.
        //}
            
           
            
            
        return dynamicDataTableGroup;
    }
 
    /*public List<List<String>> getDynamicList() {
        return dynamicList;
    }*/
 
    // Setters -----------------------------------------------------------------------------------
 
    public void setDynamicDataTableGroup(HtmlPanelGroup dynamicDataTableGroup) {
        this.dynamicDataTableGroup = dynamicDataTableGroup;
    }

    /**
     * @return the dynamicGrid
     */
    public HtmlPanelGrid getDynamicGrid() {
        return dynamicGrid;
    }

    /**
     * @param dynamicGrid the dynamicGrid to set
     */
    public void setDynamicGrid(HtmlPanelGrid dynamicGrid) {
        
       
        
        this.dynamicGrid = dynamicGrid;
    }
    
    /**
     * verfica che la somma dei pesi vosto,energia,emnissioni sia uguale 1
     * in caso contrario mostra un messaggio e imposta a false la variabile booleana pesicorretti
     * cosi da bloccare il calcola
     * @param event 
     */
    public void validaPesi(ComponentSystemEvent event) {
        
        
             dynamicGrid.getChildren().clear();
              //dynamicGrid1.getChildren().clear();
        
        
	  FacesContext fc = FacesContext.getCurrentInstance();
 
	  UIComponent components = event.getComponent();
 
	  // get costo
	  UIInput uiInputCosto = (UIInput) components.findComponent("costoid");
	  String costo = uiInputCosto.getLocalValue() == null ? ""
		: uiInputCosto.getLocalValue().toString();
	  String costoId = uiInputCosto.getClientId();
 
	  // get emissioni
	  UIInput uiInputEmissioni = (UIInput) components.findComponent("emissioneid");
	  String emissioni = uiInputEmissioni.getLocalValue() == null ? ""
		: uiInputEmissioni.getLocalValue().toString();
          
          // get energia
	  UIInput uiInputEnergia = (UIInput) components.findComponent("energiaid");
	  String energia = uiInputEnergia.getLocalValue() == null ? ""
		: uiInputEnergia.getLocalValue().toString();
 
	 System.out.println("emissioni "+ emissioni  +" costo " +costo + "  energia "+ energia + " --------------------------------------- sono nel validate   pesi  ---------------------------------------");
 
	  if (Double.parseDouble(costo) + Double.parseDouble(emissioni) + Double.parseDouble(energia) != 1) {
 
		FacesMessage msg = new FacesMessage("La somma deve essere 1");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(this.messaggioCosto.getClientId(), msg);
		fc.renderResponse();
                
               // this.refreshPage();
                
                this.pesicorretti = false;
                // System.out.println("sono nel if del validatePassword ---------------------------------------");    
	  }else
          {
              FacesMessage msg = new FacesMessage("");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(this.messaggioCosto.getClientId(), msg);
		fc.renderResponse();
                
               // this.refreshPage();
                
                this.pesicorretti = true;
          }
 
	}

    /**
     * @return the messaggioCosto
     */
    /*public UIComponent getMessaggioCosto() {
        return messaggioCosto;
    }*/

    /**
     * @param messaggioCosto the messaggioCosto to set
     */
    /*public void setMessaggioCosto(UIComponent messaggioCosto) {
        this.messaggioCosto = messaggioCosto;
    }*/

    /**
     * @return the operazionesingola
     */
    /*public boolean isOperazionesingola() {
        if(this.operazioneScelta.equals("singola"))
            this.operazionesingola = true;
        else
            this.operazionesingola = false;
        return operazionesingola;
    }*/

    /**
     * @param operazionesingola the operazionesingola to set
     */
    /*public void setOperazionesingola(boolean operazionesingola) {
          if(this.operazioneScelta.equals("singola"))
            this.operazionesingola = true;
        else
            this.operazionesingola = false;
        this.operazionesingola = operazionesingola;
    }*/

    /**
     * @return the listaRanking
     */
    /*public LinkedList<String> getListaRanking() {
        return listaRanking;
    }*/

    /**
     * @param listaRanking the listaRanking to set
     */
    /*public void setListaRanking(LinkedList<String> listaRanking) {
        this.listaRanking = listaRanking;
    }*/

    /**
     * @return the numeroRandom
     */
    public String getNumeroRandom() {
        return numeroRandom;
    }

    /**
     * @param numeroRandom the numeroRandom to set
     */
    public void setNumeroRandom(String numeroRandom) {
        this.numeroRandom = numeroRandom;
    }

    /**
     * @return the emissionia
     */
    /*public double getEmissionia() {
        return emissionia;
    }*/

    /**
     * @param emissionia the emissionia to set
     */
    /*public void setEmissionia(double emissionia) {
        this.emissionia = emissionia;
    }*/

    /**
     * @return the emissionig
     */
    /*public double getEmissionig() {
        return emissionig;
    }*/

    /**
     * @param emissionig the emissionig to set
     */
    /*public void setEmissionig(double emissionig) {
        this.emissionig = emissionig;
    }*/

    

    /**
     * @return the numeroRandom
     */
   /* public String getNumeroRandom() {
        return numeroRandom;
    }

    /**
     * @param numeroRandom the numeroRandom to set
     */
   /* public void setNumeroRandom(String numeroRandom) {
        this.numeroRandom = numeroRandom;
    }*/
    
     
}
