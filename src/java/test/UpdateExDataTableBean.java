/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.common.collect.Lists;

import com.google.common.collect.Ordering;

import com.google.common.collect.Sets;

import org.richfaces.component.UIExtendedDataTable;
 

import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;

import javax.faces.bean.ViewScoped;

import java.io.Serializable;

import java.util.Collection;

import java.util.List;

import java.util.Set;

 

 

@ManagedBean(name="updateExtDataTableBean")
@ViewScoped
public class UpdateExDataTableBean implements Serializable {

    private static final long serialVersionUID = 7908746683927598277L;

    private static Set<String> dataBase = Sets.newTreeSet(Ordering.<Comparable>natural());

    private String item;

    private List<String> items;

    private Collection<Object> selection;

    private transient UIExtendedDataTable uitable;
    
    
    private String selectionMode = "single";

    @PostConstruct
    public void init() {

        items = Lists.newArrayList(dataBase);

    }

 

 

    public void updateTable() {

        items = Lists.newArrayList(dataBase);

    }

 

 

    public void addItem() {

        if (item != null || "".equals(item)) {

            dataBase.add(item);

            updateTable();

        }

    }

 

 

    public void deleteItem() {
        
        System.out.println("Sono nel deleteitem " + selection.size());
        
       // if (!selection.isEmpty()) {

            Object originalKey = uitable.getRowKey();

            for (Object selectionKey : selection) {

                uitable.setRowKey(selectionKey);

                if (uitable.isRowAvailable()) {

                    String searchedItem = (String) uitable.getRowData();
                    
                    System.out.println("trovato " + searchedItem);
                    
                    dataBase.remove(searchedItem);

                }

            }

            uitable.setRowKey(originalKey);

            uitable.getSelection().clear();

       // }

        updateTable();

    }

 

 

    public String getItem() {

        return item;

    }

 

 

    public void setItem(String item) {

        this.item = item;

    }

 

 

    public List<String> getItems() {

        return items;

    }

 

 

    public void setItems(List<String> items) {

        this.items = items;

    }

 

 

    public Collection<Object> getSelection() {

        return selection;

    }

 

 

    public void setSelection(Collection<Object> selection) {

        this.selection = selection;

    }

 

 

    public UIExtendedDataTable getUitable() {

        return uitable;

    }

 

 

    public void setUitable(UIExtendedDataTable uitable) {

        this.uitable = uitable;

    }

    /**
     * @return the selectionMode
     */
    public String getSelectionMode() {
        return selectionMode;
    }

    /**
     * @param selectionMode the selectionMode to set
     */
    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

}