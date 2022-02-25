package com.mjy.musicdemo.service;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.SongList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-13 17:30
 */
public interface SongListService {
    /**
     * 添加一个歌单
     * @param songList
     * @return
     */
    boolean addSongList(SongList songList);

    /**
     * 根据id删除歌单
     * @param id
     * @return
     */
    boolean deleteSongListById(Integer id,String pic);

    /**
     * 批量删除歌单
     * @param ids
     * @return
     */
    boolean batchRemoveSongList(List<Integer> ids,List<String> paths);


    /**
     * 修改songList
     * @param songList
     * @return
     */
    boolean updateSongList(SongList songList);


    /**
     * 根据id查询歌单
     * @param id
     * @return
     */
    SongList getSongListById(Integer id);

    /**
     * 根据key进行模糊查询
     * @param key
     * @return
     */
    Page<SongList> getSongListByKey(String key);


    /**
     * 更新图片
     * @param id
     * @param path
     * @param file
     * @return
     */
    boolean uploadAvatar(Integer id, String path, MultipartFile file);


    /**
     * 获取歌单总数
     * @return
     */
    int getSongListNums();


    List<SongList> getAllSongListByKey(String key);
}
