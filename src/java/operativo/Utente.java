package operativo;


import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import joomla.StrutturaSpecie;
import operativo.dettaglio.Connessione;
import operativo.dettaglio.DettaglioCuaa;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.sessions.server.ServerSession;

/*
 * Questo bean serve per la parte di autenticazione usato in tutte le pagine 
 */

/**
 *
 * @author giorgio
 */
@ManagedBean( name = "utente")
@SessionScoped
public class Utente implements Serializable {
    
    
     /**
     * variabili tuili per la connesione al db
     */
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaEntityManager jpa;
    private ServerSession serverSession;
    
    
      private static final long serialVersionUID = 1L;
      
      /**
       * valore inserito dalla form presente in index.xhtml
       */
       @Size(min=3,max=40,message="Il campo username deve essere almeno di 3 e max 40 caratteri.")
      private String username = "";
      
       /**
       * valore inserito dalla form presente in index.xhtml
       */
        @Size(min=3,max=40,message="Il campo password deve essere almeno di 3 e max 40 caratteri.")
      private  String password = "";
      
      /**
       * mi dice se la verifica dei parametri inseriti dall'utetne sono
       * corretti
       */
      private  boolean corretto = false;
      
      /**
       * contiene il messaggio di avviso da far vedere all'utente
       * nella pagina iniziale cioè index.xhtml
       */
      private String messaggio = "";
      
      /**
       * username che deve inserire l'utente per passare la verficia dell'acciunt
       */
      private String nome ="azienda1";
      /**
       * password che deve inserire l'utente per passare la verficia dell'account
       */
      private String segreto ="azienda1";
      
      //mi informa sul fatto che la verifica utente sia gia avvenuta
     // private static boolean verificato = true;
      /**
       * mi informa che la verfica è andata a buon fine e l'utente ha gia 
       * inserito correttamente username e password 
       */
      private boolean inseritoCorretto = false;
      
      
      @Size(min=3,max=40,message="Il campo nome deve essere almeno di 3 e max 40 caratteri.")
      private String nomevero;
       @Size(min=3,max=40,message="Il campo cognome deve essere almeno di 3 e max 40 caratteri.")
      private String cognomevero;
      private String mail_utente;
      
      
    
      private String username_messaggio;
      
      private String mail_messaggio;
    /**
     * @return the corretto
     */
    public  boolean isCorretto() {
         DettaglioCuaa dett = null;
        
         verificaAccount();
         
         
         if(this.corretto)
         {
              dett = new DettaglioCuaa();
              dett.setUtente(this.username);
              
         }
             
         
        return this.corretto;
       
    }

