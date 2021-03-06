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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "alternative_s", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlternativeS.findAll", query = "SELECT a FROM AlternativeS a"),
    @NamedQuery(name = "AlternativeS.findById", query = "SELECT a FROM AlternativeS a WHERE a.id = :id"),
    @NamedQuery(name = "AlternativeS.findByDescrizione", query = "SELECT a FROM AlternativeS a WHERE a.descrizione = :descrizione"),
    @NamedQuery(name = "AlternativeS.findByNome", query = "SELECT a FROM AlternativeS a WHERE a.nome = :nome"),
    @NamedQuery(name = "AlternativeS.findByImmagine", query = "SELECT a FROM AlternativeS a WHERE a.immagine = :immagine")})
public class AlternativeS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @Size(max = 255)
    @Column(length = 255)
    private String nome;
    @Size(max = 255)
    @Column(length = 255)
    private String immagine;
    @OneToMany(mappedBy = "idAlternativa")
    private Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection;
    @OneToMany(mappedBy = "alternativa")
    private Collection<AlternativaTrattamento> alternativaTrattamentoCollection;
    @OneToMany(mappedBy = "idAlternativa")
    private Collection<RisultatoConfronto> risultatoConfrontoCollection;
    @JoinColumn(name = "id_tipo_forma_refluo_palabile", referencedColumnName = "id")
    @ManyToOne
    private TipoFormaRefluo idTipoFormaRefluoPalabile;
    @JoinColumn(name = "id_tipo_forma_refluo_liquido", referencedColumnName = "id")
    @ManyToOne
    private TipoFormaRefluo idTipoFormaRefluoLiquido;

    public AlternativeS() {
    }

    public AlternativeS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    @XmlTransient
    public Collection<EfficienzeNpVincoliNormativi> getEfficienzeNpVincoliNormativiCollection() {
        return efficienzeNpVincoliNormativiCollection;
    }

    public void setEfficienzeNpVincoliNormativiCollection(Collection<EfficienzeNpVincoliNormativi> efficienzeNpVincoliNormativiCollection) {
        this.efficienzeNpVincoliNormativiCollection = efficienzeNpVincoliNormativiCollection;
    }

    @XmlTransient
    public Collection<AlternativaTrattamento> getAlternativaTrattamentoCollection() {
        return alternativaTrattamentoCollection;
    }

    public void setAlternativaTrattamentoCollection(Collection<AlternativaTrattamento> alternativaTrattamentoCollection) {
        this.alternativaTrattamentoCollection = alternativaTrattamentoCollection;
    }

    @XmlTransient
    public Collection<RisultatoConfronto> getRisultatoConfrontoCollection() {
        return risultatoConfrontoCollection;
    }

    public void setRisultatoConfrontoCollection(Collection<RisultatoConfronto> risultatoConfrontoCollection) {
        this.risultatoConfrontoCollection = risultatoConfrontoCollection;
    }

    public TipoFormaRefluo getIdTipoFormaRefluoPalabile() {
        return idTipoFormaRefluoPalabile;
    }

    public void setIdTipoFormaRefluoPalabile(TipoFormaRefluo idTipoFormaRefluoPalabile) {
        this.idTipoFormaRefluoPalabile = idTipoFormaRefluoPalabile;
    }

    public TipoFormaRefluo getIdTipoFormaRefluoLiquido() {
        return idTipoFormaRefluoLiquido;
    }

    public void setIdTipoFormaRefluoLiquido(TipoFormaRefluo idTipoFormaRefluoLiquido) {
        this.idTipoFormaRefluoLiquido = idTipoFormaRefluoLiquido;
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
        if (!(object instanceof AlternativeS)) {
            return false;
        }
        AlternativeS other = (AlternativeS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AlternativeS[ id=" + id + " ]";
    }
    
}
