package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.CrawlNewsData;
import com.example.myapplication.Models.Result;
import com.example.myapplication.api.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Result> results;
    private SelectListener listener;

    public CustomAdapter(Context context, List<Result> results, SelectListener listener) {
        this.context = context;
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText((results.get(position).getTitle()));
        holder.text_source.setText(results.get(position).getSource_id());
        if (results.get(position).getImage_url() != null) {
            String a = results.get(position).getImage_url();
            if (a.contains("http://") || a.contains("https://")) {
                // Log.d("CustomAdapter", "onBindViewHolder: " + a);
                Picasso.get()
                        .load(a)
                        .placeholder(R.drawable.un_available)
                        .error(R.drawable.un_available)
                        .into(holder.img_headline);
            } else {
                String b = "http:" + a;
                Picasso.get().load(b).into(holder.img_headline);
            }
        } else if (results.get(position).getImage_url() == null) {
            ApiService.retrofit_backend.crawlNewsData(results.get(position).getLink())
                    .enqueue(new Callback<CrawlNewsData>() {
                        @Override
                        public void onResponse(Call<CrawlNewsData> call, Response<CrawlNewsData> response) {
                            Log.d("CustomAdapter", "onBindViewHolder: " + "Dda vao day vao on Respone");
                            if (!response.isSuccessful()) {
                                Log.d("DetailsActivity", "Response not successful: " + response.code());
                                return;
                            }
                            CrawlNewsData crawlNewsData = response.body();
                            if (crawlNewsData == null) {
                                Log.d("CustomAdapter", "Response body is null");
                                return;
                            }
                            if (response.body().getImageUrl().get(0) == null) {
                                String b = "http:" + response.body().getImageUrl().get(0);
                                Picasso.get().load(b).into(holder.img_headline);
                                return;
                            }
                            if (response.body().getImageUrl().get(0).contains("http://") || response.body().getImageUrl().get(0).contains("https://")) {
                                Picasso.get()
                                        .load(response.body().getImageUrl().get(0))
                                        .placeholder(R.drawable.un_available)
                                        .error(R.drawable.un_available)
                                        .into(holder.img_headline);
                            }
                            Log.d("CustomAdapter", "onResponse: " + response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<CrawlNewsData> call, Throwable t) {
                            Log.e("CustomAdapter", "API call failed: " + t.getMessage());
                            t.printStackTrace();
                        }
                    });
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(results.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
