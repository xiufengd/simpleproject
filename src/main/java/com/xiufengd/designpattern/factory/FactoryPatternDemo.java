package com.xiufengd.designpattern.factory;

import com.xiufengd.designpattern.factory.base.Shape;

/**
 * @author xiufengd
 * @date 2021/2/23 16:26
 * @DESC 工厂模式
 * 未来可期
 */
public class FactoryPatternDemo {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.getShape("CIRCLE");
        shape.show();

        shape = shapeFactory.getShape("RECTANGLE");
        shape.show();

        shape = shapeFactory.getShape("SQUARE");
        shape.show();
    }
}
