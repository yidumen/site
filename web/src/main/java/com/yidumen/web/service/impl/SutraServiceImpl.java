package com.yidumen.web.service.impl;

import com.yidumen.dao.GoodsDAO;
import com.yidumen.web.service.SutraService;
import com.yidumen.dao.SutraDAO;
import com.yidumen.dao.SutraMarkDAO;
import com.yidumen.dao.VideoDAO;
import com.yidumen.dao.constant.GoodsStatus;
import com.yidumen.dao.entity.Goods;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.entity.Video;
import com.yidumen.dao.model.VideoQueryModel;
import com.yidumen.web.view.model.LotusModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.inject.Inject;

/**
 *
 * @author 刘超 蔡迪旻<yidumen.com>
 */
public class SutraServiceImpl implements SutraService,Serializable {

    @Inject
    private SutraDAO sutraDao;
    @Inject
    private SutraMarkDAO markDao;
    @Inject
    private VideoDAO videoDAO;
    @Inject
    private GoodsDAO goodsDAO;

    //暂使用这个属性来计算了解佛教里面相关视频的代码值
    private int c;

    public SutraServiceImpl() {
        this.c = 63;
    }

    @Override
    public List<Sutra> findSutras() {
        return sutraDao.findAll();
    }

    @Override
    public Sutra findMark(long id) {
        return markDao.find(id).getNode();
    }

    @Override
    public List<LotusModel> getLotusSutras() {
        final List<LotusModel> result = new ArrayList<>();

        final List<Sutra> originals = getLotusSutrasOriginal();
        final List<Sutra> vernaculars = getLotusSutrasVernacular();
        for (Sutra vernacular : vernaculars) {
            for (Sutra original : originals) {
                if (original.getLeftValue() + 58L != vernacular.getLeftValue()) {
                    continue;
                }
                final LotusModel model = new LotusModel();
                model.setPartIdentifier(original.getPartIdentifier());
                model.setTitle(original.getTitle());
                model.setOriginalId(original.getId());
                model.setVernacularId(vernacular.getId());

                result.add(model);
            }
        }
        return result;
    }

    @Override
    public List<Sutra> getLotusSutrasOriginal() {
        return sutraDao.find(8L, 65L);
    }

    @Override
    public List<Sutra> getLotusSutrasVernacular() {
        return sutraDao.find(66L, 123L);
    }

    @Override
    public int getPageCount(Sutra sutra) {
        return (int) ((sutra.getRightValue() - sutra.getLeftValue() + 1) / 2);
    }

    @Override
    public String getVideoCode(Sutra sutra) {
        //计算方法：左值/2-c,然后c+1;
        return String.format("C1%03d", sutra.getLeftValue() / 2 - c++);
    }

    @Override
    public Sutra findPreSutra(Sutra sutra) {
        return sutraDao.findByRightvalue(sutra.getLeftValue() - 1);
    }

    @Override
    public Sutra findNextSutra(Sutra sutra) {
        return sutraDao.findByLeftvalue(sutra.getRightValue() + 1);
    }

    @Override
    public Sutra findSutra(long id) {
        return sutraDao.find(id);
    }

    @Override
    public List<Sutra> findHeartOfDharmaRealm() {
        return sutraDao.find(436, 565);
    }

    @Override
    public Sutra findSutra(String title) {
        return sutraDao.find(title);
    }

    @Override
    public List<Sutra> findStarWay() {
        return sutraDao.find(420, 435);
    }

    @Override
    public List<Sutra> findKnownBuddhism() {
        return removeSubPage(sutraDao.find(124, 237));
    }

    @Override
    public List<Sutra> findStudyBuddhims() {
        return removeSubPage(sutraDao.find(238, 407));
    }

    @Override
    public List<Sutra> findPracticeBuddhims() {
        return removeSubPage(sutraDao.find(408, 419));
    }
    
    private List<Sutra> removeSubPage(final List<Sutra> sutras) {
        final Pattern pattern = Pattern.compile("第\\d页");
        final List<Sutra> result = new ArrayList<>();
        for (final Sutra sutra : sutras) {
            if (pattern.matcher(sutra.getTitle()).matches()) {
                continue;
            }
            result.add(sutra);
        }
        sutras.clear();
        return result;
    }

    @Override
    public List<Video> findLoutsVideos() {
        final VideoQueryModel model = new VideoQueryModel();
        model.setFile("A0001");
        model.setFile2("A1006");
        model.setOrderProperty("file");
        model.setDesc(false);
        return videoDAO.find(model);
    }

}
