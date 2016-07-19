/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebGui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giorgio
 */
/**
 *
 * @author giorgio
 */
@ManagedBean(name = "logout")
@ViewScoped
public class LogOut implements Serializable{
    
     private static final long serialVersionUID = 1L;
     
     public String logout() {
    ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
    
    
     return "/index.xhtml?faces-redirect=true";
}
    
}
