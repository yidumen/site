package com.yidumen.dao.impl;

import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 * @param <T> 实体类
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateImpl<T> {

    private final Class<T> entityClass;

    public AbstractHibernateImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        HibernateUtil.getSessionFactory().getCurrentSession().persist(entity);
    }

    public void edit(T entity) {
        HibernateUtil.getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }

    public void remove(T entity) {
        HibernateUtil.getSessionFactory().getCurrentSession().delete(entity);
    }

    public T find(Long id) {
        final T result = (T) HibernateUtil.getSessionFactory().getCurrentSession().load(entityClass, id);
        return result;
    }

    public List<T> findAll() {
        final List<T> result = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(entityClass).list();
        return result;
    }

    protected void initalizeListLazy(final List<T> list) throws HibernateException {
        for (T entity : list) {
            initalizeLazy(entity);
        }
    }

    protected void initalizeLazy(T entity) {
        // Do nothing because implement by child.
    };
}
