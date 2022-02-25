package com.mjy.musicdemo.dao;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Song;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Song record);

    int insertSelective(Song record);

    Song selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Song record);

    int updateByPrimaryKeyWithBLOBs(Song record);

    int updateByPrimaryKey(Song record);

    List<Song> batchGetSong(List<Integer> ids);

    Page<Song> getSongByName(String name);

    Page<Song> getSongByNameWithBlobs(String name);

    Page<Song> getSongBySinger(@Param("singerId")Integer singerId,@Param("key")String key);

    Integer getSongIdByName(String name);

    int deleteSongByIds(List<Integer> ids);


    int deleteSongsBySinger(Integer singerId);

    int getSongNum();

    Page<Song> getPageSongs();

    List<Song> getSongsByKey(String key);

    List<Song> getAllSongBySingerId(Integer singerId);
}