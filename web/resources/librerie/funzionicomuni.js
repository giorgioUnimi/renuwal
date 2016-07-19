/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


   /**
                 *toglie dal container delle aziende quella che ha il cuaa e scenario
                 *passati
                 **/
                function toglidalcontainer(cuaa)
                {
                    //alert("ciao cuaa "+cuaa   );
                    
                    
                     $('#cart ol').find('li').each(function(){
                                    var current1 = $(this);
                                    
                                   // alert("prima dell if  "+ current1.text());
                                    
                                      if(current1.text().indexOf(cuaa)!= -1)
                                                {
                                                     //alert("sono nell if  "+ current1.text());
                                                    
                                                    current1.remove();
                                                    //$("#dialog").hide();
                                                    $("#dialog").closest('.ui-dialog-content').dialog('close'); 
                                                }
                                                
                                 if($('#cart ol li').length == 0)                   
                                 {
                                     //alert("vuoto  ");
                                   $('#cart ol').append('<li class="placeholder" style="width:210px;height: 200px;">Trascina qui le aziende</li>');
                                 }                
                                                
                                                
                     });
                }     
                
                
                 /*
                 *funzione che mostra il contenuto dell'array aziende in funzione della
                 *variabile booleana aconsole che se true stampa a console il contenuto dell'array
                 *altrimenti usa gli alert
                 */
                function mostraAziende(aconsole)
                {
                    for(var i = 0; i < aziende.length;i++)
                        {
                         var obj = aziende[i];    
                        if(aconsole)
                            {
                                console.debug("nome azienda " + obj.azienda + " scenario " +obj.scenario);
                            }else
                            {    
                                alert("nome azienda " + obj.azienda + " scenario " +obj.scenario);
                            }
                        
                        }
                }    
                
                
                 /*
             *    evento che scatta quando l'utente cambia un valore del menu atendina
             *    scenari . Cerca il valore dell'azienda nell'array aziende e quando troiva il nome dell'azienda    
             *    va amodificare il valore dello scenario nell'array
             */
             function onChangeEventHandler(event)
                {
                    var scenario = event.target.id;
                    var azienda = scenario.substr(0,scenario.length-1);
                   
                   // alert("chi " + chi );
                  
                 // alert("valore di "+scenario + " valore " +  $( "#"+scenario+" option:selected" ).text() +" nome azienda " + azienda  +" valore " + $( "#"+azienda+" td:first ").text() );
                  /*
                   *nome dell'azienda in cui l'utente sta andando a cambiare lo scenario
                   */
                  var nomeazienda = $( "#"+azienda+" td:first ").text();
                  /*
                   *valore del nuovo scenario scelato dall'utente
                   */
                  var valorescenario = $( "#"+scenario+" option:selected" ).val();
                  /*
                   *ciclo su tutte le aziende presenti nell'array e cerco quella che il nome uguale a quella della riga
                   *da cui proviene l'evento del cambiamento dello scneario
                   */
                  for(var  i = 0; i < aziende.length;i++)
                      {
                          /*
                           *quando trovo il nome uguale cambio il valore dello scenraio
                           */
                          if(aziende[i].azienda == nomeazienda)
                             { 
                                 aziende[i].scenario = valorescenario;
                                 //alert("cambio scenario ");
                                 
                             }
                          //alert(aziende[i].azienda + " " + aziende[i].scenario);
                          
                      }
                      
                }
                
                
                
                        /**
                 *USATA NEL DROP per legare l'evento click al li del cart ol ovvero al carrello dove trascini le aziende
                 *e permettere l'utente di andare nel dettaglio dell'azienda o nella pagina dei parametri di progetto  
                 *arrivandoci rapidamente senza passare del percorso
                 **/
                 function sceltaoperazione(cuaa,scenario)
                 {
                     
                     
                     $( "#dialog" ).empty();
                      $( "#dialog " ).append("<p>CUAA =" + cuaa + " SCENARIO = "+scenario+" </p>");
                        $("#dialog").append("<button onclick=\"toglidalcontainer('"+cuaa+"')\">Togli azienda dal container</button> "); 
                        
                        
                    //alert("cuaa " + cuaa + " scenario " + scenario + " dialog  " + $("#dialog").val());  
                   /*prima di passare il cuaa 
                    *verifico se il campo cuaa contiene il valore finto o quello vero in funzione del contenuto
                    *del campo cuaanascosto che contiene il nome dell'utente per cui se contiene azienda1
                    *è il cuaa vero in caso contrario è quello finto
                    */
                    if($("#cuaanascosto").val()!="azienda1")
                         cuaa = getRealCuaa(cuaa);
        
         //alert("cuaa " + cuaa + " scenario " + scenario + " dialog  " + $("#dialog").val());
                    
                       // alert("scenario " + scenario);
                       if(scenario != "0")
                       {
                     //alert("  val ol  " +  $( "#dialog linkmodifiche ol" ).val);
                     //$( "#dialog " ).append("<p>CUAA =" + cuaa + " SCENARIO = "+scenario+" </p>");
                     $( "#dialog " ).append("<ul class=\"stileol\"><li><a href=\"dettaglioAzienda.xhtml?id="+scenario+"&cuaa="+cuaa+"\">guarda il dettaglio Azienda</a></li></ul>");
                     $( "#dialog " ).append("<ul class=\"stileol\"><li><a href=\"parametridiprogetto.xhtml?id="+scenario+"&cuaa="+cuaa+"\">vai ai parametri di progetto</a></li></ul>");
                     /*
                     $( "#dialog " ).append("<ul class=\"stileol\"><li><a href=\"#?id="+scenario+"&cuaa="+cuaa+"\">modifica i parametri di progetto di tutte le aziende nel gruppo</a></li></ul>");
                     $( "#dialog " ).append("<ul class=\"stileol\"><li><a href=\"#?id="+scenario+"&cuaa="+cuaa+"\">guarda il dettaglio Azienda del gruppo</a></li></ul>");*/
                     $( "#dialog" ).dialog();
                       }else
                           {
                               //$( "#dialog " ).append("<p>CUAA =" + cuaa + " SCENARIO = "+scenario+" </p>");
                               $( "#dialog " ).append("<p>E' possibili modificare / visionare gli scenari diversi dallo zero. </p>");
                                 $( "#dialog" ).dialog();
                           }
                     
                     
                 }
                 
                 
                 
