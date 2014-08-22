package com.yidumen.cms.view.controller;

import com.yidumen.cms.service.VideoService;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@Controller("video")
public class VideoManager {

    @Inject
    private VideoService service;
    
    private Video video;
    
    public void updateVideo() {
        service.updateVideo(video);
        FacesContext.getCurrentInstance().addMessage(":form:table", new FacesMessage("视频已编辑，稍等会更新"));
        final RequestContext currentInstance = RequestContext.getCurrentInstance();
        currentInstance.closeDialog("/content/video-edit");
    }
    
    public List<Video> getVideos() {
        return service.getVideos();
    }
    
    public VideoStatus[] getVideoStatuses() {
        return VideoStatus.values();
    }
    
    public void showEditDialog() {
        RequestContext.getCurrentInstance().openDialog("/content/video-edit");
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
