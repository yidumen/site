package com.yidumen.cms.service;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.entity.Video;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Service
public class VideoService {
    private final Logger log = LoggerFactory.getLogger(VideoService.class);
    @Inject
    private VideoDAO videoDAO;

    public List<Video> getVideos() {
        return videoDAO.findAll();
    }

    public void updateVideo(Video video) {
        log.debug("更新Video {}", video.getTitle());
        videoDAO.edit(video);
    }

    public Video find(Long id) {
        return videoDAO.find(id);
    }

    public Video find(String file) {
        return videoDAO.find(file);
    }

    public void removeVideo(Video video) {
        videoDAO.remove(video);
        log.debug("视频 {} 已删除", video.getTitle());
    }

}
