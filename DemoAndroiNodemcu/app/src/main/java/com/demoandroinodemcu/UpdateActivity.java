package com.demoandroinodemcu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import me.drozdzynski.library.steppers.OnCancelAction;
import me.drozdzynski.library.steppers.OnChangeStepAction;
import me.drozdzynski.library.steppers.OnFinishAction;
import me.drozdzynski.library.steppers.SteppersItem;
import me.drozdzynski.library.steppers.SteppersView;

public class UpdateActivity extends AppCompatActivity {
    public static SharedPreferences userSharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
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

        SteppersItem stepQRCODE = new SteppersItem();

        stepQRCODE.setLabel("QR CODE");
        stepQRCODE.setSubLabel("Vui lòng nhập mã bình nưỡc");
        stepQRCODE.setFragment(new QRCODEFragment());
        steps.add(stepQRCODE);


        SteppersView steppersView = (SteppersView) findViewById(R.id.steppersView);
        steppersView.setConfig(steppersViewConfig);
        steppersView.setItems(steps);

        steppersView.build();

        //ChieuCaoFragment.edChieuCao.setText("170");
    }

    public void CapNhat(View v) {
        if (!ChieuCaoFragment.edChieuCao.getText().toString().equals("")&&!CanNangFragment.edCangNang.getText().equals("")) {
            float chieuCao = Integer.parseInt(ChieuCaoFragment.edChieuCao.getText().toString());
            float canNang = Integer.parseInt(CanNangFragment.edCangNang.getText().toString());

            SharedPreferences userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userSharePreferences.edit();
            editor.putFloat("CHIEU_CAO", chieuCao);
            editor.putFloat("CAN_NANG", canNang);
            editor.putFloat("SO_LIT", (float) (canNang / 0.03));
            editor.apply();
        } else {
        }

    }
}
