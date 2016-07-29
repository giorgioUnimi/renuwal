<%-- 
    Document   : tuttimoduli
    Created on : 13-dic-2013, 16.18.23
    Author     : giorgio
pagina che si deve occuapre di mostrare tutti moduli di una deteminata alternativa nel caso in cui l'utente 
esegua in alternative.xhtml il caso dell'alternativa migliroe o la singola deve comparire quetsa pagina come pop up
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Hashtable"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="operativo.dettaglio.InputOutputXml"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="resources/jquery/base/jquery.ui.all.css"/>
	<script src="resources/jquery/jquery-1.9.1.js"></script>
	<script src="resources/jquery/ui/jquery.ui.core.js"></script>
	<script src="resources/jquery/ui/jquery.ui.widget.js"></script>
	<script src="resources/jquery/ui/jquery.ui.mouse.js"></script>
	<script src="resources/jquery/ui/jquery.ui.draggable.js"></script>
	<script src="resources/jquery/ui/jquery.ui.droppable.js"></script>
	<script src="resources/jquery/ui/jquery.ui.sortable.js"></script>
	<script src="resources/jquery/ui/jquery.ui.accordion.js"></script>
        <!--
	<link rel="stylesheet" type="text/css" href="recources/jquery/base/demos.css"/>
        -->
        <link  rel="stylesheet" type="text/css"  href="resources/css/animated-menu.css"/>
        <link  rel="stylesheet" type="text/css" href="resources/css/jsfcrud.css"/>
      <!--  <script src="resources/css/js/jquery.easing.1.3.js" ></script>-->
        <script src="resources/css/animated-menu.js"></script>
        <title>JSP Page - moduli</title>
    </head>
    <body>
        <%!
       public String mostraSurplus(InputOutputXml leggi,String padre)
     {
         
         String ret ="";
         NodeList temp = null;
         
         /**
              * stampo il surplus cioè vado a leggere l'output degli stoccaggi e l'ouput della rimozione 
              * e faccio la differenza
              */
             ////////liquame
             //bovino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/liquame/m3",true);
             double m3bovinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/liquame/at",true);
             double atbovinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
             temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/liquame/m3",true);
             double m3bovinorimozionel = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/liquame/at",true);
             double atbovinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             //surplus liquame bovino
             double surplus_liquame_bovino_m3 = m3bovinostol - m3bovinorimozionel;
             double surplus_liquame_bovino_at = atbovinostol - atbovinorimozionel;
             
              //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/liquame/m3",true);
             double m3suinostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/liquame/at",true);
             double atsuinostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/liquame/m3",true);
             double m3suinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/liquame/at",true);
             double atsuinorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              //surplus liquame suino
             double surplus_liquame_suino_m3 = m3suinostol - m3suinorimozionel;
             double surplus_liquame_suino_at = atsuinostol - atsuinorimozionel;
             
             
             
              //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/liquame/m3",true);
             double m3avicolostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/liquame/at",true);
             double atavicolostol = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/liquame/m3",true);
             double m3avicolorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/liquame/at",true);
             double atavicolorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
              //surplus liquame avicolo
             double surplus_liquame_avicolo_m3 = m3avicolostol - m3avicolorimozionel;
             double surplus_liquame_avicolo_at = atavicolostol - atavicolorimozionel;
             
             
             
              //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/liquame/m3",true);
             double m3altrostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/liquame/at",true);
             double ataltrostol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/liquame/m3",true);
             double m3altrorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/liquame/at",true);
             double ataltrorimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
              //surplus liquame altro
             double surplus_liquame_altro_m3 = m3altrostol - m3altrorimozionel;
             double surplus_liquame_altro_at = ataltrostol - ataltrorimozionel;
             
              //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/liquame/m3",true);
             double m3biomassestol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/liquame/at",true);
             double atbiomassestol =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/liquame/m3",true);
             double m3biomasserimozionel = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/liquame/at",true);
             double atbiomasserimozionel =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              //surplus liquame biomasse
             double surplus_liquame_biomasse_m3 = m3biomassestol - m3biomasserimozionel;
             double surplus_liquame_biomasse_at = atbiomassestol - atbiomasserimozionel;
             
             ///////letame
             //bovino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/letame/m3",true);
             double m3bovinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/bovino/letame/at",true);
             double atbovinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/letame/m3",true);
             double m3bovinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/bovino/letame/at",true);
             double atbovinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
             
              //surplus letame bovino
             double surplus_letame_bovino_m3 = m3bovinostop - m3bovinorimozionep;
             double surplus_letame_bovino_at = atbovinostop - atbovinorimozionep;
             
             
              //suino
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/letame/m3",true);
             double m3suinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/suino/letame/at",true);
             double atsuinostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/letame/m3",true);
             double m3suinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/suino/letame/at",true);
             double atsuinorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
               //surplus letame suino
             double surplus_letame_suino_m3 = m3suinostop - m3suinorimozionep;
             double surplus_letame_suino_at = atsuinostop - atsuinorimozionep;
             
             
              //avicolo
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/letame/m3",true);
             double m3avicolostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/avicolo/letame/at",true);
             double atavicolostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/letame/m3",true);
             double m3avicolorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/avicolo/letame/at",true);
             double atavicolorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
               //surplus letame avicolo
             double surplus_letame_avicolo_m3 = m3avicolostop - m3avicolorimozionep;
             double surplus_letame_avicolo_at = atavicolostop - atavicolorimozionep;
             
             
              //altro
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/letame/m3",true);
             double m3altrostop = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/altro/letame/at",true);
             double ataltrostop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/letame/m3",true);
             double m3altrorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/altro/letame/at",true);
             double ataltrorimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
               //surplus letame altro
             double surplus_letame_altro_m3 = m3altrostop - m3altrorimozionep;
             double surplus_letame_altro_at = ataltrostop - ataltrorimozionep;
             
              //biomasse
             temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/letame/m3",true);
             double m3biomassestop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"stoccaggi\"]/caratteristichechimiche/biomasse/letame/at",true);
             double atbiomassestop =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/letame/m3",true);
             double m3biomasserimozionep = (int)Double.parseDouble(temp.item(0).getNodeValue());
             
              temp = leggi.cerca1(padre+"/modulo[@name=\"rimozioneazoto\"]/caratteristichechimiche/biomasse/letame/at",true);
             double atbiomasserimozionep =(int) Double.parseDouble(temp.item(0).getNodeValue());
             
             
             
                //surplus letame biomasse
                double surplus_letame_biomasse_m3 = m3biomassestop - m3biomasserimozionep;
                double surplus_letame_biomasse_at = atbiomassestop - atbiomasserimozionep;

                boolean tabellarossa = false;
                if (surplus_liquame_bovino_m3 > 0 || surplus_liquame_suino_m3 > 0 || surplus_liquame_avicolo_m3 > 0 || surplus_liquame_altro_m3 > 0 || surplus_liquame_biomasse_m3 > 0)
                       tabellarossa = true;
                
                try{

                ret +="<p class=\"labellocalizzazioneTable\">SURPLUS</p>";
                ret +="<table class=\"localizzazioneTable2\">";
                if (!tabellarossa) {
                    ret +="<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>";
                } else {
                    ret +="<thead style=\"background-color:red;color:white;\"><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>";
                }



                //ret +=("<thead><tr><th>Volume Liquame Bovino(m<sup>3</sup>)</th><th>Volume Liquame Suino(m<sup>3</sup>)</th><th>Volume Liquame Avicolo(m<sup>3</sup>)</th><th>Volume Liquame Altro(m<sup>3</sup>)</th><th>Volume Liquame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>");
                ret +="<td>" + surplus_liquame_bovino_m3 + "</td><td>" + surplus_liquame_suino_m3 + "</td><td>" + surplus_liquame_avicolo_m3 + "</td><td>" + surplus_liquame_altro_m3 + "</td><td>" + surplus_liquame_biomasse_m3 + "</td>";
                ret +="</tr></table>";

                ret +="<table class=\"localizzazioneTable2\">";

                tabellarossa = false;
                if (surplus_liquame_bovino_at > 0 || surplus_liquame_suino_at > 0 || surplus_liquame_avicolo_at > 0 || surplus_liquame_altro_at > 0 || surplus_liquame_biomasse_at > 0) {
                    tabellarossa = true;
                }



                if (!tabellarossa) {
                    ret +="<thead><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>";
                } else {
                    ret +="<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Liquame Bovino(kg)</th><th>Azoto T Liquame Suino(kg)</th><th>Azoto T Liquame Avicolo(kg)</th><th>Azoto T Liquame Altro(kg)</th><th>Azoto T Liquame Biomasse(kg)</th></tr></thead><tr>";
                }

                ret +="<td>" + surplus_liquame_bovino_at + "</td><td>" + surplus_liquame_suino_at + "</td><td>" + surplus_liquame_avicolo_at + "</td><td>" + surplus_liquame_altro_at + "</td><td>" + surplus_liquame_biomasse_at + "</td>";
                ret +="</tr></table>";


                ret +="<table class=\"localizzazioneTable2\">";


                tabellarossa = false;
                if (surplus_letame_bovino_m3 > 0 || surplus_letame_suino_m3 > 0 || surplus_letame_avicolo_m3 > 0 || surplus_letame_altro_m3 > 0 || surplus_letame_biomasse_m3 > 0)
                       tabellarossa = true;
                

                if (!tabellarossa) {
                    ret +="<thead><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>";
                } else {
                    ret +="<thead style=\"background-color:red;color:white;\"><tr><th>Volume Letame Bovino(m<sup>3</sup>)</th><th>Volume Letame Suino(m<sup>3</sup>)</th><th>Volume Letame Avicolo(m<sup>3</sup>)</th><th>Volume Letame Altro(m<sup>3</sup>)</th><th>Volume Letame Biomasse(m<sup>3</sup>)</th></tr></thead><tr>";
                }

                ret +="<td>" + surplus_letame_bovino_m3 + "</td><td>" + surplus_letame_suino_m3 + "</td><td>" + surplus_letame_avicolo_m3 + "</td><td>" + surplus_letame_altro_m3 + "</td><td>" + surplus_letame_biomasse_m3 + "</td>";
                ret +="</tr></table>";

                ret +="<table class=\"localizzazioneTable2\">";


                tabellarossa = false;
                if ( surplus_letame_bovino_at > 0 || surplus_letame_suino_at > 0 || surplus_letame_avicolo_at > 0 || surplus_letame_altro_at > 0 || surplus_letame_biomasse_at > 0)
                    tabellarossa = true;
                

                if (!tabellarossa) {
                    ret +="<thead><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>";
                } else {
                    ret +="<thead style=\"background-color:red;color:white;\"><tr><th>Azoto T Letame Bovino(kg)</th><th>Azoto T Letame Suino(kg)</th><th>Azoto T Letame Avicolo(kg)</th><th>Azoto T Letame Altro(kg)</th><th>Azoto T Letame Biomasse(kg)</th></tr></thead><tr>";
                }

                ret +="<td>" + surplus_letame_bovino_at + "</td><td>" + surplus_letame_suino_at + "</td><td>" + surplus_letame_avicolo_at + "</td><td>" + surplus_letame_altro_at + "</td><td>" + surplus_letame_biomasse_at + "</td>";
                ret +="</tr></table>";
                
                }catch(Exception ex){};
                
                return ret;

     }
        
    private String  stampaModulo(String nomemodulo, InputOutputXml  leggiModulo) {
        
         LinkedHashMap<String,NodeList> tutto = new LinkedHashMap<String,NodeList>();
         NodeList temp,temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8,temp9,temp10,temp11,temp12,temp13,temp14,temp15 ;  
         String ret = "";
         
         
         ret +="<h3>Caratteristiche chimiche</h3><br/> ";
         //out.println("<br/>Liquame Bovino<br/>");
         temp =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/bovino/liquame", true);
         if(temp != null)
         tutto.put("Liquame Bovino", temp);
         
         temp1 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/bovino/letame", true);
          if(temp1 != null)
         tutto.put("Letame Bovino", temp1);
         
          
         temp2 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/suino/liquame", true);
          if(temp2 != null)
         tutto.put("Liquame Suino", temp2);
         
          temp3 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/suino/letame", true);
           if(temp3 != null)
          tutto.put("Letame Suino", temp3);
         
          temp4 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/avicolo/liquame", true);
           if(temp4 != null)
          tutto.put("Liquame Avicolo", temp4);
          
           temp5 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/avicolo/letame", true);
            if(temp5 != null)
          tutto.put("Letame Avicolo", temp5);
          
           temp6 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/altro/liquame", true);
            if(temp6 != null)
          tutto.put("Liquame Altro", temp6);
          
           temp7 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/altro/letame", true);
            if(temp7 != null)
          tutto.put("Letame Altro", temp7);
           
           temp11 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/biomasse/liquame", true);
            if(temp11 != null)
          tutto.put("Liquame Biomasse", temp11);
          
           temp12 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/caratteristichechimiche/biomasse/letame", true);
            if(temp12 != null)
          tutto.put("Letame Biomasse", temp12);
          
          
           Set chiavi = tutto.keySet();
         
          
          Iterator it = chiavi.iterator();
         
         ret +="<table class=\"tabellaT\">";
          ret+="<tr><th>Tipologia</th><th>Volume(m<sup>3</sup>)</th><th>TKN(Kg)</th><th>TAN(Kg)</th><th>DM(Kg)</th><th>VS(Kg)</th><th>P(Kg)</th><th>K(Kg)</th></tr>";
           int valore1 = 0;
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
           
            ret+="<tr><td>"+chi+"</td>";
           
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   //String nome =tt.getNodeName();
                   String valore = tt.getFirstChild().getNodeValue();
                   if(valore != null || valore.length() != 0)
                     valore1 = (int)(Double.parseDouble(valore));
                   else
                       valore1 = 0;
                   ret+="<td>" + valore1 + "</td>";
                  }
         }
            ret+="</tr>";
            
            
          }
          
           ret +="</table>";
          
          tutto.clear();
           ret +="<h3>Emissioni,Costi,Energia</h3>";
          
           temp8 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/emissioni", true);
            if(temp8 != null)
          tutto.put("Emissioni", temp8);
          chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
           ret +="<tr><th>Emissioni</th><th>Ch4(Kg)</th><th>NH3(kg)</th><th>N2(Kg)</th><th>N2O(Kg)</th><th>CO2(Kg)</th><th>NO(Kg)</th></tr>";
           
            valore1 = 0;
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
            ret +="<tr><td>Valori</td>";
            
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   //String nome = tt.getNodeName();
                   String valore = tt.getFirstChild().getNodeValue();
                   if(valore != null || valore.length() != 0)
                       valore1 = (int)(Double.parseDouble(valore));
                   else
                       valore1 = 0;
                   ret +="<td>"+valore1 +"</td>";
                  }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
                 
          
          tutto.clear();  
            
           temp9 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/costi", true);
            if(temp9 != null)
          tutto.put("Costi", temp9);
          
          
          chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
          ret +="<tr><th>Costi</th><th>Investimento(euro)</th><th>Gestione Netto(euro/a)</th><th>Costo lordo gestione(euro/a)</th><th>Ricavo lordo energia(euro/a)</th><th>Ricavo ammonio(euro)</th></tr>";
          
           valore1 = 0;
          
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
            ret +="<tr><td>Valori</td>";
            
        for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   
                   String valore = tt.getFirstChild().getNodeValue();
                   if(valore != null || valore.length() != 0)
                      valore1 = (int)(Double.parseDouble(valore));
                   else
                       valore1 = 0;
                       
                  // String nome = tt.getNodeName();
                  // System.out.println("nomi emissioni in tutti moduli nome="+nome+" valore ="+valore1);
                   
                       ret +="<td>"+valore1 +"</td>";
                  }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
          
            
           tutto.clear(); 
            
           temp10 =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/energia", true);
            if(temp10 != null)
          tutto.put("Energia", temp10);
          
          
          chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
          ret +="<tr><th>Energia</th><th>Prodotta(KWh)</th><th>Consumata(KWh)</th><th>Venduta(KWh)</th></tr>";
            valore1 = 0;
            String valore = "";
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
            ret +="<tr><td>Valori</td>";
            
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   String nome = tt.getNodeName();
                   if(nome.equals("venduta") || nome.equals("consumata") || nome.equals("prodotta"))
                   {
                   valore = tt.getFirstChild().getNodeValue();
                   if(valore != null || valore.length() != 0)
                          valore1 = (int)(Double.parseDouble(valore));
                   else
                       valore1 = 0;
                   ret +="<td>"+valore1 +"</td>";
                  }
                 }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
            
            //lo uso per le emissioni,costi , energie dei valori al metro cubo per mostrare due cifre decimali
             DecimalFormat dformat = new DecimalFormat("#0.00");
            
            
             ret +="<h3>Valori al metro cubo Emissioni,Costi,Energia</h3>";
            ret +="<h3>Emissioni</h3>";
            tutto.clear(); 
            
          NodeList tempN =  leggiModulo.cerca1("//modulo[@name='"+nomemodulo+"']/valori_al_metro_cubo",true);
            if(tempN != null)
                  tutto.put("emissioni", tempN);
         
          chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
          ret +="<tr><th>Emissioni</th><th>Ch4(kg/m<sup>3</sup>)</th><th>Nh3(kg/m<sup>3</sup>)</th><th>N2(kg/m<sup>3</sup>)</th><th>N2o(kg/m<sup>3</sup>)</th><th>Co2(kg/m<sup>3</sup>)</th><th>No(kg/m<sup>3</sup>)</th></tr>";
            valore1 = 0;
           
          double valore2 = 0;
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
           // System.out.println(" riga 310 tuttimoduli " + chi);
            
            ret +="<tr><td>Valori</td>";
            
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   String nome = tt.getNodeName();
                   if(nome.equals("ch4") || nome.equals("nh3") || nome.equals("n2o")|| nome.equals("n2")|| nome.equals("co2")|| nome.equals("no"))
                   {
                   valore = tt.getFirstChild().getNodeValue();
                  // System.out.println("nome = " + nome + " valore " + valore);
                   if(valore != null || valore.length() != 0)
                          valore2 = Double.parseDouble(valore);
                   else
                          valore2 = 0;
                   ret +="<td>"+dformat.format(valore2) +"</td>";
                  }
                 }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
            
             ret +="<h3>Costi</h3>";
           chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
          ret +="<tr><th>Costi</th><th>Gestione Netto(euro/a*m<sup>3</sup>)</th><th>Costo Lordo Gestione(euro/a*m<sup>3</sup>)</th><th>Ricavo Lordo Energia(euro/a*m<sup>3</sup>)</th><th>Ricavo ammonio(euro)</th></tr>";
            valore1 = 0;
           
           valore2 = 0;
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
           // System.out.println(" riga 310 tuttimoduli " + chi);
            
            ret +="<tr><td>Valori</td>";
            
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   String nome = tt.getNodeName();
                   if(nome.equals("gestione") || nome.equals("costo_netto_gestione") || nome.equals("ricavo_netto_energia") )
                   {
                   valore = tt.getFirstChild().getNodeValue();
                  // System.out.println("nome = " + nome + " valore " + valore);
                   if(valore != null || valore.length() != 0)
                          valore2 = Double.parseDouble(valore);
                   else
                          valore2 = 0;
                   ret +="<td>"+dformat.format(valore2) +"</td>";
                  }
                 }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
               

              ret +="<h3>Energia</h3>";
           chiavi = tutto.keySet();
         
          
          it = chiavi.iterator();
          ret +="<table class=\"tabellaT\">";
          ret +="<tr><th>Energia</th><th>Energia Prodotta(KWh/m<sup>3</sup>)</th><th>Energia Consumata(KWh/m<sup>3</sup>)</th></tr>";
            valore1 = 0;
           
           valore2 = 0;
          while(it.hasNext())
          {
            String chi = it.next().toString();  
            NodeList nodelist = tutto.get(chi);
            
           // System.out.println(" riga 310 tuttimoduli " + chi);
            
            ret +="<tr><td>Valori</td>";
            
            for(int i = 0; i < nodelist.getLength();i++)
         {
             Node tt = nodelist.item(i);
               if (tt.getNodeType() == Node.ELEMENT_NODE) 
                  {
                   String nome = tt.getNodeName();
                   if(nome.equals("energia_prodotta") || nome.equals("energia_consumata") )
                   {
                   valore = tt.getFirstChild().getNodeValue();
                  // System.out.println("nome = " + nome + " valore " + valore);
                   if(valore != null || valore.length() != 0)
                          valore2 = Double.parseDouble(valore);
                   else
                          valore2 = 0;
                   ret +="<td>"+dformat.format(valore2) +"</td>";
                  }
                 }
             
             
         }
             ret +="</tr>";
            
            
          }
           
            ret +="</table>";
            
            
            
            
            //mostraSurplus(leggiModulo,"/");
            
            
             return ret;
                     }
        

       
       %>
        
        
        <%
        
         String numerorandom = request.getParameter("numero");
             // out.println(numerorandom);
                 
             InputOutputXml leggiModulo = new InputOutputXml();
             leggiModulo.impostaFile("output_" + numerorandom + ".xml");
                 
                 
             NodeList temp;
                 
             temp = leggiModulo.cerca1("//alternativa/scelta", false);
             String valore = "";
             for (int i = 0; i < temp.getLength(); i++) {
                 Node tt = temp.item(i);
                 if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) {
                     //String nome = temp.item(i).getNodeName();
                     valore = temp.item(i).getFirstChild().getNodeValue();
                     out.println("<h2>Alternativa scelta : " + valore + "</h2>");
                 }
             }
                 
                 
             if (valore.equals("0")) {
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("" + "<h3>Stoccaggi</h3>" + "");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                         
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("" + "<h3>Gestione Azoto</h3>" + "");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a>");
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\"  style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                         
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                 if (valore.equals("1")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionevite\">Separazione a vite elicoidale</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("" + "<h2>Stoccaggi</h2>" + "");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = stampaModulo("separazione_a_vite_elicoidale", leggiModulo);
                     out.println("<fieldset id=\"idseparazionevite\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione a vite elicoidale</h3></legend>");
                     //out.println("" + "<h3>Separazione a vite elicoidale</h3>" + "");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("" + "<h3>Gestione Azoto</h3>" + "");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                         
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                 }
                     
                     
                     
                 if (valore.equals("2")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione Anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione Anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione Anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                 }
                     
                     
                 if (valore.equals("3")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione Anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                 if (valore.equals("4")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idsbr\">SBR</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = stampaModulo("sbr_ndn", leggiModulo);
                     out.println("<fieldset id=\"idsbr\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>SBR</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -ndn</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     ret = "";
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                     
                 if (valore.equals("5")) {
                     
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idndn\">NDN</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = stampaModulo("sbr_ndn_continuo", leggiModulo);
                     out.println("<fieldset id=\"idndn\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>NDN</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -continuo</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Rimozione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                 if (valore.equals("6")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#idflottatore\">Flottatore</a></li>");
                     out.println("<li><a href=\"#idsbr\">SBR</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("flottatore", leggiModulo);
                     out.println("<fieldset id=\"idflottatore\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Flottatore</h3></legend>");
                     //out.println("<br/>" + "<h3>Flottatore</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("sbr_ndn", leggiModulo);
                     out.println("<fieldset id=\"idsbr\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>SBR</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -ndn</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                 }
                     
                     
                 if (valore.equals("7")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#idflottatore\">Flottatore</a></li>");
                     out.println("<li><a href=\"#idndn\">NDN</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                         
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                     ret = stampaModulo("flottatore", leggiModulo);
                     out.println("<fieldset id=\"idflottatore\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Flottatore</h3></legend>");
                     //out.println("<br/>" + "<h3>Flottatore</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                     ret = stampaModulo("sbr_ndn_continuo", leggiModulo);
                     out.println("<fieldset id=\"idndn\"style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>NDN</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -Continuo</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                 if (valore.equals("8")) {
                     
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                         
                     out.println("<li><a href=\"#idndn\">NDN</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("sbr_ndn", leggiModulo);
                     out.println("<fieldset id=\"idndn\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>NDN</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -ndn<h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                 }
                     
                     
                     
                 if (valore.equals("9")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idstrippaggiocaldo\">Strippaggio a caldo</a></li>");
                         
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("strippaggiocaldo", leggiModulo);
                     out.println("<fieldset id=\"idstrippaggiocaldo\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Strippaggio a caldo</h3></legend>");
                     //out.println("<br/>" + "<h3>Strippaggio a caldo<h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                         
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                 if (valore.equals("10")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#idstrippaggiofreddo\">Strippaggio a freddo</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("strippaggiofreddo", leggiModulo);
                     out.println("<fieldset id=\"idstrippaggiofreddo\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Strippaggio a freddo</h3></legend>");
                     //out.println("<br/>" + "<h3>Strippaggio a freddo</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                 }
                     
                     
                 if (valore.equals("11")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#idevaporazione\">Evaporazione</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     //System.out.println("\n\n\n\n\n" + ret +"\n\n\n\n\n");
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("evaporazione", leggiModulo);
                     out.println("<fieldset id=\"idevaporazione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Evaporazione</h3></legend>");
                     //out.println("<br/>" + "<h3>Evaporazione</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                     
                     
                 if (valore.equals("12")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                         
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione centrifuga</a></li>");
                     out.println("<li><a href=\"#idstrippaggiofreddo\">Strippaggio a freddo</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     // out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("strippaggiofreddo", leggiModulo);
                     out.println("<fieldset id=\"idstrippaggiofreddo\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Strippaggio a freddo</h3></legend>");
                     //out.println("<br/>" + "<h3>Strippaggio a freddo</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                 if (valore.equals("13")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionevite\">Separazione a vite elicoidale</a></li>");
                     out.println("<li><a href=\"#idstabilizzazioneaerobica\">Stabilizzazione aerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                         
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_a_vite_elicoidale", leggiModulo);
                     out.println("<fieldset id=\"idseparazionevite\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione A Vite Elicoidale</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione A Vite Elicoidale</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("stabilizzazione_aerobica", leggiModulo);
                     out.println("<fieldset id=\"idstabilizzazioneaerobica\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stabilizzazione aerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Stabilizzazioen aerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     // out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                     
                     
                 if (valore.equals("14")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionevite\">Separazione a vite elicoidale</a></li>");
                     out.println("<li><a href=\"#idplugflow\">Acstr PlugFlow</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     // out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_a_vite_elicoidale", leggiModulo);
                     out.println("<fieldset id=\"idseparazionevite\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione A Vite Elicoidale</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione A Vite Elicoidale</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("adcstr_plugflow", leggiModulo);
                     out.println("<fieldset id=\"idplugflow\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Adcstr plugflow</h3></legend>");
                     //out.println("<br/>" + "<h3>Adcstr plugflow</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                 if (valore.equals("15")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idndn\">NDN</a></li>");
                     out.println("<li><a href=\"#idfitodepurazione\">Fitodepurazione</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                     String ret = "";
                     ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("sbr_ndn_continuo", leggiModulo);
                     out.println("<fieldset id=\"idndn\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>NDN</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -ndn-Continuo<h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("fitodepurazione", leggiModulo);
                     out.println("<fieldset id=\"idfitodepurazione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Fitodepurazione</h3></legend>");
                     //out.println("<br/>" + "<h3>Fitodepurazione<h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     /* ret = "";
                      ret = stampaModulo("stoccaggi", leggiModulo);
                      out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");  
                      out.println("<legend> <h3>Stoccaggi</h3></legend>");
                          
                      out.println(ret);
                      out.println("</fieldset>");      
                          
                      out.println("<hr>");
                      out.println("<hr>");
                      out.println("<a href=\"#tornasu\">Su</a><br/>");  */
                          
                          
                          
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                     ret = "";
                         
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                 }
                     
                 if (valore.equals("16")) {
                     
                     out.println("<p style=\"font-size:150%;\"><b>Indice</b></p>");
                     out.println("<ul><li><a  id=\"tornasu\" href=\"#idstoccaggi\">Stoccaggi</a></li>");
                     out.println("<li><a href=\"#idseparazionecentrifuga\">Separazione Centrifuga</a></li>");
                     out.println("<li><a href=\"#idflottatore\">Flottatore</a></li>");
                     out.println("<li><a href=\"#idndn\">NDN</a></li>");
                     out.println("<li><a href=\"#idfitodepurazione\">Fitodepurazione</a></li>");
                     out.println("<li><a href=\"#iddigestione\">Digestione anaerobica</a></li>");
                     out.println("<li><a href=\"#idrimozioneazoto\">Gestione eccedenze</a></li>");
                     out.println("<li><a href=\"#idcompostaggio\">Compostaggio</a></li>");
                     out.println("</ul>");
                         
                         
                     String ret = stampaModulo("stoccaggi", leggiModulo);
                     out.println("<fieldset id=\"idstoccaggi\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Stoccaggi</h3></legend>");
                     //out.println("<br/>" + "<h3>Stoccaggi</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("separazione_centrifuga", leggiModulo);
                     out.println("<fieldset id=\"idseparazionecentrifuga\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Separazione Centrifuga</h3></legend>");
                     //out.println("<br/>" + "<h3>Separazione Centrifuga</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("flottatore", leggiModulo);
                     out.println("<fieldset id=\"idflottatore\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Flottatore</h3></legend>");
                     //out.println("<br/>" + "<h3>Flottatore</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("sbr_ndn_continuo", leggiModulo);
                     out.println("<fieldset id=\"idndn\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>NDN</h3></legend>");
                     //out.println("<br/>" + "<h3>SBR -Continuo</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                         
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("fitodepurazione", leggiModulo);
                     out.println("<fieldset id=\"idfitodepurazione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Fitodepurazione</h3></legend>");
                     //out.println("<br/>" + "<h3>Fitodepurazione<h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("ad_cstr", leggiModulo);
                     out.println("<fieldset id=\"iddigestione\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Digestione anaerobica</h3></legend>");
                     //out.println("<br/>" + "<h3>Digestione anaerobica</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("rimozioneazoto", leggiModulo);
                     out.println("<fieldset id=\"idrimozioneazoto\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Gestione Eccedenze</h3></legend>");
                     //out.println("<br/>" + "<h3>Gestione Azoto</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                     ret = "";
                     ret = stampaModulo("compostaggio", leggiModulo);
                     out.println("<fieldset id=\"idcompostaggio\" style=\"border-color:#000; border-style: solid;\">");
                     out.println("<legend> <h3>Compostaggio</h3></legend>");
                     //out.println("<br/>" + "<h3>Compostaggio</h3>" + "<br/>");
                     out.println(ret);
                     out.println("</fieldset>");
                     out.println("<hr>");
                     out.println("<hr>");
                     out.println("<a href=\"#tornasu\">Su</a><br/>");
                         
                         
                 }
          
       
         /*for(int i = 0; i < temp.getLength();i++)
         {
             Node tt = temp.item(i);
               if (temp.item(i).getNodeType() == Node.ELEMENT_NODE) 
                  {
                   String nome = temp.item(i).getNodeName();
                   String valore = temp.item(i).getFirstChild().getNodeValue();
                   out.println(nome +"  "+ valore + "<br/>");
                  }
         }*/
         
         
        %>
    </body>
</html>
