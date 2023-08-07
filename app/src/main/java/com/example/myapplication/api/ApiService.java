package com.example.myapplication.api;

import com.example.myapplication.Models.CrawlNewsData;
import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.Models.FavoriteNewsResponse;
import com.example.myapplication.Models.ProfileResponse;
import com.example.myapplication.Models.Result;
import com.example.myapplication.Models.SignupRequest;
import com.example.myapplication.Models.SignupResponse;
import com.example.myapplication.Models.TtsResponse;
import com.example.myapplication.Models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    ApiService retrofit_backend = new Retrofit.Builder() //192.168.232.2
            .baseUrl("https://8262-118-71-137-37.ngrok-free.app/")
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
    @GET("favorite/get-all")
    Call<FavoriteNewsResponse> getFavoriteNews(
            @Header("Authorization") String authToken
    );
    @POST("favorite/save")
    Call<FavoriteNewsResponse> postFavoriteNewsData(
            @Header("Authorization") String authToken,
            @Body Result request
    );
    @GET("/user")
    Call<ProfileResponse> getProfile(
            @Header("Authorization") String authToken
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
