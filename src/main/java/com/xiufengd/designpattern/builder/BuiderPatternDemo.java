package com.xiufengd.designpattern.builder;

/**
 * @author xiufengd
 * @date 2021/2/23 18:52
 * @description
 * @note 未来可期
 */
public class BuiderPatternDemo {
    public static void main(String[] args) {
        MealBuilder mealBuilder = new MealBuilder();
        Meal vegMeal = mealBuilder.prepareVgMeal();
        System.out.println("Veg Meal");
        vegMeal.showItems();
        System.out.println("cost:"+vegMeal.getCost());
    }
}
