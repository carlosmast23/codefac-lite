/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public abstract class AbstractFacade<T>
{
    public static EntityManager entityManager;
    
    public static final String namePersistence="pu_ejemplo";
    private Class<T> entityClass;
    

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //protected abstract EntityManager getEntityManager();
    public EntityManager getEntityManager()
    {
        //EntityManagerFactory factory=Persistence.createEntityManagerFactory(namePersistence);
        return entityManager;
    }

    public void create(T entity) throws ConstrainViolationExceptionSQL,DatabaseException{
        try
        {
            EntityTransaction tx= getEntityManager().getTransaction();
            tx.begin();
            //getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            entityManager.flush();
            tx.commit();
        }catch(PersistenceException e)
        {

            if(e.getCause()!=null && e.getCause().getClass().equals(DatabaseException.class) )
            {
                DatabaseException dbe=(DatabaseException) e.getCause();
                //TODO: Esta valifacion de la claves primarias es solo para la base de datos derby
                if(dbe.getCause()!=null && dbe.getCause().getClass().equals(DerbySQLIntegrityConstraintViolationException.class))
                {
                    DerbySQLIntegrityConstraintViolationException constrainViolation = (DerbySQLIntegrityConstraintViolationException) dbe.getCause();
                    System.out.println(constrainViolation.getMessage());
                    throw new ConstrainViolationExceptionSQL("Ya existe un registro registrado con la clave primaria");
                }
                throw dbe;
            }

            
        }

        //getEntityManager().getTransaction().commit();
    }

    public void edit(T entity) {
        EntityTransaction tx= getEntityManager().getTransaction();
        tx.begin();
        getEntityManager().merge(entity);
        tx.commit();
    }

    public void remove(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().getTransaction().commit();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public List<T> findByMap(Map<String, Object> parametros)
    {
        String queryString = buildQueryString(entityClass, parametros);
        Query query=getEntityManager().createQuery(queryString);
        
        /**
         * Setear los valores
         */
        if (parametros != null) {
            for (String parametro : parametros.keySet()) {
                query.setParameter(parametro, parametros.get(parametro));
            }
        }
        return query.getResultList();

    }   
    

    
    private String buildQueryString(Class clase, Map<String, Object> parametros) {
        String objectName = "e";

        String queryString = "from " + clase.getSimpleName() + " " + objectName;
        if (parametros != null && parametros.size() > 0) {
            String where =buildWhere(parametros, objectName);
            queryString += " where " + where;
        }
        System.out.println("query:" + queryString);
        return queryString;
        /*
		 * SelectQuery select = new SelectQuery(); Table table = new
		 * Table(clase); select.addObject(table); if (parametros != null &&
		 * parametros.size() > 0) addParameters(select, table, parametros);
		 * select.addOrder(new Order(new Column(table, "id"), true)); return
		 * select.getQueryString();
         */
    }
    
    //Convierte el Mapa a string(estructura query) del string objeto que se envia
	public static String buildWhere(Map aMap, String objectName) {

		Set keys = aMap.keySet();

		String query = " ";

		Iterator it = keys.iterator();
		int propertyNumber = keys.size();
		int i = 1;
		while (it.hasNext()) {
			String propertyKey = (String) it.next();
			Object property = aMap.get(propertyKey);

			if (property instanceof String) {

				String propertyString = (String) property;

				if ( propertyString.contains("null") ){
					query = query + objectName + "." + propertyKey + " is "
					+ propertyString;
					it.remove();
				}else if (propertyString.contains("%")) {

					query = query + objectName + "." + propertyKey + " like :"
							+ propertyKey;
				} else {
					query = query + objectName + "." + propertyKey + " = :"
							+ propertyKey;
				}

			} else {

				if ( property == null ){
					query = query + objectName + "." + propertyKey + " is null ";
					it.remove();
				}else
					query = query + objectName + "." + propertyKey + " = :"
						+ propertyKey;
			}

			if (i == propertyNumber) {
				query = query + " ";
			} else {
				query = query + " and ";
			}

			i++;
		}
		//System.out.println("Parte del Where del Query: " + query);
		return query;
	}
    
    public static void cargarEntityManager() throws PersistenceException,PersistenciaDuplicadaException
    {
        try 
        {
            entityManager=Persistence.createEntityManagerFactory(namePersistence).createEntityManager();
        }
        catch(PersistenceException e)
        {
            e.printStackTrace();
            DatabaseException dbe=(DatabaseException) e.getCause();
            
            SQLException sqlException= (SQLException) dbe.getCause();

            while(sqlException!=null)
            {
                System.out.println(sqlException.getCause());
                System.out.println(sqlException.getErrorCode());
                System.out.println(sqlException.getMessage());
                System.out.println(sqlException.getSQLState());
                System.out.println("---------------------------------");

                if(sqlException.getErrorCode()==45000)
                {
                    throw new PersistenciaDuplicadaException("Ya existe una versión de Codefac abierto");
                }
                /*
                if(sqlException.getErrorCode()==40000)
                {
                    throw new PersistenceException("No existe base de datos");
                }*/

                sqlException=sqlException.getNextException();

            }
 
            throw new PersistenceException("No existe base de datos");
        }
    }
    
    
    public static List<Object> findStaticDialog(String queryStr,Map<Integer,Object> map,int limiteMinimo,int limiteMaximo) {
        Query query = entityManager.createQuery(queryStr);
        //Agregar los parametros del map al query
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query.getResultList();
    }
    
    public static Long findCountStaticDialog(String queryStr,Map<Integer,Object> map) {
        Query query = entityManager.createQuery(queryStr);
        //Agregar los parametros del map al query
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        
        return (Long) query.getSingleResult();
    }
    
    /**
     * Metodo que se encarga de desasoriar una entidad gestionada para poder hacer acciones
     * sobre el objecto pero que no se reflejen en la persistencia con la base de datoss
     */
    public static void detachEntity(Object obj)
    {
        //entityManager.contains(obj) para saber si el dato es administrable
        entityManager.detach(obj);
    }
    
    /**
     * Desasocia todos los objectos hijos de objeto de la persistencia para que no
     * sean entidades administradas
     * @param obj 
     */
    //TODO implementar metodo cuando tenga referencias recursivas
    public static void detachRecursive(Object obj)
    {
        entityManager.detach(obj);
        Method[] metodos= obj.getClass().getMethods();
        for (Method metodo : metodos) {
            //Verifica que el tipo de dato sea una lista
            if(List.class.equals(metodo.getReturnType()))
            {
                try {
                    List<Object> list= (List<Object>) metodo.invoke(obj);
                    for (Object object : list) {
                        detachRecursive(object);
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static EntityTransaction crearTransaccion()
    {
        return entityManager.getTransaction();
    }

}