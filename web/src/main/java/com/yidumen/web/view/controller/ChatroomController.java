package com.yidumen.web.view.controller;

import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.web.service.VideoService;
import com.yidumen.web.view.model.VideoGroup;
import com.yidumen.web.view.model.VideoShootDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 刘超 蔡迪旻 <yidumen.com>
 */
@Named(value = "chatroom")
@RequestScoped
public class ChatroomController {

    private final static Logger logger = LoggerFactory.getLogger(ChatroomController.class);
    private int year;
    private int month;
    private String file;
    private String tagname;
    private Tag tag;

    @Inject
    private VideoService service;

    private List<Video> videos;
    private List<Video> recommends;

    private List<VideoGroup> videoGroups;
    private List<VideoShootDate> shootDates;
    private Video video;
    private String videoFormat;
    private Map<String, Integer> videoSize;
    private String readvideos;
    private String choise;
    private Video nextVideo;
    private String commit;
    private List<Tag> tags;


    @PostConstruct
    public void init() {
        HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
    }

    @PreDestroy
    public void destroy() {
        HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
    }

    /**
     * 日期列表页面初始化
     */
    public void initDate() {
        shootDates = service.getShootDateModels();
    }

    /**
     * 年份页面初始化
     *
     * @return
     */
    public String initYear() {
        if (year == 0) {
            return "pretty:failed";
        }

        videoGroups = service.findVideoGroupsInYear(year);

        if (videoGroups.isEmpty()) {
            return "pertty:error";
        }

        return null;
    }

    /**
     * 月份页面初始化
     *
     * @return
     */
    public String initMonth() {
        logger.debug("initMonth->year={}, month={}", year, month);

        if (year == 0 || month == 0) {
            return "pretty:failed";
        }

        videoGroups = service.findVideoGroupsInMonth(year, month);

        if (videoGroups.isEmpty()) {
            return "pertty:error";
        }

        return null;
    }

    /**
     * 单个视频播放页面初始化
     *
     * @return
     */
    public String initSingle() {
        logger.debug("file is {}", this.file);
        if (this.file == null || this.file.isEmpty()) {
            return "pretty:failed";
        }

        this.video = service.find(file);

        if (this.video == null) {
            return "pretty:error";
        }

        this.videos = service.find(video);
        return null;
    }

    /**
     * 初始化标签列表页面
     */
    public void initTags() {
        this.tags = service.findTags(0);
        logger.debug("{}",this.tags.size());
    }

    /**
     * 初始化单个标签页面
     */
    public void initTag() {
        this.tag = service.findTags(this.tagname);
    }

    /**
     * 最新视频页面初始化
     */
    public void loadpage() {
        recommends = service.getRecommend();
        videos = service.getTopNewVideos(15);
    }

    public String createKeywords() {
        final String regEx = "[`~!@#$%^&*()+=-|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]１２３４５６７８９０";
        final Matcher m = Pattern.compile(regEx).matcher(video.getTitle());
        return m.replaceAll("").trim() + ",杨宁视频,佛学视频,杨宁老师,易度门";
    }

    public boolean isPulished(Video video) {
        return video.getStatus() == VideoStatus.PUBLISH;
    }

    public String getMonthCN(String month) {
        return service.getChinaMonthValue(month);
    }

    
    
    public void pubComment() {
//        if (loginController.getUser() == null) {

//        }
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter">
    public List<VideoShootDate> getShootDates() {
        return shootDates;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Tag getTag() {
        return tag;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public List<Video> getRecommends() {
        return recommends;
    }

    public List<VideoGroup> getVideoGroups() {
        return videoGroups;
    }

    public Video getVideo() {
        return video;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public Map<String, Integer> getVideoSize() {
        return videoSize;
    }

    public String getReadvideos() {
        return readvideos;
    }

    public String getChoise() {
        return choise;
    }

    public Video getNextVideo() {
        return nextVideo;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

//</editor-fold>
}
