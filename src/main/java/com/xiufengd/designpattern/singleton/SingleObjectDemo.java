package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:10
 * @description 单例模式
 * 未来可期
 */
public class SingleObjectDemo {
    public static void main(String[] args) {

        SingleObject object = SingleObject.getInstance();
        object.show();
        EnumSingletonObject.INSTANCE.show();
        System.out.println(EnumSingletonObject.INSTANCE.num++);
        System.out.println(EnumSingletonObject.INSTANCE.num++);
    }
}
