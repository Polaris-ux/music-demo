package com.mjy.musicdemo.service;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-11 19:56
 */
public interface SongService {

    /**
     * 根据id获取歌曲信息
     * @param id
     * @return
     */
    Song getSongById(Integer id);

    /**
     * 根据ids批量获取歌曲
     */
    List<Song> getSongByIds(List<Integer> ids);

    /**
     * 分页查询歌曲(模糊查询)
     * @param name
     * @return
     */
    Page<Song> awareSongsByName(String name);

    /**
     * 分页查询歌曲带歌词(模糊查询)
     * @param name
     * @return
     */
    Page<Song> awareSongByNameWithBlobs(String name);


    /**
     * 根据歌曲分页查询歌曲（不带歌词）
     * @param singerId
     * @return
     */
    Page<Song> awareSongBySinger(Integer singerId,String key);

    /**
     * 根据name获取歌曲id
     * @param name
     * @return
     */
    Integer awareSongIdByName(String name);

    /**
     * 批量删除歌曲
     * @param ids
     * @return
     */
    boolean batchRemoveSongByIds(List<Integer> ids,List<String> paths);

    /**
     * 删除歌曲
     * @param id
     * @return
     */
    boolean removeSongById(Integer id,String pic,String url);

    /**
     * 根据歌手删除歌曲
     * @param singerId
     * @return
     */
    boolean removeSongBySingerId(Integer singerId);


    /**
     * 添加歌曲
     * @param song
     * @return
     */
    boolean addSong(Song song);


    /**
     * 有选择的更新歌曲信息
     * @param song
     * @return
     */
    boolean updateSongSelective(Song song);

    /**
     * 有选择的更新歌曲信息(包括歌词)
     * @param song
     * @return
     */
    boolean updateSongSelectiveWithBlobs(Song song);


    /**
     * 更新图片音频等
     * @param id
     * @param path
     * @param file
     * @return
     */
    boolean uploadResource(Integer id, String path, MultipartFile file);

    /**
     * 获取歌曲总数
     * @return
     */
    int getSongNums();

    /**
     * 分页获取歌曲
     * @return
     */
    Page<Song> awareSongPage();

    /**
     * 根据key进行模糊查询(前台)
     * @param key
     * @return
     */
    List<Song> awareSongByKey(String key);


    List<Song> awareAllSongBySinger(Integer singerId);
}
