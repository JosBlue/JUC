package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午3:53
 * @description:
 */
public class BufferInputStreamDemo {

    public static void main(String[] args) {
        BufferedInputStream bufferedInputStream = null;
        InputStream inputStream = null;
        // 字符流输出中文信息
        BufferedReader bufferedReader = null;
        Reader reader = null;
        try {
            inputStream = new FileInputStream("123.txt");
            reader = new FileReader("123.txt");
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedReader = new BufferedReader(reader);
            int readLength = 0;
            while ((readLength = bufferedInputStream.read()) != -1) {
                System.out.print((char) readLength);
            }

            System.out.println("=====================");
            readLength = 0;
            while ((readLength = bufferedReader.read()) != -1) {
                System.out.print((char) readLength);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedInputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
