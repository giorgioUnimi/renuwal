/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import ager.ContenitoreReflui;
import ager.Refluo;
import ager.trattamenti.ContenitoreAziendale;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import operativo.dettaglio.DettaglioCuaa;
import operativo.dettaglio.InputOutputXml;
import org.w3c.dom.Element;

/**
 *
 * @author giorgio
 */
public class Solutore {
    
    /**
     * genera il file xml di input al solutore in funzione dello 
     * scenario e dell'alternativa
     * @param scenario
     * @param alternativa 
     */
    public void generaFileInput(int scenario,String alternativa,String cuaa,ContenitoreAziendale contenitore) throws TransformerException
    {
        
        String numerorandom1;
        String operazione = "singolamulti1";
        DettaglioCuaa dettaglioCuaa = new DettaglioCuaa();
        System.out.println("utente loggato : " + dettaglioCuaa.getUtente() + " scenario " + scenario + " alternativa " + alternativa + " cuaa " + cuaa);
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
        
        inputoutputxml.selezioni(elemento, 0, 0, Integer.parseInt(alternativa));
        
        inputoutputxml.minimizzazioni1(elemento,0.3d,0.3d,0.2d,0.2d);
        
      
        /**
         * inserisco i parametri di progetto
         */
        inputoutputxml.parametriDiProgetto(elemento, scenario);
        
        Element azienda = null;
        azienda = inputoutputxml.aggiungiAzienda(elemento, cuaa,String.valueOf(scenario),true);
        
        /*ContenitoreAziendale contenitore = ContenitoreAziendale.getInstance(cuaa, Integer.parseInt(scenario));
        contenitore.getData(Integer.parseInt(scenario));*/
         
        /**
         * contiene le caratteristiche chimiche delle singole 4 tipologie di
         * refluo ovvero liquame bovino , liquamne suino , letame bovino ,
         * letame suinno
         */
        //List<Refluo> listaCaratteristiche = new LinkedList<Refluo>();
        List<Refluo> listaCaratteristicheLiq = new LinkedList<Refluo>();
        List<Refluo> listaCaratteristicheLet = new LinkedList<Refluo>();
        ContenitoreReflui contenitoreIniziale = null;
        
        contenitoreIniziale = contenitore.getContenitore();
        /**
         * usato come somma dei contenitori di reflui
         */
       //ContenitoreReflui contenitoreTotale = new ContenitoreReflui();

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

        inputoutputxml.aggiungiStoccaggi(azienda, scenario);

        inputoutputxml.aggiungiAcquaStoccaggio(azienda, scenario);

        numerorandom1 = getRandomNumber(5);
        DettaglioCuaa.setNumeroRandom(numerorandom1);

        try {
            inputoutputxml.scriviFile("input_" + numerorandom1 + ".xml");
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(RefluiAzienda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        Modello modello = null;
        
       
         String currentDir = System.getProperty("user.dir");
         System.out.println("Current dir using System:         " + currentDir);
         System.out.println("numero random del file xml           " + numerorandom1);  
             
         if(System.getProperty("os.name").contains("Windows"))
         {
             modello = new Modello("C:\\Users\\giorgio\\Documents\\NetBeansProjects\\seespig\\","seespig1.exe",numerorandom1,false);
         }else
         {
             modello = new Modello("/home/giorgiogalassi/Documenti/solutorec/","./solutore1_renuwal.out",numerorandom1,true); 
         }
         
                
       
          modello.run1();
          
          //tabellaIniziale(contenitoreTotale,out);
     
        
        
        InputOutputXml leggiXml = new InputOutputXml();
        LetturaRisultati test1 = new LetturaRisultati(leggiXml, numerorandom1, operazione, modello);
        test1.setContenitoreflui( contenitoreIniziale);
        test1.run1();
                  
               
    }
    
    
    
    private String getRandomNumber(int digCount) {
        Random rr = new Random();

        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++) {
            sb.append((char) ('0' + rr.nextInt(10)));
        }
        return sb.toString();
    }
    
}
