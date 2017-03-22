/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anni.findAll", query = "SELECT a FROM Anni a"),
    @NamedQuery(name = "Anni.findById", query = "SELECT a FROM Anni a WHERE a.id = :id"),
    @NamedQuery(name = "Anni.findByDescrizione", query = "SELECT a FROM Anni a WHERE a.descrizione = :descrizione")})
public class Anni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String descrizione;
    @OneToMany(mappedBy = "idAnno")
    private Collection<AziendeAnni> aziendeAnniCollection;

    public Anni() {
    }

    public Anni(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public Collection<AziendeAnni> getAziendeAnniCollection() {
        return aziendeAnniCollection;
    }

    public void setAziendeAnniCollection(Collection<AziendeAnni> aziendeAnniCollection) {
        this.aziendeAnniCollection = aziendeAnniCollection;
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
        if (!(object instanceof Anni)) {
            return false;
        }
        Anni other = (Anni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Anni[ id=" + id + " ]";
    }
    
}
