package com.yidumen.dao.impl;

import com.yidumen.dao.TagDAO;
import com.yidumen.dao.constant.TagType;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class TagHibernateImpl extends AbstractHibernateImpl<Tag> implements TagDAO {

    public TagHibernateImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> findTags(int limit) {
        final List<Tag> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Tag.OrderByHints")
                .setMaxResults(limit)
                .list();
        return result;
    }
    
    @Override
    public List<Tag> findVideoTags(final int limit) {
        final Criteria criteria = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Tag.class)
                .add(Restrictions.eq("type", TagType.CONTENT))
                .add(Restrictions.isNotEmpty("videos"))
                .addOrder(Order.desc("hits"));
        if (limit > 0) {
            criteria.setMaxResults(limit);
        }
        final List<Tag> result = criteria.list();
        return result;
    }

    @Override
    public Tag find(String tagName) {
        final Tag result = (Tag) HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Tag.findByname")
                .setString("tagname", tagName)
                .uniqueResult();
        return result;
    }

    @Override
    protected void initalizeLazy(Tag entity) {
        Hibernate.initialize(entity.getVideos());
        Hibernate.initialize(entity.getSutras());
    }
    
}
