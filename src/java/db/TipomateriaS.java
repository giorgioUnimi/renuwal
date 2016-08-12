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
@Table(name = "tipomateria_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipomateriaS.findAll", query = "SELECT t FROM TipomateriaS t"),
    @NamedQuery(name = "TipomateriaS.findById", query = "SELECT t FROM TipomateriaS t WHERE t.id = :id"),
    @NamedQuery(name = "TipomateriaS.findByNome", query = "SELECT t FROM TipomateriaS t WHERE t.nome = :nome"),
    @NamedQuery(name = "TipomateriaS.findByAnimale", query = "SELECT t FROM TipomateriaS t WHERE t.animale = :animale")})
public class TipomateriaS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    private Boolean animale;
    @OneToMany(mappedBy = "idtipomateriaId")
    private Collection<Efficienza> efficienzaCollection;
    @OneToMany(mappedBy = "tipomateriaId")
    private Collection<Efficienze> efficienzeCollection;
    @OneToMany(mappedBy = "tipologiaanimaleId")
    private Collection<SpecieS> specieSCollection;

    public TipomateriaS() {
    }

    public TipomateriaS(Integer id) {
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

    public Boolean getAnimale() {
        return animale;
    }

    public void setAnimale(Boolean animale) {
        this.animale = animale;
    }

    @XmlTransient
    public Collection<Efficienza> getEfficienzaCollection() {
        return efficienzaCollection;
    }

    public void setEfficienzaCollection(Collection<Efficienza> efficienzaCollection) {
        this.efficienzaCollection = efficienzaCollection;
    }

    @XmlTransient
    public Collection<Efficienze> getEfficienzeCollection() {
        return efficienzeCollection;
    }

    public void setEfficienzeCollection(Collection<Efficienze> efficienzeCollection) {
        this.efficienzeCollection = efficienzeCollection;
    }

    @XmlTransient
    public Collection<SpecieS> getSpecieSCollection() {
        return specieSCollection;
    }

    public void setSpecieSCollection(Collection<SpecieS> specieSCollection) {
        this.specieSCollection = specieSCollection;
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
        if (!(object instanceof TipomateriaS)) {
            return false;
        }
        TipomateriaS other = (TipomateriaS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipomateriaS[ id=" + id + " ]";
    }
    
}
