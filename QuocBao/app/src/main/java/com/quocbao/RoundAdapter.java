package com.quocbao;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Administrator on 2/2/2017.
 */

public class RoundAdapter extends RecyclerView.Adapter<RoundAdapter.ViewHolder> {

    ArrayList<Pair> pairs;
    Activity activity;

    public RoundAdapter(Activity activity, ArrayList<Pair> pairs) {
        this.activity = activity;
        this.pairs = pairs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.round_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Team team1 = HomeFragment.teams1.get(pairs.get(i).team1 - 1);
        final Team team2 = HomeFragment.teams1.get(pairs.get(i).team2 - 1);

        Glide.with(activity).load(team1.logo)
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imgTeam1);

        Glide.with(activity).load(team2.logo)
                .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.ic_no_image)
                .placeholder(R.drawable.ic_no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imgTeam2);

        viewHolder.tvNameTeam1.setText(team1.name);
        viewHolder.tvNameTeam2.setText(team2.name);

        viewHolder.edResultTeam1.setText(pairs.get(i).resultTeam1 + "");
        viewHolder.edResultTeam2.setText(pairs.get(i).resultTeam2 + "");
        viewHolder.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair pair = pairs.get(i);
                pair.resultTeam1 = Integer.parseInt(viewHolder.edResultTeam1.getText().toString());
                pair.resultTeam2 = Integer.parseInt(viewHolder.edResultTeam2.getText().toString());

                final DatabaseReference mDatabasePair = FirebaseDatabase.getInstance().getReference().child("Pair/" + String.valueOf(i + 1));
                mDatabasePair.setValue(pair);
                Toast.makeText(activity, "Update succeed", Toast.LENGTH_LONG).show();
                Team team;
                if (pair.resultTeam1 > pair.resultTeam2) {

                    team = team1;
                    team.point += 3;
                    final DatabaseReference mDatabaseTeam = FirebaseDatabase.getInstance().getReference().child("Team/" + String.valueOf(pair.team1));
                    mDatabaseTeam.setValue(team);
                } else if (pair.resultTeam1 < pair.resultTeam2) {
                    team = team2;
                    team.point += 3;
                    final DatabaseReference mDatabaseTeam = FirebaseDatabase.getInstance().getReference().child("Team/" + String.valueOf(pair.team2));
                    mDatabaseTeam.setValue(team);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return pairs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgTeam1, imgTeam2;
        public TextView tvNameTeam1, tvNameTeam2;
        public EditText edResultTeam1, edResultTeam2;
        public Button btUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNameTeam1 = (TextView) itemView.findViewById(R.id.tvTeam1);
            tvNameTeam2 = (TextView) itemView.findViewById(R.id.tvTeam2);
            btUpdate = (Button) itemView.findViewById(R.id.btUpdate);

            imgTeam1 = (ImageView) itemView.findViewById(R.id.imgTeam1);
            imgTeam2 = (ImageView) itemView.findViewById(R.id.imgTeam2);

            edResultTeam1 = (EditText) itemView.findViewById(R.id.edResultTeam1);
            edResultTeam2 = (EditText) itemView.findViewById(R.id.edResultTeam2);
        }
    }

}