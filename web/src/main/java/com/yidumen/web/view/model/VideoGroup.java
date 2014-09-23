package com.yidumen.web.view.model;

import com.yidumen.dao.entity.Video;
import java.util.Date;
import java.util.List;

/**
 * 把视频以发布日期按某种日期形式分组，用于分组的日期只是其中一个视频的发布日期。 当它用于显示时，应该只显示这组视频发布日期中的共同部分。
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class VideoGroup implements Comparable<VideoGroup> {

    private final Date commonDate;
    private final List<Video> videos;

    public VideoGroup(Date commonDate, List<Video> videos) {
        this.commonDate = commonDate;
        this.videos = videos;
    }

    public Date getCommonDate() {
        return commonDate;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public int compareTo(VideoGroup o) {
        return this.commonDate.compareTo(o.getCommonDate());
    }

}
