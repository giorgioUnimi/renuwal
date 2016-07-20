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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @NamedQuery(name = "Rotazione.findAll", query = "SELECT r FROM Rotazione r"),
    @NamedQuery(name = "Rotazione.findById", query = "SELECT r FROM Rotazione r WHERE r.id = :id"),
    @NamedQuery(name = "Rotazione.findByDescrizione", query = "SELECT r FROM Rotazione r WHERE r.descrizione = :descrizione")})
public class Rotazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @OneToMany(mappedBy = "rotazioneId")
    private Collection<ColturaleColturaRotazione> colturaleColturaRotazioneCollection;
    @OneToMany(mappedBy = "rotazioneId")
    private Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection;

    public Rotazione() {
    }

    public Rotazione(Integer id) {
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
    
}
