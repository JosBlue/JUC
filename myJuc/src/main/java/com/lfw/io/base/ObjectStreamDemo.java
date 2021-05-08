package com.lfw.io.base;

import java.io.*;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/4/28 下午10:06
 * @description:
 */
public class ObjectStreamDemo {
    public static void main(String[] args) {
        ObjectOutputStream objectOutputStream = null;
        OutputStream outputStream = null;
        ObjectInputStream objectInputStream = null;
        InputStream inputStream = null;

        try {
            outputStream = new FileOutputStream("ObjectStreamDemo.txt");
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(new Person("test", "123345"));
            inputStream = new FileInputStream("ObjectStreamDemo.txt");
            objectInputStream = new ObjectInputStream(inputStream);
            System.out.println(objectInputStream.readObject().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                objectInputStream.close();
                inputStream.close();
                objectOutputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
