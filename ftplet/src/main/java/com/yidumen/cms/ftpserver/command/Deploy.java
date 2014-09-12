package com.yidumen.cms.ftpserver.command;

import static com.yidumen.cms.ftpserver.constant.Util.VIDEO_DL;
import static com.yidumen.cms.ftpserver.constant.Util.VIDEO_NAME;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import org.apache.ftpserver.command.Command;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.impl.FtpIoSession;
import org.apache.ftpserver.impl.FtpServerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class Deploy implements Command {

    private final Logger log = LoggerFactory.getLogger(Deploy.class);
    private FtpIoSession fis;
    private String workdir;
    private String batName;
    private String videoPath;
    private String dlPath;
    private String dlmpgPath;
    private String dlmp3Path;

    @Override
    public void execute(final FtpIoSession fis,
                        final FtpServerContext fsc,
                        final FtpRequest fr)
            throws IOException, FtpException {
        this.fis = fis;
        final File uploadDirectory = new File(workdir);
        final ProcessBuilder builder = new ProcessBuilder(batName);
        builder.directory(uploadDirectory);
        final Process pro = builder.start();
        console("正在执行bat文件，请耐心等待");
        final BufferedReader reader;
        try (InputStream is = pro.getInputStream()) {
            reader = new BufferedReader(new InputStreamReader(is));
            reader.lines().forEach(line -> {
                console(line);
            });
        }
        reader.close();
        console("bat执行完毕，开始分发文件");
        final List<File> deployFiles = new ArrayList<>();
        final File[] files = uploadDirectory.listFiles();
        for (final File file : files) {
            final String filename = file.getName();
            if (!filename.matches(".*mp3$|.*mp4$|.*mpg$")) {
                continue;
            }
            console("找到文件" + filename);
            if (filename.endsWith("mp3")) {
                deleteVideo(filename.split("_")[0], dlPath, "mp3");
                final File dest = new File(dlmp3Path, filename);
                file.renameTo(dest);
                console("将音频" + file.getName() + "移动到相应目录");
                deployFiles.add(dest);
            } else if (filename.endsWith("mpg")) {
                deleteVideo(filename.split("_")[0], dlPath, "mpg");
                final File dest = new File(dlmpgPath, filename);
                file.renameTo(dest);
                console("将视频" + file.getName() + "移动到相应目录");
                deployFiles.add(dest);
            } else {
                final Matcher videoMatcher = VIDEO_NAME.matcher(filename);
                final Matcher dlMatcher = VIDEO_DL.matcher(filename);
                if (videoMatcher.matches()) {
                    deleteVideo(videoMatcher.group(1), videoPath, videoMatcher.group(2));
                    final File dest = new File(videoPath + videoMatcher.group(2), filename);
                    file.renameTo(dest);
                    console("将视频" + file.getName() + "移动到相应目录");
                    deployFiles.add(dest);
                } else if (dlMatcher.matches()) {
                    deleteVideo(dlMatcher.group(1), dlPath, dlMatcher.group(3));
                    final File dest = new File(dlPath + dlMatcher.group(3), filename);
                    file.renameTo(dest);
                    console("将视频" + file.getName() + "移动到相应目录");
                    deployFiles.add(dest);
                }
            }
        }
        fis.setAttribute("deployFiles", deployFiles);
        fis.write(new DefaultFtpReply(200, "命令执行完毕，如果没有后续操作将返回。"));
    }

    private void deleteVideo(String filecode, String parent, String sub) {
        log.debug("准备删除{}下{}目录中编号为{}的文件", new String[]{parent, sub, filecode});
        final File dest = new File(parent, sub);
        File[] files = dest.listFiles();
        for (File file : files) {
            final String filename = file.getName();
            if (!filename.startsWith(filecode)) {
                continue;
            }
            console("删除旧文件:" + filename);
            file.delete();
        }
    }

    private void console(String message) {
        fis.write(new DefaultFtpReply(211, message));
    }

    public void setWorkdir(String workdir) {
        this.workdir = workdir;
    }

    public void setBatName(String batName) {
        this.batName = batName;
    }

    public void setParent(String parent) {
        if (!parent.endsWith(File.separator)) {
            parent = parent + File.separator;
        }
        dlPath = parent + "video_dl" + File.separator;
        log.debug("dlPath={}", dlPath);
        dlmpgPath = dlPath + "mpg" + File.separator;
        log.debug("dlmpgPath={}", dlmpgPath);
        dlmp3Path = dlPath + "mp3" + File.separator;
        log.debug("dlmp3Path={}", dlmp3Path);
        videoPath = parent + "video" + File.separator;
        log.debug("videoPath={}", videoPath);
    }

}
