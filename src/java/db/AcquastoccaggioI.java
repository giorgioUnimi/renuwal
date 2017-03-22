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
@Table(name = "acquastoccaggio_i", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AcquastoccaggioI.findAll", query = "SELECT a FROM AcquastoccaggioI a"),
    @NamedQuery(name = "AcquastoccaggioI.findByIdscenario", query = "SELECT a FROM AcquastoccaggioI a WHERE a.idscenario = :idscenario"),
    @NamedQuery(name = "AcquastoccaggioI.findByAcquaImpianti", query = "SELECT a FROM AcquastoccaggioI a WHERE a.acquaImpianti = :acquaImpianti"),
    @NamedQuery(name = "AcquastoccaggioI.findBySuperficiScoperte", query = "SELECT a FROM AcquastoccaggioI a WHERE a.superficiScoperte = :superficiScoperte"),
    @NamedQuery(name = "AcquastoccaggioI.findByPioggia", query = "SELECT a FROM AcquastoccaggioI a WHERE a.pioggia = :pioggia"),
    @NamedQuery(name = "AcquastoccaggioI.findByCapLiquidi1rac", query = "SELECT a FROM AcquastoccaggioI a WHERE a.capLiquidi1rac = :capLiquidi1rac"),
    @NamedQuery(name = "AcquastoccaggioI.findByCapSolidi1rac", query = "SELECT a FROM AcquastoccaggioI a WHERE a.capSolidi1rac = :capSolidi1rac"),
    @NamedQuery(name = "AcquastoccaggioI.findBySupLiquidi1rac", query = "SELECT a FROM AcquastoccaggioI a WHERE a.supLiquidi1rac = :supLiquidi1rac"),
    @NamedQuery(name = "AcquastoccaggioI.findBySupSolidi1rac", query = "SELECT a FROM AcquastoccaggioI a WHERE a.supSolidi1rac = :supSolidi1rac")})
public class AcquastoccaggioI implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idscenario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "acqua_impianti", precision = 17, scale = 17)
    private Double acquaImpianti;
    @Column(name = "superfici_scoperte", precision = 17, scale = 17)
    private Double superficiScoperte;
    @Column(precision = 17, scale = 17)
    private Double pioggia;
    @Column(name = "cap_liquidi_1rac", precision = 17, scale = 17)
    private Double capLiquidi1rac;
    @Column(name = "cap_solidi_1rac", precision = 17, scale = 17)
    private Double capSolidi1rac;
    @Column(name = "sup_liquidi_1rac", precision = 17, scale = 17)
    private Double supLiquidi1rac;
    @Column(name = "sup_solidi_1rac", precision = 17, scale = 17)
    private Double supSolidi1rac;
    @JoinColumn(name = "idscenario", referencedColumnName = "idscenario", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ScenarioI scenarioI;

    public AcquastoccaggioI() {
    }

    public AcquastoccaggioI(Integer idscenario) {
        this.idscenario = idscenario;
    }

    public Integer getIdscenario() {
        return idscenario;
    }

    public void setIdscenario(Integer idscenario) {
        this.idscenario = idscenario;
    }

    public Double getAcquaImpianti() {
        return acquaImpianti;
    }

    public void setAcquaImpianti(Double acquaImpianti) {
        this.acquaImpianti = acquaImpianti;
    }

    public Double getSuperficiScoperte() {
        return superficiScoperte;
    }

    public void setSuperficiScoperte(Double superficiScoperte) {
        this.superficiScoperte = superficiScoperte;
    }

    public Double getPioggia() {
        return pioggia;
    }

    public void setPioggia(Double pioggia) {
        this.pioggia = pioggia;
    }

    public Double getCapLiquidi1rac() {
        return capLiquidi1rac;
    }

    public void setCapLiquidi1rac(Double capLiquidi1rac) {
        this.capLiquidi1rac = capLiquidi1rac;
    }

    public Double getCapSolidi1rac() {
        return capSolidi1rac;
    }

    public void setCapSolidi1rac(Double capSolidi1rac) {
        this.capSolidi1rac = capSolidi1rac;
    }

    public Double getSupLiquidi1rac() {
        return supLiquidi1rac;
    }

    public void setSupLiquidi1rac(Double supLiquidi1rac) {
        this.supLiquidi1rac = supLiquidi1rac;
    }

    public Double getSupSolidi1rac() {
        return supSolidi1rac;
    }

    public void setSupSolidi1rac(Double supSolidi1rac) {
        this.supSolidi1rac = supSolidi1rac;
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
        if (!(object instanceof AcquastoccaggioI)) {
            return false;
        }
        AcquastoccaggioI other = (AcquastoccaggioI) object;
        if ((this.idscenario == null && other.idscenario != null) || (this.idscenario != null && !this.idscenario.equals(other.idscenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AcquastoccaggioI[ idscenario=" + idscenario + " ]";
    }
    
}
