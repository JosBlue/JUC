package com.lfw.juc.c01.synchronizedT;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午2:02
 * @description: synchronized 当锁出现异常时，默认情况下，锁会被释放
 * 所以在并发处理过程中，有异常要多加小心，否则会出现数据不一致的问题
 * 比如，在一个Web app 处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据
 * 因此要非常小心的处理同步业务逻辑中的异常
 */
public class SyncError {

    int count = 0;

    public synchronized void m() {
        System.out.println("当前线程信息..." + Thread.currentThread().getName());

        while (true) {
            System.out.println("系统正在输出..." + count++);
            int a = 1 / 0;
        }
    }


    public static void main(String[] args) {
        SyncError syncError = new SyncError();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                syncError.m();
            }).start();
        }
    }
}
