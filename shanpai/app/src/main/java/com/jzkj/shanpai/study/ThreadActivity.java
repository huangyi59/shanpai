package com.jzkj.shanpai.study;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建AsyncTask WorkerRunnable（instance of CallBack）FutureTask(Runnalble)
 * <p>
 * 类泛型一般都是通过传入参数进行赋值的
 */
public class ThreadActivity extends AppCompatActivity {

    private Executor mExecutor = new SerialExecutor();
    ThreadPoolExecutor mThreadPoolExecutor;

    private int mCorePoolSize = 10;
    private int mMaxPoolSize = 100;
    private BlockingDeque<Runnable> mQueue = new LinkedBlockingDeque();
    private ThreadFactory mThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask#" + mCount.getAndIncrement());
        }
    };

    private IntentService mIntentService = new IntentService("") {
        @Override
        protected void onHandleIntent(Intent intent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        // 1.6之前串行 1.6之后并行 3.0串行执行
        // super.() 创建Handler对象 创建WorkRunnable 创建FutureTask（WorkRunnable runnable)对象
        // execute 执行doInbackground
        // doInbackground跑在线程池里面 -》onPostExecute执行了一次线程切换
        new MyTask().execute("");

        //
        mThreadPoolExecutor = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, mQueue);
        mThreadPoolExecutor.allowsCoreThreadTimeOut();
    }

    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                //THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }

    // 1.6之前并行 1.6-3.0串行
    static class MyTask extends AsyncTask<String, Integer, Object> {

        public MyTask(){
            super();
        }

        //主线程中执行
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //线程池中执行
        @Override
        protected Object doInBackground(String[] objects) {
            try {
                Thread.sleep(2000);
                publishProgress(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        //主线程中执行
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        //主线程中执行
        @Override
        protected void onProgressUpdate(Integer[] values) {
            super.onProgressUpdate(values);
        }

        //主线程中执行
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}
