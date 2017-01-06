package khangit96.quanlycaphe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.CustomGridViewAdapter;
import khangit96.quanlycaphe.model.Table;
import khangit96.quanlycaphe.service.NotiService;

public class ManageActivity extends AppCompatActivity {

    GridView gridView;
    CustomGridViewAdapter customGridAdapter;
    ArrayList<Table> tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
        addEvents();
    }

    /*
    *
    * */
    public void init() {
        gridView = (GridView) findViewById(R.id.gridView1);
        tableList = new ArrayList<>();
        for (int i = 1; i < MainActivity.tableList.size(); i++) {
            tableList.add(MainActivity.tableList.get(i));
        }
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, tableList);
        gridView.setAdapter(customGridAdapter);

    }

    /*
    *
    * */
    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent tableIntent = new Intent(ManageActivity.this, TableActivity.class);
                tableIntent.putExtra("TABLE_NUMBER", tableList.get(i).tableNumber);
                startActivity(tableIntent);
            }
        });

        startService(new Intent(ManageActivity.this, NotiService.class));
    }


    /*
    *
    * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}