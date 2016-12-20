package khangit96.tdmuteamfhome.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import khangit96.tdmuteamfhome.R;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
