package com.mjy.musicdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.mjy.musicdemo.entity.Admin;
import com.mjy.musicdemo.service.AdminService;
import com.mjy.musicdemo.util.Consts;
import com.mjy.musicdemo.util.CookieUtils;
import com.mjy.musicdemo.util.IdWorker;
import com.mjy.musicdemo.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @description
 * @create 2022-02-01 16:47
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private JSONObject jsonObject=new JSONObject();

    /**
     * 实现登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public ResultInfo adminLogin(@RequestParam("username") String username, @RequestParam("password") String password,
                                 HttpServletRequest request, HttpServletResponse response){
        if(username==null||"".equals(username.trim())||password==null||"".equals(password.trim())){
            throw new NullPointerException(Consts.USERNAME_PASSWORD_NULL);
        }


        String token = CookieUtils.getValue(request, Consts.ADMIN_TOKEN_KEY);
        if(token!=null){
            String info = redisTemplate.opsForValue().get(token);
            if(info!=null&&"".equals(info)){
                return ResultInfo.ok(Consts.LOGIN_SUCCESS);
            }else{
                CookieUtils.delete(request,response,token);
            }
        }

        boolean isSuccess = adminService.login(username.trim(), password.trim());

        if(!isSuccess){
            return ResultInfo.error().setCode(Consts.LOGIN_FAILED_CODE).setMessage(Consts.LOGIN_FAILED);
        }

        // 产生token
        token=IdWorker.getId();
        CookieUtils.set(response,Consts.ADMIN_TOKEN_KEY,token ,false);

        redisTemplate.opsForValue().setIfAbsent(token,"1",CookieUtils.COOKIE_MAX_AGE, TimeUnit.SECONDS);

        return ResultInfo.ok(Consts.LOGIN_SUCCESS);
    }
}
