/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.servicio;

import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import static ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.getDetalleDataReporte;
import static ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.getMapParametrosReporteProforma;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class PresupuestoControlador {
    
    private static Map<String,Object> obtenerMapReporte(Presupuesto presupuesto)
    {
        Map<String,Object> parametros=new HashMap<String,Object>();
        parametros.put("identificacion", presupuesto.getPersona().getIdentificacion());
        parametros.put("razonSocial", presupuesto.getPersona().getRazonSocial());
        parametros.put("telefonos",(presupuesto.getPersona().getEstablecimientos().get(0).getTelefonoCelular()!=null )?presupuesto.getPersona().getEstablecimientos().get(0).getTelefonoCelular():"");
        parametros.put("direccion", (presupuesto.getPersona().getEstablecimientos().get(0).getDireccion()!=null)?presupuesto.getPersona().getEstablecimientos().get(0).getDireccion():"");
        parametros.put("estado", presupuesto.getEstadoEnum().getNombre());
        parametros.put("fecha", presupuesto.getFechaPresupuesto().toString());
        parametros.put("correo", (presupuesto.getPersona().getCorreoElectronico()!=null)?presupuesto.getPersona().getCorreoElectronico():"");
        parametros.put("codigo", presupuesto.getId().toString());
        
        String objetoMantenimiento="";
        String kilometraje="";
        if(presupuesto.obtenerObjectoMantenimiento()!=null)
        {
            objetoMantenimiento=presupuesto.obtenerObjectoMantenimiento().toString();
            kilometraje=presupuesto.obtenerObjectoMantenimiento().getKilometraje()+"";
        }
        
        parametros.put("objectoMantenimiento", objetoMantenimiento);
        parametros.put("kilometraje", kilometraje);
        
        parametros.put("notas", presupuesto.getOrdenTrabajoDetalle().getNotas());
        
        //Datos de la orden de trabajo
        parametros.put("ordenTrabajo", presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getId().toString());
        parametros.put("descripcion", presupuesto.getDescripcion());
        parametros.put("observaciones", presupuesto.getObservaciones());
        
        //Calcular los totales
        parametros.put("subtotal", presupuesto.getTotalVenta().toString());
        parametros.put("descuento", presupuesto.getDescuentoVenta().toString());
        parametros.put("total", presupuesto.calcularTotalMenosDescuentos().toString());
        
        //Calcular los totales
        Presupuesto.ResultadoTotales totales=presupuesto.obtenerMapReporteTotales(presupuesto.getEmpresa().getIdentificacion());
        
        parametros.put("valorPagarCliente", totales.valorPagarCliente.toString());
        parametros.put("valorProveedores", totales.valoresProveedores.toString());
        parametros.put("produccionInterna", totales.produccionInterna.toString());
        parametros.put("utilidad", totales.utilidad.toString());
        
        return parametros ;
    }
    
    private static List<PresupuestoData> getDetalleDataReporte(Presupuesto presupuesto)
    {
        List<PresupuestoData> datos=new ArrayList<PresupuestoData>();
        if(presupuesto.getPresupuestoDetalles()!=null)
        {
            for (PresupuestoDetalle presupuestoDetalle : presupuesto.getPresupuestoDetalles()) {
                PresupuestoData presupuestoData=new PresupuestoData();
                presupuestoData.setCodigo(presupuestoDetalle.getProducto().getCodigoPersonalizado());
                presupuestoData.setCantidad(presupuestoDetalle.getCantidad().toString());
                presupuestoData.setDescuento(presupuestoDetalle.getDescuentoCompra().toString());
                presupuestoData.setIdentificacion(presupuestoDetalle.getPersona().getIdentificacion());
                presupuestoData.setRazonSocial(presupuestoDetalle.getPersona().getRazonSocial());
                presupuestoData.setProducto(presupuestoDetalle.getProductoProveedor().getProducto().getNombre());
                presupuestoData.setSubtotal(presupuestoDetalle.calcularSubtotalCompra().toString());
                presupuestoData.setTotal(presupuestoDetalle.calcularTotalCompra().toString());
                presupuestoData.setCategoria(presupuestoDetalle.getProducto().getTipoProductoEnum().getNombre());
                
                datos.add(presupuestoData);
            }
            
            //Ordenar los datos en función del tipo de servicio
            UtilidadesLista.ordenarLista(datos, new Comparator<PresupuestoData>() {
                @Override
                public int compare(PresupuestoData dato1, PresupuestoData dato2) {
                    return dato1.getCategoria().compareTo(dato2.getCategoria());
                }
            });
            
        }
        return datos;
    }
    
    public static JasperPrint getReporteJasperPresupuesto(Presupuesto presupuesto)
    {
        String nombreReporte="presupuesto.jrxml";
        Map<String, Object> mapParametros = obtenerMapReporte(presupuesto);
        List<PresupuestoData> dataReporte = getDetalleDataReporte(presupuesto);
        //Por el momento queda seleccionada la sucursal por defectp
        Sucursal sucursal=presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo().getCliente().getEmpresa().getSucursales().get(0);
        return ReporteCodefac.generarReporteInternalFramePlantillaReturn(
                sucursal, 
                presupuesto.getUsuario(), 
                RecursoCodefac.JASPER_SERVICIO, 
                nombreReporte, 
                mapParametros, 
                dataReporte, 
                "Presupuesto", 
                OrientacionReporteEnum.VERTICAL, 
                FormatoHojaEnum.A4, 
                ConfiguracionImpresoraEnum.SELECCIONAR_IMPRESORA);
    }
    

    
}
