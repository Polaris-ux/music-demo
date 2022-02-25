package com.mjy.musicdemo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * @description
 * @create 2022-02-01 20:31
 */
public class CookieUtils {

    // 默认缓存时间，单位/秒
    public static final int COOKIE_MAX_AGE=60*60*12;

    // 保存路径，根路径
    private static final String COOKIE_PATH="/";


    /**
     * 保存
     * @param response
     * @param key
     * @param value
     * @param ifRemember
     */
    public static void set(HttpServletResponse response,String key,String value,boolean ifRemember){
        set(response,key,value,null,COOKIE_PATH,COOKIE_MAX_AGE,true);
    }


    /**
     *  保存
     * @param response
     * @param key
     * @param value
     * @param domain 域名
     * @param path 保存的路径
     * @param maxAge 存活时间
     * @param isHttpOnly 是否能够通过js获取cookie，true:不能通过js获取cookie  false:能通过js获取cookie
     */
    public static void set(HttpServletResponse response,String key,String value,String domain,String path,int maxAge,boolean isHttpOnly){
        Cookie cookie = new Cookie(key, value);
        // domain:域名（若设置域名，则该域名及其子域名都可以访问）
        if(domain!=null&&"".equals(domain)){
            cookie.setDomain(domain);
        }
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie的值
     * @param request
     * @param key
     * @return
     */
    public static String getValue(HttpServletRequest request, String key){
        Cookie cookie = get(request, key);
        if(cookie!=null){
            return cookie.getValue();
        }

        return null;
    }


    /**
     * 查询Cookie
     * @param request
     * @param key
     * @return
     */
    public static Cookie get(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(key)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void delete(HttpServletRequest request,HttpServletResponse response,String key){
        Cookie cookie = get(request, key);
        if(cookie!= null){
            set(response,key,"",null,COOKIE_PATH,0,true);
        }
    }

}
