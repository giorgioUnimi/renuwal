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
@Table(name = "tabella_efficienza_coltura_modalita_tecnica", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TabellaEfficienzaColturaModalitaTecnica.findAll", query = "SELECT t FROM TabellaEfficienzaColturaModalitaTecnica t"),
    @NamedQuery(name = "TabellaEfficienzaColturaModalitaTecnica.findById", query = "SELECT t FROM TabellaEfficienzaColturaModalitaTecnica t WHERE t.id = :id"),
    @NamedQuery(name = "TabellaEfficienzaColturaModalitaTecnica.findByCoefficienteP", query = "SELECT t FROM TabellaEfficienzaColturaModalitaTecnica t WHERE t.coefficienteP = :coefficienteP"),
    @NamedQuery(name = "TabellaEfficienzaColturaModalitaTecnica.findByCoefficienteCorrezione", query = "SELECT t FROM TabellaEfficienzaColturaModalitaTecnica t WHERE t.coefficienteCorrezione = :coefficienteCorrezione"),
    @NamedQuery(name = "TabellaEfficienzaColturaModalitaTecnica.findByPercentualeNAria", query = "SELECT t FROM TabellaEfficienzaColturaModalitaTecnica t WHERE t.percentualeNAria = :percentualeNAria")})
public class TabellaEfficienzaColturaModalitaTecnica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coefficiente_p", precision = 17, scale = 17)
    private Double coefficienteP;
    @Column(name = "coefficiente_correzione", precision = 17, scale = 17)
    private Double coefficienteCorrezione;
    @Column(name = "percentuale_n_aria", precision = 17, scale = 17)
    private Double percentualeNAria;
    @JoinColumn(name = "id_tecnica", referencedColumnName = "id")
    @ManyToOne
    private Tecnicadistribuzione idTecnica;
    @JoinColumn(name = "id_qualita", referencedColumnName = "id")
    @ManyToOne
    private QualitaEfficienza idQualita;
    @JoinColumn(name = "id_modalita", referencedColumnName = "id")
    @ManyToOne
    private Modalitadistribuzione idModalita;
    @JoinColumn(name = "id_gruppo_colturale", referencedColumnName = "id")
    @ManyToOne
    private Gruppocolturale idGruppoColturale;
    @JoinColumn(name = "id_forma_refluo", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo idFormaRefluo;
    @JoinColumn(name = "id_epoca", referencedColumnName = "id")
    @ManyToOne
    private Epoca idEpoca;

    public TabellaEfficienzaColturaModalitaTecnica() {
    }

    public TabellaEfficienzaColturaModalitaTecnica(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCoefficienteP() {
        return coefficienteP;
    }

    public void setCoefficienteP(Double coefficienteP) {
        this.coefficienteP = coefficienteP;
    }

    public Double getCoefficienteCorrezione() {
        return coefficienteCorrezione;
    }

    public void setCoefficienteCorrezione(Double coefficienteCorrezione) {
        this.coefficienteCorrezione = coefficienteCorrezione;
    }

    public Double getPercentualeNAria() {
        return percentualeNAria;
    }

    public void setPercentualeNAria(Double percentualeNAria) {
        this.percentualeNAria = percentualeNAria;
    }

    public Tecnicadistribuzione getIdTecnica() {
        return idTecnica;
    }

    public void setIdTecnica(Tecnicadistribuzione idTecnica) {
        this.idTecnica = idTecnica;
    }

    public QualitaEfficienza getIdQualita() {
        return idQualita;
    }

    public void setIdQualita(QualitaEfficienza idQualita) {
        this.idQualita = idQualita;
    }

    public Modalitadistribuzione getIdModalita() {
        return idModalita;
    }

    public void setIdModalita(Modalitadistribuzione idModalita) {
        this.idModalita = idModalita;
    }

    public Gruppocolturale getIdGruppoColturale() {
        return idGruppoColturale;
    }

    public void setIdGruppoColturale(Gruppocolturale idGruppoColturale) {
        this.idGruppoColturale = idGruppoColturale;
    }

    public Formarefluo getIdFormaRefluo() {
        return idFormaRefluo;
    }

    public void setIdFormaRefluo(Formarefluo idFormaRefluo) {
        this.idFormaRefluo = idFormaRefluo;
    }

    public Epoca getIdEpoca() {
        return idEpoca;
    }

    public void setIdEpoca(Epoca idEpoca) {
        this.idEpoca = idEpoca;
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
        if (!(object instanceof TabellaEfficienzaColturaModalitaTecnica)) {
            return false;
        }
        TabellaEfficienzaColturaModalitaTecnica other = (TabellaEfficienzaColturaModalitaTecnica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TabellaEfficienzaColturaModalitaTecnica[ id=" + id + " ]";
    }
    
}
