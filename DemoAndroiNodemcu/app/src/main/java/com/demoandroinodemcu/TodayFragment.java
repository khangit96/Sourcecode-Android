package com.demoandroinodemcu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import at.grabner.circleprogress.CircleProgressView;

import static com.demoandroinodemcu.LoginActivity.userSharePreferences;

/**
 * Created by Administrator on 2/21/2017.
 */

public class TodayFragment extends Fragment {
    View view;
    CircleProgressView circleProgressView;

    TextView tvDaUong, tvConLai, tvSoLit;
    Button btViewActivity, btRecommended;

    static String DAY;
    static boolean check = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_day, container, false);

        initControls();
        initEvents();

        putValueTodayFirebase();

        circleProgressView.setText(String.valueOf(0));
        circleProgressView.setValueAnimated(0);
        return view;
    }

    public void initControls() {

        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);

        tvDaUong = (TextView) view.findViewById(R.id.tvDaUong);
        tvConLai = (TextView) view.findViewById(R.id.tvConLai);
        tvSoLit = (TextView) view.findViewById(R.id.tvSoLit);

        btViewActivity = (Button) view.findViewById(R.id.btViewActivity);
        btRecommended = (Button) view.findViewById(R.id.btRecommended);
    }

    public void initEvents() {

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentDay = c.get(Calendar.DATE);

        int daySaveSharpreference = userSharePreferences.getInt("DAY", 0);
        int monthSaveSharpreference = userSharePreferences.getInt("MONTH", 0);

        if (currentDay == daySaveSharpreference && currentMonth == monthSaveSharpreference) {
            check = true;
        } else {
            check = false;
        }
        Toast.makeText(getActivity(), "" + check, Toast.LENGTH_LONG).show();
        putIntValueSharePreferences("DAY", currentDay);
        putIntValueSharePreferences("MONTH", currentMonth);

        DAY = String.valueOf(currentDay) + "-" + String.valueOf(currentMonth);

        btViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LichSuUongActivity.class));
            }
        });

        btRecommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void putValueTodayFirebase() {

        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        userSharePreferences = getActivity().getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
        final float soLit = userSharePreferences.getFloat("SO_LIT", 0);


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Config");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                float valueFirebase = Float.parseFloat(dataSnapshot.child("value").getValue().toString());
                float daUong = userSharePreferences.getFloat("DA_UONG", 0) + valueFirebase;
                float conLai = soLit - daUong;
                int phanTram = (int) ((daUong * 100) / soLit);

                circleProgressView.setText(String.valueOf(phanTram));
                circleProgressView.setValueAnimated(phanTram);

                putFloatValueSharePreferences("DA_UONG", daUong);

                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + DAY + "/TinhTrang");
                mDatabase1.setValue(new Today(soLit, daUong, conLai));


                if (pg.isShowing()) {
                    pg.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final DatabaseReference mDatabaseTinhTrang = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + DAY + "/TinhTrang");
        mDatabaseTinhTrang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TinhTrang tinhTrang = dataSnapshot.getValue(TinhTrang.class);
                if (tinhTrang != null) {
                    tvDaUong.setText(String.format("%.2f", tinhTrang.daUong) + "ML");
                    tvSoLit.setText(String.format("%.2f", tinhTrang.soLit) + "L");
                    tvConLai.setText(String.format("%.2f", tinhTrang.conLai) + "ML");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void putFloatValueSharePreferences(String key, float value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putFloat(key, value);

        editor.apply();
    }

    public void putIntValueSharePreferences(String key, int value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
