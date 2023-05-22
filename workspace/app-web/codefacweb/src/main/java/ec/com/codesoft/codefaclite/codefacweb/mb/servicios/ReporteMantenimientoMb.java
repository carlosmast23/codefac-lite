/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.servicios;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesReporteWeb;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mantenimiento.MantenimientoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.MantenimientoResult;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class ReporteMantenimientoMb extends GeneralAbstractMb implements Serializable
{
    private List<MantenimientoEnum> estadoMantenimietoList;
    private List<MantenimientoResult> mantenimientoList;
    
    private java.util.Date fechaInicial;
    private java.util.Date fechaFinal;

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        //todo
        if(!UtilidadesLista.verificarListaVaciaONull(mantenimientoList))
        {
            InputStream path =  RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("mantenimientoReporte.jrxml");
            JasperPrint jasperPrint = ReporteCodefac.construirReporte(
                    path, new HashMap<String, Object>(),
                    MantenimientoResult.convertirDataReporte(mantenimientoList), 
                    sessionMb.getSession(), 
                    "Listado de mantemientos", 
                    OrientacionReporteEnum.HORIZONTAL, 
                    FormatoHojaEnum.A4);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
            UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint, "Reporte_mantenimientos.pdf");
        /*Map<String, Object>mapParametros = ReporteCodefac.mapReportePlantilla(OrientacionReporteEnum.HORIZONTA*/
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        mantenimientoList=new ArrayList<MantenimientoResult>();
        //estadoMantenimietoList=UtilidadesLista.obtenerDatoMayor(, comparador) Mantenimiento.MantenimientoEnum.values();
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void consultarMantenimientos()
    {
        try {
            mantenimientoList=ServiceFactory.getFactory().getMantenimientoServiceIf().consultarMantenimiento(fechaInicial,fechaFinal);
            System.out.println("Datos consultados: "+mantenimientoList.size());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteMantenimientoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<MantenimientoResult> getMantenimientoList() {
        return mantenimientoList;
    }

    public void setMantenimientoList(List<MantenimientoResult> mantenimientoList) {
        this.mantenimientoList = mantenimientoList;
    }

    public List<MantenimientoEnum> getEstadoMantenimietoList() {
        return estadoMantenimietoList;
    }

    public void setEstadoMantenimietoList(List<MantenimientoEnum> estadoMantenimietoList) {
        this.estadoMantenimietoList = estadoMantenimietoList;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    

    
    
}
