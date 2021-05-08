package com.lfw.io.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/25 下午11:00
 * @description: 图片读取
 */
public class CopyImageByReaderAndWriter {

    /**
     * 处理图片使用该方法无法正常显示
     * @param args
     */
    public static void main(String[] args) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            fileReader = new FileReader("/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/1.png");
            fileWriter = new FileWriter("2.png");

            // 先读入缓冲区
            char[] chars = new char[1024];

            int readerLength = 0;
            while ((readerLength = fileReader.read(chars)) != -1) {
                fileWriter.write(chars);
            }
            fileWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
