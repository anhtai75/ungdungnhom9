package com.example.appnhom9;


public class Diet {
    private String name;
    private String description;
    private String image;

    public Diet(String name, String description) {
        this.name = name;
        this.description = description;
        this.image = "";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}