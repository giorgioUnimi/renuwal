/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joomla;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author giorgio
 */
public class MioTipo {
    
    private String nome = "";
    
    private List<MioTipo> lista = new LinkedList<MioTipo>();

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the lista
     */
    public List<MioTipo> getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(List<MioTipo> lista) {
        this.lista = lista;
    }
    
}
