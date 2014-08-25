package com.yidumen.dao.impl;

import com.yidumen.dao.entity.Video;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 * @param <T> 实体类
 */
@Transactional
public abstract class AbstractSHImpl<T> {

    private final Class<T> entityClass;

    public AbstractSHImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract SessionFactory getSessionFactory();

    @Transactional("transactionManager")
    public void create(T entity) {
        getSessionFactory().getCurrentSession().persist(entity);
    }

    @Transactional("transactionManager")
    public void edit(T entity) {
        getSessionFactory().getCurrentSession().saveOrUpdate(entity);
    }

    @Transactional("transactionManager")
    public void remove(T entity) {
        getSessionFactory().getCurrentSession().delete(entity);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    public T find(Long id) {
        T entity = (T) getSessionFactory().getCurrentSession().load(entityClass, id);
        return initalizeLazy(entity);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    public List<T> findAll() {
        List<T> result = getSessionFactory().getCurrentSession().createCriteria(entityClass).list();
        return initalizeListLazy(result);
        
    }

    protected List<T> initalizeListLazy(final List<T> list) throws HibernateException {
        for (T entity : list) {
            initalizeLazy(entity);
        }
        return list;
    }

    abstract protected T initalizeLazy(T entity);
}
