package com.example.myapplication.api;

import com.example.myapplication.Models.CrawlNewsData;
import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.SignupRequest;
import com.example.myapplication.Models.SignupResponse;
import com.example.myapplication.Models.TextRequestBody;
import com.example.myapplication.Models.TtsResponse;
import com.example.myapplication.Models.WeatherResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    ApiService retrofit_lotery = new Retrofit.Builder()
            .baseUrl("https://api-xsmb.cyclic.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
    ApiService retrofit_tts = new Retrofit.Builder()
            .baseUrl("https://api.fpt.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);
    ApiService retrofit_crawl_news = new Retrofit.Builder() //192.168.232.2
            .baseUrl("http://192.168.232.2:8085/")
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
    @POST("auth/login")
    Call<LoginResponse> postLoginData(
            @Body LoginRequest loginRequest
    );
    @POST("auth/register")
    Call<SignupResponse> postSignupData(
            @Body SignupRequest signupRequest
    );
    @POST("hmi/tts/v5")
    Call<TtsResponse> postTextToSpeech(
            @Body String text,
            @Query("api_key") String api_key,
            @Query("voice") String voice
    );
    @GET("scraper")
    Call<CrawlNewsData> crawlNewsData(
            @Query("url") String url
    );
}
