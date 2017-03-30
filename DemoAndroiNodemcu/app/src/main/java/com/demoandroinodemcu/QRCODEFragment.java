package com.demoandroinodemcu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.sudar.zxingorient.ZxingOrient;


public class QRCODEFragment extends Fragment {
    public static Button btChieuCao;
    public static EditText edQRCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        edQRCode = (EditText) view.findViewById(R.id.edQRCODE);
        btChieuCao = (Button) view.findViewById(R.id.btScan);
        btChieuCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ZxingOrient(getActivity()).initiateScan();
            }
        });
        return view;
    }


}
