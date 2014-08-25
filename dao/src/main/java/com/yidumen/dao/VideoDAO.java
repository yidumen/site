package com.yidumen.dao;

import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface VideoDAO extends BaseDAO<Video> {

    List<Video> dateGroup();

    List<Video> find(VideoStatus videoStatus);

    List<Video> find(Date startTime, Date endTime);

    Video find(String file);

    List<Video> find(Date shootTime, String file);

    List<Video> find(Date shootTime, String file, int limit);

    List<Video> find(Date shootTime, int limit);

    List<Video> findRecommend();

    List<Video> getNewVideos(int limit);
    
}
