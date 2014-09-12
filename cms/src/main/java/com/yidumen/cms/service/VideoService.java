package com.yidumen.cms.service;

import com.yidumen.dao.entity.Video;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface VideoService {

    Video find(Long id);

    Video find(String file);

    List<Video> getVideos();

    void removeVideo(Video video);

    void updateVideo(Video video);
    
}
