package com.jzkj.shanpai.study.android.bean;

import android.util.Log;

public class RealObject implements Interface {

    @Override
    public void dosomething() {
        Log.e("tag","dosomething");
    }

    @Override
    public void somethingElse() {
        Log.e("tag","dosomethingelse");
    }

}
