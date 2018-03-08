/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
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

    public List<RubroEstudiante> obtenerDeudasEstudiante(NivelAcademico nivel) {
        String academico = "";
        if (nivel != null) {
            academico = "u.estudianteInscrito.nivelAcademico=?1";
        } else {
            academico = "1=1";
        }

        try {
            String queryString = "SELECT u FROM RubroEstudiante u WHERE " + academico;
            //String queryString = "SELECT u FROM RubroEstudiante u " ;
            Query query = getEntityManager().createQuery(queryString);
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
