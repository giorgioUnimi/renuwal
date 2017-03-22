/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package joomla;

import operativo.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *
 * @author giorgio
 */
@ManagedBean(name="recordAllevamento1")
@ViewScoped
public class RecordAllevamento1 {

    
    
    private transient boolean selected = false;

    
    private String ragioneSociale;
    
    private String desComunePresentazione;
    
    private String desAllevamento;
   
    
    private int codSpecie;
    
    private String desSpecie;
   
    
    private int codCatAllev;
    
    private String desCatAllev;
    
    
    private int codStabulazione;
    
    private String desStabulazione;
    
    
    private String numCapiSpecieStab;
    
    private double pesoVivo;
    
    private int id;
        
    private String cuaa;
    
    private List<String> listaSpecie = new LinkedList<String>();
    
    private List<String> listaCategorie = new LinkedList<String>();
    
    private List<String> listAllevamento = new LinkedList<String>();
        
    private List<String> listStabulazione = new LinkedList<String>();
    
    private UIComponent bottoneaggiungi = null;
    
    
    
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    StrutturaSpecie strutturaSpecie;
    
    
    public RecordAllevamento1()
    {
       // System.out.println(this.getClass().getCanonicalName() + "   costruttore");
        
        /**
         * recupero i dati delle specie,categorie,allevamenti,stabulazioni in 
         * un unico contenitore un unica volta ed uso il suo contenuto in tutti i record della tabella allevamenti
         */
         strutturaSpecie = StrutturaSpecie.getIstanza();
        
        
    }
    /**
     * @return the ragioneSociale
     */
    public String getRagioneSociale() {
        return ragioneSociale;
    }

    /**
     * @param ragioneSociale the ragioneSociale to set
     */
    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    /**
     * @return the desComunePresentazione
     */
    public String getDesComunePresentazione() {
        return desComunePresentazione;
    }

    /**
     * @param desComunePresentazione the desComunePresentazione to set
     */
    public void setDesComunePresentazione(String desComunePresentazione) {
        this.desComunePresentazione = desComunePresentazione;
    }

    /**
     * @return the desAllevamento
     */
    public String getDesAllevamento() {
        return desAllevamento;
    }

    /**
     * @param desAllevamento the desAllevamento to set
     */
    public void setDesAllevamento(String desAllevamento) {
        this.desAllevamento = desAllevamento;
    }

    /**
     * @return the codSpecie
     */
    public int getCodSpecie() {
        return codSpecie;
    }

    /**
     * @param codSpecie the codSpecie to set
     */
    public void setCodSpecie(int codSpecie) {
        this.codSpecie = codSpecie;
    }

    /**
     * @return the desSpecie
     */
    public String getDesSpecie() {

        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+ Thread.currentThread().getStackTrace()[1].getMethodName() + " specie " + desSpecie);

