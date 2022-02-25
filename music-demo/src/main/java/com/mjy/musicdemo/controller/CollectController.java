package com.mjy.musicdemo.controller;

import com.mjy.musicdemo.entity.Collect;
import com.mjy.musicdemo.service.CollectService;
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
 * @create 2022-02-20 11:00
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResultInfo add(Collect collect){
        if(collect==null||collect.getUserId()==null||collect.getType()==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean flag;
        if(collect.getType()==0){
            flag=collectService.isExistCollect(collect.getUserId(),collect.getType(),collect.getSongId());
        }else{
            flag=collectService.isExistCollect(collect.getUserId(),collect.getType(),collect.getSongListId());
        }

        if(flag){
            return ResultInfo.error(Consts.FAILED_CODE,"已经收藏");
        }
        boolean isSuccess = collectService.addCollect(collect);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"收藏失败");
        }
    }

    @RequestMapping(value="/getAllCollectByUserId",method = RequestMethod.GET)
    public ResultInfo getAllCollectByUserId(@RequestParam("userId")Integer userId){
        if(userId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        List<Collect> allCollectByUserId = collectService.getAllCollectByUserId(userId);

        return ResultInfo.ok().setData("collects",allCollectByUserId);

    }

    @RequestMapping(value="/isLike",method = RequestMethod.GET)
    public ResultInfo isLike(@RequestParam("userId")Integer userId,@RequestParam("type")Integer type,@RequestParam("songId")Integer songId){
        if(userId==null||type==null||songId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean existCollect = collectService.isExistCollect(userId, type, songId);
        return ResultInfo.ok().setData("isLike",existCollect);

    }

    @RequestMapping(value="/delByUserAndSong",method = RequestMethod.POST)
    public ResultInfo isLike(@RequestParam("userId")Integer userId,@RequestParam("songId")Integer songId){
        if(userId==null||songId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = collectService.removeCollectByUserAndSong(userId,songId);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"删除收藏失败");
        }

    }

    @RequestMapping(value="/batchDelByUserAndSong",method = RequestMethod.POST)
    public ResultInfo isLike(@RequestParam("userId")Integer userId,@RequestParam("songIds")List<Integer> songIds){
        if(userId==null||songIds==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        if(songIds.size()==0){
            return ResultInfo.ok();
        }
        boolean isSuccess = collectService.batchDelCollectByUserAndSong(userId,songIds);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"批量删除收藏失败");
        }

    }
}
