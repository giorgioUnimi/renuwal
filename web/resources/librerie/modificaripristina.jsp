<%-- 
    Document   : modificaripristina
    Created on : 19-lug-2016, 16.26.47
    Author     : giorgio
--%>

public void modificaRipristina(Double valore,String tipologia,int posizione,int azione)   
   {
       if (entityManagerFactory == null || !(entityManagerFactory.isOpen()))
         {
             Connessione connessione = Connessione.getInstance();
            
             entityManager = connessione.apri("renuwal1");
             entityManagerFactory = Connessione.getInstance().getEntityManagerFactory();
         }
        
       Query q = entityManager.createNamedQuery("ScenarioI.findByIdscenario").setParameter("idscenario", detto.getIdscenario());
       db.ScenarioI sce = (db.ScenarioI)q.getResultList().get(0);
       db.CaratteristicheChmiche caratteristichechimiche = sce.getCaratteristicheChmiche();
       
       System.out.println(Thread.currentThread().getStackTrace()[1].getClassName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName() + " valore passato  " +getValore());
       
       /**
        * se azione è 0 modifico la caratteristica utente usando valore
        * se azione è 1 rirpistino il valore della caratteristica usando il 
        * valore della caratteristica di sistema
        */
       switch (getTipologia()) {
           case "Liquame Bovino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LBovU(getValore());
                       } else {
                           caratteristichechimiche.setM3LBovU(caratteristichechimiche.getM3LBovS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBovS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLBovU(getValore());
                       } else {
                           caratteristichechimiche.setTknLBovU(caratteristichechimiche.getTknLBovS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBovS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLBovU(getValore());
                       } else {
                           caratteristichechimiche.setTanLBovU(caratteristichechimiche.getTanLBovS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBovS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLBovU(getValore());
                       } else {
                           caratteristichechimiche.setDmLBovU(caratteristichechimiche.getDmLBovS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBovS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLBovU(getValore());
                       } else {
                           caratteristichechimiche.setVsLBovU(caratteristichechimiche.getVsLBovS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBovS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLBovU(getValore());
                       } else {
                           caratteristichechimiche.setKLBovU(caratteristichechimiche.getKLBovS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBovS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLBovU(getValore());
                       } else {
                           caratteristichechimiche.setPLBovU(caratteristichechimiche.getPLBovS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBovS());
                       }
                       break;
               }
               break;
           case "Liquame Suino":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LSuiU(getValore());
                       } else {
                           caratteristichechimiche.setM3LSuiU(caratteristichechimiche.getM3LSuiS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LSuiS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTknLSuiU(caratteristichechimiche.getTknLSuiS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLSuiS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTanLSuiU(caratteristichechimiche.getTanLSuiS());
                            //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLSuiS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setDmLSuiU(caratteristichechimiche.getDmLSuiS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLSuiS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setVsLSuiU(caratteristichechimiche.getVsLSuiS());
                           // getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLSuiS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setKLSuiU(caratteristichechimiche.getKLSuiS());
                          // getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLSuiS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLSuiU(getValore());
                       } else {
                           caratteristichechimiche.setPLSuiU(caratteristichechimiche.getPLSuiS());
                          // getDataItemLiq().setFosforototale(caratteristichechimiche.getPLSuiS());
                       }
                       break;
               }
               break;
           case "Liquame Avicolo":
               // setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LAviU(getValore());
                       } else {
                           caratteristichechimiche.setM3LAviU(caratteristichechimiche.getM3LAviS());
                          // getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAviS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLAviU(getValore());
                       } else {
                           caratteristichechimiche.setTknLAviU(caratteristichechimiche.getTknLAviS());
                           // getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAviS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLAviU(getValore());
                       } else {
                           caratteristichechimiche.setTanLAviU(caratteristichechimiche.getTanLAviS());
                          // getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAviS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLAviU(getValore());
                       } else {
                           caratteristichechimiche.setDmLAviU(caratteristichechimiche.getDmLAviS());
                           // getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAviS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLAviU(getValore());
                       } else {
                           caratteristichechimiche.setVsLAviU(caratteristichechimiche.getVsLAviS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAviS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLAviU(getValore());
                       } else {
                           caratteristichechimiche.setKLAviU(caratteristichechimiche.getKLAviS());
                            //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAviS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLAviU(getValore());
                       } else {
                           caratteristichechimiche.setPLAviU(caratteristichechimiche.getPLAviS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAviS());
                       }
                       break;
               }
               break;
           case "Liquame Altro":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LAltU(getValore());
                       } else {
                           caratteristichechimiche.setM3LAltU(caratteristichechimiche.getM3LAltS());
                            //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LAltS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLAltU(getValore());
                       } else {
                           caratteristichechimiche.setTknLAltU(caratteristichechimiche.getTknLAltS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLAltS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLAltU(getValore());
                       } else {
                           caratteristichechimiche.setTanLAltU(caratteristichechimiche.getTanLAltS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLAltS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLAltU(getValore());
                       } else {
                           caratteristichechimiche.setDmLAltU(caratteristichechimiche.getDmLAltS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLAltS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLAltU(getValore());
                       } else {
                           caratteristichechimiche.setVsLAltU(caratteristichechimiche.getVsLAltS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLAltS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLAltU(getValore());
                       } else {
                           caratteristichechimiche.setKLAltU(caratteristichechimiche.getKLAltS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLAltS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLAltU(getValore());
                       } else {
                           caratteristichechimiche.setPLAltU(caratteristichechimiche.getPLAltS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLAltS());
                       }
                       break;
               }
               break;
           case "Liquame Biomassa":
                //setDataItemLiq((ager.Refluo) getDataTableLiq().getRowData());
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3LBioU(getValore());
                       } else {
                           caratteristichechimiche.setM3LBioU(caratteristichechimiche.getM3LBioS());
                           //getDataItemLiq().setMetricubi(caratteristichechimiche.getM3LBioS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknLBioU(getValore());
                       } else {
                           caratteristichechimiche.setTknLBioU(caratteristichechimiche.getTknLBioS());
                           //getDataItemLiq().setAzotototale(caratteristichechimiche.getTknLBioS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanLBioU(getValore());
                       } else {
                           caratteristichechimiche.setTanLBioU(caratteristichechimiche.getTanLBioS());
                           //getDataItemLiq().setAzotoammoniacale(caratteristichechimiche.getTanLBioS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmLBioU(getValore());
                       } else {
                           caratteristichechimiche.setDmLBioU(caratteristichechimiche.getDmLBioS());
                           //getDataItemLiq().setSostanzasecca(caratteristichechimiche.getDmLBioS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsLBioU(getValore());
                       } else {
                           caratteristichechimiche.setVsLBioU(caratteristichechimiche.getVsLBioS());
                           //getDataItemLiq().setSolidivolatili(caratteristichechimiche.getVsLBioS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKLBioU(getValore());
                       } else {
                           caratteristichechimiche.setKLBioU(caratteristichechimiche.getKLBioS());
                           //getDataItemLiq().setPotassiototale(caratteristichechimiche.getKLBioS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPLBioU(getValore());
                       } else {
                           caratteristichechimiche.setPLBioU(caratteristichechimiche.getPLBioS());
                           //getDataItemLiq().setFosforototale(caratteristichechimiche.getPLBioS());
                       }
                       break;
               }
               break;
           case "Letame Bovino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PBovU(getValore());
                       } else {
                           caratteristichechimiche.setM3PBovU(caratteristichechimiche.getM3PBovS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PBovS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBovU(caratteristichechimiche.getTknPBovS());
                           //dataItemLet.setAzotototale(caratteristichechimiche.getTknPBovS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBovU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBovU(caratteristichechimiche.getTanPBovS());
                           //dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBovS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBovU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBovU(caratteristichechimiche.getDmPBovS());
                           //dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBovS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBovU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBovU(caratteristichechimiche.getVsPBovS());
                           //dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBovS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBovU(getValore());
                       } else {
                           caratteristichechimiche.setKPBovU(caratteristichechimiche.getKPBovS());
                           //dataItemLet.setPotassiototale(caratteristichechimiche.getKPBovS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBovU(getValore());
                       } else {
                           caratteristichechimiche.setPPBovU(caratteristichechimiche.getPPBovS());
                           //dataItemLet.setFosforototale(caratteristichechimiche.getPPBovS());
                       }
                       break;
               }
               break;
           case "Letame Suino":
                //dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PSuiU(getValore());
                       } else {
                           caratteristichechimiche.setM3PSuiU(caratteristichechimiche.getM3PSuiS());
                           //dataItemLet.setMetricubi(caratteristichechimiche.getM3PSuiS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTknPSuiU(caratteristichechimiche.getTknPSuiS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPSuiS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setTanPSuiU(caratteristichechimiche.getTanPSuiS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPSuiS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setDmPSuiU(caratteristichechimiche.getDmPSuiS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPSuiS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setVsPSuiU(caratteristichechimiche.getVsPSuiS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPSuiS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setKPSuiU(caratteristichechimiche.getKPSuiS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPSuiS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPSuiU(getValore());
                       } else {
                           caratteristichechimiche.setPPSuiU(caratteristichechimiche.getPPSuiS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPSuiS());
                       }
                       break;
               }
               break;
           case "Letame Avicolo":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PAviU(getValore());
                       } else {
                           caratteristichechimiche.setM3PAviU(caratteristichechimiche.getM3PAviS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAviS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPAviU(getValore());
                       } else {
                           caratteristichechimiche.setTknPAviU(caratteristichechimiche.getTknPAviS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAviS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPAviU(getValore());
                       } else {
                           caratteristichechimiche.setTanPAviU(caratteristichechimiche.getTanPAviS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAviS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPAviU(getValore());
                       } else {
                           caratteristichechimiche.setDmPAviU(caratteristichechimiche.getDmPAviS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAviS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPAviU(getValore());
                       } else {
                           caratteristichechimiche.setVsPAviU(caratteristichechimiche.getVsPAviS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAviS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPAviU(getValore());
                       } else {
                           caratteristichechimiche.setKPAviU(caratteristichechimiche.getKPAviS());
                         //  dataItemLet.setPotassiototale(caratteristichechimiche.getKPAviS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPAviU(getValore());
                       } else {
                           caratteristichechimiche.setPPAviU(caratteristichechimiche.getPPAviS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAviS());
                       }
                       break;
               }
               break;
           case "Letame Altro":
              // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PAltU(getValore());
                       } else {
                           caratteristichechimiche.setM3PAltU(caratteristichechimiche.getM3PAltS());
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PAltS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPAltU(getValore());
                       } else {
                           caratteristichechimiche.setTknPAltU(caratteristichechimiche.getTknPAltS());
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPAltS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPAltU(getValore());
                       } else {
                           caratteristichechimiche.setTanPAltU(caratteristichechimiche.getTanPAltS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPAltS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPAltU(getValore());
                       } else {
                           caratteristichechimiche.setDmPAltU(caratteristichechimiche.getDmPAltS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPAltS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPAltU(getValore());
                       } else {
                           caratteristichechimiche.setVsPAltU(caratteristichechimiche.getVsPAltS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPAltS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPAltU(getValore());
                       } else {
                           caratteristichechimiche.setKPAltU(caratteristichechimiche.getKPAltS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPAltS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPAltU(getValore());
                       } else {
                           caratteristichechimiche.setPPAltU(caratteristichechimiche.getPPAltS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPAltS());
                       }
                       break;
               }
               break;
           case "Letame Biomassa":
               // dataItemLet =(ager.Refluo) getDataTableLet().getRowData();
               switch (getPosizione()) {
                   case 1:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setM3PBioU(getValore());
                       } else {
                          
                           caratteristichechimiche.setM3PBioU(caratteristichechimiche.getM3PBioS());
                         
                          // dataItemLet.setMetricubi(caratteristichechimiche.getM3PBioS());
                       }
                       break;
                   case 2:
                        if (getAzione() == 0) {
                           caratteristichechimiche.setTknPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTknPBioU(caratteristichechimiche.getTknPBioS());
                          
                          // dataItemLet.setAzotototale(caratteristichechimiche.getTknPBioS());
                       }
                       break;
                   case 3:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setTanPBioU(getValore());
                       } else {
                           caratteristichechimiche.setTanPBioU(caratteristichechimiche.getTanPBioS());
                          // dataItemLet.setAzotoammoniacale(caratteristichechimiche.getTanPBioS());
                       }
                       break;
                   case 4:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setDmPBioU(getValore());
                       } else {
                           caratteristichechimiche.setDmPBioU(caratteristichechimiche.getDmPBioS());
                          // dataItemLet.setSostanzasecca(caratteristichechimiche.getDmPBioS());
                       }
                       break;
                   case 5:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setVsPBioU(getValore());
                       } else {
                           caratteristichechimiche.setVsPBioU(caratteristichechimiche.getVsPBioS());
                          // dataItemLet.setSolidivolatili(caratteristichechimiche.getVsPBioS());
                       }
                       break;
                   case 6:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setKPBioU(getValore());
                       } else {
                           caratteristichechimiche.setKPBioU(caratteristichechimiche.getKPBioS());
                          // dataItemLet.setPotassiototale(caratteristichechimiche.getKPBioS());
                       }
                       break;
                   case 7:
                       if (getAzione() == 0) {
                           caratteristichechimiche.setPPBioU(getValore());
                       } else {
                           caratteristichechimiche.setPPBioU(caratteristichechimiche.getPPBioS());
                          // dataItemLet.setFosforototale(caratteristichechimiche.getPPBioS());
                       }
                       break;
               }
               break;
       }
       
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();
      entityManager.persist(caratteristichechimiche);
      tx.commit();
       
      System.out.println(this.getClass().getCanonicalName()+" "+Thread.currentThread().getStackTrace()[1].getMethodName()+ " valore " + getValore() +  " idscenario " +detto.getIdscenario() + "posizione " + getPosizione() + " azione " + getAzione() + " tipologia " + getTipologia());
  
//      if(azione == 1)
//  {
//       int index = this.getDataTable().getRowIndex(); 
//      
//      dataItem =(ager.Refluo) getDataTable().getRowData();
//     
//      dataItem.setAzotototale(0);
//     
//     
//  }
   
   }