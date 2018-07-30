/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class TransportistaBusquedaDialogo implements InterfaceModelFind<Transportista>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        
        Vector<ColumnaDialogo> titulo=new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion",0.15d));
        titulo.add(new ColumnaDialogo("Razón Social ",0.3d));
        titulo.add(new ColumnaDialogo("Apellidos",0.2d));
        titulo.add(new ColumnaDialogo("Nombres",0.2d));
        titulo.add(new ColumnaDialogo("Placa",0.1d));
        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "Select t from Transportista t where";
        queryString += "( LOWER(t.razonSocial) like ?1 or t.identificacion like ?1 or LOWER(t.nombres) like ?1 or LOWER(t.apellidos) like ?1)"; 
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter.toLowerCase());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Transportista t, Vector dato) {
        dato.add(t.getIdentificacion());
        dato.add((t.getRazonSocial() != null) ? t.getRazonSocial() : "");
        dato.add(t.getApellidos());
        dato.add(t.getNombres());
        dato.add(t.getPlacaVehiculo());       
    }
    
    
}
