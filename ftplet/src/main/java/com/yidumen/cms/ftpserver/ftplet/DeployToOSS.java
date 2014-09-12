package com.yidumen.cms.ftpserver.ftplet;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.openservices.oss.model.CompleteMultipartUploadResult;
import com.aliyun.openservices.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.openservices.oss.model.InitiateMultipartUploadResult;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PartETag;
import com.aliyun.openservices.oss.model.UploadPartRequest;
import com.aliyun.openservices.oss.model.UploadPartResult;
import com.yidumen.cms.ftpserver.constant.Util;
import static com.yidumen.cms.ftpserver.constant.Util.OSS_KEY;
import java.io.File;
import java.io.FileInputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class DeployToOSS extends DefaultFtplet {

    private final Logger log = LoggerFactory.getLogger(DeployToOSS.class);

    private FtpSession session;

    private String key;
    private String secret;

    @Override
    public FtpletResult afterCommand(FtpSession session,
                                     FtpRequest request,
                                     FtpReply reply)
            throws FtpException, IOException {
        log.debug("检查到命令 {}", request.getCommand());
        if (!request.getCommand().toUpperCase().equals("DEPLOY")) {
            return FtpletResult.DEFAULT;
        }
        final List<File> deployFiles = (List<File>) session.getAttribute("deployFiles");
        if (deployFiles == null) {
            log.debug("未检测到要部署的视频");
            return FtpletResult.DEFAULT;
        }
        this.session = session;

        console("部署视频到OSS");
        log.debug("创建OSS连接，key = {},secret = {}", key, secret);
        final OSSClient client = new OSSClient(key, secret);
        log.debug("连接已创建");
        deployFiles.stream().forEach((File file) -> {
            log.debug("部署文件:{}", file.getAbsolutePath());
            final Matcher matcher = OSS_KEY.matcher(file.getAbsolutePath());
            if (!matcher.matches()) {
                log.error("文件路径解析失败");
                return;
            }
            String filekey = matcher.group(1);
            log.debug("解析路径得到{}", filekey);
            filekey = filekey.replace("\\", "/");
            log.debug("替换路径分隔符得到{}", filekey);
            //删除目的文件夹中编号相同的视频
            ObjectListing listObjects = client.listObjects("yidumen", filekey);
            List<OSSObjectSummary> summaries = listObjects.getObjectSummaries();
            for (OSSObjectSummary summary : summaries) {
                console("删除OSS的旧视频:" + summary.getKey());
                client.deleteObject(summary.getBucketName(), summary.getKey());
            }
            //
            //创建视频
            ObjectMetadata metadata = new ObjectMetadata();
            final String filename = file.getName();
            String[] splitName = filename.split("_");
            if (splitName.length == 3) {
                metadata.setContentType("application/octet-stream");
            } else if (splitName.length == 2) {
                if (filename.endsWith("mp3")) {
                    metadata.setContentType("audio/mp3");
                } else if (filename.endsWith("mpg")) {
                    metadata.setContentType("video/mpg");
                } else if (filename.endsWith("mp4")) {
                    metadata.setContentType("video/mp4");
                }
            }
            log.debug("加入header:Content-Type={}", metadata.getContentType());
            metadata.setCacheControl("public");
            log.debug("加入header:Cache-Control={}", metadata.getCacheControl());
            metadata.setExpirationTime(Util.EXPIRES);

            // 开始Multipart Upload
            InitiateMultipartUploadRequest initiateMultipartUploadRequest
                    = new InitiateMultipartUploadRequest("yidumen", filekey + matcher.group(2));
            InitiateMultipartUploadResult initiateMultipartUploadResult
                    = client.initiateMultipartUpload(initiateMultipartUploadRequest);
            log.debug("准备上传Object[{}]", initiateMultipartUploadResult.getKey());
            // 设置每块为 5M
            final int partSize = 1024 * 1024 * 5;
            int partCount = (int) file.length() / partSize;
            if (file.length() % partSize != 0) {
                partCount++;
            }
            log.debug("将分为{}块上传", partCount);
            // 新建一个List保存每个分块上传后的ETag和PartNumber
            List<PartETag> partETags = new ArrayList<>();

            for (int i = 0; i < partCount; i++) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    // 跳到每个分块的开头
                    long skipBytes = partSize * i;
                    fis.skip(skipBytes);
                    // 计算每个分块的大小
                    long size = partSize < file.length() - skipBytes
                            ? partSize : file.length() - skipBytes;
                    // 创建UploadPartRequest，上传分块
                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(initiateMultipartUploadRequest.getBucketName());
                    uploadPartRequest.setKey(initiateMultipartUploadRequest.getKey());
                    uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
                    uploadPartRequest.setInputStream(fis);
                    uploadPartRequest.setPartSize(size);
                    uploadPartRequest.setPartNumber(i + 1);
                    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);

                    // 将返回的PartETag保存到List中。
                    partETags.add(uploadPartResult.getPartETag());
                    log.debug("分块{}开始上传，标识为[{}]", uploadPartResult.getPartNumber(), uploadPartResult.getPartETag());
                } catch (IOException ex) {
                    console("文件读取失败");
                    break;
                }
            }
            CompleteMultipartUploadRequest completeMultipartUploadRequest
                    = new CompleteMultipartUploadRequest(initiateMultipartUploadResult.getBucketName(),
                                                         initiateMultipartUploadResult.getKey(),
                                                         initiateMultipartUploadResult.getUploadId(), partETags);

            // 完成分块上传
            CompleteMultipartUploadResult completeMultipartUploadResult
                    = client.completeMultipartUpload(completeMultipartUploadRequest);
            console(completeMultipartUploadResult.getKey() + " 上传完毕");
        });
        session.write(new DefaultFtpReply(200, "部署至OSS的操作已完成"));
        return FtpletResult.DEFAULT;
    }

    private void console(String message) {
        try {
            session.write(new DefaultFtpReply(211, message));
        } catch (FtpException ex) {
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
