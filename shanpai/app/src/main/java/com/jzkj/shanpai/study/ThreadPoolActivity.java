package com.jzkj.shanpai.study;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.A;
import com.jzkj.shanpai.study.android.bean.B;
import com.jzkj.shanpai.study.android.bean.Car;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程池的好处
 * 1.重用线程池中的线程，避免线程因多次创建和销毁所带来的性能开销
 * 2.能有效控制线程池的最大并发数，避免大量的线程之间因互相抢占系统资源而导致的阻塞现象
 * 3.能过对线程进行简单的管理，提供及时执行及指定间隔循环执行等功能
 */
public class ThreadPoolActivity extends AppCompatActivity {

    private static final String TAG = ThreadPoolActivity.class.getSimpleName();

    private BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>();
    private ThreadPoolExecutor mExecutor;
    private int mCount = 200000;
    /**
     * 如果多个任务同时访问某个域，那么这个域就应该是volatile的 对域中的值做赋值和返回操作通常是原子的
     * long和double的非原子性 jvm允许将对64位的读写操作分离成两个32位的读写操作
     */
    private volatile int mVolatile;
    private volatile boolean mIsFinish;

    private List<String> mList = new ArrayList<>();
    private ReentrantLock mLock = new ReentrantLock();

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    Log.e(TAG, t.getName() + "--" + e.getMessage());
                }
            });
            return thread;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        //initThreadPool();
        //initList();
        //testThred1();
        //testThread2();
        //testThread3();
        Log.e(TAG, "result:" + mList.size());
        //testThread4();
        //testThread6();

        A b = new B();
        b = new B();
        B b1 = new B();

    }

    private void initThreadPool() {
        mExecutor = new ThreadPoolExecutor(10, 12, 1, TimeUnit.SECONDS, workQueue, sThreadFactory);

        /**
         * 生产商品，唤醒消费者线程，等待消费
         * 等待生产商品，消费商品，唤醒生产者线程
         */
        Car car = new Car();
        mExecutor.execute(new WorkOn(car));
        mExecutor.execute(new WorkOff(car));

        try {
            TimeUnit.SECONDS.sleep(5);
            mExecutor.shutdownNow();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initList() {
        for (int i = 0; i < 10000; i++) {
            mList.add(i + "--");
        }
    }

    private void testThred1() {
        for (int j = 0; j < 20; j++) {
            new Thread(new WorkRunnable()).start();
        }

        Log.e(TAG, "result:" + mCount);
    }

    //异常不能夸线程传播 获取带有返回值的任务
    private void testThread2() {
        Future<String> future = mExecutor.submit(new Task());
        String result = null;
        try {
            Thread.sleep(100);
            TimeUnit.MICROSECONDS.sleep(100);
            result = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "result:" + result);
    }

    private void testThread3() {
        MyTask1 myTask1 = new MyTask1("mytask1");
        myTask1.start();


        MyTask2 myTask2 = new MyTask2("mytask2", myTask1);
        myTask2.start();
        myTask1.interrupt();
    }

    /**
     * 多个工作线程，访问主线程中的同一个资源 检查锁是否可用，获取锁，使用锁，释放锁
     * 所有synchronized方法共享一个锁
     * synchronized static 类锁，防止多个任务访问静态方法中的静态成员变量
     */
    private void testThread4() {
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    remove0();
                }
            }).start();
        }
    }

    private void testThread5() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, 5000);
    }

    /**
     * 线程的几种状态 创建 就绪 阻塞 死亡
     * 调用wait 和 sleep方法可以让线程进入阻塞状态 interceput方法用来中断一个正在运行的线程或则阻塞的线程
     * 你可以中断一个调用了sleep方法而进入的阻塞状态 但你无法中断一个尝试获取锁和IO状态的任务
     * 调用sleep的时候并没有释放锁，调用wait的时候释放了锁，调用wait,notify,notifyAll,都必须获取对象的锁才能调用
     */
    private void testThread6() {
        Thread thread = new Thread(new MyTask1(""));
        thread.start();

        while (true) {
            Log.e(TAG, thread.isInterrupted()+"正在阻塞中...");
        }
    }

    private void remove0() {
        mLock.lock();
        try {
            for (int i = 0; i < 500; i++) {
                mList.remove(0);
            }
        } finally {
            Log.e(TAG, "result:" + mList.size());
            mLock.unlock();
        }
    }

    private synchronized void remove() {
        for (int i = 0; i < 500; i++) {
            mList.remove(0);
        }

        Log.e(TAG, "result:" + mList.size());
    }

    //多线程对客户端是无序的 对服务端又好像是有序的
    private class WorkRunnable implements Runnable {
        @Override
        public void run() {
            decrement();
        }
    }

    private synchronized void decrement() {
        // for循环中的数据并不是原子的
        for (int i = 0; i < 10000; i++) {
            mCount--;
        }
    }

    static class Task implements Callable<String> {
        private static int mCount = 0;
        private final int mResult = mCount++;

        @Override
        public String call() throws Exception {
            return mResult + "";
        }
    }

    static class MyTask1 extends Thread {

        private String name;

        public MyTask1(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public synchronized void run() {
            super.run();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, name + "has InterruptedException");
                return;
            }

            Log.e(TAG, name + "MyThread has awake");
        }

    }

    static class MyTask2 extends Thread {
        private String name;
        private MyTask1 mMyTask1;

        public MyTask2(String name, MyTask1 mMyTask1) {
            super(name);
            this.name = name;
            this.mMyTask1 = mMyTask1;
        }

        @Override
        public void run() {
            super.run();
            try {
                mMyTask1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e(TAG, name + " has completed");
        }
    }

    class MyTask3 implements Runnable {

        @Override
        public void run() {
            while (!mIsFinish)
                increment();

            Log.e(TAG, Thread.currentThread().getName() + " has finish");
        }

        public synchronized void increment() {
            for (int i = 0; i < 20000; i++) {
                mCount++;
            }
        }

    }

    class WorkOn implements Runnable{

        private Car car;

        public WorkOn(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            while(!Thread.interrupted()){
                Log.e(TAG,"开始抛光，....");
                try {
                    TimeUnit.MICROSECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                car.waxed();
                car.waittingBuffed();
            }

        }

    }

    class WorkOff implements Runnable{

        private Car car;

        public WorkOff(Car car) {
            this.car = car;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()){
                car.waittingWaxed();
                try {
                    TimeUnit.MICROSECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e(TAG,"开始打蜡，....");
                car.buffed();

            }
        }

    }

}
