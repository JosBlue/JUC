package com.lfw.io.base;

import com.sun.xml.internal.messaging.saaj.util.CharReader;

import java.io.CharArrayReader;
import java.io.IOException;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午10:21
 * @description:
 */
public class CharArrayReaderDemo {

    public static void main(String[] args) {
        CharArrayReader charArrayReader = null;

        char[] chars = new char[1024];

        charArrayReader = new CharArrayReader(chars);

        try {
            charArrayReader.read("信息测试".toCharArray());
            System.out.println( charArrayReader.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
