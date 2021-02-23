package com.xiufengd.designpattern.abstractfactory.impl;

import com.xiufengd.designpattern.abstractfactory.base.Color;

/**
 * @author xiufengd
 * @date 2021/2/23 16:39
 * 未来可期
 */
public class Red implements Color {

    @Override
    public void show() {
        System.out.println("red-----------");
    }
}
