package com.xiufengd.designpattern.builder.impl;

import com.xiufengd.designpattern.builder.base.Packing;

/**
 * @author xiufengd
 * @date 2021/2/23 18:06
 * @description 包装的实现类   纸质包装
 * @note 未来可期
 */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
