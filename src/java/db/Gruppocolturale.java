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
    @NamedQuery(name = "Gruppocolturale.findAll", query = "SELECT g FROM Gruppocolturale g"),
    @NamedQuery(name = "Gruppocolturale.findById", query = "SELECT g FROM Gruppocolturale g WHERE g.id = :id"),
    @NamedQuery(name = "Gruppocolturale.findByDescrizione", query = "SELECT g FROM Gruppocolturale g WHERE g.descrizione = :descrizione")})
public class Gruppocolturale implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @OneToMany(mappedBy = "gruppocolturaleId")
    private Collection<ColturaleColturaRotazione> colturaleColturaRotazioneCollection;
    @OneToMany(mappedBy = "idgruppocolturaleId")
    private Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection;

    public Gruppocolturale() {
    }

    public Gruppocolturale(Long id) {
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
    public Collection<ColturaleColturaRotazione> getColturaleColturaRotazioneCollection() {
        return colturaleColturaRotazioneCollection;
    }

    public void setColturaleColturaRotazioneCollection(Collection<ColturaleColturaRotazione> colturaleColturaRotazioneCollection) {
        this.colturaleColturaRotazioneCollection = colturaleColturaRotazioneCollection;
    }

    @XmlTransient
    public Collection<Storicocolturaappezzamento> getStoricocolturaappezzamentoCollection() {
        return storicocolturaappezzamentoCollection;
    }

    public void setStoricocolturaappezzamentoCollection(Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection) {
        this.storicocolturaappezzamentoCollection = storicocolturaappezzamentoCollection;
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
        if (!(object instanceof Gruppocolturale)) {
            return false;
        }
        Gruppocolturale other = (Gruppocolturale) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Gruppocolturale[ id=" + id + " ]";
    }
    
}
