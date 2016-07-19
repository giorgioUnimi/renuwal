/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;



/**
 *
 * @author giorgio
 */
public class LetameT extends Trattamento{
  
  private static LetameT istanza;
  
  
  

  private LetameT(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq); 
      
    
    
  }

  public static LetameT getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new LetameT(/*totlet,totliq*/);
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
