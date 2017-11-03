/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author PC
 */
public class ClienteModel extends ClienteForm
{

    @Override
    public void grabar() {
        System.out.println("estoy grabando el formulario de robert");
    }

    @Override
    public void editar() 
    {
        ClienteBusquedaDialogo clienteBusquedaDialogo= new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Persona p=(Persona) buscarDialogoModel.getResultado();
        System.out.println(p.getCedula());
        System.out.println(p.getNombre());
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_EDITAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        return permisos;
    }


    @Override
    public void imprimir() {
            String path=RecursoCodefac.JASPER_CRM.getResourcePath("reporteEjemplo.jrxml");
            
            Map parameters = new HashMap();
            parameters.put("nombre","carlos");
          
            List<DataEjemploReporte> data= new ArrayList<DataEjemploReporte>();
            data.add(new DataEjemploReporte("carlos","1"));
            data.add(new DataEjemploReporte("pedro","2"));
            ReporteCodefac.generarReporteInternalFrame(path, parameters, data, panelPadre, "Reporte Nuevos ");    
    }
    
    
}
