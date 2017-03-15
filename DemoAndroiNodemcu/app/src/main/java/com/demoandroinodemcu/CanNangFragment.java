package com.demoandroinodemcu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class CanNangFragment extends Fragment {
    public static EditText edCangNang;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_can_nang, container, false);

        edCangNang = (EditText) view.findViewById(R.id.edCangNang);
        return view;
    }

}
