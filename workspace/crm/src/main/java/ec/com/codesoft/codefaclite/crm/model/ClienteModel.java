/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PC
 */
public class ClienteModel extends ClienteForm
{

    @Override
    public void grabar() {
        System.out.println("estoy grabando el formulario de robert");
    }

    @Override
    public void editar() 
    {
        ClienteBusquedaDialogo clienteBusquedaDialogo= new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Persona p=(Persona) buscarDialogoModel.getResultado();
        System.out.println(p.getCedula());
        System.out.println(p.getNombre());
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_EDITAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        return permisos;
    }
    
}
