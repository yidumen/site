package com.yidumen.dao.impl;

import com.yidumen.dao.SutraMarkDAO;
import com.yidumen.dao.entity.SutraMark;
import javax.inject.Inject;
import org.hibernate.SessionFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class SutraMarkHibernateImpl extends AbstractSHImpl<SutraMark> implements SutraMarkDAO {

    @Inject
    private SessionFactory sessionFactory;

    public SutraMarkHibernateImpl() {
        super(SutraMark.class);
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    protected SutraMark initalizeLazy(SutraMark entity) {
        return entity;
    }

}
