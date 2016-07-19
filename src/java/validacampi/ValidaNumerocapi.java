/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validacampi;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author giorgio
 */
@FacesValidator("validacampi.ValidaNumerocapi")
public class ValidaNumerocapi implements Validator{

    
    //rappresenta il bottone aggiungi nella pagina allevamento.xhtml
    private UIComponent bottoneaggiungi;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       String va =  value.toString();
       
       
       System.out.println(this.getClass().getCanonicalName() + " quiuu");
       
       try{
            int numero = Integer.parseInt(va);
              System.out.println(this.getClass().getCanonicalName() + " numero " + numero);
       }catch(Exception ex)
       {
           FacesMessage msg = new FacesMessage("E-mail validation failed.","Invalid E-mail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                         System.out.println(this.getClass().getCanonicalName() + " lancio una eccezione ");
                         context = FacesContext.getCurrentInstance();
                         context.addMessage(getBottoneaggiungi().getClientId(context), msg);
			throw new ValidatorException(msg);
                        
                        
                       
                        
       }
    }

    /**
     * @return the bottoneaggiungi
     */
    public UIComponent getBottoneaggiungi() {
        return bottoneaggiungi;
    }

    /**
     * @param bottoneaggiungi the bottoneaggiungi to set
     */
    public void setBottoneaggiungi(UIComponent bottoneaggiungi) {
        this.bottoneaggiungi = bottoneaggiungi;
    }
    
}
