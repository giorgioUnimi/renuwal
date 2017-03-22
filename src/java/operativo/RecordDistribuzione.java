/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *rappresenta le tre distribuzioni di refluo su un appezzamento
 * @author giorgio
 */
public class RecordDistribuzione {
    
    /**
     * sono le 4 distribuzioni di refluo sull appezzamento
     */
    private List<Distribuzione> distribuzioni = new LinkedList();
    private String nomeAppezzamento;
    private double superficie;
    private String coltura;
    private int rotazione;
    private int upa;
    private double resa_attesa;
    private double asportazioniNKg;
    private double asportazioniNKgsuHA;
    private double asportazioniPKg;
    private double asportazioniPKgsuHA;
    private double asportazioniKKg;
    private double asportazioniKKgsuHA; 
    
    public RecordDistribuzione(db.RisultatoConfronto risultatoConfronto,db.Coltura coltura)
    {
        distribuzioni.add(new Distribuzione(risultatoConfronto,coltura));
        distribuzioni.add(new Distribuzione(risultatoConfronto,coltura));
        distribuzioni.add(new Distribuzione(risultatoConfronto,coltura));
        distribuzioni.add(new Distribuzione(risultatoConfronto,coltura));
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
        
       ListIterator<Distribuzione> iterDis = this.distribuzioni.listIterator();
       while(iterDis.hasNext())
       {
           iterDis.next().setSuperficie(superficie);
       }    
       
       
            
       
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
     * @return the asportazioniNKg
     */
    public double getAsportazioniNKg() {
        return asportazioniNKg;
    }

    /**
     * @param asportazioniNKg the asportazioniNKg to set
     */
    public void setAsportazioniNKg(double asportazioniNKg) {
        this.asportazioniNKg = asportazioniNKg;
    }

    /**
     * @return the asportazioniNKgsuHA
     */
    public double getAsportazioniNKgsuHA() {
        return asportazioniNKgsuHA;
    }

    /**
     * @param asportazioniNKgsuHA the asportazioniNKgsuHA to set
     */
    public void setAsportazioniNKgsuHA(double asportazioniNKgsuHA) {
        this.asportazioniNKgsuHA = asportazioniNKgsuHA;
    }

    /**
     * @return the asportazioniPKg
     */
    public double getAsportazioniPKg() {
        return asportazioniPKg;
    }

    /**
     * @param asportazioniPKg the asportazioniPKg to set
     */
    public void setAsportazioniPKg(double asportazioniPKg) {
        this.asportazioniPKg = asportazioniPKg;
    }

    /**
     * @return the asportazioniPKgsuHA
     */
    public double getAsportazioniPKgsuHA() {
        return asportazioniPKgsuHA;
    }

    /**
     * @param asportazioniPKgsuHA the asportazioniPKgsuHA to set
     */
    public void setAsportazioniPKgsuHA(double asportazioniPKgsuHA) {
        this.asportazioniPKgsuHA = asportazioniPKgsuHA;
    }

    /**
     * @return the asportazioniKKg
     */
    public double getAsportazioniKKg() {
        return asportazioniKKg;
    }

    /**
     * @param asportazioniKKg the asportazioniKKg to set
     */
    public void setAsportazioniKKg(double asportazioniKKg) {
        this.asportazioniKKg = asportazioniKKg;
    }

    /**
     * @return the asportazioniKKgsuHA
     */
    public double getAsportazioniKKgsuHA() {
        return asportazioniKKgsuHA;
    }

    /**
     * @param asportazioniKKgsuHA the asportazioniKKgsuHA to set
     */
    public void setAsportazioniKKgsuHA(double asportazioniKKgsuHA) {
        this.asportazioniKKgsuHA = asportazioniKKgsuHA;
    }

    /**
     * @return the upa
     */
    public int getUpa() {
        return upa;
    }

    /**
     * @param upa the upa to set
     */
    public void setUpa(int upa) {
        this.upa = upa;
    }

    
    
}
