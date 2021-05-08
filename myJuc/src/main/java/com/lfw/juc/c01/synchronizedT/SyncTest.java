package com.lfw.juc.c01.synchronizedT;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 上午10:35
 * @description: 分析程序输出结果
 * 不加锁：会出现乱序，重复值
 * 加锁：顺序输出，不出现重复值
 */
public class SyncTest implements Runnable {

    int count = 10;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + "===count值:" + count);
    }

    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        for (int i = 0; i < 10; i++) {
            new Thread(syncTest, "THREAD" + i).start();
        }
    }
}
