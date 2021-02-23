package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:19
 * @description 懒汉模式，方法同步锁，保证安全性，但性能较差，多线程时会卡顿
 * 未来可期
 */
public class LazySyncSingleObject {
    private static LazySyncSingleObject lazySyncSingleObject;
    private LazySyncSingleObject(){}
    public static synchronized LazySyncSingleObject getInstance(){
        if (lazySyncSingleObject==null){
            lazySyncSingleObject = new LazySyncSingleObject();
        }
        return lazySyncSingleObject;
    }
}
