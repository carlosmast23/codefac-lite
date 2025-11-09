/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubrosNivelFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class RubrosNivelService extends ServiceAbstract<RubrosNivel,RubrosNivelFacade> implements RubrosNivelServiceIf{

    public RubrosNivelService() throws RemoteException {
        super(RubrosNivelFacade.class);
    }
    
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivel(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel) throws RemoteException
    {
        
        try {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
                    mapParametros.put("nivel", nivel);
                    List<RubrosNivel> rubrosDelNivel = getFacade().findByMap(mapParametros,entityManager);
                    
                    mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
                    mapParametros.put("nivel", null);
                    
                    List<RubrosNivel> rubrosTodosLosNiveles = getFacade().findByMap(mapParametros,entityManager);
                    rubrosTodosLosNiveles.addAll(rubrosDelNivel);
                    return rubrosTodosLosNiveles;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosNivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivelPeriodo(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel,Periodo periodo) throws RemoteException
    {
        try {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
                    mapParametros.put("nivel", nivel);
                    mapParametros.put("periodo", periodo);
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

                    List<RubrosNivel> rubrosDelNivel = getFacade().findByMap(mapParametros,entityManager);

                    mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
                    mapParametros.put("nivel", null);
                    mapParametros.put("periodo", periodo);
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

                    List<RubrosNivel> rubrosTodosLosNiveles = getFacade().findByMap(mapParametros,entityManager);
                    rubrosTodosLosNiveles.addAll(rubrosDelNivel);
                    return rubrosTodosLosNiveles;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosNivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
   
    }
    
    public List<RubrosNivel> buscarPorCatalogoYNivel(CatalogoProducto catalogoProducto,Nivel nivel) throws RemoteException
    {
        try {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto", catalogoProducto);
                    mapParametros.put("nivel", nivel);
                    List<RubrosNivel> rubrosDelNivel = getFacade().findByMap(mapParametros,entityManager);

                    mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto", catalogoProducto);
                    mapParametros.put("nivel", null);

                    List<RubrosNivel> rubrosTodosLosNiveles = getFacade().findByMap(mapParametros,entityManager);
                    rubrosTodosLosNiveles.addAll(rubrosDelNivel);
                    return rubrosTodosLosNiveles;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosNivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;

    }
    
    public List<RubrosNivel> buscarPorCatalogo(CatalogoProducto catalogoProducto) throws RemoteException
    {
        try {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("catalogoProducto", catalogoProducto);
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosNivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
       
    }
    
    public List<RubrosNivel> buscarPorPeriodoYMeses(Periodo periodo,CatalogoProducto catalogoProducto,List<RubroPlantillaMes> meses) throws RemoteException
    {
        try {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    if (meses.size() > 0) {
                        return getFacade().findPorPeriodoYMeses(periodo, catalogoProducto, meses,entityManager);
                    } else {
                        return new ArrayList<RubrosNivel>();
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubrosNivelService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<RubrosNivel>();
    }
    
    public void eliminarRubroNivel(RubrosNivel rubrosNivel) throws RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException  {
                RubroEstudianteService servicio = new RubroEstudianteService();
                Long cantidadRegistros = servicio.contarRubrosEstudiantePorRubroNivel(rubrosNivel,entityManager);
                if (cantidadRegistros == 0) {
                    rubrosNivel.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(rubrosNivel);
                } else {
                    throw new ServicioCodefacException("El rubro no se puede eliminar porque existen estudiantes asignados este valor");
                }
            }
        });
    }

    @Override
    public RubrosNivel grabar(RubrosNivel entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                //Si el valor es 0 lanzar una excepcion
                if(entity.getValor().compareTo(BigDecimal.ZERO)<=0)
                {
                    throw new ServicioCodefacException("El valor tiene que ser mayor a 0");
                }
                
                entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    
    public List<RubrosNivel> buscarPorPeriodoYNivel(Periodo p,Nivel nivel) throws RemoteException,ServicioCodefacException
    {
            return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                //Cargar rubros generales para todos los niveles
                mapParametros.put("nivel", nivel);
                mapParametros.put("periodo", p);
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParametros,entityManager);
            }
        });
        

    }

    
    public List<RubrosNivel> buscarPorPeriodoYCatalogo(Periodo p,CatalogoProducto catalogoProducto) throws RemoteException,ServicioCodefacException
    {
        
        return (List<RubrosNivel>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("periodo", p);
                mapParametros.put("catalogoProducto", catalogoProducto);
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParametros,entityManager);
            }
        });
        

    }
    
    
            
}
