package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Models.LoteryResponse;
import com.example.myapplication.Models.NewsHeadLines;
import com.example.myapplication.Models.Result;
import com.example.myapplication.Models.TtsResponse;
import com.example.myapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    Result results;
    TextView text_title, text_author, text_time, text_detail, text_content;
    Button ttsButton;
    ImageView img_news;
    WebView webView;
    ProgressBar loader;
    MediaPlayer mediaPlayer = new MediaPlayer();
    String ttsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        text_title = findViewById(R.id.text_detail_title);
        text_author = findViewById(R.id.text_detail_author);
        text_time = findViewById(R.id.text_detail_time);
        text_detail = findViewById(R.id.text_detail_detail);
        text_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);
        ttsButton = findViewById(R.id.textToSpeech);

        results = (Result) getIntent().getSerializableExtra("data");

        text_title.setText(results.getTitle());
        text_author.setText(results.getSource_id());
        text_time.setText(results.getPubDate());
        text_detail.setText(results.getDescription());
        Picasso.get().load(results.getImage_url()).into(img_news);
        text_content.setText(results.getContent());
        String contentTts = results.getContent();
        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ttsUrl != null) {
                    try {
                        mediaPlayer.setDataSource(ttsUrl);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        callApiTts(contentTts);
    }

    private void callApiTts(String contentTTS) {
        ApiService.retrofit_tts.postTextToSpeech(contentTTS, "XQbk0C0RRS6RkaZTc6ejqrgMRszfCEfE", "banmai").enqueue(new Callback<TtsResponse>() {

            @Override
            public void onResponse(Call<TtsResponse> call, Response<TtsResponse> response) {
                if (response.isSuccessful()) {
                    TtsResponse ttsResponse = response.body();
                    if (ttsResponse != null) {
                        ttsUrl = response.body().getAsync();
                        //MediaPlayer mediaPlayer = new MediaPlayer();

                        Log.d("DetailsActivity", "onResponse: " + response.body().toString());
                    } else {
                        Log.d("DetailsActivity", "Response body is null");
                    }
                } else {
                    Log.d("DetailsActivity", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TtsResponse> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dừng và giải phóng MediaPlayer khi activity bị hủy bỏ
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}