package com.example.appnhom9;


public class Recipe {
    private String name;
    private String description;
    private String ingredients;
    private String instructions;
    private String image;

    public Recipe(String name, String description, String ingredients, String instructions) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.image = "";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
