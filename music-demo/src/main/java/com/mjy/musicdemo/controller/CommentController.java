package com.mjy.musicdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.musicdemo.entity.Comment;
import com.mjy.musicdemo.entity.vo.CommentVo;
import com.mjy.musicdemo.service.CommentService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


/**
 * @author
 * @description
 * @create 2022-02-19 17:08
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping(value="/addComment",method = RequestMethod.POST)
    public ResultInfo addComment(Comment comment){
        if(comment==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = commentService.addComment(comment);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"评论发表失败");
        }
    }

    @RequestMapping(value="/getSongListComment",method = RequestMethod.GET)
    public ResultInfo getSongListComment(@RequestParam("songListId")Integer songListId){
        if(songListId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        List<Comment> commentList = commentService.getCommentsBySongListId(songListId);
        return ResultInfo.ok().setData("comments",commentList);
    }

    @RequestMapping(value="/getSongComment",method = RequestMethod.GET)
    public ResultInfo getSongComment(@RequestParam("songId")Integer songId){
        if(songId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        List<Comment> commentList = commentService.getCommentsBySongId(songId);
        return ResultInfo.ok().setData("comments",commentList);
    }

    @RequestMapping(value="/incrUp",method = RequestMethod.POST)
    public ResultInfo incrUp(@RequestParam("id")Integer id){
        if(id==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = commentService.updateUpVal(id);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"点赞失败");
        }
    }

    @RequestMapping(value="/batchRemove",method = RequestMethod.POST)
    public ResultInfo batchRemove(@RequestParam("ids")List<Integer> ids){
        if(ids==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        if(ids.size()==0){
            return ResultInfo.ok();
        }

        boolean isSuccess = commentService.batchRemoveByIds(ids);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"批量删除评论失败");
        }
    }

    @RequestMapping(value="/removeById",method = RequestMethod.POST)
    public ResultInfo removeById(@RequestParam("id")Integer id){
        if(id==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        boolean isSuccess = commentService.removeById(id);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"删除评论失败");
        }
    }


    @RequestMapping(value="/getCommentVoPage",method = RequestMethod.GET)
    public ResultInfo getCommentVoPage(@RequestParam(value="pageNum",defaultValue = "1")Integer pageNum,
                                       @RequestParam(value="pageSize",defaultValue="10")Integer PageSize,
                                       @RequestParam("songListId")Integer songListId){
        if(songListId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        PageHelper.startPage(pageNum,PageSize);
        PageInfo<CommentVo> pageInfo = new PageInfo<>(commentService.getCommentVos(songListId));
        return ResultInfo.ok().setData("pageInfo",pageInfo);
    }



}
