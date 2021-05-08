package com.lfw.juc.c01.synchronizedT;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/7 下午1:19
 * @description: 脏读
 * 对业务写方法加锁，对业务读方法不加锁行不行？
 * 这样的做法会产生脏读，根据实际业务诉求判断是否允许存在脏读的现象
 */
public class SyncAccount {

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @NoArgsConstructor
    static class Account {

        Integer account = 0;

        String name;
    }


    /**
     * 写信息
     */
    public static synchronized void writeInfo(Account account, Integer accountNum) {
        // 暂停3秒后再写
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.setAccount(accountNum);
    }


    public static void main(String[] args) {

        Account account = new Account();

        // 当前线程读信息
        new Thread(() -> {
            System.out.println("t1线程读信息...account=" + account.getAccount());
        }).start();

        // 当前线程写信息
        new Thread(() -> {
            System.out.println("t2线程准备开始写信息...");
            writeInfo(account, 100);
        }).start();

        // 当前线程读信息
        new Thread(() -> {
            System.out.println("t3线程读信息...account=" + account.getAccount());
        }).start();

        // 当前线程3秒后再读信息
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t4线程读信息...account=" + account.getAccount());
        }).start();
    }

}
