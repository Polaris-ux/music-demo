package com.mjy.musicdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author
 * @description
 * @create 2022-02-09 16:03
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    public static void main(String[] args) {
        System.out.println("file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 歌手图片静态地址映射
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                        System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
        );

        // 歌曲图片静态地址映射
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                                System.getProperty("file.separator") + "songPic" + System.getProperty("file.separator")
                );
        // 歌单图片静态资源映射
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                                System.getProperty("file.separator") + "songListPic" + System.getProperty("file.separator")
                );

        // 歌曲静态资源映射
        registry.addResourceHandler("/songStore/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "songStore" +
                                System.getProperty("file.separator")
                );

        // 用户头像
        registry.addResourceHandler("/img/consumerPic/**")
                .addResourceLocations(
                        "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" +
                                System.getProperty("file.separator")+"consumerPic"+System.getProperty("file.separator")
                );
    }
}
