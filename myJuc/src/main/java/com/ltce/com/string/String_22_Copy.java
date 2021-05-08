package com.ltce.com.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/19 下午3:10
 * @description:
 */
public class String_22_Copy {

    public List<String> generateParenthesis(int n) {
        // 结果信息
        List<String> combinations = new ArrayList<>();

        // 产生结果值
        generateAll(new char[2 * n], 0, combinations);

        return combinations;
    }

    /**
     * 递归生成每一种结果值信息
     *
     * @param current 若n为4，则左右括号一共为8个，即2n
     * @param pos     递归跳出条件，当字符串长度为最大长度时，需要校验是否符合左右括号配对规则
     * @param result  结果值信息
     */
    public void generateAll(char[] current, int pos, List<String> result) {

        if (pos == current.length) {
            // 检测是否符合括号的生成规则，符合则放入值中
            if (valid(current)) {
                result.add(new String(current));
            }
        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }
    }


    /**
     * 当生成了对应数量的括号数后，校验生成的括号是否符合生成规则，符合的规则放入值中
     *
     * @param current 生成的括号数
     * @return 是否符合生成规则
     */
    public boolean valid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(') {
                ++balance;
            } else {
                --balance;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }


}
