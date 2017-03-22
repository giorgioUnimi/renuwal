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
import javax.persistence.Id;
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
@Table(catalog = "renuwal2", schema = "allevamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utenti.findAll", query = "SELECT u FROM Utenti u"),
    @NamedQuery(name = "Utenti.findById", query = "SELECT u FROM Utenti u WHERE u.id = :id"),
    @NamedQuery(name = "Utenti.findByUsername", query = "SELECT u FROM Utenti u WHERE u.username = :username"),
    @NamedQuery(name = "Utenti.findByPassword", query = "SELECT u FROM Utenti u WHERE u.password = :password"),
    @NamedQuery(name = "Utenti.findBySuperuser", query = "SELECT u FROM Utenti u WHERE u.superuser = :superuser"),
    @NamedQuery(name = "Utenti.findByNomeVero", query = "SELECT u FROM Utenti u WHERE u.nomeVero = :nomeVero"),
    @NamedQuery(name = "Utenti.findByCognomeVero", query = "SELECT u FROM Utenti u WHERE u.cognomeVero = :cognomeVero"),
    @NamedQuery(name = "Utenti.findByMail", query = "SELECT u FROM Utenti u WHERE u.mail = :mail")})
public class Utenti implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer id;
    @Size(max = 255)
    @Column(length = 255)
    private String username;
    @Size(max = 255)
    @Column(length = 255)
    private String password;
    private Boolean superuser;
    @Size(max = 255)
    @Column(name = "nome_vero", length = 255)
    private String nomeVero;
    @Size(max = 255)
    @Column(name = "cognome_vero", length = 255)
    private String cognomeVero;
    @Size(max = 255)
    @Column(length = 255)
    private String mail;
    @OneToMany(mappedBy = "idUtente")
    private Collection<AziendeI> aziendeICollection;

    public Utenti() {
    }

    public Utenti(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSuperuser() {
        return superuser;
    }

    public void setSuperuser(Boolean superuser) {
        this.superuser = superuser;
    }

    public String getNomeVero() {
        return nomeVero;
    }

    public void setNomeVero(String nomeVero) {
        this.nomeVero = nomeVero;
    }

    public String getCognomeVero() {
        return cognomeVero;
    }

    public void setCognomeVero(String cognomeVero) {
        this.cognomeVero = cognomeVero;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @XmlTransient
    public Collection<AziendeI> getAziendeICollection() {
        return aziendeICollection;
    }

    public void setAziendeICollection(Collection<AziendeI> aziendeICollection) {
        this.aziendeICollection = aziendeICollection;
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
        if (!(object instanceof Utenti)) {
            return false;
        }
        Utenti other = (Utenti) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Utenti[ id=" + id + " ]";
    }
    
}
