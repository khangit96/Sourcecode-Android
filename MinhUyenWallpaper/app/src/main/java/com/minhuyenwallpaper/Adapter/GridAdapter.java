package com.minhuyenwallpaper.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.minhuyenwallpaper.Activity.DetailActivity;
import com.minhuyenwallpaper.Others.ItemClickListener;
import com.minhuyenwallpaper.R;
import com.minhuyenwallpaper.Model.Wallpaper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/2/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    ArrayList<Wallpaper> wallpapers;
    Activity activity;

    public GridAdapter(Activity activity, ArrayList<Wallpaper> wallpapers) {
        this.activity = activity;
        this.wallpapers = wallpapers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Glide.with(activity).load(wallpapers.get(i).wallpaperURL)
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.wallpaperImg);


        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                } else {
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("pos", position);
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView wallpaperImg;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            wallpaperImg = (ImageView) itemView.findViewById(R.id.wallpaperImg);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}