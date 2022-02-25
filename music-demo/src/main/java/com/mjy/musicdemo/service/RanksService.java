package com.mjy.musicdemo.service;

import com.mjy.musicdemo.entity.Ranks;
import com.mjy.musicdemo.entity.vo.RanksVo;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-19 12:23
 */

public interface RanksService {

    boolean addRanks(Ranks rank);

    List<Ranks> getAllRanks(Integer songListId);

    RanksVo getRanksVo(Integer songListId);
}
