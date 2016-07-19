/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

/**
 *
 * @author giorgio
 */
public class Refluo {
    
   
    
    private double metricubi=0;
    //private double metricubibiomassa = 0;
    private double azotototale = 0;
    private double azotoammoniacale = 0;
    private double sostanzasecca = 0;
    private double solidivolatili = 0;
    private double fosforototale = 0;
    private double potassiototale  =0;
    private String tipologia = "";
    
   
   public Refluo(String tipologia)
   {
       this.tipologia = tipologia;
   }
    
    
   
    
    /**
     * @return the metricubi
     */
    public double getMetricubi() {
        return metricubi;
    }

    /**
     * @param metricubi the metricubi to set
     */
    public void setMetricubi(double metricubi) {
        this.metricubi = metricubi;
    }
    
    
    public void addMetricubi(double metricubiA)
    {
        this.metricubi = this.metricubi + metricubiA;
    }
    
    /**
     * @return the metricubibiomassa
     */
   /* public double getMetricubibiomassa() {
        return metricubibiomassa;
    }

    /**
     * @param metricubibiomassa the metricubibiomassa to set
     */
    /*public void setMetricubibiomassa(double metricubibiomassa) {
        this.metricubibiomassa = metricubibiomassa;
    }
    
    
    /*public void addMetricubibiomassa(double metricubibiomassaA)
    {
        this.metricubibiomassa = this.metricubibiomassa + metricubibiomassaA;
    }
    
    /**
     * @return the azotototale
     */
    public double getAzotototale() {
        return azotototale;
    }

    /**
     * @param azotototale the azotototale to set
     */
    public void setAzotototale(double azotototale) {
        this.azotototale = azotototale;
    }
    
    
    public void addAzotoTotale(double azotototaleA)
    {
        this.azotototale = this.azotototale + azotototaleA;
    }
    
    
    /**
     * @return the azotoammoniacale
     */
    public double getAzotoammoniacale() {
        return azotoammoniacale;
    }

    /**
     * @param azotoammoniacale the azotoammoniacale to set
     */
    public void setAzotoammoniacale(double azotoammoniacale) {
        this.azotoammoniacale = azotoammoniacale;
    }
    
    
    public void addAzotoAmmoniacale(double azotoammoniacaleA)
    {
        this.azotoammoniacale = this.azotoammoniacale + azotoammoniacaleA;
    }
    
    
    /**
     * @return the sostanzasecca
     */
    public double getSostanzasecca() {
        return sostanzasecca;
    }

    /**
     * @param sostanzasecca the sostanzasecca to set
     */
    public void setSostanzasecca(double sostanzasecca) {
        this.sostanzasecca = sostanzasecca;
    }

    
    public void addSostanzaSecca(double sostanzaSeccaA)
    {
        this.sostanzasecca = this.sostanzasecca + sostanzaSeccaA;
    }
    
    /**
     * @return the solidivolatili
     */
    public double getSolidivolatili() {
        return solidivolatili;
    }

    /**
     * @param solidivolatili the solidivolatili to set
     */
    public void setSolidivolatili(double solidivolatili) {
        this.solidivolatili = solidivolatili;
    }
    
    
    public void addSolidiVolatili(double solidiVolatiliA)
    {
        this.solidivolatili = this.solidivolatili + solidiVolatiliA;
    }
    
    /**
     * @return the fosforototale
     */
    public double getFosforototale() {
        return fosforototale;
    }

    /**
     * @param fosforototale the fosforototale to set
     */
    public void setFosforototale(double fosforototale) {
        this.fosforototale = fosforototale;
    }
    
    
    public void addFosforoTotale(double fosforoTotaleA)
    {
        this.fosforototale = this.fosforototale + fosforoTotaleA;
    }
    
    
    /**
     * @return the potassiototale
     */
    public double getPotassiototale() {
        return potassiototale;
    }

    /**
     * @param potassiototale the potassiototale to set
     */
    public void setPotassiototale(double potassiototale) {
        this.potassiototale = potassiototale;
    }
    
    
    public void addPotassioTotale(double potassiototaleA)
    {
        this.potassiototale = this.potassiototale + potassiototaleA;
    }
    
    /**
     * @return the tipologia
     */
    public String getTipologia() {
        return tipologia;
    }

    /**
     * @param tipologia the tipologia to set
     */
    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    

    public void addAllPP(Refluo refluo,double percentuale,String nome)
    {
        
       // System.out.println(" classe refluo azotoamm " + this.azotoammoniacale + " azotototale " + this.azotototale + " metricubi " + this.metricubi + " prima ");
      //  System.out.println(" classe refluo nome trattamento "+ nome +" azotoamm " + refluo.azotoammoniacale + " azotototale " + refluo.azotototale + " metricubi " + refluo.metricubi + " della classe da aggiungere ");

        this.azotoammoniacale = this.azotoammoniacale + (refluo.azotoammoniacale / 100)*percentuale;
        this.azotototale = this.azotototale + (refluo.azotototale / 100)*percentuale;
        this.fosforototale = this.fosforototale + (refluo.fosforototale / 100)*percentuale;
        this.metricubi = this.metricubi + (refluo.metricubi / 100)*percentuale;
       // this.metricubibiomassa = this.metricubibiomassa + (refluo.metricubibiomassa / 100)*percentuale;
        this.potassiototale = this.potassiototale + (refluo.potassiototale / 100)*percentuale;
        this.solidivolatili = this.solidivolatili + (refluo.solidivolatili / 100)*percentuale;
        this.sostanzasecca = this.sostanzasecca + (refluo.sostanzasecca / 100)*percentuale;
        
        
      //  System.out.println(" classe refluo azotoamm " + this.azotoammoniacale + " azotototale " + this.azotototale + " metricubi " + this.metricubi + "percentuale " +percentuale +"  dopo ");
        
    }

   /**
    * aggiunge un refluo a se stesso
    * @param refluo 
    */
   public void addRefluo(Refluo refluo)
   {
       
       if(refluo.metricubi != 0)
       {
        this.azotoammoniacale = this.azotoammoniacale + refluo.azotoammoniacale ;
        this.azotototale = this.azotototale + refluo.azotototale ;
        this.fosforototale = this.fosforototale + refluo.fosforototale ;
        this.metricubi = this.metricubi + refluo.metricubi ;
       // this.metricubibiomassa = this.metricubibiomassa + (refluo.metricubibiomassa / 100)*percentuale;
        this.potassiototale = this.potassiototale + refluo.potassiototale ;
        this.solidivolatili = this.solidivolatili + refluo.solidivolatili ;
        this.sostanzasecca = this.sostanzasecca + refluo.sostanzasecca ;
       }
   }

    
    
}
