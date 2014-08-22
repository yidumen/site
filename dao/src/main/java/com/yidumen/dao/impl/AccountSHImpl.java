package com.yidumen.dao.impl;

import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.entity.Account;
import java.io.Serializable;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccountSHImpl extends AbstractSHImpl<Account> implements AccountDAO,Serializable {

    @Inject
    private SessionFactory sessionFactory;

    public AccountSHImpl() {
        super(Account.class);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public Account find(String emailOrPhone) {
        return (Account) sessionFactory.getCurrentSession().getNamedQuery("Account.findByName")
                .setString("username", emailOrPhone)
                .uniqueResult();
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
