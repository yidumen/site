package com.yidumen.dao.impl;

import com.yidumen.dao.CommentDAO;
import com.yidumen.dao.entity.Comment;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class CommentSHImpl extends AbstractSHImpl<Comment> implements CommentDAO {

    @Inject
    private SessionFactory sessionFactory;


    public CommentSHImpl() {
        super(Comment.class);
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
