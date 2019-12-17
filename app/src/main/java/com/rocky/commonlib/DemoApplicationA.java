package com.rocky.commonlib;

import android.app.Application;
import android.content.Context;

import com.rocky.commonlib.net.NetWork;

/**
 * @author
 * @date 2019/12/6.
 * description：
 */
public class DemoApplicationA extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        new NetWork.Builder().baseUrl("https://www.jingjing.shop/appserver/").build();
    }

    public static Context getContext() {
        return context;
    }
}
