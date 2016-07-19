/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "stoccaggio_i", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StoccaggioI.findAll", query = "SELECT s FROM StoccaggioI s"),
    @NamedQuery(name = "StoccaggioI.findByCapacita", query = "SELECT s FROM StoccaggioI s WHERE s.capacita = :capacita"),
    @NamedQuery(name = "StoccaggioI.findBySuperficietotale", query = "SELECT s FROM StoccaggioI s WHERE s.superficietotale = :superficietotale"),
    @NamedQuery(name = "StoccaggioI.findBySuperficiescoperta", query = "SELECT s FROM StoccaggioI s WHERE s.superficiescoperta = :superficiescoperta"),
    @NamedQuery(name = "StoccaggioI.findById", query = "SELECT s FROM StoccaggioI s WHERE s.id = :id")})
public class StoccaggioI implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double capacita;
    @Column(precision = 17, scale = 17)
    private Double superficietotale;
    @Column(precision = 17, scale = 17)
    private Double superficiescoperta;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "idstoccaggio", referencedColumnName = "idstoccaggio")
    @ManyToOne
    private TipostoccaggioS idstoccaggio;
    @JoinColumn(name = "idscenario", referencedColumnName = "idscenario")
    @ManyToOne
    private ScenarioI idscenario;

    public StoccaggioI() {
    }

    public StoccaggioI(Long id) {
        this.id = id;
    }

    public Double getCapacita() {
        return capacita;
    }

    public void setCapacita(Double capacita) {
        this.capacita = capacita;
    }

    public Double getSuperficietotale() {
        return superficietotale;
    }

    public void setSuperficietotale(Double superficietotale) {
        this.superficietotale = superficietotale;
    }

    public Double getSuperficiescoperta() {
        return superficiescoperta;
    }

    public void setSuperficiescoperta(Double superficiescoperta) {
        this.superficiescoperta = superficiescoperta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipostoccaggioS getIdstoccaggio() {
        return idstoccaggio;
    }

    public void setIdstoccaggio(TipostoccaggioS idstoccaggio) {
        this.idstoccaggio = idstoccaggio;
    }

    public ScenarioI getIdscenario() {
        return idscenario;
    }

    public void setIdscenario(ScenarioI idscenario) {
        this.idscenario = idscenario;
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
        if (!(object instanceof StoccaggioI)) {
            return false;
        }
        StoccaggioI other = (StoccaggioI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.StoccaggioI[ id=" + id + " ]";
    }
    
}
