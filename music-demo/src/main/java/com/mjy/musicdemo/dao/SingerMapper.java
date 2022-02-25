package com.mjy.musicdemo.dao;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Singer;

import java.util.List;

public interface SingerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Singer record);

    int insertSelective(Singer record);

    Singer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Singer record);

    int updateByPrimaryKey(Singer record);

    List<Singer> selectAllSingersByType(Integer type);

    int batchRemoveSinger(List<Integer> ids);

    Page<Singer> selectSingersByName(String key);

    int getSingerNum();
}