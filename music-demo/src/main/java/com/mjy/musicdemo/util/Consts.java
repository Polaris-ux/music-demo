package com.mjy.musicdemo.util;

/**
 * @author
 * @description
 * @create 2022-02-01 17:06
 */
public class Consts {

    // 返回状态码
    // 成功码
    public static final String SUCCESS_CODE="50200";
    // 登录失败状态码
    public static final String LOGIN_FAILED_CODE = "60100";
    // 服务器处理错误
    public static final String FAILED_CODE = "500";
    // 上传图片错误
    public static final String UPLOAD_PICTURE_FAILED ="60101" ;
    public static final String UPLOAD_PICTURE_SUCCESS ="50201" ;
    // 保存歌曲错误
    public static final String SAVE_SONG_ERROR = "50202";
    public static final String ADD_SONG_LIST_ERROR = "50203";

    // 上传图片地址
    public static final String UPLOAD_PICTURE_ADDRESS=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
            System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator");

    public static final String UPLOAD_SONG_PICTURE_ADDRESS=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
            System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator");

    public static final String UPLOAD_SONG_LIST_PICTURE_ADDRESS=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
            System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator");

    public static final String UPLOAD_SONG_MP3_ADDRESS=System.getProperty("user.dir")+System.getProperty("file.separator")+"songStore"+
            System.getProperty("file.separator");

    public static final String UPLOAD_CONSUMER_AVATAR_ADDRESS=System.getProperty("user.dir")+System.getProperty("file.separator")+"img"+
            System.getProperty("file.separator")+"consumerPic"+System.getProperty("file.separator");

    //提示信息
    public static final String USERNAME_PASSWORD_NULL = "用户名或密码为空";
    public static final String LOGIN_FAILED = "登录失败，用户名或密码错误";
    public static final String LOGIN_SUCCESS = "登陆成功";

    public static final String ADMIN_TOKEN_KEY="admin-music-token";
    public static final String ADD_SINGER_PARAM_IS_NULL = "添加的歌手信息为空";
    public static final String ADD_SINGER_SUCCESS = "添加歌手成功";
    public static final String EDIT_SINGER_INFO_IS_NULL = "修改的用户信息为空";
    public static final String PARAM_IS_NULL = "请求参数为空";
    public static final String ADD_SONG_ERROR = "添加歌曲失败";
    public static final String DEFAULT_SINGER_PIC = "/img/singerPic/default.jpg";
    public static final String DEFAULT_SONG_PIC = "/img/songPic/boy.jpg";
    public static final String DEFAULT_SONG_LIST_PIC = "/img/songListPic/default.jpg";
    public static final String DEFAULT_CONSUMER_PIC = "/img/consumerPic/default.jpg";


    public static final String ADD_SONG_LIST_FAILED = "添加歌单失败";
}
