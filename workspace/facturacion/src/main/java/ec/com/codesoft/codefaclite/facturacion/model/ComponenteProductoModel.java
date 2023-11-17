/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponente;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoComponenteDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Frame;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ComponenteProductoModel extends ec.com.codesoft.codefaclite.facturacion.panel.ComponenteProductoDialog
{
    private Producto producto;
    
    public ComponenteProductoModel(Frame parent, boolean modal,Producto producto) {
        super(parent, modal);
        this.producto=producto;
        construirTablaOpciones();
        
    }
    
    
    
    private void construirTablaOpciones()
    {
        String[] titulo={"","Op","Componente"};
        DefaultTableModel modelTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{ProductoComponente.class,Boolean.class,String.class});
        
        List<ProductoComponenteDetalle> componenteList=producto.getComponenteList();
        for (ProductoComponenteDetalle componente : componenteList) 
        {
            Vector<Object> fila=new Vector<Object>();
            fila.add(componente.getProductoComponente());
            fila.add(false);
            fila.add(componente.getProductoComponente().getNombre());
            
            modelTabla.addRow(fila);
        }
        
        
        getTblComponentes().setModel(modelTabla);
        
        
    }
    
    
    ///////////////////////////////
    //      GET AND SET
    //////////////////////////////

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    
}
