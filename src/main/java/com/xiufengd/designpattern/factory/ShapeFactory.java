package com.xiufengd.designpattern.factory;

import com.xiufengd.designpattern.factory.base.Shape;
import com.xiufengd.designpattern.factory.impl.Circle;
import com.xiufengd.designpattern.factory.impl.Rectangle;
import com.xiufengd.designpattern.factory.impl.Square;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xiufengd
 * @date 2021/2/23 16:21
 * 未来可期
 */
public class ShapeFactory {
    public Shape getShape(String type){
        if (StringUtils.isEmpty(type)){
            return null;
        }
        if ("CIRCLE".equals(type)){
            return new Circle();
        }else if ("RECTANGLE".equals(type)){
            return new Rectangle();
        }else if ("SQUARE".equals(type)){
            return new Square();
        }
        return null;
    }
}
