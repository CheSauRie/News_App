package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.Weather;
import com.example.myapplication.Models.WeatherResponse;
import com.example.myapplication.Models.WeatherResult;
import com.example.myapplication.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.squareup.picasso.Picasso;

public class WeatherActivity extends AppCompatActivity {
    ProgressDialog dialog;
    TextView tvTemp;
    TextView tvStatus;
    TextView tvCityName;
    TextView tvMaxMinTemp;
    TextView tvFeelsLikeTemp;
    TextView tvHumidity;
    TextView tvAtm;

    ImageView ivWeatherIcon;
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
        ivWeatherIcon = findViewById(R.id.weatherIcon);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Đang cập nhật thời tiết");
        dialog.show();
        fetchWeather("Hanoi");
        //tvTemp.setText(String.valueOf(weather.getCurrentTemp()));
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
                    int [] imgList = new int[] {R.drawable.quan};
                    tvTemp.setText(String.valueOf(mainWeather.getTemp())+ "\u2103");
                    int statusCode = weatherActivity.get(0).getId();
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
                    }
                    tvStatus.setText(main);
                    tvCityName.setText("Thành phố Hà Nội");
                    tvMaxMinTemp.setText(mainWeather.getTemp_min() + " / " + mainWeather.getTemp_max());
                    tvFeelsLikeTemp.setText("Cảm giác như: " + mainWeather.getFeels_like() + "\u2103");
                    tvHumidity.setText(mainWeather.getHumidity() +
                            "%");
                    tvAtm.setText(mainWeather.getPressure() + " atm");
                    String imgURL = "https://openweathermap.org/img/wn/" +
                            weatherActivity.get(0).getIcon() + "@4x.png";
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
                    });
                    dialog.dismiss();
                } else {
                    Log.d("WeatherActivity", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("T", "onFailure: " +"failed");
            }
        });
    }
}