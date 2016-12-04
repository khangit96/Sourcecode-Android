package khangit96.demolistviewletter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nhận xét");
        listView = (ListView) findViewById(R.id.list_item);
        setData();
        adapter = new ListViewAdapter(this, R.layout.item_listview, stringArrayList);
        listView.setAdapter(adapter);
    }

    private void setData() {
        stringArrayList = new ArrayList<>();
        stringArrayList.add("Quỳnh Trang");
        stringArrayList.add("Hoàng Biên");
        stringArrayList.add("Đức Tuấn");
        stringArrayList.add("Đặng Thành");
        stringArrayList.add("Xuân Lưu");
        stringArrayList.add("Phạm Thanh");
        stringArrayList.add("Kim Kiên");
        stringArrayList.add("Ngô Trang");
        stringArrayList.add("Thanh Ngân");
        stringArrayList.add("Nguyễn Dương");
        stringArrayList.add("Quốc Cường");
        stringArrayList.add("Trần Hà");
    }
}
