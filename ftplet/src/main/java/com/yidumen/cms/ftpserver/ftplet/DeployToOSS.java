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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import org.apache.ftpserver.ftplet.DefaultFtpReply;
import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpReply;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class DeployToOSS extends DefaultFtplet {

    private FtpSession session;

    private String key;
    private String secret;

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

        console("分发视频到OSS");
        final OSSClient client = new OSSClient(key, secret);
        deployFiles.stream().forEach((File file) -> {
            final Matcher matcher = OSS_KEY.matcher(file.getAbsolutePath());
            if (!matcher.matches()) {
                return;
            }
            //删除目的文件夹中编号相同的视频
            ObjectListing listObjects = client.listObjects("yidumen", matcher.group(1));
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
            metadata.setCacheControl("public");
            metadata.setExpirationTime(Util.EXPIRES);

            // 开始Multipart Upload
            InitiateMultipartUploadRequest initiateMultipartUploadRequest
                    = new InitiateMultipartUploadRequest("yidumen", matcher.group(1) + matcher.group(2));
            InitiateMultipartUploadResult initiateMultipartUploadResult
                    = client.initiateMultipartUpload(initiateMultipartUploadRequest);
            // 设置每块为 5M
            final int partSize = 1024 * 1024 * 5;
            int partCount = (int) file.length() / partSize;
            if (file.length() % partSize != 0) {
                partCount++;
            }
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
            console(completeMultipartUploadResult.getKey()+" 上传完毕");
        });
        return FtpletResult.DEFAULT;
    }

    private void console(String message) {
        try {
            session.write(new DefaultFtpReply(666, message));
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
