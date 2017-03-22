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
@Table(name = "tipoallevamento_s", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoallevamentoS.findAll", query = "SELECT t FROM TipoallevamentoS t"),
    @NamedQuery(name = "TipoallevamentoS.findById", query = "SELECT t FROM TipoallevamentoS t WHERE t.id = :id"),
    @NamedQuery(name = "TipoallevamentoS.findByDesAllevamento", query = "SELECT t FROM TipoallevamentoS t WHERE t.desAllevamento = :desAllevamento")})
public class TipoallevamentoS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "des_allevamento", nullable = false, length = 255)
    private String desAllevamento;
    @OneToMany(mappedBy = "tipoAllevamentoBId")
    private Collection<StabulazioneAllevamentoCategoria> stabulazioneAllevamentoCategoriaCollection;
    @OneToMany(mappedBy = "tipoAllevamentoBId")
    private Collection<AllevamentoCategoria> allevamentoCategoriaCollection;
    @OneToMany(mappedBy = "tipoAllevamentoBId")
    private Collection<SpeciecategoriaallevamentostabulazionebS> speciecategoriaallevamentostabulazionebSCollection;
    @OneToMany(mappedBy = "tipoAllevamentoBId")
    private Collection<StabulazioneAllevamento> stabulazioneAllevamentoCollection;

    public TipoallevamentoS() {
    }

    public TipoallevamentoS(Integer id) {
        this.id = id;
    }

    public TipoallevamentoS(Integer id, String desAllevamento) {
        this.id = id;
        this.desAllevamento = desAllevamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesAllevamento() {
        return desAllevamento;
    }

    public void setDesAllevamento(String desAllevamento) {
        this.desAllevamento = desAllevamento;
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
    public Collection<StabulazioneAllevamento> getStabulazioneAllevamentoCollection() {
        return stabulazioneAllevamentoCollection;
    }

    public void setStabulazioneAllevamentoCollection(Collection<StabulazioneAllevamento> stabulazioneAllevamentoCollection) {
        this.stabulazioneAllevamentoCollection = stabulazioneAllevamentoCollection;
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
        if (!(object instanceof TipoallevamentoS)) {
            return false;
        }
        TipoallevamentoS other = (TipoallevamentoS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipoallevamentoS[ id=" + id + " ]";
    }
    
}
