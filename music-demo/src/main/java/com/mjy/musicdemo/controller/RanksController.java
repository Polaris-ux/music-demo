package com.mjy.musicdemo.controller;

import com.mjy.musicdemo.entity.Ranks;
import com.mjy.musicdemo.entity.vo.RanksVo;
import com.mjy.musicdemo.service.RanksService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-19 12:28
 */
@RestController
@RequestMapping("/ranks")
public class RanksController {
    @Autowired
    private RanksService ranksService;

    @RequestMapping(value="/addRank",method= RequestMethod.POST)
    public ResultInfo addRank(Ranks rank){
        if(rank==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = ranksService.addRanks(rank);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"添加评价失败");
        }
    }

    @RequestMapping(value="/getAllRank",method= RequestMethod.GET)
    public ResultInfo getAllRank(@RequestParam("songListId")Integer songListId){
        if(songListId==null){
            throw new RuntimeException(Consts.LOGIN_FAILED);
        }

        List<Ranks> allRanks = ranksService.getAllRanks(songListId);
        return ResultInfo.ok().setData("allRanks",allRanks);
    }

    @RequestMapping(value="/getRankInfo",method= RequestMethod.GET)
    public ResultInfo getRankInfo(@RequestParam("songListId")Integer songListId){
        if(songListId==null){
            throw new RuntimeException(Consts.LOGIN_FAILED);
        }

        RanksVo ranksVo = ranksService.getRanksVo(songListId);
        int res=0;
        if(ranksVo==null||ranksVo.getAllNum()==0){
            res=0;
        }else{
            res=(int)ranksVo.getScoreSum()/ranksVo.getAllNum();
        }
        return ResultInfo.ok().setData("rank",res);
    }

}
