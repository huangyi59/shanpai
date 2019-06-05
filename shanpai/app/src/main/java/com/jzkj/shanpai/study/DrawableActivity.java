package com.jzkj.shanpai.study;

import android.graphics.Color;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.widget.CustomeDrawable;

/**
 * Drawable表示的是一种可以在canvas上进行绘制的抽象概念
 * Drawable一般是通过XML来定义的 Drawable的内部宽高 getIntrinsicWidth getIntrinsicHeight
 * bitmap shapedrawale绘制背景
 */
public class DrawableActivity extends AppCompatActivity {

    private ImageView  mIvBg;
    private TextView mTvText;
    private TextView mTvScale;
    private TextView mTvCusDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        mIvBg = findViewById(R.id.iv_bg);
        mTvText = findViewById(R.id.text);
        mTvScale = findViewById(R.id.tv_scale);
        mTvCusDrawable = findViewById(R.id.tv_drawable);
        mIvBg.setImageLevel(8);
        mIvBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvBg.setImageLevel(13);
            }
        });

        TransitionDrawable drawable = (TransitionDrawable) mTvText.getBackground();
        drawable.startTransition(1000);

        ScaleDrawable scaleDrawable = (ScaleDrawable) mTvScale.getBackground();
        scaleDrawable.setLevel(1);

        CustomeDrawable customeDrawable = new CustomeDrawable(Color.RED);
        mTvCusDrawable.setBackground(customeDrawable);
    }

}
