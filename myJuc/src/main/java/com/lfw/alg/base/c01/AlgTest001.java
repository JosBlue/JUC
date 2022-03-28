package com.lfw.alg.base.c01;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/14 下午11:43
 * @description:
 */
public class AlgTest001 {

    // 一个数组中，只有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种出现了奇数次的数
    // 方法：直接用0，挨着异或,因为异或的值与数据的顺序无关，因此有偶数个的数据，异或之后，就是0，剩下的就是最终奇数个信息
    // 只有一个数出现了奇数次
    public static void printOddTimesNum1(int[] array) {
        int eor = 0;
        for (int i = 0; i < array.length; i++) {
            eor = eor ^ array[i]; // eor ^= array[i];
        }
        System.out.println(eor);
    }

    // 怎么把一个int类型的数，提取出最右侧的1来
    // 比如有一个整数的二进制数为N=0001001101000
    // 思路：N&(~N+1) 即，将N与上N取反加1即可
    // 原因：
    // 首先，N取反，即～N=1110110010111
    // 然后加1，则从右往左，为1的需要进位，最终的结果就是：1110110011000
    // 最后，再和N做与操作，则：与就是同一位置，相同就是1，不同就是0
    //原始： 1110110011000
    //变更： 0001001101000
    //结果： 0000000001000
    // 这样就把最右侧的0给取出来了

    public static void main(String[] args) {
        int N = 6;
        System.out.println(N & (~N + 1));
    }

    // 上面思想的应用
    // 一个数组中，有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种出现了奇数次的数
    // 思路：
    // 首先，我们还是先将所有数挨着做一次异或运算，这样最终的结果肯定就是为奇数的两个数，比如为eor=a^b，此时 ，eor必然!=0
    // 接着，我们找到eor最右侧为1的数,通过上面的方法： eor' = eor&(~eor+1)
    // 然后，我们重新遍历一遍数据，找到和eor'做与运算不等于0的数，不等于0，换句话说话，就是找到eor中的其中一个数
    // 因为，我们将eor'遍历做与运算，找到的，就是二进制上相同位置为1的数
    // 接着，我们再将找到的这些数，再与eor'做异或运算，偶数个数，就直接为0了，剩下的就是有奇数的其中一个数
    // 最后，我们再将eor与eor'做异或 运算，就找到了另外一个有奇数个的数
    public static void printOddTimesNum2(int[] array) {
        int eor = 0;
        for (int i = 0; i < array.length; i++) {
            eor = eor ^ array[i];
        }

        // 找到最右侧不为0的数
        int rightOne = eor & (~eor + 1);

        // 此时，eor = a ^ b
        int onlyOne = 0; // eor'
        for (int i = 0; i < array.length; i++) {
            if ((rightOne & array[i]) != 0) {
                onlyOne = onlyOne ^ array[i];
            }
        }

        System.out.println(onlyOne + "  " + (onlyOne ^ eor));
    }
}
