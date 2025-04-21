package com.example.appnhom9;

public class Food {
    private String name;
    private float calories;
    private float carbs;
    private float protein;
    private float fat;

    public Food(String name, float calories, float carbs, float protein, float fat) {
        this.name = name;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }

    public float getFat() {
        return fat;
    }
}