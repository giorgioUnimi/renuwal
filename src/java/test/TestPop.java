/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="testPop")
@RequestScoped
public class TestPop {
    private int index;
    
    private String name;
    private double valore;
    /**
     * Creates a new instance of TestPop
     */
    public TestPop() {
    }
    
    
    
    public void addRecord()
    {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " nome " + this.name + " valore " + this.valore);
    }
    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the valore
     */
    public double getValore() {
        return valore;
    }

    /**
     * @param valore the valore to set
     */
    public void setValore(double valore) {
        this.valore = valore;
    }
}
