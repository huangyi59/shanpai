package com.jzkj.shanpai.study;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jzkj.shanpai.R;

import java.lang.ref.WeakReference;

/**
 * Handler机制
 * Looper的创建和获取
 * LocalThread泛型类（类型擦除，类型补偿）
 * LocalThreadMap LocalThreadMap.Entry key.hashCode key ThreadLocal value Looper
 *
 */
public class MessageActivity extends AppCompatActivity {

    private static final String TAG = MessageActivity.class.getName();

    //1个Handler对应一个Looper 1个Looper对应一个ThreadLocal
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,msg.what+"-----");
        }
    };

    private Handler hander2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG,msg.what+"-----");
        }
    };

    private Handler handler3 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG,msg.what+"-----");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        test1();
    }

    private void test1() {
        Looper.prepare();
        Looper.myLooper();
        // Looper.prepare(); Looper.prepareMainLooper(); One Looper may be created per Thread
        // send系列方法 将Message放入消息队列中 Looer.looper -> Message.next() msg.target.dispatchMessage(msg);
        // 一个Message只能被发送一次 每个Message绑定一个Handler enqueueMessage将Handler与Message进行绑定
        // post系列方法，构建一个带r的的Message对象
        // if( msg.callBack!=null) handlerCallBack(msg.callBack.run()) else if(mCallBack!=null){ mCallBack.handleMessage(msg)}
        Message message = Message.obtain();
        message.what = 0;
        handler1.sendMessage(message);

        Message message1 = Message.obtain();
        message1.what = 1;
        hander2.sendMessage(message1);

        Message message2 = Message.obtain();
        message2.what = 2;
        handler3.sendMessage(message2);

        //自动创建Message对象了
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,3+"-----");
            }
        });
    }

}
