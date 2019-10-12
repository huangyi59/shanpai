package com.jzkj.shanpai.study;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jzkj.shanpai.Book;
import com.jzkj.shanpai.IMyAidlInterface;
import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.AboutActivity;
import com.jzkj.shanpai.study.android.ClassActivity;
import com.jzkj.shanpai.study.android.ViewActivity;
import com.jzkj.shanpai.study.android.ViewActivity2;
import com.jzkj.shanpai.study.android.ViewActivity3;
import com.jzkj.shanpai.study.android.bean.DProxyInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ThreadFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 热修复原理 主要是类替换 ，类加载涉及到PathClassLoader、DexClassLoader
 */
public class StudyActity extends Activity implements ServiceConnection {

    private static final String TAG = StudyActity.class.getSimpleName();
    private IMyAidlInterface mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        ButterKnife.bind(this);
//        Intent intent = new Intent(this,MyService.class);
//        bindService(intent,this, Context.BIND_AUTO_CREATE);
    }

    @OnClick({R.id.btn_activity, R.id.btn_arithmetic, R.id.btn_data_stucture, R.id.btn_android, R.id.btn_okhtt3, R.id.btn_retrofit, R.id.btn_rxjava, R.id.btn_glide})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_activity:
                skipToActivity(this, AboutActivity.class);
                break;
            case R.id.btn_arithmetic:
                skipToActivity(this, ArithmeticActivity.class);
                break;
            case R.id.btn_data_stucture:
                skipToActivity(this, ThreadPoolActivity.class);
                //ARouter.getInstance().build("mymodle/LoginActivity").navigation();
                break;
            case R.id.btn_android:
                Log.e("tag", Thread.currentThread().getName());
                skipToActivity(this, BeautifulActivity.class);
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

    private void startActivity() {
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

        Book book = new Book(0x01, "测试");
        mManager = IMyAidlInterface.Stub.asInterface(service);
        try {
            mManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "服务连接成功！");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "服务连接失败！");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除服务绑定
        //unbindService(this);
    }

    /**
     * 静态代理：在代码编译的时候的就知道被代理的对象，java api代理机制求被代理类必须要实现某个接口，对于静态代理方式代理类也要实现和被代理类相同的接口
     * 动态代理：在代码编译的时候不能确定被代理的对象
     */
    private <T> T create(Class<T> clz) {
        return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new DProxyInvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return super.invoke(proxy, method, args);
            }
        });
    }

    private void testReflect(String test) {
        Class clz = StudyActity.class;
        try {
            Method method = clz.getMethod("create", String.class);
            try {
                method.invoke(clz, test);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
