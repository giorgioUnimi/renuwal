/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.LinkedList;
import java.util.List;

/**
 *rappresenta le tre distribuzioni di refluo su un appezzamento
 * @author giorgio
 */
public class RecordDistribuzione {
    
    /**
     * sono le 3 distribuzioni di refluo sull appezzamento
     */
    private List<Distribuzione> distribuzioni = new LinkedList();
    private String nomeAppezzamento;
    private double superficie;
    private String coltura;
    private int rotazione;
    private double resa_attesa;
    private double asportazioniN;
    
    public RecordDistribuzione()
    {
        distribuzioni.add(new Distribuzione());
        distribuzioni.add(new Distribuzione());
        distribuzioni.add(new Distribuzione());
    }

    /**
     * @return the distribuzioni
     */
    public List<Distribuzione> getDistribuzioni() {
        return distribuzioni;
    }

    /**
     * @param distribuzioni the distribuzioni to set
     */
    public void setDistribuzioni(List<Distribuzione> distribuzioni) {
        this.distribuzioni = distribuzioni;
    }

    /**
     * @return the nomeAppezzamento
     */
    public String getNomeAppezzamento() {
        return nomeAppezzamento;
    }

    /**
     * @param nomeAppezzamento the nomeAppezzamento to set
     */
    public void setNomeAppezzamento(String nomeAppezzamento) {
        this.nomeAppezzamento = nomeAppezzamento;
    }

    /**
     * @return the superficie
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * @param superficie the superficie to set
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * @return the coltura
     */
    public String getColtura() {
        return coltura;
    }

    /**
     * @param coltura the coltura to set
     */
    public void setColtura(String coltura) {
        this.coltura = coltura;
    }

    /**
     * @return the rotazione
     */
    public int getRotazione() {
        return rotazione;
    }

    /**
     * @param rotazione the rotazione to set
     */
    public void setRotazione(int rotazione) {
        this.rotazione = rotazione;
    }

    /**
     * @return the resa_attesa
     */
    public double getResa_attesa() {
        return resa_attesa;
    }

    /**
     * @param resa_attesa the resa_attesa to set
     */
    public void setResa_attesa(double resa_attesa) {
        this.resa_attesa = resa_attesa;
    }

    /**
     * @return the asportazioniN
     */
    public double getAsportazioniN() {
        return asportazioniN;
    }

    /**
     * @param asportazioniN the asportazioniN to set
     */
    public void setAsportazioniN(double asportazioniN) {
        this.asportazioniN = asportazioniN;
    }
    
}
