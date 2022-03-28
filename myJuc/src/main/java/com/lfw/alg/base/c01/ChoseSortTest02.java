package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/2/16 下午1:27
 * @description: 选择排序 O(n平方)
 */
public class ChoseSortTest02 {

    public static int[] sort(int[] info) {

        if (info == null || info.length == 1) {
            return info;
        }

        for (int i = 0; i < info.length; i++) {
            for (int j = (i + 1); j < info.length; j++) {
                if (info[i] > info[j]) {
                    swap(i, j, info);
                }
            }
        }

        return info;
    }


    public static void swap(int firstIndex, int secondIndex, int[] info) {
        int tmp;
        tmp = info[firstIndex];
        info[firstIndex] = info[secondIndex];
        info[secondIndex] = tmp;
    }


    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = ChoseSortTest02.sort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }
}
