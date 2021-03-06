/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import WebGui.ProgressBarBean;
import ager.ContenitoreReflui;
import ager.Refluo;
import ager.TipiReflui;
import ager.VincoliNormativi;
import ager.trattamenti.ContenitoreAziendale;
import multiobiettivo.Alternativa;
import java.io.IOException;
import test.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
//import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;

import javax.faces.application.Application;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
//import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import multiobiettivo.ArrayHolder;
import multiobiettivo.FunzioneObiettivo;

import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.InputOutputXml;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class LetturaRisultati extends Thread {
    
    //implements Serializable
    // private static final long serialVersionUID = 1L;
     
     // private List<List<String>> dynamicList; // Simulate fake DB.
   // private String[] dynamicHeaders; // Optional.
  //  private HtmlPanelGroup dynamicDataTableGroup; // Placeholder.
    
    
     private HtmlPanelGrid dynamicGrid;  
    // private HtmlPanelGrid dynamicGrid1;  
     javax.servlet.jsp.JspWriter dinamicOut;
     String operazioneScelta = "";
     
     
     
     //usato da gruppoaziende.jsp per stamapre 
     //lo stato iniziale delle caratteristiche chimiche 
     //di un gruppo di aziende prima che si applichi una
     //qualsiasi alternativa
     private ContenitoreReflui contenitoreflui = null;
     
     
    InputOutputXml leggi = null;
    String numero = "";
    //boolean singolaAzienda = false;
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    
    Modello modello;
    
    /**
     * mi informa se deve stampare su html oppure no
     * se true deve stamapre altrimenti no
     */
    boolean flagOutput=true;
    
    int scenario = 0;
    
   /**
    * costruttore proveniente dalla classe solutore
    * @param leggi
    * @param numero
    * @param operazioneScelta
    * @param modello 
    */
    public LetturaRisultati(InputOutputXml leggi,String numero,String operazioneScelta,Modello modello)
    {
         this.leggi = leggi;
         this.numero = numero;
         this.operazioneScelta = operazioneScelta;
         this.modello = modello;  
    }
    /**
     * costruttore per il recupero dei risultati senza localizzazione
     * @param leggi
     * @param numero
     * @param dynamicGrid
     * @param operazioneScelta 
     */
     public LetturaRisultati(InputOutputXml leggi,String numero,HtmlPanelGrid dynamicGrid,String operazioneScelta,Modello modello)
     {
         this.leggi = leggi;
         this.numero = numero;
         this.dynamicGrid = dynamicGrid;
         
         this.operazioneScelta = operazioneScelta;
         this.modello = modello;
        
     }
     
     /**
      * caso del costruttore per multiaziende
      * @param leggi
      * @param numero
      * @param dinamicOut
      * @param modello
      * @param operazioneScelta 
      */
     public LetturaRisultati(InputOutputXml leggi,String numero,javax.servlet.jsp.JspWriter dinamicOut,Modello modello,String operazioneScelta, int scenario)
     {
         this.leggi = leggi;
         this.numero = numero;
         this.dinamicOut = dinamicOut;
         this.operazioneScelta = operazioneScelta;
         this.modello = modello;
         this.scenario = scenario;
         
         
         //this.singolaAzienda = singola;
       /* try {
            this.dinamicOut.println("sono nel costruttore del thread");
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
     }
     
     /**
      * cotruttore per la localizzazione
      * @param leggi
      * @param numero
      * @param dinamicOut è il contenitore dove scrivere i risultati nel caso della localizzionae
      * @param operazioneScelta 
      */
     public LetturaRisultati(InputOutputXml leggi,String numero,javax.servlet.jsp.JspWriter dinamicOut,String operazioneScelta,Modello modello)
     {
         this.leggi = leggi;
         this.numero = numero;
         this.dinamicOut = dinamicOut;
         this.operazioneScelta = operazioneScelta;
         this.modello = modello;
         //this.singolaAzienda = singola;
       /* try {
            this.dinamicOut.println("sono nel costruttore del thread");
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
     }
     
     
     public   void run()
     {
         
     }
     
     
     public   void run1()
     {
         int iterazione = 0;
         
         if(modello != null)
          System.out.println("Stato del thread modello " + modello.getState() + " itreazione " + iterazione + " Modello.finito " + Modello.finito);
         
         while( Modello.finito == false  )
        // while(modello == null || Modello.finito == false )     
         {
             try {
                 System.out.println("Stato del thread modello nel while " + modello.getState() + " itreazione " + iterazione + " Modello.finito " + Modello.finito);
                 this.sleep(1000);
                 iterazione++;
                 
                 if(iterazione > 20)
                 {
                     mostraErrore();
                     break;
                 }
             } catch (InterruptedException ex) {
                 Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
       /* try {
            this.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }*/
         
        
         for(int i = 0; i < 10;i++)
         {
            if(!leggi.impostaFile("output_"+numero+".xml"))
            {
                 try {  
                     this.sleep(500);
                     System.out.println("Aspetto che il file sia pronto. "+ i);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }else
                    {
                        break;
                    }
         }
       
         
         
         this.ottieniValori(leggi);
         
         //recuperMigliore(leggi);
         
         Connessione.getInstance().chiudi(true);
     }
     
      public void recuperMigliore(InputOutputXml leggi)
    {
        String hh = leggi.cercaSingolo("//alternativa/scelta");
        recuperaSingola2(leggi,true,"//alternativa",hh);
    }        
       
      
   public void recuperMiglioremulti(InputOutputXml leggi)
    {
          String hh = leggi.cercaSingolo("//alternativa/scelta");
        /**
            * apri l'header della tabella rissuntiva fuori dal dettaglio
            */
            apriHeadertable();
            recuperaDatiAnalisi(leggi,"//alternativa",hh);
           
            
            
             /**
            *  l'header della tabella rissuntiva fuori dal dettaglio
            */
            chiudiHeadertable();
        
        if(this.contenitoreflui != null)
                stampaStatoIniziale();
      
        recuperaSingola2multi(leggi,true,"//alternativa",hh);
    }        
     /**
     * dato che il ranking prende in considerazione tutte le alternative e produce in 
     * outpput (xml) la lista ordinata per posizione delle alternative vado prima a
     * leggere il numero di alternative che si trovano in output ovvero nel file xml e poi 
     * ciclo su ogno alternativa. In xpath l'incremanetale dop il nome di un nodo ne identifica la posizione
     * del nodo all'interno del xml ovvero //alternativa[4] significa prendi la 4 alternativa nella lista
     * delle laternative
     * @param leggi 
     */
    public void recuperaRank(InputOutputXml leggi)
    {
     
          dynamicGrid.getChildren().clear();
         
         // Hashtable<String,Double> temp = null;
          
        /*   System.out.println("recupera rank conta alternative inizio");
           int numeronodi = leggi.contaNodi("alternativa");
            System.out.println("numero alternative trovate " +numeronodi);
           System.out.println("recupera rank conta alternative fine");*/
            int numeronodi = leggi.contaNodi("alternativa");
          
           // System.out.println(this.getClass().getCanonicalName()+Thread.currentThread().getStackTrace()[1].getMethodName()+" numero nodi " + numeronodi +" ----------------------------");
          
             Application app = FacesContext.getCurrentInstance().getApplication();
             HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Rank Alternative ");
        text.setStyle("font-weight:bold;font-size:34;");
        //text.setStyle("background-color:lightblue;");
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
        
        //int l = 0;
        for (int i = 1; i <= numeronodi; i++) {
            String hh = leggi.cercaSingolo("//alternativa[" + i + "]/scelta");
             text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
            text.setValue("Numero : ");
            dynamicGrid.getChildren().add(text);
            text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
            text.setValue(hh);
            dynamicGrid.getChildren().add(text);
             this.aggiungiNuovaLinea(2);
             
             //System.out.println("hh " + hh  +" numero nodi " + numeronodi);
            //l++;
             
             
        }
        
        this.aggiungiNuovaLinea(0);
        this.aggiungiNuovaLinea(0);
         /*  text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE); 
        text.setValue("Rank Alternative 2121");
        dynamicGrid1.getChildren().add(text);
             this.aggiungiNuovaLinea1(1);
             text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
             dynamicGrid1.getChildren().add(text);
             this.aggiungiNuovaLinea1(1);
              text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
             dynamicGrid1.getChildren().add(text);
             this.aggiungiNuovaLinea1(1);
        */
        
            //l = l + 1;
        //this.aggiungiNuovaLinea(l++);
            
           for(int i = 1; i <= numeronodi;i++)
           {
               String hh = leggi.cercaSingolo("//alternativa["+i+"]/scelta");
               //System.out.println("recuperaRank di lettorarisultati cercasingolo  " + hh);
               recuperaSingola2(leggi,false,"//alternativa["+i+"]",hh);
               
              // listaRanking.add(hh);
           }
           
           
         /*  for(int i = 0; i < listaRanking.size();i++)
           {
               System.out.println("Lista ranking  alternativa N:" + listaRanking.get(i));
           }*/
           
          /* recuperaSingola2(leggi,false,"//alternativa[1]","1");
           recuperaSingola2(leggi,false,"//alternativa[2]","1");
           recuperaSingola2(leggi,false,"//alternativa[3]","1");
           recuperaSingola2(leggi,false,"//alternativa[4]","1");
           recuperaSingola2(leggi,false,"//alternativa[5]","1");
            recuperaSingola2(leggi,false,"//alternativa[6]","1");
           recuperaSingola2(leggi,false,"//alternativa[7]","1");*/
        
    }
    
    /**
     * crea una tabella html che mostra lo stato iniziale nel caso di gruppoaziende 
     * prima che vengano eseguite le alternative
     * 
     */
    private void stampaStatoIniziale()
    {
         try{
                           dinamicOut.println("<div id=\"labellocalizzazioneTable2\"><p><h3>Situazione zootecnica prima dell\' ottimizzazione </h3></p></div>");
                           dinamicOut.println("<table class=\"localizzazioneTable2\">");
                           dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(kg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
                          
                           }catch( Exception e)
                               {
                                e.printStackTrace();
                               
                           }
                                                   
                           
                           String[] tipi = this.contenitoreflui.getTipologie();



                           for (String tipologia : tipi) {
                               
                               Refluo ref = this.contenitoreflui.getTipologia(tipologia);
                                try{
                               dinamicOut.println("<tr><td>"+tipologia+"</td><td>"+(int)ref.getMetricubi()+"</td><td>"+(int)ref.getAzotototale()+"</td><td>"+(int)ref.getAzotoammoniacale()+"</td><td>"+(int)ref.getSostanzasecca()+"</td><td>"+(int)ref.getSolidivolatili()+"</td><td>"+(int)ref.getFosforototale()+"</td><td>"+(int)ref.getPotassiototale()+"</td></tr>");
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
    
    
    
    /**
     * dato che il ranking prende in considerazione tutte le alternative e produce in 
     * outpput (xml) la lista ordinata per posizione delle alternative vado prima a
     * leggere il numero di alternative che si trovano in output ovvero nel file xml e poi 
     * ciclo su ogno alternativa. In xpath l'incremanetale dop il nome di un nodo ne identifica la posizione
     * del nodo all'interno del xml ovvero //alternativa[4] significa prendi la 4 alternativa nella lista
     * delle laternative
     * @param leggi 
     */
    public void recuperaRankmulti(InputOutputXml leggi)
    {
     
          //dynamicGrid.getChildren().clear();
         // Hashtable<String,Double> temp = null;
          
        /*   System.out.println("recupera rank conta alternative inizio");
           int numeronodi = leggi.contaNodi("alternativa");
            System.out.println("numero alternative trovate " +numeronodi);
           System.out.println("recupera rank conta alternative fine");*/
            int numeronodi = leggi.contaNodi("alternativa");
          
           // System.out.println(this.getClass().getCanonicalName()+Thread.currentThread().getStackTrace()[1].getMethodName()+" numero nodi " + numeronodi +" ----------------------------");
          
           /**
            * apri l'header della tabella rissuntiva fuori dal dettaglio
            */
            apriHeadertable();
            List<String> listaRank = new LinkedList<String>();
            
            for(int i = 1; i <= numeronodi;i++)
           {
               //recupero l'alternativa 
               String hh = leggi.cercaSingolo("//alternativa["+i+"]/scelta");
               //System.out.println("recuperaRank di lettorarisultati cercasingolo  " + hh);
               recuperaDatiAnalisi1(leggi,"//alternativa["+i+"]",hh);
               listaRank.add(hh);
           }
            
            
           // ProgressBarBean pro = new ProgressBarBean();
            
           // pro.incrementValue(20);
            
              /**
            *  l'header della tabella rissuntiva fuori dal dettaglio
            */
            chiudiHeadertable();
            
            
              try{
                 this.dinamicOut.println("<br/><br/><br/>");
              }catch(Exception ex){ex.printStackTrace();}
            
            if(this.contenitoreflui != null)
                stampaStatoIniziale();
            
            // pro.incrementValue(20);
            
            /**
             * creo una lista di riferienti alle ancora create nel nel recuperarankmultisingola
             */
            try{
                 this.dinamicOut.println("<h4 class=\"labellocalizzazioneTable\"><a name=\"linkrapidi\">Link rapidi alle alternative :</a></h4>");
              this.dinamicOut.println("<ul class=\"labellocalizzazioneTable\">");
            }catch(Exception ex){ex.printStackTrace();}
            ListIterator<String> iterAlternative = listaRank.listIterator();
            while(iterAlternative.hasNext())
            {
                String tt = iterAlternative.next();
                try{
                this.dinamicOut.println("<li><a href=\"#"+tt+"\">Alternativa "+tt+"</a></li>");
                }catch(Exception ex){ex.printStackTrace();}
            }
            
            try{
              this.dinamicOut.println("</ul>");
            }catch(Exception ex){ex.printStackTrace();}
           
            
           for(int i = 1; i <= numeronodi;i++)
           {
               String hh = leggi.cercaSingolo("//alternativa["+i+"]/scelta");
               //System.out.println("recuperaRank di lettorarisultati cercasingolo  " + hh);
               recuperaSingola2multi(leggi,false,"//alternativa["+i+"]",hh);
           }
           
          /* recuperaSingola2(leggi,false,"//alternativa[1]","1");
           recuperaSingola2(leggi,false,"//alternativa[2]","1");
           recuperaSingola2(leggi,false,"//alternativa[3]","1");
           recuperaSingola2(leggi,false,"//alternativa[4]","1");
           recuperaSingola2(leggi,false,"//alternativa[5]","1");
            recuperaSingola2(leggi,false,"//alternativa[6]","1");
           recuperaSingola2(leggi,false,"//alternativa[7]","1");*/
           
           
           //pro.incrementValue(60);
           
           
        
    }
    
    
    
    /**
     * dato che il ranking prende in considerazione tutte le alternative e produce in 
     * outpput (xml) la lista ordinata per posizione delle alternative vado prima a
     * leggere il numero di alternative che si trovano in output ovvero nel file xml e poi 
     * ciclo su ogno alternativa. In xpath l'incremanetale dop il nome di un nodo ne identifica la posizione
     * del nodo all'interno del xml ovvero //alternativa[4] significa prendi la 4 alternativa nella lista
     * delle laternative
     * @param leggi 
     */
    public void recuperaRankmultiObiettivo(InputOutputXml leggi,List<Alternativa> alternative)
    {
     
        
            int numeronodi = leggi.contaNodi("alternativa");
       
           /**
            * apri l'header della tabella rissuntiva fuori dal dettaglio
            */
           
            List<String> listaRank = new LinkedList<String>();
            Alternativa alternativa ;
            
            for(int i = 1; i <= numeronodi;i++)
           {
               //creo una nuova laternativa in teso come contenitore di dati per l'alternativa
                alternativa = new Alternativa();
               //recupero l'alternativa 
               String hh = leggi.cercaSingolo("//alternativa["+i+"]/scelta");
               alternativa.setNumeroalternativa(Integer.parseInt(hh));
               //System.out.println("recuperaRank di lettorarisultati cercasingolo  " + hh);
              
               recuperaDatiAnalisi1m3(leggi,"//alternativa["+i+"]",hh,alternativa);
               alternative.add(alternativa);
               listaRank.add(hh);
           }
            
           /**
            *  l'header della tabella rissuntiva fuori dal dettaglio
            */
            //chiudiHeadertable();
            
           /**
            * Calcolo per tutte el alternative la funzione obiettivo e le ordino in funzione di ogni combinaizone
            * di pesi
            */
           FunzioneObiettivo funzObiettivo = new FunzioneObiettivo(alternative);
           /**
            * recupero la strutturta dati che contiene per ogni combinazione di pesi le alternative
            * ordinate 
            */
           LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> ordineAlternative = funzObiettivo.getOrdineAlternative();
           /**
            * recupero il numero di volte che le alternative son al primo posto 
            */
           List<Alternativa> numerovolte_alternative = funzObiettivo.getMediaAlternative();
           
           try {
            this.dinamicOut.println("<div id=\"descrizioneprima\"><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul></div>");

        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        creaTabellaNumeroVolte(numerovolte_alternative);
        
        try {
            this.dinamicOut.println("<br/><br/>");
            this.dinamicOut.println("<div id=\"labelseconda\"><p><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul>");
            this.dinamicOut.println("<p><h3>Seconda analisi  :</h3>di seguito tutti i pesi e per ogni combinazione di pesi tutte le alternative ordinate usando la funzioen obiettivo</p></div>");

        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }

        creaTabellaCombinazioniPesi(ordineAlternative);
           
           
           
    }
    
    /**
     * crea una lunga tabella contenente per ogni comniazione di pesi i valori di tutte le alternative orindate in funzione del peso
     * ovvero in funzoine del valore della funzoien obiettivo calcolata su quella combinazione di pesi
     * @param ordineAlternative 
     */
    private void creaTabellaCombinazioniPesi( LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> ordineAlternative)
    {
         DecimalFormat df = new DecimalFormat("#.##");
        for (ArrayHolder<Float> chiavi : ordineAlternative.keySet()) {
            Float[] chiavi1 = chiavi.getArray();
            /*try {
                this.dinamicOut.println("<br/><p>Peso ema " + chiavi1[0] + " peso emg " + chiavi1[1] + " peso energia " + chiavi1[2] + "  peso costo " + chiavi1[3] + "</p><br/> ");
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }*/
           /* try {
                this.dinamicOut.println("<br/><br/>");
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            this.apriHeadertablemulti(1);


            //data una chiave recuperi la lista delle alternative orindate dalla hashtable
            List<Alternativa> listaAlternative = ordineAlternative.get(chiavi);
            Alternativa alTemp;
            String composizione = "";
            ListIterator<Alternativa> iterAlt = listaAlternative.listIterator();
            while (iterAlt.hasNext()) {
                alTemp = iterAlt.next();



                try {
                    this.dinamicOut.println("<tr>");
                    this.dinamicOut.println("<td>" + alTemp.getNumeroalternativa() + "</td>");
                    ListIterator<String> compoList = alTemp.getComposizione().listIterator();

                    while (compoList.hasNext()) {
                        composizione += compoList.next() + " | ";
                    }
                    this.dinamicOut.println("<td>" + composizione + "</td>");


                    this.dinamicOut.println("<td>" + df.format(chiavi1[0]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[1]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[2]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[3]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEmissioni_acide_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEmissioni_gas_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEnergia_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getCosto_m3()) + "</td>");
                    
                 
                    
                    
                    
                    this.dinamicOut.println("<tr>");
                    composizione = "";
                } catch (IOException ex) {
                    Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
                }

            }


            this.chiudiHeadertable();

        }
        
        
        
        
        
        
       /* try {
            this.dinamicOut.println("<div><table id=\"tabellamulti1\"><thead><tr><th># Alt.</th><th>Descrizione</th><th>P Em A</th><th>P Em G</th><th>P Energia</th><th>P Costo</th><th>Em A</th><th>Em G</th><th>Energia</th><th>Costo</th></tr></thead>");
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
          this.apriHeadertablemulti(2);
        
        /**
         * mi creo una tabella contenente per ogni combinazione di pesi la prima alternativa
         * questa tabella mi serve per fare il secondo grafico
         */
        for (ArrayHolder<Float> chiavi : ordineAlternative.keySet()) {
            Float[] chiavi1 = chiavi.getArray();
            /*try {
                this.dinamicOut.println("<br/><p>Peso ema " + chiavi1[0] + " peso emg " + chiavi1[1] + " peso energia " + chiavi1[2] + "  peso costo " + chiavi1[3] + "</p><br/> ");
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }*/
           /* try {
              //  this.dinamicOut.println("<br/><br/>");
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
          


            //data una chiave recuperi la lista delle alternative orindate dalla hashtable
            List<Alternativa> listaAlternative = ordineAlternative.get(chiavi);
            Alternativa alTemp;
            String composizione = "";
           // ListIterator<Alternativa> iterAlt = listaAlternative.listIterator();
            //while (iterAlt.hasNext()) {
                //alTemp = iterAlt.next();

                 alTemp =listaAlternative.get(0);

                try {
                    this.dinamicOut.println("<tr>");
                    this.dinamicOut.println("<td>" + alTemp.getNumeroalternativa() + "</td>");
                    ListIterator<String> compoList = alTemp.getComposizione().listIterator();

                    while (compoList.hasNext()) {
                        composizione += compoList.next() + " | ";
                    }
                    this.dinamicOut.println("<td>" + composizione + "</td>");


                    this.dinamicOut.println("<td>" + df.format(chiavi1[0]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[1]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[2]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(chiavi1[3]) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEmissioni_acide_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEmissioni_gas_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getEnergia_m3()) + "</td>");
                    this.dinamicOut.println("<td>" + df.format(alTemp.getCosto_m3()) + "</td>");
                    
                 
                    
                    
                    
                    this.dinamicOut.println("<tr>");
                    composizione = "";
                } catch (IOException ex) {
                    Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
                }

            //}


           

        }
        try {
            this.dinamicOut.println("</table></div>");
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
    
    /**
     * crea una tabella e la popola con le alternative migliori indicandone il nuemro di vvolte che
     * compaiono nella LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> della classe funzioneobiettivo 
     * usando il metodo ciclapesi che cicla su tutte le combinazioni di pesi e per ogni combinazione di pesi calcola la 
     * funzione obiettivo ed ordina le alternative
     */
    private void creaTabellaNumeroVolte(List<Alternativa> lista)
    {
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        this.apriHeadertablemulti(0);
       
        ListIterator<Alternativa> iterAl = lista.listIterator();
        
        Alternativa alTemp ;
        String composizione = "";
        while(iterAl.hasNext())
        {
            
            alTemp = iterAl.next();
            try {
                 this.dinamicOut.println("<tr>");
                this.dinamicOut.println("<td>"+alTemp.getNumeroalternativa()+"</td>");
                ListIterator<String> compoList =alTemp.getComposizione().listIterator();
                
                while(compoList.hasNext())
                {
                    composizione +=compoList.next()+" | ";
                }
                 this.dinamicOut.println("<td>"+composizione+"</td>");
                 
                  this.dinamicOut.println("<td>"+df.format(alTemp.getNumero_volte())+"</td>");
                   this.dinamicOut.println("<td>"+df.format(alTemp.getPesomedio_emissioniacide())+"</td>");
                    this.dinamicOut.println("<td>"+df.format(alTemp.getPesomedio_emisisonigas())+"</td>");
                     this.dinamicOut.println("<td>"+df.format(alTemp.getPesomedio_energia())+"</td>");
                     this.dinamicOut.println("<td>"+df.format(alTemp.getPesomedio_costo())+"</td>");               
                     this.dinamicOut.println("<td>"+df.format(alTemp.getEmissioni_acide_m3())+"</td>");
                     this.dinamicOut.println("<td>"+df.format(alTemp.getEmissioni_gas_m3())+"</td>");
                     this.dinamicOut.println("<td>"+df.format(alTemp.getEnergia_m3())+"</td>");
                     this.dinamicOut.println("<td>"+df.format(alTemp.getCosto_m3())+"</td>");
                  this.dinamicOut.println("<tr>");
                  composizione = "";
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        
        this.chiudiHeadertable();
    }
    
    
    public void ottieniValori(InputOutputXml leggi)
    {
          // Hashtable<String,Double> temp = null;
        
        switch (this.operazioneScelta) {
            case "rank":
                recuperaRank(leggi);
                break;
            case "migliore":
                recuperMigliore(leggi);
                break;
            case "singola":
                recuperaSingola2(leggi, true, "//alternativa", "");
                break;
            case "rankmulti":
                recuperaRankmulti(leggi);
                break;
             case "rankmultiobiettivo":
                 //mi genero una lista di alternative con i totali di output 
                 //delle alternative che mi servira per l'analisi multiobiettivo
                 List<Alternativa> alternative = new LinkedList<Alternativa>();
                recuperaRankmultiObiettivo(leggi,alternative);
                break;    
            case "miglioremulti":
                /*if(this.contenitoreflui != null)
                stampaStatoIniziale();*/
                recuperMiglioremulti(leggi);
                break;
            case "singolamulti":
                 String hh = leggi.cercaSingolo("//alternativa/scelta");
                /**
                 * apri l'header della tabella rissuntiva fuori dal dettaglio
                 */
                //apriHeadertable();
                apriHeadertableRichAnalisi();
               
                recuperaDatiAnalisi(leggi, "//alternativa", hh);
                /**
                 * l'header della tabella rissuntiva fuori dal dettaglio
                 */
                chiudiHeadertable();        
            
            /*if(this.contenitoreflui != null)
                stampaStatoIniziale();*/
                
                // apriHeadertableRichAlt();
                //recuperaSingola2multi(leggi, true, "//alternativa", "");
                recuperaSingola2multiRich(leggi, true, "//alternativa", hh);
                
                 //chiudiHeadertable();    
                break;
          //caso chiamato direttamente dalla classe solutore per il progetto renuwal2  
          case "singolamulti1":
                String hh1 = leggi.cercaSingolo("//alternativa/scelta");
                String padre = "//alternativa";
                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/bovino/liquame",this.contenitoreflui.getTipologia("Liquame Bovino"));
                
                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/bovino/letame",this.contenitoreflui.getTipologia("Letame Bovino"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/suino/liquame",this.contenitoreflui.getTipologia("Liquame Suino"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/suino/letame",this.contenitoreflui.getTipologia("Letame Suino"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/avicolo/liquame",this.contenitoreflui.getTipologia("Liquame Avicolo"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/avicolo/letame",this.contenitoreflui.getTipologia("Letame Avicolo"));
                
                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/altro/liquame",this.contenitoreflui.getTipologia("Liquame Altro"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/altro/letame",this.contenitoreflui.getTipologia("Letame Altro"));
                
                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/biomassa/liquame",this.contenitoreflui.getTipologia("Liquame Biomassa"));

                caratteristichechimicheL(padre + "/totali/caratteristichechimiche/biomassa/letame",this.contenitoreflui.getTipologia("Letame Biomassa"));

                
                break;      
                
                
                //se non rientra negli altri casi sei nella localizzazione    
            default:
                recuperaLocalizzazione(leggi);
                break;
        }
       
    }
    
    /**
     * mostra una scritta di errore nel caso in cui il file
     * dei risutlati non sia stato trovato o non sia leggibile dopo il tempo
     * indicato nel ciclo while del run 
     * */
    protected void mostraErrore()
    {
         if(dynamicGrid != null)
         {
         Application app = FacesContext.getCurrentInstance().getApplication();
        
        
        HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("File dei risultati non presente ");
        text.setStyle("font-size:30px;color:red;");
       
            dynamicGrid.getChildren().add(text);
         }else
         {
            try {
                dinamicOut.println("<p style=\"font-size:30px;color:red;\">File dei risultati non presente</p>");
            } catch (IOException ex) {
                Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }
    
    
    /**
     * recupera dal file xml di output le caratteristiche chimiche di input ed output dall'impianto
     * in funzione della query che gli viene passata.Recupera le caratteristiche chimiche di un tipolgia di refluo ovvero per
     * esempio letame bovino e produce una riga della tabella.
     * @param query 
     */
    private void caratteristichechimicheMC(String query, DecimalFormat dformat)
    {
        NodeList temp = null;
        double ddfor =0;
        //temp = null;
        //String pattern1 = "(\\w*)([\\.,](\\w*))";
        try {
            temp = leggi.cerca1(query, true);
            
            this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Ch4(Kg/m<sup>3</sup>)</th><th>Nh3(Kg/m<sup>3</sup>)</th><th>N2(Kg/m<sup>3</sup>)</th><th>N2o(Kg/m<sup>3</sup>)</th><th>Co2(Kg/m<sup>3</sup>)</th><th>No(Kg/m<sup>3</sup>)</th></tr></thead><tr>");

           
            
            for (int i = 0; i < temp.getLength(); i++) {
                // this.dinamicOut.println("</br>");
                if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) // this.dinamicOut.println("<p>caratteristiche chimiche : " + temp.item(i).getFirstChild().getNodeValue() + " value " + temp.item(i).getNodeName()+"</p>");
                // this.dinamicOut.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");  
                {
                    //System.out.println(" temp.item(i).getLocalName() " + temp.item(i).getLocalName());
                    if (temp.item(i).getLocalName().equals("ch4") || temp.item(i).getLocalName().equals("nh3") || temp.item(i).getLocalName().equals("n2") || temp.item(i).getLocalName().equals("n2o") || temp.item(i).getLocalName().equals("co2") || temp.item(i).getLocalName().equals("no")) {
                        ddfor = Double.parseDouble(temp.item(i).getFirstChild().getNodeValue());

                        this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                    }
                }
            }

            this.dinamicOut.println("</tr></table>");

            this.dinamicOut.println("<br/>");
              this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
            this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
            this.dinamicOut.println("<thead><tr><th>Gestione Netto(euro/a*m<sup>3</sup>)</th><th>Costo lordo gestione(euro/a*m<sup>3</sup>)</th></tr></thead><tr>");


            for (int i = 0; i < temp.getLength(); i++) {
                // this.dinamicOut.println("</br>");
                if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) // this.dinamicOut.println("<p>caratteristiche chimiche : " + temp.item(i).getFirstChild().getNodeValue() + " value " + temp.item(i).getNodeName()+"</p>");
                // this.dinamicOut.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");  
                {
                    //System.out.println(" temp.item(i).getLocalName() " + temp.item(i).getLocalName());
                    if (temp.item(i).getLocalName().equals("gestione") || temp.item(i).getLocalName().equals("costo_netto_gestione")) {
                        ddfor = Double.parseDouble(temp.item(i).getFirstChild().getNodeValue());

                        this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                    }
                }
            }

            this.dinamicOut.println("</tr></table>");

             this.dinamicOut.println("<br/>");
            
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
            this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
            this.dinamicOut.println("<thead><tr><th>Ricavo netto energia(euro/a*m<sup>3</sup>)</th><th>Energia Prodotta(KWh/m<sup>3</sup>)</th><th>Energia Consumata(KWh/m<sup>3</sup>)</th></tr></thead><tr>");

            for (int i = 0; i < temp.getLength(); i++) {
                // this.dinamicOut.println("</br>");
                if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) // this.dinamicOut.println("<p>caratteristiche chimiche : " + temp.item(i).getFirstChild().getNodeValue() + " value " + temp.item(i).getNodeName()+"</p>");
                // this.dinamicOut.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");  
                {
                    //System.out.println(" temp.item(i).getLocalName() " + temp.item(i).getLocalName());
                    if (temp.item(i).getLocalName().equals("ricavo_netto_energia") || temp.item(i).getLocalName().equals("energia_prodotta") || temp.item(i).getLocalName().equals("energia_consumata")) {
                        ddfor = Double.parseDouble(temp.item(i).getFirstChild().getNodeValue());

                        this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                    }
                }
            }

            this.dinamicOut.println("</tr></table>");
               
               this.dinamicOut.println("<br/>"); 
             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * recupera dal file xml di output le caratteristiche chimiche di input ed output dall'impianto
     * in funzione della query che gli viene passata.Recupera le caratteristiche chimiche di un tipolgia di refluo ovvero per
     * esempio letame bovino e produce una riga della tabella.
     * @param query 
     */
    private void caratteristichechimicheMCRich(String query, DecimalFormat dformat)
    {
        NodeList temp = null;
        double ddfor =0;
        //temp = null;
        //String pattern1 = "(\\w*)([\\.,](\\w*))";
        try {
            temp = leggi.cerca1(query, true);
            
            //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\" ><colgroup span=\"11\"></colgroup>");
             this.dinamicOut.println("<thead >"
                        + "<tr><th colspan=\"11\" >Valori al metro cubo</th></tr>"
                        + "<tr><th colspan=\"6\" >Emissioni(Kg/m<sup>3</sup>)</th>"
                        + "<th colspan=\"2\" >Costi(euro/a*m<sup>3</sup>)</th>"
                        + "<th colspan=\"3\" >Energia(KWh/m<sup>3</sup>)</th></tr>");
             this.dinamicOut.println("<thead>"
                     + "<tr><th colspan=\"1\" >Ch4</th>"
                     + "<th colspan=\"1\" >Nh3</th>"
                     + "<th colspan=\"1\" >N2</th>"
                     + "<th colspan=\"1\" >N2o</th>"
                     + "<th colspan=\"1\" >Co2</th>"
                     + "<th colspan=\"1\" >No</th>"
                     + "<th colspan=\"1\" >Gestione Netto</th>"
                     + "<th colspan=\"1\" >Costo lordo gestione</th>"
                     + "<th colspan=\"1\" >Ricavo netto energia</th>"
                     + "<th colspan=\"1\" >Prodotta</th>"
                     + "<th colspan=\"1\" >Consumata</th>"
                     + "</tr></thead>" );
                     

            this.dinamicOut.println("<tbody ><tr >");
            
            Map<String,String> valoriMC = new LinkedHashMap();
            valoriMC.put("ch4","");
            valoriMC.put("nh3","");
            valoriMC.put("n2","");
            valoriMC.put("n2o","");
            valoriMC.put("co2","");
            valoriMC.put("no","");
            valoriMC.put("gestione","");
            valoriMC.put("costo_netto_gestione","");
            valoriMC.put("ricavo_netto_energia","");
            valoriMC.put("energia_prodotta","");
            valoriMC.put("energia_consumata","");
            
            for (int i = 0; i < temp.getLength(); i++) {
                // this.dinamicOut.println("</br>");
                if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) // this.dinamicOut.println("<p>caratteristiche chimiche : " + temp.item(i).getFirstChild().getNodeValue() + " value " + temp.item(i).getNodeName()+"</p>");
                // this.dinamicOut.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");  
                {
                    String tt =temp.item(i).getFirstChild().getNodeValue();
                    System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " valore letto da xml " +tt );
                    
                    //metto in valoriMC nella chiave indicata dal nome dell'item il suo valore
                    //valoriMC.put(temp.item(i).getLocalName().trim(),dformat.format(Double.parseDouble(tt)));
                    int po = tt.indexOf(".");
                    tt = tt.substring(0, po + 3);
                    valoriMC.put(temp.item(i).getLocalName().trim(),tt);
                  
                    }
                }
            
            Iterator it = valoriMC.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry pair = (Map.Entry)it.next();
                
                this.dinamicOut.println("<td>"+ pair.getValue()+"</td>");
            }
            
            
         

            this.dinamicOut.println("</tr></table>");

            this.dinamicOut.println("<br/>"); 
             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * recupera dal file xml di output le caratteristiche chimiche di input ed output dall'impianto
     * in funzione della query che gli viene passata.Recupera le caratteristiche chimiche di un tipolgia di refluo ovvero per
     * esempio letame bovino e produce una riga della tabella.
     * @param query 
     */
    private void caratteristichechimiche(String query)
    {
        NodeList temp = null;
        temp = null;
        String pattern1 = "(\\w*)([\\.,](\\w*))";
        try{
            temp = leggi.cerca1(query,true);
            
            if(temp != null)
            {
             for (int i = 0; i < temp.getLength(); i++) {
               // this.dinamicOut.println("</br>");
                    if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) {
                       // String tempV = temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1");
                        
                     this.dinamicOut.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");
                     //   this.dinamicOut.println("<td>" + tempV +"</td>");
                 }         
            }
            }
        }catch(Exception ex)
        {ex.printStackTrace();}
    }
    
    
    
    /**
     * recupera dal file xml di output le caratteristiche chimiche di input ed output dall'impianto
     * in funzione della query che gli viene passata.Recupera le caratteristiche chimiche di un tipolgia di refluo ovvero per
     * esempio letame bovino e produce una riga della tabella.
     * @param query 
     */
    private void caratteristichechimicheL(String query,Refluo lista )
    {
        NodeList temp = null;
        temp = null;
        String pattern1 = "(\\w*)([\\.,](\\w*))";
        
        
        System.out.println(query);
        try{
            temp = leggi.cerca1(query,true);
            
            if(temp != null)
            {
             for (int i = 0; i < temp.getLength(); i++) {
               // this.dinamicOut.println("</br>");
                    if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) {
                       // String tempV = temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1");
                        //recupero il nome del tipo di caratteristica
                        String tipoRefluo = temp.item(i).getLocalName(); 
                        //recupero il valore della caratterstica
                        String valore = temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1");
                        
                        
                        System.out.println("tipo refluo " + tipoRefluo + " valore : " + valore);
                        //associo il valore alla caratteristica
                        switch(tipoRefluo)
                        {
                            case "m3":
                                lista.setMetricubi(Double.parseDouble(valore));
                                break;
                            case "at":
                                lista.setAzotototale(Double.parseDouble(valore));
                                break;
                            case "am":
                                lista.setAzotoammoniacale(Double.parseDouble(valore));
                                break;
                            case "ss":
                                lista.setSostanzasecca(Double.parseDouble(valore));
                                break;
                            case "sv":
                                lista.setSolidivolatili(Double.parseDouble(valore));
                                break;
                            case "ft":
                                lista.setFosforototale(Double.parseDouble(valore));
                                break;
                            case "pt":
                                lista.setPotassiototale(Double.parseDouble(valore));
                                break;    
                        }
                     //System.out.println(" " + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +" ");
                     //   this.dinamicOut.println("<td>" + tempV +"</td>");
                 }         
            }
            }
        }catch(Exception ex)
        {ex.printStackTrace();}
    }
    
    
    /**
     * usato nella localizzazione per mostrare lo stato pirma e dopo la soluzione trovata dal solutore
     * 
     * @param query
     * @param primadopo serve per distinguere il prima dal dopo
     */
    private void recuperaStatoInizialeFinale(String query,boolean primadopo)
    {
        NodeList temp1 = null;
        String separa ="";
        
        if(primadopo)
            separa="a";
        else
            separa = "b";
        
          String pattern1 = "(\\w*)([\\.,](\\w*))";
        try{
             //temp1 = leggi.cerca1("//riepilogo/iniziale",true);
              temp1 = leggi.cerca1(query,true);
               //this.dinamicOut.println("<div id=\"welcome11\" style=\"display:none;\">aaaaaa</div>");
             this.dinamicOut.println("<br/><br/><table class=\"table table-striped table-header-rotated\"><tr>");
             
           
                     for (int i1 = 0; i1 < temp1.getLength(); i1++)
                     {
                        if (temp1.item(i1).getNodeType() == Node.ELEMENT_NODE) {
                           NodeList temp2 = temp1.item(i1).getChildNodes();
                                for (int i2 = 0; i2 < temp2.getLength(); i2++) {
                                        if (temp2.item(i2).getNodeType() == Node.ELEMENT_NODE) {
                                            String tt = temp2.item(i2).getNodeName().replace("_", " ");
                                            //<div id="sub1" onmouseover="javascript:var mydiv = document.createElement('div'); mydiv.height = 100; mydiv.width = 100; mydiv.zindex = 1000; mydiv.innerHTML = 'Welcome!'; mydiv.position = 'absolute'; mydiv.top = 0; mydiv.left = 0;">some text</div>
                                            //String prova = "\"javascript:var mydiv = document.createElement('div'); mydiv.height = 100; mydiv.width = 100; mydiv.zindex = 1000; mydiv.innerHTML = 'Welcome!'; mydiv.position = 'absolute'; mydiv.top = '900px'; mydiv.left = '0px';\"";
                                                this.dinamicOut.println("<th class=\"rotate-45\"><div class=\"norotate\" id=\""+temp2.item(i2).getNodeName()+i2+separa+"\" style=\"display:none;\">aaaaaa</div><div id="+tt.trim()+i2+" onmouseout=nasconditip('"+temp2.item(i2).getNodeName()+i2+separa+"'); onmouseover=mostratip('"+temp2.item(i2).getNodeName()+i2+separa+"',event);><span>" + tt + "</span></div></th>");
                        }//close if
                      }//close for
                   }//close if
                 }//close for
             
                
             /*       this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Costo di esercizio(euro)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Costo di esercizio al m3(euro/m3)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Costo di investimento(euro)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Costi di trasporto(euro)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni gas serra impianti(kg)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni acide impianti(kg)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni gas serra trasporti(kg)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni acide trasporti(kg)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni serra al m3(kg/m3)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Emissioni acide al m3(kg/m3)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Energia consumata(KW)</span></div></th>");
            this.dinamicOut.println("<th class=\"rotate-45\"><div><span>Energia consumata al m3(KW/m3)</span></div></th>");*/
                                
                      this.dinamicOut.println("</tr><tr>");
                     
                      for (int i1 = 0; i1 < temp1.getLength(); i1++)
                     {
                        if (temp1.item(i1).getNodeType() == Node.ELEMENT_NODE) {
                           NodeList temp2 = temp1.item(i1).getChildNodes();
                                for (int i2 = 0; i2 < temp2.getLength(); i2++) {
                                        if (temp2.item(i2).getNodeType() == Node.ELEMENT_NODE) {
                                           
                                                this.dinamicOut.println("<td>" + temp2.item(i2).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                        }//close if
                      }//close for
                   }//close if
                 }//close for
                     
                     
             this.dinamicOut.println("</tr></table>");
             
             
        }catch(Exception ex){ex.printStackTrace();};
        
    }
    
    
    
    private void recuperaLocalizzazione(InputOutputXml leggi)
    {
       

        NodeList temp = null;
        //mi serve per recuperar eil numero delle aziende presenti nel file di risposta
        temp = leggi.cerca1("//risultati/localizzazione/azienda/cuaa",false);
        
        if(temp == null)
        {
              mostraErrore();
                     return;
        }
        System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        //valore che uso nel ciclo for per ciclare su tutte le aziende del file dei risutlati
        int numeroAziende = temp.getLength();
                
        
        
       
        System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        try {
            
            
            System.out.println(this.getClass().getCanonicalName()+ " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " numerpo aziende " + numeroAziende);
            /**
             * 
             * PRIMA DI CICLARE SULLE AZIENDE PRENDO LO STATO INIZIALE OVVERO IL RIEPILOGO
             * INIZIALE E FINALE PRODOTTO DAL SOLUTORE
             * 
             */
            this.dinamicOut.println("<h2><i>Situazione prima dell'ottimizzazione</i></h2>");
            recuperaStatoInizialeFinale("//riepilogo/iniziale",true);
            
             this.dinamicOut.println("<h2><i>Situazione dopo l'ottimizzazione</i></h2>");
            recuperaStatoInizialeFinale("//riepilogo/finale",false);
            
            
             this.dinamicOut.println("</br></br>");
            
            
            for(int i1 = 1 ; i1 <= numeroAziende;i1++)
            {
            
            temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/cuaa",true);
            this.dinamicOut.println("</br>");
            this.dinamicOut.println("<p><b>Impianto aperto presso :</b> " + temp.item(0).getNodeValue()+"</p>");
            //this.dinamicOut.println("</br>");
            //recupero la ragione sociale dell'azienda con cuaa preso dall'output xml
            String hj = this.getRagioneSociale(temp.item(0).getNodeValue());
             this.dinamicOut.println("<p><b>Ragione Sociale :</b> " + hj+"</p>");
            temp = null;
            temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/alternativa_scelta",true);
             this.dinamicOut.println("<p><b>Alternativa scelta :</b> " + temp.item(0).getNodeValue()+"</p>");
              //this.dinamicOut.println("</br>");
              //recupero la composizione dell'alternativa inteso come moduli che la compongono
              Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));
              
              String moduli ="Moduli :";
              
              while(tratt.hasNext())
              {
                  db.AlternativaTrattamento tratt1 = tratt.next();
                  int i = tratt1.getTrattamento().getId();
                  //6 e 7 sono la vasca e la platea che deono stare in fondo alla descrizione
                  if(i != 6 && i!= 7) {
                      moduli +=" | " + tratt1.getTrattamento().getNome();
                  }
              }
              
              moduli +=" | Vasca | Platea";
              
              this.dinamicOut.println("<p>"+moduli+"</p>"); 
              
            temp = null;
            temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/conferimenti/azienda_provenienza",false);
             
            String conferenti ="";
            for (int i = 0; i < temp.getLength(); i++) {
               // this.dinamicOut.println("</br>");
                if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) 
                conferenti +=  temp.item(i).getFirstChild().getNodeValue() + " --  ";
            }
            
             this.dinamicOut.println("<p><b>Aziende conferenti :</b> " + conferenti + "</p>");
             
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">Caratteristiche chimiche in ingresso all'impianto : </p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
             this.dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKNkg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
              this.dinamicOut.println("<tr><td>Liquame Bovino</td>");
           
              
             caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/bovino/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Bovino</td>");
             
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/bovino/letame"); 
              
              this.dinamicOut.println("<tr><td>Liquame Suino</td>");
            
              
               caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/suino/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Suino</td>");
             
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/suino/letame"); 
              
              
              this.dinamicOut.println("<tr><td>Liquame Avicolo</td>");
            
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/avicolo/liquame"); 
              
             this.dinamicOut.println("<tr><td>Letame Avicolo</td>");
            
             
             caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/avicolo/letame"); 
              
              
              this.dinamicOut.println("<tr><td>Liquame Altro</td>");
            
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/altro/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Altro</td>");
             
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_in/altro/letame"); 
              
              
              
              
              
             this.dinamicOut.println("</table>");
             
             
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">Caratteristiche chimiche in uscita dall'impianto : </p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
             this.dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKNkg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
              this.dinamicOut.println("<tr><td>Liquame Bovino</td>");
           
              
             caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/bovino/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Bovino</td>");
             
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/bovino/letame"); 
              
              this.dinamicOut.println("<tr><td>Liquame Suino</td>");
            
              
               caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/suino/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Suino</td>");
             
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/suino/letame"); 
              
              
              this.dinamicOut.println("<tr><td>Liquame Avicolo</td>");
            
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/avicolo/liquame"); 
              
             this.dinamicOut.println("<tr><td>Letame Avicolo</td>");
            
             
             caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/avicolo/letame"); 
              
              
              this.dinamicOut.println("<tr><td>Liquame Altro</td>");
            
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/altro/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Altro</td>");
             
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/altro/letame"); 
              
              
               this.dinamicOut.println("<tr><td>Liquame Biomasse</td>");
            
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/biomassa/liquame"); 
              
              this.dinamicOut.println("<tr><td>Letame Biomasse</td>");
             
              
              caratteristichechimiche("//risultati/localizzazione/azienda["+i1+"]/caratteristiche_chimiche_out/biomassa/letame"); 
              
                            
             this.dinamicOut.println("</table>");
             
             String pattern1 = "(\\w*)([\\.,](\\w*))";
             
             
             //EMISSIONI AGRO
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable4\">EMISSIONI AGRO</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable4\">");
             this.dinamicOut.println("<thead><tr><th>NH3(kg/ha)</th><th>LEACH(kg/ha)</th><th>DRAINAGE(mm)</th></tr></thead><tr>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni_agro/nh3",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             //mi tengo da parte nh3_agro per sottrarlo all nh3 delle emissioni
             double nh3_agro = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni_agro/drainage",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni_agro/leach",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             
             
             
             
             //EMISSIONI
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable4\">EMISSIONI</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable4\">");
             this.dinamicOut.println("<thead><tr><th>NH3(kg)</th><th>CH4(kg)</th><th>CO2(kg)</th><th>N20(kg)</th><th>N2(kg)</th><th>NO(Kg)</th></tr></thead>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_nh3_impianto",true);
             double nh3 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             //nh3 = nh3 - nh3_agro;
             this.dinamicOut.println("<td>"+ nh3 +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_ch4_impianto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_co2_impianto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_n2o_impianto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_n2_impianto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_no_impianto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              //temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/emissioni/emissioni_trasporto_totali",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             
             
             
             
             //COSTI
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable4\">COSTI</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable4\">");
             this.dinamicOut.println("<thead><tr><th>Investimento(euro)</th><th>Gestione netto(euro/a)</th><th>Trasporto(euro/a)</th><th>Costo prevasche(euro/a)</th><th>Costo lordo esercizio impianti(euro/a)</th><th>Ricavo lordo energia impianti(euro/a)</th></tr></thead>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_investimento_impianti",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_esercizio_impianti",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              //temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_totale_impianti",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_trasporto",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_prevasche",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_netto_esercizio_impianti",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/ricavo_netto_energia_impianti",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             /* temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_esercizio_impianti",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");*/
             
              //temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/costi/costo_totale_finale",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             //ENERGIA
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable4\">ENERGIA</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable4\">");
             this.dinamicOut.println("<thead><tr><th>Energia Prodotta(KWh)</th><th>Energia consumata(KWh)</th></tr></thead>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/energia/energia_prodotta",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/energia/energia_consumata",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             
             
             
             this.dinamicOut.println("<br/>"); 
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">VALORI AL METRO CUBO</p>"); 
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
            
             //lo uso per le emissioni,costi , energie dei valori al metro cubo per mostrare due cifre decimali
             DecimalFormat dformat = new DecimalFormat("#0.00");
            
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
             this.dinamicOut.println("<thead><tr><th>Ch4(Kg/m<sup>3</sup>)</th><th>Nh3(Kg/m<sup>3</sup>)</th><th>N2(Kg/m<sup>3</sup>)</th><th>N2O(Kg/m<sup>3</sup>)</th><th>CO2(Kg/m<sup>3</sup>)</th><th>NO(Kg/m<sup>3</sup>)</th></tr></thead><tr>");
            // temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/prodotta",true);
               temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/ch4",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
               double ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             
             //System.out.println("ch4 in cercasinolamulti " + temp.item(0).getNodeValue());
             //temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/consumata",true);
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/nh3",true);
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/n2",true);
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/n2o",true);
             ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/co2",true);
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/no",true);
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
                this.dinamicOut.println("<br/>"); 
              //VALORI AL METRO CUBO costi
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
             this.dinamicOut.println("<thead><tr><th>Costo netto esercizio impianti(euro/a * m<sup>3</sup>)</th><th>Costo lordo esercizio impianti(euro/a * m<sup>3</sup>)</th><th>Ricavo lordo esercizio impianti(euro/a * m<sup>3</sup>)</th><th>Costo trasporti verso impianto(euro/a * m<sup>3</sup>)</th><th>Costo prevasche(euro/a * m<sup>3</sup>)</th><th>Costo totale finale annuo(euro/a * m<sup>3</sup>)</th></tr></thead><tr>");
            // temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/prodotta",true);
               temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/costo_esercizio_impianti",true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              this.dinamicOut.println("<td>"+dformat.format(ddfor) +"</td>");
             //temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/consumata",true);
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/costo_netto_esercizio_impianti",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/ricavo_netto_energia_impianti",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
             
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/costo_trasporti_verso_impianto",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
             
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/costo_prevasche",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
             
             
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/costo_totale_finale_annuo",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
              ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
             
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             
              this.dinamicOut.println("<br/>");
              //VALORI AL METRO CUBO energia
             this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
             this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
             this.dinamicOut.println("<thead><tr><th>Energia Prodotta(KWh/m<sup>3</sup>)</th><th>Energia ConsumataKWh/m<sup>3</sup>)</th></tr></thead><tr>");
            // temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/prodotta",true);
               
             
             //temp = leggi.cerca1("//risultati/singola_azienda/alternativa["+i1+"]/totali/energia/consumata",true);
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/energia_prodotta",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
               ddfor = Double.parseDouble(temp.item(0).getNodeValue());
              this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
              temp = leggi.cerca1("//risultati/localizzazione/azienda["+i1+"]/valori_al_metro_cubo/energia_consumata",true);
             //this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
               ddfor = Double.parseDouble(temp.item(0).getNodeValue());
             this.dinamicOut.println("<td>"+dformat.format(ddfor)+"</td>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</table>");
             
             
             // mostraSurplus(leggi, padre,alternativa);
             
             
             this.dinamicOut.println("<hr>");
             this.dinamicOut.println("<hr>");
             
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
      

       
    }
    
    private String getRagioneSociale(String cuaa)
    {
        
         DettaglioCuaa detto = new DettaglioCuaa();
          boolean utenteospite = false;
          
          if(!detto.getUtente().equals("azienda1"))
          {
              utenteospite = true;
          }
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
         
         /**
          * prendo l'alternativa indicata dal numero i e di questa alternativa prendo i trattamentoi che la compongono
          * */
             Query q;
            if(utenteospite)
            {
             q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.cuaaFinto=?1");
             q.setParameter(1, cuaa);
            }
            else
            {
             q = entityManager.createQuery("SELECT a FROM AziendeI a WHERE a.cuaa=?1");
             q.setParameter(1, cuaa);
            }
        
         
          List<db.AziendeI> results = q.getResultList();
          
          String ritorno = "";
          
            //Connessione.getInstance().chiudi();
            if(utenteospite)
                ritorno = results.get(0).getRagioneSocialeFinto();
            else
                ritorno = results.get(0).getRagioneSociale();
            
            
            
          return ritorno;
    }
    
    private Iterator<db.AlternativaTrattamento> composizioneAlternative1(int i)
     {
         //String ritorno = "";
        
         
          
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
         
         /**
          * prendo l'alternativa indicata dal numero i e di questa alternativa prendo i trattamentoi che la compongono
          * */
          Query q = entityManager.createQuery("SELECT a FROM AlternativeS a WHERE a.id=?1");
          q.setParameter(1, i);
          List<db.AlternativeS> results = q.getResultList();
          
          Iterator<db.AlternativaTrattamento> tt = results.get(0).getAlternativaTrattamentoCollection().iterator();
          
          //Connessione.getInstance().chiudi();
         
        return tt;
        
          
          
     }
    
    /**
     * recstituisce un iteratore ai moduli di trattamento che compongono
     * l'alternativa identificata dal numero contenuto in i
     * @param i
     * @return 
     */
     private Iterator<db.AlternativaTrattamento> composizioneAlternative(int i)
     {
         //String ritorno = "";
          Application app = FacesContext.getCurrentInstance().getApplication();
          HtmlOutputText  text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
          text.setValue("Composizione :");
           text.setStyle("font-weight:bold;");
          dynamicGrid.getChildren().add(text);
          
          
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
         
         /**
          * prendo l'alternativa indicata dal numero i e di questa alternativa prendo i trattamentoi che la compongono
          * */
          Query q = entityManager.createQuery("SELECT a FROM AlternativeS a WHERE a.id=?1");
          q.setParameter(1, i);
          List<db.AlternativeS> results = q.getResultList();
          
          Iterator<db.AlternativaTrattamento> tt = results.get(0).getAlternativaTrattamentoCollection().iterator();
          
            //Connessione.getInstance().chiudi();
         
        return tt;
        
          
          
     }
     
     private void recuperaSingola2(InputOutputXml leggi,boolean cache,String padre,String alternativa) {
         
         
          if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
         
         
         
         if(cache)
                dynamicGrid.getChildren().clear();

       NodeList temp = null;

        Application app = FacesContext.getCurrentInstance().getApplication();
        
        
        HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Alternativa " + alternativa);
        text.setStyle("font-weight:bold;font-size:34;");
        //text.setStyle("background-color:lightblue;");
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
        
        /**
         * nel caso di selezione di singola alternativa la variabile alternativa è vuota
         * */
        if(alternativa.length() != 0){
            Iterator<db.AlternativaTrattamento> tt =composizioneAlternative(Integer.parseInt(alternativa));
        
        int l = 0;
          while(tt.hasNext())
          {
              db.AlternativaTrattamento tt1 = tt.next();
              String tt2= tt1.getTrattamento().getNome();
              text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
              text.setValue(tt2);
              dynamicGrid.getChildren().add(text);
              l++;
          }
          
          l = l + 1;
          this.aggiungiNuovaLinea(l++);
        }
        
        
        
        
       // this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
                
        
        
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setStyle("font-weight:bold;");
        text.setValue("Caratterisitiche chimiche");
        dynamicGrid.getChildren().add(text);
        
        this.aggiungiNuovaLinea(1);
        this.aggiungiNuovaLinea(0);
       
        

        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/bovino/liquame",true);
        if(temp == null)
            return;
        //System.out.println(" query xpath    ------ : " + padre+"/totali/caratteristichechimiche/bovino/liquame dimesione chiavi " + temp.keySet().size());
        //leggi.stampa(temp);

        this.creaHeaderGrid(temp, "Tipologia",0);
        this.aggiungiValoriGrid(temp, "Liquame bovino");
        //leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/bovino/letame",true);
        this.aggiungiValoriGrid(temp, "Letame bovino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/suino/liquame",true);
        this.aggiungiValoriGrid(temp, "Liquame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/suino/letame",true);
        this.aggiungiValoriGrid(temp, "Letame suino");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/avicolo/liquame",true);
        this.aggiungiValoriGrid(temp, "Liquame avicolo");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/avicolo/letame",true);
        this.aggiungiValoriGrid(temp, "Letame avicolo");
        //leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/altro/liquame",true);
        this.aggiungiValoriGrid(temp, "Liquame altro");
        // leggi.stampa(temp);
        temp = leggi.cerca1(padre+"/totali/caratteristichechimiche/altro/letame",true);
        this.aggiungiValoriGrid(temp, "Letame altro");


         this.aggiungiNuovaLinea(0);
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Emissioni");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1);
          temp =leggi.cerca1(padre+"/totali/emissioni",true);
         this.creaHeaderGrid(temp, "",1);
         this.aggiungiNuovaLinea(6);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(6);
          
           this.aggiungiNuovaLinea(0);
         
         
           
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Energia");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca1(padre+"/totali/energia",true);
         this.creaHeaderGrid(temp, "",2);
          this.aggiungiNuovaLinea(4);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(4);
         
          this.aggiungiNuovaLinea(0);
          
          
          
        text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
        text.setValue("Costi");
        dynamicGrid.getChildren().add(text);
         this.aggiungiNuovaLinea(1); 
         temp =leggi.cerca1(padre+"/totali/costi",true);
         this.creaHeaderGrid(temp, "",3);
          this.aggiungiNuovaLinea(3);
         this.aggiungiValoriGrid(temp, "");
          this.aggiungiNuovaLinea(3);
         
          this.aggiungiNuovaLinea(0);
                      // leggi.stampa(temp);
                     //temp =leggi.cerca("//totali/costi");*/
                      // leggi.stampa(temp);*/
         //Connessione.getInstance().chiudi();
    }
     
      /**
      * per ogni alternativa il dettaglio iniziale da mostrare è una tabella contenente
      * emissioni a = nh3 , emissioni g = ch4 + co2 + n2o , energia , costo ,surlplus calcoalto come differenza
      * tra stoccaggi e rimozione azoto come rapporto su tutto
      * @param leggi
      * @param padre
      * @param alternativa 
      */
     private void recuperaDatiAnalisi1(InputOutputXml leggi,String padre,String alternativa)
     {
             NodeList temp = null;
             
             
              temp = leggi.cerca1(padre+"/scelta",true);
          
           
             
              //recupero la composizione dell'alternativa inteso come moduli che la compongono
              Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));
              
              String moduli ="";
              
              while(tratt.hasNext())
              {
                  db.AlternativaTrattamento tratt1 = tratt.next();
                  int i = tratt1.getTrattamento().getId();
                  if(i != 6 && i != 7) {
                      moduli +=" | " + tratt1.getTrattamento().getNome();
                  }
              }
              
              moduli += " | Vasca | Platea";
           
          
             
         
             String pattern1 = "(\\w*)([\\.,](\\w*))";
             
             try{
                 
             this.dinamicOut.println("<tr>");
             //numero alternativa
             this.dinamicOut.println("<td>"+alternativa+"</td>");
             //descrizione
             this.dinamicOut.println("<td>"+moduli+"</td>");
             //emissioni acide
              temp = leggi.cerca1(padre+"/totali/emissioni/nh3",true);
             double nh3 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double em2 = nh3  ;
             this.dinamicOut.println("<td>"+em2+"</td>");
             //emissioni gas serra
             temp = leggi.cerca1(padre+"/totali/emissioni/ch4",true);
             double ch4 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/co2",true);
             double co2 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/n2o",true);
             double n2o = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/no",true);
             double no = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double em1 = ch4 + co2 + n2o + no; 
             this.dinamicOut.println("<td>"+em1+"</td>");
             //this.dinamicOut.println("<td>"+this.pesoenergia+"</td><td>"+this.pesoemissionia+"</td><td>"+this.pesoemissionis+"</td><td>"+this.pesocosti+"</td>");
              temp = leggi.cerca1(padre+"/totali/energia/consumata",true);
              double econsumata = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              temp = leggi.cerca1(padre+"/totali/energia/prodotta",true);
              double eprodotta = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              double tt = econsumata - eprodotta;
             this.dinamicOut.println("<td>"+tt +"</td>");
             //costi         
             temp = leggi.cerca1(padre+"/totali/costi/gestione",true);
             this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             
             
             //gestione surplus : devo leggere il modulo stoccaggi ed il modulo rimozione azoto e fare la somma su liquami , letami su tutto
             //poi fare il rapporto 
             
             
              
               temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche",true);
              double[][] datistoccaggi = testLetturaSurplus(temp);
               double totAzotoStoccaggi  =0;
                 double totAzotoRimozione = 0;
              //i sono le righe k le colonne
              for(int i = 2; i < 4; i++)
              {
                  for(int k = 0;k < 5;k++)
                  {
                      totAzotoStoccaggi +=datistoccaggi[i][k]  ;
                  }
              }
              
              NodeList temp1 = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche",true);
              double[][] datirimozione = testLetturaSurplus(temp1);
              
            
               //i sono le righe k le colonne
              for(int i = 2; i < 4; i++)
              {
                  for(int k = 0;k < 5;k++)
                  {
                      totAzotoRimozione += datirimozione[i][k]  ;
                  }
              }
              
             
             
          
              double rapporto = 0;
           
             if(totAzotoStoccaggi != 0 && (totAzotoStoccaggi - totAzotoRimozione)!=0) {
                     rapporto = ((totAzotoStoccaggi - totAzotoRimozione) / totAzotoStoccaggi) * 100;
                 }
              
             // System.out.println("totAzotoStoccaggi " + totAzotoStoccaggi + " totAzotoRimozione " + totAzotoRimozione + " rapporto " + rapporto);
               //lo uso per dare un formato piu corto al double
             DecimalFormat dformat = new DecimalFormat("#0.0");        
              this.dinamicOut.println("<td>"+dformat.format(rapporto)+"</td>"); 
            
             this.dinamicOut.println("</tr>"); 
             
             }catch(Exception ex){ex.printStackTrace();};
     }
     
     
     /**
      * per ogni alternativa il dettaglio iniziale da mostrare è una tabella contenente
      * emissioni a = nh3 , emissioni g = ch4 + co2 + n2o , energia , costo ,surlplus calcoalto come differenza
      * tra stoccaggi e rimozione azoto come rapporto su tutto
      * @param leggi
      * @param padre
      * @param alternativa 
      */
     private void recuperaDatiAnalisi1m3(InputOutputXml leggi,String padre,String alternativa,Alternativa alter)
     {
             NodeList temp = null;
             
             
              temp = leggi.cerca1(padre+"/scelta",true);
          
           
             
              //recupero la composizione dell'alternativa inteso come moduli che la compongono
              Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));
              
              String moduli ="";
              List<String> composizione = new LinkedList<String>();
              
              while(tratt.hasNext())
              {
                  db.AlternativaTrattamento tratt1 = tratt.next();
                  int i = tratt1.getTrattamento().getId();
                  if(i != 6 && i != 7)
                  {
                      moduli +=" | " + tratt1.getTrattamento().getNome();
                      composizione.add(tratt1.getTrattamento().getNome());
                  }
              }
              
              moduli +=" | Vasca | Platea";
              composizione.add("Vasca"); 
              composizione.add("Platea"); 
              
           alter.setComposizione(composizione);
          
             
         
             String pattern1 = "(\\w*)([\\.,](\\w*))";
             
             try{
                 
           /*  this.dinamicOut.println("<tr>");
             //numero alternativa
             this.dinamicOut.println("<td>"+alternativa+"</td>");
             //descrizione
             this.dinamicOut.println("<td>"+moduli+"</td>");*/
             //emissioni acide
              temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/nh3",true);
              
             // System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " nh3 prima del parse " + temp.item(0).getNodeValue());
             //double nh3 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              double nh3 = Double.parseDouble(temp.item(0).getNodeValue());
             //System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+ " nh3 dopo il parse " + nh3);
             double em2 = nh3  ;
             
             alter.setNh3_m3(nh3);
             
            // this.dinamicOut.println("<td>"+em2+"</td>");
             //emissioni gas serra
             temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/ch4",true);
             //double ch4 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double ch4 = Double.parseDouble(temp.item(0).getNodeValue());
             temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/co2",true);
             //double co2 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double co2 = Double.parseDouble(temp.item(0).getNodeValue());
             temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/n2o",true);
             //double n2o = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double n2o = Double.parseDouble(temp.item(0).getNodeValue());
             temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/no",true);
             //double no = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double no = Double.parseDouble(temp.item(0).getNodeValue());
             
             alter.setCh4_m3(ch4);
             alter.setCo2_m3(co2);
             alter.setN2o_m3(n2o);
             alter.setNo_m3(no);
             
             //double em1 = ch4 + co2 + n2o + no; 
             //this.dinamicOut.println("<td>"+em1+"</td>");
             //this.dinamicOut.println("<td>"+this.pesoenergia+"</td><td>"+this.pesoemissionia+"</td><td>"+this.pesoemissionis+"</td><td>"+this.pesocosti+"</td>");
              temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/energia_consumata",true);
              //double econsumata = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              double econsumata = Double.parseDouble(temp.item(0).getNodeValue());
              temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/energia_prodotta",true);
              //double eprodotta = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              double eprodotta = Double.parseDouble(temp.item(0).getNodeValue());
              double tt = econsumata - eprodotta;
              
              alter.setEnergia_consumata_m3(econsumata);
              alter.setEnergia_prodotta_m3(eprodotta);
              
              
            // this.dinamicOut.println("<td>"+tt +"</td>");
             //costi         
             //temp = leggi.cerca1(padre+"/totali/valori_al_metro_cubo/gestione",true);
            // this.dinamicOut.println("<td>"+temp.item(0).getNodeValue().replaceAll(pattern1, "$1") +"</td>");
             
             alter.setGestione_m3(eprodotta);
            
            
            // this.dinamicOut.println("</tr>"); 
             
             }catch(Exception ex){ex.printStackTrace();};
     }
     
     
     
     /**
      * per ogni alternativa il dettaglio iniziale da mostrare è una tabella contenente
      * emissioni a = nh3 , emissioni g = ch4 + co2 + n2o , energia , costo ,surlplus calcoalto come differenza
      * tra stoccaggi e rimozione azoto come rapporto su tutto
      * @param leggi
      * @param padre
      * @param alternativa 
      */
     private void recuperaDatiAnalisi(InputOutputXml leggi,String padre,String alternativa)
     {
             
         /*ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         DettaglioAzienda  dettaA = ( DettaglioAzienda) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioAzienda");
         
         Refluo liquameSuino = new Refluo("Liquame Suino");
         liquameSuino.setMetricubi(100);
         liquameSuino.setAzotoammoniacale(230);
         dettaA.getListaCaratteristicheLiqXml().add(liquameSuino);*/
        
          
         NodeList temp = null;
         DecimalFormat dformat = new DecimalFormat("#0,0");  
         DecimalFormat dformat1 = new DecimalFormat("###.#");  
             
              temp = leggi.cerca1(padre+"/scelta",true);
          
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() +" quiiiiiiii");
             
              //recupero la composizione dell'alternativa inteso come moduli che la compongono
              Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));
              
              String moduli ="";
              
              while(tratt.hasNext())
              {
                  db.AlternativaTrattamento tratt1 = tratt.next();
                  moduli +=" | " + tratt1.getTrattamento().getNome();
              }
              
           try{
           this.dinamicOut.println("<tbody id='form:tablexml:0:tb' class='rf-dt-b'>\n"
                                + "<tr id='form:tablexml:0' class='rf-dt-r rf-dt-fst-r'>\n");
           }catch(Exception ex){ex.printStackTrace();}
         
             String pattern1 = "(\\w*)([\\.,](\\w*))";
             
             try{
                 
             this.dinamicOut.println("<tr>");
             //numero alternativa
              this.dinamicOut.println( "<td id='form:tablexml:0:j_idt257' class='rf-dt-c'>"+alternativa+"</td>");
            // this.dinamicOut.println("<td>"+alternativa+"</td>");
             //descrizione
             this.dinamicOut.println("<td id='form:tablexml:0:j_idt260' class='rf-dt-c'>"+moduli+"</td>");
             //emissioni acide
              temp = leggi.cerca1(padre+"/totali/emissioni/nh3",true);
             double nh3 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double em2 = nh3  ;
            
             this.dinamicOut.println("<td id='form:tablexml:0:j_idt263' class='rf-dt-c'>"+dformat1.format(em2)+"</td>");
             //emissioni gas serra
             temp = leggi.cerca1(padre+"/totali/emissioni/ch4",true);
             double ch4 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/co2",true);
             double co2 = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/n2o",true);
             double n2o = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             temp = leggi.cerca1(padre+"/totali/emissioni/no",true);
             double no = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             double em1 = ch4 + co2 + n2o + no; 
            
             this.dinamicOut.println("<td id='form:tablexml:0:j_idt266' class='rf-dt-c'>"+dformat1.format(em1)+"</td>");
             //this.dinamicOut.println("<td>"+this.pesoenergia+"</td><td>"+this.pesoemissionia+"</td><td>"+this.pesoemissionis+"</td><td>"+this.pesocosti+"</td>");
              temp = leggi.cerca1(padre+"/totali/energia/consumata",true);
              double econsumata = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              temp = leggi.cerca1(padre+"/totali/energia/prodotta",true);
              double eprodotta = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
              double tt = econsumata - eprodotta;
            
             this.dinamicOut.println("<td id='form:tablexml:0:j_idt269' class='rf-dt-c'>"+dformat1.format(tt) +"</td>");
             //costi         
             temp = leggi.cerca1(padre+"/totali/costi/gestione",true);
             double costo_gestione = Double.parseDouble(temp.item(0).getNodeValue().replaceAll(pattern1, "$1"));
             this.dinamicOut.println("<td id='form:tablexml:0:j_idt272' class='rf-dt-c'>"+dformat1.format(costo_gestione) +"</td>");
             
             
             //gestione surplus : devo leggere il modulo stoccaggi ed il modulo rimozione azoto e fare la somma su liquami , letami su tutto
             //poi fare il rapporto 
             
             double totAzotoStoccaggi = totaleAzoto(leggi,padre,"stoccaggi");
             
              double totAzotoRimozione = totaleAzoto(leggi,padre,"rimozioneazoto");
             
              double rapporto = ((totAzotoStoccaggi - totAzotoRimozione) / totAzotoStoccaggi) * 100;
               //lo uso per dare un formato piu corto al double
                   
              this.dinamicOut.println("<td id='form:tablexml:0:j_idt275' class='rf-dt-c'>"+dformat.format(rapporto)+"</td>"); 
            
             this.dinamicOut.println("</tr></tbody>"); 
             
             }catch(Exception ex)
             {
                 ex.printStackTrace();
             };
     }
     
     
       /**
      * crea l'header della tabella del dettaglio iniziale da mostrare prima del mostra dettaglio
      */
     private void chiudiHeadertable()
     {
         try{
          this.dinamicOut.println("</table>");
         }catch(Exception ex)
                {
                    ex.printStackTrace();
                };
     }
     
    
      /**
      * crea l'header della tabella del dettaglio iniziale da mostrare prima del mostra dettaglio
      * @param tipo se 0 crea una tabella con i th che contengono anche il numero delle volte che comapare un alternativa 
      * ed i pesidi quese alternative sono i pesi medi se tipo è 1 invece crea la tabella per il contenimento
      * di tutti i pesi e per ogni chiave peso ci sono le alternativa ordinate
      */
     private void apriHeadertablemulti(int tipo)
     {
         try{
         
          switch(tipo) 
         {
             case 0 :
                 this.dinamicOut.println("<div id=\"labelprima\"><h3>Prima analisi  :</h3>di seguito le alternative che hanno avuto maggiore successo su tutti i pesi (vengono mostrati i pesi medi)</div>");
                this.dinamicOut.println("<table class=\"localizzazioneTable3\"><thead><tr><th># Alt.</th><th>Descrizione</th><th># volte</th><th>P Em A</th><th>P Em G</th><th>P Energia</th><th>P Costo</th><th>Em A</th><th>Em G</th><th>Energia</th><th>Costo</th></tr></thead>");

                 break;
             case 1:
                this.dinamicOut.println("<table class=\"localizzazioneTable4\"><thead><tr><th># Alt.</th><th>Descrizione</th><th>P Em A</th><th>P Em G</th><th>P Energia</th><th>P Costo</th><th>Em A</th><th>Em G</th><th>Energia</th><th>Costo</th></tr></thead>");
    
                 break;
            case 2:
                this.dinamicOut.println("<br/><br/><div id=\"labelterza\"><h2>Terza Analisi</h2></p><br/><p><h3>Tabella contenente per ogni combinazione di pesi l'alternativa migliore</h3></div>");
                this.dinamicOut.println("<table class=\"localizzazioneTable5\"><thead><tr><th># Alt.</th><th>Descrizione</th><th>P Em A</th><th>P Em G</th><th>P Energia</th><th>P Costo</th><th>Em A</th><th>Em G</th><th>Energia</th><th>Costo</th></tr></thead>");
    
                 break;     
         }
         }catch(Exception ex)
                {
                    ex.printStackTrace();
                };
     }
     
     /**
      * crea l'header della tabella del nella nuova modalita usando le classe RichFAces come il dettaglioaziendale
      */
     private void apriHeadertableRichAlt()
     {
         try {
             this.dinamicOut.println("<p><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul></p>");
             this.dinamicOut.println("<table id='form:tablexmlA' class='rf-dt' style='width:700px;'>");
             this.dinamicOut.println("<colgroup span='8'></colgroup>");
             this.dinamicOut.println("<thead id='form:tablexmlA:th' class='rf-dt-thd'>");
             this.dinamicOut.println("<tr id='form:tablexmlA:ch' class='rf-dt-shdr'>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt150' class='rf-dt-shdr-c' scope='col'>Tipo</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt157' class='rf-dt-shdr-c' scope='col'>Volume</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt160' class='rf-dt-shdr-c' scope='col'>TKN</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt163' class='rf-dt-shdr-c' scope='col'>TAN</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt166' class='rf-dt-shdr-c' scope='col'>DM</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt169' class='rf-dt-shdr-c' scope='col'>VS</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt172' class='rf-dt-shdr-c' scope='col'>K</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt175' class='rf-dt-shdr-c' scope='col'>P</th>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</thead>");
         }catch(Exception ex)
                {
                    ex.printStackTrace();
                };
     }
     
     
     /**
      * crea l'header della tabella del nella nuova modalita usando le classe RichFAces come il dettaglioaziendale
      */
     private void apriHeadertableRichAnalisi()
     {
         try {
             this.dinamicOut.println("<p><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul>");
             this.dinamicOut.println("<table id='form:tablexml' class='rf-dt' style='width:700px;'>");
             this.dinamicOut.println("<colgroup span='8'></colgroup>");
             this.dinamicOut.println("<thead id='form:tablexml:th' class='rf-dt-thd'>");
             this.dinamicOut.println("<tr id='form:tablexml:ch' class='rf-dt-shdr'>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt250' class='rf-dt-shdr-c' scope='col'># Alt.</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt257' class='rf-dt-shdr-c' scope='col'>Descrizione</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt260' class='rf-dt-shdr-c' scope='col'>Em A</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt263' class='rf-dt-shdr-c' scope='col'>Em G</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt266' class='rf-dt-shdr-c' scope='col'>Energia</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt269' class='rf-dt-shdr-c' scope='col'>Costo</th>");
             this.dinamicOut.println("<th id='form:tablexml:j_idt272' class='rf-dt-shdr-c' scope='col'>% Surplus</th>");             
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</thead>");
         }catch(Exception ex)
                {
                    ex.printStackTrace();
                };
     }
     /**
      * crea l'header della tabella del dettaglio iniziale da mostrare prima del mostra dettaglio
      */
     private void apriHeadertable()
     {
         try{
          this.dinamicOut.println("<p><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul>");    
          this.dinamicOut.println("<table class=\"localizzazioneTable3\"><thead><tr><th># Alt.</th><th>Descrizione</th><th>Em A</th><th>Em G</th><th>Energia</th><th>Costo</th><th>% surplus</th></tr></thead>");
         }catch(Exception ex)
                {
                    ex.printStackTrace();
                };
     }
     
     
     
     
     
     private void recuperaSingola2multi(InputOutputXml leggi,boolean cache,String padre,String alternativa)  {
         
      
         
        // System.out.println("+++++++++++++"+padre+"++++++++++++++" + alternativa);
         
         
         
         NodeList temp = null;
        //mi serve per recuperar eil numero delle aziende presenti nel file di risposta
        temp = leggi.cerca1("//risultati/singola_azienda/alternativa",false);
        
        if(temp == null)
        {
              mostraErrore();
                     return;
        }
       // System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        //valore che uso nel ciclo for per ciclare su tutte le aziende del file dei risutlati
        int numeroAziende = temp.getLength();
                
        
        
       
        //System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        try {
            
            
            //System.out.println(this.getClass().getCanonicalName()+ " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " numerpo aziende " + numeroAziende);
            
            
          
            {
            
           
                temp = leggi.cerca1(padre + "/scelta", true);
                this.dinamicOut.println("<a class=\"labellocalizzazioneTable\" href=\"#linkrapidi\">Torna su</a>");
                this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>Alternativa scelta : <a name=\"" + temp.item(0).getNodeValue() + "\">" + temp.item(0).getNodeValue() + "</a></b></p>");

                //recupero la composizione dell'alternativa inteso come moduli che la compongono
                Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));

                String moduli = "Moduli :";

                while (tratt.hasNext()) {
                    db.AlternativaTrattamento tratt1 = tratt.next();
                    int i = tratt1.getTrattamento().getId();
                    if(i != 6 && i!=7) {
                        moduli += " | " + tratt1.getTrattamento().getNome();
                    }
                }
                moduli +=" | Vasca | Platea";
                this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>" + moduli + "</b></p>");



                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">Caratteristiche chimiche : </p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(kg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
                this.dinamicOut.println("<tr><td>Liquame Bovino</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/bovino/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Bovino</td>");




                caratteristichechimiche(padre + "/totali/caratteristichechimiche/bovino/letame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Liquame Suino</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/suino/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Suino</td>");


                caratteristichechimiche(padre + "/totali/caratteristichechimiche/suino/letame");
                this.dinamicOut.println("</tr>");

                this.dinamicOut.println("<tr><td>Liquame Avicolo</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/avicolo/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Avicolo</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/avicolo/letame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Liquame Altro</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/altro/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Altro</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/altro/letame");
                this.dinamicOut.println("</tr>");


                this.dinamicOut.println("<tr><td>Liquame Biomasse</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/biomasse/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Biomasse</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/biomasse/letame");
                this.dinamicOut.println("</tr>");



                this.dinamicOut.println("</table>");




                String pattern1 = "(\\w*)([\\.,](\\w*))";
                
                //EMISSIONI AGRO
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI AGRO</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>NH3(kg/ha)</th><th>DRAINAGE(mm)</th><th>LEACH(kg/ha)</th></tr></thead>");

                this.dinamicOut.println("<tr>");
                caratteristichechimiche(padre + "/totali/emissioni_agro");
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");
                
                
                
                
                //EMISSIONI
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>CH4(kg)</th><th>NH3(kg)</th><th>N2(kg)</th><th>N20(kg)</th><th>CO2(kg)</th><th>No(kg)</th></tr></thead>");

                this.dinamicOut.println("<tr>");
                /*temp = leggi.cerca1(padre + "/totali/emissioni/nh3", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/ch4", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/co2", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/n2o", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/n2", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/emissioni/no", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                
               caratteristichechimiche(padre + "/totali/emissioni");
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");


                //COSTI
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Investimento(euro)</th><th>Gestione Netto(euro/a)</th><th>Costo Lordo gestione(euro/a)</th><th>Ricavo Lordo energia(euro/a)</th><th>Ricavo ammonio(euro)</th></tr></thead>");
                this.dinamicOut.println("<tr>");

                /*temp = leggi.cerca1(padre + "/totali/costi/investimento", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/costi/gestione", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/costi/costo_netto_gestione", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/costi/ricavo_netto_energia", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                 caratteristichechimiche(padre + "/totali/costi");

                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");

                //ENERGIA
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Energia Prodotta(KWh)</th><th>Energia consumata(KWh)</th><th>Energia Venduta(KWh)</th></tr></thead><tr>");

                /*temp = leggi.cerca1(padre + "/totali/energia/prodotta", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/energia/consumata", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/energia/venduta", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                
                 caratteristichechimiche(padre + "/totali/energia");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");


                this.dinamicOut.println("<br/>");
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">VALORI AL METRO CUBO</p>");
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");

                //lo uso per le emissioni,costi , energie dei valori al metro cubo per mostrare due cifre decimali
                DecimalFormat dformat = new DecimalFormat("#0.00");

                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
               // this.dinamicOut.println("<thead><tr><th>Ch4(Kg/m<sup>3</sup>)</th><th>Nh3(Kg/m<sup>3</sup>)</th><th>N2(Kg/m<sup>3</sup>)</th><th>N2o(Kg/m<sup>3</sup>)</th><th>Co2(Kg/m<sup>3</sup>)</th><th>No(Kg/m<sup>3</sup>)</th></tr></thead><tr>");

                /*temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/ch4", true);

                double ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");


                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/nh3", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/n2", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/n2o", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/co2", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/no", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
                
                 caratteristichechimicheMC(padre + "/totali/valori_al_metro_cubo",dformat);
                
                //this.dinamicOut.println("</tr>");
               // this.dinamicOut.println("</table>");
                //this.dinamicOut.println("<br/>");
                //VALORI AL METRO CUBO costi
              //  this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                //this.dinamicOut.println("<thead><tr><th>Gestione Netto(euro/a*m<sup>3</sup>)</th><th>Costo lordo gestione(euro/a*m<sup>3</sup>)</th></tr></thead><tr>");

               /* temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/gestione", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());

                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/costo_netto_gestione", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
                 
                //this.dinamicOut.println("</tr>");
                //this.dinamicOut.println("</table>");

                this.dinamicOut.println("<br/>");
                //VALORI AL METRO CUBO energia
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
               // this.dinamicOut.println("<thead><tr><th>Ricavo netto energia(euro/a*m<sup>3</sup>)</th><th>Energia Prodotta(KWh/m<sup>3</sup>)</th><th>Energia Consumata(KWh/m<sup>3</sup>)</th></tr></thead><tr>");

               /* temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/ricavo_netto_energia", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());

                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/energia_prodotta", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/energia_consumata", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
               // this.dinamicOut.println("</tr>");
               // this.dinamicOut.println("</table>");


                /**
                 * stampo il surplus cioè vado a leggere l'output degli
                 * stoccaggi e l'ouput della rimozione e faccio la differenza
                 */
                mostraSurplus1(leggi, padre, alternativa);
            
            
            

                this.dinamicOut.println("<hr>");
                this.dinamicOut.println("<hr>");
             
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
        // Connessione.getInstance().chiudi();
    }
    //interroga l'xml di output e crea un contenuitorereflui che contiene  le caratteristiche chimiche del
     //refluo in output dal solutore
    private ContenitoreReflui recuperaRefluo(InputOutputXml leggi)
    {
       double m3 = 0;
       double at = 0;
       double am = 0;
       double ss = 0;
       double sv = 0;
       double ft = 0;
       double pt = 0;
       
       Refluo ref = null;
       
       ContenitoreReflui contenitore = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
       
       for(String tipologia:contenitore.getTipologie())
       {
       ref = new Refluo(tipologia);
       
       String[] pezzi_tipologia = tipologia.split(" ");
       pezzi_tipologia[0] = pezzi_tipologia[0].toLowerCase();
       pezzi_tipologia[1] = pezzi_tipologia[1].toLowerCase();
       //è un adattamento al nome usato dal solutore biomasse mentre in contenitore reflui è biomassa
       if(pezzi_tipologia[1].equals("biomassa"))
           pezzi_tipologia[1] = "biomasse";
       String temp = leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/m3");
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " tipologia " + tipologia + " tip 1 " + pezzi_tipologia[0] + " tip 2 " + pezzi_tipologia[1] + " valore " + temp + Math.round(Double.parseDouble(temp)));
       
       m3 = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/m3")));
        at = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/at")));
         am = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/am")));
          ss = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/ss")));
           sv = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/sv")));
            ft = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/ft")));
             pt = Math.round(Double.parseDouble(leggi.cercaSingolo("//totali/caratteristichechimiche/"+ pezzi_tipologia[1]+"/"+pezzi_tipologia[0]+"/pt")));
       
       //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " tipologia " + tipologia + " tip 1 " + pezzi_tipologia[0] + " tip 2 " + pezzi_tipologia[1] + " valore " + temp);
     
             
        ref.setMetricubi(m3); 
        ref.setAzotototale(at);
        ref.setAzotoammoniacale(am);
        ref.setSolidivolatili(sv);
        ref.setSostanzasecca(ss);
        ref.setPotassiototale(pt);
        ref.setFosforototale(ft);
        
        contenitore.setTipologia(tipologia, ref);
        
       }
       return contenitore;       
       
    }
    
    /**
     * salva nella tabella risultati_confronto l'output del solutore. Questo record salvato 
     * mi serve per capire se l'azienda identificata dallo scenario in questione ha effettuato un confronto
     * tramite il solutore oppure no e quindi capire se è abilitato o meno a fare il piano di 
     * concimazione
     */
    private void salvaRisultatoConfronto(ContenitoreReflui contenitore,int id_alternativa)
    {       
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         } 
         
         Query q = entityManager.createNamedQuery("AlternativeS.findById").setParameter("id", id_alternativa);
         List<db.AlternativeS> alternative = q.getResultList();
         
         db.AlternativeS alternativa = null;
         
         if(alternative.isEmpty()) {
            return;
        }
         else{
             alternativa = alternative.get(0);
         }
             
         
         Query q1 = entityManager.createNamedQuery("RisultatoConfronto.findByIdScenario").setParameter("idScenario", this.scenario);
         db.RisultatoConfronto risultatoConfronto = null;
         
         if(q1.getResultList().isEmpty()) {
            risultatoConfronto = new db.RisultatoConfronto(this.scenario);
        }
         else
         {
             risultatoConfronto = (db.RisultatoConfronto)q1.getResultList().get(0);
         }
         
         risultatoConfronto.setIdScenario(scenario);
         risultatoConfronto.setIdAlternativa(alternativa);
        // risultatoConfronto.setDigestato(alternativa.getDigestato() == 1);
              
         Refluo refluo = contenitore.getTipologia("Liquame Bovino");
         
         risultatoConfronto.setM3LBov(refluo.getMetricubi());
         risultatoConfronto.setTknLBov(refluo.getAzotototale());
         risultatoConfronto.setTanLBov(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmLBov(refluo.getSostanzasecca());
         risultatoConfronto.setVsLBov(refluo.getSolidivolatili());
         risultatoConfronto.setKLBov(refluo.getPotassiototale());
         risultatoConfronto.setPLBov(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Liquame Suino");
         
         risultatoConfronto.setM3LSui(refluo.getMetricubi());
         risultatoConfronto.setTknLSui(refluo.getAzotototale());
         risultatoConfronto.setTanLSui(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmLSui(refluo.getSostanzasecca());
         risultatoConfronto.setVsLSui(refluo.getSolidivolatili());
         risultatoConfronto.setKLSui(refluo.getPotassiototale());
         risultatoConfronto.setPLSui(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Liquame Avicolo");
         
         risultatoConfronto.setM3LAvi(refluo.getMetricubi());
         risultatoConfronto.setTknLAvi(refluo.getAzotototale());
         risultatoConfronto.setTanLAvi(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmLAvi(refluo.getSostanzasecca());
         risultatoConfronto.setVsLAvi(refluo.getSolidivolatili());
         risultatoConfronto.setKLAvi(refluo.getPotassiototale());
         risultatoConfronto.setPLAvi(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Liquame Biomassa");
         
         risultatoConfronto.setM3LBio(refluo.getMetricubi());
         risultatoConfronto.setTknLBio(refluo.getAzotototale());
         risultatoConfronto.setTanLBio(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmLBio(refluo.getSostanzasecca());
         risultatoConfronto.setVsLBio(refluo.getSolidivolatili());
         risultatoConfronto.setKLBio(refluo.getPotassiototale());
         risultatoConfronto.setPLBio(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Liquame Altro");
         
         risultatoConfronto.setM3LAlt(refluo.getMetricubi());
         risultatoConfronto.setTknLAlt(refluo.getAzotototale());
         risultatoConfronto.setTanLAlt(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmLAlt(refluo.getSostanzasecca());
         risultatoConfronto.setVsLAlt(refluo.getSolidivolatili());
         risultatoConfronto.setKLAlt(refluo.getPotassiototale());
         risultatoConfronto.setPLAlt(refluo.getFosforototale());
         
         
         
         refluo = contenitore.getTipologia("Letame Bovino");
         
         risultatoConfronto.setM3PBov(refluo.getMetricubi());
         risultatoConfronto.setTknPBov(refluo.getAzotototale());
         risultatoConfronto.setTanPBov(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmPBov(refluo.getSostanzasecca());
         risultatoConfronto.setVsPBov(refluo.getSolidivolatili());
         risultatoConfronto.setKPBov(refluo.getPotassiototale());
         risultatoConfronto.setPPBov(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Letame Suino");
         
         risultatoConfronto.setM3PSui(refluo.getMetricubi());
         risultatoConfronto.setTknPSui(refluo.getAzotototale());
         risultatoConfronto.setTanPSui(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmPSui(refluo.getSostanzasecca());
         risultatoConfronto.setVsPSui(refluo.getSolidivolatili());
         risultatoConfronto.setKPSui(refluo.getPotassiototale());
         risultatoConfronto.setPPSui(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Letame Avicolo");
         
         risultatoConfronto.setM3PAvi(refluo.getMetricubi());
         risultatoConfronto.setTknPAvi(refluo.getAzotototale());
         risultatoConfronto.setTanPAvi(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmPAvi(refluo.getSostanzasecca());
         risultatoConfronto.setVsPAvi(refluo.getSolidivolatili());
         risultatoConfronto.setKPAvi(refluo.getPotassiototale());
         risultatoConfronto.setPPAvi(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Letame Biomassa");
         
         risultatoConfronto.setM3PBio(refluo.getMetricubi());
         risultatoConfronto.setTknPBio(refluo.getAzotototale());
         risultatoConfronto.setTanPBio(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmPBio(refluo.getSostanzasecca());
         risultatoConfronto.setVsPBio(refluo.getSolidivolatili());
         risultatoConfronto.setKPBio(refluo.getPotassiototale());
         risultatoConfronto.setPPBio(refluo.getFosforototale());
         
         refluo = contenitore.getTipologia("Letame Altro");
         
         risultatoConfronto.setM3PAlt(refluo.getMetricubi());
         risultatoConfronto.setTknPAlt(refluo.getAzotototale());
         risultatoConfronto.setTanPAlt(refluo.getAzotoammoniacale());
         risultatoConfronto.setDmPAlt(refluo.getSostanzasecca());
         risultatoConfronto.setVsPAlt(refluo.getSolidivolatili());
         risultatoConfronto.setKPAlt(refluo.getPotassiototale());
         risultatoConfronto.setPPAlt(refluo.getFosforototale());
         
          entityManager.getTransaction().begin();
          entityManager.persist(risultatoConfronto);
          entityManager.getTransaction().commit();
          
          
          Connessione.getInstance().chiudi();
         
    }
    /*********************************************************************************
     * 
     * 
     *              DA FINIRE    DA FINIRE    DA FINIRE
     * 
     * ********************************************************************************/
    /**
     * genera la tabella di output dei reflui senza usare i css Richfaces perchè non 
     * riesco a gestire correttamente il toggle delle righe ed allora provo a costruirmene
     * una io
     * @param contenitore 
     */
    private void generaTabellaRefluiOut(ContenitoreReflui contenitore)
    {
         Refluo tot_letame = contenitore.totale("Letame");  
         Refluo tot_liquame = contenitore.totale("Liquame");  
         
         Refluo letame_bovino = contenitore.getTipologia("Letame Bovino");  
         Refluo letame_suino = contenitore.getTipologia("Letame Suino"); 
         Refluo letame_avicolo = contenitore.getTipologia("Letame Avicolo"); 
         Refluo letame_altro = contenitore.getTipologia("Letame Altro"); 
         Refluo letame_biomasse = contenitore.getTipologia("Letame Biomassa"); 
         
         Refluo liquame_bovino = contenitore.getTipologia("Liquame Bovino");  
         Refluo liquame_suino = contenitore.getTipologia("Liquame Suino"); 
         Refluo liquame_avicolo = contenitore.getTipologia("Liquame Avicolo"); 
         Refluo liquame_altro = contenitore.getTipologia("Liquame Altro"); 
         Refluo liquame_biomasse = contenitore.getTipologia("Liquame Biomassa"); 
         
         try{
          this.dinamicOut.println("<table class=\"localizzazioneTable3 righeBlue\">");
          this.dinamicOut.println("<thead><tr><th>Tipo</th><th>Volume</th><th>TKN</th><th>TAN</th><th>DM</th><th>VS</th><th>K</th><th>P</th></tr></thead>");
          this.dinamicOut.println("<tbody>");
          this.dinamicOut.println("<tr><td><img alt='im1' id='imgsu'  src='/renuwal/faces/javax.faces.resource/org.richfaces/up_icon.gif'>"+tot_letame.getTipologia()+"</td><td>"+tot_letame.getMetricubi()+"</td><td>"+tot_letame.getAzotototale()+"</td><td>"+tot_letame.getAzotoammoniacale()+"</td><td>"+tot_letame.getSostanzasecca()+"</td><td>"+tot_letame.getSolidivolatili()+"</td><td>"+tot_letame.getPotassiototale()+"</td><td>"+tot_letame.getFosforototale()+"</td></tr>");
          //this.dinamicOut.println("<tr><td>Tipo</th><th>Volume</td><td>TKN</td><td>TAN</td><td>DM</td><td>VS</td><td>K</td><td>P</td></tr>");
          this.dinamicOut.println("<tr><td>Bovino</td><td>"+letame_bovino.getMetricubi()+"</td><td>"+letame_bovino.getAzotototale()+"</td><td>"+letame_bovino.getAzotoammoniacale()+"</td><td>"+letame_bovino.getSostanzasecca()+"</td><td>"+letame_bovino.getSolidivolatili()+"</td><td>"+letame_bovino.getPotassiototale()+"</td><td>"+letame_bovino.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Suino</td><td>"+letame_suino.getMetricubi()+"</td><td>"+letame_suino.getAzotototale()+"</td><td>"+letame_suino.getAzotoammoniacale()+"</td><td>"+letame_suino.getSostanzasecca()+"</td><td>"+letame_suino.getSolidivolatili()+"</td><td>"+letame_suino.getPotassiototale()+"</td><td>"+letame_suino.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Avicolo</td><td>"+letame_avicolo.getMetricubi()+"</td><td>"+letame_avicolo.getAzotototale()+"</td><td>"+letame_avicolo.getAzotoammoniacale()+"</td><td>"+letame_avicolo.getSostanzasecca()+"</td><td>"+letame_avicolo.getSolidivolatili()+"</td><td>"+letame_avicolo.getPotassiototale()+"</td><td>"+letame_avicolo.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Altro</td><td>"+letame_altro.getMetricubi()+"</td><td>"+letame_altro.getAzotototale()+"</td><td>"+letame_altro.getAzotoammoniacale()+"</td><td>"+letame_altro.getSostanzasecca()+"</td><td>"+letame_altro.getSolidivolatili()+"</td><td>"+letame_altro.getPotassiototale()+"</td><td>"+letame_altro.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Biomassa</td><td>"+letame_biomasse.getMetricubi()+"</td><td>"+letame_biomasse.getAzotototale()+"</td><td>"+letame_biomasse.getAzotoammoniacale()+"</td><td>"+letame_biomasse.getSostanzasecca()+"</td><td>"+letame_biomasse.getSolidivolatili()+"</td><td>"+letame_biomasse.getPotassiototale()+"</td><td>"+letame_biomasse.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td><img alt='im2' id='imgsu1' src='/renuwal/faces/javax.faces.resource/org.richfaces/up_icon.gif'>"+tot_liquame.getTipologia()+"</td><td>"+tot_liquame.getMetricubi()+"</td><td>"+tot_liquame.getAzotototale()+"</td><td>"+tot_liquame.getAzotoammoniacale()+"</td><td>"+tot_liquame.getSostanzasecca()+"</td><td>"+tot_liquame.getSolidivolatili()+"</td><td>"+tot_liquame.getPotassiototale()+"</td><td>"+tot_liquame.getFosforototale()+"</td></tr>");
          //this.dinamicOut.println("<tr><td>Tipo</th><th>Volume</td><td>TKN</td><td>TAN</td><td>DM</td><td>VS</td><td>K</td><td>P</td></tr>");
          this.dinamicOut.println("<tr><td>Bovino</td><td>"+liquame_bovino.getMetricubi()+"</td><td>"+liquame_bovino.getAzotototale()+"</td><td>"+liquame_bovino.getAzotoammoniacale()+"</td><td>"+liquame_bovino.getSostanzasecca()+"</td><td>"+liquame_bovino.getSolidivolatili()+"</td><td>"+liquame_bovino.getPotassiototale()+"</td><td>"+liquame_bovino.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Suino</td><td>"+liquame_suino.getMetricubi()+"</td><td>"+liquame_suino.getAzotototale()+"</td><td>"+liquame_suino.getAzotoammoniacale()+"</td><td>"+liquame_suino.getSostanzasecca()+"</td><td>"+liquame_suino.getSolidivolatili()+"</td><td>"+liquame_suino.getPotassiototale()+"</td><td>"+liquame_suino.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Avicolo</td><td>"+liquame_avicolo.getMetricubi()+"</td><td>"+liquame_avicolo.getAzotototale()+"</td><td>"+liquame_avicolo.getAzotoammoniacale()+"</td><td>"+liquame_avicolo.getSostanzasecca()+"</td><td>"+liquame_avicolo.getSolidivolatili()+"</td><td>"+liquame_avicolo.getPotassiototale()+"</td><td>"+liquame_avicolo.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Altro</td><td>"+liquame_altro.getMetricubi()+"</td><td>"+liquame_altro.getAzotototale()+"</td><td>"+liquame_altro.getAzotoammoniacale()+"</td><td>"+liquame_altro.getSostanzasecca()+"</td><td>"+liquame_altro.getSolidivolatili()+"</td><td>"+liquame_altro.getPotassiototale()+"</td><td>"+liquame_altro.getFosforototale()+"</td></tr>");
          this.dinamicOut.println("<tr><td>Biomassa</td><td>"+liquame_biomasse.getMetricubi()+"</td><td>"+liquame_biomasse.getAzotototale()+"</td><td>"+liquame_biomasse.getAzotoammoniacale()+"</td><td>"+liquame_biomasse.getSostanzasecca()+"</td><td>"+liquame_biomasse.getSolidivolatili()+"</td><td>"+liquame_biomasse.getPotassiototale()+"</td><td>"+liquame_biomasse.getFosforototale()+"</td></tr>");

          this.dinamicOut.println("</tbody>");
          this.dinamicOut.println("</table>");
         }catch(Exception ex)
         {
             ex.printStackTrace();
         };
    }
    
    /**
     * mostra il risultato del solutore
     * 
     * @param leggi
     * @param cache
     * @param padre
     * @param alternativa 
     */
    private void recuperaSingola2multiRich(InputOutputXml leggi,boolean cache,String padre,String alternativa)  {
         
        // System.out.println("+++++++++++++"+padre+"++++++++++++++" + alternativa);
          
         NodeList temp = null;
        //mi serve per recuperar eil numero delle aziende presenti nel file di risposta
        temp = leggi.cerca1("//risultati/singola_azienda/alternativa",false);
        
        if(temp == null)
        {
              mostraErrore();
                     return;
        }
       // System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        //valore che uso nel ciclo for per ciclare su tutte le aziende del file dei risutlati
        int numeroAziende = temp.getLength();       
        //System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        try {     
          
            {       
           
                temp = leggi.cerca1(padre + "/scelta", true);
                
                //this.dinamicOut.println("<a class=\"labellocalizzazioneTable\" href=\"#linkrapidi\">Torna su</a>");
                //this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>Alternativa scelta : <a name=\"" + temp.item(0).getNodeValue() + "\">" + temp.item(0).getNodeValue() + "</a></b></p>");

                //recupero la composizione dell'alternativa inteso come moduli che la compongono
                Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));

                String moduli = "Moduli :";

                while (tratt.hasNext()) {
                    db.AlternativaTrattamento tratt1 = tratt.next();
                    int i = tratt1.getTrattamento().getId();
                    if(i != 6 && i!=7) {
                        moduli += " | " + tratt1.getTrattamento().getNome();
                    }
                }
                moduli +=" | Vasca | Platea";
               // this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>" + moduli + "</b></p>");
            /**
              * recupero le caratterstiche chimiche totali del refluo in uscita
              * dal solutore
              */   
             ContenitoreReflui contenitore = this.recuperaRefluo(leggi);
             
             
             //salvo i risultati ottenuti dal solutore nel db
             salvaRisultatoConfronto(contenitore,Integer.parseInt(alternativa));
             
                /**
                 * verifica dei vincoli nomrativi
                 */
                VincoliNormativi vincoli = new VincoliNormativi(contenitore,Integer.parseInt(alternativa));
                
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " vincolo nitrati " + vincoli.vincoloNormativo());
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " vincolo mas " + vincoli.vincoloMas());
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " vincolo fosforo " + vincoli.vincoloFosforo());
             this.dinamicOut.println("<br/><br/>");
             this.dinamicOut.println("<table id='form:tableVincoli' class='rf-dt' style='width:700px;'>");
             this.dinamicOut.println("<colgroup span='3'></colgroup>");
             this.dinamicOut.println("<thead id='form:tableVincoli:th' class='rf-dt-thd'>");
             this.dinamicOut.println("<tr id='form:tableVincoli:ch' class='rf-dt-shdr'>");
             this.dinamicOut.println("<th id='form:tableVincoli:j_idt150' class='rf-dt-shdr-c' scope='col'>Vincolo Nitrati</th>");
             this.dinamicOut.println("<th id='form:tableVincoli:j_idt157' class='rf-dt-shdr-c' scope='col'>Vincolo Mas</th>");
             this.dinamicOut.println("<th id='form:tableVincoli:j_idt160' class='rf-dt-shdr-c' scope='col'>Vincolo Fosforo</th>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</thead>");
                this.dinamicOut.println("<tbody id='form:tableVincoli:0:tb' class='rf-dt-b'>\n"
                        + "<tr id='form:tableVincoli:0' class='rf-dt-r rf-dt-fst-r'>\n");
                if(!vincoli.vincoloNormativo()) {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c' ></td>");
                }
                else {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c' bgcolor='#FF0000'></td>");
                }
                
                if(!vincoli.vincoloMas()) {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c'></td>");
                }
                else {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c' bgcolor='#FF0000'></td>");
                }
                
                if(!vincoli.vincoloFosforo()) {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c'></td>");
                }
                else {
                    this.dinamicOut.println("<td id='form:tableVincoli:0:j_idt157' class='rf-dt-c' bgcolor='#FF0000'></td>");
                }
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</tbody>");
                this.dinamicOut.println("</table>");
              //  this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">Caratteristiche chimiche : </p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                //this.dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(kg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
                 //this.dinamicOut.println("<p><h3>Descrizione parametri :</h3><br/><ul><li><b># Alt :</b> numero alternativa </li><li><b>Descrizione :</b> composizione in moduli dell'alternativa </li><li><b>Em A :</b>emissioni acide =nh3(Kg) </li><li><b>Em G :</b>emissioni gas serra = ch4 + co2 + n2o + n0(Kg) </li><li><b>Energia :</b> energia(KWh) consumata - prodotta </li><li><b>Costo :</b>  gestione(esercizio)(Euro)</li><li><b>% surplus :</b> (refluo prodotto - distribuzione sui terreni aziendali) / refluo prodotto  </li></ul>");
              this.dinamicOut.println("<br/><br/>");
              
              /**
               * per sostituire la tabella con le classi richfaces
               */
              this.generaTabellaRefluiOut(contenitore);
              
            /*  
              
             this.dinamicOut.println("<table id='form:tablexmlA' class='rf-dt' style='width:700px;'>");
             this.dinamicOut.println("<colgroup span='8'></colgroup>");
             this.dinamicOut.println("<thead id='form:tablexmlA:th' class='rf-dt-thd'>");
             this.dinamicOut.println("<tr id='form:tablexmlA:ch' class='rf-dt-shdr'>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt150' class='rf-dt-shdr-c' scope='col'>Tipo</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt157' class='rf-dt-shdr-c' scope='col'>Volume</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt160' class='rf-dt-shdr-c' scope='col'>TKN</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt163' class='rf-dt-shdr-c' scope='col'>TAN</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt166' class='rf-dt-shdr-c' scope='col'>DM</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt169' class='rf-dt-shdr-c' scope='col'>VS</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt172' class='rf-dt-shdr-c' scope='col'>K</th>");
             this.dinamicOut.println("<th id='form:tablexmlA:j_idt175' class='rf-dt-shdr-c' scope='col'>P</th>");
             this.dinamicOut.println("</tr>");
             this.dinamicOut.println("</thead>");
                
           
             Refluo tot_letame = contenitore.totale("Letame");  
             this.dinamicOut.println("<tbody id='form:tablexmlA:0:tb' class='rf-dt-b'>\n"
                                        +"<tr id='form:tablexmlA:0' class='rf-dt-r rf-dt-fst-r'>\n"
                                        +"<td id='form:tablexmlA:0:j_idt150' class='rf-dt-c'>\n"
                                        +"<span id='form:tablexmlA:0:j_idt155' class='rf-csttg'>\n"
                                        +"<span id='form:tablexmlA:0:j_idt155:collapsed' class='rf-csttg-colps'>\n"
                                        +"<img alt='' src='/renuwal/faces/javax.faces.resource/org.richfaces/up_icon.gif'>\n"
                                        +"</span>\n"
                                        +"<span id='form:tablexmlA:0:j_idt155:expanded' class='rf-csttg-exp' style='display: none;'>\n"
                                        +"<img alt='' src='/renuwal/faces/javax.faces.resource/org.richfaces/down_icon.gif'>\n"
                                        +"</span>\n"
                                        +"<script>\n"
                                        +"new RichFaces.ui.CollapsibleSubTableToggler('form:tablexmlA:0:j_idt155',{'expandedControl':'form:tablexmlA:0:j_idt155:expandeded','expandedControl':'form:tablexmlA:0:j_idt155:expanded','eventName':'click','forId':'form:tablexmlA:0:sbtbl'} )\n"
                                        +"</script>\n"
                                        +"</span>\n"
                                        +"Letame\n"
                                        +"</td>\n");   
             this.dinamicOut.println( "<td id='form:tablexmlA:0:j_idt157' class='rf-dt-c'>"+tot_letame.getMetricubi()+"</td>"
                     + "<td id='form:tablexmlA:0:j_idt160' class='rf-dt-c'>"+tot_letame.getAzotototale()+"</td>"
                     + "<td id='form:tablexmlA:0:j_idt163' class='rf-dt-c'>"+tot_letame.getAzotoammoniacale() +"</td>"
                     + "<td id='form:tablexmlA:0:j_idt166' class='rf-dt-c'>"+tot_letame.getSostanzasecca()+"</td>"
                     + "<td id='form:tablexmlA:0:j_idt169' class='rf-dt-c'>"+tot_letame.getSolidivolatili()+"</td>"
                     + "<td id='form:tablexmlA:0:j_idt172' class='rf-dt-c'>"+tot_letame.getPotassiototale()+"</td>"
                     + "<td id='form:tablexmlA:0:j_idt175' class='rf-dt-c'>"+tot_letame.getFosforototale()+"</td></tr></tbody>"
                     );
             
             
             this.dinamicOut.println("<tbody id='form:tablexmlA:0:sbtbl:c' class='rf-cst' >\n"
                                     +"<tr id='form:tablexmlA:0:sbtbl' >\n"
                                     +"<td></td>\n"
                                     +"</tr>\n"
                                     +"<tr id='form:tablexmlA:0:sbtbl:ch' class='rf-cst-shdr'>\n"
                                     +"<td class='rf-cst-shdr-c'>Tipo</td>\n"
                                     +"<td class='rf-cst-shdr-c'>Volume</td>\n"
                                     +"<td class='rf-cst-shdr-c'>TKN</td>\n"
                                     +"<td class='rf-cst-shdr-c'>TAN</td>\n"
                                     +"<td class='rf-cst-shdr-c'>DM</td>\n"
                                     +"<td class='rf-cst-shdr-c'>SV</td>\n"
                                     +"<td class='rf-cst-shdr-c'>K</td>\n"
                                     +"<td class='rf-cst-shdr-c'>P</td>\n"
                                     +"</tr>\n");
             
                                      Refluo ref = contenitore.getTipologia("Letame Bovino");  
             
             this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:0:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Letame Bovino</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:0:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
             
                ref = contenitore.getTipologia("Letame Suino");  
             
               this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:c:1'  class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Letame Suino</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:1:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                 
                 ref = contenitore.getTipologia("Letame Avicolo");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:2:b'  class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Letame Avicolo</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:2:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                
                 ref = contenitore.getTipologia("Letame Altro");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:3:b'  class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Letame Altro</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:3:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                
                 ref = contenitore.getTipologia("Letame Biomassa");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:4:b'  class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Letame Biomassa</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:0:sbtbl:4:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr></tbody>\n");
                
                this.dinamicOut.println("<tr id='form:tablexmlA:0:sbtbl:sc' style='display: none;'>\n"
                                        +"<td>\n"
                                        +"<script type='text/javascript'>\n"
                                        +"new RichFaces.ui.CollapsibleSubTable('form:tablexmlA:0:sbtbl','form',{'optionsInput':'form:tablexmlA:0:sbtbl:options','expandMode':'client','stateInput':'form:tablexmlA:0:sbtbl:state','eventOptions':{'incId':'1'} } )\n"
                                        +"</script>\n"
                                        +"<input id='form:tablexmlA:0:sbtbl:state' type='hidden' value='1' name='form:tablexmlA:0:sbtbl:state'>\n"
                                        +"<input id='form:tablexmlA:0:sbtbl:options' type='hidden' name='form:tablexmlA:0:sbtbl:options' value='form:tablexmlA:0:j_idt155'>\n"
                                        +"</td>\n"
                                        +"</tr>\n</tbody>\n");
                
                Refluo tot_liquame = contenitore.totale("Liquame");  
                
                this.dinamicOut.println("<tbody id='form:tablexmlA:1:tb' class='rf-dt-b'>\n"
                                        +"<tr id='form:tablexmlA:1' class='rf-dt-r'>\n"
                                        +"<td id='form:tablexmlA:1:j_idt150' class='rf-dt-c'>\n"
                                        +"<span id='form:tablexmlA:1:j_idt155' class='rf-csttg'>\n"
                                        +"<span id='form:tablexmlA:1:j_idt155:collapsed' class='rf-csttg-colps' style='display: none;'>\n"
                                        +"<img alt='' src='/renuwal/faces/javax.faces.resource/org.richfaces/up_icon.gif'>\n"
                                        +"</span>\n"
                                        +"<span id='form:tablexmlA:1:j_idt155:expanded' class='rf-csttg-exp' style=''>\n"
                                        +"<img alt='' src='/renuwal/faces/javax.faces.resource/org.richfaces/down_icon.gif'>\n"
                                        +"</span>\n"
                                        +"<script>\n"
                                        +"new RichFaces.ui.CollapsibleSubTableToggler('form:tablexmlA:1:j_idt155',{'exapandedControl':'form:tablexmlA:1:j_idt155:collapsed','collapsedControl':'form:tablexmlA:1:j_idt155:collapsed','eventName':'click','forId':'form:tablexmlA:1:sbtbl'} )\n"
                                        +"</script>\n"
                                        +"</span>\n"
                                        +"Liquame"
                                        +"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt157' class='rf-dt-c'>"+tot_liquame.getMetricubi()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt160' class='rf-dt-c'>"+tot_liquame.getAzotototale()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt163' class='rf-dt-c'>"+tot_liquame.getAzotoammoniacale()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt166' class='rf-dt-c'>"+tot_liquame.getSostanzasecca()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt169' class='rf-dt-c'>"+tot_liquame.getSolidivolatili()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt172' class='rf-dt-c'>"+tot_liquame.getPotassiototale()+"</td>\n"
                                        +"<td id='form:tablexmlA:1:j_idt175' class='rf-dt-c'>"+tot_liquame.getFosforototale()+"</td>\n"
                                        +"</tr>\n"
                                        +"</tbody>\n");
                
                
                this.dinamicOut.println("<tbody id='form:tablexmlA:1:sbtbl:c' class='rf-cst' style=''>\n"
                                     +"<tr id='form:tablexmlA:1:sbtbl' style='display: none;'>\n"
                                     +"<td></td>\n"
                                     +"</tr>\n"
                                     +"<tr id='form:tablexmlA:1:sbtbl:ch' class='rf-cst-shdr'>\n"
                                     +"<td class='rf-cst-shdr-c'>Tipo</td>\n"
                                     +"<td class='rf-cst-shdr-c'>Volume</td>\n"
                                     +"<td class='rf-cst-shdr-c'>TKN</td>\n"
                                     +"<td class='rf-cst-shdr-c'>TAN</td>\n"
                                     +"<td class='rf-cst-shdr-c'>DM</td>\n"
                                     +"<td class='rf-cst-shdr-c'>SV</td>\n"
                                     +"<td class='rf-cst-shdr-c'>K</td>\n"
                                     +"<td class='rf-cst-shdr-c'>P</td>\n"
                                     +"</tr>\n");
             
                                       ref = contenitore.getTipologia("Liquame Bovino");  
             
             this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:0:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Liquame Bovino</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:0:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
             
                ref = contenitore.getTipologia("Liquame Suino");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:1:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Liquame Suino</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:1:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                 
                 ref = contenitore.getTipologia("Liquame Avicolo");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:2:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Liquame Avicolo</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:2:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                
                 ref = contenitore.getTipologia("Liquame Altro");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:3:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Liquame Altro</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:3:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                
                 ref = contenitore.getTipologia("Liquame Biomassa");  
             
                this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:4:b' class='rf-cst-r rf-cst-fst-r odd-row'>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt178' class='rf-cst-c' style='background-color: #58ACFA;'>Liquame Biomassa</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt181' class='rf-cst-c' style='background-color: #58ACFA;'>\n"
                                    +"<span style='width:30px;'>"+ref.getMetricubi()+"</span>\n"
                                    +"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt184' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt187' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getAzotoammoniacale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt190' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSostanzasecca()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt193' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getSolidivolatili()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt196' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getPotassiototale()+"</td>\n"
                                    +"<td id='form:tablexmlA:1:sbtbl:4:j_idt199' class='rf-cst-c' style='background-color: #58ACFA;'>"+ref.getFosforototale()+"</td>\n"
                                    +"</tr>\n");
                
                this.dinamicOut.println("<tr id='form:tablexmlA:1:sbtbl:sc' style='display: none;'>\n"
                                        +"<td>\n"
                                        +"<script type='text/javascript'>\n"
                                        +"new RichFaces.ui.CollapsibleSubTable('form:tablexmlA:1:sbtbl','form',{'optionsInput':'form:tablexmlA:1:sbtbl:options','expandMode':'client','stateInput':'form:tablexmlA:1:sbtbl:state','eventOptions':{'incId':'1'} } )\n"
                                        +"</script>\n"
                                        +"<input id='form:tablexmlA:1:sbtbl:state' type='hidden' value='1' name='form:tablexmlA:1:sbtbl:state'>\n"
                                        +"<input id='form:tablexmlA:1:sbtbl:options' type='hidden' name='form:tablexmlA:1:sbtbl:options' value='form:tablexmlA:1:j_idt155'>\n"
                                        +"</td>\n"
                                        +"</tr>\n</tbody>\n");
                
                
                
                this.dinamicOut.println("</table>");*/


                

                String pattern1 = "(\\w*)([\\.,](\\w*))";
                
                //EMISSIONI AGRO
                //this.dinamicOut.println("<br/><p class=\"labellocalizzazioneTable\">EMISSIONI AGRO</p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<br/><table class=\"localizzazioneTable2\" ><colgroup span=\"9\"></colgroup>");
                //this.dinamicOut.println("<thead ><tr><th>NH3(kg/ha)</th><th>DRAINAGE(mm)</th><th>LEACH(kg/ha)</th></tr></thead>");
                this.dinamicOut.println("<thead >"
                        + "<tr><th colspan=\"9\" >Emissioni</th></tr>"
                        + "<tr><th colspan=\"3\" >Agro</th>"
                        + "<th colspan=\"6\" >ZooTecniche</th></tr>"
                        + "<tr ><th colspan=\"1\" >Nh3(kg/ha)</th><th  colspan=\"1\">Drainage(mm)</th><th colspan=\"1\">Leach(kg/ha)</th><th colspan=\"1\">Ch4(kg)</th><th colspan=\"1\">Nh3(kg)</th><th  colspan=\"1\">N2(kg)</th><th colspan=\"1\">N2O(kg)</th><th colspan=\"1\">CO2(kg)</th><th colspan=\"1\">NO(kg)</th></tr></thead>");

                this.dinamicOut.println("<tbody ><tr >");
                caratteristichechimiche(padre + "/totali/emissioni_agro");
                caratteristichechimiche(padre + "/totali/emissioni");
                this.dinamicOut.println("</tr></tbody>");
                this.dinamicOut.println("</table>");
                
                
                
                
                //EMISSIONI
                /*this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>CH4(kg)</th><th>NH3(kg)</th><th>N2(kg)</th><th>N20(kg)</th><th>CO2(kg)</th><th>No(kg)</th></tr></thead>");

                this.dinamicOut.println("<tr>");
               
                
               caratteristichechimiche(padre + "/totali/emissioni");
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");*/


                //COSTI
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
                this.dinamicOut.println("<br/><table class=\"localizzazioneTable2\"><colgroup span=\"8\"></colgroup>");
                this.dinamicOut.println("<thead >"
                        + "<tr><th colspan=\"5\" >Costi-Ricavi</th>"
                        + "<th colspan=\"3\" >Energia(KWh)</th></tr>" 
                        +"<tr><th  colspan=\"1\">Investimento(euro)</th>"
                        + "<th  colspan=\"1\">Gestione Netto(euro/a)</th>"
                        + "<th  colspan=\"1\">Costo Lordo gestione(euro/a)</th>"
                        + "<th  colspan=\"1\">Ricavo Lordo energia(euro/a)</th>"
                        + "<th  colspan=\"1\">Ricavo ammonio(euro)</th>"
                        + "<th  colspan=\"1\">Prodotta</th>"
                        + "<th  colspan=\"1\">Consumata</th>"
                        + "<th  colspan=\"1\">Venduta</th>"
                        + "</tr></thead>");
                this.dinamicOut.println("<tbody ><tr >");

                 caratteristichechimiche(padre + "/totali/costi");
                 caratteristichechimiche(padre + "/totali/energia");
                 
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</tbody></table>");

                //ENERGIA
               /* this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Energia Prodotta(KWh)</th><th>Energia consumata(KWh)</th><th>Energia Venduta(KWh)</th></tr></thead><tr>");
          
                caratteristichechimiche(padre + "/totali/energia");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");*/


                this.dinamicOut.println("<br/>");
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">VALORI AL METRO CUBO</p>");
           
                //lo uso per le emissioni,costi , energie dei valori al metro cubo per mostrare due cifre decimali
                DecimalFormat dformat = new DecimalFormat("#0.00");
             
                caratteristichechimicheMCRich(padre + "/totali/valori_al_metro_cubo",dformat);
                
                this.dinamicOut.println("<br/>");
                //VALORI AL METRO CUBO energia
               /**
                 * stampo il surplus cioè vado a leggere l'output degli
                 * stoccaggi e l'ouput della rimozione e faccio la differenza
                 */
                mostraSurplus1(leggi, padre, alternativa);       

                this.dinamicOut.println("<hr>");
                this.dinamicOut.println("<hr>");
             
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
        // Connessione.getInstance().chiudi();
    } 
     /**
      * recupera le caratteristiche chimiche dal file di output del soultore 
      * ed inserisce i valori nel contenitore reflui
      * @param leggi
      * @param cache
      * @param padre
      * @param alternativa 
      */
     private void recuperaSingola(InputOutputXml leggi,boolean cache,String padre,String alternativa)  {
         
      
         
        // System.out.println("+++++++++++++"+padre+"++++++++++++++" + alternativa);
         
         
         
         NodeList temp = null;
        //mi serve per recuperar eil numero delle aziende presenti nel file di risposta
        temp = leggi.cerca1("//risultati/singola_azienda/alternativa",false);
        
        if(temp == null)
        {
              mostraErrore();
                     return;
        }
       // System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        //valore che uso nel ciclo for per ciclare su tutte le aziende del file dei risutlati
        int numeroAziende = temp.getLength();
                
        
        
       
        //System.out.println(this.getClass().getCanonicalName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + "  risultati trovati " + temp.getLength() );
        try {
            
            
            //System.out.println(this.getClass().getCanonicalName()+ " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " numerpo aziende " + numeroAziende);
            
            
          
            {
            
           
                temp = leggi.cerca1(padre + "/scelta", true);
                this.dinamicOut.println("<a class=\"labellocalizzazioneTable\" href=\"#linkrapidi\">Torna su</a>");
                this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>Alternativa scelta : <a name=\"" + temp.item(0).getNodeValue() + "\">" + temp.item(0).getNodeValue() + "</a></b></p>");

                //recupero la composizione dell'alternativa inteso come moduli che la compongono
                Iterator<db.AlternativaTrattamento> tratt = this.composizioneAlternative1(Integer.parseInt(temp.item(0).getNodeValue()));

                String moduli = "Moduli :";

                while (tratt.hasNext()) {
                    db.AlternativaTrattamento tratt1 = tratt.next();
                    int i = tratt1.getTrattamento().getId();
                    if(i != 6 && i!=7) {
                        moduli += " | " + tratt1.getTrattamento().getNome();
                    }
                }
                moduli +=" | Vasca | Platea";
                this.dinamicOut.println("<p  class=\"labellocalizzazioneTable\"><b>" + moduli + "</b></p>");



                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">Caratteristiche chimiche : </p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(kg)</th><th>TAN(kg)</th><th>DM(kg)</th><th>VS(kg)</th><th>P(kg)</th><th>K(kg)</th></tr></thead>");
                this.dinamicOut.println("<tr><td>Liquame Bovino</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/bovino/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Bovino</td>");




                caratteristichechimiche(padre + "/totali/caratteristichechimiche/bovino/letame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Liquame Suino</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/suino/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Suino</td>");


                caratteristichechimiche(padre + "/totali/caratteristichechimiche/suino/letame");
                this.dinamicOut.println("</tr>");

                this.dinamicOut.println("<tr><td>Liquame Avicolo</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/avicolo/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Avicolo</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/avicolo/letame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Liquame Altro</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/altro/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Altro</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/altro/letame");
                this.dinamicOut.println("</tr>");


                this.dinamicOut.println("<tr><td>Liquame Biomasse</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/biomasse/liquame");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("<tr><td>Letame Biomasse</td>");



                caratteristichechimiche(padre + "/totali/caratteristichechimiche/biomasse/letame");
                this.dinamicOut.println("</tr>");



                this.dinamicOut.println("</table>");




                String pattern1 = "(\\w*)([\\.,](\\w*))";
                
                //EMISSIONI AGRO
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI AGRO</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>NH3(kg/ha)</th><th>DRAINAGE(mm)</th><th>LEACH(kg/ha)</th></tr></thead>");

                this.dinamicOut.println("<tr>");
                caratteristichechimiche(padre + "/totali/emissioni_agro");
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");
                
                
                
                
                //EMISSIONI
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>CH4(kg)</th><th>NH3(kg)</th><th>N2(kg)</th><th>N20(kg)</th><th>CO2(kg)</th><th>No(kg)</th></tr></thead>");

                this.dinamicOut.println("<tr>");
                /*temp = leggi.cerca1(padre + "/totali/emissioni/nh3", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/ch4", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/co2", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/n2o", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/emissioni/n2", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/emissioni/no", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                
               caratteristichechimiche(padre + "/totali/emissioni");
                
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");


                //COSTI
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Investimento(euro)</th><th>Gestione Netto(euro/a)</th><th>Costo Lordo gestione(euro/a)</th><th>Ricavo Lordo energia(euro/a)</th><th>Ricavo ammonio(euro)</th></tr></thead>");
                this.dinamicOut.println("<tr>");

                /*temp = leggi.cerca1(padre + "/totali/costi/investimento", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/costi/gestione", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/costi/costo_netto_gestione", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/costi/ricavo_netto_energia", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                 caratteristichechimiche(padre + "/totali/costi");

                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");

                //ENERGIA
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                this.dinamicOut.println("<thead><tr><th>Energia Prodotta(KWh)</th><th>Energia consumata(KWh)</th><th>Energia Venduta(KWh)</th></tr></thead><tr>");

                /*temp = leggi.cerca1(padre + "/totali/energia/prodotta", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");

                temp = leggi.cerca1(padre + "/totali/energia/consumata", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");
                temp = leggi.cerca1(padre + "/totali/energia/venduta", true);
                this.dinamicOut.println("<td>" + temp.item(0).getNodeValue().replaceAll(pattern1, "$1") + "</td>");*/
                
                 caratteristichechimiche(padre + "/totali/energia");
                this.dinamicOut.println("</tr>");
                this.dinamicOut.println("</table>");


                this.dinamicOut.println("<br/>");
                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">VALORI AL METRO CUBO</p>");
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">EMISSIONI</p>");

                //lo uso per le emissioni,costi , energie dei valori al metro cubo per mostrare due cifre decimali
                DecimalFormat dformat = new DecimalFormat("#0.00");

                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
               // this.dinamicOut.println("<thead><tr><th>Ch4(Kg/m<sup>3</sup>)</th><th>Nh3(Kg/m<sup>3</sup>)</th><th>N2(Kg/m<sup>3</sup>)</th><th>N2o(Kg/m<sup>3</sup>)</th><th>Co2(Kg/m<sup>3</sup>)</th><th>No(Kg/m<sup>3</sup>)</th></tr></thead><tr>");

                /*temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/ch4", true);

                double ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");


                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/nh3", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/n2", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/n2o", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/co2", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/no", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
                
                 caratteristichechimicheMC(padre + "/totali/valori_al_metro_cubo",dformat);
                
                //this.dinamicOut.println("</tr>");
               // this.dinamicOut.println("</table>");
                //this.dinamicOut.println("<br/>");
                //VALORI AL METRO CUBO costi
              //  this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">COSTI</p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                //this.dinamicOut.println("<thead><tr><th>Gestione Netto(euro/a*m<sup>3</sup>)</th><th>Costo lordo gestione(euro/a*m<sup>3</sup>)</th></tr></thead><tr>");

               /* temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/gestione", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());

                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/costo_netto_gestione", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
                 
                //this.dinamicOut.println("</tr>");
                //this.dinamicOut.println("</table>");

                this.dinamicOut.println("<br/>");
                //VALORI AL METRO CUBO energia
                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">ENERGIA</p>");
                //this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
               // this.dinamicOut.println("<thead><tr><th>Ricavo netto energia(euro/a*m<sup>3</sup>)</th><th>Energia Prodotta(KWh/m<sup>3</sup>)</th><th>Energia Consumata(KWh/m<sup>3</sup>)</th></tr></thead><tr>");

               /* temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/ricavo_netto_energia", true);
                ddfor = Double.parseDouble(temp.item(0).getNodeValue());

                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");

                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/energia_prodotta", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");
                temp = leggi.cerca1(padre + "/totali/valori_al_metro_cubo/energia_consumata", true);

                ddfor = Double.parseDouble(temp.item(0).getNodeValue());
                this.dinamicOut.println("<td>" + dformat.format(ddfor) + "</td>");*/
               // this.dinamicOut.println("</tr>");
               // this.dinamicOut.println("</table>");


                /**
                 * stampo il surplus cioè vado a leggere l'output degli
                 * stoccaggi e l'ouput della rimozione e faccio la differenza
                 */
                mostraSurplus1(leggi, padre, alternativa);
            
            
            

                this.dinamicOut.println("<hr>");
                this.dinamicOut.println("<hr>");
             
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LetturaRisultati.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
        // Connessione.getInstance().chiudi();
    }
     
     
     
     /**
      * restituisce la somma di azoto totale su un determinato modulo di una alternativa facendo la 
      * somma su liquame,letame su bovino,suino,avicolo,altro biomasse
      * @param leggi
      * @param padre
      * @param alternativa
      * @param nomemodulo
      * @return 
      */
     private double totaleAzoto(InputOutputXml leggi,String padre,String nomemodulo)
     {
             NodeList temp = null;
             double totale = 0;
         
             ////////liquame
             //bovino
              temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/bovino/liquame/at",true);
             double atbovinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
              //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/suino/liquame/at",true);
             double atsuinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
              //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/avicolo/liquame/at",true);
             double atavicolostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/altro/liquame/at",true);
             double ataltrostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
            //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/biomasse/liquame/at",true);
             double atbiomassestol =(int) Double.parseDouble(temp.item(0).getNodeValue());
                   
             ///////letame
             //bovino
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/bovino/letame/at",true);
             double atbovinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/suino/letame/at",true);
             double atsuinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
            //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/avicolo/letame/at",true);
             double atavicolostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/altro/letame/at",true);
             double ataltrostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\""+nomemodulo+"\"]/caratteristichechimiche/biomasse/letame/at",true);
             double atbiomassestop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
             
          totale = atbovinostol + atsuinostol + atavicolostol + ataltrostol +  atbiomassestol + atbovinostop + atsuinostop + atavicolostop + ataltrostop + atbiomassestop;
                
                return totale;
 
     }
     
     private double[][] assegnaValori(String tipoanimale,String tiporefluo,String caratteristica,String valore,double[][] contenitore)
     {
         int x = 0;
         int y = 0;
         switch (tipoanimale) {
             case "bovino":
                 y = 0;
                 break;
             case "suino":
                 y = 1;
                 break;
             case "avicolo":
                 y = 2;
                 break;
             case "altro":
                 y = 3;
                 break;
             case "biomasse":
                 y = 4;
                 break;

         }
         
         if(tiporefluo.equals("liquame") && caratteristica.equals("m3"))
             x = 0;
        
          if(tiporefluo.equals("letame") && caratteristica.equals("m3"))
             x = 1;
           if(tiporefluo.equals("liquame") && caratteristica.equals("at"))
             x = 2;
          if(tiporefluo.equals("letame") && caratteristica.equals("at"))
             x = 3;
         
          contenitore[x][y] = Double.parseDouble(valore);
         
         return contenitore;
     }
     
     
     /**
      * restituicse una tabella che ha sulle righe m3l ,mep ,atl , atp e sulle colonne
      * bovino,suino,avicolo,altro,biomasse
      *         bov     sui     avi     alt     bio
      * m3l     0,0     0,1     0,2     0,3
      * m3p     1,0     1,1
      * atl     2,0     2,1
      * atp
      * @param temp
      * @return 
      */
     private double[][] testLetturaSurplus(NodeList temp)
     {
       // NodeList temp = null;
        //temp = null;
       // String pattern1 = "(\\w*)([\\.,](\\w*))";
        double[][] dati = new double[4][5];
        try{
            // temp = leggi.cerca1(query,true);
            
             for (int i = 0; i < temp.getLength(); i++) {
                    if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) 
                    {
                        String animale = temp.item(i).getLocalName();
                        //System.out.println("animale " + animale);
                        NodeList temp1 = temp.item(i).getChildNodes();
                        for(int k = 0; k < temp1.getLength();k++)
                        {
                             if (temp1.item(k).getNodeType() == Node.ELEMENT_NODE) 
                             {  
                                 String tiporefluo = temp1.item(k).getLocalName();
                                 //System.out.println("- k "+k + " tiporefluo  " +tiporefluo);
                                 
                                 NodeList temp2 = temp1.item(k).getChildNodes();
                                 
                                 for(int k1 = 0; k1 < temp2.getLength();k1++)
                                 {
                                      if (temp2.item(k1).getNodeType() == Node.ELEMENT_NODE) 
                                      {
                                          String carachimica = temp2.item(k1).getLocalName();
                                          String valorecarachimica = temp2.item(k1).getFirstChild().getNodeValue();
                                           //System.out.println("- k1 "+k1 + " carachimica nome  " +carachimica);
                                          // System.out.println("- k1 "+k1 + " valorecarachimica  " +valorecarachimica);
                                           
                                           if(carachimica.equals("m3")||carachimica.equals("at"))
                                           {
                                               dati = assegnaValori(animale,tiporefluo,carachimica,valorecarachimica,dati);
                                           }
                                      }
                               
                                 }
                             
                             }
                        }
                         //System.out.println("-"+temp1.item(1).getLocalName());
                        //System.out.println("<td>" + temp.item(i).getFirstChild().getNodeValue().replaceAll(pattern1, "$1") +"</td>");         
                    
                    }
            }
        }catch(Exception ex)
        {ex.printStackTrace();}
        
        return dati;
         
     }
     /**
      * Genera una tabella contenente volume e at su letame liquame su tutte le specie 
      * ottenuto dalla differenza tra quello che esce dagli stoccaggi e quello che esce dalla rimozione azoto 
      * per una definita alternativa
      * @param leggi
      * @param padre
      * @param alternativa 
      */
     public void mostraSurplus(InputOutputXml leggi,String padre,String alternativa)
     {
         
         
              NodeList temp = null;
              
            
              
         /**
              * stampo il surplus cioè vado a leggere l'output degli stoccaggi e l'ouput della rimozione 
              * e faccio la differenza
              */
             ////////liquame
             //bovino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/liquame/m3",true);
             double m3bovinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/liquame/at",true);
             double atbovinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
             temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/liquame/m3",true);
             double m3bovinorimozionel = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/liquame/at",true);
             double atbovinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             //surplus liquame bovino
             double surplus_liquame_bovino_m3 = m3bovinostol - m3bovinorimozionel;
             double surplus_liquame_bovino_at = atbovinostol - atbovinorimozionel;
             
              //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/liquame/m3",true);
             double m3suinostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/liquame/at",true);
             double atsuinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/liquame/m3",true);
             double m3suinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/liquame/at",true);
             double atsuinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              //surplus liquame suino
             double surplus_liquame_suino_m3 = m3suinostol - m3suinorimozionel;
             double surplus_liquame_suino_at = atsuinostol - atsuinorimozionel;
             
             
             
              //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/liquame/m3",true);
             double m3avicolostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/liquame/at",true);
             double atavicolostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/liquame/m3",true);
             double m3avicolorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/liquame/at",true);
             double atavicolorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
              //surplus liquame avicolo
             double surplus_liquame_avicolo_m3 = m3avicolostol - m3avicolorimozionel;
             double surplus_liquame_avicolo_at = atavicolostol - atavicolorimozionel;
             
             
             
              //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/liquame/m3",true);
             double m3altrostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/liquame/at",true);
             double ataltrostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/liquame/m3",true);
             double m3altrorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/liquame/at",true);
             double ataltrorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
              //surplus liquame altro
             double surplus_liquame_altro_m3 = m3altrostol - m3altrorimozionel;
             double surplus_liquame_altro_at = ataltrostol - ataltrorimozionel;
             
              //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/liquame/m3",true);
             double m3biomassestol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/liquame/at",true);
             double atbiomassestol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/liquame/m3",true);
             double m3biomasserimozionel = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/liquame/at",true);
             double atbiomasserimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              //surplus liquame biomasse
             double surplus_liquame_biomasse_m3 = m3biomassestol - m3biomasserimozionel;
             double surplus_liquame_biomasse_at = atbiomassestol - atbiomasserimozionel;
             
             ///////letame
             //bovino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/letame/m3",true);
             double m3bovinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/letame/at",true);
             double atbovinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/letame/m3",true);
             double m3bovinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/letame/at",true);
             double atbovinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
             
              //surplus letame bovino
             double surplus_letame_bovino_m3 = m3bovinostop - m3bovinorimozionep;
             double surplus_letame_bovino_at = atbovinostop - atbovinorimozionep;
             
             
              //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/letame/m3",true);
             double m3suinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/letame/at",true);
             double atsuinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/letame/m3",true);
             double m3suinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/letame/at",true);
             double atsuinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
               //surplus letame suino
             double surplus_letame_suino_m3 = m3suinostop - m3suinorimozionep;
             double surplus_letame_suino_at = atsuinostop - atsuinorimozionep;
             
             
              //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/letame/m3",true);
             double m3avicolostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/letame/at",true);
             double atavicolostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/letame/m3",true);
             double m3avicolorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/letame/at",true);
             double atavicolorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
               //surplus letame avicolo
             double surplus_letame_avicolo_m3 = m3avicolostop - m3avicolorimozionep;
             double surplus_letame_avicolo_at = atavicolostop - atavicolorimozionep;
             
             
              //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/letame/m3",true);
             double m3altrostop = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/letame/at",true);
             double ataltrostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/letame/m3",true);
             double m3altrorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/letame/at",true);
             double ataltrorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
               //surplus letame altro
             double surplus_letame_altro_m3 = m3altrostop - m3altrorimozionep;
             double surplus_letame_altro_at = ataltrostop - ataltrorimozionep;
             
              //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/letame/m3",true);
             double m3biomassestop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/letame/at",true);
             double atbiomassestop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/letame/m3",true);
             double m3biomasserimozionep = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/letame/at",true);
             double atbiomasserimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
             
                //surplus letame biomasse
                double surplus_letame_biomasse_m3 = m3biomassestop - m3biomasserimozionep;
                double surplus_letame_biomasse_at = atbiomassestop - atbiomasserimozionep;

                boolean tabellarossa = false;
                if (surplus_liquame_bovino_m3 > 0 || surplus_liquame_suino_m3 > 0 || surplus_liquame_avicolo_m3 > 0 || surplus_liquame_altro_m3 > 0 || surplus_liquame_biomasse_m3 > 0)
                       tabellarossa = true;
                
                try{

                this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">SURPLUS</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");
                if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                }



                //this.dinamicOut.println("<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                this.dinamicOut.println("<td>" + surplus_liquame_bovino_m3 + "</td><td>" + surplus_liquame_suino_m3 + "</td><td>" + surplus_liquame_avicolo_m3 + "</td><td>" + surplus_liquame_altro_m3 + "</td><td>" + surplus_liquame_biomasse_m3 + "</td>");
                this.dinamicOut.println("</tr></table>");

                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");

                tabellarossa = false;
                if (surplus_liquame_bovino_at > 0 || surplus_liquame_suino_at > 0 || surplus_liquame_avicolo_at > 0 || surplus_liquame_altro_at > 0 || surplus_liquame_biomasse_at > 0) {
                    tabellarossa = true;
                }



                if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + surplus_liquame_bovino_at + "</td><td>" + surplus_liquame_suino_at + "</td><td>" + surplus_liquame_avicolo_at + "</td><td>" + surplus_liquame_altro_at + "</td><td>" + surplus_liquame_biomasse_at + "</td>");
                this.dinamicOut.println("</tr></table>");


                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");


                tabellarossa = false;
                if (surplus_letame_bovino_m3 > 0 || surplus_letame_suino_m3 > 0 || surplus_letame_avicolo_m3 > 0 || surplus_letame_altro_m3 > 0 || surplus_letame_biomasse_m3 > 0)
                       tabellarossa = true;
                

                if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + surplus_letame_bovino_m3 + "</td><td>" + surplus_letame_suino_m3 + "</td><td>" + surplus_letame_avicolo_m3 + "</td><td>" + surplus_letame_altro_m3 + "</td><td>" + surplus_letame_biomasse_m3 + "</td>");
                this.dinamicOut.println("</tr></table>");

                this.dinamicOut.println("<table class=\"localizzazioneTable2\">");


                tabellarossa = false;
                if ( surplus_letame_bovino_at > 0 || surplus_letame_suino_at > 0 || surplus_letame_avicolo_at > 0 || surplus_letame_altro_at > 0 || surplus_letame_biomasse_at > 0)
                    tabellarossa = true;
                

                if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + surplus_letame_bovino_at + "</td><td>" + surplus_letame_suino_at + "</td><td>" + surplus_letame_avicolo_at + "</td><td>" + surplus_letame_altro_at + "</td><td>" + surplus_letame_biomasse_at + "</td>");

                this.dinamicOut.println("</tr></table>");
                
                }catch(Exception ex){};

     }
     
     
     public void mostraSurplus1(InputOutputXml leggi,String padre,String alternativa)
     {
         
         
              NodeList temp = null;
              
               temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche",true);
              double[][] datistoccaggi = testLetturaSurplus(temp);
              
               temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche",true);
              double[][] datirimozione = testLetturaSurplus(temp);
              
              int[][] datitotali = new int[4][5];
              /**
               * dato che il modulo stoccaggi contiene l'output dell'alternativa e il modulo rimozioneazoto 
               * contiene gli stoccaggi meno il surplus per ottenere il surplus faccio la differenza tra i due
               * 
               */
              for(int i = 0; i < 4; i++)
              {
                  for(int k = 0;k < 5;k++)
                  {
                      datitotali[i][k] =(int)(datistoccaggi[i][k] - datirimozione[i][k]) ;
                  }
              }
              
        
              
             

             
                boolean[] tabellarossa = new boolean[4];
                for(int i = 0; i < tabellarossa.length;i++)
                    tabellarossa[i] = false;
                //verifica surplus su liquame
                if (datitotali[0][0] > 0 || datitotali[0][1] > 0 || datitotali[0][2] > 0 ||datitotali[0][3] > 0 ||datitotali[0][4] > 0 )
                       tabellarossa[0] = true;
                //verifica surplus su letame
                if (datitotali[1][0] > 0 || datitotali[1][1] > 0 || datitotali[1][2] > 0 || datitotali[1][3] > 0 || datitotali[1][4] > 0)
                       tabellarossa[1] = true;
                  //verifica surplus su liquame azoto
                if (datitotali[2][0] > 0 || datitotali[2][1] > 0 ||datitotali[2][2] > 0 || datitotali[2][3] > 0 || datitotali[2][4] > 0) {
                    tabellarossa[2] = true;
                }              
                //verififca surplus su letame azoto
                   if ( datitotali[3][0] > 0 || datitotali[3][1] > 0 || datitotali[3][2] > 0 || datitotali[3][3] > 0 || datitotali[3][4] > 0)
                    tabellarossa[3] = true;
                
                
                /*try{

                //this.dinamicOut.println("<p class=\"labellocalizzazioneTable\">SURPLUS</p>");
                this.dinamicOut.println("<table class=\"localizzazioneTable2\" >");
                if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                }*/



                //this.dinamicOut.println("<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
              /*  this.dinamicOut.println("<td>" + datitotali[0][0] + "</td><td>" + datitotali[0][1] + "</td><td>" + datitotali[0][2] + "</td><td>" + datitotali[0][3] + "</td><td>" + datitotali[0][4] + "</td>");
                this.dinamicOut.println("</tr></table>");

                this.dinamicOut.println("<table id=\"surplus1\" >");*/

               



              /*  if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + datitotali[2][0] + "</td><td>" + datitotali[2][1] + "</td><td>" + datitotali[2][2] + "</td><td>" + datitotali[2][3] + "</td><td>" + datitotali[2][4] + "</td>");
                this.dinamicOut.println("</tr></table>");


                this.dinamicOut.println("<table id=\"surplus1\" >");*/


                /*tabellarossa = false;
                if (datitotali[1][0] > 0 || datitotali[1][1] > 0 || datitotali[1][2] > 0 || datitotali[1][3] > 0 || datitotali[1][4] > 0)
                       tabellarossa = true;*/
                

              /*  if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + datitotali[1][0] + "</td><td>" + datitotali[1][1] + "</td><td>" + datitotali[1][2] + "</td><td>" + datitotali[1][3] + "</td><td>" + datitotali[1][4] + "</td>");
                this.dinamicOut.println("</tr></table>");

                this.dinamicOut.println("<table id=\"surplus1\" ");*/


                /*tabellarossa = false;
                if ( datitotali[3][0] > 0 || datitotali[3][1] > 0 || datitotali[3][2] > 0 || datitotali[3][3] > 0 || datitotali[3][4] > 0)
                    tabellarossa = true;*/
                

             /*   if (!tabellarossa) {
                    this.dinamicOut.println("<thead><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>");
                } else {
                    this.dinamicOut.println("<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>");
                }

                this.dinamicOut.println("<td>" + datitotali[3][0] + "</td><td>" + datitotali[3][1] + "</td><td>" + datitotali[3][2] + "</td><td>" + datitotali[3][3] + "</td><td>" + datitotali[3][4] + "</td>");

                this.dinamicOut.println("</tr></table>");*/
                
                //this.dinamicOut.println("<table>");
                try{
                  this.dinamicOut.println("<table class=\"localizzazioneTable2\" >");
                  this.dinamicOut.println("<colgroup span=\"10\">");
                  this.dinamicOut.println("<thead><tr>");
                  if(tabellarossa[0])
                  {
                      this.dinamicOut.println("<th colspan=\"5\" style=\"background-color:red;\">Volume Liquame(m<sup>3</sup>)</th>");
                  }
                        else
                  {
                     this.dinamicOut.println("<th colspan=\"5\" >Volume Liquame(m<sup>3</sup>)</th>");

                  }
                  
                  if(tabellarossa[1])
                  {
                      this.dinamicOut.println("<th colspan=\"5\" style=\"background-color:red;\">Volume Letame(m<sup>3</sup>)</th>");
                  }
                        else
                  {
                     this.dinamicOut.println("<th colspan=\"5\" >Volume Letame(m<sup>3</sup>)</th>");

                  }
                  
                   this.dinamicOut.println("</tr>");
                 
                  this.dinamicOut.println("<tr>"
                          + "<th colspan=\"1\">Bovino</th>"
                          + "<th colspan=\"1\">Suino</th>"
                          + "<th colspan=\"1\">Avicolo</th>"
                          + "<th colspan=\"1\">Altro</th>"
                          + "<th colspan=\"1\">Biomasse</th>"
                          + "<th colspan=\"1\">Bovino</th>"
                          + "<th colspan=\"1\">Suino</th>"
                          + "<th colspan=\"1\">Avicolo</th>"
                          + "<th colspan=\"1\">Altro</th>"
                          + "<th colspan=\"1\">Biomasse</th>"
                          + "</tr>"
                          + "</thead>");
                  this.dinamicOut.println("<tbody>"
                          + "<tr>"
                          + "<td>"+datitotali[0][0]+"</td>"
                          + "<td>"+datitotali[0][1]+"</td>"
                          + "<td>"+datitotali[0][2]+"</td>"
                          + "<td>"+datitotali[0][3]+"</td>"
                          + "<td>"+datitotali[0][4]+"</td>"
                          + "<td>"+datitotali[1][0]+"</td>"
                          + "<td>"+datitotali[1][1]+"</td>"
                          + "<td>"+datitotali[1][2]+"</td>"
                          + "<td>"+datitotali[1][3]+"</td>"
                          + "<td>"+datitotali[1][4]+"</td>"
                          + "</tr>"
                          + "</tbody>");
                  this.dinamicOut.println("</table>");
                  
                  
                  
                  this.dinamicOut.println("<table class=\"localizzazioneTable2\" >");
                  this.dinamicOut.println("<colgroup span=\"10\">");
                  this.dinamicOut.println("<thead><tr>");
                  
                  if(tabellarossa[2])
                  {
                      this.dinamicOut.println("<th colspan=\"5\" style=\"background-color:red;\">Azoto Totale Liquame(Kg)</th>");
                  }
                        else
                  {
                     this.dinamicOut.println("<th colspan=\"5\" >Azoto Totale Liquame(Kg)</th>");

                  }
                  
                 if(tabellarossa[3])
                  {
                      this.dinamicOut.println("<th colspan=\"5\" style=\"background-color:red;\">Azoto Totale Letame(Kg)</th>");
                  }
                        else
                  {
                     this.dinamicOut.println("<th colspan=\"5\" >Azoto Totale Letame(Kg)</th>");

                  }  
                  
                         
                         this.dinamicOut.println("</tr>");
                  this.dinamicOut.println("<tr>"
                          + "<th colspan=\"1\">Bovino</th>"
                          + "<th colspan=\"1\">Suino</th>"
                          + "<th colspan=\"1\">Avicolo</th>"
                          + "<th colspan=\"1\">Altro</th>"
                          + "<th colspan=\"1\">Biomasse</th>"
                          + "<th colspan=\"1\">Bovino</th>"
                          + "<th colspan=\"1\">Suino</th>"
                          + "<th colspan=\"1\">Avicolo</th>"
                          + "<th colspan=\"1\">Altro</th>"
                          + "<th colspan=\"1\">Biomasse</th>"
                          + "</tr>"
                          + "</thead>");
                  this.dinamicOut.println("<tbody>"
                          + "<tr>"
                          + "<td>"+datitotali[2][0]+"</td>"
                          + "<td>"+datitotali[2][1]+"</td>"
                          + "<td>"+datitotali[2][2]+"</td>"
                          + "<td>"+datitotali[2][3]+"</td>"
                          + "<td>"+datitotali[2][4]+"</td>"
                          + "<td>"+datitotali[3][0]+"</td>"
                          + "<td>"+datitotali[3][1]+"</td>"
                          + "<td>"+datitotali[3][2]+"</td>"
                          + "<td>"+datitotali[3][3]+"</td>"
                          + "<td>"+datitotali[3][4]+"</td>"
                          + "</tr>"
                          + "</tbody>");
                  this.dinamicOut.println("</table>"); 
                  
                
                
                }catch(Exception ex){};

     }
     
     
     /**
     * 
     * @param elementiinseriti numero di elementi gia presenti sulla riga
     * stile mi dice che deve essere impostato anche lo stile
     * style è la regola css 
     */
    private void aggiungiNuovaLinea(int elementiinseriti)
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
       
    }
    
    
    
    /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @return 
     */
    private void aggiungiValoriGrid(NodeList contenuto,String label)
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
        
        
        
    }
    
    
     /**
     * creato per l'header del panelgrid
     * @param contenuto
     * @param label
     * @param tipo è 0 per le caratteristiche chimiche 1 per le emissioni 2 per l'energia 3 per i costi. Inserisco un tipo per differenziare le diverse unita di misura
     * @return 
     */
    private void creaHeaderGrid(NodeList contenuto,String label,int tipo)
    {
     
      Application app = FacesContext.getCurrentInstance().getApplication();
      HtmlOutputText text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
      text.setValue(label);
      dynamicGrid.getChildren().add(text);
      
     
      String unitadimisura="";
             switch(tipo)
             {
                 case 0://caratteristiche chimiche
                     unitadimisura = "(Kg)";
                     break;
                 case 1://emissioni
                      unitadimisura = "(Kg)";
                     break;
                 case 2://energia
                     unitadimisura = "(Kwh)";
                     break;
                 case 3://costi
                     unitadimisura = "(euro)";
                     break;
                case 4://costi
                     unitadimisura = "(euro/anno)";
                     break;     
             }
                
        
      for(int i = 0; i < contenuto.getLength();i++)
        {
           
             Node temp = contenuto.item(i);
             if (temp.getNodeType() == Node.ELEMENT_NODE) {
                
                    String c = temp.getNodeName();
                    
                   
                       
                    if(c.equals("m3"))
                        c = "volume";
                     if(c.equals("manutanzione"))
                     {
                         c="esercizio(euro/anno)";
                     text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
                     text.setStyle("font-weight:bold;");
                     text.setValue(c.toUpperCase());
                     }else
                     {
                     text = (HtmlOutputText) app.createComponent(HtmlOutputText.COMPONENT_TYPE);
                     text.setStyle("font-weight:bold;");
                     text.setValue(c.toUpperCase()+unitadimisura);
                     }
                     
                    
                    dynamicGrid.getChildren().add(text);
             }
        }
        
        
        
    }

    /**
     * @return the contenitoreflui
     */
    public ContenitoreReflui getContenitoreflui() {
        return contenitoreflui;
    }

    /**
     * @param contenitoreflui the contenitoreflui to set
     */
    public void setContenitoreflui(ContenitoreReflui contenitoreflui) {
        this.contenitoreflui = contenitoreflui;
    }
     
    
}
