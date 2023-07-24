package com.example.myapplication.Models;

public class VideoData {
    private String desc;
    private String title;
    private String url;

    public VideoData(String desc, String title, String url) {
        this.desc = desc;
        this.title = title;
        this.url = url;
    }

    public VideoData()
    {

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
