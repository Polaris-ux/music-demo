package com.mjy.musicdemo.service.impl;

import com.mjy.musicdemo.dao.RanksMapper;
import com.mjy.musicdemo.entity.Ranks;
import com.mjy.musicdemo.entity.vo.RanksVo;
import com.mjy.musicdemo.service.RanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-19 12:25
 */

@Service
public class RanksServiceImpl implements RanksService {

    @Autowired
    private RanksMapper ranksMapper;

    @Override
    public boolean addRanks(Ranks rank) {
        return ranksMapper.insert(rank)>0;
    }

    @Override
    public List<Ranks> getAllRanks(Integer songListId) {
        return ranksMapper.getAllRanks(songListId);
    }

    @Override
    public RanksVo getRanksVo(Integer songListId) {
        return ranksMapper.getRanksVo(songListId);
    }
}
