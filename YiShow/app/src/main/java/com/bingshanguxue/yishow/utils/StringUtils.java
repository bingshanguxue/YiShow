package com.bingshanguxue.yishow.utils;

import java.util.Random;

/**
 * Created by Administrator on 2015/6/8.
 */
public class StringUtils {
    /**
     * ��������ַ���
     * @param length �ַ�������
     * */
    public static String getRandomString(int length) { //length��ʾ�����ַ����ĳ���
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
