package khangit96.demointerface3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DownloadListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Download download = new Download(getApplicationContext(), this);
        download.startDownload();
    }

    @Override
    public void onDownloadComplete(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }
}
