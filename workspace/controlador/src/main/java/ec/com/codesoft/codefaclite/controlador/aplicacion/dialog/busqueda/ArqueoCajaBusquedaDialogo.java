/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Robert
 */
public class ArqueoCajaBusquedaDialogo implements InterfaceModelFind<ArqueoCaja>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Tiempo", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Valor ", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Valor letras ", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT ac FROM Arqueo_Caja c WHERE ";
        //queryString+=" ( LOWER(c.nombre) like ?1 and (c.estado) like ?2 )";
        //queryString+=" ((c.estado) like ?2 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        //queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(ArqueoCaja t, Vector dato) {
        dato.add(t.getFechaHora().toString());
        dato.add(t.getValorFisico().toString());
        dato.add(t.getValorTeorico().toString());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("fechaHora");
        propiedades.add("valorTeorico");
        propiedades.add("valorFisico");
        return propiedades;
    }
    
}
