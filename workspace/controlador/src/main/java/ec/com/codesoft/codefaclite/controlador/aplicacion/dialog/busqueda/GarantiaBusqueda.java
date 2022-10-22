/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Garantia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class GarantiaBusqueda implements InterfaceModelFind<Garantia>{
    
    private Empresa empresa;
    //private Producto productoFiltro;

    public GarantiaBusqueda(Empresa empresa) 
    {
        this.empresa = empresa;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Código", 0.2d));
        titulo.add(new ColumnaDialogo("Producto", 0.2d));
        titulo.add(new ColumnaDialogo("Fecha Elaboración", 0.3d));
        titulo.add(new ColumnaDialogo("Fecha Caducidad", 0.3d));
        //titulo.add(new ColumnaDialogo("Stock", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //Garantia garantia;
        //garantia.
        String queryString=" SELECT u FROM Garantia u where u.estado=?1 ";
        
        
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        /*queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        
        if(productoFiltro!=null)
        {
            queryDialog.agregarParametro(4, productoFiltro);
        }*/
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Garantia t, Vector dato) {
        dato.add(t.getId());
        dato.add(t.getDescripcionIngreso());
        dato.add(t.getFechaIngreso());
        dato.add(t.getEstado());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
