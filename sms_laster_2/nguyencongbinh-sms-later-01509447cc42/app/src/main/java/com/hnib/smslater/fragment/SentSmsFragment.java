package com.hnib.smslater.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.hnib.smslater.R;
import com.hnib.smslater.activity.MainActivity;
import com.hnib.smslater.adapter.SmsAdapter;
import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.utils.AppConstants;
import com.hnib.smslater.utils.ItemClickSupport;
import com.hnib.smslater.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SentSmsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SmsAdapter adapter;
    private List<SmsPojo> smsPojos;
    private BroadcastReceiver mRefreshReceiver;
    private TextView tvNoMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("binh SentSmsFragment", "onCreate");
        super.onCreate(savedInstanceState);

    }

    /*public void fakeData() {

        SmsPojo sms1 = new SmsPojo();
        sms1.content = "I will be late. Sorry.";
        sms1.status = 1;
        sms1.date = "Fri 15-01-2016";
        sms1.time = "08:15";
        sms1.save();
        ContactPojo contactPojo = new ContactPojo();
        contactPojo.name = "vfa aNam";
        contactPojo.mobileNumber = "989898";
        contactPojo.smsPojo = sms1;

        ContactPojo contactPojo2 = new ContactPojo();
        contactPojo2.name = "vfa aTung";
        contactPojo2.mobileNumber = "989298";
        contactPojo2.smsPojo = sms1;
        contactPojo2.save();

        SmsPojo sms3 = new SmsPojo();
        sms3.content = "Time for join my wedding now, this message just remind you go ontime :)";
        sms3.status = 1;
        sms3.date = "Fri 15-01-2016";
        sms3.time = "11:50";
        sms3.save();

        ContactPojo contactPojo5 = new ContactPojo();
        contactPojo5.name = "Hai";
        contactPojo5.mobileNumber = "9833898";
        contactPojo5.smsPojo = sms3;

        ContactPojo contactPojo6 = new ContactPojo();
        contactPojo6.name = "Nam";
        contactPojo6.mobileNumber = "983298";
        contactPojo6.smsPojo = sms3;
        contactPojo6.save();

        SmsPojo sms2 = new SmsPojo();
        sms2.content = "Do not wake up me, plzzzzz";
        sms2.status = -1;
        sms2.date = "Thu 14-01-2016";
        sms2.time = "06:00";
        sms2.save();
        ContactPojo contactPojo3 = new ContactPojo();
        contactPojo3.name = "Me cu Kien";
        contactPojo3.mobileNumber = "98976768";
        contactPojo3.smsPojo = sms2;

        ContactPojo contactPojo4 = new ContactPojo();
        contactPojo4.name = "Minh ten";
        contactPojo4.mobileNumber = "9323898";
        contactPojo4.smsPojo = sms2;
        contactPojo4.save();

        smsPojos.add(sms1);
        smsPojos.add(sms2);
        smsPojos.add(sms3);
    }*/

    private void loadSentSmsFromDatabase() {
        Log.d("binh SentSmsFragment", "load SMS from database");
        smsPojos = new Select()
                .all()
                .from(SmsPojo.class)
                .where("status!=?", 0)
                .execute();
        if (smsPojos == null) {
            smsPojos = new ArrayList<>();
        }

        Collections.sort(smsPojos, new SmsPojo()); //sort by date&time
        adapter.setSmsPojos(smsPojos);
        adapter.notifyDataSetChanged();
        if (smsPojos == null || smsPojos.size() == 0) {
            tvNoMessage.setVisibility(View.VISIBLE);
        } else {
            tvNoMessage.setVisibility(View.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("binh SentSmsFragment", "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent_sms, container, false);
        tvNoMessage = (TextView) view.findViewById(R.id.tvNoMessage);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        LinearLayoutManager llm = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(llm);
        adapter = new SmsAdapter();
        adapter.setSmsPojos(smsPojos);
        recyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                SmsPojo smsPojo = smsPojos.get(position);
                long id = smsPojo.getId();
                Log.d("binh", "sms selected id: " + id);
                PreferenceUtils.saveToPreferences(mActivity, PreferenceUtils.KEY_SMS_SELECTED_ID, id);
                startFragment(R.id.root_frame2, new DetailSmsFragment());

            }
        });
        initAdmob(view);
        return view;
    }
    protected void initAdmob(View view) {
        final AdView adView = (AdView) view.findViewById(R.id.adView);


        if (adView != null) {



            if (AppConstants.isAdmobEnabledSentSmsScreen) {

                adView.setVisibility(View.VISIBLE);
                AdRequest.Builder builder = new AdRequest.Builder();
                AdRequest adRequest = builder.build();
                builder.addTestDevice("F36E25E3C328F38CB722C2DAD93ABA6B");
                builder.addTestDevice("E74FBD30CF340F99B6FD8BF2B2DD4356");


                // Start loading the ad in the background.
                adView.loadAd(adRequest);

            } else {
                adView.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("binh SentSmsFragment", "onResume");
        MainActivity.canExit = true;
        ((MainActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
        loadSentSmsFromDatabase();

        mRefreshReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Log.d("binh", "Receive broadcast from Service, SMS sent ");
                //update data for adapter
                loadSentSmsFromDatabase();

            }
        };

        IntentFilter mIntentFilter = new IntentFilter(AppConstants.ACTION_SMS_SENT);

        mActivity.registerReceiver(mRefreshReceiver, mIntentFilter);
        ((MainActivity) getActivity()).viewPager.setOnTouchListener(null);//enable swipe
        ((MainActivity) getActivity()).setupDefaultActionBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.unregisterReceiver(mRefreshReceiver);
    }
}
