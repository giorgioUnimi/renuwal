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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "colturale_coltura_rotazione", catalog = "renuwal1", schema = "allevamento")
public class Colturale_coltura_rotazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Coltura coltura; 
    @ManyToOne
    private Rotazione rotazione;
    @ManyToOne
    private GruppoColturale gruppoColturale;

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
        if (!(object instanceof Colturale_coltura_rotazione)) {
            return false;
        }
        Colturale_coltura_rotazione other = (Colturale_coltura_rotazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Colturale_coltura_rotazione[ id=" + id + " ]";
    }

    /**
     * @return the coltura
     */
    public Coltura getColtura() {
        return coltura;
    }

    /**
     * @param coltura the coltura to set
     */
    public void setColtura(Coltura coltura) {
        this.coltura = coltura;
    }

    /**
     * @return the rotazione
     */
    public Rotazione getRotazione() {
        return rotazione;
    }

    /**
     * @param rotazione the rotazione to set
     */
    public void setRotazione(Rotazione rotazione) {
        this.rotazione = rotazione;
    }

    /**
     * @return the gruppoColturale
     */
    public GruppoColturale getGruppoColturale() {
        return gruppoColturale;
    }

    /**
     * @param gruppoColturale the gruppoColturale to set
     */
    public void setGruppoColturale(GruppoColturale gruppoColturale) {
        this.gruppoColturale = gruppoColturale;
    }
    
}
