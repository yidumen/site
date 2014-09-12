package com.yidumen.dao.impl;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.framework.HibernateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoSHImpl extends AbstractSHImpl<Video> implements VideoDAO, Serializable {

    public VideoSHImpl() {
        super(Video.class);
    }

    @Override
    public List<Video> find(final VideoStatus videoStatus) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.findByStatus")
                .setParameter(1, videoStatus)
                .list();
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> getNewVideos(final int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.findNew")
                .setMaxResults(limit).list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> findRecommend() {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.findRecommend").list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> dateGroup() {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.dateGroup").list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> find(final Date startTime, final Date endTime) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.findBetween")
                .setDate(1, new java.sql.Date(startTime.getTime()))
                .setDate(2, new java.sql.Date(endTime.getTime()))
                .list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public Video find(final String file) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final Video result = (Video) currentSession.getNamedQuery("video.findByFile")
                .setString("file", file)
                .uniqueResult();
        initalizeLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.getAutoPlayList")
                .setDate(1, shootTime)
                .setString(2, file)
                .list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file, final int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.getAutoPlayList2")
                .setDate(1, shootTime)
                .setString(2, file)
                .setMaxResults(limit)
                .list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final int limit) {
        final Session currentSession = HibernateUtil.getSessionFactory().getCurrentSession();
        final List<Video> result = currentSession.getNamedQuery("video.getAutoPlayList3")
                .setDate(1, shootTime)
                .setMaxResults(limit)
                .list();
        initalizeListLazy(result);
        currentSession.getTransaction().commit();
        return result;
    }

    @Override
    protected void initalizeLazy(final Video video) throws HibernateException {
        Hibernate.initialize(video.getExtInfo());
        Hibernate.initialize(video.getTags());
        Hibernate.initialize(video.getComments());
    }

}
