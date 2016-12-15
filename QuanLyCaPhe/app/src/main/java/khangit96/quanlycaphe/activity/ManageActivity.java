package khangit96.quanlycaphe.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.databinding.ActivityManageBinding;

public class ManageActivity extends AppCompatActivity {
    ActivityManageBinding binding;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Quản Lý Đặt Hàng");

        addEvents();

    }

    private void addEvents() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
