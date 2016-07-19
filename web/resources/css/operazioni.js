/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function eliminaScenario(id)
{
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("GET","dajquery/operazioni.jsp?idscenario=" + id.value+"&idoperazione=2",true);
    xmlhttp.send();
   
   alert("idscenario da eliminare " + id.value + " text " + id.text);
   
   window.location.reload();
   
} 


var $j = jQuery.noConflict();
$j(document).ready(function() {
    
   
    $j('#formcreazionescenario').hide();
   $j('#formeliminazionescenario').hide();
   /**
    *quando clicchi sul bottone creascenario 
    *mostra i campi per la creazione dello scenario 
    **/
   $j('#creascenario').click(function(){
       //alert("test");
       $j('#formcreazionescenario').toggle();
        
   });
   
   /**
    *quando clicchi sul bottone creascenario 
    *mostra i campi per la creazione dello scenario 
    **/
   $j('#eliminascenario').click(function(){
       //alert("test");
       $j('#formeliminazionescenario').toggle();
        
   });
   
   /**
   *al caricamento della pagina 
   *nella pagina dettaglioAzienda le due tabelle che mostrano le
   *specifiche delle caratteristiche del liquame e letame devono rimanere nascoste
   */      
   $j('#formRoot\\:tabellaAziendaLetPan').hide();
   $j('#formRoot\\:tabellaAziendaLiqPan').hide();
   /**
    *mostra nasconde le tabelle delle specifiche delle caratteristiche
    */
   $j('#mostra_nascondi').click(function(){
       //alert("value bottone " + $("#mostra_nascondi").text());
       if( $("#mostra_nascondi").text()=='Nascondi')
            $("#mostra_nascondi").text('Mostra');
        else
            $("#mostra_nascondi").text('Nascondi');
       //alert("mostra nascondi");
        $j('#formRoot\\:tabellaAziendaLetPan').toggle();
        $j('#formRoot\\:tabellaAziendaLiqPan').toggle();
   });
  
   
   
   
   /**
    *nasconde la form di creazione dello scenario
    */
   /*$j('#nascondiscenario1').click(function(){
       $j('#formcreazionescenario').hide();
   });*/
   
   
   $j('#eliminascenario1').click(function(){
      alert("elimina scenario"); 
   });
   
   
   /**
    *quando clicchi sul bottone crea devi creare un nuovo scenario per una data azienda
    *duplicando i valori dello scenario 0 della medesima azienda che sono presi come riferimento per la creazione
    *di un nuovo scenario ovvero quando crei un nuovo scenario devi partire dalla situazione presente nello scenario 0
    *di quella azienda. A livello grafico devi aggiungere il nuovo id dello scenario alle select di visione ed aliminazione 
    **/
   //$j('#creascenario1').click(function(){
   $j('#creascenario1a').click(function(){    
       
    /*   var scen ="";
      $j('#vediscenario option').each(function(){
        scen = scen + "-" + $j(this).val();
        
        });*/
        
        /**
         *verifico se il campo descrizione è vuoto o meno
         *se è vuoto non permetto l'operazione altrimenti
         *procedo
         **/
        if($j('#descrizionescenario')[0].value.length == 0)
        {
              alert("Descrizione mancante");  
        }else
        {
            
            
            /**
             *mette il cursor in wait per mostrare all'utente 
             *lo stato di attesa. Quando ottiene la risposta da ajax
             *rimette il cursor nello statoi normale
             */
            $j('html, body').css("cursor", "wait");
            
            var id_azienda = $j('#id_azienda').val();
            var desc = $j('#descrizionescenario').val();
            var anno = $j('#anno_scenario option:selected').text();
            
            alert("id_azienda " +id_azienda + " desc " + desc + " anno " + anno );
            
            $j.ajax(
            {
                type:'GET',
                url:'dajquery/operazioni.jsp',
                data: {
                    idoperazione : 1 , 
                    idazienda : id_azienda, 
                    descrizione : desc,
                    anno : anno
                    },
                success:function(data)
                {
                    /**
                     *rimetto lo stato del cursore nello stato normale
                     */
                     $j('html, body').css("cursor", "default");
                   // alert("tutto bene " + data);
                    /*
                     *aggiungo alle due select di scenario ovvero vedi scenario ed elimina scenario
                     *il valore dell'id dello scenario ritornato 
                     **/
                    $j("#vediscenario").append('<option value='+data+'>'+$j('#descrizionescenario').val()+'</option>');
                    $j("#eliminascenario").append('<option value='+data+'>'+$j('#descrizionescenario').val()+'</option>');
                    
                    
                    
                    alert("Lo scenario "+$j('#descrizionescenario').val() +" per l'azienda "+ $j('#cuaa1').val()+" e' stato creato con successo.");
                    
                    /*
                     *nascondo la form per la creazione dello scenario
                     **/
                    
                    $j('#formcreazionescenario').hide();
                    
                     
                    window.location.reload();
                    
                    
                    
                    
                    
                    //location.reload();
                }
            }
            );  
         
        }//close else 
       
       //alert("scenari " + scen + " cuaa " + $j('#cuaa').val() );
   });
   $j('#eliscenario\\:selsce1').change( function() {
         alert("elimina scenario 11aa" + $j(this).val());
   });
 /*  $j('#anno_scenario').click(function(){alert('ciao');});
   /*
    *
    **/
   /*$j('#selscenario\\:selsce').click(function(){
       alert('second click');
   });*/
    
    
//   $j('#eliscenario\\:selsce1').change( function() {
//         alert("elimina scenario 11aa" + $j(this).val());
//         
//         
//         
//  if($j(this).val() == '--' )
//        {
//            alert("Lo scenario --:fa da segnaposto");
//        }
//        else
//        {
//            /**
//             *mediante ajax recupero l'id dello scenario dato il cuaa e l'id
//             **/
//           /* $j.ajax(
//            {
//                type:'GET',
//                url:'dajquery/operazioni.jsp',
//                data: {
//                    idoperazione : 3 , 
//                    cuaa : $j('#cuaa').val(), 
//                     id : $j(this).val()
//                    },
//                success:function(data)
//                {
//                     /**
//                     *elimino gli spazi da data per renderelo maggiormente trattabile e visibile
//                     **/
//                  /*  data = data.replace(/\s/g, '');
//                    
//                    alert(" idscenario " + data);*/
//              /*  }
//            });*/
//           
//            
//            var desrizionescenario = $j( "#second option:selected" ).text();
//            
//            
//             alert("id " + $j(this).val() + " descrizione scenario " +desrizionescenario);
//            /*
//             *se l'id dello scenario è 0 la descrizione è di desault 'iniziale'
//             **/
//            if($j(this).val() == 0)
//                $j('#descrizionescenario').val("iniziale");
//            //window.location.replace("dettaglioAzienda.xhtml?id="+$j(this).val()+"&descrizione="+desrizionescenario);
//        }
//    });
   
   /*
    *elimina lo scenario indicato dal valore selezionato tranne lo scenario zero
    *che solo essere modificat ma non cancellato
    **/
//   $j('#eliminascenario').change(function() {
//       
//        var selezione = $j(this).val();
//        if($j(this).val() == 0 || $j(this).val() == '--' )
//            {
//                alert("Non puoi cancellare lo scenario 0");
//            }
//        else
//        {
//  
//            $j.ajax(
//            {
//                type:'GET',
//                url:'dajquery/operazioni.jsp',
//                data: {
//                    idoperazione : 2 , 
//                    cuaa : $j('#cuaa').val(), 
//                    id : $j(this).val()
//                },
//                success:function(data)
//                {
//                    /**
//                     *elimino gli spazi da data per renderelo maggiormente trattabile e visibile
//                     **/
//                    data = data.replace(/\s/g, '');
//                    //alert("dopo success in ajax " + selezione + " data " + data);
//                   /*
//                    * se l'operazione è andata a buon fine elimino l'opzione
//                    * dalla select vediscenario ed eliminascenario
//                    *  */ 
//                   if(data != 0)
//                    {
//                            $j("#vediscenario option[value="+selezione+"]").remove();
//                            $j("#eliminascenario option[value="+selezione+"]").remove();
//                   
//                   
//                            alert("L'operazione ha avuto successo ." );
//                        }else
//                            {
//                                 alert("L'operazione non ha avuto successo ."); 
//                            }
//                }
//            }
//            );
//                
//   
//        }
//  
//});//close eliminascenario.change
   
   /**
    *------------CHIUSURA-----------------------
    *
    *       parte relativa alla pagina scenario.jsp
    *
    *------------CHIUSURA-----------------------
    **/ 
   
 /* 
  *  test ajax per vedere se è possibile chiamare da jquery
  *   una pagina jsp passandolgi dei parametri e ritornando dei parametri
  *   la prova è positiva
  *     $j.ajax(
    {
        type:'GET',
        url:'test/testAggiungiazienda.jsp',
        data: {id : 142},
        success:function(data)
        {
            alert("tutto bene " + data);
        }
    }
    );
   */
   
   
 });
