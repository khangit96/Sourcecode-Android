package com.demogridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> arrPicture;
    PictureAdapter pictureAdapter;
    GridView gv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv= (GridView) findViewById(R.id.gv);
        arrPicture=new ArrayList<>();
        arrPicture.add(R.drawable.pic1);
        arrPicture.add(R.drawable.pic2);
        arrPicture.add(R.drawable.pic3);
        arrPicture.add(R.drawable.pic4);
        arrPicture.add(R.drawable.pic5);
        pictureAdapter=new PictureAdapter(MainActivity.this,R.layout.item,arrPicture);
        gv.setAdapter(pictureAdapter);
    }
}
