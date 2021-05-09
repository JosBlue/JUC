package com.lfw.juc.c03;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/8 下午5:05
 * @description: Phaser
 */
public class PhaserTest {

    static MyPhaser myPhaser = new MyPhaser();
    static Random r = new Random();


    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        myPhaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();

        new Thread(new Person("新娘")).start();

    }


    @Data
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class Person implements Runnable {

        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            milliSleep(r.nextInt(1000));
            System.out.println(name + "到了...");
            myPhaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.println(name + "吃完了...");
            myPhaser.arriveAndAwaitAdvance();
        }


        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 离开！\n", name);
            myPhaser.arriveAndAwaitAdvance();
        }


        public void hug() {
            if (Objects.equals(name, "新郎") || Objects.equals(name, "新娘")) {
                milliSleep(r.nextInt(1000));
                System.out.println(name + "抱抱...");
                myPhaser.arriveAndAwaitAdvance();
            } else {
                myPhaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();

            eat();

            leave();

            hug();
        }
    }


    static class MyPhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {

            switch (phase) {
                case 0: {
                    System.out.println("所有人都到了...");
                    System.out.println();
                    return false;
                }
                case 1: {
                    System.out.println("所有人都吃完了...");
                    System.out.println();
                    return false;
                }
                case 2: {
                    System.out.println("所有人都离开了...");
                    System.out.println();
                    return false;
                }
                case 3: {
                    System.out.println("婚礼结束，新郎，新娘都离开了...");
                    System.out.println();
                    return true;
                }
                default: {
                    return true;
                }
            }
        }

    }

}
