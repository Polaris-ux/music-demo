package com.mjy.musicdemo.service.impl;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.dao.ListSongMapper;
import com.mjy.musicdemo.dao.SongListMapper;
import com.mjy.musicdemo.entity.SongList;
import com.mjy.musicdemo.service.SongListService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.FileUtil;
import com.mjy.musicdemo.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-13 17:38
 */
@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private ListSongMapper listSongMapper;
    @Override
    public boolean addSongList(SongList songList) {

        return songListMapper.insert(songList)>0;
    }

    @Override
    public boolean deleteSongListById(Integer id,String pic) {
        // 删除歌单中对应的歌曲记录
        listSongMapper.deleteBySongListId(id);
        if(pic!=null){
            if(!Consts.DEFAULT_SONG_LIST_PIC.equals(pic)){
                String filename=pic.substring(pic.lastIndexOf("/")+1);
                String delPath=Consts.UPLOAD_SONG_LIST_PICTURE_ADDRESS+filename;
                FileUtil.removeFile(delPath);
            }
        }

        return songListMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean batchRemoveSongList(List<Integer> ids,List<String> paths) {
        // 删除对应的歌曲记录
        listSongMapper.deleteBySongListIds(ids);
        for (String path : paths) {
            if(path!=null){
                if(!Consts.DEFAULT_SONG_LIST_PIC.equals(path)){
                    String filename=path.substring(path.lastIndexOf("/")+1);
                    String delPath=Consts.UPLOAD_SONG_LIST_PICTURE_ADDRESS+filename;
                    FileUtil.removeFile(delPath);
                }
            }
        }

        return songListMapper.batchDeleteByIds(ids)>0;
    }

    @Override
    public boolean updateSongList(SongList songList) {

        return songListMapper.updateByPrimaryKeySelective(songList)>0;
    }

    @Override
    public SongList getSongListById(Integer id) {
        return songListMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<SongList> getSongListByKey(String key) {
        return songListMapper.selectSongListByKey(key);
    }

    @Override
    public boolean uploadAvatar(Integer id, String path, MultipartFile file) {
        if(path!=null){
            if(!Consts.DEFAULT_SONG_LIST_PIC.equals(path)){
                String oldFileName=path.substring(path.lastIndexOf("/")+1);
                String delPath= Consts.UPLOAD_SONG_LIST_PICTURE_ADDRESS+oldFileName;
                FileUtil.removeFile(delPath);
            }
        }

        String filename= IdWorker.getId()+"-"+file.getOriginalFilename();
        String savePath=Consts.UPLOAD_SONG_LIST_PICTURE_ADDRESS+filename;
        File file1 = new File(savePath);
        try {
            file.transferTo(file1);
            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic("/img/songListPic/"+filename);
            return songListMapper.updateByPrimaryKeySelective(songList)>0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getSongListNums() {
        return songListMapper.getSongListNum();
    }

    @Override
    public List<SongList> getAllSongListByKey(String key) {
        return songListMapper.selectAllSongListByKey(key);
    }
}
