package com.yidumen.dao.impl;

import com.yidumen.dao.TagDAO;
import com.yidumen.dao.constant.TagType;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class TagHibernateImpl extends AbstractHibernateImpl<Tag> implements TagDAO {

    public TagHibernateImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> findTags(int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final List<Tag> result = currentSession.getNamedQuery("Tag.OrderByHints")
                .setMaxResults(limit)
                .list();
        transaction.commit();
        return result;
    }
    
    @Override
    public List<Tag> findVideoTags(final int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final Criteria criteria = currentSession.createCriteria(Tag.class)
                .add(Restrictions.eq("type", TagType.CONTENT))
                .add(Restrictions.isNotEmpty("videos"))
                .addOrder(Order.desc("hits"));
        if (limit > 0) {
            criteria.setMaxResults(limit);
        }
        final List<Tag> result = criteria.list();
        transaction.commit();
        return result;
    }

    @Override
    public Tag find(String tagName) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final Tag result = (Tag) currentSession.getNamedQuery("Tag.findByname")
                .setString("tagname", tagName)
                .uniqueResult();
        transaction.commit();
        return result;
    }
}
