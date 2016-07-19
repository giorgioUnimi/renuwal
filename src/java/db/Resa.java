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
@Table(name = "resa", catalog = "renuwal1", schema = "allevamento")
public class Resa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double quantita;
   /*@OneToOne
    private Coltura idColtura;*/

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
        if (!(object instanceof Resa)) {
            return false;
        }
        Resa other = (Resa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Resa[ id=" + id + " ]";
    }

    /**
     * @return the quantita
     */
    public double getQuantita() {
        return quantita;
    }

    /**
     * @param quantita the quantita to set
     */
    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    /**
     * @return the idColtura
     */
    /*public Coltura getIdColtura() {
        return idColtura;
    }*/

    /**
     * @param idColtura the idColtura to set
     */
   /* public void setIdColtura(Coltura idColtura) {
        this.idColtura = idColtura;
    }*/

    
}
