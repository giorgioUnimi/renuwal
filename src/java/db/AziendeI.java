/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giorgio
 */
@Entity
@Table(name = "aziende_i", catalog = "renuwal1", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AziendeI.findAll", query = "SELECT a FROM AziendeI a"),
    @NamedQuery(name = "AziendeI.findByCuaa", query = "SELECT a FROM AziendeI a WHERE a.cuaa = :cuaa"),
    @NamedQuery(name = "AziendeI.findByRagioneSociale", query = "SELECT a FROM AziendeI a WHERE a.ragioneSociale = :ragioneSociale"),
    @NamedQuery(name = "AziendeI.findByCodComune", query = "SELECT a FROM AziendeI a WHERE a.codComune = :codComune"),
    @NamedQuery(name = "AziendeI.findByDesComune", query = "SELECT a FROM AziendeI a WHERE a.desComune = :desComune"),
    @NamedQuery(name = "AziendeI.findByCuaaFinto", query = "SELECT a FROM AziendeI a WHERE a.cuaaFinto = :cuaaFinto"),
    @NamedQuery(name = "AziendeI.findByRagioneSocialeFinto", query = "SELECT a FROM AziendeI a WHERE a.ragioneSocialeFinto = :ragioneSocialeFinto"),
    @NamedQuery(name = "AziendeI.findById", query = "SELECT a FROM AziendeI a WHERE a.id = :id"),
    @NamedQuery(name = "AziendeI.findByNote", query = "SELECT a FROM AziendeI a WHERE a.note = :note"),
    @NamedQuery(name = "AziendeI.findByDeroga", query = "SELECT a FROM AziendeI a WHERE a.deroga = :deroga"),
    @NamedQuery(name = "AziendeI.findByCentrox", query = "SELECT a FROM AziendeI a WHERE a.centrox = :centrox"),
    @NamedQuery(name = "AziendeI.findByCentroy", query = "SELECT a FROM AziendeI a WHERE a.centroy = :centroy"),
    @NamedQuery(name = "AziendeI.findByDesprovincia", query = "SELECT a FROM AziendeI a WHERE a.desprovincia = :desprovincia"),
    @NamedQuery(name = "AziendeI.findByCodiceprovincia", query = "SELECT a FROM AziendeI a WHERE a.codiceprovincia = :codiceprovincia")})
public class AziendeI implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String cuaa;
    @Size(max = 255)
    @Column(name = "ragione_sociale", length = 255)
    private String ragioneSociale;
    @Column(name = "cod_comune")
    private Integer codComune;
    @Size(max = 255)
    @Column(name = "des_comune", length = 255)
    private String desComune;
    @Size(max = 255)
    @Column(name = "cuaa_finto", length = 255)
    private String cuaaFinto;
    @Size(max = 255)
    @Column(name = "ragione_sociale_finto", length = 255)
    private String ragioneSocialeFinto;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 200)
    @Column(length = 200)
    private String note;
    private Boolean deroga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double centrox;
    @Column(precision = 17, scale = 17)
    private Double centroy;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String desprovincia;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String codiceprovincia;
    @OneToMany(mappedBy = "idAzienda")
    private Collection<AziendeAnni> aziendeAnniCollection;
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    @ManyToOne
    private Utenti idUtente;

    public AziendeI() {
    }

    public AziendeI(Integer id) {
        this.id = id;
    }

    public AziendeI(Integer id, String cuaa) {
        this.id = id;
        this.cuaa = cuaa;
    }

    public String getCuaa() {
        return cuaa;
    }

    public void setCuaa(String cuaa) {
        this.cuaa = cuaa;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public Integer getCodComune() {
        return codComune;
    }

    public void setCodComune(Integer codComune) {
        this.codComune = codComune;
    }

    public String getDesComune() {
        return desComune;
    }

    public void setDesComune(String desComune) {
        this.desComune = desComune;
    }

    public String getCuaaFinto() {
        return cuaaFinto;
    }

    public void setCuaaFinto(String cuaaFinto) {
        this.cuaaFinto = cuaaFinto;
    }

    public String getRagioneSocialeFinto() {
        return ragioneSocialeFinto;
    }

    public void setRagioneSocialeFinto(String ragioneSocialeFinto) {
        this.ragioneSocialeFinto = ragioneSocialeFinto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getDeroga() {
        return deroga;
    }

    public void setDeroga(Boolean deroga) {
        this.deroga = deroga;
    }

    public Double getCentrox() {
        return centrox;
    }

    public void setCentrox(Double centrox) {
        this.centrox = centrox;
    }

    public Double getCentroy() {
        return centroy;
    }

    public void setCentroy(Double centroy) {
        this.centroy = centroy;
    }

    public String getDesprovincia() {
        return desprovincia;
    }

    public void setDesprovincia(String desprovincia) {
        this.desprovincia = desprovincia;
    }

    public String getCodiceprovincia() {
        return codiceprovincia;
    }

    public void setCodiceprovincia(String codiceprovincia) {
        this.codiceprovincia = codiceprovincia;
    }

    @XmlTransient
    public Collection<AziendeAnni> getAziendeAnniCollection() {
        return aziendeAnniCollection;
    }

    public void setAziendeAnniCollection(Collection<AziendeAnni> aziendeAnniCollection) {
        this.aziendeAnniCollection = aziendeAnniCollection;
    }

    public Utenti getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utenti idUtente) {
        this.idUtente = idUtente;
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
        if (!(object instanceof AziendeI)) {
            return false;
        }
        AziendeI other = (AziendeI) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.AziendeI[ id=" + id + " ]";
    }
    
}
