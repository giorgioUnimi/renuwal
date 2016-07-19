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
@Table(name = "specie_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SpecieS.findAll", query = "SELECT s FROM SpecieS s"),
    @NamedQuery(name = "SpecieS.findByCodSpecie", query = "SELECT s FROM SpecieS s WHERE s.codSpecie = :codSpecie"),
    @NamedQuery(name = "SpecieS.findByDesSpecie", query = "SELECT s FROM SpecieS s WHERE s.desSpecie = :desSpecie")})
public class SpecieS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_specie", nullable = false)
    private Integer codSpecie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "des_specie", nullable = false, length = 255)
    private String desSpecie;
    @JoinColumn(name = "tipologiaanimale_id", referencedColumnName = "id")
    @ManyToOne
    private TipomateriaS tipologiaanimaleId;
    @OneToMany(mappedBy = "speciebSCodSpecie")
    private Collection<SpeciecategoriaallevamentostabulazionebS> speciecategoriaallevamentostabulazionebSCollection;
    @OneToMany(mappedBy = "codiceSpecie")
    private Collection<CategoriaSpecie> categoriaSpecieCollection;

    public SpecieS() {
    }

    public SpecieS(Integer codSpecie) {
        this.codSpecie = codSpecie;
    }

    public SpecieS(Integer codSpecie, String desSpecie) {
        this.codSpecie = codSpecie;
        this.desSpecie = desSpecie;
    }

    public Integer getCodSpecie() {
        return codSpecie;
    }

    public void setCodSpecie(Integer codSpecie) {
        this.codSpecie = codSpecie;
    }

    public String getDesSpecie() {
        return desSpecie;
    }

    public void setDesSpecie(String desSpecie) {
        this.desSpecie = desSpecie;
    }

    public TipomateriaS getTipologiaanimaleId() {
        return tipologiaanimaleId;
    }

    public void setTipologiaanimaleId(TipomateriaS tipologiaanimaleId) {
        this.tipologiaanimaleId = tipologiaanimaleId;
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
        hash += (codSpecie != null ? codSpecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpecieS)) {
            return false;
        }
        SpecieS other = (SpecieS) object;
        if ((this.codSpecie == null && other.codSpecie != null) || (this.codSpecie != null && !this.codSpecie.equals(other.codSpecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.SpecieS[ codSpecie=" + codSpecie + " ]";
    }
    
}
