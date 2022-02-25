package com.mjy.musicdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author
 * @description 解决跨域问题
 * @create 2022-02-01 15:44
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 拦截所有请求
                .allowedOrigins("*") // 可跨域的域名，可以为*
                .allowCredentials(true) // 设置为true会与设置的域名建立高度信任，会将一些敏感信息暴露，如cookie等
                .allowedMethods("*") // 允许跨域的方法，跨域单独的配置
                .allowedHeaders("*"); // 允许跨域的请求头，跨域单独的配置
    }
}
