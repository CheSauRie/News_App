package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.Weather;
import com.example.myapplication.Models.WeatherResponse;
import com.example.myapplication.Models.WeatherResult;
import com.example.myapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBackgroundService extends Service {
    public MyBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //To do

        callApi();

        return START_NOT_STICKY;
    }

    void callApi() {
        fetchWeather("Hanoi");
    }

    void fetchWeather(String cityname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<WeatherResponse> call = apiService.getData(cityname,
                "5b31ef22641b9bd114643b900e0815af", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    MainWeather mainWeather = weatherResponse.getMain();
                    List<WeatherResult> weatherActivity = weatherResponse.getWeather();
                    Weather weather = new Weather();
                    weather.setCurrentTemp(mainWeather.getTemp());
                    int statusCode = weatherActivity.get(0).getId();
                    /**
                    String main = "";
                    String imgSrc = "";
                    if (statusCode >= 200 && statusCode < 300) {
                        main = "Giông bão";
                        imgSrc = "";
                    }
                    if (statusCode >= 300 && statusCode < 400) {
                        main = "Mưa phùn";
                    }
                    if (statusCode >= 500 && statusCode < 600) {
                        main = "Trời mưa";

                        imgSrc = "@drawable/10d";
                    }
                    if (statusCode >= 600 && statusCode < 700 ) {
                        main = "Tuyết rơi";
                    }
                    if (statusCode == 800) {
                        main = "Nắng gắt";
                    }
                    if (statusCode > 800) {
                        main = "Nắng nhiều mây";
                    } **/
                    //weather.setMain(main);
                    weather.setCode(weatherActivity.get(0).getId());
                    weather.setCityName("Thành phố Hà Nội");
                    //tvMaxMinTemp.setText(mainWeather.getTemp_min() + " / " + mainWeather.getTemp_max());
                    weather.setMaxTemp(mainWeather.getTemp_max());
                    weather.setMinTemp(mainWeather.getTemp_min());
                    //tvFeelsLikeTemp.setText("Cảm giác như: " + mainWeather.getFeels_like() + "\u2103");
                    weather.setFeelsLike(mainWeather.getFeels_like());
                    //tvHumidity.setText(mainWeather.getHumidity() +
                            //"%");
                    weather.setHumidity(mainWeather.getHumidity());
                    weather.setPressure(mainWeather.getPressure());
                    //tvAtm.setText(mainWeather.getPressure() + " atm");
                    weather.setImgURL("https://openweathermap.org/img/wn/" +
                            weatherActivity.get(0).getIcon() + "@4x.png");
                    /**
                    Picasso.get().load(imgURL).placeholder(R.drawable.sun_cloud)
                            .error(R.drawable.sun_cloud).
                            into(ivWeatherIcon, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    // Image loaded successfully
                                }

                                @Override
                                public void onError(Exception e) {
                                    // Error occurred while loading the image
                                    e.printStackTrace();
                                }
                            }); **/
                } else {
                    Log.d("LottoActivity", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("T", "onFailure: " +"failed");
            }
        });
    }
}