/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

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

    public void create(T entity) {
        EntityTransaction tx= getEntityManager().getTransaction();
        tx.begin();
        //getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        tx.commit();
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
        //System.info("query interno: " + queryString);
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
    
    public static void cargarEntityManager()
    {
        entityManager=Persistence.createEntityManagerFactory(namePersistence).createEntityManager();
    }

}