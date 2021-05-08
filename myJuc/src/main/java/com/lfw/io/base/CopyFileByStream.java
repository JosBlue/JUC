package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/25 下午10:06
 * @description: 图片复制
 */
public class CopyFileByStream {

    public static void main(String[] args) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream("/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/1.png");
            outputStream = new FileOutputStream("2.png");

            // 先读入缓冲区
            byte[] butter = new byte[1024];

            int byteLength = 0;
            while ((byteLength = inputStream.read(butter)) != -1) {
                outputStream.write(butter);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
