package khangit96.tdmuteamfhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.adapter.CustomListHouseArea;
import khangit96.tdmuteamfhome.model.RecyclerItemClickListener;

public class HouseAreaActivity extends AppCompatActivity {
    CustomListHouseArea adapter;
    private RecyclerView recyclerview;
    List<Integer> imgList;
    List<String> distanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_area);

        addControls();
        addEvents();
    }

    private void addControls() {
        imgList = new ArrayList<>();
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro2jpg);
        imgList.add(R.drawable.nhatro3);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);
        imgList.add(R.drawable.nhatro);

        Collections.sort(MainActivity.houseList);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HouseAreaActivity.this);
        recyclerview.setLayoutManager(layoutManager);

        adapter = new CustomListHouseArea(getApplicationContext(), MainActivity.houseList, imgList);
        recyclerview.setAdapter(adapter);

        //init Toolbar
        initToolbar();
    }

    private void addEvents() {
        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent mainIntent = new Intent(HouseAreaActivity.this, MainActivity.class);
                mainIntent.putExtra("HOUSE_SELECTED", position);
                setResult(RESULT_OK, mainIntent);
                finish();
            }

        }));
    }


    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
