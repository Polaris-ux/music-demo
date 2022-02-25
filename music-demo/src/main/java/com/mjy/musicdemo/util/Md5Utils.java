package com.mjy.musicdemo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author
 * @description
 * @create 2022-02-01 16:41
 */
public class Md5Utils {


    public static void main(String[] args) {
        System.out.println(md5("123456"));

    }
    /**
     * md5加密
     * @return
     */
    public static String md5(String source){
        // 对传入参数进行校验
        if( source ==null || source.length() == 0 ){
            //字符串为空抛出异常
            throw new RuntimeException("密码不能为空");
        }

        try {
            // 获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 获取明文对应的字节数组
            byte[] input = source.getBytes();

            // 执行加密
            byte[] output = messageDigest.digest(input);

            // 创建BigInteger对象
            int signum=1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 按照16进制将bigInteger的值传唤为字符串

            // 指定进制
            int radix=16;
            String encoded = bigInteger.toString(radix);

            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
