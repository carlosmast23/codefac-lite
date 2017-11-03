/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Carlos
 */
public abstract class AbstractFacade<T>
{
    public EntityManager entityManager=Persistence.createEntityManagerFactory("pu_ejemplo").createEntityManager();
    
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

}