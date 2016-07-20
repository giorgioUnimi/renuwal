/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.math.BigInteger;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Efficienza.findAll", query = "SELECT e FROM Efficienza e"),
    @NamedQuery(name = "Efficienza.findById", query = "SELECT e FROM Efficienza e WHERE e.id = :id"),
    @NamedQuery(name = "Efficienza.findByValore", query = "SELECT e FROM Efficienza e WHERE e.valore = :valore"),
    @NamedQuery(name = "Efficienza.findByIdcolturaId", query = "SELECT e FROM Efficienza e WHERE e.idcolturaId = :idcolturaId")})
public class Efficienza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double valore;
    @Column(name = "idcoltura_id")
    private BigInteger idcolturaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "efficienza")
    private Collection<AppezzamentoEfficienza> appezzamentoEfficienzaCollection;
    @JoinColumn(name = "idtipomateria_id", referencedColumnName = "id")
    @ManyToOne
    private TipomateriaS idtipomateriaId;
    @JoinColumn(name = "idtipoefficienza_id", referencedColumnName = "id")
    @ManyToOne
    private Tipoefficienza idtipoefficienzaId;
    @JoinColumn(name = "idterreno_id", referencedColumnName = "id")
    @ManyToOne
    private TipoTerreno idterrenoId;
    @JoinColumn(name = "idtecnicadistribuzione_id", referencedColumnName = "id")
    @ManyToOne
    private Tecnicadistribuzione idtecnicadistribuzioneId;
    @JoinColumn(name = "idmodalitadistribuzione_id", referencedColumnName = "id")
    @ManyToOne
    private Modalitadistribuzione idmodalitadistribuzioneId;
    @JoinColumn(name = "idformarefluo_id", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo idformarefluoId;
    @JoinColumn(name = "idepoca_id", referencedColumnName = "id")
    @ManyToOne
    private Epoca idepocaId;
    @JoinColumn(name = "iddose_id", referencedColumnName = "id")
    @ManyToOne
    private Dose iddoseId;

    public Efficienza() {
    }

    public Efficienza(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValore() {
        return valore;
    }

    public void setValore(Double valore) {
        this.valore = valore;
    }

    public BigInteger getIdcolturaId() {
        return idcolturaId;
    }

    public void setIdcolturaId(BigInteger idcolturaId) {
        this.idcolturaId = idcolturaId;
    }

    @XmlTransient
    public Collection<AppezzamentoEfficienza> getAppezzamentoEfficienzaCollection() {
        return appezzamentoEfficienzaCollection;
    }

    public void setAppezzamentoEfficienzaCollection(Collection<AppezzamentoEfficienza> appezzamentoEfficienzaCollection) {
        this.appezzamentoEfficienzaCollection = appezzamentoEfficienzaCollection;
    }

    public TipomateriaS getIdtipomateriaId() {
        return idtipomateriaId;
    }

    public void setIdtipomateriaId(TipomateriaS idtipomateriaId) {
        this.idtipomateriaId = idtipomateriaId;
    }

    public Tipoefficienza getIdtipoefficienzaId() {
        return idtipoefficienzaId;
    }

    public void setIdtipoefficienzaId(Tipoefficienza idtipoefficienzaId) {
        this.idtipoefficienzaId = idtipoefficienzaId;
    }

    public TipoTerreno getIdterrenoId() {
        return idterrenoId;
    }

    public void setIdterrenoId(TipoTerreno idterrenoId) {
        this.idterrenoId = idterrenoId;
    }

    public Tecnicadistribuzione getIdtecnicadistribuzioneId() {
        return idtecnicadistribuzioneId;
    }

    public void setIdtecnicadistribuzioneId(Tecnicadistribuzione idtecnicadistribuzioneId) {
        this.idtecnicadistribuzioneId = idtecnicadistribuzioneId;
    }

    public Modalitadistribuzione getIdmodalitadistribuzioneId() {
        return idmodalitadistribuzioneId;
    }

    public void setIdmodalitadistribuzioneId(Modalitadistribuzione idmodalitadistribuzioneId) {
        this.idmodalitadistribuzioneId = idmodalitadistribuzioneId;
    }

    public Formarefluo getIdformarefluoId() {
        return idformarefluoId;
    }

    public void setIdformarefluoId(Formarefluo idformarefluoId) {
        this.idformarefluoId = idformarefluoId;
    }

    public Epoca getIdepocaId() {
        return idepocaId;
    }

    public void setIdepocaId(Epoca idepocaId) {
        this.idepocaId = idepocaId;
    }

    public Dose getIddoseId() {
        return iddoseId;
    }

    public void setIddoseId(Dose iddoseId) {
        this.iddoseId = iddoseId;
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
        if (!(object instanceof Efficienza)) {
            return false;
        }
        Efficienza other = (Efficienza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Efficienza[ id=" + id + " ]";
    }
    
}
