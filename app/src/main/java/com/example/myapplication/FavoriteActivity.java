package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Models.FavoriteNewsResponse;
import com.example.myapplication.Models.NewsData;
import com.example.myapplication.Models.Result;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements SelectListener{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải tin tức..");
        dialog.show();
        searchView = findViewById(R.id.search_view_favorite);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                dialog.setTitle("Đang tải tin tức");
                dialog.show();
                RequestManager manager = new RequestManager(FavoriteActivity.this);
                manager.getNewsData(listener, s, null, null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        SharedPreferences preferences = getSharedPreferences("Auth", MODE_PRIVATE);
        String accessToken = preferences.getString("accessToken","");
        if (!accessToken.equals("")) {
            RequestManager manager = new RequestManager(this);
            manager.getFavoriteNewsData(listener, accessToken);
            dialog.dismiss();
        } else {
            Toast.makeText(FavoriteActivity.this, "Please login to see your favorite news", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.favourite);

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
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private final OnFetchDataListener<FavoriteNewsResponse> listener = new OnFetchDataListener<FavoriteNewsResponse>() {
        @Override
        public void onFetchData(List<Result> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(FavoriteActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
            } else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Log.d("err", "onError: " + message);
            Toast.makeText(FavoriteActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<Result> list) {
        recyclerView = findViewById(R.id.recycler_favorite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    public void OnNewsClicked(Result result) {
        startActivity(new Intent(FavoriteActivity.this, DetailsActivity.class)
                .putExtra("data", result));
    }
}