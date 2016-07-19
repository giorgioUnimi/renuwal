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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
 
/**
 *Descrive i tipi di terreno disponibili. Ha un legame 1 a 1 con appezzamento.
 * @author giorgio
 */
@Entity
@Table(name="tipo_terreno", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTerreno.findAll", query = "SELECT t FROM TipoTerreno t"),
    @NamedQuery(name = "TipoTerreno.findById", query = "SELECT t FROM TipoTerreno t where t.id = :id"),
    @NamedQuery(name = "TipoTerreno.findByDescrizione", query = "SELECT t FROM TipoTerreno t where t.descrizione = :descrizione")
    })
public class TipoTerreno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
        if (!(object instanceof TipoTerreno)) {
            return false;
        }
        TipoTerreno other = (TipoTerreno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "db.TipoTerreno[ id=" + id + " ]";
        return id.toString();
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
