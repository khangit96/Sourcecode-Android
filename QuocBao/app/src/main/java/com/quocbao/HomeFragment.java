package com.quocbao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {
    public static ArrayList<Team> teams;
    public static ArrayList<Team> teams1;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getDataTeamFromFirebase();
        return rootView;
    }

    private void initRecyclerViewTeam() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HomeAdapter(getActivity(), teams);
        mRecyclerView.setAdapter(mAdapter);
        registerForContextMenu(mRecyclerView);


    }

    public void getDataTeamFromFirebase() {
        teams = new ArrayList<>();
        teams1 = new ArrayList<>();
        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Loading...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Team");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Team team = dt.getValue(Team.class);
                    team.pos=Integer.parseInt(dt.getKey());
                    teams.add(team);
                    teams1.add(team);
                }
                Collections.sort(teams);
                Collections.reverse(teams);
                initRecyclerViewTeam();
                pg.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuEdit) {
            startActivity(new Intent(getActivity(), EditTeamActivity.class));
        }
        return super.onContextItemSelected(item);
    }
}
