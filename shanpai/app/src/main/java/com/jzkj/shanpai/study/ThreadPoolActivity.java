package com.jzkj.shanpai.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池的好处
 * 1.重用线程池中的线程，避免线程因多次创建和销毁所带来的性能开销
 * 2.能有效控制线程池的最大并发数，避免大量的线程之间因互相抢占系统资源而导致的阻塞现象
 * 3.能过对线程进行简单的管理，提供及时执行及指定间隔循环执行等功能
 */
public class ThreadPoolActivity extends AppCompatActivity {

    private BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>();

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,12,1, TimeUnit.SECONDS,workQueue,sThreadFactory);
    }

}
