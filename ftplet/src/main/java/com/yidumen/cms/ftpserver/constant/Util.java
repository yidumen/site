package com.yidumen.cms.ftpserver.constant;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class Util {

    public final static Pattern VIDEO_NAME = Pattern.compile("^(\\w{5})_(\\d{3})\\.mp4$");
    public final static Pattern VIDEO_DL = Pattern.compile("^(\\w{5})_(.*\\W)_(\\d{3})\\.mp4$");
    public final static Pattern OSS_KEY = Pattern.compile("^.*/([video_dl]{5,8}/\\d{3}/\\w{5})(.*)$");

    public final static Date EXPIRES;

    static {
        Calendar cal = Calendar.getInstance();
        cal.set(2020, 11, 31, 23, 59, 59);
        EXPIRES = cal.getTime();
    }
}
