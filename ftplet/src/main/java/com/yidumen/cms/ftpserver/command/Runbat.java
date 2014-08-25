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

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public final class Runbat implements Command {

    private FtpIoSession fis;
    private static String workdir;
    private static String batName;
    public static String videoPath;
    public static String dlPath;
    public static String dlmpgPath;
    public static String dlmp3Path;

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
        final File[] files = uploadDirectory.listFiles();
        for (final File file : files) {
            final String filename = file.getName();
            if (!filename.matches("mp3$|mp4$|mpg$")) {
                continue;
            }
            final List<File> deployFiles = (List<File>) fis.getAttribute("deployFiles", new ArrayList<>());
            if (filename.endsWith("mp3")) {
                deleteVideo(filename.split("_")[0], dlPath, "mp3");
                final File dest = new File(dlmp3Path, filename);
                file.renameTo(dest);
                deployFiles.add(dest);
            } else if (filename.endsWith("mpg")) {
                deleteVideo(filename.split("_")[0], dlPath, "mpg");
                final File dest = new File(dlmpgPath, filename);
                file.renameTo(dest);
                deployFiles.add(dest);
            } else {
                final Matcher videoMatcher = VIDEO_NAME.matcher(filename);
                final Matcher dlMatcher = VIDEO_DL.matcher(filename);
                if (videoMatcher.matches()) {
                    deleteVideo(videoMatcher.group(1), videoPath, videoMatcher.group(2));
                    final File dest = new File(videoPath + videoMatcher.group(2), filename);
                    file.renameTo(dest);
                    deployFiles.add(dest);
                } else if (dlMatcher.matches()) {
                    deleteVideo(dlMatcher.group(1), dlPath, dlMatcher.group(2));
                    final File dest = new File(dlPath + dlMatcher.group(2), filename);
                    file.renameTo(dest);
                    deployFiles.add(dest);
                }
            }
        }
        console("命令执行完毕，如果没有后续操作将退出执行。");
    }

    private void deleteVideo(String filecode, String parent, String sub) {
        final File dest = new File(parent, sub);
        File[] files = dest.listFiles();
        for (File file : files) {
            final String filename = file.getName();
            if (!filename.startsWith(filecode)) {
                continue;
            }
            console("删除旧视频:" + filename);
            file.delete();
        }
    }

    private void console(String message) {
        fis.write(new DefaultFtpReply(666, message));
    }

    public void setWorkdir(String workdir) {
        Runbat.workdir = workdir;
    }

    public void setBatName(String batName) {
        Runbat.batName = batName;
    }

    public void setParent(String parent) {
        if (!parent.endsWith("/")) {
            parent = parent + "/";
        }
        dlPath = parent + "video_dl/";
        dlmpgPath = dlPath + "mpg/";
        dlmp3Path = dlPath + "mp3/";
        videoPath = parent + "video/";
    }

}
