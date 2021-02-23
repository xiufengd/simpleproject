package com.xiufengd.designpattern.abstractfactory;

import com.xiufengd.designpattern.abstractfactory.base.Color;
import com.xiufengd.designpattern.abstractfactory.base.Shape;
import com.xiufengd.designpattern.abstractfactory.impl.*;

/**
 * @author xiufengd
 * @date 2021/2/23 16:46
 * 未来可期
 */
public class ColorFactory extends AbstractFactory{
    @Override
    public Color getColor(String type) {
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("RED")){
            return new Red();
        } else if(type.equalsIgnoreCase("GREEN")){
            return new Green();
        } else if(type.equalsIgnoreCase("BLUE")){
            return new Blue();
        }
        return null;
    }

    @Override
    public Shape getShape(String type) {
        return null;
    }
}
