package com.yidumen.dao.impl;

import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class SutraSHImpl extends AbstractSHImpl<Sutra> implements SutraDAO {

    public SutraSHImpl() {
        super(Sutra.class);
    }

    @Override
    public List<Sutra> find(long leftValue, long rightValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Sutra> result = currentSession.getNamedQuery("Sutra.findNodes")
                .setLong("leftValue", leftValue)
                .setLong("rightValue", rightValue)
                .list();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public Sutra findByLeftvalue(long leftValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Sutra result = (Sutra) currentSession.getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, leftValue)
                .uniqueResult();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public Sutra findByRightvalue(long rightValue) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Sutra result = (Sutra) currentSession.getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, rightValue)
                .uniqueResult();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Sutra> findParents(Sutra sutra) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Sutra> result = currentSession.getNamedQuery("Sutra.findParents")
                .setLong(1, sutra.getLeftValue())
                .setLong(2, sutra.getRightValue())
                .list();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    protected void initalizeLazy(Sutra entity) {
    }
}
