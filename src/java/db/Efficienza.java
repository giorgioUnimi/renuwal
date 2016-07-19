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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "efficienza", catalog = "renuwal1", schema = "allevamento")
public class Efficienza implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private TipoTerreno idTerreno;
    @OneToOne
    private Coltura idColtura;
    @OneToOne
    private TipomateriaS idTipomateria;
    @OneToOne
    private Dose idDose;
    @OneToOne
    private Epoca idEpoca;
    @OneToOne
    private Modalitadistribuzione idModalitadistribuzione;
    @OneToOne
    private Tecnicadistribuzione idTecnicadistribuzione;
    @OneToOne
    private Tipoefficienza idTipoefficienza;
    @OneToOne
    private Formarefluo idFormarefluo;
    private double valore;

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
        if (!(object instanceof Efficienza)) {
            return false;
        }
        Efficienza other = (Efficienza) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Efficienza[ id=" + id + " ]";
    }

    /**
     * @return the idTipomateria
     */
    public TipomateriaS getIdTipomateria() {
        return idTipomateria;
    }

    /**
     * @param idTipomateria the idTipomateria to set
     */
    public void setIdTipomateria(TipomateriaS idTipomateria) {
        this.idTipomateria = idTipomateria;
    }

    /**
     * @return the idDose
     */
    public Dose getIdDose() {
        return idDose;
    }

    /**
     * @param idDose the idDose to set
     */
    public void setIdDose(Dose idDose) {
        this.idDose = idDose;
    }

    /**
     * @return the idEpoca
     */
    public Epoca getIdEpoca() {
        return idEpoca;
    }

    /**
     * @param idEpoca the idEpoca to set
     */
    public void setIdEpoca(Epoca idEpoca) {
        this.idEpoca = idEpoca;
    }

    /**
     * @return the idModalitadistribuzione
     */
    public Modalitadistribuzione getIdModalitadistribuzione() {
        return idModalitadistribuzione;
    }

    /**
     * @param idModalitadistribuzione the idModalitadistribuzione to set
     */
    public void setIdModalitadistribuzione(Modalitadistribuzione idModalitadistribuzione) {
        this.idModalitadistribuzione = idModalitadistribuzione;
    }

    /**
     * @return the idTecnicadistribuzione
     */
    public Tecnicadistribuzione getIdTecnicadistribuzione() {
        return idTecnicadistribuzione;
    }

    /**
     * @param idTecnicadistribuzione the idTecnicadistribuzione to set
     */
    public void setIdTecnicadistribuzione(Tecnicadistribuzione idTecnicadistribuzione) {
        this.idTecnicadistribuzione = idTecnicadistribuzione;
    }

    /**
     * @return the idTipoefficienza
     */
    public Tipoefficienza getIdTipoefficienza() {
        return idTipoefficienza;
    }

    /**
     * @param idTipoefficienza the idTipoefficienza to set
     */
    public void setIdTipoefficienza(Tipoefficienza idTipoefficienza) {
        this.idTipoefficienza = idTipoefficienza;
    }

    /**
     * @return the idFormarefluo
     */
    public Formarefluo getIdFormarefluo() {
        return idFormarefluo;
    }

    /**
     * @param idFormarefluo the idFormarefluo to set
     */
    public void setIdFormarefluo(Formarefluo idFormarefluo) {
        this.idFormarefluo = idFormarefluo;
    }

    /**
     * @return the idTerreno
     */
    public TipoTerreno getIdTerreno() {
        return idTerreno;
    }

    /**
     * @param idTerreno the idTerreno to set
     */
    public void setIdTerreno(TipoTerreno idTerreno) {
        this.idTerreno = idTerreno;
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
     * @return the valore
     */
    public double getValore() {
        return valore;
    }

    /**
     * @param valore the valore to set
     */
    public void setValore(double valore) {
        this.valore = valore;
    }

    
    
}
