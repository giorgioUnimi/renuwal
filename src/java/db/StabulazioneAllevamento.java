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
@Table(name = "stabulazione_allevamento", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StabulazioneAllevamento.findAll", query = "SELECT s FROM StabulazioneAllevamento s"),
    @NamedQuery(name = "StabulazioneAllevamento.findById", query = "SELECT s FROM StabulazioneAllevamento s WHERE s.id = :id")})
public class StabulazioneAllevamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "stabulazioneb_s_id", referencedColumnName = "id")
    @ManyToOne
    private TipostabulazioneS stabulazionebSId;
    @JoinColumn(name = "tipo_allevamento_b_id", referencedColumnName = "id")
    @ManyToOne
    private TipoallevamentoS tipoAllevamentoBId;

    public StabulazioneAllevamento() {
    }

    public StabulazioneAllevamento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StabulazioneAllevamento)) {
            return false;
        }
        StabulazioneAllevamento other = (StabulazioneAllevamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.StabulazioneAllevamento[ id=" + id + " ]";
    }
    
}
