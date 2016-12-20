package khangit96.demogirdviewadapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set grid view item

        Bitmap table = BitmapFactory.decodeResource(this.getResources(), R.drawable.table);

        gridArray.add(new Item(table,"Bàn 1"));
        gridArray.add(new Item(table,"Bàn 2"));
        gridArray.add(new Item(table,"Bàn 3"));
        gridArray.add(new Item(table,"Bàn 4"));
        gridArray.add(new Item(table,"Bàn 5"));
        gridArray.add(new Item(table,"Bàn 6"));
        gridArray.add(new Item(table,"Bàn 7"));
        gridArray.add(new Item(table,"Bàn 8"));
        gridArray.add(new Item(table,"Bàn 9"));


        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
    }
}
