package com.jzkj.shanpai.study;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jzkj.shanpai.R;

import java.lang.reflect.Method;

import sp.base.utils.LogUtil;

public class HotFixActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);
        printClassLoader();
    }

    private void printClassLoader(){
        ClassLoader classLoader = getClassLoader();
        if(classLoader != null){
            Log.e("tag",classLoader.toString());
            if(classLoader.getParent() != null){
                Log.e("tag",classLoader.getParent().toString());
            }
        }
    }

    private void initGetResource(String apkPath, Context context){
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssertPath",String.class);
            method.invoke(assetManager,apkPath);
            Resources resources = new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
