package com.github.vaseghifard.weatherapplication.utils;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MyApplication extends Application {

    public static MyApplication appInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        Hawk.init(this).build();


    }


}
