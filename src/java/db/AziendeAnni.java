/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "aziende_anni", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AziendeAnni.findAll", query = "SELECT a FROM AziendeAnni a"),
    @NamedQuery(name = "AziendeAnni.findById", query = "SELECT a FROM AziendeAnni a WHERE a.id = :id")})
public class AziendeAnni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private AziendeI idAzienda;
    @JoinColumn(name = "id_anno", referencedColumnName = "id")
    @OneToOne
    private Anni idAnno;
    @OneToMany(mappedBy = "idAziendeanni",cascade = CascadeType.ALL)
    private Collection<ScenarioI> scenarioICollection;

    public AziendeAnni() {
    }

    public AziendeAnni(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AziendeI getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(AziendeI idAzienda) {
        this.idAzienda = idAzienda;
    }

    public Anni getIdAnno() {
        return idAnno;
    }

    public void setIdAnno(Anni idAnno) {
        this.idAnno = idAnno;
    }

    @XmlTransient
    public Collection<ScenarioI> getScenarioICollection() {
        return scenarioICollection;
    }

    public void setScenarioICollection(Collection<ScenarioI> scenarioICollection) {
        this.scenarioICollection = scenarioICollection;
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
        if (!(object instanceof AziendeAnni)) {
            return false;
        }
        AziendeAnni other = (AziendeAnni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AziendeAnni[ id=" + id + " ]";
    }
    
}
