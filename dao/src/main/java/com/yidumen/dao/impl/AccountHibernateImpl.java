package com.yidumen.dao.impl;

import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.entity.Account;
import com.yidumen.dao.framework.HibernateUtil;
import java.io.Serializable;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccountHibernateImpl extends AbstractHibernateImpl<Account> implements AccountDAO,Serializable {

    public AccountHibernateImpl() {
        super(Account.class);
    }

    @Override
    public Account find(String emailOrPhone) {
        final Account result = (Account) HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("Account.findByName")
                .setString("username", emailOrPhone)
                .uniqueResult();
        return result;
    }
}
