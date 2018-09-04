/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ProformaDetalleData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProformaModel extends FacturacionModel{

    public ProformaModel() {
        super();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        valoresIniciales();
    }
    
    

    private void valoresIniciales() {
        //nombre de la pantalla
        setTitle("Proforma");

        //cargar el documento de proforma
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.PROFORMA);

        //desactivar el panel formas de pago porque no utilizo
        getPanelFormasPago().setVisible(false);     
        
    }

    @Override
    public void cargarSecuencial() {
        try {
            //Obtener los secuenciales para las proformas
            Integer secuencial = ServiceFactory.getFactory().getFacturacionServiceIf().obtenerSecuencialProformas();
            getLblSecuencial().setText(secuencial.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public InterfaceModelFind getBusquedaInterface() {
        return new ProformaBusqueda();
    }

    @Override
    protected void setearValoresDefaultFactura() {
        super.setearValoresDefaultFactura(); //To change body of generated methods, choose Tools | Templates.
        factura.setSecuencial(Integer.parseInt(getLblSecuencial().getText()));
        //facrur
        
    }
    
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            validacionesGrabar(); //Metodo que realiza validaciones previas antes de grabar
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura();
            factura = servicio.grabarProforma(factura);
            DialogoCodefac.mensaje("Correcto","Proforma generada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }        

    }

    @Override
    public void imprimir() 
    {/*
         try {
            InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("proforma.jrxml");
            Map<String,Object> parameters = new HashMap<String,Object>();
            parameters.put("codigo", factura.getSecuencial());
            parameters.put("identificacion", factura.getIdentificacion());
            parameters.put("nombre_cliente", factura.getRazonSocial());
            parameters.put("telefonos", factura.getTelefono());
            parameters.put("direccion", factura.getDireccion());
            //parameters.put("iva_porcentaje", factura.get);
            parameters.put("subtotal_cero", factura.getSubtotalSinImpuestos().toString());
            parameters.put("subtotal_con_iva", factura.getSubtotalImpuestos().toString());
            parameters.put("descuento", factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
            parameters.put("iva_valor", factura.getIva().toString());
            parameters.put("total_valor",factura.getTotal().toString());
            
            
            List<ProformaDetalleData> data = new ArrayList<ProformaDetalleData>();
            
            for(FacturaDetalle facturaDetalle : factura.getDetalles())
            {
                ProformaDetalleData detalle=new ProformaDetalleData();
                detalle.setCodigo(facturaDetalle.getReferenciaId().toString());
                detalle.setCantidad(facturaDetalle.getCantidad().toString());
                detalle.setDescripcion(facturaDetalle.getDescripcion());
                detalle.setDescuento(facturaDetalle.getDescuento().toString());
                detalle.setValorUnitario(facturaDetalle.getPrecioUnitario().toString());
                detalle.setPrecioTotal(facturaDetalle.getTotal().toString());
            }
            
            
            
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Clientes ");
            dispose();
            setVisible(false);

            
            
            PersonaServiceIf service=ServiceFactory.getFactory().getPersonaServiceIf();
            List<Persona> clientes=service.obtenerTodos(); //Todo: Obtener filtrar solo por clientes
            
            for (Persona cliente : clientes) {
                ClienteData clienteData=new ClienteData();
                clienteData.setDireccion(cliente.getDireccion());
                clienteData.setEmail(cliente.getCorreoElectronico());
                clienteData.setIdentificacion(cliente.getIdentificacion());
                clienteData.setNombresCompletos(cliente.getRazonSocial());
                clienteData.setNombreLegal(cliente.getNombreLegal());
                clienteData.setTelefono(cliente.getTelefonoCelular());
                data.add(clienteData);
            }
            
            Collections.sort(data, new Comparator<ClienteData>(){
                public int compare(ClienteData obj1, ClienteData obj2)
                {
                    return obj1.getNombresCompletos().compareTo(obj2.getNombresCompletos());
                }
            });
            
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
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Clientes ");
                    dispose();
                    setVisible(false);
                }
            });
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    
}
