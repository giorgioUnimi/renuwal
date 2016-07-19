/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobiettivo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Nasce con lo scopo di essere di supporto alla multiobiettivo e quindi come contenitore del tipo di alternativa
 * dei totoali e dei totali al metro cubo di emissioni acide, emissioni gas serra,energia , costo
 * @author giorgio
 */
public class Alternativa  implements Comparable<Alternativa>,Cloneable{
    //il valore numerico dell'alternativa
    private int numeroalternativa;
    //lista dei moduli che la compongono
    private List<String> composizione;
    //emissioni
    private double nh3;
    private double ch4;
    private double n2o;
    private double no;
    private double co2;
    //energia
    private double energia_prodotta;
    private double energia_consumata;
    private double energia_venduta;
    //costi
    private double investimento;
    private double gestione;
    private double ricavo;
    
    
     //emissioni m3
    private double nh3_m3;
    private double ch4_m3;
    private double n2o_m3;
    private double no_m3;
    private double co2_m3;
    //energia m3
    private double energia_prodotta_m3;
    private double energia_consumata_m3;
    private double energia_venduta_m3;
    //costi m3
    private double investimento_m3;
    private double gestione_m3;
    private double ricavo_m3;
    
    //riassumono e sono il frutto delle variabili definite precdentemente
    private double emissioni_acide;
    private double emissioni_gas;
    private double costo;
    private double energia;
    
    
    
     //riassumono al m3 e sono il frutto delle variabili al m3  definite precdentemente
    private double emissioni_acide_m3;
    private double emissioni_gas_m3;
    private double costo_m3;
    private double energia_m3;
    
    
    //valore numerico che identifica il valore dell'alternativa
    private double valore;
    
    /**
     * le quattro seguenti variabili mi servo come contenitore
     * a livello statistico per tenere traccia delle alternative migliori e delle medie
     * dei loro pesi
     */
    private float pesomedio_emissioniacide;
    private float pesomedio_emisisonigas;
    private float pesomedio_energia;
    private float pesomedio_costo;
    
    /**
     * numero di volte che compare questa alternativa
     * mi serve per l'analisi statisctica
    */
    private int numero_volte = 1;
    /**
     * @return the numeroalternativa
     */
    public int getNumeroalternativa() {
        return numeroalternativa;
    }

    /**
     * @param numeroalternativa the numeroalternativa to set
     */
    public void setNumeroalternativa(int numeroalternativa) {
        this.numeroalternativa = numeroalternativa;
    }

    /**
     * @return the composizione
     */
    public List<String> getComposizione() {
        return composizione;
    }

    /**
     * @param composizione the composizione to set
     */
    public void setComposizione(List<String> composizione) {
        this.composizione = composizione;
    }

    /**
     * @return the nh3
     */
    public double getNh3() {
        return nh3;
    }

    /**
     * @param nh3 the nh3 to set
     */
    public void setNh3(double nh3) {
        this.nh3 = nh3;
    }

    /**
     * @return the ch4
     */
    public double getCh4() {
        return ch4;
    }

    /**
     * @param ch4 the ch4 to set
     */
    public void setCh4(double ch4) {
        this.ch4 = ch4;
    }

    /**
     * @return the n2o
     */
    public double getN2o() {
        return n2o;
    }

    /**
     * @param n2o the n2o to set
     */
    public void setN2o(double n2o) {
        this.n2o = n2o;
    }

    /**
     * @return the no
     */
    public double getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(double no) {
        this.no = no;
    }

    /**
     * @return the co2
     */
    public double getCo2() {
        return co2;
    }

    /**
     * @param co2 the co2 to set
     */
    public void setCo2(double co2) {
        this.co2 = co2;
    }

    /**
     * @return the energia_prodotta
     */
    public double getEnergia_prodotta() {
        return energia_prodotta;
    }

    /**
     * @param energia_prodotta the energia_prodotta to set
     */
    public void setEnergia_prodotta(double energia_prodotta) {
        this.energia_prodotta = energia_prodotta;
    }

    /**
     * @return the energia_consumata
     */
    public double getEnergia_consumata() {
        return energia_consumata;
    }

    /**
     * @param energia_consumata the energia_consumata to set
     */
    public void setEnergia_consumata(double energia_consumata) {
        this.energia_consumata = energia_consumata;
    }

    /**
     * @return the energia_venduta
     */
    public double getEnergia_venduta() {
        return energia_venduta;
    }

    /**
     * @param energia_venduta the energia_venduta to set
     */
    public void setEnergia_venduta(double energia_venduta) {
        this.energia_venduta = energia_venduta;
    }

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
     * @return the gestione
     */
    public double getGestione() {
        return gestione;
    }

