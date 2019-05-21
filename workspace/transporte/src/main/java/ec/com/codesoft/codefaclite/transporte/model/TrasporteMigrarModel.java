/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarTrasporte;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class TrasporteMigrarModel extends MigrarModel{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migrar Transporte");
    }
    
    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
         return new ExcelMigrar.MigrarInterface() {
             @Override
             public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                 try {
                     Transportista transportista=new Transportista();
                     transportista.setIdentificacion((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.IDENTIFICACION).valor);
                     transportista.setNombres((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.NOMBRES).valor);
                     transportista.setApellidos((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.APELLIDOS).valor);
                     transportista.setRazonSocial((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.RAZON_SOCIAL).valor);
                     transportista.setNombreComercial((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.NOMBRE_COMERCIAL).valor);
                     transportista.setDireccion((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.DIRECCION).valor);
                     transportista.setTelefonoConvencional((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.TELEFONO).valor);
                     transportista.setTelefonoCelular((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.CELULAR).valor);
                     transportista.setCorreoElectronico((String) fila.getByEnum(ExcelMigrarTrasporte.Enum.CORREO).valor);
                     
                     //Todo: por el momento solo dejo considerado para estos 2 casos
                     if (transportista.getIdentificacion().length() > 10) {
                         transportista.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.RUC);
                     } else {
                         transportista.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.CEDULA);
                     }
                     
                     //Todo: si la razon social es vacia entonces yo me encargo de setear ese campo automaticamente
                     if (transportista.getRazonSocial() == null || transportista.getRazonSocial().isEmpty()) {
                         transportista.setRazonSocial(transportista.getApellidos() + " " + transportista.getNombres());
                     }
                     
                     transportista.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                     
                     ServiceFactory.getFactory().getTransportistaServiceIf().grabar(transportista);
                 } catch (ServicioCodefacException ex) {
                     //Logger.getLogger(TrasporteMigrarModel.class.getName()).log(Level.SEVERE, null, ex);
                     throw new ExcelMigrar.ExcepcionExcel(ex.getMessage());
                 } catch (RemoteException ex) {
                     Logger.getLogger(TrasporteMigrarModel.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 //return false;
             }
         };
    }

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarTrasporte();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Map<Integer, Boolean> permisosFormulario() {
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

    @Override
    public InputStream getInputStreamExcel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
