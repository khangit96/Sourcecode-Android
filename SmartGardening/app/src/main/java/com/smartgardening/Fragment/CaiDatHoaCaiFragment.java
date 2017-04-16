package com.smartgardening.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.Activity.DetailActivity;
import com.smartgardening.Model.CaiDatHoaCai;
import com.smartgardening.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class CaiDatHoaCaiFragment extends Fragment {
    View v;
    TextView tvTenHeThong, tvHenGioTuoi, tvThoiGianCapNhatDuLieu, tvThoiGianTuoi, tvDoAmDatThapNhat;
    Button btTenHeThong, btHenGioTuoi, btThoiGianCapNhat, btThoiGianTuoi, btDoAmDatThapNhat;
    static int check = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cai_dat_hoa_cai, container, false);

        khoiTao();
        return v;
    }

    public void khoiTao() {
        final ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("1/CaiDat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CaiDatHoaCai caiDatHoaCai = dataSnapshot.getValue(CaiDatHoaCai.class);

                tvHenGioTuoi = (TextView) v.findViewById(R.id.tvHenGioTuoi);
                tvThoiGianTuoi = (TextView) v.findViewById(R.id.tvThoiGianTuoi);
                tvThoiGianCapNhatDuLieu = (TextView) v.findViewById(R.id.tvThoiGianCapNhatDuLieu);
                tvDoAmDatThapNhat = (TextView) v.findViewById(R.id.tvDoAmDatThapNhat);

                tvHenGioTuoi.setText(caiDatHoaCai.henGioTuoi);
                tvThoiGianTuoi.setText(String.valueOf(caiDatHoaCai.thoiGianTuoi) + " giây");
                tvThoiGianCapNhatDuLieu.setText(String.valueOf(caiDatHoaCai.thoiGianCapNhatDuLieu) + " phút");
                tvDoAmDatThapNhat.setText(String.valueOf(caiDatHoaCai.doAmDatThapNhat) + "%");

                pg.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tvTenHeThong = (TextView) v.findViewById(R.id.tvTenHeThong);
        tvTenHeThong.setText(DetailActivity.item.tenHeThong);

        btTenHeThong = (Button) v.findViewById(R.id.btTenHeThong);
        btTenHeThong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNhapTenHeThong();
            }
        });

        btHenGioTuoi = (Button) v.findViewById(R.id.btHenGioTuoi);
        btHenGioTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                final ProgressDialog pg = new ProgressDialog(getActivity());
                                pg.setCanceledOnTouchOutside(false);
                                pg.setMessage("Đang cài đặt hẹn giờ tưới");
                                pg.show();

                                SharedPreferences preferences = getActivity().getSharedPreferences("KEY", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("KEY","1");
                                editor.apply();

                                String thoiGianTuoi = hourOfDay + ":" + minute;
                                tvHenGioTuoi.setText(hourOfDay + ":" + minute);

                                DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        pg.dismiss();
                                        Toast.makeText(getActivity(), "Cài đặt hẹn giờ tưới thành công!", Toast.LENGTH_LONG).show();
                                    }
                                };

                                DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                                mData.child("1/CaiDat/henGioTuoi").setValue(thoiGianTuoi, listener);

                            }
                        })
                        .setStartTime(10, 10)
                        .setDoneText("Đồng ý")
                        .setCancelText("Huỷ");
                rtpd.show(getActivity().getSupportFragmentManager(), "sds");
            }
        });

        btThoiGianCapNhat = (Button) v.findViewById(R.id.btThoiGianCapNhat);
        btThoiGianCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                dialogNhapThongTinSo("Thời gian cập nhật dữ liệu");
            }
        });

        btThoiGianTuoi = (Button) v.findViewById(R.id.btThoiGianTuoi);
        btThoiGianTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 2;
                dialogNhapThongTinSo("Thời gian tưới");
            }
        });

        btDoAmDatThapNhat = (Button) v.findViewById(R.id.btDoAmDatThapNhat);
        btDoAmDatThapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 3;
                dialogNhapThongTinSo("Độ ẩm đất thấp nhất");
            }
        });


    }

    public void dialogNhapThongTinSo(String tieuDe) {
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

        final EditText inputReview = new EditText(getActivity());
        inputReview.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inputReview.setHeight(100);
        inputReview.setInputType(InputType.TYPE_CLASS_NUMBER);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(tieuDe);
        builder.setView(inputReview);

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!inputReview.getText().toString().equals("")) {
                    final ProgressDialog pg = new ProgressDialog(getActivity());
                    pg.setCanceledOnTouchOutside(false);

                    if (check == 1) {
                        pg.setMessage("Đang cài đặt thời gian cập nhật dữ liệu...");
                        pg.show();

                        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                pg.dismiss();
                                tvThoiGianCapNhatDuLieu.setText(inputReview.getText().toString() + " phút");
                                Toast.makeText(getActivity(), "Cài đặt thời gian cập nhật dữ liệu thành công!", Toast.LENGTH_LONG).show();
                            }
                        };
                        mData.child("1/CaiDat/thoiGianCapNhatDuLieu").setValue(Integer.parseInt(inputReview.getText().toString()), listener);
                    } else if (check == 2) {
                        //thời gian tưới
                        pg.setMessage("Đang cài đặt thời gian tưới...");
                        pg.show();

                        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                pg.dismiss();
                                tvThoiGianTuoi.setText(inputReview.getText().toString() + " giây");
                                Toast.makeText(getActivity(), "Cài đặt thời gian tưới thành công!", Toast.LENGTH_LONG).show();
                            }
                        };
                        mData.child("1/CaiDat/thoiGianTuoi").setValue(Integer.parseInt(inputReview.getText().toString()), listener);


                    } else {
                        //độ ẩm đất thấp nhất
                        pg.setMessage("Đang cài đặt độ ẩm đất thấp nhất...");
                        pg.show();

                        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                pg.dismiss();
                                tvDoAmDatThapNhat.setText(inputReview.getText().toString() + "%");
                                Toast.makeText(getActivity(), "Cài đặt độ ẩm đất thấp nhất thành công!", Toast.LENGTH_LONG).show();
                            }
                        };
                        mData.child("1/CaiDat/doAmDatThapNhat").setValue(Integer.parseInt(inputReview.getText().toString()), listener);
                    }
                } else {
                    Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu hợp lệ!", Toast.LENGTH_LONG).show();
                }

            }
        });
        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }

    public void dialogNhapTenHeThong() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final View inflate = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_nhap_thongtin, null, false);
        builder.setView(inflate);

        final EditText edThongTin = (EditText) inflate.findViewById(R.id.edThongTin);
        edThongTin.setText(DetailActivity.item.tenHeThong);
        edThongTin.setInputType(T);

        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!edThongTin.getText().toString().equals("")) {
                    final ProgressDialog pg = new ProgressDialog(getActivity());
                    pg.setMessage("Đang cập nhật tên hệ thống... ");
                    pg.setCanceledOnTouchOutside(false);
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pg.dismiss();
                            tvTenHeThong.setText(edThongTin.getText().toString());
                            Toast.makeText(getContext(), "Thay đổi tên thành công", Toast.LENGTH_LONG).show();
                        }
                    };
                    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
                    mData.child("1/ThongTinHeThong/tenHeThong").setValue(edThongTin.getText().toString(), listener);
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập tên hợp lệ!", Toast.LENGTH_LONG).show();
                }

            }
        });

        builder.show();
    }

}
