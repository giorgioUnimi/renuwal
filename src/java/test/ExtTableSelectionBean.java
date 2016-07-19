package test;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
 
import org.richfaces.component.AbstractExtendedDataTable;
//import    .librich.InventoryItem;
import test.librich.*;
 
@ManagedBean
@ViewScoped
public class ExtTableSelectionBean implements Serializable {
    private String selectionMode = "single";
    private Collection<Object> selection;
    //@ManagedProperty(value = "#{carsBean.allInventoryItems}")
    private List<InventoryItem> inventoryItems = new LinkedList<InventoryItem>();
    private List<InventoryItem> selectionItems = new ArrayList<InventoryItem>();
 
    public ExtTableSelectionBean()
    {
        InventoryItem itemI = new InventoryItem();
        itemI.setVendor("aaaaa");
        itemI.setModel("bbbbb");
        itemI.setPrice(1200);
        
        InventoryItem itemI1 = new InventoryItem();
        itemI1.setVendor("ccccc");
        itemI1.setModel("ddddd");
        itemI1.setPrice(1300);
        
        InventoryItem itemI2 = new InventoryItem();
        itemI2.setVendor("eeeeee");
        itemI2.setModel("ffffff");
        itemI2.setPrice(1400);
        
         InventoryItem itemI3 = new InventoryItem();
        itemI3.setVendor("gggggg");
        itemI3.setModel("hhhhhh");
        itemI3.setPrice(1500);
        
       inventoryItems.add(itemI);
        inventoryItems.add(itemI1);
         inventoryItems.add(itemI2);
          inventoryItems.add(itemI3);
        //selection.add(itemI);
    }
    
    
    public void selectionListener(AjaxBehaviorEvent event) {
        AbstractExtendedDataTable dataTable = (AbstractExtendedDataTable) event.getComponent();
        Object originalKey = dataTable.getRowKey();
       selectionItems.clear();
        for (Object selectionKey : selection) {
            dataTable.setRowKey(selectionKey);
            if (dataTable.isRowAvailable()) {
                selectionItems.add((InventoryItem) dataTable.getRowData());
                InventoryItem invItem =(InventoryItem) dataTable.getRowData();
                System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+" " +invItem.getModel()+" " + invItem.getVendor());
            }
        }
        dataTable.setRowKey(originalKey);
    }
 
    public Collection<Object> getSelection() {
        return selection;
    }
 
    public void setSelection(Collection<Object> selection) {
        this.selection = selection;
    }
 
    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }
 
    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
 
    public InventoryItem getSelectionItem() {
        if (selectionItems == null || selectionItems.isEmpty()) {
            return null;
        }
        return selectionItems.get(0);
    }
 
    public List<InventoryItem> getSelectionItems() {
        return selectionItems;
    }
 
    public void setSelectionItems(List<InventoryItem> selectionItems) {
        this.selectionItems = selectionItems;
    }
 
    public String getSelectionMode() {
        return selectionMode;
    }
 
    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }
}