package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/13 下午11:00
 * @description:
 */
public class MyTest {

    public static void main(String[] args) {
        int[] array = {1, 3, 2, 2, 5, 7, 8};
        for (int i = 0; i < array.length; i++) {
            if ((array[i] ^ 1) > array[i]) {
                System.out.println(array[i]);
            }
        }
    }
}
