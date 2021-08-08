package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/26 下午1:38
 * @description: 插入排序（把小的数往前排）最大时间复杂度O(N^2) 最好的时间复杂度为O(N)
 * 外层循环，从第二数开始循环，因为第一个数与第一个数没有比较的意义
 * 内层循环，每次只比较2个数，即外层循环的index与(index+1)
 */
public class InsertSortTest {

    public static int[] sort(int[] array) {

        if (array == null || array.length < 2) {
            return array;
        }

//        for (int i = 0; i < array.length; i++) {
//            for (int j = (array.length - 1); j > i; j--) {
//                if (array[j] < array[j - 1]) {
//                    swap(j, (j - 1), array);
//                }
//            }
//        }

        for (int i = 1; i < array.length; i++) {
            for (int j = (i - 1); j >= 0; j--) {
                if (array[j] > array[j + 1]) {
                    swap(j, (j + 1), array);
                }
            }
        }

        // zcy
//        for (int i = 1; i < arr.length; i++) { // 0 ~ i 做到有序
//            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
//                swap(arr, j, j + 1);
//            }
//        }

        return array;
    }


    public static void swap(int firstIndex, int secondIndex, int[] array) {
        array[firstIndex] = array[firstIndex] ^ array[secondIndex];
        array[secondIndex] = array[firstIndex] ^ array[secondIndex];
        array[firstIndex] = array[firstIndex] ^ array[secondIndex];
    }


    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = InsertSortTest.sort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }
}
