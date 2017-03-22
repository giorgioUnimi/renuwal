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
    @NamedQuery(name = "Colturaprecedente.findAll", query = "SELECT c FROM Colturaprecedente c"),
    @NamedQuery(name = "Colturaprecedente.findById", query = "SELECT c FROM Colturaprecedente c WHERE c.id = :id"),
    @NamedQuery(name = "Colturaprecedente.findByDescrzione", query = "SELECT c FROM Colturaprecedente c WHERE c.descrzione = :descrzione"),
    @NamedQuery(name = "Colturaprecedente.findByRiduzione", query = "SELECT c FROM Colturaprecedente c WHERE c.riduzione = :riduzione")})
public class Colturaprecedente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrzione;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double riduzione;
    @OneToMany(mappedBy = "colturaprecedenteId")
    private Collection<Appezzamento> appezzamentoCollection;

    public Colturaprecedente() {
    }

    public Colturaprecedente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrzione() {
        return descrzione;
    }

    public void setDescrzione(String descrzione) {
        this.descrzione = descrzione;
    }

    public Double getRiduzione() {
        return riduzione;
    }

    public void setRiduzione(Double riduzione) {
        this.riduzione = riduzione;
    }

    @XmlTransient
    public Collection<Appezzamento> getAppezzamentoCollection() {
        return appezzamentoCollection;
    }

    public void setAppezzamentoCollection(Collection<Appezzamento> appezzamentoCollection) {
        this.appezzamentoCollection = appezzamentoCollection;
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
        if (!(object instanceof Colturaprecedente)) {
            return false;
        }
        Colturaprecedente other = (Colturaprecedente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Colturaprecedente[ id=" + id + " ]";
    }
    
}
