/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "scenario_i", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScenarioI.findAll", query = "SELECT s FROM ScenarioI s"),
    @NamedQuery(name = "ScenarioI.findByIdscenario", query = "SELECT s FROM ScenarioI s WHERE s.idscenario = :idscenario"),
    @NamedQuery(name = "ScenarioI.findByDescrizione", query = "SELECT s FROM ScenarioI s WHERE s.descrizione = :descrizione")})
public class ScenarioI implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long idscenario;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String descrizione;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "scenarioI")
    private CaratteristicheChmiche caratteristicheChmiche;
    @OneToMany(mappedBy = "idScenario")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection;
    @OneToMany(mappedBy = "idscenario")
    private Collection<StoccaggioI> stoccaggioICollection;
    @OneToMany(mappedBy = "scenarioIdscenario")
    private Collection<Appezzamento> appezzamentoCollection;
    @OneToMany(mappedBy = "idscenario")
    private Collection<AllevamentoI> allevamentoICollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "scenarioI")
    private RisultatoConfronto risultatoConfronto;
    @JoinColumn(name = "id_aziendeanni", referencedColumnName = "id")
    @ManyToOne
    private AziendeAnni idAziendeanni;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "scenarioI")
    private AcquastoccaggioI acquastoccaggioI;

    public ScenarioI() {
    }

    public ScenarioI(Long idscenario) {
        this.idscenario = idscenario;
    }

    public Long getIdscenario() {
        return idscenario;
    }

    public void setIdscenario(Long idscenario) {
        this.idscenario = idscenario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public CaratteristicheChmiche getCaratteristicheChmiche() {
        return caratteristicheChmiche;
    }

    public void setCaratteristicheChmiche(CaratteristicheChmiche caratteristicheChmiche) {
        this.caratteristicheChmiche = caratteristicheChmiche;
    }

    @XmlTransient
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection() {
        return parametridiprogettoSCollection;
    }

    public void setParametridiprogettoSCollection(Collection<ParametridiprogettoS> parametridiprogettoSCollection) {
        this.parametridiprogettoSCollection = parametridiprogettoSCollection;
    }

    @XmlTransient
    public Collection<StoccaggioI> getStoccaggioICollection() {
        return stoccaggioICollection;
    }

    public void setStoccaggioICollection(Collection<StoccaggioI> stoccaggioICollection) {
        this.stoccaggioICollection = stoccaggioICollection;
    }

    @XmlTransient
    public Collection<Appezzamento> getAppezzamentoCollection() {
        return appezzamentoCollection;
    }

    public void setAppezzamentoCollection(Collection<Appezzamento> appezzamentoCollection) {
        this.appezzamentoCollection = appezzamentoCollection;
    }

    @XmlTransient
    public Collection<AllevamentoI> getAllevamentoICollection() {
        return allevamentoICollection;
    }

    public void setAllevamentoICollection(Collection<AllevamentoI> allevamentoICollection) {
        this.allevamentoICollection = allevamentoICollection;
    }

    public RisultatoConfronto getRisultatoConfronto() {
        return risultatoConfronto;
    }

    public void setRisultatoConfronto(RisultatoConfronto risultatoConfronto) {
        this.risultatoConfronto = risultatoConfronto;
    }

    public AziendeAnni getIdAziendeanni() {
        return idAziendeanni;
    }

    public void setIdAziendeanni(AziendeAnni idAziendeanni) {
        this.idAziendeanni = idAziendeanni;
    }

    public AcquastoccaggioI getAcquastoccaggioI() {
        return acquastoccaggioI;
    }

    public void setAcquastoccaggioI(AcquastoccaggioI acquastoccaggioI) {
        this.acquastoccaggioI = acquastoccaggioI;
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
        if (!(object instanceof ScenarioI)) {
            return false;
        }
        ScenarioI other = (ScenarioI) object;
        if ((this.idscenario == null && other.idscenario != null) || (this.idscenario != null && !this.idscenario.equals(other.idscenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ScenarioI[ idscenario=" + idscenario + " ]";
    }
    
}