    /**
     * @param aCorretto the corretto to set
     */
    public void setCorretto(boolean aCorretto) {
        corretto = aCorretto;
    }
    
    
    private void verificaAccount()
    {
        /*entityManagerFactory = Persistence.createEntityManagerFactory("renuwal2");
        entityManager = entityManagerFactory.createEntityManager();
        jpa = (JpaEntityManager) entityManager.getDelegate();*/
        
        /**
         * viene messo qui come test
         */
        //StrutturaSpecie.getIstanza();
        if(this.corretto)
            return;
        if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
        
        System.out.println("verifica Account username :" + username + " password :" + password);
        
        
        Query q = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.username=?1 AND u.password=?2");
        q.setParameter(1, username);
        q.setParameter(2, password);
        
        //if(username.equals(nome) && password.equals(segreto)) {
        if(q.getResultList().size() != 0)
        {
            setCorretto(true);
            System.out.println("utente corretto username " + username +" password " + password);
            messaggio = "";
            inseritoCorretto = true;
            db.Utenti ut = (db.Utenti)q.getSingleResult();
            this.setMail_utente(ut.getMail());
        }
        else {
            setCorretto(false);
            inseritoCorretto = false;
            //System.out.println("utente non corretto username " + username +" password " + password);
            messaggio = "Username o password non corretti.";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
                //return;
            } catch (IOException ex) {
                Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
      
        
        
        
          Connessione.getInstance().chiudi();
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        System.out.println("getUsername  username "+username);
        return username;
    }

    /**
     * @param aUsername the username to set
     */
    public  void setUsername(String aUsername) {
        System.out.println("setUsername  aUsername "+aUsername);
        username = aUsername;
    }

    /**
     * @return the password
     */
    public  String getPassword() {
        return password;
    }

    /**
     * @param aPassword the password to set
     */
    public void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * @return the messaggio
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * @param messaggio the messaggio to set
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    /**
     * @return the inseritoCorretto
     */
    public boolean isInseritoCorretto() {
        return inseritoCorretto;
    }

    /**
     * @param inseritoCorretto the inseritoCorretto to set
     */
    public void setInseritoCorretto(boolean inseritoCorretto) {
        this.inseritoCorretto = inseritoCorretto;
    }

    /**
     * @return the nomevero
     */
    public String getNomevero() {
        return nomevero;
    }

    /**
     * @param nomevero the nomevero to set
     */
    public void setNomevero(String nomevero) {
        this.nomevero = nomevero;
    }

    /**
     * @return the cognomevero
     */
    public String getCognomevero() {
        return cognomevero;
    }

    /**
     * @param cognomevero the cognomevero to set
     */
    public void setCognomevero(String cognomevero) {
        this.cognomevero = cognomevero;
    }

    /**
     * @return the mail_utente
     */
    public String getMail_utente() {
        return mail_utente;
    }

    /**
     * @param mail_utente the mail_utente to set
     */
    public void setMail_utente(String mail_utente) {
        this.mail_utente = mail_utente;
    }
    
    
    public String salva()
    {
        /**
         * il contenuto della stringa viene inserito nel campo action di un bottone e determina
         * in funzione del suo contenuto dove verra rediretta la pagina
         */
        String ritorno ="salvato";
        
         if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
        System.out.println("sono nel salva username :"  +username+" psw:" + password+" nomervero: " + nomevero+" cognomevero: " + cognomevero+" mail:" + mail_utente);
        
        /**
         * recupero l'id dell'ultimo utente da usare per il nuovo inserito
         */
        Query q = entityManager.createQuery("SELECT u FROM Utenti u ORDER BY u.id ");
        db.Utenti ut1 = (db.Utenti)q.getResultList().get(q.getResultList().size()-1);
        
        //if(username.equals(nome) && password.equals(segreto)) {
        System.out.println(" ultimo utente id"+ut1.getId() + " nome " + ut1.getUsername());
        /**
         * verifico la presenza del nuovo utente : il suo username è gia presente
         * se si non procedere con l'inserimento altrimenti si 
         */
        q = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.username=?1 ");
        q.setParameter(1, username);
        
        if(q.getResultList().size()==0)
        //System.out.println("utente gia presente " + q.getResultList().size());
        {
            EntityTransaction tx = entityManager.getTransaction();
        
        tx.begin();
        db.Utenti ut = new db.Utenti();
        ut.setId(ut1.getId()+1);
        ut.setMail(mail_utente);
        ut.setNomeVero(nomevero);
        ut.setCognomeVero(cognomevero);
        ut.setPassword(password);
        ut.setUsername(username);
        ut.setSuperuser(false);
        entityManager.persist(ut);
        tx.commit();
        
        
        mandaMail(username,password,mail_utente,true);
        
        }
        else
        {
            ritorno = null;
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username o mail non trovati nel database ")); 
        }
        return ritorno;
    }
    
    
    private String getBrowserName() {
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");

    if(userAgent.contains("MSIE")){ 
        return "Internet Explorer";
    }
    if(userAgent.contains("Firefox")){ 
        return "Firefox";
    }
    if(userAgent.contains("Chrome")){ 
        return "Chrome";
    }
    if(userAgent.contains("Opera")){ 
        return "Opera";
    }
    if(userAgent.contains("Safari")){ 
        return "Safari";
    }
    return "Unknown";
}
    
    
    
    private String getRemoteAddr() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + " ipadrress " + ipAddress);
        
