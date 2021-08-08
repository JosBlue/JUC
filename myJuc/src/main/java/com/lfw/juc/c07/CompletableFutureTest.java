package com.lfw.juc.c07;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/17 下午9:08
 * @description:
 */
public class CompletableFutureTest {


    private static Double priceTM() {
        delay("TM");
        return 1.00;
    }

    private static Double priceTB() {
        delay("TB");
        return 2.00;
    }

    private static Double priceJD() {
        delay("JD");
        return 2.50;
    }

    private static void delay(String name) {
        int time = new Random().nextInt(500);
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s After %s sleep!\n", name, time);
    }

    public static void main(String[] args) throws IOException {
        Long startTime = System.currentTimeMillis();
//        CompletableFuture<Double> futureTM = CompletableFuture.supplyAsync(() -> priceTM());
//
//        CompletableFuture<Double> futureTB = CompletableFuture.supplyAsync(() -> priceTB());
//
//        CompletableFuture<Double> futureJD = CompletableFuture.supplyAsync(() -> priceJD());

//        CompletableFuture.allOf(futureTM, futureTB, futureJD).join();

        CompletableFuture.supplyAsync(() -> priceTB())
                .thenApply(String::valueOf)
                .thenApply(price -> "price" + price)
                .thenAccept(System.out::print);

//        System.out.println("执行时间==" + (System.currentTimeMillis() - startTime));
        System.in.read();
    }

}
