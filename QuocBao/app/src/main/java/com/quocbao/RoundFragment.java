package com.quocbao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class RoundFragment extends Fragment {
    public static ArrayList<Pair> pairs;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_round, container, false);
        initRecyclerViewTeam();
        getDataRoundFromFirebase();
        return rootView;
    }

    private void initRecyclerViewTeam() {
        pairs = new ArrayList<>();
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RoundAdapter(getActivity(), pairs);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getDataRoundFromFirebase() {
        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Loading...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Pair");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pair pair = dataSnapshot.getValue(Pair.class);
                pairs.add(pair);
                mAdapter.notifyDataSetChanged();
                pg.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
