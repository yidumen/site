package com.yidumen.dao.impl;

import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 * @param <T> 实体类
 */
public abstract class AbstractSHImpl<T> {

    private final Class<T> entityClass;

    public AbstractSHImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        currentSession.persist(entity);
        currentSession.getTransaction().commit();
    }

    public void edit(T entity) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        currentSession.saveOrUpdate(entity);
        currentSession.getTransaction().commit();
    }

    public void remove(T entity) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        currentSession.delete(entity);
        currentSession.getTransaction().commit();
    }

    public T find(Long id) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        final T result = (T) currentSession.load(entityClass, id);
        initalizeLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    public List<T> findAll() {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        final List<T> result = currentSession.createCriteria(entityClass).list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    protected void initalizeListLazy(final List<T> list) throws HibernateException {
        for (T entity : list) {
            initalizeLazy(entity);
        }
    }

    abstract protected void initalizeLazy(T entity);
}
