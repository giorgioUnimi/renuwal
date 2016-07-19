/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;

/**
 * Classe basata sul pattern Factory e sul pattern singleton. Il pattern singleton 
 * per avere un riferimento di questa classe ovunque nel programma ed il Factory per 
 * separare il dettaglio dei trattamenti dalle richieste del client che deve solo passare una stringa
 * corrispondente al nome del trattamento. Le stringhe possibili sono :
 * 
 * @author giorgio
 */
public class CostruttoreTrattamento {
    
    private static CostruttoreTrattamento istanza;
    
    private CostruttoreTrattamento(){}
    
    public static CostruttoreTrattamento getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new CostruttoreTrattamento(/*totlet,totliq*/);
    }

    return istanza;
  }
    
    
    /**
     * Identifica la classe che deve svolgere il trattamento
     *         
     * @param nometrattamento stringhe accettate : "liquame" , "letame" , "separazione a vite elicoidale" ,"ndn", "sbr" ,"vasca" ,"platea" , "vendita" ,
     * "uso agronomico","n minerale" ,"da" , "stabilizzazione aerobica" , "rete idrica"
     * @return null neel caso in cui il trattamento no sia stato identificato 
     * altrimenti un riferimento all'istanza del trattamento
     */
    public Trattamento getTrattamento(String nometrattamento)
    {
        Trattamento tratto = null;
            
            switch(nometrattamento)
            {
                case "liquame":
                    tratto = LiquameT.getInstance();
                    break;
                    
                case "letame":
                    tratto = LetameT.getInstance();
                    break;
                case "separazione a vite elicoidale":
                    tratto = SeparazioneAVite.getInstance();
                    break;
                case "ndn":
                   tratto = Ndn.getInstance();
                    break;
                case "sbr":
                    tratto = Sbr.getInstance();
                    break;
                case "vasca":
                   tratto = Vasca.getInstance();
                    break;
                case "platea":
                    tratto = Platea.getInstance();
                    break;
                case "n minerale":
                   tratto = NMinerale.getInstance();
                    break;
                case "da":
                    tratto = Da.getInstance();
                    break;
                case "stabilizzazione aerobica":
                    tratto = Stabilizzazione.getInstance();
                    break; 
                    
                case "rete idrica":
                    tratto = ReteIdrica.getInstance();
                    break;
    }
         
            return tratto;
    }
    
}
