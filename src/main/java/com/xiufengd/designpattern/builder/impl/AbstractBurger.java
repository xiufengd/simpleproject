package com.xiufengd.designpattern.builder.impl;

import com.xiufengd.designpattern.builder.base.Item;
import com.xiufengd.designpattern.builder.base.Packing;

/**
 * @author xiufengd
 * @date 2021/2/23 18:08
 * @description  汉堡抽象实现类,只实现包装类，名称和价格交给extend的子类去实现
 * @note 未来可期
 */
public abstract class AbstractBurger implements Item {
    @Override
    public Packing packing() {
        //纸质包装 Wapper是Packing的实现类，可以作为返回值返回
        return new Wrapper();
    }

    @Override
    public abstract String name();

    @Override
    public abstract float price();
}
