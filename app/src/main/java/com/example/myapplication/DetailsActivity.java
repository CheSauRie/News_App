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

import com.example.myapplication.Models.CrawlNewsData;
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
    boolean isTtsUrlReady = false;

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

        if (results.getImage_url() != null) {
            String a = results.getImage_url();
            if (a.contains("http://") || a.contains("https://")) {
                // Log.d("CustomAdapter", "onBindViewHolder: " + a);
                Picasso.get()
                        .load(a)
                        .placeholder(R.drawable.un_available)
                        .error(R.drawable.un_available)
                        .into(img_news);
            } else {
                String b = "http:" + a;
                Picasso.get().load(b).into(img_news);
            }
        } else if (results.getImage_url() == null) {
            callApiImg(results.getLink());
        }

        text_content.setText(results.getContent());
        String contentTts = results.getContent();
        ttsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DetailsActivity", "onClick: " + ttsUrl);
                if (isTtsUrlReady) {
                    try {
                        mediaPlayer.setDataSource(ttsUrl);
                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                Log.d("DetailsActivity", "onPrepared: " + "Dã chuẩn bị xong");
                                mediaPlayer.start();
                            }
                        });
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        callApiTts(contentTts);

    }

    private void callApiImg(String url) {
        ApiService.retrofit_crawl_news.crawlNewsData(url)
                .enqueue(new Callback<CrawlNewsData>() {
                    @Override
                    public void onResponse(Call<CrawlNewsData> call, Response<CrawlNewsData> response) {
                        Log.d("CustomAdapter", "onBindViewHolder: " + "Dda vao day vao on Respone");
                        if (response.isSuccessful()) {
                            CrawlNewsData crawlNewsData = response.body();
                            if (crawlNewsData != null) {
                                if (response.body().getImageUrl().get(0) != null) {
                                    if (response.body().getImageUrl().get(0).contains("http://") || response.body().getImageUrl().get(0).contains("https://")) {
                                        Picasso.get()
                                                .load(response.body().getImageUrl().get(0))
                                                .placeholder(R.drawable.un_available)
                                                .error(R.drawable.un_available)
                                                .into(img_news);
                                    } else {
                                        String b = "http:" + response.body().getImageUrl().get(0);
                                        Picasso.get().load(b).into(img_news);
                                    }
                                }
                                Log.d("CustomAdapter", "onResponse: " + response.body().toString());
                            } else {
                                Log.d("CustomAdapter", "Response body is null");
                            }
                        } else {
                            Log.d("DetailsActivity", "Response not successful: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CrawlNewsData> call, Throwable t) {
                        Log.e("CustomAdapter", "API call failed: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
    }

    private void callApiTts(String contentTTS) {
        ApiService.retrofit_tts.postTextToSpeech(contentTTS, "btyG4uVZk4ppdxpKx1zHVR1ms3Z4UTep", "banmai").enqueue(new Callback<TtsResponse>() {

            @Override
            public void onResponse(Call<TtsResponse> call, Response<TtsResponse> response) {
                if (response.isSuccessful()) {
                    TtsResponse ttsResponse = response.body();
                    if (ttsResponse != null) {
                        ttsUrl = response.body().getAsync();
                        isTtsUrlReady = true;

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
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isTtsUrlReady = false;
        }
    }
}