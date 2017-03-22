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
@Table(name = "provenienza_refluo", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProvenienzaRefluo.findAll", query = "SELECT p FROM ProvenienzaRefluo p"),
    @NamedQuery(name = "ProvenienzaRefluo.findById", query = "SELECT p FROM ProvenienzaRefluo p WHERE p.id = :id"),
    @NamedQuery(name = "ProvenienzaRefluo.findByNome", query = "SELECT p FROM ProvenienzaRefluo p WHERE p.nome = :nome"),
    @NamedQuery(name = "ProvenienzaRefluo.findByAnimale", query = "SELECT p FROM ProvenienzaRefluo p WHERE p.animale = :animale")})
public class ProvenienzaRefluo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    private Boolean animale;
    @OneToMany(mappedBy = "idProvenienzaRefluo")
    private Collection<MappingRefluoTipoAlfam> mappingRefluoTipoAlfamCollection;
    @OneToMany(mappedBy = "idProvenienzaRefluo")
    private Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection;
    @OneToMany(mappedBy = "idProvenienzaRefluo")
    private Collection<RefluoTipoPerditeariaAlfam> refluoTipoPerditeariaAlfamCollection;
    @OneToMany(mappedBy = "tipologiaanimaleId")
    private Collection<SpecieS> specieSCollection;
    @OneToMany(mappedBy = "idProvenienzaRefluo")
    private Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection;

    public ProvenienzaRefluo() {
    }

    public ProvenienzaRefluo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAnimale() {
        return animale;
    }

    public void setAnimale(Boolean animale) {
        this.animale = animale;
    }

    @XmlTransient
    public Collection<MappingRefluoTipoAlfam> getMappingRefluoTipoAlfamCollection() {
        return mappingRefluoTipoAlfamCollection;
    }

    public void setMappingRefluoTipoAlfamCollection(Collection<MappingRefluoTipoAlfam> mappingRefluoTipoAlfamCollection) {
        this.mappingRefluoTipoAlfamCollection = mappingRefluoTipoAlfamCollection;
    }

    @XmlTransient
    public Collection<EfficienzeNpVincoliNormativi> getEfficienzeNpVincoliNormativiCollection() {
        return efficienzeNpVincoliNormativiCollection;
    }

    public void setEfficienzeNpVincoliNormativiCollection(Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection) {
        this.efficienzeNpVincoliNormativiCollection = efficienzeNpVincoliNormativiCollection;
    }

    @XmlTransient
    public Collection<RefluoTipoPerditeariaAlfam> getRefluoTipoPerditeariaAlfamCollection() {
        return refluoTipoPerditeariaAlfamCollection;
    }

    public void setRefluoTipoPerditeariaAlfamCollection(Collection<RefluoTipoPerditeariaAlfam> refluoTipoPerditeariaAlfamCollection) {
        this.refluoTipoPerditeariaAlfamCollection = refluoTipoPerditeariaAlfamCollection;
    }

    @XmlTransient
    public Collection<SpecieS> getSpecieSCollection() {
        return specieSCollection;
    }

    public void setSpecieSCollection(Collection<SpecieS> specieSCollection) {
        this.specieSCollection = specieSCollection;
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
        if (!(object instanceof ProvenienzaRefluo)) {
            return false;
        }
        ProvenienzaRefluo other = (ProvenienzaRefluo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ProvenienzaRefluo[ id=" + id + " ]";
    }
    
}
