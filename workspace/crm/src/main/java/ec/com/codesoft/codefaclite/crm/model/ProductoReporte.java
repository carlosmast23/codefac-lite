/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.crm.data.ClienteData;
import ec.com.codesoft.codefaclite.crm.data.ProductoData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.InputStream;
import java.net.URL;
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
public class ProductoReporte extends ControladorCodefacInterface{

    public ProductoReporte() {        
        
    }
    
    private void imprimirReporte()
    {
        try {
            InputStream path = RecursoCodefac.JASPER_CRM.getResourceInputStream("reporteProducto.jrxml");
            Map parameters = new HashMap();
            List<ProductoData> data = new ArrayList<ProductoData>();
            ProductoServiceIf service=ServiceFactory.getFactory().getProductoServiceIf();
            List<Producto> productos=service.obtenerTodos();
            
            for (Producto producto : productos) {
                ProductoData productoData=new ProductoData();
                productoData.setCodigoPrincipal(producto.getCodigoPersonalizado());
                productoData.setImpuestoIva(producto.getCatalogoProducto().getIva().getNombre());
                productoData.setNombre(producto.getNombre());
                productoData.setTipoProducto(producto.getCatalogoProducto().getTipoProducto());
                productoData.setValorUnitario(producto.getValorUnitario().toString());
                data.add(productoData);
            }
            setClosable(true);
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Productos");
            //this.dispose();
            //this.setVisible(false);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoReporte.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public String getNombre() {
        return "Producto Reporte";
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
        imprimirReporte();
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
    
}
