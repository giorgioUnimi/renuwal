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
@Table(name = "colturale_coltura_rotazione", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColturaleColturaRotazione.findAll", query = "SELECT c FROM ColturaleColturaRotazione c"),
    @NamedQuery(name = "ColturaleColturaRotazione.findById", query = "SELECT c FROM ColturaleColturaRotazione c WHERE c.id = :id")})
public class ColturaleColturaRotazione implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "rotazione_id", referencedColumnName = "id")
    @ManyToOne
    private Rotazione rotazioneId;
    @JoinColumn(name = "gruppocolturale_id", referencedColumnName = "id")
    @ManyToOne
    private Gruppocolturale gruppocolturaleId;
    @JoinColumn(name = "coltura_id", referencedColumnName = "id")
    @ManyToOne
    private Coltura colturaId;

    public ColturaleColturaRotazione() {
    }

    public ColturaleColturaRotazione(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rotazione getRotazioneId() {
        return rotazioneId;
    }

    public void setRotazioneId(Rotazione rotazioneId) {
        this.rotazioneId = rotazioneId;
    }

    public Gruppocolturale getGruppocolturaleId() {
        return gruppocolturaleId;
    }

    public void setGruppocolturaleId(Gruppocolturale gruppocolturaleId) {
        this.gruppocolturaleId = gruppocolturaleId;
    }

    public Coltura getColturaId() {
        return colturaId;
    }

    public void setColturaId(Coltura colturaId) {
        this.colturaId = colturaId;
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
        if (!(object instanceof ColturaleColturaRotazione)) {
            return false;
        }
        ColturaleColturaRotazione other = (ColturaleColturaRotazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.ColturaleColturaRotazione[ id=" + id + " ]";
    }
    
}
