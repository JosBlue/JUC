package com.lfw.ms;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2022/3/15 上午12:35
 * @description: 线程安全的单例
 */
public class SecuritySingleTon {

    private static volatile SecuritySingleTon securitySingleTon = null;

    public static SecuritySingleTon getSecuritySingleTon() {
        if (securitySingleTon == null) {
            synchronized (SecuritySingleTon.class) {
                if (securitySingleTon == null) {
                    securitySingleTon = new SecuritySingleTon();
                }
            }
        }
        return securitySingleTon;
    }


}
