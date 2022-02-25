package com.mjy.musicdemo.service;

import com.mjy.musicdemo.entity.ListSong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-14 15:17
 */
public interface ListSongService {

    /**
     * 根据songListId删除记录
     *
     * @param id
     * @return
     */
    boolean removeBySongListId(Integer id);

    /**
     * 根据songListId获取songId
     *
     * @param songListId
     * @return
     */
    List<Integer> getSongsIdBySongList(Integer songListId);


    /**
     * 根据songId删除记录
     * @param songId
     * @return
     */
    boolean removeBySongId(Integer songId,Integer songListId);

    /**
     * 批量删除记录
     * @param songIds
     * @return
     */
    boolean batchRemoveBySongIds(List<Integer> songIds,Integer songListId);

    /**
     * 添加listSong
     * @param listSong
     * @return
     */
    boolean addListSong(ListSong listSong);

    /**
     * 修改
     * @param listSong
     * @return
     */
    boolean updateListSong(ListSong listSong);
}
