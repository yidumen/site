package com.yidumen.web.component;

import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

/**
 *
 * @author 蔡迪旻
 */
@Named(value = "player")
@RequestScoped
public class VideoPlayer {

    private String online;
    private String download;

    public VideoPlayer() {
        Map<String, Object> cookies = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        final Cookie cookieOnline = (Cookie) cookies.get("online");
        final Cookie cookieDownload = (Cookie) cookies.get("download");
        online = cookieOnline.getValue();
        download = cookieDownload.getValue();
        if ("null".equals(online)) {
            online = "360";
        }
        if ("null".equals(download)) {
            download = "360";
        }
    }

    public String getOnline() {
        return online;
    }

    public String getDownload() {
        return download;
    }

}
