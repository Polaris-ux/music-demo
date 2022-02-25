package com.mjy.musicdemo.controller;

import com.mjy.musicdemo.entity.ListSong;
import com.mjy.musicdemo.service.ListSongService;
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
 * @create 2022-02-14 15:45
 */
@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    @RequestMapping(value="/addListSong",method=RequestMethod.POST)
    public ResultInfo addListSong(ListSong listSong){
        if(listSong==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = listSongService.addListSong(listSong);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"添加歌单与歌曲的映射失败");
        }
    }

    @RequestMapping(value="/getSongIds",method=RequestMethod.GET)
    public ResultInfo getSongIds(@RequestParam("songListId")Integer songListId){
        if(songListId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        List<Integer> songIds = listSongService.getSongsIdBySongList(songListId);
        return ResultInfo.ok().setData("songIds",songIds);
    }

    @RequestMapping(value="/delListSong",method=RequestMethod.GET)
    public ResultInfo delListSong(@RequestParam("songId")Integer songId,@RequestParam("songListId")Integer songListId){

        if(songId==null||songListId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = listSongService.removeBySongId(songId,songListId);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"删除歌单与歌曲的映射失败");
        }
    }

    @RequestMapping(value="/batchRemoveListSong",method=RequestMethod.GET)
    public ResultInfo batchRemoveListSong(@RequestParam("songIds")List<Integer> songIds,@RequestParam("songListId")Integer songListId){
        if(songIds==null||songListId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = listSongService.batchRemoveBySongIds(songIds,songListId);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"批量删除歌单与歌曲的映射失败");
        }
    }
}
