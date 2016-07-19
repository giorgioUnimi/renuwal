/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;


/**
 *
 * @author giorgio
 */
public class NMinerale extends Trattamento {
    
    private static NMinerale istanza;
  
 

  private NMinerale(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
     //super(totlet,totliq);
  }

  public static NMinerale getInstance(/*Totaleletame totlet,Totaleliquame totliq*/)
  {
    if (istanza == null)
    {
      istanza = new NMinerale(/*totlet,totliq*/);
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
