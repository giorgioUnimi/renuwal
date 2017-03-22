/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operativo;

import alfam.Alfam;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/**
 *ha lo scopo di rappresentare i valori di una distribuzione di refluo 
 * su un determinato appezzamento 
 * @author giorgio
 */
public class Distribuzione {
    
    
    //metri cubi
    private double quantita_distribuita = 0;
    //metri cubi su ettaro
    private double quantita_distribuita_mcubi = 0;
    private double efficienza = 0;
    private double efficienza_alcampo = 0;
    //superficie della somma di appezzamenti che hanno la setssa coltura nella stessa rotazione
    private double superficie ;
    /**
     * i singoli valori dei menu a tendina corrispondenti sotto
     */
    private int tipoRefluo = 0;
    private int tipoMese  = 0;
    private int tipoTecnica  = 0;
    private int tipoModalita  = 0; 
    /**
     * le liste dei menui a tendina
     */
    private List<SelectItem> tipiReflui = new LinkedList<SelectItem>();
    private List<SelectItem> listaMesi = new LinkedList<SelectItem>();
    private List<SelectItem> tipiTecniche = new LinkedList<SelectItem>();
    private List<SelectItem> tipiModalita =new LinkedList<SelectItem>();

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    private DettaglioCuaa detto = null;
    private db.RisultatoConfronto risultatoConfronto = null;
    private db.Coltura coltura = null;
    
    private double perditaInAria = 0;
    private double perditaInAcqua = 0;
    //kg di azoto di letame / metri cubi di superficie
    private double indiceLetame = 0;
     //kg di azoto di liquame / metri cubi di superficie
    private double indiceLiquame = 0;
    private double dose = 0;
    private double doseha = 0;
    //mi informa se è letame o liquame
    private boolean liquame = false;
    //epoca 
    private db.Epoca epoca = null;
    
     public Distribuzione(db.RisultatoConfronto risultatoConfronto,db.Coltura coltura){
         
         
         this.resetQuantita();
         
         this.risultatoConfronto = risultatoConfronto;
         
         //this.calcolaIndici();
              
         this.coltura = coltura;
         //inserisco le etichette nel menu a tendina refluo distribuito del piano 
         //di concimazione
         popolaListaTipiReflui();
         //inserisce i tipi di epoche nel menu a tendinda delle epoche 
         //della pagina delle distribuzioni
        // popolaListaTipiMesi();
         //inserisco i tipi di tecniche nel menu a tendina delle liste di tecniche
         //popolaListaTecniche();
         
         //inserisco i tipi di modalita di distribuzione nel menu a tendina 
         //popolaModalita();
     }
    /**
     * calcola l'indice del azoto in funzione del liquame e del letame
     * viene chiamato titolo nei documenti word di alberto e flavio
     */
     private double calcolaIndici(int id_refluo,int tipo_refluo)
     {
         double tot_liquame = this.risultatoConfronto.getM3LAlt() + this.risultatoConfronto.getM3LAvi() + this.risultatoConfronto.getM3LBio() + this.risultatoConfronto.getM3LBov() + this.risultatoConfronto.getM3LSui();
         double tot_letame = this.risultatoConfronto.getM3PAlt() + this.risultatoConfronto.getM3PAvi() + this.risultatoConfronto.getM3PBio() + this.risultatoConfronto.getM3PBov() + this.risultatoConfronto.getM3PSui();
         double tot_azoto_liquame = this.risultatoConfronto.getTknLAlt() + this.risultatoConfronto.getTknLAvi() + this.risultatoConfronto.getTknLBio()+this.risultatoConfronto.getTknLBov()+ this.risultatoConfronto.getTknLSui();
         double tot_azoto_letame = this.risultatoConfronto.getTknPAlt() + this.risultatoConfronto.getTknPAvi() + this.risultatoConfronto.getTknPBio()+this.risultatoConfronto.getTknPBov()+ this.risultatoConfronto.getTknPSui();
       
         
         this.indiceLetame = tot_azoto_letame / tot_letame;
         this.indiceLiquame = tot_azoto_liquame / tot_liquame;
             //liquami
         
         double titolo = 0;
         
        if(tipo_refluo == 1 ||tipo_refluo == 3 ||tipo_refluo == 4 ||tipo_refluo == 5 )
        {
            switch(id_refluo)
            {
                case 1://bovino
                    titolo = this.risultatoConfronto.getTknLBov() / this.risultatoConfronto.getM3LBov();
                  
                    break;
                 case 2://suino
                    titolo = this.risultatoConfronto.getTknLSui() / this.risultatoConfronto.getM3LSui();
                   
                    break;   
                 case 3://avicolo
                    titolo = this.risultatoConfronto.getTknLAvi() / this.risultatoConfronto.getM3LAvi();
                   
                    break;
                 case 4://altro
                     titolo = this.risultatoConfronto.getTknLAlt() / this.risultatoConfronto.getM3LAlt();
                   
                    break;
                 case 9://biomasse
                    titolo = this.risultatoConfronto.getTknLBio() / this.risultatoConfronto.getM3LBio();
                 
                    break;    
            }
        }
        //letami
        if(tipo_refluo == 2 ||tipo_refluo == 6 ||tipo_refluo == 7 )
        {
         switch(id_refluo)
            {
               case 1://bovino
                    titolo = this.risultatoConfronto.getTknPBov() / this.risultatoConfronto.getM3PBov();
                   
                    break;
                 case 2://suino
                    titolo = this.risultatoConfronto.getTknPSui() / this.risultatoConfronto.getM3PSui();
                   
                    break;   
                 case 3://avicolo
                    titolo = this.risultatoConfronto.getTknPAvi() / this.risultatoConfronto.getM3PAvi();
                 
                    break;
                 case 4://altro
                     titolo = this.risultatoConfronto.getTknPAlt() / this.risultatoConfronto.getM3PAlt();
                 
                    break;
                 case 9://biomasse
                    titolo = this.risultatoConfronto.getTknPBio() / this.risultatoConfronto.getM3PBio();
                  
                    break;    
            }
            
        }  
         
         
         return titolo;
         
         
         
     }
     /**
      * ad ogni cambiamento di una voce di uno dei menu a tendina 
      * devi mettere a zero tutti i campi che dipendono da questo
      */
     private void resetQuantita()
     {
         this.quantita_distribuita = 0;
         this.quantita_distribuita_mcubi = 0;
         this.dose = 0;
         this.doseha = 0;
         this.efficienza_alcampo = 0;
         this.efficienza = 0;
     }
     
