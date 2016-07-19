/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobiettivo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *Calcola la funzione obiettivo sui costi,emisisoni acide, emissioni gas serra,energia
 * nella forma pesocosto * (costo in uscita dall'alternativa - costo minimo tra tutte le alternative)/(valore max del costo tra tutte le alternative - costo minimo tra tutte le alternative)           
 * @author giorgio
 */
public class FunzioneObiettivo {
    
    //contiene i valori di output tutte le alternative
    private List<Alternativa> alternative;
    //sono i valori di riferimento per il calcolo della funzione
    //che verranno recuperati dai valori presenti nella lista delle alterntive
    private double costomin;
    private double costomax;
    private double emissioniamin;
    private double emissioniamax;
    private double emissionigmin;
    private double emissionigmax;
    private double energiamin;
    private double energiamax;
    
    //sono i valori al m3 di riferimento per il calcolo della funzione 
    //che verranno recuperati dai valori presenti nella lista delle alterntive
    private double costominm3;
    private double costomaxm3;
    private double emissioniaminm3;
    private double emissioniamaxm3;
    private double emissionigminm3;
    private double emissionigmaxm3;
    private double energiaminm3;
    private double energiamaxm3;
    
    //contenitore dei pesi e per ogni combinazione di pesi che rappresenta una chiave 
    //sono presenti tutte le alternative ordinate usando la funzione obiettivo in funzione di quella combinazione
    //di pesi
    private LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> ordineAlternative = new LinkedHashMap<ArrayHolder<Float>,List<Alternativa>>();
    
    /**
     * contiene il numero di volte che un alternativa compare come migliore ed il valore medio
     * dei pesi con cui compare quell'alternativa
     */
    private List<Alternativa> mediaAlternative = new LinkedList<Alternativa>();
    
    
    public FunzioneObiettivo(List<Alternativa> alternative)
    {
        this.alternative = alternative;
        //imposto i valori minimi delle variabili locali
        recuperaMin();
        
         //imposto i valori max delle variabili locali
        recuperaMax();
        
        
        
        //calcola la funzione obiettivo su tutte le alternatuive per tutte le
        //combinaziomni di pesi
        ordineAlternative = ciclaPesi(0.2f);
        
        
       // stampaOrdine(0.0f,0.0f,0.0f,1.0f);
        
        /**
         * cicla sulla linkedhasmap ordineAlternative e calcola il numero di volte che 
         * una determinata alternativa compare come migliore
         */
        analisiMedia();
        
        
        
    }
    
    /**
     * cerca nella lista mediaAlternative se è presente l'alternativa alt infunzione del numero di alternativa presente in alt
     * se non lo trova la aggiunge alla lista se la trova incremente di uno la variabile numero_volte di alternativa e 
     * @param alt
     * @param mediaAlternative 
     */
    private void aggiungiAlternativa(Alternativa alt)
    {
       Alternativa ttemp;
       /**
        * cerco l'alternativa migliore nella hash mediaAlternative se questa non è presente la aggiungo se invece è già presente devo mantenre la chiave di ricerca
        * della hashmap ma devo fare la media dei pesi 
        */
  
     
     ListIterator<Alternativa> iterAl = getMediaAlternative().listIterator();
       
     
      int posizione =-1;//= Collections.binarySearch(lista, alt);
       //ListIterator<Alternativa> iterAl = lista.listIterator();
       Alternativa al ;
       
       while(iterAl.hasNext())
       {
           al = iterAl.next();
           if(al.getNumeroalternativa() == alt.getNumeroalternativa())
           { 
               posizione = iterAl.nextIndex() -1;
               break;
           }
       }
     
     
     
     
      int numerovolte = 0;
     /**
      * se >= 0 ho  trovato una posizione e quindi ho trovato l'alternativa
      * altrimenti la aggiungi
      */
     if(posizione >= 0)
     {
         ttemp = getMediaAlternative().get(posizione);
         numerovolte = ttemp.getNumero_volte();
         numerovolte= numerovolte +  1;
         ttemp.setNumero_volte(numerovolte);
         
         float pc = ttemp.getPesomedio_costo();
         float pg = ttemp.getPesomedio_emisisonigas();
         float pa = ttemp.getPesomedio_emissioniacide();
         float pe = ttemp.getPesomedio_energia();
         
         ttemp.setPesomedio_costo((pc + alt.getPesomedio_costo()) /2);
         ttemp.setPesomedio_emisisonigas((pg + alt.getPesomedio_emisisonigas())/2 );
         ttemp.setPesomedio_emissioniacide((pa + alt.getPesomedio_emissioniacide())/2);
         ttemp.setPesomedio_energia((pe + alt.getPesomedio_energia())/2 );
         
         
          //System.out.println("aggiorno  alternativa "  +   ttemp.getNumeroalternativa() + " posizione " + posizione + " numero volte alternativa " +  ttemp.getNumero_volte() + " numerovolte variabile " + numerovolte);
         
     }else
     {
            getMediaAlternative().add((Alternativa)alt.clone());
         
         
         // System.out.println("inserisco  alternativa "  +   alt.getNumeroalternativa() + " posizione " + posizione + " numero volte " +  alt.getNumero_volte());
         
     }
     
     
    
       
    }
    
    
    /**
     * prende per ogni chiave presente nella hashtable delle alternative orindate la prima laoternativa che corrisposnde a
     * quella migliroe per quella combinazione di pesi e la inserisce nella lista mediaAlternative 
     */
    private void analisiMedia()
    {
     
       /* System.out.println("  ---------analisiMedia    ---------analisiMedia    ---------analisiMedia");
        for (ArrayHolder<Float> chiavi : ordineAlternative.keySet()) {
            Float[] chiavi1 = chiavi.getArray();

         

            //data una chiave recuperi la lista delle alternative orindate dalla hashtable
            List<Alternativa> listaAlternative = ordineAlternative.get(chiavi);


            ListIterator<Alternativa> iterAlt = listaAlternative.listIterator();
           // System.out.println(" emissione a min " + this.emissioniaminm3 + " emissioni a max " + this.emissioniamaxm3 + " emissioni g min " + this.emissionigminm3 + " emissioni g max " + this.emissionigmaxm3 + " costo min " + this.costominm3 + " costo max " + this.costomaxm3 + " energia min " + this.energiaminm3 + " energia max" + this.energiamaxm3);
            while (iterAlt.hasNext()) {
                Alternativa al = iterAlt.next();

                System.out.println(" pesoea " + chiavi1[0] + " pesoeg " + chiavi1[1] + " pesoen " + chiavi1[2] + " pesoc " + chiavi1[3] + " alternativa numero " + al.getNumeroalternativa() + " valore funz " + al.getValore() + " emissione A " + al.getEmissioni_acide_m3() + " emissioni G" + al.getEmissioni_gas_m3() + " costo " + al.getCosto_m3() + " energia " + al.getEnergia_m3());
            }

            System.out.println("  ---------analisiMedia    ---------analisiMedia    ---------analisiMedia");



        }
        */
        //mediaAlternative;
           
        
         // System.out.println("  ---------Media    ---------Media    ---------Media");
       int ii = 0;
        for (ArrayHolder<Float> chiavi : ordineAlternative.keySet()) {
            Float[] chiavi1 = chiavi.getArray();

           // System.out.println(chiavi1[0] + " " + chiavi1[1] + " " + chiavi1[2] + "  " + chiavi1[3]);

            //data una chiave recuperi la lista delle alternative orindate dalla hashtable
            List<Alternativa> listaAlternative = ordineAlternative.get(chiavi);
            
            
           Alternativa tt =  listaAlternative.get(0);
           
           tt.setPesomedio_emissioniacide(chiavi1[0]);
           tt.setPesomedio_emisisonigas(chiavi1[1]);
           tt.setPesomedio_costo(chiavi1[2]);
           tt.setPesomedio_energia(chiavi1[3]);
           
           aggiungiAlternativa(tt);
           /* ListIterator<Alternativa> iterAlt = listaAlternative.listIterator();
            System.out.println(" emissione a min " + this.emissioniaminm3 + " emissioni a max " + this.emissioniamaxm3 + " emissioni g min " + this.emissionigminm3 + " emissioni g max " + this.emissionigmaxm3 + " costo min " + this.costominm3 + " costo max " + this.costomaxm3 + " energia min " + this.energiaminm3 + " energia max" + this.energiamaxm3);
            while (iterAlt.hasNext()) {
                Alternativa al = iterAlt.next();

                System.out.println(" pesoea " + chiavi1[0] + " pesoeg " + chiavi1[1] + " pesoen " + chiavi1[2] + " pesoc " + chiavi1[3] + " alternativa numero " + al.getNumeroalternativa() + " valore funz " + al.getValore() + " emissione A " + al.getEmissioni_acide_m3() + " emissioni G" + al.getEmissioni_gas_m3() + " costo " + al.getCosto_m3() + " energia " + al.getEnergia_m3());
            }*/

           // System.out.println("  ---------Media    ---------Media    ---------Media");

           ii++;

        }
        
         /*System.out.println("  ---------Media    ---------Media    ---------Media  numero iterazioni " +ii);
         for(int i = 0; i < this.getMediaAlternative().size();i++)
         {
             Alternativa tt1 = this.getMediaAlternative().get(i);
             
             System.out.println(" posizione di "  +  tt1.getNumeroalternativa() +  " numero volte " + tt1.getNumero_volte() + " pesi medi ema  " + tt1.getPesomedio_emissioniacide() + " medi emg " + tt1.getPesomedio_emisisonigas() + " costo "  + tt1.getPesomedio_costo() + " energia " + tt1.getPesomedio_energia() );
         }
         
         
         System.out.println("  ---------Media    ---------Media    ---------Media");*/
        
                
    }
    /**
     * cicla sui 4 pesi ovvero emissioni acide, emissioni gas serra, energia,costo con un passo dato che chiamo delta
     * e quando la somma vale 1 calcolo la funzione obiettivo su tutte le alternative le ordino in funzione dle loro valore numerico 
     * ritornato dalla funzione obiettivo e salvo quella configurazione un una linkedhashmap che mi servira per mostrare i risultati sul 
     * web
     * @param delta
     * @return 
     */
    private LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> ciclaPesi(float delta)
    {
            
        
       ordineAlternative.clear();
       
       ListIterator<Alternativa> iterAlternative;
       double valoreO = 0;
        //double[] chiave ;
        List<Alternativa> listTemp;// = new LinkedList(this.alternative); 
         //Collections.copy(listTemp, alternative);
         
       for (float pesoea = 0; pesoea <= 1 ; pesoea= pesoea + delta )
       {
           for (float pesoeg = 0; pesoeg <= 1 ; pesoeg = pesoeg + delta ) 
           {
                 for(float pesoen = 0; pesoen <= 1 ; pesoen = pesoen + delta )
                 {
                      for(float pesoc = 0; pesoc <= 1 ; pesoc = pesoc + delta )
                      {
                          if((pesoea + pesoeg + pesoen + pesoc) == 1)
                          {
                            
                            listTemp = new LinkedList(); 
                           // Collections.copy(listTemp, alternative);
                            iterAlternative = this.alternative.listIterator();
                            /**
                             * ciclo su tutte le alternative calcolando la funzione obiettivo la quale vaira in funzione dei pesi passati
                             * calcolato il valore lo inserisoc nell'alternativa stessa e la inserisco nella linkedhashmap 
                             */
                            while(iterAlternative.hasNext())
                            {
                                   Alternativa temp =(Alternativa) iterAlternative.next().clone();
                                   valoreO = calcolaObiettivo(pesoc,pesoea,pesoeg,pesoen,temp,true);
                                 
                                  // System.out.println(" valore " + valoreO + " alternativa " + temp.getNumeroalternativa() + "pesoc " + pesoc+" peso ea "+pesoea,pesoeg,pesoen );
                                   temp.setValore(valoreO);
                                     listTemp.add(temp);
                                  
                                   
                            }
                            
                         // listTemp = new LinkedList(this.alternative); 
                          
                            
                           ordinaAlternative(listTemp);
                           
                          
                         
                           ordineAlternative.put(new ArrayHolder<>(new Float[]{pesoea , pesoeg , pesoen , pesoc}), listTemp);
                           
                           
                           //this.stampaOrdine(pesoea, pesoeg, pesoen, pesoc);
                           
                           
                          }
                      }
                 }
           }
       }
        
        return  ordineAlternative;
    }
    
    
    private void stampaOrdine(Float pesoea,Float pesoeg,Float pesoen,Float pesoc){
        
        //Float[] chiave = { pesoea , pesoeg , pesoen , pesoc };
        List<Alternativa> listaAlternative = ordineAlternative.get(new ArrayHolder<>(new Float[]{pesoea , pesoeg , pesoen , pesoc}));
        
       /* for(ArrayHolder<Float> chiavi:ordineAlternative.keySet())
        {
            Float[] chiavi1 = chiavi.getArray();
            
            System.out.println(chiavi1[0] + " "+  chiavi1[1]+" " + chiavi1[2]+"  " +chiavi1[3]);
        }*/
        //System.out.println("chiave cercata " + chiave[0]+" " +chiave[1]+" " +chiave[2]+" " +chiave[3]);
        
       /* for(float[]chiave1:ordineAlternative.keySet())
        {
            System.out.println("chiavi presenti in ordineAlternative " + chiave1[0]+" " +chiave1[1]+" " +chiave1[2]+" " +chiave1[3]);
        }*/
        
        if(listaAlternative == null)
        {
            System.out.println("  ---------chiave non trovata ");
            return;
        }
        
        System.out.println("  ---------stampa ordine    ---------stampa ordine    ---------stampa ordine");
        
        
        ListIterator<Alternativa> iterAlt = listaAlternative.listIterator();
        System.out.println(" emissione a min " + this.emissioniaminm3 + " emissioni a max " + this.emissioniamaxm3 + " emissioni g min " + this.emissionigminm3 + " emissioni g max " + this.emissionigmaxm3 + " costo min " + this.costominm3 + " costo max " + this.costomaxm3 + " energia min " + this.energiaminm3 + " energia max" + this.energiamaxm3);
        while(iterAlt.hasNext())
        {
            Alternativa al = iterAlt.next();
            
            System.out.println(" pesoea " + pesoea + " pesoeg " + pesoeg + " pesoen " + pesoen + " pesoc " + pesoc + " alternativa numero " + al.getNumeroalternativa() + " valore funz " + al.getValore() + " emissione A " +al.getEmissioni_acide_m3() + " emissioni G" + al.getEmissioni_gas_m3() + " costo " + al.getCosto_m3() + " energia " + al.getEnergia_m3() );
        }
        
        System.out.println("  ---------stampa ordine    ---------stampa ordine    ---------stampa ordine"); 
    }
    
    
    /**
     * calcola la funzione obiettivo di una determinata alternativa ritornda il valore calcolato
     * @param pesocosto
     * @param pesoemissionea
     * @param pesoemissioneg
     * @param pesoenergia
     * @param alternativa contiene i valori di costo,emissione a , emissione g, energia
     * @param metrocubo se true il valore della funzione obiettivo deve essere calcolato sui valori al metro cubo se false su quelli lineari
     */
    private double calcolaObiettivo(double pesocosto,double pesoemissionea,double pesoemissioneg,double pesoenergia,Alternativa alternativa,boolean metrocubo)
    {
        double ritorno = 0;
        
        if(metrocubo)
        {
            ritorno = pesocosto * (alternativa.getCosto_m3() - this.costominm3)/(this.costomaxm3 - this.costominm3) + pesoemissionea * (alternativa.getEmissioni_acide_m3() - this.emissioniaminm3) / (this.emissioniamaxm3 - this.emissioniaminm3) + pesoemissioneg * (alternativa.getEmissioni_gas_m3() - this.emissionigminm3)/(this.emissionigmaxm3 - this.emissionigminm3) + pesoenergia * (alternativa.getEnergia_m3() - this.energiaminm3) / (this.energiamaxm3 - this.energiaminm3);
            
        }else
        {
           ritorno = pesocosto * (alternativa.getCosto() - this.costomin)/(this.costomax - this.costomin) + pesoemissionea * (alternativa.getEmissioni_acide() - this.emissioniamin) / (this.emissioniamax - this.emissioniamin) + pesoemissioneg * (alternativa.getEmissioni_gas() - this.emissionigmin)/(this.emissionigmax - this.emissionigmin) + pesoenergia * (alternativa.getEnergia() - this.energiamin) / (this.energiamax - this.energiamin);
 
        }
        
        
        return ritorno;
    }
    
    /**
     * ordina le alternative in funzione del valore calcolato e presente nell'alternativa stesso calcolato 
     * in funzione della funzione obiettivo
     * @param alternative
     * @return 
     */
    private List<Alternativa> ordinaAlternative(List<Alternativa> alternativeA)
    {
      //  System.out.println("\n\n\n ordino alternative -------------------------------------");
        Collections.sort(alternativeA);
      
       /* List<Alternativa> listaClone = new LinkedList();
        
        for(int i = 0; i < alternativeA.size();i++)
            listaClone.add(alternativeA.get(i));*/
        
        
     /*    ListIterator<Alternativa> iterAlt = alternative.listIterator();
        System.out.println(" emissione a min " + this.emissioniaminm3 + " emissioni a max " + this.emissioniamaxm3 + " emissioni g min " + this.emissionigminm3 + " emissioni g max " + this.emissionigmaxm3 + " costo min " + this.costominm3 + " costo max " + this.costomaxm3 + " energia min " + this.energiaminm3 + " energia max" + this.energiamaxm3);
        while(iterAlt.hasNext())
        {
            Alternativa al = iterAlt.next();
            
            System.out.println(" alternativa numero " + al.getNumeroalternativa() + " valore funz " + al.getValore() + " emissione A " +al.getEmissioni_acide_m3() + " emissioni G" + al.getEmissioni_gas_m3() + " costo " + al.getCosto_m3() + " energia " + al.getEnergia_m3() );
        }
          System.out.println("\n\n\n ordino alternative -------------------------------------");*/
        return alternativeA;
    }        
            
    /**
     * recupera i valori minimi del costo,energia, emissioni dai valori delle alternative
     * e li setta nelle variabili locali sia i valori al metro cubo che non 
     * @return 
     */
    private void recuperaMin()
    {
        ListIterator<Alternativa> alternativa = alternative.listIterator();
        /**
         * prima di ciclare sulla lista delle alternative prendo il primo valore delle alternative
         * e lo impost alle variabili locali
         */
        this.costomin = alternative.get(0).getCosto();
        this.costominm3 =  alternative.get(0).getCosto_m3();
        this.emissioniamin =  alternative.get(0).getEmissioni_acide();
        this.emissioniaminm3 =  alternative.get(0).getEmissioni_acide_m3();
        this.emissionigmin =  alternative.get(0).getEmissioni_gas();
        this.emissionigminm3 =  alternative.get(0).getEmissioni_gas_m3();
        this.energiamin =  alternative.get(0).getEnergia();
        this.energiaminm3 =  alternative.get(0).getEnergia_m3();
        
        //mi serve per muovermi sulla lista delle alternative
        Alternativa alttemp ;
        
        /**
         * ciclo sull'alternativa tenedo nelle variabili clocali i valori minimi di costo,
         * emissioni acide, emissioni gas serra, energia
         */
        while (alternativa.hasNext())
                {
                    alttemp = alternativa.next();
                    
                    if(this.costomin > alttemp.getCosto())
                    {
                        this.costomin = alttemp.getCosto();
                    }
                    
                    
                    if(this.costominm3 > alttemp.getCosto_m3())
                    {
                        this.costominm3 = alttemp.getCosto_m3();
                    }
                    
                    if(this.emissioniamin > alttemp.getEmissioni_acide())
                    {
                        this.emissioniamin = alttemp.getEmissioni_acide();
                    }
                    
                    
                    if(this.emissioniaminm3 > alttemp.getEmissioni_acide_m3())
                    {
                        this.emissioniaminm3 = alttemp.getEmissioni_acide_m3();
                    }
                    
                    if(this.emissionigmin > alttemp.getEmissioni_gas())
                    {
                        this.emissionigmin = alttemp.getEmissioni_gas();
                    }
                    
                    
                    if(this.emissionigminm3 > alttemp.getEmissioni_gas_m3())
                    {
                        this.emissionigminm3 = alttemp.getEmissioni_gas_m3();
                    }
                    
                    if(this.energiamin > alttemp.getEnergia())
                    {
                        this.energiamin = alttemp.getEnergia();
                    }
                    
                    if(this.energiaminm3 > alttemp.getEnergia_m3())
                    {
                        this.energiaminm3 = alttemp.getEnergia_m3();
                    }
                    
                }
    }
    
     private void recuperaMax()
    {
        ListIterator<Alternativa> alternativa = alternative.listIterator();
        /**
         * prima di ciclare sulla lista delle alternative prendo il primo valore delle alternative
         * e lo impost alle variabili locali
         */
        this.costomax = alternative.get(0).getCosto();
        this.costomaxm3 =  alternative.get(0).getCosto_m3();
        this.emissioniamax =  alternative.get(0).getEmissioni_acide();
        this.emissioniamaxm3 =  alternative.get(0).getEmissioni_acide_m3();
        this.emissionigmax =  alternative.get(0).getEmissioni_gas();
        this.emissionigmaxm3 =  alternative.get(0).getEmissioni_gas_m3();
        this.energiamax =  alternative.get(0).getEnergia();
        this.energiamaxm3 =  alternative.get(0).getEnergia_m3();
        
        //mi serve per muovermi sulla lista delle alternative
        Alternativa alttemp ;
        
        /**
         * ciclo sull'alternativa tenedo nelle variabili clocali i valori minimi di costo,
         * emissioni acide, emissioni gas serra, energia
         */
        while (alternativa.hasNext())
                {
                    alttemp = alternativa.next();
                    
                    if(this.costomax < alttemp.getCosto())
                    {
                        this.costomax = alttemp.getCosto();
                    }
                    
                    
                    if(this.costomaxm3 < alttemp.getCosto_m3())
                    {
                        this.costomaxm3 = alttemp.getCosto_m3();
                    }
                    
                    if(this.emissioniamax < alttemp.getEmissioni_acide())
                    {
                        this.emissioniamax = alttemp.getEmissioni_acide();
                    }
                    
                    
                    if(this.emissioniamaxm3 < alttemp.getEmissioni_acide_m3())
                    {
                        this.emissioniamaxm3 = alttemp.getEmissioni_acide_m3();
                    }
                    
                    if(this.emissionigmax < alttemp.getEmissioni_gas())
                    {
                        this.emissionigmax = alttemp.getEmissioni_gas();
                    }
                    
                    
                    if(this.emissionigmaxm3 < alttemp.getEmissioni_gas_m3())
                    {
                        this.emissionigmaxm3 = alttemp.getEmissioni_gas_m3();
                    }
                    
                    if(this.energiamax < alttemp.getEnergia())
                    {
                        this.energiamax = alttemp.getEnergia();
                    }
                    
                    if(this.energiamaxm3 < alttemp.getEnergia_m3())
                    {
                        this.energiamaxm3 = alttemp.getEnergia_m3();
                    }
                    
                }
    }

    /**
     * @return the ordineAlternative
     */
    public LinkedHashMap<ArrayHolder<Float>,List<Alternativa>> getOrdineAlternative() {
        return ordineAlternative;
    }

    /**
     * @return the mediaAlternative
     */
    public List<Alternativa> getMediaAlternative() {
        return mediaAlternative;
    }

    /**
     * @param mediaAlternative the mediaAlternative to set
     */
    public void setMediaAlternative(List<Alternativa> mediaAlternative) {
        this.mediaAlternative = mediaAlternative;
    }

    
    
    
    
    
    






}