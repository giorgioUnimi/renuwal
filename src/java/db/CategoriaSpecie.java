/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "categoria_specie", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaSpecie.findAll", query = "SELECT c FROM CategoriaSpecie c"),
    @NamedQuery(name = "CategoriaSpecie.findById", query = "SELECT c FROM CategoriaSpecie c WHERE c.id = :id")})
public class CategoriaSpecie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "codice_specie", referencedColumnName = "cod_specie")
    @ManyToOne
    private SpecieS codiceSpecie;
    @JoinColumn(name = "codice_categoria", referencedColumnName = "id")
    @ManyToOne
    private CategoriaS codiceCategoria;

    public CategoriaSpecie() {
    }

    public CategoriaSpecie(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecieS getCodiceSpecie() {
        return codiceSpecie;
    }

    public void setCodiceSpecie(SpecieS codiceSpecie) {
        this.codiceSpecie = codiceSpecie;
    }

    public CategoriaS getCodiceCategoria() {
        return codiceCategoria;
    }

    public void setCodiceCategoria(CategoriaS codiceCategoria) {
        this.codiceCategoria = codiceCategoria;
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
        if (!(object instanceof CategoriaSpecie)) {
            return false;
        }
        CategoriaSpecie other = (CategoriaSpecie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.CategoriaSpecie[ id=" + id + " ]";
    }
    
}
