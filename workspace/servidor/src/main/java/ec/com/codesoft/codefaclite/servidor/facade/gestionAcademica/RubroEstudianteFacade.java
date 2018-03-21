/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class RubroEstudianteFacade extends AbstractFacade<RubroEstudiante> {

    public RubroEstudianteFacade() {
        super(RubroEstudiante.class);
    }

    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante) {
        String academico = "";
        if (estudiante != null) {
            academico = "u.estudianteInscrito.estudiante=?1";
        } else {
            academico = "1=1";
        }
        try {
            String queryString = "SELECT u FROM RubroEstudiante u WHERE " + academico;
            //String queryString = "SELECT u FROM RubroEstudiante u " ;
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERY--->"+query.toString());
            if (estudiante != null) {
                query.setParameter(1, estudiante);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo) {
        String academico = "";
        if (periodo != null) {
            academico = "u.estudianteInscrito.nivelAcademico.periodo=?1";
        } else {
            academico = "1=1";
        }
        try {
            String queryString = "SELECT u.estudianteInscrito.nivelAcademico,u.rubroNivel,SUM(u.rubroNivel.valor) FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico + " GROUP BY u.estudianteInscrito.nivelAcademico,u.rubroNivel";
            Query query = getEntityManager().createQuery(queryString);
            if (periodo != null) {
                query.setParameter(1, periodo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<NivelAcademico> obtenerRubroPeriodo(Periodo periodo) {
        String academico = "";
        if (periodo != null) {
            academico = "u.estudianteInscrito.nivelAcademico.periodo=?1";
        } else {
            academico = "1=1";
        }
        try {
            String queryString = "SELECT u.estudianteInscrito.nivelAcademico FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico + " GROUP BY u.estudianteInscrito.nivelAcademico";
            Query query = getEntityManager().createQuery(queryString);
            if (periodo != null) {
                query.setParameter(1, periodo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
//retornar el tipo OBJECT [ ]

    public List<RubrosNivel> obtenerRubroNivel(NivelAcademico nivel) {
        String academico = "";
        if (nivel != null) {
            academico = "u.estudianteInscrito.nivelAcademico=?1";
        } else {
            academico = "1=1";
        }
        try {
            String queryString = "SELECT u.rubroNivel FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico + " GROUP BY u.rubroNivel";
            Query query = getEntityManager().createQuery(queryString);
            if (nivel != null) {
                query.setParameter(1, nivel);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RubroEstudiante> obtenerRubro(NivelAcademico nivel, RubrosNivel rubro) {
        String academico = "";
        if (nivel != null && rubro != null) {
            academico = "u.estudianteInscrito.nivelAcademico=?1 AND u.rubroNivel=?2";
        } else {
            academico = "1=1";
        }

        try {
            String queryString = "SELECT u FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico;
            //String queryString = "SELECT u FROM RubroEstudiante u " ;
            Query query = getEntityManager().createQuery(queryString);
            System.err.println("QUERY--->" + query.toString());
            if (nivel != null && rubro != null) {
                query.setParameter(1, nivel);
                query.setParameter(2, rubro);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
