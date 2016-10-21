package khangit96.demobottomsheetfab;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmail_style);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.gmail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.gmail_fab);

    }
}
