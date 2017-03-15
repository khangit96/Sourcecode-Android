package com.demoandroinodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TomorrowDaykFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        String day = args.getString("DAY", "");

        View view = inflater.inflate(R.layout.fragment_tomorrow_dayk, container, false);
        TextView tvNoti = (TextView) view.findViewById(R.id.tvNoti);
        tvNoti.setText(day + "\n\n" + "Chưa có dữ liệu");

        return view;
    }

}
