/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "storicocolturaappezzamento", catalog = "renuwal1", schema = "allevamento")
public class StoricoColturaAppezzamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Coltura idColtura;
    @ManyToOne
    private Appezzamento idAppezzamento;
    @ManyToOne
    private Rotazione rotazione;
    @OneToOne
    private GruppoColturale idGruppoColturale;
    private double asportazioneAzoto; 
    private double asportazioneFosforo;
    private double asportazionePotassio;
    private double resa_attesa;
    private double mas;
  

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
        if (!(object instanceof StoricoColturaAppezzamento)) {
            return false;
        }
        StoricoColturaAppezzamento other = (StoricoColturaAppezzamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.StoricoColturaAppezzamento[ id=" + id + " ]";
    }

    /**
     * @return the idColtura
     */
    public Coltura getIdColtura() {
        return idColtura;
    }

    /**
     * @param idColtura the idColtura to set
     */
    public void setIdColtura(Coltura idColtura) {
        this.idColtura = idColtura;
    }

    /**
     * @return the idAppezzamento
     */
    public Appezzamento getIdAppezzamento() {
        return idAppezzamento;
    }

    /**
     * @param idAppezzamento the idAppezzamento to set
     */
    public void setIdAppezzamento(Appezzamento idAppezzamento) {
        this.idAppezzamento = idAppezzamento;
    }

    /**
     * @return the rotazione
     */
    public Rotazione getRotazione() {
        return rotazione;
    }

    /**
     * @param rotazione the rotazione to set
     */
    public void setRotazione(Rotazione rotazione) {
        this.rotazione = rotazione;
    }

    /**
     * @return the idGruppoColturale
     */
    public GruppoColturale getIdGruppoColturale() {
        return idGruppoColturale;
    }

    /**
     * @param idGruppoColturale the idGruppoColturale to set
     */
    public void setIdGruppoColturale(GruppoColturale idGruppoColturale) {
        this.idGruppoColturale = idGruppoColturale;
    }

    /**
     * @return the asportazioneAzoto
     */
    public double getAsportazioneAzoto() {
        return asportazioneAzoto;
    }

    /**
     * @param asportazioneAzoto the asportazioneAzoto to set
     */
    public void setAsportazioneAzoto(double asportazioneAzoto) {
        this.asportazioneAzoto = asportazioneAzoto;
    }

    /**
     * @return the asportazioneFosforo
     */
    public double getAsportazioneFosforo() {
        return asportazioneFosforo;
    }

    /**
     * @param asportazioneFosforo the asportazioneFosforo to set
     */
    public void setAsportazioneFosforo(double asportazioneFosforo) {
        this.asportazioneFosforo = asportazioneFosforo;
    }

    /**
     * @return the asportazionePotassio
     */
    public double getAsportazionePotassio() {
        return asportazionePotassio;
    }

    /**
     * @param asportazionePotassio the asportazionePotassio to set
     */
    public void setAsportazionePotassio(double asportazionePotassio) {
        this.asportazionePotassio = asportazionePotassio;
    }

    /**
     * @return the resa_attesa
     */
    public double getResa_attesa() {
        return resa_attesa;
    }

    /**
     * @param resa_attesa the resa_attesa to set
     */
    public void setResa_attesa(double resa_attesa) {
        this.resa_attesa = resa_attesa;
    }

    /**
     * @return the mas
     */
    public double getMas() {
        return mas;
    }

    /**
     * @param mas the mas to set
     */
    public void setMas(double mas) {
        this.mas = mas;
    }

 
}
