package com.yidumen.cms.service;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.entity.Video;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Service
public class VideoService {

    @Inject
    private VideoDAO videoDAO;

    public List<Video> getVideos() {
        return videoDAO.findAll();
    }

    public void updateVideo(Video video) {
        videoDAO.edit(video);
    }

    public Video find(Long id) {
        return videoDAO.find(id);
    }

    public Video find(String file) {
        return videoDAO.find(file);
    }

}
