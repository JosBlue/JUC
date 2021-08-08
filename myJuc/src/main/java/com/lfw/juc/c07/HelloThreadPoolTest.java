package com.lfw.juc.c07;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/17 下午9:51
 * @description:
 */
public class HelloThreadPoolTest {

    static class myTask implements Runnable {

        private int i;

        public myTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "====" + i);
            try {
                // 让程序暂停
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "myTask{" +
                    "i=" + i +
                    '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy() // 直接抛出异常
//                new ThreadPoolExecutor.DiscardPolicy() // 直接丢弃
//                new ThreadPoolExecutor.DiscardOldestPolicy() // 移除最早进入队列的信息
                  new ThreadPoolExecutor.CallerRunsPolicy() // 使用主线程完成任务执行

        );

        for (int i = 0; i < 8; i++) {
            threadPoolExecutor.execute(new myTask(i));
        }

        // 队列中的信息
        System.out.println(threadPoolExecutor.getQueue());

        // 新增一个任务
        threadPoolExecutor.execute(new myTask(100));

        System.out.println(threadPoolExecutor.getQueue());

        threadPoolExecutor.shutdown();
    }
}
