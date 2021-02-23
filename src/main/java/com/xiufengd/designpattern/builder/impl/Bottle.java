package com.xiufengd.designpattern.builder.impl;

import com.xiufengd.designpattern.builder.base.Packing;

/**
 * @author xiufengd
 * @date 2021/2/23 18:07
 * @description 包装实现类 瓶装包装
 * @note 未来可期
 */
public class Bottle implements Packing {
    @Override
    public String pack() {
        return "Bottle";
    }
}
