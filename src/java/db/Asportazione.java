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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @NamedQuery(name = "Asportazione.findAll", query = "SELECT a FROM Asportazione a"),
    @NamedQuery(name = "Asportazione.findById", query = "SELECT a FROM Asportazione a WHERE a.id = :id"),
    @NamedQuery(name = "Asportazione.findByK2o", query = "SELECT a FROM Asportazione a WHERE a.k2o = :k2o"),
    @NamedQuery(name = "Asportazione.findByAspp2o5", query = "SELECT a FROM Asportazione a WHERE a.aspp2o5 = :aspp2o5"),
    @NamedQuery(name = "Asportazione.findByFattorecorrettivo", query = "SELECT a FROM Asportazione a WHERE a.fattorecorrettivo = :fattorecorrettivo"),
    @NamedQuery(name = "Asportazione.findByMasn", query = "SELECT a FROM Asportazione a WHERE a.masn = :masn")})
public class Asportazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double k2o;
    @Column(precision = 17, scale = 17)
    private Double aspp2o5;
    @Column(precision = 17, scale = 17)
    private Double fattorecorrettivo;
    @Column(precision = 17, scale = 17)
    private Double masn;
    @JoinTable(name = "coltura_asportazione", joinColumns = {
        @JoinColumn(name = "asportazione_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "coltura_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private Collection<Coltura> colturaCollection;
    @OneToMany(mappedBy = "asportazioneId")
    private Collection<Coltura> colturaCollection1;

    public Asportazione() {
    }

    public Asportazione(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getK2o() {
        return k2o;
    }

    public void setK2o(Double k2o) {
        this.k2o = k2o;
    }

    public Double getAspp2o5() {
        return aspp2o5;
    }

    public void setAspp2o5(Double aspp2o5) {
        this.aspp2o5 = aspp2o5;
    }

    public Double getFattorecorrettivo() {
        return fattorecorrettivo;
    }

    public void setFattorecorrettivo(Double fattorecorrettivo) {
        this.fattorecorrettivo = fattorecorrettivo;
    }

    public Double getMasn() {
        return masn;
    }

    public void setMasn(Double masn) {
        this.masn = masn;
    }

    @XmlTransient
    public Collection<Coltura> getColturaCollection() {
        return colturaCollection;
    }

    public void setColturaCollection(Collection<Coltura> colturaCollection) {
        this.colturaCollection = colturaCollection;
    }

    @XmlTransient
    public Collection<Coltura> getColturaCollection1() {
        return colturaCollection1;
    }

    public void setColturaCollection1(Collection<Coltura> colturaCollection1) {
        this.colturaCollection1 = colturaCollection1;
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
        if (!(object instanceof Asportazione)) {
            return false;
        }
        Asportazione other = (Asportazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Asportazione[ id=" + id + " ]";
    }
    
}
