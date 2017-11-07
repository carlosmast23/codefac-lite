/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
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
    private PersonaService personaService;
    private Persona persona;

    public ClienteModel()
    {
        this.personaService = new PersonaService();
    }
    
    
    @Override
    public void grabar() 
    {
        Persona p = new Persona();
        //p.setIdcliente(3);
        p.setNombreSocial(getjTextNombreSocial().getText());
        p.setTipoIdentificacion((String) getjComboIdentificacion().getSelectedItem());
        p.setCedula(Integer.parseInt(getjTextIdentificacion().getText()));
        p.setTipCliente((String) getjComboTipoCliente().getSelectedItem());
        p.setDireccion(getjTextAreaDireccion().getText());
        p.setTelefonoConvencional(getjTextTelefono().getText());
        p.setExtensionTelefono(getjTextExtension().getText());
        p.setTelefonoCelular(getjTextCelular().getText());
        p.setCorreoElectronico(getjTextCorreo().getText());
        
        personaService.grabar(p);
        System.err.println("GRABADO");
       
    }

    @Override
    public void editar() 
    {
        persona.setCedula(Integer.parseInt(getjTextIdentificacion().getText()));
        persona.setNombreSocial(getjTextNombreSocial().getText());
        personaService.editar(persona);
        System.out.println("Se edito correctamente");
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
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
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

    @Override
    public String getURLAyuda() {
        return "https://support.office.com/es-es";
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() {
        ClienteBusquedaDialogo clienteBusquedaDialogo= new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        persona=(Persona) buscarDialogoModel.getResultado();
        getjTextNombreSocial().setText(persona.getNombreSocial());
        getjTextIdentificacion().setText(""+persona.getCedula());
        System.out.println(persona.getCedula());
        System.out.println(persona.getNombreSocial());
    }
    
    @validacionPersonalizadaAnotacion(errorTitulo = "formato cedula incorrecto")
    public boolean validarCedula()
    {
        /*
        if(getjTextField1().getText().equals("hola"))
        {
            return true;
        }*/
        return true;
    }
    
    @validacionPersonalizadaAnotacion(errorTitulo = "formato otra validacion incorrecto")
    public boolean validarOtro()
    {
        /*
        if(getjTextField1().getText().equals("holas"))
        {
            return true;
        }*/
        return true;
    }
    
}
