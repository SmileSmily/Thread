package com.jian8.juc.lock;

/**
 * @Author:王婷婷
 * @Description:  死锁的案例
 * @Date：Created in ${Time} 2019\8\12 0012
 */

import java.util.concurrent.TimeUnit;

/**
 * 死锁是指两个或者以上的进程在执行过程过程中，
 * 持有自己的，还想要别人的
 *
 * 查看java进行的命令
 */
class  HoldLockThread implements  Runnable{

    private String lockA;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    private String lockB;


    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有："+lockA+"\t 尝试获得："+lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t自己持有："+lockB+"\t 尝试获得："+lockA);
            }
        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
      String lockA="lockA";
      String lockB="lockB";

      new Thread(new HoldLockThread(lockA,lockB),"ThreadAAA").start();
      new Thread((new HoldLockThread(lockB,lockA)),"ThreadBBB").start();



    }
}
