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
@Table(name = "caratteristiche_chmiche", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CaratteristicheChmiche.findAll", query = "SELECT c FROM CaratteristicheChmiche c"),
    @NamedQuery(name = "CaratteristicheChmiche.findByIdScenario", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.idScenario = :idScenario"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBovU = :m3LBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBovU = :tknLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBovU = :tanLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBovU = :dmLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBovU = :vsLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBovU = :pLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBovU = :kLBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBovU = :m3PBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBovU = :tknPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBovU = :tanPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBovU = :dmPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBovU = :vsPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBovU = :pPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBovU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBovU = :kPBovU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LSuiU = :m3LSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLSuiU = :tknLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLSuiU = :tanLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLSuiU = :dmLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLSuiU = :vsLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLSuiU = :pLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLSuiU = :kLSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PSuiU = :m3PSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPSuiU = :tknPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPSuiU = :tanPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPSuiU = :dmPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPSuiU = :vsPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPSuiU = :pPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPSuiU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPSuiU = :kPSuiU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAviU = :m3LAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAviU = :tknLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAviU = :tanLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAviU = :dmLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAviU = :vsLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAviU = :pLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAviU = :kLAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAviU = :m3PAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAviU = :tknPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAviU = :tanPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAviU = :dmPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAviU = :vsPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAviU = :pPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAviU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAviU = :kPAviU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAltU = :m3LAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAltU = :tknLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAltU = :tanLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAltU = :dmLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAltU = :vsLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAltU = :pLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAltU = :kLAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAltU = :m3PAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAltU = :tknPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAltU = :tanPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAltU = :dmPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAltU = :vsPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAltU = :pPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAltU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAltU = :kPAltU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBioU = :m3LBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBioU = :tknLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBioU = :tanLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBioU = :dmLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBioU = :vsLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBioU = :pLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBioU = :kLBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBioU = :m3PBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBioU = :tknPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBioU = :tanPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBioU = :dmPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBioU = :vsPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBioU = :pPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBioU", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBioU = :kPBioU"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBovS = :m3LBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBovS = :tknLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBovS = :tanLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBovS = :dmLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBovS = :vsLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBovS = :pLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBovS = :kLBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBovS = :m3PBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBovS = :tknPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBovS = :tanPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBovS = :dmPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBovS = :vsPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBovS = :pPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBovS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBovS = :kPBovS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LSuiS = :m3LSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLSuiS = :tknLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLSuiS = :tanLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLSuiS = :dmLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLSuiS = :vsLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLSuiS = :pLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLSuiS = :kLSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PSuiS = :m3PSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPSuiS = :tknPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPSuiS = :tanPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPSuiS = :dmPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPSuiS = :vsPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPSuiS = :pPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPSuiS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPSuiS = :kPSuiS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAviS = :m3LAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAviS = :tknLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAviS = :tanLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAviS = :dmLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAviS = :vsLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAviS = :pLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAviS = :kLAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAviS = :m3PAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAviS = :tknPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAviS = :tanPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAviS = :dmPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAviS = :vsPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAviS = :pPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAviS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAviS = :kPAviS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAltS = :m3LAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAltS = :tknLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAltS = :tanLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAltS = :dmLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAltS = :vsLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAltS = :pLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAltS = :kLAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAltS = :m3PAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAltS = :tknPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAltS = :tanPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAltS = :dmPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAltS = :vsPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAltS = :pPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAltS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAltS = :kPAltS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBioS = :m3LBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBioS = :tknLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBioS = :tanLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBioS = :dmLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBioS = :vsLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBioS = :pLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBioS = :kLBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBioS = :m3PBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBioS = :tknPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBioS = :tanPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBioS = :dmPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBioS = :vsPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBioS = :pPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBioS", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBioS = :kPBioS"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBovM = :m3LBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBovM = :tknLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBovM = :tanLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBovM = :dmLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBovM = :vsLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBovM = :pLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBovM = :kLBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBovM = :m3PBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBovM = :tknPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBovM = :tanPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBovM = :dmPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBovM = :vsPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBovM = :pPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBovM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBovM = :kPBovM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LSuiM = :m3LSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLSuiM = :tknLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLSuiM = :tanLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLSuiM = :dmLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLSuiM = :vsLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLSuiM = :pLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLSuiM = :kLSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PSuiM = :m3PSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPSuiM = :tknPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPSuiM = :tanPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPSuiM = :dmPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPSuiM = :vsPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPSuiM = :pPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPSuiM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPSuiM = :kPSuiM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAviM = :m3LAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAviM = :tknLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAviM = :tanLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAviM = :dmLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAviM = :vsLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAviM = :pLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAviM = :kLAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAviM = :m3PAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAviM = :tknPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAviM = :tanPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAviM = :dmPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAviM = :vsPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAviM = :pPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAviM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAviM = :kPAviM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LAltM = :m3LAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLAltM = :tknLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLAltM = :tanLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLAltM = :dmLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLAltM = :vsLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLAltM = :pLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLAltM = :kLAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PAltM = :m3PAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPAltM = :tknPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPAltM = :tanPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPAltM = :dmPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPAltM = :vsPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPAltM = :pPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPAltM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPAltM = :kPAltM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3LBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3LBioM = :m3LBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknLBioM = :tknLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanLBioM = :tanLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmLBioM = :dmLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsLBioM = :vsLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pLBioM = :pLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKLBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kLBioM = :kLBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByM3PBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.m3PBioM = :m3PBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTknPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tknPBioM = :tknPBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByTanPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.tanPBioM = :tanPBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByDmPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.dmPBioM = :dmPBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByVsPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.vsPBioM = :vsPBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByPPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.pPBioM = :pPBioM"),
    @NamedQuery(name = "CaratteristicheChmiche.findByKPBioM", query = "SELECT c FROM CaratteristicheChmiche c WHERE c.kPBioM = :kPBioM")})
