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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tipo_forma_refluo", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoFormaRefluo.findAll", query = "SELECT t FROM TipoFormaRefluo t"),
    @NamedQuery(name = "TipoFormaRefluo.findById", query = "SELECT t FROM TipoFormaRefluo t WHERE t.id = :id"),
    @NamedQuery(name = "TipoFormaRefluo.findByDescrizione", query = "SELECT t FROM TipoFormaRefluo t WHERE t.descrizione = :descrizione")})
public class TipoFormaRefluo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @OneToMany(mappedBy = "idTipoFormaRefluo")
    private Collection<MappingRefluoTipoAlfam> mappingRefluoTipoAlfamCollection;
    @OneToMany(mappedBy = "idTipoFormaRefluo")
    private Collection<RefluoTipoPerditeariaAlfam> refluoTipoPerditeariaAlfamCollection;
    @JoinColumn(name = "id_forma", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo idForma;
    @OneToMany(mappedBy = "idTipoFormaRefluo")
    private Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection;
    @OneToMany(mappedBy = "idTipoFormaRefluoPalabile")
    private Collection<AlternativeS> alternativeSCollection;
    @OneToMany(mappedBy = "idTipoFormaRefluoLiquido")
    private Collection<AlternativeS> alternativeSCollection1;

    public TipoFormaRefluo() {
    }

    public TipoFormaRefluo(Integer id) {
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
    public Collection<MappingRefluoTipoAlfam> getMappingRefluoTipoAlfamCollection() {
        return mappingRefluoTipoAlfamCollection;
    }

    public void setMappingRefluoTipoAlfamCollection(Collection<MappingRefluoTipoAlfam> mappingRefluoTipoAlfamCollection) {
        this.mappingRefluoTipoAlfamCollection = mappingRefluoTipoAlfamCollection;
    }

    @XmlTransient
    public Collection<RefluoTipoPerditeariaAlfam> getRefluoTipoPerditeariaAlfamCollection() {
        return refluoTipoPerditeariaAlfamCollection;
    }

    public void setRefluoTipoPerditeariaAlfamCollection(Collection<RefluoTipoPerditeariaAlfam> refluoTipoPerditeariaAlfamCollection) {
        this.refluoTipoPerditeariaAlfamCollection = refluoTipoPerditeariaAlfamCollection;
    }

    public Formarefluo getIdForma() {
        return idForma;
    }

    public void setIdForma(Formarefluo idForma) {
        this.idForma = idForma;
    }

    @XmlTransient
    public Collection<TabellaEfficienzaRefluoDose> getTabellaEfficienzaRefluoDoseCollection() {
        return tabellaEfficienzaRefluoDoseCollection;
    }

    public void setTabellaEfficienzaRefluoDoseCollection(Collection<TabellaEfficienzaRefluoDose> tabellaEfficienzaRefluoDoseCollection) {
        this.tabellaEfficienzaRefluoDoseCollection = tabellaEfficienzaRefluoDoseCollection;
    }

    @XmlTransient
    public Collection<AlternativeS> getAlternativeSCollection() {
        return alternativeSCollection;
    }

    public void setAlternativeSCollection(Collection<AlternativeS> alternativeSCollection) {
        this.alternativeSCollection = alternativeSCollection;
    }

    @XmlTransient
    public Collection<AlternativeS> getAlternativeSCollection1() {
        return alternativeSCollection1;
    }

    public void setAlternativeSCollection1(Collection<AlternativeS> alternativeSCollection1) {
        this.alternativeSCollection1 = alternativeSCollection1;
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
        if (!(object instanceof TipoFormaRefluo)) {
            return false;
        }
        TipoFormaRefluo other = (TipoFormaRefluo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipoFormaRefluo[ id=" + id + " ]";
    }
    
}
