package com.demoandroinodemcu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnChangeStepAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

import static com.demoandroinodemcu.ThongTinChung.TEN_DANG_NHAP;


public class ThongTinActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin);

        SteppersView.Config steppersViewConfig = new SteppersView.Config();
        steppersViewConfig.setOnFinishAction(new OnFinishAction() {
            @Override
            public void onFinish() {
                // Action on last step Finish button
            }
        });

        steppersViewConfig.setOnCancelAction(new OnCancelAction() {
            @Override
            public void onCancel() {
                // Action when click cancel on one of steps
            }
        });

        steppersViewConfig.setOnChangeStepAction(new OnChangeStepAction() {
            @Override
            public void onChangeStep(int position, SteppersItem activeStep) {
                // Action when click continue on each step
            }
        });

        steppersViewConfig.setFragmentManager(getSupportFragmentManager());

        ArrayList<SteppersItem> steps = new ArrayList<>();

        SteppersItem stepChieuCao = new SteppersItem();

        stepChieuCao.setLabel("Chiều cao");
        stepChieuCao.setSubLabel("Vui lòng nhập chiều cao");
        stepChieuCao.setFragment(new ChieuCaoFragment());
        steps.add(stepChieuCao);

        SteppersItem stepCanNang = new SteppersItem();

        stepCanNang.setLabel("Cân nặng");
        stepCanNang.setSubLabel("Vui lòng nhập cân nặng");
        stepCanNang.setFragment(new CanNangFragment());
        steps.add(stepCanNang);


        SteppersView steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);
        steppersView.build();

    }

    public void HoanTat(View v) {
        float chieuCao = Integer.parseInt(ChieuCaoFragment.edChieuCao.getText().toString());
        float canNang = Integer.parseInt(CanNangFragment.edCangNang.getText().toString());

        ThongTinChung thongTinChung = new ThongTinChung(canNang, chieuCao, ThongTinChung.MAT_KHAU,(float) ((float) canNang / 0.03), ThongTinChung.TEN_DANG_NHAP);
        ThongTinChung.CHIEU_CAO = thongTinChung.chieuCao;
        ThongTinChung.CAN_NANG = thongTinChung.canNang;
        ThongTinChung.SO_LIT = thongTinChung.soLit;

        SharedPreferences userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putFloat("CHIEU_CAO", thongTinChung.chieuCao);
        editor.putFloat("CAN_NANG", thongTinChung.canNang);
        editor.putFloat("SO_LIT", thongTinChung.soLit);
        editor.apply();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" + ThongTinChung.KEY + "/ThongTinChung");
        mDatabase.setValue(thongTinChung);
    }



}
