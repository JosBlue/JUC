package com.ltce.com.order;

import java.util.Objects;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/1/14 下午4:49
 * @description:
 */
public class Two_Good_Copy {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode head = null;

        ListNode tail = null;
        int carry = 0;

        while (l1 != null || l2 != null) {

            int num1 = Objects.nonNull(l1) ? l1.val : 0;

            int num2 = Objects.nonNull(l2) ? l2.val : 0;

            int sum = num1 + num2 + carry;

            if (Objects.isNull(head)) {
                head = tail = new ListNode(sum % 10);
            } else {
                // tail = new ListNode(sum % 10);
                // tail = tail.next;
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }

            carry = sum / 10;

            if (Objects.nonNull(l1)) {
                l1 = l1.next;
            }

            if (Objects.nonNull(l2)) {
                l2 = l2.next;
            }
        }

        if (carry > 0) {
            tail.next = new ListNode(carry);
        }


        return head;
    }

//    public static void main(String[] args) {
//        System.out.println(14 % 10);
//    }
}
