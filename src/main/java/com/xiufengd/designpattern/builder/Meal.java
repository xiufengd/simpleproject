package com.xiufengd.designpattern.builder;

import com.xiufengd.designpattern.builder.base.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiufengd
 * @date 2021/2/23 18:38
 * @description
 * @note 未来可期
 */
public class Meal {
    private List<Item> items = new ArrayList<>();
    public void addItem(Item item){
        items.add(item);
    }
    public float getCost(){
        float cost = 0.0f;
        for (Item item:items) {
            cost+=item.price();
        }
        return cost;
    }
    public void showItems(){
        for (Item item:items) {
            System.out.println("item:"+item.name());
            //获取条目的包装类，并调用包装类的具体实现方法pack方法
            System.out.println("Packing:"+item.packing().pack());
            System.out.println("Price:"+item.price());
        }
    }
}
