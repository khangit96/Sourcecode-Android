package khangit96.demopanterdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.eminayar.panter.DialogType;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.interfaces.OnTextInputConfirmListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDialog();
    }

    public void showDialog() {
        new PanterDialog(this)
                .setHeaderBackground(R.drawable.pattern_bg_orange)
                //.setHeaderLogo(R.drawable.sample_logo)
                .setTitle("Thêm nhận xét")
                .setDialogType(DialogType.INPUT)
                .setPositive("Thêm", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Thêm", Toast.LENGTH_LONG).show();
                    }
                })// You can pass also View.OnClickListener as second param
                .setNegative("Huỷ", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Huỷ", Toast.LENGTH_LONG).show();
                    }
                })
                .input("Nhập nội dung nhận xét vào đây", new OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String input) {
                        Toast.makeText(getApplicationContext(), input, Toast.LENGTH_LONG).show();
                    }
                })
                .isCancelable(false)
                .show();
    }
}