    /**
     * @param gestione the gestione to set
     */
    public void setGestione(double gestione) {
        this.gestione = gestione;
    }

    /**
     * @return the ricavo
     */
    public double getRicavo() {
        return ricavo;
    }

    /**
     * @param ricavo the ricavo to set
     */
    public void setRicavo(double ricavo) {
        this.ricavo = ricavo;
    }

    /**
     * @return the nh3_m3
     */
    public double getNh3_m3() {
        return nh3_m3;
    }

    /**
     * @param nh3_m3 the nh3_m3 to set
     */
    public void setNh3_m3(double nh3_m3) {
        this.nh3_m3 = nh3_m3;
    }

    /**
     * @return the ch4_m3
     */
    public double getCh4_m3() {
        return ch4_m3;
    }

    /**
     * @param ch4_m3 the ch4_m3 to set
     */
    public void setCh4_m3(double ch4_m3) {
        this.ch4_m3 = ch4_m3;
    }

    /**
     * @return the n2o_m3
     */
    public double getN2o_m3() {
        return n2o_m3;
    }

    /**
     * @param n2o_m3 the n2o_m3 to set
     */
    public void setN2o_m3(double n2o_m3) {
        this.n2o_m3 = n2o_m3;
    }

    /**
     * @return the no_m3
     */
    public double getNo_m3() {
        return no_m3;
    }

    /**
     * @param no_m3 the no_m3 to set
     */
    public void setNo_m3(double no_m3) {
        this.no_m3 = no_m3;
    }

    /**
     * @return the co2_m3
     */
    public double getCo2_m3() {
        return co2_m3;
    }

    /**
     * @param co2_m3 the co2_m3 to set
     */
    public void setCo2_m3(double co2_m3) {
        this.co2_m3 = co2_m3;
    }

    /**
     * @return the energia_prodotta_m3
     */
    public double getEnergia_prodotta_m3() {
        return energia_prodotta_m3;
    }

    /**
     * @param energia_prodotta_m3 the energia_prodotta_m3 to set
     */
    public void setEnergia_prodotta_m3(double energia_prodotta_m3) {
        this.energia_prodotta_m3 = energia_prodotta_m3;
    }

    /**
     * @return the energia_consumata_m3
     */
    public double getEnergia_consumata_m3() {
        return energia_consumata_m3;
    }

    /**
     * @param energia_consumata_m3 the energia_consumata_m3 to set
     */
    public void setEnergia_consumata_m3(double energia_consumata_m3) {
        this.energia_consumata_m3 = energia_consumata_m3;
    }

    /**
     * @return the energia_venduta_m3
     */
    public double getEnergia_venduta_m3() {
        return energia_venduta_m3;
    }

    /**
     * @param energia_venduta_m3 the energia_venduta_m3 to set
     */
    public void setEnergia_venduta_m3(double energia_venduta_m3) {
        this.energia_venduta_m3 = energia_venduta_m3;
    }

    /**
     * @return the investimento_m3
     */
    public double getInvestimento_m3() {
        return investimento_m3;
    }

    /**
     * @param investimento_m3 the investimento_m3 to set
     */
    public void setInvestimento_m3(double investimento_m3) {
        this.investimento_m3 = investimento_m3;
    }

    /**
     * @return the gestione_m3
     */
    public double getGestione_m3() {
        return gestione_m3;
    }

    /**
     * @param gestione_m3 the gestione_m3 to set
     */
    public void setGestione_m3(double gestione_m3) {
        this.gestione_m3 = gestione_m3;
    }

    /**
     * @return the ricavo_m3
     */
    public double getRicavo_m3() {
        return ricavo_m3;
    }

    /**
     * @param ricavo_m3 the ricavo_m3 to set
     */
    public void setRicavo_m3(double ricavo_m3) {
        this.ricavo_m3 = ricavo_m3;
    }

    /**
     * @return the emissioni_acide
     */
    public double getEmissioni_acide() {
        emissioni_acide = this.nh3;
        
        return emissioni_acide;
    }

    /**
     * @param emissioni_acide the emissioni_acide to set
     */
    public void setEmissioni_acide(double emissioni_acide) {
        this.emissioni_acide = emissioni_acide;
    }

    /**
     * @return the emissioni_gas
     */
    public double getEmissioni_gas() {
        
        emissioni_gas = this.ch4 + this.co2 + this.n2o + this.no;
        
        return emissioni_gas;
    }

