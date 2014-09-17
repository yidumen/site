package com.yidumen.dao.impl;

import com.yidumen.dao.AccessInfoDAO;
import com.yidumen.dao.entity.AccessInfo;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccessInfoHibernateImpl extends AbstractHibernateImpl<AccessInfo> implements AccessInfoDAO {

    public AccessInfoHibernateImpl() {
        super(AccessInfo.class);
    }
}
