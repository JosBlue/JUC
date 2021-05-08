package com.lfw.juc.c01.synchronizedT;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午1:51
 * @description: synchronized 锁可重入，经常会体现在继承上
 */
public class SyncReentrantParentAndChild {

    synchronized void m() {
        System.out.println("m1....start....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1....end....");
    }


    static class T extends SyncReentrantParentAndChild {
        @Override
        synchronized void m() {
            System.out.println("child....start....");
            super.m();
            System.out.println("child....end....");
        }
    }

    public static void main(String[] args) {
        T t = new T();
        t.m();
    }
}
