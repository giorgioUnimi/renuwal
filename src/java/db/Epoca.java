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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Epoca.findAll", query = "SELECT e FROM Epoca e"),
    @NamedQuery(name = "Epoca.findById", query = "SELECT e FROM Epoca e WHERE e.id = :id"),
    @NamedQuery(name = "Epoca.findByDescrizione", query = "SELECT e FROM Epoca e WHERE e.descrizione = :descrizione")})
public class Epoca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @JoinTable(name = "epoca_mese", joinColumns = {
        @JoinColumn(name = "epoca_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "mesi_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private Collection<Mese> meseCollection;
    @OneToMany(mappedBy = "idepocaId")
    private Collection<Efficienza> efficienzaCollection;

    public Epoca() {
    }

    public Epoca(Long id) {
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

    @XmlTransient
    public Collection<Mese> getMeseCollection() {
        return meseCollection;
    }

    public void setMeseCollection(Collection<Mese> meseCollection) {
        this.meseCollection = meseCollection;
    }

    @XmlTransient
    public Collection<Efficienza> getEfficienzaCollection() {
        return efficienzaCollection;
    }

    public void setEfficienzaCollection(Collection<Efficienza> efficienzaCollection) {
        this.efficienzaCollection = efficienzaCollection;
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
        if (!(object instanceof Epoca)) {
            return false;
        }
        Epoca other = (Epoca) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Epoca[ id=" + id + " ]";
    }
    
}
