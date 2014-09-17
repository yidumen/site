package com.yidumen.dao.entity;

import com.yidumen.dao.constant.VideoStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 *
 * @author 蔡迪旻yidumen.com>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "video.findByFile",
                query = "SELECT v FROM Video v WHERE v.file = :file"),
    @NamedQuery(name = "video.findByStatus",
                query = "SELECT v FROM Video v WHERE v.status = :status ORDER BY v.sort DESC"),
    @NamedQuery(name = "video.findRecommend",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH AND v.recommend > 0 ORDER BY v.sort DESC"),
    @NamedQuery(name = "video.findNew",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH ORDER BY v.pubDate DESC"),
    @NamedQuery(name = "video.findBetween",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH AND v.shootTime BETWEEN ?1 AND ?2 ORDER BY v.shootTime DESC"),
    @NamedQuery(name = "video.getAutoPlayList",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH AND LOCATE('A',v.file) = 0 AND v.shootTime <= ?1 AND v.file <= ?2 ORDER BY v.shootTime, v.file"),
    @NamedQuery(name = "video.getAutoPlayList2",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH AND LOCATE('A',v.file) = 0 AND v.shootTime >= ?1 AND v.file > ?2 ORDER BY v.shootTime, v.file"),
    @NamedQuery(name = "video.getAutoPlayList3",
                query = "SELECT v FROM Video v WHERE v.status = com.yidumen.dao.constant.VideoStatus.PUBLISH AND LOCATE('A',v.file) = 0 AND v.shootTime <= ?1 ORDER BY v.shootTime, v.file")
})
@NamedNativeQuery(name = "video.dateGroup",
                  query = "SELECT DATE_FORMAT(SHOOTTIME,'%Y%m') AS 'date', count(*) AS 'count' "
                          + "FROM VIDEO "
                          + "WHERE DATE_FORMAT(SHOOTTIME,'%Y') > 2011 AND STATUS = 0 "
                          + "GROUP BY DATE_FORMAT(SHOOTTIME,'%Y%m');")
public class Video implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long sort;

    /**
     * 视频名称
     */
    @Basic(optional = false)
    private String title;
    /**
     * 视频文件编号
     */
    @Basic(optional = false)
    private String file;

    /**
     * 不同清晰度的视频信息
     */
    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy(value = "resolution")
    private List<VideoInfo> extInfo;

    @ManyToMany(mappedBy = "videos", cascade = {CascadeType.MERGE,
                                                CascadeType.PERSIST,
                                                CascadeType.REFRESH})
    private List<Tag> tags;

    private String descrpition;
    private String note;
    @Column(length = 5)
    private String grade;
    @Basic(optional = false)
    private Long duration;

    /**
     * 视频拍摄时间
     */
    @Basic(optional = false)
    private java.sql.Date shootTime;

    /**
     * 视频状态，可取的值：发布、审核、存档
     */
    @Enumerated(EnumType.ORDINAL)
    @Basic(optional = false)
    private VideoStatus status;

    private boolean chatroomVideo;

    private int recommend;

    private Date pubDate;

    @OneToMany(mappedBy = "video")
    private List<Comment> comments;

    public boolean isChatroomVideo() {
        return chatroomVideo;
    }

    public void setChatroomVideo(boolean chatroomVideo) {
        this.chatroomVideo = chatroomVideo;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * 视频发布顺序号，在页面中显示时它紧跟在名称后面为用户提示视频的索引号
     *
     * @return 表示发布索引号的长整型数据
     */
    public long getSort() {
        return sort;
    }

    public void setSort(long sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(final String file) {
        this.file = file;
    }

    public VideoStatus getStatus() {
        return status;
    }

    public void setStatus(final VideoStatus status) {
        this.status = status;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public java.sql.Date getShootTime() {
        return shootTime;
    }

    public void setShootTime(java.sql.Date shootTime) {
        this.shootTime = shootTime;
    }

    public List<VideoInfo> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(final List<VideoInfo> extInfo) {
        this.extInfo = extInfo;
    }

    /**
     * 视频发布日期.<br>
     * 这个属性的意义有两层：<br>
     * 1. 当发布日期在当前日期之后，说明这是计划发布的日期，系统将在指定时间自动进行发布工作；<br>
     * 2. 如果发布日期在当前日期之前，说明这是已发布（或以前发布过）的视频。
     *
     * @return 发布日期
     */
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getDescrpition() {
        return descrpition;
    }

    public void setDescrpition(String descrpition) {
        this.descrpition = descrpition;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
