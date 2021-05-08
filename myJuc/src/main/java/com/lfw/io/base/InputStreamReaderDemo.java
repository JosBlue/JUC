package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/26 下午10:21
 * @description:
 */
public class InputStreamReaderDemo {

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = null;

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("/Users/liufuwei/Documents/my-project/my-juc/JUC/myJuc/fileOne.txt");
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");


            char[] chars = new char[1024];
            int length = 0;

            while ((length = inputStreamReader.read(chars)) != -1) {
//                System.out.println((char) inputStreamReader.read());
                System.out.println(new String(chars, 0, length));
            }


            // 文本较少时，可以不使用循环，因为占不满1024个缓冲区，可以直接输出
//            int length2 = inputStreamReader.read(chars);
//            System.out.println(new String(chars, 0, length2));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
