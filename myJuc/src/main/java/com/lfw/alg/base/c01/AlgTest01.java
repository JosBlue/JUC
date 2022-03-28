package com.lfw.alg.base.c01;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/2/15 上午9:41
 * @description: 给你一个有序数组nums, 请你原地删除重复出现的元素，使每个元素最多出现两次，返回删除后数组的长度
 * 不要使用额外的数组空间，你必须在原地修改数组 并在使用O(1)额外空间的条件下完成
 * nums = [1,1,1,2,2,3]
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [1,1,2]
 * 输出：2, nums = [1,2,_]
 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 * 示例 2：
 * <p>
 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
 * 输出：5, nums = [0,1,2,3,4]
 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
 */
public class AlgTest01 {

    public static void main(String[] args) {
        Integer[] nums = new Integer[6];
        nums[0] = 1;
        nums[1] = 1;
        nums[2] = 1;
        nums[3] = 2;
        nums[4] = 2;
        nums[5] = 3;
//        Map<Integer,Integer> map = new HashMap<>();
//        Integer length = 0;
//        for (int i = 0; i < nums.length; i++) {
//            Integer info =  map.get(nums[i]);
//
//            if(Objects.isNull(info)){
//                map.put(nums[i],1);
//                length ++;
//            }
//
//            if(Objects.nonNull(info)){
//                if(info > 1){
//                    continue;
//                }else{
//                    map.put(nums[i],2);
//                    length ++;
//                }
//            }
//        }
//
//        System.out.println(length);

        for (int i = 0; i < nums.length; i++) {
            if (i == (nums.length - 1)) {

            }


            Integer info = nums[i];
            for (int j = (i + 1); j < nums.length; j++) {
                if (nums[j].compareTo(info) == 0) {
                    nums[j] = nums[j + 1];
                }
            }
        }
    }
}
