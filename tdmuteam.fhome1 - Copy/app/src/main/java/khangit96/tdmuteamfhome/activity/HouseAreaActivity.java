package khangit96.tdmuteamfhome.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
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

        distanceList = new ArrayList<>();
        distanceList.add("1.5 Km");
        distanceList.add("1.9 Km");
        distanceList.add("2.0 Km");
        distanceList.add("2.4 Km");
        distanceList.add("2.5 Km");
        distanceList.add("3.0 Km");
        distanceList.add("3.7 Km");
        distanceList.add("4.5 Km");
        distanceList.add("4.8 Km");
        distanceList.add("6.5 Km");
        distanceList.add("6.7 Km");
        distanceList.add("6.9 Km");
        distanceList.add("7.0 Km");
        distanceList.add("7.3 Km");
        distanceList.add("7.5 Km");
        distanceList.add("7.9 Km");
        distanceList.add("8.5 Km");
        distanceList.add("9.0 Km");
        distanceList.add("9.4 Km");
        distanceList.add("9.9 Km");
        distanceList.add("10.5 Km");
        distanceList.add("10.7 Km");
        distanceList.add("11.5 Km");

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HouseAreaActivity.this);
        recyclerview.setLayoutManager(layoutManager);

        adapter = new CustomListHouseArea(getApplicationContext(), MainActivity.houseList, imgList, distanceList);
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
