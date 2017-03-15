package com.demoandroinodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class ChieuCaoFragment extends Fragment {
    public static EditText edChieuCao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chieu_cao, container, false);

        edChieuCao = (EditText) view.findViewById(R.id.edChieuCao);
        return view;
    }


}
