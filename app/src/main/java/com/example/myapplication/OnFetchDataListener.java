package com.example.myapplication;

import com.example.myapplication.Models.NewsHeadLines;
import com.example.myapplication.Models.Result;

import java.util.List;

public interface OnFetchDataListener<NewsData> {
    void onFetchData(List<Result> list, String message);
    void onError(String message);
}
