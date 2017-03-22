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
@Table(name = "efficienze_np_vincoli_normativi", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EfficienzeNpVincoliNormativi.findAll", query = "SELECT e FROM EfficienzeNpVincoliNormativi e"),
    @NamedQuery(name = "EfficienzeNpVincoliNormativi.findById", query = "SELECT e FROM EfficienzeNpVincoliNormativi e WHERE e.id = :id"),
    @NamedQuery(name = "EfficienzeNpVincoliNormativi.findByCoefficienteN", query = "SELECT e FROM EfficienzeNpVincoliNormativi e WHERE e.coefficienteN = :coefficienteN"),
    @NamedQuery(name = "EfficienzeNpVincoliNormativi.findByCoefficienteP", query = "SELECT e FROM EfficienzeNpVincoliNormativi e WHERE e.coefficienteP = :coefficienteP")})
public class EfficienzeNpVincoliNormativi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coefficiente_n", precision = 17, scale = 17)
    private Double coefficienteN;
    @Column(name = "coefficiente_p", precision = 17, scale = 17)
    private Double coefficienteP;
    @JoinColumn(name = "id_provenienza_refluo", referencedColumnName = "id")
    @ManyToOne
    private ProvenienzaRefluo idProvenienzaRefluo;
    @JoinColumn(name = "id_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo idFormaRefluo;
    @JoinColumn(name = "id_alternativa", referencedColumnName = "id")
    @ManyToOne
    private AlternativeS idAlternativa;

    public EfficienzeNpVincoliNormativi() {
    }

    public EfficienzeNpVincoliNormativi(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCoefficienteN() {
        return coefficienteN;
    }

    public void setCoefficienteN(Double coefficienteN) {
        this.coefficienteN = coefficienteN;
    }

    public Double getCoefficienteP() {
        return coefficienteP;
    }

    public void setCoefficienteP(Double coefficienteP) {
        this.coefficienteP = coefficienteP;
    }

    public ProvenienzaRefluo getIdProvenienzaRefluo() {
        return idProvenienzaRefluo;
    }

    public void setIdProvenienzaRefluo(ProvenienzaRefluo idProvenienzaRefluo) {
        this.idProvenienzaRefluo = idProvenienzaRefluo;
    }

    public Formarefluo getIdFormaRefluo() {
        return idFormaRefluo;
    }

    public void setIdFormaRefluo(Formarefluo idFormaRefluo) {
        this.idFormaRefluo = idFormaRefluo;
    }

    public AlternativeS getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(AlternativeS idAlternativa) {
        this.idAlternativa = idAlternativa;
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
        if (!(object instanceof EfficienzeNpVincoliNormativi)) {
            return false;
        }
        EfficienzeNpVincoliNormativi other = (EfficienzeNpVincoliNormativi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.EfficienzeNpVincoliNormativi[ id=" + id + " ]";
    }
    
}