     private void popolaModalita()
     {
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
            
          Query q = entityManager.createNamedQuery("Modalitadistribuzione.findAll");
          List<db.Modalitadistribuzione> tipiModalita =(List<db.Modalitadistribuzione>) q.getResultList();
          
          SelectItem ite = null;
          
          ListIterator<db.Modalitadistribuzione> iterModalita =tipiModalita.listIterator();
            
          while(iterModalita.hasNext())
          {
              db.Modalitadistribuzione temp = iterModalita.next();
              ite = new SelectItem();
              ite.setLabel(temp.getDescrizione());
              ite.setValue(temp.getId());
              
              this.getTipiModalita().add(ite);
          }
          
          Connessione.getInstance().chiudi();    
     }
     
     
    private void popolaListaTecniche()
    {
        this.resetQuantita();
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
            
          Query q = entityManager.createNamedQuery("Tecnicadistribuzione.findAll");
          List<db.Tecnicadistribuzione> tipiDiTecniche =(List<db.Tecnicadistribuzione>) q.getResultList();
          
          SelectItem ite = null;
          
          ListIterator<db.Tecnicadistribuzione> iterTecniche =tipiDiTecniche.listIterator();
            
          while(iterTecniche.hasNext())
          {
              db.Tecnicadistribuzione temp = iterTecniche.next();
              ite = new SelectItem();
              ite.setLabel(temp.getDescrizione());
              ite.setValue(temp.getId());
              
              this.tipiTecniche.add(ite);
          }
          
          Connessione.getInstance().chiudi();    
    }
     
     
     
    private void popolaListaTipiMesi()
    {
        
        this.resetQuantita();
        
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
            
          Query q = entityManager.createNamedQuery("Mese.findAll");
          List<db.Mese> tipiDiMesi =(List<db.Mese>) q.getResultList();
          
          SelectItem ite = null;
          
          ListIterator<db.Mese> iterMesi =tipiDiMesi.listIterator();
            
          while(iterMesi.hasNext())
          {
              db.Mese temp = iterMesi.next();
              ite = new SelectItem();
              ite.setLabel(temp.getDescrizione());
              ite.setValue(temp.getId());
              
              this.getListaMesi().add(ite);
              
          }
          
          Connessione.getInstance().chiudi();    
    }
    
     private void popolaListaTipiReflui()
     {
         
         this.resetQuantita();
         
           SelectItem itemLiquame = new SelectItem();
           SelectItem itemLetame  = new SelectItem();
          
           //allevamento 
           itemLiquame.setLabel(this.risultatoConfronto.getIdAlternativa().getIdTipoFormaRefluoLiquido().getDescrizione());
           itemLiquame.setValue(this.risultatoConfronto.getIdAlternativa().getIdTipoFormaRefluoLiquido().getId());
           itemLetame.setLabel(this.risultatoConfronto.getIdAlternativa().getIdTipoFormaRefluoPalabile().getDescrizione());
           itemLetame.setValue(this.risultatoConfronto.getIdAlternativa().getIdTipoFormaRefluoPalabile().getId()); 
           
            this.tipiReflui.add(itemLetame);
            this.tipiReflui.add(itemLiquame);
            
            
     }
     
     
   /* public Distribuzione()
    {
       ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        detto = ( DettaglioCuaa) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "dettaglioCuaa");
          
        
     
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
         
       Query q = entityManager.createNamedQuery("RisultatoConfronto.findByIdScenario").setParameter("idScenario", (long)detto.getScenario());
       db.RisultatoConfronto risu = null;
       
       if(q.getResultList().isEmpty()) {
            return;
        }
       else {
            risu =(db.RisultatoConfronto)q.getSingleResult();
        }
       
       popolaItemRefluo(risu.getIdAlternativa());
       
       
       
    }*/
    
    /*private void popolaItemRefluo(db.AlternativeS alt)
    {
        SelectItem[] test = new SelectItem[8];
        
        if(alt.getId() == 0 || alt.getId() == 1 || alt.getId() == 4 || alt.getId() == 5 || alt.getId() == 12 || alt.getId() == 13 || alt.getId() == 15)
        {
            test[0] = new SelectItem();
            test[1] = new SelectItem();
            test[2] = new SelectItem();
            test[3] = new SelectItem();
            test[4] = new SelectItem();
            test[5] = new SelectItem();
            //allevamento 
            test[0].setLabel("Liquame Bovino");
            test[0].setValue(new Integer(1));
            test[1].setLabel("Liquame Suino");
            test[1].setValue(new Integer(2));
            test[2].setLabel("Liquame Avicolo");
            test[2].setValue(new Integer(3));
            
            test[3].setLabel("Letame Bovino");
            test[3].setValue(new Integer(4));
            test[4].setLabel("Letame Suino");
            test[4].setValue(new Integer(5));
            test[5].setLabel("Letame Avicolo");
            test[5].setValue(new Integer(6));
            
            this.tipiReflui.add(test[0]);
            this.tipiReflui.add(test[1]);
            this.tipiReflui.add(test[2]);
            this.tipiReflui.add(test[3]);
            this.tipiReflui.add(test[4]);
            this.tipiReflui.add(test[5]);
        }
        
        if(alt.getId() == 2 || alt.getId() == 8 || alt.getId() == 11 || alt.getId() == 14)
        {
            test[0] = new SelectItem();
            test[1] = new SelectItem();
            test[2] = new SelectItem();
            test[3] = new SelectItem();
            test[4] = new SelectItem();
            test[5] = new SelectItem();
            test[6] = new SelectItem();
            test[7] = new SelectItem();
            //digestato
            test[0].setLabel("Liquame Digestato da Bovino");
            test[0].setValue(new Integer(7));
            test[1].setLabel("Liquame Digestato da Suino");
            test[1].setValue(new Integer(8));
            test[2].setLabel("Liquame Digestato da Avicolo");
            test[2].setValue(new Integer(9));
            test[3].setLabel("Liquame Digestato da Biomassa");
            test[3].setValue(new Integer(10));
            
            test[4].setLabel("Letame Digestato da Bovino");
            test[4].setValue(new Integer(11));
            test[5].setLabel("Letame Digestato da Suino");
            test[5].setValue(new Integer(12));
            test[6].setLabel("Letame Digestato da Avicolo");
            test[6].setValue(new Integer(13));
            test[7].setLabel("Letame Digestato da Avicolo");
            test[7].setValue(new Integer(14));
            
            this.tipiReflui.add(test[0]);
            this.tipiReflui.add(test[1]);
            this.tipiReflui.add(test[2]);
            this.tipiReflui.add(test[3]);
            this.tipiReflui.add(test[4]);
            this.tipiReflui.add(test[5]);
            this.tipiReflui.add(test[6]);
            this.tipiReflui.add(test[7]);
            
            
        }
        
        
        if(alt.getId() == 3 || alt.getId() == 6 || alt.getId() == 7 || alt.getId() == 9 || alt.getId() == 10 || alt.getId() == 16 )
        {
            test[0] = new SelectItem(); 
            test[1] = new SelectItem(); 
            
            //digestato chiarificato
            test[0].setLabel("Digestato frazione chiarificata");
            test[0].setValue(new Integer(15));
           
            test[1].setLabel("Letame");
            test[1].setValue(new Integer(16));
            
            this.tipiReflui.add(test[0]);
            this.tipiReflui.add(test[1]);
            
        }
        
        
    }*/
    
    
   

