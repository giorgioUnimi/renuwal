/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;



/**
 *
 * @author giorgio
 */
public class Da extends Trattamento{
    
    private static Da istanza;
  
 

  private Da(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     super(/*totlet,totliq*/);
  }

  public static Da getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new Da(/*totlet,totliq*/);
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
