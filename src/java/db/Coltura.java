/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "coltura", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coltura.findAll", query = "SELECT t FROM Coltura t")   
    })
public class Coltura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descrizione;
    @OneToMany(mappedBy="coltura")
    @JoinTable(schema="allevamento")
    private Collection<db.Colturale_coltura_rotazione> Colturale_coltura_rotazione;
    @OneToOne
    //@JoinColumn(referencedColumnName="idColtura")
    private db.Asportazione asportazione;
    @OneToOne
    private db.Resa resa;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Coltura)) {
            return false;
        }
        Coltura other = (Coltura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Coltura[ id=" + id + " ]";
    }

    /**
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the Colturale_coltura_rotazione
     */
    public Collection<db.Colturale_coltura_rotazione> getColturale_coltura_rotazione() {
        return Colturale_coltura_rotazione;
    }

    /**
     * @param Colturale_coltura_rotazione the Colturale_coltura_rotazione to set
     */
    public void setColturale_coltura_rotazione(Collection<db.Colturale_coltura_rotazione> Colturale_coltura_rotazione) {
        this.Colturale_coltura_rotazione = Colturale_coltura_rotazione;
    }

    /**
     * @return the asportazione
     */
    public db.Asportazione getAsportazione() {
        return asportazione;
    }

    /**
     * @param asportazione the asportazione to set
     */
    public void setAsportazione(db.Asportazione asportazione) {
        this.asportazione = asportazione;
    }

    /**
     * @return the resa
     */
    public db.Resa getResa() {
        return resa;
    }

    /**
     * @param resa the resa to set
     */
    public void setResa(db.Resa resa) {
        this.resa = resa;
    }

    
    
}
