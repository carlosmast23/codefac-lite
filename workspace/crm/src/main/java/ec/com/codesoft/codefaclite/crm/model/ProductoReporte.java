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
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ProductoReporte extends ControladorCodefacInterface{

    public ProductoReporte() {        
        
    }
    
    private void imprimirReporte()
    {
        String path = RecursoCodefac.JASPER_CRM.getResourcePath("reporteProducto.jrxml");
        Map parameters = new HashMap();
        List<ProductoData> data = new ArrayList<ProductoData>();
        ProductoService service=new ProductoService();
        List<Producto> productos=service.obtenerTodos();
        
        for (Producto producto : productos) {
            ProductoData productoData=new ProductoData();
            productoData.setCodigoPrincipal(producto.getCodigoPrincipal());
            productoData.setImpuestoIva(producto.getIva().getNombre());
            productoData.setNombre(producto.getNombre());
            productoData.setTipoProducto(producto.getTipoProducto());
            productoData.setValorUnitario(producto.getValorUnitario().toString());
            data.add(productoData);
        }
        
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Productos");
        this.dispose();
        this.setVisible(false);
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
    public void iniciar() {
        imprimirReporte();
    }
    
}
