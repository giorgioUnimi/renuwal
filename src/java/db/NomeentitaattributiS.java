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
@Table(name = "nomeentitaattributi_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomeentitaattributiS.findAll", query = "SELECT n FROM NomeentitaattributiS n"),
    @NamedQuery(name = "NomeentitaattributiS.findById", query = "SELECT n FROM NomeentitaattributiS n WHERE n.id = :id"),
    @NamedQuery(name = "NomeentitaattributiS.findByNome", query = "SELECT n FROM NomeentitaattributiS n WHERE n.nome = :nome")})
public class NomeentitaattributiS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    @OneToMany(mappedBy = "idEntita1")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection;
    @OneToMany(mappedBy = "idEntita")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection1;
    @OneToMany(mappedBy = "idAttributo1")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection2;
    @OneToMany(mappedBy = "idAttributo")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection3;

    public NomeentitaattributiS() {
    }

    public NomeentitaattributiS(Integer id) {
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
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection1() {
        return parametridiprogettoSCollection1;
    }

    public void setParametridiprogettoSCollection1(Collection<ParametridiprogettoS> parametridiprogettoSCollection1) {
        this.parametridiprogettoSCollection1 = parametridiprogettoSCollection1;
    }

    @XmlTransient
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection2() {
        return parametridiprogettoSCollection2;
    }

    public void setParametridiprogettoSCollection2(Collection<ParametridiprogettoS> parametridiprogettoSCollection2) {
        this.parametridiprogettoSCollection2 = parametridiprogettoSCollection2;
    }

    @XmlTransient
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection3() {
        return parametridiprogettoSCollection3;
    }

    public void setParametridiprogettoSCollection3(Collection<ParametridiprogettoS> parametridiprogettoSCollection3) {
        this.parametridiprogettoSCollection3 = parametridiprogettoSCollection3;
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
        if (!(object instanceof NomeentitaattributiS)) {
            return false;
        }
        NomeentitaattributiS other = (NomeentitaattributiS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.NomeentitaattributiS[ id=" + id + " ]";
    }
    
}
