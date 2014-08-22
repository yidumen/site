package com.yidumen.dao;

import com.yidumen.dao.entity.Sutra;
import java.util.List;

/**
 *
 * @author 蔡迪旻 <yidumen.com>
 */
public interface SutraDAO extends BaseDAO<Sutra> {

    List<Sutra> find(long leftValue, long rightValue);

    Sutra findByLeftvalue(long leftValue);

    Sutra findByRightvalue(long rightValue);

    List<Sutra> findParents(Sutra sutra);
    
}
