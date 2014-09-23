package com.yidumen.web.view.controller;

import com.yidumen.dao.entity.Goods;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.web.service.SutraService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author 蔡迪旻
 */
@Named(value = "goods")
@RequestScoped
public class GoodsController {

    @Inject
    private SutraService service;
    private List<Sutra> sutras;
    private Sutra sutra;
    private Goods goods;

    @PostConstruct
    public void init() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public String createGoods() {
        return null;
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
    
    public void setGoods(Goods goods) {
        this.goods = goods;
//        String sessionId = ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
//        CacheServiceFactory.getCacheService().put("#goods", goods);
    }
    
    public Goods getGoods() {
//        String sessionId = ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
//        Goods goods = (Goods) CacheServiceFactory.getCacheService().get("#goods");
//        if (goods == null) {
//            return new Goods();
//        }
        return goods;
    }

}
