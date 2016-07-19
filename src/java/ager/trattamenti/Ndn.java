/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;


/**
 *
 * @author giorgio
 */
public class Ndn extends Trattamento{
    
      private static Ndn istanza;
  
  

  private Ndn(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
  }

  public static Ndn getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Ndn(/*totlet,totliq*/);
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
