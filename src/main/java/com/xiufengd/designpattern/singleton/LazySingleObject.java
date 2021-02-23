package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:16
 * @description 懒汉模式 用的时候才加载，不保证安全性
 * 未来可期
 */
public class LazySingleObject {
    private static LazySingleObject lazySingleObject;
    private LazySingleObject(){}
    public static LazySingleObject getInstance(){
        if (lazySingleObject==null){
            lazySingleObject = new LazySingleObject();
        }
        return lazySingleObject;
    }
}
