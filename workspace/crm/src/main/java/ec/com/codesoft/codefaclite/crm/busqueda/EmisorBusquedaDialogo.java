/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.Emisor;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.service.EmisorService;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class EmisorBusquedaDialogo implements InterfaceModelFind<Emisor>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("RUC", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Social", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Comercial", 0.3d));
        titulo.add(new ColumnaDialogo("Direccion Matriz", 0.3d));
        titulo.add(new ColumnaDialogo("Direccion Establecimiento", 0.3d));
        titulo.add(new ColumnaDialogo("Cod Establecimiento", 0.3d));
        
        return titulo;
        
    }


    @Override
    public void agregarObjeto(Emisor t, Vector dato) 
    {
        dato.add(t.getRuc());
        dato.add(t.getRazonSocial());
        dato.add(t.getNomComercial());
        dato.add(t.getDireccionMatriz());
        dato.add(t.getDirEstablecimiento());
        dato.add(t.getCodEstablecimiento());
    }

    @Override
    public Boolean buscarObjeto(Emisor t, Object valor) 
    {
        return t.getRuc().equals(valor.toString());   
    }

    @Override
    public QueryDialog getConsulta(String filter) {       
        String queryString = "SELECT u FROM Emisor u WHERE ";
        queryString+=" ( LOWER(u.razonSocial) like "+filter+" )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        return queryDialog;
    }
    
}
