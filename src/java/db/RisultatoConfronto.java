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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "risultato_confronto", catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RisultatoConfronto.findAll", query = "SELECT r FROM RisultatoConfronto r"),
    @NamedQuery(name = "RisultatoConfronto.findByIdScenario", query = "SELECT r FROM RisultatoConfronto r WHERE r.idScenario = :idScenario"),
    @NamedQuery(name = "RisultatoConfronto.findByM3LBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3LBov = :m3LBov"),
    @NamedQuery(name = "RisultatoConfronto.findByTknLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknLBov = :tknLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByTanLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanLBov = :tanLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByDmLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmLBov = :dmLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByVsLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsLBov = :vsLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByKLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.kLBov = :kLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByPLBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.pLBov = :pLBov"),
    @NamedQuery(name = "RisultatoConfronto.findByM3LSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3LSui = :m3LSui"),
    @NamedQuery(name = "RisultatoConfronto.findByTknLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknLSui = :tknLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByTanLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanLSui = :tanLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByDmLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmLSui = :dmLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByVsLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsLSui = :vsLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByKLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.kLSui = :kLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByPLSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.pLSui = :pLSui"),
    @NamedQuery(name = "RisultatoConfronto.findByM3LAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3LAvi = :m3LAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByTknLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknLAvi = :tknLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByTanLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanLAvi = :tanLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByDmLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmLAvi = :dmLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByVsLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsLAvi = :vsLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByKLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.kLAvi = :kLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByPLAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.pLAvi = :pLAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByM3LAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3LAlt = :m3LAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByTknLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknLAlt = :tknLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByTanLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanLAlt = :tanLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByDmLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmLAlt = :dmLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByVsLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsLAlt = :vsLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByKLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.kLAlt = :kLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByPLAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.pLAlt = :pLAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByM3LBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3LBio = :m3LBio"),
    @NamedQuery(name = "RisultatoConfronto.findByTknLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknLBio = :tknLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByTanLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanLBio = :tanLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByDmLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmLBio = :dmLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByVsLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsLBio = :vsLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByKLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.kLBio = :kLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByPLBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.pLBio = :pLBio"),
    @NamedQuery(name = "RisultatoConfronto.findByM3PBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3PBov = :m3PBov"),
    @NamedQuery(name = "RisultatoConfronto.findByTknPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknPBov = :tknPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByTanPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanPBov = :tanPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByDmPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmPBov = :dmPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByVsPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsPBov = :vsPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByKPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.kPBov = :kPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByPPBov", query = "SELECT r FROM RisultatoConfronto r WHERE r.pPBov = :pPBov"),
    @NamedQuery(name = "RisultatoConfronto.findByM3PSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3PSui = :m3PSui"),
    @NamedQuery(name = "RisultatoConfronto.findByTknPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknPSui = :tknPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByTanPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanPSui = :tanPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByDmPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmPSui = :dmPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByVsPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsPSui = :vsPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByKPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.kPSui = :kPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByPPSui", query = "SELECT r FROM RisultatoConfronto r WHERE r.pPSui = :pPSui"),
    @NamedQuery(name = "RisultatoConfronto.findByM3PAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3PAvi = :m3PAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByTknPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknPAvi = :tknPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByTanPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanPAvi = :tanPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByDmPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmPAvi = :dmPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByVsPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsPAvi = :vsPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByKPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.kPAvi = :kPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByPPAvi", query = "SELECT r FROM RisultatoConfronto r WHERE r.pPAvi = :pPAvi"),
    @NamedQuery(name = "RisultatoConfronto.findByM3PAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3PAlt = :m3PAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByTknPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknPAlt = :tknPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByTanPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanPAlt = :tanPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByDmPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmPAlt = :dmPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByVsPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsPAlt = :vsPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByKPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.kPAlt = :kPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByPPAlt", query = "SELECT r FROM RisultatoConfronto r WHERE r.pPAlt = :pPAlt"),
    @NamedQuery(name = "RisultatoConfronto.findByM3PBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.m3PBio = :m3PBio"),
    @NamedQuery(name = "RisultatoConfronto.findByTknPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.tknPBio = :tknPBio"),
    @NamedQuery(name = "RisultatoConfronto.findByTanPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.tanPBio = :tanPBio"),
    @NamedQuery(name = "RisultatoConfronto.findByDmPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.dmPBio = :dmPBio"),
    @NamedQuery(name = "RisultatoConfronto.findByVsPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.vsPBio = :vsPBio"),
    @NamedQuery(name = "RisultatoConfronto.findByKPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.kPBio = :kPBio"),
    @NamedQuery(name = "RisultatoConfronto.findByPPBio", query = "SELECT r FROM RisultatoConfronto r WHERE r.pPBio = :pPBio")})
