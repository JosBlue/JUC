package com.ltce.com.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/13 上午11:24
 * @description: 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class Two_Error {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        List oneNum = new ArrayList();

        // 遍历获取当前节点的信息
        ListNode tmp = l1;
        while (tmp != null) {
            oneNum.add(tmp.val);
            tmp = tmp.next;
        }

        // 翻转
        StringBuilder numOne = new StringBuilder();
        for (int i = oneNum.size() - 1; i > -1; i--) {
            numOne.append(oneNum.get(i));
        }


        List twoNum = new ArrayList();
        ListNode tmp2 = l2;
        while (tmp2 != null) {
            twoNum.add(tmp2.val);
            tmp2 = tmp2.next;
        }

        // 翻转
        StringBuilder numTwo = new StringBuilder();
        for (int i = twoNum.size() - 1; i > -1; i--) {
            numTwo.append(twoNum.get(i));
        }

        // 计算两者值
        String value = String.valueOf(Long.parseLong(String.valueOf(numOne)) + Long.parseLong(String.valueOf(numTwo)));
//        String value = new BigInteger(String.valueOf(numOne)).add(new BigInteger((String.valueOf(numTwo)))).toString();

//        Long s = Long.valueOf(a + b);
        // 拆分计算值组装为链表
        // 第一个节点
        ListNode result = new ListNode();
        result.val = Integer.parseInt(value.substring((value.length() - 1)));

        //  其他节点
        if (value.length() > 1) {
            ListNode tmpInfo = new ListNode();
            result.next = tmpInfo;

            for (int i = value.length() - 2; i > -1; i--) {
                tmpInfo.val = Integer.parseInt(value.substring(i, (i + 1)));
                // 获取tmpInfo的next
                ListNode tmpInfo2 = new ListNode();
                if (i != 0) {
                    tmpInfo.next = tmpInfo2;
                    tmpInfo = tmpInfo2;
                }
            }
        }

        return result;
    }

//    public static void main(String[] args) {
////[2,4,3]
////[5,6,4]
//
//        // 拆分计算值组装为链表
////        // 第一个节点
//        ListNode l1 = getListNode("9999999991");
//        ListNode l2 = getListNode("9");
//        System.out.println(l1);
//        System.out.println(l2);
//
//        ListNode listNode = addTwoNumbers(l1, l2);
//        System.out.println(listNode);
//
//    }

    public static ListNode getListNode(String value) {
        ListNode result = new ListNode();
        result.val = Integer.parseInt(value.substring((value.length() - 1)));

        //  其他节点
        if (value.length() > 1) {
            ListNode tmpInfo = new ListNode();
            result.next = tmpInfo;

            for (int i = value.length() - 2; i > -1; i--) {
                tmpInfo.val = Integer.parseInt(value.substring(i, (i + 1)));
                // 获取tmpInfo的next
                ListNode tmpInfo2 = new ListNode();
                if (i != 0) {
                    tmpInfo.next = tmpInfo2;
                    tmpInfo = tmpInfo2;
                }
            }
        }
        return result;
    }
}
