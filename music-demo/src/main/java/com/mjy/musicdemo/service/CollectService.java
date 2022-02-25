package com.mjy.musicdemo.service;

import com.mjy.musicdemo.entity.Collect;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-20 10:54
 */
public interface CollectService {

    boolean addCollect(Collect collect);


    List<Collect> getAllCollectByUserId(Integer userId);

    boolean isExistCollect(Integer userId,Integer type,Integer id);

    boolean removeCollectByUserAndSong(Integer userId,Integer songId);

    boolean batchDelCollectByUserAndSong(Integer userId,List<Integer> songIds);
}
