package com.mjy.musicdemo.dao;

import com.mjy.musicdemo.entity.Ranks;
import com.mjy.musicdemo.entity.vo.RanksVo;

import java.util.List;

public interface RanksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ranks record);

    int insertSelective(Ranks record);

    Ranks selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ranks record);

    int updateByPrimaryKey(Ranks record);

    List<Ranks> getAllRanks(Integer songListId);

    RanksVo getRanksVo(Integer songListId);
}