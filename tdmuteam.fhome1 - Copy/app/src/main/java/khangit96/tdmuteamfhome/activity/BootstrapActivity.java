package khangit96.tdmuteamfhome.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import khangit96.tdmuteamfhome.R;

public class BootstrapActivity extends AppCompatActivity {
    private static boolean SPLASH_LOADED = false;
    private ProgressBar processBar;
    private TextView status_mesg;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  Kiểm tra trạng thái hiển thị Splash
        //  Nếu Chưa
        if (!SPLASH_LOADED) {
            setContentView(R.layout.activity_bootstrap);
            onInit();
        } else { // Nếu rồi
            // Khởi chạy MainActivity
            Intent i = new Intent(BootstrapActivity.this, MainActivity.class);
            // Đặt cờ không cho phép user quay lại màn hình này
            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(i);
            // Đóng màn hình này
            finish();
        }

    }

    public void onInit() {

        sharedPref = PreferenceManager.getDefaultSharedPreferences(BootstrapActivity.this);
        processBar = (ProgressBar) findViewById(R.id.bootstrap_progressbar);
        status_mesg = (TextView) findViewById(R.id.bootstrap_status_message);

        // Kiểm tra đây có phải lần chạy đầu tiên không
        // Nếu đây là lần khởi động đầu tiên
        if (sharedPref.getBoolean("firstboot", true)) {
            // Hiển thị thanh trạng thái
            processBar.setVisibility(View.VISIBLE);
            processBar.setIndeterminate(true);
            // Hiển thị câu chào
            status_mesg.setText(R.string.splash_initializing);

            new KhoiTaoCSDL(BootstrapActivity.this).execute();
        } else { // Nếu đây không phải là lần khởi động đầu tiên
            // Kiểm tra hiển thị màn hình Splash
            if (!SPLASH_LOADED) {
                status_mesg.setText(R.string.splash_status_welcome_back);
                //status_mesg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        // Khởi chạy MainActivity
                        Intent intent = new Intent(BootstrapActivity.this, MainActivity.class);
                        startActivity(intent);
                        // Đóng màn hình này
                        finish();
                        // Đặt cờ là đã hiển thị Splash
                        SPLASH_LOADED = true;
                    }
                }, 1000);
            } else {
                // Khởi chạy MainActivity
                Intent intent = new Intent(BootstrapActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                // Đóng màn hình này
                finish();
            }
        }
    }

    private class KhoiTaoCSDL extends AsyncTask<Void, Integer, Void> {
        BootstrapActivity activity;
        int count;

        public KhoiTaoCSDL(BootstrapActivity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            processBar.setVisibility(View.VISIBLE);
            processBar.setIndeterminate(false);
            processBar.setProgress(0);
            processBar.setMax(100);

            status_mesg.setText(R.string.splash_status_processing);
        }

        protected Void doInBackground(Void... voids) {
            count = 25;
            for (int i = 0; i < count; i++) {
                SystemClock.sleep(100);
                publishProgress(i);
            }
            //
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            processBar.setMax(count);
            processBar.setProgress(values[0] + 1);
            status_mesg.setText(String.format(getString(R.string.splash_status_processing_with_number), values[0] + 1, count));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            processBar.setIndeterminate(true);
            status_mesg.setText(R.string.splash_status_welcome);

            // Đặt cờ là đã hiển thị Splash
            SPLASH_LOADED = true;

            // Đánh dấu là đã khởi tạo dữ liệu
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("firstboot", false);
            editor.putLong("firstboot_time", System.currentTimeMillis());
            editor.putLong("start_learn_time", System.currentTimeMillis());
            editor.apply();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Khởi chạy MainActivity
                    Intent intent = new Intent(BootstrapActivity.this, MainActivity.class);
                    activity.startActivity(intent);
                    // Đóng màn hình này
                    activity.finish();
                }
            }, 1000);
        }
    }
}