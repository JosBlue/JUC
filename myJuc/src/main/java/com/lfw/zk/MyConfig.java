package com.lfw.zk;

/**
 * @email fuwei.liu@dmall.com
 * @author: fuwei.iu
 * @date: 2021/11/14 下午5:15
 * @description: zk配置信息数据类
 * 这个类，是与实际的业务逻辑关联性最强的
 */
public class MyConfig {

    String config;

    public void setConfig(String config) {
        this.config = config;
    }

    public String getConfig() {
        return config;
    }
}
