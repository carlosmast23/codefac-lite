/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.Banco;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class BancoBusqueda implements InterfaceModelFind<Banco>{
    
    private Empresa empresa;
    private Banco bancoFiltro;

    public BancoBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    public BancoBusqueda(Empresa empresa, Banco bancoFiltro) {
        this.empresa = empresa;
        this.bancoFiltro = bancoFiltro;
    }
    
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Código", 0.2d));
        titulo.add(new ColumnaDialogo("Código Multicash", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {        
        
        String queryString=" SELECT u FROM Banco u where u.estado=?1 and u.empresa=?2 and ( u.codigo LIKE ?3 or u.nombre LIKE ?3 ) ";
        
        if(bancoFiltro!=null)
        {
            queryString+=" and u.producto= ?4 ";
        }
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        
        if(bancoFiltro!=null)
        {
            queryDialog.agregarParametro(4, bancoFiltro);
        }
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Banco t, Vector dato) {
        dato.add(t.getCodigo());
        dato.add(t.getCodigoMulticash());
        dato.add(t.getNombre());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
