/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

/**
 *ha lo scopo di rappresentare i valori di una distribuzione di refluo 
 * su un determinato appezzamento 
 * @author giorgio
 */
public class Distribuzione {
    private int mese ;
    private int refluo_distribuito;
    private int modalita_distribuzione;
    private double efficienzaN;
    private double N_netto;
    private double N_al_campo;
    private double quantita_refluo;

    /**
     * @return the mese
     */
    public int getMese() {
        return mese;
    }

    /**
     * @param mese the mese to set
     */
    public void setMese(int mese) {
        this.mese = mese;
    }

    /**
     * @return the refluo_distribuito
     */
    public int getRefluo_distribuito() {
        return refluo_distribuito;
    }

    /**
     * @param refluo_distribuito the refluo_distribuito to set
     */
    public void setRefluo_distribuito(int refluo_distribuito) {
        this.refluo_distribuito = refluo_distribuito;
    }

    /**
     * @return the modalita_distribuzione
     */
    public int getModalita_distribuzione() {
        return modalita_distribuzione;
    }

    /**
     * @param modalita_distribuzione the modalita_distribuzione to set
     */
    public void setModalita_distribuzione(int modalita_distribuzione) {
        this.modalita_distribuzione = modalita_distribuzione;
    }

    /**
     * @return the efficienzaN
     */
    public double getEfficienzaN() {
        return efficienzaN;
    }

    /**
     * @param efficienzaN the efficienzaN to set
     */
    public void setEfficienzaN(double efficienzaN) {
        this.efficienzaN = efficienzaN;
    }

    /**
     * @return the N_netto
     */
    public double getN_netto() {
        return N_netto;
    }

    /**
     * @param N_netto the N_netto to set
     */
    public void setN_netto(double N_netto) {
        this.N_netto = N_netto;
    }

    /**
     * @return the N_al_campo
     */
    public double getN_al_campo() {
        return N_al_campo;
    }

    /**
     * @param N_al_campo the N_al_campo to set
     */
    public void setN_al_campo(double N_al_campo) {
        this.N_al_campo = N_al_campo;
    }

    /**
     * @return the quantita_refluo
     */
    public double getQuantita_refluo() {
        return quantita_refluo;
    }

    /**
     * @param quantita_refluo the quantita_refluo to set
     */
    public void setQuantita_refluo(double quantita_refluo) {
        this.quantita_refluo = quantita_refluo;
    }
    
}
