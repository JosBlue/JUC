package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/25 下午10:06
 * @description: 文件复制
 */
public class CopyFile {

    public static void main(String[] args) {
        // 读取源文件
        File fileONe = new File("/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/fileOne.txt");

        // 写入文件
        File fileTwoCopyOne = new File("fileTwoCopyOne.txt");
        if (!fileTwoCopyOne.exists()) {
            try {
                fileTwoCopyOne.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 创建文件输出流及文件输入流
        InputStream inputStream = null;

        OutputStream outputStream = null;

        // 将文件转换成输入流及输出流

        try {
            inputStream = new FileInputStream(fileONe);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        try {
            outputStream = new FileOutputStream(fileTwoCopyOne);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // 创建缓冲区
            byte[] butter = new byte[1024];

            // 长度
            int length = 0;

            while (((length = inputStream.read(butter)) != -1)) {
                outputStream.write(butter);
            }
            System.out.println("结束...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
