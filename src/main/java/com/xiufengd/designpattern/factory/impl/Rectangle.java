package com.xiufengd.designpattern.factory.impl;

import com.xiufengd.designpattern.factory.base.Shape;

/**
 * @author xiufengd
 * @date 2021/2/23 16:18
 * 未来可期
 */
public class Rectangle implements Shape {
    @Override
    public void show() {
        System.out.println("Rectangle--------------------");
    }
}
