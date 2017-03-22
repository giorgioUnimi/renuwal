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
@Table(catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tecnicadistribuzione.findAll", query = "SELECT t FROM Tecnicadistribuzione t"),
    @NamedQuery(name = "Tecnicadistribuzione.findById", query = "SELECT t FROM Tecnicadistribuzione t WHERE t.id = :id"),
    @NamedQuery(name = "Tecnicadistribuzione.findByDescrizione", query = "SELECT t FROM Tecnicadistribuzione t WHERE t.descrizione = :descrizione")})
public class Tecnicadistribuzione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @OneToMany(mappedBy = "idTecnica")
    private Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection;

    public Tecnicadistribuzione() {
    }

    public Tecnicadistribuzione(Integer id) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tecnicadistribuzione)) {
            return false;
        }
        Tecnicadistribuzione other = (Tecnicadistribuzione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Tecnicadistribuzione[ id=" + id + " ]";
    }
    
}
