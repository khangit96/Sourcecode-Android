package com.smartgardening.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.smartgardening.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DuLieuFragment extends Fragment {
    View v;
    TextView tvDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_du_lieu, container, false);

        initEvents();
        chartNhiettDo();
        chartDoAm();

        return v;
    }

    private void initEvents() {
        tvDay = (TextView) v.findViewById(R.id.tvDay);
        v.findViewById(R.id.fabCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                String day = String.valueOf(dayOfMonth) + " / " + String.valueOf(monthOfYear + 1) + " / " + String.valueOf(year);
                                Calendar c = Calendar.getInstance();
                                int currentMonth = c.get(Calendar.MONTH) + 1;
                                int currentDay = c.get(Calendar.DATE);

                                tvDay.setText(day);

                                if (dayOfMonth > currentDay) {

                                    if (monthOfYear + 1 >= currentMonth) {
                                        v.findViewById(R.id.tvData).setVisibility(View.VISIBLE);
                                        v.findViewById(R.id.ln).setVisibility(View.GONE);

                                    } else {
                                        v.findViewById(R.id.ln).setVisibility(View.VISIBLE);
                                        v.findViewById(R.id.tvData).setVisibility(View.GONE);
                                    }


                                } else if (dayOfMonth < currentDay) {

                                    if (monthOfYear + 1 <= currentMonth) {
                                        v.findViewById(R.id.tvData).setVisibility(View.GONE);
                                        v.findViewById(R.id.ln).setVisibility(View.VISIBLE);

                                    } else {
                                        v.findViewById(R.id.ln).setVisibility(View.GONE);
                                        v.findViewById(R.id.tvData).setVisibility(View.VISIBLE);
                                    }

                                } else if (dayOfMonth == currentDay && monthOfYear + 1 == currentMonth) {
                                    v.findViewById(R.id.ln).setVisibility(View.VISIBLE);
                                    v.findViewById(R.id.tvData).setVisibility(View.GONE);
                                    tvDay.setText("Hôm nay");
                                }

                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setDoneText("Đồng ý")
                        .setCancelText("Huỷ bỏ");
                cdp.show(getActivity().getSupportFragmentManager(), "lđ");
            }
        });
    }

    public void chartNhiettDo() {
        LineChart lineChart = (LineChart) v.findViewById(R.id.chartNhietDo);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(20, 0));
        entries.add(new Entry(18, 1));
        entries.add(new Entry(29, 2));
        entries.add(new Entry(20, 3));
        entries.add(new Entry(15, 4));
        entries.add(new Entry(10, 5));
        entries.add(new Entry(22, 6));
        entries.add(new Entry(12, 7));
        entries.add(new Entry(26, 8));
        entries.add(new Entry(19, 9));
        entries.add(new Entry(14, 10));
        entries.add(new Entry(10, 11));
        entries.add(new Entry(27, 12));
        entries.add(new Entry(39, 13));
        entries.add(new Entry(40, 14));
        entries.add(new Entry(20, 15));
        entries.add(new Entry(45, 16));
        entries.add(new Entry(32, 17));
        entries.add(new Entry(28, 18));
        entries.add(new Entry(55, 19));
        entries.add(new Entry(14, 20));
        entries.add(new Entry(30, 21));
        entries.add(new Entry(48, 22));
        entries.add(new Entry(14, 23));

        LineDataSet dataset = new LineDataSet(entries, "Nhiệt độ");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("13h");
        labels.add("14h");
        labels.add("15h");
        labels.add("16h");
        labels.add("17h");
        labels.add("18h");
        labels.add("19h");
        labels.add("20h");
        labels.add("21h");
        labels.add("22h");
        labels.add("23h");
        labels.add("0h");
        labels.add("1h");
        labels.add("2h");
        labels.add("3h");
        labels.add("4h");
        labels.add("5h");
        labels.add("6h");
        labels.add("7h");
        labels.add("8h");
        labels.add("9h");
        labels.add("10h");
        labels.add("11h");
        labels.add("12h");

        LineData data = new LineData(labels, dataset);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.setBorderColor(getResources().getColor(R.color.colorAccent));
    }

    public void chartDoAm() {
        LineChart lineChart = (LineChart) v.findViewById(R.id.chartDoAm);
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(20, 0));
        entries.add(new Entry(18, 1));
        entries.add(new Entry(29, 2));
        entries.add(new Entry(20, 3));
        entries.add(new Entry(15, 4));
        entries.add(new Entry(10, 5));
        entries.add(new Entry(22, 6));
        entries.add(new Entry(12, 7));
        entries.add(new Entry(26, 8));
        entries.add(new Entry(19, 9));
        entries.add(new Entry(14, 10));
        entries.add(new Entry(10, 11));
        entries.add(new Entry(27, 12));
        entries.add(new Entry(39, 13));
        entries.add(new Entry(40, 14));
        entries.add(new Entry(20, 15));
        entries.add(new Entry(45, 16));
        entries.add(new Entry(32, 17));
        entries.add(new Entry(28, 18));
        entries.add(new Entry(55, 19));
        entries.add(new Entry(14, 20));
        entries.add(new Entry(30, 21));
        entries.add(new Entry(48, 22));
        entries.add(new Entry(14, 23));


        LineDataSet dataset = new LineDataSet(entries, "Độ ẩm đất");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("13h");
        labels.add("14h");
        labels.add("15h");
        labels.add("16h");
        labels.add("17h");
        labels.add("18h");
        labels.add("19h");
        labels.add("20h");
        labels.add("21h");
        labels.add("22h");
        labels.add("23h");
        labels.add("0h");
        labels.add("1h");
        labels.add("2h");
        labels.add("3h");
        labels.add("4h");
        labels.add("5h");
        labels.add("6h");
        labels.add("7h");
        labels.add("8h");
        labels.add("9h");
        labels.add("10h");
        labels.add("11h");
        labels.add("12h");

        LineData data = new LineData(labels, dataset);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.setBorderColor(getResources().getColor(R.color.colorAccent));
    }


}
