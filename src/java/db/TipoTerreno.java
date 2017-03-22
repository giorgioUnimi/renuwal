/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "tipo_terreno", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTerreno.findAll", query = "SELECT t FROM TipoTerreno t"),
    @NamedQuery(name = "TipoTerreno.findById", query = "SELECT t FROM TipoTerreno t WHERE t.id = :id"),
    @NamedQuery(name = "TipoTerreno.findByDescrizione", query = "SELECT t FROM TipoTerreno t WHERE t.descrizione = :descrizione")})
public class TipoTerreno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;

    public TipoTerreno() {
    }

    public TipoTerreno(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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
        return "db.TipoTerreno[ id=" + id + " ]";
    }
    
}
