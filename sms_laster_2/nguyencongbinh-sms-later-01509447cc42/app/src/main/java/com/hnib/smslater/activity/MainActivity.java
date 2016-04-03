package com.hnib.smslater.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hnib.smslater.R;
import com.hnib.smslater.adapter.CustomFragmentPagerAdapter;
import com.hnib.smslater.fragment.RootListSmsFragment;
import com.hnib.smslater.fragment.RootSentSmsFragment;
import com.hnib.smslater.utils.AppConstants;

public class MainActivity extends BaseActivity {

    public Toolbar toolbar;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    public static int tabPosition;
    public static boolean canExit = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("binh", "MainActivity onCreate()");
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDefaultActionBar();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setupDefaultActionBar() {
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_home));

        //getSupportActionBar().setTitle("S
        // MS Later");
    }

    public void setupBackButtonActionBar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));

    }

    private void setupViewPager(ViewPager viewPager) {
        CustomFragmentPagerAdapter adapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RootListSmsFragment(), AppConstants.SMS_PENDING);
        adapter.addFragment(new RootSentSmsFragment(), AppConstants.SMS_SENT);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(tabPosition);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("binh", "MainActivity onResume()");

    }

    @Override
    public void onBackPressed() {
        if(canExit==false){
            super.onBackPressed();
        }else{
            showDialogConfirmExit();
        }

    }
}
