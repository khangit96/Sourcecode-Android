package com.smartgardening.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.Activity.DetailActivity;
import com.smartgardening.R;
import com.smartgardening.Model.ThongTinHeThong;
import com.smartgardening.Adapter.ThongTinHeThongAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class TongQuanFragment extends Fragment {

    View rootView;
    private ArrayList<ThongTinHeThong> thongTinHeThongList = new ArrayList<>();
    boolean isFabLedClick = false, isFabWatering = false, isFabPush = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tong_quan, container, false);

        initEvents();
        initGridView();

        return rootView;
    }

    /*
      * Init Event
      * */
    private void initEvents() {

        //Event led
        rootView.findViewById(R.id.fabLed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (isFabLedClick) {

                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);

                        }
                    };

                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/batDen");
                    mDatabase1.setValue(false, listener1);

                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/batDen");
                    mDatabase2.setValue(false, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabLed);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabLedClick = false;

                } else {
                    pg.setMessage("Đang bật đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                        }
                    };

                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/batDen");
                    mDatabase1.setValue(true, listener1);

                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/batDen");
                    mDatabase2.setValue(true, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabLed);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabLedClick = true;
                }

            }
        });

        //Event watering
        rootView.findViewById(R.id.fabWatering).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (isFabWatering) {

                    pg.setMessage("Đang tắt máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);

                        }
                    };

                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/batMayBom");
                    mDatabase1.setValue(false, listener1);
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/batMayBom");
                    mDatabase2.setValue(false, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabWatering);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabWatering = false;

                } else {
                    pg.setMessage("Đang bật máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);

                        }
                    };
                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/batMayBom");
                    mDatabase1.setValue(true, listener1);
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/batMayBom");
                    mDatabase2.setValue(true, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabWatering);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabWatering = true;
                }
            }
        });

        //Event push
        rootView.findViewById(R.id.fabPush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(getActivity());
                pg.setCanceledOnTouchOutside(false);

                if (isFabPush) {
                    pg.setMessage("Đang tắt kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);

                        }
                    };
                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/keoMang");
                    mDatabase1.setValue(false, listener1);
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/keoMang");
                    mDatabase2.setValue(false, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabPush);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabPush = false;

                } else {
                    pg.setMessage("Đang bật kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener1 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);

                        }
                    };
                    DatabaseReference.CompletionListener listener2 = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };

                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/keoMang");
                    mDatabase1.setValue(true, listener1);
                    DatabaseReference mDatabase2 = FirebaseDatabase.getInstance().getReference().child("2/DieuKhien/keoMang");
                    mDatabase2.setValue(true, listener2);

                    FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fabPush);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabPush = true;
                }
            }
        });
    }

    public void initGridView() {
        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final ThongTinHeThongAdapter thongTinHeThongAdapter = new ThongTinHeThongAdapter(getActivity(), thongTinHeThongList);
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
        gridView.setAdapter(thongTinHeThongAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity = new Intent(getActivity(), DetailActivity.class);
                activity.putExtra("SYSTEM", (Serializable) thongTinHeThongList.get(position));
                startActivity(activity);
            }
        });

        mData.child("/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (!dt.getKey().equals("DieuKhienChung")) {
                        DataSnapshot dt1 = dt.child("ThongTinHeThong");
                        ThongTinHeThong thongTinHeThong = dt1.getValue(ThongTinHeThong.class);
                        thongTinHeThongList.add(thongTinHeThong);
                        thongTinHeThongAdapter.notifyDataSetChanged();
                        pg.dismiss();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
