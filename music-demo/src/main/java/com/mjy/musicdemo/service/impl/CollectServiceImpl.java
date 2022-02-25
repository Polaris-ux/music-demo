package com.mjy.musicdemo.service.impl;

import com.mjy.musicdemo.dao.CollectMapper;
import com.mjy.musicdemo.entity.Collect;
import com.mjy.musicdemo.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-20 10:56
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    @Override
    public boolean addCollect(Collect collect) {
        collect.setCreateTime(new Date());
        return collectMapper.insert(collect)>0;
    }


    @Override
    public List<Collect> getAllCollectByUserId(Integer userId) {
        return collectMapper.selectAllCollectByUserId(userId);
    }

    @Override
    public boolean isExistCollect(Integer userId, Integer type, Integer id) {
        return collectMapper.selectCollectById(userId,type,id)>0;
    }

    @Override
    public boolean removeCollectByUserAndSong(Integer userId, Integer songId) {
        return collectMapper.deleteByUserAndSong(userId,songId)>0;
    }

    @Override
    public boolean batchDelCollectByUserAndSong(Integer userId, List<Integer> songIds) {
        return collectMapper.batchDeleteByUserAndSong(userId,songIds)>0;
    }
}
