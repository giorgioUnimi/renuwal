/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import bean_entity.StoricoColturaAppezzamentoE;
import com.sun.xml.ws.client.RequestContext;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;


/**
 *
 * @author giorgio
 */
public class RecordAppezzamento {
    
    //private List<db.TipoTerreno> tipiTerreni;
   // private List<db.TipoIrrigazione> tipiIrrigazione;
    private db.TipoTerreno tipoTerreno;
    private db.TipoIrrigazione tipoIrrigazione;
    private String nome = "";
    private double superficie = 0d;
    private boolean svn = false;
    private Long id;
    
     private List<db.Coltura> listaColture;
     private db.Coltura coltura;
     private db.Resa resa;
     private double mas = 0;
     private double asp_azoto = 0;
     private double asp_fosforo = 0;
     private double asp_potassio = 0;
    /**
     * Gli apporti massimi di azoto devono essere ridotti:
     *   - di 40 kg N/ha per la coltura che segue l’aratura di un prato avvicendato di durata almeno triennale;
     *   - di 60 kg N/ha per la coltura che segue l’aratura di un medicaio di durata almeno triennale.
     * */
     private int coltura_precedente = 1;
     private List<StoricoColturaAppezzamentoE> storico = new LinkedList<StoricoColturaAppezzamentoE>();
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private db.Appezzamento appezzamento;
    /**
     * appezamaneto è il riferimento nel datatabase dell'appezzamaneto a cui fa
     * rifereimento recordappezzamaneto
     * @param appezzamento 
     */
    public RecordAppezzamento(db.Appezzamento appezzamento)
    {
      this.appezzamento = appezzamento;
      
      //popolaRotazioni();
    }
    
    public void popolaRotazioni()
    {
        
    System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" 1a ");

        
     if(appezzamento != null )
     {
        /**
         * verifico la connnesione con il database
         */
        if (entityManagerFactory == null || (!entityManagerFactory.isOpen())) {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
        }  
       /**
        * mi collego a storicocoltuappezzamenti del db e popolo i tre record SoritcoColturaAppezzamentoE 
        * con i dati del db 
        */ 
       db.Appezzamento app = entityManager.find(db.Appezzamento.class,appezzamento.getId());
       
           System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" 2a id appezzamento " + appezzamento.getId());

       /**
        * recupero lo storico del db storicolturaappezzamento
        */
       Iterator<db.StoricoColturaAppezzamento> iterSto =   app.getStoricoColturaAppezzamento().iterator();       
       
       StoricoColturaAppezzamentoE[] temp = new  StoricoColturaAppezzamentoE[3];
       
