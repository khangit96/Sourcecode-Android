package com.demojsoup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.ArrayList;


/**
 * Created by Administrator on 2/2/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    ArrayList<String> urls;
    Activity activity;

    public GridAdapter(Activity activity, ArrayList<String> urls) {
        this.activity = activity;
        this.urls = urls;
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
        Glide.with(activity).load(urls.get(i))
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.wallpaperImg);


    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView wallpaperImg;


        public ViewHolder(View itemView) {
            super(itemView);
            wallpaperImg = (ImageView) itemView.findViewById(R.id.wallpaperImg);
        }
    }

}
