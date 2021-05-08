package com.lfw.juc.c01.synchronizedT;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午1:40
 * @description: synchronized 这个锁是可重入的 其可重入最大的作用是避免死锁
 */
public class SyncReentrant {

    public synchronized void m1() {
        System.out.println("m1方法调用...");
        m2();
    }


    public synchronized void m2() {
        System.out.println("m2方法调用...");
    }

    public static void main(String[] args) {
        SyncReentrant syncReentrant = new SyncReentrant();
        syncReentrant.m1();
    }

}
