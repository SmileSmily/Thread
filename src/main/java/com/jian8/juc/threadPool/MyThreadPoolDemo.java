package com.jian8.juc.threadPool;

import java.lang.reflect.Array;
import java.util.concurrent.*;

/**
 * @Author:王婷婷
 * @Description: 线程池的实现获取线程
 * @Date：Created in ${Time} 2019\8\11 0011
 */
public class MyThreadPoolDemo {
    //Array  Arrays
    //Executor  Executors

    /**
     *
     */
    public static void main(String[] args) {
         //调用自带的创建线程池
        //   threadPoolInit();
        ExecutorService threadPool=new ThreadPoolExecutor(
                2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        //模拟10个用户办理业务，每个用户就是一个来自外部的请求线程；
        try{
            //此时超过了5+3=8,然后就会抛异常RejectedExecutionException
            for (int i=1;i<=10;i++){
                threadPool.execute(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    /**
     * JDK版的自动创建线程池--newSingleThreadExecutor、newFixedThreadPool、newCachedThreadPool
     * 不用它，有弊端，造成OOM
     */
    private static void threadPoolInit() {
        //一池一个线程
        // ExecutorService threadPool2= Executors.newSingleThreadExecutor();

        //一池5个线程
        ExecutorService threadPool= Executors.newFixedThreadPool(5);

        //一池N个处理线程
        // ExecutorService threadPool= Executors.newCachedThreadPool();


        //模拟10个用户办理业务，每个用户就是一个来自外部的请求线程；
        try{
            for (int i=1;i<=10;i++){
                threadPool.execute(() ->{
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }


}
