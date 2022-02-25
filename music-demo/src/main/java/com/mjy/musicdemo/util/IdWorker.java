package com.mjy.musicdemo.util;

import java.util.UUID;

/**
 * @author
 * @description 生成token
 * @create 2022-02-01 20:55
 */
public class IdWorker {
    public static void main(String[] args) {
        System.out.println(getId());
    }
    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
