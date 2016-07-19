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
@Table(name = "dose", catalog = "renuwal1", schema = "allevamento")
public class Dose implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int min_dose;
    private int max_dose;
    private String descrizione;
    
    
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
        if (!(object instanceof Dose)) {
            return false;
        }
        Dose other = (Dose) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Dose[ id=" + id + " ]";
    }

    /**
     * @return the min_dose
     */
    public int getMin_dose() {
        return min_dose;
    }

    /**
     * @param min_dose the min_dose to set
     */
    public void setMin_dose(int min_dose) {
        this.min_dose = min_dose;
    }

    /**
     * @return the max_dose
     */
    public int getMax_dose() {
        return max_dose;
    }

    /**
     * @param max_dose the max_dose to set
     */
    public void setMax_dose(int max_dose) {
        this.max_dose = max_dose;
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

   
    
}
