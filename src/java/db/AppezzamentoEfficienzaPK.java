/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author giorgio
 */
@Embeddable
public class AppezzamentoEfficienzaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "appezzamento_id", nullable = false)
    private long appezzamentoId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "listaefficienze_id", nullable = false)
    private long listaefficienzeId;

    public AppezzamentoEfficienzaPK() {
    }

    public AppezzamentoEfficienzaPK(long appezzamentoId, long listaefficienzeId) {
        this.appezzamentoId = appezzamentoId;
        this.listaefficienzeId = listaefficienzeId;
    }

    public long getAppezzamentoId() {
        return appezzamentoId;
    }

    public void setAppezzamentoId(long appezzamentoId) {
        this.appezzamentoId = appezzamentoId;
    }

    public long getListaefficienzeId() {
        return listaefficienzeId;
    }

    public void setListaefficienzeId(long listaefficienzeId) {
        this.listaefficienzeId = listaefficienzeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) appezzamentoId;
        hash += (int) listaefficienzeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppezzamentoEfficienzaPK)) {
            return false;
        }
        AppezzamentoEfficienzaPK other = (AppezzamentoEfficienzaPK) object;
        if (this.appezzamentoId != other.appezzamentoId) {
            return false;
        }
        if (this.listaefficienzeId != other.listaefficienzeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AppezzamentoEfficienzaPK[ appezzamentoId=" + appezzamentoId + ", listaefficienzeId=" + listaefficienzeId + " ]";
    }
    
}