    /**
     * @return the tipiReflui
     */
    public List<SelectItem> getTipiReflui() {
        return tipiReflui;
    }

    /**
     * @param tipiReflui the tipiReflui to set
     */
    public void setTipiReflui(List<SelectItem> tipiReflui) {
        this.tipiReflui = tipiReflui;
    }

   

    /**
     * @return the tipiTecniche
     */
    public List<SelectItem> getTipiTecniche() {
        
        this.resetQuantita();
        this.tipiModalita.clear();
        if(this.tipoRefluo == 0 || this.tipoMese == 0) {
            return null;
        }
        
        this.tipiTecniche.clear();        
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
          /**
           * devo filtrare i mesi in funzione del primo parametro che l'utente 
           * puo scegliere ovvero il tipo di refluo o meglio se è palabile o liquido
           */
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
          
          Query q2 = entityManager.createNamedQuery("Mese.findById").setParameter("id",this.tipoMese);
          db.Mese meseT = (db.Mese)q2.getSingleResult();
                           
          /**
           * recupero le epoche dalla TabellaEfficienzaColturaModalitaTecnica perchè ragiona in epoche
           * e poi recupero dalle epoche i mesi che compongono le epoche
           */
          Query q = entityManager.createQuery("Select distinct a.idTecnica FROM TabellaEfficienzaColturaModalitaTecnica a WHERE a.idFormaRefluo=?1 and a.idGruppoColturale=?2 and a.idEpoca=?3");
          q.setParameter(1,tipoformarefluo.getIdForma());
          q.setParameter(2, this.coltura.getIdGruppoColturale());
          q.setParameter(3, meseT.getIdEpoca());
          
          List<db.Tecnicadistribuzione> listTecnica = q.getResultList();
          
          ListIterator<db.Tecnicadistribuzione> iterTecnica = listTecnica.listIterator();
          
          SelectItem ite = null;
          
          while(iterTecnica.hasNext())
          {
              db.Tecnicadistribuzione temp = iterTecnica.next();
               ite = new SelectItem();
                  ite.setLabel(temp.getDescrizione());
                  ite.setValue(temp.getId());

                  this.tipiTecniche.add(ite);
              
          }
        
          
        Connessione.getInstance();  
          
        return tipiTecniche;
    }

    /**
     * @param tipiTecniche the tipiTecniche to set
     */
    public void setTipiTecniche(List<SelectItem> tipiTecniche) {
        this.tipiTecniche = tipiTecniche;
    }

    /**
     * @return the tipiModalita
     */
    public List<SelectItem> getTipiModalita() {
        
        
        this.resetQuantita();
        
          if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0) {
            return null;
        }
        
        this.tipiModalita.clear();        
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
          /**
           * devo filtrare i mesi in funzione del primo parametro che l'utente 
           * puo scegliere ovvero il tipo di refluo o meglio se è palabile o liquido
           */
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
          
          //imposto per tutta la classe  se è liquame o letame 
          if(tipoformarefluo.getIdForma().getId() == 1) {
            this.setLiquame(true);
           }
          else {
            this.setLiquame(false);
          }          
          
          //
          Query q2 = entityManager.createNamedQuery("Mese.findById").setParameter("id",this.tipoMese);
          db.Mese meseT = (db.Mese)q2.getSingleResult();
          
          
          //imposto l'epoca per tutta la classe
          this.setEpoca(meseT.getIdEpoca());
          
          Query q3 = entityManager.createNamedQuery("Tecnicadistribuzione.findById").setParameter("id",this.tipoTecnica);
          db.Tecnicadistribuzione tecnica = (db.Tecnicadistribuzione)q3.getSingleResult();
                           
          /**
           * recupero le epoche dalla TabellaEfficienzaColturaModalitaTecnica perchè ragiona in epoche
           * e poi recupero dalle epoche i mesi che compongono le epoche
           */
          Query q = entityManager.createQuery("Select distinct a.idModalita FROM TabellaEfficienzaColturaModalitaTecnica a WHERE a.idFormaRefluo=?1 and a.idGruppoColturale=?2 and a.idEpoca=?3 and a.idTecnica=?4");
          q.setParameter(1,tipoformarefluo.getIdForma());
          q.setParameter(2, this.coltura.getIdGruppoColturale());
          q.setParameter(3, meseT.getIdEpoca());
          q.setParameter(4, tecnica);
          
          List<db.Modalitadistribuzione> listModalita = q.getResultList();
          
          ListIterator<db.Modalitadistribuzione> iterModalita = listModalita.listIterator();
          
          SelectItem ite = null;
          
