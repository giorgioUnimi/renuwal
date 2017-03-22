/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tipo_irrigazione", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIrrigazione.findAll", query = "SELECT t FROM TipoIrrigazione t"),
    @NamedQuery(name = "TipoIrrigazione.findById", query = "SELECT t FROM TipoIrrigazione t WHERE t.id = :id"),
    @NamedQuery(name = "TipoIrrigazione.findByDescrizione", query = "SELECT t FROM TipoIrrigazione t WHERE t.descrizione = :descrizione")})
public class TipoIrrigazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoirrigazione")
    private Collection<Appezzamento> appezzamentoCollection;

    public TipoIrrigazione() {
    }

    public TipoIrrigazione(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public Collection<Appezzamento> getAppezzamentoCollection() {
        return appezzamentoCollection;
    }

    public void setAppezzamentoCollection(Collection<Appezzamento> appezzamentoCollection) {
        this.appezzamentoCollection = appezzamentoCollection;
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
        if (!(object instanceof TipoIrrigazione)) {
            return false;
        }
        TipoIrrigazione other = (TipoIrrigazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipoIrrigazione[ id=" + id + " ]";
    }
    
}
