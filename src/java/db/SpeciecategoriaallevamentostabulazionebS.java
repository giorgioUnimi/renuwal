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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "speciecategoriaallevamentostabulazioneb_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findAll", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findById", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.id = :id"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByPesoVivoKg", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.pesoVivoKg = :pesoVivoKg"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquame", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquame = :liquame"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameN", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameN = :liquameN"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameNEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameNEx = :liquameNEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameTanEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameTanEx = :liquameTanEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameSt", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameSt = :liquameSt"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameSv", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameSv = :liquameSv"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameP205", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameP205 = :liquameP205"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLiquameK20", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.liquameK20 = :liquameK20"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetame", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letame = :letame"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameN", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameN = :letameN"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameNEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameNEx = :letameNEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameTanEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameTanEx = :letameTanEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameSt", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameSt = :letameSt"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameSv", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameSv = :letameSv"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameP205", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameP205 = :letameP205"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByLetameK20", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.letameK20 = :letameK20"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleN", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleN = :totaleN"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleNEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleNEx = :totaleNEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleTanEx", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleTanEx = :totaleTanEx"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleSt", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleSt = :totaleSt"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleSv", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleSv = :totaleSv"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleP205", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleP205 = :totaleP205"),
    @NamedQuery(name = "SpeciecategoriaallevamentostabulazionebS.findByTotaleK20", query = "SELECT s FROM SpeciecategoriaallevamentostabulazionebS s WHERE s.totaleK20 = :totaleK20")})
