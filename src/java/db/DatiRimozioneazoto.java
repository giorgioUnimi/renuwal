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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "dati_rimozioneazoto", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatiRimozioneazoto.findAll", query = "SELECT d FROM DatiRimozioneazoto d"),
    @NamedQuery(name = "DatiRimozioneazoto.findByIdscenario", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.idscenario = :idscenario"),
    @NamedQuery(name = "DatiRimozioneazoto.findBySauzv", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.sauzv = :sauzv"),
    @NamedQuery(name = "DatiRimozioneazoto.findBySauzvn", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.sauzvn = :sauzvn"),
    @NamedQuery(name = "DatiRimozioneazoto.findByMasLordo", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.masLordo = :masLordo"),
    @NamedQuery(name = "DatiRimozioneazoto.findByEffMedia", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.effMedia = :effMedia"),
    @NamedQuery(name = "DatiRimozioneazoto.findByMaxnsau", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.maxnsau = :maxnsau"),
    @NamedQuery(name = "DatiRimozioneazoto.findByMaxncolture", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.maxncolture = :maxncolture"),
    @NamedQuery(name = "DatiRimozioneazoto.findByDistanzaCentroParticelle", query = "SELECT d FROM DatiRimozioneazoto d WHERE d.distanzaCentroParticelle = :distanzaCentroParticelle")})
public class DatiRimozioneazoto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long idscenario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double sauzv;
    @Column(precision = 17, scale = 17)
    private Double sauzvn;
    @Column(name = "mas_lordo", precision = 17, scale = 17)
    private Double masLordo;
    @Column(name = "eff_media", precision = 17, scale = 17)
    private Double effMedia;
    @Column(precision = 17, scale = 17)
    private Double maxnsau;
    @Column(precision = 17, scale = 17)
    private Double maxncolture;
    @Column(name = "distanza_centro_particelle")
    private Integer distanzaCentroParticelle;
    @JoinColumn(name = "idscenario", referencedColumnName = "idscenario", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ScenarioI scenarioI;

    public DatiRimozioneazoto() {
    }

    public DatiRimozioneazoto(Long idscenario) {
        this.idscenario = idscenario;
    }

    public Long getIdscenario() {
        return idscenario;
    }

    public void setIdscenario(Long idscenario) {
        this.idscenario = idscenario;
    }

    public Double getSauzv() {
        return sauzv;
    }

    public void setSauzv(Double sauzv) {
        this.sauzv = sauzv;
    }

    public Double getSauzvn() {
        return sauzvn;
    }

    public void setSauzvn(Double sauzvn) {
        this.sauzvn = sauzvn;
    }

    public Double getMasLordo() {
        return masLordo;
    }

    public void setMasLordo(Double masLordo) {
        this.masLordo = masLordo;
    }

    public Double getEffMedia() {
        return effMedia;
    }

    public void setEffMedia(Double effMedia) {
        this.effMedia = effMedia;
    }

    public Double getMaxnsau() {
        return maxnsau;
    }

    public void setMaxnsau(Double maxnsau) {
        this.maxnsau = maxnsau;
    }

    public Double getMaxncolture() {
        return maxncolture;
    }

    public void setMaxncolture(Double maxncolture) {
        this.maxncolture = maxncolture;
    }

    public Integer getDistanzaCentroParticelle() {
        return distanzaCentroParticelle;
    }

    public void setDistanzaCentroParticelle(Integer distanzaCentroParticelle) {
        this.distanzaCentroParticelle = distanzaCentroParticelle;
    }

    public ScenarioI getScenarioI() {
        return scenarioI;
    }

    public void setScenarioI(ScenarioI scenarioI) {
        this.scenarioI = scenarioI;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idscenario != null ? idscenario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatiRimozioneazoto)) {
            return false;
        }
        DatiRimozioneazoto other = (DatiRimozioneazoto) object;
        if ((this.idscenario == null && other.idscenario != null) || (this.idscenario != null && !this.idscenario.equals(other.idscenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.DatiRimozioneazoto[ idscenario=" + idscenario + " ]";
    }
    
}
