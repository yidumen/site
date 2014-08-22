package com.yidumen.dao.constant;

/**
 *
 * @author 蔡迪旻<yidumen.com>
 */
public enum VideoResolution {

    SHD("720p", "超清"),
    HD("480p", "高清"),
    SD("360p", "标清"),
    FLOW("180p", "流畅");

    private final String resolution;
    private final String descript;

    private VideoResolution(String resolution, String descript) {
        this.resolution = resolution;
        this.descript = descript;
    }

    public String getResolution() {
        return resolution;
    }

    public String getDescript() {
        return descript;
    }

}
