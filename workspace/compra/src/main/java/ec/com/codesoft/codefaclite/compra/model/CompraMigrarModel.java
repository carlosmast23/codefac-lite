/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarCompras;
import ec.com.codesoft.codefaclite.controlador.model.MigrarModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class CompraMigrarModel extends MigrarModel{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        setTitle("Migrar Compras");
    }

    
    
    @Override
    public ExcelMigrar.MigrarInterface getInterfaceMigrar() {
        return new ExcelMigrar.MigrarInterface() {
            @Override
            public void procesar(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado {
                
                validacionDatos(fila);
                                                
            }
        };
    }
    
    public Compra construirCompra(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel
    {
        Compra compra = new Compra();
        compra.setAutorizacion((String) fila.getByEnum(ExcelMigrarCompras.Enum.AUTORIZACION).valor);
        compra.setObservacion((String) fila.getByEnum(ExcelMigrarCompras.Enum.OBSERVACION).valor);    
        compra.setProveedor(buscarCliente((String) fila.getByEnum(ExcelMigrarCompras.Enum.IDENTICACION).valor));
        compra.setSecuencial((Integer) fila.getByEnum(ExcelMigrarCompras.Enum.SECUENCIAL).valor);    
        compra.setPuntoEmision((Integer) fila.getByEnum(ExcelMigrarCompras.Enum.PUNTO_EMISION).valor);    
        compra.setPuntoEstablecimiento(new BigDecimal(fila.getByEnum(ExcelMigrarCompras.Enum.PUNTO_ESTABLECIMIENTO).valor.toString()));    
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        
        compra.setSubtotalImpuestos((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.SUBTOTAL_IVA).valor);    
        compra.setSubtotalSinImpuestos((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.SUBTOTAL_SIN_IVA).valor);    
        compra.setDescuentoImpuestos((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.DESCUENTO_IVA).valor);    
        compra.setDescuentoSinImpuestos((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.DESCUENTO_SIN_IVA).valor); 
        compra.setIva((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.IVA).valor); 
        compra.setTotal((BigDecimal) fila.getByEnum(ExcelMigrarCompras.Enum.TOTAL).valor); 
        
        
        String fechaCreacionStr=(String) fila.getByEnum(ExcelMigrarCompras.Enum.AUTORIZACION).valor;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");        
        compra.setFechaFactura(UtilidadesFecha.getFechaHoy());
        try {
            compra.setFechaCreacion(new java.sql.Date(format.parse(fechaCreacionStr).getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(CompraMigrarModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcelMigrar.ExcepcionExcel("Error con el formato de la fecha");
        }
        
        return compra;
    }
    
    public void validacionDatos(ExcelMigrar.FilaResultado fila) throws ExcelMigrar.ExcepcionExcel, ExcelMigrar.ExcepcionExcelRegistroDuplicado
    {
        //Validar si existe el cliente en la base de datos
        String identificacion =(String)fila.getByEnum(ExcelMigrarCompras.Enum.IDENTICACION).valor;
        
        if(identificacion==null || identificacion.isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("El campo identificacion es obligatorio");
        }
        
        if (buscarCliente(identificacion) == null) {
            throw new ExcelMigrar.ExcepcionExcel("El proveedor no esta creado en el sistema");
        }
        
        String autorizacion=(String)fila.getByEnum(ExcelMigrarCompras.Enum.AUTORIZACION).valor;
        if(autorizacion==null || autorizacion.isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("El campo autorizacion es obligatorio");
        }
        
        String fecha=(String)fila.getByEnum(ExcelMigrarCompras.Enum.FECHA).valor;
        if(autorizacion==null || autorizacion.isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("El campo autorizacion es obligatorio");
        }
        
        
        
    }
    
    private Persona buscarCliente(String cedula)
    {
        try {
            return ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(cedula,session.getEmpresa());
        } catch (RemoteException ex) {
            Logger.getLogger(CompraMigrarModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ExcelMigrar getExcelMigrar() {
        return new ExcelMigrarCompras();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void limpiar() {
        
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
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream getInputStreamExcel() {
        return RecursoCodefac.PLANTILLAS_EXCEL.getResourceInputStream("compras.xlsx");
    }
    
    
}