          while(iterModalita.hasNext())
          {
              db.Modalitadistribuzione temp = iterModalita.next();
               ite = new SelectItem();
                  ite.setLabel(temp.getDescrizione());
                  ite.setValue(temp.getId());

                  this.tipiModalita.add(ite);
                  
                  
                  //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() +" modalita trovata  " + temp.getDescrizione());
              
          }
        
        
        
        
        Connessione.getInstance().chiudi();
        
        
        return tipiModalita;
    }

    /**
     * @param tipiModalita the tipiModalita to set
     */
    public void setTipiModalita(List<SelectItem> tipiModalita) {
                      // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);

        this.tipiModalita = tipiModalita;
    }

    /**
     * @return the quantita_distribuita
     */
    public double getQuantita_distribuita() {
        
        
        //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);

        
        if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0 || this.tipoModalita ==0) {
            return 0d;
        }
        
        //if(this.getQuantita_distribuita_mcubi() != 0)
        //{
            quantita_distribuita = this.getQuantita_distribuita_mcubi() * this.getSuperficie();
        //}
                     //  System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);

        return quantita_distribuita;
    }

    /**
     * @param quantita_distribuita the quantita_distribuita to set
     */
    public void setQuantita_distribuita(double quantita_distribuita) {
        this.quantita_distribuita = quantita_distribuita;
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);

    }

    /**
     * @return the quantita_distribuita_mcubi
     */
     public double getQuantita_distribuita_mcubi() {
        if(this.quantita_distribuita != 0) {
            this.quantita_distribuita_mcubi = this.quantita_distribuita / this.superficie;
            
           // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " this.quantita_distribuita_mcubi " + this.quantita_distribuita_mcubi + " this.quantita_distribuita " + this.quantita_distribuita + " this.superficie " +this.superficie);
        }
        return quantita_distribuita_mcubi;
    }

    /**
     * @param quantita_distribuita_mcubi the quantita_distribuita_mcubi to set
     */
    public void setQuantita_distribuita_mcubi(double quantita_distribuita_mcubi) {
        this.quantita_distribuita_mcubi = quantita_distribuita_mcubi;
        this.setQuantita_distribuita(this.quantita_distribuita_mcubi * this.superficie);
    }
    
    /**
     * identifica dose è dipendente anche dal tipo di tiporefluo cioè se il refluo 
     * è digestato tal quale,separato liquido da digestato o separato solido da digestato i casi 
     * sono <125 e > 125 negli altri invece sono <125 ,125-250 e >250
     * @param valoreDose
     * @param tipoformarefluo
     * @return 
     */
    private int identificaDose(double valoreDose,int tipoformarefluo)
    {
     int ritorno = 0;
     
     if(tipoformarefluo == 1 || tipoformarefluo == 4 || tipoformarefluo == 6  )
     {
        if(valoreDose < 125) {
            return 1;
        }
        
        if(valoreDose > 125) {
            return 4;
        }
     }else
     {
        if(valoreDose < 125) {
            return 1;
        } 
         
        
        if(valoreDose < 250 && valoreDose > 124) {
            return 2;
        }
           
        if(valoreDose > 250) {
            return 3;
        }       
        
        
     }
        return ritorno;
    }
    /**
     * resti una quantita proporzionata di refluo in funzione della quantita nella specie animale
     * @param specieAnimale bovino(1),suino(2),avicolo(3),altro(4),biomasse(9)
     * @param tiporefluo {digestato tal quale(1),liquame tal quale(3),separato liquido da digestato(4),separato solido da liquame(5): sono liquami}
     * {letame tal quale(2),separato solido da digestato(6),separato liquido da liquame(7) sono letami}
     * @param quantitaDistribuita quella inserita dall'utente che deve essere rapportata alla quantita che deriva dalla distribuzione
     * @return la porzione di refluo liquido o palabile proporzionato alla specie animale in funzione delle quantia di refluo 
     * presenti nelle caratteristichechimiche provenienti dal confronto
     */
    private double rapportoDistribuita(int specieAnimale,int tiporefluo,double quantitaDistribuita,double sommaLiquame,double sommaLetame)
    {
        double coeff_rapporto = 0;
        //liquami
        if(tiporefluo == 1 ||tiporefluo == 3 ||tiporefluo == 4 ||tiporefluo == 5 )
        {
            switch(specieAnimale)
            {
                case 1://bovino
                    coeff_rapporto = this.risultatoConfronto.getM3LBov()/sommaLiquame;
                    break;
                 case 2://suino
                     coeff_rapporto = this.risultatoConfronto.getM3LSui()/sommaLiquame;
                    break;   
                 case 3://avicolo
                     coeff_rapporto = this.risultatoConfronto.getM3LAvi()/sommaLiquame;
                    break;
                 case 4://altro
                     coeff_rapporto = this.risultatoConfronto.getM3LAlt()/sommaLiquame;
                    break;
                 case 9://biomasse
                     coeff_rapporto = this.risultatoConfronto.getM3LBio()/sommaLiquame;
                    break;    
            }
        }
        //letami
        if(tiporefluo == 2 ||tiporefluo == 6 ||tiporefluo == 7 )
        {
         switch(specieAnimale)
            {
                case 1://bovino
                    coeff_rapporto = this.risultatoConfronto.getM3PBov()/sommaLetame;
                    break;
                 case 2://suino
                     coeff_rapporto = this.risultatoConfronto.getM3PSui()/sommaLetame;
                    break;   
                 case 3://avicolo
                     coeff_rapporto = this.risultatoConfronto.getM3PAvi()/sommaLetame;
                    break;
                 case 4://altro
                     coeff_rapporto = this.risultatoConfronto.getM3PAlt()/sommaLetame;
                    break;
                 case 9://biomasse
                     coeff_rapporto = this.risultatoConfronto.getM3PBio()/sommaLetame;
                    break;    
            }
            
        }       
        
        return quantitaDistribuita * coeff_rapporto;
    }
    /**
     * Calcolo la media ponderata dell'efficienza prendendo i valori delle efficienze da 
     * TabellaEfficienzaRefluoDose e TabellaEfficienzaColturaModalitaTecnica . Per fare la media ponderata
     * per l'azoto mi servono i dati dell'azoto totale che mi sono stati calcolati dal solutore
     * e che sono presenti nella variabile db.RisultatoConfronto risultatoConfronto
     * @return the efficienza
     */
    public double getEfficienza() {
        
        //double ritorno = 0d;
        
        //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);
   
        /**
         * quando viene caricata la pagina questi parametri sono tutti a zero
         */
        if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0 || this.tipoModalita == 0 ) {
            return 0d;
        }
        
        /**
         * devo suddividere la quantita distribuita sulle diverse specie animali
         * devo capire se è liquame o letame
         */
        double sommaliquame = risultatoConfronto.getM3LBov() + risultatoConfronto.getM3LSui() + risultatoConfronto.getM3LAvi() + risultatoConfronto.getM3LAlt() + risultatoConfronto.getM3LBio();
        double sommaletame = risultatoConfronto.getM3PBov() + risultatoConfronto.getM3PSui() + risultatoConfronto.getM3PAvi() + risultatoConfronto.getM3PAlt() + risultatoConfronto.getM3PBio();
        

        
        /**
         * specie 1 bovino,2suino,3avicolo,4altro,9 biomasse
         */
        int[] specieAnimale = {1,2,3,4,9};
       
         /**
         * io ho un unica quantita distribuita che però va rapportata con le quantita
         * delle caratteristiche chimiche presenti nel confronto. Per cui devo ciclare per ogni tipologia animale
         * rapportando la quantita distribuita con la quantita della specie animale
         */
        
        //double n_tot_kg_m3 = 0;
        double n_tot_kg_ha = 0;
        double n_perso_aria_kg_ha = 0;
        double n_perso_aria_percento = 0;
        double n_eff = 0;
        double n_perso = 0;
        double n_perso_acqua_kg_ha = 0;
        double n_perso_acqua_percento = 0;
        double quantita = 0;
        Alfam alfam = new Alfam();
        for(int i=0;i< specieAnimale.length;i++)
        {
            quantita = rapportoDistribuita(specieAnimale[i], tipoRefluo,quantita_distribuita,sommaliquame,sommaletame);
            n_tot_kg_ha = quantita * this.calcolaIndici(specieAnimale[i], tipoRefluo);
            
            //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " m3 p bov  " +risultatoConfronto.getM3PBov() + " m3 l bov " + risultatoConfronto.getM3LBov());

            
             alfam.parametriAlfam(1, risultatoConfronto, this.getEpoca(), quantita, specieAnimale[i], tipoRefluo);
             n_perso_aria_kg_ha += alfam.get_Lost_Tan_Tot_kg();
             n_perso_aria_percento += n_perso_aria_kg_ha / (n_tot_kg_ha * 100);
             double coefficienteN = this.getEfficienzaDB((int)quantita, false, 1 , specieAnimale[i]);
             n_eff = n_tot_kg_ha * coefficienteN;
             n_perso = n_tot_kg_ha - n_eff;
             n_perso_acqua_kg_ha += n_perso - n_perso_aria_kg_ha;
             n_perso_acqua_percento += n_perso_acqua_kg_ha /(n_tot_kg_ha * 100);
             
             if(this.tipoTecnica != 1)
             {
                 double fattore_lisciviazione = n_perso_acqua_kg_ha / (n_tot_kg_ha - n_perso_aria_kg_ha);
                  alfam.parametriAlfam(1, risultatoConfronto, this.getEpoca(), quantita, specieAnimale[i], tipoRefluo);
                  n_perso_aria_kg_ha += alfam.get_Lost_Tan_Tot_kg();
                  n_perso_aria_percento += n_perso_aria_kg_ha / (n_tot_kg_ha * 100);
                  n_perso_acqua_kg_ha = (n_tot_kg_ha - n_perso_aria_kg_ha) * fattore_lisciviazione;
                  n_perso_acqua_percento = n_perso_acqua_kg_ha /(n_tot_kg_ha * 100);
                  coefficienteN = (1 - (n_perso_aria_kg_ha+n_perso_acqua_kg_ha)) / (n_tot_kg_ha * 100);
             }
        }  
        
        
        Connessione.getInstance().chiudi();
          
        return efficienza;
    }
    
    
    /*private void setDosiNCampo()
    {
        
    }*/
    /**
     * @param efficienza the efficienza to set
     */
    public void setEfficienza(double efficienza) {
        this.efficienza = efficienza;
    }
    
    /**
     * ritorna l'efficienza numerica della distribuzione
     * di azoto presa dalla tabella tabellaefficienza_refluo_forma_tipo_dose come 
     * media ponderata
     * @param dose1
     * @param condose se true la dose è definita dal parametro dose1 altrimenti la calcola
     * 
     * @return 
     */
    private double getEfficienzaDBMedia(int dose1,boolean condose,int idtecnica )
    {        
        double efficienza_db = 0;
        
       if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0 || this.tipoModalita == 0 ) {
            return 0d;
        }  
        
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }     
         
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo ");
       
       
        /**
           * devo filtrare i mesi in funzione del primo parametro che l'utente 
           * puo scegliere ovvero il tipo di refluo o meglio se è palabile o liquido
           */
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
          
          Query q2 = entityManager.createNamedQuery("Mese.findById").setParameter("id",this.tipoMese);
          db.Mese meseT = (db.Mese)q2.getSingleResult();
          
          Query q3 = null;
          if(idtecnica != 0) {
            q3 = entityManager.createNamedQuery("Tecnicadistribuzione.findById").setParameter("id",idtecnica);
          }else
          {
            q3 = entityManager.createNamedQuery("Tecnicadistribuzione.findById").setParameter("id",this.tipoTecnica);
          }
          db.Tecnicadistribuzione tecnica = (db.Tecnicadistribuzione)q3.getSingleResult();
          
          Query q4 = entityManager.createNamedQuery("Modalitadistribuzione.findById").setParameter("id",this.tipoModalita);
          db.Modalitadistribuzione modalita = (db.Modalitadistribuzione)q4.getSingleResult();
           db.QualitaEfficienza qualita = null;
          
          /**
           * recupero le epoche dalla TabellaEfficienzaColturaModalitaTecnica perchè ragiona in epoche
           * e poi recupero dalle epoche i mesi che compongono le epoche
           */
          Query q = entityManager.createQuery("Select distinct a.idQualita FROM TabellaEfficienzaColturaModalitaTecnica a WHERE a.idFormaRefluo=?1 and a.idGruppoColturale=?2 and a.idEpoca=?3 and a.idTecnica=?4 and a.idModalita=?5");
          q.setParameter(1,tipoformarefluo.getIdForma());
          q.setParameter(2, this.coltura.getIdGruppoColturale());
          q.setParameter(3, meseT.getIdEpoca());
          q.setParameter(4, tecnica);
          q.setParameter(5, modalita);
          /**
           * recupero la qualita dell'efficienza che uso per trovare il valore 
           */
          qualita = (db.QualitaEfficienza)q.getSingleResult();
        
        //se non specifichi la dose la calcolo
        //altrimenti se è a true dose1 gia contiene il numero identificstivo della dose
        if(!condose){
         dose1 = 0;
        
        //per il calcolo della dose devo sapere se il refluo è letame o liquame per 
        //se 1 è liquame
        if(tipoformarefluo.getIdForma().getId() == 1) {
            //this.setDose(this.quantita_distribuita * this.indiceLiquame);
             dose1 = this.identificaDose(this.getDose() , tipoformarefluo.getId() );
        }
           
        
        //se 2 è letame
        if(tipoformarefluo.getIdForma().getId() == 2) {
             //this.setDose(this.quantita_distribuita * this.indiceLetame);
             dose1 = this.identificaDose(this.getDose() , tipoformarefluo.getId() );
        }
        }//close if(!condose)
        //this.setDoseha(this.dose / this.superficie);
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dose " + this.dose + " quantita distri " + this.quantita_distribuita);

        
        Query q_dose = entityManager.createNamedQuery("Dose.findById").setParameter("id", dose1);
        db.Dose doseT = (db.Dose)q_dose.getSingleResult();
        
        /**
         * da questa query recupero le efficienze dei liquami o dei letami delle specie bovino,suino,avicolo,altro e biomassa
         * le uso per fare una media pesata per le rispettive quantita di azoto
         */
        Query query5 = entityManager.createQuery("Select a from TabellaEfficienzaRefluoDose a where a.idDose=?1  and a.idQualita=?2 and a.idFormaRefluo=?3 and a.idTipoFormaRefluo=?4");
        query5.setParameter(1, doseT);
        query5.setParameter(2, qualita);
        query5.setParameter(3, tipoformarefluo.getIdForma());
        query5.setParameter(4, tipoformarefluo);
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " mese " + this.tipoMese + " modalita " + this.tipoModalita + " refluo " + this.tipoRefluo +" tecnica " + this.tipoTecnica + " dose " + dose + " numero record trovati " + query5.getResultList().size()) ;
        
        List<db.TabellaEfficienzaRefluoDose> tabellaEfficienze = (List<db.TabellaEfficienzaRefluoDose>)query5.getResultList();
        ListIterator<db.TabellaEfficienzaRefluoDose> iterEfficienze = tabellaEfficienze.listIterator();
        /**
         * itero sulle efficienze trovate moltiplicando l'efficienze di una tipologia di refluo
         * con il relativo quantitativo di azoto di quella specie che arriva dal confronto ovvero
         * dal solutore
         */
        double numeratore = 0;
        double denominatore = 0;
        while(iterEfficienze.hasNext())
        {
            db.TabellaEfficienzaRefluoDose temp = iterEfficienze.next();
            //liquame
            if(tipoformarefluo.getIdForma().getId() == 1)
            {
                switch(temp.getIdProvenienzaRefluo().getNome()){
                    case "bovino"://se è una efficienza che arriva dal tipo bovino
                        //la devo moltiplicare per il relativo azoto liquame bovino che arriva dal confronto
                        numeratore +=this.risultatoConfronto.getTknLBov()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknLBov();
                        break;
                     case "suino":
                        numeratore +=this.risultatoConfronto.getTknLSui()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknLSui();
                        break;  
                     case "avicolo":
                         numeratore +=this.risultatoConfronto.getTknLAvi()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknLAvi();
                        break;    
                     case "altro":
                        numeratore +=this.risultatoConfronto.getTknLAlt()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknLAlt();
                        break;      
                     case "biomassa":
                        numeratore +=this.risultatoConfronto.getTknLBio()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknLBio();
                        break;    
                }
            }
            //letame
            if(tipoformarefluo.getIdForma().getId() == 2)
            {
                 switch(temp.getIdProvenienzaRefluo().getNome()){
                    case "bovino"://se è una efficienza che arriva dal tipo bovino
                        //la devo moltiplicare per il relativo azoto liquame bovino che arriva dal confronto
                        numeratore +=this.risultatoConfronto.getTknPBov()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknPBov();
                        break;
                     case "suino":
                        numeratore +=this.risultatoConfronto.getTknPSui()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknPSui();
                        break;  
                     case "avicolo":
                         numeratore +=this.risultatoConfronto.getTknPAvi()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknPAvi();
                        break;    
                     case "altro":
                        numeratore +=this.risultatoConfronto.getTknPAlt()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknPAlt();
                        break;      
                     case "biomassa":
                        numeratore +=this.risultatoConfronto.getTknPBio()*temp.getCoefficienteN();
                        denominatore +=this.risultatoConfronto.getTknPBio();
                        break;    
                }
            }
            //temp.
            //this.risultatoConfronto.gettk
        }
        
        if(denominatore !=0) {
            efficienza_db = numeratore / denominatore;
            //this.setEfficienza(efficienza);
        }
        else {
            //this.setEfficienza(0);
            efficienza_db = 0;
        }        
        
        
        //this.setEfficienza_alcampo(this.doseha * this.efficienza);
        
        
        //Connessione.getInstance().chiudi();
        
        return efficienza_db;
        
    }
    
    /**
     * ritorna l'efficienza numerica della distribuzione
     * di azoto presa dalla tabella tabellaefficienza_refluo_forma_tipo_dose come 
     * media ponderata
     * @param dose1
     * @param condose se true la dose è definita dal parametro dose1 altrimenti la calcola
     * 
     * @return 
     */
    private double getEfficienzaDB(int dose1,boolean condose,int idtecnica,int id_refluo )
    {        
        double efficienza_db_r = 0;
        
       if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0 || this.tipoModalita == 0 ) {
            return 0d;
        }  
        
       if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }     
         
      // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo ");
              
        /**
           * devo filtrare i mesi in funzione del primo parametro che l'utente 
           * puo scegliere ovvero il tipo di refluo o meglio se è palabile o liquido
           */
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
          
          Query q2 = entityManager.createNamedQuery("Mese.findById").setParameter("id",this.tipoMese);
          db.Mese meseT = (db.Mese)q2.getSingleResult();
          
          Query q3 = null;
          if(idtecnica != 0) {
            q3 = entityManager.createNamedQuery("Tecnicadistribuzione.findById").setParameter("id",idtecnica);
          }else
          {
            q3 = entityManager.createNamedQuery("Tecnicadistribuzione.findById").setParameter("id",this.tipoTecnica);
          }
          db.Tecnicadistribuzione tecnica = (db.Tecnicadistribuzione)q3.getSingleResult();
          
          Query q4 = entityManager.createNamedQuery("Modalitadistribuzione.findById").setParameter("id",this.tipoModalita);
          db.Modalitadistribuzione modalita = (db.Modalitadistribuzione)q4.getSingleResult();
           db.QualitaEfficienza qualita = null;
         
          /**
           * recupero le epoche dalla TabellaEfficienzaColturaModalitaTecnica perchè ragiona in epoche
           * e poi recupero dalle epoche i mesi che compongono le epoche
           */
          Query q = entityManager.createQuery("Select distinct a.idQualita FROM TabellaEfficienzaColturaModalitaTecnica a WHERE a.idFormaRefluo=?1 and a.idGruppoColturale=?2 and a.idEpoca=?3 and a.idTecnica=?4 and a.idModalita=?5");
          q.setParameter(1,tipoformarefluo.getIdForma());
          q.setParameter(2, this.coltura.getIdGruppoColturale());
          q.setParameter(3, meseT.getIdEpoca());
          q.setParameter(4, tecnica);
          q.setParameter(5, modalita);
          /**
           * recupero la qualita dell'efficienza che uso per trovare il valore 
           */
             qualita = (db.QualitaEfficienza)q.getSingleResult();
         
        //se non specifichi la dose la calcolo
        //altrimenti se è a true dose1 gia contiene il numero identificstivo della dose
        if(!condose){
         dose1 = 0;
        
        //per il calcolo della dose devo sapere se il refluo è letame o liquame per 
        //se 1 è liquame
        if(tipoformarefluo.getIdForma().getId() == 1) {
            //this.setDose(this.quantita_distribuita * this.indiceLiquame);
             dose1 = this.identificaDose(this.getDose() , tipoformarefluo.getId() );
        }
           
        
        //se 2 è letame
        if(tipoformarefluo.getIdForma().getId() == 2) {
             //this.setDose(this.quantita_distribuita * this.indiceLetame);
             dose1 = this.identificaDose(this.getDose() , tipoformarefluo.getId() );
        }
        }//close if(!condose)
        //this.setDoseha(this.dose / this.superficie);
        
        //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dose " + this.dose + " quantita distri " + this.quantita_distribuita);

        
        Query q_dose = entityManager.createNamedQuery("Dose.findById").setParameter("id", dose1);
        db.Dose doseT = (db.Dose)q_dose.getSingleResult();
        
        
        Query q_provenienza = entityManager.createNamedQuery("ProvenienzaRefluo.findById").setParameter("id", id_refluo);
        db.ProvenienzaRefluo provenienza = (db.ProvenienzaRefluo)q_provenienza.getSingleResult();
        /**
         * da questa query recupero le efficienze dei liquami o dei letami delle specie bovino,suino,avicolo,altro e biomassa
         * le uso per fare una media pesata per le rispettive quantita di azoto
         */
        Query query5 = entityManager.createQuery("Select a from TabellaEfficienzaRefluoDose a where a.idDose=?1  and a.idQualita=?2 and a.idFormaRefluo=?3 and a.idTipoFormaRefluo=?4 and a.idProvenienzaRefluo=?5");
        query5.setParameter(1, doseT);
        query5.setParameter(2, qualita);
        query5.setParameter(3, tipoformarefluo.getIdForma());
        query5.setParameter(4, tipoformarefluo);
        query5.setParameter(5, provenienza);
        //System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " mese " + this.tipoMese + " modalita " + this.tipoModalita + " refluo " + this.tipoRefluo +" tecnica " + this.tipoTecnica + " dose " + dose + " numero record trovati " + query5.getResultList().size()) ;
        if(query5.getResultList().size() == 0)
        {
            efficienza_db_r = 0;        
        }else
        {
            db.TabellaEfficienzaRefluoDose efficienzadb = (db.TabellaEfficienzaRefluoDose)query5.getSingleResult();
            efficienza_db_r = efficienzadb.getCoefficienteN();
        }
       
       
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " " +Thread.currentThread().getStackTrace()[1].getMethodName() + " efficienza trovata " + efficienza_db_r) ;

        
        return efficienza_db_r;
        
    }
    /**
     * @return the efficienza_alcampo
     */
    public double getEfficienza_alcampo() {
        /*if(this.getDoseha() == 0 || this.getEfficienza() == 0) {
            return 0;
        }*/
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dose " + this.dose + " quantita distri " + this.quantita_distribuita);

        
        efficienza_alcampo = this.getDoseha() * this.getEfficienza();
        this.setEfficienza_alcampo(efficienza_alcampo);
        return efficienza_alcampo;
    }

    /**
     * dose 1 -> x<125 , 2 -> 125<x<250 , 3 -> x > 250
     * @param efficienza_alcampo the efficienza_alcampo to set
     */
    public void setEfficienza_alcampo(double efficienza_alcampo) {
        this.efficienza_alcampo = efficienza_alcampo;
        int id_dose = 1;
        boolean uscita = false;       
        
        if(this.tipoRefluo == 0 || this.tipoMese == 0 || this.tipoTecnica == 0 || this.tipoModalita == 0)
        {
            return;
        }     
        if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }
        /**
         * nel caso in cui l'utente inserisca N.eff che è KgN/ha * Eff. non conoscendo Eff. 
         * perché dipende dalla dose che non ho ancora non so tornare indietro cioè non so trovare m3 e m3/ha
         * allora faccio delle supposizioni
         */
        //provo con una dose bassa
        double efficienzaT = this.getEfficienzaDBMedia(id_dose, true,0);
        
        double doseT = this.efficienza_alcampo / efficienzaT   ;
        
        if(doseT < 125)
        {
            uscita = true;
        }else
        {
            id_dose = id_dose + 1;
            efficienzaT = this.getEfficienzaDBMedia(id_dose, true,0);
        
            doseT = this.efficienza_alcampo / efficienzaT ;
        }
        
        if(!uscita)
        {
        if(doseT >= 125 && doseT <= 250 )
        {
          uscita = true;   
        }else
        {
            id_dose = id_dose + 1;
            efficienzaT = this.getEfficienzaDBMedia(id_dose, true,0);
        
            doseT = this.efficienza_alcampo / efficienzaT ;
        }
        }
         
        if(!uscita )
        {
        if(doseT > 250)
        {
            uscita = true;
        }else
        {
            id_dose = id_dose + 1;
            efficienzaT = this.getEfficienzaDBMedia(id_dose, true,0);
        
            doseT = this.efficienza_alcampo / efficienzaT  ;
        }
        }       
        
        this.setEfficienza(efficienzaT);
        this.setDoseha(doseT);
        this.setDose(doseT * this.superficie);
        
        Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
        db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
        //liquame
        if(tipoformarefluo.getIdForma().getId() == 1) {
            this.setQuantita_distribuita(doseT * this.superficie / this.indiceLiquame);
        }
        else {
            this.setQuantita_distribuita(doseT * this.superficie / this.indiceLetame);
        }
        
        this.setQuantita_distribuita_mcubi(this.quantita_distribuita / this.superficie);
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" " + Thread.currentThread().getStackTrace()[1].getMethodName() + " dose " + this.dose + " quantita distri " + this.quantita_distribuita);
    
        //Connessione.getInstance().chiudi();
        
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
     * @return the tipoRefluo
     */
    public int getTipoRefluo() {
        return tipoRefluo;
    }

    /**
     * @param tipoRefluo the tipoRefluo to set
     */
    public void setTipoRefluo(int tipoRefluo) {
        this.tipoRefluo = tipoRefluo;
    }

   

    /**
     * @return the tipoTecnica
     */
    public int getTipoTecnica() {
        return tipoTecnica;
    }

    /**
     * @param tipoTecnica the tipoTecnica to set
     */
    public void setTipoTecnica(int tipoTecnica) {
        this.tipoTecnica = tipoTecnica;
    }

    /**
     * @return the tipoModalita
     */
    public int getTipoModalita() {
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+ " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " valore modalita " + this.tipoModalita);
        
        return tipoModalita;
    }

    /**
     * @param tipoModalita the tipoModalita to set
     */
    public void setTipoModalita(int tipoModalita) {
        
            System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+ " " + Thread.currentThread().getStackTrace()[1].getMethodName() + " valore modalita " + this.tipoModalita);

        
        this.tipoModalita = tipoModalita;
    }

   

    /**
     * @return the tipoMese
     */
    public int getTipoMese() {
        return tipoMese;
    }

    /**
     * @param tipoMese the tipoMese to set
     */
    public void setTipoMese(int tipoMese) {
        this.tipoMese = tipoMese;
    }

    /**
     * @return the listaMesi
     */
    public List<SelectItem> getListaMesi() {
                
        this.listaMesi.clear();
        this.tipiTecniche.clear();
        this.tipiModalita.clear();
               
                
        if(this.tipoRefluo == 0)
            return null;
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }      
          /**
           * devo filtrare i mesi in funzione del primo parametro che l'utente 
           * puo scegliere ovvero il tipo di refluo o meglio se è palabile o liquido
           */
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
          
          /**
           * recupero le epoche dalla TabellaEfficienzaColturaModalitaTecnica perchè ragiona in epoche
           * e poi recupero dalle epoche i mesi che compongono le epoche
           */
          Query q = entityManager.createQuery("Select distinct a.idEpoca FROM TabellaEfficienzaColturaModalitaTecnica a WHERE a.idFormaRefluo=?1 and a.idGruppoColturale=?2");
          q.setParameter(1,tipoformarefluo.getIdForma());
          q.setParameter(2, this.coltura.getIdGruppoColturale());
          
          List<db.Epoca> epoche =(List<db.Epoca>) q.getResultList();
          
          SelectItem ite = null;
          
          ListIterator<db.Epoca> iterepoche =epoche.listIterator();
          /**
           * itero sulle epoche recuperate e per ogni epoca 
           * aggiungo i mesi alla lista degli item dei mesi con i relativi
           * valori numerici
           */  
          while(iterepoche.hasNext())
          {
              db.Epoca temp = iterepoche.next();
              Iterator<db.Mese> iterMese = temp.getMeseCollection().iterator();
              
              while (iterMese.hasNext()) {
                  db.Mese mesetemp = iterMese.next();
                  ite = new SelectItem();
                  ite.setLabel(mesetemp.getDescrizione());
                  ite.setValue(mesetemp.getId());

                  this.listaMesi.add(ite);
              }
          }
          
          Connessione.getInstance().chiudi();    
        
        
        
        return listaMesi;
    }

    /**
     * @param listaMesi the listaMesi to set
     */
    public void setListaMesi(List<SelectItem> listaMesi) {
        this.listaMesi = listaMesi;
    }

    /**
     * @return the perditaInAria
     */
    public double getPerditaInAria() {
        // this.perditaInAria = Math.random();
        return perditaInAria;
    }

    /**
     * @param perditaInAria the perditaInAria to set
     */
    public void setPerditaInAria(double perditaInAria) {
        this.perditaInAria = perditaInAria;
    }

    /**
     * @return the perditaInAcqua
     */
    public double getPerditaInAcqua() {
        
        //this.perditaInAcqua = Math.random();
        return perditaInAcqua;
    }

    /**
     * @param perditaInAcqua the perditaInAcqua to set
     */
    public void setPerditaInAcqua(double perditaInAcqua) {
        this.perditaInAcqua = perditaInAcqua;
    }

    /**
     * @return the dose
     */
    public double getDose() {
        //return  Math.random();
        //double dose_re = 0;
        if(this.tipoRefluo == 0) {
            dose = 0;
        }else
        {
        
         if(entityManagerFactory == null || (!entityManagerFactory.isOpen()))
         {
            Connessione connessione = Connessione.getInstance();
            entityManager = connessione.apri("renuwal2");
         }  
         
          Query q1 = entityManager.createNamedQuery("TipoFormaRefluo.findById").setParameter("id",this.tipoRefluo);
          db.TipoFormaRefluo tipoformarefluo = (db.TipoFormaRefluo)q1.getSingleResult();
         if(tipoformarefluo.getIdForma().getId() == 1) {
            return this.getQuantita_distribuita() * this.indiceLiquame;
           
            
            
        }
        
        //se 2 è letame
        if(tipoformarefluo.getIdForma().getId() == 2) {
            return  this.getQuantita_distribuita() * this.indiceLetame;
           
        }
        
        Connessione.getInstance().chiudi();
        //this.setDose(dose);
        }
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() +" dose " + this.dose);
        
        
        return dose;
    }

    /**
     * @param dose the dose to set
     */
    public void setDose(double dose) {
              // System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " prima del tipoformarefluo tiporefluo "+this.tipoRefluo +" tipomese "+ this.tipoMese +" tipotecnica " + this.tipoTecnica +" tipomodalita "+ this.tipoModalita);

        this.dose = dose;
    }

    /**
     * @return the doseha
     */
    public double getDoseha() {
        
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " dose "+this.getDose() +" superficie "+ this.getSuperficie());

        
        if(this.getDose() == 0 || this.superficie == 0) {
            return 0;
        }
        
        doseha = this.getDose() / this.superficie;
        
        this.setDoseha(doseha);
        
        return doseha;
    }

    /**
     * @param doseha the doseha to set
     */
    public void setDoseha(double doseha) {
     
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() +" " +Thread.currentThread().getStackTrace()[1].getMethodName() + " dose "+this.dose +" superficie "+ this.superficie);

       this.doseha = doseha;
    }

    /**
     * @return the liquame
     */
    public boolean isLiquame() {
        return liquame;
    }

    /**
     * @param liquame the liquame to set
     */
    public void setLiquame(boolean liquame) {
        this.liquame = liquame;
    }

    /**
     * @return the epoca
     */
    public db.Epoca getEpoca() {
        return epoca;
    }

    /**
     * @param epoca the epoca to set
     */
    public void setEpoca(db.Epoca epoca) {
        this.epoca = epoca;
    }

    
    
    
}
