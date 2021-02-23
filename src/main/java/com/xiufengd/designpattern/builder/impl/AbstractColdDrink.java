package com.xiufengd.designpattern.builder.impl;

import com.xiufengd.designpattern.builder.base.Item;
import com.xiufengd.designpattern.builder.base.Packing;

/**
 * @author xiufengd
 * @date 2021/2/23 18:11
 * @description 饮料的实现类，只实现包装方法，名称和价格交给extend的子类去实现
 * @note 未来可期
 */
public abstract class AbstractColdDrink implements Item {

    @Override
    public Packing packing() {
        //饮料是瓶装
        return new Bottle();
    }

    @Override
    public abstract String name();

    @Override
    public abstract float price();
}