public class SpeciecategoriaallevamentostabulazionebS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "peso_vivo_kg", precision = 17, scale = 17)
    private Double pesoVivoKg;
    @Column(precision = 17, scale = 17)
    private Double liquame;
    @Column(name = "liquame_n", precision = 17, scale = 17)
    private Double liquameN;
    @Column(name = "liquame_n_ex", precision = 17, scale = 17)
    private Double liquameNEx;
    @Column(name = "liquame_tan_ex", precision = 17, scale = 17)
    private Double liquameTanEx;
    @Column(name = "liquame_st", precision = 17, scale = 17)
    private Double liquameSt;
    @Column(name = "liquame_sv", precision = 17, scale = 17)
    private Double liquameSv;
    @Column(name = "liquame_p205", precision = 17, scale = 17)
    private Double liquameP205;
    @Column(name = "liquame_k20", precision = 17, scale = 17)
    private Double liquameK20;
    @Column(precision = 17, scale = 17)
    private Double letame;
    @Column(name = "letame_n", precision = 17, scale = 17)
    private Double letameN;
    @Column(name = "letame_n_ex", precision = 17, scale = 17)
    private Double letameNEx;
    @Column(name = "letame_tan_ex", precision = 17, scale = 17)
    private Double letameTanEx;
    @Column(name = "letame_st", precision = 17, scale = 17)
    private Double letameSt;
    @Column(name = "letame_sv", precision = 17, scale = 17)
    private Double letameSv;
    @Column(name = "letame_p205", precision = 17, scale = 17)
    private Double letameP205;
    @Column(name = "letame_k20", precision = 17, scale = 17)
    private Double letameK20;
    @Column(name = "totale_n", precision = 17, scale = 17)
    private Double totaleN;
    @Column(name = "totale_n_ex", precision = 17, scale = 17)
    private Double totaleNEx;
    @Column(name = "totale_tan_ex", precision = 17, scale = 17)
    private Double totaleTanEx;
    @Column(name = "totale_st", precision = 17, scale = 17)
    private Double totaleSt;
    @Column(name = "totale_sv", precision = 17, scale = 17)
    private Double totaleSv;
    @Column(name = "totale_p205", precision = 17, scale = 17)
    private Double totaleP205;
    @Column(name = "totale_k20", precision = 17, scale = 17)
    private Double totaleK20;
    @OneToMany(mappedBy = "speciecategoriaallevamentostabulazionebId")
    private Collection<AllevamentoI> allevamentoICollection;
    @JoinColumn(name = "stabulazioneb_s_id", referencedColumnName = "id")
    @ManyToOne
    private TipostabulazioneS stabulazionebSId;
    @JoinColumn(name = "tipo_allevamento_b_id", referencedColumnName = "id")
    @ManyToOne
    private TipoallevamentoS tipoAllevamentoBId;
    @JoinColumn(name = "specieb_s_cod_specie", referencedColumnName = "cod_specie")
    @ManyToOne
    private SpecieS speciebSCodSpecie;
    @JoinColumn(name = "categoriab_s_id", referencedColumnName = "id")
    @ManyToOne
    private CategoriaS categoriabSId;

    public SpeciecategoriaallevamentostabulazionebS() {
    }

    public SpeciecategoriaallevamentostabulazionebS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPesoVivoKg() {
        return pesoVivoKg;
    }

    public void setPesoVivoKg(Double pesoVivoKg) {
        this.pesoVivoKg = pesoVivoKg;
    }

    public Double getLiquame() {
        return liquame;
    }

    public void setLiquame(Double liquame) {
        this.liquame = liquame;
    }

    public Double getLiquameN() {
        return liquameN;
    }

    public void setLiquameN(Double liquameN) {
        this.liquameN = liquameN;
    }

    public Double getLiquameNEx() {
        return liquameNEx;
    }

    public void setLiquameNEx(Double liquameNEx) {
        this.liquameNEx = liquameNEx;
    }

    public Double getLiquameTanEx() {
        return liquameTanEx;
    }

    public void setLiquameTanEx(Double liquameTanEx) {
        this.liquameTanEx = liquameTanEx;
    }

    public Double getLiquameSt() {
        return liquameSt;
    }

    public void setLiquameSt(Double liquameSt) {
        this.liquameSt = liquameSt;
    }

    public Double getLiquameSv() {
        return liquameSv;
    }

    public void setLiquameSv(Double liquameSv) {
        this.liquameSv = liquameSv;
    }

    public Double getLiquameP205() {
        return liquameP205;
    }

    public void setLiquameP205(Double liquameP205) {
        this.liquameP205 = liquameP205;
    }

    public Double getLiquameK20() {
        return liquameK20;
    }

    public void setLiquameK20(Double liquameK20) {
        this.liquameK20 = liquameK20;
    }

    public Double getLetame() {
        return letame;
    }

    public void setLetame(Double letame) {
        this.letame = letame;
    }

    public Double getLetameN() {
        return letameN;
    }

    public void setLetameN(Double letameN) {
        this.letameN = letameN;
    }

    public Double getLetameNEx() {
        return letameNEx;
    }

    public void setLetameNEx(Double letameNEx) {
        this.letameNEx = letameNEx;
    }

    public Double getLetameTanEx() {
        return letameTanEx;
    }

    public void setLetameTanEx(Double letameTanEx) {
        this.letameTanEx = letameTanEx;
    }

    public Double getLetameSt() {
        return letameSt;
    }

    public void setLetameSt(Double letameSt) {
        this.letameSt = letameSt;
    }

    public Double getLetameSv() {
        return letameSv;
    }

    public void setLetameSv(Double letameSv) {
        this.letameSv = letameSv;
    }

    public Double getLetameP205() {
        return letameP205;
    }

    public void setLetameP205(Double letameP205) {
        this.letameP205 = letameP205;
    }

    public Double getLetameK20() {
        return letameK20;
    }

    public void setLetameK20(Double letameK20) {
        this.letameK20 = letameK20;
    }

    public Double getTotaleN() {
        return totaleN;
    }

    public void setTotaleN(Double totaleN) {
        this.totaleN = totaleN;
    }

    public Double getTotaleNEx() {
        return totaleNEx;
    }

    public void setTotaleNEx(Double totaleNEx) {
        this.totaleNEx = totaleNEx;
    }

    public Double getTotaleTanEx() {
        return totaleTanEx;
    }

    public void setTotaleTanEx(Double totaleTanEx) {
        this.totaleTanEx = totaleTanEx;
    }

    public Double getTotaleSt() {
        return totaleSt;
    }

    public void setTotaleSt(Double totaleSt) {
        this.totaleSt = totaleSt;
    }

    public Double getTotaleSv() {
        return totaleSv;
    }

    public void setTotaleSv(Double totaleSv) {
        this.totaleSv = totaleSv;
    }

    public Double getTotaleP205() {
        return totaleP205;
    }

    public void setTotaleP205(Double totaleP205) {
        this.totaleP205 = totaleP205;
    }

    public Double getTotaleK20() {
        return totaleK20;
    }

    public void setTotaleK20(Double totaleK20) {
        this.totaleK20 = totaleK20;
    }

    @XmlTransient
    public Collection<AllevamentoI> getAllevamentoICollection() {
        return allevamentoICollection;
    }

    public void setAllevamentoICollection(Collection<AllevamentoI> allevamentoICollection) {
        this.allevamentoICollection = allevamentoICollection;
    }

    public TipostabulazioneS getStabulazionebSId() {
        return stabulazionebSId;
    }

    public void setStabulazionebSId(TipostabulazioneS stabulazionebSId) {
        this.stabulazionebSId = stabulazionebSId;
    }

    public TipoallevamentoS getTipoAllevamentoBId() {
        return tipoAllevamentoBId;
    }

    public void setTipoAllevamentoBId(TipoallevamentoS tipoAllevamentoBId) {
        this.tipoAllevamentoBId = tipoAllevamentoBId;
    }

    public SpecieS getSpeciebSCodSpecie() {
        return speciebSCodSpecie;
    }

    public void setSpeciebSCodSpecie(SpecieS speciebSCodSpecie) {
        this.speciebSCodSpecie = speciebSCodSpecie;
    }

    public CategoriaS getCategoriabSId() {
        return categoriabSId;
    }

    public void setCategoriabSId(CategoriaS categoriabSId) {
        this.categoriabSId = categoriabSId;
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
        if (!(object instanceof SpeciecategoriaallevamentostabulazionebS)) {
            return false;
        }
        SpeciecategoriaallevamentostabulazionebS other = (SpeciecategoriaallevamentostabulazionebS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.SpeciecategoriaallevamentostabulazionebS[ id=" + id + " ]";
    }
    
}
