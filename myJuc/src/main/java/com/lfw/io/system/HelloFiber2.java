package com.lfw.io.system;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;

import java.util.concurrent.ExecutionException;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/4 上午11:47
 * @description: 启动100W个纤程，执行计算方法
 * 10W-10s左右
 * 100W-12S左右
 */
public class HelloFiber2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Long startTime = System.currentTimeMillis();

        // 定义10W个纤程
        int size = 1000000;
        Fiber[] fibers = new Fiber[size];

        for (int i = 0; i < fibers.length; i++) {
//            fibers[i] = new Fiber<Void>((SuspendableRunnable) HelloFiber::cal);

            fibers[i] = new Fiber(new SuspendableRunnable() {
                @Override
                public void run() throws SuspendExecution, InterruptedException {
                    cal();
                }
            });
        }

        // 启动执行
        for (int i = 0; i < fibers.length; i++) {
            fibers[i].start();
        }

        // 等待所有信息执行返回结果
        for (int i = 0; i < fibers.length; i++) {
            fibers[i].join();
        }

        // 输出执行时间
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
