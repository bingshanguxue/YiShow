package com.bingshanguxue.yishow.app.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.bingshanguxue.yishow.app.base.BaseApplication;

/**
 * Created by Administrator on 2015/6/17.
 */
public class SharedPreferencesManager {

    private static String PREF_NAME = "creativelocker.pref";


    public static SharedPreferences getPreferences() {
        SharedPreferences pre = BaseApplication.context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    public static SharedPreferences getPreferences(String prefName) {
        return BaseApplication.context().getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS);
    }

}
