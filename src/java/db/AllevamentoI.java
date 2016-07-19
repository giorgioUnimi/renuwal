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
@Table(name = "allevamento_i", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AllevamentoI.findAll", query = "SELECT a FROM AllevamentoI a"),
    @NamedQuery(name = "AllevamentoI.findByNumCapiSpecieStab", query = "SELECT a FROM AllevamentoI a WHERE a.numCapiSpecieStab = :numCapiSpecieStab"),
    @NamedQuery(name = "AllevamentoI.findById", query = "SELECT a FROM AllevamentoI a WHERE a.id = :id")})
public class AllevamentoI implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "num_capi_specie_stab")
    private Integer numCapiSpecieStab;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "speciecategoriaallevamentostabulazioneb_id", referencedColumnName = "id")
    @ManyToOne
    private SpeciecategoriaallevamentostabulazionebS speciecategoriaallevamentostabulazionebId;
    @JoinColumn(name = "idscenario", referencedColumnName = "idscenario")
    @ManyToOne
    private ScenarioI idscenario;

    public AllevamentoI() {
    }

    public AllevamentoI(Long id) {
        this.id = id;
    }

    public Integer getNumCapiSpecieStab() {
        return numCapiSpecieStab;
    }

    public void setNumCapiSpecieStab(Integer numCapiSpecieStab) {
        this.numCapiSpecieStab = numCapiSpecieStab;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpeciecategoriaallevamentostabulazionebS getSpeciecategoriaallevamentostabulazionebId() {
        return speciecategoriaallevamentostabulazionebId;
    }

    public void setSpeciecategoriaallevamentostabulazionebId(SpeciecategoriaallevamentostabulazionebS speciecategoriaallevamentostabulazionebId) {
        this.speciecategoriaallevamentostabulazionebId = speciecategoriaallevamentostabulazionebId;
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
        if (!(object instanceof AllevamentoI)) {
            return false;
        }
        AllevamentoI other = (AllevamentoI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AllevamentoI[ id=" + id + " ]";
    }
    
}
