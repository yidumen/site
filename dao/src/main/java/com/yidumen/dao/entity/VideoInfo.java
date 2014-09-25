package com.yidumen.dao.entity;

import com.yidumen.dao.constant.VideoResolution;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
@Entity
public class VideoInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Video video;

    @Basic(optional = false)
    @Enumerated(EnumType.ORDINAL)
    private VideoResolution resolution;

    private int width;

    private int height;

    @Basic(optional = false)
    private int fileSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public VideoResolution getResolution() {
        return resolution;
    }

    public void setResolution(VideoResolution resolution) {
        this.resolution = resolution;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

}
