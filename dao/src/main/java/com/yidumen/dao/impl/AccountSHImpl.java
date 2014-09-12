package com.yidumen.dao.impl;

import com.yidumen.dao.AccountDAO;
import com.yidumen.dao.entity.Account;
import com.yidumen.dao.framework.HibernateUtil;
import java.io.Serializable;
import org.hibernate.Session;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class AccountSHImpl extends AbstractSHImpl<Account> implements AccountDAO,Serializable {

    public AccountSHImpl() {
        super(Account.class);
    }

    @Override
    public Account find(String emailOrPhone) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        currentSession.beginTransaction();
        final Account result = (Account) currentSession.getNamedQuery("Account.findByName")
                .setString("username", emailOrPhone)
                .uniqueResult();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    protected void initalizeLazy(Account entity) {
    }

}
