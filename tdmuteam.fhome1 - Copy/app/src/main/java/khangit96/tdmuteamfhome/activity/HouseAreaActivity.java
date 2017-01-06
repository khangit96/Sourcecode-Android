package khangit96.tdmuteamfhome.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.adapter.CustomListHouseArea;

public class HouseAreaActivity extends AppCompatActivity {
    ListView lvHouseArea;
    CustomListHouseArea adapter;
    List<String> houseAreaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_area);

        addControls();
    }

    private void addControls() {
        houseAreaList = new ArrayList<>();
        houseAreaList = new ArrayList<>();
        houseAreaList.add("Khang");
        houseAreaList.add("Khang");
        houseAreaList.add("Khang");
        houseAreaList.add("Khang");
        houseAreaList.add("Khang");
        lvHouseArea = (ListView) findViewById(R.id.lvHouseArea);
        adapter = new CustomListHouseArea(getApplicationContext(), R.layout.list_house_area, houseAreaList);
        lvHouseArea.setAdapter(adapter);
    }
}