    /**
     * @param emissioni_gas the emissioni_gas to set
     */
    public void setEmissioni_gas(double emissioni_gas) {
        this.emissioni_gas = emissioni_gas;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        costo = gestione;
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the energia
     */
    public double getEnergia() {
        
        energia = this.energia_consumata - this.energia_prodotta;
        return energia;
    }

    /**
     * @param energia the energia to set
     */
    public void setEnergia(double energia) {
        this.energia = energia;
    }

    /**
     * @return the emissioni_acide_m3
     */
    public double getEmissioni_acide_m3() {
        return emissioni_acide_m3 = this.nh3_m3;
    }

    /**
     * @param emissioni_acide_m3 the emissioni_acide_m3 to set
     */
    public void setEmissioni_acide_m3(double emissioni_acide_m3) {
        this.emissioni_acide_m3 = emissioni_acide_m3;
    }

    /**
     * @return the emissioni_gas_m3
     */
    public double getEmissioni_gas_m3() {
        return emissioni_gas_m3 = this.ch4_m3 + this.co2_m3 + this.n2o_m3 + this.no_m3;
    }

    /**
     * @param emissioni_gas_m3 the emissioni_gas_m3 to set
     */
    public void setEmissioni_gas_m3(double emissioni_gas_m3) {
        this.emissioni_gas_m3 = emissioni_gas_m3;
    }

    /**
     * @return the costo_m3
     */
    public double getCosto_m3() {
        return costo_m3 = this.gestione_m3;
    }

    /**
     * @param costo_m3 the costo_m3 to set
     */
    public void setCosto_m3(double costo_m3) {
        this.costo_m3 = costo_m3;
    }

    /**
     * @return the energia_m3
     */
    public double getEnergia_m3() {
        return energia_m3 = this.energia_consumata_m3 - this.energia_prodotta_m3;
    }

    /**
     * @param energia_m3 the energia_m3 to set
     */
    public void setEnergia_m3(double energia_m3) {
        this.energia_m3 = energia_m3;
    }

    /**
     * @return the valore
     */
    public double getValore() {
        return valore;
    }

    /**
     * @param valore the valore to set
     */
    public void setValore(double valore) {
        this.valore = valore;
    }

    
   /* @Override
    public int compareTo(Object o) {
       
        Alternativa altTemp = (Alternativa)o;
        
        if(valore < altTemp.getValore()) {
            return -1;
        }
        else if (valore == altTemp.getValore()) {
            return 0;
        }
        else {
            return 1;
        }
    }*/
    
    
    @Override
    protected Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Alternativa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the numero_volte
     */
    public int getNumero_volte() {
        return numero_volte;
    }

    /**
     * @param numero_volte the numero_volte to set
     */
    public void setNumero_volte(int numero_volte) {
        this.numero_volte = numero_volte;
    }

    /**
     * @return the pesomedio_emissioniacide
     */
    public float getPesomedio_emissioniacide() {
        return pesomedio_emissioniacide;
    }

    /**
     * @param pesomedio_emissioniacide the pesomedio_emissioniacide to set
     */
    public void setPesomedio_emissioniacide(float pesomedio_emissioniacide) {
        this.pesomedio_emissioniacide = pesomedio_emissioniacide;
    }

    /**
     * @return the pesomedio_emisisonigas
     */
    public float getPesomedio_emisisonigas() {
        return pesomedio_emisisonigas;
    }

    /**
     * @param pesomedio_emisisonigas the pesomedio_emisisonigas to set
     */
    public void setPesomedio_emisisonigas(float pesomedio_emisisonigas) {
        this.pesomedio_emisisonigas = pesomedio_emisisonigas;
    }

    /**
     * @return the pesomedio_energia
     */
    public float getPesomedio_energia() {
        return pesomedio_energia;
    }

    /**
     * @param pesomedio_energia the pesomedio_energia to set
     */
    public void setPesomedio_energia(float pesomedio_energia) {
        this.pesomedio_energia = pesomedio_energia;
    }

    /**
     * @return the pesomedio_costo
     */
    public float getPesomedio_costo() {
        return pesomedio_costo;
    }

    /**
     * @param pesomedio_costo the pesomedio_costo to set
     */
    public void setPesomedio_costo(float pesomedio_costo) {
        this.pesomedio_costo = pesomedio_costo;
    }

    @Override
    public int compareTo(Alternativa o) {
         Alternativa altTemp = (Alternativa)o;
        
        if(valore < altTemp.getValore()) {
            return -1;
        }
        else if (valore == altTemp.getValore()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    
   
    
    
   
}