public class RisultatoConfronto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scenario", nullable = false)
    private Integer idScenario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "m3_l_bov", precision = 17, scale = 17)
    private Double m3LBov;
    @Column(name = "tkn_l_bov", precision = 17, scale = 17)
    private Double tknLBov;
    @Column(name = "tan_l_bov", precision = 17, scale = 17)
    private Double tanLBov;
    @Column(name = "dm_l_bov", precision = 17, scale = 17)
    private Double dmLBov;
    @Column(name = "vs_l_bov", precision = 17, scale = 17)
    private Double vsLBov;
    @Column(name = "k_l_bov", precision = 17, scale = 17)
    private Double kLBov;
    @Column(name = "p_l_bov", precision = 17, scale = 17)
    private Double pLBov;
    @Column(name = "m3_l_sui", precision = 17, scale = 17)
    private Double m3LSui;
    @Column(name = "tkn_l_sui", precision = 17, scale = 17)
    private Double tknLSui;
    @Column(name = "tan_l_sui", precision = 17, scale = 17)
    private Double tanLSui;
    @Column(name = "dm_l_sui", precision = 17, scale = 17)
    private Double dmLSui;
    @Column(name = "vs_l_sui", precision = 17, scale = 17)
    private Double vsLSui;
    @Column(name = "k_l_sui", precision = 17, scale = 17)
    private Double kLSui;
    @Column(name = "p_l_sui", precision = 17, scale = 17)
    private Double pLSui;
    @Column(name = "m3_l_avi", precision = 17, scale = 17)
    private Double m3LAvi;
    @Column(name = "tkn_l_avi", precision = 17, scale = 17)
    private Double tknLAvi;
    @Column(name = "tan_l_avi", precision = 17, scale = 17)
    private Double tanLAvi;
    @Column(name = "dm_l_avi", precision = 17, scale = 17)
    private Double dmLAvi;
    @Column(name = "vs_l_avi", precision = 17, scale = 17)
    private Double vsLAvi;
    @Column(name = "k_l_avi", precision = 17, scale = 17)
    private Double kLAvi;
    @Column(name = "p_l_avi", precision = 17, scale = 17)
    private Double pLAvi;
    @Column(name = "m3_l_alt", precision = 17, scale = 17)
    private Double m3LAlt;
    @Column(name = "tkn_l_alt", precision = 17, scale = 17)
    private Double tknLAlt;
    @Column(name = "tan_l_alt", precision = 17, scale = 17)
    private Double tanLAlt;
    @Column(name = "dm_l_alt", precision = 17, scale = 17)
    private Double dmLAlt;
    @Column(name = "vs_l_alt", precision = 17, scale = 17)
    private Double vsLAlt;
    @Column(name = "k_l_alt", precision = 17, scale = 17)
    private Double kLAlt;
    @Column(name = "p_l_alt", precision = 17, scale = 17)
    private Double pLAlt;
    @Column(name = "m3_l_bio", precision = 17, scale = 17)
    private Double m3LBio;
    @Column(name = "tkn_l_bio", precision = 17, scale = 17)
    private Double tknLBio;
    @Column(name = "tan_l_bio", precision = 17, scale = 17)
    private Double tanLBio;
    @Column(name = "dm_l_bio", precision = 17, scale = 17)
    private Double dmLBio;
    @Column(name = "vs_l_bio", precision = 17, scale = 17)
    private Double vsLBio;
    @Column(name = "k_l_bio", precision = 17, scale = 17)
    private Double kLBio;
    @Column(name = "p_l_bio", precision = 17, scale = 17)
    private Double pLBio;
    @Column(name = "m3_p_bov", precision = 17, scale = 17)
    private Double m3PBov;
    @Column(name = "tkn_p_bov", precision = 17, scale = 17)
    private Double tknPBov;
    @Column(name = "tan_p_bov", precision = 17, scale = 17)
    private Double tanPBov;
    @Column(name = "dm_p_bov", precision = 17, scale = 17)
    private Double dmPBov;
    @Column(name = "vs_p_bov", precision = 17, scale = 17)
    private Double vsPBov;
    @Column(name = "k_p_bov", precision = 17, scale = 17)
    private Double kPBov;
    @Column(name = "p_p_bov", precision = 17, scale = 17)
    private Double pPBov;
    @Column(name = "m3_p_sui", precision = 17, scale = 17)
    private Double m3PSui;
    @Column(name = "tkn_p_sui", precision = 17, scale = 17)
    private Double tknPSui;
    @Column(name = "tan_p_sui", precision = 17, scale = 17)
    private Double tanPSui;
    @Column(name = "dm_p_sui", precision = 17, scale = 17)
    private Double dmPSui;
    @Column(name = "vs_p_sui", precision = 17, scale = 17)
    private Double vsPSui;
    @Column(name = "k_p_sui", precision = 17, scale = 17)
    private Double kPSui;
    @Column(name = "p_p_sui", precision = 17, scale = 17)
    private Double pPSui;
    @Column(name = "m3_p_avi", precision = 17, scale = 17)
    private Double m3PAvi;
    @Column(name = "tkn_p_avi", precision = 17, scale = 17)
    private Double tknPAvi;
    @Column(name = "tan_p_avi", precision = 17, scale = 17)
    private Double tanPAvi;
    @Column(name = "dm_p_avi", precision = 17, scale = 17)
    private Double dmPAvi;
    @Column(name = "vs_p_avi", precision = 17, scale = 17)
    private Double vsPAvi;
    @Column(name = "k_p_avi", precision = 17, scale = 17)
    private Double kPAvi;
    @Column(name = "p_p_avi", precision = 17, scale = 17)
    private Double pPAvi;
    @Column(name = "m3_p_alt", precision = 17, scale = 17)
    private Double m3PAlt;
    @Column(name = "tkn_p_alt", precision = 17, scale = 17)
    private Double tknPAlt;
    @Column(name = "tan_p_alt", precision = 17, scale = 17)
    private Double tanPAlt;
    @Column(name = "dm_p_alt", precision = 17, scale = 17)
    private Double dmPAlt;
    @Column(name = "vs_p_alt", precision = 17, scale = 17)
    private Double vsPAlt;
    @Column(name = "k_p_alt", precision = 17, scale = 17)
    private Double kPAlt;
    @Column(name = "p_p_alt", precision = 17, scale = 17)
    private Double pPAlt;
    @Column(name = "m3_p_bio", precision = 17, scale = 17)
    private Double m3PBio;
    @Column(name = "tkn_p_bio", precision = 17, scale = 17)
    private Double tknPBio;
    @Column(name = "tan_p_bio", precision = 17, scale = 17)
    private Double tanPBio;
    @Column(name = "dm_p_bio", precision = 17, scale = 17)
    private Double dmPBio;
    @Column(name = "vs_p_bio", precision = 17, scale = 17)
    private Double vsPBio;
    @Column(name = "k_p_bio", precision = 17, scale = 17)
    private Double kPBio;
    @Column(name = "p_p_bio", precision = 17, scale = 17)
    private Double pPBio;
    @JoinColumn(name = "id_scenario", referencedColumnName = "idscenario", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ScenarioI scenarioI;
    @JoinColumn(name = "id_alternativa", referencedColumnName = "id")
    @ManyToOne
    private AlternativeS idAlternativa;

    public RisultatoConfronto() {
    }

    public RisultatoConfronto(Integer idScenario) {
        this.idScenario = idScenario;
    }

    public Integer getIdScenario() {
        return idScenario;
    }

    public void setIdScenario(Integer idScenario) {
        this.idScenario = idScenario;
    }

    public Double getM3LBov() {
        return m3LBov;
    }

    public void setM3LBov(Double m3LBov) {
        this.m3LBov = m3LBov;
    }

    public Double getTknLBov() {
        return tknLBov;
    }

    public void setTknLBov(Double tknLBov) {
        this.tknLBov = tknLBov;
    }

    public Double getTanLBov() {
        return tanLBov;
    }

    public void setTanLBov(Double tanLBov) {
        this.tanLBov = tanLBov;
    }

    public Double getDmLBov() {
        return dmLBov;
    }

    public void setDmLBov(Double dmLBov) {
        this.dmLBov = dmLBov;
    }

    public Double getVsLBov() {
        return vsLBov;
    }

    public void setVsLBov(Double vsLBov) {
        this.vsLBov = vsLBov;
    }

    public Double getKLBov() {
        return kLBov;
    }

    public void setKLBov(Double kLBov) {
        this.kLBov = kLBov;
    }

    public Double getPLBov() {
        return pLBov;
    }

    public void setPLBov(Double pLBov) {
        this.pLBov = pLBov;
    }

    public Double getM3LSui() {
        return m3LSui;
    }

    public void setM3LSui(Double m3LSui) {
        this.m3LSui = m3LSui;
    }

    public Double getTknLSui() {
        return tknLSui;
    }

    public void setTknLSui(Double tknLSui) {
        this.tknLSui = tknLSui;
    }

    public Double getTanLSui() {
        return tanLSui;
    }

    public void setTanLSui(Double tanLSui) {
        this.tanLSui = tanLSui;
    }

    public Double getDmLSui() {
        return dmLSui;
    }

    public void setDmLSui(Double dmLSui) {
        this.dmLSui = dmLSui;
    }

    public Double getVsLSui() {
        return vsLSui;
    }

    public void setVsLSui(Double vsLSui) {
        this.vsLSui = vsLSui;
    }

    public Double getKLSui() {
        return kLSui;
    }

    public void setKLSui(Double kLSui) {
        this.kLSui = kLSui;
    }

    public Double getPLSui() {
        return pLSui;
    }

    public void setPLSui(Double pLSui) {
        this.pLSui = pLSui;
    }

    public Double getM3LAvi() {
        return m3LAvi;
    }

    public void setM3LAvi(Double m3LAvi) {
        this.m3LAvi = m3LAvi;
    }

    public Double getTknLAvi() {
        return tknLAvi;
    }

    public void setTknLAvi(Double tknLAvi) {
        this.tknLAvi = tknLAvi;
    }

    public Double getTanLAvi() {
        return tanLAvi;
    }

    public void setTanLAvi(Double tanLAvi) {
        this.tanLAvi = tanLAvi;
    }

    public Double getDmLAvi() {
        return dmLAvi;
    }

    public void setDmLAvi(Double dmLAvi) {
        this.dmLAvi = dmLAvi;
    }

    public Double getVsLAvi() {
        return vsLAvi;
    }

    public void setVsLAvi(Double vsLAvi) {
        this.vsLAvi = vsLAvi;
    }

    public Double getKLAvi() {
        return kLAvi;
    }

    public void setKLAvi(Double kLAvi) {
        this.kLAvi = kLAvi;
    }

    public Double getPLAvi() {
        return pLAvi;
    }

    public void setPLAvi(Double pLAvi) {
        this.pLAvi = pLAvi;
    }

    public Double getM3LAlt() {
        return m3LAlt;
    }

    public void setM3LAlt(Double m3LAlt) {
        this.m3LAlt = m3LAlt;
    }

    public Double getTknLAlt() {
        return tknLAlt;
    }

    public void setTknLAlt(Double tknLAlt) {
        this.tknLAlt = tknLAlt;
    }

    public Double getTanLAlt() {
        return tanLAlt;
    }

    public void setTanLAlt(Double tanLAlt) {
        this.tanLAlt = tanLAlt;
    }

    public Double getDmLAlt() {
        return dmLAlt;
    }

    public void setDmLAlt(Double dmLAlt) {
        this.dmLAlt = dmLAlt;
    }

    public Double getVsLAlt() {
        return vsLAlt;
    }

    public void setVsLAlt(Double vsLAlt) {
        this.vsLAlt = vsLAlt;
    }

    public Double getKLAlt() {
        return kLAlt;
    }

    public void setKLAlt(Double kLAlt) {
        this.kLAlt = kLAlt;
    }

    public Double getPLAlt() {
        return pLAlt;
    }

    public void setPLAlt(Double pLAlt) {
        this.pLAlt = pLAlt;
    }

    public Double getM3LBio() {
        return m3LBio;
    }

    public void setM3LBio(Double m3LBio) {
        this.m3LBio = m3LBio;
    }

    public Double getTknLBio() {
        return tknLBio;
    }

    public void setTknLBio(Double tknLBio) {
        this.tknLBio = tknLBio;
    }

    public Double getTanLBio() {
        return tanLBio;
    }

    public void setTanLBio(Double tanLBio) {
        this.tanLBio = tanLBio;
    }

    public Double getDmLBio() {
        return dmLBio;
    }

    public void setDmLBio(Double dmLBio) {
        this.dmLBio = dmLBio;
    }

    public Double getVsLBio() {
        return vsLBio;
    }

    public void setVsLBio(Double vsLBio) {
        this.vsLBio = vsLBio;
    }

    public Double getKLBio() {
        return kLBio;
    }

    public void setKLBio(Double kLBio) {
        this.kLBio = kLBio;
    }

    public Double getPLBio() {
        return pLBio;
    }

    public void setPLBio(Double pLBio) {
        this.pLBio = pLBio;
    }

    public Double getM3PBov() {
        return m3PBov;
    }

    public void setM3PBov(Double m3PBov) {
        this.m3PBov = m3PBov;
    }

    public Double getTknPBov() {
        return tknPBov;
    }

    public void setTknPBov(Double tknPBov) {
        this.tknPBov = tknPBov;
    }

    public Double getTanPBov() {
        return tanPBov;
    }

    public void setTanPBov(Double tanPBov) {
        this.tanPBov = tanPBov;
    }

    public Double getDmPBov() {
        return dmPBov;
    }

    public void setDmPBov(Double dmPBov) {
        this.dmPBov = dmPBov;
    }

    public Double getVsPBov() {
        return vsPBov;
    }

    public void setVsPBov(Double vsPBov) {
        this.vsPBov = vsPBov;
    }

    public Double getKPBov() {
        return kPBov;
    }

    public void setKPBov(Double kPBov) {
        this.kPBov = kPBov;
    }

    public Double getPPBov() {
        return pPBov;
    }

    public void setPPBov(Double pPBov) {
        this.pPBov = pPBov;
    }

    public Double getM3PSui() {
        return m3PSui;
    }

    public void setM3PSui(Double m3PSui) {
        this.m3PSui = m3PSui;
    }

    public Double getTknPSui() {
        return tknPSui;
    }

    public void setTknPSui(Double tknPSui) {
        this.tknPSui = tknPSui;
    }

    public Double getTanPSui() {
        return tanPSui;
    }

    public void setTanPSui(Double tanPSui) {
        this.tanPSui = tanPSui;
    }

    public Double getDmPSui() {
        return dmPSui;
    }

    public void setDmPSui(Double dmPSui) {
        this.dmPSui = dmPSui;
    }

    public Double getVsPSui() {
        return vsPSui;
    }

    public void setVsPSui(Double vsPSui) {
        this.vsPSui = vsPSui;
    }

    public Double getKPSui() {
        return kPSui;
    }

    public void setKPSui(Double kPSui) {
        this.kPSui = kPSui;
    }

    public Double getPPSui() {
        return pPSui;
    }

    public void setPPSui(Double pPSui) {
        this.pPSui = pPSui;
    }

    public Double getM3PAvi() {
        return m3PAvi;
    }

    public void setM3PAvi(Double m3PAvi) {
        this.m3PAvi = m3PAvi;
    }

    public Double getTknPAvi() {
        return tknPAvi;
    }

    public void setTknPAvi(Double tknPAvi) {
        this.tknPAvi = tknPAvi;
    }

    public Double getTanPAvi() {
        return tanPAvi;
    }

    public void setTanPAvi(Double tanPAvi) {
        this.tanPAvi = tanPAvi;
    }

    public Double getDmPAvi() {
        return dmPAvi;
    }

    public void setDmPAvi(Double dmPAvi) {
        this.dmPAvi = dmPAvi;
    }

    public Double getVsPAvi() {
        return vsPAvi;
    }

    public void setVsPAvi(Double vsPAvi) {
        this.vsPAvi = vsPAvi;
    }

    public Double getKPAvi() {
        return kPAvi;
    }

    public void setKPAvi(Double kPAvi) {
        this.kPAvi = kPAvi;
    }

    public Double getPPAvi() {
        return pPAvi;
    }

    public void setPPAvi(Double pPAvi) {
        this.pPAvi = pPAvi;
    }

    public Double getM3PAlt() {
        return m3PAlt;
    }

    public void setM3PAlt(Double m3PAlt) {
        this.m3PAlt = m3PAlt;
    }

    public Double getTknPAlt() {
        return tknPAlt;
    }

    public void setTknPAlt(Double tknPAlt) {
        this.tknPAlt = tknPAlt;
    }

    public Double getTanPAlt() {
        return tanPAlt;
    }

    public void setTanPAlt(Double tanPAlt) {
        this.tanPAlt = tanPAlt;
    }

    public Double getDmPAlt() {
        return dmPAlt;
    }

    public void setDmPAlt(Double dmPAlt) {
        this.dmPAlt = dmPAlt;
    }

    public Double getVsPAlt() {
        return vsPAlt;
    }

    public void setVsPAlt(Double vsPAlt) {
        this.vsPAlt = vsPAlt;
    }

    public Double getKPAlt() {
        return kPAlt;
    }

    public void setKPAlt(Double kPAlt) {
        this.kPAlt = kPAlt;
    }

    public Double getPPAlt() {
        return pPAlt;
    }

    public void setPPAlt(Double pPAlt) {
        this.pPAlt = pPAlt;
    }

    public Double getM3PBio() {
        return m3PBio;
    }

    public void setM3PBio(Double m3PBio) {
        this.m3PBio = m3PBio;
    }

    public Double getTknPBio() {
        return tknPBio;
    }

    public void setTknPBio(Double tknPBio) {
        this.tknPBio = tknPBio;
    }

    public Double getTanPBio() {
        return tanPBio;
    }

    public void setTanPBio(Double tanPBio) {
        this.tanPBio = tanPBio;
    }

    public Double getDmPBio() {
        return dmPBio;
    }

    public void setDmPBio(Double dmPBio) {
        this.dmPBio = dmPBio;
    }

    public Double getVsPBio() {
        return vsPBio;
    }

    public void setVsPBio(Double vsPBio) {
        this.vsPBio = vsPBio;
    }

    public Double getKPBio() {
        return kPBio;
    }

    public void setKPBio(Double kPBio) {
        this.kPBio = kPBio;
    }

    public Double getPPBio() {
        return pPBio;
    }

    public void setPPBio(Double pPBio) {
        this.pPBio = pPBio;
    }

    public ScenarioI getScenarioI() {
        return scenarioI;
    }

    public void setScenarioI(ScenarioI scenarioI) {
        this.scenarioI = scenarioI;
    }

    public AlternativeS getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(AlternativeS idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idScenario != null ? idScenario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RisultatoConfronto)) {
            return false;
        }
        RisultatoConfronto other = (RisultatoConfronto) object;
        if ((this.idScenario == null && other.idScenario != null) || (this.idScenario != null && !this.idScenario.equals(other.idScenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.RisultatoConfronto[ idScenario=" + idScenario + " ]";
    }
    
}
