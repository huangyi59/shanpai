package com.jzkj.shanpai.study;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.jzkj.shanpai.Book;
import com.jzkj.shanpai.IMyAidlInterface;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.service.MyService;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StudyActity extends Activity implements ServiceConnection{

    private static final String TAG = StudyActity.class.getSimpleName();
    private IMyAidlInterface mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.btn_arithmetic, R.id.btn_data_stucture, R.id.btn_android, R.id.btn_okhtt3, R.id.btn_retrofit, R.id.btn_rxjava, R.id.btn_glide})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_arithmetic:
                skipToActivity(this, ArithmeticActivity.class);
                break;
            case R.id.btn_data_stucture:
                skipToActivity(this, DataStructeActivity.class);
                break;
            case R.id.btn_android:
                //skipToActivity(this, ProgressActivity.class);
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

    private void startActivity(){
        //隐式启动
        Intent intent = new Intent("com.test");
        intent.addCategory("com.test1");
        intent.setDataAndType(Uri.parse("http://abc"), "image/*");
        startActivity(intent);
    }

    protected void skipToActivity(Activity activity, Class<? extends Activity> toClass) {
        Intent intent = new Intent(activity, toClass);
        startActivity(intent);
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

        Book book = new Book(0x01,"测试");
        mManager = IMyAidlInterface.Stub.asInterface(service);
        try {
            mManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"服务连接成功！");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG,"服务连接失败！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除服务绑定
        unbindService(this);
    }
}
