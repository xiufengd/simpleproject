package com.xiufengd.designpattern.abstractfactory;

import com.xiufengd.designpattern.abstractfactory.base.Color;
import com.xiufengd.designpattern.abstractfactory.base.Shape;

/**
 * @author xiufengd
 * @date 2021/2/23 16:42
 * 未来可期
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String type);
    public abstract Shape getShape(String type);
}
