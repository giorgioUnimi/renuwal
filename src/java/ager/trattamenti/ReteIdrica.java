/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;



/**
 *
 * @author giorgio
 */
public class ReteIdrica extends Trattamento{
 
         private static ReteIdrica istanza;
  
 

  private ReteIdrica(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    super(/*totlet,totliq*/);
  }

  public static ReteIdrica getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new ReteIdrica(/*totlet,totliq*/);
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
