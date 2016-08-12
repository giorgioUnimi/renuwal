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
    @NamedQuery(name = "Efficienze.findAll", query = "SELECT e FROM Efficienze e"),
    @NamedQuery(name = "Efficienze.findById", query = "SELECT e FROM Efficienze e WHERE e.id = :id"),
    @NamedQuery(name = "Efficienze.findByEfficienzaAzotoLiquame", query = "SELECT e FROM Efficienze e WHERE e.efficienzaAzotoLiquame = :efficienzaAzotoLiquame"),
    @NamedQuery(name = "Efficienze.findByEfficienzaAzotoLetame", query = "SELECT e FROM Efficienze e WHERE e.efficienzaAzotoLetame = :efficienzaAzotoLetame"),
    @NamedQuery(name = "Efficienze.findByEfficienzaFosforo", query = "SELECT e FROM Efficienze e WHERE e.efficienzaFosforo = :efficienzaFosforo"),
    @NamedQuery(name = "Efficienze.findByDaDigestato", query = "SELECT e FROM Efficienze e WHERE e.daDigestato = :daDigestato")})
public class Efficienze implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "efficienza_azoto_liquame", precision = 17, scale = 17)
    private Double efficienzaAzotoLiquame;
    @Column(name = "efficienza_azoto_letame", precision = 17, scale = 17)
    private Double efficienzaAzotoLetame;
    @Column(name = "efficienza_fosforo", precision = 17, scale = 17)
    private Double efficienzaFosforo;
    @Column(name = "da_digestato")
    private Boolean daDigestato;
    @JoinColumn(name = "tipomateria_id", referencedColumnName = "id")
    @ManyToOne
    private TipomateriaS tipomateriaId;

    public Efficienze() {
    }

    public Efficienze(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEfficienzaAzotoLiquame() {
        return efficienzaAzotoLiquame;
    }

    public void setEfficienzaAzotoLiquame(Double efficienzaAzotoLiquame) {
        this.efficienzaAzotoLiquame = efficienzaAzotoLiquame;
    }

    public Double getEfficienzaAzotoLetame() {
        return efficienzaAzotoLetame;
    }

    public void setEfficienzaAzotoLetame(Double efficienzaAzotoLetame) {
        this.efficienzaAzotoLetame = efficienzaAzotoLetame;
    }

    public Double getEfficienzaFosforo() {
        return efficienzaFosforo;
    }

    public void setEfficienzaFosforo(Double efficienzaFosforo) {
        this.efficienzaFosforo = efficienzaFosforo;
    }

    public Boolean getDaDigestato() {
        return daDigestato;
    }

    public void setDaDigestato(Boolean daDigestato) {
        this.daDigestato = daDigestato;
    }

    public TipomateriaS getTipomateriaId() {
        return tipomateriaId;
    }

    public void setTipomateriaId(TipomateriaS tipomateriaId) {
        this.tipomateriaId = tipomateriaId;
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
        if (!(object instanceof Efficienze)) {
            return false;
        }
        Efficienze other = (Efficienze) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Efficienze[ id=" + id + " ]";
    }
    
}
