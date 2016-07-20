/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "appezzamento_efficienza", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppezzamentoEfficienza.findAll", query = "SELECT a FROM AppezzamentoEfficienza a"),
    @NamedQuery(name = "AppezzamentoEfficienza.findByAppezzamentoId", query = "SELECT a FROM AppezzamentoEfficienza a WHERE a.appezzamentoEfficienzaPK.appezzamentoId = :appezzamentoId"),
    @NamedQuery(name = "AppezzamentoEfficienza.findByListaefficienzeId", query = "SELECT a FROM AppezzamentoEfficienza a WHERE a.appezzamentoEfficienzaPK.listaefficienzeId = :listaefficienzeId")})
public class AppezzamentoEfficienza implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AppezzamentoEfficienzaPK appezzamentoEfficienzaPK;
    @JoinColumn(name = "listaefficienze_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Efficienza efficienza;

    public AppezzamentoEfficienza() {
    }

    public AppezzamentoEfficienza(AppezzamentoEfficienzaPK appezzamentoEfficienzaPK) {
        this.appezzamentoEfficienzaPK = appezzamentoEfficienzaPK;
    }

    public AppezzamentoEfficienza(long appezzamentoId, long listaefficienzeId) {
        this.appezzamentoEfficienzaPK = new AppezzamentoEfficienzaPK(appezzamentoId, listaefficienzeId);
    }

    public AppezzamentoEfficienzaPK getAppezzamentoEfficienzaPK() {
        return appezzamentoEfficienzaPK;
    }

    public void setAppezzamentoEfficienzaPK(AppezzamentoEfficienzaPK appezzamentoEfficienzaPK) {
        this.appezzamentoEfficienzaPK = appezzamentoEfficienzaPK;
    }

    public Efficienza getEfficienza() {
        return efficienza;
    }

    public void setEfficienza(Efficienza efficienza) {
        this.efficienza = efficienza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appezzamentoEfficienzaPK != null ? appezzamentoEfficienzaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppezzamentoEfficienza)) {
            return false;
        }
        AppezzamentoEfficienza other = (AppezzamentoEfficienza) object;
        if ((this.appezzamentoEfficienzaPK == null && other.appezzamentoEfficienzaPK != null) || (this.appezzamentoEfficienzaPK != null && !this.appezzamentoEfficienzaPK.equals(other.appezzamentoEfficienzaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AppezzamentoEfficienza[ appezzamentoEfficienzaPK=" + appezzamentoEfficienzaPK + " ]";
    }
    
}
