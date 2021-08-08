package com.lfw.tt;

import java.util.Arrays;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/6/30 下午1:46
 * @description:
 */
public class ArraysTest {

    public static void main(String[] args) {
        int[] arrays = new int[3];
        arrays[0] = 1;
        arrays[1] = 2;
        arrays[2] = 3;

        // 此处输出1，2，3
        for (int i = 0; i < arrays.length; i++) {
            System.out.println(arrays[i]);
        }

        // 此处只输出一个hashcode的值的对象
        List<int[]> ints = Arrays.asList(arrays);
        ints.forEach(System.out::println);
    }
}
