package com.mjy.musicdemo.util;

import java.io.File;

/**
 * @author
 * @description
 * @create 2022-02-12 22:44
 */
public class FileUtil {

    public static void main(String[] args) {
        // test
        String path = Consts.UPLOAD_SONG_MP3_ADDRESS+"5f7e842b94104d20a84cf8d719711388-逃跑计划 - 再飞行.mp3";
        System.out.println(removeFile(path));
    }

    public static boolean removeFile(String path){
        if(path==null){
            return false;
        }
        File file = new File(path);
        if(file.exists()&&file.isFile()){
            return file.delete();
        }
        return false;
    }
}
