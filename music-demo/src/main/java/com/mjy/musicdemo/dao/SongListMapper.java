package com.mjy.musicdemo.dao;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.SongList;

import java.util.List;

public interface SongListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SongList record);

    int insertSelective(SongList record);

    SongList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SongList record);

    int updateByPrimaryKeyWithBLOBs(SongList record);

    int updateByPrimaryKey(SongList record);

    int batchDeleteByIds(List<Integer> ids);

    Page<SongList> selectSongListByKey(String key);


    int getSongListNum();


    List<SongList> selectAllSongListByKey(String key);
}