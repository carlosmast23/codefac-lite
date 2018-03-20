/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroEstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RubroEstudianteService extends ServiceAbstract<RubroEstudiante, RubroEstudianteFacade> implements RubroEstudianteServiceIf {

    RubroEstudianteFacade rubroEstudianteFacade;

    public RubroEstudianteService() throws RemoteException {
        super(RubroEstudianteFacade.class);
        rubroEstudianteFacade= new RubroEstudianteFacade();
    }
    
    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) throws RemoteException
    {
        return getFacade().findRubrosEstudiantesPorRubros(rubros);
    }
    
    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla,MesEnum mesEnum,String nombreRubroMes) throws RemoteException
    {
        try
        {
            EntityTransaction transaccion=getTransaccion();
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

            entityManager.persist(rubroNivel);

            for (RubroPlantillaEstudiante estudiateInscrito : rubroPlantilla.getDetalles()) {
                RubroEstudiante rubroEstudiante = new RubroEstudiante();
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                rubroEstudiante.setEstudianteInscrito(estudiateInscrito.getEstudianteInscrito());
                rubroEstudiante.setRubroNivel(rubroNivel);
                rubroEstudiante.setSaldo(BigDecimal.ZERO);
                
                entityManager.persist(rubroEstudiante);
            }
            
            //Modificar el valor del mes que se esta generando
            rubroPlantilla.cambiarEstadoMes(mesEnum,Boolean.TRUE);
            entityManager.merge(rubroPlantilla);
            
            
            transaccion.commit();
            return rubroPlantilla;

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException {
        EntityTransaction transaccion = getTransaccion();
        transaccion.begin();
        for (EstudianteInscrito estudiante : estudiantes) {
            RubroEstudiante rubroEstudiante = new RubroEstudiante();
            rubroEstudiante.setEstudianteInscrito(estudiante);
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
            entityManager.persist(rubroEstudiante);
        }
        entityManager.flush();
        transaccion.commit();

    }

    @Override
    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante est) throws RemoteException {
        return rubroEstudianteFacade.obtenerDeudasEstudiante(est);
    }

  

}
