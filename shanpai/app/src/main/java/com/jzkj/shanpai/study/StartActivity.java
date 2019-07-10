package com.jzkj.shanpai.study;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.receiver.MyReceiver;
import com.jzkj.shanpai.study.android.service.MyService;

import java.util.LinkedList;
import java.util.Queue;

/**
 * startActivity -startActivityForResult -Instrumentation(execStartActivity checkStartActivityResult)
 * -ActivityManagerNative.getDefault.startActivity(ActivityManagerService Binder) 由AMS中的startActivity方法转移到
 * ActivityStackSupervisor - ActivityStack 经过一系列的方法调用最终调用ActivityStackSupervisor中realStartActivityLocked
 * -调用ActivityThrad(app.thread ApplicationThread extends ApplicationThreadNative implements IApplicationThread)
 * app.thread.scheduleLaunchActivity 发送一个启动acitvity的消息交由Handler处理
 * -performLaunchActivity 完成两件事 获取待启动的Activity的组件信息 通过Instrumentation的newActivity方法使用类加载器创建Activity对象
 * 创建创建ContextImpl对象并通过Activity的Attach方法来完成一些重要数据的初始化
 *
 * startService -ContextWrapper -AMS -startServiceCommon -ActiviceService -startServicedLocked -startServiceInnerLocked
 * bringUpServiceLocked 调用realStartServiceLocked -scheduleCreateService（发送一个启动Service的消息） -onCreate -sendServiceArgsLocked
 * ActivityThread的handleCreateService方法来完成Service的最终启动 通过handleServiceArgs方法调用onStartCommand
 */
public class StartActivity extends Activity {

    private Button mBtnTest;
    private MyReceiver mMyReceiver;
    private Queue<String> mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mBtnTest = findViewById(R.id.btn_test);
        registerReceiver();
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("test");
                sendBroadcast(intent);
            }
        });
        mMyReceiver = new MyReceiver();
        mQueue = new LinkedList<>();
    }

    private void startService(){
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("test");
        registerReceiver(mMyReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyReceiver);
    }
}
