package com.mjy.musicdemo.service.impl;

import com.mjy.musicdemo.dao.AdminMapper;
import com.mjy.musicdemo.entity.Admin;
import com.mjy.musicdemo.service.AdminService;
import com.mjy.musicdemo.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description
 * @create 2022-02-01 16:39
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     * @param name
     * @param password
     * @return
     */
    @Override
    public boolean login(String name, String password) {
        Admin admin=adminMapper.getAdminByName(name);

        if(admin!=null){
            String encodedPassword= Md5Utils.md5(password);
            if(admin.getPassword().equals(encodedPassword)){
                return true;
            }

        }

        return false;
    }
}
