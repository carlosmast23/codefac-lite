/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.util.Vector;

/**
 * TODO: Ver como unificar el dialogo con la factura y otras que usan similares
 * @author PC
 */
public class ClienteBusquedaDialogo implements InterfaceModelFind<Persona>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo=new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion",0.15d));
        titulo.add(new ColumnaDialogo("Razón Social ",0.3d));
        titulo.add(new ColumnaDialogo("Nombre Legal",0.2d));
        titulo.add(new ColumnaDialogo("Telefono",0.1d));
        titulo.add(new ColumnaDialogo("Celular",0.1d));
        
        return titulo;
      
    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) 
    {
        dato.add(t.getIdentificacion());
        dato.add(t.getRazonSocial());
        dato.add((t.getNombreLegal()!=null)?t.getNombreLegal():"");
        dato.add(t.getTelefonoConvencional());       
        dato.add(t.getTelefonoCelular());
   
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Persona p;
        //p.getNombreLegal();
        String queryString = "SELECT u FROM Persona u WHERE ";
        queryString+=" ( LOWER(u.razonSocial) like ?1 or u.identificacion like ?1 or LOWER(u.nombreLegal) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter.toLowerCase());
        return queryDialog;
    }
    
}
