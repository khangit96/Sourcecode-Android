package com.demochart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private GraphicalView mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Chart();
    }

    //Tinh thu
    public int tthu(int sngay, int sthang, int snam) {
        if (sthang == 1) {
            sthang = 13;
            snam -= 1;
        }
        if (sthang == 2) {
            sthang = 14;
            snam -= 1;
        }
        int thu = ((sngay + 2 * sthang + (3 * (sthang + 1) / 5) + snam + (snam / 4) - (snam / 100) + (snam / 400) + 2) % 7);
        if (sthang == 13) {
            sthang = 1;
            snam += 1;
        }
        if (sthang == 14) {
            sthang = 2;
            snam += 1;
        }
        return thu;
    }

    //Bi?u ð? th?ng kê s? lít ný?c u?ng trong tu?n
    public void Chart() {
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
        Calendar calendar = Calendar.getInstance();
        int thu = tthu(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(20, 0));
        entries.add(new Entry(18, 1));
        entries.add(new Entry(29, 2));
        entries.add(new Entry(20, 3));
        entries.add(new Entry(17, 4));


        LineDataSet dataset = new LineDataSet(entries, "Nhiệt độ");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("13h");
        labels.add("14h");
        labels.add("15h");
        labels.add("16h");
        labels.add("17h");

        LineData data = new LineData(labels, dataset);
        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
     /*   dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setHighlightEnabled(true);*/
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.setBorderColor(getResources().getColor(R.color.colorAccent));
        //lineChart.animateY(5000);
    }

}
