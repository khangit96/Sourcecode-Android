package com.smartgardening.Fragment;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartgardening.Activity.DetailActivity;
import com.smartgardening.R;

public class DieuKhienFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dieu_khien, container, false);
        if (DetailActivity.key.equals("2")) {
            TextView tvTuoiNuoc = (TextView) rootView.findViewById(R.id.tvTuoiNuoc);
            tvTuoiNuoc.setText("Phun sương hệ thống");
            rootView.findViewById(R.id.lnKeoMang).setVisibility(View.GONE);
        }
        initEvents();

        return rootView;
    }

    /*
    * Init Event
    * */
    public void initEvents() {

        //Event Watering
        final SwitchCompat switchWatering = (SwitchCompat) rootView.findViewById(R.id.switchWatering);
        switchWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (switchWatering.isChecked()) {
                    pg.setMessage("Đang bật máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/batMayBom");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt máy bơm" +
                            "...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/batMayBom");
                    mDatabase.setValue(false, listener);
                }
            }
        });

        //Event push
        final SwitchCompat switchPush = (SwitchCompat) rootView.findViewById(R.id.switchPush);
        switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (switchPush.isChecked()) {
                    pg.setMessage("Đang bật kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/keoMang");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/keoMang");
                    mDatabase.setValue(false, listener);
                }
            }
        });

        //Event enable led
        final SwitchCompat switchEnableLed = (SwitchCompat) rootView.findViewById(R.id.switchEnableLed);
        switchEnableLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (switchEnableLed.isChecked()) {
                    pg.setMessage("Đang bật đèn ...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/batDen");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(DetailActivity.key + "/DieuKhien/batDen");
                    mDatabase.setValue(false, listener);
                }
            }
        });
    }


}
