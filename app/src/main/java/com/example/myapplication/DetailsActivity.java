package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Models.NewsHeadLines;
import com.example.myapplication.Models.Result;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    Result results;
    TextView text_title, text_author, text_time, text_detail, text_content;
    ImageView img_news;
    WebView webView;
    ProgressBar loader;
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


        headlines = (NewsHeadLines) getIntent().getSerializableExtra("data");

        text_title.setText(headlines.getTitle());
        text_author.setText(headlines.getAuthor());
        text_time.setText(headlines.getPublishedAt());
        text_detail.setText(headlines.getDescription());
        text_content.setText(headlines.getContent());
        text_content.setMovementMethod(new ScrollingMovementMethod());
        Picasso.get().load(headlines.getUrlToImage()).into(img_news);
        System.out.println(text_content);

        results = (Result) getIntent().getSerializableExtra("data");

        text_title.setText(results.getTitle());
        text_author.setText(results.getSource_id());
        text_time.setText(results.getPubDate());
        text_detail.setText(results.getDescription());
        Picasso.get().load(results.getImage_url()).into(img_news);
        text_content.setText(results.getContent());

    }
}