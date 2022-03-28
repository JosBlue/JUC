package com.lfw.ms;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/23 上午11:04
 * @description:
 */
public class LinkedHashMapTest {

    public static void main(String[] args) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(10);
        linkedHashMap.put("1", "2");
        System.out.println(JSON.toJSONString(linkedHashMap));
    }
}
