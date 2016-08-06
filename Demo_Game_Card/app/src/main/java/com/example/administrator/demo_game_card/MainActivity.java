package com.example.administrator.demo_game_card;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageView img1,img2,img3;
    Button btShow,btPlayAgain;
    String n1,n2,n3;
    boolean check1=false;
    boolean check2=false;
    boolean check3=false;
    TextView tvScore;
    private String[]card_type={"co","chuon","bich","ro"};
    private Integer[]card_number={1,5,8,9,12,13};
  private  ArrayList<String>arrCard=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //.setAction("Action", null).show();
                Toast.makeText(MainActivity.this,"sdsd",Toast.LENGTH_LONG).show();
            }
        });*/
        init();
      /*  ArrayList<Integer> arr=new ArrayList<Integer>();
        arr.add(R.drawable.card1);
        arr.add(R.drawable.card2);
        arr.add(R.drawable.card3);*/



    }
    public void init(){
        img1=(ImageView)findViewById(R.id.img1);
        img2=(ImageView)findViewById(R.id.img2);
        img3=(ImageView)findViewById(R.id.img3);
        tvScore=(TextView)findViewById(R.id.tvScore);
        btShow=(Button)findViewById(R.id.btShow);
        btPlayAgain=(Button)findViewById(R.id.btPlayAgain);
    }

    public void Click(View v){
        Random r_card_type=new Random();
        Integer r_type= r_card_type.nextInt(card_type.length);
        Random r_card_number=new Random();
        Integer r_number=r_card_number.nextInt(card_number.length);
        String card_value=card_type[r_type]+"_"+card_number[r_number].toString();
        Integer id=getResources().getIdentifier(card_value, "drawable", getPackageName());
        while(arrCard.contains(card_value)){
            r_type= r_card_type.nextInt(card_type.length);
            r_number=r_card_number.nextInt(card_number.length);
            card_value=card_type[r_type]+"_"+card_number[r_number].toString();
             id=getResources().getIdentifier(card_value,"drawable",getPackageName());
        }
        arrCard.add(card_value);

     switch (v.getId()){
         case R.id.img1:
             n1=card_value;
             img1.setImageResource(id);
             img1.setClickable(false);
             check1=true;
             break;
         case R.id.img2:
             n2=card_value;
             img2.setImageResource(id);
             img2.setClickable(false);
             check2=true;
             break;
         case R.id.img3:
             n3=card_value;
             img3.setImageResource(id);
             img3.setClickable(false);
             check3=true;
             break;
     }
    }
    public void Show(View v) {

        if(check1==true&&check2==true&&check3==true) {
            String[] card_fix_1 = n1.split("_");
            String[] card_fix_2 = n2.split("_");
            String[] card_fix_3 = n3.split("_");
            String s1 = null;
            String s2=null;
            String s3=null;
            for (String val : card_fix_1) {
                s1=val;
            }
            for (String val : card_fix_2) {
                s2=val;
            }
            for (String val : card_fix_3) {
                s3=val;
            }
            Integer s1_num=Integer.parseInt(s1);
            Integer s2_num=Integer.parseInt(s2);
            Integer s3_num=Integer.parseInt(s3);
            if(s1_num>=10){
                s1_num=0;
            }
            if(s2_num>=10) {
                s2_num = 0;
            }
            if(s3_num>=10){
                s3_num=0;
            }
            Integer result=s1_num+s2_num+s3_num;
            String result_fix=String.valueOf(result);
            if(result>=10){
                Integer le=result_fix.length();
                result_fix=result_fix.substring(le-1);
            }
            tvScore.setText("Score: "+result_fix);
        }
        else{
            tvScore.setText("Please open three card!");
        }
    }


    public void PlayAgain(View v){
        img1.setImageResource(R.drawable.macdinh);
        img2.setImageResource(R.drawable.macdinh);
        img3.setImageResource(R.drawable.macdinh);
        img1.setClickable(true);
        img2.setClickable(true);
        img3.setClickable(true);
        arrCard.removeAll(arrCard);
        check1=false;
        check2=false;
        check3=false;
        tvScore.setText("Score:");
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R .id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
