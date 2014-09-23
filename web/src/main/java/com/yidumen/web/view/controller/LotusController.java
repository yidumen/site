package com.yidumen.web.view.controller;

import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.web.service.SutraService;
import com.yidumen.web.view.model.LotusModel;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * 《妙法莲华》栏目后台Bean
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Named("lotus")
@RequestScoped
public class LotusController {

    @Inject
    private SutraService service;

    private Long oid;
    private Long vid;
    private Sutra original;
    private List<Video> videos;
    private int i;

    @PostConstruct
    public void init() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    public void changeVideoIndex(int i) {
        this.i = i;
    }
    
    public List<LotusModel> getLotusSutras() {
        return service.getLotusSutras();
    }

    public List<Sutra> getOriginals() {
        return service.getLotusSutrasOriginal();
    }

    public List<Sutra> getVernaculars() {
        return service.getLotusSutrasVernacular();
    }

    public String getPreTitle() {
        return service.findPreSutra(original).getTitle();
    }

    public String getNextTitle() {
        return service.findNextSutra(original).getTitle();
    }

    public Sutra getSutra() {
        return original;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Sutra getOriginal() {
        return original;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public int getI() {
        return i;
    }

    /**
     * 内部调用，勿用于页面.<br />
     * 初始化
     *
     * @return
     */
    public String loadSutra() {
        if (oid == null) {
            return "pretty:error";
        }
        switch (oid.intValue()) {
            case 1995:
            case 2052:
                original = service.findMark(oid);
                break;
            default:
                original = service.findSutra(oid);
        }
        if (original == null) {
            return "pretty:error";
        }

        return null;
    }
    
    public void initLotus() {
        videos = service.findLoutsVideos();
        i = 0;
    }
}
