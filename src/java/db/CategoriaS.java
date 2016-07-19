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
@Table(name = "categoria_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaS.findAll", query = "SELECT c FROM CategoriaS c"),
    @NamedQuery(name = "CategoriaS.findById", query = "SELECT c FROM CategoriaS c WHERE c.id = :id"),
    @NamedQuery(name = "CategoriaS.findByDesCatAllev", query = "SELECT c FROM CategoriaS c WHERE c.desCatAllev = :desCatAllev")})
public class CategoriaS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "des_cat_allev", nullable = false, length = 255)
    private String desCatAllev;
    @OneToMany(mappedBy = "categoriabSId")
    private Collection<StabulazioneAllevamentoCategoria> stabulazioneAllevamentoCategoriaCollection;
    @OneToMany(mappedBy = "categoriabSId")
    private Collection<AllevamentoCategoria> allevamentoCategoriaCollection;
    @OneToMany(mappedBy = "categoriabSId")
    private Collection<SpeciecategoriaallevamentostabulazionebS> speciecategoriaallevamentostabulazionebSCollection;
    @OneToMany(mappedBy = "codiceCategoria")
    private Collection<CategoriaSpecie> categoriaSpecieCollection;

    public CategoriaS() {
    }

    public CategoriaS(Integer id) {
        this.id = id;
    }

    public CategoriaS(Integer id, String desCatAllev) {
        this.id = id;
        this.desCatAllev = desCatAllev;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesCatAllev() {
        return desCatAllev;
    }

    public void setDesCatAllev(String desCatAllev) {
        this.desCatAllev = desCatAllev;
    }

    @XmlTransient
    public Collection<StabulazioneAllevamentoCategoria> getStabulazioneAllevamentoCategoriaCollection() {
        return stabulazioneAllevamentoCategoriaCollection;
    }

    public void setStabulazioneAllevamentoCategoriaCollection(Collection<StabulazioneAllevamentoCategoria> stabulazioneAllevamentoCategoriaCollection) {
        this.stabulazioneAllevamentoCategoriaCollection = stabulazioneAllevamentoCategoriaCollection;
    }

    @XmlTransient
    public Collection<AllevamentoCategoria> getAllevamentoCategoriaCollection() {
        return allevamentoCategoriaCollection;
    }

    public void setAllevamentoCategoriaCollection(Collection<AllevamentoCategoria> allevamentoCategoriaCollection) {
        this.allevamentoCategoriaCollection = allevamentoCategoriaCollection;
    }

    @XmlTransient
    public Collection<SpeciecategoriaallevamentostabulazionebS> getSpeciecategoriaallevamentostabulazionebSCollection() {
        return speciecategoriaallevamentostabulazionebSCollection;
    }

    public void setSpeciecategoriaallevamentostabulazionebSCollection(Collection<SpeciecategoriaallevamentostabulazionebS> speciecategoriaallevamentostabulazionebSCollection) {
        this.speciecategoriaallevamentostabulazionebSCollection = speciecategoriaallevamentostabulazionebSCollection;
    }

    @XmlTransient
    public Collection<CategoriaSpecie> getCategoriaSpecieCollection() {
        return categoriaSpecieCollection;
    }

    public void setCategoriaSpecieCollection(Collection<CategoriaSpecie> categoriaSpecieCollection) {
        this.categoriaSpecieCollection = categoriaSpecieCollection;
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
        if (!(object instanceof CategoriaS)) {
            return false;
        }
        CategoriaS other = (CategoriaS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.CategoriaS[ id=" + id + " ]";
    }
    
}
