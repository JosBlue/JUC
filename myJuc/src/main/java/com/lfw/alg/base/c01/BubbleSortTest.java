package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/26 上午10:29
 * @description: 冒泡排序（把大的数往后排）最坏时间复杂度O(N^2） 最好的时间复杂度为O(N)
 * 将大的数往后排，第一个数与第二个数比较，更大的数排在后面，以此类推
 * 外层循环从后往前，内层循环从前往后，到外层循环的最大值时，就停止循环
 * 外层循环从后往前，是因为每一次外层循环完成之后，最大的数已经被移到了当前外层循环的index处，下一次就不需要再次循环，这样可以加快遍历速度
 * 即：内层循环控制，前后大小比较，大的放后面，小的放前面，外层循环控制每次内存循环的次数
 */
public class BubbleSortTest {

    public static int[] sort(int[] info) {

        // 只有一个数，直接返回
        if (info == null || info.length < 2) {
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

    /**
     * 对数组中的两个数进行交换
     *
     * @param firstIndex  第一个数下标
     * @param secondIndex 第二个数的下标
     * @param info        数组信息
     * @return 交换后的数组信息
     */
    public static int[] swap(int firstIndex, int secondIndex, int[] info) {
        int temp;
        temp = info[firstIndex];
        info[firstIndex] = info[secondIndex];
        info[secondIndex] = temp;
        return info;
    }

    public static void main(String[] args) {
        int[] info = {7, 6, 8, 4, 3, 5, 1, 2};
        int[] sort = BubbleSortTest.sort(info);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i]);
        }
    }
}
