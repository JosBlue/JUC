package com.lfw.io.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午3:33
 * @description:
 */
public class ByteArrayInputStreamDemo {

    public static void main(String[] args) {
        ByteArrayInputStream byteArrayInputStream = null;

        byte[] bytes = "123sdfs".getBytes();
        byteArrayInputStream = new ByteArrayInputStream(bytes);
        int readLength = 0;
        while ((readLength = byteArrayInputStream.read()) != -1) {
            byteArrayInputStream.skip(2);
            System.out.println((char) readLength);
        }
        try {
            byteArrayInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
