package com.lfw.juc.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/6 下午5:57
 * @description: 线程状态
 */
public class T04_ThreadState {

    static class MyThread extends Thread {

        @Override
        public void run() {
            System.out.println(this.getName() + "当前状态..." + this.getState());
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
            }
        }

        public static void main(String[] args) {

            Thread t = new MyThread();
            System.out.println(t.getName() + "当前状态000..." + t.getState());


            t.start();
//            t2.start();
            // 等待当前线程执行完毕
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.getName() + "当前状态001" + t.getState());
        }


    }
}
