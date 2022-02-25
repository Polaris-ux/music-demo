package com.mjy.musicdemo.service.impl;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.dao.SongMapper;
import com.mjy.musicdemo.entity.Song;
import com.mjy.musicdemo.service.SongService;
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
 * @create 2022-02-11 20:15
 */
@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;

    @Override
    public Song getSongById(Integer id) {
        return songMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Song> getSongByIds(List<Integer> ids) {

        return songMapper.batchGetSong(ids);
    }

    @Override
    public Page<Song> awareSongsByName(String name) {
        return songMapper.getSongByName(name);
    }

    @Override
    public Page<Song> awareSongByNameWithBlobs(String name) {
        return songMapper.getSongByNameWithBlobs(name);
    }

    @Override
    public Page<Song> awareSongBySinger(Integer singerId,String key) {
        return songMapper.getSongBySinger(singerId,key);
    }

    @Override
    public Integer awareSongIdByName(String name) {
        return songMapper.getSongIdByName(name);
    }

    @Override
    public boolean batchRemoveSongByIds(List<Integer> ids,List<String> paths) {
        for (String path : paths) {
            String realPath=null;
            if(path!=null){
                String fileName=path.substring(path.lastIndexOf("/")+1);
                if(path.contains("/singerPic/")&&!path.equals(Consts.DEFAULT_SINGER_PIC)){
                    realPath=Consts.UPLOAD_PICTURE_ADDRESS+fileName;
                }else if(path.contains("/songPic/")&&!path.equals(Consts.DEFAULT_SONG_PIC)){
                    realPath=Consts.UPLOAD_SONG_PICTURE_ADDRESS+fileName;
                }else if(path.contains("/songStore/")){
                    realPath=Consts.UPLOAD_SONG_MP3_ADDRESS+fileName;
                }
            }
            FileUtil.removeFile(realPath);
        }

        return songMapper.deleteSongByIds(ids)>0;
    }

    @Override
    public boolean removeSongById(Integer id,String pic,String url) {
        if(pic!=null&& !Consts.DEFAULT_SONG_PIC.equals(pic)){
            String fileName=pic.substring(pic.lastIndexOf("/")+1);
            FileUtil.removeFile(Consts.UPLOAD_SONG_PICTURE_ADDRESS+fileName);
        }
        if(url!=null){
            String fileName=url.substring(url.lastIndexOf("/"));
            FileUtil.removeFile(Consts.UPLOAD_SONG_MP3_ADDRESS+fileName);
        }

        return songMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean removeSongBySingerId(Integer singerId) {
        return songMapper.deleteSongsBySinger(singerId)>0;
    }

    @Override
    public boolean addSong(Song song) {
        return songMapper.insert(song)>0;
    }

    @Override
    public boolean updateSongSelective(Song song) {
        return songMapper.updateByPrimaryKeySelective(song)>0;
    }

    @Override
    public boolean updateSongSelectiveWithBlobs(Song song) {
        return songMapper.updateByPrimaryKeyWithBLOBs(song)>0;
    }

    @Override
    public boolean uploadResource(Integer id, String path, MultipartFile file) {
        String realPath="";
        boolean isJpg=true;
        if(path!=null){
            String delPath=null;
            if(path.contains("/songStore/")){
                String filename=path.substring(path.lastIndexOf("/")+1);
                delPath=Consts.UPLOAD_SONG_MP3_ADDRESS+filename;
                realPath+=Consts.UPLOAD_SONG_MP3_ADDRESS;
                isJpg=false;
            }else if(path.contains("/songPic/")){
                if(!Consts.DEFAULT_SONG_PIC.equals(path)){
                    String filename=path.substring(path.lastIndexOf("/")+1);
                    delPath=Consts.UPLOAD_SONG_PICTURE_ADDRESS+filename;
                }
                realPath+=Consts.UPLOAD_SONG_PICTURE_ADDRESS;
            }
            FileUtil.removeFile(delPath);
        }
        if(realPath.equals("")){
            return false;
        }
        String newFileName= IdWorker.getId()+"-"+file.getOriginalFilename();

        File destFile=new File(realPath+newFileName);
        try {
            file.transferTo(destFile);
            Song song = new Song();
            song.setId(id);
            if(isJpg){
                String pic="/img/songPic/"+newFileName;
                song.setPic(pic);
            }else{
                String url="/songStore/"+newFileName;
                song.setUrl(url);
            }

            return songMapper.updateByPrimaryKeySelective(song)>0;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getSongNums() {
        return songMapper.getSongNum();
    }

    @Override
    public Page<Song> awareSongPage() {
        return songMapper.getPageSongs();
    }

    @Override
    public List<Song> awareSongByKey(String key) {
        return songMapper.getSongsByKey(key);
    }

    @Override
    public List<Song> awareAllSongBySinger(Integer singerId) {
        return songMapper.getAllSongBySingerId(singerId);
    }
}
