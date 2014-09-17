package com.yidumen.dao.model;

import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import java.sql.Date;
import java.util.List;

/**
 * 用于存储视频查询条件的model.<br />
 * 视频查询条件设置规则：<br />
 * 1. id:不列入查询条件；<br />
 * 2. sort:范围；<br />
 * 3. title:包含；<br />
 * 4. file:包含；<br />
 * 5. extInfo:查询各类文件大小:范围；<br />
 * 6. tags:是否包含特定的标签:或；<br />
 * 7. descrpition:包含；<br />
 * 8. note:包含；<br />
 * 9. grade:包含；<br />
 * 10. duration://TODO 决定时长的保存和查询方式<br />
 * 11. shootTime:范围<br />
 * 12. status:或；<br />
 * 13. chatroomVideo:单条件相等；<br />
 * 14. recommend:范围<br />
 * 15. pubDate:范围；<br />
 * 16. comments:包含；<br />
 * 附加条件：<br />
 * 1. limit:限制结果数<br />
 * 2. orderProperty
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoQueryModel extends Video {

    //范围查询
    private long sort2;
    private String file2;
    private String duration2;
    private Date shootTime2;
    private Date pubDate2;
    private List<VideoInfo> extInfo2;
    private List<VideoStatus> status2;
    private boolean chatroom2;
    private boolean recommendVideo;
    private int recommend2;
    private String comment2;

    //附加条件
    private long limit;
    private String orderProperty;
    private boolean desc;

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public int getRecommend2() {
        return recommend2;
    }

    public void setRecommend2(int recommend2) {
        this.recommend2 = recommend2;
    }

    public boolean isRecommendVideo() {
        return recommendVideo;
    }

    public void setRecommendVideo(boolean recommendVideo) {
        this.recommendVideo = recommendVideo;
    }

    public String getFile2() {
        return file2;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public boolean isChatroom2() {
        return chatroom2;
    }

    public void setChatroom2(boolean chatroom2) {
        this.chatroom2 = chatroom2;
    }

    public long getSort2() {
        return sort2;
    }

    public void setSort2(long sort2) {
        this.sort2 = sort2;
    }

    public String getDuration2() {
        return duration2;
    }

    public void setDuration2(String duration2) {
        this.duration2 = duration2;
    }

    public Date getShootTime2() {
        return shootTime2;
    }

    public void setShootTime2(Date shootTime2) {
        this.shootTime2 = shootTime2;
    }

    public Date getPubDate2() {
        return pubDate2;
    }

    public void setPubDate2(Date pubDate2) {
        this.pubDate2 = pubDate2;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public List<VideoInfo> getExtInfo2() {
        return extInfo2;
    }

    public void setExtInfo2(List<VideoInfo> extInfo2) {
        this.extInfo2 = extInfo2;
    }

    public List<VideoStatus> getStatus2() {
        return status2;
    }

    public void setStatus2(List<VideoStatus> status2) {
        this.status2 = status2;
    }

}
