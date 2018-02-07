/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class ClienteFacturacionBusqueda implements InterfaceModelFind<Persona> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
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
    public void agregarObjeto(Persona t, Vector dato) {
        dato.add(t.getRazonSocial());
        dato.add(t.getIdentificacion());
        dato.add(t.getTelefonoConvencional());
        dato.add(t.getExtensionTelefono());
        dato.add(t.getTelefonoCelular());
        dato.add(t.getCorreoElectronico());
    }

    @Override
    public Boolean buscarObjeto(Persona t, Object valor) {
        if(t.getIdentificacion().equals(valor.toString()))
        {
            return true;
        }   
        else
        {
            return false;
        }    
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //p.getRazonSocial();
        String queryString = "SELECT u FROM Persona u WHERE (u.estado<>?1) AND u.tipo=?2 AND ";
        queryString+=" ( LOWER(u.razonSocial) like "+filter+" )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,ClienteEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,OperadorNegocioEnum.CLIENTE.getLetra());
        return queryDialog;
    }
    
}
