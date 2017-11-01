/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.main.model.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class UsuarioModeloBusqueda implements InterfaceModelFind<Persona>{

    @Override
    public Vector<String> getColumnas() {
        Vector<String> titulo=new Vector<String>();
        titulo.add(" Campo1");
        titulo.add(" Campo2");
        titulo.add(" Campo3");
        return titulo;
    }

    @Override
    public ArrayList<Persona> getConsulta() {
        ArrayList<Persona> personas=new ArrayList<Persona>();
        Persona p1=new Persona();
        p1.setCedula("17123123");
        p1.setNombre("Carlos");
        personas.add(p1);
        
        p1=new Persona();
        p1.setCedula("1737733");
        p1.setNombre("Pato");
        personas.add(p1);
        
        p1=new Persona();
        p1.setCedula("173888");
        p1.setNombre("Luis");
        personas.add(p1);
        
        p1=new Persona();
        p1.setCedula("1799999");
        p1.setNombre("Pedro");
        personas.add(p1);
        
        p1=new Persona();
        p1.setCedula("179336");
        p1.setNombre("Juan");
        personas.add(p1);
        
        p1=new Persona();
        p1.setCedula("176666");
        p1.setNombre("Robert");
        personas.add(p1);
                
        return personas;
    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) {
        dato.add(t.getCedula());
        
        if(t.getNombre()!=null)
            dato.add(t.getNombre());
        else
            dato.add("No hay nombre ");
    }

    @Override
    public Boolean buscarObjeto(Persona t, Object valor) {
      
        if(t.getNombre().toLowerCase().indexOf(valor.toString().toLowerCase())>=0)
        {
            return true;
        }
        return false;
    }


    
}
