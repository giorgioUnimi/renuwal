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
    @NamedQuery(name = "Epoca.findAll", query = "SELECT e FROM Epoca e"),
    @NamedQuery(name = "Epoca.findById", query = "SELECT e FROM Epoca e WHERE e.id = :id"),
    @NamedQuery(name = "Epoca.findByDescrizione", query = "SELECT e FROM Epoca e WHERE e.descrizione = :descrizione"),
    @NamedQuery(name = "Epoca.findByLSm1", query = "SELECT e FROM Epoca e WHERE e.lSm1 = :lSm1"),
    @NamedQuery(name = "Epoca.findByAirtemp", query = "SELECT e FROM Epoca e WHERE e.airtemp = :airtemp")})
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
    @Column(name = "l_sm1")
    private Integer lSm1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double airtemp;
    @OneToMany(mappedBy = "idEpoca")
    private Collection<Mese> meseCollection;
    @OneToMany(mappedBy = "idEpoca")
    private Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection;

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

    public Integer getLSm1() {
        return lSm1;
    }

    public void setLSm1(Integer lSm1) {
        this.lSm1 = lSm1;
    }

    public Double getAirtemp() {
        return airtemp;
    }

    public void setAirtemp(Double airtemp) {
        this.airtemp = airtemp;
    }

    @XmlTransient
    public Collection<Mese> getMeseCollection() {
        return meseCollection;
    }

    public void setMeseCollection(Collection<Mese> meseCollection) {
        this.meseCollection = meseCollection;
    }

    @XmlTransient
    public Collection<TabellaEfficienzaColturaModalitaTecnica> getTabellaEfficienzaColturaModalitaTecnicaCollection() {
        return tabellaEfficienzaColturaModalitaTecnicaCollection;
    }

    public void setTabellaEfficienzaColturaModalitaTecnicaCollection(Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection) {
        this.tabellaEfficienzaColturaModalitaTecnicaCollection = tabellaEfficienzaColturaModalitaTecnicaCollection;
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
