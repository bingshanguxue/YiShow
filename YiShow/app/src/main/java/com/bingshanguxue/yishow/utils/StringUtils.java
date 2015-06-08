package com.bingshanguxue.yishow.utils;

import java.util.Random;

/**
 * Created by Administrator on 2015/6/8.
 */
public class StringUtils {
    /**
     * 生成随机字符串
     * @param length 字符串长度
     * */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
