/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;


/**
 *
 * @author giorgio
 */
public class Sbr extends Trattamento{
    
       private static Sbr istanza;
  
 

  private Sbr(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
  }

  public static Sbr getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Sbr(/*totlet,totliq*/);
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
