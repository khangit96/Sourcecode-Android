package com.smartgardening.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.R;

public class ThongTinHoaCaiFragment extends Fragment {
    View v;
    TextView tvCamBien1, tvCamBien2, tvCamBien3, tvCamBienNhietDo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_thong_tin_hoa_cai, container, false);

        getDataHoaCaiFromFirebase();

        return v;
    }

    public void getDataHoaCaiFromFirebase() {
        tvCamBien1 = (TextView) v.findViewById(R.id.tvCamBien1);
        tvCamBien2 = (TextView) v.findViewById(R.id.tvCamBien2);
        tvCamBien3 = (TextView) v.findViewById(R.id.tvCamBien3);
        tvCamBienNhietDo = (TextView) v.findViewById(R.id.tvCamBienNhietDo);

        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("1/DuLieu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String doAm1 = dataSnapshot.child("DoAm1").getValue().toString();
                String doAm2 = dataSnapshot.child("DoAm2").getValue().toString();
                String doAm3 = dataSnapshot.child("DoAm3").getValue().toString();

                tvCamBien1.setText(chuyenDoiCamBienDoAm(doAm1));
                tvCamBien2.setText(chuyenDoiCamBienDoAm(doAm2));
                tvCamBien3.setText(chuyenDoiCamBienDoAm(doAm3));

                String nhietDo = dataSnapshot.child("NhietDo").getValue().toString();
              /*  String nhietDoNumber1 = nhietDo.substring(1, 2);
                String nhietDoNumber2 = nhietDo.substring(2, 3);
                String nhietDoNumber3 = nhietDo.substring(3, 4);*/

                tvCamBienNhietDo.setText(nhietDo + "°C");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String chuyenDoiCamBienDoAm(String doAm) {
        String doAm1Number2 = doAm.substring(2, 3);
        String doAm1Number3 = doAm.substring(3, 4);

        return doAm1Number2 + doAm1Number3 + "%";
    }

}
