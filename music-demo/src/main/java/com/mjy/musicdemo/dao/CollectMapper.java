package com.mjy.musicdemo.dao;

import com.mjy.musicdemo.entity.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<Collect> selectAllCollectByUserId(Integer userId);

    int selectCollectById(@Param("userId")Integer userId,@Param("type")Integer type,@Param("id")Integer id);

    int deleteByUserAndSong(@Param("userId")Integer userId,@Param("songId")Integer songId);

    int batchDeleteByUserAndSong(@Param("userId")Integer userId,@Param("songIds")List<Integer> songIds);
}