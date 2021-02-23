package com.xiufengd.designpattern.abstractfactory;

import com.xiufengd.designpattern.abstractfactory.base.Color;
import com.xiufengd.designpattern.abstractfactory.base.Shape;

/**
 * @author xiufengd
 * @date 2021/2/23 16:50
 * @description 抽象工厂模式
 * 未来可期
 */
public class AbstractFactoryPatternDemo {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("COLOR");
        Color color =  shapeFactory.getColor("GREEN");
        color.show();
        shapeFactory = FactoryProducer.getFactory("SHAPE");
        Shape shape =  shapeFactory.getShape("CIRCLE");
        shape.show();
    }
}
