/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "asportazione", catalog = "renuwal1", schema = "allevamento")
public class Asportazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*@OneToOne
    private Coltura Coltura;*/
    private double masN;
    private double fattoreCorrettivo;
    private double aspP2O5;
    private double K2O;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asportazione)) {
            return false;
        }
        Asportazione other = (Asportazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Asportazione[ id=" + id + " ]";
    }

    

    /**
     * @return the masN
     */
    public double getMasN() {
        return masN;
    }

    /**
     * @param masN the masN to set
     */
    public void setMasN(double masN) {
        this.masN = masN;
    }

    /**
     * @return the fattoreCorrettivo
     */
    public double getFattoreCorrettivo() {
        return fattoreCorrettivo;
    }

    /**
     * @param fattoreCorrettivo the fattoreCorrettivo to set
     */
    public void setFattoreCorrettivo(double fattoreCorrettivo) {
        this.fattoreCorrettivo = fattoreCorrettivo;
    }

    /**
     * @return the aspP2O5
     */
    public double getAspP2O5() {
        return aspP2O5;
    }

    /**
     * @param aspP2O5 the aspP2O5 to set
     */
    public void setAspP2O5(double aspP2O5) {
        this.aspP2O5 = aspP2O5;
    }

    /**
     * @return the K2O
     */
    public double getK2O() {
        return K2O;
    }

    /**
     * @param K2O the K2O to set
     */
    public void setK2O(double K2O) {
        this.K2O = K2O;
    }

    /**
     * @return the Coltura
     */
    /*public Coltura getColtura() {
        return Coltura;
    }*/

    /**
     * @param Coltura the Coltura to set
     */
    /*public void setColtura(Coltura Coltura) {
        this.Coltura = Coltura;
    }*/
    
}
