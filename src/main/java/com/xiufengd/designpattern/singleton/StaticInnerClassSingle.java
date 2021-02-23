package com.xiufengd.designpattern.singleton;

/**
 * @author xiufengd
 * @date 2021/2/23 17:42
 * @description 静态内部类模式
 * @note 未来可期
 */
public class StaticInnerClassSingle {
    private static class StaticInnerClassSingleHandle{
        private static StaticInnerClassSingle staticInnerClassSingle = new StaticInnerClassSingle();
    }
    private StaticInnerClassSingle(){}
    public static StaticInnerClassSingle getInstance(){
        return StaticInnerClassSingleHandle.staticInnerClassSingle;
    }
}
