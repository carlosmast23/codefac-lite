/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.EmpleadoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class EmpleadoService extends ServiceAbstract<Empleado, EmpleadoFacade> implements EmpleadoServiceIf
{
    private EmpleadoFacade empleadoFacade;
    
    public EmpleadoService() throws RemoteException
    {
        super(EmpleadoFacade.class);
        this.empleadoFacade = new EmpleadoFacade();
    }
    
    /*
    @Override
     public Empleado grabar(Empleado b) throws ServicioCodefacException {
        try {
            empleadoFacade.create(b);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        }
        return b;
    }
    */
    @Override
    public void eliminar(Empleado b) {
        
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    b.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    empleadoFacade.editData(b,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SriIdentificacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<Empleado> buscar() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Empleado> buscarVendedores(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException
    {
        
        return (List<Empleado>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> parametroMap = new HashMap<String, Object>();
                parametroMap.put("departamento.tipo", Departamento.TipoEnum.Ventas.getLetra());
                parametroMap.put("estado", GeneralEnumEstado.ACTIVO.getLetra());

                return getFacade().findByMap(parametroMap,entityManager);
            }
        });
        
    }   
    
    public List<Empleado> buscarPorDepartamento(Departamento departamento,Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException
    {
        return (List<Empleado>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> parametroMap = new HashMap<String, Object>();
                parametroMap.put("departamento", departamento);
                //parametroMap.put("departamento", departamento);
                return getFacade().findByMap(parametroMap,entityManager);
            }
        });
        
    }
    
    //TODO: Terminar de implementar para que funcione tomando en cuenta el parametro de la empresa
    public List<Empleado> buscarActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException
    {
        return (List<Empleado>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParametros,entityManager);
            }
        });
    }
    
    /*public Empleado buscarPorUsuario(Usuario usuario) throws ServicioCodefacException, java.rmi.RemoteException
    {
        Usuario u;
        u.getEmpleado()
        //Empleado empleado;
        //empleado.getEstado()
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }*/
    
}
