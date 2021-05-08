package com.lfw.juc.c03;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/8 下午3:28
 * @description: 循环栅栏
 * 它的作用就是会让所有线程都等待完成后才会继续下一步行动。
 */
public class CycliBarrierTest {


    public static void main(String[] args) {

        // 20满人 然后发车
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("20满人...发车...");
            }
        });

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // 线程等待
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cyclicBarrier.reset();

        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                // 线程等待
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
