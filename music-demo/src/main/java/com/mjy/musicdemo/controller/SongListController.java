package com.mjy.musicdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.musicdemo.entity.SongList;
import com.mjy.musicdemo.service.SongListService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-13 18:26
 */
@RestController
@RequestMapping("/songList")
public class SongListController implements InitializingBean {
    @Autowired
    private SongListService songListService;

    @RequestMapping(value="/addSongList",method = RequestMethod.POST)
    public ResultInfo addSongList(SongList songList){
        if(songList==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songListService.addSongList(songList);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.ADD_SONG_LIST_ERROR,Consts.ADD_SONG_LIST_FAILED);
        }
    }

    @RequestMapping(value="/delSongListById",method = RequestMethod.GET)
    public ResultInfo delSongListById(@RequestParam("id") Integer id,@RequestParam("pic")String pic){
        if(id==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songListService.deleteSongListById(id,pic);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"删除歌单失败");
        }
    }

    @RequestMapping(value="/batchRemoveSongListByIds",method = RequestMethod.GET)
    public ResultInfo batchRemoveSongListByIds(@RequestParam("ids") List<Integer> ids,@RequestParam("paths")List<String> paths){
        if(ids==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songListService.batchRemoveSongList(ids,paths);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"批量删除歌单失败");
        }
    }

    @RequestMapping(value="/updateSongList",method = RequestMethod.POST)
    public ResultInfo updateSongList(SongList songList){
        if(songList==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songListService.updateSongList(songList);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"修改歌单信息失败");
        }
    }

    @RequestMapping(value="/getSongListByKey",method = RequestMethod.GET)
    public ResultInfo getSongListByKey(@RequestParam(value="pageNum",defaultValue="1")int pageNum,@RequestParam(value="pageSize",defaultValue="7")int pageSize
            ,@RequestParam(value="key",required = false)String key){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<SongList> songListPageInfo = new PageInfo<>(songListService.getSongListByKey(key));
        return ResultInfo.ok().setData("songListPageInfo",songListPageInfo);
    }

    @RequestMapping(value="/getSongListById",method = RequestMethod.GET)
    public ResultInfo getSongListById(@RequestParam("id")Integer id){
        if(id==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        SongList songListById = songListService.getSongListById(id);
        return ResultInfo.ok().setData("songList",songListById);
    }

    @RequestMapping(value="/uploadAvatar",method = RequestMethod.POST)
    public ResultInfo uploadAvatar(@RequestParam("id")Integer id, @RequestParam("path")String path,
                                   @RequestParam("file")MultipartFile file){
        if(id==null||file==null||file.isEmpty()){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songListService.uploadAvatar(id, path, file);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"更新图片失败");
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        File file = new File(Consts.UPLOAD_SONG_LIST_PICTURE_ADDRESS);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    @RequestMapping(value="/getNum",method=RequestMethod.GET)
    public ResultInfo getNum(){
        int count=songListService.getSongListNums();
        return ResultInfo.ok().setData("songListNum",count);
    }

    @RequestMapping(value="/getAllSongListByKey",method=RequestMethod.GET)
    public ResultInfo getAllSongListByKey(@RequestParam(value="key",required = false)String key){

        List<SongList> allSongListByKey = songListService.getAllSongListByKey(key);
        return ResultInfo.ok().setData("songLists",allSongListByKey);
    }
}
