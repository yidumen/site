package com.yidumen.cms.view.model;

import com.yidumen.dao.entity.Video;
import java.util.Date;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoQueryModel extends Video {

    private long sort2;
    private String duration2;
    private Date shootTime2;
    private Date pubDate2;

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

}
