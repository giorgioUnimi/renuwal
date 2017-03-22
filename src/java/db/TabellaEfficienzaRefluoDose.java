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
@Table(name = "tabella_efficienza_refluo_dose", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TabellaEfficienzaRefluoDose.findAll", query = "SELECT t FROM TabellaEfficienzaRefluoDose t"),
    @NamedQuery(name = "TabellaEfficienzaRefluoDose.findById", query = "SELECT t FROM TabellaEfficienzaRefluoDose t WHERE t.id = :id"),
    @NamedQuery(name = "TabellaEfficienzaRefluoDose.findByCoefficienteN", query = "SELECT t FROM TabellaEfficienzaRefluoDose t WHERE t.coefficienteN = :coefficienteN")})
public class TabellaEfficienzaRefluoDose implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coefficiente_n", precision = 17, scale = 17)
    private Double coefficienteN;
    @JoinColumn(name = "id_tipo_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private TipoFormaRefluo idTipoFormaRefluo;
    @JoinColumn(name = "id_qualita", referencedColumnName = "id")
    @ManyToOne
    private QualitaEfficienza idQualita;
    @JoinColumn(name = "id_provenienza_refluo", referencedColumnName = "id")
    @ManyToOne
    private ProvenienzaRefluo idProvenienzaRefluo;
    @JoinColumn(name = "id_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo idFormaRefluo;
    @JoinColumn(name = "id_dose", referencedColumnName = "id")
    @ManyToOne
    private Dose idDose;

    public TabellaEfficienzaRefluoDose() {
    }

    public TabellaEfficienzaRefluoDose(Integer id) {
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

    public TipoFormaRefluo getIdTipoFormaRefluo() {
        return idTipoFormaRefluo;
    }

    public void setIdTipoFormaRefluo(TipoFormaRefluo idTipoFormaRefluo) {
        this.idTipoFormaRefluo = idTipoFormaRefluo;
    }

    public QualitaEfficienza getIdQualita() {
        return idQualita;
    }

    public void setIdQualita(QualitaEfficienza idQualita) {
        this.idQualita = idQualita;
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

    public Dose getIdDose() {
        return idDose;
    }

    public void setIdDose(Dose idDose) {
        this.idDose = idDose;
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
        if (!(object instanceof TabellaEfficienzaRefluoDose)) {
            return false;
        }
        TabellaEfficienzaRefluoDose other = (TabellaEfficienzaRefluoDose) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TabellaEfficienzaRefluoDose[ id=" + id + " ]";
    }
    
}
