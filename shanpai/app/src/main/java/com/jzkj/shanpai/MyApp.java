package com.jzkj.shanpai;

import android.util.Log;

import sp.base.BaseApplication;

public class MyApp extends BaseApplication {

    private static final String TAG = "BaseApplication";

    public static boolean isLogin = false;

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
    }


}
