/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Residuoprecessione.findAll", query = "SELECT r FROM Residuoprecessione r"),
    @NamedQuery(name = "Residuoprecessione.findById", query = "SELECT r FROM Residuoprecessione r WHERE r.id = :id"),
    @NamedQuery(name = "Residuoprecessione.findByQuantita", query = "SELECT r FROM Residuoprecessione r WHERE r.quantita = :quantita")})
public class Residuoprecessione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double quantita;
    @JoinColumn(name = "idcoltura_id", referencedColumnName = "id")
    @ManyToOne
    private Coltura idcolturaId;

    public Residuoprecessione() {
    }

    public Residuoprecessione(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQuantita() {
        return quantita;
    }

    public void setQuantita(Double quantita) {
        this.quantita = quantita;
    }

    public Coltura getIdcolturaId() {
        return idcolturaId;
    }

    public void setIdcolturaId(Coltura idcolturaId) {
        this.idcolturaId = idcolturaId;
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
        if (!(object instanceof Residuoprecessione)) {
            return false;
        }
        Residuoprecessione other = (Residuoprecessione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Residuoprecessione[ id=" + id + " ]";
    }
    
}
