/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class PersonaService extends ServiceAbstract<Persona, PersonaFacade> implements PersonaServiceIf {

    private PersonaFacade personaFacade;

    public PersonaService() throws RemoteException {
        super(PersonaFacade.class);
        this.personaFacade = new PersonaFacade();
    }

    public void editarPersona(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                for (PersonaEstablecimiento establecimiento : p.getEstablecimientos()) {
                    if (establecimiento.getId() == null) {
                        entityManager.persist(establecimiento);
                    } else {
                        entityManager.merge(establecimiento);
                    }
                }
                entityManager.merge(p);
            }
        });
    }

    public Persona grabar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                /**
                 * Validaciones previas de los datos
                 */
                if(!p.validarCedula())
                {
                    throw new ServicioCodefacException("Error al validar la identificación");
                }
                
                if(p.getRazonSocial()==null || p.getRazonSocial().isEmpty())
                {
                    throw new ServicioCodefacException("La razón social no puede ser vacia");
                }
                

                if (p.getEstablecimientos() == null || p.getEstablecimientos().size() == 0) {
                    //Si no tiene un establecimiento lo creo automaticamente 
                    PersonaEstablecimiento personaEstablecimiento = PersonaEstablecimiento.buildFromPersona(p);
                    personaEstablecimiento.setCodigoSucursal("1");
                    personaEstablecimiento.setCorreoElectronico(""); //implementar de forma posterior
                    personaEstablecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
                    //Si no ingreso un nombre comercial se graba como matriz
                    if(personaEstablecimiento.getNombreComercial().isEmpty())
                    {
                        personaEstablecimiento.setNombreComercial(Sucursal.TipoSucursalEnum.MATRIZ.getNombre());
                    }
                    
                    entityManager.persist(personaEstablecimiento);
                    p.addEstablecimiento(personaEstablecimiento);
                }

                p.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                for (PersonaEstablecimiento establecimiento : p.getEstablecimientos()) {
                    entityManager.persist(establecimiento);
                }
                entityManager.persist(p);
            }
        });
        return p;

    }

    public void editar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        personaFacade.edit(p);

    }

    public void eliminar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        //personaFacade.remove(p);
        p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        editar(p);
    }

    public List<Persona> buscar() {
        return personaFacade.findAll();
    }

    public Persona buscarPorIdentificacionYestado(String identificacion, GeneralEnumEstado estado) throws ServicioCodefacException, java.rmi.RemoteException {

        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("identificacion", identificacion);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

        List<Persona> resultados = getFacade().findByMap(mapParametros);
        if (resultados.size() == 0) {
            return null;
        } else {
            return resultados.get(0);
        }

    }

    @Override
    public List<Persona> buscarPorTipo(OperadorNegocioEnum tipoEnum, GeneralEnumEstado estado) throws java.rmi.RemoteException {
        return getFacade().buscarPorTipoFacade(tipoEnum, estado);
    }

    @Override
    public Persona buscarPorIdentificacion(String identificacion) throws java.rmi.RemoteException {
        //Persona p;
        //p.getIdentificacion();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("identificacion", identificacion);

        List<Persona> personas = getFacade().findByMap(mapParametros);
        if (personas.size() > 0) {
            return personas.get(0);
        }

        return null;

    }

}
