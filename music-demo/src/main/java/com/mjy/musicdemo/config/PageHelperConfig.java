package com.mjy.musicdemo.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author
 * @description
 * @create 2022-02-10 12:00
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper=new PageHelper();
        Properties props = new Properties();
        props.setProperty("dialect","mysql");
        props.setProperty("offsetAsPageNum","true");
        props.setProperty("rowBoundsWithCount","true");
        pageHelper.setProperties(props);
        return pageHelper;
    }

    @Bean
    public PageInterceptor pageInterceptor(){
        return new PageInterceptor();
    }
}
