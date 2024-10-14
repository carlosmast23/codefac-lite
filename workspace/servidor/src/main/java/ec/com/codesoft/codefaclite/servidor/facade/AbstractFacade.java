/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoBaseDatosEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoQueryEnum;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import jakarta.persistence.RollbackException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public abstract class AbstractFacade<T>
{
    public static EntityManager entityManager;
    
    /**
     * Datos para setear las variables globales de conexion a la base de datos
     */
    public static String usuarioDb;
    public static String claveDb;
    //Por defecto dejo seteado la base de datos en derby
    public static TipoBaseDatosEnum baseDatosEnum=TipoBaseDatosEnum.DERBY;
    
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

    //Metodo eliminado porque esta en desuso y puede generar muchos problemas de persistencia
    /*
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
    */

    /**
     * @deprecated 
     * @param entity 
     */
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
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        jakarta.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public List<T> findByMap(Map<String, Object> parametros)
    {
        return findByMap(parametros,null);

    }   
    
    public List<T> findByMap(Map<String, Object> parametros,String orderField)
    {
        String queryString = buildQueryString(entityClass, parametros);
        
        boolean asc=true;
        if (orderField != null && !orderField.isEmpty()) 
        {
            queryString += " ORDER BY e." + orderField + (asc ? " ASC" : " DESC");
        }
        
        Query query=getEntityManager().createQuery(queryString);
        
        
        /**
         * Setear los valores
         */
        if (parametros != null) {
            for (String parametro : parametros.keySet()) {
                query.setParameter(parametro.replace(".",""), parametros.get(parametro));
            }
        }
        
        query.setFlushMode(FlushModeType.COMMIT);
        //query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();
    }
    
    private String buildQueryString(Class clase, Map<String, Object> parametros) {
        String objectName = "e";

        String queryString = "from " + clase.getSimpleName() + " " + objectName;
        if (parametros != null && parametros.size() > 0) {
            String where =buildWhere(parametros, objectName);
            queryString += " where " + where;
        }
        return queryString;
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
							+ propertyKey.replace(".","");
				}

			} else {

				if ( property == null ){
					query = query + objectName + "." + propertyKey + " is null ";
					it.remove();
				}else
					query = query + objectName + "." + propertyKey + " = :"
						+ propertyKey.replace(".","");
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
            //Esta linea se ejecuta si existe la base de datos
            //TODO: Utilizar propertys para cambiar el url cuando es en Linux porque no funciona y tiene otra sintaxis
            Map<String,String> properties=new HashMap<String,String>();
            
            if(baseDatosEnum.equals(TipoBaseDatosEnum.MYSQL))
            {
                properties.put("jakarta.persistence.jdbc.url","jdbc:mysql://localhost:3306/codefac");
                properties.put("jakarta.persistence.jdbc.driver","com.mysql.cj.jdbc.Driver");
                properties.put("eclipselink.target-database","MySQL");
            }
            else if(baseDatosEnum.equals(TipoBaseDatosEnum.DERBY))
            {
                properties.put("jakarta.persistence.jdbc.url","jdbc:derby:Derby2.DB");
                properties.put("jakarta.persistence.jdbc.driver","org.apache.derby.jdbc.EmbeddedDriver");
                properties.put("eclipselink.target-database","DERBY");
            }
            
            properties.put("jakarta.persistence.jdbc.user", AbstractFacade.usuarioDb);
            properties.put("jakarta.persistence.jdbc.password", AbstractFacade.claveDb);
            
            entityManager=Persistence.createEntityManagerFactory(namePersistence,properties).createEntityManager();
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
                sqlException=sqlException.getNextException();

            }
 
            throw new PersistenceException("No existe base de datos");
        }
    }
    
    /**
     * 
     * @param nombreTabla
     * @param nombrePK
     * @return RETORNA VERDADERO SI ENCUENTRA CLAVES REPETIDAS
     */
        
    /**
     * TODO: Ver como parametrizar el nombre del esquema que parece que siempre es el mismo nombre del usuario al momento de crear la base de datos
     * @param nombreTabla 
     */
    public static void consultarConsistenciaTabla(String nombreTabla)
    {
        String queryString="VALUES SYSCS_UTIL.SYSCS_CHECK_TABLE ('LAGOS' ,'?1')";
        queryString=queryString.replace("?1",nombreTabla);
               
        Query query = entityManager.createNativeQuery(queryString);
        List resultado=query.getResultList();        
        System.out.println("resultado:"+resultado);
    }
    
    
    public static List<Object> findStaticDialog(String queryStr,Map<Integer,Object> map,TipoQueryEnum tipoQueryEnum,int limiteMinimo,int limiteMaximo) {
        Query query = ejecutarConsultaConParametros(queryStr, map,tipoQueryEnum, limiteMinimo, limiteMaximo);
        return query.getResultList();
    }
    
    public static Long findStaticSizeDialog(String queryStr,Map<Integer,Object> map,TipoQueryEnum tipoQueryEnum,int limiteMinimo,int limiteMaximo) {
        Query query = ejecutarConsultaConParametros(queryStr, map,tipoQueryEnum, limiteMinimo, limiteMaximo);
        return (Long) query.getSingleResult();
    }
    
    public static Query ejecutarConsultaConParametros(String queryStr,Map<Integer,Object> map,TipoQueryEnum tipoQueryEnum,int limiteMinimo,int limiteMaximo)
    {
        System.out.println("[Dialog]"+queryStr);
        Query query = null;
        
        if(tipoQueryEnum==null || tipoQueryEnum.equals(TipoQueryEnum.JPQL))
        {
            query=entityManager.createQuery(queryStr);
        }
        else if(tipoQueryEnum.equals(tipoQueryEnum.NATIVO))
        {
            query=entityManager.createNativeQuery(queryStr);
        }
                
        //Agregar los parametros del map al query
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query;
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
    
    public static Boolean buscarClavesRepetidasBaseDatos(String nombreTabla,String nombrePK)
    {
        //SELECT ID,COUNT (ID) FROM FACTURA GROUP BY ID HAVING COUNT(ID)>1;
        List resultado=buscarClavesRepetidasBaseDatosLista(nombreTabla, nombrePK);
        if(resultado.size()>0)
        {
            return true;
        }
        return false;
    }
    
        public static List buscarClavesRepetidasBaseDatosLista(String nombreTabla,String nombrePK)
    {
        //SELECT ID,COUNT (ID) FROM FACTURA GROUP BY ID HAVING COUNT(ID)>1;
        String queryString=" SELECT ?ID FROM ?NOMBRE_TABLA GROUP BY ID HAVING COUNT(?ID)>1";
        queryString=queryString.replace("?ID",nombrePK);
        queryString=queryString.replace("?NOMBRE_TABLA",nombreTabla);
                
        Query query = entityManager.createNativeQuery(queryString);
        
        List resultados=query.getResultList();
        
        //if(resultados.size()==0)
        //{
        //    return new ArrayList();
        //}
        
        for (Object resultado : resultados) 
        {            
            Long idPk=(Long) resultado;
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.WARNING,"ERROR CLAVES DUPLICADOS EN TABLA: "+nombreTabla+" ,ID="+idPk);
        }
        return resultados;
    }


    
    public static EntityTransaction crearTransaccion()
    {
        return entityManager.getTransaction();
    }
    
    

}