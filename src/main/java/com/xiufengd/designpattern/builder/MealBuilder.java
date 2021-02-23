package com.xiufengd.designpattern.builder;

import com.xiufengd.designpattern.builder.impl.ChickenBurger;
import com.xiufengd.designpattern.builder.impl.Coke;
import com.xiufengd.designpattern.builder.impl.Pepsi;
import com.xiufengd.designpattern.builder.impl.VegBurger;

/**
 * @author xiufengd
 * @date 2021/2/23 18:48
 * @description
 * @note 未来可期
 */
public class MealBuilder {
    public Meal prepareVgMeal(){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return  meal;
    }
    public Meal prepareNonVegMeal(){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
