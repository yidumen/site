package com.yidumen.dao.impl;

import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class SutraHibernateImpl extends AbstractHibernateImpl<Sutra> implements SutraDAO {

    public SutraHibernateImpl() {
        super(Sutra.class);
    }

    @Override
    public List<Sutra> find(final long leftValue, final long rightValue) {
        final List<Sutra> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Sutra.findNodes")
                .setLong("leftValue", leftValue)
                .setLong("rightValue", rightValue)
                .list();
        return result;
    }

    @Override
    public Sutra findByLeftvalue(final long leftValue) {
        final Sutra result = (Sutra) HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, leftValue)
                .uniqueResult();
        return result;
    }

    @Override
    public Sutra findByRightvalue(final long rightValue) {
        final Sutra result = (Sutra) HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Sutra.findByLeftValue")
                .setLong(1, rightValue)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Sutra> findParents(final Sutra sutra) {
        final List<Sutra> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Sutra.findParents")
                .setLong(1, sutra.getLeftValue())
                .setLong(2, sutra.getRightValue())
                .list();
        return result;
    }

    @Override
    public Sutra find(String title) {
        return (Sutra) HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Sutra.class)
                .add(Restrictions.eq("title", title)).uniqueResult();
    }

    @Override
    protected void initalizeLazy(Sutra entity) {
        Hibernate.initialize(entity.getTags());
        Hibernate.initialize(entity.getContent());
    }

}
