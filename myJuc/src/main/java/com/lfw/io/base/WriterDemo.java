package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/25 下午10:36
 * @description: 写入字符流信息
 */
public class WriterDemo {

    /**
     * 字节流（inputStream/outputStream）与字符流(reader/writer)的区别：
     * 字符流可以直接处理中文，但是字符流处理中文会出现乱码
     *
     * @param args
     */

    public static void main(String[] args) {
        Writer writer = null;

        try {
            writer = new FileWriter("writerForTest.text");
            writer.write("123");
            writer.write("中文才是他的优势");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
