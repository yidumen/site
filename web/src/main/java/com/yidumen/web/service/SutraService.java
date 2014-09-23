package com.yidumen.web.service;

import com.yidumen.dao.entity.Goods;
import com.yidumen.dao.entity.Sutra;
import com.yidumen.dao.entity.Video;
import com.yidumen.web.view.model.LotusModel;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface SutraService {

    Sutra findMark(long id);

    Sutra findNextSutra(Sutra sutra);

    Sutra findPreSutra(Sutra sutra);

    Sutra findSutra(long id);

    List<Sutra> findSutras();

    List<LotusModel> getLotusSutras();

    List<Sutra> getLotusSutrasOriginal();

    List<Sutra> getLotusSutrasVernacular();

    int getPageCount(Sutra sutra);

    String getVideoCode(Sutra sutra);

    List<Sutra> findHeartOfDharmaRealm();

    Sutra findSutra(String title);

    List<Sutra> findStarWay();

    List<Sutra> findKnownBuddhism();

    List<Sutra> findStudyBuddhims();

    List<Sutra> findPracticeBuddhims();

    List<Video> findLoutsVideos();

}
