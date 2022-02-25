package com.mjy.musicdemo.dao;

import com.mjy.musicdemo.entity.ListSong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ListSongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ListSong record);

    int insertSelective(ListSong record);

    ListSong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ListSong record);

    int updateByPrimaryKey(ListSong record);

    int deleteBySongListId(Integer id);

    List<Integer> getListSongsById(Integer songListId);

    int deleteBySongId(@Param("songId")Integer songId,@Param("songListId")Integer songListId);

    int deleteBySongIds(@Param("list")List<Integer> songIds,@Param("songListId")Integer songListId);

    int deleteBySongListIds(List<Integer> ids);
}

