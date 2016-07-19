/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "rotazione", catalog = "renuwal1", schema = "allevamento")
public class Rotazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descrizione;
    @OneToMany(mappedBy="rotazione")
    @JoinTable(schema="allevamento")
    private Collection<db.Colturale_coltura_rotazione> colturale_coltura_rotazione;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof Rotazione)) {
            return false;
        }
        Rotazione other = (Rotazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Rotazione[ id=" + id + " ]";
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the colturale_coltura_rotazione
     */
    public Collection<db.Colturale_coltura_rotazione> getColturale_coltura_rotazione() {
        return colturale_coltura_rotazione;
    }

    /**
     * @param colturale_coltura_rotazione the colturale_coltura_rotazione to set
     */
    public void setColturale_coltura_rotazione(Collection<db.Colturale_coltura_rotazione> colturale_coltura_rotazione) {
        this.colturale_coltura_rotazione = colturale_coltura_rotazione;
    }
    
}
