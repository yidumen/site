package com.yidumen.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
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
        return (T) getSessionFactory().getCurrentSession().get(entityClass, id);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    public List<T> findAll() {
        Criteria cq = getSessionFactory().getCurrentSession().createCriteria(entityClass);
        return cq.list();
    }

}
