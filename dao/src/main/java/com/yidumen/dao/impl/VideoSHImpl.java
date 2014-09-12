package com.yidumen.dao.impl;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Video;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public class VideoSHImpl extends AbstractSHImpl<Video> implements VideoDAO,Serializable {

    @Inject
    private SessionFactory sessionFactory;

    public VideoSHImpl() {
        super(Video.class);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(final VideoStatus videoStatus) {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.findByStatus")
                .setParameter(1, videoStatus);
        return q.list();
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> getNewVideos(final int limit) {
        final Query q = sessionFactory.getCurrentSession().getNamedQuery("video.findNew");
        final List<Video> result = q.setMaxResults(limit).list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> findRecommend() {
        final List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.findRecommend").list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> dateGroup() {
        final List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.dateGroup").list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(final Date startTime, final Date endTime) {
        List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.findBetween")
                .setDate(1, new java.sql.Date(startTime.getTime()))
                .setDate(2, new java.sql.Date(endTime.getTime()))
                .list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public Video find(final String file) {
        final Video result = (Video) sessionFactory
                .getCurrentSession()
                .getNamedQuery("video.findByFile")
                .setString("file", file)
                .uniqueResult();
        return initalizeLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(final Date shootTime, final String file) {
        List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList")
                .setDate(1, shootTime)
                .setString(2, file)
                .list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(final Date shootTime, final String file, final int limit) {
        List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList2")
                .setDate(1, shootTime)
                .setString(2, file)
                .setMaxResults(limit)
                .list();
        return initalizeListLazy(result);
    }

    @Transactional(value = "transactionManager", readOnly = true)
    @Override
    public List<Video> find(final Date shootTime, final int limit) {
        List<Video> result = sessionFactory.getCurrentSession().getNamedQuery("video.getAutoPlayList3")
                .setDate(1, shootTime)
                .setMaxResults(limit)
                .list();
        return initalizeListLazy(result);
    }

    @Override
    protected Video initalizeLazy(final Video video) throws HibernateException {
        Hibernate.initialize(video.getExtInfo());
        Hibernate.initialize(video.getTags());
        Hibernate.initialize(video.getComments());

        return video;
    }

    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
