package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Models.LoginRequest;
import com.example.myapplication.Models.LoginResponse;
import com.example.myapplication.Models.MainWeather;
import com.example.myapplication.Models.WeatherResponse;
import com.example.myapplication.Models.WeatherResult;
import com.example.myapplication.api.ApiService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    String token= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButtonInLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallLoginApi(username.getText().toString(), password.getText().toString());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
    void CallLoginApi(String email, String password) {
        LoginRequest dataObject = new LoginRequest(email,password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://25bc-42-119-181-197.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginResponse> call = apiService.postLoginData(dataObject);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    assert loginResponse != null;
                    token = loginResponse.getToken();
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                } else {
                    Log.d("callAPi", "error" + response.code());
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("T", "onFailure: " +"failed");
            }
        });
    }
}

