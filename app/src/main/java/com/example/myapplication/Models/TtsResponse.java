package com.example.myapplication.Models;

public class TtsResponse {
    private String async;

    public String getAsync() {
        return async;
    }

    public void setAsync(String async) {
        this.async = async;
    }

    @Override
    public String toString() {
        return "TtsResponse{" +
                "async='" + async + '\'' +
                '}';
    }
}
