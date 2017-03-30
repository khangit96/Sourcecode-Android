package com.demoandroinodemcu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    static String DAY;
    int count = 0;
    static int countWater = 0;

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

    }

    public void initEvents() {

        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentDay = c.get(Calendar.DATE);

        DAY = String.valueOf(currentDay) + "-" + String.valueOf(currentMonth);

    }

    public void putValueTodayFirebase() {

        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        userSharePreferences = getActivity().getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Config");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final TextView tvCheck = (TextView) view.findViewById(R.id.tvCheck);
                final ImageView imgCheck = (ImageView) view.findViewById(R.id.imgCheck);

                count++;
                if (count > 1) {

                    final float soLit = userSharePreferences.getFloat("SO_LIT", 0);
                    float valueFirebase = Float.parseFloat(dataSnapshot.child("value").getValue().toString());
                    // float daUong = userSharePreferences.getFloat("DA_UONG", 0) + valueFirebase;
                    float daUong = valueFirebase;
                    float conLai = soLit - daUong;
                    if (conLai > 0) {

                        putFloatValueSharePreferences("DA_UONG", daUong);
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + DAY + "/TinhTrang");
                        mDatabase1.setValue(new Today(soLit, daUong, conLai));
                    } else {
                        countWater++;
                        if (userSharePreferences.getBoolean("THONG_BAO", false)) {
                            showNotification("Bạn đã uống dư " + String.valueOf(valueFirebase-soLit) + " ML.");
                        }
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + DAY + "/TinhTrang");
                        mDatabase1.setValue(new Today(soLit, soLit, (float) 0.00));
                    }


                } else {


                    final DatabaseReference mDatabaseTinhTrang = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + DAY + "/TinhTrang");
                    mDatabaseTinhTrang.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            TinhTrang tinhTrang = dataSnapshot.getValue(TinhTrang.class);

                            if (tinhTrang != null) {

                                int phanTram = (int) ((tinhTrang.daUong * 100) / tinhTrang.soLit);

                                if (phanTram < 10) {
                                    setColorCircleProgressView(R.color.level1);
                                } else if (phanTram >= 10 && phanTram <= 20) {
                                    setColorCircleProgressView(R.color.level2);

                                } else if (phanTram > 20 && phanTram <= 30) {
                                    setColorCircleProgressView(R.color.level3);

                                } else if (phanTram > 30 && phanTram <= 50) {
                                    setColorCircleProgressView(R.color.level4);

                                } else if (phanTram > 50 && phanTram <= 60) {
                                    setColorCircleProgressView(R.color.level5);

                                } else if (phanTram > 60 && phanTram <= 70) {
                                    setColorCircleProgressView(R.color.level6);

                                } else if (phanTram > 70 && phanTram <= 80) {
                                    setColorCircleProgressView(R.color.level7);

                                } else if (phanTram > 80 && phanTram <= 90) {
                                    setColorCircleProgressView(R.color.level8);

                                } else if (phanTram > 90 && phanTram <= 100) {
                                    setColorCircleProgressView(R.color.level9);
                                }
                                circleProgressView.setText(String.valueOf(phanTram));
                                circleProgressView.setValueAnimated(phanTram);

                                putFloatValueSharePreferences("DA_UONG", tinhTrang.daUong);
                                putFloatValueSharePreferences("SO_LIT", tinhTrang.soLit);

                                tvDaUong.setText(String.format("%.2f", tinhTrang.daUong) + "ML");
                                tvSoLit.setText(String.format("%.2f", tinhTrang.soLit) + "ML");
                                tvConLai.setText(String.format("%.2f", tinhTrang.conLai) + "ML");

                                if (tinhTrang.conLai > 0) {
                                    tvCheck.setText("Chưa hoàn thành");
                                    imgCheck.setImageResource(R.drawable.ic_not_check_24dp);
                                } else {
                                    tvCheck.setText("Đã hoàn thành");
                                    imgCheck.setImageResource(R.drawable.ic_check_circle_black_24dp);
                                }

                            } else {

                                tvCheck.setText("Chưa hoàn thành");
                                imgCheck.setImageResource(R.drawable.ic_not_check_24dp);
                                circleProgressView.setText(String.valueOf(0));
                                circleProgressView.setValueAnimated(0);

                                tvDaUong.setText(String.format("%.2f", 0.00) + "ML");
                                tvSoLit.setText(String.format("%.2f", userSharePreferences.getFloat("SO_LIT", 0)) + "ML");
                                tvConLai.setText(String.format("%.2f", 0.00) + "ML");
                                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Config/value");
                                mDatabase1.setValue(0);

                                putFloatValueSharePreferences("DA_UONG", 0);

                            }

                            if (pg.isShowing()) {
                                pg.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void setColorCircleProgressView(int color) {
        circleProgressView.setBarColor(getResources().getColor(color));
    }

    public void putFloatValueSharePreferences(String key, float value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putFloat(key, value);

        editor.apply();
    }

    /*
   * Show notification to user
   * */
    public void showNotification(String message) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getActivity(), 1, intent, 0);
        Notification mNotification = new Notification.Builder(getActivity())
                .setContentTitle("Cảnh báo!")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        notificationManager.notify(countWater, mNotification);
    }
}
