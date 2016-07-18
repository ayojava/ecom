/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * 
 *
 * @author ayojava
 */
public abstract class AbstractFacade<T> {
    
    @PersistenceContext(unitName = "localMongoDb_PU")
    private EntityManager em;
    
//    @PersistenceContext(unitName = "openShiftMongoDb_PU")
//    private EntityManager em;

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public T edit(T entity) {
        return getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
}
