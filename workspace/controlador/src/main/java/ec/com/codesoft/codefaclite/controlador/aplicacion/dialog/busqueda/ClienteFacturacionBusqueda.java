/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ClienteFacturacionBusqueda implements InterfaceModelFind<PersonaEstablecimiento>, InterfacesPropertisFindWeb {

    private Empresa empresa;

    public ClienteFacturacionBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion", 0.15d));
        titulo.add(new ColumnaDialogo("Razón Social ", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Legal", 0.2d));
        titulo.add(new ColumnaDialogo("Telefono", 0.1d));
        titulo.add(new ColumnaDialogo("Celular", 0.1d));

        return titulo;
    }

    @Override
    public void agregarObjeto(PersonaEstablecimiento t, Vector dato) {
        dato.add(t.getPersona().getIdentificacion());
        dato.add(t.getPersona().getRazonSocial());
        dato.add((t.getNombreComercial() != null) ? t.getNombreComercial() : "");
        dato.add(t.getTelefonoConvencional());
        dato.add(t.getTelefonoCelular());
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //PersonaEstablecimiento pe;
        //pe.getNombreComercial();
        //p.getRazonSocial();
        //Persona persona=new Persona();
        String queryFiltroEmpresa=" AND u.persona.empresa=?5";
        Boolean datosCompartidosEmpresas=false;
        try {
            datosCompartidosEmpresas=ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI);           
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteEstablecimientoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (datosCompartidosEmpresas) 
        {
            //Si los datos son compratidos entre empresas entoces no hago ningun filtro
            queryFiltroEmpresa = "";
        }
        
        
        String queryString = "SELECT u FROM PersonaEstablecimiento u WHERE 1=1 "+queryFiltroEmpresa+" AND (u.persona.estado<>?1) AND (u.persona.tipo=?2 OR u.persona.tipo=?3 ) AND ";
        queryString += " ( LOWER(u.persona.razonSocial) like ?4 or LOWER(u.nombreComercial) like ?4 or u.persona.identificacion like ?4 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2, OperadorNegocioEnum.CLIENTE.getLetra());
        queryDialog.agregarParametro(3, OperadorNegocioEnum.AMBOS.getLetra());

        queryDialog.agregarParametro(4, filter.toLowerCase());
        if (!datosCompartidosEmpresas) 
        {
            queryDialog.agregarParametro(5, empresa);
        }
        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("persona.identificacion");
        propiedades.add("persona.razonSocial");
        propiedades.add("nombreComercial");
        propiedades.add("telefonoConvencional");
        propiedades.add("telefonoCelular");
        return propiedades;
    }

}
