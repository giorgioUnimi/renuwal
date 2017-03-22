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
@Table(name = "qualita_efficienza", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QualitaEfficienza.findAll", query = "SELECT q FROM QualitaEfficienza q"),
    @NamedQuery(name = "QualitaEfficienza.findById", query = "SELECT q FROM QualitaEfficienza q WHERE q.id = :id"),
    @NamedQuery(name = "QualitaEfficienza.findByDescrizione", query = "SELECT q FROM QualitaEfficienza q WHERE q.descrizione = :descrizione")})
public class QualitaEfficienza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(length = 20)
    private String descrizione;
    @OneToMany(mappedBy = "idQualita")
    private Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection;
    @OneToMany(mappedBy = "idQualita")
    private Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection;

    public QualitaEfficienza() {
    }

    public QualitaEfficienza(Integer id) {
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
    public Collection<TabellaEfficienzaColturaModalitaTecnica> getTabellaEfficienzaColturaModalitaTecnicaCollection() {
        return tabellaEfficienzaColturaModalitaTecnicaCollection;
    }

    public void setTabellaEfficienzaColturaModalitaTecnicaCollection(Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection) {
        this.tabellaEfficienzaColturaModalitaTecnicaCollection = tabellaEfficienzaColturaModalitaTecnicaCollection;
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
        if (!(object instanceof QualitaEfficienza)) {
            return false;
        }
        QualitaEfficienza other = (QualitaEfficienza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.QualitaEfficienza[ id=" + id + " ]";
    }
    
}
