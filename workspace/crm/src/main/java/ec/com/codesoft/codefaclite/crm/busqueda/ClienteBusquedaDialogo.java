/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class ClienteBusquedaDialogo implements InterfaceModelFind<Persona>
{

    @Override
    public Vector<String> getColumnas() 
    {
        Vector<String> titulo=new Vector<String>();
        titulo.add("cedula");
        titulo.add("nombre");
        
        return titulo;
      
    }

    @Override
    public List<Persona> getConsulta() 
    {
        ArrayList<Persona> personas = new ArrayList<Persona>();
        PersonaService personaservice = new PersonaService();
        
        return personaservice.buscar();
    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) 
    {
        dato.add(t.getCedula());
        dato.add(t.getNombreSocial());
   
    }
    @Override
    public Boolean buscarObjeto(Persona t, Object valor) 
    {
        if(t.getCedula().equals(valor.toString()))
        {
            return true;
        }   
        else
        {
            return false;
        }       
    }
    
}
