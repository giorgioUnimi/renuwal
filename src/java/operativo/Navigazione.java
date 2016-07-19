/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *Questo bean mi serve per intercettare la pagina di chiamata alla pagina guida.
 * Se alla pagina guida arriva da index o aziende deve poter ritornare solo a quelle ed 
 * non andare anche in allevamenti,stoccaggi,alternative,separazione dove non avrebbe modo di capire quale
 * azienda è stata scelta perchè un azienda non è ancora stata scelta.
 * @author giorgio
 */
@ManagedBean(name = "navigazione")
@SessionScoped
public class Navigazione implements Serializable{
    
    
    private String daChi ;
    public String getPaginaAttuale()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
           
           String[] ss = ctx.getViewRoot().getViewId().split("['/']");
        
        //for(String g:ss)
        //    System.out.println("pagina chiamante " + g);
        
       // System.out.println("pagina chiamante posizione slash " + poslash + "  nella frase " + daChi);
          // return ctx.getViewRoot().getViewId();
           
        
        return ss[ss.length-1];
           
    }
    /**
     * @return the daChi
     */
    public String getDaChi() {
        //daChi = "pagina chiamante " + FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
              Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
   
            String gg = ((HttpServletRequest) request).getRequestURL().toString();
    
   // System.out.println("-----------"+gg+"----------------");
        String[] ss = gg.split("['/']");
        
        //for(String g:ss)
        //    System.out.println("pagina chiamante " + g);
        
       // System.out.println("pagina chiamante posizione slash " + poslash + "  nella frase " + daChi);
        
        return ss[ss.length-1];
    }

    /**
     * @param daChi the daChi to set
     */
    public void setDaChi(String daChi) {
        this.daChi = daChi;
    }
}
