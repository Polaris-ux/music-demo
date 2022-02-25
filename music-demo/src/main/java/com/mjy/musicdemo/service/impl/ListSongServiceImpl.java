package com.mjy.musicdemo.service.impl;

import com.mjy.musicdemo.dao.ListSongMapper;
import com.mjy.musicdemo.entity.ListSong;
import com.mjy.musicdemo.service.ListSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-14 15:30
 */
@Service
public class ListSongServiceImpl implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public boolean removeBySongListId(Integer id) {
        return listSongMapper.deleteBySongListId(id)>0;
    }

    @Override
    public List<Integer> getSongsIdBySongList(Integer songListId) {
        return listSongMapper.getListSongsById(songListId);
    }

    @Override
    public boolean removeBySongId(Integer songId,Integer songListId) {
        return listSongMapper.deleteBySongId(songId,songListId)>0;
    }

    @Override
    public boolean batchRemoveBySongIds(List<Integer> songIds,Integer songListId) {
        return listSongMapper.deleteBySongIds(songIds,songListId)>0;
    }

    @Override
    public boolean addListSong(ListSong listSong) {
        return listSongMapper.insert(listSong)>0;
    }

    @Override
    public boolean updateListSong(ListSong listSong) {
        return listSongMapper.updateByPrimaryKeySelective(listSong)>0;
    }
}
