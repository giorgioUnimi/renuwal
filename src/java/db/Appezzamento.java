/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appezzamento.findAll", query = "SELECT a FROM Appezzamento a"),
    @NamedQuery(name = "Appezzamento.findById", query = "SELECT a FROM Appezzamento a WHERE a.id = :id"),
    @NamedQuery(name = "Appezzamento.findByNome", query = "SELECT a FROM Appezzamento a WHERE a.nome = :nome"),
    @NamedQuery(name = "Appezzamento.findBySuperficie", query = "SELECT a FROM Appezzamento a WHERE a.superficie = :superficie"),
    @NamedQuery(name = "Appezzamento.findBySvz", query = "SELECT a FROM Appezzamento a WHERE a.svz = :svz"),
    @NamedQuery(name = "Appezzamento.findByUpa", query = "SELECT a FROM Appezzamento a WHERE a.upa = :upa")})
public class Appezzamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double superficie;
    private Boolean svz;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short upa;
    @JoinColumn(name = "tipoirrigazione", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TipoIrrigazione tipoirrigazione;
    @JoinColumn(name = "scenario_idscenario", referencedColumnName = "idscenario")
    @ManyToOne
    private ScenarioI scenarioIdscenario;
    @JoinColumn(name = "colturaprecedente_id", referencedColumnName = "id")
    @ManyToOne
    private Colturaprecedente colturaprecedenteId;
    @OneToMany(mappedBy = "idappezzamentoId")
    private Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection;

    public Appezzamento() {
    }

    public Appezzamento(Integer id) {
        this.id = id;
    }

    public Appezzamento(Integer id, short upa) {
        this.id = id;
        this.upa = upa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public Boolean getSvz() {
        return svz;
    }

    public void setSvz(Boolean svz) {
        this.svz = svz;
    }

    public short getUpa() {
        return upa;
    }

    public void setUpa(short upa) {
        this.upa = upa;
    }

    public TipoIrrigazione getTipoirrigazione() {
        return tipoirrigazione;
    }

    public void setTipoirrigazione(TipoIrrigazione tipoirrigazione) {
        this.tipoirrigazione = tipoirrigazione;
    }

    public ScenarioI getScenarioIdscenario() {
        return scenarioIdscenario;
    }

    public void setScenarioIdscenario(ScenarioI scenarioIdscenario) {
        this.scenarioIdscenario = scenarioIdscenario;
    }

    public Colturaprecedente getColturaprecedenteId() {
        return colturaprecedenteId;
    }

    public void setColturaprecedenteId(Colturaprecedente colturaprecedenteId) {
        this.colturaprecedenteId = colturaprecedenteId;
    }

    @XmlTransient
    public Collection<Storicocolturaappezzamento> getStoricocolturaappezzamentoCollection() {
        return storicocolturaappezzamentoCollection;
    }

    public void setStoricocolturaappezzamentoCollection(Collection<Storicocolturaappezzamento> storicocolturaappezzamentoCollection) {
        this.storicocolturaappezzamentoCollection = storicocolturaappezzamentoCollection;
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
        if (!(object instanceof Appezzamento)) {
            return false;
        }
        Appezzamento other = (Appezzamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Appezzamento[ id=" + id + " ]";
    }
    
}
