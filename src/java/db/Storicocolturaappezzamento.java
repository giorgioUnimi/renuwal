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
@Table(catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Storicocolturaappezzamento.findAll", query = "SELECT s FROM Storicocolturaappezzamento s"),
    @NamedQuery(name = "Storicocolturaappezzamento.findById", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.id = :id"),
    @NamedQuery(name = "Storicocolturaappezzamento.findByAsportazioneazoto", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.asportazioneazoto = :asportazioneazoto"),
    @NamedQuery(name = "Storicocolturaappezzamento.findByAsportazionefosforo", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.asportazionefosforo = :asportazionefosforo"),
    @NamedQuery(name = "Storicocolturaappezzamento.findByAsportazionepotassio", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.asportazionepotassio = :asportazionepotassio"),
    @NamedQuery(name = "Storicocolturaappezzamento.findByMas", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.mas = :mas"),
    @NamedQuery(name = "Storicocolturaappezzamento.findByResaAttesa", query = "SELECT s FROM Storicocolturaappezzamento s WHERE s.resaAttesa = :resaAttesa")})
public class Storicocolturaappezzamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double asportazioneazoto;
    @Column(precision = 17, scale = 17)
    private Double asportazionefosforo;
    @Column(precision = 17, scale = 17)
    private Double asportazionepotassio;
    @Column(precision = 17, scale = 17)
    private Double mas;
    @Column(name = "resa_attesa", precision = 17, scale = 17)
    private Double resaAttesa;
    @JoinColumn(name = "rotazione_id", referencedColumnName = "id")
    @ManyToOne
    private Rotazione rotazioneId;
    @JoinColumn(name = "idgruppocolturale_id", referencedColumnName = "id")
    @ManyToOne
    private Gruppocolturale idgruppocolturaleId;
    @JoinColumn(name = "idcoltura_id", referencedColumnName = "id")
    @ManyToOne
    private Coltura idcolturaId;
    @JoinColumn(name = "idappezzamento_id", referencedColumnName = "id")
    @ManyToOne
    private Appezzamento idappezzamentoId;

    public Storicocolturaappezzamento() {
    }

    public Storicocolturaappezzamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAsportazioneazoto() {
        return asportazioneazoto;
    }

    public void setAsportazioneazoto(Double asportazioneazoto) {
        this.asportazioneazoto = asportazioneazoto;
    }

    public Double getAsportazionefosforo() {
        return asportazionefosforo;
    }

    public void setAsportazionefosforo(Double asportazionefosforo) {
        this.asportazionefosforo = asportazionefosforo;
    }

    public Double getAsportazionepotassio() {
        return asportazionepotassio;
    }

    public void setAsportazionepotassio(Double asportazionepotassio) {
        this.asportazionepotassio = asportazionepotassio;
    }

    public Double getMas() {
        return mas;
    }

    public void setMas(Double mas) {
        this.mas = mas;
    }

    public Double getResaAttesa() {
        return resaAttesa;
    }

    public void setResaAttesa(Double resaAttesa) {
        this.resaAttesa = resaAttesa;
    }

    public Rotazione getRotazioneId() {
        return rotazioneId;
    }

    public void setRotazioneId(Rotazione rotazioneId) {
        this.rotazioneId = rotazioneId;
    }

    public Gruppocolturale getIdgruppocolturaleId() {
        return idgruppocolturaleId;
    }

    public void setIdgruppocolturaleId(Gruppocolturale idgruppocolturaleId) {
        this.idgruppocolturaleId = idgruppocolturaleId;
    }

    public Coltura getIdcolturaId() {
        return idcolturaId;
    }

    public void setIdcolturaId(Coltura idcolturaId) {
        this.idcolturaId = idcolturaId;
    }

    public Appezzamento getIdappezzamentoId() {
        return idappezzamentoId;
    }

    public void setIdappezzamentoId(Appezzamento idappezzamentoId) {
        this.idappezzamentoId = idappezzamentoId;
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
        if (!(object instanceof Storicocolturaappezzamento)) {
            return false;
        }
        Storicocolturaappezzamento other = (Storicocolturaappezzamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Storicocolturaappezzamento[ id=" + id + " ]";
    }
    
}
