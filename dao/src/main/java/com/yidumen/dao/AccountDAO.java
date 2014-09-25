package com.yidumen.dao;

import com.yidumen.dao.entity.Account;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface AccountDAO extends BaseDAO<Account> {

    Account find(String emailOrPhone);
    
}
