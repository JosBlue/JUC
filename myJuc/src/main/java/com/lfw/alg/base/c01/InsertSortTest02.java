package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/2/16 下午1:55
 * @description: 插入排序
 * 插入排序（把小的数往前排）最大时间复杂度O(N^2) 最好的时间复杂度为O(N)
 */
public class InsertSortTest02 {

    public static int[] sort(int[] info) {

        if (info == null || info.length < 2) {
            return info;
        }

        for (int i = 1; i < info.length; i++) {

            for (int j = (i - 1); j >= 0; j--) {
                if (info[j] > info[j + 1]) {
                    swap(j, (j + 1), info);
                }
            }
        }

        return info;
    }


    public static void swap(int firstIndex, int secondIndex, int[] info) {
//        int tmp;
//        tmp = info[secondIndex];
//        info[secondIndex] = info[firstIndex];
//        info[firstIndex] = tmp;

        info[firstIndex] = info[firstIndex] ^ info[secondIndex];
        info[secondIndex] = info[firstIndex] ^ info[secondIndex];
        info[firstIndex] = info[firstIndex] ^ info[secondIndex];
    }

    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = InsertSortTest02.sort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }
}
