/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class ObjetoMantenimientoBusqueda implements InterfaceModelFind<ObjetoMantenimiento>,InterfacesPropertisFindWeb,Serializable{
    
    private Empresa empresa;
    
    public ObjetoMantenimientoBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("VIN", 0.4d));
        titulo.add(new ColumnaDialogo("Código", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.2d));        
        //titulo.add(new ColumnaDialogo("Stock", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //ObjetoMantenimiento objeto;
        //objeto.getEstado()
        //objeto.getVin()
        //String queryString=" SELECT u FROM ObjetoMantenimiento u where u.estado=?1 AND u.empresa=?2 AND ( LOWER(u.nombre) like ?3 OR LOWER(u.codigo) like ?3 OR LOWER(u.vin) like ?3 ) ";
        String queryString=" SELECT u FROM ObjetoMantenimiento u WHERE 1=1 AND u.estado=?1 AND ( LOWER(u.nombre) like ?3 OR LOWER(u.codigo) like ?3 OR LOWER(u.vin) like ?3 ) ";
        

        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        //queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        

        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(ObjetoMantenimiento t, Vector dato) {
        dato.add(t.getVin());     
        dato.add(t.getCodigo());        
        dato.add(t.getNombre());        
        dato.add(t.getDescripcion());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("vin"); 
        propiedades.add("codigo");
        propiedades.add("nombre");
        propiedades.add("descripcion");
        return propiedades;
    }
    
}
