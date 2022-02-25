package com.mjy.musicdemo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.musicdemo.entity.Singer;
import com.mjy.musicdemo.service.SingerService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-07 17:13
 */
@RestController
@RequestMapping("/singer")
public class SingerController{
    @Autowired
    private SingerService singerService;

    @RequestMapping(value="/addSinger",method = RequestMethod.POST)
    public ResultInfo addSinger(Singer singer){
        if(singer==null){
            throw new RuntimeException(Consts.ADD_SINGER_PARAM_IS_NULL);
        }

        boolean addSuccess = singerService.addSinger(singer);
        if(addSuccess){
            return ResultInfo.ok(Consts.ADD_SINGER_SUCCESS);
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,null);
        }
    }

    @RequestMapping(value="/editSinger",method = RequestMethod.POST)
    public ResultInfo editSinger(Singer singer){
        if(singer==null){
            throw new RuntimeException(Consts.EDIT_SINGER_INFO_IS_NULL);
        }

        boolean isSuccess = singerService.editSingerSelective(singer);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,null);
        }
    }

    @RequestMapping(value = "/removeSinger",method = RequestMethod.GET)
    public ResultInfo removeSinger(@RequestParam("id")Integer id){
        if(id==null){
            throw new RuntimeException("参数为空");
        }

        boolean isSuccess = singerService.removeSinger(id);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,null);
        }
    }

    @RequestMapping(value="/batchRemoveSinger",method = RequestMethod.POST)
    public ResultInfo batchRemoveSinger(@RequestParam("ids") List<Integer> ids){
        if(ids==null||ids.size()==0){
            return ResultInfo.ok();
        }

        boolean isSuccess = singerService.bathRemoveSinger(ids);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,null);
        }
    }

    @RequestMapping(value="/getSingerById",method = RequestMethod.GET)
    public ResultInfo getSingerById(@RequestParam("id")Integer id){
        if(id==null){
            return ResultInfo.error(Consts.FAILED_CODE,"用户id为空");
        }

        Singer singerById = singerService.getSingerById(id);
        if(singerById!=null){
            return ResultInfo.ok().setData("singerInfo",singerById);
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"没有查询到该歌手");
        }
    }

    @RequestMapping(value="/getAllSingerByType",method = RequestMethod.GET)
    public ResultInfo getAllSingerByType(@RequestParam(value = "type",required = false)Integer type){
        List<Singer> allSingers = singerService.getAllSingersByType(type);
        return ResultInfo.ok().setData("allSingers",allSingers);
    }

    @RequestMapping(value="/getSingersByKey",method = RequestMethod.GET)
    public ResultInfo getSingersByKey(@RequestParam(value="pageNum")int pageNum,@RequestParam(value="pageSize") int pageSize,
                                      @RequestParam(value="key",required = false)String key){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Singer> pageInfo = new PageInfo<>(singerService.getSingersByKey(key));
        return ResultInfo.ok().setData("pageInfo",pageInfo);
    }

    @RequestMapping(value = "/updateSingerPic",method = RequestMethod.POST)
    public ResultInfo updateSingerPic(@RequestBody MultipartFile file,@RequestParam("id") Integer id){
        if(file.isEmpty()||id==null){
            return ResultInfo.error(Consts.UPLOAD_PICTURE_FAILED,Consts.PARAM_IS_NULL);
        }

        // 判断上传路径是否存在
        File file1 = new File(Consts.UPLOAD_PICTURE_ADDRESS);
        if(!file1.exists()){
            file1.mkdir();
        }

        // 加上时间戳防止重复
        String filename=System.currentTimeMillis()+"-"+file.getOriginalFilename();
        // 实际的文件地址
        File file2 = new File(Consts.UPLOAD_PICTURE_ADDRESS+ filename);

        // 存储到数据库的相对位置
        String pic="/img/singerPic/"+filename;

        // 上传图片
        String msg="上传图片失败";
        try {
            file.transferTo(file2);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(pic);
            boolean isSuccess = singerService.editSingerSelective(singer);
            if(isSuccess){
                return ResultInfo.ok("上传图片成功").setCode(Consts.UPLOAD_PICTURE_SUCCESS);
            }
        } catch (IOException e) {
            e.printStackTrace();
            msg=e.getMessage();
        }

        return ResultInfo.error(Consts.UPLOAD_PICTURE_FAILED,msg);
    }

    @RequestMapping(value="/getNum",method=RequestMethod.GET)
    public ResultInfo getNum(){
        int count=singerService.getSingerNums();
        return ResultInfo.ok().setData("singerNum",count);
    }
}