        return ipAddress;
    }
    
    
    private boolean mandaMail(String username,String password,String mail,boolean nuovo)
    {
         // Recipient's email ID needs to be mentioned.
      String to = mail;

      // Sender's email ID needs to be mentioned
      String from = "giorgiogalassi5@gmail.com";

      // Assuming you are sending email from localhost
      String host = "smtp.gmail.com";

      // Get system properties
      //Properties properties = System.getProperties();

      // Setup mail server
      //properties.setProperty("mail.smtp.host", host);
      String indirizzoIP = getRemoteAddr();
      String browserusato =  getBrowserName();
    
      
      Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
      

      // Get the default Session object.
      //Session session = Session.getDefaultInstance(props);
      Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("giorgiogalassi5@gmail.com", "19visto45iia");
			}
		  });
 
      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));
         
          message.addRecipient(Message.RecipientType.BCC,
                                  new InternetAddress("giorgio.galassi@unimi.it"));

         // Set Subject: header field
         message.setSubject("Renuwal message - Gestione Utenti");
         
         if(nuovo)
            // Now set the actual message
             message.setText("Il tuo utente è stato registrato con successo sul portale Renuwal.Usa i seguenti dati per accedere alle pagine del progetto :\n username :"+username+"\n passsword :"+password + "\n browser :" + browserusato +"\n\n Grazie e Buona Navigazione \n\n Renuwal \n\n Dipartimento di Scienze Agrarie e Ambientali \n Universita degli Studi di Milano " );
         else
              message.setText("I dati necessari per loggarti sul portale Renuwal sono:\n username :"+username+"\n passsword :"+password + "\n\n Grazie e Buona Navigazione \n\n Renuwal \n\n Dipartimento di Scienze Agrarie e Ambientali \n\n Universita degli Studi di Milano ");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
         
         return true;
         
      }catch (MessagingException mex) {
         mex.printStackTrace();
         
         return false;
        }
      
      
      
    }
    
    
    public String mandaPassword()
    {
        
         if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
        System.out.println("sono nel salva username :"  +username+" psw:" + password+" nomervero: " + nomevero+" cognomevero: " + cognomevero+" mail:" + mail_utente);
        
        /**
         * recupero l'id dell'ultimo utente da usare per il nuovo inserito
         */
       
        /**
         * verifico la presenza del nuovo utente : il suo username è gia presente
         * se si non procedere con l'inserimento altrimenti si 
         */
        Query q = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.username=?1 and u.mail=?2 ");
        q.setParameter(1, username);
        q.setParameter(2, mail_utente);
        
        if(q.getResultList().size() != 0)
        { 
            db.Utenti ut1 =(db.Utenti) q.getSingleResult();
            mandaMail(ut1.getUsername(),ut1.getPassword(),ut1.getMail(),false);
            
            this.mail_messaggio = "mail spedita con successo";
        
        }else
        {
             this.mail_messaggio = "mail non spedita : username e mail non coincidono ";
        }
         
         return "spedita";
        // return "";
    }
    
    
    
   

    /**
     * @return the username_messaggio
     */
    public String getUsername_messaggio() {
        
         if (entityManagerFactory == null || !(entityManager.isOpen()))
                            {
                               Connessione connessione = Connessione.getInstance();
                              
                               entityManager = connessione.apri("renuwal2");
                               entityManagerFactory = connessione.getEntityManagerFactory();
                               System.out.println(" apro la connessione perchè chiusa ed adesso è aperta : " +  entityManager.isOpen() + " entityManagerfactory aperto : " + entityManagerFactory.isOpen());
                            }else
        {
             System.out.println(" connessione è gia aperta ");
        }
        
        
       
        /**
         * verifico la presenza del nuovo utente : il suo username è gia presente
         * se si non procedere con l'inserimento altrimenti si 
         */
        Query q = entityManager.createQuery("SELECT u FROM Utenti u WHERE u.username=?1 ");
        q.setParameter(1, username);
        
        if(q.getResultList().size()!= 0)
            username_messaggio= "username già presente";
        else
            username_messaggio = "";
        System.out.println("get username_messaggio " +username_messaggio );
       // username_messaggio = "b";
        
        return username_messaggio;
    }

    /**
     * @param username_messaggio the username_messaggio to set
     */
    public void setUsername_messaggio(String username_messaggio) {
        
        System.out.println("set username_messaggio " +username_messaggio );
        this.username_messaggio = username_messaggio;
    }

    /**
     * @return the mail_messaggio
     */
    public String getMail_messaggio() {
        
              
        return mail_messaggio;
    }

    /**
     * @param mail_messaggio the mail_messaggio to set
     */
    public void setMail_messaggio(String mail_messaggio) {
        this.mail_messaggio = mail_messaggio;
    }
    
}
