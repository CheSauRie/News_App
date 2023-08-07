package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Models.NewsApiResponse;
import com.example.myapplication.Models.NewsHeadLines;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.myapplication.Models.NewsData;
import com.example.myapplication.Models.Result;


import java.util.List;


public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1, b2, b3, b4, b5, b6, b7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải tin tức..");
        dialog.show();
        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                dialog.setTitle("Đang tải tin tức");
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsData(listener, s, null, null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        b1 = findViewById(R.id.btn_1);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.btn_2);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.btn_3);
        b3.setOnClickListener(this);
        b4 = findViewById(R.id.btn_4);
        b4.setOnClickListener(this);
        b5 = findViewById(R.id.btn_5);
        b5.setOnClickListener(this);
        b6 = findViewById(R.id.btn_6);
        b6.setOnClickListener(this);
        b7 = findViewById(R.id.btn_7);
        b7.setOnClickListener(this);


        RequestManager manager = new RequestManager(this);
        manager.getNewsData(listener, null, null, "vnexpress,danviet,nhipsongkinhdoanh,datviet,docbao");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.video) {
                startActivity(new Intent(getApplicationContext(), VideoActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.favourite) {
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private final OnFetchDataListener<NewsData> listener = new OnFetchDataListener<NewsData>() {
        @Override
        public void onFetchData(List<Result> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }
            showNews(list);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {
            Log.d("err", "onError: " + message);
            Toast.makeText(MainActivity.this, "Đã có lỗi xảy ra", Toast.LENGTH_SHORT).show();
        }
    };

    //
    private void showNews(List<Result> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new CustomAdapter(this, list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(Result result) {
        startActivity(new Intent(MainActivity.this, DetailsActivity.class)
                .putExtra("data", result));
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String category_vi = button.getText().toString();
        String category = "";
        if (category_vi.equals("Kinh tế")) {
            category = "business";
        } else if (category_vi.equals("Giải trí")) {
            category = "entertainment";
        } else if (category_vi.equals("Sức khỏe")) {
            category = "health";
        } else if (category_vi.equals("Tin nóng")) {
            category = "top";
        } else if (category_vi.equals("Khoa học")) {
            category = "science";
        } else if (category_vi.equals("Thể thao")) {
            category = "sports";
        } else if (category_vi.equals("Công nghệ")) {
            category = "technology";
        }
        Log.d("MainActivity", "onClick: " + category + category_vi);
        dialog.setTitle("Đang tải tin tức");
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsData(listener, null, category, null);
    }
}