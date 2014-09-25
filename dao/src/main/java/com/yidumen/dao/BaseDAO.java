package com.yidumen.dao;

import java.util.List;

/**
 * 
 * @author 蔡迪旻 <yidumen.com>
 * @param <T>
 */
public interface BaseDAO<T> {

    void create(T entity);

    void edit(T entity);

    T find(Long id);

    List<T> findAll();

    void remove(T entity);
    
}
