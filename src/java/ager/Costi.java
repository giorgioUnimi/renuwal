/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

/**
 *Classe di supporto ai moduli delle alternative per la visulizzazione dei costi dopo i lrecupero da xml
 * @author giorgio
 */
public class Costi {
    
    private double investimento = 0;
    private double manutenzione = 0;
    private double totale = 0;

    /**
     * @return the investimento
     */
    public double getInvestimento() {
        return investimento;
    }

    /**
     * @param investimento the investimento to set
     */
    public void setInvestimento(double investimento) {
        this.investimento = investimento;
    }

    /**
     * @return the manutenzione
     */
    public double getManutenzione() {
        return manutenzione;
    }

    /**
     * @param manutenzione the manutenzione to set
     */
    public void setManutenzione(double manutenzione) {
        this.manutenzione = manutenzione;
    }

    /**
     * @return the totale
     */
    public double getTotale() {
        return totale;
    }

    /**
     * @param totale the totale to set
     */
    public void setTotale(double totale) {
        this.totale = totale;
    }
    
}
