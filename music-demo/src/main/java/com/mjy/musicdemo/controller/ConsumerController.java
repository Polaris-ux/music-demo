package com.mjy.musicdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mjy.musicdemo.entity.Consumer;
import com.mjy.musicdemo.entity.vo.ConsumerVo;
import com.mjy.musicdemo.service.ConsumerService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.CookieUtils;
import com.mjy.musicdemo.util.IdWorker;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @description
 * @create 2022-02-14 22:35
 */

@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("username") String username, @RequestParam("password")String password){
        if(username==null||password==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        String token = CookieUtils.getValue(request, "userInfo");
        if(token==null||"".equals(token)){
            Consumer consumer = consumerService.login(username);
            if(consumer!=null&&consumer.getPassword().equals(password)){
                String newToken = IdWorker.getId();
                CookieUtils.set(response,"userInfo",token,true);
                ConsumerVo consumerVo = new ConsumerVo();
                BeanUtils.copyProperties(consumer,consumerVo);
                String info = JSONObject.toJSONString(consumerVo);
                redisTemplate.opsForValue().set(newToken,info,CookieUtils.COOKIE_MAX_AGE);
                return ResultInfo.ok().setData("consumerInfo",consumerVo);
            }else{
                return ResultInfo.error(Consts.LOGIN_FAILED_CODE,Consts.LOGIN_FAILED);
            }
        }else{
            String s = redisTemplate.opsForValue().get(token);
            ConsumerVo consumerVo = JSONObject.parseObject(s, ConsumerVo.class);
            return ResultInfo.ok().setData("consumerInfo",consumerVo);
        }

    }

    @RequestMapping(value="/addConsumer",method= RequestMethod.POST)
    public ResultInfo addConsumer(Consumer consumer){
        if(consumer==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        if(consumer.getUsername()==null){
            return ResultInfo.error(Consts.FAILED_CODE,"用户名不能为空");
        }
        if(consumer.getPassword()==null){
            return ResultInfo.error(Consts.FAILED_CODE,"用户密码不能为空");
        }
        boolean flag=consumerService.isExistUser(consumer.getUsername());

        if(flag){
            return ResultInfo.error(Consts.FAILED_CODE,"该用户名已存在");
        }else{
            boolean isSuccess = consumerService.addConsumer(consumer);
            if(isSuccess){
                return ResultInfo.ok();
            }else{
                return ResultInfo.error(Consts.FAILED_CODE,"添加用户失败");
            }
        }

    }

    @RequestMapping(value="/delConsumer",method=RequestMethod.POST)
    public ResultInfo delConsumer(@RequestParam("id")Integer id,@RequestParam("pic")String pic){
        if(id==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = consumerService.deleteConsumerById(id,pic);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"删除用户失败");
        }
    }

    @RequestMapping(value="/batchDeleteConsumer",method=RequestMethod.POST)
    public ResultInfo batchRemoveConsumer(@RequestParam("ids") List<Integer> ids,@RequestParam("paths")List<String> paths){
        if(ids==null||paths==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = consumerService.batchRemoveConsumer(ids,paths);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"批量删除用户失败");
        }
    }

    @RequestMapping(value="/updateConsumer",method=RequestMethod.POST)
    public ResultInfo updateConsumer(Consumer consumer){
        if(consumer==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        consumer.setUpdateTime(new Date());
        boolean isSuccess = consumerService.updateConsumer(consumer);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"修改用户信息失败");
        }
    }

    @RequestMapping(value="/getConsumersByKey",method=RequestMethod.GET)
    public ResultInfo getConsumersByKey(@RequestParam(value="pageNum",defaultValue="1")int pageNum,
                                        @RequestParam(value="key",required=false)String key,
                                        @RequestParam(value="pageSize",defaultValue="7")int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Consumer> consumerPageInfo = new PageInfo<>(consumerService.getConsumersByKey(key));
        return ResultInfo.ok().setData("pageInfo",consumerPageInfo);
    }

    @RequestMapping(value="/updateAvatar",method=RequestMethod.POST)
    public ResultInfo updateAvatar(@RequestParam("id")Integer id, @RequestParam("pic")String pic,
                                   @RequestParam("file")MultipartFile file){
        if(id==null||file==null||file.isEmpty()){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        boolean isSuccess = consumerService.uploadAvatar(id,pic,file);
        if(isSuccess){
            return ResultInfo.ok();
        }else{
            return ResultInfo.error(Consts.FAILED_CODE,"修改用户头像失败");
        }
    }

    @RequestMapping(value="/getNum",method=RequestMethod.GET)
    public ResultInfo getNum(){
        int count=consumerService.getConsumersNum();
        return ResultInfo.ok().setData("consumerNum",count);
    }

    @RequestMapping(value="/getManNum",method=RequestMethod.GET)
    public ResultInfo getManNum(){
        int count=consumerService.getConsumerOfMan();
        return ResultInfo.ok().setData("consumerOfMan",count);
    }

    @RequestMapping(value="/getUserByIds",method=RequestMethod.GET)
    public ResultInfo getUserByIds(@RequestParam("ids")List<Integer> ids){
        if(ids==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }
        if(ids.size()==0){
            return ResultInfo.ok().setData("consumerList",new ArrayList<>());
        }

        List<ConsumerVo> consumerByIds = consumerService.getConsumerByIds(ids);
        return ResultInfo.ok().setData("consumerList",consumerByIds);
    }

    @RequestMapping(value="/getUserById",method=RequestMethod.GET)
    public ResultInfo getUserByIds(@RequestParam("userId")Integer userId){
        if(userId==null){
            throw new RuntimeException(Consts.PARAM_IS_NULL);
        }

        Consumer consumer= consumerService.getConsumerById(userId);
        ConsumerVo consumerVo=new ConsumerVo();
        BeanUtils.copyProperties(consumer,consumerVo);
        return ResultInfo.ok().setData("userInfo",consumerVo);
    }


}
