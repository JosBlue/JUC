package com.ltce.com.order;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/13 上午9:59
 * @description: 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 * <p>
 * 你可以按任意顺序返回答案。
 */
public class One {

//    public static void main(String[] args) {
//        int[] nums = {-1, -2, -3, -4, -5, -8};
//        int[] result = twoSum(nums, -8);
//        System.out.println(result[0] + "====" + result[1]);
//    }


    public static int[] twoSum(int[] nums, int target) {
        int[] finalNums = new int[2];
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = (i + 1); j < nums.length; j++) {
//                if (nums[i] + nums[j] == target) {
//                    finalNums[0] = i;
//                    finalNums[1] = j;
//                    break;
//                }
//            }

        Map<Integer, Integer> hash = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i])) {
                finalNums[0] = i;
                finalNums[1] = hash.get(nums[i]);
            }

            hash.put(target - nums[i], i);

        }

        return finalNums;
    }
}
