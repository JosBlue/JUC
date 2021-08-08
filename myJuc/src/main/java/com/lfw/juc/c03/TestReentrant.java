package com.lfw.juc.c03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/13 上午10:09
 * @description:
 */
public class TestReentrant {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        System.out.println("123");
        lock.unlock();
    }
}
