package com.xiufengd.designpattern.abstractfactory.impl;

import com.xiufengd.designpattern.abstractfactory.base.Shape;

/**
 * @author xiufengd
 * @date 2021/2/23 16:20
 * 未来可期
 */
public class Circle implements Shape {
    @Override
    public void show() {
        System.out.println("Circle------------------------");
    }
}