       for(int i1=0; i1 < 3;i1++) {
            temp[i1] = new StoricoColturaAppezzamentoE(appezzamento);
        }
       
       
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" 3 id appezzamento " + appezzamento.getId() + " numero storico  " +app.getStoricoColturaAppezzamento().size());

       
       int i = 0;
       while(iterSto.hasNext())
       {
           db.StoricoColturaAppezzamento temp1 = iterSto.next();
           
           temp[i].setAsportazioneAzoto(temp1.getAsportazioneAzoto());
           temp[i].setAsportazioneFosforo(temp1.getAsportazioneFosforo());
           temp[i].setAsportazionePotassio(temp1.getAsportazionePotassio());
           temp[i].setIdAppezzamento(temp1.getIdAppezzamento());
           temp[i].setIdColtura(temp1.getIdColtura().getId());
           temp[i].setMas(temp1.getMas());
           temp[i].setResa_attesa(temp1.getResa_attesa());
           temp[i].setRotazione(temp1.getRotazione());
           
           
           
         System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" rotazione " + i +"  su appezamento " + app.getNome() );
 storico.add(temp[i]);
 i++;
       }
       
       /*StoricoColturaAppezzamentoE temp1 = new StoricoColturaAppezzamentoE(appezzamento);
        StoricoColturaAppezzamentoE temp2 = new StoricoColturaAppezzamentoE(appezzamento);
         StoricoColturaAppezzamentoE temp3 = new StoricoColturaAppezzamentoE(appezzamento);*/
      /*  storico.add(temp[0]);
        storico.add(temp[1]);
        storico.add(temp[2]);*/
        
        
       
           
        
        Connessione.getInstance().chiudi();
     } 
    }
  

    /**
     * @return the tipiIrrigazione
     */
    /*public List<db.TipoIrrigazione> getTipiIrrigazione() {
        return tipiIrrigazione;
    }*/

    /**
     * @param tipiIrrigazione the tipiIrrigazione to set
     */
   /* public void setTipiIrrigazione(List<db.TipoIrrigazione> tipiIrrigazione) {
        this.tipiIrrigazione = tipiIrrigazione;
    }*/

    /**
     * @return the tipoTerreno
     */
    public db.TipoTerreno getTipoTerreno() {
        return tipoTerreno;
    }

    /**
     * @param tipoTerreno the tipoTerreno to set
     */
    public void setTipoTerreno(db.TipoTerreno tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    /**
     * @return the tipoIrrigazione
     */
    public db.TipoIrrigazione getTipoIrrigazione() {
        return tipoIrrigazione;
    }

    /**
     * @param tipoIrrigazione the tipoIrrigazione to set
     */
    public void setTipoIrrigazione(db.TipoIrrigazione tipoIrrigazione) {
        this.tipoIrrigazione = tipoIrrigazione;
    }

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
     * @return the superficie
     */
    public double getSuperficie() {
        return superficie;
    }

    /**
     * @param superficie the superficie to set
     */
    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    /**
     * @return the svn
     */
    public boolean isSvn() {
     //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" svn " + this.svn);

        return svn;
    }

    /**
     * @param svn the svn to set
     */
    public void setSvn(boolean svn) {
        this.svn = svn;
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" svn " + this.svn );
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         } 
        
         
         if (this.appezzamento != null) {
            db.Appezzamento modifyAppezzamento = entityManager.find(db.Appezzamento.class, this.appezzamento.getId());

            entityManager.getTransaction().begin();

            modifyAppezzamento.setSvz(svn);

            entityManager.getTransaction().commit();
        }

    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the coltura
     */
    public db.Coltura getColtura() {
        return coltura;
    }

    /**
     * @param coltura the coltura to set
     */
    public void setColtura(db.Coltura coltura) {
        this.coltura = coltura;
    }

    /**
     * @return the resa
     */
    public db.Resa getResa() {
        return resa;
    }

    /**
     * @param resa the resa to set
     */
    public void setResa(db.Resa resa) {
        this.resa = resa;
    }

    /**
     * @return the mas
     */
    public double getMas() {
        return mas;
    }

    /**
     * @param mas the mas to set
     */
    public void setMas(double mas) {
        this.mas = mas;
    }

    /**
     * @return the asp_azoto
     */
    public double getAsp_azoto() {
        this.asp_azoto = 0;
        this.asp_fosforo = 0;
         this.asp_potassio = 0;
        Iterator<StoricoColturaAppezzamentoE> it = this.storico.iterator();
        while(it.hasNext())
        {
            StoricoColturaAppezzamentoE storicoE = it.next();
            this.asp_azoto = this.asp_azoto + storicoE.getAsportazioneAzoto();
            
         //   System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " asp azoto appezamento" + this.asp_azoto +" storico " + storicoE.getAsportazioneAzoto() + " coltura precedente " + this.getColtura_precedente());
            this.asp_fosforo = this.asp_fosforo +  storicoE.getAsportazioneFosforo();
            this.asp_potassio = this.asp_potassio + storicoE.getAsportazionePotassio();          
            
            
        }
        
      
        
        
        return asp_azoto;
    }

    /**
     * @param asp_azoto the asp_azoto to set
     */
    public void setAsp_azoto(double asp_azoto) {
        this.asp_azoto = asp_azoto;
    }

    /**
     * @return the asp_fosforo
     */
    public double getAsp_fosforo() {
        return asp_fosforo;
    }

    /**
     * @param asp_fosforo the asp_fosforo to set
     */
    public void setAsp_fosforo(double asp_fosforo) {
        this.asp_fosforo = asp_fosforo;
    }

    /**
     * @return the asp_potassio
     */
    public double getAsp_potassio() {
        return asp_potassio;
    }

    /**
     * @param asp_potassio the asp_potassio to set
     */
    public void setAsp_potassio(double asp_potassio) {
        this.asp_potassio = asp_potassio;
    }

    /**
     * @return the listaColture
     */
    public List<db.Coltura> getListaColture() {
        return listaColture;
    }

    /**
     * @param listaColture the listaColture to set
     */
    public void setListaColture(List<db.Coltura> listaColture) {
        this.listaColture = listaColture;
    }

   

    /**
     * @return the storico
     */
    public List<StoricoColturaAppezzamentoE> getStorico() {
        return storico;
    }

    /**
     * @param storico the storico to set
     */
    public void setStorico(List<StoricoColturaAppezzamentoE> storico) {
        this.storico = storico;
    }

    /**
     * @return the coltura_precedente
     */
    public int getColtura_precedente() {
        
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" coltura precedente " + this.coltura_precedente);
        
        return coltura_precedente;
    }

    /**
     * @param coltura_precedente the coltura_precedente to set
     */
    public void setColtura_precedente(int coltura_precedente) {
        
        this.coltura_precedente = coltura_precedente;
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" coltura precedente " + this.coltura_precedente);
        
        
      
       // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" coltura precedente " + this.coltura_precedente );

        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal1");
         } 
        
         if (this.appezzamento != null) {
            db.ColturaPrecedente colturaPrecedente = entityManager.find(db.ColturaPrecedente.class, coltura_precedente);
            db.Appezzamento modifyAppezzamento = entityManager.find(db.Appezzamento.class, this.appezzamento.getId());

            entityManager.getTransaction().begin();

            modifyAppezzamento.setColturaPrecedente(colturaPrecedente);

            entityManager.getTransaction().commit();
            
           
            // popolaRotazioni();
        }
         
         
          Connessione.getInstance().chiudi();
        
    }
    
}
