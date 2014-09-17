package com.yidumen.dao.impl;

import com.yidumen.dao.SutraMarkDAO;
import com.yidumen.dao.entity.SutraMark;
import org.hibernate.Hibernate;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class SutraMarkHibernateImpl extends AbstractHibernateImpl<SutraMark> implements SutraMarkDAO {

    public SutraMarkHibernateImpl() {
        super(SutraMark.class);
    }

    @Override
    protected void initalizeLazy(SutraMark entity) {
        Hibernate.initialize(entity.getNode());
    }
    
}
