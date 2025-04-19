package com.example.appnhom9;


import java.io.Serializable;

public class Diet implements Serializable {
    private String name;
    private String description;
    private String allergies;

    public Diet(String name, String description, String allergies) {
        this.name = name;
        this.description = description;
        this.allergies = allergies;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAllergies() {
        return allergies;
    }
}