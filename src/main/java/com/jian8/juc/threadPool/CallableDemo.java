package com.jian8.juc.threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author:王婷婷
 * @Description:  Callale接口
 * @Date：Created in ${Time} 2019\8\11 0011
 */

/**
 * Callale接口
 *
 *
 */
class MyThread implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("*******************come in Callable");
        return 1024;
    }
}
public class CallableDemo   {

    public static void main(String[] args) {
        //两个线程，一个main线程，一个AA FurtureTask线程；
        //
        //FutureTask(Callable<V>callable);
        FutureTask<Integer> futureTask=new FutureTask<Integer>(new MyThread());
       Thread t1=new Thread(futureTask,"AA");
        t1.start();
       int result01=100;
        int result02=0;
        try {
            //如无必要，可以放到最后,因为如果前面的线程没有计算完成，会造成阻塞
            result02=futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("************result："+(result01+result02));

    }
}
