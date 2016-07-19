

package operativo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import operativo.dettaglio.InputOutputXml;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author giorgio
 */
public class Modello  extends Thread {
    
    
     //private static final long serialVersionUID = 1L;

   
    
    String percorsomodello = null;
    String eseguibile = null;
    String nomefile = null;
    /**
     * mi dice se il sistema operativo è linux o no
     */
    boolean linux=false;
   //     LetturaRisultati letturaRisultati = null;
    /**
     * variabile che indica che il solutore ha finito anche di scrivere sul file di output
     */
   public static boolean finito = false;
    
    public Modello(String percorsomodello,String eseguibile,String nomefile,boolean linux)
    {
        this.eseguibile = eseguibile;
        this.percorsomodello = percorsomodello;
        this.nomefile = nomefile;
        this.linux = linux;
       // this.letturaRisultati = letturaRisultati;
        
         System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
       
         try {
            String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);
            String currentDir = System.getProperty("user.dir");
            System.out.println("Current dir using System:" + currentDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
         
    }
    
    public void run()
    {
        
    }
    
    public  void run1()
    {
      
        risolvi();
    }
  
    private  void risolvi()
    {
        
        try {
                       
            ProcessBuilder p1 = new ProcessBuilder(eseguibile,nomefile);
                       
            Process pro = p1.start();
            
              System.out.println("-----------------lancio il modello-------------------" );
            
                   
            //OutputStream stdin = pro.getOutputStream();
            InputStream stderr = pro.getErrorStream();
            InputStream stdout = pro.getInputStream();
            
             //System.out.println("-----------------in corso   modello-------------------" + pro.exitValue()  );
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println("Stdout: " + line);
                
                  if(line.equals("fine"))
                  {
                      
                     System.out.println("------SOLUTORE FINITO------------");
                     reader.close();
                     //this.wait(1500); 
                    //this.sleep(500);
                     //StatoSolutore.getIstanza().setfinito(true);
                    // this.letturaRisultati.notify();
                     
                     //System.out.println("stato del thread lettura risutlasti " +  this.letturaRisultati.getState() );
                     Modello.finito = true;
                     //this.notifyAll();
                     break;
                  }
                  
                  //this.sleep(20);
                  
                  //this.wait(20);
            }
            
            
            reader = new BufferedReader(new InputStreamReader(stderr));
         line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println("Stdout: " + line);
                
              
            }
            
            reader.close();
         
            
            
          //  System.out.println("-----------------fine  modello-------------------" + pro.exitValue()  );
       
        } catch (Exception exc) {
                exc.printStackTrace();
        }


    }
    
  
    
    
}
