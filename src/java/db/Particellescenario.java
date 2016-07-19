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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Particellescenario.findAll", query = "SELECT p FROM Particellescenario p"),
    @NamedQuery(name = "Particellescenario.findByFlagvulnerabilita", query = "SELECT p FROM Particellescenario p WHERE p.flagvulnerabilita = :flagvulnerabilita"),
    @NamedQuery(name = "Particellescenario.findBySuperficieutilizzata", query = "SELECT p FROM Particellescenario p WHERE p.superficieutilizzata = :superficieutilizzata"),
    @NamedQuery(name = "Particellescenario.findById", query = "SELECT p FROM Particellescenario p WHERE p.id = :id")})
public class Particellescenario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(length = 255)
    private String flagvulnerabilita;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double superficieutilizzata;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "idscenario", referencedColumnName = "idscenario")
    @ManyToOne
    private ScenarioI idscenario;

    public Particellescenario() {
    }

    public Particellescenario(Long id) {
        this.id = id;
    }

    public String getFlagvulnerabilita() {
        return flagvulnerabilita;
    }

    public void setFlagvulnerabilita(String flagvulnerabilita) {
        this.flagvulnerabilita = flagvulnerabilita;
    }

    public Double getSuperficieutilizzata() {
        return superficieutilizzata;
    }

    public void setSuperficieutilizzata(Double superficieutilizzata) {
        this.superficieutilizzata = superficieutilizzata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Particellescenario)) {
            return false;
        }
        Particellescenario other = (Particellescenario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Particellescenario[ id=" + id + " ]";
    }
    
}
