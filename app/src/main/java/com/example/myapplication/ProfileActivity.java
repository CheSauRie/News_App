package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.FavoriteNewsResponse;
import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.ProfileResponse;
import com.example.myapplication.Models.Result;
import com.example.myapplication.api.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
        String token = preferences.getString("accessToken","");
        if(token.equals("")) {
            setContentView(R.layout.activity_profile_unlogged);
        } else {
            setContentView(R.layout.activity_profile);
            name = findViewById(R.id.name_profile);
            getProfile(token);
        }


        //setContentView(R.layout.activity_profile);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else if (itemId == R.id.video) {
                startActivity(new Intent(getApplicationContext(), VideoActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.favourite) {
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                return true;
            }
            return false;
        });

        Button btn_weather = findViewById(R.id.start_weather_btn);
        Button btn_lotto = findViewById(R.id.start_lotto_btn);
        Button btn_gold_price = findViewById(R.id.start_gp_btn);
        Button btn_log_out = findViewById(R.id.start_log_out_btn);
        Button btn_log_in = findViewById(R.id.start_log_in_btn);
        btn_weather.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(ProfileActivity.this, WeatherActivity.class));
           }
        });

        btn_lotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, LottoActivity.class));
            }
        });

        btn_gold_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, GoldPriceActivity.class));
            }
        });

        if (btn_log_out != null) {
            btn_log_out.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
                    preferences.edit().putString("accessToken", "").apply();
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                }
            });
        }

        if (btn_log_in != null) {
            btn_log_in.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                }
            });
        }
    }
    void getProfile(String accessToken) {
        Call<ProfileResponse> call = ApiService.retrofit_backend.getProfile(accessToken);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {
                    ProfileResponse dataResponse = response.body();
                    assert dataResponse != null;
                    name.setText(dataResponse.getName());
                } else {
                    Log.d("callAPi123", "error asd" + response.code() + response.message());
                    Toast.makeText(ProfileActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.d("error", "onFailure: " +"failed " + call.timeout() + t.getMessage());
            }
        });
    }
}