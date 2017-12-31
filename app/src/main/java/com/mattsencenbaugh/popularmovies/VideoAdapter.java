package com.mattsencenbaugh.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by msencenb on 12/27/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoAdapterViewHolder> {
    private List<Video> videos;
    final private VideoAdapterOnClickHandler mClickHandler;

    interface VideoAdapterOnClickHandler {
        void onVideoClicked(Video video);
    }

    VideoAdapter(VideoAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public VideoAdapter.VideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.video_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        return new VideoAdapter.VideoAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapterViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.updateForVideo(video);
    }

    @Override
    public int getItemCount() {
        if (videos == null) return 0;

        return videos.size();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    class VideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mNameTextView;
        final TextView mSiteTextView;
        final TextView mTypeTextView;

        public VideoAdapterViewHolder(View view) {
            super(view);
            mNameTextView = view.findViewById(R.id.tv_video_name);
            mSiteTextView = view.findViewById(R.id.tv_site);
            mTypeTextView = view.findViewById(R.id.tv_type);
            view.setOnClickListener(this);
        }

        public void updateForVideo(Video video) {
            mNameTextView.setText(video.getName());
            mSiteTextView.setText(video.getSite());
            mTypeTextView.setText(video.getType());
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            Video video = videos.get(index);
            mClickHandler.onVideoClicked(video);
        }
    }
}
