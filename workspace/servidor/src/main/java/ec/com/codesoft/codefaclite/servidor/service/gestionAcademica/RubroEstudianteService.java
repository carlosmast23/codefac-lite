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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            if (rubrosEstudiante.getEstadoFacturaEnum().equals(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR)) {
                rubrosEstudiante = entityManager.merge(rubrosEstudiante);
                entityManager.remove(rubrosEstudiante);
            }
        }
        transaccion.commit();
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

            entityManager.persist(rubroNivel);

            for (RubroPlantillaEstudiante estudiateInscrito : rubroPlantilla.getDetalles()) {
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
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                for (RubroEstudiante rubroEstudiante : rubroEstudiantes) {
                    entityManager.merge(rubroEstudiante);                    
                }
            }
        });
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
            rubroEstudiante.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
            rubroEstudiante.setFechaGenerado(UtilidadesFecha.getFechaHoy());
            rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
            entityManager.persist(rubroEstudiante);
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
