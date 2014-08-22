package com.yidumen.dao.impl;

import com.yidumen.dao.TaoDAO;
import com.yidumen.dao.BaseDAO;
import com.yidumen.dao.entity.Tag;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class TagSHImpl extends AbstractSHImpl<Tag> implements TaoDAO {

    @Inject
    private SessionFactory sessionFactory;

    public TagSHImpl() {
        super(Tag.class);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Tag> findTags(int limit) {
        return sessionFactory.getCurrentSession().getNamedQuery("Tag.OrderByHints")
                .setMaxResults(limit)
                .list();
    }
    
    @Override
    public Tag find(String tagName) {
        return (Tag) sessionFactory.getCurrentSession().getNamedQuery("Tag.findByname")
                .setString("tagname", tagName)
                .uniqueResult();
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
