package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/2/16 上午10:16
 * @description: 冒泡排序
 * 从后往前，逐一比较，大的排在最前面
 */
public class BubbleSortTest02 {

    public static int[] bubbleSort(int[] info) {

        if (info == null || info.length == 1) {
            return info;
        }

        for (int i = (info.length - 1); i > 0; i--) {

            for (int j = 0; j < i; j++) {
                if (info[j] > info[j + 1]) {
                    swap(j, (j + 1), info);
                }
            }
        }

        return info;
    }

    public static void swap(Integer firstIndex, int secondIndex, int[] info) {
        int tmp;
        tmp = info[firstIndex];
        info[firstIndex] = info[secondIndex];
        info[secondIndex] = tmp;
    }

    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = BubbleSortTest02.bubbleSort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }

}
