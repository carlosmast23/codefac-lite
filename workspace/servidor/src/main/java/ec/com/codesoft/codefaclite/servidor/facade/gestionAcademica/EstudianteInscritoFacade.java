/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoFacade extends AbstractFacade<EstudianteInscrito> {

    public EstudianteInscritoFacade() {
        super(EstudianteInscrito.class);
    }
    
    public Long obtenerTamanioEstudiatesInscritosPorEstudiante(Estudiante estudiante) 
    {

        EstudianteInscrito estudianteInscrito = new EstudianteInscrito();
        estudianteInscrito.getNivelAcademico();
        String queryString = "SELECT count(1) FROM EstudianteInscrito u WHERE u.estado=?1 and u.estudiante=?2 ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, estudiante);
        return (Long) query.getSingleResult();

    }
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico)
    {
        EstudianteInscrito estudianteInscrito=new EstudianteInscrito();
        estudianteInscrito.getNivelAcademico();
        String queryString="SELECT count(1) FROM EstudianteInscrito u WHERE u.estado=?1 and u.nivelAcademico=?2 ";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, nivelAcademico);
        return (Long) query.getSingleResult();
        
    }

    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo) {
        //EstudianteInscrito estudianteInscrito;
        //estudianteInscrito.getNivelAcademico().getPeriodo();
        
        String academico = "";
        if (nivel != null) {
            academico = "u.nivelAcademico=?1";
        } else {
            academico = "1=1";
        }

        try {
            String queryString = "SELECT u FROM EstudianteInscrito u WHERE u.estado=?2 AND " + academico;
            
            if(periodo!=null)
            {
                queryString+=" AND u.nivelAcademico.periodo=?3";
            }
            
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
            
            if(periodo!=null)
            {
                query.setParameter(3, periodo);
            }
            
            //System.err.println("QUERY--->"+query.toString());
            if (nivel != null) {
                query.setParameter(1, nivel);
            }
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
