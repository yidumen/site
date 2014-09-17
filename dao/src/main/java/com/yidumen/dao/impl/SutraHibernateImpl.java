package com.yidumen.dao.impl;

import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class SutraHibernateImpl extends AbstractHibernateImpl<Sutra> implements SutraDAO {

    public SutraHibernateImpl() {
        super(Sutra.class);
    }

    @Override
    public List<Sutra> find(final long leftValue, final long rightValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final List<Sutra> result = currentSession.getNamedQuery("Sutra.findNodes")
                .setLong("leftValue", leftValue)
                .setLong("rightValue", rightValue)
                .list();
        transaction.commit();
        return result;
    }

    @Override
    public Sutra findByLeftvalue(final long leftValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final Sutra result = (Sutra) currentSession.getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, leftValue)
                .uniqueResult();
        transaction.commit();
        return result;
    }

    @Override
    public Sutra findByRightvalue(final long rightValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final Sutra result = (Sutra) currentSession.getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, rightValue)
                .uniqueResult();
        transaction.commit();
        return result;
    }

    @Override
    public List<Sutra> findParents(final Sutra sutra) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final List<Sutra> result = currentSession.getNamedQuery("Sutra.findParents")
                .setLong(1, sutra.getLeftValue())
                .setLong(2, sutra.getRightValue())
                .list();
        transaction.commit();
        return result;
    }

    @Override
    protected void initalizeLazy(Sutra entity) {
        Hibernate.initialize(entity.getTags());
        Hibernate.initialize(entity.getContent());
    }
    
    
}
