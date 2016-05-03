package com.example.cheng.task;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mac on 16/4/19.
 */
public class TaskPool {
    //线程池
    private static TaskPool mInstance = null;
    private ExecutorService mThreadPool;
    private final int mThreadNum = 5;
    private ArrayList<Runnable> mTaskQuene = new ArrayList<Runnable>();

    public TaskPool(){
        mThreadPool = Executors.newFixedThreadPool(mThreadNum);
    }

    public static TaskPool getInstance(){
        if(mInstance == null){
            mInstance = new TaskPool();
        }
        return mInstance;
    }

    //添加一个任务
    public void quene(Runnable r){
        mThreadPool.execute(r);
    }


}
