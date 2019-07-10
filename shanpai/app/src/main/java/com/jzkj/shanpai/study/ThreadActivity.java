package com.jzkj.shanpai.study;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jzkj.shanpai.R;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/**
 * 创建AsyncTask WorkerRunnable（instance of CallBack）FutureTask(Runnalble)
 *
 * 类泛型一般都是通过传入参数进行赋值的
 *
 */
public class ThreadActivity extends AppCompatActivity {

    private Executor mExecutor = new SerialExecutor();
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        //exectute SerialExecutor排队 THREAD_POOL_EXECUTOR执行任务 FutureTask.run WorkRunnable-call ->doingBack->postResult
        // handler 发送并处理信息，切换到主线程 调用finish -onPostExecute
        new MyTask().execute("");
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
