package com.ltce.com.string;


import com.sun.deploy.util.StringUtils;

import java.util.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/14 下午5:33
 * @description: <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * </p>
 */
public class String_20_TODO {

    public static boolean isValid(String s) {

        int n = s.length();
        // 奇数则为直接抛出
        if (n % 2 == 1) {
            return false;
        }

        // 定义Hash基础信息
        Map<String, String> map = new HashMap<String, String>() {{
            put(")", "(");
            put("}", "{");
            put("]", "[");
        }};

        // 使用char，内存会小一些
//        Map<Character, Character> map = new HashMap<Character, Character>() {{
//            put(')', '(');
//            put('}', '{');
//            put(']', '[');
//        }};

        // 定义栈，根据先进后出的特性，用于实现查找配对
        Deque<String> stack = new LinkedList<String>();
//        Deque<Character> stack = new LinkedList<Character>();

        for (int i = 0; i < n; i++) {
            // 将字符串转换为字符
//            char ch = s.charAt(i);
            String ch = s.substring(i, i + 1);

            if (map.containsKey(ch)) {
                if (stack.isEmpty() || !Objects.equals(stack.peek(), map.get(ch))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String s = "{([{()}])}{)";
        System.out.println(isValid(s));
    }

}
