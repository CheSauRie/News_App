package com.example.myapplication;

import android.annotation.SuppressLint;
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
import com.example.myapplication.Models.SignupRequest;
import com.example.myapplication.Models.SignupResponse;
import com.example.myapplication.api.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    EditText name;
    EditText username;
    EditText password;
    EditText confirmPassword;
    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name_signup);
        username = findViewById(R.id.username_signup);
        password = findViewById(R.id.password_signup);
        confirmPassword = findViewById(R.id.confirm_password);
        signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "confirmPassword does not match with password", Toast.LENGTH_SHORT).show();
                    return;
                }
                CallLoginApi(name.getText().toString(), username.getText().toString(), password.getText().toString());
            }
        });
    }
    void CallLoginApi(String name, String email, String password) {
        SignupRequest dataObject = new SignupRequest(name,email,password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://25bc-42-119-181-197.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<SignupResponse> call = apiService.postSignupData(dataObject);

        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()) {
                    SignupResponse signupResponse = response.body();
                    assert signupResponse != null;
                    Toast.makeText(SignupActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                } else {
                    Log.d("callAPi", "error" + response.code());
                    Toast.makeText(SignupActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.d("T", "onFailure: " +"failed");
            }
        });
    }
}