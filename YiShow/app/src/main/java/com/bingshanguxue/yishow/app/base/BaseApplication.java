package com.bingshanguxue.yishow.app.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bingshanguxue.yishow.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2015/6/9.
 */
public class BaseApplication extends Application {

    static Context _context;

    @Override
    public void onCreate() {
        super.onCreate();
        _context = getApplicationContext();
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }
}
