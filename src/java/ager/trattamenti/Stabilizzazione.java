/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;



/**
 *
 * @author giorgio
 */
public class Stabilizzazione extends Trattamento{
    
   private static Stabilizzazione istanza;
  
 

  private Stabilizzazione(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    //super(totlet,totliq);
  }

  public static Stabilizzazione getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Stabilizzazione(/*totlet,totliq*/);
    }

    return istanza;
  }
  
  public void calcolaRefluo() {
       
         this.setContenitoreRefluiOut(this.getContenitoreRefluiIn());
        
    }

   
    public void calcolaEmissioni() {
       
    }

   
    public void calcolaGestionali() {
        
    }
  
}
