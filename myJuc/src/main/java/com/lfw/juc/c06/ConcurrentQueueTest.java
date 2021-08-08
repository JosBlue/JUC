package com.lfw.juc.c06;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/17 下午4:47
 * @description: ConcurrentLinkQueue
 */
public class ConcurrentQueueTest {

    public static void main(String[] args) {
        Queue<String> strings = new ConcurrentLinkedQueue<>();

        // 循环放入
        for (int i = 0; i < 10; i++) {
            strings.offer(i + "q");
        }

        System.out.println(strings);
        System.out.println(strings.size());

        // 只取不移除
        System.out.println(strings.peek());
        System.out.println(strings.size());

        // 取值并移除
        System.out.println(strings.poll());
        System.out.println(strings.size());

    }
}
