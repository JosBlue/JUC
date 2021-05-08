package com.lfw.io.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/25 下午10:36
 * @description: 读取字符流信息
 */
public class ReaderDemo {

    /**
     * 字节流（inputStream/outputStream）与字符流(reader/writer)的区别：
     * 字符流可以直接处理中文，但是字符流处理中文会出现乱码
     *
     * @param args
     */

    public static void main(String[] args) {
        Reader reader = null;

        try {
            reader = new FileReader("/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/fileOne.txt");
            int readerLength = 0;
            while (((readerLength = reader.read()) != -1)) {
                System.out.println((char) reader.read());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
