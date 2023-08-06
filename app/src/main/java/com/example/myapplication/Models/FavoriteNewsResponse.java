package com.example.myapplication.Models;

import java.util.List;

public class FavoriteNewsResponse {
    private String message;
    private List<Result> data;

    public FavoriteNewsResponse(String message, List<Result> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }
}
