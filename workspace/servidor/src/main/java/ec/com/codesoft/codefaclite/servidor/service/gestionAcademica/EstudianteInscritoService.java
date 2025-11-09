/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.EstudianteInscritoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccionResultado;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import jakarta.persistence.EntityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.EntityTransaction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoService extends ServiceAbstract<EstudianteInscrito, EstudianteInscritoFacade> implements EstudianteInscritoServiceIf {

    EstudianteInscritoFacade estudianteInscritoFacade;

    public EstudianteInscritoService() throws RemoteException {
        super(EstudianteInscritoFacade.class);
        this.estudianteInscritoFacade = new EstudianteInscritoFacade();

    }
    
    public EstudianteInscrito obtenerPorEstudianteYNivelYEstado(Estudiante estudiante,NivelAcademico nivel,GeneralEnumEstado estado ) throws RemoteException
    {

        try {
            return (EstudianteInscrito) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {

                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("estudiante", estudiante);
                    mapParametros.put("nivelAcademico", nivel);
                    mapParametros.put("estado", estado.getEstado());
                    List<EstudianteInscrito> estudiantesIncritos = getFacade().findByMap(mapParametros, entityManager);
                    if (estudiantesIncritos.size() > 0) {
                        return estudiantesIncritos.get(0);
                    }

                    return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Long obtenerTamanioPorEstudiante(Estudiante estudiante,EntityManager em)
    {
        return getFacade().obtenerTamanioEstudiatesInscritosPorEstudiante(estudiante,em);
    }
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico,EntityManager em) throws RemoteException
    {
        return getFacade().obtenerTamanioEstudiatesInscritosPorCurso(nivelAcademico,em);
    }
    
    public EstudianteInscrito matricularEstudiante(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            
            @Override
            public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                matricularEstudianteValidar(estudianteInscrito,rubroMatricula);
                entityManager.persist(estudianteInscrito);
                EstudianteInscrito estudianteInscritoTmp=entityManager.merge(estudianteInscrito);
                rubroMatricula.setEstudianteInscrito(estudianteInscritoTmp);
                rubroMatricula.setFechaGenerado(UtilidadesFecha.getFechaHoy());
                entityManager.persist(rubroMatricula);
                setearEstudiante(estudianteInscrito, estudianteInscritoTmp);
            }
            
            private void setearEstudiante(EstudianteInscrito estudianteInscrito,EstudianteInscrito estudianteTmp)
            {
                estudianteInscrito=estudianteTmp;
            }
        });
        
        return estudianteInscrito;
    }
    
    private void matricularEstudianteValidar(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws RemoteException,ServicioCodefacException
    {
        //rubroMatricula.getEstadoEnum();
        //rubroMatricula.getRubroNivel();
        //rubroMatricula.getEstudianteInscrito();
        
        if(estudianteInscrito==null)
        {
            throw new ServicioCodefacException("Estudiante inscrito vacio");
        }
        
        EstudianteInscritoService estudianteService=new EstudianteInscritoService();
        EstudianteInscrito estudianteInscritoTmp=estudianteService.obtenerPorEstudianteYNivelYEstado(estudianteInscrito.getEstudiante(),estudianteInscrito.getNivelAcademico(),GeneralEnumEstado.ACTIVO);
        //List<RubroEstudiante> resultadoRubros=rubroEstudianteService.buscarPorEstudianteInscritoYRubroNivelActivos(estudianteInscrito, rubroMatricula.getRubroNivel());
        if(estudianteInscritoTmp!=null)
        {
            throw new ServicioCodefacException("El estudiante ya se encuentra matriculado");
        }
        
    }
    
    public void eliminarEstudiantesInscrito(List<EstudianteInscrito> estudiantesEliminar) throws RemoteException
    {
        
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    RubroPlantillaEstudiante rpe;
                    
                    for (EstudianteInscrito estudianteInscrito : estudiantesEliminar) {
                        estudianteInscrito = entityManager.merge(estudianteInscrito);
                        estudianteInscrito.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        
                        //Buscar los rubroPlantillaEstudiante para tambien actualizar el objecto que tiene referencia
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void matriculaEstudianteByList(List<EstudianteInscrito> estudiantesPorMatricular) throws RemoteException
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    for (EstudianteInscrito estudianteInscrito : estudiantesPorMatricular) {
                        if (estudianteInscrito.getId() == null) {
                            estudianteInscrito = entityManager.merge(estudianteInscrito);
                            estudianteInscrito.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                            entityManager.persist(estudianteInscrito);
                        } else {
                            entityManager.merge(estudianteInscrito);
                        }
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Permite grabar un conjunto de estudiantes para matricular
     *
     * @param mapEstudiantes
     * @throws RemoteException
     */
    /*public void matricularEstudiantesByMap(Map<NivelAcademico, List<Estudiante>> mapEstudiantes) throws RemoteException {
        EntityManager entityManager=AbstractFacade.nuevoEntityManager();
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (Map.Entry<NivelAcademico, List<Estudiante>> entry : mapEstudiantes.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            List<Estudiante> lista = entry.getValue();

            for (Estudiante estudiante : lista) {
                EstudianteInscrito matricula = new EstudianteInscrito();
                matricula.setEstudiante(estudiante);
                matricula.setNivelAcademico(nivelAcademico);
                entityManager.persist(matricula);
            }

        }

        transaccion.commit();
    }*/

    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo) throws RemoteException {
        try {
            return (List<EstudianteInscrito>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    return estudianteInscritoFacade.obtenerEstudiantesInscritos(nivel,periodo,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }

    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodo(Periodo periodo) throws RemoteException {
        
        try {
            return (List<EstudianteInscrito>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("nivelAcademico.periodo", periodo);
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    @Override
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodoYEstudiante(Periodo periodo,Estudiante estudiante) throws RemoteException {
        
        
        try {
            return (List<EstudianteInscrito>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("nivelAcademico.periodo", periodo);
                    mapParametros.put("estudiante", estudiante);
                    mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                    return getFacade().findByMap(mapParametros,entityManager);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
    @Override
    public List<Object[]> consultarRepresentanteConEstudiantesYCursos() throws RemoteException {

        try {
            return (List<Object[]>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                @Override
                public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                    PeriodoService periodoService = new PeriodoService();
                    Periodo periodoActivo = periodoService.obtenerPeriodoActivo().get(0);
                    PersonaService personaService = new PersonaService();
                    EstudianteService estudianteService = new EstudianteService();
                    EstudianteInscritoService estudianteInscritoService = new EstudianteInscritoService();
                    
                    List<Object[]> resultadoObjetos = new ArrayList< Object[]>();
                    
                    List<Object[]> resultadosIds = getFacade().consultarRepresentanteConEstudiantesYCursosFacade(periodoActivo,entityManager);
                    for (Object[] resultadoId : resultadosIds) {
                        Long representanteId = (Long) resultadoId[0];
                        Long estudianteId = (Long) resultadoId[1];
                        Long estudianteInscritoId = (Long) resultadoId[2];
                        
                        Persona representante = (Persona) ((representanteId != null) ? personaService.buscarPorId(representanteId,entityManager) : null);
                        Estudiante estudiante = (Estudiante) ((estudianteId != null) ? estudianteService.buscarPorId(estudianteId,entityManager) : null);
                        EstudianteInscrito estudianteInscrito = (EstudianteInscrito) ((estudianteInscritoId != null) ? estudianteInscritoService.buscarPorId(estudianteInscritoId,entityManager) : null);
                        
                        Object[] dato = {
                            representante,
                            estudiante,
                            estudianteInscrito
                        };
                        
                        resultadoObjetos.add(dato);
                    }
                    return resultadoObjetos;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EstudianteInscritoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public List<EstudianteInscrito> buscarPorNivelAcademico(Periodo periodo,NivelAcademico nivel) throws ServicioCodefacException, java.rmi.RemoteException {
       
        return (List<EstudianteInscrito>) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                return getFacade().buscarPorNivelAcademicoFacade(periodo, nivel,entityManager);
            }
        });
        
        
    }
    
    public EstudianteInscrito buscarEstudianteMatriculadoPeriodoActivo(Estudiante estudiante) throws ServicioCodefacException, java.rmi.RemoteException {
        
        return (EstudianteInscrito) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
            @Override
            public Object transaccion(EntityManager entityManager) throws ServicioCodefacException, RemoteException {
                PeriodoService servicePeriodo = new PeriodoService();
                Periodo periodoActivo = servicePeriodo.obtenerUnicoPeriodoActivo();

                Map<String, Object> mapParametro = new HashMap<String, Object>();
                mapParametro.put("estudiante", estudiante);
                mapParametro.put("nivelAcademico.periodo", periodoActivo);
                mapParametro.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

                List<EstudianteInscrito> resultados = getFacade().findByMap(mapParametro,entityManager);
                if (resultados.size() > 0) {
                    return resultados.get(0);
                }
                return null;
            }
        });

       
    
    }

   
    
}
