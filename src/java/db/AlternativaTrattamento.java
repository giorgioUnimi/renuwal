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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "alternativa_trattamento", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlternativaTrattamento.findAll", query = "SELECT a FROM AlternativaTrattamento a"),
    @NamedQuery(name = "AlternativaTrattamento.findById", query = "SELECT a FROM AlternativaTrattamento a WHERE a.id = :id")})
public class AlternativaTrattamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "trattamento", referencedColumnName = "id")
    @ManyToOne
    private TrattamentoS trattamento;
    @JoinColumn(name = "alternativa", referencedColumnName = "id")
    @ManyToOne
    private AlternativeS alternativa;

    public AlternativaTrattamento() {
    }

    public AlternativaTrattamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TrattamentoS getTrattamento() {
        return trattamento;
    }

    public void setTrattamento(TrattamentoS trattamento) {
        this.trattamento = trattamento;
    }

    public AlternativeS getAlternativa() {
        return alternativa;
    }

    public void setAlternativa(AlternativeS alternativa) {
        this.alternativa = alternativa;
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
        if (!(object instanceof AlternativaTrattamento)) {
            return false;
        }
        AlternativaTrattamento other = (AlternativaTrattamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AlternativaTrattamento[ id=" + id + " ]";
    }
    
}
