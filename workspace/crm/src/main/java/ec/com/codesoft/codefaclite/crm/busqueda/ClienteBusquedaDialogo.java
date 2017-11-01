/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.util.ArrayList;
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
    public ArrayList<Persona> getConsulta() 
    {
        ArrayList<Persona> personas=new ArrayList<Persona>();
        Persona p=new Persona();
        p.setCedula("1707641427");
        p.setNombre("Carlos");
        Persona p1=new Persona();
        p1.setCedula("2307641427");
        p1.setNombre("Alfonso");
        Persona p2=new Persona();
        p2.setCedula("9807641427");
        p2.setNombre("Ñauñay");
        
        personas.add(p);
        personas.add(p1);
        personas.add(p2);
        
        return personas;
    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) 
    {
        dato.add(t.getCedula());
        dato.add(t.getNombre());
   
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
