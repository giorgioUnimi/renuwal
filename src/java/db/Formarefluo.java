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
    @NamedQuery(name = "Formarefluo.findAll", query = "SELECT f FROM Formarefluo f"),
    @NamedQuery(name = "Formarefluo.findById", query = "SELECT f FROM Formarefluo f WHERE f.id = :id"),
    @NamedQuery(name = "Formarefluo.findByTipo", query = "SELECT f FROM Formarefluo f WHERE f.tipo = :tipo"),
    @NamedQuery(name = "Formarefluo.findByTipo1", query = "SELECT f FROM Formarefluo f WHERE f.tipo1 = :tipo1")})
public class Formarefluo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String tipo;
    @Size(max = 100)
    @Column(length = 100)
    private String tipo1;
    @OneToMany(mappedBy = "idFormaRefluo")
    private Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection;
    @OneToMany(mappedBy = "idForma")
    private Collection<TipoFormaRefluo> tipoFormaRefluoCollection;
    @OneToMany(mappedBy = "forma")
    private Collection<TipostoccaggioS> tipostoccaggioSCollection;
    @OneToMany(mappedBy = "idFormaRefluo")
    private Collection<TabellaEfficienzaColturaModalitaTecnica> tabellaEfficienzaColturaModalitaTecnicaCollection;
    @OneToMany(mappedBy = "idFormaRefluo")
    private Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection;

    public Formarefluo() {
    }

    public Formarefluo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    @XmlTransient
    public Collection<EfficienzeNpVincoliNormativi> getEfficienzeNpVincoliNormativiCollection() {
        return efficienzeNpVincoliNormativiCollection;
    }

    public void setEfficienzeNpVincoliNormativiCollection(Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection) {
        this.efficienzeNpVincoliNormativiCollection = efficienzeNpVincoliNormativiCollection;
    }

    @XmlTransient
    public Collection<TipoFormaRefluo> getTipoFormaRefluoCollection() {
        return tipoFormaRefluoCollection;
    }

    public void setTipoFormaRefluoCollection(Collection<TipoFormaRefluo> tipoFormaRefluoCollection) {
        this.tipoFormaRefluoCollection = tipoFormaRefluoCollection;
    }

    @XmlTransient
    public Collection<TipostoccaggioS> getTipostoccaggioSCollection() {
        return tipostoccaggioSCollection;
    }

    public void setTipostoccaggioSCollection(Collection<TipostoccaggioS> tipostoccaggioSCollection) {
        this.tipostoccaggioSCollection = tipostoccaggioSCollection;
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
        if (!(object instanceof Formarefluo)) {
            return false;
        }
        Formarefluo other = (Formarefluo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Formarefluo[ id=" + id + " ]";
    }
    
}
