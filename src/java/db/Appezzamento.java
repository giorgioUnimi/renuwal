/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * appezzamento di una data azienda . Un azienda ha piu anni ed ogni anno
 * ha piu scenari ed ogni scenario ha piu appezzamenti.
 * @author giorgio
 */
@Entity
@Table(name="appezzamento", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appezzamento.findAll", query = "SELECT a FROM Appezzamento a"),
    @NamedQuery(name = "Appezzamento.findById", query = "SELECT a FROM Appezzamento a where a.id = :id")
    })
public class Appezzamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean svz;
    private double superficie;
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name= "add_fk_tipoterreno" , nullable = false)
    @OneToOne
    @JoinColumn(name= "tipoterreno" , nullable = false)
    private TipoTerreno tipoTerreno;
    //@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name= "add_fk_tipoirrigazione" , nullable = false)
    @OneToOne
    @JoinColumn(name= "tipoirrigazione" , nullable = false)
    private TipoIrrigazione tipoIrrigazione;
    //@OneToMany(mappedBy="idAppezzamento",cascade=CascadeType.PERSIST)
    @OneToMany(mappedBy="idAppezzamento",cascade=CascadeType.ALL)
    private Collection<StoricoColturaAppezzamento> storicoColturaAppezzamento;
    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(schema = "allevamento")
    private Collection<db.Efficienza> listaEfficienze ;*/
    /*@ManyToOne(cascade=CascadeType.REMOVE)
    private ScenarioI scenario; */
    @OneToOne
    private ColturaPrecedente colturaPrecedente;
            
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
        if (!(object instanceof Appezzamento)) {
            return false;
        }
        Appezzamento other = (Appezzamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "db.Appezzamento[ id=" + id + " ]";
        return id.toString();
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the svz
     */
    public boolean isSvz() {
        return svz;
    }

    /**
     * @param svz the svz to set
     */
    public void setSvz(boolean svz) {
        this.svz = svz;
    }

    /**
     * @return the superficie
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * @param superficie the superficie to set
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * @return the tipoTerreno
     */
    public TipoTerreno getTipoTerreno() {
        return tipoTerreno;
    }

    /**
     * @param tipoTerreno the tipoTerreno to set
     */
    public void setTipoTerreno(TipoTerreno tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    /**
     * @return the tipoIrrigazione
     */
    public TipoIrrigazione getTipoIrrigazione() {
        return tipoIrrigazione;
    }

    /**
     * @param tipoIrrigazione the tipoIrrigazione to set
     */
    public void setTipoIrrigazione(TipoIrrigazione tipoIrrigazione) {
        this.tipoIrrigazione = tipoIrrigazione;
    }

    /**
     * @return the storicoColturaAppezzamento
     */
    public Collection<StoricoColturaAppezzamento> getStoricoColturaAppezzamento() {
        return storicoColturaAppezzamento;
    }

    /**
     * @param storicoColturaAppezzamento the storicoColturaAppezzamento to set
     */
    public void setStoricoColturaAppezzamento(Collection<StoricoColturaAppezzamento> storicoColturaAppezzamento) {
        this.storicoColturaAppezzamento = storicoColturaAppezzamento;
    }

    /**
     * @return the colturaPrecedente
     */
    public ColturaPrecedente getColturaPrecedente() {
        return colturaPrecedente;
    }

    /**
     * @param colturaPrecedente the colturaPrecedente to set
     */
    public void setColturaPrecedente(ColturaPrecedente colturaPrecedente) {
        this.colturaPrecedente = colturaPrecedente;
    }

    /**
     * @return the scenario
     */
    /*public ScenarioI getScenario() {
        return scenario;
    }
*/
    /**
     * @param scenario the scenario to set
     */
    /*public void setScenario(ScenarioI scenario) {
        this.scenario = scenario;
    }*/

    /**
     * @return the listaEfficienze
     */
   /* public Collection<db.Efficienza> getListaEfficienze() {
        return listaEfficienze;
    }*/

    /**
     * @param listaEfficienze the listaEfficienze to set
     */
   /* public void setListaEfficienze(Collection<db.Efficienza> listaEfficienze) {
        this.listaEfficienze = listaEfficienze;
    }
*/
    /**
     * @return the scenario
     */
   /* public ScenarioI getScenario() {
        return scenario;
    }*/

    /**
     * @param scenario the scenario to set
     */
    /*public void setScenario(ScenarioI scenario) {
        this.scenario = scenario;
    }*/
    
}
