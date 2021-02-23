package com.xiufengd.designpattern.builder.base;

/**
 * @author xiufengd
 * @date 2021/2/23 18:04
 * @description 食物描述类 包含：包装 价格 名字
 * @note 未来可期
 */
public interface Item {
    public Packing packing();
    public String name();
    public float price();
}
