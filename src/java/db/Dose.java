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
    @NamedQuery(name = "Dose.findAll", query = "SELECT d FROM Dose d"),
    @NamedQuery(name = "Dose.findById", query = "SELECT d FROM Dose d WHERE d.id = :id"),
    @NamedQuery(name = "Dose.findByDescrizione", query = "SELECT d FROM Dose d WHERE d.descrizione = :descrizione"),
    @NamedQuery(name = "Dose.findByMaxDose", query = "SELECT d FROM Dose d WHERE d.maxDose = :maxDose"),
    @NamedQuery(name = "Dose.findByMinDose", query = "SELECT d FROM Dose d WHERE d.minDose = :minDose")})
public class Dose implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @Column(name = "max_dose")
    private Integer maxDose;
    @Column(name = "min_dose")
    private Integer minDose;
    @OneToMany(mappedBy = "idDose")
    private Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection;

    public Dose() {
    }

    public Dose(Long id) {
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

    public Integer getMaxDose() {
        return maxDose;
    }

    public void setMaxDose(Integer maxDose) {
        this.maxDose = maxDose;
    }

    public Integer getMinDose() {
        return minDose;
    }

    public void setMinDose(Integer minDose) {
        this.minDose = minDose;
    }

    @XmlTransient
    public Collection<TabellaEfficienzaRefluoDose> getTabellaEfficienzaRefluoDoseCollection() {
        return tabellaEfficienzaRefluoDoseCollection;
    }

    public void setTabellaEfficienzaRefluoDoseCollection(Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection) {
        this.tabellaEfficienzaRefluoDoseCollection = tabellaEfficienzaRefluoDoseCollection;
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
        if (!(object instanceof Dose)) {
            return false;
        }
        Dose other = (Dose) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Dose[ id=" + id + " ]";
    }
    
}
