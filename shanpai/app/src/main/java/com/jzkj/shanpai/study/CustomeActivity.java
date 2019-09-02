package com.jzkj.shanpai.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.widget.CustomeView;

public class CustomeActivity extends AppCompatActivity {

    private CustomeView mCustomeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custome);
        mCustomeView = findViewById(R.id.customview);
        mCustomeView.startAnimator();
    }
}
