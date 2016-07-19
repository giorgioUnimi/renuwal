/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

/**
 *Classe di supporto ai moduli delle alternative per la visulizzazione dell'energia dopo i lrecupero da xml
 * @author giorgio
 */
public class Energia {
    
    private double prodotta = 0;
    private double consumata = 0;
    private double venduta = 0;

    /**
     * @return the prodotta
     */
    public double getProdotta() {
        return prodotta;
    }

    /**
     * @param prodotta the prodotta to set
     */
    public void setProdotta(double prodotta) {
        this.prodotta = prodotta;
    }

    /**
     * @return the consumata
     */
    public double getConsumata() {
        return consumata;
    }

    /**
     * @param consumata the consumata to set
     */
    public void setConsumata(double consumata) {
        this.consumata = consumata;
    }

    /**
     * @return the venduta
     */
    public double getVenduta() {
        return venduta;
    }

    /**
     * @param venduta the venduta to set
     */
    public void setVenduta(double venduta) {
        this.venduta = venduta;
    }
    
    
    
}
