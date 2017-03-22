/*
 * Ogni trattamento contiene un contenitore reflui che a sua volta contiene 
 * i 4 liquami e letami (bovino,suino,avicolo,altro). Il contenitore dei reflui contiene anche le biomasse
 * che è di tipo refluo e l'azoto minerale.Il trattamento deve tener presente le emissioni in aria ed ogni trattamento che estende 
 * la classe trattamento conterra gli output gestionali specifici del trattamento.
 * Gli output gestionali sono comuni a tutti itrattamenti per cui vengono inseriti in questa classe
 * che verra estesa da tutti trattamenti per cui le variabili di questa classe verranno ereditate 
 * da tutti i trattamenti che la estendono.Gli output gestionali sono rappresentati dai tre gruppi 
 *in idcati di seguito  EMISSIONI IN ARIA,COSTI E RICAVI , ENERGIA.
 */
package ager.trattamenti;

import ager.ContenitoreReflui;
import ager.TipiReflui;


/**
 *
 * @author giorgio
 */
public class Trattamento implements CalcolaTrattamento{
     
   
    
    private ContenitoreReflui contenitoreRefluiIn = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
    
    private ContenitoreReflui contenitoreRefluiOut = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
    
    /**************************************************************
     * EMISSIONI IN ARIA
     * Le emissioni di un trattamento sono comuni a tutti i trattamenti.
     * Le emissioni sono ch4 per metano, nh3 per azoto ammoniacale , 
     * co2 per anidride carbonica , n2 per azoto e n2o per protossido di azoto.
     *************************************************************/
   protected double ch4 = 0;
   protected double nh3 = 0;
   protected double co2 = 0;
   protected double n2 = 0;
   protected double n2o = 0;
     /**************************************************************
     * EMISSIONI IN ARIA
     *************************************************************/
    /**
     * COSTI E RICAVI
     * Rappresentano gli euro all'anno spesi/guadagnati per il trattamento  in questione.
     * Comprendono i costi di esercizio, di investimento, il valore dell'energia elettrica venduta,
     * il valore del compost venduto, il valore del solido essiccato
     */
   protected double esercizio = 0;
   protected double investimento = 0 ;
   protected double costototale = 0;
   protected double energiavenduta = 0;
   protected double compostvenduto = 0;
   protected double solidoessiccato = 0;
     /**
     * COSTI E RICAVI
     */
    /**
     * ENERGIA
     * Comprende l'energia elettrica prodotta e quella consumata in kWh / anno
     */
   protected double energiaprodotta = 0;
   protected double energiaconsumata = 0;
     /**
     * ENERGIA
     */
    
    public Trattamento(/*Totaleletame totlet,Totaleliquame totliq*/)
    {
     
       // totletNew = new Totaleletame(totlet.getLetameBovino(),totlet.getLetameSuino());
       // totliqNew = new Totaleliquame(totliq.getLiquameSuino(),totliq.getLiquameBovino());
        
    }
    
    
    /**
     * mette a zero i valori di tutte le variabili 
     * gestionali
     */
    public void azzeraGestionali()
    {
        ch4 = 0;
        nh3 = 0;
        co2 = 0;
        n2 = 0;
        n2o = 0;
        esercizio = 0;
        investimento = 0;
        costototale = 0;
        energiavenduta = 0;
        compostvenduto = 0;
        solidoessiccato = 0;
        energiaprodotta = 0;
        energiaconsumata = 0;
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
     * @return the n2
     */
    public double getN2() {
        return n2;
    }

    /**
     * @param n2 the n2 to set
     */
    public void setN2(double n2) {
        this.n2 = n2;
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

  
    public void calcolaRefluo() {
       
    }

   
    public void calcolaEmissioni() {
       
    }

   
    public void calcolaGestionali() {
        
    }

    /**
     * @return the contenitoreRefluiOut
     */
    public ContenitoreReflui getContenitoreRefluiOut() {
        return contenitoreRefluiOut;
    }

    /**
     * @param contenitoreRefluiOut the contenitoreRefluiOut to set
     */
    public void setContenitoreRefluiOut(ContenitoreReflui contenitoreRefluiOut) {
        this.contenitoreRefluiOut = contenitoreRefluiOut;
    }

    /**
     * @return the contenitoreRefluiIn
     */
    public ContenitoreReflui getContenitoreRefluiIn() {
        return contenitoreRefluiIn;
    }

    /**
     * @param contenitoreRefluiIn the contenitoreRefluiIn to set
     */
    public void setContenitoreRefluiIn(ContenitoreReflui contenitoreRefluiIn) {
        this.contenitoreRefluiIn = contenitoreRefluiIn;
    }

    /**
     * Crea due nuove istanze della classe ContenitoreReflui e le assegna
     * ai riferimenti  this.contenitoreRefluiIn e  this.contenitoreRefluiOut.
     * L'effetto è quello di azzerare/svuotare il this.contenitoreRefluiIn ed il 
     * this.contenitoreRefluiOut.
     */
    public void azzeraContenitori() {
        
        this.contenitoreRefluiIn = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
        
        this.contenitoreRefluiOut = new ContenitoreReflui(TipiReflui.getInstance().getTipologieDaAllevamento());
    }

    /**
     * @return the esercizio
     */
    public double getEsercizio() {
        return esercizio;
    }

    /**
     * @param esercizio the esercizio to set
     */
    public void setEsercizio(double esercizio) {
        this.esercizio = esercizio;
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
     * @return the energiavenduta
     */
    public double getEnergiavenduta() {
        return energiavenduta;
    }

    /**
     * @param energiavenduta the energiavenduta to set
     */
    public void setEnergiavenduta(double energiavenduta) {
        this.energiavenduta = energiavenduta;
    }

    /**
     * @return the compostvenduto
     */
    public double getCompostvenduto() {
        return compostvenduto;
    }

    /**
     * @param compostvenduto the compostvenduto to set
     */
    public void setCompostvenduto(double compostvenduto) {
        this.compostvenduto = compostvenduto;
    }

    /**
     * @return the solidoessiccato
     */
    public double getSolidoessiccato() {
        return solidoessiccato;
    }

    /**
     * @param solidoessiccato the solidoessiccato to set
     */
    public void setSolidoessiccato(double solidoessiccato) {
        this.solidoessiccato = solidoessiccato;
    }

    /**
     * @return the energiaprodotta
     */
    public double getEnergiaprodotta() {
        return energiaprodotta;
    }

    /**
     * @param energiaprodotta the energiaprodotta to set
     */
    public void setEnergiaprodotta(double energiaprodotta) {
        this.energiaprodotta = energiaprodotta;
    }

    /**
     * @return the energiaconsumata
     */
    public double getEnergiaconsumata() {
        return energiaconsumata;
    }

    /**
     * @param energiaconsumata the energiaconsumata to set
     */
    public void setEnergiaconsumata(double energiaconsumata) {
        this.energiaconsumata = energiaconsumata;
    }

    /**
     * @return the costototale
     */
    public double getCostototale() {
        return costototale;
    }

    /**
     * @param costototale the costototale to set
     */
    public void setCostototale(double costototale) {
        this.costototale = costototale;
    }

   
    
    
}
