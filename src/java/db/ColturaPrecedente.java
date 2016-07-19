/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name="colturaprecedente", catalog = "renuwal1", schema = "allevamento")
public class ColturaPrecedente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;
    private String descrzione;
    /**Gli apporti massimi di azoto devono essere ridotti:
        - di 40 kg N/ha per la coltura che segue l’aratura di un prato avvicendato di durata almeno triennale;
        - di 60 kg N/ha per la coltura che segue l’aratura di un medicaio di durata almeno triennale.
        * */
    private double riduzione;

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
        if (!(object instanceof ColturaPrecedente)) {
            return false;
        }
        ColturaPrecedente other = (ColturaPrecedente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ColturaPrecedente[ id=" + id + " ]";
    }

    /**
     * @return the descrzione
     */
    public String getDescrzione() {
        return descrzione;
    }

    /**
     * @param descrzione the descrzione to set
     */
    public void setDescrzione(String descrzione) {
        this.descrzione = descrzione;
    }

    /**
     * @return the riduzione
     */
    public double getRiduzione() {
        return riduzione;
    }

    /**
     * @param riduzione the riduzione to set
     */
    public void setRiduzione(double riduzione) {
        this.riduzione = riduzione;
    }
    
}
