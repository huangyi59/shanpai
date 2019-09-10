package com.jzkj.shanpai.study.android.manager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author huangyi
 * @email
 *
 */
public class ThreadPoolManager {

    public static Executor mExecutor;

    //创建线程池的几种方式
    private ThreadPoolManager(){
        // 只有一个核心线程
        mExecutor = Executors.newSingleThreadExecutor();

        // 只有指定大小的核心线程
        mExecutor = Executors.newFixedThreadPool(3);

        // 只有非核心线程
        mExecutor = Executors.newCachedThreadPool();
    }

    private static final class SingleHolder{
        private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
    }

    public static ThreadPoolManager getInstance(){
        return SingleHolder.INSTANCE;
    }

    public void execute(Runnable runnable){
        if(runnable == null)
            throw new NullPointerException("runnable not be null");

        if(mExecutor == null)
            return;

        mExecutor.execute(runnable);
    }

}
