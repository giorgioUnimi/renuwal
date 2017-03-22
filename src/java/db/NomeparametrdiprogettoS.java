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
@Table(name = "nomeparametrdiprogetto_s", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomeparametrdiprogettoS.findAll", query = "SELECT n FROM NomeparametrdiprogettoS n"),
    @NamedQuery(name = "NomeparametrdiprogettoS.findById", query = "SELECT n FROM NomeparametrdiprogettoS n WHERE n.id = :id"),
    @NamedQuery(name = "NomeparametrdiprogettoS.findByNome", query = "SELECT n FROM NomeparametrdiprogettoS n WHERE n.nome = :nome"),
    @NamedQuery(name = "NomeparametrdiprogettoS.findByDescrizione", query = "SELECT n FROM NomeparametrdiprogettoS n WHERE n.descrizione = :descrizione")})
public class NomeparametrdiprogettoS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String descrizione;
    @OneToMany(mappedBy = "idNomeparametro")
    private Collection<ParametridiprogettoS> parametridiprogettoSCollection;

    public NomeparametrdiprogettoS() {
    }

    public NomeparametrdiprogettoS(Integer id) {
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public Collection<ParametridiprogettoS> getParametridiprogettoSCollection() {
        return parametridiprogettoSCollection;
    }

    public void setParametridiprogettoSCollection(Collection<ParametridiprogettoS> parametridiprogettoSCollection) {
        this.parametridiprogettoSCollection = parametridiprogettoSCollection;
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
        if (!(object instanceof NomeparametrdiprogettoS)) {
            return false;
        }
        NomeparametrdiprogettoS other = (NomeparametrdiprogettoS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.NomeparametrdiprogettoS[ id=" + id + " ]";
    }
    
}
