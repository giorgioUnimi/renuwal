/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "mapping_refluo_tipo_alfam", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MappingRefluoTipoAlfam.findAll", query = "SELECT m FROM MappingRefluoTipoAlfam m"),
    @NamedQuery(name = "MappingRefluoTipoAlfam.findById", query = "SELECT m FROM MappingRefluoTipoAlfam m WHERE m.id = :id"),
    @NamedQuery(name = "MappingRefluoTipoAlfam.findByDescrizioneAlfam", query = "SELECT m FROM MappingRefluoTipoAlfam m WHERE m.descrizioneAlfam = :descrizioneAlfam"),
    @NamedQuery(name = "MappingRefluoTipoAlfam.findByLMt1", query = "SELECT m FROM MappingRefluoTipoAlfam m WHERE m.lMt1 = :lMt1"),
    @NamedQuery(name = "MappingRefluoTipoAlfam.findByCoeTipo", query = "SELECT m FROM MappingRefluoTipoAlfam m WHERE m.coeTipo = :coeTipo")})
public class MappingRefluoTipoAlfam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(name = "descrizione_alfam", length = 20)
    private String descrizioneAlfam;
    @Column(name = "l_mt1")
    private Integer lMt1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coe_tipo", precision = 17, scale = 17)
    private Double coeTipo;
    @JoinColumn(name = "id_tipo_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private TipoFormaRefluo idTipoFormaRefluo;
    @JoinColumn(name = "id_provenienza_refluo", referencedColumnName = "id")
    @ManyToOne
    private ProvenienzaRefluo idProvenienzaRefluo;

    public MappingRefluoTipoAlfam() {
    }

    public MappingRefluoTipoAlfam(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizioneAlfam() {
        return descrizioneAlfam;
    }

    public void setDescrizioneAlfam(String descrizioneAlfam) {
        this.descrizioneAlfam = descrizioneAlfam;
    }

    public Integer getLMt1() {
        return lMt1;
    }

    public void setLMt1(Integer lMt1) {
        this.lMt1 = lMt1;
    }

    public Double getCoeTipo() {
        return coeTipo;
    }

    public void setCoeTipo(Double coeTipo) {
        this.coeTipo = coeTipo;
    }

    public TipoFormaRefluo getIdTipoFormaRefluo() {
        return idTipoFormaRefluo;
    }

    public void setIdTipoFormaRefluo(TipoFormaRefluo idTipoFormaRefluo) {
        this.idTipoFormaRefluo = idTipoFormaRefluo;
    }

    public ProvenienzaRefluo getIdProvenienzaRefluo() {
        return idProvenienzaRefluo;
    }

    public void setIdProvenienzaRefluo(ProvenienzaRefluo idProvenienzaRefluo) {
        this.idProvenienzaRefluo = idProvenienzaRefluo;
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
        if (!(object instanceof MappingRefluoTipoAlfam)) {
            return false;
        }
        MappingRefluoTipoAlfam other = (MappingRefluoTipoAlfam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.MappingRefluoTipoAlfam[ id=" + id + " ]";
    }
    
}
