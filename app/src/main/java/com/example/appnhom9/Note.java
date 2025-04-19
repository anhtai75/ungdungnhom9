package com.example.appnhom9;

public class Note {
    private String title;
    private String content;

    // Constructor
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter cho title
    public String getTitle() {
        return title;
    }

    // Getter cho content
    public String getContent() {
        return content;
    }
}