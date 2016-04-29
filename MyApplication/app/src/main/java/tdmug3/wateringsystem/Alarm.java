package tdmug3.wateringsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by Administrator on 4/14/2016.
 */
public class Alarm extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btTime;
    SwitchCompat switchRepeat;
    String time = "";
    boolean checkRepeat = false;
    public static int t = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm);
        setTitle("Cài đặt");
        Init();
    }

    public void Init() {
        btTime = (Button) findViewById(R.id.btTime);
        switchRepeat = (SwitchCompat) findViewById(R.id.swichRepeat);
        switchRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkRepeat = true;
                } else {
                    checkRepeat = false;
                }
            }
        });
        if (switchRepeat.isChecked()) {
            checkRepeat = true;
        } else {
            checkRepeat = false;
        }
    }

    public void Time(View v) {
        time = "";

        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                (TimePickerDialog.OnTimeSetListener) Alarm.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.setAccentColor(Color.parseColor("#F26149"));
        timePickerDialog.show(getFragmentManager(), "Timepicker");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //   getMenuInflater().inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent iHome = new Intent(Alarm.this, MainActivity.class);
            if (checkRepeat == true) {
                iHome.putExtra("repeat", "r");
            } else {
                iHome.putExtra("repeat", "n");
            }
            if (time != "") {
                iHome.putExtra("time", time);
            }
            NavUtils.navigateUpTo(this, iHome);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if (String.valueOf(hourOfDay).length() == 1) {
            time += "0";
        }
        time += String.valueOf(hourOfDay);
        if (String.valueOf(minute).length() == 1) {
            time += "0";
        }
        time += (String.valueOf(minute));

    }
}
