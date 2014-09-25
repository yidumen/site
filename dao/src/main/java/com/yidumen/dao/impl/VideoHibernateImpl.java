package com.yidumen.dao.impl;

import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.VideoStatus;
import com.yidumen.dao.entity.Tag;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.entity.VideoInfo;
import com.yidumen.dao.framework.HibernateUtil;
import com.yidumen.dao.model.VideoQueryModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
@SuppressWarnings("unchecked")
public class VideoHibernateImpl extends AbstractHibernateImpl<Video> implements VideoDAO, Serializable {

    public VideoHibernateImpl() {
        super(Video.class);
    }

    @Override
    public List<Video> find(final VideoStatus videoStatus) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.findByStatus")
                .setParameter(0, videoStatus)
                .list();
        return result;
    }

    @Override
    public List<Video> getNewVideos(final int limit) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.findNew")
                .setMaxResults(limit).list();
        return result;
    }

    @Override
    public List<Video> findRecommend() {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.findRecommend").list();
        return result;
    }

    @Override
    public List dateGroup() {
        final List result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.dateGroup").list();
        return result;
    }

    @Override
    public List<Video> find(final Date startTime, final Date endTime) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.findBetween")
                .setDate(0, new java.sql.Date(startTime.getTime()))
                .setDate(1, new java.sql.Date(endTime.getTime()))
                .list();
        return result;
    }

    @Override
    public Video find(final String file) {
        final Video result = (Video) HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.findByFile")
                .setString("file", file)
                .uniqueResult();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.getAutoPlayList")
                .setDate(0, shootTime)
                .setString(1, file)
                .list();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final String file, final int limit) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.getAutoPlayList2")
                .setDate(0, shootTime)
                .setString(1, file)
                .setMaxResults(limit)
                .list();
        return result;
    }

    @Override
    public List<Video> find(final Date shootTime, final int limit) {
        final List<Video> result = HibernateUtil.getSessionFactory().getCurrentSession().getNamedQuery("video.getAutoPlayList3")
                .setDate(0, shootTime)
                .setMaxResults(limit)
                .list();
        return result;
    }

    @Override
    public List<Video> find(VideoQueryModel model) {
        final Criteria criteria = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Video.class);
        if (model.getSort2() > 0) {
            criteria.add(Restrictions.between("sort", model.getSort(), model.getSort2()));
        }
        if (model.getTitle() != null) {
            criteria.add(Restrictions.like("title", "%" + model.getTitle() + "%"));
        }
        if (model.getFile() != null) {
            criteria.add(Restrictions.between("file", model.getFile(), model.getFile2()));
        }
        if (model.getExtInfo() != null) {
            criteria.createAlias("extInfo", "ext");
            for (VideoInfo info : model.getExtInfo()) {
                for (VideoInfo info2 : model.getExtInfo2()) {
                    if (!info.getResolution().equals(info2.getResolution())) {
                        continue;
                    }
                    criteria.add(Restrictions.eq("ext.resolution", info.getResolution()))
                            .add(Restrictions.between("ext.fileSize", info.getFileSize(), info2.getFileSize()));
                }
            }
        }
        if (model.getTags() != null) {
            criteria.createAlias("tags", "tag");
            List<Criterion> restrictionses = new ArrayList<>();
            for (Tag tag : model.getTags()) {
                if (tag.getTagname() != null) {
                    restrictionses.add(Restrictions.eq("tag.tagname", tag.getTagname()));
                }
                if (tag.getType() != null) {
                    restrictionses.add(Restrictions.eq("tag.type", tag.getType()));
                }
            }
            criteria.add(Restrictions.or((Criterion[])restrictionses.toArray()));
        }
        if (model.getDescrpition() != null) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getDescrpition() + "%"));
        }
        if (model.getNote() != null) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getNote() + "%"));
        }
        if (model.getGrade() != null) {
            criteria.add(Restrictions.like("descrpition", "%" + model.getGrade()+ "%"));
        }
        if (model.getShootTime() != null) {
            criteria.add(Restrictions.between("shootTime", model.getShootTime(), model.getShootTime2()));
        }
        if (model.getStatus2() != null) {
            Criterion[] restrictionses = new Criterion[model.getStatus2().size()];
            for (int i = 0; i < restrictionses.length; i++) {
                VideoStatus status = model.getStatus2().get(i);
                restrictionses[i] = Restrictions.eq("status", status);
            }
            criteria.add(Restrictions.or(restrictionses));
        }
        if (model.isChatroom2()) {
            criteria.add(Restrictions.eq("chatroomVideo", model.isChatroomVideo()));
        }
        if (model.isRecommendVideo()) {
            criteria.add(Restrictions.between("recommend", model.getRecommend(), model.getRecommend2()));
        }
        if (model.getPubDate() != null) {
            criteria.add(Restrictions.between("pubDate", model.getPubDate(), model.getPubDate2()));
        }
        if (model.getComment2() != null) {
            criteria.createAlias("comments", "comment")
                    .add(Restrictions.like("comment.content", "%" + model.getComment2()+ "%"));
        }
        if (model.getOrderProperty() != null) {
            if (model.isDesc()) {
                criteria.addOrder(Order.desc(model.getOrderProperty()));
            } else {
                criteria.addOrder(Order.asc(model.getOrderProperty()));
            }
        }
        if (model.getLimit() > 0) {
            criteria.setMaxResults(new Long(model.getLimit()).intValue());
        }
        final List<Video> result = criteria.list();
        return result;
    }

    @Override
    protected void initalizeLazy(final Video video) throws HibernateException {
        Hibernate.initialize(video.getExtInfo());
        Hibernate.initialize(video.getTags());
        Hibernate.initialize(video.getComments());
    }

}
