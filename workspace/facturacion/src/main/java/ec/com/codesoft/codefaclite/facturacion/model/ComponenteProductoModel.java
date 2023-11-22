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
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collections;
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
        String[] titulo={"Op","Componente"};
        DefaultTableModel modelTabla=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Boolean.class,String.class});
        
        List<ProductoComponenteDetalle> componenteList=producto.getComponenteList();
        for (ProductoComponenteDetalle componente : componenteList) 
        {
            Vector<Object> fila=new Vector<Object>();            
            fila.add(false);
            fila.add(componente.getProductoComponente().getNombre());
            
            modelTabla.addRow(fila);
        }
        
        
        getTblComponentes().setModel(modelTabla);
        
        
    }
    
    public String getResultado()
    {
        List<String> componentesList=new ArrayList<String>();
        for (int i = 0; i < getTblComponentes().getRowCount(); i++) {
            Boolean seleccion= (Boolean) getTblComponentes().getModel().getValueAt(i, 0);
            if(seleccion)
            {
                componentesList.add((String) getTblComponentes().getModel().getValueAt(i,1));
            }
        }
        
        if(componentesList.size()>0)
        {
            //Ordenar la lista en orden alfabetico
            Collections.sort(componentesList);            
            return UtilidadesLista.castListToString(componentesList,",");
        }
        
        return "";
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
