/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WebGui;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.richfaces.component.SortOrder;

/**
 *Bean di supporto alla pagina aziende.xhtml 
 * alla tabella listaaziende1 
 * serve per implementare l'ordinamento
 * @author giorgio
 */
@ManagedBean(name = "sortaziende")
@ViewScoped
public class SortAziende implements Serializable{
    
    private static final long serialVersionUID = -6237417487105926855L;
    
    
    private SortOrder cuaaOrder = SortOrder.unsorted;
    private SortOrder ragionisocialiOrder = SortOrder.unsorted;
    private SortOrder cuaafintoOrder = SortOrder.unsorted;
    private SortOrder ragionisocialifintoOrder = SortOrder.unsorted;
    private SortOrder comuniOrder = SortOrder.unsorted;
    private SortOrder codcomuniOrder = SortOrder.unsorted;
    private SortOrder provinciaOrder = SortOrder.unsorted;
    private SortOrder codprovinciaOrder = SortOrder.unsorted;
    private SortOrder derogaOrder = SortOrder.unsorted;
    
    
    
    public void sortByCuaa() {
        
       
        
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getCuaaOrder().equals(SortOrder.ascending)) {
            setCuaaOrder(SortOrder.descending);
        } else {
            setCuaaOrder(SortOrder.ascending);
        }
    }
    
    
    public void sortByCuaafinto() {
        System.out.println("sort by cuaafinto");
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getCuaafintoOrder().equals(SortOrder.ascending)) {
            setCuaafintoOrder(SortOrder.descending);
        } else {
            setCuaafintoOrder(SortOrder.ascending);
        }
    }
    
    
    
    public void sortByRagione() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getRagionisocialiOrder().equals(SortOrder.ascending)) {
            setRagionisocialiOrder(SortOrder.descending);
        } else {
             setRagionisocialiOrder(SortOrder.ascending);
        }
    }
     
    
    public void sortByRagionefinto() {
        setRagionisocialiOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getRagionisocialifintoOrder().equals(SortOrder.ascending)) {
            setRagionisocialifintoOrder(SortOrder.descending);
        } else {
             setRagionisocialifintoOrder(SortOrder.ascending);
        }
    }
    
    
    public void sortByComuni() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getComuniOrder().equals(SortOrder.ascending)) {
            setComuniOrder(SortOrder.descending);
        } else {
            setComuniOrder(SortOrder.ascending);
        }
    }
    
    
     public void sortByCodComuni() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getCodcomuniOrder().equals(SortOrder.ascending)) {
            setCodcomuniOrder(SortOrder.descending);
        } else {
            setCodcomuniOrder(SortOrder.ascending);
        }
    }
    
    public void sortByProvincia() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getProvinciaOrder().equals(SortOrder.ascending)) {
            setProvinciaOrder(SortOrder.descending);
        } else {
            setProvinciaOrder(SortOrder.ascending);
        }
    }
    
    public void sortByCodProvincia() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        setDerogaOrder(SortOrder.unsorted);
        if (getCodprovinciaOrder().equals(SortOrder.ascending)) {
            setCodprovinciaOrder(SortOrder.descending);
        } else {
            setCodprovinciaOrder(SortOrder.ascending);
        }
    }
    
    public void sortByDeroga() {
        setRagionisocialifintoOrder(SortOrder.unsorted);
        setCuaafintoOrder(SortOrder.unsorted);
        setCuaaOrder(SortOrder.unsorted);
        setRagionisocialiOrder(SortOrder.unsorted);
        setComuniOrder(SortOrder.unsorted);
        setProvinciaOrder(SortOrder.unsorted);
        setCodprovinciaOrder(SortOrder.unsorted);
        setCodcomuniOrder(SortOrder.unsorted);
        if (getDerogaOrder().equals(SortOrder.ascending)) {
            setDerogaOrder(SortOrder.descending);
        } else {
            setDerogaOrder(SortOrder.ascending);
        }
    }
    
    
    /**
     * @return the cuaaOrder
     */
    public SortOrder getCuaaOrder() {
        return cuaaOrder;
    }

    /**
     * @param cuaaOrder the cuaaOrder to set
     */
    public void setCuaaOrder(SortOrder cuaaOrder) {
        this.cuaaOrder = cuaaOrder;
    }

    /**
     * @return the ragionisocialiOrder
     */
    public SortOrder getRagionisocialiOrder() {
        return ragionisocialiOrder;
    }

    /**
     * @param ragionisocialiOrder the ragionisocialiOrder to set
     */
    public void setRagionisocialiOrder(SortOrder ragionisocialiOrder) {
        this.ragionisocialiOrder = ragionisocialiOrder;
    }

    /**
     * @return the comuniOrder
     */
    public SortOrder getComuniOrder() {
        return comuniOrder;
    }

    /**
     * @param comuniOrder the comuniOrder to set
     */
    public void setComuniOrder(SortOrder comuniOrder) {
        this.comuniOrder = comuniOrder;
    }

    /**
     * @return the cuaafintoOrder
     */
    public SortOrder getCuaafintoOrder() {
        return cuaafintoOrder;
    }

    /**
     * @param cuaafintoOrder the cuaafintoOrder to set
     */
    public void setCuaafintoOrder(SortOrder cuaafintoOrder) {
        this.cuaafintoOrder = cuaafintoOrder;
    }

    /**
     * @return the ragionisocialifintoOrder
     */
    public SortOrder getRagionisocialifintoOrder() {
        return ragionisocialifintoOrder;
    }

    /**
     * @param ragionisocialifintoOrder the ragionisocialifintoOrder to set
     */
    public void setRagionisocialifintoOrder(SortOrder ragionisocialifintoOrder) {
        this.ragionisocialifintoOrder = ragionisocialifintoOrder;
    }

    /**
     * @return the codcomuniOrder
     */
    public SortOrder getCodcomuniOrder() {
        return codcomuniOrder;
    }

    /**
     * @param codcomuniOrder the codcomuniOrder to set
     */
    public void setCodcomuniOrder(SortOrder codcomuniOrder) {
        this.codcomuniOrder = codcomuniOrder;
    }

    /**
     * @return the provinciaOrder
     */
    public SortOrder getProvinciaOrder() {
        return provinciaOrder;
    }

    /**
     * @param provinciaOrder the provinciaOrder to set
     */
    public void setProvinciaOrder(SortOrder provinciaOrder) {
        this.provinciaOrder = provinciaOrder;
    }

    /**
     * @return the codprovinciaOrder
     */
    public SortOrder getCodprovinciaOrder() {
        return codprovinciaOrder;
    }

    /**
     * @param codprovinciaOrder the codprovinciaOrder to set
     */
    public void setCodprovinciaOrder(SortOrder codprovinciaOrder) {
        this.codprovinciaOrder = codprovinciaOrder;
    }

    /**
     * @return the derogaOrder
     */
    public SortOrder getDerogaOrder() {
        return derogaOrder;
    }

    /**
     * @param derogaOrder the derogaOrder to set
     */
    public void setDerogaOrder(SortOrder derogaOrder) {
        this.derogaOrder = derogaOrder;
    }
    
    
    
}
