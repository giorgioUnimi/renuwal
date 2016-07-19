/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager.trattamenti;

/**
 *Interfaccia che definisce i metodi dei trattamenti.
 * Con calcolaRefluo viene calcolato la trasformazione tra relfuo di input ed output
 * (caratteristiche chimiche) secondo le regole di trasformazione del trattamento in esecuzione
 * Con calcolaEmissioni si determinano le emissioni in aria causate dal trattamento
 * Con calcolaGestionali si dterminano i parametri gestionali del trattamento in esecuzione
 * Con azzeraContenitore vengono svuotati i contenitori dei reflui
 * @author giorgio
 */
public interface CalcolaTrattamento {
    
    /**
     * trasforma le caratteristiche chimiche del refluo
     */
    void calcolaRefluo();
    
    /**
     * calcola le emissioni in aria prodotte dal trattamento in essere
     */
    void calcolaEmissioni();
    /**
     * calcola i parametri gestionali del trattamento in essere
     */
    void calcolaGestionali();
    
    /**
     * svuota il contenitore di input e di output dei reflui
     */
    void azzeraContenitori();
    
}
