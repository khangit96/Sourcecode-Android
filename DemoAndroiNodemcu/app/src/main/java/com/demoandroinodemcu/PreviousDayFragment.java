package com.demoandroinodemcu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.grabner.circleprogress.CircleProgressView;


public class PreviousDayFragment extends Fragment {

    View view;
    CircleProgressView circleProgressView;
    TextView tvDaUong, tvConLai, tvSoLit;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_previous_day, container, false);
        initControls();

        final Bundle args = getArguments();
        String day = args.getString("DAY1", "");
        final SharedPreferences userSharePreferences = getActivity().getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);

        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + userSharePreferences.getString("KEY", "") + "/" + day + "/TinhTrang");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TinhTrang tinhTrang = dataSnapshot.getValue(TinhTrang.class);

                TextView tvCheck = (TextView) view.findViewById(R.id.tvCheck);
                ImageView imgCheck = (ImageView) view.findViewById(R.id.imgCheck);


                if (tinhTrang == null) {
                    view.findViewById(R.id.ln).setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Chưa có dữ liệu", Toast.LENGTH_LONG).show();

                } else {
                    TextView tvDay = (TextView) view.findViewById(R.id.tvDay);
                    tvDay.setText(args.getString("DAY", ""));

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

                    tvDaUong.setText(String.format("%.2f", tinhTrang.daUong) + "ML");
                    tvSoLit.setText(String.format("%.2f", tinhTrang.soLit) + "L");
                    tvConLai.setText(String.format("%.2f", tinhTrang.conLai) + "ML");

                    if (tinhTrang.conLai > 0) {

                        tvCheck.setText("Chưa hoàn thành");
                        imgCheck.setImageResource(R.drawable.ic_not_check_24dp);
                    } else {

                        tvCheck.setText("Đã hoàn thành");
                        imgCheck.setImageResource(R.drawable.ic_check_circle_black_24dp);
                    }
                }
                pg.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }

    public void setColorCircleProgressView(int color) {
        circleProgressView.setBarColor(getResources().getColor(color));
    }

    public void initControls() {

        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);

        tvDaUong = (TextView) view.findViewById(R.id.tvDaUong);
        tvConLai = (TextView) view.findViewById(R.id.tvConLai);
        tvSoLit = (TextView) view.findViewById(R.id.tvSoLit);
    }

}
