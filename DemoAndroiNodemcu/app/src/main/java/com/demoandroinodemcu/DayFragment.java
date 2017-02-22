package com.demoandroinodemcu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Created by Administrator on 2/21/2017.
 */

public class DayFragment extends Fragment {
    CircleProgressView circleProgressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int value = args.getInt("VALUE", 0);
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        circleProgressView.setText(String.valueOf(value));
        circleProgressView.setValueAnimated(value);
        return view;
    }
}
