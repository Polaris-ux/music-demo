package com.mjy.musicdemo.service;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Singer;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-07 16:40
 */
public interface SingerService {
    /**
     * 添加歌手
     * @param singer
     * @return
     */
    boolean addSinger(Singer singer);

    /**
     * 修改歌手信息
     * @param singer
     * @return
     */
    boolean editSinger(Singer singer);

    /**
     * 有选择的修改歌手信息
     * @param singer
     * @return
     */
    boolean editSingerSelective(Singer singer);

    /**
     * 根据主键查询歌手
     * @param id
     * @return
     */
    Singer getSingerById(Integer id);

    /**
     * 获取所有歌手
     * @return
     */
    List<Singer> getAllSingersByType(Integer type);

    /**
     * 根据id删除歌手
     * @param id
     * @return
     */
    boolean removeSinger(Integer id);

    /**
     * 根据id批量删除歌手
     * @param ids
     * @return
     */
    boolean bathRemoveSinger(List<Integer> ids);

    /**
     * 根据关键字，搜索歌手
     * @param key
     * @return
     */
    Page<Singer> getSingersByKey(String key);

    /**
     * 获取歌手总数
     * @return
     */
    int getSingerNums();

}
