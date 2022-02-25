package com.mjy.musicdemo.service;

import com.mjy.musicdemo.entity.Admin;

/**
 * @author
 * @description
 * @create 2022-02-01 16:37
 */
public interface AdminService {
    boolean login(String name,String password);
}
