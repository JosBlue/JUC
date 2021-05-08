package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午3:53
 * @description:
 */
public class BufferOutputStreamDemo {

    public static void main(String[] args) {
        OutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            outputStream = new FileOutputStream("BufferOutputStreamDemo.txt");
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            bufferedOutputStream.write(132);
            bufferedOutputStream.write("sdfsdf".getBytes());
            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
