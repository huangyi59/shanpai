package com.jzkj.shanpai.study;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jzkj.shanpai.R;

public class RemoteViewActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view);
        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
