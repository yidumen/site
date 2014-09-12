package com.yidumen.dao.impl;

import com.yidumen.dao.TaoDAO;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class TagSHImpl extends AbstractSHImpl<Tag> implements TaoDAO {

    public TagSHImpl() {
        super(Tag.class);
    }

    @Override
    public List<Tag> findTags(int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Tag> result = currentSession.getNamedQuery("Tag.OrderByHints")
                .setMaxResults(limit)
                .list();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public Tag find(String tagName) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Tag result = (Tag) currentSession.getNamedQuery("Tag.findByname")
                .setString("tagname", tagName)
                .uniqueResult();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    protected void initalizeLazy(Tag entity) {
    }
}
