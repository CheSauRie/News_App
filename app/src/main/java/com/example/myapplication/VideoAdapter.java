package com.example.myapplication;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.VideoData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class VideoAdapter extends FirebaseRecyclerAdapter<VideoData, VideoAdapter.MyViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public VideoAdapter(@NonNull FirebaseRecyclerOptions<VideoData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull VideoData model) {
        holder.setData(model);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_video_row, parent, false);
        return  new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView title, desc;
        ProgressBar pbar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = (VideoView) itemView.findViewById(R.id.videoView);
            title = (TextView) itemView.findViewById(R.id.textVideoTitle);
            desc = (TextView) itemView.findViewById(R.id.textVideoDescription);
            pbar = (ProgressBar) itemView.findViewById(R.id.videoProgressBar);
        }

        void setData(VideoData videoData) {
            videoView.setVideoPath(videoData.getUrl());
            title.setText(videoData.getTitle());
            desc.setText(videoData.getDesc());
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pbar.setVisibility(View.GONE);
                    mediaPlayer.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
        }
    }
}
