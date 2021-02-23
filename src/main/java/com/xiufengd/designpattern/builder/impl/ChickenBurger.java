package com.xiufengd.designpattern.builder.impl;

/**
 * @author xiufengd
 * @date 2021/2/23 18:30
 * @description 具体的实现类，继承抽象的burger，只需要实现name和price即可，包装已经在父类中实现了
 * @note 未来可期
 */
public class ChickenBurger extends AbstractBurger{
    @Override
    public String name() {
        return "chicken Burger";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
