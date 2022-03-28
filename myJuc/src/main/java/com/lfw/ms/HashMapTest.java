package com.lfw.ms;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/20 上午11:17
 * @description:
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap();
        map.put(null,null);

        Map<String, String> hashTable = new Hashtable<>();
        hashTable.put(null,"1");
        hashTable.put("1",null);
    }
}
