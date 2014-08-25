package com.yidumen.cms.webservice;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.entity.Video;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Video> allVideo() {
        return service.getVideos();
    }

    @RequestMapping(value = "/{file}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Video> findVideo(@PathVariable("file") String file) {
        Video video = service.find(file);
        if (video == null) {
            return new ResponseEntity<Video>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Video>(video, HttpStatus.OK);
    }

}
