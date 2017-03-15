package com.quocbao;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/2/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    ArrayList<Team> teams;
    Activity activity;

    public HomeAdapter(Activity activity, ArrayList<Team> teams) {
        this.activity = activity;
        this.teams = teams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Glide.with(activity).load(teams.get(i).logo)
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.logo);

        viewHolder.tvName.setText(teams.get(i).name);
        viewHolder.tvRank.setText(String.valueOf(i + 1));
        viewHolder.tvPoint.setText(teams.get(i).point + "");
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MainActivity.POS_SELECTED = i;
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView logo;
        public TextView tvName, tvRank, tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvRank = (TextView) itemView.findViewById(R.id.rank);
            tvPoint = (TextView) itemView.findViewById(R.id.point);
        }
    }

}