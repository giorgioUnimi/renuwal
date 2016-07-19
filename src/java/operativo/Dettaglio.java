/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author giorgio
 */
@ManagedBean(name = "dettaglio")
@SessionScoped
public class Dettaglio {
    
        private String nascondi ="";
 
        public void nascondi()
        {
             
              
           UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
           UIComponent comp = view.findComponent("j_idt32:tabellaAziendaLetPan"); 
           comp.setRendered(false);
           
           comp = view.findComponent("j_idt32:tabellaAziendaLiqPan"); 
           comp.setRendered(false);
        }
        
        
         public void mostra()
        {
             
              
           UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
           UIComponent comp = view.findComponent("j_idt32:tabellaAziendaLetPan"); 
           comp.setRendered(true);
           
           comp = view.findComponent("j_idt32:tabellaAziendaLiqPan"); 
           comp.setRendered(true);
        }
        
        public void faiqualcosa()
        {
             
              
           UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
           UIComponent comp = view.findComponent("j_idt32:tabellaAziendaLetPan"); 
           if(nascondi.equals("mostra"))
           {
               System.out.println("mostro " + comp.getClientId());
               comp.setRendered(true);
               
           }
           
           if(nascondi.equals("nascondi"))
           {
                System.out.println("nascondo " + comp.getClientId());
               comp.setRendered(false);
               
           }
       // String value = (String) comp.;
        
        System.out.println("----------------valore radio button -----------"+this.nascondi+"----------------------");
        
        
        /*FacesContext context = FacesContext.getCurrentInstance();
	String viewId = context.getViewRoot().getViewId();
	ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
	root.setViewId(viewId);
	context.setViewRoot(root);*/
        
       
        }

    /**
     * @return the nascondi
     */
    public String getNascondi() {
        return nascondi;
    }

    /**
     * @param nascondi the nascondi to set
     */
    public void setNascondi(String nascondi) {
        this.nascondi = nascondi;
    }
 
}
    

