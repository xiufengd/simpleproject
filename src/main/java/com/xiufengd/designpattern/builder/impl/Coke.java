package com.xiufengd.designpattern.builder.impl;

/**
 * @author xiufengd
 * @date 2021/2/23 18:31
 * @description 具体的实现类，继承抽象的drink，只需要实现name和price即可，包装已经在父类中实现了
 * @note 未来可期
 */
public class Coke extends AbstractColdDrink{
    @Override
    public String name() {
        return "Coke";
    }

    @Override
    public float price() {
        return 5.0f;
    }
}
