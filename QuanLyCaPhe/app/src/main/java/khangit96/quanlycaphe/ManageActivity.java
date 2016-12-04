package khangit96.quanlycaphe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.firebase.ui.FirebaseListAdapter;
public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        setTitle("Quản Lý Đặt Hàng");
    }
}
