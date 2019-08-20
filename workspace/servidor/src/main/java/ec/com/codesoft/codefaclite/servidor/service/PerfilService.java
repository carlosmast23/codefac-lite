/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.rmi.RemoteException;
import java.util.List;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class PerfilService extends ServiceAbstract<Perfil,PerfilFacade> implements PerfilServiceIf{

    private PerfilFacade perfilFacade;
    public PerfilService() throws RemoteException 
    {
        super(PerfilFacade.class);
        this.perfilFacade=new PerfilFacade();
    }
    
    
    public Perfil grabar(Perfil entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();
        entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        entityManager.persist(entity);
        transaction.commit();
        return entity;
    }
    
    public void eliminar(Perfil entity) throws java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();        
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaction.commit();
    }
    
    public void editar(Perfil entity)
    {
        try {
            EntityTransaction transaction=getTransaccion();
            transaction.begin();
            
            Perfil perfilSinModificar = buscarPorId(entity.getId());
            for (PermisoVentana permisoVentana : perfilSinModificar.getVentanasPermisos()) {
                //Comprabar si algun objeto fue eliminado
                if (!entity.getVentanasPermisos().contains(permisoVentana)) {
                    entityManager.remove(permisoVentana);
                }
            }            
            
            entityManager.merge(entity);
            transaction.commit();
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Perfil> obtenerPerfilesPorUsuario(Usuario usuario)
    {
        return this.perfilFacade.getPerfilesByUsuario(usuario);
    }
    
    /**
     * Funcion utilizada para que al momento de crear una empresa ya tenga perfil para que pueda asignar a los usuarios
     */
    public void crearPerfilPorDefectoSinTransaccion(Empresa empresa) throws ServicioCodefacException
    {        
        try {
            Perfil perfilDefecto=new Perfil();
            perfilDefecto.setDescripcion("perfil por defecto");
            perfilDefecto.setEmpresa(empresa);
            perfilDefecto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            perfilDefecto.setNombre("defecto");
            
            entityManager.persist(perfilDefecto);
            
            PermisoVentanaService permisoService=new PermisoVentanaService();
            List<PermisoVentana> ventanas=new ArrayList<PermisoVentana>();
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CATEGORIA_PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CATALOGO_PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CONFIGURACION_DEFECTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURA_DISENIO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.NOTA_CREDITO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURA_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.UTILIDAD_COMPROBANTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CLIENTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROVEEDOR.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CLIENTE_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PRODUCTO_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.EMPRESA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.RESPALDAR_INFORMACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PERFILES.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.USUARIOS.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROVEEDOR_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.SUCURSAL.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PUNTO_EMISION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.IMPRIMIR_CODIGO_BARRAS.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CONFIGURACION_DEFECTO.getCodigo()));
            //ventanas.add(e)

            perfilDefecto.setVentanasPermisos(ventanas);
            entityManager.merge(perfilDefecto);
            
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
