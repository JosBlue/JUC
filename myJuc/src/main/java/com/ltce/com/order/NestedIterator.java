package com.ltce.com.order;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/3/23 下午3:40
 * @description: #341. 扁平化嵌套列表迭代器
 * 给你一个嵌套的整型列表。请你设计一个迭代器，使其能够遍历这个整型列表中的所有整数。
 * <p>
 * 列表中的每一项或者为一个整数，或者是另一个列表。其中列表的元素也可能是整数或是其他列表。
 * 示例 1:
 * <p>
 * 输入: [[1,1],2,[1,1]]
 * 输出: [1,1,2,1,1]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,1,2,1,1]。
 * <p>
 * 示例 2:
 * <p>
 * 输入: [1,[4,[6]]]
 * 输出: [1,4,6]
 * 解释: 通过重复调用 next 直到 hasNext 返回 false，next 返回的元素的顺序应该是: [1,4,6]。
 *
 * <p>
 */
public class NestedIterator implements Iterator<Integer> {

    private List<Integer> vals;
    private Iterator<Integer> cur;

    public NestedIterator(List<NestedInteger> nestedList) {
        vals = new ArrayList<>();
        exchangData(nestedList);
        cur = vals.iterator();

    }

    @Override
    public Integer next() {
        return cur.next();

    }

    @Override
    public boolean hasNext() {
        return cur.hasNext();
    }

    private void exchangData(List<NestedInteger> nestedList) {
        for (NestedInteger nested : nestedList) {
            if (nested.isInteger(nested)) {
                vals.add(nested.val);
            } else {
                exchangData(nested.getList);
            }
        }
    }


    public static void main(String[] args) {
        List<NestedInteger> nestedList = new ArrayList<>();

        NestedIterator nestedIterator = new NestedIterator(nestedList);
    }
}
