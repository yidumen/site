package com.yidumen.dao.impl;

import com.yidumen.dao.CommentDAO;
import com.yidumen.dao.entity.Comment;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class CommentSHImpl extends AbstractSHImpl<Comment> implements CommentDAO {

    public CommentSHImpl() {
        super(Comment.class);
    }

    @Override
    protected void initalizeLazy(Comment entity) {
    }

}
