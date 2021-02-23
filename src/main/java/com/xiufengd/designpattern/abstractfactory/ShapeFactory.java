package com.xiufengd.designpattern.abstractfactory;

import com.xiufengd.designpattern.abstractfactory.base.Color;
import com.xiufengd.designpattern.abstractfactory.base.Shape;
import com.xiufengd.designpattern.abstractfactory.impl.Circle;
import com.xiufengd.designpattern.abstractfactory.impl.Rectangle;
import com.xiufengd.designpattern.abstractfactory.impl.Square;

/**
 * @author xiufengd
 * @date 2021/2/23 16:43
 * 未来可期
 */
public class ShapeFactory extends AbstractFactory{

    @Override
    public Color getColor(String type) {
        return null;
    }

    @Override
    public Shape getShape(String type) {
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(type.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(type.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }
}
