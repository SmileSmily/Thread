package com.jian8.juc.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author:王婷婷
 * @Description:不存储数据，放一个，拿一个-SynchronousQueue
 * @Date：Created in ${Time} 2019\8\10 0010
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue blockingQueue=new SynchronousQueue<>();
        new Thread(() ->{
            try{
                System.out.println(Thread.currentThread().getName()+"/t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"/t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"/t put 3");
                blockingQueue.put("3");

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(() ->{

               try{
                   TimeUnit.SECONDS.sleep(5);
                   System.out.println(Thread.currentThread().getName()+"/t"+blockingQueue.take());
               }catch (InterruptedException e){e.printStackTrace();}
            try{
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"/t"+blockingQueue.take());
            }catch (InterruptedException e){e.printStackTrace();}
            try{
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"/t"+blockingQueue.take());
            }catch (InterruptedException e){e.printStackTrace();}

        },"BBB").start();
    }
}
