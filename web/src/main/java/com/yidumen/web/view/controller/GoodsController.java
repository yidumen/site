package com.yidumen.web.view.controller;

import com.alibaba.appengine.api.cache.CacheService;
import com.alibaba.appengine.api.cache.CacheServiceFactory;
import com.yidumen.dao.entity.Goods;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.web.service.GoodsService;
import com.yidumen.web.service.SutraService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻
 */
@Named(value = "goods")
@RequestScoped
public class GoodsController {

    private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
    @Inject
    private SutraService service;
    @Inject
    private GoodsService gs;
    private List<Sutra> sutras;
    private Sutra sutra;

    @PostConstruct
    public void init() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public String createGoods() {
        Goods goods = getGoods();
        if (goods.getName() == null) {
            return "pretty:fail";
        }
        gs.create(goods);
        return null;
    }

    public void initBookCheck() {
        Map<String, String> request = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (log.isDebugEnabled()) {
            for (String key : request.keySet()) {
                log.debug("parameter {}={}", key, request.get(key));
            }
        }
        Goods goods = new Goods();
        goods.setName(request.get("name"));
        goods.setPhone(request.get("phone"));
        goods.setAddress(request.get("address"));
        setGoods(goods);
    }

    public void initHeartOfDharmaRealm() {
        sutras = this.service.findHeartOfDharmaRealm();
    }

    public void initStarWay() {
        sutras = this.service.findStarWay();
    }

    public void initSutra() {
        sutra = this.service.findSutra(Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));
    }

    public List<Sutra> getSutras() {
        return sutras;
    }

    public Sutra getSutra() {
        return sutra;
    }

    public Goods getGoods() {
        final String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        log.debug("session id is {}", sessionId);
        final Map<String, Object> map = (Map<String, Object>) CacheServiceFactory.getCacheService().get(sessionId);
        Goods g = (Goods) map.getOrDefault("goods", new Goods());
        log.debug("goods is {}", g.getName());
        return g;
    }

    public void setGoods(final Goods goods) {
        final String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        log.debug("session id is {}", sessionId);
        final CacheService cacheService = CacheServiceFactory.getCacheService();
        log.debug("cacheService is {}", cacheService);
        final HashMap<String, Object> map = (HashMap<String, Object>) cacheService.get(sessionId);
        map.put("goods", goods);
        cacheService.put(sessionId, map);
    }

}
