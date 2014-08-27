package com.yidumen.cms.ftpserver.ftplet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yidumen.cms.ftpserver.constant.Util;
import com.yidumen.dao.constant.VideoResolution;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class UpdateVideoInfo extends DefaultFtplet {

    private String finderUrl;
    private String updateUrl;
    private FtpSession session;

    @Override
    public FtpletResult afterCommand(FtpSession session,
                                     FtpRequest request,
                                     FtpReply reply)
            throws FtpException, IOException {
        if (!request.getCommand().equals("DEPLOY")) {
            return FtpletResult.DEFAULT;
        }
        final List<File> deployFiles = (List<File>) session.getAttribute("deployFiles");
        if (deployFiles == null) {
            return FtpletResult.DEFAULT;
        }
        this.session = session;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Video> videos = new ArrayList<>();
        deployFiles.stream().forEach((File file) -> {
            final String[] splitFile = file.getName().split("_");
            Video video = null;
            for (Video v : videos) {
                if (!v.getFile().equals(splitFile[0])) {
                    continue;
                }
                video = v;
                break;
            }
            if (video == null) {
                try {
                    console("向CMS服务器查询视频" + splitFile[0]);
                    String json = Request.Get(finderUrl)
                            .bodyForm(Form.form().add("file", splitFile[0]).build())
                            .execute().returnContent().asString();
                    video = objectMapper.readValue(json, Video.class);
                    video.setFile(splitFile[0]);
                    videos.add(video);
                } catch (IOException ex) {
                    console(ex.getLocalizedMessage());
                    return;
                }
            }
            Matcher m = Util.VIDEO_TYPE.matcher(file.getAbsolutePath());
            if (m.matches()) {
                if (m.group(1).equals("video_dl")) {
                    video.setTitle(splitFile[1]);
                }
                VideoInfo fileInfo = null;
                for (VideoInfo info : video.getExtInfo()) {
                    if (!info.getResolution().getDescript().equals(m.group(2))) {
                        continue;
                    }
                    info.setFileSize((int) (file.length() / 1024 / 1024));
                    fileInfo = info;
                    break;
                }
                if (fileInfo == null) {
                    fileInfo = new VideoInfo();
                    fileInfo.setResolution(VideoResolution.getResolutionByDescript(m.group(2)));
                    fileInfo.setFileSize((int) (file.length() / 1024 / 1024));
                    switch (m.group(2)) {
                        case "720":
                            fileInfo.setWidth(1280);
                            fileInfo.setHeight(720);
                            break;
                        case "480":
                            fileInfo.setWidth(720);
                            fileInfo.setHeight(480);
                            break;
                        case "360":
                            fileInfo.setWidth(640);
                            fileInfo.setHeight(360);
                            break;
                        case "180":
                            fileInfo.setWidth(320);
                            fileInfo.setHeight(180);
                    }
                    video.getExtInfo().add(fileInfo);
                    video.setStatus(VideoStatus.VERIFY);
                }
            }
        });
        for (Video video : videos) {
            console("向CMS提交视频信息:" + video.getFile());
            Request.Post(updateUrl)
                    .bodyString(objectMapper.writeValueAsString(video), ContentType.APPLICATION_JSON)
                    .execute();
        }
        console("视频信息更新完毕");
        return FtpletResult.DEFAULT;
    }

    private void console(String message) {
        try {
            session.write(new DefaultFtpReply(666, message));
        } catch (FtpException ex) {
        }
    }

    public void setFinderUrl(String finderUrl) {
        this.finderUrl = finderUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

}
