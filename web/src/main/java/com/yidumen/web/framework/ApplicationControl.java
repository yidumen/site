package com.yidumen.web.framework;

import com.yidumen.dao.framework.HibernateUtil;
import javax.annotation.PreDestroy;

/**
 *
 * @author 蔡迪旻
 */
public class ApplicationControl {

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().close();
    }
}
