package com.demomultiplescreenlayout;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;
import android.util.FloatMath;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        setContentView(R.layout.activity_main);
        // DisplayMetrics dm = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(dm);
      /*  int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);*/

  /*      double size = 0;
        try {

            // Compute screen size

            DisplayMetrics dm = MainActivity.this.getResources().getDisplayMetrics();

            float screenWidth  = dm.widthPixels / dm.xdpi;

            float screenHeight = dm.heightPixels / dm.ydpi;

            size = Math.sqrt(Math.pow(width, 2) +

                    Math.pow(height, 2));
            Toast.makeText(getApplicationContext(),""+size, Toast.LENGTH_LONG).show();

        } catch(Throwable t) {

        }*/
        DisplayMetrics dm = getResources().getDisplayMetrics();

        double density = dm.density * 160;
        double x = Math.pow(dm.widthPixels / density, 2);
        double y = Math.pow(dm.heightPixels / density, 2);
        double screenInches = Math.sqrt(x + y);
        Toast.makeText(getApplicationContext(), "" + screenInches, Toast.LENGTH_LONG).show();


        // tv.setText("The screen size is:"+FloatMath.sqrt(height*height+width*width));*/
      /*  DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int absoluteHeightInPx = displayMetrics.heightPixels;
        int absoluteWidthInPx = displayMetrics.widthPixels;
        double diagonalPixels = Math.sqrt((absoluteHeightInPx * absoluteHeightInPx) + (absoluteWidthInPx * absoluteWidthInPx));
        double diagonalInInches = diagonalPixels / displayMetrics.densityDpi;*/
     /*   DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float height = metrics.heightPixels / metrics.xdpi;
        float width = metrics.widthPixels / metrics.ydpi;*/
      /*  Display display1 = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display1.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
        Toast.makeText(getApplicationContext(), "" + dpWidth + "*" + dpHeight, Toast.LENGTH_LONG).show();*/
        Configuration config = getResources().getConfiguration();
        /*if (config.smallestScreenWidthDp >= 600)
        {
            setContentView(R.layout.main_activity_tablet);
        }
        else
        {
            setContentView(R.layout.main_activity);
        }*/
        //  Toast.makeText(getApplicationContext(), "" + config.smallestScreenWidthDp + "*" + config.screenHeightDp+"-"+config.screenLayout, Toast.LENGTH_LONG).show();
    }
}
