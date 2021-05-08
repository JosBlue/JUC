package com.lfw.io.system;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/4 上午11:46
 * @description: 执行100W个线程，计算时间
 * 10W - 11S左右
 * 100W - 死掉了
 */
public class HelloFiber {

    public static void main(String[] args) throws InterruptedException {
        Long startTime = System.currentTimeMillis();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // 执行方法
                cal();
            }
        };

        // 创建1W个线程
        int threadSize = 1000000;

        Thread[] threads = new Thread[threadSize];

        // 分别添加执行方法
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(runnable);
        }

        // 分别执行
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

        // 等待所有线程执行完毕后返回结果
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        // 输出执行完毕时间
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    // 执行方法
    static int cal() {
        int result = 0;
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 2000; j++) {
                result += i;
            }
        }
        return result;
    }
}
