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
@Table(catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formarefluo.findAll", query = "SELECT f FROM Formarefluo f"),
    @NamedQuery(name = "Formarefluo.findById", query = "SELECT f FROM Formarefluo f WHERE f.id = :id"),
    @NamedQuery(name = "Formarefluo.findByTipo", query = "SELECT f FROM Formarefluo f WHERE f.tipo = :tipo")})
public class Formarefluo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String tipo;
    @OneToMany(mappedBy = "idformarefluoId")
    private Collection<Efficienza> efficienzaCollection;
    @OneToMany(mappedBy = "forma")
    private Collection<TipostoccaggioS> tipostoccaggioSCollection;

    public Formarefluo() {
    }

    public Formarefluo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Efficienza> getEfficienzaCollection() {
        return efficienzaCollection;
    }

    public void setEfficienzaCollection(Collection<Efficienza> efficienzaCollection) {
        this.efficienzaCollection = efficienzaCollection;
    }

    @XmlTransient
    public Collection<TipostoccaggioS> getTipostoccaggioSCollection() {
        return tipostoccaggioSCollection;
    }

    public void setTipostoccaggioSCollection(Collection<TipostoccaggioS> tipostoccaggioSCollection) {
        this.tipostoccaggioSCollection = tipostoccaggioSCollection;
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
        if (!(object instanceof Formarefluo)) {
            return false;
        }
        Formarefluo other = (Formarefluo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Formarefluo[ id=" + id + " ]";
    }
    
}
