package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/26 下午10:34
 * @description:
 */
public class OutputStreamWriterDemo {

    public static void main(String[] args) {

        OutputStreamWriter outputStreamWriter = null;
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream("123.txt");
            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
            outputStreamWriter.write("123");
            outputStreamWriter.write("qqq");
            outputStreamWriter.write("舒服舒服");
            outputStreamWriter.write("电饭锅蛋糕");
            outputStreamWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
