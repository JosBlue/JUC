package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午5:14
 * @description:
 */
public class DataDemo {

    public static void main(String[] args) {
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream("DataDemo.txt");
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeChar(1);
            dataOutputStream.writeBoolean(false);
            dataOutputStream.writeDouble(23.44D);
            dataOutputStream.writeUTF("UTF-8");

            inputStream = new FileInputStream("DataDemo.txt");
            dataInputStream = new DataInputStream(inputStream);
            System.out.println(dataInputStream.readChar());
            System.out.println(dataInputStream.readBoolean());
            System.out.println(dataInputStream.readDouble());
            System.out.println(dataInputStream.readUTF());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
                dataInputStream.close();
                outputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
