package com.lfw.juc.c03;

import java.util.concurrent.CountDownLatch;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/8 下午2:25
 * @description: countDownLatch 等待所有信息执行完毕后在返回值
 */
public class CountDownLatchTest {

    static int threadLength = 100;

    static Thread[] threads = new Thread[threadLength];

    /**
     * countDownLatch执行测试
     */
    public static void countDownRun() {
        Long startTime = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(threadLength);

        for (int i = 0; i < threadLength; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 100000; j++) {
                    result = result + j;
                }
//                System.out.println(Thread.currentThread().getName() + "==计算出结果值:" + result);
                countDownLatch.countDown();
            });
        }

        for (int i = 0; i < threadLength; i++) {
            threads[i].start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("countDownLatch计算时间..." + (System.currentTimeMillis() - startTime));
    }

    /**
     * 执行join方法
     */
    public static void joinRun() {
        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadLength; i++) {
            threads[i] = new Thread(() -> {
                int result = 0;
                for (int j = 0; j < 100000; j++) {
                    result = result + j;
                }
//                System.out.println(Thread.currentThread().getName() + "==计算出结果值:" + result);
            });
        }

        for (int i = 0; i < threadLength; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadLength; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Join计算时间..." + (System.currentTimeMillis() - startTime));
    }

    public static void main(String[] args) {
        countDownRun();
        joinRun();
    }


}
