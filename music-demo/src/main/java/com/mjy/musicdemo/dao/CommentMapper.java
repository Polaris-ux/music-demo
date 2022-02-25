package com.mjy.musicdemo.dao;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Comment;
import com.mjy.musicdemo.entity.vo.CommentVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selectBySongListId(Integer songListId);

    List<Comment> selectBySongId(Integer songId);


    int updateUp(Integer id);

    Page<CommentVo> selectCommentVoBySongList(Integer songListId);

    int batchDeleteCommentById(List<Integer> ids);
}