package com.jzkj.shanpai.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.jzkj.shanpai.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class StudyActity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_arithmetic, R.id.btn_data_stucture,R.id.btn_android, R.id.btn_okhtt3, R.id.btn_retrofit, R.id.btn_rxjava, R.id.btn_glide})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_arithmetic:
                skipToActivity(this,ArithmeticActivity.class);
                break;
            case R.id.btn_data_stucture:
                skipToActivity(this,DataStructeActivity.class);
                break;
            case R.id.btn_android:
                skipToActivity(this,AndroidActivity.class);
                break;
            case R.id.btn_okhtt3:
                break;
            case R.id.btn_retrofit:
                break;
            case R.id.btn_rxjava:
                break;
            case R.id.btn_glide:
                break;
            default:
                break;
        }
    }

    protected void skipToActivity(Activity activity,Class<? extends Activity> toClass){
        Intent intent = new Intent(activity,toClass);
        startActivity(intent);
    }



}
