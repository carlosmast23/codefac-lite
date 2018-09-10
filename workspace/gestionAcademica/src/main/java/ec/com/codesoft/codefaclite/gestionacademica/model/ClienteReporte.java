/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.gestionacademica.other.ClienteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author Carlos
 */
public class ClienteReporte extends ControladorCodefacInterface{

    public ClienteReporte() {        
        
    }
    
    private void imprimirReporte() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        try {
            InputStream path = RecursoCodefac.JASPER_ACADEMICO.getResourceInputStream("reporteClientes.jrxml");
            EstudianteInscritoServiceIf na = ServiceFactory.getFactory().getEstudianteInscritoServiceIf();
            PeriodoServiceIf psi = ServiceFactory.getFactory().getPeriodoServiceIf();
            List<Periodo> dataPeriodos = psi.obtenerPeriodoActivo();
            List<EstudianteInscrito> dataEstudiantes = na.obtenerEstudiantesInscritos(null, dataPeriodos.get(0));
            
            List<ClienteData> data = new ArrayList<ClienteData>();
            
            for (EstudianteInscrito est : dataEstudiantes) {
                
                String nombreRepresentante1="s/n";
                String nombreRepresentante2="s/n";
                ClienteData clienteData;
                
                if(est.getEstudiante().getRepresentante()!=null)
                    nombreRepresentante1 = est.getEstudiante().getRepresentante().getNombresCompletos();
                
                if(est.getEstudiante().getRepresentante2() != null)
                    nombreRepresentante2 = est.getEstudiante().getRepresentante2().getNombresCompletos();
                
                    clienteData = new ClienteData();
                    clienteData.setDireccion(est.getEstudiante().getRepresentante().getDireccion());
                    clienteData.setEmail(est.getEstudiante().getRepresentante().getCorreoElectronico());
                    clienteData.setIdentificacion(est.getEstudiante().getRepresentante().getIdentificacion());
                    clienteData.setNombresCompletos(est.getEstudiante().getRepresentante().getRazonSocial());
                    clienteData.setNombreLegal(est.getEstudiante().getRepresentante().getNombreLegal());
                    clienteData.setTelefono(est.getEstudiante().getRepresentante().getTelefonoCelular());
                    clienteData.setNombresCompletosEstudiante(est.getEstudiante().getNombreCompleto());
                    clienteData.setCurso(est.getNivelAcademico().getNombre());
                    data.add(clienteData);
                    
                    if(!nombreRepresentante2.equals("s/n")){
                        clienteData = new ClienteData();
                        clienteData.setDireccion(est.getEstudiante().getRepresentante2().getDireccion());
                        clienteData.setEmail(est.getEstudiante().getRepresentante2().getCorreoElectronico());
                        clienteData.setIdentificacion(est.getEstudiante().getRepresentante2().getIdentificacion());
                        clienteData.setNombresCompletos(est.getEstudiante().getRepresentante2().getRazonSocial());
                        clienteData.setNombreLegal(est.getEstudiante().getRepresentante2().getNombreLegal());
                        clienteData.setTelefono(est.getEstudiante().getRepresentante2().getTelefonoCelular());
                        clienteData.setNombresCompletosEstudiante("");
                        clienteData.setCurso(est.getNivelAcademico().getNombre());
                        data.add(clienteData);
                    }

            }
            
            Map parameters = new HashMap();
            
//            Collections.sort(data, new Comparator<ClienteData>(){
//                public int compare(ClienteData obj1, ClienteData obj2)
//                {
//                    return obj1.getNombresCompletos().compareTo(obj2.getNombresCompletos());
//                }
//            });
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Identificación", "Nombres completos","Nombre Legal", "Telefono", "Dirección","Email"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Academico-Clientes ", OrientacionReporteEnum.HORIZONTAL);
                    dispose();
                    setVisible(false);
                }
            });
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Cliente Reporte";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite{
        try {
            imprimirReporte();
        } catch (IOException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new ExcepcionCodefacLite("Cerrar Ventan");
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
