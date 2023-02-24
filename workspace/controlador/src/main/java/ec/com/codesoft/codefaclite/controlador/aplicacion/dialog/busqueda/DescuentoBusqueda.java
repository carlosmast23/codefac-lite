/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class DescuentoBusqueda implements InterfaceModelFind<Descuento>{
    
    private Empresa empresa;
    //private Producto productoFiltro;

    public DescuentoBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    /*public DescuentoBusqueda(Empresa empresa, Producto productoFiltro) {
        this.empresa = empresa;
        this.productoFiltro = productoFiltro;
    }*/
    
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.2d));
        titulo.add(new ColumnaDialogo("Fecha Inicio", 0.3d));
        titulo.add(new ColumnaDialogo("Fecha Fin", 0.3d));
        //titulo.add(new ColumnaDialogo("Stock", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        Descuento descuento;
        //descuento.getEmpresa();
        
        
        
        String queryString=" SELECT u FROM Descuento u where u.estado=?1 and u.empresa=?2 and u.nombre like ?3 ";
                        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
                
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Descuento t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getDescripcion());
        dato.add(t.getFechaInicio());
        dato.add(t.getFechaFin());
        //dato.add(t.getStock());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
