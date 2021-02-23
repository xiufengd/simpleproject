package com.xiufengd.designpattern.abstractfactory;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiufengd
 * @date 2021/2/23 16:52
 * 未来可期
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String type){
        if (StringUtils.isEmpty(type)){
            return null;
        }
        if ("COLOR".equals(type)){
            return new ColorFactory();
        }else {
            return new ShapeFactory();
        }
    }
}
