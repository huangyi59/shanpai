package com.jzkj.shanpai.study.android.bean;

import android.util.Log;

public class B extends A{

    static {
        Log.e("tag","B");
    }

    public B() {
        Log.e("tag","BB");
    }
}