//                    /**
//                 *toglie dal container delle aziende quella che ha il cuaa e scenario
//                 *passati
//                 **/
//                function toglidalcontainer(cuaa)
//                {
//                    alert("ciao cuaa "+cuaa   );
//                    
//                    
//                     $('#cart ol').find('li').each(function(){
//                                    var current1 = $(this);
//                                    
//                                    //alert("prima dell if  "+ current1.text());
//                                    
//                                      if(current1.text().indexOf(cuaa)!= -1)
//                                                {
//                                                     //alert("sono nell if  "+ current1.text());
//                                                    
//                                                    current1.remove();
//                                                    //$("#dialog").hide();
//                                                    $("#dialog").closest('.ui-dialog-content').dialog('close'); 
//                                                }
//                                                
//                                 if($('#cart ol li').length == 0)                   
//                                 {
//                                     //alert("vuoto  ");
//                                   $('#cart ol').append('<li class="placeholder" style="width:210px;height: 200px;">Trascina qui le aziende</li>');
//                                 }                
//                                                
//                                                
//                     });
//                }
                /*
                *dato il cuaa finto ritorna il cuaa vero
                *di una dterminata azienda
                */
                function getRealCuaa(cuaafinto)
                {
                    var cuaa;
                    
                    
                    
                    $.ajax({
                        async: false,
                        type : "GET",
                        url:'dajquery/operazioni.jsp',
                        data: {
                                idoperazione : 4 , 
                                cuaa : cuaafinto, 
                                descrizione : ""
                            },
                            success:function(data)
                            {
                                cuaa = data.trim();
                                
                                
                               
                                
                            }      
                        });
                    
                    
                    
                    return cuaa;
                }
                
                
                 function replaceAll(find, replace, str) {
  return str.replace(new RegExp(escapeRegExp(find), 'g'), replace);
}       
            function escapeRegExp(str) {
  return str.replace(/[\-\[\]\/\{\}\(\)\*\+\?\.\\\^\$\|]/g, "\\$&");
}        


