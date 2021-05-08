package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午3:33
 * @description:
 */
public class ByteArrayOutputStreamDemo {

    public static void main(String[] args) {
        OutputStream outputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new FileOutputStream("byteArrayInfo.txt");
            byteArrayOutputStream.write(123);
            byteArrayOutputStream.write("ssss".getBytes());
            // 输出到文件
            byteArrayOutputStream.writeTo(outputStream);
            // 转换成字符输出
            System.out.println(byteArrayOutputStream.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
