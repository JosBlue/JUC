package com.ltce.com.string;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/5/19 下午3:49
 * @description:
 */
public class String_20_my {

    static Deque<String> leftQueue = new LinkedList<>();

    static Deque<String> rightQueue = new LinkedList<>();

//    static String value = "{{}}[]";

    public static boolean add(String s) {

        if (s.length() == 0 || s.length() % 2 != 0) {
            return false;
        }

        boolean flag = true;

        for (int i = 0; i < s.length(); i++) {
            String value = s.substring(i, i + 1);
            if (Objects.equals(value, "{") || Objects.equals(value, "(") || Objects.equals(value, "[")) {
                leftQueue.push(value);
            } else {
                if (leftQueue.size() == 0) {
                    flag = false;
                    break;
                }
                String peek = leftQueue.pop();
                if (Objects.equals((peek + value), "{}") ||
                        Objects.equals((peek + value), "()") ||
                        Objects.equals((peek + value), "[]")) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if(leftQueue.size()!=0){
            flag = false;
        }
        return flag;
    }

    public static void main(String[] args) {
        String s = "()";
        System.out.println(add(s));

    }


}
