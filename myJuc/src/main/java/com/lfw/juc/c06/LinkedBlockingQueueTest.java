package com.lfw.juc.c06;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/17 下午4:52
 * @description: LinkedBlockingQueue可以实现简单的生产者消费者模型 阻塞
 */
public class LinkedBlockingQueueTest {

    static BlockingQueue<String> info = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        // 生产者
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                try {
                    info.put("a" + i); // 如果满了，就会等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                    TimeUnit.SECONDS.sleep(1);
            }
        }, "p1").start();

        // 消费者
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    // 如果空了，就会等待
                    System.out.println(Thread.currentThread().getName() + "===" + info.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "c" + i).start();

        }
    }

}
