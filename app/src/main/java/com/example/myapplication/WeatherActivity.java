package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.WeatherResponse;
import com.example.myapplication.Models.WeatherResult;
import com.example.myapplication.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    TextView tvTemp;
    TextView tvStatus;
    TextView tvCityName;
    TextView tvMaxMinTemp;
    TextView tvFeelsLikeTemp;
    TextView tvHumidity;
    TextView tvAtm;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather1);
        tvTemp = findViewById(R.id.textView6);
        tvStatus = findViewById(R.id.textView5);
        tvCityName = findViewById(R.id.cityName);
        tvMaxMinTemp = findViewById(R.id.maxMinTemp);
        tvFeelsLikeTemp = findViewById(R.id.feelsLikeTemp);
        tvHumidity = findViewById(R.id.humidity);
        tvAtm = findViewById(R.id.atm);
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
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    MainWeather mainWeather = weatherResponse.getMain();
                    List<WeatherResult> weatherActivity = weatherResponse.getWeather();

                    tvTemp.setText(String.valueOf(mainWeather.getTemp())+ "\u2103");
                    tvStatus.setText(weatherActivity.get(0).getMain());
                    tvCityName.setText(weatherResponse.getName());
                    tvMaxMinTemp.setText(mainWeather.getTemp_min() + " / " + mainWeather.getTemp_max());
                    tvFeelsLikeTemp.setText("Cảm giác như: " + mainWeather.getFeels_like() + "\u2103");
                    tvHumidity.setText(mainWeather.getHumidity() +
                            "%");
                    tvAtm.setText(mainWeather.getPressure() + " atm");
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