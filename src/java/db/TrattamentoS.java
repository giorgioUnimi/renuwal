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
import javax.persistence.Id;
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
@Table(name = "trattamento_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrattamentoS.findAll", query = "SELECT t FROM TrattamentoS t"),
    @NamedQuery(name = "TrattamentoS.findById", query = "SELECT t FROM TrattamentoS t WHERE t.id = :id"),
    @NamedQuery(name = "TrattamentoS.findByNome", query = "SELECT t FROM TrattamentoS t WHERE t.nome = :nome")})
public class TrattamentoS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    @OneToMany(mappedBy = "idTrattamento")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection;
    @OneToMany(mappedBy = "trattamento")
    private Collection<AlternativaTrattamento> alternativaTrattamentoCollection;

    public TrattamentoS() {
    }

    public TrattamentoS(Integer id) {
        this.id = id;
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

    @XmlTransient
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection() {
        return parametridiprogettoSCollection;
    }

    public void setParametridiprogettoSCollection(Collection<ParametridiprogettoS> parametridiprogettoSCollection) {
        this.parametridiprogettoSCollection = parametridiprogettoSCollection;
    }

    @XmlTransient
    public Collection<AlternativaTrattamento> getAlternativaTrattamentoCollection() {
        return alternativaTrattamentoCollection;
    }

    public void setAlternativaTrattamentoCollection(Collection<AlternativaTrattamento> alternativaTrattamentoCollection) {
        this.alternativaTrattamentoCollection = alternativaTrattamentoCollection;
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
        if (!(object instanceof TrattamentoS)) {
            return false;
        }
        TrattamentoS other = (TrattamentoS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TrattamentoS[ id=" + id + " ]";
    }
    
}
