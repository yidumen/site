package com.yidumen.web.service;

import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import com.yidumen.web.view.model.VideoGroup;
import com.yidumen.web.view.model.VideoShootDate;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface VideoService {

    Video find(String file);

    /**
     * 根据给定的视频查找拍摄日期相同的其它视频
     * @param video
     * @return 
     */
    List<Video> find(Video video);

    List<Tag> findTags(int limit);

    List<VideoGroup> findVideoGroupsInMonth(final int year, final int month);

    List<VideoGroup> findVideoGroupsInYear(final int year);

    String getChinaMonthValue(String month);

    List<Video> getRecommend();

    List<VideoShootDate> getShootDateModels();

    List<Video> getTopNewVideos(final int limit);

    List<Video> find(VideoQueryModel model);

    Tag findTags(String tagname);
    
}
