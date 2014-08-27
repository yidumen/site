package com.yidumen.cms.webservice;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@RestController
@RequestMapping("/video")
public class VideoWebService {

    private final Logger log = LoggerFactory.getLogger(VideoWebService.class);
    @Inject
    private VideoService service;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public List<Video> allVideo() {
        return service.getVideos();
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public Video findVideo(@RequestParam("file") String file) {
        Video video = service.find(file);
        if (video == null) {
            video = new Video();
            video.setExtInfo(new ArrayList<VideoInfo>());
        }
        return video;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public void updateVideo(@RequestBody Video video) {
        service.updateVideo(video);
    }

}
