package com.lfw.ms;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/23 上午11:07
 * @description:
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> implements Map<K, V> {
    private static final long serialVersionUID = 1L;

    // init
    public LRUCache(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    /**
     * 重写LinkedHashMap的remove方法，当元素大于6时，移除最近最不常用的元素
     *
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        if (size() > 6) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        LRUCache<Character, Integer> lruCache = new LRUCache<Character, Integer>(16, 0.75f, true);
        String s = "abcdefjhijk";
        for (int i = 0; i < s.length(); i++) {
            lruCache.put(s.charAt(i), i);
        }
        System.out.println("LRU中key为h的Entry值：" + lruCache.get('h'));
        System.out.println("LRU的大小" + lruCache.size());
        System.out.println("LRU的信息" + lruCache);

    }
}
