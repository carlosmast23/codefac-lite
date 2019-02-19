/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class ProveedorBusquedaDialogo implements InterfaceModelFind<PersonaEstablecimiento>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo=new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion",0.2d));
        titulo.add(new ColumnaDialogo("Razón Social",0.3d));        
        titulo.add(new ColumnaDialogo("Nombre Legal",0.3d));        
        titulo.add(new ColumnaDialogo("Telefono",0.15d));
        titulo.add(new ColumnaDialogo("Celular",0.10d));
        
        return titulo;
      
    }

    @Override
    public void agregarObjeto(PersonaEstablecimiento t, Vector dato) 
    {
        dato.add(t.getPersona().getIdentificacion());
        dato.add(t.getPersona().getRazonSocial());    
        dato.add(t.getNombreComercial());  
        dato.add(t.getTelefonoConvencional());
        dato.add(t.getExtensionTelefono());
        dato.add(t.getTelefonoCelular());
   
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
        PersonaEstablecimiento p;
        //p.getTipo();
        String queryString = "SELECT u FROM PersonaEstablecimiento u WHERE ";
        queryString+=" ( (LOWER(u.nombreComercial) like ?3 or u.persona.identificacion like ?5 or  LOWER(u.persona.razonSocial) like ?2 ) and (u.persona.tipo = ?1 or u.persona.tipo = ?4 ) )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,OperadorNegocioEnum.PROVEEDOR.getLetra());
        queryDialog.agregarParametro(4,OperadorNegocioEnum.AMBOS.getLetra());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,filter);
        queryDialog.agregarParametro(5,filter);
        
        return queryDialog;
    }
    
}
