package com.mjy.musicdemo.service.impl;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.dao.CommentMapper;
import com.mjy.musicdemo.entity.Comment;
import com.mjy.musicdemo.entity.vo.CommentVo;
import com.mjy.musicdemo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-19 17:05
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.insert(comment)>0;
    }

    @Override
    public List<Comment> getCommentsBySongListId(Integer songListId) {
        return commentMapper.selectBySongListId(songListId);
    }

    @Override
    public List<Comment> getCommentsBySongId(Integer songId) {
        return commentMapper.selectBySongId(songId);
    }

    @Override
    public boolean updateUpVal(Integer id) {
        return commentMapper.updateUp(id)>0;
    }

    @Override
    public Page<CommentVo> getCommentVos(Integer songListId) {
        return commentMapper.selectCommentVoBySongList(songListId);
    }

    @Override
    public boolean batchRemoveByIds(List<Integer> ids) {
        return commentMapper.batchDeleteCommentById(ids)>0;
    }

    @Override
    public boolean removeById(Integer id) {
        return commentMapper.deleteByPrimaryKey(id)>0;
    }
}
