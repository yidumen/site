package com.yidumen.cms.webservice;

import com.yidumen.cms.service.impl.VideoServiceImpl;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoWebService {

    private final Logger log = LoggerFactory.getLogger(VideoWebService.class);
    @Inject
    private VideoServiceImpl service;

    public List<Video> allVideo() {
        return service.getVideos();
    }

    public Video findVideo(String file) {
        Video video = service.find(file);
        if (video == null) {
            video = new Video();
            video.setExtInfo(new ArrayList<VideoInfo>());
        }
        return video;
    }
    
    public void updateVideo(Video video) {
        service.updateVideo(video);
    }

}
