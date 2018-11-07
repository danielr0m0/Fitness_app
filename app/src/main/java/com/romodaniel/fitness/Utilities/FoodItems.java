package com.romodaniel.fitness.Utilities;

/**
 * Created by Daniel on 7/27/2017.
 */

public class FoodItems {
    String item_name;

    public FoodItems(String item_name, String calories, String brand_name, String serving_size_qty, String serving_size_unit) {
        this.item_name = item_name;
        this.calories = calories;
        this.brand_name = brand_name;
        this.serving_size_qty = serving_size_qty;
        this.serving_size_unit = serving_size_unit;
    }

    public FoodItems() {
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getServing_size_qty() {
        return serving_size_qty;
    }

    public void setServing_size_qty(String serving_size_qty) {
        this.serving_size_qty = serving_size_qty;
    }

    public String getServing_size_unit() {
        return serving_size_unit;
    }

    public void setServing_size_unit(String serving_size_unit) {
        this.serving_size_unit = serving_size_unit;
    }

    String brand_name;
    String serving_size_qty;
    String serving_size_unit;

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    String calories;

}
