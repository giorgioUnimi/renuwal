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
@Table(name = "tipostabulazione_s", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipostabulazioneS.findAll", query = "SELECT t FROM TipostabulazioneS t"),
    @NamedQuery(name = "TipostabulazioneS.findById", query = "SELECT t FROM TipostabulazioneS t WHERE t.id = :id"),
    @NamedQuery(name = "TipostabulazioneS.findByDesStabulazione", query = "SELECT t FROM TipostabulazioneS t WHERE t.desStabulazione = :desStabulazione")})
public class TipostabulazioneS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(name = "des_stabulazione", length = 255)
    private String desStabulazione;
    @OneToMany(mappedBy = "stabulazionebSId")
    private Collection<StabulazioneAllevamentoCategoria> stabulazioneAllevamentoCategoriaCollection;
    @OneToMany(mappedBy = "stabulazionebSId")
    private Collection<SpeciecategoriaallevamentostabulazionebS> speciecategoriaallevamentostabulazionebSCollection;
    @OneToMany(mappedBy = "stabulazionebSId")
    private Collection<StabulazioneAllevamento> stabulazioneAllevamentoCollection;

    public TipostabulazioneS() {
    }

    public TipostabulazioneS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesStabulazione() {
        return desStabulazione;
    }

    public void setDesStabulazione(String desStabulazione) {
        this.desStabulazione = desStabulazione;
    }

    @XmlTransient
    public Collection<StabulazioneAllevamentoCategoria> getStabulazioneAllevamentoCategoriaCollection() {
        return stabulazioneAllevamentoCategoriaCollection;
    }

    public void setStabulazioneAllevamentoCategoriaCollection(Collection<StabulazioneAllevamentoCategoria> stabulazioneAllevamentoCategoriaCollection) {
        this.stabulazioneAllevamentoCategoriaCollection = stabulazioneAllevamentoCategoriaCollection;
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
        if (!(object instanceof TipostabulazioneS)) {
            return false;
        }
        TipostabulazioneS other = (TipostabulazioneS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipostabulazioneS[ id=" + id + " ]";
    }
    
}
