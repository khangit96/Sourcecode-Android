package khangit96.tdmuteamfhome;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Administrator on 10/11/2016.
 */

public class Person implements ClusterItem {
    public String title;
    public String snippet;
    public LatLng latLng;
    public BitmapDescriptor icon;
    public Person(MarkerOptions markerOptions) {
        this.latLng = markerOptions.getPosition();
        this.title = markerOptions.getTitle();
        this.snippet = markerOptions.getSnippet();
        this.icon = markerOptions.getIcon();
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }
}
