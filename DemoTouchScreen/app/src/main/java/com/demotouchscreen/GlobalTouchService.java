package com.demotouchscreen;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

/**
 * Created by Administrator on 5/3/2016.
 */
public class GlobalTouchService extends Service implements View.OnTouchListener {

    private String TAG = this.getClass().getSimpleName();
    // window manager
    private WindowManager mWindowManager;
    // linear layout will use to detect touch event
    private LinearLayout touchLayout;
    private LinearLayout mDummyView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // create linear layout
        touchLayout = new LinearLayout(this);
        // set layout width 30 px and height is equal to full screen
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        touchLayout.setLayoutParams(lp);
        // set color if you want layout visible on screen
//		touchLayout.setBackgroundColor(Color.CYAN);
        // set on touch listener
        touchLayout.setOnTouchListener(this);

        // fetch window manager object
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // set layout parameter of window manager
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, // width of layout 30 px
                WindowManager.LayoutParams.MATCH_PARENT, // height is equal to full screen
                WindowManager.LayoutParams.TYPE_PHONE, // Type Ohone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                 // this window won't ever get key input focus
                PixelFormat.TRANSLUCENT);
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i(TAG, "add View");
/*
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);*/
        mWindowManager.addView(touchLayout, mParams);
        /*mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mDummyView = new LinearLayout(this);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, // width of layout 30 px
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT
        );
        params.gravity = Gravity.LEFT | Gravity.TOP;
        mDummyView.setLayoutParams(params);
        mDummyView.setOnTouchListener(this);
        mWindowManager.addView(mDummyView, params);*/
    }

    @Override
    public void onDestroy() {
        if (mWindowManager != null) {
            if (touchLayout != null) mWindowManager.removeView(touchLayout);
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //  Log.d("test", "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
            Toast.makeText(getBaseContext(), "Down", Toast.LENGTH_LONG).show();
           // mWindowManager.removeView(touchLayout);
            touchLayout = new LinearLayout(this);
            // set layout width 30 px and height is equal to full screen
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            touchLayout.setLayoutParams(lp);
            // set color if you want layout visible on screen
//		touchLayout.setBackgroundColor(Color.CYAN);
            // set on touch listener
            touchLayout.setOnTouchListener(this);

            // fetch window manager object
            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            // set layout parameter of window manager
            WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                    1, // width of layout 30 px
                    1, // height is equal to full screen
                    WindowManager.LayoutParams.TYPE_PHONE, // Type Ohone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    // this window won't ever get key input focus
                    PixelFormat.TRANSLUCENT);
            mParams.gravity = Gravity.LEFT | Gravity.TOP;

        }
        else if(event.getAction()==MotionEvent.ACTION_UP){
            Toast.makeText(getBaseContext(), "Up", Toast.LENGTH_LONG).show();

        }

        return false;
    }

}
