package com.bingshanguxue.yishow.wxapi;

/**
 * Created by Administrator on 2014/11/15.
 */
public interface WXConstants {
    //应用在微信开放平台官方网站上
    public static final String APP_ID = "wx1dbac2f50c918d7d";
    public static final String APP_SECRET = "25f4df09a0790cabc02ae96fd32af616";
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String CHECK_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/auth";
    public static final String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
    public static final String AUTH_SUCCESS = "weixin.auth.success";
    public static final String AUTH_FAIL = "weixin.auth.fail";
    public static final String AUTH_START = "weixin.auth.start";
    public static final String BIND_TO_XEIXIN = "bind.to.weixin";
    public static final String LOGIN_SUCCESS = "login_success";
}
