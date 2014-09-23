package com.yidumen.web.service.impl;

import com.yidumen.dao.GoodsDAO;
import com.yidumen.dao.constant.GoodsStatus;
import com.yidumen.dao.entity.Goods;
import com.yidumen.web.service.GoodsService;
import java.util.Date;
import javax.inject.Inject;

/**
 *
 * @author 蔡迪旻
 */
public class GoodsServiceImpl implements GoodsService {

    @Inject
    private GoodsDAO goodsDAO;

    @Override
    public void create(Goods goods) {
        goods.setCreatedate(new Date());
        goods.setStatus(GoodsStatus.WAIT);
        goodsDAO.create(goods);
    }
}
