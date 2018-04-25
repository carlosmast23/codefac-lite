/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.rmi.RemoteException;
import java.sql.Date;
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

    public List<RubroEstudiante> findRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) {

        String queryString = "SELECT u FROM RubroEstudiante u WHERE";

        for (int i = 1; i <= rubros.size(); i++) {
            queryString += " u.rubroNivel=?" + i + " OR";
        }

        queryString = queryString.substring(0, queryString.length() - 3);

        Query query = getEntityManager().createQuery(queryString);

        //Setear las variables con el numero
        for (int i = 1; i <= rubros.size(); i++) {
            query.setParameter(i, rubros.get(i - 1));
        }

        return query.getResultList();

    }

    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante,Periodo periodo) {
        //RubroEstudiante rubroEstudiante;
        //rubroEstudiante.getEstudianteInscrito().getNivelAcademico().getPeriodo();
            
        String academico = "";
        if (estudiante != null) {
            academico = "u.estudianteInscrito.estudiante=?1";
        } else {
            academico = "1=1";
        }
        try {
            String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estudianteInscrito.nivelAcademico.periodo=?2 AND " + academico;
            //String queryString = "SELECT u FROM RubroEstudiante u " ;
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(2,periodo);
            
            if (estudiante != null) {
                query.setParameter(1, estudiante);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo,Date fechaInicio,Date fechaFin) 
    {
        //RubroEstudiante rubroEstudiante;
        //rubroEstudiante.getFechaGenerado();
        //rubroEstudiante.getEstado()
        String academico = "";
        if (periodo != null) {
            academico = "u.estudianteInscrito.nivelAcademico.periodo=?1";
        } else {
            academico = "1=1";
        }
        try {
            //String queryString = "SELECT u.estudianteInscrito.nivelAcademico,u.rubroNivel,SUM(u.valor)-SUM(u.saldo),SUM(u.saldo) FROM RubroEstudiante u WHERE (u.estadoFactura <> 'f') AND " + academico + " GROUP BY u.estudianteInscrito.nivelAcademico,u.rubroNivel";
            String queryString = "SELECT u.estudianteInscrito.nivelAcademico,u.rubroNivel,SUM(u.valor)-SUM(u.saldo),SUM(u.saldo) FROM RubroEstudiante u WHERE (u.estado <> ?2 AND u.estado <> ?3 ) AND " + academico;
            
            String queryStringFecha="";
            if(fechaInicio!=null)
            {
                queryStringFecha+=" AND  u.fechaGenerado>=?4 ";
            }
            
            if(fechaFin!=null)
            {
                queryStringFecha+=" AND  u.fechaGenerado<=?5 ";
            }
            
            queryString+=queryStringFecha+" GROUP BY u.estudianteInscrito.nivelAcademico,u.rubroNivel";
            
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(2,GeneralEnumEstado.ELIMINADO.getEstado());
            query.setParameter(3,GeneralEnumEstado.ANULADO.getEstado());
            
            
            if (periodo != null) {
                query.setParameter(1, periodo);
            }
            
            if(fechaInicio!=null)
            {
                query.setParameter(4, fechaInicio);
            }
            
            if(fechaFin!=null)
            {
                query.setParameter(5, fechaFin);
            }
            
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<RubroEstudiante> getRubrosActivosPorEstudianteYEstadoFacturado(RubroEstudiante.FacturacionEstadoEnum estadoFacturadoEnum) throws RemoteException {

        String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estado!=?1 and u.estado!=?2 and u.estadoFactura=?3 ";
        Query query = getEntityManager().createQuery(queryString);

        query.setParameter(1, GeneralEnumEstado.ANULADO.getEstado());
        query.setParameter(2, GeneralEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3, estadoFacturadoEnum.getLetra());

        return query.getResultList();

    }

    public List<RubroEstudiante> getRubrosActivosPorEstudiante(EstudianteInscrito estudianteInscrito) throws RemoteException {

        String queryString = "SELECT u FROM RubroEstudiante u WHERE u.estado!=?1 and u.estado!=?2 and u.estudianteInscrito=?3  ";
        Query query = getEntityManager().createQuery(queryString);

        query.setParameter(1, GeneralEnumEstado.ANULADO.getEstado());
        query.setParameter(2, GeneralEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3, estudianteInscrito);

        return query.getResultList();
    }

    public List<RubroEstudiante> buscarRubrosMes(EstudianteInscrito estudiante, Periodo periodo, CatalogoProducto catalogoProducto, List<MesEnum> meses) throws RemoteException {
        String cat, qmes = "";
        if (catalogoProducto != null) {
            cat = "rn.catalogoProducto=?2";
        } else {
            cat = "1=1";
        }
        String stringQuery = "";
        if (meses != null && meses.size()>0) {
            stringQuery = "(SELECT rn.id FROM RubrosNivel rn WHERE " + cat + " AND rn.periodo=?3 AND ";
            String stringQueryMeses = "";
            //Solo van entre parentesis si tiene mas datos que 1
            if (meses.size() > 1) {
                stringQueryMeses = "( ";
            }
            if (meses.size() > 0) {
                for (MesEnum mes : meses) {
                    stringQueryMeses = stringQueryMeses + " rn.mesNumero=" + mes.getNumero() + " OR";
                }
            }
            //Cortar el ultimo OR que sobra de la candena;
            stringQueryMeses = stringQueryMeses.substring(0, stringQueryMeses.length() - 3);
            if (meses.size() > 1) {
                stringQueryMeses = stringQueryMeses + ")";
            }
            //Une los querys parciales y genera uno total
            stringQuery += stringQueryMeses + ")";
            qmes = "u.rubroNivel.id IN " + stringQuery;
        } else {
            stringQuery = "(SELECT rn.id FROM RubrosNivel rn WHERE " + cat + " AND rn.periodo=?3) ";
            qmes = "u.rubroNivel.id IN " + stringQuery;
            //qmes = "1=1";
        }

        String academico = "";
        if (estudiante != null) {
            academico = "u.estudianteInscrito=?1";
        } else {
            academico = "1=1";
        }
//VALIDAR DEUDAS

        try {
            String queryString = "SELECT u FROM RubroEstudiante u WHERE (u.estado <> ?4 AND u.estado <> ?5 AND u.saldo > 0) AND " + academico + " AND " + qmes;
            Query query = getEntityManager().createQuery(queryString);
            System.err.println("QUERY--->" + query.toString());
            if (estudiante != null) {
                query.setParameter(1, estudiante);
            }
            if (catalogoProducto != null) {
                query.setParameter(2, catalogoProducto);
            }
            query.setParameter(3, periodo);
            query.setParameter(4, GeneralEnumEstado.ANULADO.getEstado());
            query.setParameter(5, GeneralEnumEstado.ELIMINADO.getEstado());
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
