package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        if(!results.get(position).getImage_url().contains("null"))
        {
            Picasso.get().load(results.get(position).getImage_url()).into(holder.img_headline);
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