        return desSpecie;
    }

    /**
     * @param desSpecie the desSpecie to set
     */
    public void setDesSpecie(String desSpecie) {
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+ Thread.currentThread().getStackTrace()[1].getMethodName() + " specie " + desSpecie);
        this.desSpecie = desSpecie;
       
    }

    /**
     * @return the codCatAllev
     */
    public int getCodCatAllev() {
        return codCatAllev;
    }

    /**
     * @param codCatAllev the codCatAllev to set
     */
    public void setCodCatAllev(int codCatAllev) {
        this.codCatAllev = codCatAllev;
    }

    /**
     * @return the desCatAllev
     */
    public String getDesCatAllev() {
        return desCatAllev;
    }

    /**
     * @param desCatAllev the desCatAllev to set
     */
    public void setDesCatAllev(String desCatAllev) {
        this.desCatAllev = desCatAllev;
    }

    /**
     * @return the codStabulazione
     */
    public int getCodStabulazione() {
        return codStabulazione;
    }

    /**
     * @param codStabulazione the codStabulazione to set
     */
    public void setCodStabulazione(int codStabulazione) {
        this.codStabulazione = codStabulazione;
    }

    /**
     * @return the desStabulazione
     */
    public String getDesStabulazione() {
        return desStabulazione;
    }

    /**
     * @param desStabulazione the desStabulazione to set
     */
    public void setDesStabulazione(String desStabulazione) {
        this.desStabulazione = desStabulazione;
       
 
       if(!desSpecie.equals("----")&& !desCatAllev.equals("----") && !desAllevamento.equals("----") && !desStabulazione.equals("----")) {
            if (desSpecie != null && desSpecie.length() != 0) {
                if (desCatAllev != null && desCatAllev.length() != 0) {
                    if (desAllevamento != null && desAllevamento.length() != 0) {
                        if (desStabulazione != null && desStabulazione.length() != 0) {

                          /*  entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
                            entityManager = entityManagerFactory.createEntityManager();
                            jpa = (JpaEntityManager) entityManager.getDelegate();
                            serverSession = jpa.getServerSession();
                            serverSession.dontLogMessages();*/
                            
                            
                            
                         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                                {
                                   Connessione connessione = Connessione.getInstance();
                                   entityManager = connessione.apri("renuwal2");
                                   entityManagerFactory = connessione.getEntityManagerFactory();
                                }
                            
                            
                            Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",this.desSpecie);
                            db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                           // Integer cospecie = sp.getCodSpecie();
                            
                            q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", this.desCatAllev);
                            db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                            //Integer cocat = cat.getId();
                            
                            q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", this.desAllevamento);
                            db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                            //Integer coalleva = tipoalleva.getId();
                            
                            q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione", this.desStabulazione);
                            db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getResultList().get(0);
                            //Integer costabu =tipostabu.getId();

                            q = entityManager.createQuery("SELECT DISTINCT l.pesoVivoKg  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                            q.setParameter(1, sp);
                            q.setParameter(2, cat);
                            q.setParameter(3, tipoalleva);
                            q.setParameter(4, tipostabu);



                            List<Double> resu = q.getResultList();
                            
                         /*   if(resu == null || resu.isEmpty()) {
                                System.out.println("non è stata trovata la specie " + sp + " categoria " + cat + " allevamento " + tipoalleva + " stabulazione " + tipostabu);
                            }
                            else {
                                System.out.println("E stata trovata la specie " + sp + " categoria " + cat + " allevamento " + tipoalleva + " stabulazione " + tipostabu + " peso vivo " + resu.get(0));
                            }*/


                            if (resu.size() != 0) {
                               
                                this.setPesoVivo(resu.get(0));
                                //this.setId(resu.get(1));
                                
                            }

                            //entityManager.close();
                            //entityManagerFactory.close();
                            

                            Connessione.getInstance().chiudi();

                        }

                    }
                }
            }
        }

    }

    /**
     * @return the numCapiSpecieStab
     */
    public String getNumCapiSpecieStab() {
        return numCapiSpecieStab;
    }

    /**
     * @param numCapiSpecieStab the numCapiSpecieStab to set
     */
    public void setNumCapiSpecieStab(String numCapiSpecieStab) {
        this.numCapiSpecieStab = numCapiSpecieStab;
    }

    /**
     * @return the pesoVivo
     */
    public double getPesoVivo() {
        return pesoVivo;
    }

    /**
     * @param pesoVivo the pesoVivo to set
     */
    public void setPesoVivo(double pesoVivo) {
        this.pesoVivo = pesoVivo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the cuaa
     */
    public String getCuaa() {
        return cuaa;
    }

    /**
     * @param cuaa the cuaa to set
     */
    public void setCuaa(String cuaa) {
        this.cuaa = cuaa;
    }

    /**
     * @return the listaSpecie
     */
    public List<String> getListaSpecie() {
        
       
        
        /*
         if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }


       

        //Query q = entityManager.createQuery("SELECT DISTINCT l.desSpecie FROM Stabulaziones l");
        Query q = entityManager.createQuery("SELECT DISTINCT l.desSpecie FROM SpecieS l");
        List<String> results = q.getResultList();


        // for(int i = 0; i < results.size();i++)
        //     System.out.println(results.get(i));

        //List dropList = new ArrayList();
        this.listaSpecie = new ArrayList();
        SelectItem selectItemForDropList = new SelectItem();
        try {
            /*dropList.add(new SelectItem(String.valueOf("Alabama"), "Alabama"));
            dropList.add(new SelectItem(String.valueOf("Alaska"), "Alaska"));
            dropList.add(new SelectItem(String.valueOf("Arizona"), "Arizona"));*/

/*
            for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i);
                this.listaSpecie.add(new SelectItem(String.valueOf(temp), temp));
            }


        } catch (Exception e) {
//do something
        }
       
        
        */
        
       // entityManager.close();
      //  entityManagerFactory.close();
        
      //  Connessione.getInstance().chiudi();
        
       
        listaSpecie = this.strutturaSpecie.getSpecie();
               
        return listaSpecie;
    }

    /**
     * @param listaSpecie the listaSpecie to set
     */
    public void setListaSpecie(ArrayList listaSpecie) {
        this.listaSpecie = listaSpecie;
    }

    /**
     * @return the listaCategorie
     */
    public List<String> getListaCategorie() {
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
                serverSession.dontLogMessages();*/
        
        /* if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
        
        
        
                
              if(this.desSpecie  == null)
              {
                  return null;
              }
                
        System.out.println("specie recordallevamento in getlistcategorie " + this.desSpecie);

       

        //Query q = entityManager.createQuery("SELECT DISTINCT l.desCatAllev FROM Stabulaziones l WHERE l.desSpecie =?1");
        // q.setParameter(1, desSpecie);
        Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",desSpecie);
        //q.setParameter(1, desSpecie);
        //List<String> results = q.getResultList();
        db.SpecieS specie = (db.SpecieS)q.getResultList().get(0);
       List<db.CategoriaSpecie> cat =(List<db.CategoriaSpecie>)specie.getCategoriaSpecieCollection();
       ListIterator<db.CategoriaSpecie> iterCat =(ListIterator<db.CategoriaSpecie>) cat.iterator();
        
       this.listaCategorie = new ArrayList();
        
       while(iterCat.hasNext())
               {
                   db.CategoriaSpecie categoria = iterCat.next();
                   String temp = categoria.getCodiceCategoria().getDesCatAllev();
                   this.listaCategorie.add(new SelectItem(String.valueOf(temp), temp));
               }
      
     /*   this.listaCategorie = new ArrayList();
        SelectItem selectItemForDropList = new SelectItem();
        try {
           


            for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i);
                this.listaCategorie.add(new SelectItem(String.valueOf(temp), temp));
            }


        } catch (Exception e) {

        }
       
       */ 
        
        
        //entityManager.close();
      
        //Connessione.getInstance().chiudi();
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " +Thread.currentThread().getStackTrace()[1].getMethodName()+" specie: " + this.desSpecie);
        
        
        //listaCategorie = this.strutturaSpecie.getCategorie(RecordAllevamento1.getDesSpecieS());
        listaCategorie = this.strutturaSpecie.getCategorie(this.desSpecie);
        
        return listaCategorie;
    }

    /**
     * @param listaCategorie the listaCategorie to set
     */
    public void setListaCategorie(ArrayList listaCategorie) {
        this.listaCategorie = listaCategorie;
    }

    /**
     * @return the listAllevamento
     */
    public List<String> getListAllevamento() {
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        serverSession.dontLogMessages();*/
        
       /*  if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
        


       if(this.desCatAllev == null)
           return null;

       /* Query q = entityManager.createQuery("SELECT DISTINCT l.desAllevamento FROM Stabulaziones l WHERE l.desSpecie=?1 AND l.desCatAllev=?2");
         q.setParameter(1, desSpecie);
          q.setParameter(2, desCatAllev);
        List<String> results = q.getResultList();


        

       
        this.listAllevamento = new ArrayList();
        SelectItem selectItemForDropList = new SelectItem();
        try {
         


          /*  for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i);
                this.listAllevamento.add(new SelectItem(String.valueOf(temp), temp));
            }


        } catch (Exception e) {
//do something
        }
       
        
        
        
        entityManager.close();
        //entityManagerFactory.close();
        
        */
                
                
             /*    this.listAllevamento = new ArrayList();
                 Query q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", this.desCatAllev);
                 db.CategoriaS categoria =(db.CategoriaS) q.getResultList().get(0);
                 List<db.AllevamentoCategoria> allecategoria = (List<db.AllevamentoCategoria>)categoria.getAllevamentoCategoriaCollection();
                 ListIterator<db.AllevamentoCategoria> iterallecategoria = allecategoria.listIterator();
                 
                 
                 while(iterallecategoria.hasNext())
                 {
                     db.AllevamentoCategoria temp1 = iterallecategoria.next();
                     String temp = temp1.getTipoAllevamentoBId().getDesAllevamento();
                     this.listAllevamento.add(new SelectItem(String.valueOf(temp), temp));
                 }
                 
                 
                 //entityManager.close();
                 
                 Connessione.getInstance().chiudi();*/
                 
        listAllevamento = this.strutturaSpecie.getAllevamenti(desSpecie, this.desCatAllev);         
        return listAllevamento;
    }

    /**
     * @param listAllevamento the listAllevamento to set
     */
    public void setListAllevamento(ArrayList listAllevamento) {
        this.listAllevamento = listAllevamento;
    }

    /**
     * @return the listStabulazione
     */
    public List<String> getListStabulazione() {
        
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();
        serverSession = jpa.getServerSession();
        serverSession.dontLogMessages();*/
        
        
        /* if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                            }
        

       

       /* Query q = entityManager.createQuery("SELECT DISTINCT l.desStabulazione FROM Stabulaziones l WHERE l.desSpecie=?1 AND l.desCatAllev=?2 AND l.desAllevamento=?3");
        q.setParameter(1, desSpecie);
        q.setParameter(2, desCatAllev);
        q.setParameter(3, desAllevamento);

        List<String> results = q.getResultList();

        /**
         * a questo punto ho tuti le informazioni per il dettagliocuaa ovvero codice specie,categoria,stabulazione dalla query q
         * ed aggiungo questi dati nella classe dettagliocuaa
         */
        //DettaglioCuaa detto = new DettaglioCuaa();
        
        
        /**
         * sulla base del record trovato imposto il pesovivo del capo in funzione
         * della tipologia di stabulazione trovata nella tabella stabulaziones
         */
        //this.pesoVivo = 300;
        
        
     /*   db.Stabulaziones stabula = (db.Stabulaziones)q.getResultList().get(0);
        
        detto.setCodicecategoria(stabula.getCodAllevamento());
        detto.setCodicespecie(stabula.getCodSpecie());
        detto.setCodicestabulazione(stabula.getIdStabulazione());*/
        // for(int i = 0; i < results.size();i++)
        //     System.out.println(results.get(i));

        //List dropList = new ArrayList();
       /* this.listStabulazione = new ArrayList();
        SelectItem selectItemForDropList = new SelectItem();
        try {
           


            for (int i = 0; i < results.size(); i++) {
                String temp = results.get(i);
                this.listStabulazione.add(new SelectItem(String.valueOf(temp), temp));
            }


        } catch (Exception e) {
//do something
        }
       
        
        */
        
      /*  if(this.desAllevamento == null)
        {
            return null;
        }
        this.listStabulazione = new ArrayList();
        
       Query q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento",desAllevamento);
       db.TipoallevamentoS alleva =(db.TipoallevamentoS) q.getResultList().get(0);
       List<db.StabulazioneAllevamento> stabulazioni =  (List<db.StabulazioneAllevamento>)alleva.getStabulazioneAllevamentoCollection();
       ListIterator<db.StabulazioneAllevamento> iterStabulazioni = stabulazioni.listIterator();
       
       
       while(iterStabulazioni.hasNext())
       {
           db.StabulazioneAllevamento sta = iterStabulazioni.next();
           String temp = sta.getStabulazionebSId().getDesStabulazione();
           this.listStabulazione.add(new SelectItem(String.valueOf(temp), temp));
       }
       
        
        
        //entityManager.close();
       
       
       Connessione.getInstance().chiudi();
      
        */
        
      
        listStabulazione = this.strutturaSpecie.getStabulazioni(desSpecie,desCatAllev, desAllevamento);
       
        return listStabulazione;
    }

    /**
     * @param listStabulazione the listStabulazione to set
     */
    public void setListStabulazione(ArrayList listStabulazione) {
        this.listStabulazione = listStabulazione;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * eseguito dal bottone aggiungi dalla pagina allevamenti
     */
    public void aggiungi() {
       
        
        
        FacesMessage message = null;
        FacesContext context = null;
        if(this.desSpecie.equals("----") || this.desCatAllev.equals("----") || this.desAllevamento.equals("----") || this.desStabulazione.equals("----") ||
            this.desSpecie == null || this.desSpecie.isEmpty() || this.desCatAllev == null || this.desCatAllev.isEmpty() || this.desAllevamento == null || this.desAllevamento.isEmpty() || this.desStabulazione == null || this.desStabulazione.isEmpty())
        {
            message = new FacesMessage(" Inserimento non corretto ");
          
             context = FacesContext.getCurrentInstance();
             context.addMessage(getBottoneaggiungi().getClientId(context), message);
             
             System.out.println(this.getClass().getCanonicalName() + " ritorno null perchè qualche valore è nullo");
             return ;
        }
        
        if (entityManagerFactory == null || !(entityManagerFactory.isOpen())) {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
            entityManagerFactory = connessione.getEntityManagerFactory();
        }
        
        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        DettaglioCuaa dettCuaa = (DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
         
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " idscenario " + dettCuaa.getIdscenario() + " specie "  +this.desSpecie);

         /**
         * recupero il numero id dell'ultimo allevamento presente
         */
        Query q = entityManager.createNamedQuery("SpecieS.findByDesSpecie").setParameter("desSpecie",this.getDesSpecie());
                        db.SpecieS sp =(db.SpecieS)q.getSingleResult();
                       // Integer cospecie = sp.getCodSpecie();
                        
                        q = entityManager.createNamedQuery("CategoriaS.findByDesCatAllev").setParameter("desCatAllev", this.getDesCatAllev());
                        db.CategoriaS cat = (db.CategoriaS)q.getSingleResult();
                        //Integer cocat = cat.getId();
                        
                        q = entityManager.createNamedQuery("TipoallevamentoS.findByDesAllevamento").setParameter("desAllevamento", this.getDesAllevamento());
                        db.TipoallevamentoS tipoalleva =(db.TipoallevamentoS) q.getSingleResult();
                        //Integer coalleva = tipoalleva.getId();
                        
                        q = entityManager.createNamedQuery("TipostabulazioneS.findByDesStabulazione").setParameter("desStabulazione",this.getDesStabulazione());
                        db.TipostabulazioneS tipostabu =(db.TipostabulazioneS) q.getSingleResult();
                        
                         q = entityManager.createQuery("SELECT DISTINCT l  FROM SpeciecategoriaallevamentostabulazionebS l WHERE l.speciebSCodSpecie=?1 AND l.categoriabSId=?2 AND l.tipoAllevamentoBId=?3 AND l.stabulazionebSId=?4");
                        q.setParameter(1, sp);
                        q.setParameter(2, cat);
                        q.setParameter(3, tipoalleva);
                        q.setParameter(4, tipostabu);
                        
                        db.SpeciecategoriaallevamentostabulazionebS scas = (db.SpeciecategoriaallevamentostabulazionebS)q.getSingleResult();
        /**
         * cerco l'azienda che ha il cuaa specificato e da quell'azienda recupero gli allevamenti
         */
        q = entityManager.createQuery("SELECT l FROM ScenarioI l WHERE l.idscenario=?1 ");
        q.setParameter(1 , dettCuaa.getIdscenario());
        
        db.ScenarioI sc = (db.ScenarioI)q.getSingleResult();
        List<db.AllevamentoI> listaAllevamenti = (List<db.AllevamentoI>)sc.getAllevamentoICollection();
      
        /**
         * 
         * da cancellare
         */
        System.out.println(this.getClass().getCanonicalName() + " ----------------------------------addrecord  idscenario " + sc.getIdscenario());
        
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        db.AllevamentoI alleva = new db.AllevamentoI();
        alleva.setIdscenario(sc);
        alleva.setSpeciecategoriaallevamentostabulazionebId(scas);
        alleva.setNumCapiSpecieStab(Integer.parseInt(this.getNumCapiSpecieStab()));
        listaAllevamenti.add(alleva);
        entityManager.persist(alleva);
        tx.commit();
        Connessione.getInstance().chiudi();
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
