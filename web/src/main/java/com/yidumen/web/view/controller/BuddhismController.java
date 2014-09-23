package com.yidumen.web.view.controller;

import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.web.service.SutraService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Named("buddhist")
@RequestScoped
public class BuddhismController {

    private final static Logger log = LoggerFactory.getLogger(BuddhismController.class);

    @Inject
    private SutraService service;

    /**
     * 存储页面传递的buddhism id参数，此参数用于向后台查询相应的佛经对象
     */
    private String id;

    private Sutra buddhism;
    private List<Sutra> buddhisms;

    @PostConstruct
    public void init() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    /**
     * 了解佛教页面初始化
     *
     * @return
     */
    public String initKnown() {
        log.debug("buddhism's id is {}", id);

        if (id == null) {
            return "pretty:error";
        }
        long sid = Long.parseLong(id);

        buddhism = service.findSutra(sid);

        if (buddhism == null) {
            log.debug("no buddhsim that id is {} found.", id);
            return "pretty:error";
        }

        return null;
    }

    /**
     * 了解佛教目录页面初始化
     */
    public void initKnownDir() {
        this.buddhisms = this.service.findKnownBuddhism();
    }

    /**
     * 学习佛法、修证佛法页面初始化
     *
     * @return
     */
    public String initStudy() {
        if (id == null) {
            return "pretty:error";
        }

        buddhism = service.findSutra(Long.parseLong(id));

        if (buddhism == null) {
            log.debug("no buddhsim that id is {} found.", id);
            return "pretty:error";
        }
        return null;
    }

    /**
     * 学习佛法目录页面初始化
     */
    public void initStudyDir() {
        buddhisms = service.findStudyBuddhims();
    }

    /**
     * 修证佛法目录页面初始化
     */
    public void initPracticeDir() {
        buddhisms = service.findPracticeBuddhims();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sutra getBuddhism() {
        return buddhism;
    }

    public List<Sutra> getBuddhisms() {
        return buddhisms;
    }

}
