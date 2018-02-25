/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class EstudianteFacade extends AbstractFacade<Estudiante>{

    public EstudianteFacade() {
        super(Estudiante.class);
    }
    
    public List<Estudiante> getEstudiantesSinMatricula(Periodo periodo)
    {
        try {
            //Estudiante e;
            //EstudianteInscrito ei;
            //ei.getEstudiante()
            //ei.getNivelAcademico().getPeriodo();            
            String queryString = "SELECT e FROM EstudianteInscrito u LEFT JOIN u.estudiante e WHERE u.nivelAcademico.periodo=?1";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,periodo);
            return (List<Estudiante>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
