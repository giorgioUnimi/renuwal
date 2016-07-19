/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author giorgio
 */
public class Test {
    
   private String nome = "";
   private String cognome = "";
   private double eta = 35;
   private int x = 0;
   private int y = 0;
    
    public Test(int a, int b)
    {
        this.x = a;
        this.y = b;
    }
    
    
    public int somma(int a,int b)
    {
        return a+b;
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the eta
     */
    public double getEta() {
        return eta;
    }

    /**
     * @param eta the eta to set
     */
    public void setEta(double eta) {
        this.eta = eta;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    
    
    
}
