package com.yidumen.web.service.impl;

import com.yidumen.web.service.VideoService;
import com.yidumen.dao.TagDAO;
import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import com.yidumen.web.view.model.VideoGroup;
import com.yidumen.web.view.model.VideoShootDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author 刘超 蔡迪旻 <yidumen.com>
 */
public class VideoServiceImpl implements VideoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private VideoDAO videoDao;
    @Inject
    private TagDAO tagDao;

    private final String[] month_cn;

    public VideoServiceImpl() {
        this.month_cn = new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一", "十二"};
    }

    @Override
    public List<Video> getTopNewVideos(final int limit) {
        final VideoQueryModel model = new VideoQueryModel();

        final List<VideoStatus> status = new ArrayList<>();
        status.add(VideoStatus.PUBLISH);
        model.setStatus2(status);

        model.setOrderProperty("pubDate");
        model.setDesc(true);
        model.setLimit(limit);
        return this.find(model);
    }

    @Override
    public List<Video> getRecommend() {
        final VideoQueryModel model = new VideoQueryModel();

        final List<VideoStatus> status = new ArrayList<>();
        status.add(VideoStatus.PUBLISH);
        model.setStatus2(status);

        model.setRecommendVideo(true);
        model.setRecommend(1);
        model.setRecommend2(Integer.MAX_VALUE);
        model.setOrderProperty("recommend");
        model.setDesc(true);
        return this.find(model);
    }

    @Override
    public List<VideoShootDate> getShootDateModels() {
        final List<VideoShootDate> result = new ArrayList<>();

        final List videos = videoDao.dateGroup();

        for (int i = 0; i < videos.size();) {
            final Object[] s = (Object[]) videos.get(i);
            final VideoShootDate date = new VideoShootDate();
            final String year = s[0].toString().substring(0, 4);
            date.setYear(Integer.parseInt(year));
            date.addMonth(s[0].toString().substring(4));
            videos.remove(s);
            for (int j = 0; j < videos.size();) {
                final Object[] s1 = (Object[]) videos.get(j);
                if (s1[0].toString().substring(0, 4).equals(s[0].toString().substring(0, 4))) {
                    date.addMonth(s1[0].toString().substring(4));
                    videos.remove(s1);
                } else {
                    j++;
                }
            }
            result.add(date);
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public String getChinaMonthValue(String month) {
        return month_cn[Integer.parseInt(month) - 1];
    }

    @Override
    public List<Tag> findTags(int limit) {
        return tagDao.findVideoTags(limit);
    }

    @Override
    public List<VideoGroup> findVideoGroupsInYear(final int year) {
        final List<VideoGroup> result = new ArrayList<>();
        try {
            final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
            final Date startTime = formater.parse(year + "0101 00:00:00.000");
            final Date endTime = formater.parse(year + "1231 23:59:59.999");
            final List<Video> allVideos = videoDao.find(startTime, endTime);
            List<Video> videos = new ArrayList<>();
            int currMonth = 13;
            for (Video video : allVideos) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(video.getShootTime());
                int month = calendar.get(Calendar.MONTH);
                logger.debug("findVideoGroupsInYear->month={}, currMonth={}", month, currMonth);
                if (month != currMonth) {
                    videos = new ArrayList<>();
                    result.add(new VideoGroup(calendar.getTime(), videos));
                    videos.add(video);
                    currMonth = month;
                } else {
                    videos.add(video);
                }
            }
        } catch (ParseException ex) {
            logger.error(ex.getLocalizedMessage());
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public List<VideoGroup> findVideoGroupsInMonth(final int year, final int month) {
        final List<VideoGroup> result = new ArrayList<>();
        try {
            final SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
            final Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month - 1);
            int maxMonthDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println(maxMonthDay);

            final Date startTime = formater.parse(String.format("%d%02d01 00:00:00.000", year, month));
            final Date endTime = formater.parse(String.format("%d%02d%02d 23:59:59.999", year, month, maxMonthDay));
            logger.debug("findVideoGroupsInMonth->startTime={}, endTime={}", formater.format(startTime), formater.format(endTime));
            final List<Video> allVideos = videoDao.find(startTime, endTime);
            List<Video> videos = new ArrayList<>();
            int currDay = 0;
            for (Video video : allVideos) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTime(video.getShootTime());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                logger.debug("findVideoGroupsInMonth->day={}, currday={}", day, currDay);
                if (day != currDay) {
                    videos = new ArrayList<>();
                    result.add(new VideoGroup(calendar.getTime(), videos));
                    videos.add(video);
                    logger.debug("create VideoGroups {}", formater.format(calendar.getTime()));
                    currDay = day;
                } else {
                    videos.add(video);
                }
            }
        } catch (ParseException ex) {
            logger.error(ex.getLocalizedMessage());
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public Video find(String file) {
        return videoDao.find(file);
    }

    @Override
    public List<Video> find(final Video video) {
        final VideoQueryModel model = new VideoQueryModel();
        model.setShootTime(video.getShootTime());
        model.setShootTime2(video.getShootTime());
        model.setOrderProperty("sort");
        model.setDesc(true);
        final List<Video> result = videoDao.find(model);
        result.remove(video);
        return result;
    }

    /**
     * 通过查询模型Bean来查询符合特定条件的视频。<br/>
     * model只需设置需要成为条件的属性即可，未设置的属性将被忽略掉。
     *
     * @param model
     * @return
     */
    @Override
    public List<Video> find(VideoQueryModel model) {
        return videoDao.find(model);
    }

    @Override
    public Tag findTags(String tagname) {
        return tagDao.find(tagname);
    }
}
