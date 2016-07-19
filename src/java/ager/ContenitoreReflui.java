/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

import java.util.Arrays;
import java.util.Hashtable;


/**
 *
 * @author giorgio
 */
public class ContenitoreReflui {
    
    /**
     * Contiene le tipolgie animali ovvero bovino,suino,avicolo ...
     * Sulla base delle tipolgie viene popolata la lista di reflui interna alla classe
     * Per ogni tipologia vengono creati due inserimenti nella lista reflui:una relativa
     * al liquame ed una relativa al letame.
     */
    //List<String> tipologie = null;
   private String[] tipologie = {"Liquame Bovino","Liquame Suino","Liquame Avicolo","Liquame Altro","Liquame Biomassa","Letame Bovino","Letame Suino","Letame Avicolo","Letame Altro","Letame Biomassa"};
    
   /**
    * Contenitore delle caratteristiche chimiche delle biomasse
    */
  //private Refluo biomassa = new Refluo("Biomassa");
    /**
   * Contenitore dell'azoto minerale 
   */
  private double azotominerale = 0;
  
 
    /**
     * E' il contenitore di tutte le tipologie di refluo 
     * da trattare divise per liquame e letame
     */
    private Hashtable<String,Refluo> contenitore = null;
    /**
     * Costruisce un contenitore di reflui basati sulla classe refluo 
     * 
     * @param tipologie 
     */
    public ContenitoreReflui()
    {
        
        contenitore = new Hashtable<String,Refluo>();
        
      
        
        for(String s:tipologie)
        {
            contenitore.put(s,new Refluo(s));
        }
    }
    
    
    
    /**
     * Ritorna la tipolgia di refluo specificato dalla tipolgia specificata dal parametro passato
     * @param tipologia puo essere uno tra i seguenti :"Liquame Bovino","Liquame Suino","Liquame Avicolo","Liquame Altro",
     * "Letame Bovino","Letame Suino","Letame Avicolo","Letame Altro"
     * @return null se la tipoligia non corrisponde ad un elemento altrimenti una istanza di refluo
     */
    public Refluo getTipologia(String tipologia)
    {
       return contenitore.get(tipologia);
    }
    
    
    public Refluo setTipologia(String tipologia,Refluo refluo)
    {
        return this.contenitore.put(tipologia, refluo);
        
        
        
    }
    /**
     * @return the tipologie
     */
    public String[] getTipologie() {
        return tipologie;
    }

    /**
     * @return the contenitore
     */
    public Hashtable<String,Refluo> getContenitore() {
        return contenitore;
    }

    /**
     * @param contenitore the contenitore to set
     */
    public void setContenitore(Hashtable<String,Refluo> contenitore) {
        this.contenitore = contenitore;
    }
    
    
    /**
     * ritorna le tre tipolgie di totali :Liquame, Letame , Totale.
     * @param tipo se Liquame ritorna il totale dei liquami inseriti, se Letame ritorna il totale dei letami inseriti,
     * se Totale ritorna il totale del refluo inserito.
     * @return una istanza della classe refluo che contiene la tipologia di totale richiesta se tipo 
     * non corrispondesse a Liquame o Letame o Totale ritorna null;
     */
    public Refluo totale(String tipo)
    {
        Refluo re = null;
        
        /**
         * per i totali parziali cerco tra le tipologie che rappresentano le chiavi della hashtable
         * e faccio le somme nella classe refluo che devo ritornare
         */
        if(tipo.equals("Liquame") || tipo.equals("Letame"))
        {
                
        re = new Refluo(tipo);
        
        for(String  s:tipologie)
        {
                if(s.contains(tipo))
                {
                  re.addAzotoAmmoniacale(contenitore.get(s).getAzotoammoniacale()) ;
                  re.addAzotoTotale(contenitore.get(s).getAzotototale());
                  re.addFosforoTotale(contenitore.get(s).getFosforototale());
                  re.addMetricubi(contenitore.get(s).getMetricubi());
                  //re.addMetricubibiomassa(contenitore.get(s).getMetricubibiomassa());
                  re.addPotassioTotale(contenitore.get(s).getPotassiototale());
                  re.addSolidiVolatili(contenitore.get(s).getSolidivolatili());
                  re.addSostanzaSecca(contenitore.get(s).getSostanzasecca());
                }
        }
        
        return re;
        
        }
        
        
        if(tipo.equals("Totale"))
        {
            re = new Refluo(tipo);
        
        for(String  s:tipologie)
        {
              
                  re.addAzotoAmmoniacale(contenitore.get(s).getAzotoammoniacale()) ;
                  re.addAzotoTotale(contenitore.get(s).getAzotototale());
                  re.addFosforoTotale(contenitore.get(s).getFosforototale());
                  re.addMetricubi(contenitore.get(s).getMetricubi());
                  //re.addMetricubibiomassa(contenitore.get(s).getMetricubibiomassa());
                  re.addPotassioTotale(contenitore.get(s).getPotassiototale());
                  re.addSolidiVolatili(contenitore.get(s).getSolidivolatili());
                  re.addSostanzaSecca(contenitore.get(s).getSostanzasecca());
                
        }
        
        return re;
        
        }
        return re;
    }
    
    
    
    /**
     * somma a se stesso un altro contenitore reflui sommando le varie tipologie di refluo
     * @param daaggiungere 
     */
    public void aggiungiContenitore(ContenitoreReflui daaggiungere)
    {
        for(String  s:tipologie)
        {
                this.getTipologia(s).addRefluo(daaggiungere.getTipologia(s));
                
                    
                
        }
    }
    
    
    /**
     * @return the biomassa
     */
   /* public Refluo getBiomassa() {
        return biomassa;
    }*/

    /**
     * @param biomassa the biomassa to set
     */
    /*public void setBiomassa(Refluo biomassa) {
        this.biomassa = biomassa;
    }*/

    /**
     * @return the azotominerale
     */
    public double getAzotominerale() {
        return azotominerale;
    }

    /**
     * @param azotominerale the azotominerale to set
     */
    public void setAzotominerale(double azotominerale) {
        this.azotominerale = azotominerale;
    }
    
    
    /**
     * stampa l'intero contenuto del contenitore reflui
     */
    public void stampaContenuto()
    {
        for(String tipo:this.getTipologie())
        {
            Refluo ref = this.getTipologia(tipo);
            
            System.out.println(tipo + " metri cubi " + ref.getMetricubi() + " azoto am " + ref.getAzotoammoniacale() + " azoto tot " + ref.getAzotototale() + " fosforo " + ref.getFosforototale());
        }
    }

    
    
}
