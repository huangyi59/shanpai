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

/**
 * UI控件不是线程安全的
 * 如果加锁机制，逻辑变得复杂，控件访问效率降低
 * 所以采用高效的单线程进行访问
 * <p>
 *  当某些数据以线程为作用域，不同的数据具有不同的数据副本的时候
 * ThreadLocal 通过操作本地的table数组（Entry extends WeakReference<ThreadLocal<T>>）new Entry(ThreadLoack<Looper> key,value = Looper )
 * get - ThreadLocalMap
 * 根据指定的线程来存储数据
 * <p>
 * MessageQueue是个单列表的结构 enqueueMessage实现单链表的插入操作 next方法实现无限循环读取，有消息就先返回该消息然后处理，移除单链表
 *
 * Looper.prepare方法 创建Looper对象存入ThreadLocal 并在构造函数中初始化MessageQueue
 * <p>
 * Handler sendMessage MessageQueue -enqueueMessage将消息存入loop调用MessageQueue的next方法 msg.target.dispatchMessage
 * Handler的diapatchMessage方法先判断msg.callback（其实是一个runnable由post方法传入）(null else !=null handleCallback(msg))
 * 在判断mCallBack 通过Handler带参的方法创建
 *
 *
 */
public class MessageActivity extends AppCompatActivity {

    //dispatchMessgae后获取分发消息判断 msg.callback mCallBack
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    private HandlerThread mHandlerThread = new HandlerThread("mHandlerThread");

    private Handler handler2;

    //ThreadLocal线程内部的存储类，在指定的线程中存储数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        final Test test = new Test();
        Thread thread = new Thread(test);
        thread.start();

        //方法一实现子线程向主线程发消息
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                test.mHandler.sendEmptyMessage(1);
            }
        }, 1000);
        Message message = Message.obtain();
        mHandler.sendMessage(message);
        mHandler.sendEmptyMessage(1);

        //方法二实现先子线程向主线程发送消息
        mHandlerThread.start();
        handler2 = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e("TAG", "子线程收到消息了：" + msg.what + "-------");
            }
        };
        handler2.post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private class Test implements Runnable {

        public Handler mHandler;

        @Override
        public void run() {
            Looper.prepare();
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    Log.e("TAG", msg.what + "-------");
                }
            };
            Looper.loop();
            Looper.myLooper().quit();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Looper.myLooper().quitSafely();
            }
        }
    }


}
