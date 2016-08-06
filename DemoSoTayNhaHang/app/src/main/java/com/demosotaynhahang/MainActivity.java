package com.demosotaynhahang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.demosotaynhahang.model.FakeNhaHang;
import com.demosotaynhahang.model.NhaHang;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvNhaHang;
    ArrayList<NhaHang> dsNhaHang;
    ArrayAdapter<NhaHang> adapterNhaHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        lvNhaHang=(ListView)findViewById(R.id.lv);
        dsNhaHang= FakeNhaHang.layDanhSach();
        adapterNhaHang=new ArrayAdapter<NhaHang>(MainActivity.this,android.R.layout.simple_list_item_1,dsNhaHang);
        lvNhaHang.setAdapter(adapterNhaHang);
    }
    private void addEvents(){
        lvNhaHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NhaHang nhaHang=dsNhaHang.get(i);
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("NHAHANG",nhaHang);
                startActivity(intent);
            }
        });
    }

}

