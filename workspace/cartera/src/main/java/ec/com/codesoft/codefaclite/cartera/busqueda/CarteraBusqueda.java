/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class CarteraBusqueda implements InterfaceModelFind<Cartera> 
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Preimpreso", 0.15d));
        titulo.add(new ColumnaDialogo("Documento", 0.3d));
        titulo.add(new ColumnaDialogo("Identificacion", 0.15d));
        titulo.add(new ColumnaDialogo("Nombre Completo", 0.15d));
        titulo.add(new ColumnaDialogo("Saldo", 0.1d));
        titulo.add(new ColumnaDialogo("Total", 0.2d));
        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Cartera cartera;
        //cartera.getSecuencial();
        //cartera.getPersona().getRazonSocial();
        //cartera.getPreimpreso();
        String queryString = "SELECT u FROM Cartera u WHERE ";
        queryString += " ( LOWER(u.persona.razonSocial) like ?1 or u.persona.identificacion like ?1 or u.secuencial like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter.toLowerCase());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Cartera t, Vector dato) {
        dato.add(t.getPreimpreso());
        dato.add((t.getCarteraDocumentoEnum()!=null)?t.getCarteraDocumentoEnum().getNombre():"");
        dato.add(t.getPersona().getIdentificacion());
        dato.add(t.getPersona().getNombresCompletos());
        dato.add(t.getSaldo());
        dato.add(t.getTotal());
    }
    
}
