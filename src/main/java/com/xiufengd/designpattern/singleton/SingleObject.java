package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:08
 * @description 饿汉模式 加载即初始化 会造成资源浪费
 * 未来可期
 */
public class SingleObject {
    private static SingleObject singleObject = new SingleObject();
    //构造方法
    private SingleObject(){}

    public static SingleObject getInstance(){
        return singleObject;
    }

    public void show(){
        System.out.println("SingleObject");
    }
}
