/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ager;

/**
 *
 * @author giorgio
 */
public class TipiReflui {
    
    private String[] tipologieDaAllevamento = {"Liquame Bovino","Liquame Suino","Liquame Avicolo","Liquame Altro","Liquame Biomassa","Letame Bovino","Letame Suino","Letame Avicolo","Letame Altro","Letame Biomassa"};
    private String[] tipologieDigestato = {"Liquame Digestato da Bovino" ,"Liquame Digestato da Suino" ,"Liquame Digestato da Avicolo" ,"Liquame Digestato da Biomasse" ,"Letame Digestato da Bovino" ,"Letame Digestato da Suino" ,"Letame Digestato da Avicolo" ,"Letame Digestato da Biomasse"};
    private String[] tipologieChiarificato = {"Digestato frazione chiarificata","Letame"};

    
    
    private static TipiReflui instance = null;
    protected TipiReflui(){
        
    }
    
    public static TipiReflui getInstance(){
        if(instance == null) {
         instance = new TipiReflui();
      }
      return instance;
    }

    /**
     * @return the tipologieDaAllevamento
     */
    public String[] getTipologieDaAllevamento() {
        return tipologieDaAllevamento;
    }

    /**
     * @param tipologieDaAllevamento the tipologieDaAllevamento to set
     */
    public void setTipologieDaAllevamento(String[] tipologieDaAllevamento) {
        this.tipologieDaAllevamento = tipologieDaAllevamento;
    }

    /**
     * @return the tipologieDigestato
     */
    public String[] getTipologieDigestato() {
        return tipologieDigestato;
    }

    /**
     * @param tipologieDigestato the tipologieDigestato to set
     */
    public void setTipologieDigestato(String[] tipologieDigestato) {
        this.tipologieDigestato = tipologieDigestato;
    }

    /**
     * @return the tipologieChiarificato
     */
    public String[] getTipologieChiarificato() {
        return tipologieChiarificato;
    }

    /**
     * @param tipologieChiarificato the tipologieChiarificato to set
     */
    public void setTipologieChiarificato(String[] tipologieChiarificato) {
        this.tipologieChiarificato = tipologieChiarificato;
    }
    
}
