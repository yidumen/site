package com.yidumen.dao.impl;

import com.yidumen.dao.AccessInfoDAO;
import com.yidumen.dao.entity.AccessInfo;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccessInfoSHImpl extends AbstractSHImpl<AccessInfo> implements AccessInfoDAO {

    @Inject
    private SessionFactory sessionFactory;

    public AccessInfoSHImpl() {
        super(AccessInfo.class);
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