public class CaratteristicheChmiche implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_scenario", nullable = false)
    private Long idScenario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "m3_l_bov_u", precision = 17, scale = 17)
    private Double m3LBovU;
    @Column(name = "tkn_l_bov_u", precision = 17, scale = 17)
    private Double tknLBovU;
    @Column(name = "tan_l_bov_u", precision = 17, scale = 17)
    private Double tanLBovU;
    @Column(name = "dm_l_bov_u", precision = 17, scale = 17)
    private Double dmLBovU;
    @Column(name = "vs_l_bov_u", precision = 17, scale = 17)
    private Double vsLBovU;
    @Column(name = "p_l_bov_u", precision = 17, scale = 17)
    private Double pLBovU;
    @Column(name = "k_l_bov_u", precision = 17, scale = 17)
    private Double kLBovU;
    @Column(name = "m3_p_bov_u", precision = 17, scale = 17)
    private Double m3PBovU;
    @Column(name = "tkn_p_bov_u", precision = 17, scale = 17)
    private Double tknPBovU;
    @Column(name = "tan_p_bov_u", precision = 17, scale = 17)
    private Double tanPBovU;
    @Column(name = "dm_p_bov_u", precision = 17, scale = 17)
    private Double dmPBovU;
    @Column(name = "vs_p_bov_u", precision = 17, scale = 17)
    private Double vsPBovU;
    @Column(name = "p_p_bov_u", precision = 17, scale = 17)
    private Double pPBovU;
    @Column(name = "k_p_bov_u", precision = 17, scale = 17)
    private Double kPBovU;
    @Column(name = "m3_l_sui_u", precision = 17, scale = 17)
    private Double m3LSuiU;
    @Column(name = "tkn_l_sui_u", precision = 17, scale = 17)
    private Double tknLSuiU;
    @Column(name = "tan_l_sui_u", precision = 17, scale = 17)
    private Double tanLSuiU;
    @Column(name = "dm_l_sui_u", precision = 17, scale = 17)
    private Double dmLSuiU;
    @Column(name = "vs_l_sui_u", precision = 17, scale = 17)
    private Double vsLSuiU;
    @Column(name = "p_l_sui_u", precision = 17, scale = 17)
    private Double pLSuiU;
    @Column(name = "k_l_sui_u", precision = 17, scale = 17)
    private Double kLSuiU;
    @Column(name = "m3_p_sui_u", precision = 17, scale = 17)
    private Double m3PSuiU;
    @Column(name = "tkn_p_sui_u", precision = 17, scale = 17)
    private Double tknPSuiU;
    @Column(name = "tan_p_sui_u", precision = 17, scale = 17)
    private Double tanPSuiU;
    @Column(name = "dm_p_sui_u", precision = 17, scale = 17)
    private Double dmPSuiU;
    @Column(name = "vs_p_sui_u", precision = 17, scale = 17)
    private Double vsPSuiU;
    @Column(name = "p_p_sui_u", precision = 17, scale = 17)
    private Double pPSuiU;
    @Column(name = "k_p_sui_u", precision = 17, scale = 17)
    private Double kPSuiU;
    @Column(name = "m3_l_avi_u", precision = 17, scale = 17)
    private Double m3LAviU;
    @Column(name = "tkn_l_avi_u", precision = 17, scale = 17)
    private Double tknLAviU;
    @Column(name = "tan_l_avi_u", precision = 17, scale = 17)
    private Double tanLAviU;
    @Column(name = "dm_l_avi_u", precision = 17, scale = 17)
    private Double dmLAviU;
    @Column(name = "vs_l_avi_u", precision = 17, scale = 17)
    private Double vsLAviU;
    @Column(name = "p_l_avi_u", precision = 17, scale = 17)
    private Double pLAviU;
    @Column(name = "k_l_avi_u", precision = 17, scale = 17)
    private Double kLAviU;
    @Column(name = "m3_p_avi_u", precision = 17, scale = 17)
    private Double m3PAviU;
    @Column(name = "tkn_p_avi_u", precision = 17, scale = 17)
    private Double tknPAviU;
    @Column(name = "tan_p_avi_u", precision = 17, scale = 17)
    private Double tanPAviU;
    @Column(name = "dm_p_avi_u", precision = 17, scale = 17)
    private Double dmPAviU;
    @Column(name = "vs_p_avi_u", precision = 17, scale = 17)
    private Double vsPAviU;
    @Column(name = "p_p_avi_u", precision = 17, scale = 17)
    private Double pPAviU;
    @Column(name = "k_p_avi_u", precision = 17, scale = 17)
    private Double kPAviU;
    @Column(name = "m3_l_alt_u", precision = 17, scale = 17)
    private Double m3LAltU;
    @Column(name = "tkn_l_alt_u", precision = 17, scale = 17)
    private Double tknLAltU;
    @Column(name = "tan_l_alt_u", precision = 17, scale = 17)
    private Double tanLAltU;
    @Column(name = "dm_l_alt_u", precision = 17, scale = 17)
    private Double dmLAltU;
    @Column(name = "vs_l_alt_u", precision = 17, scale = 17)
    private Double vsLAltU;
    @Column(name = "p_l_alt_u", precision = 17, scale = 17)
    private Double pLAltU;
    @Column(name = "k_l_alt_u", precision = 17, scale = 17)
    private Double kLAltU;
    @Column(name = "m3_p_alt_u", precision = 17, scale = 17)
    private Double m3PAltU;
    @Column(name = "tkn_p_alt_u", precision = 17, scale = 17)
    private Double tknPAltU;
    @Column(name = "tan_p_alt_u", precision = 17, scale = 17)
    private Double tanPAltU;
    @Column(name = "dm_p_alt_u", precision = 17, scale = 17)
    private Double dmPAltU;
    @Column(name = "vs_p_alt_u", precision = 17, scale = 17)
    private Double vsPAltU;
    @Column(name = "p_p_alt_u", precision = 17, scale = 17)
    private Double pPAltU;
    @Column(name = "k_p_alt_u", precision = 17, scale = 17)
    private Double kPAltU;
    @Column(name = "m3_l_bio_u", precision = 17, scale = 17)
    private Double m3LBioU;
    @Column(name = "tkn_l_bio_u", precision = 17, scale = 17)
    private Double tknLBioU;
    @Column(name = "tan_l_bio_u", precision = 17, scale = 17)
    private Double tanLBioU;
    @Column(name = "dm_l_bio_u", precision = 17, scale = 17)
    private Double dmLBioU;
    @Column(name = "vs_l_bio_u", precision = 17, scale = 17)
    private Double vsLBioU;
    @Column(name = "p_l_bio_u", precision = 17, scale = 17)
    private Double pLBioU;
    @Column(name = "k_l_bio_u", precision = 17, scale = 17)
    private Double kLBioU;
    @Column(name = "m3_p_bio_u", precision = 17, scale = 17)
    private Double m3PBioU;
    @Column(name = "tkn_p_bio_u", precision = 17, scale = 17)
    private Double tknPBioU;
    @Column(name = "tan_p_bio_u", precision = 17, scale = 17)
    private Double tanPBioU;
    @Column(name = "dm_p_bio_u", precision = 17, scale = 17)
    private Double dmPBioU;
    @Column(name = "vs_p_bio_u", precision = 17, scale = 17)
    private Double vsPBioU;
    @Column(name = "p_p_bio_u", precision = 17, scale = 17)
    private Double pPBioU;
    @Column(name = "k_p_bio_u", precision = 17, scale = 17)
    private Double kPBioU;
    @Column(name = "m3_l_bov_s", precision = 17, scale = 17)
    private Double m3LBovS;
    @Column(name = "tkn_l_bov_s", precision = 17, scale = 17)
    private Double tknLBovS;
    @Column(name = "tan_l_bov_s", precision = 17, scale = 17)
    private Double tanLBovS;
    @Column(name = "dm_l_bov_s", precision = 17, scale = 17)
    private Double dmLBovS;
    @Column(name = "vs_l_bov_s", precision = 17, scale = 17)
    private Double vsLBovS;
    @Column(name = "p_l_bov_s", precision = 17, scale = 17)
    private Double pLBovS;
    @Column(name = "k_l_bov_s", precision = 17, scale = 17)
    private Double kLBovS;
    @Column(name = "m3_p_bov_s", precision = 17, scale = 17)
    private Double m3PBovS;
    @Column(name = "tkn_p_bov_s", precision = 17, scale = 17)
    private Double tknPBovS;
    @Column(name = "tan_p_bov_s", precision = 17, scale = 17)
    private Double tanPBovS;
    @Column(name = "dm_p_bov_s", precision = 17, scale = 17)
    private Double dmPBovS;
    @Column(name = "vs_p_bov_s", precision = 17, scale = 17)
    private Double vsPBovS;
    @Column(name = "p_p_bov_s", precision = 17, scale = 17)
    private Double pPBovS;
    @Column(name = "k_p_bov_s", precision = 17, scale = 17)
    private Double kPBovS;
    @Column(name = "m3_l_sui_s", precision = 17, scale = 17)
    private Double m3LSuiS;
    @Column(name = "tkn_l_sui_s", precision = 17, scale = 17)
    private Double tknLSuiS;
    @Column(name = "tan_l_sui_s", precision = 17, scale = 17)
    private Double tanLSuiS;
    @Column(name = "dm_l_sui_s", precision = 17, scale = 17)
    private Double dmLSuiS;
    @Column(name = "vs_l_sui_s", precision = 17, scale = 17)
    private Double vsLSuiS;
    @Column(name = "p_l_sui_s", precision = 17, scale = 17)
    private Double pLSuiS;
    @Column(name = "k_l_sui_s", precision = 17, scale = 17)
    private Double kLSuiS;
    @Column(name = "m3_p_sui_s", precision = 17, scale = 17)
    private Double m3PSuiS;
    @Column(name = "tkn_p_sui_s", precision = 17, scale = 17)
    private Double tknPSuiS;
    @Column(name = "tan_p_sui_s", precision = 17, scale = 17)
    private Double tanPSuiS;
    @Column(name = "dm_p_sui_s", precision = 17, scale = 17)
    private Double dmPSuiS;
    @Column(name = "vs_p_sui_s", precision = 17, scale = 17)
    private Double vsPSuiS;
    @Column(name = "p_p_sui_s", precision = 17, scale = 17)
    private Double pPSuiS;
    @Column(name = "k_p_sui_s", precision = 17, scale = 17)
    private Double kPSuiS;
    @Column(name = "m3_l_avi_s", precision = 17, scale = 17)
    private Double m3LAviS;
    @Column(name = "tkn_l_avi_s", precision = 17, scale = 17)
    private Double tknLAviS;
    @Column(name = "tan_l_avi_s", precision = 17, scale = 17)
    private Double tanLAviS;
    @Column(name = "dm_l_avi_s", precision = 17, scale = 17)
    private Double dmLAviS;
    @Column(name = "vs_l_avi_s", precision = 17, scale = 17)
    private Double vsLAviS;
    @Column(name = "p_l_avi_s", precision = 17, scale = 17)
    private Double pLAviS;
    @Column(name = "k_l_avi_s", precision = 17, scale = 17)
    private Double kLAviS;
    @Column(name = "m3_p_avi_s", precision = 17, scale = 17)
    private Double m3PAviS;
    @Column(name = "tkn_p_avi_s", precision = 17, scale = 17)
    private Double tknPAviS;
    @Column(name = "tan_p_avi_s", precision = 17, scale = 17)
    private Double tanPAviS;
    @Column(name = "dm_p_avi_s", precision = 17, scale = 17)
    private Double dmPAviS;
    @Column(name = "vs_p_avi_s", precision = 17, scale = 17)
    private Double vsPAviS;
    @Column(name = "p_p_avi_s", precision = 17, scale = 17)
    private Double pPAviS;
    @Column(name = "k_p_avi_s", precision = 17, scale = 17)
    private Double kPAviS;
    @Column(name = "m3_l_alt_s", precision = 17, scale = 17)
    private Double m3LAltS;
    @Column(name = "tkn_l_alt_s", precision = 17, scale = 17)
    private Double tknLAltS;
    @Column(name = "tan_l_alt_s", precision = 17, scale = 17)
    private Double tanLAltS;
    @Column(name = "dm_l_alt_s", precision = 17, scale = 17)
    private Double dmLAltS;
    @Column(name = "vs_l_alt_s", precision = 17, scale = 17)
    private Double vsLAltS;
    @Column(name = "p_l_alt_s", precision = 17, scale = 17)
    private Double pLAltS;
    @Column(name = "k_l_alt_s", precision = 17, scale = 17)
    private Double kLAltS;
    @Column(name = "m3_p_alt_s", precision = 17, scale = 17)
    private Double m3PAltS;
    @Column(name = "tkn_p_alt_s", precision = 17, scale = 17)
    private Double tknPAltS;
    @Column(name = "tan_p_alt_s", precision = 17, scale = 17)
    private Double tanPAltS;
    @Column(name = "dm_p_alt_s", precision = 17, scale = 17)
    private Double dmPAltS;
    @Column(name = "vs_p_alt_s", precision = 17, scale = 17)
    private Double vsPAltS;
    @Column(name = "p_p_alt_s", precision = 17, scale = 17)
    private Double pPAltS;
    @Column(name = "k_p_alt_s", precision = 17, scale = 17)
    private Double kPAltS;
    @Column(name = "m3_l_bio_s", precision = 17, scale = 17)
    private Double m3LBioS;
    @Column(name = "tkn_l_bio_s", precision = 17, scale = 17)
    private Double tknLBioS;
    @Column(name = "tan_l_bio_s", precision = 17, scale = 17)
    private Double tanLBioS;
    @Column(name = "dm_l_bio_s", precision = 17, scale = 17)
    private Double dmLBioS;
    @Column(name = "vs_l_bio_s", precision = 17, scale = 17)
    private Double vsLBioS;
    @Column(name = "p_l_bio_s", precision = 17, scale = 17)
    private Double pLBioS;
    @Column(name = "k_l_bio_s", precision = 17, scale = 17)
    private Double kLBioS;
    @Column(name = "m3_p_bio_s", precision = 17, scale = 17)
    private Double m3PBioS;
    @Column(name = "tkn_p_bio_s", precision = 17, scale = 17)
    private Double tknPBioS;
    @Column(name = "tan_p_bio_s", precision = 17, scale = 17)
    private Double tanPBioS;
    @Column(name = "dm_p_bio_s", precision = 17, scale = 17)
    private Double dmPBioS;
    @Column(name = "vs_p_bio_s", precision = 17, scale = 17)
    private Double vsPBioS;
    @Column(name = "p_p_bio_s", precision = 17, scale = 17)
    private Double pPBioS;
    @Column(name = "k_p_bio_s", precision = 17, scale = 17)
    private Double kPBioS;
    @Column(name = "m3_l_bov_m")
    private Boolean m3LBovM;
    @Column(name = "tkn_l_bov_m")
    private Boolean tknLBovM;
    @Column(name = "tan_l_bov_m")
    private Boolean tanLBovM;
    @Column(name = "dm_l_bov_m")
    private Boolean dmLBovM;
    @Column(name = "vs_l_bov_m")
    private Boolean vsLBovM;
    @Column(name = "p_l_bov_m")
    private Boolean pLBovM;
    @Column(name = "k_l_bov_m")
    private Boolean kLBovM;
    @Column(name = "m3_p_bov_m")
    private Boolean m3PBovM;
    @Column(name = "tkn_p_bov_m")
    private Boolean tknPBovM;
    @Column(name = "tan_p_bov_m")
    private Boolean tanPBovM;
    @Column(name = "dm_p_bov_m")
    private Boolean dmPBovM;
    @Column(name = "vs_p_bov_m")
    private Boolean vsPBovM;
    @Column(name = "p_p_bov_m")
    private Boolean pPBovM;
    @Column(name = "k_p_bov_m")
    private Boolean kPBovM;
    @Column(name = "m3_l_sui_m")
    private Boolean m3LSuiM;
    @Column(name = "tkn_l_sui_m")
    private Boolean tknLSuiM;
    @Column(name = "tan_l_sui_m")
    private Boolean tanLSuiM;
    @Column(name = "dm_l_sui_m")
    private Boolean dmLSuiM;
    @Column(name = "vs_l_sui_m")
    private Boolean vsLSuiM;
    @Column(name = "p_l_sui_m")
    private Boolean pLSuiM;
    @Column(name = "k_l_sui_m")
    private Boolean kLSuiM;
    @Column(name = "m3_p_sui_m")
    private Boolean m3PSuiM;
    @Column(name = "tkn_p_sui_m")
    private Boolean tknPSuiM;
    @Column(name = "tan_p_sui_m")
    private Boolean tanPSuiM;
    @Column(name = "dm_p_sui_m")
    private Boolean dmPSuiM;
    @Column(name = "vs_p_sui_m")
    private Boolean vsPSuiM;
    @Column(name = "p_p_sui_m")
    private Boolean pPSuiM;
    @Column(name = "k_p_sui_m")
    private Boolean kPSuiM;
    @Column(name = "m3_l_avi_m")
    private Boolean m3LAviM;
    @Column(name = "tkn_l_avi_m")
    private Boolean tknLAviM;
    @Column(name = "tan_l_avi_m")
    private Boolean tanLAviM;
    @Column(name = "dm_l_avi_m")
    private Boolean dmLAviM;
    @Column(name = "vs_l_avi_m")
    private Boolean vsLAviM;
    @Column(name = "p_l_avi_m")
    private Boolean pLAviM;
    @Column(name = "k_l_avi_m")
    private Boolean kLAviM;
    @Column(name = "m3_p_avi_m")
    private Boolean m3PAviM;
    @Column(name = "tkn_p_avi_m")
    private Boolean tknPAviM;
    @Column(name = "tan_p_avi_m")
    private Boolean tanPAviM;
    @Column(name = "dm_p_avi_m")
    private Boolean dmPAviM;
    @Column(name = "vs_p_avi_m")
    private Boolean vsPAviM;
    @Column(name = "p_p_avi_m")
    private Boolean pPAviM;
    @Column(name = "k_p_avi_m")
    private Boolean kPAviM;
    @Column(name = "m3_l_alt_m")
    private Boolean m3LAltM;
    @Column(name = "tkn_l_alt_m")
    private Boolean tknLAltM;
    @Column(name = "tan_l_alt_m")
    private Boolean tanLAltM;
    @Column(name = "dm_l_alt_m")
    private Boolean dmLAltM;
    @Column(name = "vs_l_alt_m")
    private Boolean vsLAltM;
    @Column(name = "p_l_alt_m")
    private Boolean pLAltM;
    @Column(name = "k_l_alt_m")
    private Boolean kLAltM;
    @Column(name = "m3_p_alt_m")
    private Boolean m3PAltM;
    @Column(name = "tkn_p_alt_m")
    private Boolean tknPAltM;
    @Column(name = "tan_p_alt_m")
    private Boolean tanPAltM;
    @Column(name = "dm_p_alt_m")
    private Boolean dmPAltM;
    @Column(name = "vs_p_alt_m")
    private Boolean vsPAltM;
    @Column(name = "p_p_alt_m")
    private Boolean pPAltM;
    @Column(name = "k_p_alt_m")
    private Boolean kPAltM;
    @Column(name = "m3_l_bio_m")
    private Boolean m3LBioM;
    @Column(name = "tkn_l_bio_m")
    private Boolean tknLBioM;
    @Column(name = "tan_l_bio_m")
    private Boolean tanLBioM;
    @Column(name = "dm_l_bio_m")
    private Boolean dmLBioM;
    @Column(name = "vs_l_bio_m")
    private Boolean vsLBioM;
    @Column(name = "p_l_bio_m")
    private Boolean pLBioM;
    @Column(name = "k_l_bio_m")
    private Boolean kLBioM;
    @Column(name = "m3_p_bio_m")
    private Boolean m3PBioM;
    @Column(name = "tkn_p_bio_m")
    private Boolean tknPBioM;
    @Column(name = "tan_p_bio_m")
    private Boolean tanPBioM;
    @Column(name = "dm_p_bio_m")
    private Boolean dmPBioM;
    @Column(name = "vs_p_bio_m")
    private Boolean vsPBioM;
    @Column(name = "p_p_bio_m")
    private Boolean pPBioM;
    @Column(name = "k_p_bio_m")
    private Boolean kPBioM;
    @JoinColumn(name = "id_scenario", referencedColumnName = "idscenario", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ScenarioI scenarioI;

    public CaratteristicheChmiche() {
    }

    public CaratteristicheChmiche(Long idScenario) {
        this.idScenario = idScenario;
    }

    public Long getIdScenario() {
        return idScenario;
    }

    public void setIdScenario(Long idScenario) {
        this.idScenario = idScenario;
    }

    public Double getM3LBovU() {
        return m3LBovU;
    }

    public void setM3LBovU(Double m3LBovU) {
        this.m3LBovU = m3LBovU;
    }

    public Double getTknLBovU() {
        return tknLBovU;
    }

    public void setTknLBovU(Double tknLBovU) {
        this.tknLBovU = tknLBovU;
    }

    public Double getTanLBovU() {
        return tanLBovU;
    }

    public void setTanLBovU(Double tanLBovU) {
        this.tanLBovU = tanLBovU;
    }

    public Double getDmLBovU() {
        return dmLBovU;
    }

    public void setDmLBovU(Double dmLBovU) {
        this.dmLBovU = dmLBovU;
    }

    public Double getVsLBovU() {
        return vsLBovU;
    }

    public void setVsLBovU(Double vsLBovU) {
        this.vsLBovU = vsLBovU;
    }

    public Double getPLBovU() {
        return pLBovU;
    }

    public void setPLBovU(Double pLBovU) {
        this.pLBovU = pLBovU;
    }

    public Double getKLBovU() {
        return kLBovU;
    }

    public void setKLBovU(Double kLBovU) {
        this.kLBovU = kLBovU;
    }

    public Double getM3PBovU() {
        return m3PBovU;
    }

    public void setM3PBovU(Double m3PBovU) {
        this.m3PBovU = m3PBovU;
    }

    public Double getTknPBovU() {
        return tknPBovU;
    }

    public void setTknPBovU(Double tknPBovU) {
        this.tknPBovU = tknPBovU;
    }

    public Double getTanPBovU() {
        return tanPBovU;
    }

    public void setTanPBovU(Double tanPBovU) {
        this.tanPBovU = tanPBovU;
    }

    public Double getDmPBovU() {
        return dmPBovU;
    }

    public void setDmPBovU(Double dmPBovU) {
        this.dmPBovU = dmPBovU;
    }

    public Double getVsPBovU() {
        return vsPBovU;
    }

    public void setVsPBovU(Double vsPBovU) {
        this.vsPBovU = vsPBovU;
    }

    public Double getPPBovU() {
        return pPBovU;
    }

    public void setPPBovU(Double pPBovU) {
        this.pPBovU = pPBovU;
    }

    public Double getKPBovU() {
        return kPBovU;
    }

    public void setKPBovU(Double kPBovU) {
        this.kPBovU = kPBovU;
    }

    public Double getM3LSuiU() {
        return m3LSuiU;
    }

    public void setM3LSuiU(Double m3LSuiU) {
        this.m3LSuiU = m3LSuiU;
    }

    public Double getTknLSuiU() {
        return tknLSuiU;
    }

    public void setTknLSuiU(Double tknLSuiU) {
        this.tknLSuiU = tknLSuiU;
    }

    public Double getTanLSuiU() {
        return tanLSuiU;
    }

    public void setTanLSuiU(Double tanLSuiU) {
        this.tanLSuiU = tanLSuiU;
    }

    public Double getDmLSuiU() {
        return dmLSuiU;
    }

    public void setDmLSuiU(Double dmLSuiU) {
        this.dmLSuiU = dmLSuiU;
    }

    public Double getVsLSuiU() {
        return vsLSuiU;
    }

    public void setVsLSuiU(Double vsLSuiU) {
        this.vsLSuiU = vsLSuiU;
    }

    public Double getPLSuiU() {
        return pLSuiU;
    }

    public void setPLSuiU(Double pLSuiU) {
        this.pLSuiU = pLSuiU;
    }

    public Double getKLSuiU() {
        return kLSuiU;
    }

    public void setKLSuiU(Double kLSuiU) {
        this.kLSuiU = kLSuiU;
    }

    public Double getM3PSuiU() {
        return m3PSuiU;
    }

    public void setM3PSuiU(Double m3PSuiU) {
        this.m3PSuiU = m3PSuiU;
    }

    public Double getTknPSuiU() {
        return tknPSuiU;
    }

    public void setTknPSuiU(Double tknPSuiU) {
        this.tknPSuiU = tknPSuiU;
    }

    public Double getTanPSuiU() {
        return tanPSuiU;
    }

    public void setTanPSuiU(Double tanPSuiU) {
        this.tanPSuiU = tanPSuiU;
    }

    public Double getDmPSuiU() {
        return dmPSuiU;
    }

    public void setDmPSuiU(Double dmPSuiU) {
        this.dmPSuiU = dmPSuiU;
    }

    public Double getVsPSuiU() {
        return vsPSuiU;
    }

    public void setVsPSuiU(Double vsPSuiU) {
        this.vsPSuiU = vsPSuiU;
    }

    public Double getPPSuiU() {
        return pPSuiU;
    }

    public void setPPSuiU(Double pPSuiU) {
        this.pPSuiU = pPSuiU;
    }

    public Double getKPSuiU() {
        return kPSuiU;
    }

    public void setKPSuiU(Double kPSuiU) {
        this.kPSuiU = kPSuiU;
    }

    public Double getM3LAviU() {
        return m3LAviU;
    }

    public void setM3LAviU(Double m3LAviU) {
        this.m3LAviU = m3LAviU;
    }

    public Double getTknLAviU() {
        return tknLAviU;
    }

    public void setTknLAviU(Double tknLAviU) {
        this.tknLAviU = tknLAviU;
    }

    public Double getTanLAviU() {
        return tanLAviU;
    }

    public void setTanLAviU(Double tanLAviU) {
        this.tanLAviU = tanLAviU;
    }

    public Double getDmLAviU() {
        return dmLAviU;
    }

    public void setDmLAviU(Double dmLAviU) {
        this.dmLAviU = dmLAviU;
    }

    public Double getVsLAviU() {
        return vsLAviU;
    }

    public void setVsLAviU(Double vsLAviU) {
        this.vsLAviU = vsLAviU;
    }

    public Double getPLAviU() {
        return pLAviU;
    }

    public void setPLAviU(Double pLAviU) {
        this.pLAviU = pLAviU;
    }

    public Double getKLAviU() {
        return kLAviU;
    }

    public void setKLAviU(Double kLAviU) {
        this.kLAviU = kLAviU;
    }

    public Double getM3PAviU() {
        return m3PAviU;
    }

    public void setM3PAviU(Double m3PAviU) {
        this.m3PAviU = m3PAviU;
    }

    public Double getTknPAviU() {
        return tknPAviU;
    }

    public void setTknPAviU(Double tknPAviU) {
        this.tknPAviU = tknPAviU;
    }

    public Double getTanPAviU() {
        return tanPAviU;
    }

    public void setTanPAviU(Double tanPAviU) {
        this.tanPAviU = tanPAviU;
    }

    public Double getDmPAviU() {
        return dmPAviU;
    }

    public void setDmPAviU(Double dmPAviU) {
        this.dmPAviU = dmPAviU;
    }

    public Double getVsPAviU() {
        return vsPAviU;
    }

    public void setVsPAviU(Double vsPAviU) {
        this.vsPAviU = vsPAviU;
    }

    public Double getPPAviU() {
        return pPAviU;
    }

    public void setPPAviU(Double pPAviU) {
        this.pPAviU = pPAviU;
    }

    public Double getKPAviU() {
        return kPAviU;
    }

    public void setKPAviU(Double kPAviU) {
        this.kPAviU = kPAviU;
    }

    public Double getM3LAltU() {
        return m3LAltU;
    }

    public void setM3LAltU(Double m3LAltU) {
        this.m3LAltU = m3LAltU;
    }

    public Double getTknLAltU() {
        return tknLAltU;
    }

    public void setTknLAltU(Double tknLAltU) {
        this.tknLAltU = tknLAltU;
    }

    public Double getTanLAltU() {
        return tanLAltU;
    }

    public void setTanLAltU(Double tanLAltU) {
        this.tanLAltU = tanLAltU;
    }

    public Double getDmLAltU() {
        return dmLAltU;
    }

    public void setDmLAltU(Double dmLAltU) {
        this.dmLAltU = dmLAltU;
    }

    public Double getVsLAltU() {
        return vsLAltU;
    }

    public void setVsLAltU(Double vsLAltU) {
        this.vsLAltU = vsLAltU;
    }

    public Double getPLAltU() {
        return pLAltU;
    }

    public void setPLAltU(Double pLAltU) {
        this.pLAltU = pLAltU;
    }

    public Double getKLAltU() {
        return kLAltU;
    }

    public void setKLAltU(Double kLAltU) {
        this.kLAltU = kLAltU;
    }

    public Double getM3PAltU() {
        return m3PAltU;
    }

    public void setM3PAltU(Double m3PAltU) {
        this.m3PAltU = m3PAltU;
    }

    public Double getTknPAltU() {
        return tknPAltU;
    }

    public void setTknPAltU(Double tknPAltU) {
        this.tknPAltU = tknPAltU;
    }

    public Double getTanPAltU() {
        return tanPAltU;
    }

    public void setTanPAltU(Double tanPAltU) {
        this.tanPAltU = tanPAltU;
    }

    public Double getDmPAltU() {
        return dmPAltU;
    }

    public void setDmPAltU(Double dmPAltU) {
        this.dmPAltU = dmPAltU;
    }

    public Double getVsPAltU() {
        return vsPAltU;
    }

    public void setVsPAltU(Double vsPAltU) {
        this.vsPAltU = vsPAltU;
    }

    public Double getPPAltU() {
        return pPAltU;
    }

    public void setPPAltU(Double pPAltU) {
        this.pPAltU = pPAltU;
    }

    public Double getKPAltU() {
        return kPAltU;
    }

    public void setKPAltU(Double kPAltU) {
        this.kPAltU = kPAltU;
    }

    public Double getM3LBioU() {
        return m3LBioU;
    }

    public void setM3LBioU(Double m3LBioU) {
        this.m3LBioU = m3LBioU;
    }

    public Double getTknLBioU() {
        return tknLBioU;
    }

    public void setTknLBioU(Double tknLBioU) {
        this.tknLBioU = tknLBioU;
    }

    public Double getTanLBioU() {
        return tanLBioU;
    }

    public void setTanLBioU(Double tanLBioU) {
        this.tanLBioU = tanLBioU;
    }

    public Double getDmLBioU() {
        return dmLBioU;
    }

    public void setDmLBioU(Double dmLBioU) {
        this.dmLBioU = dmLBioU;
    }

    public Double getVsLBioU() {
        return vsLBioU;
    }

    public void setVsLBioU(Double vsLBioU) {
        this.vsLBioU = vsLBioU;
    }

    public Double getPLBioU() {
        return pLBioU;
    }

    public void setPLBioU(Double pLBioU) {
        this.pLBioU = pLBioU;
    }

    public Double getKLBioU() {
        return kLBioU;
    }

    public void setKLBioU(Double kLBioU) {
        this.kLBioU = kLBioU;
    }

    public Double getM3PBioU() {
        return m3PBioU;
    }

    public void setM3PBioU(Double m3PBioU) {
        this.m3PBioU = m3PBioU;
    }

    public Double getTknPBioU() {
        return tknPBioU;
    }

    public void setTknPBioU(Double tknPBioU) {
        this.tknPBioU = tknPBioU;
    }

    public Double getTanPBioU() {
        return tanPBioU;
    }

    public void setTanPBioU(Double tanPBioU) {
        this.tanPBioU = tanPBioU;
    }

    public Double getDmPBioU() {
        return dmPBioU;
    }

    public void setDmPBioU(Double dmPBioU) {
        this.dmPBioU = dmPBioU;
    }

    public Double getVsPBioU() {
        return vsPBioU;
    }

    public void setVsPBioU(Double vsPBioU) {
        this.vsPBioU = vsPBioU;
    }

    public Double getPPBioU() {
        return pPBioU;
    }

    public void setPPBioU(Double pPBioU) {
        this.pPBioU = pPBioU;
    }

    public Double getKPBioU() {
        return kPBioU;
    }

    public void setKPBioU(Double kPBioU) {
        this.kPBioU = kPBioU;
    }

    public Double getM3LBovS() {
        return m3LBovS;
    }

    public void setM3LBovS(Double m3LBovS) {
        this.m3LBovS = m3LBovS;
    }

    public Double getTknLBovS() {
        return tknLBovS;
    }

    public void setTknLBovS(Double tknLBovS) {
        this.tknLBovS = tknLBovS;
    }

    public Double getTanLBovS() {
        return tanLBovS;
    }

    public void setTanLBovS(Double tanLBovS) {
        this.tanLBovS = tanLBovS;
    }

    public Double getDmLBovS() {
        return dmLBovS;
    }

    public void setDmLBovS(Double dmLBovS) {
        this.dmLBovS = dmLBovS;
    }

    public Double getVsLBovS() {
        return vsLBovS;
    }

    public void setVsLBovS(Double vsLBovS) {
        this.vsLBovS = vsLBovS;
    }

    public Double getPLBovS() {
        return pLBovS;
    }

    public void setPLBovS(Double pLBovS) {
        this.pLBovS = pLBovS;
    }

    public Double getKLBovS() {
        return kLBovS;
    }

    public void setKLBovS(Double kLBovS) {
        this.kLBovS = kLBovS;
    }

    public Double getM3PBovS() {
        return m3PBovS;
    }

    public void setM3PBovS(Double m3PBovS) {
        this.m3PBovS = m3PBovS;
    }

    public Double getTknPBovS() {
        return tknPBovS;
    }

    public void setTknPBovS(Double tknPBovS) {
        this.tknPBovS = tknPBovS;
    }

    public Double getTanPBovS() {
        return tanPBovS;
    }

    public void setTanPBovS(Double tanPBovS) {
        this.tanPBovS = tanPBovS;
    }

    public Double getDmPBovS() {
        return dmPBovS;
    }

    public void setDmPBovS(Double dmPBovS) {
        this.dmPBovS = dmPBovS;
    }

    public Double getVsPBovS() {
        return vsPBovS;
    }

    public void setVsPBovS(Double vsPBovS) {
        this.vsPBovS = vsPBovS;
    }

    public Double getPPBovS() {
        return pPBovS;
    }

    public void setPPBovS(Double pPBovS) {
        this.pPBovS = pPBovS;
    }

    public Double getKPBovS() {
        return kPBovS;
    }

    public void setKPBovS(Double kPBovS) {
        this.kPBovS = kPBovS;
    }

    public Double getM3LSuiS() {
        return m3LSuiS;
    }

    public void setM3LSuiS(Double m3LSuiS) {
        this.m3LSuiS = m3LSuiS;
    }

    public Double getTknLSuiS() {
        return tknLSuiS;
    }

    public void setTknLSuiS(Double tknLSuiS) {
        this.tknLSuiS = tknLSuiS;
    }

    public Double getTanLSuiS() {
        return tanLSuiS;
    }

    public void setTanLSuiS(Double tanLSuiS) {
        this.tanLSuiS = tanLSuiS;
    }

    public Double getDmLSuiS() {
        return dmLSuiS;
    }

    public void setDmLSuiS(Double dmLSuiS) {
        this.dmLSuiS = dmLSuiS;
    }

    public Double getVsLSuiS() {
        return vsLSuiS;
    }

    public void setVsLSuiS(Double vsLSuiS) {
        this.vsLSuiS = vsLSuiS;
    }

    public Double getPLSuiS() {
        return pLSuiS;
    }

    public void setPLSuiS(Double pLSuiS) {
        this.pLSuiS = pLSuiS;
    }

    public Double getKLSuiS() {
        return kLSuiS;
    }

    public void setKLSuiS(Double kLSuiS) {
        this.kLSuiS = kLSuiS;
    }

    public Double getM3PSuiS() {
        return m3PSuiS;
    }

    public void setM3PSuiS(Double m3PSuiS) {
        this.m3PSuiS = m3PSuiS;
    }

    public Double getTknPSuiS() {
        return tknPSuiS;
    }

    public void setTknPSuiS(Double tknPSuiS) {
        this.tknPSuiS = tknPSuiS;
    }

    public Double getTanPSuiS() {
        return tanPSuiS;
    }

    public void setTanPSuiS(Double tanPSuiS) {
        this.tanPSuiS = tanPSuiS;
    }

    public Double getDmPSuiS() {
        return dmPSuiS;
    }

    public void setDmPSuiS(Double dmPSuiS) {
        this.dmPSuiS = dmPSuiS;
    }

    public Double getVsPSuiS() {
        return vsPSuiS;
    }

    public void setVsPSuiS(Double vsPSuiS) {
        this.vsPSuiS = vsPSuiS;
    }

    public Double getPPSuiS() {
        return pPSuiS;
    }

    public void setPPSuiS(Double pPSuiS) {
        this.pPSuiS = pPSuiS;
    }

    public Double getKPSuiS() {
        return kPSuiS;
    }

    public void setKPSuiS(Double kPSuiS) {
        this.kPSuiS = kPSuiS;
    }

    public Double getM3LAviS() {
        return m3LAviS;
    }

    public void setM3LAviS(Double m3LAviS) {
        this.m3LAviS = m3LAviS;
    }

    public Double getTknLAviS() {
        return tknLAviS;
    }

    public void setTknLAviS(Double tknLAviS) {
        this.tknLAviS = tknLAviS;
    }

    public Double getTanLAviS() {
        return tanLAviS;
    }

    public void setTanLAviS(Double tanLAviS) {
        this.tanLAviS = tanLAviS;
    }

    public Double getDmLAviS() {
        return dmLAviS;
    }

    public void setDmLAviS(Double dmLAviS) {
        this.dmLAviS = dmLAviS;
    }

    public Double getVsLAviS() {
        return vsLAviS;
    }

    public void setVsLAviS(Double vsLAviS) {
        this.vsLAviS = vsLAviS;
    }

    public Double getPLAviS() {
        return pLAviS;
    }

    public void setPLAviS(Double pLAviS) {
        this.pLAviS = pLAviS;
    }

    public Double getKLAviS() {
        return kLAviS;
    }

    public void setKLAviS(Double kLAviS) {
        this.kLAviS = kLAviS;
    }

    public Double getM3PAviS() {
        return m3PAviS;
    }

    public void setM3PAviS(Double m3PAviS) {
        this.m3PAviS = m3PAviS;
    }

    public Double getTknPAviS() {
        return tknPAviS;
    }

    public void setTknPAviS(Double tknPAviS) {
        this.tknPAviS = tknPAviS;
    }

    public Double getTanPAviS() {
        return tanPAviS;
    }

    public void setTanPAviS(Double tanPAviS) {
        this.tanPAviS = tanPAviS;
    }

    public Double getDmPAviS() {
        return dmPAviS;
    }

    public void setDmPAviS(Double dmPAviS) {
        this.dmPAviS = dmPAviS;
    }

    public Double getVsPAviS() {
        return vsPAviS;
    }

    public void setVsPAviS(Double vsPAviS) {
        this.vsPAviS = vsPAviS;
    }

    public Double getPPAviS() {
        return pPAviS;
    }

    public void setPPAviS(Double pPAviS) {
        this.pPAviS = pPAviS;
    }

    public Double getKPAviS() {
        return kPAviS;
    }

    public void setKPAviS(Double kPAviS) {
        this.kPAviS = kPAviS;
    }

    public Double getM3LAltS() {
        return m3LAltS;
    }

    public void setM3LAltS(Double m3LAltS) {
        this.m3LAltS = m3LAltS;
    }

    public Double getTknLAltS() {
        return tknLAltS;
    }

    public void setTknLAltS(Double tknLAltS) {
        this.tknLAltS = tknLAltS;
    }

    public Double getTanLAltS() {
        return tanLAltS;
    }

    public void setTanLAltS(Double tanLAltS) {
        this.tanLAltS = tanLAltS;
    }

    public Double getDmLAltS() {
        return dmLAltS;
    }

    public void setDmLAltS(Double dmLAltS) {
        this.dmLAltS = dmLAltS;
    }

    public Double getVsLAltS() {
        return vsLAltS;
    }

    public void setVsLAltS(Double vsLAltS) {
        this.vsLAltS = vsLAltS;
    }

    public Double getPLAltS() {
        return pLAltS;
    }

    public void setPLAltS(Double pLAltS) {
        this.pLAltS = pLAltS;
    }

    public Double getKLAltS() {
        return kLAltS;
    }

    public void setKLAltS(Double kLAltS) {
        this.kLAltS = kLAltS;
    }

    public Double getM3PAltS() {
        return m3PAltS;
    }

    public void setM3PAltS(Double m3PAltS) {
        this.m3PAltS = m3PAltS;
    }

    public Double getTknPAltS() {
        return tknPAltS;
    }

    public void setTknPAltS(Double tknPAltS) {
        this.tknPAltS = tknPAltS;
    }

    public Double getTanPAltS() {
        return tanPAltS;
    }

    public void setTanPAltS(Double tanPAltS) {
        this.tanPAltS = tanPAltS;
    }

    public Double getDmPAltS() {
        return dmPAltS;
    }

    public void setDmPAltS(Double dmPAltS) {
        this.dmPAltS = dmPAltS;
    }

    public Double getVsPAltS() {
        return vsPAltS;
    }

    public void setVsPAltS(Double vsPAltS) {
        this.vsPAltS = vsPAltS;
    }

    public Double getPPAltS() {
        return pPAltS;
    }

    public void setPPAltS(Double pPAltS) {
        this.pPAltS = pPAltS;
    }

    public Double getKPAltS() {
        return kPAltS;
    }

    public void setKPAltS(Double kPAltS) {
        this.kPAltS = kPAltS;
    }

    public Double getM3LBioS() {
        return m3LBioS;
    }

    public void setM3LBioS(Double m3LBioS) {
        this.m3LBioS = m3LBioS;
    }

    public Double getTknLBioS() {
        return tknLBioS;
    }

    public void setTknLBioS(Double tknLBioS) {
        this.tknLBioS = tknLBioS;
    }

    public Double getTanLBioS() {
        return tanLBioS;
    }

    public void setTanLBioS(Double tanLBioS) {
        this.tanLBioS = tanLBioS;
    }

    public Double getDmLBioS() {
        return dmLBioS;
    }

    public void setDmLBioS(Double dmLBioS) {
        this.dmLBioS = dmLBioS;
    }

    public Double getVsLBioS() {
        return vsLBioS;
    }

    public void setVsLBioS(Double vsLBioS) {
        this.vsLBioS = vsLBioS;
    }

    public Double getPLBioS() {
        return pLBioS;
    }

    public void setPLBioS(Double pLBioS) {
        this.pLBioS = pLBioS;
    }

    public Double getKLBioS() {
        return kLBioS;
    }

    public void setKLBioS(Double kLBioS) {
        this.kLBioS = kLBioS;
    }

    public Double getM3PBioS() {
        return m3PBioS;
    }

    public void setM3PBioS(Double m3PBioS) {
        this.m3PBioS = m3PBioS;
    }

    public Double getTknPBioS() {
        return tknPBioS;
    }

    public void setTknPBioS(Double tknPBioS) {
        this.tknPBioS = tknPBioS;
    }

    public Double getTanPBioS() {
        return tanPBioS;
    }

    public void setTanPBioS(Double tanPBioS) {
        this.tanPBioS = tanPBioS;
    }

    public Double getDmPBioS() {
        return dmPBioS;
    }

    public void setDmPBioS(Double dmPBioS) {
        this.dmPBioS = dmPBioS;
    }

    public Double getVsPBioS() {
        return vsPBioS;
    }

    public void setVsPBioS(Double vsPBioS) {
        this.vsPBioS = vsPBioS;
    }

    public Double getPPBioS() {
        return pPBioS;
    }

    public void setPPBioS(Double pPBioS) {
        this.pPBioS = pPBioS;
    }

    public Double getKPBioS() {
        return kPBioS;
    }

    public void setKPBioS(Double kPBioS) {
        this.kPBioS = kPBioS;
    }

    public Boolean getM3LBovM() {
        return m3LBovM;
    }

    public void setM3LBovM(Boolean m3LBovM) {
        this.m3LBovM = m3LBovM;
    }

    public Boolean getTknLBovM() {
        return tknLBovM;
    }

    public void setTknLBovM(Boolean tknLBovM) {
        this.tknLBovM = tknLBovM;
    }

    public Boolean getTanLBovM() {
        return tanLBovM;
    }

    public void setTanLBovM(Boolean tanLBovM) {
        this.tanLBovM = tanLBovM;
    }

    public Boolean getDmLBovM() {
        return dmLBovM;
    }

    public void setDmLBovM(Boolean dmLBovM) {
        this.dmLBovM = dmLBovM;
    }

    public Boolean getVsLBovM() {
        return vsLBovM;
    }

    public void setVsLBovM(Boolean vsLBovM) {
        this.vsLBovM = vsLBovM;
    }

    public Boolean getPLBovM() {
        return pLBovM;
    }

    public void setPLBovM(Boolean pLBovM) {
        this.pLBovM = pLBovM;
    }

    public Boolean getKLBovM() {
        return kLBovM;
    }

    public void setKLBovM(Boolean kLBovM) {
        this.kLBovM = kLBovM;
    }

    public Boolean getM3PBovM() {
        return m3PBovM;
    }

    public void setM3PBovM(Boolean m3PBovM) {
        this.m3PBovM = m3PBovM;
    }

    public Boolean getTknPBovM() {
        return tknPBovM;
    }

    public void setTknPBovM(Boolean tknPBovM) {
        this.tknPBovM = tknPBovM;
    }

    public Boolean getTanPBovM() {
        return tanPBovM;
    }

    public void setTanPBovM(Boolean tanPBovM) {
        this.tanPBovM = tanPBovM;
    }

    public Boolean getDmPBovM() {
        return dmPBovM;
    }

    public void setDmPBovM(Boolean dmPBovM) {
        this.dmPBovM = dmPBovM;
    }

    public Boolean getVsPBovM() {
        return vsPBovM;
    }

    public void setVsPBovM(Boolean vsPBovM) {
        this.vsPBovM = vsPBovM;
    }

    public Boolean getPPBovM() {
        return pPBovM;
    }

    public void setPPBovM(Boolean pPBovM) {
        this.pPBovM = pPBovM;
    }

    public Boolean getKPBovM() {
        return kPBovM;
    }

    public void setKPBovM(Boolean kPBovM) {
        this.kPBovM = kPBovM;
    }

    public Boolean getM3LSuiM() {
        return m3LSuiM;
    }

    public void setM3LSuiM(Boolean m3LSuiM) {
        this.m3LSuiM = m3LSuiM;
    }

    public Boolean getTknLSuiM() {
        return tknLSuiM;
    }

    public void setTknLSuiM(Boolean tknLSuiM) {
        this.tknLSuiM = tknLSuiM;
    }

    public Boolean getTanLSuiM() {
        return tanLSuiM;
    }

    public void setTanLSuiM(Boolean tanLSuiM) {
        this.tanLSuiM = tanLSuiM;
    }

    public Boolean getDmLSuiM() {
        return dmLSuiM;
    }

    public void setDmLSuiM(Boolean dmLSuiM) {
        this.dmLSuiM = dmLSuiM;
    }

    public Boolean getVsLSuiM() {
        return vsLSuiM;
    }

    public void setVsLSuiM(Boolean vsLSuiM) {
        this.vsLSuiM = vsLSuiM;
    }

    public Boolean getPLSuiM() {
        return pLSuiM;
    }

    public void setPLSuiM(Boolean pLSuiM) {
        this.pLSuiM = pLSuiM;
    }

    public Boolean getKLSuiM() {
        return kLSuiM;
    }

    public void setKLSuiM(Boolean kLSuiM) {
        this.kLSuiM = kLSuiM;
    }

    public Boolean getM3PSuiM() {
        return m3PSuiM;
    }

    public void setM3PSuiM(Boolean m3PSuiM) {
        this.m3PSuiM = m3PSuiM;
    }

    public Boolean getTknPSuiM() {
        return tknPSuiM;
    }

    public void setTknPSuiM(Boolean tknPSuiM) {
        this.tknPSuiM = tknPSuiM;
    }

    public Boolean getTanPSuiM() {
        return tanPSuiM;
    }

    public void setTanPSuiM(Boolean tanPSuiM) {
        this.tanPSuiM = tanPSuiM;
    }

    public Boolean getDmPSuiM() {
        return dmPSuiM;
    }

    public void setDmPSuiM(Boolean dmPSuiM) {
        this.dmPSuiM = dmPSuiM;
    }

    public Boolean getVsPSuiM() {
        return vsPSuiM;
    }

    public void setVsPSuiM(Boolean vsPSuiM) {
        this.vsPSuiM = vsPSuiM;
    }

    public Boolean getPPSuiM() {
        return pPSuiM;
    }

    public void setPPSuiM(Boolean pPSuiM) {
        this.pPSuiM = pPSuiM;
    }

    public Boolean getKPSuiM() {
        return kPSuiM;
    }

    public void setKPSuiM(Boolean kPSuiM) {
        this.kPSuiM = kPSuiM;
    }

    public Boolean getM3LAviM() {
        return m3LAviM;
    }

    public void setM3LAviM(Boolean m3LAviM) {
        this.m3LAviM = m3LAviM;
    }

    public Boolean getTknLAviM() {
        return tknLAviM;
    }

    public void setTknLAviM(Boolean tknLAviM) {
        this.tknLAviM = tknLAviM;
    }

    public Boolean getTanLAviM() {
        return tanLAviM;
    }

    public void setTanLAviM(Boolean tanLAviM) {
        this.tanLAviM = tanLAviM;
    }

    public Boolean getDmLAviM() {
        return dmLAviM;
    }

    public void setDmLAviM(Boolean dmLAviM) {
        this.dmLAviM = dmLAviM;
    }

    public Boolean getVsLAviM() {
        return vsLAviM;
    }

    public void setVsLAviM(Boolean vsLAviM) {
        this.vsLAviM = vsLAviM;
    }

    public Boolean getPLAviM() {
        return pLAviM;
    }

    public void setPLAviM(Boolean pLAviM) {
        this.pLAviM = pLAviM;
    }

    public Boolean getKLAviM() {
        return kLAviM;
    }

    public void setKLAviM(Boolean kLAviM) {
        this.kLAviM = kLAviM;
    }

    public Boolean getM3PAviM() {
        return m3PAviM;
    }

    public void setM3PAviM(Boolean m3PAviM) {
        this.m3PAviM = m3PAviM;
    }

    public Boolean getTknPAviM() {
        return tknPAviM;
    }

    public void setTknPAviM(Boolean tknPAviM) {
        this.tknPAviM = tknPAviM;
    }

    public Boolean getTanPAviM() {
        return tanPAviM;
    }

    public void setTanPAviM(Boolean tanPAviM) {
        this.tanPAviM = tanPAviM;
    }

    public Boolean getDmPAviM() {
        return dmPAviM;
    }

    public void setDmPAviM(Boolean dmPAviM) {
        this.dmPAviM = dmPAviM;
    }

    public Boolean getVsPAviM() {
        return vsPAviM;
    }

    public void setVsPAviM(Boolean vsPAviM) {
        this.vsPAviM = vsPAviM;
    }

    public Boolean getPPAviM() {
        return pPAviM;
    }

    public void setPPAviM(Boolean pPAviM) {
        this.pPAviM = pPAviM;
    }

    public Boolean getKPAviM() {
        return kPAviM;
    }

    public void setKPAviM(Boolean kPAviM) {
        this.kPAviM = kPAviM;
    }

    public Boolean getM3LAltM() {
        return m3LAltM;
    }

    public void setM3LAltM(Boolean m3LAltM) {
        this.m3LAltM = m3LAltM;
    }

    public Boolean getTknLAltM() {
        return tknLAltM;
    }

    public void setTknLAltM(Boolean tknLAltM) {
        this.tknLAltM = tknLAltM;
    }

    public Boolean getTanLAltM() {
        return tanLAltM;
    }

    public void setTanLAltM(Boolean tanLAltM) {
        this.tanLAltM = tanLAltM;
    }

    public Boolean getDmLAltM() {
        return dmLAltM;
    }

    public void setDmLAltM(Boolean dmLAltM) {
        this.dmLAltM = dmLAltM;
    }

    public Boolean getVsLAltM() {
        return vsLAltM;
    }

    public void setVsLAltM(Boolean vsLAltM) {
        this.vsLAltM = vsLAltM;
    }

    public Boolean getPLAltM() {
        return pLAltM;
    }

    public void setPLAltM(Boolean pLAltM) {
        this.pLAltM = pLAltM;
    }

    public Boolean getKLAltM() {
        return kLAltM;
    }

    public void setKLAltM(Boolean kLAltM) {
        this.kLAltM = kLAltM;
    }

    public Boolean getM3PAltM() {
        return m3PAltM;
    }

    public void setM3PAltM(Boolean m3PAltM) {
        this.m3PAltM = m3PAltM;
    }

    public Boolean getTknPAltM() {
        return tknPAltM;
    }

    public void setTknPAltM(Boolean tknPAltM) {
        this.tknPAltM = tknPAltM;
    }

    public Boolean getTanPAltM() {
        return tanPAltM;
    }

    public void setTanPAltM(Boolean tanPAltM) {
        this.tanPAltM = tanPAltM;
    }

    public Boolean getDmPAltM() {
        return dmPAltM;
    }

    public void setDmPAltM(Boolean dmPAltM) {
        this.dmPAltM = dmPAltM;
    }

    public Boolean getVsPAltM() {
        return vsPAltM;
    }

    public void setVsPAltM(Boolean vsPAltM) {
        this.vsPAltM = vsPAltM;
    }

    public Boolean getPPAltM() {
        return pPAltM;
    }

    public void setPPAltM(Boolean pPAltM) {
        this.pPAltM = pPAltM;
    }

    public Boolean getKPAltM() {
        return kPAltM;
    }

    public void setKPAltM(Boolean kPAltM) {
        this.kPAltM = kPAltM;
    }

    public Boolean getM3LBioM() {
        return m3LBioM;
    }

    public void setM3LBioM(Boolean m3LBioM) {
        this.m3LBioM = m3LBioM;
    }

    public Boolean getTknLBioM() {
        return tknLBioM;
    }

    public void setTknLBioM(Boolean tknLBioM) {
        this.tknLBioM = tknLBioM;
    }

    public Boolean getTanLBioM() {
        return tanLBioM;
    }

    public void setTanLBioM(Boolean tanLBioM) {
        this.tanLBioM = tanLBioM;
    }

    public Boolean getDmLBioM() {
        return dmLBioM;
    }

    public void setDmLBioM(Boolean dmLBioM) {
        this.dmLBioM = dmLBioM;
    }

    public Boolean getVsLBioM() {
        return vsLBioM;
    }

    public void setVsLBioM(Boolean vsLBioM) {
        this.vsLBioM = vsLBioM;
    }

    public Boolean getPLBioM() {
        return pLBioM;
    }

    public void setPLBioM(Boolean pLBioM) {
        this.pLBioM = pLBioM;
    }

    public Boolean getKLBioM() {
        return kLBioM;
    }

    public void setKLBioM(Boolean kLBioM) {
        this.kLBioM = kLBioM;
    }

    public Boolean getM3PBioM() {
        return m3PBioM;
    }

    public void setM3PBioM(Boolean m3PBioM) {
        this.m3PBioM = m3PBioM;
    }

    public Boolean getTknPBioM() {
        return tknPBioM;
    }

    public void setTknPBioM(Boolean tknPBioM) {
        this.tknPBioM = tknPBioM;
    }

    public Boolean getTanPBioM() {
        return tanPBioM;
    }

    public void setTanPBioM(Boolean tanPBioM) {
        this.tanPBioM = tanPBioM;
    }

    public Boolean getDmPBioM() {
        return dmPBioM;
    }

    public void setDmPBioM(Boolean dmPBioM) {
        this.dmPBioM = dmPBioM;
    }

    public Boolean getVsPBioM() {
        return vsPBioM;
    }

    public void setVsPBioM(Boolean vsPBioM) {
        this.vsPBioM = vsPBioM;
    }

    public Boolean getPPBioM() {
        return pPBioM;
    }

    public void setPPBioM(Boolean pPBioM) {
        this.pPBioM = pPBioM;
    }

    public Boolean getKPBioM() {
        return kPBioM;
    }

    public void setKPBioM(Boolean kPBioM) {
        this.kPBioM = kPBioM;
    }

    public ScenarioI getScenarioI() {
        return scenarioI;
    }

    public void setScenarioI(ScenarioI scenarioI) {
        this.scenarioI = scenarioI;
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
        if (!(object instanceof CaratteristicheChmiche)) {
            return false;
        }
        CaratteristicheChmiche other = (CaratteristicheChmiche) object;
        if ((this.idScenario == null && other.idScenario != null) || (this.idScenario != null && !this.idScenario.equals(other.idScenario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.CaratteristicheChmiche[ idScenario=" + idScenario + " ]";
    }
    
}
