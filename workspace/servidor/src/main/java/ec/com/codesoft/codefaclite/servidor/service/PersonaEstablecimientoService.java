/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PersonaEstablecimientoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaEstablecimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author Carlos
 */
public class PersonaEstablecimientoService extends ServiceAbstract<PersonaEstablecimiento,PersonaEstablecimientoFacade> implements PersonaEstablecimientoServiceIf {

    public PersonaEstablecimientoService() throws RemoteException {
        super(PersonaEstablecimientoFacade.class);
    }
 
    public List<PersonaEstablecimiento> buscarActivoPorIdentificacion(String identificacion, Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException 
    {
        //PersonaEstablecimiento pe;
        //pe.getPersona().getEstadoEnum()
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("persona.identificacion", identificacion);
        mapParametros.put("persona.empresa", empresa);
        mapParametros.put("persona.estado", GeneralEnumEstado.ACTIVO.getEstado());
        
        return  getFacade().findByMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
    }
}
