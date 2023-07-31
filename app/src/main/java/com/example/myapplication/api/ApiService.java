package com.example.myapplication.api;

import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    ApiService retrofit_lotery = new Retrofit.Builder()
            .baseUrl("https://api-xsmb.cyclic.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("api/v1/")
    Call<LoteryResponse> callLoteryResponse();
    @GET("data/2.5/weather")
    Call<WeatherResponse> getData(
            @Query("q") String q,
            @Query("appid") String API_KEY,
            @Query("units") String units
    );
}
