package com.mjy.musicdemo.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.musicdemo.dao.SongMapper;
import com.mjy.musicdemo.entity.Song;
import com.mjy.musicdemo.service.SongService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.IdWorker;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-11 20:29
 */
@RestController
@RequestMapping("/song")
public class SongController implements InitializingBean {


    @Autowired
    private SongService songService;


    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    public ResultInfo addSong(Song song,@RequestParam("file") MultipartFile mpFile) {
        if (song == null || mpFile == null || mpFile.isEmpty()) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        // 上传歌曲文件到服务器
        String fileName = IdWorker.getId() + "-" + mpFile.getOriginalFilename();
        File file = new File(Consts.UPLOAD_SONG_MP3_ADDRESS + fileName);
        try {
            mpFile.transferTo(file);
            String url = "/songStore/" + fileName;
            song.setUrl(url);
            song.setCreateTime(new Date());
            boolean isSuccess = songService.addSong(song);
            if (isSuccess) {
                return ResultInfo.ok();
            } else {
                return ResultInfo.error(Consts.FAILED_CODE, Consts.ADD_SONG_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResultInfo.error(Consts.SAVE_SONG_ERROR, e.getMessage());
        }
    }

    @RequestMapping(value = "/removeSongById", method = RequestMethod.GET)
    public ResultInfo removeSongById(@RequestParam("id") Integer id,@RequestParam("pic")String pic,
                                     @RequestParam("url")String url) {
        if (id == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = songService.removeSongById(id,pic,url);
        if (isSuccess) {
            return ResultInfo.ok();
        } else {
            return ResultInfo.error(Consts.FAILED_CODE, "删除歌曲失败");
        }
    }

    @RequestMapping(value = "/batchRemoveSongById", method = RequestMethod.GET)
    public ResultInfo batchRemoveSongById(@RequestParam("ids") List<Integer> ids,@RequestParam("paths") List<String> paths) {
        if (ids == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songService.batchRemoveSongByIds(ids,paths);
        if (isSuccess) {
            return ResultInfo.ok();
        } else {
            return ResultInfo.error(Consts.FAILED_CODE, "批量删除歌曲失败");
        }
    }

    @RequestMapping(value = "/getSongById", method = RequestMethod.GET)
    public ResultInfo getSongById(@RequestParam("id") Integer id) {
        if (id == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        Song songById = songService.getSongById(id);
        return ResultInfo.ok().setData("song", songById);
    }

    @RequestMapping(value = "/getSongByIds", method = RequestMethod.GET)
    public ResultInfo getSongByIds(@RequestParam("songIds")List<Integer> songIds) {
        if (songIds == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        if(songIds.size()==0){
            return ResultInfo.ok().setData("songList",new ArrayList<>());
        }
        List<Song> songByIds = songService.getSongByIds(songIds);
        return ResultInfo.ok().setData("songList", songByIds);
    }

    @RequestMapping(value = "/getIdByName", method = RequestMethod.GET)
    public ResultInfo getIdByName(@RequestParam("name")String name) {
        if (name == null||"".equals(name)) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        Integer id = songService.awareSongIdByName(name);
        if(id!=null){
            return ResultInfo.ok().setData("songId",id);
        }else {
            return ResultInfo.error(Consts.FAILED_CODE,"该歌曲不存在，请先添加歌曲");
        }
    }


    @RequestMapping(value = "/getSongsByName", method = RequestMethod.GET)
    public ResultInfo getSongsByName(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "withBlob", defaultValue = "false") boolean withBlob, @RequestParam("name") String name) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Song> pageInfo;
        if (withBlob) {
            pageInfo = new PageInfo<>(songService.awareSongByNameWithBlobs(name));
        } else {
            pageInfo = new PageInfo<>(songService.awareSongsByName(name));
        }
        return ResultInfo.ok().setData("pageInfo", pageInfo);
    }

    @RequestMapping(value = "/getSongsBySinger", method = RequestMethod.GET)
    public ResultInfo getSongsBySinger(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                       @RequestParam("singerId") Integer singerId, @RequestParam(value = "searchKey", required = false) String searchKey) {

        if (singerId == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Song> songPageInfo = new PageInfo<>(songService.awareSongBySinger(singerId, searchKey));
        return ResultInfo.ok().setData("songPageInfo", songPageInfo);
    }

    @RequestMapping(value = "/getSongsPage",method = RequestMethod.GET)
    public ResultInfo getSongsPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<Song> songsPage= songService.awareSongPage();
        PageInfo<Song> songPageInfo = new PageInfo<>(songsPage);
        return ResultInfo.ok().setData("songPageInfo",songPageInfo);
    }

    @RequestMapping(value="/getSongsByKey",method=RequestMethod.GET)
    public ResultInfo getSongsByKey(@RequestParam("key") String key){
        if(key==null||"".equals(key)){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        List<Song> list=songService.awareSongByKey(key);
        return ResultInfo.ok().setData("searchSongs",list);

    }

    @RequestMapping(value = "/updateSongInfo", method = RequestMethod.POST)
    public ResultInfo updateSongInfo(Song song, @RequestParam(value = "withBlobs", defaultValue = "false") boolean withBlobs) {
        if (song == null) {
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess;
        song.setUpdateTime(new Date());
        if (withBlobs) {
            isSuccess = songService.updateSongSelective(song);
        } else {
            isSuccess = songService.updateSongSelectiveWithBlobs(song);
        }

        if (isSuccess) {
            return ResultInfo.ok();
        } else {
            return ResultInfo.error(Consts.FAILED_CODE, "修改歌曲信息失败");
        }


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        File dir = new File(Consts.UPLOAD_SONG_MP3_ADDRESS);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dir1 = new File(Consts.UPLOAD_SONG_PICTURE_ADDRESS);
        if (!dir1.exists()) {
            dir1.mkdirs();
        }
    }

    @RequestMapping(value="/uploadStaticResource",method = RequestMethod.POST)
    public ResultInfo uploadStaticResource(@RequestParam("id")Integer id,@RequestParam("path")String path,@RequestParam("file")MultipartFile file){
        if(id==null||file==null||file.isEmpty()){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = songService.uploadResource(id, path, file);
        if (isSuccess) {
            return ResultInfo.ok();
        } else {
            return ResultInfo.error(Consts.FAILED_CODE, "修改静态资源失败");
        }


    }

    @RequestMapping(value="/getNum",method=RequestMethod.GET)
    public ResultInfo getNum(){
        int count=songService.getSongNums();
        return ResultInfo.ok().setData("songNum",count);
    }

    @RequestMapping(value="/getAllSongBySinger",method=RequestMethod.GET)
    public ResultInfo getAllSongBySinger(@RequestParam("singerId")Integer singerId){
        if(singerId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        List<Song> songs = songService.awareAllSongBySinger(singerId);
        return ResultInfo.ok().setData("songs",songs);
    }
}
