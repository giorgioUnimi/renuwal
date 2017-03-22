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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "refluo_tipo_perditearia_alfam", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RefluoTipoPerditeariaAlfam.findAll", query = "SELECT r FROM RefluoTipoPerditeariaAlfam r"),
    @NamedQuery(name = "RefluoTipoPerditeariaAlfam.findById", query = "SELECT r FROM RefluoTipoPerditeariaAlfam r WHERE r.id = :id"),
    @NamedQuery(name = "RefluoTipoPerditeariaAlfam.findByPerditaInAria", query = "SELECT r FROM RefluoTipoPerditeariaAlfam r WHERE r.perditaInAria = :perditaInAria")})
public class RefluoTipoPerditeariaAlfam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "perdita_in_aria", precision = 17, scale = 17)
    private Double perditaInAria;
    @JoinColumn(name = "id_tipo_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private TipoFormaRefluo idTipoFormaRefluo;
    @JoinColumn(name = "id_provenienza_refluo", referencedColumnName = "id")
    @ManyToOne
    private ProvenienzaRefluo idProvenienzaRefluo;

    public RefluoTipoPerditeariaAlfam() {
    }

    public RefluoTipoPerditeariaAlfam(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPerditaInAria() {
        return perditaInAria;
    }

    public void setPerditaInAria(Double perditaInAria) {
        this.perditaInAria = perditaInAria;
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
        if (!(object instanceof RefluoTipoPerditeariaAlfam)) {
            return false;
        }
        RefluoTipoPerditeariaAlfam other = (RefluoTipoPerditeariaAlfam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.RefluoTipoPerditeariaAlfam[ id=" + id + " ]";
    }
    
}
