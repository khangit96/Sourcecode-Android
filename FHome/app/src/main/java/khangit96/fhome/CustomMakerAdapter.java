package khangit96.fhome;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Administrator on 6/29/2016.
 */
public class CustomMakerAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    NhaTro nhaTro;

    public CustomMakerAdapter(Activity context, NhaTro nhaTro) {
        this.context = context;
        this.nhaTro = nhaTro;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.maker_nha_tro, null);
      /*  TextView tvTenMaker = (TextView) row.findViewById(R.id.tvTenMaker);
        tvTenMaker.setText("Duy Khang");*/
        return row;
    }
}
