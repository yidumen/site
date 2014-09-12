package com.yidumen.dao.impl;

import com.yidumen.dao.AccessInfoDAO;
import com.yidumen.dao.entity.AccessInfo;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccessInfoSHImpl extends AbstractSHImpl<AccessInfo> implements AccessInfoDAO {

    public AccessInfoSHImpl() {
        super(AccessInfo.class);
    }

    @Override
    protected void initalizeLazy(AccessInfo entity) {
    }
}
