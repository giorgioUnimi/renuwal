/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;



/**
 *
 * @author giorgio
 */
public class LiquameT extends Trattamento{
    
    private static LiquameT istanza;
  
  

  private LiquameT(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
  }

  public static LiquameT getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new LiquameT(/*totlet,totliq*/);
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
