package com.yidumen.dao.impl;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoSHImpl extends AbstractSHImpl<Video> implements VideoDAO {

    @Inject
    private SessionFactory sessionFactory;

    public VideoSHImpl() {
        super(Video.class);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(VideoStatus videoStatus) {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.findByStatus")
                .setParameter(1, videoStatus);
        return q.list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> getNewVideos(int limit) {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.findNew");
        q.setMaxResults(limit);
        return q.list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> findRecommend() {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.findRecommend");
        return q.list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List dateGroup() {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.dateGroup");
        return q.list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(Date startTime, Date endTime) {
        return sessionFactory.getCurrentSession().getNamedQuery("video.findBetween")
                .setDate(1, new java.sql.Date(startTime.getTime()))
                .setDate(2, new java.sql.Date(endTime.getTime()))
                .list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public Video find(String file) {
        return (Video) sessionFactory.getCurrentSession().getNamedQuery("video.findByFile")
                .setString("file", file)
                .uniqueResult();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(Date shootTime, String file) {
        return sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList")
                .setDate(1, shootTime)
                .setString(2, file)
                .list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(Date shootTime, String file, int limit) {
        return sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList2")
                .setDate(1, shootTime)
                .setString(2, file)
                .setMaxResults(limit)
                .list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(Date shootTime, int limit) {
        return sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList3")
                .setDate(1, shootTime)
                .setMaxResults(limit)
                .list();
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
