package com.jzkj.shanpai.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jzkj.shanpai.R;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
    }

    private void load1(String url, ImageView imageView){
        /**
         * 1.给当前上下文对象添加隐藏的SupportRequestManagerFramment对象，检测其生命周期
         */
        Glide.with(this).load(url)
                .override(100,100)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                .into(imageView);
    }

}
