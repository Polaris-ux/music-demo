package com.mjy.musicdemo.service;

import com.github.pagehelper.Page;
import com.mjy.musicdemo.entity.Comment;
import com.mjy.musicdemo.entity.vo.CommentVo;

import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-19 17:02
 */
public interface CommentService {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    boolean addComment(Comment comment);

    /**
     * 获取歌单评论
     * @param songListId
     * @return
     */
    List<Comment> getCommentsBySongListId(Integer songListId);

    /**
     * 获取歌曲评论
     * @param songId
     * @return
     */
    List<Comment> getCommentsBySongId(Integer songId);

    /**
     * 点赞数
     * @param id
     * @return
     */
    boolean updateUpVal(Integer id);

    Page<CommentVo> getCommentVos(Integer songListId);

    boolean batchRemoveByIds(List<Integer> ids);

    boolean removeById(Integer id);
}
