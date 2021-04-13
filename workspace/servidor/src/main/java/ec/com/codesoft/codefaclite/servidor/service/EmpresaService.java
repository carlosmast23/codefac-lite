/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.EmpresaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class EmpresaService extends ServiceAbstract<Empresa, EmpresaFacade> implements EmpresaServiceIf
{
    private EmpresaFacade empresaFacade;
    
    public EmpresaService() throws RemoteException 
    {        
        super(EmpresaFacade.class);
        this.empresaFacade = new EmpresaFacade();
    }
    
    public Empresa grabar(Empresa p) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(p);
            }
        });
        
        return p;
    }
    
    private void grabarSinTransaccion(Empresa p) throws ServicioCodefacException, RemoteException
    {
        //Grabar la empresa        
        p.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        entityManager.persist(p);

        //Grabar un perfil por defecto
        PerfilService perfilService = new PerfilService();
        perfilService.crearPerfilPorDefectoSinTransaccion(p);

        //Grabar el usuario de consumidor final por defecto
        PersonaService personaService = new PersonaService();
        personaService.crearConsumidorFinalSinTransaccion(p);

        //Grabar parametros por defecto
        ParametroCodefacService parametroService = new ParametroCodefacService();
        parametroService.crearParametroPorDefectoEmpresaSinTrasaccion(p);

        //TODO: Por el momento no puedo crear una bodega por defecto en este punto por que necesito una sucursal
        //BodegaService bodegaService=new BodegaService();
        //bodegaService.buscarPorNombre(nombre);    
    }
    
    
    
    /*public void editar(Empresa p)
    {
        try
        {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    entityManager.merge(p);
                }
            });
        }
        catch(ServicioCodefacException e)
        {
            e.printStackTrace();
        }
        
    }*/
    
    public void eliminar(Empresa p) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //Empresa empresa;
        //empresa.getEstado();
        //p.setEstadoEnum(GeneralEnumEstado.);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                p.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(p);
            }
        });
        //empresaFacade.remove(p);
    }
    
    public List<Empresa> buscar()
    {
        return empresaFacade.findAll();
    } 
    
    public Empresa buscarPorIdentificacion(String identificacion) throws RemoteException 
    {
        //Empresa empresa;       
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("identificacion", identificacion);
        List<Empresa> empresas=getFacade().findByMap(mapParametros);
        if(empresas.size()>0)
        {
            return empresas.get(0);
        }
        return null;
    }
    
    public List<Empresa> obtenerTodosActivos(OrdenarEnum ordenarEnum) throws RemoteException
    {
        /*getFacade().
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        List<Empresa> empresas=getFacade().findByMap(mapParametros);*/
        return getFacade().obtenerTodosActivosFacade(ordenarEnum);
        
    }
    
    public Empresa grabarConfiguracionInicial(Empresa empresa,Sucursal sucursal,PuntoEmision puntoEmision,Usuario usuario,List<ParametroCodefac> parametros) throws RemoteException, ServicioCodefacException
    {
        return (Empresa) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion() throws ServicioCodefacException, RemoteException {
                //System.out.println("Grabado la sucursal"+sucursal);
                //Grabando primero la EMPRESA
                empresa.setCodigo("COD");
                grabarSinTransaccion(empresa);
                
                
                //Grabar la SUCURSAL
                agregarDatosDefectoSucursal(sucursal, empresa);
                SucursalService sucursalService=new SucursalService();
                sucursalService.grabarSinTransaccion(sucursal);
                
                
                //Grabar el PUNTO DE EMISION completando los datos por DEFECTO que faltaban
                agregarDatosPuntoEmisionDefecto(puntoEmision, sucursal);
                PuntoEmisionService puntoEmisionService=new PuntoEmisionService();
                puntoEmisionService.grabarSinTransaccion(puntoEmision);
                
                /*
                //Grabar los parametros de configuracion para la FACTURACION ELECTRONICA
                ParametroCodefacService parametroCodefacService =new ParametroCodefacService();
                parametroCodefacService.editarParametrosSinTransaccion(parametros);
                
                //Grabar el USUARIO POR DEFECTO para ingresar al sistema
                agregarDatosUsuarioDefecto(usuario, empresa, puntoEmision);
                UsuarioServicio usuarioService=new UsuarioServicio();
                usuarioService.grabarSinTransaccion(usuario);*/
                
                //Grabando los PARAMETROS DEL SISTEMA
                agregarParametroPorDefecto(empresa, parametros);
                ParametroCodefacService parametroCodefacService=new ParametroCodefacService();
                parametroCodefacService.editarParametrosSinTransaccion(parametros);
                
                //Retorno la empresa grabada por que necesita para un proceso posterior
                return empresa;
            }
        });
        
    }
    
    
    private void agregarDatosDefectoSucursal(Sucursal sucursal,Empresa empresa)
    {
        sucursal.setNombre("Principal");
        sucursal.setCodigo("SUC");
        sucursal.setTipoEnum(Sucursal.TipoSucursalEnum.MATRIZ);
        sucursal.setEmpresa(empresa);
        sucursal.setEstadoEnum(GeneralEnumEstado.ACTIVO);        
    }
    
    private void agregarParametroPorDefecto(Empresa empresa,List<ParametroCodefac> parametros)
    {
        //Creando el directorio por defecto de los recursos
        String pathPorDefecto = ParametrosSistemaCodefac.DIRECTORIO_RECURSOS_DEFECTO;
        ParametroCodefac parametroDirectorioRecursos = new ParametroCodefac();
        parametroDirectorioRecursos.setNombre(ParametroCodefac.DIRECTORIO_RECURSOS);
        parametroDirectorioRecursos.setValor(pathPorDefecto);
        parametroDirectorioRecursos.setEmpresa(empresa);
        parametros.add(parametroDirectorioRecursos);
        
        
       
        
    }
    
    private void agregarDatosUsuarioDefecto(Usuario usuario,Empresa empresa,PuntoEmision puntoEmision)
    {
        usuario.setEmpresa(empresa);
        usuario.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        
        //Creando el registro para el enlace del punto de emision
        PuntoEmisionUsuario puntoEmisionUsuario=new PuntoEmisionUsuario();
        puntoEmisionUsuario.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        puntoEmisionUsuario.setPuntoEmision(puntoEmision);
        puntoEmisionUsuario.setUsuario(usuario);
        
        List<PuntoEmisionUsuario> puntosEmisionList=new ArrayList<PuntoEmisionUsuario>()
        {
            {
                add(puntoEmisionUsuario);
            }
        };
        
        usuario.setPuntosEmisionUsuario(puntosEmisionList);
        
    }
    
    private PuntoEmision agregarDatosPuntoEmisionDefecto(PuntoEmision puntoEmision,Sucursal sucursal)
    {
        puntoEmision.setDescripcion("principal");
        puntoEmision.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        puntoEmision.setSecuencialGuiaRemision(1);
        puntoEmision.setSecuencialGuiaRemisionInterna(1);
        puntoEmision.setSecuencialLiquidacionCompra(1);
        puntoEmision.setSecuencialNotaDebito(1);
        puntoEmision.setSecuencialNotaVenta(1);
        puntoEmision.setSecuencialNotaVentaInterna(1);
        puntoEmision.setSucursal(sucursal);
        puntoEmision.setTipoFacturacionEnum(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA);
        return puntoEmision;
    }
        
}