//               /**
//                 * ha il compito di ripristinare il contenitore delle aziende e delle
//                 * alternative allo stato iniziale
//                 * 
//                 */
//                $('#bottonesvuota').click(function(){
//                    $('#cart ol').empty();
//                    
//                   $('#cart ol').append('<li class="placeholder" style="width:210px;height: 200px;">Trascina qui le aziende</li>');
//                    $('#risultati').empty();
//                    if(finestraDistanze != null)
//                    finestraDistanze.close();
//                  alert("sono nel click di svuota");
//                   // $("#cart div ol").append('<li>Trascina qui le aziende </li>');
//                 
//                });
                
                
                  /*
                * da testare deve aggiungere tutte le aziende nel carello cosi da fae una prova in un colpo solo
                * cosi da verficare i tempi del solutore facendo calcolare una soluzione con tutte le aziende
                 */
                function aggiungiTutte()
                {
                   $('#cart ol').empty();  
                   alert("Applicare la localizzazione su tutte le aziende comporta mediamente un tempo di calcolo di circa 7/8 minuti.");
                   //$('#cart').append("<ol></ol>");
                    
                  
                    
                    $('#tabellaaziende tr').each(function(){
                      //$('#provatabella tr').each(function(){   
                        
                        var tduno = $(this).find("td:nth-child(1)").html();
                        //var posspan1 = tduno.indexOf("<span>", 0);
                        var posspan2 = tduno.indexOf("</span>", 0);
                        tduno = tduno.substring(posspan2+7, tduno.length-1);
                        
                            $('#cart ol').append("<li>"+tduno+"-scenario: 0</li>");
                        
                        
                    });
                }
                
              /*
               * quando cliccki sul bottone "aggiungi tutte " vengono copiate nel
               * carrello del drag and drop (solo dove avviene il drop) tutte le aziende
               * presenti nel contenitore delle aziende ovvero tabellaziende. Questa funzioen è un test per verificare 
               * il carico massimo del solutore e della pagina permettendo di cpaire i tempi di esecuzione
               */  
//               $('#aggiungitutte').click(function(){
//                   aggiungiTutte();
//               }); 
               
               
               
                /*
                 *ritorna il contenuto del menu a tendina scenari
                 *Ha l'unico scopo di servire per la ricerca dell'azienda nel testo che viene 
                 *draggato e quindi contiene l'intero tr ovvero sia il td del nome dell'azieda 
                 *che il td dello scenario
                 */
                function contenutoScenari()
                {
                    var scenari = $("#idazienda0a").text();
                    //alert("contenuto scenari " + scenari);
                    return  scenari;
                }
                
               /*
                * cerca nell'array aziende un record che abbia nome pari a quello che
                * gli viene passato e se lo trova ritorna un oggetto che ha un campo azienda ed uno scenario
                */   
               function cercaAzienda(nomeazienda)
               {
                   
                   var cuaa = nomeazienda.split("-");
                  // alert("cuaa " + cuaa[0] + " nomeazienda " +nomeazienda);
                   /*
                    * recupero il contenuto degli scenari cosi da sottrarlo al nome azienda che
                    * deriva dal conteuto draggato/droppato e quindi contiene anche il contenuto del menu 
                    * a tendina degli scenari
                    */ 
                   var scena = contenutoScenari();
                   var posIniziale = nomeazienda.indexOf("iniziale");
                   //alert("posIniziale " +posIniziale);
                   nomeazienda = nomeazienda.substr(0,nomeazienda.length - (scena.length) - posIniziale);
                   //nomeazienda = nomeazienda.substr(0,posIniziale);
                   
                   for(var i = 0; i< aziende.length;i++)
                     {
                     //  console.debug("nome cercato " + cuaa[0] + " azienda nel array " + aziende[i].azienda);
                       if((aziende[i].azienda.indexOf(cuaa[0]))!= -1)
                          {
                             // alert("cuaa " +cuaa +"   trovata " + aziende[i].azienda + " scenario " + aziende[i].scenario);
                              return aziende[i];
                          }
                     }
               }
                    
                
              
                //aggiunge un singolo valore all'array aziende'
                function aggiungiAzienda(nomeAzienda,scenarioAzienda)
                {
                    var obj = {azienda:nomeAzienda,scenario:scenarioAzienda};
                    aziende.push(obj);
                }
                
                /*
                 *recupera il contenuto della tabella che contiene 
                 *tutte le aaziende prende il prim otd che contiene la ragione sociale
                 *e il cuaa li inserisce nell'array aziende insieme al valore standard  dello scenario che
                 *è 0
                 */
                function listaAziende()
                {
                    $("#tabellaaziende table tr td:nth-child(1)").each(function(){
                        //alert($(this).text());
                        aggiungiAzienda($(this).text(),0);
                    });
                    var i = 0;
                    $("#tabellaaziende table tr td:nth-child(2)").each(function(){
                       //alert($(this).text() + " i " + i);
                       $("#idazienda"+i+"a ").val("0");
                       i++;
                    });
                    
                  //  alert("numer righe " + $("#tabellaaziende table tr td").children().length );
                }
                
                
                
                  