/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class ProveedorBusquedaDialogo implements InterfaceModelFind<Persona>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo=new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre",0.3d));
        titulo.add(new ColumnaDialogo("Identificacion",0.2d));
        titulo.add(new ColumnaDialogo("Telefono",0.15d));
        titulo.add(new ColumnaDialogo("Extension",0.10d));
        titulo.add(new ColumnaDialogo("Celular",0.10d));
        titulo.add(new ColumnaDialogo("Correo",0.15d));
        
        return titulo;
      
    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) 
    {
        dato.add(t.getRazonSocial());
        dato.add(t.getIdentificacion());
        dato.add(t.getEstablecimientos().get(0).getTelefonoConvencional());
        dato.add(t.getEstablecimientos().get(0).getExtensionTelefono());
        dato.add(t.getEstablecimientos().get(0).getTelefonoCelular());
        dato.add(t.getEstablecimientos().get(0).getCorreoElectronico());
   
    }
    /*
    @Override
    public Boolean buscarObjeto(Persona t, Object valor) 
    {
        if(t.getIdentificacion().equals(valor.toString()))
        {
            return true;
        }   
        else
        {
            return false;
        }       
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {
        //Persona p;
        //p.getTipo();
        String queryString = "SELECT u FROM Persona u WHERE ";
        queryString+=" ( LOWER(u.razonSocial) like ?2 and u.tipo = ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,OperadorNegocioEnum.PROVEEDOR.getLetra());
        queryDialog.agregarParametro(2,filter);
        
        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
