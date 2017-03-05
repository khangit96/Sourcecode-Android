package com.demoandroinodemcu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import at.grabner.circleprogress.CircleProgressView;

import static com.demoandroinodemcu.R.id.btViewActivity;

/**
 * Created by Administrator on 2/21/2017.
 */

public class DayFragment extends Fragment {
    CircleProgressView circleProgressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_day, container, false);

        Bundle args = getArguments();
        int value = args.getInt("VALUE", 0);

        circleProgressView = (CircleProgressView) view.findViewById(R.id.circleView);
        circleProgressView.setText(String.valueOf(value));
        circleProgressView.setValueAnimated(value);

        String day=args.getString("DAY");
        TextView tvDay= (TextView) view.findViewById(R.id.tvDay);
        tvDay.setText(day);

        Button btViewActivity = (Button) view.findViewById(R.id.btViewActivity);
        btViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "View", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
