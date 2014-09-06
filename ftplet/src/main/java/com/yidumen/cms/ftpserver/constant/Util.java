package com.yidumen.cms.ftpserver.constant;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class Util {
    private final static Logger log = LoggerFactory.getLogger(Util.class);
    public final static Pattern VIDEO_NAME = Pattern.compile("^(\\w{5})_(\\d{3})\\.mp4$");
    public final static Pattern VIDEO_DL = Pattern.compile("^(\\w{5})_(.*\\W)_(\\d{3})\\.mp4$");
    public final static Pattern OSS_KEY;
    public final static Pattern VIDEO_TYPE;

    public final static Date EXPIRES;

    static {
        String separator = File.separator;
        if (separator.equals("\\")) {
            separator = "\\\\";
        }
        OSS_KEY = Pattern.compile("^.*" + separator + "(\\w{5,8}" + separator + "\\w{3}" + separator + "\\w{5})(.*)$");
        log.debug("OSS_KEY= {}", OSS_KEY.pattern());
        VIDEO_TYPE = Pattern.compile("^.*" + separator + "(\\w{5,8})" + separator + "(\\w{3})" + separator + ".*$");
        log.debug("VIDEO_TYPE= {}", VIDEO_TYPE.pattern());
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 11, 31, 23, 59, 59);
        EXPIRES = cal.getTime();
    }
}
