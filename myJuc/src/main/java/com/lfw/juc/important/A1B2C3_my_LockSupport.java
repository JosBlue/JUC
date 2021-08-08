package com.lfw.juc.important;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/11 下午2:11
 * @description: 要求两个线程交替打印A1B2C3...Z26
 */
public class A1B2C3_my_LockSupport {

    private static LinkedList<String> numberList = new LinkedList<>();

    private static LinkedList<String> wordList = new LinkedList();

    String numberStr = "1,2,3,4,5,6";

    String wordStr = "A,B,C,D,E,F";

    private Lock lock = new ReentrantLock();

    private static volatile boolean flag = true;

    static Thread t1 = null;

    static Thread t2 = null;

    public void setInfo() {
        List<String> numbers = Arrays.asList(numberStr.split(","));
        List<String> words = Arrays.asList(wordStr.split(","));
        numberList.addAll(numbers);
        wordList.addAll(words);
    }

    public String getNumber() {
        return numberList.removeFirst();
    }

    public String getWord() {
        return wordList.removeFirst();
    }

    public static void main(String[] args) {
        A1B2C3_my_LockSupport test = new A1B2C3_my_LockSupport();
        test.setInfo();

        t1 = new Thread(() -> {
            while (!wordList.isEmpty()) {
                System.out.print(test.getWord());
                if (wordList.isEmpty()) {
                    LockSupport.unpark(t2);
                    System.out.println("t1结束==============");
                    break;
                } else {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        });

        t2 = new Thread(() -> {
            while (!numberList.isEmpty()) {
                LockSupport.park(); // park之后，唤醒后，就从这里往后继续执行
                System.out.print(test.getNumber());
                LockSupport.unpark(t1);
                if (numberList.isEmpty()) {
                    System.out.println("t2结束==============");
                    break;
                }
            }
        });

        t1.start();
        t2.start();
    }


}
