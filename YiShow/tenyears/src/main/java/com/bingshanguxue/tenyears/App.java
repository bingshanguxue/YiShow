package com.bingshanguxue.tenyears;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by drakeet on 6/21/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LitePalApplication.initialize(this);

        SQLiteDatabase db = Connector.getDatabase();//初始化数据库

    }
}
