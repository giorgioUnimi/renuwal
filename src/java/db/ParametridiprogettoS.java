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
@Table(name = "parametridiprogetto_s", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParametridiprogettoS.findAll", query = "SELECT p FROM ParametridiprogettoS p"),
    @NamedQuery(name = "ParametridiprogettoS.findByContenutoattributo", query = "SELECT p FROM ParametridiprogettoS p WHERE p.contenutoattributo = :contenutoattributo"),
    @NamedQuery(name = "ParametridiprogettoS.findByValore", query = "SELECT p FROM ParametridiprogettoS p WHERE p.valore = :valore"),
    @NamedQuery(name = "ParametridiprogettoS.findByTipo", query = "SELECT p FROM ParametridiprogettoS p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "ParametridiprogettoS.findByEntitacoinvolte", query = "SELECT p FROM ParametridiprogettoS p WHERE p.entitacoinvolte = :entitacoinvolte"),
    @NamedQuery(name = "ParametridiprogettoS.findByDiscriminante", query = "SELECT p FROM ParametridiprogettoS p WHERE p.discriminante = :discriminante"),
    @NamedQuery(name = "ParametridiprogettoS.findByContenutoattributo1", query = "SELECT p FROM ParametridiprogettoS p WHERE p.contenutoattributo1 = :contenutoattributo1"),
    @NamedQuery(name = "ParametridiprogettoS.findById", query = "SELECT p FROM ParametridiprogettoS p WHERE p.id = :id")})
public class ParametridiprogettoS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(length = 255)
    private String contenutoattributo;
    @Size(max = 255)
    @Column(length = 255)
    private String valore;
    @Size(max = 255)
    @Column(length = 255)
    private String tipo;
    private Integer entitacoinvolte;
    @Size(max = 255)
    @Column(length = 255)
    private String discriminante;
    @Size(max = 255)
    @Column(length = 255)
    private String contenutoattributo1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "id_trattamento", referencedColumnName = "id")
    @ManyToOne
    private TrattamentoS idTrattamento;
    @JoinColumn(name = "id_scenario", referencedColumnName = "idscenario")
    @ManyToOne
    private ScenarioI idScenario;
    @JoinColumn(name = "id_nomeparametro", referencedColumnName = "id")
    @ManyToOne
    private NomeparametrdiprogettoS idNomeparametro;
    @JoinColumn(name = "id_entita", referencedColumnName = "id")
    @ManyToOne
    private NomeentitaattributiS idEntita;
    @JoinColumn(name = "id_entita1", referencedColumnName = "id")
    @ManyToOne
    private NomeentitaattributiS idEntita1;
    @JoinColumn(name = "id_attributo", referencedColumnName = "id")
    @ManyToOne
    private NomeentitaattributiS idAttributo;
    @JoinColumn(name = "id_attributo1", referencedColumnName = "id")
    @ManyToOne
    private NomeentitaattributiS idAttributo1;

    public ParametridiprogettoS() {
    }

    public ParametridiprogettoS(Long id) {
        this.id = id;
    }

    public String getContenutoattributo() {
        return contenutoattributo;
    }

    public void setContenutoattributo(String contenutoattributo) {
        this.contenutoattributo = contenutoattributo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getEntitacoinvolte() {
        return entitacoinvolte;
    }

    public void setEntitacoinvolte(Integer entitacoinvolte) {
        this.entitacoinvolte = entitacoinvolte;
    }

    public String getDiscriminante() {
        return discriminante;
    }

    public void setDiscriminante(String discriminante) {
        this.discriminante = discriminante;
    }

    public String getContenutoattributo1() {
        return contenutoattributo1;
    }

    public void setContenutoattributo1(String contenutoattributo1) {
        this.contenutoattributo1 = contenutoattributo1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrattamentoS getIdTrattamento() {
        return idTrattamento;
    }

    public void setIdTrattamento(TrattamentoS idTrattamento) {
        this.idTrattamento = idTrattamento;
    }

    public ScenarioI getIdScenario() {
        return idScenario;
    }

    public void setIdScenario(ScenarioI idScenario) {
        this.idScenario = idScenario;
    }

    public NomeparametrdiprogettoS getIdNomeparametro() {
        return idNomeparametro;
    }

    public void setIdNomeparametro(NomeparametrdiprogettoS idNomeparametro) {
        this.idNomeparametro = idNomeparametro;
    }

    public NomeentitaattributiS getIdEntita() {
        return idEntita;
    }

    public void setIdEntita(NomeentitaattributiS idEntita) {
        this.idEntita = idEntita;
    }

    public NomeentitaattributiS getIdEntita1() {
        return idEntita1;
    }

    public void setIdEntita1(NomeentitaattributiS idEntita1) {
        this.idEntita1 = idEntita1;
    }

    public NomeentitaattributiS getIdAttributo() {
        return idAttributo;
    }

    public void setIdAttributo(NomeentitaattributiS idAttributo) {
        this.idAttributo = idAttributo;
    }

    public NomeentitaattributiS getIdAttributo1() {
        return idAttributo1;
    }

    public void setIdAttributo1(NomeentitaattributiS idAttributo1) {
        this.idAttributo1 = idAttributo1;
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
        if (!(object instanceof ParametridiprogettoS)) {
            return false;
        }
        ParametridiprogettoS other = (ParametridiprogettoS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ParametridiprogettoS[ id=" + id + " ]";
    }
    
}
