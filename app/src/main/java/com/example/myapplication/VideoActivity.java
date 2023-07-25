package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Models.VideoData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class VideoActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    VideoAdapter videoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //Video
        viewPager2 = (ViewPager2) findViewById(R.id.vpager);

        FirebaseRecyclerOptions<VideoData> options =
                new FirebaseRecyclerOptions.Builder<VideoData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("videos"), VideoData.class)
                        .build();

        videoAdapter = new VideoAdapter(options);
        viewPager2.setAdapter(videoAdapter);

        //Bottom bar
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.video);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else if (itemId == R.id.video) {
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

    @Override
    protected void onStart() {
        super.onStart();
        videoAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        videoAdapter.stopListening();
    }
}