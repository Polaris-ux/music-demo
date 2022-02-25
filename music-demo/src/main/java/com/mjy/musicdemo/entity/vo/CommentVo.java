package com.mjy.musicdemo.entity.vo;

import java.io.Serializable;

/**
 * @author
 * @description
 * @create 2022-02-20 19:14
 */
public class CommentVo implements Serializable {
    private Integer id;
    private String content;
    private String username;
    private Integer sex;
    private Integer up;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {

        this.username = username == null ? null : username.trim();
    }
    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }
}
