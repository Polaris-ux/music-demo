package com.mjy.musicdemo.service.impl;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.dao.SingerMapper;
import com.mjy.musicdemo.entity.Singer;
import com.mjy.musicdemo.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-07 16:40
 */
@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;

    @Override
    public boolean addSinger(Singer singer) {
        return singerMapper.insert(singer)>0;

    }

    @Override
    public boolean editSinger(Singer singer) {
        return singerMapper.updateByPrimaryKey(singer)>0;
    }

    @Override
    public boolean editSingerSelective(Singer singer) {
        return singerMapper.updateByPrimaryKeySelective(singer)>0;
    }

    @Override
    public Singer getSingerById(Integer id) {
        return singerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Singer> getAllSingersByType(Integer type) {
        return singerMapper.selectAllSingersByType(type);
    }

    @Override
    public boolean removeSinger(Integer id) {
        return singerMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean bathRemoveSinger(List<Integer> ids) {
        return singerMapper.batchRemoveSinger(ids)>0;
    }

    @Override
    public Page<Singer> getSingersByKey(String key) {
        return singerMapper.selectSingersByName(key);
    }

    @Override
    public int getSingerNums() {
        return singerMapper.getSingerNum();
    }
}
