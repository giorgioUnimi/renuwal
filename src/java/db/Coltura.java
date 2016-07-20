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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Coltura.findAll", query = "SELECT c FROM Coltura c"),
    @NamedQuery(name = "Coltura.findById", query = "SELECT c FROM Coltura c WHERE c.id = :id"),
    @NamedQuery(name = "Coltura.findByDescrizione", query = "SELECT c FROM Coltura c WHERE c.descrizione = :descrizione")})
public class Coltura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @ManyToMany(mappedBy = "colturaCollection")
    private Collection<Asportazione> asportazioneCollection;
    @OneToMany(mappedBy = "colturaId")
    private Collection<ColturaleColturaRotazione> colturaleColturaRotazioneCollection;
    @OneToMany(mappedBy = "idcolturaId")
    private Collection<Residuoprecessione> residuoprecessioneCollection;
    @JoinColumn(name = "resa_id", referencedColumnName = "id")
    @ManyToOne
    private Resa resaId;
    @JoinColumn(name = "asportazione_id", referencedColumnName = "id")
    @ManyToOne
    private Asportazione asportazioneId;
    @OneToMany(mappedBy = "idcolturaId")
    private Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection;

    public Coltura() {
    }

    public Coltura(Integer id) {
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
    public Collection<Asportazione> getAsportazioneCollection() {
        return asportazioneCollection;
    }

    public void setAsportazioneCollection(Collection<Asportazione> asportazioneCollection) {
        this.asportazioneCollection = asportazioneCollection;
    }

    @XmlTransient
    public Collection<ColturaleColturaRotazione> getColturaleColturaRotazioneCollection() {
        return colturaleColturaRotazioneCollection;
    }

    public void setColturaleColturaRotazioneCollection(Collection<ColturaleColturaRotazione> colturaleColturaRotazioneCollection) {
        this.colturaleColturaRotazioneCollection = colturaleColturaRotazioneCollection;
    }

    @XmlTransient
    public Collection<Residuoprecessione> getResiduoprecessioneCollection() {
        return residuoprecessioneCollection;
    }

    public void setResiduoprecessioneCollection(Collection<Residuoprecessione> residuoprecessioneCollection) {
        this.residuoprecessioneCollection = residuoprecessioneCollection;
    }

    public Resa getResaId() {
        return resaId;
    }

    public void setResaId(Resa resaId) {
        this.resaId = resaId;
    }

    public Asportazione getAsportazioneId() {
        return asportazioneId;
    }

    public void setAsportazioneId(Asportazione asportazioneId) {
        this.asportazioneId = asportazioneId;
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
        if (!(object instanceof Coltura)) {
            return false;
        }
        Coltura other = (Coltura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Coltura[ id=" + id + " ]";
    }
    
}
