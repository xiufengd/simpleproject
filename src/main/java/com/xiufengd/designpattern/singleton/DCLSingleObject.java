package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:25
 * @description 双重锁定模式 多线程下保持高性能
 * 未来可期
 */
public class DCLSingleObject {
    private static DCLSingleObject dclSingleObject;
    private DCLSingleObject(){}
    private static DCLSingleObject getInstance(){
        if (dclSingleObject==null){
            synchronized (DCLSingleObject.class){
                if (dclSingleObject==null){
                    dclSingleObject = new DCLSingleObject();
                }
            }
        }
        return dclSingleObject;
    }
}
