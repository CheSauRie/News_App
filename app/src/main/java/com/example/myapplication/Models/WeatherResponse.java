package com.example.myapplication.Models;

import java.util.List;

public class WeatherResponse {
    private List<WeatherResult> weather;
    private MainWeather main;
    private String name;

    public WeatherResponse(List<WeatherResult> weather, MainWeather main, String name) {
        this.weather = weather;
        this.main = main;
        this.name = name;

    }

    public List<WeatherResult> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherResult> weather) {
        this.weather = weather;
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
