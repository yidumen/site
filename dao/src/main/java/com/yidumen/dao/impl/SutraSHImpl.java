package com.yidumen.dao.impl;

import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.entity.Sutra;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class SutraSHImpl extends AbstractSHImpl<Sutra> implements SutraDAO {

    @Inject
    private SessionFactory sessionFactory;

    public SutraSHImpl() {
        super(Sutra.class);
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public List<Sutra> find(long leftValue, long rightValue) {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("Sutra.findNodes")
                .setLong("leftValue", leftValue)
                .setLong("rightValue", rightValue);
        return q.list();
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Sutra findByLeftvalue(long leftValue) {
        return (Sutra) sessionFactory.getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, leftValue)
                .uniqueResult();
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public Sutra findByRightvalue(long rightValue) {
        return (Sutra) sessionFactory.getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, rightValue)
                .uniqueResult();
    }

    @Override
    @Transactional(value = "transactionManager", readOnly = true)
    public List<Sutra> findParents(Sutra sutra) {
        return sessionFactory.getCurrentSession().getNamedQuery("Sutra.findParents")
                .setLong(1, sutra.getLeftValue())
                .setLong(2, sutra.getRightValue())
                .list();
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    protected Sutra initalizeLazy(Sutra entity) {
        return entity;
    }
}
