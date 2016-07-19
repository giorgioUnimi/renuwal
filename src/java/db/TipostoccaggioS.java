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
@Table(name = "tipostoccaggio_s", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipostoccaggioS.findAll", query = "SELECT t FROM TipostoccaggioS t"),
    @NamedQuery(name = "TipostoccaggioS.findByIdstoccaggio", query = "SELECT t FROM TipostoccaggioS t WHERE t.idstoccaggio = :idstoccaggio"),
    @NamedQuery(name = "TipostoccaggioS.findByDescrizione", query = "SELECT t FROM TipostoccaggioS t WHERE t.descrizione = :descrizione")})
public class TipostoccaggioS implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer idstoccaggio;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String descrizione;
    @OneToMany(mappedBy = "idstoccaggio")
    private Collection<StoccaggioI> stoccaggioICollection;
    @JoinColumn(name = "forma", referencedColumnName = "id")
    @ManyToOne
    private Formarefluo forma;

    public TipostoccaggioS() {
    }

    public TipostoccaggioS(Integer idstoccaggio) {
        this.idstoccaggio = idstoccaggio;
    }

    public Integer getIdstoccaggio() {
        return idstoccaggio;
    }

    public void setIdstoccaggio(Integer idstoccaggio) {
        this.idstoccaggio = idstoccaggio;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @XmlTransient
    public Collection<StoccaggioI> getStoccaggioICollection() {
        return stoccaggioICollection;
    }

    public void setStoccaggioICollection(Collection<StoccaggioI> stoccaggioICollection) {
        this.stoccaggioICollection = stoccaggioICollection;
    }

    public Formarefluo getForma() {
        return forma;
    }

    public void setForma(Formarefluo forma) {
        this.forma = forma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstoccaggio != null ? idstoccaggio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipostoccaggioS)) {
            return false;
        }
        TipostoccaggioS other = (TipostoccaggioS) object;
        if ((this.idstoccaggio == null && other.idstoccaggio != null) || (this.idstoccaggio != null && !this.idstoccaggio.equals(other.idstoccaggio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.TipostoccaggioS[ idstoccaggio=" + idstoccaggio + " ]";
    }
    
}
