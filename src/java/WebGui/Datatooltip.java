/*
 * il seguente bean nasce allo scopo di fornire le stringhe per i tooltip di spiegazione 
 * delle label delle diverse voci dei pèarametri diprogetto .
 */
package WebGui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author giorgio
 */
@ManagedBean(name = "datatooltip")
@ViewScoped
public class Datatooltip implements Serializable{
    
   private static final long serialVersionUID = 1L;
   
   public  Datatooltip(){}
   
   
   public String spiegaTip(int quale)
   {
       String ritorno = "";
       
       switch(quale)
       {
           case 1:
               ritorno = "valore percentuale";
               break;
           case 2:
               ritorno = "valore percentuale";
               break;
           case 3:
               ritorno = "da definire";
               break;
               
          case 4://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Codice unico di identificazione dell'azienda agricola";
               break;     
           case 5://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Nome dell'azienda";
               break;
          case 6://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Comune in cui risiede l'azienda";
               break;      
         case 7://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Codice catastale del comune";
               break;  
         case 8://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Provincia in cui risiede l'azienda";
               break;     
          case 9://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Codice catastale della provincia";
               break; 
          case 10://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "longitudine del centro aziendale";
               break;     
          case 11://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "latitudine del centro aziendale";
               break;     
         case 12://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Superficie(ha) agricola utile in zona non vulnerabile";
               break;      
         case 13://usato nella creazione della nuova azienda pagina nuova.xhtml
               ritorno = "Superficie(ha) agricola utile in zona vulnerabile";
               break; 
         case 14://usato nella pagina dettaglioAzienda    
             ritorno = "Quantità(kg) media di azoto asportato ";
             break;
         case 15://usato nella pagina dettaglioAzienda    
             ritorno = "Quantità(kg) media di azoto asportato ";
             break;
         case 16://usato nella pagina dettaglioAzienda    
             ritorno = "unità metri cubi";
             break; 
          case 17://usato nella pagina dettaglioAzienda    
             ritorno = "azoto totale kjeldahl";
             break; 
          case 18://usato nella pagina dettaglioAzienda    
             ritorno = "azoto ammoniacale";
             break; 
           case 19://usato nella pagina dettaglioAzienda    
             ritorno = "sostanza secca";
             break; 
           case 20://usato nella pagina dettaglioAzienda    
             ritorno = "solidi volatili";
             break; 
          case 21://usato nella pagina dettaglioAzienda    
             ritorno = "potassio";
             break; 
         case 22://usato nella pagina dettaglioAzienda    
             ritorno = "fosforo";
             break; 
         case 23://usato nella pagina dettaglioAzienda    
             ritorno = "carico massimo di azoto zootecnico(Kg) distribuibile per azienda";
             break;      
         case 24://usato nella pagina dettaglioAzienda    
             ritorno = "carico massimo di azoto totale(Kg) distribuibile per azienda";
             break;          
         case 25://usato nella pagina dettaglioAzienda    
             ritorno = "valore percentuale dell'efficienza di asportazione dell'azoto";
             break;
             
        case 26://usato nella pagina gruppoaziende.xhtml e localizzazione2.xhtml    
             ritorno = "peso percentuale dato nel ranking delle alternative al costo <br/> se 1 e tutto il resto a zero il ranking verra ordinato<br/> in funzione del costo ";
             break;     
        case 27://usato nella pagina gruppoaziende.xhtml e localizzazione2.xhtml     
             ritorno = "peso percentuale dato nel ranking delle alternative alle <br/>emissioni acide(nh3) se 1 e tutto il resto a zero il ranking verra ordinato<br/> in funzione delle emissioni acide";
             break;    
        case 28://usato nella pagina gruppoaziende.xhtml e localizzazione2.xhtml    
             ritorno = "peso percentuale dato nel ranking delle alternative <br/>alle emissioni gas serra(ch4+co2+n2o) se 1 e tutto il resto a zero il ranking verra<br/> ordinato in funzione delle emissioni gas serra";
             break;     
        case 29://usato nella pagina gruppoaziende.xhtml e localizzazione2.xhtml    
             ritorno = "peso percentuale dato nel ranking delle alternative <br/>all' energia se 1 e tutto il resto a zero il ranking verra ordinato in <br/>funzione dell'energia";
             break;         
         case 30://usato nella pagina compostaggio    
             ritorno = "rapporto tra kg di sostanza secca e kg di coformulante";
             break;    
         case 31://usato nella pagina compostaggio    
             ritorno = "rapporto tra kg di sostanza secca e kg di miscela da compostare";
             break;         
        case 32://usato nella pagina compostaggio    
             ritorno = "rapporto tra kg di sostanza secca e kg di compost";
             break;
         case 33://usato nella pagina compostaggio    
             ritorno = "Il processo di compostaggio viene attivato solo nel caso in cui il ricavo dalla vendita <br/>del compost sia uguale o superiore al costo del trattamento. C’è un parametro <br/>di progetto (sempre_attivo) che lo forza comunque a essere attivo bypassando <br/>la condizione se ha valore 'sì'. Viceversa se assume valore 'mai', <br/>non viene mai attivato il compostaggio";
             break;  
        case 40://usato nella pagina parametri vasca    
             ritorno = "giorni richiesti di stoccaggio per i liquami bovini : influisce sulla capacità richiesta<br/> dello stoccaggio dalla formula capacita = (volume * giorni richiesti)/giorni anno   ";    
            break;
        case 41://usato nella pagina parametri vasca    
             ritorno = "giorni richiesti di stoccaggio per i liquami suini : influisce sulla capacità richiesta<br/> dello stoccaggio dalla formula capacita = (volume * giorni richiesti)/giorni anno   ";    
            break;
        case 42://usato nella pagina parametri vasca    
             ritorno = "giorni richiesti di stoccaggio per i liquami avicoli : influisce sulla capacità richiesta<br/> dello stoccaggio dalla formula capacita = (volume * giorni richiesti)/giorni anno   ";    
            break;
        case 43://usato nella pagina parametri vasca    
             ritorno = "giorni richiesti di stoccaggio per i liquami non bovini,suini,avicoli : influisce sulla capacità richiesta<br/> dello stoccaggio dalla formula capacita = (volume * giorni richiesti)/giorni anno   ";    
             break;
         case 44://usato nella pagina parametri vasca    
             ritorno = "giorni richiesti di stoccaggio per le biomasse : influisce sulla capacità richiesta<br/> dello stoccaggio dalla formula capacita = (volume * giorni richiesti)/giorni anno   ";    
            break;
         case 60://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il volume totale in ingresso produce i m3 di biomassa";
             break;
        case 61://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per i m3 di metano prodotti definisce la potenza del motore";     
            break;
        case 62://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per la potenza del motore definisce l'energia prodotta";     
            break;
        case 63://usato nella pagina parametri digestione    
             ritorno = "influisce sulla formula che definisce le perdite di CH4";     
            break;  
        case 64://usato nella pagina parametri digestione    
             ritorno = "moltitplicato per l'energia prodotta degfinisce l'energia per autoconsumo";     
            break;  
         case 65://usato nella pagina parametri digestione    
             ritorno = "influisce sulla formula che definisce le perdite di NH3";     
            break;     
        case 66://usato nella pagina parametri digestione    
             ritorno = "moltipliato per i solidi volatili in ingresso definisce la quantita di biogas prodotta (kg)";     
            break;  
        case 67://usato nella pagina parametri digestione    
             ritorno = "influisce sulla produzione di CH4";     
            break;
       case 68://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
       case 69://usato nella pagina parametri digestione    
             ritorno = "definisce il costo di investimento = (c_inv * potenza motore)^e_inv ";     
            break;  
       case 80://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
       case 81://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
       case 82://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
      case 83://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
     case 84://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
      case 85://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;  
      case 86://usato nella pagina parametri digestione    
             ritorno = "moltiplicato per il biogas prodotto definisce la componente solida persa";     
            break;      
      case 100://usato nella pagina parametri trasporto
             ritorno = "fattore di emissione della CO2 nel percorso da azienda a impianto.";     
            break;          
     case 101://usato nella pagina parametri trasporto 
             ritorno = "fattore di emissione di NH3 nel percorso da azienda a impianto.";     
            break;  
     case 102://usato nella pagina parametri trasporto  
             ritorno = "fattore di emissione di N2O in fase di accensione del trattore.";     
            break;  
     case 103://usato nella pagina parametri trasporto  
             ritorno = "fattore di emissione di CH4 nel percorso da azienda a impianto.";     
            break;  
     case 104://usato nella pagina parametri trasporto 
             ritorno = "fattore di emissione di N2O in fase di accensione del trattore.";     
            break;  
     case 105://usato nella pagina parametri trasporto  
             ritorno = "fattore di emissione di NO nel percorso da azienda a impianto.";     
            break;  
     case 106://usato nella pagina parametri trasporto 
             ritorno = "fattore di emissione di N2O nel percorso dalle vasche aziendali ai terreni aziendali.";     
            break;      
      case 107://usato nella pagina parametri trasporto 
             ritorno = "fattore di emissione di CH4 nel percorso dalle vasche aziendali ai terreni aziendali.";     
            break;            
    case 200://usato nella pagina nuova azienda campo deroga 
             ritorno = "definisce se un azienda è in deroga ponendo il limite dei 250kg/ettaro";     
            break;        
       }     
       return ritorno;
   }
   
}
           