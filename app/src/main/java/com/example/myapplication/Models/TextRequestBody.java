package com.example.myapplication.Models;

public class TextRequestBody {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextRequestBody(String text) {
        this.text = text;
    }
}
