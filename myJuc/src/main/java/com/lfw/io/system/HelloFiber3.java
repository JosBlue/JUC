package com.lfw.io.system;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.SuspendableRunnable;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutionException;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/4 上午11:47
 * @description: 利用100个线程执行，同时，每个线程中又分别执行1W个纤程执行，
 * 充分利用CPU对线程的切换，充分利用JVM对纤程的切换
 * 10W--9s左右
 * 100W--16S
 */
public class HelloFiber3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Long startTime = System.currentTimeMillis();

        int fiberSize = 10000;

        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {


                // 每个线程对应1000个纤程（协程）
                Fiber[] fibers = new Fiber[fiberSize];

                for (int i = 0; i < fiberSize; i++) {
                    fibers[i] = new Fiber((SuspendableRunnable) () -> HelloFiber.cal());
                }

                for (int i = 0; i < fiberSize; i++) {
                    fibers[i].start();
                }

                for (int i = 0; i < fiberSize; i++) {
                    fibers[i].join();
                }
            }
        };

        int threadSize = 100;

        // 10个线程
        Thread[] threads = new Thread[threadSize];

        for (int i = 0; i < threadSize; i++) {
            threads[i] = new Thread(runnable);
        }

        for (int i = 0; i < threadSize; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadSize; i++) {
            threads[i].join();
        }

        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);


    }

}
