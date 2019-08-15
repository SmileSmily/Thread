package com.jian8.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource1{
    //默认开启，进行生产+消费
    //volatile线程间互相通信
    volatile  private boolean Flag=true;
    //保证原子性，代表生产一个，消费一个
    private AtomicInteger atomicInteger=new AtomicInteger();

    BlockingQueue<String> blockingQueue=null;
    public MyResource1(BlockingQueue<String> blockingQueue){
        this.blockingQueue=blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    //生产者生产产品，放到阻塞队列
    public void  myProd()throws  Exception{
        String data=null;
        boolean retValue;
        while (Flag){
            data=atomicInteger.incrementAndGet()+"";
            //往阻塞队列中插入数据
           retValue= blockingQueue.offer(data,2L, TimeUnit.SECONDS);
            if(retValue){
                System.out.println(Thread.currentThread().getName()+"/t 插入队列"+data+"成功！");
            }else{
                System.out.println(Thread.currentThread().getName()+"/t 插入队列"+data+"失败！");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"/t 大老板叫停，表示flag=false，生产动作结束");
    }

    //消费者从阻塞队列消费产品
    public void myConsumer()throws  Exception{
        String result=null;
        while(Flag){
            blockingQueue.poll(2L,TimeUnit.SECONDS);
            if(null==result||result.equalsIgnoreCase("") ){
                Flag=false;
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到蛋糕，消费退出");
                System.out.println();
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"/t 消费队列"+result+"成功");
        }
    }

    //叫停方法
    public  void stop() throws Exception{
        this.Flag=false;
    }

}

/**
 * @Author:王婷婷
 * @Description:   生产消费阻塞队列---高并发版
 * @Date：Created in ${Time} 2019\8\11 0011
 */



public class ProductConsumerDemo {
    public static void main(String[] args) throws Exception {
        MyResource myResource=new MyResource(new LinkedBlockingQueue<>(10));

        //生产线程启动
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"/t 生产线程启动");

            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }

        },"生产者").start();

      //消费者线程启动
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"/t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"消费者").start();

        //暂停一会线程
        TimeUnit.SECONDS.sleep(5);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒时间到，大老板叫停");
        myResource.stop();

        //查看运行的CPU
        //System.out.println(Runtime.getRuntime().availableProcessors());
    }

}
