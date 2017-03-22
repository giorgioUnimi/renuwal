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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mese.findAll", query = "SELECT m FROM Mese m"),
    @NamedQuery(name = "Mese.findById", query = "SELECT m FROM Mese m WHERE m.id = :id"),
    @NamedQuery(name = "Mese.findByDescrizione", query = "SELECT m FROM Mese m WHERE m.descrizione = :descrizione")})
public class Mese implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String descrizione;
    @JoinColumn(name = "id_epoca", referencedColumnName = "id")
    @ManyToOne
    private Epoca idEpoca;

    public Mese() {
    }

    public Mese(Integer id) {
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

    public Epoca getIdEpoca() {
        return idEpoca;
    }

    public void setIdEpoca(Epoca idEpoca) {
        this.idEpoca = idEpoca;
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
        if (!(object instanceof Mese)) {
            return false;
        }
        Mese other = (Mese) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Mese[ id=" + id + " ]";
    }
    
}
