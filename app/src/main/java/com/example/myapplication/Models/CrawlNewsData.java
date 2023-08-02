package com.example.myapplication.Models;

import java.util.List;

public class CrawlNewsData {
    private String imgLogo;
    private List<String> content;
    private List<String> imageUrl;
    private List<String> videoUrl;

    public String getImgLogo() {
        return imgLogo;
    }

    public void setImgLogo(String imgLogo) {
        this.imgLogo = imgLogo;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(List<String> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public CrawlNewsData(String imgLogo, List<String> content, List<String> imageUrl, List<String> videoUrl) {
        this.imgLogo = imgLogo;
        this.content = content;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    @Override
    public String toString() {
        return "CrawlNewsData{" +
                "imgLogo='" + imgLogo + '\'' +
                ", content=" + content +
                ", imageUrl=" + imageUrl +
                ", videoUrl=" + videoUrl +
                '}';
    }
}
