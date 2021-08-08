package com.lfw.juc.c07;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/17 下午8:15
 * @description:
 */
public class FutureTaskTest {

    public static void main(String[] args) {
        FutureTask<String> task = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                return "1000";
            }
        });

//        new Thread(task).start();
        task.run();

        try {
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
