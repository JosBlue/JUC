package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/26 下午2:11
 * @description: 选择排序 最坏及最好的时间复杂度均为O(N^2) 性质不稳定
 */
public class ChoseSortTest {

    public static int[] sort(int[] array) {

        if (array == null || array.length < 2) {
            return array;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = (i + 1); j < array.length; j++) {
                if (array[i] > array[j]) {
                    swap(i, j, array);
                }
            }
        }

        return array;
    }

    public static int[] swap(int firstIndex, int secondIndex, int[] info) {
        int temp;
        temp = info[firstIndex];
        info[firstIndex] = info[secondIndex];
        info[secondIndex] = temp;
        return info;
    }

    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = ChoseSortTest.sort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }

}
