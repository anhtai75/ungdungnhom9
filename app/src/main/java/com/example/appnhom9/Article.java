package com.example.appnhom9;

public class Article {

    private String title;
    private String description;
    private String content;
    private int imageResId; // Dùng int thay vì String

    // Constructor
    public Article(String title, String description, String content, int imageResId) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public int getImageResId() {
        return imageResId;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // toString
    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", imageResId=" + imageResId +
                '}';
    }
}