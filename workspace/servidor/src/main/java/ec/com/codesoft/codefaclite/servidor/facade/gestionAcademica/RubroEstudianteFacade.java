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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
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
    public List<RubroEstudiante> findRubrosEstudiantesPorRubros(List<RubrosNivel> rubros)
    {
       
        String queryString = "SELECT u FROM RubroEstudiante u WHERE";
        
        for (int i = 1; i <= rubros.size(); i++) {
            queryString+=" u.rubroNivel=?"+i+" OR";
        }
        
        queryString=queryString.substring(0, queryString.length()-3);
        
        Query query = getEntityManager().createQuery(queryString);
        
        //Setear las variables con el numero
        for (int i = 1; i <= rubros.size(); i++) {
            query.setParameter(i,rubros.get(i-1));
        }
        
        return query.getResultList();
    
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
            String queryString = "SELECT u.estudianteInscrito.nivelAcademico,u.rubroNivel,SUM(u.valor)-SUM(u.saldo),SUM(u.saldo) FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico + " GROUP BY u.estudianteInscrito.nivelAcademico,u.rubroNivel";
            Query query = getEntityManager().createQuery(queryString);
            if (periodo != null) {
                query.setParameter(1, periodo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<RubroEstudiante> getRubrosActivosPorEstudianteYEstadoFacturado(RubroEstudiante.FacturacionEstadoEnum estadoFacturadoEnum) throws RemoteException
    {
       
        String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estado!=?1 and u.estado!=?2 and u.estadoFactura=?3 ";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,GeneralEnumEstado.ANULADO.getEstado());
        query.setParameter(2,GeneralEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3,estadoFacturadoEnum.getLetra());
        
        return query.getResultList();
        
    }
    
    
    public List<RubroEstudiante> getRubrosActivosPorEstudiante(EstudianteInscrito estudianteInscrito) throws RemoteException
    {

        String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estado!=?1 and u.estado!=?2 and u.estudianteInscrito=?3  ";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,GeneralEnumEstado.ANULADO.getEstado());
        query.setParameter(2,GeneralEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3,estudianteInscrito);
        
        return query.getResultList();
    }


   
}
