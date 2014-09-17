package com.yidumen.dao.impl;

import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.entity.Account;
import com.yidumen.dao.framework.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Transaction transaction = currentSession.beginTransaction();
        final Account result = (Account) currentSession.getNamedQuery("Account.findByName")
                .setString("username", emailOrPhone)
                .uniqueResult();
        transaction.commit();
        return result;
    }
}
