package com.yidumen.web.framework;

import com.alibaba.appengine.api.cache.CacheServiceFactory;
import com.yidumen.dao.framework.HibernateUtil;
import java.util.HashMap;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web application lifecycle listener.
 *
 * @author 蔡迪旻
 */
@WebListener("ace")
public class ACEServletListener implements HttpSessionListener,
                                           ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(ACEServletListener.class);

    @Override
    public void sessionCreated(final HttpSessionEvent hse) {
        final String sessionId = hse.getSession().getId();
        HashMap<String, Object> map = new HashMap<>();
        CacheServiceFactory.getCacheService().put(sessionId, map);
        log.debug("已经为{}建立缓存", sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        final String sessionId = hse.getSession().getId();
        CacheServiceFactory.getCacheService().delete(sessionId);
        log.debug("{}缓存已删除", sessionId);
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.debug("监听器已加载");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory().close();
        log.debug("Hibernate Session Factory 已关闭");
    }

//    @Override
//    public void requestDestroyed(ServletRequestEvent sre) {
//        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
//        log.debug("已建立 Hibernate 事务");
//    }
//
//    @Override
//    public void requestInitialized(ServletRequestEvent sre) {
//        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
//        log.debug("已提交 Hibernate 事务");
//    }
}
