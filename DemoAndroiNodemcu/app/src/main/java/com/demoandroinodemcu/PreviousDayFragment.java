package com.demoandroinodemcu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

                if (tinhTrang == null) {

                   /* float daUong = new Random().nextInt(500);
                    float soLit = userSharePreferences.getFloat("DA_UONG", 0);

                    int phanTram = (int) ((daUong * 100) / soLit);

                    circleProgressView.setText(String.valueOf(phanTram));
                    circleProgressView.setValueAnimated(phanTram);
                    tvDaUong.setText(String.format("%.2f", daUong) + "ML");
                    tvSoLit.setText(String.format("%.2f", soLit) + "ML");
                    tvConLai.setText(String.format("%.2f", soLit - daUong) + "ML");*/
                    view.findViewById(R.id.ln).setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Chưa có dữ liệu", Toast.LENGTH_LONG).show();

                } else {
                    TextView tvDay = (TextView) view.findViewById(R.id.tvDay);
                    tvDay.setText(args.getString("DAY", ""));

                    int phanTram = (int) ((tinhTrang.daUong * 100) / tinhTrang.soLit);

                    circleProgressView.setText(String.valueOf(phanTram));
                    circleProgressView.setValueAnimated(phanTram);

                    tvDaUong.setText(String.format("%.2f", tinhTrang.daUong) + "ML");
                    tvSoLit.setText(String.format("%.2f", tinhTrang.soLit) + "L");
                    tvConLai.setText(String.format("%.2f", tinhTrang.conLai) + "ML");
                }
                pg.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public void initControls() {

        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);

        tvDaUong = (TextView) view.findViewById(R.id.tvDaUong);
        tvConLai = (TextView) view.findViewById(R.id.tvConLai);
        tvSoLit = (TextView) view.findViewById(R.id.tvSoLit);
    }

}
