package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LottoActivity extends AppCompatActivity {
    ProgressDialog dialog;
    TextView giaiDB, giaiNhat, giaiNhi1, giaiNhi2, giaiBa1, giaiBa2, giaiBa3, giaiBa4, giaiBa5, giaiBa6,
            giaiBon1, giaiBon2, giaiBon3, giaiBon4, giaiNam1,giaiNam2,giaiNam3,giaiNam4,giaiNam5,giaiNam6,
            giaiSau1, giaiSau2, giaiSau3, giaiBay1, giaiBay2, giaiBay3, giaiBay4, date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải kết quả");
        dialog.show();
        callApi();
        date = findViewById(R.id.ngayXoso);
        giaiNhat = findViewById(R.id.giaiNhat);
        giaiDB = findViewById(R.id.giaiDB);
        giaiNhi1 = findViewById(R.id.giaiNhi1);
        giaiNhi2 = findViewById(R.id.giaiNhi2);
        giaiBa1 = findViewById(R.id.giaiBa1);
        giaiBa2 = findViewById(R.id.giaiBa2);
        giaiBa3 = findViewById(R.id.giaiBa3);
        giaiBa4 = findViewById(R.id.giaiBa4);
        giaiBa5 = findViewById(R.id.giaiBa5);
        giaiBa6 = findViewById(R.id.giaiBa6);
        giaiBon1 = findViewById(R.id.giaiBon1);
        giaiBon2 = findViewById(R.id.giaiBon2);
        giaiBon3 = findViewById(R.id.giaiBon3);
        giaiBon4 = findViewById(R.id.giaiBon4);
        giaiNam1 = findViewById(R.id.giaiNam1);
        giaiNam2 = findViewById(R.id.giaiNam2);
        giaiNam3 = findViewById(R.id.giaiNam3);
        giaiNam4 = findViewById(R.id.giaiNam4);
        giaiNam5 = findViewById(R.id.giaiNam5);
        giaiNam6 = findViewById(R.id.giaiNam6);
        giaiSau1 = findViewById(R.id.giaiSau1);
        giaiSau2 = findViewById(R.id.giaiSau2);
        giaiSau3 = findViewById(R.id.giaiSau3);
        giaiBay1 = findViewById(R.id.giaiBay1);
        giaiBay2 = findViewById(R.id.giaiBay2);
        giaiBay3 = findViewById(R.id.giaiBay3);
        giaiBay4 = findViewById(R.id.giaiBay4);
    }

    private void callApi() {
        ApiService.retrofit_lotery.callLoteryResponse().enqueue(new Callback<LoteryResponse>() {
            @Override
            public void onResponse(Call<LoteryResponse> call, Response<LoteryResponse> response) {
                if (response.isSuccessful()) {
                    LoteryResponse loteryResponse = response.body();
                    if (loteryResponse != null) {
                        String db = response.body().getResults().getDB().get(0);
                        String g1 = response.body().getResults().getG1().get(0);
                        String g2_0 = response.body().getResults().getG2().get(0);
                        String g2_1 = response.body().getResults().getG2().get(1);
                        String g3_0 = response.body().getResults().getG3().get(0);
                        String g3_1 = response.body().getResults().getG3().get(1);
                        String g3_2 = response.body().getResults().getG3().get(2);
                        String g3_3 = response.body().getResults().getG3().get(3);
                        String g3_4 = response.body().getResults().getG3().get(4);
                        String g3_5 = response.body().getResults().getG3().get(5);
                        String g4_0 = response.body().getResults().getG4().get(0);
                        String g4_1 = response.body().getResults().getG4().get(1);
                        String g4_2 = response.body().getResults().getG4().get(2);
                        String g4_3 = response.body().getResults().getG4().get(3);
                        String g5_0 = response.body().getResults().getG5().get(0);
                        String g5_1 = response.body().getResults().getG5().get(1);
                        String g5_2 = response.body().getResults().getG5().get(2);
                        String g5_3 = response.body().getResults().getG5().get(3);
                        String g5_4 = response.body().getResults().getG5().get(4);
                        String g5_5 = response.body().getResults().getG5().get(5);
                        String g6_0 = response.body().getResults().getG6().get(0);
                        String g6_1 = response.body().getResults().getG6().get(1);
                        String g6_2 = response.body().getResults().getG6().get(2);
                        String g7_0 = response.body().getResults().getG7().get(0);
                        String g7_1 = response.body().getResults().getG7().get(1);
                        String g7_2 = response.body().getResults().getG7().get(2);
                        String g7_3 = response.body().getResults().getG7().get(3);
                        String time = response.body().getTime();
                        date.setText(time);
                        giaiDB.setText(db);
                        giaiNhat.setText(g1);
                        giaiNhi1.setText(g2_0);
                        giaiNhi2.setText(g2_1);
                        giaiBa1.setText(g3_0);
                        giaiBa2.setText(g3_1);
                        giaiBa3.setText(g3_2);
                        giaiBa4.setText(g3_3);
                        giaiBa5.setText(g3_4);
                        giaiBa6.setText(g3_5);
                        giaiBon1.setText(g4_0);
                        giaiBon2.setText(g4_1);
                        giaiBon3.setText(g4_2);
                        giaiBon4.setText(g4_3);
                        giaiNam1.setText(g5_0);
                        giaiNam2.setText(g5_1);
                        giaiNam3.setText(g5_2);
                        giaiNam4.setText(g5_3);
                        giaiNam5.setText(g5_4);
                        giaiNam6.setText(g5_5);
                        giaiSau1.setText(g6_0);
                        giaiSau2.setText(g6_1);
                        giaiSau3.setText(g6_2);
                        giaiBay1.setText(g7_0);
                        giaiBay2.setText(g7_1);
                        giaiBay3.setText(g7_2);
                        giaiBay4.setText(g7_3);
                        dialog.dismiss();
                    } else {
                        Log.d("LottoActivity", "Response body is null");
                    }
                } else {
                    Log.d("LottoActivity", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoteryResponse> call, Throwable t) {
                Log.d("T", "onFailure: " +"failed");
            }
        });
    }
}