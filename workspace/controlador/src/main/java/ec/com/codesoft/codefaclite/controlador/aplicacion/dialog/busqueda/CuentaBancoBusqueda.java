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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.CuentaBanco;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class CuentaBancoBusqueda implements InterfaceModelFind<CuentaBanco>{
    
    private Empresa empresa;

    public CuentaBancoBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Cuenta", 0.2d));
        titulo.add(new ColumnaDialogo("Banco", 0.2d));
        titulo.add(new ColumnaDialogo("Tipo", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {        
        //CuentaBanco cuentaBanco;
        //cuentaBanco.getNumeroCuenta();
        String queryString=" SELECT u FROM CuentaBanco u where u.estado=?1 and u.banco.empresa=?2 and ( u.numeroCuenta LIKE ?3 or u.numeroCuenta LIKE ?3 ) ";
        
       
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        
       
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CuentaBanco t, Vector dato) {
        dato.add(t.getNumeroCuenta());
        dato.add(t.getBanco().getNombre());
        dato.add(t.getTipoCuenta());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
