package com.ltce.com.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/14 下午1:33
 * @description: 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * <p>
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * <p>
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class Two_Success {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        List<Integer> numOne = new ArrayList();

        // 遍历获取当前节点的信息
        ListNode tmp = l1;
        while (tmp != null) {
            numOne.add(tmp.val);
            tmp = tmp.next;
        }


        List<Integer> numTwo = new ArrayList();
        ListNode tmp2 = l2;
        while (tmp2 != null) {
            numTwo.add(tmp2.val);
            tmp2 = tmp2.next;
        }

        // 根据下标做加和操作
        List<Integer> result = new ArrayList();
        int forSize = 0;
        if (numOne.size() >= numTwo.size()) {

            forSize = numTwo.size();

            // 取信息
            List<Integer> infoList = numOne.subList(0, forSize);

            int value = 0;
            for (int i = 0; i < infoList.size(); i++) {
                String info = String.valueOf(infoList.get(i) + numTwo.get(i) + value);
                if (info.length() > 1) {
                    value = Integer.parseInt(info.substring(0, 1));
                    result.add(Integer.parseInt(info.substring(1)));
                } else {
                    value = 0;
                    result.add(Integer.parseInt(info));
                }
            }

            if (value != 0) {
                for (int i = forSize; i < numOne.size(); i++) {
                    String info = String.valueOf(numOne.get(i) + value);
                    if (info.length() > 1) {
                        value = Integer.parseInt(info.substring(0, 1));
                        result.add(Integer.parseInt(info.substring(1)));
                    } else {
                        value = 0;
                        result.add(Integer.parseInt(info));
                    }
                }
                if (value != 0) {
                    result.add(value);
                }
            } else {
                // 遍历将信息放入
                for (int i = forSize; i < numOne.size(); i++) {
                    result.add(numOne.get(i));
                }
            }
        } else {
            forSize = numOne.size();

            // 取信息
            List<Integer> infoList = numTwo.subList(0, forSize);

            int value = 0;
            for (int i = 0; i < infoList.size(); i++) {
                String info = String.valueOf(infoList.get(i) + numOne.get(i) + value);
                if (info.length() > 1) {
                    value = Integer.parseInt(info.substring(0, 1));
                    result.add(Integer.parseInt(info.substring(1)));
                } else {
                    value = 0;
                    result.add(Integer.parseInt(info));
                }
            }

            if (value != 0) {
                for (int i = forSize; i < numTwo.size(); i++) {
                    String info = String.valueOf(numTwo.get(i) + value);
                    if (info.length() > 1) {
                        value = Integer.parseInt(info.substring(0, 1));
                        result.add(Integer.parseInt(info.substring(1)));
                    } else {
                        value = 0;
                        result.add(Integer.parseInt(info));
                    }
                }
                if (value != 0) {
                    result.add(value);
                }
            } else {
                // 遍历将信息放入
                for (int i = forSize; i < numTwo.size(); i++) {
                    result.add(numTwo.get(i));
                }
            }
        }

        System.out.println(result.size());

        ListNode resultNode = new ListNode();

        //  其他节点
        resultNode.val = result.get(0);
        if (result.size() > 1) {
            ListNode tmpInfo = new ListNode();
            resultNode.next = tmpInfo;
            for (int i = 1; i < result.size(); i++) {
                tmpInfo.val = result.get(i);
                // 获取tmpInfo的next
                ListNode tmpInfo2 = new ListNode();
                if (i != result.size() - 1) {
                    tmpInfo.next = tmpInfo2;
                    tmpInfo = tmpInfo2;
                }
            }
        }


        return resultNode;
    }

//    public static void main(String[] args) {
//        String a = "[0]".replace("[", "").replace("]", "").replace(",", "");
//        String b = "[1]".replace("[", "").replace("]", "").replace(",", "");
//        System.out.println(a.length());
//        ListNode l1 = getListNode(a);
//        ListNode l2 = getListNode(b);
//        System.out.println(l1);
//        System.out.println(l2);
//
//        ListNode listNode = addTwoNumbers(l1, l2);
//        System.out.println(listNode);
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
