/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroEstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RubroEstudianteService extends ServiceAbstract<RubroEstudiante, RubroEstudianteFacade> implements RubroEstudianteServiceIf {

    RubroEstudianteFacade rubroEstudianteFacade;

    public RubroEstudianteService() throws RemoteException {
        super(RubroEstudianteFacade.class);
        rubroEstudianteFacade = new RubroEstudianteFacade();
    }

    public void eliminar(RubroEstudiante entity) throws java.rmi.RemoteException
    {        
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaccion.commit();        
    }
    
    public List<RubroEstudiante> obtenerRubroMatriculaPorEstudianteInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("estudianteInscrito",estudianteInscrito);
        mapParametros.put("rubroNivel.catalogoProducto.tipoCod",CatalogoProducto.TipoEnum.MATRICULA.getCodigo());
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }
    
    public List<RubroEstudiante> obtenerRubrosActivosPorEstudianteYEstadoFacturado(RubroEstudiante.FacturacionEstadoEnum estadoFacturadoEnum) throws RemoteException
    {
        return getFacade().getRubrosActivosPorEstudianteYEstadoFacturado(estadoFacturadoEnum);
    }

    public List<RubroEstudiante> obtenerRubrosActivosPorEstudiantesInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException {
        return getFacade().getRubrosActivosPorEstudiante(estudianteInscrito);
    }

    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) throws RemoteException {
        return getFacade().findRubrosEstudiantesPorRubros(rubros);
    }

    public void eliminarRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (RubroEstudiante rubrosEstudiante : rubrosEstudiantes) {
            //Solo elimina los rubros de los que esten sin facturar
            if (rubrosEstudiante.getEstadoFacturaEnum().equals(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR)) {
                rubrosEstudiante.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                rubrosEstudiante = entityManager.merge(rubrosEstudiante);
                entityManager.remove(rubrosEstudiante);
            }
        }
        transaccion.commit();
    }
    
    public void eliminarMesRubroPlantilla(RubroPlantillaMes rubroPlantillaMes) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException {
                try {
                    RubrosNivel rubroNivel=rubroPlantillaMes.getRubroNivel();
                    
                    if(rubroNivel==null)
                    {
                        RubroPlantillaMes entityPersistent=entityManager.merge(rubroPlantillaMes);
                        entityPersistent.getRubroPlantilla().getMesesGenerados().remove(entityPersistent); //Eliminar de la referencia de la lista
                        entityManager.remove(entityPersistent); //eliminar de la persistenca
                        
                        return ;//Si no estado atado a un rubro nivel entonces solo borro la referencia , pero al usuario le toca borrar manualmente las referencias creadas
                    }
                    
                    RubroEstudianteService servicio=new RubroEstudianteService();
                    
                    List<RubrosNivel> rubrosNivelList=new ArrayList<RubrosNivel>();
                    rubrosNivelList.add(rubroNivel);
                    
                    //Obtener todos los rubrosEstudiantes vinculados al rubro nivel creado por la plantilla
                    List<RubroEstudiante> rubrosEstudiante=servicio.obtenerRubrosEstudiantesPorRubros(rubrosNivelList);
                    
                    for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {
                        
                        //TODO: Ahorita solo estoy verificando que solo no tenga nnguna factura para anular , pero verificar si tambien me toca verificar el estado del rubro estudiante
                        if(rubroEstudiante.getEstadoFactura().equals(RubroEstudiante.FacturacionEstadoEnum.FACTURADO.getLetra()))
                        {
                            String mensajeException="No se puede procesar porque el estudiante :"+rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreSimple();
                            mensajeException+="\n con el rubro "+rubroNivel.getNombre()+" , porque se encuentra facturado";
                            throw new ServicioCodefacException(mensajeException); //Auotmaticamente genera el rollback cuando lanzo esta excepcion
                        }
                        //Cambiar el estado a eliminado del rubroEstudiante
                        rubroEstudiante.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        entityManager.merge(rubroEstudiante);
                    }
                    
                    //Eliminar el rubro del nivel generado
                    rubroNivel.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(rubroNivel);
                    
                    //Eliminar el rubro plantilla del mes
                    RubroPlantillaMes entityPersistent=entityManager.merge(rubroPlantillaMes);
                    entityPersistent.getRubroPlantilla().getMesesGenerados().remove(entityPersistent); //Eliminar de la referencia de la lista
                    entityManager.remove(entityPersistent); //eliminar de la persistenca
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(RubroEstudianteService.class.getName()).log(Level.SEVERE, null, ex);
                }

                    
        
            }
        });
        
    }

    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla, MesEnum mesEnum, String nombreRubroMes,Integer anio) throws RemoteException {
        try {
            EntityTransaction transaccion = getTransaccion();

            transaccion.begin();
            //Crear el rubro nivel de esa plantilla
            RubrosNivel rubroNivel = new RubrosNivel();
            rubroNivel.setCatalogoProducto(rubroPlantilla.getCatalogoProducto());
            rubroNivel.setDiasCredito(rubroPlantilla.getDiasCredito());
            rubroNivel.setNivel(null);
            rubroNivel.setNombre(nombreRubroMes);
            rubroNivel.setPeriodo(rubroPlantilla.getPeriodo());
            rubroNivel.setValor(rubroPlantilla.getValor());
            rubroNivel.setMesNumero(mesEnum.getNumero());
            rubroNivel.setAnio(anio);
            rubroNivel.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
            //rubroNivel.setReferenciaPlantilla(rubroPlantilla);

            entityManager.persist(rubroNivel);

            for (RubroPlantillaEstudiante estudiateInscrito : rubroPlantilla.getDetalles()) {
                
                //Cuando el estudiante inscrito esta con estado inactivo no genera la deuda para ese estudiante
                if(!estudiateInscrito.getEstudianteInscrito().getEnumEstado().equals(GeneralEnumEstado.ACTIVO))
                {
                    continue; //Pasa al siguiente registro
                }
                
                RubroEstudiante rubroEstudiante = new RubroEstudiante();
                
                rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                rubroEstudiante.setEstudianteInscrito(estudiateInscrito.getEstudianteInscrito());
                rubroEstudiante.setRubroNivel(rubroNivel);

                rubroEstudiante.setSaldo(rubroNivel.getValor());
                rubroEstudiante.setValor(rubroNivel.getValor());

                entityManager.persist(rubroEstudiante);
            }

            //Grabar los valores del mes que se estan generando
            RubroPlantillaMes rubroPlantillaMes=new RubroPlantillaMes();
            rubroPlantillaMes.setAnio(anio);
            rubroPlantillaMes.setNumeroMes(mesEnum.getNumero());
            rubroPlantillaMes.setRubroPlantilla(rubroPlantilla);
            rubroPlantillaMes.setRubroNivel(rubroNivel);
            entityManager.persist(rubroPlantillaMes);
            
            rubroPlantilla.addMesGenerado(rubroPlantillaMes);
            
            //Actualizar el rubroPlantillaMes
            entityManager.merge(rubroPlantilla);

            transaccion.commit();
            return rubroPlantilla;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void actualizarRubrosEstudiante(List<RubroEstudiante> rubroEstudiantes) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    for (RubroEstudiante rubroEstudiante : rubroEstudiantes) {                    
                        entityManager.merge(rubroEstudiante);
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RubroEstudianteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (EstudianteInscrito estudiante : estudiantes) {
            RubroEstudiante rubroEstudiante = new RubroEstudiante();
            rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
            rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
            rubroEstudiante.setEstudianteInscrito(estudiante);
            rubroEstudiante.setSaldo(rubroNivel.getValor());
            rubroEstudiante.setValor(rubroNivel.getValor());
            rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
            rubroEstudiante.setRubroNivel(rubroNivel);

            entityManager.persist(rubroEstudiante);
        }
        entityManager.flush();
        transaccion.commit();
    }

    public void crearRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (RubroEstudiante rubroEstudiante : rubrosEstudiantes) {
            //Solo valido que creen rubros que no exitan es decir que no tengan id
            if(rubroEstudiante.getId()==null)
            {
                rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                entityManager.persist(rubroEstudiante);
            }
        }
        entityManager.flush();
        transaccion.commit();

    }

    @Override
    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante,Periodo periodo) throws RemoteException {
        return rubroEstudianteFacade.obtenerDeudasEstudiante(estudiante,periodo);
    }

    @Override
    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo,Date fechaInicio,Date fechaFin) throws RemoteException {
        return rubroEstudianteFacade.obtenerRubroPeriodoGrupo(periodo,fechaInicio,fechaFin);
    }

    public List<RubroEstudiante> buscarRubrosMes(EstudianteInscrito est,Periodo periodo, CatalogoProducto catalogoProducto, List<RubroPlantillaMes> meses) throws RemoteException 
    {
        return rubroEstudianteFacade.buscarRubrosMes(est,periodo, catalogoProducto, meses);
    }
    
    public Long contarRubrosEstudiantePorRubroNivel(RubrosNivel rubroNivel) throws RemoteException
    {
        return getFacade().contarRubrosEstudiantePorRubroNivelFacade(rubroNivel);
    }

}
